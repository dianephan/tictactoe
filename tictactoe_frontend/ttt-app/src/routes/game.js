import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';

function Game() { 
  var gameid = document.getElementById("gameid").value;
  useEffect(() => {
    fetch('/game/{gameid}', {method:"GET"})
        .then(response => response.json())
        .then(response => console.log(response));
  }, []); 

}

export default Game;