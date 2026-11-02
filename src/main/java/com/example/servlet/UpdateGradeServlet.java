package com.example.servlet;

import com.example.dao.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/update-grade")
public class UpdateGradeServlet extends HttpServlet {

    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // Đã sửa kiểu 'string' thành 'String' (chữ S viết hoa)
        String maSv = req.getParameter("maSv");
        String hoTen = req.getParameter("hoTen");
        float diemQuaTrinh = Float.parseFloat(req.getParameter("diemQuaTrinh"));
        float diemThi = Float.parseFloat(req.getParameter("diemThi"));

        // Cập nhật Họ Tên và Điểm vào CSDL
        studentDAO.updateStudentAndGrade(maSv, hoTen, diemQuaTrinh, diemThi);

        // Quay lại trang Dashboard
        resp.sendRedirect("dashboard");
    }
}