import React, { useState } from 'react';
import './App.css';
import Compile from './components/Compile';
import MyComponent from './components/MyComponent';

function App() {
  /*const [uri, setUri] = useState("https://localhost:3000");*/
  //this.props.router.push('/docker');


  return (
    <div>
      <Compile />
      <MyComponent />
    </div>
  );
}

export default App;
