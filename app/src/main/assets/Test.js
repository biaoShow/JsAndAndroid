function jsMethod(){
 //利用对话框返回的值 （true 或者 false）
    if (confirm("你确定提交吗？")) {
        alert("点击了确定");
    }else{
        alert("点击了取消");
    }
}
function jsMethod1(msg){
    alert("to android data:"+msg);
    if (confirm("返回数据?")) {
            window.Test.setString("123")
             return 123;
        }else{
             window.Test.setString("null")
            return "null";
        }
}
function getAndroidData(){
   alert(window.Test.getString());
}
function setAndroidData(){
    window.Test.setString("js data")
}
function jsToAndroidMethod(){
    window.Test.showAndroid();
}
function jsToAndroidMethod1(){
     window.WebViewJavascriptBridge.callHandler(
                   'submitFromWeb'
                   , {'Data': 'json数据传给Android端'}  //该类型是任意类型
                   , function(responseData) {
                       document.getElementById("show").innerHTML = "得到Java传过来的数据 data = " + responseData
                   }
               );
}

