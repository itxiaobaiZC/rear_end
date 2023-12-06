<template>
    <div>
        <div class="admin-card">
            <el-row :gutter="20" style=" ;margin-right: 15px ; margin-left: -5px" type="flex" v-loading="loading" >
                <el-col v-for="(item,index) in tableData" :key="index" :span="8">
                    <el-card class="box-card" shadow="always" :body-style="{ padding: '0px' }">
                        <div slot="header" class="clearfix">
                            <span style="color: green;font-size: 24px;font-family: '楷体'">{{ item.title }}</span>
                        </div>
                        <div style="margin-left: 10px;font-size: 18px;font-family: '楷体';">
                            {{ item.content }}
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
                <!-- 分页栏 -->
        <div  style="margin-left: 50%;margin-top: 20%;">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[4, 6, 10, 12,14]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
    </div>
   
    
</template>

<script>
export default {
    name: "FrontGarbageTest",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 12,
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/knowledge/page", {
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

            this.request.get("/knowledge/findAll").then(res=>{
                this.roles=res.data
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

<style>
.el-row {
  display:flex;
  flex-wrap: wrap;
  align-items: center;
}
.box-card{
    margin-top: 10px;
    width: 360px;
    height: 300px;
}
.admin-card{
    /* 
    加上display:flex可以显示在一行，

    加上flex-wrap: wrap;可以进行换行显示比较好看
    */
    display: flex;
    flex-wrap: wrap;
    /* 这个属性可以加大卡片之间的间隔距离 */
    justify-content: space-around;
    margin-top: 10px;
}
</style>