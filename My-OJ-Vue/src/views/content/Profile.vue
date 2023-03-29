<template>

    <div v-if="not_logined">
        <h2>尚未登陆</h2>
    </div>
    <div v-else>

    <!-- 个人信息 -->
    <h4>&nbsp;&nbsp;个人信息</h4>
    <el-descriptions class="margin-top" :column="4" border>
        <template slot="extra">
            <router-link :to="{name: 'change'}">
                <i class="el-icon-edit"></i> 修改密码
            </router-link>
        </template>

        <el-descriptions-item>
        <template slot="label">
            <i class="el-icon-user"></i>
            用户名
        </template>
            {{this.info.name}}
        </el-descriptions-item>

        <el-descriptions-item>
            <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            邮箱
            </template>
            {{this.info.account}}
        </el-descriptions-item>

        <el-descriptions-item>
            <template slot="label">
            <i class="el-icon-tickets"></i>
            年级
            </template>
            <el-tag size="small">{{this.info.grade}}</el-tag>
        </el-descriptions-item>

         <el-descriptions-item>
            <template slot="label">
            <i class="el-icon-office-building"></i>
            注册时间
            </template>
            {{this.info.register_time}}
        </el-descriptions-item>

        <el-descriptions-item>
            <template slot="label">
            <i class="el-icon-location-outline"></i>
            通过题目
            </template>
            {{this.info.finished}}
        </el-descriptions-item>

        <el-descriptions-item>
            <template slot="label">
            <i class="el-icon-location-outline"></i>
            通过容易题目数
            </template>
            {{this.info.simple_finished}}
        </el-descriptions-item>

        <el-descriptions-item>
            <template slot="label">
            <i class="el-icon-location-outline"></i>
            通过中等题目数
            </template>
            {{this.info.middle_finished}}
        </el-descriptions-item>

        <el-descriptions-item>
            <template slot="label">
            <i class="el-icon-location-outline"></i>
            通过困难题目数
            </template>
            {{this.info.hard_finished}}
        </el-descriptions-item>

        <el-descriptions-item>
            <template slot="label">
            <i class="el-icon-location-outline"></i>
            Java通过题目数
            </template>
            {{this.info.java_finished}}
        </el-descriptions-item>

        <el-descriptions-item>
            <template slot="label">
            <i class="el-icon-location-outline"></i>
            C/C++通过题目数
            </template>
            {{this.info.c_finished}}
        </el-descriptions-item>

        <el-descriptions-item>
            <template slot="label">
            <i class="el-icon-location-outline"></i>
            Python通过题目数
            </template>
            {{this.info.python_finished}}
        </el-descriptions-item>

    </el-descriptions>

    <el-divider></el-divider>

    <!-- 个人话题 -->
    <el-row :gutter="12">
        <h4>&nbsp;&nbsp;发布话题</h4><br>
        <el-col :span="8" v-for="item in this.topics" :key="item.num">

            <el-card shadow="hover" class="box-card">
                <div slot="header" class="clearfix">
                    <span style="font-size: 18px">{{item.title}}</span>
                    <router-link style="float: right;" :to="{name: 'topic', params:{num: item.num}}">
                        <span style="color: pink">查看详情</span>
                    </router-link>
                </div>
                <div class="text item">
                    {{item.desc}}
                </div><br>
                <div class="bottom clearfix">
                    <time class="time">{{item.tag}}</time>
                    <time class="time" style="float: right; margin-top: -7px">
                        <el-button size="small" @click="deleteTopic(item.num)">删除</el-button>
                    </time>                   
                </div>
            </el-card><br><br>

        </el-col>            
    </el-row>

    <el-divider></el-divider>
    <h4>&nbsp;&nbsp;发布画作</h4><br>
    <el-row :gutter="12">
        <el-col :span="6" v-for="item in this.paintings" :key="item.num">

            <el-card shadow="hover" class="box-card">
                <div slot="header" class="clearfix">
                    <span style="font-size: 18px">{{item.title}}</span>
                </div>
                <div class="text item">
                    {{item.desc}}
                </div><br>
                <div class="bottom clearfix">
                        <time class="time">
                            <el-button icon="el-icon-thumb" size="small">点赞 {{item.thumb}}</el-button>
                        </time>
                        <time class="time" style="float: right">
                            <el-button size="small" @click="deletePainting(item.num)">删除</el-button>
                        </time>                    
                    </div>
            </el-card><br>

        </el-col>            
    </el-row>

    <el-divider></el-divider>

    </div>
</template>

<script>
export default {
    name: 'Profile',

    data() {
        return {
            not_logined: true,
            username: '',
            info: {
                name: 'northboat',
                account: 'northboat@163.com',
                finished: 2,
                grade: '大二',
                simple_finished: 1,
                middle_finished: 1,
                hard_finished: 0,
                register_time: '2022-2.17',
                java_finished: 2,
                c_finished: 0,
                python_finished: 0
            },
            topics: [],
            paintings: [],
        };
    },

    mounted() {
        this.username = window.sessionStorage.getItem('loginUsername');
        if(this.username == null){
            this.$message("用户未登录");
            return;
        }
        this.not_logined = false;
        let info = {name: this.username};
        //console.log(info);
        this.$axios.post("/getTopicsByName", info).then(response=>{
            let result = response.data;
            if(result.code !== 200){
                this.$message.error('请求数据失败');
                return;
            }
            this.topics = result.data;
        })

        this.$axios.post("/getInfoByName", info).then((response)=>{
            let result = response.data;
            if(result.code !== 200){
                this.$message.error('请求数据失败');
                return;
            }
            this.info = result.data;
        })

        this.$axios.post("/getPaintingsByName", info).then((response)=>{
            let result = response.data;
            if(result.code !== 200){
                this.$message.error('请求数据失败');
                return;
            }
            this.paintings = result.data;
        })
    },

    methods: {
        deleteTopic(num){
            //console.log(num);
            let info = {num: num};
            this.$axios.post("/deleteTopic", info).then(response => {
                let result = response.data;
                if(result.code !== 200){
                    this.$message.error("删除失败");
                    return;
                }
                this.$message({
                    type: 'success',
                    message: '删除成功'
                })
                this.$router.push({name: 'dispatch', params: {delete: true}});
            })
        },

        deletePainting(num){
            let info = {num: num};
            this.$axios.post("/deletePainting", info).then(response => {
                let result = response.data;
                if(result.code !== 200){
                    this.$message.error("删除失败");
                    return;
                }
                this.$message({
                    type: 'success',
                    message: '删除成功'
                })
                this.$router.push({name: 'dispatch', params: {delete: true}});
            })
        }
    },
};
</script>

<style lang="scss" scoped>

</style>