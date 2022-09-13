package com.caliburn.servlet;

import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponse;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsResponseBody;
import com.aliyun.alidns20150109.models.UpdateDomainRecordResponse;
import com.aliyun.tea.TeaException;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public interface AliUtils {
    public static String getAccessId(HttpServlet servlet){
        InputStream is = servlet.getServletContext().getResourceAsStream("/WEB-INF/classes/AliyunAPIKey.properties");
        Properties prop = new Properties();
        try {
            prop.load(is);
            String ret = prop.getProperty("accessid");
            is.close();
            return ret;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String getPasscode(HttpServlet servlet){
        InputStream is = servlet.getServletContext().getResourceAsStream("/WEB-INF/classes/AliyunAPIKey.properties");
        Properties prop = new Properties();
        try {
            prop.load(is);
            String ret = prop.getProperty("passcode");
            is.close();
            return ret;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.alidns20150109.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "alidns.cn-hongkong.aliyuncs.com";
        return new com.aliyun.alidns20150109.Client(config);


    }
    public  static  boolean updateDNS(AliClient _client, AliDomainName adn, String _new_ip, List<String> errors) throws Exception {
        com.aliyun.alidns20150109.Client client = createClient(_client.getId(),_client.getPass());
        com.aliyun.alidns20150109.models.UpdateDomainRecordRequest updateDomainRecordRequest = new com.aliyun.alidns20150109.models.UpdateDomainRecordRequest();
        //input parameters
        updateDomainRecordRequest.setLang("en");
        updateDomainRecordRequest.setRecordId(adn.getRecord_id());
        updateDomainRecordRequest.setRR(adn.getRr());
        updateDomainRecordRequest.setType(adn.getType());
        updateDomainRecordRequest.setValue(_new_ip);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            UpdateDomainRecordResponse udrr = client.updateDomainRecordWithOptions(updateDomainRecordRequest, runtime);
            if(adn.getRecord_id().equals(udrr.getBody().getRecordId())){
                System.out.println("Update successfully,record id:"+ adn.getRecord_id());
                return true;
            }else{
                System.out.println("Update failed,UpdateDomainRecordResponse is not correct.");
                errors.add("Update failed,UpdateDomainRecordResponse is not correct.");
                return false;
            }
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
            System.out.println("Update domain name failed:\n" + error.message);
            errors.add("Update domain name failed:\n" + error.message);
            return false;
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
            System.out.println("Update domain name failed:\n" + error.message);
            errors.add("Update domain name failed:\n" + error.message);
            return false;
        }
    }
    public static List<AliDomainName> queryDns(AliClient _client,String _domainName,List<String> errors) throws Exception {
        com.aliyun.alidns20150109.Client client = createClient(_client.getId(),_client.getPass());
        com.aliyun.alidns20150109.models.DescribeDomainRecordsRequest describeDomainRecordsRequest = new com.aliyun.alidns20150109.models.DescribeDomainRecordsRequest();
        describeDomainRecordsRequest.setLang("en");
        describeDomainRecordsRequest.setDomainName(_domainName);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            DescribeDomainRecordsResponse resp = client.describeDomainRecordsWithOptions(describeDomainRecordsRequest, runtime);
            java.util.List<DescribeDomainRecordsResponseBody.DescribeDomainRecordsResponseBodyDomainRecordsRecord> rlist = resp.getBody().getDomainRecords().getRecord();
            List<AliDomainName> record_list = new ArrayList<>();
            for(DescribeDomainRecordsResponseBody.DescribeDomainRecordsResponseBodyDomainRecordsRecord record:rlist){
                record_list.add(new AliDomainName(record.getRecordId(),record.getDomainName(),record.getRR(),record.getTTL(),record.getValue(),record.getType()));
            }
            return record_list;
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
            System.out.println("Query Domain name:"+_domainName+" failed.\n"+error.message);
            errors.add("Query Domain name:"+_domainName+" failed.\n"+error.message);
            error.printStackTrace();
            return null;
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
            System.out.println("Query Domain name:" + _domainName + " failed.\n"+error.message);
            errors.add("Query Domain name:" + _domainName + " failed.\n"+error.message);
            error.printStackTrace();
            return null;
        }
    }
}
