(self.webpackChunkplenum=self.webpackChunkplenum||[]).push([[713,994],{4713:(e,t,n)=>{"use strict";n.d(t,{Z:()=>u});var r=n(7294),l=n(5538);const a=function(e){var t={border:"1px solid black",width:"25px",height:"25px",textAlign:"center",outline:"none"};"A"===e.unavailable&&(t.backgroundColor="red"),void 0!==e.opacity&&(t.backgroundColor="hsla(120, 50%, 50%, ".concat(e.opacity));var n=void 0!==e.users_avail&&e.users_avail.length?r.createElement(l.Z,{id:e.tooltip_id,place:"top",effect:"solid"},r.createElement("ul",{style:{margin:"0",padding:"0"}},e.users_avail.map((function(e){return r.createElement("li",{key:e},e)})))):null;return r.createElement("td",null,r.createElement("button",{style:t,className:"day"+e.day,value:e.time,"data-tip":!0,"data-for":e.tooltip_id}),n)};function o(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}const i=function(e){var t,n,l=(t=(0,r.useState)("A"===e.unavailable),n=2,function(e){if(Array.isArray(e))return e}(t)||function(e,t){if("undefined"!=typeof Symbol&&Symbol.iterator in Object(e)){var n=[],r=!0,l=!1,a=void 0;try{for(var o,i=e[Symbol.iterator]();!(r=(o=i.next()).done)&&(n.push(o.value),!t||n.length!==t);r=!0);}catch(e){l=!0,a=e}finally{try{r||null==i.return||i.return()}finally{if(l)throw a}}return n}}(t,n)||function(e,t){if(e){if("string"==typeof e)return o(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?o(e,t):void 0}}(t,n)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()),a=l[0],i=l[1],u=(0,r.useRef)(!0),c={border:"1px solid black",width:"25px",height:"25px",textAlign:"center",outline:"none"},d=function(){};return void 0!==e.onAvailChange&&(d=e.onAvailChange(e.day,e.time,a)),(0,r.useEffect)((function(){u.current?u.current=!1:d()}),[a]),a&&(c.backgroundColor="red"),r.createElement("td",null,r.createElement("button",{onClick:function(){i(!a)},style:c,className:"day"+e.day,value:a?e.time:-1}))},u=function(e){for(var t=e.file,n=new Array(12),l=0;l<n.length;l++)n[l]=new Array(7);for(var o=0;o<12;o++)for(var u=0;u<7;u++)n[o][u]="U";if(void 0!==t&&void 0!==t.dates){console.log("test");for(var c=t.dates,d=0;d<c.length;d++)for(var m=0;m<c[d].times.length;m++)n[c[d].times[m]][c[d].date]="A"}return console.log(n),r.createElement("table",null,r.createElement("thead",null,r.createElement("tr",null,r.createElement("th",null),r.createElement("th",null,"Su"),r.createElement("th",null,"M"),r.createElement("th",null,"T"),r.createElement("th",null,"W"),r.createElement("th",null,"Th"),r.createElement("th",null,"F"),r.createElement("th",null,"S"))),r.createElement("tbody",null,n.map((function(t,n){return r.createElement("tr",{key:n},r.createElement("td",{style:{textAlign:"right"}},0===(l=n)||6===l?12:2*l%12),void 0!==e.editable&&!0===e.editable?t.map((function(t,l){return r.createElement(i,{key:l,onAvailChange:e.onAvailChange,unavailable:t,time:n,day:l})})):t.map((function(e,t){return r.createElement(a,{key:t,unavailable:e,time:n,day:t})})));var l}))))}}}]);
//# sourceMappingURL=713.js.map