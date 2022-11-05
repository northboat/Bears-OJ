//主管控导航栏

module.exports = [

	//{ text: '啊哈', link: '/guide/', },
	//{ text: 'Toys', link: '/toys/', },
	
	
	{ 
		text: 'Mine', 
		items: [
			{text: 'Notes', link: '/mine/notes/'},
            {text: 'Project', link: '/mine/project/'},
		], 
	},
	
	
	{
		text: 'Professional Courses',
		items: [
			{text: 'Compilation', link: '/course/compilation/'},	
			{text: 'Operating System',  link: '/course/os/',},
			{text: 'Network', link: '/course/net/',},
			{text: 'Datastruct', link: '/course/datastruct/',},
			{text: 'Data Base', link: '/course/db/',},
			{text: 'Organization', link: '/course/organization/',},
		]
	},
			
			
    {
        text: 'Programming',
		items: [
            {text: 'C++', link: '/programming/c++/'},
            {text: 'Java', link: '/programming/java/'},
			{text: 'Front End', link: '/programming/front/'},
			{text: 'Linux', link: '/programming/linux/'},
			{text: 'Golang', link: '/programming/golang/'},
        ]
    },
	
	
	
	{
		text: 'Tools',
		items: [		
			{text: 'Git', link: '/tools/git/'},
			{text: 'Docker', link: '/tools/docker/'},
			{text: 'API', link: '/tools/api/'},	
			{text: 'Java Tool', link: '/tools/java/'},	
		]
	},

	
	{
		text: 'Artificial Intelligence', 
		items: [
			{text: 'Course', link: '/ai/course/'},
			{text: 'Python', link: '/ai/python/'},
		]
	},
			
	

]
