<template>
  <div id="login">
    <img src="../../../static/img/logo.png" style="margin-bottom: 20px;">

    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">

      <h3 class="login-title">Login</h3>

      <el-form-item label="username" prop="username">
        <el-input type="text" placeholder="用户名或邮箱地址" clearable v-model="form.username"/>
      </el-form-item>

      <el-form-item label="password" prop="password">
        <el-input type="password" placeholder="请输入密码" show-password v-model="form.password"/>
      </el-form-item>

      <el-form-item id="login-button">
        <el-button type="primary"  round @click="login()">登录</el-button>
      </el-form-item>

    </el-form>

    <div id="tips">
      <p>New to OJ? <router-link type="primary" :to="{name: 'register'}">Create an account</router-link></p>
      <p><router-link type="primary" :to="{name: 'change'}">Maybe you forget password ?</router-link></p>
      <p><router-link type="primary" :to="{name: 'dispatch'}">Go back</router-link></p>
      
    </div>
    
  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        form: {
          username: '',
          password: '',       
        },
        //表单验证，需要在el-form-item 元素中增加prop 属性
        rules: {
          username: [                       
            {required: true, message: " 账号不可为空", trigger: 'blur'}
          ],
          password: [
            {required: true, message: " 密码不可为空 ", trigger: 'blur'}
          ]
        },
      }
    },
    methods: {
      login() {
        if(this.form.username === '' || this.form.password === ''){
            this.$message.error('请完整填写用户名及密码');
            return;
        }
        let info = {username: this.form.username, password: this.form.password};
        this.$axios.post('/login', info).then(response => {
          let result = response.data;
          if(result.code !== 200) {
            this.$message.error(result.message);
          } else{
            this.$message({
              message: "登陆成功",
              type: 'success'
            });
            window.sessionStorage.setItem("loginUsername", result.data.name);
            window.sessionStorage.setItem("loginAccount", result.data.account);
            this.$router.push({name: 'dispatch'});
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .login-box {
    border: 1px solid #DCDFE6;
    width: 250px;
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

  #login-button{
    position: relative;
    left: -12%;

  }

  #login {
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