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
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>


        <div style="padding: 10px 0;">
            <!-- 搜索框 -->
            <el-input style="width: 300px;height: 40px; margin-left:60% ;" placeholder="请输入标题" suffix-icon="el-icon-search"
                v-model="title" size="small"></el-input>
            <el-button style="margin-left: 5px;" type="primary" @click="load" size="small">搜索</el-button>
            <el-button style="margin-left: 5px;" type="warning" @click="reset" size="small">重置</el-button>
        </div>
        <div style="margin: 10px 0">
            <div style="padding: 10px 0; border-bottom: 1px dashed #ccc" v-for="item in tableData" :key="item.id">
                <div class="pd-10" style="font-size: 20px; color: #3F5EFB; cursor: pointer" @click="$router.push('/front/articleDetail?id=' + item.id)">{{ item.title }}</div>
                <div style="font-size: 14px; margin-top: 10px">
                <i class="el-icon-user-solid"></i> <span >{{ item.user }}</span>
                <i class="el-icon-time" style="margin-left: 10px"></i> <span>{{ item.time }}</span>
                </div>
            </div>
        </div>
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
    name: "FrontArticle",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 5,
            title: "",
            number: "",
            address: "",
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
        this.load()
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
            this.request.get("/article/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    title: this.title,
                    number: this.number,
                    address: this.address
                }
            }).then(res => {
                console.log(res);
                this.tableData = res.records
                this.total = res.total
            })

            this.request.get("/article/findAll").then(res=>{
                this.roles=res.data
            })


        },

        //重置按钮
        reset() {
            this.title = ""
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
            this.request.post("/article/deleteMul", ids).then(res => {
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
            this.request.post("/article/save", this.form).then(res => {
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
            this.request.delete("/article/delete/" + id).then(res => {
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