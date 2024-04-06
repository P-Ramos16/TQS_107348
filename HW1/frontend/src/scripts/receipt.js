window.onload = function() {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const ticket = urlParams.get('ticket')

  // Creating a XHR object
  let xhr1 = new XMLHttpRequest();
  url = "http://localhost:8080/tickets/get/" + ticket;

  // open a connection
  xhr1.open("GET", url, true);

  // Set the request header i.e. which type of content you are sending
  xhr1.setRequestHeader("Content-Type", "application/json");
  
  xhr1.addEventListener("readystatechange", function() {
      if (xhr1.readyState == 4 && xhr1.status == 200) {
        returnedData = xhr1.responseText;
        var data = JSON.parse(returnedData);

        document.getElementById("ticket_id").innerHTML = data.id;
        document.getElementById("ticket_fname").innerHTML = data.firstname;
        document.getElementById("ticket_lname").innerHTML = data.lastname;
        document.getElementById("ticket_phone").innerHTML = data.phone;
        document.getElementById("ticket_email").innerHTML = data.email;
        document.getElementById("ticket_ccard").innerHTML = data.creditCard;
        document.getElementById("ticket_npeople").innerHTML = data.numberOfPeople;
        document.getElementById("ticket_seatn").innerHTML = data.seatNumber;
        document.getElementById("ticket_time").innerHTML = data.aquisitionDate;
        document.getElementById("ticket_price").innerHTML = data.finalPrice;
        document.getElementById("ticket_currency").innerHTML = data.currency;

        document.getElementById("trip_id").innerHTML = data.trip.id;
        document.getElementById("trip_origin").innerHTML = data.trip.route.origin.name;
        document.getElementById("trip_destination").innerHTML = data.trip.route.destination.name;
        document.getElementById("trip_length_km").innerHTML = data.trip.tripLengthKm;
        document.getElementById("trip_length_time").innerHTML = data.trip.tripLengthTime;
        document.getElementById("trip_date").innerHTML = data.trip.date;
        document.getElementById("trip_time").innerHTML = data.trip.time;
        document.getElementById("trip_busn").innerHTML = data.trip.busNumber;
        document.getElementById("trip_price").innerHTML = data.trip.basePrice;
        document.getElementById("trip_total_seats").innerHTML = data.trip.numberOfSeatsTotal;
        document.getElementById("trip_available_seats").innerHTML = data.trip.numberOfSeatsAvailable;

        document.getElementById("headertext").innerHTML = "Trips from " + data.trip.route.origin.name + " to " + data.trip.route.destination.name;
      }
      else if (xhr1.status == 0) {
        alert("No API connection could be established!");
      }
      else if (xhr1.readyState == 4) {
        alert("Code " + xhr1.status + ": " + xhr1.statusText);
      }
  });
  xhr1.send();
};

function returnHome() {
  //window.location.href = "http://localhost:3000/index.html";
  window.location.href = "index.html";
  return false;
}