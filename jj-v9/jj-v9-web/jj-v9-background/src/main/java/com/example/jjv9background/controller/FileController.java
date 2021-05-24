package com.example.jjv9background.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>Description: 其实真正应该是一个独立服务，临时寄存于此</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Controller
@RequestMapping("file")
public class FileController {

    @RequestMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file){
        System.out.println(file+"11111111111");
        //1.获取到文件对象，将文件对象上传到fastDFS上
        //2.把服务器的文件保存地址返回给客户端
        //3.客户端将这个地址回显到浏览器上
        return "ok";
    }
}
