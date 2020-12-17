// we set a constant to find the values from the search bar
const params = new URLSearchParams(window.location.search);

for(let param of params ){
    console.log("here i am",param)
    let id = param[1];
    console.log(id);
    getData(id)
}

function getData(id){
    fetch('http://localhost:9092/shop/read/'+id)
      .then(
        function(response) {
          if (response.status !== 200) {
            console.log('Looks like there was a problem. Status Code: ' +
              response.status);
            return;
          }
          // Examine the text in the response
          response.json().then(function(data) {
             console.log("MY DATA OBJ",data)

             document.querySelector("input#commentShopId").value = data.id
             document.querySelector("input#commentCategory").value = data.category
             document.querySelector("input#commentShop").value = data.storeName
    
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    }


    document
  .querySelector("form.viewRecord")
  .addEventListener("submit", function (stop) {
    stop.preventDefault();
    let formElements = document.querySelector("form.viewRecord").elements;
    console.log(formElements)
    let id=formElements["commentShopId"].value;
    let cat=formElements["commentCategory"].value;
    let storeName=formElements["commentShop"].value;

    let data = {
      "category":cat,
      "id": id,
      "Store name":storeName,
      "shop":{
          "id":1
      }
    }
    console.log("Data to post",data)
    console.log(id)

    sendData(data,id)
    // postData(noteTitle,noteBody)
  });


  function sendData(data,id){
    fetch("http://localhost:9092/shop/update/"+id, {
        method: 'put',
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

    function deleteByid(id){
      fetch("http://localhost:9092/shop/delete/"+id, {
          method: 'delete',
          headers: {
            "Content-type": "application/json; charset=UTF-8"
          },
        })
        
        
      }

      