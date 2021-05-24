package com.jijie.fastdfs;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
class FastdfsApplicationTests {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Test
    void contextLoads() throws FileNotFoundException {
        File file = new File("C://Users//connie//Desktop//test.png");
        String fileName = file.getName();
        String extName = fileName.substring(fileName.lastIndexOf(".")+1);
        FileInputStream inputStream = new FileInputStream("C://Users//connie//Desktop//test.png");

        //uploadFile方法参数说明：1.文件输入流。2.文件大小。3.文件后缀名
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(), extName, null);
        //
        System.out.println(storePath.getGroup());
        System.out.println(storePath.getPath());
        System.out.println(storePath.getFullPath());
    }

}
