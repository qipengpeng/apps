package com.ugo.dao.activityDao;

import com.ugo.entity.activity.Activity;
import com.ugo.entity.activity.ActivityPT;
import com.ugo.entity.activity.ActivityProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityDao {

    /**
     * 获取点位
     * @return
     */
    List<ActivityPT> getActivityPTs();

    /**
     * 获取订单
     * @return
     */
    List<ActivityProduct> getActivityProducts();

    /**
     * 创建活动返回id
     * @param activity
     * @return
     */
    int createActivity(Activity activity);

    /**
     * 批量添加活动商品
     * @param list
     * @param id
     * @return
     */
    int addActivityProduct(@Param("list") List<ActivityProduct> list, @Param("id") int id);

    /**
     * 批量添加活动点位
     * @param list
     * @param id
     * @return
     */
    int addActivityPT(@Param("list") List<ActivityPT> list, @Param("id") int id);
}
