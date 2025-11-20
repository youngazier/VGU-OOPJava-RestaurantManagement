package com.vgu.restaurant.controller;

import com.google.gson.Gson;
import com.vgu.restaurant.model.MenuItem;
import com.vgu.restaurant.service.MenuItemService;
import com.vgu.restaurant.service.MenuItemServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/api/menu-items/*")
public class MenuItemController extends HttpServlet {

    private final MenuItemService menuItemService = new MenuItemServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo(); // "/{id}" hoặc "/"

        if (path == null || path.equals("/")) {
            List<MenuItem> items = menuItemService.getAll();
            resp.getWriter().write(gson.toJson(items));
            return;
        }

        // GET /api/menu-items/{id}
        try {
            int id = Integer.parseInt(path.substring(1));
            Optional<MenuItem> item = menuItemService.getById(id);

            if (item.isEmpty()) {
                resp.getWriter().write("{\"error\":\"Not found\"}");
            } else {
                resp.getWriter().write(gson.toJson(item.get()));
            }

        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\":\"Invalid ID\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BufferedReader reader = req.getReader();
        MenuItem item = gson.fromJson(reader, MenuItem.class);

        boolean success = menuItemService.add(item);

        resp.setContentType("application/json");
        resp.getWriter().write("{\"success\":" + success + "}");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            resp.getWriter().write("{\"error\":\"Missing ID\"}");
            return;
        }

        try {
            int id = Integer.parseInt(path.substring(1));

            BufferedReader reader = req.getReader();
            MenuItem item = gson.fromJson(reader, MenuItem.class);
            item.setId(id);

            boolean success = menuItemService.update(item);

            resp.getWriter().write("{\"success\":" + success + "}");

        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\":\"Invalid ID\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            resp.getWriter().write("{\"error\":\"Missing ID\"}");
            return;
        }

        try {
            int id = Integer.parseInt(path.substring(1));
            boolean success = menuItemService.delete(id);

            resp.getWriter().write("{\"success\":" + success + "}");

        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\":\"Invalid ID\"}");
        }
    }
}
