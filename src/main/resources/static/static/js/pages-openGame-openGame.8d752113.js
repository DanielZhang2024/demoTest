(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-openGame-openGame"],{"0682":function(t,e,i){var n=i("c86c"),a=i("2ec5"),o=i("a616");e=n(!1);var s=a(o);e.push([t.i,".content[data-v-bd4e45ba]{background-image:url("+s+");background-size:cover;background-repeat:no-repeat;width:100vw;height:100vh;color:#fff}.sizeimg[data-v-bd4e45ba]{width:100%;height:%?78?%;position:absolute;bottom:%?210?%;left:0}uni-video[data-v-bd4e45ba]{height:100vh;background-size:100% 100%;width:100vw;position:absolute;-webkit-filter:blur(0);filter:blur(0);top:0;left:0}uni-video[data-v-bd4e45ba]::-webkit-media-controls-fullscreen-button{display:none}uni-video[data-v-bd4e45ba]::-webkit-media-controls-play-button{display:none}uni-video[data-v-bd4e45ba]::-webkit-media-controls-timeline{display:none}uni-video[data-v-bd4e45ba]::-webkit-media-controls-current-time-display{display:none}uni-video[data-v-bd4e45ba]::-webkit-media-controls-time-remaining-display{display:none}uni-video[data-v-bd4e45ba]::-webkit-media-controls-mute-button{display:none}uni-video[data-v-bd4e45ba]::-webkit-media-controls-toggle-closed-captions-button{display:none}uni-video[data-v-bd4e45ba]::-webkit-media-controls-volume-slider{display:none}uni-video[data-v-bd4e45ba]::-webkit-media-controls-enclosure{display:none}uni-video[data-v-bd4e45ba]::-webkit-media-controls{display:none!important}.onLogin[data-v-bd4e45ba]{height:%?120?%;line-height:%?120?%;border-radius:%?10?%;width:25%;font-size:%?40?%;margin-top:%?60?%;background-color:#11111a;margin-left:38%;color:#fff}.container[data-v-bd4e45ba]{position:relative;width:50%;height:90vh;margin-left:25%}.image-container[data-v-bd4e45ba]{position:absolute;top:0;left:0;width:200px;height:200px;transition:left 3s linear}.progress-bar[data-v-bd4e45ba]{position:absolute;bottom:0;left:0;height:8px;border-radius:%?15?%;background-color:#ff00d8;transition:width 3s linear,box-shadow 3s linear}",""]),t.exports=e},"179e":function(t,e,i){"use strict";i.r(e);var n=i("df0e"),a=i.n(n);for(var o in n)["default"].indexOf(o)<0&&function(t){i.d(e,t,(function(){return n[t]}))}(o);e["default"]=a.a},"17c4":function(t,e,i){t.exports=i.p+"assets/Comp_3.7129cc80.gif"},"2ec5":function(t,e,i){"use strict";t.exports=function(t,e){return e||(e={}),t=t&&t.__esModule?t.default:t,"string"!==typeof t?t:(/^['"].*['"]$/.test(t)&&(t=t.slice(1,-1)),e.hash&&(t+=e.hash),/["'() \t\n]/.test(t)||e.needQuotes?'"'.concat(t.replace(/"/g,'\\"').replace(/\n/g,"\\n"),'"'):t)}},"40df":function(t,e,i){t.exports=i.p+"static/indexSize.png"},"912a":function(t,e,i){"use strict";i.r(e);var n=i("a367"),a=i("179e");for(var o in a)["default"].indexOf(o)<0&&function(t){i.d(e,t,(function(){return a[t]}))}(o);i("d7ab");var s=i("828b"),r=Object(s["a"])(a["default"],n["b"],n["c"],!1,null,"bd4e45ba",null,!1,n["a"],void 0);e["default"]=r.exports},a367:function(t,e,i){"use strict";i.d(e,"b",(function(){return n})),i.d(e,"c",(function(){return a})),i.d(e,"a",(function(){}));var n=function(){var t=this.$createElement,e=this._self._c||t;return e("v-uni-view",{staticClass:"content"},[e("v-uni-view",[e("v-uni-image",{staticClass:"sizeimg",attrs:{src:i("40df"),mode:"aspectFit"}}),e("v-uni-view",{staticClass:"container"},[e("v-uni-view",{staticClass:"image-container",style:{left:this.progress+"%"}},[e("v-uni-image",{staticStyle:{width:"360rpx",height:"360rpx","margin-left":"-180rpx","padding-top":"70vh"},attrs:{src:i("17c4"),mode:"aspectFit"}}),this._v(this._s(this.count)+"%")],1),e("v-uni-view",{staticClass:"progress-bar",style:{width:this.progress+"%",boxShadow:"0 0 "+this.progress/6+"px 0 rgba(255, 0, 0, 0.5)"}})],1)],1)],1)},a=[]},a616:function(t,e,i){t.exports=i.p+"assets/indexImage.833bb10a.png"},d7ab:function(t,e,i){"use strict";var n=i("e8b9"),a=i.n(n);a.a},df0e:function(t,e,i){"use strict";i("6a54");var n=i("f5bd").default;Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var a=n(i("ff65")),o=n(i("c430")),s={components:{piaoyiProgressBar:a.default,fuiInput:o.default},data:function(){return{title:"Hello",islogin:!0,progress:0,timer:null,timers:null,count:0,startTime:null,duration:3e3}},onLoad:function(){this.startAnimation(),this.startCountdown()},methods:{gologin:function(){this.islogin=!1},onclickLogin:function(){uni.redirectTo({url:"/pages/OA/oa"})},startAnimation:function(){var t=this;this.timer=setInterval((function(){t.progress+=3.33,t.progress>=100&&clearInterval(t.timer)}))},startCountdown:function(){var t=this;this.timers||(this.startTime=Date.now(),this.timers=setInterval((function(){var e=Date.now()-t.startTime,i=Math.min(e/t.duration,1);t.count=Math.round(100*i),e>=t.duration&&(t.count=100,t.stopCountdown())}),16))},stopCountdown:function(){this.timer&&(clearInterval(this.timers),this.timers=null),setTimeout((function(){uni.navigateTo({url:"/pages/goodsHi/goodsHi"})}),1e3)}}};e.default=s},e8b9:function(t,e,i){var n=i("0682");n.__esModule&&(n=n.default),"string"===typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);var a=i("967d").default;a("8710f8a4",n,!0,{sourceMap:!1,shadowMode:!1})}}]);