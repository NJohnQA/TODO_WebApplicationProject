fetch('http://localhost:9092/shop/readall')
.then(
  function(response) {
    if (response.status !== 200) {
      console.log('Looks like there was a problem. Status Code: ' +
        response.status);
      return;
    }

    // Examine the text in the response
    response.json().then(function(commentData) {
     console.log(commentData);

      let table = document.querySelector("table");
      let data = Object.keys(commentData[0]); // first record in the array pos 0
      
      createTableHead(table,data);
      createTableBody(table,commentData);
      
    });
  }
)
.catch(function(err) {
  console.log('Fetch Error :-S', err);
});

function createTableHead(table,data){
    let tableHead= table.createTHead();
    let row = tableHead.insertRow();
    for(let keys of data){
        // console.log("data",data)
        let th = document.createElement("th");
        let text = document.createTextNode(keys);
        th.appendChild(text);
        row.appendChild(th)
      }
    }
    

function createTableBody(table,commentData){
    for(let commentRecord of commentData){
        let row = table.insertRow();
        for(let values in commentRecord){
            let cell = row.insertCell();
            let text = document.createTextNode(commentRecord[values]);
            cell.appendChild(text);
          }
          let newCell = row.insertCell();
          let myViewButton = document.createElement("a");
          let myButtonValue = document.createTextNode("View one")
          myViewButton.className ="btn btn-warning";
          myViewButton.href="readOneShop.html?id="+commentRecord.id
          myViewButton.appendChild(myButtonValue);
          newCell.appendChild(myViewButton)
        }
}


