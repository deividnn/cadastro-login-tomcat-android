/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function verificaNumero(e) {
    if (e.which !== 8 && e.which !== 0 && (e.which < 48 || e.which > 57)) {
        return false;
    }
}

function removerDesabilitados() {

    $("input").removeClass("ui-state-disabled");
    $("span").removeClass("ui-state-disabled");
    $("div").removeClass("ui-state-disabled");
    $("textarea").removeClass("ui-state-disabled");
    $(".numero").keypress(verificaNumero);
    $(".upper").blur(function () {
        var id = jQuery(this).attr("id");
        document.getElementById(id).value = document.getElementById(id).value.toUpperCase();

    });

    $(".upper").keypress(function () {
        $('.upper').css('text-transform', 'uppercase');
    });

    $('.telefone').focusout(function () {

        var phone, element;
        element = $(this);
        element.unmask();
        phone = element.val().replace(/\D/g, '');
        if (phone.length > 10) {
            element.mask("(99) 99999-999?9");
        } else {
            element.mask("(99) 9999-9999?9");
        }
    }).trigger('focusout');

}

$(document).bind("keydown", function (e) {
    e = e || window.event;
    var key = e.which || e.charCode || e.keyCode;

    if ($(".criar").is(':disabled') === false) {
        if ((key === 117)) {
            e.preventDefault();
            $(".criar").click();
        }
    }

    if ($(".editar").is(':disabled') === false) {
        if ((key === 118)) {
            e.preventDefault();
            $(".editar").click();
        }
    }

    if ($(".ver").is(':disabled') === false) {
        if ((key === 119)) {
            e.preventDefault();
            $(".ver").click();
        }
    }

    if ($(".revisoes").is(':disabled') === false) {
        if ((key === 120)) {
            e.preventDefault();
            $(".revisoes").click();
        }
    }
    if ($(".deletar").is(':disabled') === false) {
        if ((key === 121)) {
            e.preventDefault();
            $(".deletar").click();
        }
    }
});


