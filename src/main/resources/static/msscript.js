ChemicalizeMarvinJs.createEditor("#marvin-test").then(function (marvin) {

    function handleMolChange() {
        marvin.exportStructure("name").then(function (name) {
            document.getElementById("marvin-log").innerHTML = "Name: " + name;});

        marvin.exportStructure("inchi").then(function (inchi) {
            document.getElementById("marvin-logg").innerHTML = "InChi: " + inchi;});


    }



    function send() {


        var n;
        var s;
        var i;

        marvin.exportStructure("name").then(function (name) { n =  name; });
        marvin.exportStructure("inchi").then(function (inchi) { i =  inchi; });
        marvin.exportStructure("smiles").then(function (smiles) { s =  smiles; });


        marvin.exportStructure("inchi").then(function (inchi) {

                let object = {
                "name": n,
                "inchi": inchi,
                "smiles": s

            };

            let json = JSON.stringify(object);

            let xhr = new XMLHttpRequest();
            xhr.open("POST", '/inchi', false);
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.send(json);

            if (xhr.status == 200) {
              alert("Success!");
            }

        });
    }

    document.getElementById("action-button").addEventListener("click", send);
    marvin.on("molchange", handleMolChange);
});