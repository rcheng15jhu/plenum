function download(content) {
    const a = document.createElement("a");
    const file = new Blob([JSON.stringify(content)], {type: "application/json"});
    a.href = URL.createObjectURL(file);
    a.download = "template.json";
    a.click();
    URL.revokeObjectURL(a.href);
}

function downloadTemplate() {
    let objToDownload = {};
    objToDownload.dates = [];
    for (let i = 0; i < 7; i++) {
        const buttons = [...document.getElementsByClassName("day" + i)];
        let times = [];
        buttons.forEach(button => {
            if(button.checked) {
                times.push(parseInt(button.value));
            }
        });
        if(times.length !== 0) {
            objToDownload.dates.push({
                date: i,
                times: times
            })
        }
    }
    download(objToDownload);
    console.log("Test");
}