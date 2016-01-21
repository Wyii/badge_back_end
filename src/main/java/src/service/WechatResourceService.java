package src.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wyiss on 16/1/8.
 */
@Service
public class WechatResourceService {

    //获取企业号成员详情
    private static String WEXIN_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=#ACCESS_TOKEN#&department_id=#DEPARTMENT_ID#&fetch_child=#FETCH_CHILD#&status=#STATUS#";
    //获取AccessToken
    private static String ACCESSTOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=#id#&corpsecret=#secrect#";
    //根据code获取成员信息
    private static String CURRENT_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=#ACCESS_TOKEN#&code=#CODE#";

//    private static String secret = "4EWw34BFbCKto0BvB0f5WxAl2I2_RWcCa5JBdLNp_w9th9PuJwsRq1Ek7NXVOmD7";
//    private static String corpId = "wxd6e1a181d0c0d42f";

    //MIZI
    private static String corpId = "wx5ea398be4315d16d";
    private static String secret = "vJpYZOuweZPp42g83atLK08pXqAK3oifHMn80R2R6jyNl3qKR5LDtdS0Y4qX8OZP";

    private JsonGenerator jsonGenerator = null;

    public static ArrayList<HashMap> USERLIST = new ArrayList();
    public static HashMap USERMAP = new HashMap();


    public String getAccessToken(){
        String url = ACCESSTOKEN;
        String accessToken = "";
        try {
            URL obj = new URL(url.replace("#id#",corpId)
                                    .replace("#secrect#",secret));
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            InputStreamReader insr = new InputStreamReader(con.getInputStream());

            // 读取服务器的响应内容并显示
            int respInt = insr.read();
            StringBuffer result = new StringBuffer();
            while (respInt != -1) {
                result.append((char)respInt);
                respInt = insr.read();
            }
            ObjectMapper mapper = new ObjectMapper();
            HashMap resultMap =  mapper.readValue(result.toString(), HashMap.class);
            accessToken = (String) resultMap.get("access_token");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public ArrayList getMembers(){
        String url = WEXIN_USER;
        ArrayList userList = new ArrayList();
        try {
            URL obj = new URL(url.replace("#ACCESS_TOKEN#",getAccessToken())
                                    .replace("#DEPARTMENT_ID#", "1")
                                    .replace("#FETCH_CHILD#","1")
                                    .replace("#STATUS#","0"));

            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            InputStreamReader insr = new InputStreamReader(con.getInputStream());
            // 读取服务器的响应内容并显示
            int respInt = insr.read();
            StringBuffer result = new StringBuffer();
            while (respInt != -1) {
                result.append((char)respInt);
                respInt = insr.read();
            }
            ObjectMapper mapper = new ObjectMapper();
            HashMap resultMap =  mapper.readValue(result.toString(), HashMap.class);
            userList = (ArrayList) resultMap.get("userlist");
            USERLIST = userList;
            for (int i = 0; i < userList.size();i ++ ){
                String key = (String) ((HashMap)userList.get(i)).get("userid");
                USERMAP.put(key,userList.get(i));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public String getCurrentUser(String code){
        String url = CURRENT_USER;
        String userId = "";
        try {
            URL obj = new URL(url.replace("#ACCESS_TOKEN#",getAccessToken())
                                .replace("#CODE#",code));
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            InputStreamReader insr = new InputStreamReader(con.getInputStream());
            int respInt = insr.read();
            StringBuffer result = new StringBuffer();
            while (respInt != -1){
                result.append((char)respInt);
                respInt = insr.read();
            }
            ObjectMapper mapper = new ObjectMapper();
            HashMap resultMap = mapper.readValue(result.toString(), HashMap.class);
            userId = (String) resultMap.get("UserId");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userId;
    }
}
