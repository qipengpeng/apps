package com.ugo.web;


import com.ugo.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @RequestMapping("/updateToken")
    @ResponseBody
    public String updateToken(){
        couponService.updateToken();
        return "成功";
    }
}
