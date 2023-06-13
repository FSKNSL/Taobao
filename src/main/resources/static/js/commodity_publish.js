$(document).ready(function (){
    // $("#form-publish-img").on(function (){
    // 上传图片
    $("#form-publish-img").bind("input propertychange",function(event){
        var reads = new FileReader();
        var f = $(this).get(0).files[0];
        var rep = /jpeg|png|gif|bmp|jpg/ig;
        var gstyle = f.type.split("/")[1];

        if(rep.test(gstyle)){
             reads.readAsDataURL(f);
             reads.onload = function(e) {
                     $("#form-publish-img-show").attr("src",this.result)
                 };
        }else{
             alert("图片格式不正确，请上传 jpeg|png|gif|bmp 格式的图片")
        }
    })

    $("#form-button").click(function (){

        $("#form-publish").ajaxSubmit({
            //?commodityName=' + $("#input-commodity-name").val()
            type: 'post',
            enctype: 'multipart/form-data',
            url: '/publish',
            success: function (data){
                alert('提交成功!')
                window.location.href="/commodity_publish.jsp"
            }
        })
    });
});
