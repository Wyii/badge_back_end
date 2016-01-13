package src.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.Domain.Badge;
import src.Domain.Record;
import src.Domain.User;
import src.service.BadgeRepository;
import src.service.RecordRepository;
import src.service.UserRepository;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by wyiss on 15/12/24.
 */
@RestController
@RequestMapping(value = "/records")
public class RecordController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    BadgeRepository badgeRepository;


    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Record add(@RequestBody String recordStr,ServletResponse res,HttpServletRequest request){
            ObjectMapper mapper = new ObjectMapper();
            Record record = new Record();
        try {
            HashMap<String,String> recordMap =  mapper.readValue(recordStr, HashMap.class);
            record.setToUser(recordMap.get("toUser"));
            record.setFromUser(recordMap.get("fromUser"));
            record.setComment(recordMap.get("comment"));
            long recordId = Long.parseLong(recordMap.get("badge"));
            record.setBadge(badgeRepository.findById(recordId));
            record = recordRepository.save(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        record = recordRepository.save();
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        return record;
    }
}
