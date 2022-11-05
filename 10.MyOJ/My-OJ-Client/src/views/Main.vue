<template>
<div v-cloak>
    <div class="wrapper">
        <link rel="stylesheet" href="./static/css/app.css">
		<link rel="stylesheet" href="./static/fonts">
        <!--侧边栏  -->
		<nav id="sidebar" class="sidebar">
			<div class="sidebar-content js-simplebar">
				
				<router-link style="margin-top: 12px" class="sidebar-brand" to="/main/hello">
                    <span class="align-middle">NEUQ OJ&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../static/img/logo-dark.png" alt=""></span>
                </router-link>
				

				<ul style="margin-top: -7px" class="sidebar-nav">
					<li class="sidebar-header">
						Online Judge
					</li>

					<li class="sidebar-item">
						<router-link class="sidebar-link" to="/main/hello">
                            <i class="el-icon-s-home"></i> <span class="align-middle">主页</span>
                        </router-link>
					</li>

					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name: 'dispatch', params: {repoTag: 'All'}}">
                            <i class="el-icon-suitcase-1"></i> <span class="align-middle">题库</span>
                        </router-link>
					</li>

					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name: 'dispatch', params: {discTag: 'All'}}">
                            <i class="el-icon-toilet-paper"></i> <span class="align-middle">茶余</span>
                        </router-link>
					</li>

					

					<li class="sidebar-header">
						Study & Play
					</li>
					<li class="sidebar-item">
						<a data-target="#ui" data-toggle="collapse" class="sidebar-link collapsed">
                            <i class="el-icon-suitcase"></i> <span class="align-middle">数据结构</span>
                        </a>
						<ul id="ui" class="sidebar-dropdown list-unstyled collapse " data-parent="#sidebar">
							<li class="sidebar-item"><router-link class="sidebar-link" :to="{name: 'dispatch', params:{repoTag: 'List'}}">链表</router-link></li>
							<li class="sidebar-item"><router-link class="sidebar-link" :to="{name: 'dispatch', params:{repoTag: 'Stack&Queue'}}">栈和队列</router-link></li>
							<li class="sidebar-item"><router-link class="sidebar-link" :to="{name: 'dispatch', params:{repoTag: 'String&Arr'}}">串和数组</router-link></li>
							<li class="sidebar-item"><router-link class="sidebar-link" :to="{name: 'dispatch', params:{repoTag: 'Tree'}}">二叉树</router-link></li>
							<li class="sidebar-item"><router-link class="sidebar-link" :to="{name: 'dispatch', params:{repoTag: 'Map'}}">图</router-link></li>
							<li class="sidebar-item"><router-link class="sidebar-link" :to="{name: 'dispatch', params:{repoTag: 'Sort&Search'}}">排序与查找</router-link></li>
						</ul>
					</li>


					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name: 'board'}">
                            <i class="el-icon-magic-stick"></i> <span class="align-middle">前端画板</span>
                        </router-link>
					</li>

					

					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name: 'dispatch', params:{discTag: 'Books'}}">
                            <i class="el-icon-notebook-2"></i> <span class="align-middle">书目推荐</span>
                        </router-link>
					</li>

					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name: 'dispatch', params:{discTag: 'Teams'}}">
                            <i class="el-icon-phone-outline"></i> <span class="align-middle">组队资讯</span>
                        </router-link>
					</li>

					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name: 'dispatch', params:{discTag: 'Push'}}">
                            <i class="el-icon-key"></i> <span class="align-middle">内推人脉</span>
                        </router-link>
					</li>

					<!-- li:data-toggle="collapse" class="sidebar-link collapsed"  a:data-target="#forms" -->
					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name: 'list'}">
                            <i class="el-icon-star-off"></i> <span class="align-middle">力扣题单</span>
                        </router-link>
						<!-- <ul id="forms" class="sidebar-dropdown list-unstyled collapse " data-parent="#sidebar">
							<li class="sidebar-item"><a class="sidebar-link" href="#">数组</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">字符串</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">数与位</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">栈与递归</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">链表</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">哈希表</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">贪心算法</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">双指针法</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">树</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">图与搜索</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">二分查找</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">二进制运算的应用</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">动态规划</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">采样</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">计算几何</a></li>
							<li class="sidebar-item"><a class="sidebar-link" href="#">常用技巧与算法</a></li>
						</ul> -->
					</li>



					<li class="sidebar-header">
						Profile & Settings
					</li>

					<li class="sidebar-item">
						<router-link class="sidebar-link" :to="{name: 'profile'}">
                            <i class="el-icon-s-custom"></i> <span class="align-middle">个人</span>
                        </router-link>
					</li>

				</ul>
			</div>
		</nav>

		<div class="main">
            <!-- 导航栏 -->
			<nav class="navbar navbar-expand navbar-light navbar-bg">

				<!-- 展开/收缩按钮 -->
				<a class="sidebar-toggle d-flex">
                    <i class="hamburger align-self-center"></i>
                </a>

				<!-- 搜索框 -->
				<form class="d-none d-sm-inline-block">
					<div class="input-group input-group-navbar">
						<input type="text" class="form-control" placeholder="Search…" aria-label="Search">
						<button class="btn" type="button">
                            <i class="align-middle" data-feather="search"></i>
                        </button>
					</div>
				</form>

				<div class="navbar-collapse collapse">
					<ul class="navbar-nav navbar-align" v-if="logined">
						
						<!-- 个人中心下拉框 -->
						<li class="nav-item dropdown">

							<a class="nav-link dropdown-toggle d-none d-sm-inline-block" href="#" data-toggle="dropdown">	
                            	<i class="el-icon-user"></i>                 
                                <!-- <img src="img/avatars/avatar.jpg" class="avatar img-fluid rounded mr-1" alt="Charles Hall" /> <span class="text-dark">Charles Hall</span> -->
                            </a>
							<div class="dropdown-menu dropdown-menu-right">

								<!-- 这里要改 -->
								<router-link class="dropdown-item" :to="{name: 'profile'}">Profile</router-link>
								<router-link class="dropdown-item" :to="{name: 'dispatch', params: {repoTag: 'All'}}">Repository</router-link>

								<div class="dropdown-divider"></div>

								<router-link class="dropdown-item" :to="{name: 'board'}">Painting</router-link>
								<router-link class="dropdown-item" :to="{name: 'write'}">Write something</router-link>

								<div class="dropdown-divider"></div>

								<el-button class="dropdown-item" @click="logout()">Log out</el-button>
							</div>
						</li>
					</ul>
					<ul class="navbar-nav navbar-align" v-else>
						<li><router-link :to="{name: 'login'}"><el-button type="primary" plain>登录</el-button></router-link></li>
					</ul>
				</div>
			</nav>
			
			<!-- 内容页 -->
			<main class="content">
				<router-view></router-view>
			</main>

            <!-- 页脚 -->
			<footer class="footer">
				<div class="container-fluid">
					<div class="row text-muted">
						<div class="col-6 text-left">
							<p class="mb-0">
								<a href="https://github.com/NorthBoat" class="text-muted"><strong>NorthBoat.github</strong></a> &copy;
							</p>
						</div>
						<div class="col-6 text-right">
							<ul class="list-inline">
								<li class="list-inline-item">
									<a class="text-muted" href="https://github.com/Juminiy">Support</a>
								</li>
								<li class="list-inline-item">
									<a class="text-muted" href="https://northboat.github.io/Blog/">Study Blog</a>
								</li>
								<li class="list-inline-item">
									<a class="text-muted" href="https://northboat.github.io/">EKeeper</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</footer>
		</div>
	</div>
        
</div>
</template>

<script>
import bootstrap from '../../static/js/app'
export default {
    name: 'Main',

    data() {
        return {
            logined: false,
            // active: ['sidebar-item', 'sidebar-item', 'sidebar-item', 'sidebar-item', 'sidebar-item']
        };
    },

    mounted() {
        var username = window.sessionStorage.getItem('loginUsername');
        if(username !== null){
            this.logined = true;
            console.log(username + " 已登录");
        }
    },

    methods: {
        // turnActive(num){
		// 	//console.log("进来了");
		// 	for(let i = 0; i < this.active.length; i++){
		// 		if(this.active[i] === 'sidebar-item active'){
		// 			this.active[i] = 'sidebar-item';
		// 		}
		// 	}
		// 	this.active[num] = 'sidebar-item active';
		// },
		logout(){
			//console.log("hahaha");
			window.sessionStorage.removeItem("loginUsername");
			window.location.reload();
		},
    },

	watch: {
		// '$route': function(){
		// 	//console.log(this.$route.name);
		// 	if(this.$route.name === 'hello'){
		// 		this.turnActive(0);
		// 	} else if(this.$route.name === 'repository'){
		// 		this.turnActive(1);
		// 	}
		// }
	}
};
</script>

<style lang="scss" scoped>
	[v-cloak] {
  		display: none;
	}
</style>