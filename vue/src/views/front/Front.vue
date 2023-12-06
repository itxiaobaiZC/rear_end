<template>
  <div >
    <!--    头部文件-->
    <div style=" display: flex; height: 60px; line-height: 60px; border-bottom: 1px solid #000000;background-color: rgb(48,65,86);; ">
      <div style="width: 300px; display: flex; padding-left: 30px">
        <div style="width: 60px">
          <a href="" target="blank">
            <img src="../../assets/logo1.png" alt="" style="width: 50px; position: relative; top: 5px; right: 0">
          </a>
        </div>
        <div style="flex: 1;color: white; font-size: 24px; font-family: '楷体'">垃圾分类管理</div>
      </div>
      <div style="flex: 1 ;" >
        <el-menu :default-active="'1'" class="el-menu-demo" mode="horizontal" router style="background-color: rgb(48,65,86); font-family: '楷体'; ">
          <el-menu-item index="/front/fhome" style="width: 15%;color: rgb(255, 255, 255);font-size: 16px;">首页</el-menu-item>
          
          <el-menu-item index="/front/garbage" style="width: 20%;color: rgb(255, 255, 255);font-size: 16px;">垃圾分类</el-menu-item>
          <el-menu-item index="/front/test" style="width: 20%;color: rgb(255, 255, 255);font-size: 16px;">垃圾分类小知识</el-menu-item>
          <el-menu-item index="/front/policy" style="width: 20%;color: rgb(255, 255, 255);font-size: 16px;">政府政策</el-menu-item>
          <el-menu-item index="/front/article" style="width: 20%;color: rgb(255, 255, 255);font-size: 16px;">系统公告</el-menu-item>
        </el-menu>
      </div>
      <div style="width: 200px">
        <div v-if="!user.name" style="text-align: right; padding-right: 30px">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/sgin')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown style="width: 150px;">
            <div style="display: inline-block;">
              <img :src="user.avatarUrl" alt=""
                style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
              <span style="color: rgb(255, 255, 255);">{{ user.name }}</span><i class="el-icon-arrow-down" style="color: rgb(255, 255, 255);margin-left: 5px"></i>
            </div>

            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <router-link to="/person">个人信息</router-link>
              </el-dropdown-item>
              <el-dropdown-item>
                <router-link to="/password">修改密码</router-link>
              </el-dropdown-item>
              <el-dropdown-item>
                <span style="text-decoration: none;" @click="logout">退出 </span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>
    <!-- 主体 -->
    <div style="width: 90%; margin: 0 auto">
      <router-view >
      </router-view>
    </div>
    <div class="empty-content">
    </div>
    <!-- 底部导航 -->
    <div style="margin-top: 100px;">
      <div class="bottom" >
        <div style="margin-left: 20px; margin-top: 10px;">
          <img src="../../assets/logo1.png" alt="" style="width: 18px; height: 18px;">
          <span class="bottom_test_title">垃圾分类管理系统</span>
       </div>
       <div style="margin-left: 60px; margin-top: 10px;">
          <i class="el-icon-phone" style="color: white; "></i>
          <span class="bottom_test">联系方式 : 12345678911</span>
          <el-divider direction="vertical" style="color: white;"></el-divider>
          <i class="el-icon-message" style="color: white; margin-left: 30px;"></i>
          <span class="bottom_test">邮  箱   : 12345678911</span>
          <span style="color: white;font-size: 12px; margin-left: 60px">版权所有皆为作者<i class="el-icon-bell" style="color: white;"></i></span>
          <span style="margin-left: 50%;font-size: 12px"><i class="el-icon-view" style="color: white;">10086</i></span>
       </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Front",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {

  },
  methods: {
    logout() {
      this.$store.commit("logout")
      this.$message.success("退出成功")
    }

  }
}
</script>

<style>
.item {
  display: inline-block;
  width: 100px;

  text-align: center;
  cursor: pointer
}

.item a {
  color: #1E90FF;
}

.item:hover {
  background-color: LightPink;
}
.bottom{
  position: fixed; 
  height: 70px;
  bottom: 0;
  z-index: 1000;
  background-color: rgb(48,65,86); 
  width: 100%; 
}
.bottom_test_title{
  margin-left: 20px auto;
  margin-top: 20px;
  color: white;
  font-size: 18px;
  font-family: "楷体";
}
.bottom_test{
  color: white;
  font-size: 12px;
  margin-right: 12px;
}
/* 占位符 */
.empty-content{
  bottom: 0;
}

</style>
