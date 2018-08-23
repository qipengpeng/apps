package com.ugo.service;

import com.alibaba.fastjson.JSON;
import com.ugo.dao.couponDao.TokenDao;
import com.ugo.entity.CouponEntity.AccessToken;
import com.ugo.tools.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CouponService {


    private static final String  URL = "https://api.weixin.qq.com/cgi-bin/token";
    private static final String APPID = "wx99bd0a3519f0876a";
    private static final String SECRET = "3af1887b36d069ea1c745cc565426f72";
    private static final String GRANT_TYPE = "client_credential";
    private Logger logger = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private TokenDao tokenDao;

//    @Scheduled(cron=" 0 0 */1 * * ?")
    public void updateToken(){
        /*获取token*/
        Map<String,String> map = new HashMap<>();
        map.put("grant_type",GRANT_TYPE);
        map.put("appid",APPID);
        map.put("secret",SECRET);
        HttpClient hc = new HttpClient();
        String rueslt= null;
        try {
            rueslt =  hc.get(URL,map); //获取token
            logger.info("token的值："+rueslt);
        } catch (IOException e) {
            logger.error("微信Token获取失败");
            e.printStackTrace();
        }
        Map ruesltMap = (Map) JSON.parseObject(rueslt);

        /*报错*/
        if (rueslt.indexOf("access_token")==-1){
            logger.error("微信Token获取失败");
            for (Object obj: ruesltMap.keySet()
                    ) {
                logger.error("错误标题:"+obj+",错误体："+ruesltMap.get(obj));
            }
            return;
        }

        /*更新token*/
        AccessToken token = new AccessToken();
        token.setId(1);
        token.setToken((String) ruesltMap.get("access_token"));
        token.setExpires((Integer) ruesltMap.get("expires_in"));

        logger.info("map里的值"+ruesltMap.toString());
        logger.info("token里的值"+token.toString());
        tokenDao.updateToken(token);
        logger.info("token更新完成！");
        /*if (tokenDao.updateToken(token)){
            logger.info("token更新完成！");
        }*/

    }


}
