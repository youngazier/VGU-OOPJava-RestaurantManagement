package com.vgu.restaurant.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    private static final Path IMAGE_DIR = Paths
            .get("D:/CSE-VGU/3rd year (2025-2026)/OOP Java/RestaurantManagement/images")
            .toAbsolutePath()
            .normalize();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Image name is required");
            return;
        }

        String decoded = URLDecoder.decode(pathInfo, StandardCharsets.UTF_8);
        String fileName = Paths.get(decoded).getFileName().toString();
        Path requestedFile = IMAGE_DIR.resolve(fileName).normalize();

        if (!requestedFile.startsWith(IMAGE_DIR) || !Files.exists(requestedFile) || Files.isDirectory(requestedFile)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
            return;
        }

        String contentType = Files.probeContentType(requestedFile);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        resp.setContentType(contentType);
        resp.setHeader("Cache-Control", "max-age=86400");

        Files.copy(requestedFile, resp.getOutputStream());
    }
}

