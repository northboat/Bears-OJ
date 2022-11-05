module.exports = [
    [
      '@vuepress-reco/vuepress-plugin-bgm-player',
      {
        "audios": [
          {
            name: '我',
            artist: '张国荣',
            url: 'http://www.ytmp3.cn/down/46480.mp3',
            cover: '/reco/zhang&tang.jpg'
          },
	  {
	    name: 'You Are Beautiful',
            artist: 'James Blunt',
	    url: 'http://www.ytmp3.cn/down/77296.mp3',
	    cover: '/reco/wangfangfang.jpg'
	  },
	  {
	    name: '遥远的他',
	    artist: '陈奕迅',
	    url: 'http://www.ytmp3.cn/down/64842.mp3',
	    cover: '/reco/road.jpg'
	  },
	  {
	    name: '冤气',
	    artist: '陈奕迅',
	    url: '/bgm/冤气-陈奕迅.flac',
	    cover: '/reco/gufeng.jpg'
	  },
	  {
	    name: '最冷一天',
	    artist: '陈奕迅',
	    url: 'http://www.ytmp3.cn/down/64370.mp3',
	    cover: '/reco/20201213.jpg'
	  },
	  {
	    name: '倾城',
	    artist: '陈奕迅',
	    url: 'http://www.ytmp3.cn/down/64402.mp3',
	    cover: '/reco/20210504.jpg'
	  },
	  {
	    name: '让一切随风',
	    artist: '钟镇涛',
	    url: 'http://www.ytmp3.cn/down/74929.mp3',
	    cover: '/reco/kelamayi.jpg'
	  },
	  {
	    name: '唯一',
	    artist: '告五人',
	    url: 'http://www.ytmp3.cn/down/75603.mp3',
	    cover: '/reco/20200727.jpg'
	  },
	  {
	    name: '失忆蝴蝶',
	    artist: '陈奕迅',
	    url: 'http://www.ytmp3.cn/down/52174.mp3',
	    cover: '/reco/20210830.jpg'
	  },
        ],
        // 是否默认缩小
        "autoShrink": true ,
        // 缩小时缩为哪种模式
        "shrinkMode": 'float',
	// 悬浮方位
	"floatPosition": 'left',
        // 悬浮窗样式
        "floatStyle":{ "bottom": "44px", "z-index": "999999" },
	//"position": { left: '10px', bottom: '0px', 'z-index': "999999" }
      }
    ],
    [
      "vuepress-plugin-nuggets-style-copy", {
      copyText: "复制代码",
      tip: {
          content: "复制成功!"
      }
    }]
]
