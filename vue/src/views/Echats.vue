<template>
    <div >
        <el-row :gutter="10" style="margin-bottom: 40px;">
          <el-col :span="12">
            <el-card>
                <span style="color: rgb(134, 235, 39)">系统注册人数：</span>  
                <span style="color: rgb(197, 48, 11)">{{ count }}</span>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card>
                <span style="color: chartreuse;">收录垃圾数量：</span>  
                <span style="color: rgb(197, 48, 11)">{{ garbagecount }}</span>
            </el-card>
        </el-col>
        </el-row>
        
        <el-row>
            <el-col :span="12">
                <div id="main" style="width: 500px; height: 400px;"></div>
            </el-col>
            <el-col :span="12">
                <div id="pie" style="width: 500px; height: 400px; margin-right: 5px;"></div>
            </el-col>
        </el-row>


    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    name: "Echats",
    data() {
        return {
            count:0,
            garbagecount:0
        }
    },
    created() {
        this.getCount()
        this.getGarbagecount()
    },
    methods: {
         getCount(){
            this.request.get("/echarts/count").then(res=>{
                this.count=res.data
            })
        },
        getGarbagecount(){
            this.request.get("/echarts/gcount").then(res=>{
                this.garbagecount=res.data
            })
        }
    },
    mounted() {//页面渲染后再触发
        let option = {
            title: {
                text: '每个季度注册用户数量',
                left: 'center'
            },
            xAxis: {
                type: 'category',
                data: ["第一季度", "第二季度", "第三季度", "第四季度"]
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    data: [],
                    type: 'line'
                }
            ]
        };
        const pieoption = {
            title: {
                text: '已收录垃圾',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name: 'Access From',
                    type: 'pie',
                    radius: '50%',
                    label:{
                        normal:{
                            show:true,
                            position:'inner',
                            textStyle :{
                                fontWerght :300,
                                fontSize:16
                            },
                            formatter:'{d}%',
                            color : '#fff'
                        },
                    
                    },
                    data: [
   
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        var chartDom = document.getElementById('main');
        var myChart = echarts.init(chartDom);

        var chartDom = document.getElementById('pie');
        var pieChart = echarts.init(chartDom);

        this.request.get("/echarts/member").then(res => {
            option.series[0].data = res.data
            pieoption.series[0].data=[
                {name:"可回收垃圾",value: res.data[0]},
                {name:"厨余垃圾",value: res.data[1]},
                {name:"有害垃圾",value: res.data[2]},
                {name:"其他垃圾",value: res.data[3]},
            ]
            myChart.setOption(option);
        })

        this.request.get("/echarts/garbage").then(res => {
            pieoption.series[0].data=[
                {name:"可回收垃圾",value: res.data[0]},
                {name:"厨余垃圾",value: res.data[1]},
                {name:"有害垃圾",value: res.data[2]},
                {name:"其他垃圾",value: res.data[3]},
            ]
            pieChart.setOption(pieoption);
        })
    }
}
</script>

<style></style>