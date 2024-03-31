$(document).ready(function () {
    // Gets the video src from the data-src on each button
    var $videoSrc;
    $('.video-btn').click(function () {
        $videoSrc = "https://www.youtube.com/embed/" + $(this).data("src");
    });
    console.log($videoSrc);

    // when the modal is opened autoplay it
    $('#myModal').on('shown.bs.modal', function (e) {

        // set the video src to autoplay and not to show related video. Youtube related video is like a box of chocolates... you never know what you're gonna get
        $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
    })

    // stop playing the youtube video when I close the modal
    $('#myModal').on('hide.bs.modal', function (e) {
        // a poor man's stop video
        $("#video").attr('src', $videoSrc);
    })
    $("#ip-search").keyup(function (e) {
        var mv = $("#ip-search").val();
        if (mv != "") {
            $.ajax({
                type: "GET",
                url: "SearchMovieServlet?action=searchPage",
                data: {keyword: mv}, // serializes the form's elements.
                success: function (data)
                {
                    console.log(data.trim());
                    $("#div-view-search").html(data.trim());
                    if ($("#div-view-search").html() == "") {
                        $(".h3Error").html("Không tìm thấy bộ phim nào cả").show();
                    } else {
                        $(".h3Error").hide();
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        } else {
            $("#div-view-search").html("");
            $(".h3Error").html("Bạn phải nhập tên bộ phim mà bạn muốn tìm kiếm").show();
        }
    });
});