package com.itzc.rear_end.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itzc.rear_end.common.Result;
import com.itzc.rear_end.entity.Files;
import com.itzc.rear_end.mapper.FileMapper;
import com.itzc.rear_end.service.IGarbageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

//文件上传接口
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;
    @Resource
    private IGarbageService iGarbageService;


    @PostMapping("/upload")
    //前端请求数据
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //原始名称
        String type = FileUtil.extName(originalFilename);  //获取类型
        long size = file.getSize();
        //存储到磁盘

        //定义文件唯一标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid=uuid + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUuid);
        //判断配置文件目录是否存在
        File parentFile=uploadFile.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        //当文件存在的时候在获取文件的md5
        String md5;
        String url;
        //获取文件的MD5
        file.transferTo(uploadFile);

        md5= SecureUtil.md5(uploadFile);
        //查询存在是否相同内容
        Files dbFiles = getFileByMd5(md5);
        //根据 从数据库查询 查询md5
        if (dbFiles!=null){
            url=dbFiles.getUrl();
            uploadFile.delete();
        }else {
            //获取到的文件存储到磁盘目录
            url="http://localhost:9090/file/" + fileUuid;
        }

        //存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url;
    }
    //文件下载接口 https://localhost:9090/file/{fileUuid}
    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        //根据文件唯一标识码获得文件
        File uploadFile = new File(fileUploadPath + fileUuid);
        //设置输出流格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUuid,"UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件的字节流
        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();
    }
    //md5 查询文件
    private Files getFileByMd5(String md5){
        QueryWrapper<Files> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("md5",md5);
        List<Files> filesList = fileMapper.selectList(objectQueryWrapper);
        return filesList.size()== 0 ? null : filesList.get(0);
    }



    @PostMapping("/updateById")
    public Result updateById(@RequestBody Files files){
        return Result.success(fileMapper.updateById(files));
    }



    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        fileMapper.updateById(files);
        return Result.success();
    }
    //批量删除
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
        QueryWrapper<Files> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.in("id",ids);
        List<Files> files = fileMapper.selectList(objectQueryWrapper);
        for (Files file : files){
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }


    //分页查询
    @GetMapping("/page")
    public IPage<Files> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String name){
        QueryWrapper<Files> objectQueryWrapper = new QueryWrapper<>();

        //查询未删除的记录
        objectQueryWrapper.eq("is_delete",false);
        if (!"".equals(name)){
            objectQueryWrapper.like("name",name);
        }

        //返回当前登录用户信息
//        SysUser currentUser= TokenUtils.getCurrentsysUser();
//        System.out.println("======="+currentUser.getName());

        return fileMapper.selectPage(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }
}
