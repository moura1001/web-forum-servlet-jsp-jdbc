$(document).ready(function () {
    $("#conteudo").keyup(validateTextarea);

    function validateTextarea() {
        const errorMsg = "Conteúdo inválido. Deve ser um texto de tamanho [1, 2048] sem caracteres especiais";
        const textarea = this;

        // check each line of text
        $.each($(this).val().split("\n"), function () {
            // check if the line matches the pattern
            const hasError = !/^[\,\.\sA-Za-z0-9\u00C0-\u00ff]{1,2048}$/.test(this);

            $(textarea).toggleClass("error", hasError);
            $(textarea).toggleClass("ok", !hasError);

            if (hasError) {
                $(textarea).attr("title", errorMsg);
            } else {
                $(textarea).removeAttr("title");
            }
            return !hasError;
        });
    }

    $.validator.addMethod(
            "validarConteudo",
            function (value) {
                return /^[\,\.\sA-Za-z0-9\u00C0-\u00ff]{1,2048}$/.test(value);
            },
            "Conteúdo inválido. Deve ser um texto de tamanho [1, 2048] sem caracteres especiais"
            );

    $("#formCriar").validate({
        errorPlacement: function errorPlacement(error, element) {
            element.after(error);
        },
        rules: {
            conteudo: {
                required: true,
                validarConteudo: true
            }
        }
    });
});