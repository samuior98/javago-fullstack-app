import React from "react"
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import logoBB2 from "./logoBB2.png";
import './Navbar.css';
import {Outlet, Link} from "react-router-dom";

class Navbar extends React.Component {

    constructor(props) {
        super(props);
    }
    
    logout = () => {
        localStorage.removeItem("username");
        localStorage.removeItem("token");
        localStorage.removeItem("admin");
        window.location.reload();
        window.location.replace("/");
    }
    
    toFooter = () => {
        window.location.href= "#divFooter";
    } 

    
    render() {
        return(
            <div id="navbox">
                <nav className="navbar navbar-expand-lg navbar-light bg-light">
                    <Link to="/">
                        <a className="navbar-brand">
                            <img src={logoBB2} alt="logo"/>
                        </a>
                    </Link>
                    
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav mr-auto">
                            <li className="nav-item">
                                <Link to="/" style={{textDecoration : 'none'}}><a className="nav-link" id="home">Home</a></Link>
                            </li>
                            
                            <li className="nav-item">
                                <Link to="/aboutus" style={{textDecoration : 'none'}}><a className="nav-link">About us</a></Link>
                            </li>

                            <li className="nav-item dropdown">
                                <a className="nav-link dropdown-toggle" style={{cursor:"pointer"}} id="navbarDropdownMenuLink" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Rooms
                                </a>

                                <div className="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                    {this.props.rooms.map(
                                        r => (
                                            <Link to= {`/room/${r.id}`} style={{textDecoration : 'none'}}>
                                                <a key={r.id} className="dropdown-item">Room {r.name}</a>
                                            </Link>
                                        )
                                    )}
                                </div>
                            </li>


                            <li className="nav-item">
                                <a className="nav-link" onClick={this.toFooter} style={{cursor:"pointer"}}>Contacts</a>
                            </li>

                            {
                            localStorage.getItem("admin") ?
                                (<Link to="/seasons" style={{textDecoration : 'none'}}>
                                    <a className="nav-link">SEASONS</a>
                                 </Link>
                                )
                                :
                                (<li></li>)
                            }

                            <li>
                                {
                                this.props.needLogin ?
                                ""
                                :
                                (localStorage.getItem("admin") ?
                                    <Link to="/userdetail" id="detAdm">
                                        <button type="button" className="btn">
                                            ADMINISTRATION PAGES
                                        </button>
                                    </Link>
                                :
                                    <Link to="/userdetail" id="det">
                                        <button type="button" className="btn">
                                            PROFILE
                                        </button>
                                    </Link>
                                )
                                }
                            </li>

                            <li className="nav-item">
                                {
                                this.props.needLogin ?
                                    (<Link to="/login" id="btnLog">
                                        <button type="button" name="LOGIN" value="LOGIN" className="btn btn-success">
                                            LOGIN
                                        </button>
                                    </Link>)
                                    :
                                    (<button type="button" name="LOGOUT" value="LOGOUT" className="btn btn-danger" onClick={this.logout}>
                                        LOGOUT
                                     </button>)
                                }
                            </li>
                        </ul>
                    </div>
                </nav>

                <Outlet/>
            </div>
        );
    }

}

export default Navbar;