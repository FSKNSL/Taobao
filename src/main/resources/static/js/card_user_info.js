function check_phone(phone){

    var re = /^1[0-9]{10}$/;

    if(re.test(phone.val()))
    {
        phone.next().hide();
        $(".bt-mdify-ok").attr("disabled", false)
    }
    else
    {
        phone.next().html('温馨提示: 格式不正确,请输入1开头的11位电话号码')
        phone.next().show();
        $(".bt-mdify-ok").attr("disabled", true)
    }

}

$(document).ready(function (){


    /* 修改窗口，联系方式提示输入正确的格式*/
    $(".input-phone").blur(function (){
        check_phone($(this))
    });

    $(".bt-mdify-ok").click(function (){
        if(( $(".input-phone").val()).length != 11) {
            alert('请输入11位的联系电话')
            return ;
        } else{
            $.ajax({
                type: 'POST',
                url: 'toUpdate?phone=' + $(".input-phone").val(),
                async: false,
                error: function (data){
                    alert('访问servlet失败 ' + data)
                },
                success: function (data){
                    if(data == "true"){
                        alert('修改成功!')
                    } else{
                        alert('修改失败.')
                    }
                    window.location.href='/card_user_info.jsp'
                }
            })
        }
    });

});