document
    .querySelector("form.updateTodo")
    .addEventListener("submit", function (stop) {
      stop.preventDefault();
      let formElements = document.querySelector("form.updateTodo").elements;
      console.log(formElements)
      let id=formElements["commentShopId"].value;
      let cat=formElements["commentCategory"].value;
      let storeName =formElements["commentShop"].value;
  
      let data = {
        "category":cat,
        "id": id,
        "Store name":storeName,
        "shop":{
            "id":id
        }
      }
    console.log("Data to post",data)
    submit(data,id)
  
      // postData(noteTitle,noteBody)
    });

    function submit(data,id){
    fetch(`http://localhost:8082/shop/update/${id}/`, {
        method: "put",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify(data),
      })
      .then(function(data){
          console.log("Success!!",data)
      }

      )
      alert(`Updated Todo ${id}!`)
    }

    document
    .querySelector("form.updateTask")
    .addEventListener("submit", function (stop) {
      stop.preventDefault();
      let formElements = document.querySelector("form.updateTask").elements;
      console.log(formElements)
      let id=formElements["commentShopId"].value;
      let cat=formElements["commentCategory"].value;
      let storeName =formElements["commentShop"].value;
  
      let data = {
        "category":cat,
        "id": id,
        "Store name":storeName,
        "shop":{
            "id":id
        }
      }
    console.log("Data to post",data)
    submitTask(data,id)
  
      // postData(noteTitle,noteBody)
    });

    function submitTask(data,id){
    fetch(`http://localhost:8082/shop/update/${id}/`, {
        method: "put",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify(data),
      })
      .then(function(data){
          console.log("Success!!",data)
      }

      )
      alert(`Updated Task ${id}!`)
    }