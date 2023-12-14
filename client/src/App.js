import React from "react";
import './App.css';
import Navbar from './Navbar/Navbar.js';
import $ from "jquery"
import "./flickity.css";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import Home from "./Home.js";
import RoomDetail from './RoomDetailPage/RoomDetail.js';
import Login from "./Login/Login.js";
import UserDetail from './UserPage/UserDetail.js';
import SeasonsPage from './Seasons/SeasonsPage.js';
import AboutUs from './AboutUs/AboutUs.js';
import Services from './Services/Services.js';
import { Fragment } from 'react'; 
import ScrollButton from './components/ScrollButton'; 
import Surrounding from "./Surrounding/Surrounding.js";

class App extends React.Component {

  componentDidMount()  {
    if(localStorage.getItem('token'))
      $.ajaxSetup({
        headers: {"Authorization":"Bearer "+localStorage.getItem('token')}
      });

    $.getJSON("api/room", (data) => {
      this.setState({loaded:true, rooms:data})
    });

    let year= new Date().getFullYear();
    var settings = {
      "url": "/api/bookingmask/" + year,
      "method": "GET",
      "headers": {
        "Content-Type": "application/json"
      }
    };

    $.ajax(settings).done(response => {
      this.setState({allavailability : response});
    });
  }


  constructor(props) {
    super(props);
    this.state= {
      rooms: [],
      tempCredentials:{},
      needLogin: localStorage.getItem("token") ? false : true
    };
  }


  changeNeedLogin = (booleano) => {
    console.log(booleano);
    this.setState({needLogin:booleano});
    window.location.replace("/");
  }


  render() {
    if(!this.state.loaded)
      return(<div>ciao</div>)
      
    return (
      <div id='box'>
        <BrowserRouter> 
          <Routes>
            <Route path="/" element={<Navbar rooms={this.state.rooms} needLogin={this.state.needLogin}/>}> 
              <Route index element={<Home/>}/>
              <Route path="aboutus" element={<AboutUs/>}/>
              <Route path="aboutus/allservices" element={<Services/>}/>
              <Route path="surroundings" element={<Surrounding/>}/>

              <Route path="room/:id" element={<RoomDetail rooms={this.state.rooms} allavailability={this.state.allavailability}/>}/>
              
              <Route path="login" element={<Login tempCredentials={this.state.tempCredentials} needLogin={this.state.needLogin} change={this.changeNeedLogin}/>}/>
              <Route path="userdetail" element={<UserDetail user={localStorage.getItem("username")} />}/>
              <Route path="seasons" element={<SeasonsPage/>}/>
            </Route>
          </Routes>
        </BrowserRouter>

        <Fragment> 
          <ScrollButton/>
        </Fragment>
      </div>
    );
  }

}

export default App;