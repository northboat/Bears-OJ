//主管控导航栏

module.exports = [
	
    {
        text: "Home",
        link: "/",
        "icon": "reco-home"
    },
	
    {
        text: 'Developer',
		"icon": "reco-other",
		items: [
            {text: 'C/C++', link: '/docs/developer/c/'},
            {text: 'Java', link: '/docs/developer/java/'},
			{text: 'Front End', link: '/docs/developer/frontend/'},
			{text: 'DevOps', link: '/docs/developer/devops/'},				
        ]
    },	
    {
	text: 'Major',
	"icon": "reco-npm",
	items: [
	    {text: 'Data Struct', link: '/docs/major/datastruct/',},
	    {text: 'Operating System',  link: '/docs/major/operating/',},
	    {text: 'Network', link: '/docs/major/network/',},
	    {text: 'Organization', link: '/docs/major/organization/',},
	]
    },
			

	
    {
	text: 'DataScience', 
	"icon": "reco-eye",
	items: [
	    {text: 'Mathematics', link: '/docs/ai/math/'},	
	    {text: 'Python', link: '/docs/ai/python/'},
	    {text: 'Machine Learning', link: '/docs/ai/ml/'},
	    //{text: 'Deep Learning', link: '/'},			
	]
    },
			
	
    {
        text: "TimeLine",
        link: "/timeline/",
        "icon": "reco-date"
    },
	

]
