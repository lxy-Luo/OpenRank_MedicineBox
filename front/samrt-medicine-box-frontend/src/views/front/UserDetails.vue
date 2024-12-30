<!--  -->
<template>
  <div style=" background-color: #f5f5f5;">
    <div style="display: flex; margin:0 auto;width: 1300px;">
      <!-- 用户信息&邮件信息&用药信息 -->
      <div style="width: 1000px; ">
        <!-- 用户信息 -->
        <div style="width: 1000px;height: 300px;padding: 10px 5px">
          <div
            style=" width: 990px;border-radius: 1%;height: 280px;padding: 20px 20px;background:linear-gradient(45deg,#69eedb,#F5EEF8,#ffffff);">
            <div style="margin-top: 20px;margin-bottom:0px;width: 100%;height: 60%;display: flex;">
              <div v-if="userInfo.uavatar" style="width: 80px; height: 80px; border-radius: 5px;margin:5px auto;">
                <img :src="userInfo.uavatar" alt="" style="width: 80px; height: 80px;border-radius: 50%">
              </div>
              <div v-else style="width: 80px; height: 80px; border-radius: 50%;margin:5px auto;">
                <img src="../.././assets/imgs/touxiang1.png" alt=""
                  style="width: 80px; height: 80px; border-radius: 50%;">
              </div>
              <div style="margin: 5px auto;">
                <div class="line1" style="margin: 10px 0;color: #000; font-weight: bold;font-size: 30px;">{{
                  userInfo.uname }}</div>
                <div class="line1" style="margin: 10px 0; color: #000; font-weight: bold;font-size: 10px;">{{
                  userInfo.uemail }}</div>
                <div class="line1" style="margin: 10px 0; color: #000; font-weight: bold;font-size: 10px;">{{
                  userInfo.uphone }}</div>
              </div>
            </div>
            <div style="margin:0 30px;padding-top: 30px; height: 10%;display: flex;border-top: 1px solid gray;">
              <span style="width: 200px;"><i class="el-icon-male"
                  style=" margin-right: 5px;color:violet ">性别：</i>男</span>
              <span style="width: 200px;"><i class="el-icon-warning-outline"
                  style="  margin-right: 5px;color: red;">紧急联系人：</i>{{ userInfo.emergencyContactName }}</span>
              <span style="width: 200px;"><i class="el-icon-phone-outline"
                  style="  margin-right: 5px;color: red;">紧急电话：</i>{{ userInfo.emergencyContactPhone }}</span>
              <span style="width: 250px;"><i class="el-icon-location-information"
                  style=" margin-right: 5px;color: aqua;">通讯地址：</i>湖北省宜昌市西陵区</span>
            </div>
          </div>
        </div>
        <!-- 用药记录 -->
        <div style=" min-height: 400px;padding: 5px 5px">
          <div style="width: 990px;background-color: #fff;min-height: 400px;padding: 20px;">
            <div style="font-size: 20px;font-weight: 500;border-bottom: 1px solid gray;padding-bottom: 10px;">用药记录</div>
            <div v-infinite-scroll="load" :infinite-scroll-immediate="isHistLoading"
              style="overflow:auto; height:400px">
              <div v-for="item in history" type="info" class="history_box">
                您好,您于 <span style="color:#409EFF;"> {{ item.stime }} </span> 服用 <el-tag>{{ item.mname }}</el-tag>
                {{ item.sdosing }} {{ item.sunit }},祝你早日康复！
              </div>
            </div>
          </div>
        </div>
        <!-- 邮件信息 -->
        <div style=" min-height: 400px;padding: 5px 5px">
          <div style="width: 990px;background-color: #fff;min-height: 400px;padding: 20px;">
            <div style="font-size: 20px;font-weight: 500;border-bottom: 1px solid gray;padding-bottom: 10px;">邮件信息</div>
            <div v-infinite-scroll="msgLoad" :infinite-scroll-immediate="isMsgLoading"
              style="overflow:auto; height:400px">
              <div v-for="item in messageList" class="msg_box">
                <el-descriptions title="邮件详情">
                  <el-descriptions-item label="发送方">{{ item.sender }}</el-descriptions-item>
                  <el-descriptions-item label="发送时间">{{ item.date }}</el-descriptions-item>
                  <el-descriptions-item label="是否阅读">
                    <el-tag size="small" :type="item.isRead ? 'primary' : 'danger'">{{ item.isRead ? '已读' : '未读'
                      }}</el-tag>
                  </el-descriptions-item>
                  <el-descriptions-item label="内容">{{ item.content }}</el-descriptions-item>
                </el-descriptions>
              </div>
            </div>
          </div>
        </div>
        <!-- 药品信息 -->
        <div style=" min-height: 400px;padding: 5px 5px">
          <div style="width: 990px;background-color: #fff;min-height: 400px;padding: 20px;">
            <div style="font-size: 20px;font-weight: 500;border-bottom: 1px solid gray;padding-bottom: 10px;">收录药品</div>
            <div class="table">
              <el-table :data="tableData" stripe>
                <el-table-column prop="mid" label="序号" width="80" align="center" sortable></el-table-column>
                <el-table-column prop="mname" label="药品名" show-overflow-tooltip></el-table-column>
                <el-table-column prop="mdesc" label="介绍"></el-table-column>
                <el-table-column prop="mpic" label="图片" show-overflow-tooltip></el-table-column>
                <el-table-column prop="mcname" label="药品类别" show-overflow-tooltip></el-table-column>
                <el-table-column prop="mmanufacturer" label="制药公司" show-overflow-tooltip></el-table-column>
                <!-- <el-table-column prop="user" label="创建人"></el-table-column> -->
              </el-table>
              <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" :current-page="pageNum"
                  :page-sizes="[5, 10, 20]" :page-size="pageSize" layout="total, prev, pager, next" :total="total">
                </el-pagination>
              </div>
            </div>
          </div>
        </div>

      </div>
      <!-- 用药计划 -->
      <div style="width: 300px;padding: 10px 5px">
        <div
          style="background-color: #fff; width: 290px;border-radius: 1%;min-height: 580px;margin: 0 auto;padding: 10px;background:linear-gradient(45deg,#69eedb,#F5EEF8,#ffffff);">
          <div
            style=" width: 200px; margin: 0px auto; display: flex; flex-direction: column; justify-content: center; align-items: center;">
            <p
              style="font-family: Georgia, 'Times New Roman', Times, serif;font-size: 30px;background-color: #69eedb; font-weight: bold;-webkit-background-clip: text;-webkit-text-fill-color: transparent;">
              今日计划</p>
          </div>
          <div style="width: 250px;margin: 20px auto;min-height: 550px;">
            <div v-for="item in userPlanList" :key="item.id"
              style="display: flex;width: 250px;height: 80px;margin-top: 5px;">
              <div v-if="item.medicationImage"
                style="background-color: #fff;width: 80px; border-radius: 1%;margin: 0 auto;">
                <img :src="userInfo.uavatar" alt="" style="width: 80px; height: 80px;">
              </div>
              <div v-else style="background-color: #fff; width: 80px;margin: 0 auto;">
                <img src="../.././assets/imgs/yaoping1.png" alt="" style="width: 80px; height: 80px;border-radius: 50%">
              </div>
              <div style="width: 130px;">
                <div class="line1" style="font-size: 15px;margin:10px 5px;">{{ item.medicationName }}</div>
                <div class="line2" style="font-size: 10px;margin:0px 5px;">{{ item.medicationIntroduction }}</div>
              </div>
              <div style="width: 40px;margin: 20px auto;">{{ item.dosage }}/{{ item.medicationUnit }} {{
                item.medicationType }}</div>
            </div>
          </div>
        </div>
        <div style="background-color: #fff; width: 290px;border-radius: 2%;height: 90px;margin: 10px auto;">
          <div class="card" style="width: 290px;height: 90px;padding: 20px;">
            <div style="display: flex; grid-gap:10px; align-items: center">
              <div style="flex: 1">
                <div>微信打款，支持开发者</div>
                <div>😊😊😊😊😊😊😊</div>
              </div>
              <img src="../.././assets/imgs/img.png" alt="" style="width: 50px; height: 50px  ">
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';
import { getAllMedicine } from '@/api/medicine';
export default {
  name: "UserDeatails",
  // import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    // 这里存放数据
    return {
      total: 0,
      tableData: [],
      pageNum: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      isHistLoading: true,
      isMsgLoading: true,
      histPage: 0,
      msgPage: 0,
      userEntityId: this.$route.query.userEntityId,
      user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
      userInfo: [],
      userPlanList: [],
      history: [],
      messageList: []
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 方法集合
  methods: {
    handleCurrentChange(pageNum) {
      this.selectAllMedicines(pageNum)
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    selectAllMedicines(pageNum) {
      if (pageNum) this.pageNum = pageNum
      getAllMedicine({
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        mName: this.mName,
      })
        .then(res => {
          console.log(res)
          this.tableData = res.data?.list
          console.log(this.tableData)
          this.total = res.data?.total
        })
    },
    selectMessage() {
      this.$request.get("/message/list/" + this.userEntityId + '/' + this.msgPage).then(res => {
        if (res.code === 0) {
          if (res.list.length == 0) {
            this.isMsgLoading = false
            return
          }
          this.messageList = this.messageList.concat(res.list)
        } else {
          this.$message.error(res.msg)  // 弹出错误的信息
        }
      })
    },
    selectMedicinePlan() {
      this.$request.get("/medicationsituation/history/list/" + this.userEntityId + '/' + this.histPage).then(res => {
        if (res.code === 0) {
          if (res.list.length == 0) {
            this.isHistLoading = false
            return
          }
          this.history = this.history.concat(res.list)
          console.log(this.history)
        } else {
          this.$message.error(res.msg)  // 弹出错误的信息
        }
      })
    },
    selectUserEntityById() {
      this.$request.get("/smbUser/selectUserInfoByUserId/" + this.userEntityId).then(res => {
        if (res.code === '200') {
          this.userInfo = res.data || []
        } else {
          this.$message.error(res.msg)  // 弹出错误的信息
        }
      })
    },
    msgLoad() {
      console.log("邮件信息到底了")
      this.msgPage++
      this.selectMessage()
    },
    load() {
      console.log("历史用药信息到底了")
      this.histPage++
      this.selectMedicinePlan()
    },
    selectUserPlanById() {
      this.$request.get("/dailymedicationplan/userPlan/" + this.userEntityId).then(res => {
        if (res.code === '200') {
          this.userPlanList = res.data || []
        } else {
          this.$message.error(res.msg)  // 弹出错误的信息
        }
      })
    }

  },
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.selectAllMedicines()
    //this.selectMessage()
    this.selectUserEntityById();
    this.selectUserPlanById();
    //this.selectMedicinePlan();
  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {

  },
  beforeCreate() { }, // 生命周期 - 创建之前
  beforeMount() { }, // 生命周期 - 挂载之前
  beforeUpdate() { }, // 生命周期 - 更新之前
  updated() { }, // 生命周期 - 更新之后
  beforeDestroy() { }, // 生命周期 - 销毁之前
  destroyed() { }, // 生命周期 - 销毁完成
  activated() { } // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style scoped>
/*一行省略*/
.line1 {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/*两行省略*/
.line2 {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.history_box {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  margin: 10px 10px;
  padding: 10px 10px;
  height: 50px;
  line-height: 35px
}

.msg_box {
  margin: 10px 10px;
  padding: 10px 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}
</style>