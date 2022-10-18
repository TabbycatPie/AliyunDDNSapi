package com.caliburn.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckIP extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String addr = req.getRemoteAddr();
        System.out.println("RemoteAddr:"+addr);
        String html = "<html><head><title>Current IP Check</title></head><body>Current IP Address:"+addr+"</body></html>";
        resp.getWriter().print(html);
    }
}
