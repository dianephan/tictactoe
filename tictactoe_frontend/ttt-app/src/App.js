import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';

function App() {

  useEffect(() => {
    // GET request using fetch inside useEffect React hook
    // created a new game here. need to store gameID in useState
    fetch('/game', {method:"POST"})
        .then(response => response.json())
        .then(response => console.log(response));
        

// empty dependency array means this effect will only run once (like componentDidMount in classes)
}, []);

  const [gameID, setGameID] = useState({ gameid: {} });
  const { id } = useParams(); 

  const handleChange = (e) => {
  // holds ids created from post req
    let updatedValue = {};
    updatedValue = {game1:e.target.value};
    setGameID(gameID => ({
      ...gameID,
      ...updatedValue
    }));
  }

  return (
    <div className="App">
      <header className="App-header">
        <p>
          <code>enter your game id to get started</code>
        </p>
        <form action = "/game" method = "GET" enctype = "multipart/form-data">
          <label>
            <code>game id</code>
            <br></br>
            <input type = "text" name = "gameid" />
          </label>
            <input type="submit" value="find game"/>
        </form>
 

      </header>
    </div>
  );
}

export default App;
