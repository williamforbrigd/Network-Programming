import React, { useState } from 'react';
import { Button, TextareaAutosize} from '@material-ui/core';

function Compile() {
    return (
        <div>
            <h3>Enter the c++ code:</h3>
            <CompileRun/>
        </div>
    );
}

function CompileRun(props) {
    const [textInput, setTextInput] = useState("");

    console.log(textInput);

    const handleInput = (e) => {
        setTextInput(e.target.value);
    }

    /*
    const compile = (e) => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ title: 'React POST request example '})
        };
    }
    */

    return (
        <div>
            <TextareaAutosize
                input={textInput}
                onInput={handleInput} 
                rowsMin={10}
                defaultValue="hei"
            />
            <Button >
                Click me
            </Button>
        </div>
 );
}

export default Compile;