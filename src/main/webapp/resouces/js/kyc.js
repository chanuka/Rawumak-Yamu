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

    // sefie image
    // -------------------------------------------------------------------------



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

        $("#idCopyBackView_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#idCopyBackView_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());

    (function () {
        var $image = $("#billCopyView"),
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

        $("#billCopyView_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#billCopyView_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#billCopyView_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#billCopyView_rotate_left").click(function () {
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

        $("#selfieView_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#selfieView_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());
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

        $("#identityCopyView_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#identityCopyView_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());
    (function () {
        var $image = $("#selfieViewID"),
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

        $("#selfieViewID_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#selfieViewID_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#selfieViewID_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#selfieViewID_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());

//for view customer page ----------------------------------------------------------------
    (function () {
        var $image = $("#profileView"),
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

        $("#profileView_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#profileView_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#profileView_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#profileView_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());

    (function () {
        var $image = $("#selfieViewImage"),
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

        $("#selfieViewImage_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#selfieViewImage_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#selfieViewImage_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#selfieViewImage_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());
//----------------------------for customer search ----------------------------------------------------------------
    (function () {
        var $image = $("#identityCopyViewS"),
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

        $("#identityCopyViewS_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#identityCopyViewS_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#identityCopyViewS_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#identityCopyViewS_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());

    (function () {
        var $image = $("#selfieViewIDS"),
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

        $("#selfieViewIDS_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#selfieViewIDS_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#selfieViewIDS_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#selfieViewIDS_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());

    (function () {
        var $image = $("#selfieViewS"),
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

        $("#selfieViewS_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#selfieViewS_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#selfieViewS_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#selfieViewS_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());

    (function () {
        var $image = $("#idCopyBackViewS"),
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

        $("#idCopyBackViewS_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#idCopyBackViewS_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#idCopyBackViewS_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#idCopyBackViewS_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());

    (function () {
        var $image = $("#billCopyViewS"),
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

        $("#billCopyViewS_zoom_in").click(function () {
            $image.cropper("zoom", 0.1);
        });
        $("#billCopyViewS_zoom_out").click(function () {
            $image.cropper("zoom", -0.1);
        });

        $("#billCopyViewS_rotate_right").click(function () {
            $image.cropper("rotate", 90);
        });
        $("#billCopyViewS_rotate_left").click(function () {
            $image.cropper("rotate", -90);
        });

        $("[data-toggle='tooltip']").tooltip();

    }());
});
