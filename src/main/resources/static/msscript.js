ChemicalizeMarvinJs.createEditor("#marvin-test").then(function (marvin) {

    function handleMolChange() {
        marvin.exportStructure("name").then(function (name) {
            document.getElementById("marvin-log").innerHTML = "Name: " + name;});

        marvin.exportStructure("inchi").then(function (inchi) {
            document.getElementById("marvin-logg").innerHTML = "InChi: " + inchi;});

    }

    function send() {

        marvin.exportStructure("name").then(function (name) { n =  name; });
        marvin.exportStructure("inchi").then(function (inchi) { i =  inchi; });
        marvin.exportStructure("smiles").then(function (smiles) { s =  smiles; });

        marvin.exportStructure("inchi").then(function (inchi) {

                let object = {
                "name": n,
                "inchi": i,
                "smiles": s

            };

            let json = JSON.stringify(object);

            let xhr = new XMLHttpRequest();
            xhr.open("POST", '/inchi', false);
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.send(json);


            window.location.replace("/search");

        });
    }

    document.getElementById("action-button").addEventListener("click", send);
    marvin.on("molchange", handleMolChange);
});