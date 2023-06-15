
$(function () {
    // 删除单条
    $('.btn_delete').post({
        confirm: '<div class="alert alert-danger">确定要将该产品移出购物车吗？</div>',
        success: function (r) {
            $$.toast(r, function () {
                location.reload();
            });
        }
    });

    // 点击单个复选框
    $('.check_single').click(function () {
        $('#record_total').click();
    })

    $('#record_total').click(function () {
        var checkTotal = []; //定义一个空数组
        $("input[name='check_single']:checked").not('.failure').each(function (i) { //把所有被选中的复选框的值存入数组
            checkTotal[i] = $(this).data('price');
        });

        // 求数组和
        function sum(arr) {
            return eval(arr.join("+"));
        };

        // 总价
        var total_price = sum(checkTotal);
        if (checkTotal.length == 0) {
            $('.total_price').text('¥' + 0.00);
        } else {
            $('.total_price').text('¥' + sum(checkTotal));
        }

        // 已选应用
        $('.check_num').text(checkTotal.length);
    })

    // 全选
    $('.check_all').click(function () {
        if ($(this).is(':checked')) {
            $('input[name="check_single"]').not('.failure').each(function () {
                $(this).prop("checked", true);
            });
            $('#record_total').click();
        } else {
            $('input[name="check_single"]').not('.failure').each(function () {
                $(this).prop("checked", false);
            });
            $('#record_total').click();
        }
    })
});

