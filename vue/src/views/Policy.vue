<template>
  <div>
      <!-- 文章预览 -->
      <el-dialog title="文章预览" :visible.sync="viewDialogVis" width="60%">
          <div>
              <mavon-editor 
              class="md"
              :value="content"
              :subfield="false"
              :defaultOpen="'preview'"
              :toolbarsFlag="false"
              :editable="false"
              :scrollStyle="true"
              :inhljs="true">

              </mavon-editor>
          </div>
          <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" >确 定</el-button>
          </div>
      </el-dialog>
      <!-- 新增弹窗 -->
      <el-dialog title="发布内容" :visible.sync="dialogFormVisible" width="60%">
          <el-form label-width="80px" size="small">
              <el-form-item label="标题" :label-width="formLabelWidth">
                  <el-input v-model="form.title" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="内容" :label-width="formLabelWidth">
                  <mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"></mavon-editor>
              </el-form-item>
              <el-form-item label="发布时间" :label-width="formLabelWidth">
                  <el-input v-model="form.releasetime" autocomplete="off"></el-input>
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
          </div>
      </el-dialog>


      <div style="padding: 10px 0;">
          <!-- 搜索框 -->
          <el-input style="width: 200px; margin-right: 5px;" placeholder="请输入标题" suffix-icon="el-icon-search"
              v-model="name"></el-input>
          <el-button style="margin-left: 5px;" type="primary" @click="load">搜索</el-button>
          <el-button style="margin-left: 5px;" type="warning" @click="reset">重置</el-button>
      </div>
      <div style="padding-top: 10px 0;">
          <el-button type="primary" @click="handAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
          <el-popconfirm style="margin-left: 5px;" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
              icon-color="red" title="确定删除吗？" @confirm="delMul">
              <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
      </div>

      <el-table :data="tableData" style="padding: 5px 0;" border stripe :header-cell-class-name="tableHeadColor"
          @selection-change="handleSelectionchange">
          <el-table-column type="selection" width="55">
          </el-table-column>
          <el-table-column prop="id" label="Id" width="80px">
          </el-table-column>
          <el-table-column prop="title" label="标题" width="120px">
          </el-table-column>
          <el-table-column prop="content" label="内容" width="200 px">
              <template slot-scope="scope">
                  <el-button type="primary" @click="view(scope.row.content)">预览</el-button>
              </template>
          </el-table-column>
          <el-table-column prop="releasetime" label="发布时间" width="150px">     
          </el-table-column>
          <el-table-column label="操作">
              <template slot-scope="scope">
                  <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>

                  <el-popconfirm style="margin-left: 5px;" confirm-button-text='确定' cancel-button-text='我再想想'
                      icon="el-icon-info" icon-color="red" title="确定删除吗？" @confirm="handleDel(scope.row.id)">
                      <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
                  </el-popconfirm>
              </template>
          </el-table-column>

      </el-table>
      <!-- 分页栏 -->
      <div style="padding:10px 0 ;">
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
              :page-sizes="[2, 5, 10, 15]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
      </div>
  </div>
</template>

<script>

import axios from 'axios'


export default {
  name: "Article",
  data() {
      return {
          tableData: [],
          total: 0,
          pageNum: 1,
          pageSize: 5,
          name: "",
          dialogFormVisible: false,
          form: {},
          multipleSelection: [],
          tableHeadColor: 'tableHeadColor',
          roles:[],
          content:'',
          viewDialogVis :false
      }
  },
  created() {
      this.load()
  },
  methods: {
      view(content) {
          this.content = content
          this.viewDialogVis = true
      },
      // 绑定@imgAdd event
      imgAdd(pos, $file) {
      let $vm = this.$refs.md
      // 第一步.将图片上传到服务器.
      const formData = new FormData();
      formData.append('file', $file);
      axios({
          url: 'http://localhost:9090/file/upload',
          method: 'post',
          data: formData,
          headers: {'Content-Type': 'multipart/form-data'},
      }).then((res) => {
          // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
          $vm.$img2Url(pos, res.data);
      })
      },
      load() {
          this.request.get("/policy/page", {
              params: {
                  pageNum: this.pageNum,
                  pageSize: this.pageSize,
                  name: this.name,
                  number: this.number,
                  address: this.address
              }
          }).then(res => {
              console.log(res);
              this.tableData = res.records
              this.total = res.total
          })

          this.request.get("/policy/findAll").then(res=>{
              this.roles=res.data
          })


      },

      //重置按钮
      reset() {
          this.name = "",
              this.number = "",
              this.address = "",
              this.load()
      },

      //新增按钮-弹窗
      handAdd() {
          this.dialogFormVisible = true
          this.form = {}
      },
      //批量删除
      handleSelectionchange(val) {
          console.log(val)
          this.multipleSelection = val
      },
      delMul() {
          let ids = this.multipleSelection.map(v => v.id)
          this.request.post("/policy/deleteMul", ids).then(res => {
              if (res.code==='200') {
                  this.$message.success("批量删除成功！")
                  this.load()
              } else {
                  this.$message.error("删除失败！")
              }
          })
      },
      //新增
      save() {
          this.request.post("/policy/save", this.form).then(res => {
              if (res.code==='200') {
                  this.$message.success("添加成功！")
                  this.dialogFormVisible = false
                  this.load()
              } else {
                  this.$message.error("添加失败！")
              }
          })
      },
      //编辑用户
      handleEdit(row) {
          this.form = row
          this.dialogFormVisible = true
          this.load()
      },
      //删除用户
      handleDel(id) {
          this.request.delete("/policy/delete/" + id).then(res => {
              if (res.code==='200') {
                  this.$message.success("删除成功！")
                  this.load()
              } else {
                  this.$message.error("删除失败！")
              }
          })
      },

      handleSizeChange(pageSize) {
          this.pageSize = pageSize;
          this.load()
      },
      handleCurrentChange(pageNum) {
          this.pageNum = pageNum
          this.load()
      }
  }

}
</script>

<style >
.tableHeadColor {
  background: #eee !important;
}
</style>