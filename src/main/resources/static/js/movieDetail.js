$(document).ready(function () {
    function convert(str) {
        var mnths = {
                Jan: "01",
                Feb: "02",
                Mar: "03",
                Apr: "04",
                May: "05",
                Jun: "06",
                Jul: "07",
                Aug: "08",
                Sep: "09",
                Oct: "10",
                Nov: "11",
                Dec: "12"
            },
            date = str.split(" ");
        console.log(date);
        return [date[2] + "-" + mnths[date[1]] + "-" + date[5]];
    }
    $("#movieStart").html(convert($("#movieStart").text().trim()))



});