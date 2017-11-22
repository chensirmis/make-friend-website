/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.console.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.androidpn.server.model.Information;
import org.androidpn.server.service.InformationService;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.util.Config;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/** 
 * A controller class to process the notification related requests.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 * 
 * 
 */
public class NotificationController extends MultiActionController {

    private NotificationManager notificationManager;
    
    private InformationService  informationService;
    
    private  HttpSession  session;
    
    private ServletContext application;
    
    private Information information;

    public NotificationController() {
    	
    	
        notificationManager = new NotificationManager();
        informationService=ServiceLocator.getInformationService() ;
    }

    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        // mav.addObject("list", null);
        mav.setViewName("notification/form");
        return mav;
    }
    
    //接收App发送的文件
    public String  upLoad(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        	
    	information= new Information();
    	session=request.getSession();
    	application=session.getServletContext();
        String broadcast = null;
        String uri = null;
       PrintWriter out=response.getWriter();
       out.write("注册成功");
       out.close();
       
        DiskFileItemFactory  factory=new DiskFileItemFactory();
        factory.setSizeThreshold(102400);
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        
        try {
			List<FileItem> filelist = servletFileUpload.parseRequest(request);	
			for (FileItem fileItem : filelist) {
				if( fileItem.isFormField() ){//如果为真就是表单中的文本内容
					if(fileItem.getFieldName().equals("username")){
						broadcast = new String( fileItem.getString().getBytes("iso-8859-1"),"utf-8");
						}
				}else{
					if( fileItem.getSize() > 0 ){//文件大于零才上传该文件
						
						String fileName = fileItem.getName();//得到文件的名字
						
						File f = new File(application.getRealPath("/")+"homework/");
						f.mkdirs();
						f=new File(f,fileName);
						
						fileItem.write(f);//文件上传
						//path=request.getRequestURI();
						
						File desk = new File("C:/Users/Administrator/Desktop/homework/");
							desk.mkdirs();
						desk=new File(desk,fileName );
						fileItem.write(desk);				
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
    	return "forward:index.do"; 	
    }
    
  //向App发送文件
    public ModelAndView send(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	information= new Information();
    	session=request.getSession();
    	application=session.getServletContext();
        String broadcast = null;
        String username = null;
        String title = null;
        String messagejsp =null;
        String uri = "";
        String path=null;
        String apiKey = Config.getString("apiKey", "");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        DiskFileItemFactory  factory=new DiskFileItemFactory();
        factory.setSizeThreshold(102400);
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        
        try {
			List<FileItem> filelist = servletFileUpload.parseRequest(request);	
			for (FileItem fileItem : filelist) {
				if( fileItem.isFormField() ){//如果为真就是表单中的文本内容
					if(fileItem.getFieldName().equals("broadcast")){
						broadcast = new String( fileItem.getString().getBytes("iso-8859-1"),"utf-8");
					}else if(fileItem.getFieldName().equals("username")){
						username = new String( fileItem.getString().getBytes("iso-8859-1"),"utf-8");
					}else if(fileItem.getFieldName().equals("title")){
						title = new String( fileItem.getString().getBytes("iso-8859-1"),"utf-8");
					}else if(fileItem.getFieldName().equals("message")){
						messagejsp = new String( fileItem.getString().getBytes("iso-8859-1"),"utf-8");
					}
				}else{//就是上传的图片
					if( fileItem.getSize() > 0 ){//文件大于零才上传该文件
						
						String fileName = fileItem.getName();//得到文件的名字
						
						File f = new File(application.getRealPath("/")+"homework/");
						f.mkdirs();
						f=new File(f,fileName );
						
						fileItem.write(f);//文件上传
						//path=request.getRequestURI();
						String serviceName=request.getServerName();
						int servicePort=request.getLocalPort();
						uri="http://"+ serviceName+":"+servicePort+"/"+"homework/"+fileName;
						System.out.println(uri);
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		information.setApiKey(apiKey);
		information.setMessage(messagejsp);
		information.setTitle(title);
		information.setUri(uri);
		information.setUsername(username);
        logger.debug("apiKey=" + apiKey);
        if (broadcast.equalsIgnoreCase("Y")) {
        	
        	informationService.save(information);
            notificationManager.sendBroadcast(apiKey, title, messagejsp, uri);
            
        } else {
        	informationService.save(information);
            notificationManager.sendNotifcationToUser(apiKey, username, title,
            		messagejsp, uri);
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("notification/form");
        return mav;
    }
    
    
   

}
