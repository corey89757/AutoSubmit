import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoSubmitMain2 {

    public static List<String> fileList = new ArrayList<String>();
    static{
        for(int i=5175,count =5176;i<count;i++) {
            fileList.add(String.valueOf(i));
        }

    }

    public static void main(String[] args) {
        for(int i=0,count = fileList.size();i<count;i++){
            autoSubmit(fileList.get(i));
            try{
                Thread.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void autoSubmit(String pdbId) {//"1i1n";//2xpg
        System.out.println(pdbId);
        /**get token and cookid**/
        HttpResponse httpResponse = HttpRequest.get("https://soft.dezyme.com/login").execute();

        String csrf_token = StrUtil.subAfter(httpResponse.body(), "\"_csrf_token\" value=\"", false);
        csrf_token = StrUtil.subBefore(csrf_token, "\"/>", false);

        System.out.println("csrf_token : " + csrf_token);

        /**do login**/

        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("_csrf_token", csrf_token);
//        hashMap.put("_username", "376773242@qq.com");
//        hashMap.put("_password", "wujian88729");
        hashMap.put("_username", "estellequan@gmail.com");
        hashMap.put("_password", "Qlj7712988.");
        hashMap.put("_submit", "Login");

        httpResponse = HttpRequest.post("https://soft.dezyme.com/login_check")
//                .header("Cookie", cookieStr)
                .form(hashMap)
                .execute();

        List<HttpCookie> cookies = httpResponse.getCookies();

        String cookieName = "";
        String cookieValue = "";
        for (HttpCookie httpCookie : cookies) {
            System.out.println("name : " + httpCookie.getName() + " , value : " + httpCookie.getValue());
            cookieName = httpCookie.getName();
            cookieValue = httpCookie.getValue();
            break;
        }

        /*String cookieStr = httpResponse.getCookieStr();
        System.out.println(cookieStr);*/

        /** enter snp page **/
        httpResponse = HttpRequest.get("https://soft.dezyme.com/query/create/snp")
                .header("Cookie", cookieName + "=" + cookieValue)
                .execute();
//        System.out.println(httpResponse.body());
        String pdbSearchFormToken = StrUtil.subAfter(httpResponse.body(), "name=\"PdbSearchForm[_token]\" value=\"", false);
        pdbSearchFormToken = StrUtil.subBefore(pdbSearchFormToken, "\" /></div></span>", false);
        System.out.println("pdbSearchFormToken : " + pdbSearchFormToken);

        String queryProcessFormToken = StrUtil.subAfter(httpResponse.body(), "name=\"queryProcessForm[_token]\" value=\"", false);
        queryProcessFormToken = StrUtil.subBefore(queryProcessFormToken, "\" /></div>", false);
        System.out.println("queryProcessFormToken : " + queryProcessFormToken);


        /** build **/
        hashMap = new HashMap<String, Object>();
        hashMap.put("pdbId", pdbId);
        hashMap.put("mutfileId", "");
        hashMap.put("queryProcessForm[mode]", "syst");
        hashMap.put("queryProcessForm[_token]", queryProcessFormToken);
        httpResponse = HttpRequest.post("https://soft.dezyme.com/query/build/snp")
                .header("Cookie", cookieName + "=" + cookieValue)
//                .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
//                .header("Accept-Encoding","gzip, deflate, br")
//                .header("Accept-Language","zh-CN,zh;q=0.9")
//                .header("Cache-Control","max-age=0")
//                .header("Connection","keep-alive")
//                .header("Content-Length","123")
//                .header("Content-Type","application/x-www-form-urlencoded")
//                .header("Host","soft.dezyme.com")
//                .header("Origin","https://soft.dezyme.com")
//                .header("Referer","https://soft.dezyme.com/query/create/snp")
//                .header("Upgrade-Insecure-Requests","1")
//                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
                .form(hashMap)
                .timeout(30000)
                .execute();
//        System.out.println(httpResponse.body());

        /**list**//*
        httpResponse = HttpRequest.post("https://soft.dezyme.com/result/list")
                .header("Cookie", cookieName + "=" + cookieValue)
                .timeout(30000)
                .execute();
        System.out.println(httpResponse.body());


        *//** process **//*
        httpResponse = HttpRequest.post("https://soft.dezyme.com/result/process.json")
                .header("Cookie", cookieName + "=" + cookieValue)
                .timeout(30000)
                .execute();
        System.out.println(httpResponse.body());*/
    }


}
