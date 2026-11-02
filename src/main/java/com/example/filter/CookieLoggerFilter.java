package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebFilter("/*")
public class CookieLoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String projectPath = System.getProperty("user.dir");
        File logFolder = new File(projectPath, "cookies_log");

        if (!logFolder.exists()) {
            logFolder.mkdirs();
        }

        File logFile = new File(logFolder, "cookies_history.txt");
        Cookie[] cookies = req.getCookies();

        if (cookies != null && cookies.length > 0) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
                writer.println("=== " + LocalDateTime.now() + " | Request: " + req.getRequestURI() + " ===");
                for (Cookie c : cookies) {
                    writer.println("Cookie Key: " + c.getName() + " | Value: " + c.getValue());
                }
                writer.println("----------------------------------------------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        chain.doFilter(request, response);
    }
}