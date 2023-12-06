package com.itzc.rear_end.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itzc.rear_end.common.Constants;
import com.itzc.rear_end.common.Result;
import com.itzc.rear_end.controller.dto.UserDTO;
import com.itzc.rear_end.controller.dto.UserPasswordDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;
import com.itzc.rear_end.service.ISysUserService;
import com.itzc.rear_end.entity.SysUser;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author itzc
 * @since 2023-10-27
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
    String name = userDTO.getName();
    String password = userDTO.getPassword();
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto= null;
        try {
            dto = sysUserService.login(userDTO);
            dto.setPassword("");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Result.success(dto);
    }
//    注册
    @PostMapping("/sgin")
    public Result sgin(@RequestBody UserDTO userDTO){
        String name = userDTO.getName();
        String password = userDTO.getPassword();
        //bug1
        if ((StringUtils.isBlank(name) || StringUtils.isBlank(password)) && StringUtils.isBlank(password)  ){
            return Result.error(Constants.CODE_400,"用户已存在");
        }
        return Result.success(sysUserService.sgin(userDTO));
    }

//    新增用户
    @PostMapping("/save")
    public Result save(@RequestBody SysUser sysUser){
        return Result.success(sysUserService.saveOrUpdate(sysUser));
    }
//    修改密码
    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO){
        sysUserService.updatePassword(userPasswordDTO);
        return Result.success();
    }
//    根据id删除信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
            return Result.success(sysUserService.removeById(id));
    }
//    批量删除信息
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
            return Result.success(sysUserService.removeByIds(ids));
    }
    //查找全部用户信息
    @GetMapping("/findAll")
    public Result findAll(){
            return Result.success(sysUserService.list());
    }
    //修改当前登录用户个人信息
    @GetMapping("/updateName/{name}")
    public Result updateName(@PathVariable String name){
        QueryWrapper<SysUser> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("name",name);
        return Result.success(sysUserService.getOne(objectQueryWrapper));
    }
//    根据id朝招
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
            return Result.success(sysUserService.getById(id));
    }
//    返回当前登录用户名
    @GetMapping("/findByName/{name}")
    public Result findByName(@PathVariable String name){
        return Result.success(sysUserService.findByName(name));
    }

//分页查询
    @GetMapping("/page")
    public IPage<SysUser> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String name,
                                   @RequestParam(defaultValue = "") String number,
                                   @RequestParam(defaultValue = "") String address){
        QueryWrapper<SysUser> objectQueryWrapper = new QueryWrapper<>();
        if (!"".equals(name)){
            objectQueryWrapper.like("name",name);
        }
        if (!"".equals(number)){
            objectQueryWrapper.like("number",number);
        }
        if (!"".equals(address)){
            objectQueryWrapper.like("address",address);
        }
        return sysUserService.page(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<SysUser> list = sysUserService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("name", "名字");
        writer.addHeaderAlias("number", "手机");
        writer.addHeaderAlias("address", "地址");
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
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
        List<SysUser> sysUsersList = CollUtil.newArrayList();
        for (List<Object> row : list) {
            SysUser sysUser = new SysUser();
            sysUser.setName(row.get(1).toString());
            sysUser.setNumber(row.get(2).toString());
            sysUser.setAddress(row.get(3).toString());

            sysUsersList.add(sysUser);
        }
        sysUserService.saveBatch(sysUsersList);
        return Result.success(true);
    }




}

