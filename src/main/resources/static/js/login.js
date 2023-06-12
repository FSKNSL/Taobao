$(document).ready(function (){
   $("#login").click(function (){
       var username = $("#un").val();
       var password = $("#pw").val();
       $.ajax({
          type: 'POST',
          url: "/User2/login?user_id="+username +
              "&user_pwd=" + password,
          async: false,
          success: function (result){
              if(result != "true") {
                  $("#modal-error-info").html(result);
                  $("#btn-modal-error").click();
              }
              else window.location.href = "/User2/login";
          }
      });
   });
});