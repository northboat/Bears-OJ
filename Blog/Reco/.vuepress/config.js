module.exports = {
  "title": "NorthBoat",
  "description": "留住一条小尾巴",
  "dest": "public",
  "base": '/',
  "port": '7777',
  "head": [
    [ "link", { "rel": "icon", "href": "/favicon.ico" }],
    [ "meta", { "name": "viewport", "content": "width=device-width,initial-scale=1,user-scalable=no" }],
    ['link', {rel:'stylesheet', href:'https://cdn.jsdelivr.net/npm/katex@0.15.1/dist/katex.min.css'}],
    //['link', {rel:'stylesheet', href:'https://gitcdn.xyz/cdn/goessner/markdown-it-texmath/master/texmath.css'}],
    //['script', {src: 'https://github.com/markdown-it/markdown-it/blob/master/bin/markdown-it.js'}],
    //['script', {src: 'https://gitcdn.xyz/cdn/goessner/markdown-it-texmath/master/texmath.js'}],
    ['script', {src: 'https://cdn.jsdelivr.net/npm/katex@0.15.1/dist/katex.min.js'}],

  ],
  "theme": "reco",
  "themeConfig": {
    "nav": require("./nav.js"),
    "sidebar": require("./sidebar.js"),
    "subSidebar": 'auto',//在所有页面中启用自动生成子侧边栏，原 sidebar 仍然兼容
    "type": "blog",
    "blogConfig": require("./blogConfig.js"),
    "friendLink": require("./frientLink.js"),
    "logo": "/logo.jpg",
    "search": true,
    "searchMaxSuggestions": 7,
    "lastUpdated": "Last Updated",
    "author": "北舟",
    "authorAvatar": "/avatar.png",
    "record": "舟桐",
    "startYear": "2020",
    "lastUpdated": "Last Updated"
  },
  "markdown": {
    "lineNumbers": true
  },
  "plugins": require('./plugins.js'),
  "markdown": {
    "lineNumbers": true,
    "anchor": { permalink: false },
    "toc": {includeLevel: [1,2]},
    "extendMarkdown": md => {
      md.use(require('markdown-it-texmath'))
    }
  }

}

    

