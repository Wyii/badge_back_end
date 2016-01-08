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
    private static String WEXIN_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=#ACCESS_TOKEN#&department_id=#DEPARTMENT_ID#&fetch_child=#FETCH_CHILD#&status=#STATUS#";
    private static String ACCESSTOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=#id#&corpsecret=#secrect#";

    private static String secret = "4EWw34BFbCKto0BvB0f5WxAl2I2_RWcCa5JBdLNp_w9th9PuJwsRq1Ek7NXVOmD7";
    private static String corpId = "wxd6e1a181d0c0d42f";
    private JsonGenerator jsonGenerator = null;

    public static ArrayList USERLIST = new ArrayList();

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
                                    .replace("#DEPARTMENT_ID#","1")
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
