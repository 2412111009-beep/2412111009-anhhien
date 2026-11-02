package com.example.servlet;

import com.example.dao.StudentDAO;
import com.example.model.StudentGradeDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        List<StudentGradeDTO> grades = studentDAO.getGradesByClassCode("CNTT01");

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"vi\"><head><meta charset=\"UTF-8\">");
        out.println("<title>Dashboard - Quản lý điểm CNTT</title>");
        out.println("<style>");
        out.println("table { border-collapse: collapse; width: 90%; margin-top: 15px; }");
        out.println("th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }");
        out.println("th { background-color: #4CAF50; color: white; }");
        out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
        out.println("input[type='number'] { width: 70px; padding: 5px; }");
        out.println("button { background-color: #008CBA; color: white; border: none; padding: 6px 12px; cursor: pointer; border-radius: 4px; }");
        out.println("button:hover { background-color: #005f73; }");
        out.println(".btn-init { background-color: #ff9800; color: white; border: none; padding: 8px 16px; cursor: pointer; border-radius: 4px; font-weight: bold; margin-bottom: 15px; }");
        out.println(".btn-init:hover { background-color: #e68a00; }");
        out.println("</style></head><body>");

        out.println("<h1>Chào mừng, Admin!</h1>");
        out.println("<h2>Danh sách & Cập nhật điểm Lớp CNTT01</h2>");

        // Nút tự động khởi tạo CSDL bằng Java
        out.println("<form action='init-db' method='POST' style='display:inline-block;'>");
        out.println("<button type='submit' class='btn-init'>⚡ Tự động tạo Bảng & Dữ liệu mẫu (Bằng Java)</button>");
        out.println("</form>");

        out.println("<table>");
        out.println("<tr><th>Mã SV</th><th>Họ Tên</th><th>Lớp</th><th>Môn Học</th><th>Điểm Quá Trình</th><th>Điểm Thi</th><th>Điểm Tổng Kết</th><th>Thao Tác</th></tr>");

        if (grades != null && !grades.isEmpty()) {
            for (StudentGradeDTO dto : grades) {
                out.println("<tr>");
                out.println("<form action='update-grade' method='POST'>");
                out.println("<td>" + dto.getMaSv() + "<input type='hidden' name='maSv' value='" + dto.getMaSv() + "'></td>");
                out.println("<td>" + dto.getHoTen() + "</td>");
                out.println("<td>" + dto.getTenLop() + "</td>");
                out.println("<td>" + dto.getTenMon() + "</td>");
                out.println("<td><input type='number' step='0.1' min='0' max='10' name='diemQuaTrinh' value='" + dto.getDiemQuaTrinh() + "'></td>");
                out.println("<td><input type='number' step='0.1' min='0' max='10' name='diemThi' value='" + dto.getDiemThi() + "'></td>");
                out.println("<td><strong>" + dto.getDiemTongKet() + "</strong></td>");
                out.println("<td><button type='submit'>Cập nhật</button></td>");
                out.println("</form>");
                out.println("</tr>");
            }
        } else {
            out.println("<tr><td colspan='8' style='color: red; text-align: center;'>Chưa có dữ liệu sinh viên. Nhấn nút <b>'⚡ Tự động tạo Bảng...'</b> ở trên để tạo bảng và chèn dữ liệu!</td></tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}