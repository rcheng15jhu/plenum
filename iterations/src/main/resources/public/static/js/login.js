(()=>{"use strict";var e={2271:(e,t,r)=>{var n=r(7294),a=r(3935),o=r(2427),i=r(3832),l=r(9803),c=r(2318),s=r(1749),m=r(282),u=r(3457),d=r(8878),p=r(9791),f=r(5579);function g(e,t){var r=document.createElement("alert");a.render(n.createElement(f.Z,{severity:t,msg:e}),r),document.getElementById("content").appendChild(r)}function v(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function y(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?v(Object(r),!0).forEach((function(t){h(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):v(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function h(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function b(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}var w=(0,o.Z)((function(e){return{root:{"& > *":{display:"flex",flexDirection:"column",alignItems:"center",justifyContent:"center"}},margin:{margin:e.spacing(1)},center:{display:"flex",alignItems:"center",justifyContent:"center"},contentDiv:{margin:"10px auto",padding:"50px 0",width:"50%",border:"2px solid black",borderRadius:"10"},button:{marginTop:"30px"},text:{marginBottom:"20px"},home:{position:"fixed"}}})),E=function(){var e,t,r=w(),a=(e=n.useState({username:"",password:"",confirm:""}),t=2,function(e){if(Array.isArray(e))return e}(e)||function(e,t){if("undefined"!=typeof Symbol&&Symbol.iterator in Object(e)){var r=[],n=!0,a=!1,o=void 0;try{for(var i,l=e[Symbol.iterator]();!(n=(i=l.next()).done)&&(r.push(i.value),!t||r.length!==t);n=!0);}catch(e){a=!0,o=e}finally{try{n||null==l.return||l.return()}finally{if(a)throw o}}return r}}(e,t)||function(e,t){if(e){if("string"==typeof e)return b(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?b(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()),o=a[0],f=a[1],v=[{value:"Login",clicked:function(){!function(e){""!==e.username.normalize()&&""!==e.password.normalize()?fetch("/api/login?username="+e.username.normalize()+"&password="+e.password.normalize(),{method:"POST",mode:"cors"}).then((function(t){console.log(t),401===t.status?g("Incorrect login information!","error"):200===t.status&&(g("Successfully logged in!","success"),document.cookie="username="+e.username.normalize()+"; path=/;",window.location.assign("/profile"))})):g("Username and password cannot be blank!","error")}(o)}},{value:"Sign-up",clicked:function(){!function(e){""!==e.username.normalize()&&""!==e.password.normalize()?e.password.length>40?g("Please limit your password to 40 characters.","error"):e.confirm.normalize()==e.password.normalize()?fetch("/api/adduser?username="+e.username.normalize()+"&password="+e.password.normalize(),{method:"POST",mode:"cors"}).then((function(t){console.log(t),401===t.status?g("Username taken!","error"):200===t.status&&(g("Successfully signed up!","success"),document.cookie="username="+e.username.normalize()+"; path=/;",window.location.assign("/profile"))})):g("Confirm password is not equal to password!","error"):g("Username and password cannot be blank!","error")}(o)}}],E=function(e){f(y(y({},o),{},h({},e.target.name,e.target.value)))};return n.createElement(u.Z,{theme:d.Z},n.createElement(c.Z,{variant:"h6",className:r.home,id:"content"},n.createElement(m.Z,{variant:"contained",size:"large",href:"/",color:"primary"},n.createElement(p.Z,{style:{marginRight:"5px"}}),"Plenum")),n.createElement(i.Z,{className:r.root},v.map((function(e){return n.createElement("div",{key:e.value,className:r.contentDiv},"Sign-up"===e.value?n.createElement(c.Z,{variant:"h5",color:"secondary",className:r.text},"Don't have an account?"):n.createElement("div",null),n.createElement(c.Z,{component:"h4",variant:"h4",className:r.title},e.value),n.createElement("div",{className:r.margin},n.createElement(s.Z,{container:!0,spacing:1,alignItems:"flex-end"},n.createElement(s.Z,{item:!0},n.createElement(c.Z,{variant:"h6"},"Username:")),n.createElement(s.Z,{item:!0},n.createElement(l.Z,{name:"username",onChange:E,required:!0,label:"required"})))),n.createElement("div",{className:r.margin},n.createElement(s.Z,{container:!0,spacing:1,alignItems:"flex-end"},n.createElement(s.Z,{item:!0},n.createElement(c.Z,{variant:"h6"},"Password:")),n.createElement(s.Z,{item:!0},n.createElement(l.Z,{name:"password",type:"password",onChange:E,required:!0,label:"required"})))),n.createElement("div",{className:r.margin},"Sign-up"===e.value?n.createElement("div",null,n.createElement(s.Z,{item:!0},n.createElement(c.Z,{variant:"h6"},"Confirm password:")),n.createElement(s.Z,{item:!0},n.createElement(l.Z,{name:"confirm",type:"password",onChange:E,required:!0,label:"required"}))):n.createElement("div",null)),n.createElement("div",{className:r.center},n.createElement(m.Z,{variant:"contained",color:"secondary",className:r.button,onClick:e.clicked,id:"".concat(r.button,"button")},e.value)))}))))};a.render(n.createElement(E,null),document.getElementById("root"))}},t={};function r(n){if(t[n])return t[n].exports;var a=t[n]={exports:{}};return e[n](a,a.exports,r),a.exports}r.m=e,r.n=e=>{var t=e&&e.__esModule?()=>e.default:()=>e;return r.d(t,{a:t}),t},r.d=(e,t)=>{for(var n in t)r.o(t,n)&&!r.o(e,n)&&Object.defineProperty(e,n,{enumerable:!0,get:t[n]})},r.o=(e,t)=>Object.prototype.hasOwnProperty.call(e,t),r.r=e=>{"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},r.j=535,(()=>{var e={535:0},t=[[2271,851,638,643,715,749,434,471,35,779,878,579]],n=()=>{};function a(){for(var n,a=0;a<t.length;a++){for(var o=t[a],i=!0,l=1;l<o.length;l++){var c=o[l];0!==e[c]&&(i=!1)}i&&(t.splice(a--,1),n=r(r.s=o[0]))}return 0===t.length&&(r.x(),r.x=()=>{}),n}r.x=()=>{r.x=()=>{},i=i.slice();for(var e=0;e<i.length;e++)o(i[e]);return(n=a)()};var o=a=>{for(var o,i,[c,s,m,u]=a,d=0,p=[];d<c.length;d++)i=c[d],r.o(e,i)&&e[i]&&p.push(e[i][0]),e[i]=0;for(o in s)r.o(s,o)&&(r.m[o]=s[o]);for(m&&m(r),l(a);p.length;)p.shift()();return u&&t.push.apply(t,u),n()},i=self.webpackChunkplenum=self.webpackChunkplenum||[],l=i.push.bind(i);i.push=o})(),r.x()})();
//# sourceMappingURL=login.js.map