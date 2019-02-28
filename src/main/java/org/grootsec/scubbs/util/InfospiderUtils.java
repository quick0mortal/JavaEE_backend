package org.grootsec.scubbs.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.grootsec.scubbs.entity.Student;
import org.grootsec.scubbs.entity.StudentInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class InfospiderUtils {
    public static String login(Student student) {
        String html = "error";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("zjh", student.getStudentNumber()));
            nameValuePairs.add(new BasicNameValuePair("mm", student.getPassword()));
            HttpPost httpPost = new HttpPost("http://202.115.47.141/loginAction.do");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String temp = EntityUtils.toString(response.getEntity(), "gb2312");
            Document document = Jsoup.parse(temp);
            if (document.select("title").text().equals("四川大学本科教务系统 - 登录")) {
                html = "error";
                return html;
            }
            HttpGet httpGet = new HttpGet("http://202.115.47.141/xjInfoAction.do?oper=xjxx");
            CloseableHttpResponse response1 = httpClient.execute(httpGet);
            System.out.println(response1.getStatusLine().getStatusCode());
            if (response1.getStatusLine().getStatusCode() == 200) {
                html = EntityUtils.toString(response1.getEntity(), "gb2312");
                EntityUtils.consume(response1.getEntity());
            } else {
                html = "error";
                EntityUtils.consume(response1.getEntity());
            }
            html = new String(html.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    public static StudentInfo getData (String html) {

        StudentInfo studentInfo = new StudentInfo();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table[id=tblView]").select("td");
        studentInfo.setStudentName(elements.get(3).text());
        studentInfo.setStudentAcademy(elements.get(53).text());
        studentInfo.setStudentClass(elements.get(61).text());
        studentInfo.setStudentGrade(elements.get(59).text());
        studentInfo.setStudentNumber(elements.get(1).text());
        studentInfo.setStudentProfession(elements.get(55).text());
        studentInfo.setStudentSex(elements.get(15).text());
        return studentInfo;
    }
}
