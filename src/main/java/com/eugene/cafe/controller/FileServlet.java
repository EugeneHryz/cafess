package com.eugene.cafe.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet(name="upload", urlPatterns = "/upload/*")
public class FileServlet extends HttpServlet {

    private static final String basePath = "C:/Users/Eugene/Desktop/java_course/Final project/upload";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filePath = req.getPathInfo().replace("/", "\\");

        File file = new File(basePath + filePath);

        resp.setHeader("Content-Type", getServletContext().getMimeType(file.getName()));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
