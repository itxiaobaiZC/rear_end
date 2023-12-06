<template>
    <div>
        <!-- 新增弹窗 -->
        <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
                <el-form-item label="用户名" :label-width="formLabelWidth">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="权限" :label-width="formLabelWidth">
                    <el-input v-model="form.flag" autocomplete="off"></el-input>
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
        <!-- 新增菜单弹窗 -->
        <el-dialog title="菜单分配" :visible.sync="menuDialogView" width="30%">
            <el-tree 
            :props="props" 
            :data="menuDate" 
            show-checkbox 
            node-key="id" 
            ref="tree" 
            :default-expanded-keys="expends"
            :default-checked-keys="checks">
                <span class="custom-tree-node" slot-scope="{ node , data}">
                    <span><i :class="data.icon"></i>{{ data.name }}</span>
                </span>
            </el-tree>
            <div slot="footer" class="dialog-footer">
                <el-button @click="menuDialogView = false">取 消</el-button>
                <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
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

        <el-table :data="tableData" style="padding: 5px 0;" border stripe :header-cell-class-name="tableHeadColor"
            @selection-change="handleSelectionchange">
            <el-table-column type="selection" width="55">


            </el-table-column>


            <el-table-column prop="id" label="Id" width="80px">
            </el-table-column>
            <el-table-column prop="name" label="用户名" width="100px">
            </el-table-column>
            <el-table-column prop="description" label="描述" width="120px">
            </el-table-column>
            <el-table-column prop="flag" label="权限" width="120px">
            </el-table-column>

            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="info" @click="selectMenu(scope.row)">分配菜单<i class="el-icon-menu"></i></el-button>

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
            dialogFormVisible: false,
            form: {},
            multipleSelection: [],
            tableHeadColor: 'tableHeadColor',
            menuDialogView: false,
            menuDate: [],
            props: {
                label: 'name'
            },
            expends: [],
            checks: [],
            roleId: null,
            roleFlag:''
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/sys-role/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                }
            }).then(res => {
                console.log(res);
                this.tableData = res.records
                this.total = res.total
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
            this.request.post("/sys-role/deleteMul", ids).then(res => {
                if (res.code === '200') {
                    this.$message.success("批量删除成功！")
                    this.load()
                } else {
                    this.$message.error("删除失败！")
                }
            })
        },
        //新增用户
        save() {
            this.request.post("/sys-role/save", this.form).then(res => {
                if (res.code === '200') {
                    this.$message.success("添加成功！")
                    this.dialogFormVisible = false
                    this.load()
                } else {
                    this.$message.error("添加失败！")
                }
            })
        },
        saveRoleMenu() {
            this.request.post("/sys-role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res =>{
                if(res.code==='200'){
                    this.$message.success("设置权限成功")
                    this.menuDialogView=false
                    //操作管理员角色后需要重新登陆
                    if(this.roleFlag==='ROLE_ADMIN'){
                        //退出
                        this.$store.commit("logout")
                    }

                }else{
                    this.$message.error(res.msg)
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
            this.request.delete("/sys-role/delete/" + id).then(res => {
                if (res.code === '200') {
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
        selectMenu(role) {
            this.menuDialogView = true
            this.roleId = role.id,
            this.roleFlag = role.flag,
                //请求菜单数据
                this.request.get("/sys-menu/findAll").then(res => {
                    this.menuDate = res.data

                    //后台返回数据
                    this.expends = this.menuDate.map(v => v.id)

                })

            this.request.get("/sys-role/getRoleMenu/" + this.roleId).then(res => {
                this.checks = res.data
                
                this.request.get("/sys-menu/findAllIds").then(r => {
                    
                    const ids = r.data
                    //和之前选择的菜单项 进行对比
                    //选中id 不包含时 false
                    ids.forEach(id => {
                        if (!this.checks.includes(id)) {
                            this.$refs.tree.setChecked(id, false)
                        }
                    })
                })
            })
        },
        handleCheckChange(data, checked, indeterminate) {
            console.log(data, checked, indeterminate);
        },

    }
}

</script>

<style >
.tableHeadColor {
    background: #eee !important;
}
</style>