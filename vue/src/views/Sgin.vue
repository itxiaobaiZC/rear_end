<template>
  <div class="wrapper">
    <el-card class="box-card">
    <div style="margin: 10px auto; width: 350px; height: 400px; padding: 20px; border-radius: 30%;">
        <div style="margin: 20px 0; text-align: center; font-size: 24px;"><b style="color: rgb(68, 68, 68);">用户注册</b></div>

        <el-form :model="user" :rules="rules" ref="userForm">
            <el-form-item prop="name">
                <el-input placeholder="请输入账号" v-model="user.name" size="medium" style="margin: 5px 0;" prefix-icon='el-icon-user'></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input placeholder="请输入密码" v-model="user.password" size="medium" style="margin: 5px 0;" prefix-icon='el-icon-lock' show-password></el-input>
            </el-form-item>
            <el-form-item prop="comfirmPassword">
                <el-input placeholder="确认密码" v-model="user.comfirmPassword" size="medium" style="margin: 5px 0;" prefix-icon='el-icon-lock' show-password></el-input>
            </el-form-item>
            <el-form-item prop="uname">
                <el-input placeholder="请输入昵称" v-model="user.uname" size="medium" style="margin: 5px 0;" prefix-icon='el-icon-user' ></el-input>
            </el-form-item>
            <el-form-item prop="number">
                <el-input placeholder="请输入手机号" v-model="user.number" size="medium" style="margin: 5px 0;" prefix-icon='el-icon-phone' ></el-input>
            </el-form-item>
            <el-form-item prop="address">
                <el-input placeholder="请输入地址" v-model="user.address" size="medium" style="margin: 5px 0;" prefix-icon='el-icon-location' ></el-input>
            </el-form-item>
            <el-form-item prop="email">
                <el-input placeholder="请输入邮箱" v-model="user.email" size="medium" style="margin: 5px 0;" prefix-icon='el-icon-message' ></el-input>
            </el-form-item>
                
                
            <el-form-item style="margin: 10px 0; text-align: right;">
                <el-button type="primary" size="small" autocomplete="off" @click="login">确认注册</el-button>
                <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
            </el-form-item>
        </el-form>
        
    

    </div>
    </el-card>
  </div>
</template>

<script>
export default {
    name:"Login",
    data(){
        return{
            user:{},
            rules:{
                uname:[
                    {required: true, message:'请输入用户名', trigger: 'blur'},

                ],
                password:[
                    {required: true, message:'请输入密码', trigger: 'blur'},
                    {min:3,max:20,message:"长度在3到20个字符",trigger: 'blur'}
                ],
                comfirmPassword:[
                    {required: true, message:'请输入密码', trigger: 'blur'},
                    {min:3,max:20,message:"长度在3到20个字符",trigger: 'blur'}
                ],
                number:[
                    {required: true, message:'请输入手机号', trigger: 'blur'},
                    {min:11,max:11,message:"格式不正确",trigger: 'blur'}
                ],
                address:[
                    {required: true, message:'请输入地址', trigger: 'blur'},
                ],
                email:[
                    {required: true, message:'请输入地址', trigger: 'blur'},
                ]

            }

            
        }
    },
    methods:{
        login(){
            this.$refs['userForm'].validate((valid)=>{
                if(valid){ //表单校验合法
                    this.request.post("/sys-user/sgin",this.user).then(res=>{
                        if(this.password!=this.comfirmPassword){
                            this.$message.error("两次密码输入不一致")
                            return false
                        }
                        if(res.code=='200'){
                            this.$message.success("注册成功")
                        }else{
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
.box-card {
    height: 700px;
	  width: 500px;
	  position: absolute;
	  left: 50%;
	  top: 50%;
	  
	  background-color: rgba(255,255,255,0);
	  transform: translate(-50%, -50%);
}
</style>