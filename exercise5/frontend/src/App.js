import React, { useState } from "react";
import "./styles/App.css";
import { TextareaAutosize, Button } from "@material-ui/core";

function App() {
  const [code, setCode] = useState(
    '#include <iostream> \n int main() {std::cout<<"hei willi"<<std::endl;}'
  );
  const [output, setOutput] = useState("");

  const postRequest = () => {
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(code),
    };
    fetch("http://localhost:8080/docker", requestOptions)
      .then((response) => response.json())
      .then((result) => {
        setOutput(result.code);
      });
  };

  const handleInput = (e) => {
    setCode(e.target.value);
  };

  return (
    <div className="grid-style">
      <h3>main.cpp</h3>
      <TextareaAutosize className="border textArea" rowsMin={15} text={code} onInput={handleInput} defaultValue={code}/>
      <Button style={styles.button} onClick={postRequest}>Compile and Run</Button>
      <h3>Output</h3>
      <div className="output border">
        <h5>{output}</h5>
      </div>
    </div>
  );
}

const styles = {
  button: {
    background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
    borderRadius: 3,
    border: 0,
    color: 'white',
    height: 48,
    padding: '0 30px',
    boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
  },
}

export default App;
