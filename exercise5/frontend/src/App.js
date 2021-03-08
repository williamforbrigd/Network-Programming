import React, { useState } from 'react';
import './App.css';
import { TextareaAutosize, Button } from '@material-ui/core';

function App() {
const [code, setCode ] = useState("#include <iostream> \n int main() {std::cout<<\"hei willi\"<<std::endl;}");
const [output, setOutput] = useState("");

const postRequest = async() => {
  const requestOptions = {
          method: 'POST',
          headers: { 'Content-Type' : 'application/json'},
          body: JSON.stringify(code)
  };
  fetch("http://localhost:8080/docker", requestOptions)
    .then(response => response.json())
    .then(result => {
      setOutput(result.code);
    });
};

const handleInput = (e) => {
  setCode(e.target.value);
};

return (
  <div>
    <h3>Enter the c++ code:</h3>
    <TextareaAutosize 
      rowsMin={10}
      text={code}
      onInput={handleInput}
    />
    <Button onClick={postRequest}>
      Compile and Run
    </Button>
    <h5>
      {output}
    </h5>
  </div>
  );
}

export default App;
