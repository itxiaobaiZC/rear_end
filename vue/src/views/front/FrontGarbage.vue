<template>
    <div >
        <div style="padding: 10px 0;margin-left: 28%;">
            <!-- 搜索框 -->
            <el-input style="width: 260px; margin-right: 5px;" placeholder="请输入想要查询的垃圾名称" suffix-icon="el-icon-search"
                v-model="name"></el-input>
            <el-input style="width: 260px; margin-right: 5px;" placeholder="请输入想要查询的垃圾类别" suffix-icon="el-icon-search"
            v-model="category"></el-input>
            <el-button style="margin-left: 5px;" type="primary" @click="load">搜索</el-button>
            <el-button style="margin-left: 5px;" type="warning" @click="reset">重置</el-button>
        </div>
        <div style="margin-left: 10%;">
            <el-card style="width: 55%;height: 50%; margin-left: 20%;">
                <el-table :data="tableData" style="">
                    <el-table-column prop="picurl" label="图片" width="120px" >
                        <template slot-scope="scope">
                            <img :src="(scope.row.picurl)" alt="" style="width: 50px; height: 50px;">
                        </template>
                    </el-table-column>
                    <el-table-column  prop="name" label="垃圾名称" width="120px" >
                    </el-table-column>
                    <el-table-column prop="category" label="垃圾种类" width="120px" >
                    </el-table-column>
                    <el-table-column prop="descrition" label="垃圾描述" width="120px" >
                    </el-table-column>
                </el-table>
            </el-card>
        </div>

    </div>
</template>

<script>
export default {
    name: "FrontGarbage",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 5,
            name: "",
            category:""
        }
    },
    created() {
    },
    methods: {
        load() {
            this.request.get("/garbage/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                    category:this.category
                }
            }).then(res => {
                console.log(res);
                this.tableData = res.records
                this.total = res.total
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
.box-card{
    margin-bottom: 10px;
    height: 300px;
    width: 100%;
}
.img{
    width: 75%;
    height: 250px;
    margin-top: 8px;
}

</style>