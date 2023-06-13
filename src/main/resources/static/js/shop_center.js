$(document).ready(function (){
    var cid = 0
    var uid = 0
    $(".card-commodity").click(function (){
       cid = $(this).data("cid")
       uid = $(this).data("uid");
       nowuid = $(this).data("nowuid")
        // 判断是否为用户自己的商品
        if(uid != nowuid) {
            // 埋点记录
            $.ajax({
                url: '/visted?cid=' + cid + "&uid=" + uid,
                type: "GET",
                success: function (data) {
                }
            });
            $("#bt-buy").click();
        } else $("#btn-buy-no").click()
        });
    $(".btn-commodity-type").click(function (){
        window.location.href=$(this).attr("href");
    });
    $("#btn-buy-ok").click(function (){
        //确认购买后，添加商品订单
       $.ajax({
           url: '/addOrder?cid=' + cid,
           type: "POST",
           success: function (data){
               $("#btn-buy-cancel").click();
           }
       });
    });
});