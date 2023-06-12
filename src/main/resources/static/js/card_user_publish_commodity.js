$(document).ready(function (){

    $("#bt-delete-commodity").click(function (){
        $.ajax({
            url: '/deleteCommodity?cid=' + $(this).data("cid"),
            type: 'GET',
            error: function (data){
                alert("提交请求失败!")
            },
            success: function (res){
                if(res == "true"){
                    alert("删除成功！")
                } else alert("删除失败.")
                window.location.href="/queryPublish"
            }
        })

    })

});