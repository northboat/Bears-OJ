<template>
    <el-row :gutter="20">
        <!-- 展示区 -->
        <el-col :span="12">
          <div class="grid-content bg-purple">
            <el-header>
              <p style="font: 20px Extra large;">{{this.question.num}}&nbsp;&nbsp;{{this.question.title}}</p><br>
              <p style="font: 14px Base; margin-top: -7px">
                
                  题目难度:&nbsp;<el-tag size="small">{{this.question.level}}</el-tag>&nbsp;&nbsp;&nbsp;&nbsp;
                  点赞:&nbsp;<el-tag size="small">{{this.question.thumb}}</el-tag>&nbsp;&nbsp;&nbsp;&nbsp;
                  样例数:&nbsp;<el-tag size="small">{{this.question.example}}</el-tag>
              </p>  
            </el-header> <br><br>

            <div style="padding-left: 20px; padding-right: 12%">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                  <el-tab-pane label="题目详情" name="first"></el-tab-pane>
                  <el-tab-pane label="评论区" name="second"></el-tab-pane>
                  <el-tab-pane label="历史记录" name="third"></el-tab-pane>
                </el-tabs>
            
                <!-- &nbsp;&nbsp;&nbsp;
                <router-link :to="{name: 'introduce', params:{num: this.question.num}}">
                  <el-button type="primary" icon="el-icon-fork-spoon">                 
                    <span style="color: white">题目详情</span>                 
                  </el-button>
                </router-link>
                
                <router-link :to="{name: 'comment', params:{num: this.question.num}}">
                  <el-button type="primary" icon="el-icon-male">
                    <span style="color: white">评论区</span>
                  </el-button>
                </router-link>
                
                <router-link :to="{name: 'result', params:{num: this.question.num}}">
                  <el-button type="primary" icon="el-icon-sugar">                 
                    <span style="color: white">历史记录</span>
                  </el-button> 
                </router-link> -->
                
                
            </div> 
            <router-view></router-view>
          </div>
        </el-col>

        <!-- 做题区 -->
        <el-col :span="12" >
            <!-- 选择语言 -->
            <el-dropdown style="margin-bottom: 9px" @command="choseLang">
              <span style="font: 16px Medium" class="el-dropdown-link">&nbsp;{{this.chose}}<i class="el-icon-arrow-down el-icon--right"></i></span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="a">Java 8</el-dropdown-item>
                    <el-dropdown-item command="b">Java 11</el-dropdown-item>
                    <el-dropdown-item command="c" disabled>C++</el-dropdown-item>
                    <el-dropdown-item command="d" disabled>Python</el-dropdown-item>
                  </el-dropdown-menu>
            </el-dropdown> 

            <div class="grid-content bg-purple" id="workplace">  
                
                <el-input
                    type="textarea"
                    :rows="29"
                    v-model="answer"
                    id="answerArea">
                </el-input>

                <el-button type="primary" round id="btn" @click="judge()" :loading="judging">{{this.button_status}}</el-button>
                
            </div>
        </el-col>
    </el-row>
</template>

<script>
export default {
    name: 'Detail',
    data() {
        return {
            button_status: '提交',
            judging: false,
            chose: '请选择语言',
            lang: -1,
            answer: '',  
            question: {
                num: '',
                title: '',
                level: '',
                thumb: '',
                example: '',
                name: '',
                func: '',
                tag: '',
            },
            memory_limit: '',
            activeName: '',

        };
    },

    watch:{
        visible(val) {
          if (val) {
          //console.log(this.$parent.Selected);
          setTimeout(() => {
            let dom = document.getElementById("answerArea");
            console.log(dom);
            dom.addEventListener("keydown", (event) => {
              if (event.keyCode === 9) {
                console.log(777);
                event.preventDefault();
              }
            });
          }, 100);
        }},
    },

    mounted(){  
      let info = {num: this.$route.params.num};
      this.$axios.post('/getQuestion', info).then(response=>{
          let result = response.data;
          if(result.code === 1001){
              this.$router.push({name: 'repository'});
              this.$message.error('请求参数错误');
              return;
          } else if(result.code === 2008){
              this.$router.push({name: 'repository'});
              this.$message.error('请求资源不存在');
              return;
          }
          this.question = response.data.data;
          //console.log(this.question);
          this.answer = 'class Solution{\n\tpublic '+this.question.func+'{\n\n\t}\n}';
          if(this.question.tag == 'List'){
            this.answer =  '/*\n你所用到的节点数据结构\nclass Node{\n\tint val;\n\tNode next;\n\tpublic Node(){}\n\tpublic Node(int val){ this.val = val; this.next = null; }\n\tpublic Node(int val, Node next){ this.val = val; this.next = next; }\n}\n*/\n\n\n'+this.answer;
          } else if(this.question.tag == 'Tree'){
            this.answer =  '/*\n你所用到的节点数据结构\nclass Node{\n\tint val;\n\tNode left;\n\tNode right;\n\tpublic Node(){}\n\tpublic Node(int val){ this.val = val; this.left = null; this.right = null; }\n\tpublic Node(int val, Node left, Node right){\n\t\tthis.val = val; this.left = left; this.right = right;\n\t}\n}\n*/\n\n\n'+this.answer;
          }
      });
    },

    methods: {
        choseLang(command){
          switch(command){
            case "a": this.lang = 20800; this.chose = 'Java 8'; break;
            case "b": this.lang = 21100; this.chose = 'Java 11'; break;
            case "c": this.lang = 10730; this.chose = 'C++'; break;
            case "d": this.lang = 10520; this.chose = 'Python'; break;
          }
        },
        judge(){
          this.memory_limit = window.sessionStorage.getItem("memory_limit");
          //console.log(this.name + " " + this.memory_limit);
          if(window.sessionStorage.getItem("loginUsername") == null){
              this.$message({
                type: 'warning',
                message: '请先登录'
              });
              return;
          }
          if(this.lang === -1){
              this.$message({
                type: 'warning',
                message: '请先选择编程语言'
              });
              return;
          }
          this.judging = true;
          this.button_status = '正在判题';
          let info = {num: this.question.num, answer: this.answer, 
                      username: window.sessionStorage.getItem("loginAccount"), 
                      level: this.question.level,
                      name: this.question.name, lang: this.lang,
                      memoryLimit: this.memory_limit};
          this.$axios.post("/judge", info).then(response=>{
              this.judging = false;
              this.button_status = '提交';
              let result = response.data;
              if(result.code !== 200){
                this.$notify({
                  title: '服务器错误, 请重试',
                  message: result.message,
                  duration: 0
                });
                return;
              }
              this.$message({
                type: 'success',
                message: '判题结束',
              });
              //console.log(window.location);
              if(window.location.hash === "#/main/detail/result/" + this.question.num){
                console.log('nmsl')
                this.$router.push({name: 'dispatch', params: {num: this.question.num, result: result.data, code: this.answer}});
              } 
              else {
                console.log("hahaha");               
                this.$router.push({name: 'result', params: {result: result.data, code: this.answer}});
              }
              
              //window.sessionStorage.setItem("result", result.data);          
          });
        },

        handleClick(tab, event) {
          // console.log(tab, event);
          //console.log(this.activeName)
          switch(this.activeName){
              case 'first': this.$router.push({name: 'introduce', params: {num: this.question.num}}); break;
              case 'second': this.$router.push({name: 'comment', params: {num: this.question.num}}); break;
              case 'third': this.$router.push({name: 'result', params: {num: this.question.num}}); break;
          }
          
        }
    },
};
</script>

<style lang="scss" scoped>
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }

  .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
    
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  #workplace{
      text-align: center;
  }

  #btn{
      width: 140px;
      margin-top: 12px;
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