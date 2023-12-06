<template>
    <div class="wrapper">
        <el-card class="box-logincard">
            <div style=" margin: 10px auto; width: 350px; height: 600px; padding: 20px; border-radius: 30%;">
                <div style="margin: 20px 0; text-align: center; font-size: 24px;"><b
                        style="color: rgb(68, 68, 68);">用户登录</b></div>
                <el-form :model="user" :rules="rules" ref="userForm">
                    <el-form-item prop="name">
                        <el-input v-model="user.name" size="medium" style="margin: 10px 0;"
                            prefix-icon='el-icon-user'></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input v-model="user.password" size="medium" style="margin: 10px 0;" prefix-icon='el-icon-lock'
                            show-password></el-input>
                    </el-form-item>
                    <el-form-item style="margin: 10px 0; text-align: right;">
                        <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
                        <el-button type="warning" size="small" autocomplete="off"
                            @click="$router.push('/sgin')">注册</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>

    </div>
</template>

<script>
import { setRouter } from "@/router"
export default {
    name: "Login",
    data() {
        return {
            user: {},
            rules: {
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 3, max: 20, message: "长度在6到20个字符", trigger: 'blur' }
                ],
                name:[
                    {required: true, message:'请输入用户名', trigger: 'blur'},
                    {min:3,max:20,message:"长度在6到20个字符",trigger: 'blur'}
                ],

            }


        }
    },
    methods: {
        login() {
            this.$refs['userForm'].validate((valid) => {
                if (valid) { //表单校验合法
                    this.request.post("/sys-user/login", this.user).then(res => {
                        if (res.code === '200') {
                            localStorage.setItem("user", JSON.stringify(res.data))  //存储用户数据到浏览器

                            localStorage.setItem("menus", JSON.stringify(res.data.menus))

                            //动态设置当前用户的路由
                            setRouter()
                            this.$message.success("登录成功")
                            this.request.post("/daily-time/save",this.user).then(res=>{
                                console.log(res)
                            })
                            if (res.data.role === 'ROLE_USER') {
                                this.$router.push("/front/fhome")
                            } else {
                                this.$router.push("/home")
                            }
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }
            });
        }
    }
}
</script>

<style>
.wrapper {
    height: 100vh;
    overflow: hidden;
    background: url("../img/login.jpeg") no-repeat;
    background-size: 100% 100vh;
}
.box-logincard {
	  height: 350px;
	  width: 500px;
	  position: absolute;
	  left: 50%;
	  top: 50%;
	  
	  background-color: rgba(255,255,255,0);
	  transform: translate(-50%, -50%);
}

</style>