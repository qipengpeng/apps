/**
 * 
 */
package com.ugo.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.ugo.entity.Products;
import com.ugo.service.ProductsService;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 * 获取商品信息控制类
 */
@Controller
@RequestMapping("/PC")
public class ProductsController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ProductsController.class);
	@Autowired
	private ProductsService productsService;
	
	/**
	 * @author sunshangfeng
	 *获取商品信息
	 * @throws ServletException 
	 */
	@RequestMapping(value = "/getProductsList",method = RequestMethod.GET)
	public String getProducts(Model model){
		List<Products> productList = productsService.getList();
		model.addAttribute("productList", productList);
		return "/products";
	}
	
	/**
	 * @author sunshangfeng
	 *新增商品信息+图片上传
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @throws ServletException 
	 */
	@RequestMapping("addProduct")
	public ModelAndView addProduct(Products products,MultipartFile file,MultipartFile file1,MultipartFile file2) throws IllegalStateException, IOException{
		ModelAndView model = new ModelAndView("redirect:/PC/getProductsList");
		String realPath1 = "/images/";
		
		String realPath = "/ugo/apps/images/";
		System.out.println("realPath--------"+realPath);
		String url = null;
		if(!file.isEmpty()) {
		   String fileName = UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		   File dir = new File(realPath, fileName);
		   System.out.println("dir----------"+dir);
		   if(!dir.exists()){  dir.mkdirs();  }
		   file.transferTo(dir); 
		   url = realPath1+fileName;
		}
		String url1 = null;
		if(!file1.isEmpty()) {
		String fileName1 = UUID.randomUUID().toString().replaceAll("-","")+file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf(".")); 
		   File dir1 = new File(realPath, fileName1);
		   if(!dir1.exists()){  dir1.mkdirs();  }
		   file1.transferTo(dir1); 
		   url1 = realPath1+fileName1;
		}
		String url2 = null;
		if(!file2.isEmpty()) {
		String fileName2 = UUID.randomUUID().toString().replaceAll("-","")+file2.getOriginalFilename().substring(file2.getOriginalFilename().lastIndexOf(".")); 
		 File dir2 = new File(realPath, fileName2);
	     if(!dir2.exists()){  dir2.mkdirs();  }
	     file2.transferTo(dir2); 
	     url2 = realPath1+fileName2;
		}
         products.setIngredientImg(url);
         products.setListImg(url1);
         products.setDetailsImg(url2);
         logger.debug("新增商品---"+products);
		 productsService.add(products);
		return model;
	}
	
	/**
	 * 单个上传图片
	 */
     public String fileUpload(MultipartFile file,HttpServletRequest request) throws IOException {
	    
    	 //图片上传成功后，将图片的地址写到数据库
	    String filePath = request.getServletContext().getRealPath("/images/");//保存图片的路径
	    //获取原始图片的拓展名
	    String originalFilename = file.getOriginalFilename();
	    //新的文件名字
	    String newFileName = UUID.randomUUID()+originalFilename;
	    //封装上传文件位置的全路径
	    File targetFile = new File(filePath,newFileName); 
	    //把本地文件上传到封装上传文件位置的全路径
	    file.transferTo(targetFile);
	    return ""; 
    }
	 
	/**
	 *获取商品信息
	 */
	@RequestMapping(value="/getProduct",method = RequestMethod.GET)
	public ModelAndView getProduct(int id) {
		Products product = productsService.getProduct(id);
		ModelAndView model = new ModelAndView("productsUpdate");
		model.addObject("product", product);
		return model;
	}
	
	/**
	 *修改商品信息
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("updateProduct")
	public String updateProduct(Products product,MultipartFile file,MultipartFile file1,MultipartFile file2) throws IllegalStateException, IOException{
		String realPath1 = "/images/";
		String realPath = "/ugo/apps/images/";
		String url = null;
		String url1 = null;
		String url2 = null;
		if(!file.isEmpty()) {
		   String fileName = UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		   File dir = new File(realPath, fileName);
		   System.out.println("dir----------"+dir);
		   if(!dir.exists()){  dir.mkdirs();  }
		   file.transferTo(dir); 
		   url = realPath1+fileName;
		}
		if(!file1.isEmpty()) {
		String fileName1 = UUID.randomUUID().toString().replaceAll("-","")+file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf(".")); 
		   File dir1 = new File(realPath, fileName1);
		   if(!dir1.exists()){  dir1.mkdirs();  }
		   file1.transferTo(dir1); 
		   url1 = realPath1+fileName1;
		}
		if(!file2.isEmpty()) {
		String fileName2 = UUID.randomUUID().toString().replaceAll("-","")+file2.getOriginalFilename().substring(file2.getOriginalFilename().lastIndexOf(".")); 
		 File dir2 = new File(realPath, fileName2);
	     if(!dir2.exists()){  dir2.mkdirs();  }
	     file2.transferTo(dir2); 
	     url2 = realPath1+fileName2;
		}
		product.setIngredientImg(url);
		product.setListImg(url1);
		product.setDetailsImg(url2);
		System.out.println("修改的数据"+product);
		productsService.updateProduct(product);
		return "redirect:/PC/getProductsList";
	}
	
	
	
	//ajax查询数据
    @ResponseBody
    @RequestMapping("/findbysid")
    public Products findBySid(@RequestParam("id") int id) {
        return productsService.getProduct(id);
    }
    
  //ajax查询数据
    @ResponseBody
    @RequestMapping("/findJson")
    public List<Products>  findJson() {
         List<Products> list = productsService.getList();
         return list;
    }
}
