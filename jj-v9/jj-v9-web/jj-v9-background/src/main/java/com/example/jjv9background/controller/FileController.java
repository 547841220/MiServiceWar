package com.example.jjv9background.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.jijie.v9.common.pojo.MultiUploadBean;
import com.jijie.v9.common.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>Description: 其实真正应该是一个独立服务，临时寄存于此</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Controller
@RequestMapping("file")
public class FileController {

    @Value("${image.server}")
    private String image_server;

    //fastDFS文件服务器Client
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping("upload")
    @ResponseBody
    public ResultBean upload(MultipartFile file){
        System.out.println(file+"11111111111");
        //1.获取到文件对象，将文件对象上传到fastDFS上
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

        try {
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extName, null);
            //2.把服务器的文件保存地址返回给客户端
            String fullPath = storePath.getFullPath();
            String path = new StringBuffer(image_server).append(fullPath).toString();
            return new ResultBean("200",path);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultBean("500","您的网络当前不畅通，请稍后再试！");
        }
    }

    @RequestMapping("multiUpload")
    @ResponseBody
    public ResultBean multiUpload(MultipartFile[] files){

        String[] data = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i]+"11111111111");
            //1.获取到文件对象，将文件对象上传到fastDFS上
            String originalFilename = files[i].getOriginalFilename();
            System.out.println(originalFilename);
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

            try {
                StorePath storePath = fastFileStorageClient.uploadFile(files[i].getInputStream(), files[i].getSize(), extName, null);
                //2.把服务器的文件保存地址返回给客户端
                String fullPath = storePath.getFullPath();
                String path = new StringBuffer(image_server).append(fullPath).toString();
                data[i] = path;

            } catch (IOException e) {
                e.printStackTrace();
                return new ResultBean("500","上传失败");
            }
        }

        return new ResultBean("200",data);

    }
}
