<template>
  <div >
    <!-- 轮播图 -->
    <div style="margin: px 0 ; ">
      <el-card >
        <el-carousel height="450px" :interval="2000" style="width: 100%;height: 100%;">
          <el-carousel-item v-for="item in imgs" :key="item">
              <img :src="item" alt="" style="width: 100% ;height: 450px; ;">
          </el-carousel-item>
        </el-carousel>
      </el-card>
    </div>

    <!-- 文体部分 -->
    <div>
      <el-row :gutter="20" style="margin-top: 10px;">
        <el-col :span="16">
          <el-card>
              <span>
                垃圾分类（Garbage classification），一般是指按一定规定或标准将垃圾分类储存、投放和搬运，从而转变成公共资源的一系列活动的总称
              </span>
          </el-card>
          <el-row :gutter="16" style="margin-top:10px;">
              <el-col :span="12">
                <el-card style="height: 200px">
                  <span>从国外各城市对生活垃圾分类的方法来看，大致都是根据垃圾的成分构成、产生量，结合本地垃圾的资源利用和处理方式来进行分类。如德国一般分为纸、玻璃、金属和塑料等；澳大利亚一般分为可堆肥垃圾，可回收垃圾，不可回收垃圾；</span>

                </el-card>
              </el-col>
              <el-col :span="12">
                <el-card style="height: 200px">
                  <span>每个人每天都会扔出许多垃圾，在一些垃圾管理较好的地区，大部分垃圾会得到卫生填埋、焚烧、堆肥等无害化处理，而更多地方的垃圾则常常被简易堆放或填埋，导致臭气蔓延，并且污染土壤和地下水。</span>
  
                </el-card>
              </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">
            <el-card>
              <span>2019年6月25日，固体废物污染环境防治法修订草案初次提请全国人大常委会审议。</span>
            </el-card>
            <el-card style="margin-top:10px;height: 200px">
                  <span>2019年9月，为深入贯彻落实习近平总书记关于垃圾分类工作的重要指示精神，推动全国公共机构做好生活垃圾分类工作，发挥率先示范作用，国家机关事务管理局印发通知，公布《公共机构生活垃圾分类工作评价参考标准》，并就进一步推进有关工作提出要求</span>

            </el-card>
        </el-col>
      </el-row>

    </div>

  </div>
</template>

<script>
export default {
  name: "FrontHome",
  data() {
    return {
      imgs: [],
    }
  },
  created() {
      this.load();
  },
  methods: {
      load(){
          this.request.get("/homepic/findByEnable").then(res=>{
                this.imgs=res.data
                console.log(this.imgs);
            })
      }
  }
}
</script>

<style>

</style>
