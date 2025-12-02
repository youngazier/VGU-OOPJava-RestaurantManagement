package com.vgu.restaurant.controller;

import com.google.gson.Gson;
import com.vgu.restaurant.model.Table;
import com.vgu.restaurant.model.TableStatus;
import com.vgu.restaurant.service.TableService;
import com.vgu.restaurant.service.TableServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/api/tables/*")
public class TableController extends HttpServlet {

    private final TableService tableService = new TableServiceImpl();
    private final Gson gson = new Gson();

    private static class TableRequest {
        Integer capacity;
        String status;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        TableRequest body = readBody(req);

        if (body.capacity == null || body.status == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing fields\"}");
            return;
        }

        Table table = new Table(
            body.capacity,
            TableStatus.valueOf(body.status)
        );

        boolean created = tableService.create(table);

        if (created) {
            resp.getWriter().write(gson.toJson(table));
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Failed to create table\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo(); // /, /1, /2

        // GET /api/tables
        if (path == null || path.equals("/") || path.equals("")) {
            List<Table> tables = tableService.getAll();
            resp.getWriter().write(gson.toJson(tables));
            return;
        }

        // GET /api/tables/{id}
        try {
            int id = Integer.parseInt(path.substring(1));
            Optional<Table> table = tableService.getById(id);

            if (table.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Table not found\"}");
                return;
            }
            resp.getWriter().write(gson.toJson(table.get()));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid table ID\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo();

        if (path == null || path.equals("/") || path.length() < 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid table ID\"}");
            return;
        }

        // /{id} or /{id}/status
        String[] parts = path.substring(1).split("/");

        int tableId;
        try {
            tableId = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid table ID\"}");
            return;
        }

        // PUT /api/tables/{id}/status
        if (parts.length == 2 && parts[1].equals("status")) {

            TableRequest body = readBody(req);

            if (body.status == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Missing status\"}");
                return;
            }

            boolean updated = tableService.updateStatus(tableId, TableStatus.valueOf(body.status));
            resp.getWriter().write("{\"success\": " + updated + "}");
            return;
        }

        // PUT /api/tables/{id}
        TableRequest body = readBody(req);

        Optional<Table> tableOptional = tableService.getById(tableId);
        if (tableOptional.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Table not found\"}");
            return;
        }

        Table t = tableOptional.get();

        if (body.capacity != null) t.setCapacity(body.capacity);
        if (body.status != null) t.setStatus(TableStatus.valueOf(body.status));

        boolean updated = tableService.update(t);

        resp.getWriter().write("{\"success\": " + updated + "}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo();

        if (path == null || path.equals("/") || path.length() < 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid table ID\"}");
            return;
        }

        try {
            int id = Integer.parseInt(path.substring(1));
            boolean deleted = tableService.delete(id);

            resp.getWriter().write("{\"success\": " + deleted + "}");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid table ID\"}");
        }
    }

    private TableRequest readBody(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) sb.append(line);

        return gson.fromJson(sb.toString(), TableRequest.class);
    }
}
