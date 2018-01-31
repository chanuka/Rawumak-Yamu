$(function () {

    "use strict";

    var console = window.console || {log: function () {
        }},
    $alert = $(".docs-alert"),
            $message = $alert.find(".message"),
            showMessage = function (message, type) {
                $message.text(message);

                if (type) {
                    $message.addClass(type);
                }

                $alert.fadeIn();

                setTimeout(function () {
                    $alert.fadeOut();
                }, 3000);
            };

    // 
    // -------------------------------------------------------------------------

    
    
    (function () {
        var $image = $("#identityCopyView"),
                $dataX = $("#dataX"),
                $dataY = $("#dataY"),
                $dataHeight = $("#dataHeight"),
                $dataWidth = $("#dataWidth"),
                options = {
                    
                    aspectRatio: 16 / 9,
                    data: {
                        x: 640,
                        y: 60,
                        width: 640,
                        height: 360
                    },
                    autoCropArea: 0.6, // Center 60%
                    dragCrop: false,
                    modal: false,
                    autoCrop: false,
                    preview: ".img-preview",
                    done: function (data) {
                        $dataX.val(Math.round(data.x));
                        $dataY.val(Math.round(data.y));
                        $dataHeight.val(Math.round(data.height));
                        $dataWidth.val(Math.round(data.width));
                    }
                };

        $image.cropper(options).on({
            "build.cropper": function (e) {
                console.log(e.type);
            },
            "built.cropper": function (e) {
                console.log(e.type);
            }
        });

        $(document).on("click", "[data-method]", function () {
            var data = $(this).data();

            if (data.method) {
                $image.cropper(data.method, data.option);
            }
        });

        $("#identityCopyView_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#identityCopyView_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#identityCopyView_rotate_left").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#identityCopyView_rotate_right").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());
    
    (function () {
        var $image = $("#idCopyBackView"),
                $dataX = $("#dataX"),
                $dataY = $("#dataY"),
                $dataHeight = $("#dataHeight"),
                $dataWidth = $("#dataWidth"),
                options = {
                    aspectRatio: 16 / 9,
                    data: {
                        x: 640,
                        y: 60,
                        width: 640,
                        height: 360
                    },
                    autoCropArea: 0.6, // Center 60%
                    dragCrop: false,
                    modal: false,
                    autoCrop: false,
                    preview: ".img-preview",
                    done: function (data) {
                        $dataX.val(Math.round(data.x));
                        $dataY.val(Math.round(data.y));
                        $dataHeight.val(Math.round(data.height));
                        $dataWidth.val(Math.round(data.width));
                    }
                };

        $image.cropper(options).on({
            "build.cropper": function (e) {
                console.log(e.type);
            },
            "built.cropper": function (e) {
                console.log(e.type);
            }
        });

        $(document).on("click", "[data-method]", function () {
            var data = $(this).data();

            if (data.method) {
                $image.cropper(data.method, data.option);
            }
        });

        $("#idCopyBackView_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#idCopyBackView_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#idCopyBackView_rotate_left").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#idCopyBackView_rotate_right").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());
    
    (function () {
        var $image = $("#selfieView"),
                $dataX = $("#dataX"),
                $dataY = $("#dataY"),
                $dataHeight = $("#dataHeight"),
                $dataWidth = $("#dataWidth"),
                options = {
                    aspectRatio: 16 / 9,
                    data: {
                        x: 640,
                        y: 60,
                        width: 640,
                        height: 360
                    },
                    autoCropArea: 0.6, // Center 60%
                    dragCrop: false,
                    modal: false,
                    autoCrop: false,
                    preview: ".img-preview",
                    done: function (data) {
                        $dataX.val(Math.round(data.x));
                        $dataY.val(Math.round(data.y));
                        $dataHeight.val(Math.round(data.height));
                        $dataWidth.val(Math.round(data.width));
                    }
                };

        $image.cropper(options).on({
            "build.cropper": function (e) {
                console.log(e.type);
            },
            "built.cropper": function (e) {
                console.log(e.type);
            }
        });

        $(document).on("click", "[data-method]", function () {
            var data = $(this).data();

            if (data.method) {
                $image.cropper(data.method, data.option);
            }
        });

        $("#selfieView_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#selfieView_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#selfieView_rotate_left").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#selfieView_rotate_right").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());
    
});
