import React from "react";
import {useParams} from 'react-router-dom';
import './RoomDetail.css';
import Flickity from "react-flickity-component";
import "../flickity.css";
import Calendar from "../Calendar/Calendar.js";
import $ from "jquery";


export function withRouter(Children) {
    return(props) => {
       const match  = {params: useParams()};
       return <Children {...props}  match = {match}/>
   }
}

class RoomDetail extends React.Component {

  componentDidMount()  {
    let id= this.props.match.params.id-1;
    this.setState({room : this.props.rooms[id], id:id, loaded:true, availability : this.props.allavailability.filter(
      el => el.idRoom == this.props.match.params.id
    )});

    var settings = {
      "url": "/api/customer/" + localStorage.getItem("username") + "/email",
      "method": "GET",
      "headers": {
        "Content-Type": "application/json"
      }
    };
    
    $.ajax(settings).done(response => {
      console.log(response);
      this.setState({iduser: response});
    });
  }


  componentDidUpdate()  {
    let id= this.props.match.params.id-1;
    if((this.state && id!=this.state.id) || !this.state.room) 
      this.setState({room : this.props.rooms[id], id:id, availability : this.props.allavailability.filter(
        el => el.idRoom == id+1
      )}
    );
  }

  constructor(props) {
    super(props);
    this.state= {room: this.props.room, availability: [], loaded:false};
  }


  render() {
    if(! this.state.loaded)
      return(<div></div>)

    return (        
      <div id="divroom">
        <header class="masthead">
          <div class="container h-100">
            <div class="row h-100 align-items-center">
              <div class="col-12 text-center">
                <h1 class="fw-normal">Room {this.state && this.state.room.name}</h1>
                <p class="lead">A beautiful room</p>
              </div>
            </div>
          </div>
        </header>

        <hr class="solid"/>

        <div id="divGiallo">      
          <div class="card" style={{width: "25rem", height: "20rem"}}>
            <div class="card-body">
              <h5 class="card-title">ROOM DETAIL</h5>
              <p class="card-text">{this.state && this.state.room.notes}</p>
            </div>
          </div>
          
          <br/>
          
          <span class="badge rounded-pill text-bg-primary">PRICE | {this.state && this.state.room.pricePerNight} €/night</span>
          
          <br/><br/>

          <span class="badge rounded-pill text-bg-primary">ROOM CAPACITY | {this.state && this.state.room.capacity} people</span>

          <div id="calendardiv">
            <Calendar idroom= {this.state.room.id} iduser={this.state.iduser} maxcapacity={this.state.room.capacity} availability= {this.state.availability}/>
          </div>
        </div>

        <hr class="solid"/>

        <div id='box'>
          <Flickity
            className={'carousel'}
            elementType={'div'}
            options={{
              autoPlay:2500,
              initialIndex:0,
              wrapAround: true,
              selectedAttraction: 0.01,
              friction: 0.15,
              setGallerySize: true
            }}
            disableImagesLoaded={false}
            reloadOnUpdate
            static
          >
            
            <img src="../img/img8.png"/>
            <img src="../img/img1.png"/>
            <img src="../img/img2.png"/>
            <img src="../img/img3.png"/>
            <img src="../img/img4.png"/>
            <img src="../img/img9.png"/>
            <img src="../img/img6.png"/>
            <img src="../img/img7.png"/>
            <img src="../img/img5.png"/>
          </Flickity>

          <hr class="solid"/><br/>
        </div>
      </div>
    );
  }
}

export default withRouter(RoomDetail);