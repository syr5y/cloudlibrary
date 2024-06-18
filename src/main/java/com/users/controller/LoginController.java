package com.users.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.bean.MyFile;
import com.spring.utiles.FiledUtiles;
import com.users.bean.User;
import com.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
//    d登录
    @RequestMapping("/login")
    public String login(User user,HttpServletRequest req) {
        if(user!=null){
            User user1=userService.selectByIdAndName(user);
            System.out.println(user);
            if(user1!=null){
                req.getSession().setAttribute("USER_SESSION",user1);
                req.getSession().setAttribute("USER_IMGPATH","img/user.jpg");
                return "main";
            }
            req.setAttribute("msg","用户信息错误!");
            return "forward:admin/login.jsp";
        }else {
            req.setAttribute("msg","请输入用户信息!");
            return "forward:admin/login.jsp";
        }
    }
    //注销用户
    @RequestMapping("/logout")
    public  String logout(HttpServletRequest req) {
        HttpSession session=req.getSession();
        session.invalidate();
        return "redirect:index.jsp";
    }
//    注册
    @RequestMapping("/regist")
    public String regist(HttpServletRequest req,User user) {
        User user1=new User();
        user1.setEmail(user.getEmail());
        //判断是否注册
        if(userService.selectByIdAndName(user1)!=null){
            req.setAttribute("msg","用户已经注册");
            return "forward:admin/regist.jsp";
        }else{
            int a=userService.insertUser(user);
            if(a>0){
                return "redirect:index.jsp";
            }else {
                req.setAttribute("msg","注册失败");
                return "forward:admin/regist.jsp";
            }
        }
    }
//    修改
    @RequestMapping("/update")
    public String update(User user,HttpServletRequest req){
        User user1= (User) req.getSession().getAttribute("USER_SESSION");
        if(user!=null){
            if(userService.selectByIdAndName(user)==null){
                user.setId(user1.getId());//通过id修改
                int a=userService.updateUser(user);
                if(a>0){
                    HttpSession session=req.getSession();
                    session.invalidate();
                    return "redirect:index.jsp";
                }else {
                    req.setAttribute("msg","更新失败");
                    return "forward:admin/user.jsp";
                }
            }else {
                req.setAttribute("msg","信息未修改");
                return "forward:admin/user.jsp";
            }
        }else {
            req.setAttribute("msg","信息错误");
            return "forward:admin/user.jsp";
        }
    }
//    删除
    @RequestMapping("/delete")
    public String delete(HttpServletRequest req){
        User user= (User) req.getSession().getAttribute("USER_SESSION");
        if(user!=null){
            int a=userService.deleteUser(user.getId());
            if(a>0){
                req.setAttribute("msg","删除成功");
                HttpSession session=req.getSession();
                session.invalidate();
                return "redirect:admin/index.jsp";
            }else {
                req.setAttribute("msg","删除失败");
                return "forward:admin/user.jsp";
            }
        }else {
            req.setAttribute("msg","系统错误");
            return "forward:admin/user.jsp";
        }
    }
    //头像
    @RequestMapping(value = "/file",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String getFile(@RequestPart("file") MultipartFile file, HttpServletRequest req) throws IOException {
        String path=req.getServletContext().getRealPath("/")+"/img/user/";
        System.out.println(file.getOriginalFilename());
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<MyFile> listfile=new ArrayList<MyFile>();
        if(file!=null){
            //获取文件
            String filename=file.getOriginalFilename();
            String newpath=path+filename;//set路径
            //查看file.json是否保存过
            String checkJson=FiledUtiles.readFiled(path+"userimg.json");
            if(checkJson.length()!=0){
                //json->object
                listfile=mapper.readValue(checkJson,new TypeReference<List<MyFile>>(){});
                //重名处理（直接返回不保存）
                for(MyFile list : listfile){
                    if(filename.equals(list.getName())){
                        req.setAttribute("msg","头像选择成功");
                        req.getSession().setAttribute("USER_IMGPATH","img/user/"+filename);
                        return "user";
                    }
                }
            }
            //添加到list
            listfile.add(new MyFile(filename));
            file.transferTo(new File(newpath));//保存到目录
            //object->json
            String jsonfile=mapper.writeValueAsString(listfile);
            String jsonpath=path+"userimg.json";
            FiledUtiles.writeFiled(jsonfile,jsonpath);//写入json

            req.setAttribute("msg","头像上传成功");
            req.getSession().setAttribute("USER_IMGPATH","img/user/"+filename);
            return "user";
        }
        req.setAttribute("msg","未选择头像");
        return "forward:admin/user.jsp";
    }
}
