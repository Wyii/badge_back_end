package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import src.Domain.Badge;
import src.service.BadgeRepository;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wyiss on 15/12/25.
 */
@RestController
public class BadgeController {

    @Autowired
    BadgeRepository badgeRepository;

    @RequestMapping(value = "/badge/all")
    public List<Badge> all(ServletResponse res){
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods","POST");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
        return (List<Badge>) badgeRepository.findAll();
    }
}
