package com.vgu.restaurant.controller;

import com.google.gson.Gson;
import com.vgu.restaurant.model.*;
import com.vgu.restaurant.service.OrderService;
import com.vgu.restaurant.service.OrderServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/api/orders/*")
public class OrderController extends HttpServlet {

    private final OrderService orderService = new OrderServiceImpl();
    private final Gson gson = new Gson();

    // DTO cho request create/update
    private static class OrderRequest {
        int tableId;
        Integer customerId;
        List<OrderItem> items;
        String status;
        LocalDateTime createdAt;
        String note;
    }

    private static class StatusRequest {
        String status;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo(); // / , /{id}

        if (path == null || path.equals("/")) {
            List<Order> list = orderService.getAll();
            resp.getWriter().write(gson.toJson(list));
            return;
        }

        // /{id}
        try {
            int id = Integer.parseInt(path.substring(1));
            Optional<Order> order = orderService.getById(id);

            if (order == null) {
                resp.setStatus(404);
                resp.getWriter().write("{\"error\":\"Order not found\"}");
                return;
            }

            resp.getWriter().write(gson.toJson(order));

        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"Invalid order ID\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        BufferedReader reader = req.getReader();
        OrderRequest body = gson.fromJson(reader, OrderRequest.class);

        Order order = new Order(
                body.tableId,
                body.customerId,
                body.items,
                OrderStatus.valueOf(body.status),
                body.createdAt,
                body.note
        );

        boolean ok = orderService.create(order);
        if (!ok) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"Failed to create order\"}");
            return;
        }

        resp.getWriter().write("{\"message\":\"Order created successfully\"}");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo(); // /{id}/status

        if (path == null || !path.matches("^/\\d+/status$")) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"Invalid endpoint\"}");
            return;
        }

        int id = Integer.parseInt(path.split("/")[1]);

        BufferedReader reader = req.getReader();
        StatusRequest body = gson.fromJson(reader, StatusRequest.class);

        boolean ok = orderService.updateStatus(id, OrderStatus.valueOf(body.status));

        if (!ok) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"Failed to update status\"}");
            return;
        }

        resp.getWriter().write("{\"message\":\"Status updated successfully\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo(); // /{id}

        if (path == null || path.equals("/")) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"Missing order ID\"}");
            return;
        }

        try {
            int id = Integer.parseInt(path.substring(1));

            boolean ok = orderService.delete(id);
            if (!ok) {
                resp.setStatus(404);
                resp.getWriter().write("{\"error\":\"Order not found / cannot delete\"}");
                return;
            }

            resp.getWriter().write("{\"message\":\"Order deleted successfully\"}");

        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"Invalid order ID\"}");
        }
    }
}