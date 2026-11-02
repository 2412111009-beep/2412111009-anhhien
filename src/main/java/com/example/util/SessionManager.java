package com.example.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionManager {

    private SessionManager() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static void set(HttpServletRequest request, String key, Object value) {
        if (request != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute(key, value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(HttpServletRequest request, String key) {
        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                return (T) session.getAttribute(key);
            }
        }
        return null;
    }

    public static boolean has(HttpServletRequest request, String key) {
        return get(request, key) != null;
    }

    public static void remove(HttpServletRequest request, String key) {
        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(key);
            }
        }
    }

    /**
     * Sửa lỗi No Session: Tạo session nếu chưa có, hoặc đổi ID nếu đã có
     */
    public static void regenerateSessionId(HttpServletRequest request) {
        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session == null) {
                // Chưa có session -> Tạo session mới (tự sinh ID mới)
                request.getSession(true);
            } else {
                // Đã có session -> Tiến hành đổi ID
                request.changeSessionId();
            }
        }
    }

    public static void destroy(HttpServletRequest request) {
        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
    }
}