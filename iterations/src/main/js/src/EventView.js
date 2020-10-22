import React, { useState } from 'react'
import TemplateBin from './TemplateBin'
import UserView from './UserView'

const EventView = (props) => {

    const [selectedTemplates, setSelectedTemplates] = useState([])

    return (
        <div>
            <div>
                <UserView
                    templates={props.templates}
                    selectedTemplates={selectedTemplates}
                />
            </div>
            <h1 style={{width: '100%', textAlign: 'center', position: 'absolute', bottom: '130px'}}>Templates</h1>
            <div style={{width: '100%', textAlign: 'center', position: 'absolute', bottom: '80px'}}>
                <TemplateBin
                    templates={props.templates}
                    updateSelectedTemplates={(fromChild) => setSelectedTemplates(fromChild)}
                />
            </div>
        </div>
    )
}

export default EventView