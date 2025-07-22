/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;



/**
 *
 * @author Admin
 */
// XAuth
public class XAuth {
    public static Employee nhanVien = null;

    public static void clear() {
        nhanVien = null;
    }

    public static boolean isManager() {
        return nhanVien != null && "Quản lý".equalsIgnoreCase(nhanVien.getChucVu());
    }
}

