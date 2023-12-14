import React from "react";
import 'bootstrap/dist/css/bootstrap.css';
import $ from "jquery";
import './Login.css';

class Login extends React.Component {

  constructor(props) {
    super(props);
    this.state= {tempCredentials : this.props.tempCredentials, dataRegister:{}, userInDb:"", admin:false};
  }


  handleChange = (e) => {
    let tempCredentials2 = this.state.tempCredentials;
    tempCredentials2[e.target.name] = e.target.value;
    this.setState({tempCredentials: tempCredentials2});
  }


  handleSubmit = (e) => {
    e.preventDefault();
    var settings = {
      "url": "http://localhost:8080/authenticate",
      "method": "POST",
      "timeout": 0,
      "data": JSON.stringify(this.state.tempCredentials),
      "headers": {
        "Content-Type": "application/json"
      }
    };
    
    $.ajax(settings).done(responseConToken => {
        localStorage.setItem("token", responseConToken.token);
        localStorage.setItem("username", this.state.tempCredentials.username);
        $.ajaxSetup({
          headers: {"Authorization":"Bearer "+localStorage.getItem('token')}
        });
        
        localStorage.removeItem("admin");
        this.checkAdmin();
        //this.props.change(false);
        //window.location.replace("/");
    }).fail(() => {
      this.setState({"pswErr": "Incorrect password or user not registered"});
      $('#formLogin')[0].reset();
    });
  }


  checkAdmin = () => {
    if(this.state.admin) {
      $.getJSON("/api/admin/" + localStorage.getItem("username") + "/email", (data) => {
        localStorage.setItem("admin", this.state.admin);
        this.props.change(false);
      }).fail(() => {
        alert("Invalid administrator credentials");
        this.logout();
      })
    } else {
      this.props.change(false);
    }
  }


  logout = () => {
    localStorage.removeItem("username");
    localStorage.removeItem("token");
    localStorage.removeItem("admin");
    window.location.reload();
    window.location.replace("/login");
  }

  showRegisterForm() {
    $('#divRegister').show();
  }


  //REG
  handleChangeRegForm = (e) => {
    let dataRegister2= this.state.dataRegister;
    dataRegister2[e.target.name]= e.target.value;
    this.setState({dataRegister:dataRegister2});
  }

  handleRegistration = (e) => {
    e.preventDefault();
    let usernameToInsert= this.state.dataRegister.username;

    var settings = {
      "url": "http://localhost:8080/api/customer/" + usernameToInsert + "/email",
      "method": "GET",
      "timeout": 0,
      "headers": {
        "Content-Type": "application/json"
      }
    };

    $.ajax(settings).done(response => {
      localStorage.setItem("respUser", response.username);
    }).fail(() => {
        this.setState({"userInDb" : "NO"});
        this.register();
      }
    );

    let respUsers= localStorage.getItem("respUser");
    if(respUsers === usernameToInsert) {
      this.setState({"userInDb" : "SI"});
      this.setState({"pswInc": "USER ALREADY REGISTERED!"})
    }
  }


  register= () => {
    var settings = {
      "url": "http://localhost:8080/register",
      "method": "POST",
      "timeout": 0,
      "data": JSON.stringify(this.state.dataRegister),
      "headers": {
        "Content-Type": "application/json"
      }
    };
    
    $.ajax(settings).done(responseConToken => {
      localStorage.setItem("token", responseConToken.token);
      localStorage.setItem("username", this.state.dataRegister.username);
      window.location.reload();
    }).fail(
      this.setState({"pswInc": "PASSWORD NOT VALID!"})
    );

    let dto= {"email": this.state.dataRegister.username, "password": this.state.dataRegister.password};
    var settings2 = {
      "url": "http://localhost:8080/api/customer",
      "method": "POST",
      "timeout": 0,
      "data": JSON.stringify(dto),
      "headers": {
        "Content-Type": "application/json"
      }
    };
    
    $.ajax(settings2).done(response => {
      alert("User registered")
    })
  }
  
  
  handleSwitch = (e) => {
    this.setState({admin : !this.state.admin});
    console.log(! this.state.admin);
  }


  render() {
    if(this.props.needLogin)
      return (
        <div id="divlogin">
            <form id="formLogin" onSubmit={this.handleSubmit}>
                <h3>Login</h3>
                <div className="form-group">
                    <label htmlFor="exampleInputEmail1">Email address or username</label>
                    <input type="text" className="form-control" id="exampleInputEmail1" required name="username" aria-describedby="emailHelp" placeholder="Enter your email or username" onChange={this.handleChange}/>
                    <small id="emailHelp" className="form-text text-muted">We'll never share your email with anyone else.</small>
                </div>

                <div className="form-group">
                    <label htmlFor="exampleInputPassword1">Password</label>
                    <input type="password" className="form-control" id="exampleInputPassword1" required name="password" placeholder="Enter your password" onChange={this.handleChange}/>
                </div>
                
                <div className="form-group form-check">
                    <input type="checkbox" className="form-check-input" id="exampleCheck1" onClick={this.handleSwitch}/>
                    <label className="form-check-label" htmlFor="exampleCheck1">Administration login</label>
                </div>

                <p>{this.state.pswErr}</p>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>

            <p>
                Not a member?  <button type="button" className="btn btn-primary btn-sm" onClick={this.showRegisterForm}>Register now</button>
            </p>
          
            <div id="divRegister" style={{display:"none"}}>
                <form onSubmit={this.handleRegistration} id="formReg">
                    <h3>Register</h3>
                    <div className="form-group">
                        <label htmlFor="emailreg">Email address</label>
                        <input type="text" className="form-control" id="emailreg" name="username" placeholder="Enter your email" onChange={this.handleChangeRegForm} required/> <br/>
                    </div>

                    <div className="form-group">
                        <label htmlFor="pswreg">Password</label>
                        <input type="password" className="form-control" name="password" id="pswreg" placeholder="Create password" onChange={this.handleChangeRegForm} required pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\]).{8,32}$"/> <br/>
                    </div>

                    <button type="submit" className="btn btn-success">Register</button>
                </form>

                <br/>

                <p> 
                    Minimum 8 characters <br/>
                    - one uppercase letter <br/>
                    - one lowercase letter <br/>
                    - one number <br/>
                    - one special character
                </p>
            </div>
        </div>
      );
  }
}

export default Login;