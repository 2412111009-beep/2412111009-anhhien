package com.example.model;

public class StudentGradeDTO {
    private String maSv;
    private String hoTen;
    private String tenLop;
    private String tenMon;
    private float diemQuaTrinh;
    private float diemThi;
    private float diemTongKet;

    public StudentGradeDTO(String maSv, String hoTen, String tenLop, String tenMon, float diemQuaTrinh, float diemThi, float diemTongKet) {
        this.maSv = maSv;
        this.hoTen = hoTen;
        this.tenLop = tenLop;
        this.tenMon = tenMon;
        this.diemQuaTrinh = diemQuaTrinh;
        this.diemThi = diemThi;
        this.diemTongKet = diemTongKet;
    }

    public String getMaSv() { return maSv; }
    public String getHoTen() { return hoTen; }
    public String getTenLop() { return tenLop; }
    public String getTenMon() { return tenMon; }
    public float getDiemQuaTrinh() { return diemQuaTrinh; }
    public float getDiemThi() { return diemThi; }
    public float getDiemTongKet() { return diemTongKet; }
}