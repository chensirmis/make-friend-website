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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.model.User;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.UserService;
import org.androidpn.server.xmpp.presence.PresenceManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/** 
 * A controller class to process the user related requests.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class UserController extends MultiActionController {

    private UserService userService;
    private User  user;

    public UserController() {
        userService = ServiceLocator.getUserService();
    }

    
    
    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        PresenceManager presenceManager = new PresenceManager();
        List<User> userList = userService.getUsers();
        for (User user : userList) {
            if (presenceManager.isAvailable(user)) {
                // Presence presence = presenceManager.getPresence(user);
                user.setOnline(true);
            } else {
                user.setOnline(false);
            }
            // logger.debug("user.online=" + user.isOnline());
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("userList", userList);
        mav.setViewName("user/list");
        return mav;
    }
    
    
    
    public void  save(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	 user= new User(); 
    	String username=request.getParameter("username");
        String password=request.getParameter("password");
    	String university=request.getParameter("university");
    	String department= request.getParameter("department");
    	String studentname= request.getParameter("studentname");
    	String studentid=request.getParameter("studentid");
    	String studentemail=request.getParameter("studentemail");
    	String studentclass=request.getParameter("studentclass");
    	String studentpassword=request.getParameter("studentpassword");
    	user.setUsername(username);
    	user.setPassword(password);
    	user.setUniversity(university);
    	user.setDepartment(department);
    	user.setStudentname(studentname);
    	user.setStudentid(studentid);
    	user.setEmail(studentemail);
    	user.setStudentclass(studentclass);
    	user.setStudentPassword(studentpassword);
    	userService.saveUser(user);	
    	list(request,response);
    }
    
    
    public void  check(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
    	
    	user= new User(); 
    	
    	String studentname= request.getParameter("studentname");
    	String studentid=request.getParameter("studentid");
    	String studentpassword=request.getParameter("studentpassword");
    	
    	user.setStudentname(studentname);
    	user.setStudentid(studentid);
    	user.setStudentPassword(studentpassword);
    	
    	PrintWriter out= response.getWriter();
    	List<User> list= new ArrayList();
    	list=userService.checkUser(user);
    	if(list.size()>0){
    		user=list.get(0);
    		out.print("用户合法-"+user.getUsername()+"-"+user.getPassword());
    	}else{
    		out.print("用户不合法-");
    	}
    	
    	out.flush();
    	out.close();
    	
    }
    
    

}
