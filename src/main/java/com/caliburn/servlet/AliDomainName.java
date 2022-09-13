package com.caliburn.servlet;

public class AliDomainName {
    private String domain_name = "";
    private boolean locked = false;
    private long priority = 0;
    private String rr = "";
    private String record_id = "";
    private long ttl = 0;
    private String remark = "";
    private String ip_value="0.0.0.0";
    private String status = "";
    private String type = "";
    private boolean is_empty = true;
    public AliDomainName() {
    }
    public AliDomainName(String domain_name, boolean locked, long priority, String rr, String record_id, long ttl, String remark, String ip_value, String status, String type) {
        this.domain_name = domain_name;
        this.locked = locked;
        this.priority = priority;
        this.rr = rr;
        this.record_id = record_id;
        this.ttl = ttl;
        this.remark = remark;
        this.ip_value = ip_value;
        this.status = status;
        this.type = type;
        this.is_empty = false;
    }

    public AliDomainName(String record_id,String domain_name, String rr, long ttl, String ip_value, String type) {
        this.record_id = record_id;
        this.domain_name = domain_name;
        this.rr = rr;
        this.ttl = ttl;
        this.ip_value = ip_value;
        this.type = type;
        this.is_empty = false;
    }

    public boolean isEmpty(){
        return this.is_empty;
    }
    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIp_value() {
        return ip_value;
    }

    public void setIp_value(String ip_value) {
        this.ip_value = ip_value;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
