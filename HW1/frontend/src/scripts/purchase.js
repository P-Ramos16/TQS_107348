window.onload = function() {};

function submitTicket(currency) {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const route = urlParams.get('route')


  // Creating a XHR object
  let xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/trips/listByRoute?route=" + route + "&currency=" + currency;

  // open a connection
  xhr.open("GET", url, true);

  // Set the request header i.e. which type of content you are sending
  xhr.setRequestHeader("Content-Type", "application/json");
  
  xhr.addEventListener("readystatechange", function() {
      if (xhr.readyState == 4 && xhr.status == 200) {
        returnedData = xhr.responseText;
        var data = JSON.parse(returnedData);

        var outHTML = "";
        var i;
        for(i = 0; i < data.length; i++) {
        
          outHTML += "<tr>";
          outHTML += "<td> " + data[i].id + " </td>";
          outHTML += "<td> " + data[i].date + " </td>";
          outHTML += "<td> " + data[i].time + " </td>";
          outHTML += "<td> " + data[i].tripLengthTime + " </td>";
          outHTML += "<td> " + data[i].tripLengthKm + " </td>";
          outHTML += "<td> " + data[i].busNumber + " </td>";
          outHTML += "<td> " + data[i].basePrice + " " + currency + " </td>";
          outHTML += "<td><button class='tripSelectBtn' onclick='select_trip(\""+ data[i].id  + "\")'> Select <i class=\"fa fa-check-square-o \" aria-hidden=\"true\"></i> </button> </td>";
          outHTML += "</tr>";
        
        }

        document.getElementById("triplist").innerHTML = outHTML;
      }
      else if (xhr.status == 0) {
        alert("No API connection could be established!");
      }
      else if (xhr.readyState == 4) {
        alert("Code " + xhr.status + ": " + xhr.statusText);
      }
  });
  xhr.send();
};