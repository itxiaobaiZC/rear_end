<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card style="margin-bottom: 10px;">
          <span style="font-family: '楷体';">欢迎 <span style="font-size: 24px;">{{user.name}}</span>登录系统</span>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <el-descriptions title="当前登录用户信息" direction="vertical" :column="4" border>
            <el-descriptions-item label="用户名">{{ user.name }}</el-descriptions-item>
            <el-descriptions-item label="昵称">{{ user.uname }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ user.number }}</el-descriptions-item>
            <el-descriptions-item label="居住地" :span="2">{{ user.address }}</el-descriptions-item>
            <el-descriptions-item label="备注">
              <el-tag size="small">学校</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="菜单权限">{{ user.role }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
        <el-row :gutter="16" style="margin-top:10px;">
            <el-col :span="12">
              <el-card class="box-card" style="height: 400px;width: 90%;">
                <span style="">系统公告</span>
                <el-timeline style="margin-top: 20px;">
                  <el-timeline-item v-for="(tableDatas,index) in tableData"
                  :key="index"
                  :timestamp="tableDatas.time"
                  placement="top">
                    <el-card>
                      <h4>{{ tableDatas.title }}</h4>
                      <p style="color: red;">{{tableDatas.content}}</p>
                    </el-card>
                  </el-timeline-item>
                </el-timeline>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card class="box-card" style="height: 400px;width: 90%;">
                <div class="radio">
                  登录日志
                  <el-radio-group v-model="reverse" style="margin-left: 20px;">
                    <el-radio :label="true">倒序</el-radio>
                    <el-radio :label="false">正序</el-radio>
                  </el-radio-group>
                </div>
                <el-timeline :reverse="reverse" style="margin-top: 30px;">
                  <el-timeline-item
                    v-for="(activity, index) in activities"
                    :key="index"
                    :timestamp="activity.logtime">
                    {{activity.name}}登录系统
                  </el-timeline-item>
                </el-timeline>
              </el-card>
            </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      tableData: [],
      reverse: true,
      activities: [],     // form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/article/findAll").then(res => {
        console.log(res);
        this.tableData = res.data
      })
      this.request.get("/daily-time/findAll").then(res => {
        console.log(res);
        this.activities = res.data
      })

    },


  }

}
</script>

<style>
#left {
  float: left;
  padding: 10px;
  width: 50%;
}

#right {
  float: left;
  padding: 10px;
  width: 50%;
  margin-left: -200px;
}

.box-card {
  overflow: auto; /* 自动开启滚动条 */
	max-height: 350px; /* 设置最大高度 */
	max-width: 350px; /* 设置最大宽度度 */
}
</style>