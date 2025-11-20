package com.vgu.restaurant.controller;

import com.google.gson.Gson;
import com.vgu.restaurant.model.Order;
import com.vgu.restaurant.model.OrderItem;
import com.vgu.restaurant.model.OrderStatus;
import com.vgu.restaurant.service.OrderService;
import com.vgu.restaurant.service.OrderServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@WebServlet("/api/orders/*")
public class OrderController extends HttpServlet {

    private final OrderService orderService = new OrderServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo();

        // GET /api/orders
        if (path == null || path.equals("/")) {
            resp.getWriter().write(gson.toJson(orderService.getAll()));
            return;
        }

        // GET /api/orders/{id}
        try {
            int id = Integer.parseInt(path.substring(1));
            var order = orderService.getById(id);

            if (order.isPresent()) resp.getWriter().write(gson.toJson(order.get()));
            else resp.getWriter().write("{\"error\":\"Not found\"}");

        } catch (Exception e) {
            resp.getWriter().write("{\"error\":\"Invalid ID\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();

        // POST /api/orders → Tạo order mới
        if (path == null || path.equals("/")) {
            BufferedReader reader = req.getReader();
            Order order = gson.fromJson(reader, Order.class);

            boolean success = orderService.create(order);
            resp.getWriter().write("{\"success\":" + success + "}");
            return;
        }

        // POST /api/orders/{id}/items → thêm món
        if (path.matches("/\\d+/items")) {
            int orderId = Integer.parseInt(path.split("/")[1]);

            BufferedReader reader = req.getReader();
            OrderItem item = gson.fromJson(reader, OrderItem.class);

            boolean success = orderService.addItem(item.getOrderId(), item);
            resp.getWriter().write("{\"success\":" + success + "}");
            return;
        }

        resp.getWriter().write("{\"error\":\"Invalid POST endpoint\"}");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String path = req.getPathInfo();

        // PUT /api/orders/{itemId}/quantity
        if (path.matches("/\\d+/quantity")) {
            int itemId = Integer.parseInt(path.split("/")[1]);

            Map data = gson.fromJson(req.getReader(), Map.class);
            int qty = ((Double) data.get("quantity")).intValue();

            boolean success = orderService.updateItemQuantity(itemId, qty);
            resp.getWriter().write("{\"success\":" + success + "}");
            return;
        }

        // PUT /api/orders/{id}/status
        if (path.matches("/\\d+/status")) {
            int orderId = Integer.parseInt(path.split("/")[1]);

            Map data = gson.fromJson(req.getReader(), Map.class);
            OrderStatus newStatus = OrderStatus.valueOf((String) data.get("status"));

            boolean success = orderService.updateStatus(orderId, newStatus);
            resp.getWriter().write("{\"success\":" + success + "}");
            return;
        }

        resp.getWriter().write("{\"error\":\"Invalid PUT endpoint\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String path = req.getPathInfo();

        // DELETE /api/orders/items/{id}
        if (path.startsWith("/items/")) {
            int itemId = Integer.parseInt(path.substring(7));
            boolean success = orderService.removeItem(itemId);
            resp.getWriter().write("{\"success\":" + success + "}");
            return;
        }

        resp.getWriter().write("{\"error\":\"Invalid DELETE endpoint\"}");
    }
}