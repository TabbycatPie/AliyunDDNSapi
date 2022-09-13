package com.caliburn.servlet;

import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponse;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponseBody;
import com.aliyun.alidns20150109.models.UpdateDomainRecordResponse;
import com.aliyun.tea.TeaException;
import org.bouncycastle.tsp.TSPUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.caliburn.servlet.AliUtils.queryDns;
import static com.caliburn.servlet.AliUtils.updateDNS;


public class UpdateDomainName extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //get info from req
        String domain_name = req.getParameter("domain_name");
        String new_ip = req.getParameter("new_ip");
        System.out.println("domain_name: "+ domain_name);
        System.out.println("new_ip     : "+new_ip);

        AliDomainName alidomainname = new AliDomainName();
        List<AliDomainName> records;
        AliClient client = new AliClient(AliUtils.getAccessId(this),AliUtils.getPasscode(this));
        List<String> errors = new ArrayList<>();

        //get domain name list
        try {
            //gets response from Aliyun openapi(DDNS services)
            String root_domain = domain_name.substring(domain_name.indexOf(".")+1);
            records = queryDns(client,root_domain,errors);
            //search for correct domain name
            if (records == null) {
                return;
            }
            for(AliDomainName dn : records){
                if(domain_name.equals(dn.getRr()+"."+dn.getDomain_name())){
                    alidomainname = dn;
                    break;
                }
            }
            if("".equals(alidomainname.getRecord_id())){
                System.out.println("There is no valid domain name.");
                errors.add("There is no valid domain name.");
                return;
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Domain name query failed.");
            errors.add("Domain name query failed.");
        }

        //Use the report id to update dns
        boolean success =false;
        try{
            if(!alidomainname.isEmpty()) {
                success = updateDNS(client, alidomainname, new_ip,errors);
                if(new_ip.equals(alidomainname.getIp_value()))
                    success = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        PrintWriter writer = resp.getWriter();

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        if(!success){
            writer.println("Failed");
            System.out.println("Failed");
            for(String e : errors){
                System.out.println(e);
            }
        }else {
            writer.println("Success");
            System.out.println("Success");
        }
        System.out.println("Time: " + formatter.format(date));

    }

}
