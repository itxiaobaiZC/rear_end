<template>
    <el-card style="width: 30%; ">
        <!-- 新增弹窗 -->
        <el-form label-width="80px" size="small">

            <el-upload  class="avatar-uploader" action="http://localhost:9090/file/upload" 
            :show-file-list="false"
            :on-success="handleAvatarSuccess" 
            >
                <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            
            <el-form-item label="用户名"  :label-width="formLabelWidth">
                <el-input v-model="form.name" autocomplete="off" disabled></el-input>
            </el-form-item>
            <el-form-item label="昵称"  :label-width="formLabelWidth">
                <el-input v-model="form.uname" autocomplete="off" ></el-input>
            </el-form-item>
            <el-form-item label="电话" :label-width="formLabelWidth">
                <el-input v-model="form.number" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="地址" :label-width="formLabelWidth">
                <el-input v-model="form.address" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item >
                <el-button type="primary" @click="save">保存</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
export default {
    name: "Person",
    data() {
        return {
            form: {},
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    },
    created() {
        this.getUser().then(res=>{
            this.form=res
        })
    },
    methods: {
        //新增用户
        async getUser(){
            return (await this.request.get("/sys-user/updateName/" + this.user.name)).data
        },
        async save() {
            this.request.post("/sys-user/save", this.form).then(res => {
                if (res.code === '200') {
                    this.$message.success("保存成功！")

                    //触发父级更新user 存储用户信息
                    this.$emit("refreshUser")
                    // 更新浏览器存储的用户信息
                    this.getUser().then(res => {
                        res.token = JSON.parse(localStorage.getItem("user")).token
                        localStorage.setItem("user", JSON.stringify(res))
                    })

                } else {
                    this.$message.error("保存失败！")
                }
            })
        },
        handleAvatarSuccess(res){
            this.form.avatarUrl=res
        }
    }
}
</script>

<style>
.avatar-uploader{
    text-align: center;
}
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>