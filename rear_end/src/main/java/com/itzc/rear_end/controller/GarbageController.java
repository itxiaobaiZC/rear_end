package com.itzc.rear_end.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.itzc.rear_end.common.Result;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.itzc.rear_end.service.IGarbageService;
import com.itzc.rear_end.entity.Garbage;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author itzc
 * @since 2023-11-09
 */
@RestController
@RequestMapping("/garbage")
public class GarbageController {

    @Resource
    private IGarbageService garbageService;

    @PostMapping("/save")
    public Result save(@RequestBody Garbage garbage){
        return Result.success(garbageService.saveOrUpdate(garbage));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
            return Result.success(garbageService.removeById(id));
    }
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
            return Result.success(garbageService.removeByIds(ids));
    }

    @GetMapping("/findAll")
    public Result findAll(){
            return Result.success(garbageService.list());
    }


    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
            return Result.success(garbageService.getById(id));
    }

    @GetMapping("/page")
    public Page<Garbage> findPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String name,
                                  @RequestParam(defaultValue = "") String category){
        QueryWrapper<Garbage> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.like("name",name);
        objectQueryWrapper.like("category",category);
        return garbageService.page(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }
    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Garbage> list = garbageService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("picurl", "图片");
        writer.addHeaderAlias("name", "名称");
        writer.addHeaderAlias("category", "类别");
        writer.addHeaderAlias("price", "价值");
        writer.addHeaderAlias("descrition", "描述");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("垃圾分类信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);
        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Garbage> garbageList = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Garbage garbage = new Garbage();
            garbage.setPicurl(row.get(1).toString());
            garbage.setName(row.get(2).toString());
            garbage.setCategory(row.get(3).toString());
            garbage.setPrice(row.get(4).toString());
            garbage.setDescrition(row.get(5).toString());
            garbageList.add(garbage);
        }
        garbageService.saveBatch(garbageList);
        return Result.success(true);
    }




}

