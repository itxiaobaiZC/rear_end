<template>
    <div>
        <!-- 新增弹窗 -->
        <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="路径" :label-width="formLabelWidth">
                    <el-input v-model="form.path" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="图标" :label-width="formLabelWidth">
                    <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%;">
                        <el-option v-for="item in Options" :key="item.name" :label="item.name" :value="item.value">
                            <i :class="item.value"></i>{{ item.name }}
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="页面路径" :label-width="formLabelWidth">
                    <el-input v-model="form.pagePath" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="描述" :label-width="formLabelWidth">
                    <el-input v-model="form.description" autocomplete="off"></el-input>
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
        </div>

        <el-table :data="tableData" style="padding: 5px 0;" 
        border 
        stripe 
        :header-cell-class-name="tableHeadColor"
        @selection-change="handleSelectionchange"
        row-key="id"
        default-expand-all
        >

            <el-table-column prop="id" label="Id" width="80px">
            </el-table-column>
            <el-table-column prop="name" label="名称" width="80px">
            </el-table-column>
            <el-table-column prop="path" label="路径" width="100px">
            </el-table-column>
            <el-table-column prop="icon" label="图标" width="80px">
                <template slot-scope="scope">
                    <i :class="scope.row.icon" ></i>
                </template>
            </el-table-column>
            <el-table-column prop="pagePath" label="页面路径" >
            </el-table-column>
            <el-table-column prop="description" label="描述" >
            </el-table-column>


            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" v-if="!scope.row.pid && !scope.row.path" @click="handerAdd(scope.row.id)">新增子菜单<i class="el-icon-edit"></i></el-button>
                    <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>

                    <el-popconfirm style="margin-left: 5px;" confirm-button-text='确定' cancel-button-text='我再想想'
                        icon="el-icon-info" icon-color="red" title="确定删除吗？" @confirm="handleDel(scope.row.id)">
                        <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>

        </el-table>
        <!-- 分页栏 -->
        <!-- <div style="padding:10px 0 ;">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[2, 5, 10, 15]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div> -->
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
            dialogFormVisible: false,
            form: {},
            multipleSelection: [],
            tableHeadColor: 'tableHeadColor'
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/sys-menu/findAll", {
                params: {
                    name: this.name,
                }
            }).then(res => {
                this.tableData = res.data
            })
        },
        //新增子菜单
        handerAdd(pid){
            this.dialogFormVisible=true
            this.form={}
            if(pid){
                this.form.pid=pid
            }
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
        //批量删除
        delMul() {
            let ids = this.multipleSelection.map(v => v.id)
            this.request.post("/sys-menu/deleteMul", ids).then(res => {
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
            this.request.post("/sys-menu/save", this.form).then(res => {
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
            //图表数据
            this.request.get("/sys-menu/icons").then(res=>{
                this.Options=res.data
            })
            this.load()
        },
        //删除用户
        handleDel(id) {
            this.request.delete("/sys-menu/delete/" + id).then(res => {
                if (res.code==='200') {
                    this.$message.success("删除成功！")
                    this.load()
                } else {
                    this.$message.error("删除失败！")
                }
            })
        },

        // handleSizeChange(pageSize) {
        //     this.pageSize = pageSize;
        //     this.load()
        // },
        // handleCurrentChange(pageNum) {
        //     this.pageNum = pageNum
        //     this.load()
        // }
    }

}
</script>

<style >
.tableHeadColor {
    background: #eee !important;
}
</style>