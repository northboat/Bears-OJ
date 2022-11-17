<template>
    <div class="container-fluid p-0">

        <div class="row mb-2 mb-xl-3">
            <div class="col-auto d-none d-sm-block">
                <h3>Hello!&nbsp;{{this.username}}</h3>
            </div>

            <div class="col-auto ml-auto text-right mt-n1">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb bg-transparent p-0 mt-1 mb-0">
                        <li class="breadcrumb-item"><router-link :to="{name: 'dispatch', params: {repoTag: 'All'}}">practice</router-link></li>
                        <li class="breadcrumb-item"><router-link :to="{name: 'profile'}">profile</router-link></li>
                        <li class="breadcrumb-item active" aria-current="page">当前版本: {{this.news.version}}</li>
                    </ol>
                </nav>
            </div>
        </div>

        <div class="row">
            <div class="col-xl-6 col-xxl-5 d-flex">
                <div class="w-100">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title mb-4">总用户量</h5>
                                    <h1 class="mt-1 mb-3">{{this.news.user_num}}</h1>
                                    <div class="mb-1">                                       
                                        <span class="text-muted">Since </span>
                                        <span class="text-success"> <i class="mdi mdi-arrow-bottom-right"></i> 2022/2/17 </span>
                                    </div>
                                </div>
                            </div>
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title mb-4">总访问量</h5>
                                    <h1 class="mt-1 mb-3">{{this.news.visit_num}}</h1>
                                    <div class="mb-1">
                                        <span class="text-muted">Since</span>
                                        <span class="text-success"> <i class="mdi mdi-arrow-bottom-right"></i> 2022/2/17 </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title mb-4">题目总量</h5>
                                    <h1 class="mt-1 mb-3">{{this.quesInfo.ques_num}}</h1>
                                    <div class="mb-1">
                                        <span class="text-muted">Since begin</span>
                                    </div>
                                </div>
                            </div>
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title mb-4">新增题目</h5>
                                    <h1 class="mt-1 mb-3">{{this.quesInfo.new_ques.title}}</h1>
                                    <div class="mb-1">
                                        <span class="text-muted">题号&nbsp;</span>
                                        <span class="text-success"> <i class="mdi mdi-arrow-bottom-right"></i>{{this.quesInfo.new_ques.num}}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xl-6 col-xxl-7">
                <div class="card flex-fill w-100">
                    <div class="card-header">
                        <h5 class="card-title mb-0">校内资讯</h5>
                    </div>
                    <div class="card-body py-3">
                        <div class="block" style="text-align: center">
                            <el-carousel trigger="click" height="252px">
                                <el-carousel-item v-for="item in carousel" :key="item">
                                    <h3 class="small">{{item}}</h3>
                                </el-carousel-item>
                            </el-carousel>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-12 col-md-6 col-xxl-3 d-flex order-2 order-xxl-3">
                <div class="card flex-fill w-100">
                    <div class="card-header">
                        <h5 class="card-title mb-0">语言使用率</h5>
                    </div>
                    <div class="card-body d-flex">
                        <div class="align-self-center w-100">
                            <el-progress :text-inside="true" :stroke-width="25" :percentage="this.percent.java"></el-progress><br>
                            <el-progress :text-inside="true" :stroke-width="25" :percentage="this.percent.c" status="success"></el-progress><br>
                            <el-progress :text-inside="true" :stroke-width="25" :percentage="this.percent.python" color="pink"></el-progress><br><br>
                            

                            <table class="table mb-0">
                                <tbody>
                                    <tr>
                                        <td>Java</td>
                                        <td class="text-right">{{this.news.java_using}}</td>
                                    </tr>
                                    <tr>
                                        <td>C++/C</td>
                                        <td class="text-right">{{this.news.c_using}}</td>
                                    </tr>
                                    <tr>
                                        <td>Python</td>
                                        <td class="text-right">{{this.news.python_using}}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            

            <div class="col-12 col-lg-8 col-xxl-9 d-flex">
                <div class="card flex-fill">
                    <div class="card-header">

                        <h5 class="card-title mb-0">小花卷er~</h5>
                    </div>
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>昵称</th>
                                <th>联系方式</th>
                                <th>入站时间</th>
                                <th>通过题目数</th>
                                <th>年级</th>
                                <th class="d-none d-md-table-cell">擅长语言</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- 7个 -->
                            <tr v-for="user in this.topUsers" :key="user.name">
                                <td>{{user.name}}</td>
                                <td>{{user.account}}</td>
                                <td class="d-none d-xl-table-cell">{{user.register_time}}</td>
                                <td class="d-none d-xl-table-cell">{{user.finished}}</td>
                                <!-- badge bg-danger bg-warning-->
                                <td><span class="badge bg-success">{{user.grade}}</span></td>
                                <td class="d-none d-md-table-cell">{{user.skillful_lang}}</td>
                            </tr>
                           
                            
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</template>

<script>

export default {
    name: 'Display',

    data() {
        return {
            username: '',
            news:{
                version: 0,
                user_num: -1,
                visit_num: -1,
                news_one: -1,
                news_two: -1,
                news_three: -1,
                news_four: -1,
                java_using: 0,
                c_using: 0,
                python_using: 0
            },
            carousel: [12, 9, 2, 95],
            percent:{
                c: 0,
                java: 0,
                python: 0,
            },
            quesInfo:{
                ques_num: -1,
                new_ques: {
                    num: -1,
                    title: '出bug辣'
                },
            },
            topUsers:{
                name: '',
                account: '',
                finished: -1,
                grade: 0,
                register_time: '',
                skillful_lang: '',
            },
            
        };
    },

    mounted() {
        if(window.sessionStorage.getItem("loginUsername") !== null){
            this.username = window.sessionStorage.getItem("loginUsername");
        }
        this.$notify({
          title: '公告',
          message: '本站仍处于测试阶段, 若出现问题麻烦开发者:1543625674@qq.com',
          duration: 4000,
          position: 'top-right',
          offset: 54,
        });

        this.$axios.post("/getNews").then(response => {
            if(response.data.code != 200){
                this.$message.error("加载数据失败");
                return;
            }
            this.news = response.data.data;
            //console.log(this.news)
            this.setCarousel();
            this.setPercent();
        })

        this.$axios.post("/getQuesNews").then(response => {
            if(response.data.code != 200){
                this.$message.error("加载数据失败");
                return;
            }
            //console.log(response.data.data);
            this.quesInfo = response.data.data;
        })

        this.$axios.post("/getTopUser").then(response => {
            if(response.data.code !== 200){
                this.$message.error("加载数据失败");
                return;
            }
            this.topUsers = response.data.data;
        })
    },

    methods: {
        setCarousel(data){
            this.carousel[0] = this.news.news_one;
            //console.log(this.news.news_two + " " + this.carousel[0])
            this.carousel[1] = this.news.news_two;
            this.carousel[2] = this.news.news_three;
            this.carousel[3] = this.news.news_four;
        },
        setPercent(){
            let tatol = this.news.java_using + this.news.c_using + this.news.python_using;
            this.percent.c = this.fixingPercent(this.news.c_using / tatol);
            //console.log(this.percent.c)
            this.percent.java = this.fixingPercent(this.news.java_using / tatol);
            this.percent.python = this.fixingPercent(this.news.python_using / tatol);
        },
        fixingPercent(srcPercent){
            return parseFloat((srcPercent * 100).toFixed(2));
        }
    },
};
</script>

<style lang="scss" scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 252px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
     background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
     background-color: #d3dce6;
  }
</style>