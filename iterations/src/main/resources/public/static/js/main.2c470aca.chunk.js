(this["webpackJsonptemplate-view"]=this["webpackJsonptemplate-view"]||[]).push([[0],{12:function(e,t,n){"use strict";n.r(t);var a=n(0),l=n.n(a),r=n(4),c=n.n(r),o=n(6),u=function(e){var t=Object(a.useState)("A"===e.unavailable),n=Object(o.a)(t,2),r=n[0],c=n[1],u={border:"1px solid black",width:"25px",height:"25px",textAlign:"center",outline:"none"};return r||(u.backgroundColor="red"),l.a.createElement("td",null,l.a.createElement("button",{onClick:function(){c(!r)},key:e.key,style:u,className:"day"+e.day,value:r?e.time:-1}))},i=function(e){for(var t=e.file,n=new Array(12),a=0;a<n.length;a++)n[a]=new Array(7);for(var r=0;r<12;r++)for(var c=0;c<7;c++)n[r][c]="A";if(void 0!==t)for(var o=0;o<t.length;o++)for(var i=0;i<t[o].times.length;i++)n[t[o].times[i]][t[o].date]="U";return console.log(n),l.a.createElement("table",null,l.a.createElement("thead",null,l.a.createElement("tr",null,l.a.createElement("th",null),l.a.createElement("th",null,"Su"),l.a.createElement("th",null,"M"),l.a.createElement("th",null,"T"),l.a.createElement("th",null,"W"),l.a.createElement("th",null,"Th"),l.a.createElement("th",null,"F"),l.a.createElement("th",null,"S"))),l.a.createElement("tbody",null,n.map((function(e,t){return l.a.createElement("tr",{key:t},l.a.createElement("td",{style:{textAlign:"right"}},0===(n=t)||6===n?12:2*n%12),e.map((function(e,n){return l.a.createElement(u,{key:n,unavailable:e,time:t,day:n})})));var n}))))},m=n(5);function d(){for(var e={dates:[]},t=function(t){var n=Object(m.a)(document.getElementsByClassName("day"+t)),a=[];n.forEach((function(e){if(e.checked){var t=parseInt(e.value);-1!==t&&a.push(t)}})),0!==a.length&&e.dates.push({date:t,times:a})},n=0;n<7;n++)t(n);return e}var s=function(){var e;e=d(),fetch("/addcalendar?title=test",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(e)}).then((function(e){console.log(e)}))},h=function(){return l.a.createElement("div",null,l.a.createElement(i,null),l.a.createElement("div",{className:"form-example"},l.a.createElement("label",{htmlFor:"event"},"Enter your event id: "),l.a.createElement("input",{type:"text",name:"event",id:"event"})),l.a.createElement("button",{onClick:s},"Upload template!"))};c.a.render(l.a.createElement(h,null),document.getElementById("root"))},7:function(e,t,n){e.exports=n(12)}},[[7,1,2]]]);
//# sourceMappingURL=main.2c470aca.chunk.js.map