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
        console.log(buttons);
        let times = [];
        buttons.forEach(button => {
            let value = parseInt(button.value);
            console.log(value);
            if(value !== -1)
            times.push(value);
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

function upload(title, content) {
    fetch("http://localhost:7000/addcalendar?title=" + title, {
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
    let title = document.getElementById("title").value;
    if (title !== "") {
        upload(title, getObjToSave());
    }
}

export default uploadTemplate