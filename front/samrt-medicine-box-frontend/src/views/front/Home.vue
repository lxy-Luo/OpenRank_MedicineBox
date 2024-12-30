<template>
  <div  style="display: flex; justify-content: center; align-items: flex-start; min-height: 100vh; background-color: #f5f5f5;">
    <!-- 页面主体内容容器 -->
    <div class="page-container" style="width: 100%; display: flex; align-items: flex-start; flex-direction: column; justify-content: center; align-items: flex-start;">
      <!-- 轮播图 -->
      <div class="carousel-wrapper" style="height: 550px; width: 1640px; background-color: white; margin-bottom: 30px;">
        <el-carousel  style="height: 550px;" indicator-position="outside">
          <el-carousel-item style="height: 550px;" v-for="item in carouselImages" :key="item">
            <img :src="item" alt="图片" class="carousel-image">
          </el-carousel-item>
        </el-carousel>
      </div>

      <!-- 中间内容区域 -->
      <div class="content-box" style="width: 1200px;height: 480px;">
        <!-- 描述框 -->
        <div class="description-box">
          <div class="description-text">
            <p style="font-size: 30px;line-height: 1.5; background: linear-gradient(to right, #ff1ec7, #9fff87);-webkit-background-clip: text;-webkit-text-fill-color: transparent;margin-bottom: 30px;"><b>AI 智能药箱：守护健康的智慧之选</b></p>
            <div style="display: grid;grid-template-columns: repeat(2, 1fr); /* 两列，等宽 */grid-template-rows: repeat(2, 1fr); /* 两行，等高 */gap: 20px; /* 网格间隙 */">
              <p class="gp">精准提醒，不再错过服药时间</p>
              <p class="gp">AI 智能识别，保障用药安全</p>
              <p class="gp">便捷设计，轻松融入生活</p>
              <p class="gp">数据同步与医疗共享</p>
            </div>
            
          </div>
          <div style="width: 400px;height: 400px;">
            <img src="../.././assets/imgs/WhiteMedicineBox.png" alt="用户设备图片" class="device-image">
          </div>
        </div>
      </div>

      <div class="db" style="">
        <div style="display: flex;width: 100%; height:60px; background-color: #41ecca;">
            <div style="font-size: 30px;margin: 10px auto;width: 900px;height: 40px;">设备绑定用户</div>
        </div>
        <div style="margin:20px auto;height: 200px;width: 1200px;">
          <el-row :gutter="10" style="">
            <el-col :span="6" v-for="item in userList" :key="item.id">
              <div class="card" style="margin-bottom: 10px" @click="goDetail(item.uid)" >
                <div class="ckp" style="">
                  <div style="display: flex;"> 
                    <div v-if="item.uavatar" style="width: 80px; height:
                     80px; border-radius: 5px;margin:5px auto;">
                      <img  :src="item.uavatar" alt="" style="width: 80px; height: 80px; border-radius: 5px;">
                    </div>
                    <div style="margin: 5px auto;"> 
                      <div class="line1" style="margin: 10px 0;color: #000; font-weight: bold;font-size: 30px;">{{ item.uname }}</div>
                      <div class="line1" style="margin: 10px 0; color: #000; font-weight: bold;font-size: 10px;">{{ item.uemail }}</div>
                    </div>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>



    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem("xm-user") || '{}'), //当前登录用户数据
      userList: [],  // 所有的设备用户数据
      carouselImages: [
        'https://www-file.huawei.com/-/media/corp2020/home/banner/11/harmonyos-next.jpg',
        'https://www-file.huawei.com/-/media/corp2020/home/banner/11/vmall-1111-2024.jpg',
      ],
    };
  },
  mounted() {
    this.selectUserByDeviceId()
        // 可以在这里初始化轮播图相关的插件或其他操作
  },
  methods: {
    goDetail(id) {
      window.open('/front/UserDetails?userEntityId=' + id)
    },
    selectUserByDeviceId() {
      this.$request.get('/user/selectUserByDeviceId/'+this.user.device).then(res => {
        if(res.code === '200'){
          this.userList = res.data || []
          console.log(this.userList)
        }else{
          this.$message.error(res.msg)  // 弹出错误的信息
        }
      })
    }
    // 这里可以添加轮播图切换、小块点击等相关的函数
  }
};
</script>

<style scoped>
/* 彩色小卡片 */
.ckp {
  height: 90px;
  width: 250px;
  color: rgba(255,255,255,.7); 
  background:linear-gradient(45deg,#69eedb,#F5EEF8,#ffffff);
  border-radius: 10px

}

/*一行省略*/
.line1 {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.db {
  width:100%;min-height: 400px;background-color: #69eedb;
  
}
.gp {
  background-color: #f5f5f5e0; /* 浅灰色背景 */
      height: 100px;
      width: 300px;
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 15px; /* 内边距 */
      font-family: 'Cambria', serif;
      font-size: 20px;
      line-height: 1.4;
      margin: 10px 10px;
      border-radius: 5%;
}

.carousel-wrapper {
  position: relative;
  overflow: hidden;
  margin: 0 auto;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  position: relative;
  opacity: 1;
  transition: opacity 0.5s ease;
}

.description-box {
  display: flex;
  margin: 20px 0;
}

.description-text {
  width: 60%;
  padding-right: 20px;
}

.device-image {
  width: 100%;
  height: 100%;
  border-radius: 10%;
  margin-left: 20px;
}
.el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 550px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }


    /* 整体内容框样式 */
 .content-box {
      border-radius: 5%;
      background-color: white;
      margin: 20px auto;
      width: 70%;
      height: 450px;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: row;
      padding: 40px;
    }

    /* 描述框样式 */
 .description-box {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: row;
      width: 100%;
      gap: 40px; /* 增加描述文字和图片之间的间距 */

    }

    /* 描述文字样式 */
 .description-text {
      text-align: center;
      font-family: 'Cambria', serif;
    }

 .description-text p {
      font-size: 20px; line-height: 1.4; margin-bottom: 20px;
    }

    /* 设备图片样式 */
 .device-image {
      width: 100%;
      height: 100%;
    }
</style>