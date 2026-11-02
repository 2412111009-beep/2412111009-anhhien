package com.example.servlet;

import com.example.dao.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/init-db")
public class InitDbServlet extends HttpServlet {

    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Tự động tạo bảng & chèn dữ liệu trực tiếp từ Java
        studentDAO.initDatabaseDirectly();

        resp.sendRedirect("dashboard");
    }
}