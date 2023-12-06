<template>
    <div>
        <!-- 新增弹窗 -->
        <el-dialog title="垃圾信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
                <el-upload  class="avatar-uploader" action="http://localhost:9090/file/upload" 
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess" 
                    >
                        <img v-if="form.picurl" :src="form.picurl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                <el-form-item label="名称" >
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="类别" >
                    <el-input v-model="form.category" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="价格" >
                    <el-input v-model="form.price" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="form.descrition" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>


        <div style="padding: 10px 0;">
            <!-- 搜索框 -->
            <el-input style="width: 200px; margin-right: 5px;" placeholder="请输入名称" suffix-icon="el-icon-search"
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
            <el-button style="margin:5px;" type="primary" @click="exp">导出<i class="el-icon-download"></i></el-button>
    
            <el-upload action="http://localhost:9090/garbage/import" :show-file-list="false" accept="xlsx" :on-success="handerImportSuccess" style="display: inline;">
                <el-button style="margin:5px;" type="primary" @click="imp">导入<i class="el-icon-upload2"></i></el-button>
            </el-upload>

        </div>

        <el-table :data="tableData" style="padding: 5px 0;" border stripe :header-cell-class-name="tableHeadColor"
            @selection-change="handleSelectionchange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="id" label="Id" width="80px">
            </el-table-column>
            <el-table-column prop="picurl" label="图片" width="120px" >
                <template slot-scope="scope">
                    <img :src="(scope.row.picurl)" alt="" style="width: 50px; height: 50px;">
                </template>
            </el-table-column>
            <el-table-column prop="name" label="垃圾名称" width="120px">
            </el-table-column>
            <el-table-column prop="category" label="类别" width="120 px">
            </el-table-column>
            <el-table-column prop="price" label="价格" width="120px" >
            </el-table-column>
            <el-table-column prop="descrition" label="描述" width="120px">
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
export default {
    name: "Garbage",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 5,
            name: "",
            category: "",
            price: "",
            dialogFormVisible: false,
            form: {},
            multipleSelection: [],
            tableHeadColor: 'tableHeadColor',
            roles:[],
            imgUrl:[],
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/garbage/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                }
            }).then(res => {
                this.tableData = res.records
                this.total = res.total
                this.imgUrl=res.records.picurl
            })

            this.request.get("/garbage/findAll").then(res=>{
                this.roles=res.data
            })
        },

        //重置按钮
        reset() {
            this.name = "",
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
            this.request.post("/garbage/deleteMul", ids).then(res => {
                if (res.code==='200') {
                    this.$message.success("批量删除成功！")
                    this.load()
                } else {
                    this.$message.error("删除失败！")
                }
            })
        },
        //新增用户
        save() {
            this.request.post("/garbage/save", this.form).then(res => {
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
            this.request.delete("/garbage/delete/" + id).then(res => {
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
        },
        //导出文件
        exp(){
            window.open("http://localhost:9090/garbage/export")
        },
        handerImportSuccess(){
            this.$message.success("导入成功！")
            this.load()
        },
        handleAvatarSuccess(res){
            this.form.picurl=res
        }
    }

}
</script>

<style >
.tableHeadColor {
    background: #eee !important;
}
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