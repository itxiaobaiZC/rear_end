<template>
  <div style=" font-size: 12px;line-height: 60px; display: flex;">
    <div style="flex: 1; font-size: 18px;">
      <span :class="collapseBtnClass" style="cursor: pointer;" @click="collapse"></span>
      
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px;">
            <el-breadcrumb-item :to= "'/'" >首页</el-breadcrumb-item>
            <el-breadcrumb-item  >{{currentPathName}}</el-breadcrumb-item>
      </el-breadcrumb>

    </div>
    <el-dropdown style="width: 150px;">
      <div style="display: inline-block;">
        <img :src="user.avatarUrl" alt=""
             style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
        <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
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
          <router-link to="/front/fhome">返回前台</router-link>
        </el-dropdown-item>
        <el-dropdown-item>
          <span style="text-decoration: none;" @click="logout">退出 </span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    user:Object
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;  //路由监听
    }
  },
  data(){
    return{
      
    }
  },
  watch:{
    currentPathName(newVal,oldVal){
      console.log(newVal);
    }
  },
  methods:{
    collapse(){
      this.$emit("asideCollapse")
    },
    logout(){
      this.$store.commit("logout")
      localStorage.removeItem("menus")
      this.$message.success("退出登录")
    }
  }

}
</script>

<style></style>