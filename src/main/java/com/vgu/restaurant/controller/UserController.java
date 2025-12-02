package com.vgu.restaurant.controller;

import com.google.gson.Gson;
import com.vgu.restaurant.model.*;
import com.vgu.restaurant.service.UserService;
import com.vgu.restaurant.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/api/users/*")
public class UserController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private final Gson gson = new Gson();

    private static class RegisterRequest {
        String username;
        String password;
        String fullName;
        String phone;
        String role;
    }

    private static class LoginRequest {
        String username;
        String password;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo(); // /register hoặc /login

        if (path == null) {
            writeError(resp, "Invalid endpoint");
            return;
        }

        switch (path) {
            case "/register":
                handleRegister(req, resp);
                break;

            case "/login":
                handleLogin(req, resp);
                break;

            default:
                writeError(resp, "Unknown endpoint: " + path);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        User loggedIn = (User)  req.getSession().getAttribute("user");
        if (loggedIn == null) {
            writeError(resp, "You must login first!");
            return;
        }

        if (loggedIn.getRole() == Role.CUSTOMER) {
            writeError(resp, "Access denied. Customers cannot view user list.");
            return;
        }

        String path = req.getPathInfo(); // / , /all
        if (path == null || path.equals("/") || path.equals("/all")) {
            resp.getWriter().write(gson.toJson(userService.getAll()));
            return;
        }

        writeError(resp, "Unknown GET endpoint: " + path);
    }

    // POST /api/users/register
    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        RegisterRequest data = parseJson(req, RegisterRequest.class);

        if (data == null) {
            writeError(resp, "Invalid JSON format");
            return;
        }

        Role role = Role.fromString(data.role);

        User user;
        if (role == Role.CUSTOMER) {
            user = new Customer(data.username, data.password, data.fullName, data.phone);
        } else {
            user = new Employee(data.username, data.password, data.fullName, role, data.phone);
        }

        boolean ok = userService.register(user);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", ok);

        if (ok) {
            responseData.put("message", "User registered successfully");
        } else {
            responseData.put("message", "Registration failed (username may already exist)");
        }

        resp.getWriter().write(gson.toJson(responseData));
    }

    // POST /api/users/login
    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        LoginRequest data = parseJson(req, LoginRequest.class);

        if (data == null) {
            writeError(resp, "Invalid JSON");
            return;
        }

        Optional<User> opt = userService.login(data.username, data.password);

        if (opt.isEmpty()) {
            writeError(resp, "Invalid username or password");
            return;
        }

        User user = opt.get();  // LẤY USER RA ĐỂ DÙNG

        // Save user to the current session
        req.getSession().setAttribute("user", user);

        // success
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("id", user.getId());
        res.put("username", user.getUsername());
        res.put("fullName", user.getFullName());
        res.put("role", user.getRole().toString());

        resp.getWriter().write(gson.toJson(res));
    }

    // Parse JSON helper
    private <T> T parseJson(HttpServletRequest req, Class<T> clazz) {
        try {
            BufferedReader reader = req.getReader();
            return gson.fromJson(reader, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    private void writeError(HttpServletResponse resp, String message) throws IOException {
        Map<String, Object> res = new HashMap<>();
        res.put("success", false);
        res.put("error", message);
        resp.getWriter().write(gson.toJson(res));
    }
}
