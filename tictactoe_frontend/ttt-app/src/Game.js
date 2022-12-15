import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  useParams,
} from "react-router-dom";

function Game() { 
  let { id } = useParams();
  let jsondata; 
  // var gameid = document.getElementById("gameid").value;
  useEffect(() => {
    fetch(`/game/${id}`, {method:"GET"})
        .then(response => response.json())
        .then(response => console.log("get req for ", response))
        .then((response) => {
          this.setState({
              isLoading: false,
              dataSource: response.shosfc.weekday[7]
          }, function(){
              jsondata = response;
              console.log("jsondata = ", jsondata)
          })
        })
      

  }, [id]); 


  // const fetchDetails = () => {
  //   fetch(`/game/${encodeURIComponent(gameInfo.gameid)}}`, {
  //     method: "GET"
  //     })
  //       // .then(response => response.json())
  //       // .then(response => console.log(response));  
  // }  

  return <div style = {{ fontSize: "50px" }}>
    now showing game <br></br> { id } 
  </div>;
}

export default Game;