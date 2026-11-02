package com.example.dao;

import com.example.model.StudentGradeDTO;
import com.example.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<StudentGradeDTO> getGradesByClassCode(String maLop) {
        List<StudentGradeDTO> list = new ArrayList<>();
        String sql = "SELECT sv.ma_sv, sv.ho_ten, l.ten_lop, d.ten_mon, d.diem_qua_trinh, d.diem_thi, d.diem_tong_ket " +
                     "FROM sinh_vien sv " +
                     "JOIN lop l ON sv.lop_id = l.id " +
                     "JOIN diem d ON sv.id = d.sinh_vien_id " +
                     "WHERE l.ma_lop = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maLop);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new StudentGradeDTO(
                        rs.getString("ma_sv"),
                        rs.getString("ho_ten"),
                        rs.getString("ten_lop"),
                        rs.getString("ten_mon"),
                        rs.getFloat("diem_qua_trinh"),
                        rs.getFloat("diem_thi"),
                        rs.getFloat("diem_tong_ket")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật cả Họ tên và Điểm của sinh viên
    public boolean updateStudentAndGrade(String maSv, String hoTen, float diemQuaTrinh, float diemThi) {
        float diemTongKet = (float) Math.round((diemQuaTrinh * 0.3f + diemThi * 0.7f) * 10) / 10;

        String sql = "UPDATE diem d " +
                     "JOIN sinh_vien sv ON d.sinh_vien_id = sv.id " +
                     "SET sv.ho_ten = ?, d.diem_qua_trinh = ?, d.diem_thi = ?, d.diem_tong_ket = ? " +
                     "WHERE sv.ma_sv = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hoTen);
            ps.setFloat(2, diemQuaTrinh);
            ps.setFloat(3, diemThi);
            ps.setFloat(4, diemTongKet);
            ps.setString(5, maSv);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean initDatabaseDirectly() {
        String sqlLop = "CREATE TABLE IF NOT EXISTS lop (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "ma_lop VARCHAR(20) NOT NULL UNIQUE, " +
                        "ten_lop VARCHAR(100) NOT NULL)";

        String sqlSinhVien = "CREATE TABLE IF NOT EXISTS sinh_vien (" +
                             "id INT AUTO_INCREMENT PRIMARY KEY, " +
                             "ma_sv VARCHAR(20) NOT NULL UNIQUE, " +
                             "ho_ten VARCHAR(100) NOT NULL, " +
                             "lop_id INT, " +
                             "FOREIGN KEY (lop_id) REFERENCES lop(id))";

        String sqlDiem = "CREATE TABLE IF NOT EXISTS diem (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "sinh_vien_id INT, " +
                         "ten_mon VARCHAR(100) NOT NULL, " +
                         "diem_qua_trinh FLOAT DEFAULT 0, " +
                         "diem_thi FLOAT DEFAULT 0, " +
                         "diem_tong_ket FLOAT DEFAULT 0, " +
                         "FOREIGN KEY (sinh_vien_id) REFERENCES sinh_vien(id))";

        String insertLop = "INSERT INTO lop (ma_lop, ten_lop) VALUES ('CNTT01', 'Công Nghệ Thông Tin 1') " +
                           "ON DUPLICATE KEY UPDATE ten_lop=VALUES(ten_lop)";

        String insertSV = "INSERT INTO sinh_vien (ma_sv, ho_ten, lop_id) VALUES " +
                          "('SV001', 'Nguyễn Văn A', 1), " +
                          "('SV002', 'Trần Thị B', 1) " +
                          "ON DUPLICATE KEY UPDATE ho_ten=VALUES(ho_ten)";

        String insertDiem = "INSERT INTO diem (sinh_vien_id, ten_mon, diem_qua_trinh, diem_thi, diem_tong_ket) VALUES " +
                            "(1, 'Lập trình Java', 8.5, 7.0, 7.5), " +
                            "(2, 'Lập trình Java', 9.0, 8.0, 8.3) " +
                            "ON DUPLICATE KEY UPDATE diem_tong_ket=VALUES(diem_tong_ket)";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlLop);
            stmt.execute(sqlSinhVien);
            stmt.execute(sqlDiem);
            stmt.execute(insertLop);
            stmt.execute(insertSV);
            stmt.execute(insertDiem);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}