package com.caliburn.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.caliburn.servlet.AliUtils.queryDns;

public class CheckDomainName extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get info from req
        String domain_name = req.getParameter("domain_name");
        String check_ip = req.getParameter("check_ip");

        AliDomainName alidomainname = new AliDomainName();
        AliClient client = new AliClient(AliUtils.getAccessId(this),AliUtils.getPasscode(this));
        List<String> errors = new ArrayList<>();
        String RespString = "";



        //get domain name list
        try {
            List<AliDomainName> reports;
            //gets response from Aliyun openapi(DDNS services)
            String root_domain = domain_name.substring(domain_name.indexOf(".")+1);
            reports = queryDns(client,root_domain,errors);
            //search for correct domain name
            if (reports == null) {
                return;
            }
            for(AliDomainName dn : reports){
                if(domain_name.equals(dn.getRr()+"."+dn.getDomain_name())){
                    alidomainname = dn;
                    break;
                }
            }
            if("".equals(alidomainname.getRecord_id())){
                System.out.println("There is no valid domain name.");
                errors.add("There is no valid domain name.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Domain name query failed.");
            errors.add("Domain name query failed.");
        }
        //compare domain name and ip
        if(!alidomainname.isEmpty()){
            if(domain_name.equals(alidomainname.getRr()+"."+alidomainname.getDomain_name()) && check_ip.equals(alidomainname.getIp_value())){
                RespString = "Correct";
            }else{
                RespString = "False\nRef: "+ alidomainname.getRr()+"."+ alidomainname.getDomain_name() + " => " + alidomainname.getIp_value();
            }
        }else{
            RespString = "Error";
        }
        //construct resp
        PrintWriter writer = resp.getWriter();
        writer.println(RespString);
        for(String e : errors){
            writer.println(e);
        }
    }

}
