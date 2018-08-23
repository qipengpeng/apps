package com.ugo.web.activityController;

import com.alibaba.fastjson.JSONObject;
import com.ugo.entity.activity.dto.DiscountActivityDTO;
import com.ugo.service.activityService.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/PC")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/activityList")
    public String actvityList(){
        return "activityList";
    }

    @GetMapping("/addDiscount")
    public String addDiscount(Model model){
        model.addAttribute("PTList",JSONObject.toJSON(activityService.getPT()));
        model.addAttribute("productList",JSONObject.toJSON(activityService.getProducts()));
        return "addDiscount";
    }


    @PostMapping("/createDiscount")
    @ResponseBody
    public String createDiscount(DiscountActivityDTO dad){
        System.out.println(dad);
        return dad.toString();
    }


    @GetMapping("/getPt")
    @ResponseBody
    public String getPT(){

        return activityService.getPT().toString();
    }

    @GetMapping("/getProduct1")
    @ResponseBody
    public String getProduct(){

        return activityService.getProducts().toString();
    }
}
