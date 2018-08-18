<%-- 
    Document   : ValidateUserInputScript
    Created on : 25 Sep, 2014, 2:20:34 PM
    Author     : asela
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript">

            // validate uploadmerchantcertificate user inputs
            function validateMerchantCertificateInputs() {
                var merID = $('#merchantId').val();
                var file = $('#file').val();
                var filesufix = file.substring(file.length - 3, file.length);
                if (!merID) {
                    $('#divmsg').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Merchant ID cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!file) {
                    $('#divmsg').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>File cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (filesufix != 'cer') {
                    $('#divmsg').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Select valid file.</span></p>\
                </div>\
                </div>');
                    return;
                }

                $("#upButton").click();
            }

            // validate uploadmerchantcertificate user inputs(for add)
            function validateAddMerchantAddonUserInputs() {
                //        var merchantID = $('#merchantID').val();
                var merchantNameID = $('#imerchantNameID').val();
                var image = $('#iimage').val();
                var cordinateX = $('#icordinateX').val();
                var cordinateY = $('#icordinateY').val();
                var displayText = $('#idisplayText').val();
                var fontFamily = $('#ifontFamily').val();
                var fontStyle = $('#ifontStyle').val();
                var fontSize = $('#ifontSize').val();
                var remarks = $('#iremarks').val();

                var filesufix = image.substring(image.length - 3, image.length);
                var issufixmatch = validateForImageFiles(filesufix);
//                if (!merchantID) {
//                    $('#divmsg').html('<div class="ui-widget actionError">\
//                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
//                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
//                <span>Merchant ID cannot be empty</span></p>\
//                </div>\
//                </div>');
//                    return;
                if (!merchantNameID) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Merchant name cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!displayText) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Tag Line cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!cordinateX) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>X-coordinate cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!cordinateY) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Y-coordinate cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!fontFamily) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Font family cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!fontStyle) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Font style cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!fontSize) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Font size cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!remarks) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Remarks is invalid</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!image) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Logo path cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!issufixmatch) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>You must upload an image file with one of the following extensions: jpg, jpeg, gif, png, bmp</span></p>\
                </div>\
                </div>');
                    return;
                }
                $("#addButton").click();
            }
            // validate uploadmerchantcertificate user inputs(for update)
            function  validateUpdateMerchantAddonUserInputs() {
                var merchantID = $('#emerchantID').val();
//                var merchantNameID = $('#emerchantNameID').val();
                var merchantNameID = $('#emerchantID').val();
                var cordinateX = $('#ecordinateX').val();
                var cordinateY = $('#ecordinateY').val();
                var displayText = $('#edisplayText').val();
                var fontFamily = $('#efontFamily').val();
                var fontStyle = $('#efontStyle').val();
                var fontSize = $('#efontSize').val();
                var remarks = $('#eremarks').val();



                if (!merchantID) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Merchant ID cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!merchantNameID) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Merchant name cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!displayText) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Tag Line cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!cordinateX) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>X-coordinate cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!cordinateY) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Y-coordinate cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!fontFamily) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Font family cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!fontStyle) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Font style cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!fontSize) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Font size cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!remarks) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Remarks is invalid</span></p>\
                </div>\
                </div>');
                    return;
                } else {

                    var image = $('#eimage').val();
                    if (image != '') {
                        var filesufix = image.substring(image.length - 3, image.length);
                        var issufixmatch = validateForImageFiles(filesufix);
                        if (!issufixmatch) {
                            $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>You must upload an image file with one of the following extensions: jpg, jpeg, gif, png, bmp</span></p>\
                </div>\
                </div>');
                            return;
                        }

                    }
                }
                $("#updateButton").click();
            }

            // validate IPG Card Association user inputs(for add)
            function  validateAddIpgCardAssociation() {

                var cardAssociationCode = $('#icardAssociationCode').val();
                var description = $('#idescription').val();
                var verifieldImageURL = $("#iverifieldImageURL").val();
                var cardImageURL = $('#icardImageURL').val();
                var sortKey = $('#isortKey').val();

                var vfilesufix = verifieldImageURL.substring(verifieldImageURL.length - 3, verifieldImageURL.length);
                var isvsufixmatch = validateForImageFiles(vfilesufix);

                var filesufix = verifieldImageURL.substring(cardImageURL.length - 3, cardImageURL.length);
                var issufixmatch = validateForImageFiles(filesufix);


                if (!cardAssociationCode) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Card Association code cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!description) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Description cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!verifieldImageURL) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Verified Image URL cannot be emptyset</span></p>\
                </div>\
                </div>');
                    return;

                } else if (!isvsufixmatch) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>You must upload an image file with one of the following extensions: jpg, jpeg, gif, png, bmp</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!cardImageURL) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Card Image URL cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!issufixmatch) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>You must upload an image file with one of the following extensions: jpg, jpeg, gif, png, bmp</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!sortKey) {
                    $('#divmsginsert').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Sort Key cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                }
                $("#addButton").click();
            }
            // validate IPG Card Association user inputs(for add)
            function  validateUpdateIpgCardAssociation() {

                var cardAssociationCode = $('#ecardAssociationCode').val();
                var description = $('#edescription').val();
                var verifieldImageURL = $("#everifieldImageURL").val();
                var cardImageURL = $('#ecardImageURL').val();
                var sortKey = $('#esortKey').val();

                if (!cardAssociationCode) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Card Association code cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!description) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Description cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else if (!sortKey) {
                    $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>Sort Key cannot be empty</span></p>\
                </div>\
                </div>');
                    return;
                } else {

                    var filesufix = verifieldImageURL.substring(cardImageURL.length - 3, cardImageURL.length);
                    var issufixmatch = validateForImageFiles(filesufix);

                    if (verifieldImageURL != '') {
                        var vfilesufix = verifieldImageURL.substring(verifieldImageURL.length - 3, verifieldImageURL.length);
                        var isvsufixmatch = validateForImageFiles(vfilesufix);

                        if (!isvsufixmatch) {
                            $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>You must upload an image file with one of the following extensions: jpg, jpeg, gif, png, bmp</span></p>\
                </div>\
                </div>');
                            return;
                        }
                    }

                    if (cardImageURL != '') {
                        var filesufix = verifieldImageURL.substring(cardImageURL.length - 3, cardImageURL.length);
                        var status = validateForImageFiles(filesufix);

                        if (!status) {
                            $('#divmsgupdate').html('<div class="ui-widget actionError">\
                <div class="ui-state-error ui-corner-all" style="padding: 0.3em 0.7em; margin-top: 20px;"> \
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>\
                <span>You must upload an image file with one of the following extensions: jpg, jpeg, gif, png, bmp</span></p>\
                </div>\
                </div>');
                            return;
                        }
                    }

                }
                $("#updateButton").click();
            }

            function validateForImageFiles(sufix) {
                var status = false;
                if (sufix == 'jpg') {
                    status = true;
                } else if (sufix == 'jpeg') {
                    status = true;
                } else if (sufix == 'gif') {
                    status = true;
                } else if (sufix == 'png') {
                    status = true;
                } else if (sufix == 'bmp') {
                    status = true;
                }
                return status;
            }

        </script>
    </head>
    <body>

    </body>
</html>
