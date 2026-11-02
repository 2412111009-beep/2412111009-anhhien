package com.example.servlet;

import com.example.util.SessionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("""
            <!DOCTYPE html>
            <html lang="vi">
            <head><meta charset="UTF-8"><title>Đăng nhập</title></head>
            <body>
                <h2>Đăng Nhập Hệ Thống</h2>
                <form method="POST" action="login">
                    <label>Username: <input type="text" name="username" required></label><br><br>
                    <label>Password: <input type="password" name="password" required></label><br><br>
                    <button type="submit">Đăng nhập</button>
                </form>
            </body>
            </html>
        """);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && "123456".equals(password)) {
            // Tái tạo Session ID bảo mật
            SessionManager.regenerateSessionId(req);

            // Lưu dữ liệu vào Session
            SessionManager.set(req, "username", username);
            SessionManager.set(req, "role", "administrator");

            resp.sendRedirect("dashboard");
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<p style='color:red;'>Mật khẩu hoặc tài khoản không đúng!</p>");
            resp.getWriter().println("<a href='login'>Thử lại</a>");
        }
    }
}