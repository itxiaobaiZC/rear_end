<template>
  <div style="">
    <el-container style="height: 100vh">
      <el-aside sty :width="sideWidth + 'px'"
        style=" background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px  rgb(0, 32, 75);">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" ></Aside>
      </el-aside>

      <el-container>
        <el-header style=" border-bottom: 1px solid #ccc ;">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="isCollapse" :user="user"></Header>
        </el-header>

        <el-main>
          <!-- 当前页面的子路由在router-view -->
          <router-view @refreshUser="getUser"/>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<script>
// @ is an alias to /src

import Aside from "@/components/Aside"
import Header from "@/components/Header"

export default {

  name: 'HomeView',

  data() {
    return {

      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      user: {}
    }
  },
  components:{
    Aside,
    Header
  },
  created(){
    this.getUser()
  },
  methods: {
    // 收索按钮触发
    collapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false;
      } else {
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true;
      }
    },
    getUser(){
      let username=localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")).name : ""
      //从后台获取用户数据
     this.request.get("/sys-user/updateName/" + username).then(res=>{
        this.user=res.data
     })
    }
  }
}
</script>

