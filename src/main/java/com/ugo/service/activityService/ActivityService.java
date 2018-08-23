package com.ugo.service.activityService;

import com.ugo.dao.activityDao.ActivityDao;
import com.ugo.entity.activity.ActivityPT;
import com.ugo.entity.activity.ActivityProduct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityDao activityDao;

    private Logger log = LoggerFactory.getLogger(ActivityService.class);

    /**
     * 获取商品信息
     * @return
     */
    public List<ActivityProduct> getProducts(){
        List<ActivityProduct> list =  activityDao.getActivityProducts();
        log.debug(list.toString());
        return list;
    }

    /**
     * 获取点位信息
     * @return
     */
    public List<ActivityPT> getPT(){
        List<ActivityPT> list = activityDao.getActivityPTs();
        log.debug(list.toString());
        return list;
    }

    /**
     * 创建折扣
     */
    public void createDiscount(){

    }

}
