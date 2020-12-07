import {useEffect} from "react";

export default function deleteId(idToDelete, fetchAPI, setIdToDelete){
    useEffect(() => {
        if(idToDelete > 0) {
            fetchAPI(idToDelete)
            setIdToDelete(-1)
        }
    }, [idToDelete])
}