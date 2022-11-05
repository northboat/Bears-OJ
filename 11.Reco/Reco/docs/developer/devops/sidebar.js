
module.exports = [
	{
		title: 'LINUX',
		collapsable: true,
		children: [
			'linux/server',
			'linux/manjaro',
		]
	},
	
	{
		title: '工具和中间件',
		collapsable: true,
		children: [
		    {
			title: '工具',
			collapsable: false,
			children: [
			    'tool&mid/git',		
			]
		    },
		    {
			title: '中间件',
			collapsable: false,
			children: [
			    'tool&mid/dockerI',
			    'tool&mid/dockerII',	
			]
		    },		
		]
	},

	{
		title: '杂项',
		collapsable: true,
		children: [
			'misc/wechall',
		]
	},
	
]
