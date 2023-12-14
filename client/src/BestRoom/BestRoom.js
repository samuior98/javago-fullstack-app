import React from "react";
import 'bootstrap/dist/css/bootstrap.css';
import './BestRoom.css';
import {Outlet, Link} from "react-router-dom";

class BestRoom extends React.Component {

  constructor(props) {
    super(props);
  }


  render() {
    return (
        <div id="divBestRoom">
            <h4>most chosen rooms</h4>
            
            <div className="card-group">
                <div className="card" style={{width:"10rem"}}>
                    <img src="../img/img6.png" className="card-img-top" alt="..."/>
                    <div className="card-body">
                        <p className="card-text">
                            <Link to="/room/1" style={{textDecoration: 'none' }}>Room A</Link>
                        </p>
                    </div>
                </div>


                <div className="card" style={{width:"10rem"}}>
                    <img src="../img/img10.jpg" className="card-img-top" alt="..."/>
                    <div className="card-body">
                        <p className="card-text">
                            <Link to="/room/4" style={{ textDecoration: 'none' }}>Room D</Link>
                        </p>
                    </div>
                </div>


                <div className="card" style={{width:"10rem"}}>
                    <img src="../img/img11.jpg" className="card-img-top" alt="..."/>
                    <div className="card-body">
                        <p className="card-text">
                            <Link to="/room/6" style={{ textDecoration: 'none' }}>Room F</Link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
  }

}

export default BestRoom;