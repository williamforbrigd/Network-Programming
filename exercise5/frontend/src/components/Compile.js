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
    const [code, setCode] = useState("cpp program");

    console.log(code);
    console.log(textInput);

    const handleClick = (e) => {
        setCode(e.target.value);
    };

    const handleInput = (e) => {
        setTextInput(e.target.value);
    }

    return (
        <div>
            <TextareaAutosize 
                input={code}
                onInput={handleInput} 
                rowsMin={10}
                defaultValue="hei"
            />
            <Button onClick={handleClick}>
                Click me
            </Button>
        </div>
 );
}


export default Compile;