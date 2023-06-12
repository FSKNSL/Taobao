$(document).ready(function (){
    var dcid = 0        // 记录需删除的订单ID
    /** 点击删除订单按钮后 **/
    $("#bt-delete-order").click(function (){
        dcid = $(this).data("cid")
    });
    /** 点击确认删除订单按钮后 **/
    $("#btn-modal-delete").click(function (){
        $.ajax({
            url: '/deleteOrder?cid='+dcid,
            type: 'GET',
            error: function (data){
                alert("AJAX请求错误")
            },
            success: function (res){
                if(res == "true"){
                    $("#btn-to-modal-delete").click()
                } else{
                    alert("删除失败!")
                }
            }

        })
    })
});
