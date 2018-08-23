/**
 * 
 */
package com.ugo.web;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.ugo.bus.MsgBus;
import com.ugo.dto.VmHttpRequest;
import com.ugo.entity.ReportLogs;
import com.ugo.entity.Sales;
import com.ugo.entity.Vendors;
import com.ugo.service.ApkVersionService;
import com.ugo.service.SalesService;
import com.ugo.service.VendorsService;
import com.ugo.service.VmReportService;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
@RequestMapping("/vms")
@Controller
public class VmsController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(VmsController.class);
	
	@Autowired
	private VendorsService vendorsService;
	
	@Autowired
	private SalesService salesService;
	
	@Autowired
	private VmReportService vmReportService;
	
	@Autowired
	private ApkVersionService apkVersionService;
	/**
	 * 设备端匹配设备详情
	 * */
	@RequestMapping("/init")
	@ResponseBody
	public Map<String,Object> findVendorsByIdAndPwd(String vmCode,String vmPwd,String apkVer) {
		System.out.println("初始化设备信息--------start");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		System.out.println("设备id:"+vmCode+"设备密码:"+vmPwd);
		Vendors vendors = vendorsService.findVendorsByIdAndPwd(Integer.parseInt(vmCode), vmPwd);
		System.out.println("获取设备详情-----");
		if(vendors !=null) {
			map1.put("nodeId", vendors.getNodeId());
			map1.put("nodeName",vendors.getNodeName());
			map1.put("vmCode",vendors.getId());
			map1.put("vmType",vendors.getProducer()*10+vendors.getType());
			map.put("code", 0);
			map.put("msg", "匹配成功");
			map.put("data", map1);
			System.out.println("设备详情信息返回-----");
		}else {
			map.put("code", 1);
			map.put("msg", "没有对应的设备信息");
		}
		return map;
	}
	
	/**
	 * 设备故障信息记录
	 * */
	@RequestMapping("/report")
	@ResponseBody
	public Map<String,Object> addRepotLogs(ReportLogs reportLogs) {
		Map<String,Object> map = new HashMap<String,Object>();
		System.out.println("故障信息reportLogs--------"+reportLogs.toString());
		if(reportLogs.getErrNum()>0) {
			//salesService.updateSalesState(Integer.parseInt(reportLogs.getVmCode()), 0, 4);
			vmReportService.addReportLogs(reportLogs);
			System.out.println("reportLogs保存成功!");
				map.put("code", 0);
				map.put("msg", "记录成功");
		}else if(reportLogs.getErrNum()==0) {
			salesService.updateSalesState(Integer.parseInt(reportLogs.getVmCode()), 0, 1);
			System.out.println("恢复设备初始化状态----------");
		}
		return map;
	}
	
	/**
	 * 企业微信消息发送
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * */
	@RequestMapping("/sendMsgaaa")
	public void sendMsg(String msg){
		Map<String,String> map = new HashMap<>();
		String accessToken = vmReportService.getAccessToken();
		//String accessToken = "eoSecXgbNkIrGLylW8heSns0ci_dGBfwGIDtv5CUfCabgmZbsHPygSfn052agzVO0Xn_50eq8BG7LCBTPVgGuE8Pi_jPHCkRD2xTA9h20mdXmpTzy23juHi-ISzfjw8NHxbbIradSHJprIAf3cpszi4A13frFr2YJAjXxgZBJXBSXz19fraJcxMngnYmxTx3_6fM6eKsSiwfdw6zHecpIw";
		System.out.println("Old:"+accessToken);
		//当前时间
		Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format( now );
        String msg1 = msg+"\n故障报警时间:"+format;
        map.put("content", msg1);
		logger.info("发现提示-------start:"+msg);
		JSONObject sendPost = null;
		try {
			sendPost = VmHttpRequest.sendPost(accessToken, map);
			logger.info("sendPost"+sendPost);
		} catch (IOException e) {
			logger.error("预警消息发送异常!"+e);
			e.printStackTrace();
		}
		logger.info("发现提示-------end");
//		int errcode = (int) sendPost.get("errcode");
//		if(errcode != 0) {
//			JSONObject sendGet = null;
//			try {
//				sendGet = VmHttpRequest.sendGet();
//				logger.info("sendGet"+sendGet);
//			} catch (IOException e) {
//				logger.error("获取预警token异常!"+e);
//				e.printStackTrace();
//			}
//			String accessToken1 = sendGet.getString("access_token");
//			System.out.println("NEW:"+accessToken1);
//			vmReportService.updateAccessToken(accessToken1);
//			try {
//				JSONObject sendPost2 = VmHttpRequest.sendPost(accessToken1, map);
//				logger.info("sendPost2"+sendPost2);
//				int errcode1 = (int) sendPost2.get("errcode");
//				if(errcode1 != 0) {
//					logger.info("企业微信消息再次发送--失败!");
//					return;
//				}
//			} catch (IOException e) {
//				logger.error("再次预警消息发送异常!"+e);
//				e.printStackTrace();
//			}
//		}
		logger.info("企业微信消息成功发送!");
	}

    /**
     * 企业微信图片发送
     * qipeng 20180820
     * @Param file 图片文件
     * @Param vmCode 设备ID
     * */
    @RequestMapping("/sendScreen")
    @ResponseBody
    public Map<String, Object> sendScreen(MultipartFile file,String vmCode){
        logger.info("企业微信推送截屏,设备ID:"+vmCode);
        Map<String, Object> result = new HashMap<>();
        Map<String,String> map = new HashMap<>();
        String accessToken = vmReportService.getAccessToken();
        //String accessToken = "-GCagxpe9rD5zVGa9nzsHvb98bVsxCuo8XggsWmaZ5ZOrGwaCbXahikfaWLGqDwUptjiS11cBpa_nXH1WjxjwlLU40hQWylppjRnaRYk_VmRkojBcREP6vmG49GAeu12xqJIPEMajFpOkgsy8VUFoWlHC4Z2ebx8VB9hgQJDWBbJYb0TKlY54UsXCpFPzCrfvrD3yohOIicR4NjRMRN5uA";
        String msg = null;
        try {
            msg = VmHttpRequest.httpRequest(file,accessToken);
        } catch (HttpException e1) {
            logger.error("获取图片media_id失败");
            e1.printStackTrace();
            result.put("code",1);
            result.put("msg","获取media_id异常");
            return result;
        }
        if("EEROR".equals(msg)) {
            logger.info("企业微信发送失败,未获取图片media_id!");
            result.put("code",1);
            result.put("msg","ERROR未获取到media_id");
            return result;
        }
        map.put("media_id", msg);
        JSONObject sendPost = null;
        try {
            sendPost = VmHttpRequest.sendPostIMG(accessToken, map);
            logger.info("sendPost"+sendPost);
        } catch (IOException e) {
            logger.error("预警图片发送异常!"+e);
            e.printStackTrace();
            result.put("code",1);
            result.put("msg","图片发送异常");
            return result;
        }
        logger.info("发现提示-------end");
        logger.info("企业微信图片成功发送!");
        result.put("code",0);
        result.put("msg","SUCCESS");
        return result;
    }

    /**
     * @author qipeng 2018/8/17
     * 设备日志上传
     * @param : 设备Id
     * */
    @ResponseBody
    @RequestMapping("/uploadLog")
    public Map<String, Object> uploadVmLog(String vmCode,MultipartFile file){
        logger.info("设备日志上传,设备ID:"+vmCode);
        Map<String, Object> result = new HashMap<>();
        String realPath = "/ugo/apps/logs/vmlogs/"+vmCode;
        System.out.println("realPath--------"+realPath);
        String url = null;
        if(!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            File dir = new File(realPath, fileName);
            System.out.println("dir----------"+dir);
            if(!dir.exists()){  dir.mkdirs();  }
            try {
                file.transferTo(dir);
            } catch (IOException e) {
                logger.error("设备日志上传,文件transfer异常");
                e.printStackTrace();
                result.put("code",1);
                result.put("msg","文件上传失败");
                return result;
            }
        }
        result.put("code",0);
        result.put("msg","SUCCESS");
        return result;
    }

	/**
	 * 屏显商品详情
	 * */
	@RequestMapping("/productList")
	@ResponseBody
	public Map<String, Object> vmsQueryProductList(HttpServletRequest request) {
		return vmReportService.queryVmsProductDetail(request);
	}
	
	/**
	 * 刷新屏显
	 * */
	@ResponseBody
	@RequestMapping("/refreshOSD")
	public String refreshProductList(String id) {
		logger.info("设备屏列表更新,ID:"+id);
		try {
			MsgBus.getInstance().updateList(id);
		} catch (MqttException e) {
			logger.error("设备屏列表更新---接口调用失败");
			return "error";
		}
		return "success";
	}
	
	/**
	 * @author qipeng 2018/8/17
	 * 设备重启
	 * @param id 设备Id
	 * */
	@ResponseBody
	@RequestMapping("/restart")
	public  String restart(String id) {
		logger.info("指令_设备重启,ID:"+id);
		try {
			MsgBus.getInstance().reboot(id);
		} catch (MqttException e) {
			logger.error("设备重启---接口调用失败");
			return "error";
		}
		return "success";
	}
	
	/**
	 * @author qipeng 2018/8/17
	 * 设备截屏
	 * @param id 设备Id
	 * */
	@ResponseBody
	@RequestMapping("/screenShot")
	public String screenShot(String id) {
		logger.info("指令_截屏,ID:"+id);
		try {
			MsgBus.getInstance().screen(id);
		} catch (MqttException e) {
			logger.error("截屏---接口调用失败");
			return "error";
		}
		return "success";
	}

    /**
     * @author qipeng 2018/8/17
     * 日志上传
     * @param id 设备Id
     * */
    @ResponseBody
    @RequestMapping("/Getlog")
    public String Getlog(String id,String date) {
        logger.info("指令_日志上传,ID:"+id+",时间:"+date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String setDate = String.valueOf(dateFormat.parse(date).getTime()/1000);
			System.out.println("时间戳"+setDate);
			//MsgBus.getInstance().getLog(id,setDate);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("时间戳转换异常");
			return "date_error";
//        } catch (MqttException e) {
//            logger.error("日志上传---接口调用失败");
//            return "error";
        }
        return "success";
    }
	
	/**
	 * 点位详情设备状态查看
	 * */
	@RequestMapping("/preBuy")
	@ResponseBody
	public Map<String,Object> getVmsState(HttpServletRequest request) {
		Map<String,Object>map = new HashMap<>();
		String vmCode = request.getParameter("vmCode");
		int vendorState = 0;
		if(!vmCode.isEmpty()) {
			vendorState = vendorsService.getVendorsNodeState(Integer.parseInt(vmCode));
		}
		if(vendorState == 1) {
			map.put("code",0);
			map.put("msg","运营中");
		}else if (vendorState == 2) {
			map.put("code",1);	
			map.put("msg","停止售卖");
		}else {
			map.put("code",2);
			map.put("msg","");
		}
		return map;
	}
	
	/**
	 * 版本更新检测
	 * */
	@RequestMapping("/upgrade")
	@ResponseBody
	public Map<String,Object> updateVersion(String vmCode) {
		if(vmCode.isEmpty()) {
			return null;
		}
		Map<String, Object> apkVersion = vmReportService.getApkVersion(Integer.parseInt(vmCode));
		Map<String,Object> map = new HashMap<>();
		if(apkVersion == null) {
			map.put("code", 0);
			map.put("msg", "未获取到更新数据!");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "成功");
		map.put("data", apkVersion);
		return map;
	}
	
	/**
	 * 同步程序版本
	 * */
	@RequestMapping("/setVersion")
	@ResponseBody
	public Map<String,Object> setVersion(String vmCode,int apkVer) {
		Map<String,Object> map = new HashMap<>();
		try {
			apkVersionService.setVersion(vmCode, apkVer);
			map.put("code", 0);
			map.put("msg", "同步完成");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "同步失败");
			return map;
		}
		return map;
	}

	/**
	 * 库存状态
	 * */
	@RequestMapping("/setStockState")
	@ResponseBody
	public Map<String,Object> setStockState(String vmCode,String state) {
		logger.info("库存预警:"+vmCode+"--"+state);
		Map<String,Object> map = new HashMap<>();
		if(vmCode.isEmpty() || state.isEmpty()) {
			map.put("code", 1);
			map.put("msg", "参数不能为空");
			return map;
		}
		int vendorId = Integer.parseInt(vmCode);
		String[] arr = state.split(",");
		List<Sales> salesList = salesService.existingNum(vendorId);
		String msg = "该货道未检测到商品,库存分别剩余";
		String channelId = "";
		String ExistingNum = "";
		if(salesList.size() == 4) {
			if("0".equals(arr[0]) && salesList.get(0).getExistingNum() != 0) {
				System.out.println("警报1");
				channelId = channelId+salesList.get(0).getChannelId()+"/";
				ExistingNum = ExistingNum + salesList.get(0).getExistingNum()+"/";
			}
			if("0".equals(arr[1]) && salesList.get(1).getExistingNum() != 0) {
				System.out.println("警报2");
				channelId = channelId+salesList.get(1).getChannelId()+"/";
				ExistingNum = ExistingNum + salesList.get(1).getExistingNum()+"/";
			}
			if("0".equals(arr[3]) && salesList.get(2).getExistingNum() != 0) {
				System.out.println("警报3");
				channelId = channelId+salesList.get(2).getChannelId()+"/";
				ExistingNum = ExistingNum + salesList.get(2).getExistingNum()+"/";
			}
			if("0".equals(arr[4]) && salesList.get(3).getExistingNum() != 0) {
				System.out.println("警报4");
				channelId = channelId+salesList.get(3).getChannelId()+"/";
				ExistingNum = ExistingNum + salesList.get(3).getExistingNum()+"/";
			}
			String setWarning = setWarning(vendorId, channelId, salesList.get(0).getNodeId(), salesList.get(0).getNodeName(), msg+ExistingNum);
			sendMsg(setWarning);
		}
		map.put("code", 0);
		map.put("msg", "成功");
		return map;
	}
	
	/**
	 * @author qipeng 2018/8/16
	 * 有饭预警信息格式
	 * @param obj1  设备ID
	 * @param obj2 货道编号
	 * @param obj3 点位ID
	 * @param obj4 点位名称
	 * @param obj5 预警内容
	 * */
	public static String setWarning(Object obj1,Object obj2,Object obj3,Object obj4,Object obj5) {
		String msg = "设备ID:"+obj1+"\n货道编号:"+obj2+"\n点位ID:"+obj3+"\n点位名称:"+obj4+"\n预警内容:"+obj5;
		return msg;
	}
}
