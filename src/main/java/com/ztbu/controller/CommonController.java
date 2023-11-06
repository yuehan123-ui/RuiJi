package com.ztbu.controller;

import com.ztbu.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${RuiJi.path}")//在yml配置文件中获取值
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info(file.toString());
        /**
         * file是一个临时文件 需要转存到指定位置 否则本次请求完成后会删除
         */
        String originalFilename = file.getOriginalFilename();//获取原来文件名

        //suffix  后缀
        //filename.lastIndexOf(".") 获取最后一个 "." 到末尾的字符串
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//获取后缀名
        //使用UUID重新生成随机文件名 防止文件名重复覆盖
        String fileName = UUID.randomUUID() + suffix;

        //判断目录是否存在 如果不存在则新建目录
        File dir = new File(basePath);
        if (!dir.exists()){
            dir.mkdir();
        }
        file.transferTo(new File(basePath+fileName));//将文件存储到指定位置
        return Result.success(fileName);
    }

    /**
     * 下载文件 图片回显
     * @param name
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        // 读取指定路径下的文件并创建文件输入流读取文件内容
        FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));

        // 获取HTTP响应输出流 将文件写回浏览器 在浏览器展示图片
        ServletOutputStream outputStream = response.getOutputStream();

        // 设置响应内容类型为image/jpeg
        response.setContentType("image/jpeg");

        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = fileInputStream.read(bytes)) != -1) {
            // 将读取的字节写入输出流
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }

        // 关闭文件输入流和输出流
        fileInputStream.close();
        outputStream.close();
    }

}
