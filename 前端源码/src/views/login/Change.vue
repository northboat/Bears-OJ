<template>
    <div id="change">
    <img src="../../../static/img/logo.png" style="margin-bottom: 20px;" alt="">

    <el-form ref="loginForm" label-width="70px" class="login-box">

        <h3 class="login-title">Change your password</h3>

        <el-form-item label="邮箱">
            <el-input type="email" placeholder="请输入邮箱" clearable v-model="form.email"/>
        </el-form-item>

        <el-form-item id="button1">
            <el-button type="primary" round @click="getCodes()" :disabled="disabled">{{this.tip}}</el-button>
        </el-form-item>

        <el-form-item label="验证码">
            <el-input type="text" placeholder="请输入验证码" v-model="form.codes"/>
        </el-form-item>

        <el-form-item label="密码">
            <el-input type="password" placeholder="请设置新密码" show-password v-model="form.password"/>
        </el-form-item>

        <el-form-item label="确认密码">
            <el-input type="password" placeholder="请确认新密码" show-password v-model="form.enter_password"/>
        </el-form-item>

        <el-form-item id="button2">
            <el-button type="primary" round @click="change()">修改</el-button>
        </el-form-item>

    </el-form>

    <div id="tips">
        <p>Haven't login? <router-link type="primary" :to="{name: 'login'}">login</router-link></p>
        <p>Don't wanna change? <router-link type="primary" :to="{name: 'profile'}">back profile</router-link></p>    
    </div>
    </div>
</template>

<script>
export default {
    name: 'Change',

    data() {
        return {
            form: {
                email: '',
                codes: '',
                password: '',
                enter_password: '',
            },
            tip: '获取验证码',
            disabled: false,
        };
    },

    methods: {
        //用正则表达式验证邮箱是否规范
        isEmail(eamil){
            let regex = /^([a-zA-z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([A-zA-Z0-9]{2,4})+$/;
            return regex.test(eamil);
        },
        //获取验证码
        getCodes() {
            //console.log("hahaha");
            if(!this.isEmail(this.form.email)){
                this.$message.error('请正确填写邮箱');
                return;
            }
            let e = this.form.email;
            let info = {email: e};
            this.$axios.post('/getCode', info).then(response => {
                let result = response.data;
                if(result.code !== 200){
                    this.$message({
                        message: result.message,
                        type: 'warning'
                    });
                    this.$notify({
                        title: '提示',
                        message: result.data,
                        duration: 0,
                        type: 'warning',
                        position: 'top-left'
                    });
                } else{
                    this.$message({
                        message: '验证码发送成功, 请注意查收, 有效期10分钟',
                        type: 'success'
                    });
                }                      
                let time = 60;
                this.disabled = true; 
                this.tick(time);
            });
        },
        //60s定时发送，使用setTimeout计时器
        tick(time){
            if(time > 0){
                this.disabled = true;
                this.tip = (time--)+"s后重新发送";   
                //console.log(time);    
                var timer = window.setTimeout(()=>this.tick(time), 1000); 
            } else {
                clearTimeout(timer);
                this.disabled = false;
                this.tip = '获取验证码';      
            }
            
        },

        change() {
            let e = this.form.email;
            //console.log(e);
            if(e == null){
                this.$message({
                    message: '请先点击发送邮件按钮并接收邮件或补全邮箱',
                    type: 'warning'
                });
                return;
            }
            let c = this.form.codes;
            if(c === '' || c.length != 6){
                this.$message.error('请正确输入验证码');
                return;
            }
            if(this.form.password !== this.form.enter_password){
                this.$message.error('两次密码不一致，请检查后输入');
                return;
            }
            let info = {email: e, codes: c, password: this.form.password};
            this.$axios.post("/change", info).then(response => {
                let result = response.data;
                if(result.code !== 200){
                    this.$message.error(result.message);
                    return;
                }
                this.$message({
                    message: '修改成功',
                    type: 'success'
                });
                this.$router.push({name: 'main'});
            })
        },
    },
};
</script>

<style lang="scss" scoped>
  .login-box {
    border: 1px solid #DCDFE6;
    width: 300px;
    margin: auto;
    margin-bottom: 20px;
    padding: 35px 35px 15px 35px;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    box-shadow: 0 0 25px #909399;
  }

  #tips {
    font: 13px Small;
    border: 3px solid #DCDFE6;
    width: 250px;
    margin: auto;
    border-radius: 4px;
    padding: 9px 32px 9px 32px;
  }

  .login-title {
    text-align: center;
    margin: 0 auto 40px auto;
    color: #303133;
  }

  #button1 {
    position: relative;
    left: -4%;
  }
  #button2 {
    position: relative;
    left: -7%;
  }

  #change {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }

  .router-link-active{
    text-decoration: none;
  }
  
  a{
    text-decoration: none;
  }

  a:hover{
    text-decoration: none;
  }
</style>