package com.vgu.restaurant.controller;

import com.google.gson.Gson;
import com.vgu.restaurant.model.Customer;
import com.vgu.restaurant.model.Employee;
import com.vgu.restaurant.model.Role;
import com.vgu.restaurant.model.User;
import com.vgu.restaurant.service.UserService;
import com.vgu.restaurant.service.UserServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/users/*")
public class UserController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private final Gson gson = new Gson();

    // DTO để nhận JSON từ client
    private static class UserRequest {
        String username;
        String password;
        String fullName;
        String phone;
        String role; // CUSTOMER, WAITER, MANAGER, CHEF
    }

    // GET /api/users           → get all users
    // GET /api/users/{id}      → get user by id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo(); // /{id} hoặc null

        if (pathInfo == null || pathInfo.equals("/")) {
            // GET all users
            List<User> users = userService.getAll();
            resp.getWriter().write(gson.toJson(users));
        } else {
            // GET user by id
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                User user = userService.getById(id);

                if (user == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"User not found\"}");
                    return;
                }

                resp.getWriter().write(gson.toJson(user));

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid ID\"}");
            }
        }
    }

    // POST /api/users  → register user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        BufferedReader reader = req.getReader();
        UserRequest body = gson.fromJson(reader, UserRequest.class);

        if (body == null || body.username == null || body.password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"username and password are required\"}");
            return;
        }

        // Xử lý role: nếu null → CUSTOMER
        Role role;
        try {
            if (body.role == null || body.role.isBlank()) {
                role = Role.CUSTOMER;
            } else {
                role = Role.valueOf(body.role.toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid role\"}");
            return;
        }

        // Tạo User cụ thể: Customer hoặc Employee
        User newUser;
        if (role == Role.CUSTOMER) {
            newUser = new Customer(
                    0,
                    body.username,
                    body.password,
                    body.fullName,
                    body.phone
            );
        } else {
            newUser = new Employee(
                    0,
                    body.username,
                    body.password,
                    body.fullName,
                    role,
                    body.phone
            );
        }

        boolean success = userService.register(newUser);

        if (success) {
            resp.getWriter().write("{\"status\":\"OK\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"status\":\"FAILED\",\"message\":\"Username may already exist\"}");
        }
    }
}
