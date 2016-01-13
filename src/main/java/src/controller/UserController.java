package src.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import src.Domain.Record;
import src.Domain.User;
import src.service.RecordRepository;
import src.service.UserRepository;
import src.service.WechatResourceService;

import javax.persistence.Entity;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyiss on 15/12/23.
        */
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    WechatResourceService wechatResourceService;


//    @RequestMapping("/user/{id}")
//    public Map view(@PathVariable("id") Long id,ServletResponse res) {
//        User user =  userRepository.findById(id);
//        List recordList = new ArrayList<>();
//        Map toUserMap = new HashMap<>();
//        toUserMap.put("id",user.getId());
//        toUserMap.put("name",user.getName());
//        toUserMap.put("email",user.getEmail());
//        toUserMap.put("avatar",user.getAvatar());
//        List<Record> records = recordRepository.findByToUser(user);
//        for (int i = 0;i < records.size();i++){
//            Record record = records.get(i);
//
//            HashMap recordInfo = new HashMap();
//            recordInfo.put("fromUser",record.getFromUser().getName());
//            recordInfo.put("comment",record.getComment());
//            recordInfo.put("dateCreated",new SimpleDateFormat("yyyy-MM-dd").format(record.getDateCreated()));
//            recordInfo.put("badgeUrl",record.getBadge().getUrl());
//
//            recordList.add(recordInfo);
//        }
//        Map result = new HashMap<>();
//        result.put("toUser",toUserMap);
//        result.put("recordList",recordList);
//        HttpServletResponse response = (HttpServletResponse) res;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        return result;
//    }

//    @RequestMapping("/")
//    public Iterable<User> list(ServletResponse res){
//        List<User> userList = (List<User>) userRepository.findAll();
//        List userInfoList = new ArrayList();
//        for (int i = 0;i < userList.size();i++){
//            HashMap userInfo = new HashMap();
//            userInfo.put("id",userList.get(i).getId());
//            userInfo.put("name",userList.get(i).getName());
//            userInfo.put("avatar",userList.get(i).getAvatar());
//            userInfo.put("email",userList.get(i).getEmail());
//
//            int recordCount = recordRepository.findByToUser(userList.get(i)).size();
//            userInfo.put("count",recordCount);
//            userInfoList.add(userInfo);
//        }
//        HttpServletResponse response = (HttpServletResponse) res;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        return  userInfoList;
//    }


//    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
//    public List addUser(@RequestBody String test,ServletResponse res) throws IOException {
//        /**
//         * 请求方法
//         * */
//        ObjectMapper mapper = new ObjectMapper();
//        ArrayList userList =  mapper.readValue(test, ArrayList.class);
//
//        ArrayList savedUserList = new ArrayList();
//
//        for (int i = 0; i < userList.size();i++){
//            HashMap userMap = (HashMap) ((ArrayList)(((HashMap)userList.get(i)).get("userlist"))).get(0);
//            User user = new User();
//            user.setUserid((String) userMap.get("userid"));
//            user.setName((String) userMap.get("name"));
//            user.setDepartment(userMap.get("department").toString());
//            user.setJob((String) userMap.get("position"));
//            user.setMobile((String) userMap.get("mobile"));
//            user.setGender((String) userMap.get("gender"));
//            user.setEmail((String) userMap.get("email"));
//            user.setWeixinid((String) userMap.get("weixinid"));
//            user.setAvatar((String) userMap.get("avatar"));
//            user.setStatus((Integer) userMap.get("status"));
//            user = userRepository.save(user);
//            savedUserList.add(user);
//        }
//        HttpServletResponse response = (HttpServletResponse) res;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        return savedUserList;
//    }

    @RequestMapping(value = "users/currentuser")
    public HashMap user(@RequestParam String code, HttpServletResponse response, HttpServletRequest request){
        String userId = wechatResourceService.getCurrentUser(code);
        wechatResourceService.getMembers();
        response.addCookie(new Cookie("X_CURRENT_USER_ID",userId));
        for (int i = 0;i < wechatResourceService.USERLIST.size();i++){
           if (userId.equals(wechatResourceService.USERLIST.get(i).get("userid"))){
               response.addCookie(new Cookie("X_CURRENT_USER_ID",userId));
               return wechatResourceService.USERLIST.get(i);
           }
        }
       return null;
    }

    //获取所有user
    @RequestMapping(value = "users")
    public ArrayList userlist(HttpServletResponse response,HttpServletRequest request){
        return wechatResourceService.getMembers();

    }

    @RequestMapping(value = "users/{userId}")
    public HashMap userInfo(@PathVariable("userId") String userId){
        for (int i = 0;i < wechatResourceService.USERLIST.size();i++){
            if (userId.equals(wechatResourceService.USERLIST.get(i).get("userid"))){
                return wechatResourceService.USERLIST.get(i);
            }
        }
        return null;
    }

    @RequestMapping(value = "users/{userId}/badges")
    public List<Record> badgeListByUser(@PathVariable("userId") String userId){
        ArrayList<Record> badgelist = (ArrayList<Record>) recordRepository.findByToUser(userId);
        ArrayList result = new ArrayList();
        for (int i = 0;i < badgelist.size();i ++){
            HashMap recordMap = new HashMap();
            recordMap.put("id",badgelist.get(i).getId());
            recordMap.put("toUser",badgelist.get(i).getToUser());
            recordMap.put("fromUser",wechatResourceService.USERMAP.get(badgelist.get(i).getFromUser()));
            recordMap.put("badge",badgelist.get(i).getBadge());
            recordMap.put("comment",badgelist.get(i).getComment());
            recordMap.put("dateCreated",badgelist.get(i).getDateCreated());
            recordMap.put("lastUpdated",badgelist.get(i).getLastUpdated());
            result.add(recordMap);
        }

        return result;
    }

    @RequestMapping(value = "users/badged")
    public ArrayList userListWithBadge(){
        ArrayList ulwb = (ArrayList) recordRepository.findAll();
        ArrayList result = new ArrayList();

        HashMap<String,Object> user = new HashMap();
        HashMap<String,Integer> badgeCount = new HashMap();

        for (int i = 0;i < ulwb.size(); i ++){
            String key = ((Record)ulwb.get(i)).getToUser();
            if (badgeCount.containsKey(key)){
                badgeCount.put(key,badgeCount.get(key) + 1);
            }else {
                badgeCount.put(key,1);
            }
            if (!user.containsKey(key)){
                user.put(key,wechatResourceService.USERMAP.get(key));
            }
        }

        for (Map.Entry entity : user.entrySet()){
            HashMap temp = new HashMap();
            temp.put("userInfo",entity.getValue());
            temp.put("badgeCount",badgeCount.get(entity.getKey()));
            result.add(temp);
        }
        return result;
    }
}
