
module.exports = [
	{
		title: '算法',
		collapsable: true,
		children: [
			'algorithm/one',
			'algorithm/two',
			'algorithm/summary',
		]
	},
	
	{
		title: '数据结构',
		collapsable: true,
		children: [
			'basic/struct',
			'basic/algorithm',
			'basic/leetcode',
		]
	},
	
	{
		title: '数据库',
		collapsable: true,
		children: [
		    {
			title: '系统概论',
			collapsable: false,
			children: [
			    'database/relation',
			    'database/sql',	
			    'database/design',
			]
		    },
		    {
			title: '应用',
			collapsable: false,
			children: [
			    'database/mysql',
			    'database/redis',
			]
		    },
				
						
		]
	},
]
