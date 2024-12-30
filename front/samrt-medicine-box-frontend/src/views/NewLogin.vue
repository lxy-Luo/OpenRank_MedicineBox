<template>
<div class="content" style="width: 1660px; height: 840px;">
    <div class="left">
    <img src="../assets/imgs/asset/img2.png" class="people p-animtion" alt="people" />
    <img src="../assets/imgs/asset/img1.png" class="sphere s-animtion" alt="sphere" />
    </div>
    <div class="right">
    <div class="top">
        <div class="top-item" v-for="item in topItems" :key="item">
        <span style="font-family:LiSu ;" class="top-text">{{ item }}</span>
        </div>
    </div>
    <div class="form-wrappepr">
        <h1><p style="font-family:YouYuan ;font-size: 40px;">凌霄空间</p><p style="font-style: italic;font-size: 10px;">Ethereal Space</p></h1>
        <input
        type="text"
        class="inputs user"
        placeholder="Enter Email Or Number"
        v-model="form.username"
        />
        <input
        type="password"
        class="inputs pwd"
        placeholder="Password"
        v-model="form.password"
        />
        <!-- <div style="height: 50px; width: 400px;" > -->
            <div class="radio-container">
                <div class="radio-group" style="padding-bottom: 20px;">
                <label v-for="role in roles" :key="role.value"   style=" display: flex;align-items: center;justify-content: center;margin-right: 30px;">
                    <input style="height: 20px;width: 20px;" type="radio" :value="role.value" v-model="form.role">
                    <span class="radio-label" style=" ">{{role.label}}</span>
                </label>
                </div>
            </div>
        <!-- </div> -->
        <div style="display: block;height: 70px;">
            <input type="text" class="inputsMin" style="" placeholder="验证码" v-model="code"/> 
            <Identify style="" :identifyCode="identifyCode" @click.native="refreshCode" />
        </div>
        
        <div class="tips">忘记密码</div>
        <button @click="login">Sigin In</button>
        <div class="other-login">
        <div class="divider">
            <span class="line"></span>
            <span class="divider-text">or continue with</span>
            <span class="line"></span>
        </div>
        <div class="other-login-wrapper">
            <div class="other-login-item">
            <img src="../assets/imgs/asset/QQ.png" alt="QQ" />
            </div>
            <div class="other-login-item">
            <img src="../assets/imgs/asset/WeChat.png" alt="WeChat" />
            </div>
        </div>
        </div>
        <div style="margin: 5px auto;width: 800px;">
            <span style=" text-align: center; float: left;">鄂网公备：<a  href="https://beian.miit.gov.cn/" target="_blank" style="color:#999;margin-right: 20px;">鄂ICP备2024070679号-1</a></span>
            <span style="text-align: center;float: left;">版权所有：三峡大学戴润东</span>
            <span style="text-align: center;float: left;margin-left: 15px;">启明星创新实验室</span>
        </div>
    </div>
    </div>
</div>
</template>

<script>
//引入图片验证组件
import Identify from "@/components/Identify";
export default {


    name:"NewLogin",
    data() {
        return {
            form: {role:"USER",username:"",password:""},
            code: '',   // 表单绑定的验证码
            // 图片验证码
            identifyCode: '',
            // 验证码规则（基础生成码的内容）
            identifyCodes: '123456789ABCDEFGHGKMNPQRSTUVWXY',
            topItems: ["主页", "注册", "联系我们", "帮助"],
            userInput: "",
            password: "",
            code:"",
            roles: [
                { value: 'USER', label: '用户账号' },
                { value: 'ADMIN', label: '管理账号' }
            ],
            
        };
    },
    components: {
        Identify
    },
    mounted() {
        this.refreshCode()
    },
    methods: {
         // 切换验证码
        refreshCode() {
            // 清空随机码
            this.identifyCode = ''
            // 给随机码重新赋值
            this.makeCode(this.identifyCodes, 4)
        },
        // 生成随机验证码
        makeCode(o, l) {
            for (let i = 0; i < l; i++) {
                this.identifyCode += this.identifyCodes[Math.floor(Math.random() * (this.identifyCodes.length))]
            }
        },
        signIn() {
            // 这里添加登录逻辑
            console.log("尝试登录", this.userInput, this.password);
        },
        login() {
            if(this.form.username === "" ||this.form.password === ""){
                this.$message.warning('请输入账号或密码')
                this.refreshCode()
                return
            }
            if (!this.code) {
                this.$message.warning('请输入验证码')
                this.refreshCode()
                return
            }
            //不限制验证码大小写
            if (this.code.toLowerCase() !== this.identifyCode.toLowerCase()) {
                this.$message.warning('验证码错误')
                this.refreshCode()
                return
            }
            // 验证通过
            this.$request.post('/login', this.form).then(res => {
                if (res.code === '200') {
                localStorage.setItem("xm-user", JSON.stringify(res.data))  // 存储用户数据
                this.$message.success('登录成功')
                //跳转延时
                setTimeout(() => {
                    // 跳转主页
                    if (res.data.role === 'ADMIN') {
                    location.href = '/home'  //后台
                    } else {
                    location.href = '/front'  //前台主页
                    }
                }, 500)
                } else {
                this.refreshCode()
                this.$message.error(res.msg)
                }
            })
        }
    }
};
</script>

<style scoped>
/* 这里可以添加你的样式 */
@import "../assets/css/login/style.css";

.radio-container {
  margin: 5px 20px;
}
.radio-group {
  display: flex;
  align-items: center;
}
.radio-label {
    padding: auto;
  float: left;
  text-align: center;
  font-size: 20px;
  width: 100px;
  height: 30px;
}
input[type="radio"] {
  appearance: none;
  -webkit-appearance: none;
  width: 18px;
  height: 18px;
  border: 2px solid #ccc;
  border-radius: 50%;
  outline: none;
  cursor: pointer;
  transition: border 0.2s ease-in-out;
}
input[type="radio"]:checked {
  border-color: #007bff;
}
input[type="radio"]:hover {
  border-color: #555;
}
</style>