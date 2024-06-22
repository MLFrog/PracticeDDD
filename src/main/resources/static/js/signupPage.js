document.addEventListener('DOMContentLoaded', function() {
    $("#signUpBtn").click(function() {
        let valiTp = valiInput();
        if(valiTp){
            const data = {
                loginId: $('#inputLoginId').val(),
                password: $('#inputPassword').val(),
                email: $('#inputEmail').val() + $("#EmailType").val(),
                phoneNumber: $('#inputNumber').val(),
                nickname: $('#inputNickname').val(),
                realname: $('#inputRealName').val(),
                roles: getRoleCode(),
                AGREE: getAgreeString()
            };
            $.ajax({
                type: 'POST',
                url: '/sign-up/save',
                data: JSON.stringify(data),
                contentType: 'application/json; charset=utf-8',
                success: function(response) {
                    showModal('회원가입', '회원가입이 완료되었습니다. 이메일을 확인해주세요.');
                },
                error: function(error) {
                    showModal('회원가입', '회원가입중 서버 오류 발생.');
                }
            });
        }
    });
    $("#idDuplicateChkTrue").click(function() {
        $("#inputLoginId").prop("disabled", false);
        $("#idDuplicateChk").removeClass('btn btn-outline-success').addClass('btn btn-outline-warning').removeClass('disabled');
        $("#idDuplicateChkTrue").addClass('d-none');
    });

    $("#idDuplicateChk").click(function() {
        if($("#inputLoginId").val().trim() !== ""){
            const data = {
                loginId: $('#inputLoginId').val()
            };

            $.ajax({
                type: 'POST',
                url: '/sign-up/idcheck',
                data: JSON.stringify(data),
                contentType: 'application/json; charset=utf-8',
                success: function(response) {
                    if(response) {
                        $("#inputLoginId").prop("disabled", true);
                        $("#idDuplicateChk").removeClass('btn btn-outline-warning').addClass('btn btn-outline-success').addClass('disabled');
                        showModal('아이디중복확인', '사용가능한 아이디 입니다.');
                        $("#idDuplicateChkTrue").removeClass('d-none');
                    }else {
                        showModal('아이디중복확인', '이미 사용중인 아이디 입니다.');
                    }
                },
                error: function(error) {
                    showModal('아이디중복확인', '아이디 중복체크중 서버오류 발생.');
                }
            });
        }else{
            showModal('아이디중복확인', '아이디입력후 시도해주세요.');
        }
    });

    function valiInput(){
        $("#inputLoginId,#inputPassword,#inputPasswordChk, #inputNickname, #inputEmail, #inputRealName,#inputNumber").removeClass("error");
        let isValid = true;

        if ($("#inputLoginId").val().trim() === "") {
            $("#inputLoginId").addClass("error");
            isValid = false;
        }

        if ($("#inputPassword").val().trim() === "") {
            $("#inputPassword").addClass("error");
            isValid = false;
        }

        if ($("#inputPasswordChk").val().trim() === "") {
            $("#inputPasswordChk").addClass("error");
            isValid = false;
        }

        if ($("#inputEmail").val().trim() === "") {
            $("#inputEmail").addClass("error");
            isValid = false;
        }

        if ($("#inputNumber").val().trim() === "") {
            $("#inputNumber").addClass("error");
            isValid = false;
        }

        if ($("#inputNickname").val().trim() === "") {
            $("#inputNickname").addClass("error");
            isValid = false;
        }
        if ($("#inputRealName").val().trim() === "") {
            $("#inputRealName").addClass("error");
            isValid = false;
        }

        if ($("#passwordMessage").text()!=="비밀번호가 일치합니다.")
            isValid = false;

        if (!$("#agreeCheck").is(':checked')){
            showModal('개인정보수집동의', '개인정보 수집 동의후 회원가입이 가능합니다.');
            return false;
        }

        if (!$('#inputLoginId').is(':disabled')){
            showModal('아이디중복확인', '사용가능 아이디인지 확인후 회원가입이 가능합니다.');
            return  false;
        }
        return isValid;
    }

    function showModal(title, content) {
        let modal = new bootstrap.Modal($("#showModal"));
        $("#showModal").textContent = title;
        document.querySelector('#showModal .modal-body').textContent = content;
        modal.show();
    }

    function getAgreeString() {
        let agreeVal = "";
        if($("#agreeCheck").is(':checked'))
            agreeVal = 'T';
        else agreeVal = 'F';

        return agreeVal;
    }

    function getRoleCode() {
        let code = $('#inputRoles').val();
        let returnCode = '';
        if(code==='ADMIN'){
            returnCode = 'ADMIN';
        }else
            returnCode = 'NORMAL';
        return returnCode;
    }

    $("#inputNumber").on("input", function() {
        let maxLength = 11; // 최대 길이
        let input = $(this).val(); // 입력된 문자열

        if (input.length > maxLength) {
            input = input.slice(0, maxLength);
            $(this).val(input);
        }
    });

    $("#selectEmailType").change(function() {
        let selectedValue = $(this).val();
        if (selectedValue === "직접입력") {
            $("#EmailType").prop("disabled", false).val("").focus();
        } else {
            $("#EmailType").prop("disabled", true).val(selectedValue);
        }
    });

    $("#EmailType").val($("#selectEmailType").val());

    $("#inputPassword, #inputPasswordChk").on("keyup", function() {
        let password = $("#inputPassword").val();
        let passwordChk = $("#inputPasswordChk").val();

        if (password === passwordChk) {
            $("#passwordMessage").text("비밀번호가 일치합니다.").css("color", "green");
        } else {
            $("#passwordMessage").text("비밀번호가 일치하지 않습니다.").css("color", "red");
        }
    });
});