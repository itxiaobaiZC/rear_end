<template>
    <div>
        <div style="padding: 10px 0;">
            <!-- 搜索框 -->
            <el-input style="width: 200px; margin-right: 5px;" placeholder="请输入名称" suffix-icon="el-icon-search"
                v-model="name"></el-input>
            <el-button style="margin-left: 5px;" type="primary" @click="load">搜索</el-button>
            <el-button style="margin-left: 5px;" type="warning" @click="reset">重置</el-button>
        </div>
        
        <div style="padding-top: 10px 0;">
            <el-upload action="http://localhost:9090/file/upload" :show-file-list="false" :on-success="handleFileUpLodaSuccess" style="display: inline;" >
              <el-button type="primary" style="margin-left: 5px;">上传文件<i class="el-icon-top"></i></el-button>
            </el-upload>
             <el-popconfirm style="margin-left: 5px;" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                icon-color="red" title="确定删除吗？" @confirm="delMul">
                <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
        </div>
        <el-table :data="tableData" style="padding: 5px 0;" border stripe :header-cell-class-name="tableHeadColor"
            @selection-change="handleSelectionchange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="id" label="Id" width="140">
            </el-table-column>
            <el-table-column prop="name" label="名称" width="140">
            </el-table-column>
            <el-table-column prop="type" label="图片类型" width="140">
            </el-table-column>
            <el-table-column prop="size" label="大小类型" width="120">
            </el-table-column>
            <el-table-column label="下载">
                <template slot-scope="scope">
                    <el-button type="primary" @click="downlocal(scope.row.url)">下载</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="enable" label="启用">
                <template slot-scope="scope">
                    <el-switch @change="changEnable(scope.row)" v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc"></el-switch>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">           
                    <el-popconfirm style="margin-left: 5px;" confirm-button-text='确定' cancel-button-text='我再想想'
                        icon="el-icon-info" icon-color="red" title="确定删除吗？" @confirm="handleDel(scope.row.id)">
                        <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>

        </el-table>
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
    name: "File",
    data() {
        return {
            tableData: [],
            name: '',
            multipleSelection: [],
            pageNum: 1,
            pageSize : 5,
            total: 0,
            tableHeadColor: 'tableHeadColor'
        }
    },
    created() {
        this.load();
    },
    methods: {
        load() {
            this.request.get("/file/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name
                }
            }).then(res => {
                this.tableData = res.records
                this.total = res.total
            })
        },

        //重置按钮
        reset() {
            this.name = "",
            this.load()
        },

        //批量删除
        handleSelectionchange(val) {
            console.log(val)
            this.multipleSelection = val
        },
        delMul() {
            let ids = this.multipleSelection.map(v => v.id)
            this.request.post("/file/deleteMul", ids).then(res => {
                if (res.code==='200') {
                    this.$message.success("批量删除成功！")
                    this.load()
                } else {
                    this.$message.error("删除失败！")
                }
            })
        },


        //删除用户
        handleDel(id) {
            this.request.delete("/file/delete/" + id).then(res => {
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
        handleFileUpLodaSuccess(res){
            console.log(res);
            this.load();
        },
        downlocal(url){
            window.open(url);
        },
        changEnable(row){
            this.request.post("/file/updateById",row).then(res=>{
                if(res.code==='200'){
                    this.$message.success("操作成功！")
                }
            })
        }
    }
}
</script>

<style></style>