function download(content) {
    const a = document.createElement("a");
    const file = new Blob([JSON.stringify(content)], {type: "application/json"});
    a.href = URL.createObjectURL(file);
    a.download = "template.json";
    a.click();
    URL.revokeObjectURL(a.href);
}

function getObjToSave() {
    let objToSave = {};
    objToSave.dates = [];
    for (let i = 0; i < 7; i++) {
        const buttons = [...document.getElementsByClassName("day" + i)];
        let times = [];
        buttons.forEach(button => {
            if(button.checked) {
                times.push(parseInt(button.value));
            }
        });
        if(times.length !== 0) {
            objToSave.dates.push({
                date: i,
                times: times
            })
        }
    }
    return objToSave;
}

function downloadTemplate() {
    download(getObjToSave());
    console.log("Test");
}

function upload(name, event, content) {
    fetch("/addcalendar?title=test&userId=" + name + "&eventId=" + event, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },body: JSON.stringify(content)
    }).then(data => {
        console.log(data);
    });
}

function uploadTemplate() {
    let name = document.getElementById("name").value;
    let event = document.getElementById("event").value;
    if (name !== "" && event !== "") {
        upload(name, event, getObjToSave());
    }
}