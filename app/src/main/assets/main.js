function connectWebViewJavascriptBridge(callback) {
  if (window.WebViewJavascriptBridge) {
      callback(WebViewJavascriptBridge)
  } else {
      document.addEventListener(
          'WebViewJavascriptBridgeReady',
          function () {
              callback(WebViewJavascriptBridge)
          },
          false
      );
  }
}
connectWebViewJavascriptBridge(function (bridge) {
  bridge.init(function (message, responseCallback) {
      responseCallback(data);
  });
  bridge.registerHandler("functionInJs", function (data, responseCallback) {
      responseCallback(data);
  });
   bridge.registerHandler("functionIn123", function(data, responseCallback) {
         document.getElementById("show").innerHTML = ("data from Java: = " + data);
         var responseData = "Javascript Says Right back aka!";
         responseCallback(responseData);
         });
})



