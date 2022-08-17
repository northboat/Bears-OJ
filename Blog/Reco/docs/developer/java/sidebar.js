//java的侧边栏
module.exports = [
	{
		title: 'JAVA 基础',
		collapsable: true,
		children: [
			'basic/class',
			'basic/collection',
			'basic/io',
		]
	},
	
	{
		title: 'WEB 基础',
		collapsable: true,
		children: [
			'web/tomcat',
			'web/maven',
			'web/servlet',
			'web/session',
		]
	},
	
	{
		title: '项目',
		collapsable: true,
		children: [
			'project/helper',
			'project/shutdown',
			'project/performance',
			'project/post',
			'project/controller',
			'project/oj',
			'project/seckill'
		]
	},
	
	{
		title: 'JUC 编程及池化技术',
		collapsable: true,
		children: [
			'juc/lock',
			'juc/pool',
			'juc/fork',
			'juc/single',
		]
	},
	
	{
		title: '框架',
		collapsable: true,
		children: [
			'frame/mybatis',
			'frame/springboot',
		]
	},
	
	
]
