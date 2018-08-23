package com.ugo.dao.couponDao;

import com.ugo.entity.CouponEntity.AccessToken;
import org.apache.ibatis.annotations.Param;

/**
 * token更新
 */
public interface TokenDao {

    void updateToken(@Param("token") AccessToken token);

}
