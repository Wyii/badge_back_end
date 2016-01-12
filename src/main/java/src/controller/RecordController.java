package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.Domain.Badge;
import src.Domain.Record;
import src.Domain.User;
import src.service.BadgeRepository;
import src.service.RecordRepository;
import src.service.UserRepository;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
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
    public Record add(Record record,ServletResponse res){
        record = recordRepository.save(record);
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        return record;
    }
}
