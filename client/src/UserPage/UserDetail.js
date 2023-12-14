import React from "react";
import $ from "jquery";
import "./UserDetail.css";
import { isBefore, format } from "date-fns";

class UserDetail extends React.Component {

    componentDidMount() {
        let admin= localStorage.getItem("admin");
        let url= admin ? "/api/booking" : ("/api/booking/" + localStorage.getItem("username") + "/bookings");
        
        var settings = {
            "url": url,
            "method": "GET",
            "timeout": 0,
            "headers": {
              "Content-Type": "application/json"
            }
        };
        
        $.ajax(settings).done(response => {
            this.setState({bookings:response, loaded:true});
        })
    }


    constructor(props) {
        super(props);
        this.state= {loaded:false};
    }

    confirmBooking = (el) => {
        let newJson= {
            "id": el.id,
            "checkIn": el.checkIn,
            "checkOut": el.checkOut,
            "notes": el.notes,
            "guests": el.guests,
            "confirmed": !el.confirmed,
            "customerId": el.customerId,
            "roomId": el.roomId,
            "price": el.price
        };


        console.log(newJson);
        var settings = {
            "url": "/api/booking/" + el.id,
            "method": "PUT",
            "timeout": 0,
            "data": JSON.stringify(newJson),
            "headers": {
              "Content-Type": "application/json"
            }
          };
          
        $.ajax(settings).done(response => {
            //1) prendo vettore nello stato 
            //2) trovo indice del booking modificato
            //3) sostituisco la response a quel booking
            //4) risetto lo state

            let bookings= this.state.bookings;
            let index= bookings.findIndex(b => b.id == response.id);
            bookings[index]= response;
            this.setState({bookings:bookings});
        });
    }

    delete = (e) => {
        var settings = {
            "url": "/api/booking/" + e.target.id,
            "method": "DELETE",
            "timeout": 0,
            "headers": {
              "Content-Type": "application/json"
            }
          };
          
        $.ajax(settings).done(response => {
            let bookings= this.state.bookings;
            let index= bookings.findIndex(b => b.id == e.target.id);
            bookings.splice(index, 1);
            this.setState({bookings:bookings});
            window.location.reload();
            alert("Booking deleted")
        });
    }


    render () {
        if(! this.state.loaded)
            return(<div></div>)

        if(localStorage.getItem("admin")) {
            return(
                <div id="adminpage">
                    <div className="card">
                        <b>All bookings</b>

                        {this.state.bookings.map(el => 
                            <div className="card">
                                <div className="card-body" id="divNotConfirmed">
                                    <p>
                                        <b>n°</b> {el.id} &nbsp; | &nbsp;
                                        <b> Client id:</b> {el.customerId} &nbsp;
                                        <b> Room:</b> {el.roomId} &nbsp;
                                        <b> People:</b> {el.guests} &nbsp; | &nbsp;
                                        <b> checkin</b> {format(new Date(el.checkIn.slice(0,10)), "eee, dd MMM yyyy")} &nbsp; &nbsp;
                                        <b> checkout</b> {format(new Date(el.checkOut.slice(0,10)), "eee, dd MMM yyyy")} &nbsp; | &nbsp;
                                        <b> Total price</b> {el.price} €
                                    </p>
                                </div>

                                {isBefore(new Date(), new Date(el.checkIn)) 
                                            ? 
                                            (<button className="btn btn-danger" id={el.id} onClick={(e) => this.delete(e)}>DELETE</button>)
                                            : 
                                            (<div></div>)
                                }
                            </div>
                        )}
                    </div>
                </div>
            )
        }

        return(
            <div id="userpage">
                <div className="card">
                    <div className="card-body">
                        <h2>Benvenuto {this.props.user}!</h2>

                        <br/>
                        
                        <p>
                            <button className="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseWidthExample" aria-expanded="false" aria-controls="collapseWidthExample">
                                Show your personal data
                            </button>
                        </p>
                        
                        <div style={{"min-height": "120px"}}>
                            <div className="collapse collapse-horizontal" id="collapseWidthExample">
                                <div className="card card-body" style={{"width": "300px"}}>
                                    Email: samu.iorio98@gmail.com <br/>
                                    Address: via xxx, 1 Roma (RM)
                                </div>
                            </div>
                        </div>

                        <br/>

                        <p>
                            <button className="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseBook" aria-expanded="false" aria-controls="collapseBook">
                                Bookings to be confirmed
                            </button>
                        </p>

                        <div style={{"min-height": "120px"}}>
                            <div className="collapse collapse-horizontal" id="collapseBook">
                                {this.state.bookings.filter(el => !el.confirmed).map(el => 
                                    <div className="card">
                                        <div className="card-body" id="divNotConfirmed">
                                            <p>
                                                <b>n°</b> {el.id} &nbsp; | &nbsp;
                                                <b> Room:</b> {el.roomId} &nbsp; &nbsp;
                                                <b> People:</b> {el.guests} &nbsp; | &nbsp;
                                                <b> checkin</b> {format(new Date(el.checkIn.slice(0,10)), "eee, dd MMM yyyy")} &nbsp; &nbsp;
                                                <b> checkout</b> {format(new Date(el.checkOut.slice(0,10)), "eee, dd MMM yyyy")} &nbsp; | &nbsp;
                                                <b> Price</b> {el.price} €

                                                <br/>
                                                
                                                <p>
                                                    Please check in by {format(new Date(el.checkIn), "hh:mm")} a.m.
                                                    and checkout by {format(new Date(el.checkOut), "hh:mm")} a.m.
                                                </p>

                                                <button className="btn btn-success" onClick={() => this.confirmBooking(el)}>CONFIRM</button>

                                                {isBefore(new Date(), new Date(el.checkIn)) ? 
                                                    (<button className="btn btn-danger" id={el.id} onClick={(e) => this.delete(e)}>DELETE</button>) : 
                                                    (<div></div>)
                                                }
                                            </p>
                                        </div>
                                    </div>
                                )}
                            </div>
                        </div>

                        <br/><br/><br/>

                        <p>
                            <button className="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseBookConf" aria-expanded="false" aria-controls="collapseBookConf">
                                Bookings confirmed
                            </button>
                        </p>

                        <div style={{"min-height": "120px"}}>
                            <div className="collapse collapse-horizontal" id="collapseBookConf">
                                {this.state.bookings.filter(el => el.confirmed).map(el => 
                                    <div className="card">
                                        <div className="card-body" id="divConfirmed">
                                            <p>
                                                <b>n°</b> {el.id} &nbsp; | &nbsp;
                                                <b> Room:</b> {el.roomId} &nbsp; &nbsp;
                                                <b> People:</b> {el.guests} &nbsp; | &nbsp;
                                                <b> checkin</b> {format(new Date(el.checkIn.slice(0,10)), "eee, dd MMM yyyy")} &nbsp; &nbsp;
                                                <b> checkout</b> {format(new Date(el.checkOut.slice(0,10)), "eee, dd MMM yyyy")} &nbsp; | &nbsp;
                                                <b> Price</b> {el.price} €

                                                <br/>

                                                <p>
                                                    Please check in by {format(new Date(el.checkIn), "hh:mm")} a.m.
                                                    and checkout by {format(new Date(el.checkOut), "hh:mm")} a.m.
                                                </p>

                                                {isBefore(new Date(), new Date(el.checkIn)) ? 
                                                    (<button className="btn btn-danger" id={el.id} onClick={(e) => this.delete(e)}>DELETE</button>) : 
                                                    (<div></div>)
                                                }
                                            </p>
                                        </div>
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default UserDetail;