import React, { useState } from 'react'

const TemplateBin = (props) => {
    const [ selectedTemplates, setSelectedTemplates ] = useState([]) 

    function removeFromSelectedTemplates(fromChild) {
        const newSelectedTemplates = selectedTemplates.filter(template => template !== fromChild)
        setSelectedTemplates(newSelectedTemplates)
        props.updateSelectedTemplates(newSelectedTemplates)
    }

    function addToSelectedTemplates(fromChild) {
        const newSelectedTemplates = selectedTemplates.concat(fromChild)
        setSelectedTemplates(newSelectedTemplates)
        props.updateSelectedTemplates(newSelectedTemplates)
    }

    return (
        <div>
            {props.templates.map(template => 
                <TemplateBinCard 
                    templateName={template.name}
                    removeFromSelectedTemplates={(fromChild) => removeFromSelectedTemplates(fromChild)}
                    addToSelectedTemplates={(fromChild) => addToSelectedTemplates(fromChild)}
                />
            )}
        </div>
    )

}

const TemplateBinCard = (props) => {
    const styles = {
        'font-size': '40px'
    }

    const [ isSelected, setIsSelected ] = useState(false)
    const [ color, setColor ] = useState({})

    const handleClick = () => {
        if (isSelected) {
            props.removeFromSelectedTemplates(props.templateName)
            setColor({})
            setIsSelected(false)
        } else {
            props.addToSelectedTemplates(props.templateName)
            setColor({color: "green"})
            setIsSelected(true)
        }
    }

    return (
        <button onClick={handleClick} style={Object.assign({}, color, styles)}>
            {props.templateName}
        </button>
    )
}

export default TemplateBin