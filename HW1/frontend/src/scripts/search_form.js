$('.dropdown-el').click(function(e) {
    e.preventDefault();
    e.stopPropagation();
    $(this).toggleClass('expanded');
    $('#'+$(e.target).attr('for')).prop('checked',true);
  });
$(document).click(function() {
    $('.dropdown-el').removeClass('expanded');
});

window.onload = function() {
  var selectObj = document.getElementById("origin");

  // Creating a XHR object
  let xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/trips/listRouteOrigins";

  // open a connection
  xhr.open("GET", url, true);

  // Set the request header i.e. which type of content you are sending
  //xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.setRequestHeader("Content-Type", "application/json");
  
  xhr.addEventListener("readystatechange", function() {
      if (xhr.readyState == 4 && xhr.status == 200) {
        returnedData = xhr.responseText;
        var data = JSON.parse(returnedData);

        var outHTML = "";
        var i;
        for(i = 0; i < data.length; i++) {
          outHTML += "<option value=" + data[i].id + "> " + data[i].name + " </option>";
        }

        selectObj.innerHTML += outHTML;
      }
      else if (xhr.status == 0) {
        alert("No API connection could be established!");
      }
      else if (xhr.readyState == 4) {
        alert("Code " + xhr.status + ": " + xhr.statusText);
      }
  });
  xhr.send();

  // Creating a XHR object
  let xhr1 = new XMLHttpRequest();
  var url = "http://localhost:8080/tickets/list";

  // open a connection
  xhr1.open("GET", url, true);

  // Set the request header i.e. which type of content you are sending
  //xhr1.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr1.setRequestHeader("Content-Type", "application/json");
  
  xhr1.addEventListener("readystatechange", function() {
      if (xhr1.readyState == 4 && xhr1.status == 200) {
        returnedData = xhr1.responseText;
        var data = JSON.parse(returnedData);

        var outHTML = "";
        var i;
        for(i = 0; i < data.length; i++) {
          outHTML += "<tr>";
          outHTML +=  "<td class='tdLabel'> " + data[i].id + " </td>";
          outHTML +=  "<td class='tdValue'> " + data[i].trip.route.origin.name + " </td>";
          outHTML +=  "<td class='tdValue'> " + data[i].trip.route.destination.name + " </td>";
          outHTML +=  "<td class='tdValue'> " + data[i].finalPrice + " " + data[i].currency + " </td>";
          outHTML +=  "<td class='tdValue'> <button class='tripSelectBtn' onclick='seeTicket(\""+ data[i].id  + "\")'> <i class=\"fa fa-search \" aria-hidden=\"true\"></i> Info </button> </td>";
          outHTML += "</tr>";
        }

        document.getElementById("ticketsList").innerHTML = outHTML;
      }
      else if (xhr1.status == 0) {
        alert("No API connection could be established!");
      }
      else if (xhr1.readyState == 4) {
        alert("Code " + xhr1.status + ": " + xhr1.statusText);
      }
  });
  xhr1.send();

  $('#origin').change(function() {
    $("#destination").val($("#destination option:first").val());
    $("#destination").removeAttr("style")
    originID = this.value
    var selectObj = document.getElementById("destination");

    // Creating a XHR object
    let xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/trips/listRouteDestinationsByOrigin?origin=" + originID;

    // open a connection
    xhr.open("GET", url, true);

    // Set the request header i.e. which type of content you are sending
    xhr.setRequestHeader("Content-Type", "application/json");
        
    xhr.addEventListener("readystatechange", function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
          returnedData = xhr.responseText;
          var data = JSON.parse(returnedData);

          var outHTML = "<option value=\"\" selected disabled hidden> Destination</option>";
          var i;
          for(i = 0; i < data.length; i++) {
            outHTML += "<option value=" + data[i].id + "> " + data[i].name + " </option>";
          }

          selectObj.innerHTML = outHTML;
          document.getElementById("destination").disabled = false;
          document.getElementById("submitbtn").disabled = true;
        }
        else if (xhr.readyState == 4) {
          alert("Code " + xhr.status + ": " + xhr.statusText);
        }
    });
    xhr.send();
  });

  $('#destination').change(function() {
    document.getElementById("submitbtn").disabled = false;
  });
};


function search_form() {
  var routeID = document.getElementById("destination").value;

  if (routeID === null || routeID === "") {
    alert("Please Select all the required parameters!");
    return false;
  }

  //window.location.href = "http://localhost:3000/selectpage.html?route=" + routeID;
  window.location.href = "selectpage.html?route=" + routeID;
  return false;
};

function seeTicket(ticketID) {

  //window.location.href = "http://localhost:3000/selectpage.html?route=" + routeID;
  window.location.href = "receipt.html?ticket=" + ticketID;
  return false;
}