document
.querySelector("form.viewRecord")
.addEventListener("submit", function (stop) {
  stop.preventDefault();
  let formElements = document.querySelector("form.viewRecord").elements;
  console.log(formElements)
  let itemId=formElements["commentItemId"].value;
  let category =formElements["commentCategory"].value;
  let item=formElements["commentItem"].value;
  let quantity =formElements["commentQuantity"].value;

  let data = {
  
    "id": itemId,
      "itemName": item,
      "category": category,
      "quantity": quantity,
      "Shop": {
        "id": [],

    }

  }
console.log("Data to post",data)
sendData(data)

  // postData(noteTitle,noteBody)
});


function sendData(data){
fetch("http://localhost:8082/item/create", {
    method: 'post',
    headers: {
      "Content-type": "application/json; charset=UTF-8"
    },
    body:JSON.stringify(data)
  })
  .then(function (data) {
    console.log('Request succeeded with JSON response', data);
  })
  .catch(function (error) {
    console.log('Request failed', error);
  });
}