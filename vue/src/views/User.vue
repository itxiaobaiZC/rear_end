<template>
    <div>
        <!-- 新增弹窗 -->
        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small" :rules="rules" :model="form">
                <el-form-item prop="name" label="用户名" :label-width="formLabelWidth">
                    <el-input  v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="role" label="角色">
                    <el-select clearable v-model="form.role" placeholder="请选择角色" style="width: 100%;">
                        <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.flag"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="uname"  label="昵称" :label-width="formLabelWidth">
                    <el-input v-model="form.uname" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item prop="number" label="电话" :label-width="formLabelWidth">
                    <el-input v-model="form.number" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="address" label="地址" :label-width="formLabelWidth">
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="email" label="E-mail" :label-width="formLabelWidth">
                    <el-input v-model="form.email" autocomplete="off"></el-input>
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
            <el-input style="width: 200px; margin-right: 5px;" placeholder="请输入电话" suffix-icon="el-icon-message"
                v-model="number"></el-input>
            <el-input style="width: 200px; margin-right: 5px;" placeholder="请输入地址" suffix-icon="el-icon-position"
                v-model="address"></el-input>
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
            <el-upload action="http://localhost:9090/sys-user/import" :show-file-list="false" accept="xlsx" :on-success="handerImportSuccess" style="display: inline;">
                <el-button style="margin:5px;" type="primary" @click="imp">导入<i class="el-icon-upload2"></i></el-button>
            </el-upload>
        </div>

        <el-table :data="tableData" style="padding: 5px 0;" border stripe :header-cell-class-name="tableHeadColor"
            @selection-change="handleSelectionchange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="id" label="Id" width="80px">
            </el-table-column>
            <el-table-column prop="name" label="用户名" width="120px">
            </el-table-column>
            <el-table-column prop="uname" label="昵称" width="120px" >
            </el-table-column>
            <el-table-column prop="number" label="电话" width="120px">
            </el-table-column>
            <el-table-column prop="address" label="地址" width="150px">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" width="150px">
            </el-table-column>
            <el-table-column prop="role" label="角色" width="150px">
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
    name: "User",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 5,
            name: "",
            number: "",
            address: "",
            dialogFormVisible: false,
            form: {},
            multipleSelection: [],
            tableHeadColor: 'tableHeadColor',
            roles:[],
            rules: {
                number:[
                    {required: true, message:'请输入手机号', trigger: 'blur'},
                    {min:11,max:11,message:"格式不正确",trigger: 'blur'}
                ],
                address:[
                    {required: true, message:'请输入地址', trigger: 'blur'},
                ],
                uname:[
                    {required: true, message:'请输入昵称', trigger: 'blur'},
                    {min:1,max:11,message:"格式不正确",trigger: 'blur'}
                ],
                name:[
                    {required: true, message:'请输入用户名', trigger: 'blur'},
                    {min:6,max:11,message:"格式不正确",trigger: 'blur'}
                ],
                role:[
                    {required: true, message:'请输入手机号', trigger: 'blur'},
                ],
            },

        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/sys-user/page", {
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

            this.request.get("/sys-role/findAll").then(res=>{
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
            this.request.post("/sys-user/deleteMul", ids).then(res => {
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
            this.request.post("/sys-user/save", this.form).then(res => {
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
            this.request.delete("/sys-user/delete/" + id).then(res => {
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
            window.open("http://localhost:9090/sys-user/export")
        },
        handerImportSuccess(){
            this.$message.success("导入成功！")
            this.load()
        },
    }

}
</script>

<style >
.tableHeadColor {
    background: #eee !important;
}
</style>