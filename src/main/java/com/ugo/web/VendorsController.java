/**
 * 
 */
package com.ugo.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ugo.dao.VendorsDao;
import com.ugo.dto.MD5Util;
import com.ugo.entity.ApkVersion;
import com.ugo.entity.Vendors;
import com.ugo.service.ApkVersionService;
import com.ugo.service.VendorsService;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *设备
 */
@RequestMapping("/PC")
@Controller
public class VendorsController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(VendorsController.class);
	@Autowired
	private VendorsService vendorsService;
	
	@Autowired
	private VendorsDao vendorsDao;
	
	@Autowired
	private ApkVersionService apkVersionService;
	
	/**
	 * 获取所有设备信息
	 * */
	@RequestMapping("/getVendorsList")
	public String getListVendors(Model model) {
		List<Vendors> vendorsList = vendorsService.getList();
		model.addAttribute("vendorsList",vendorsList);
		return "/vendors";
	}
	
	/**
	 * 获取指定设备信息
	 * */
	@RequestMapping("/getVendors")
	public ModelAndView getVendors(int id) {
		Vendors vendors = vendorsService.getVendors(id);
		ModelAndView model = new ModelAndView("vendorsDetails");
		model.addObject("vendors",vendors);
		return model;
	}
	
	/**
	 * 添加设备
	 * */
	@RequestMapping("/addVendors")
	public String addVendors(String nameplate, int type, int producer) {
		Vendors vendors = new Vendors();
		vendors.setType(type);
		vendors.setProducer(producer);
		String replace = nameplate.replace("，", ",");
		String[] splitNameplate = replace.split(",");
		for(int i=0;i<splitNameplate.length;i++) {
			vendors.setNameplate(splitNameplate[i]);
			vendors.setPassword(this.getStringRandom());
			System.out.println(vendors.toString());
			logger.debug("设备添加------");
			vendorsService.addVendors(vendors);
		}
		return "redirect:/PC/getVendorsList";
	} 
	
	//随机生成设备密码
	public String getStringRandom() {  

        String val = "";  
        Random random = new Random();        
        //length为几位密码 
        for(int i = 0; i < 10; i++) {          
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }  
	
	//ajax查询数据
	@ResponseBody
    @RequestMapping("/findVendorList")
    public List<Vendors>  findVendors() {
         List<Vendors> list = vendorsDao.getNotSetList();
         return list;
    }
	
	/**
	 * 修改点位设备状态
	 * */
	@RequestMapping("/updateNodeVmsState")
	public void updateState(int id,int state) {
		vendorsService.updateNodeVmsState(id, state);
	}
	
	/**
	 * 上传apk更新包
	 * */
	@RequestMapping("/apkUpload")
	public void apkUpload(MultipartFile apkFile,String packageName,int apkVer,String descArea,HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {
		String realPath1 = "/apks/";
		String realPath = "/ugo/apps/apks/";
		String filename = apkFile.getOriginalFilename();
		String md5Encode = null;
		String url = null;
		if(!apkFile.isEmpty()) {
			File dir = new File(realPath, filename);
		    if(!dir.exists()){  dir.mkdirs();  }
		    apkFile.transferTo(dir); 
		    url = realPath1+filename;
		    md5Encode = MD5Util.md5sum(realPath+filename);
		}
		System.out.println("url"+realPath+filename+"--md5--"+md5Encode);
		ApkVersion apkVersion = new ApkVersion();
		apkVersion.setVersionCode(apkVer);
		apkVersion.setPackageName(packageName);
		apkVersion.setMd5(md5Encode);
		apkVersion.setDescArea(descArea);
		apkVersion.setDownUrl(url);
		boolean addApk = apkVersionService.addApk(apkVersion);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		if(addApk == true) {
			request.setAttribute("msg", "上传成功");
		}else {
			request.setAttribute("msg", "上传失败");		
		}
		try {
			request.getRequestDispatcher("/vendorsUpgrade.jsp").forward(request, response);
		} catch (ServletException e) {
			logger.error("返回页面消息异常");
		}
	}
	
	/**
	 * 获取版本号列表
	 * */
	@RequestMapping("getVersionCode")
	@ResponseBody
	public List<Integer> getVersionCode() {
		List<Integer> versionCode = apkVersionService.findVersionCode();
		return versionCode;
	}
	
	@RequestMapping("/updateVersion")
	public String updateVersion(String vmCode,int version,String updateTime,HttpServletRequest request) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String versionTime = String.valueOf(sdf.parse(updateTime+":00").getTime()/1000); 
			if("all".equals(vmCode)) {
				apkVersionService.updateALLVersion(version,versionTime);
				logger.info("所有设备版本更新------");
			}else if(!vmCode.isEmpty()){
				String replace = vmCode.replace("，", ",");
				String[] vmCodeList = replace.split(",");
				for(int i=0;i<vmCodeList.length;i++) {
					apkVersionService.updateVersion(vmCodeList[i], version,versionTime);
					logger.info("指定设备版本更新------");
				}
			}
			request.setAttribute("msg1", "版本更新成功!");
		} catch (Exception e) {
			request.setAttribute("msg1", "版本更新失败!");
			return "forward:/vendorsUpgrade.jsp";
		}
		return "forward:/vendorsUpgrade.jsp";
	}
}
