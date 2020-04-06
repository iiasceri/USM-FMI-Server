function getAllUsersAsJson() {
    var url = "/json";
    $.ajax({
        url: url
    }).then(function (data) {
        alert(data);
        var resultList = '<h3>Users:</h3>';
        for (var i = 0; i < data.length; i++) {
            resultList += "<p style=\"cursor: pointer\"  onclick=\'getUserById(" + data[i].id + ")\'>" + data[i].username + ' ' + data[i].status + '</p>';
        }
        $('#users').html(resultList);
    });
}

function getUserById(id) {
    var url = "/user/" + id;
    $.ajax({
        url: url
    }).then(function (data) {
        var userInfo = data.username + ' ' + data.age + ' ' + data.status;
        $('#showUserInfo').html(userInfo);
    });
}