import {useEffect} from "react";

export default function useDeleteId(idToDelete, fetchAPI, setIdToDelete){
    useEffect(() => {
        if(idToDelete > 0) {
            fetchAPI(idToDelete)
            setIdToDelete(-1)
        }
    }, [idToDelete])
}