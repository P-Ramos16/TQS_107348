window.onload = function() {
  // Creating a XHR object
  let xhr2 = new XMLHttpRequest();
  url = "http://localhost:8080/trips/listCurrencies";

  // open a connection
  xhr2.open("GET", url, true);

  // Set the request header i.e. which type of content you are sending
  xhr2.setRequestHeader("Content-Type", "application/json");
  
  xhr2.addEventListener("readystatechange", function() {
      if (xhr2.readyState == 4 && xhr2.status == 200) {
        returnedData = xhr2.responseText;
        var data = JSON.parse(returnedData);

        var outHTML = "";
        var i;
        for(i = 0; i < data.length; i++) {
          outHTML += "<option value=" + data[i].abreviation + "> " + data[i].abreviation + " </option>";
        }

        document.getElementById("currency").innerHTML = outHTML;


        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const curr = urlParams.get('currency');

        document.getElementById("currency").value = curr;

      }
      else if (xhr2.status == 0) {
        alert("No API connection could be established!");
      }
      else if (xhr2.readyState == 4) {
        alert("Code " + xhr2.status + ": " + xhr2.statusText);
      }
  });
  xhr2.send();


  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const trip = urlParams.get('trip')

  // Creating a XHR object
  let xhr1 = new XMLHttpRequest();
  url = "http://localhost:8080/trips/get/" + trip;

  // open a connection
  xhr1.open("GET", url, true);

  // Set the request header i.e. which type of content you are sending
  xhr1.setRequestHeader("Content-Type", "application/json");
  
  xhr1.addEventListener("readystatechange", function() {
      if (xhr1.readyState == 4 && xhr1.status == 200) {
        returnedData = xhr1.responseText;
        var data = JSON.parse(returnedData);
      

        var outHTML = "";
        var i;

        const filledSeatsArr = data.filledSeats
        const numSeatsTotal = data.numberOfSeatsTotal

        for(i = 1; i < numSeatsTotal + 1; i++) {

          if (filledSeatsArr.includes(i)) {
            btnClasses = "seatFilled";
            disabled = "disabled";
          }
          else {
            btnClasses = "";
            disabled = "";
          }

          
          if ((i + 1) % 4 == 0) {
            outHTML += "<button type='button' id='seat" + i + "' class='seat seatmargin " + btnClasses + "' " + disabled + " onclick='selectSeat(" + i + ")'>" + i + "</button>"
          }
          else {
            outHTML += "<button type='button' id='seat" + i + "' class='seat " + btnClasses + "' " + disabled + " onclick='selectSeat(" + i + ")'>" + i + "</button>"
          }

          if (i % 4 == 0) {
            outHTML += "<div class='seatrow'>"
            outHTML += "</div>"
          }
        }

        document.getElementById("bus").innerHTML = outHTML;


        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const curr = urlParams.get('currency')

        document.getElementById('currency').value = curr;

        reloadPrice(1, curr);

      }
      else if (xhr1.status == 0) {
        alert("No API connection could be established!");
      }
      else if (xhr1.readyState == 4) {
        alert("Code " + xhr1.status + ": " + xhr1.statusText);
      }
  });
  xhr1.send();

  $('#npeople').change(function() {
    npeople = this.value

    reloadPrice(npeople);

    return false;
  });

  $('#currency').change(function() {
    curr = this.value
    var currency = document.getElementById("currency").value;
  
    if (currency === null || currency === "") {
      currency = "USD";
    }
  
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const tripID = urlParams.get('trip')

    //window.location.href = "http://localhost:3000/purchase.html?trip=" + tripID + "&currency=" + currency;
    window.location.href = "purchase.html?trip=" + tripID + "&currency=" + currency;
    return false;
  });
};


function reloadPrice(npeople, curr = null) {

  var currency = document.getElementById("currency").value;

  if (currency === null || currency === "") {
    currency = "USD";
  }

  if (curr != null) {
    currency = curr;
  }

  


  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const trip = urlParams.get('trip');

  // Creating a XHR object
  let xhr1 = new XMLHttpRequest();
  url = "http://localhost:8080/tickets/getPrice?trip=" + trip + "&numpeople=" + npeople + "&currency=" + currency;

  // open a connection
  xhr1.open("GET", url, true);

  // Set the request header i.e. which type of content you are sending
  xhr1.setRequestHeader("Content-Type", "application/json");
  
  xhr1.addEventListener("readystatechange", function() {
      if (xhr1.readyState == 4 && xhr1.status == 200) {
        returnedData = xhr1.responseText;
        var data = JSON.parse(returnedData);

        document.getElementById("finalprice").innerHTML = data + " " + currency;
      }
      else if (xhr1.status == 0) {
        alert("No API connection could be established!");
      }
      else if (xhr1.readyState == 4) {
        alert("Code " + xhr1.status + ": " + xhr1.statusText);
      }
  });
  xhr1.send();
}

function selectSeat(seatnumber) {
  var currSeat = document.getElementById("seatn").value;

  if (currSeat != "") {
    document.getElementById("seat" + currSeat).style.backgroundColor = "#39C3E7"
  }

  document.getElementById("seat" + seatnumber).style.backgroundColor = "#00E95D"
  document.getElementById("seatn").value = seatnumber;

}

function purchaseTicket() {

  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  var trip = urlParams.get('trip');
  var currency = urlParams.get('currency');

  var fname = document.getElementById("fname").value;
  var lname = document.getElementById("lname").value;
  var phone = document.getElementById("phone").value;
  var email = document.getElementById("email").value;
  var creditcard = document.getElementById("creditcard").value;
  var npeople = document.getElementById("npeople").value;
  var seatn = document.getElementById("seatn").value;

  var price = document.getElementById("finalprice").innerHTML;
  
  if (confirm("You are going to purchase " + npeople + " ticket at a price of " + price + "!") != true) {
    return 0;
  } 
  // Creating a XHR object
  let xhr1 = new XMLHttpRequest();
  url = "http://localhost:8080/tickets/buy?firstname="+fname+"&lastname="+lname+"&phone="+phone+"&email="+email+"&creditCard="+creditcard+"&numberOfPeople="+npeople+"&seatNumber="+seatn+"&trip="+trip+"&currency="+currency;

  // open a connection
  xhr1.open("POST", url, true);

  // Set the request header i.e. which type of content you are sending
  xhr1.setRequestHeader("Content-Type", "application/json");
  
  xhr1.addEventListener("readystatechange", function() {
      if (xhr1.readyState == 4 && xhr1.status == 200) {
        returnedData = xhr1.responseText;
        var data = JSON.parse(returnedData);

        console.log(data);

        if (confirm(data) != true) {
          return 0;
        } 

        //window.location.href = "http://localhost:3000/receipt.html?trip=" + tripID + "&currency=" + currency;
        window.location.href = "receipt.html?ticket=" + data.id;
        return false;
      }
      else if (xhr1.status == 0) {
        alert("No API connection could be established!");
      }
      else if (xhr1.readyState == 4) {
        alert("Code " + xhr1.status + ": " + xhr1.statusText);
      }
  });
  xhr1.send();
}