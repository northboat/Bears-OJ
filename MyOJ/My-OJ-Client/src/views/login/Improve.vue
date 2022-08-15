<template>
    <div id="improve">
        <img src="../../../static/img/logo.png" style="margin-bottom: 20px;" alt="">

    <el-form ref="loginForm" label-width="70px" class="login-box">

      <h3 class="login-title">Improve your Info</h3>

      <el-form-item label="昵称">
        <el-input type="text" placeholder="请输入昵称" clearable maxlength="10" show-word-limit v-model="username"/>
      </el-form-item>


      <el-form-item label="密码">
        <el-input type="password" placeholder="请设置密码" show-password v-model="password"/>
      </el-form-item>

      <el-form-item label="确认密码">
        <el-input type="password" placeholder="请确认密码" show-password v-model="enter_password"/>
      </el-form-item>

      <el-form-item id="button">
        <el-button type="primary" round @click="register()">注册</el-button>
      </el-form-item>

    </el-form>

    <div id="tips">
      <p>Wrong email? <router-link type="primary" :to="{name: 'register'}" @click="clearSession">refill the address</router-link></p>    
    </div>
    </div>
</template>

<script>
export default {
    name: 'Improve',

    data() {
        return {
            name_list: [],
            occupied: false,
            username: '',
            password: '',
            enter_password: ''
        };
    },

    watch:{
        username(){          
            for(var i = 0; i < this.name_list.length; i++){
              let n = this.name_list[i]
              //console.log(n);
              if(n === this.username){
                //令不可用
                //console.log("昵称已被占用");
                this.occupied = true;
                return;
              }
            }
            this.occupied = false;
            //console.log("合法昵称");
        },
    },

    mounted() {
        let codes = window.sessionStorage.getItem("registerCodes");
        if(codes === null){
            this.$router.push('/register');
            this.$message({
                message: '请先注册邮箱',
                type: 'warning'
            });
            return;
        }
        this.$axios.post("/userList").then( response => {
            this.name_list = response.data.data;   
            //console.log(this.name_list[0]);      
        });
    },

    methods: {
        clearSession(){
            window.sessionStorage.removeItem("registerCodes");
            window.sessionStorage.removeItem("registerEmail");
        },
        register(){
            if(this.occupied){
                this.$message.error('该昵称已被占用');
                return;
            }
            if(this.password === '' || this.enter_password === ''){
                this.$message.error('请输入密码');
                return;
            }
            if(this.password !== this.enter_password){
                this.$message({
                    message: '两次密码内容不一致',
                    type: 'warning'
                });
                return;
            }
            let info = {account: window.sessionStorage.getItem("registerEmail"), username: this.username, password: this.password};
            this.$axios.post("/register", info).then(response=>{
                let result = response.data;
                if(result.code !== 200){    
                    this.$router.push('/register');
                    this.$message.error("注册失败, 请重试");
                    return;
                }
                window.sessionStorage.removeItem("registerEmail");
                window.sessionStorage.removeItem("registerCodes");
                this.$router.push({name: "login"});
                this.$message({
                    message: '注册成功!',
                    type: 'success'
                });
            });
        }
    },
};
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

  #button {
    position: relative;
    left: -9%;
  }

  #improve {
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