<!--  -->
<template>
  <div style="width: 100%;">
<div class="serve">
  <div class="logo" style=" width: 100%; height: 20%">
    <img src="../.././assets/imgs/logo_fuwu.png" alt="">
  </div>

  <div class="website" style="display: flex;width: 100%; height: 40%">
    <el-card shadow="always" style="height: 60%; margin-right: 10px; background-color: #f2fdf6; border-radius: 15px;">
      <!--          上方标题-->
      <div class="upTitle" style="height:10%; width: 90%; margin-bottom: 20px">各学院网站</div>
      <!--          下方链接-->
      <div style="display: flex; height:80%; gap:10px">
        <div style="gap:15px; background-color: #fff; border-radius: 10px; padding: 10px; display: flex; flex-direction: column;">
          <a href="https://it.ctgu.edu.cn/">计算机与信息学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">水利与环境学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">电气与新能源学院</a>
        </div>
        <div style="gap:15px;background-color: #fff; border-radius: 10px; padding: 10px; display: flex; flex-direction: column;">
          <a href="https://it.ctgu.edu.cn/">机械与动力学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">经济与管理学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">健康医学院</a>
        </div>
        <div style="gap:15px;background-color: #fff; border-radius: 10px; padding: 10px; display: flex; flex-direction: column;">
          <a href="https://it.ctgu.edu.cn/">田家炳教育学院（高教所）</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">法学与公共管理学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">生物与制药学院</a>
        </div>
        <div style="gap:15px;background-color: #fff; border-radius: 10px;padding: 10px; display: flex; flex-direction: column;">
          <a href="https://it.ctgu.edu.cn/">艺术学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">国际学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">创新创业学院</a>
        </div>
        <div style="gap:15px;background-color: #fff; border-radius: 10px;padding: 10px; display: flex; flex-direction: column;">
          <a href="https://it.ctgu.edu.cn/">土木与建设学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">材料与化工学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">基础医学院</a>
        </div>
        <div style="gap:15px;background-color: #fff; border-radius: 10px;padding: 10px; display: flex; flex-direction: column;">
          <a href="https://it.ctgu.edu.cn/">文学与传媒学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">马克思主义学院</a>
          <a href="https://lxy.ctgu.edu.cn/index.jsp/">理学院</a>
        </div>
      </div>

    </el-card>
    <el-card shadow="always" style="height: 60%; background-color: #f2fdf6;border-radius: 15px;">
      <!--          上方标题-->
      <div class="upTitle" style="height:10%; width: 90%; margin-bottom: 20px">各学院网站</div>
      <!--          下方链接-->
      <div class="right_scoll" ref="scrollContainer" @mouseenter="stopScrolling" @mouseleave="startScrolling">
        <a href="https://it.ctgu.edu.cn/">计算机与信息学院</a>
        <a href="https://lxy.ctgu.edu.cn/index.jsp/" >理学院</a>
        <a href="https://it.ctgu.edu.cn/" >水利与环境学院</a>
        <a href="https://lxy.ctgu.edu.cn/index.jsp/">马克思主义学院</a>
        <a href="https://lxy.ctgu.edu.cn/index.jsp/">艺术学院</a>
        <a href="https://lxy.ctgu.edu.cn/index.jsp/">体育学院</a>
      </div>
    </el-card>
  </div>

  <div class="butTab" style="weight:100%; height:40%">
    <!--        上部区域-->
    <div class="up_theme">
      <a href="https://sxdxztjy.ctgu.edu.cn/">
        <img src="../.././assets/imgs/zhuan1.png" alt="" :style="getImgStyle(isHover)" @mouseover="isHover=true" @mouseout="isHover=false">
      </a>
      <a href="https://sxdxztjy.ctgu.edu.cn/">
        <img src="../.././assets/imgs/zhuan2.png" alt="" :style="getImgStyle(isHover1)" @mouseover="isHover1=true" @mouseout="isHover1=false">
      </a>
      <a href="https://sxdxztjy.ctgu.edu.cn/">
        <img src="../.././assets/imgs/zhuan3.png" alt="" :style="getImgStyle(isHover2)" @mouseover="isHover2=true" @mouseout="isHover2=false">
      </a>
      <a href="https://sxdxztjy.ctgu.edu.cn/" >
        <img src="../.././assets/imgs/zhuan4.png" alt="" :style="getImgStyle(isHover3)" @mouseover="isHover3=true" @mouseout="isHover3=false">
      </a>
    </div>
    <!--        下部分区域-->
    <div class="bom_sign">
      <a href="http://47.98.255.137:8080/login" class="icon-item" style="display: flex; flex-direction: column; align-items: center;margin-right: 120px">
        <img src="../.././assets/imgs/icon/trans.png" alt="" style="width:60px; height:60px; margin-bottom: 20px">
        <span>二手交易平台</span>
      </a>
      <a href="https://sxdxjwc.ctgu.edu.cn/" class="icon-item" style="display: flex; flex-direction: column; align-items: center;margin-right: 120px">
        <img src="../.././assets/imgs/icon/teach.png" alt="" style="width:70px; height:70px; margin-bottom: 10px">
        <span>三峡大学教务处</span>
      </a>
      <a href="http://jwxk.ctgu.edu.cn/xsxk/profile/index.html" class="icon-item" style="display: flex; flex-direction: column; align-items: center;margin-right: 120px">
        <img src="../.././assets/imgs/icon/course.png" alt="" style="width:70px; height:70px; margin-bottom: 10px">
        <span>三峡大学选课系统</span>
      </a>
      <a href="http://47.98.255.137:8080/login" class="icon-item" style="display: flex; flex-direction: column; align-items: center;margin-right: 120px">
        <img src="../.././assets/imgs/icon/buy.png" alt="" style="width:70px; height:70px; margin-bottom: 10px">
        <span>三峡大学缴费系统</span>
      </a>
      <a href="http://47.98.255.137:8080/login" class="icon-item" style="display: flex; flex-direction: column; align-items: center;margin-right: 120px">
        <img src="../.././assets/imgs/icon/bicycle.png" alt="" style="width:70px; height:70px; margin-bottom: 10px">
        <span>校园巴士</span>
      </a>
      <a href="https://tieba.baidu.com" class="icon-item" style="display: flex; flex-direction: column; align-items: center;">
        <img src="../.././assets/imgs/icon/topic.png" alt="" style="width:70px; height:70px; margin-bottom: 10px">
        <span>三峡大学贴吧</span>
      </a>
      <!-- <a href="http://localhost:8080/SSO?token={{token}}&username={{username}}&password={{password}}" class="icon-item" style="display: flex; flex-direction: column; align-items: center;">
        <img src="../.././assets/imgs/icon/topic.png" alt="" style="width:70px; height:70px; margin-bottom: 10px">
        <span>三峡大学贴吧</span>
      </a> -->
    </div>

  </div>
</div>
</div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';

export default {
    name:"Other",
// import引入的组件需要注入到对象中才能使用
  components: {},
  data  () {
// 这里存放数据
    return {
      isHover:false,
      isHover1:false,
      isHover2:false,
      isHover3:false,
      scrollInterval: null, // 保存 setInterval 的 ID
      iconsVisible: false,
      icons: [],
      animationTriggered: false, // 添加标志来跟踪动画状态

      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),//用户登录数据
      token:this.user.token,
      username:this.user.username,
      password:this.user.password,
      
    }
  },
// 监听属性 类似于data概念
  computed: {
  },
// 监控data中的数据变化
  watch: {},
// 方法集合
  methods: {
    getImgStyle(isHover) {
      return {
        width: isHover ? '320px' : '300px',
        height: 'auto',
        transition: 'transform 0.5s ease'
      }
    },
    startScrolling() {
      this.scrollInterval = setInterval(() => {
        const container = this.$refs.scrollContainer;
        container.scrollTop += 1; // 每次滚动 1px
        if (container.scrollTop >= container.scrollHeight - container.clientHeight) {
          container.scrollTop = 0; // 滚动到底部时返回顶部
        }
      }, 35); // 调整滚动速度，值越小滚动越快
    },
    stopScrolling() {
      clearInterval(this.scrollInterval); // 停止滚动
    },

  //   图标显示
    checkScrollPosition() {
      const scrollPosition = window.scrollY + window.innerHeight;
      const bomSignTop = this.$el.querySelector('.bom_sign').offsetTop;
      const bomSignBottom = bomSignTop + this.$el.querySelector('.bom_sign').offsetHeight;

      if (scrollPosition > bomSignTop) {
        if (!this.animationTriggered) {
          this.iconsVisible = true;
          this.showIcons();
          this.animationTriggered = true; // 标记动画已触发
        }
      } else {
        this.iconsVisible = false;
        this.resetIcons(); // 滚动回顶部区域时重置图标
        this.animationTriggered = false; // 标记动画未触发
      }
    },
    showIcons() {
      this.icons.forEach((icon, index) => {
        setTimeout(() => {
          icon.classList.add('show');
        }, index * 200); // 逐个显示，间隔 300ms
      });
    },
    resetIcons() {
      this.icons.forEach((icon) => {
        icon.classList.remove('show');
      });
    }
  },
// 生命周期 - 创建完成（可以访问当前this实例）
  created  () {

  },
// 生命周期 - 挂载完成（可以访问DOM元素）
  mounted  () {
    // console.log("this.$refs.scrollContainer:", this.$refs.scrollContainer)
    const scrollContainer = this.$refs.scrollContainer;
    const clone = scrollContainer.innerHTML;
    scrollContainer.innerHTML += clone;
    this.startScrolling();

  //   图标显示
    this.icons = document.querySelectorAll('.icon-item');
    window.addEventListener('scroll', this.checkScrollPosition);
  },
  beforeCreate  () {}, // 生命周期 - 创建之前
  beforeMount  () {}, // 生命周期 - 挂载之前
  beforeUpdate  () {}, // 生命周期 - 更新之前
  updated  () {}, // 生命周期 - 更新之后
  beforeDestroy  () {
    this.stopScrolling(); // 组件销毁时清除滚动
    window.removeEventListener('scroll', this.checkScrollPosition);
  }, // 生命周期 - 销毁之前
  destroyed  () {}, // 生命周期 - 销毁完成
  activated  () {} // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style scoped>
.serve{
  margin: 0px auto;
  width:1644px;
  height:950px;
  background-image: url('../../assets/imgs/bg_fuwu.jpg');
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}


.logo{
  display: flex;
  justify-content: center;
  align-items: center;
}
.website {
  display: flex;
  justify-content: center;
  align-items: center;
}

.butTab{
  display: flex;
  flex-direction: column;
}

.up_theme{
  gap: 10px;
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.bom_sign{
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.rotate360 {
  animation: rotate360 .5s ease-out 0s;
}

.bom_sign img:hover{
  animation: rotate360 .5s ease-out 0s;
}

.upTitle{
  font-family: 华文琥珀;
  font-size: 25px;
  color: #18632d;
}

.right_scoll{
  background-color: #fff;
  border-radius: 10px;
  margin-right: 10px;
  padding: 10px; display: flex;
  flex-direction: column;
  gap: 12px;
  width: 100%;
  height:140px;
  overflow-y: hidden; /* 隐藏滚动条 */
}

.icon-item {
  opacity: 0; /* 初始隐藏 */
  transition: opacity 0.5s ease-in-out, transform 0.5s ease-in-out;
  transform: translateY(20px); /* 初始位置 */
}

.icon-item.show {
  opacity: 1;
  transform: translateY(0); /* 动画完成后的状态 */
}
@keyframes rotate360 {
  100% {
    transform: rotate(360deg);
  }
}
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>