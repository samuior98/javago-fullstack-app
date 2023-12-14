import React from "react";
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Footer.css';
import './lineicons.css';

class Footer extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
        <div id="divFooter">
           <div className="container">
                <h1 className="text-center">informations</h1>
                
                <hr/>

                <div className="row">
                    <div className="col-sm-8">
                      <iframe src="https://yandex.com/map-widget/v1/?um=constructor%3A73ed611bb54e2f37a3cd0337d9d1b00b59bb027b1d15bf916a6722ed9883c012&amp;source=constructor" width="820" height="410" frameborder="0"></iframe>
                    </div>

                    <div className="col-sm-4" id="contact2">
                        <h3>Where we are</h3>
                        
                        <hr align="left" width="50%"/>
                        
                        <h4 className="pt-2">Address</h4>
                        <i className="lni lni-map-marker"></i> Via A. Rossi, 1 - 70011 Alberobello (BA) | Puglia IT<br/>
                        
                        <h4 className="pt-2">Phone</h4>
                        <i className="lni lni-phone"></i><a> +39 331 2345678</a><br/>
                        
                        <h4 className="pt-2">Email</h4>
                        <i className="lni lni-envelope"></i><a> javazonb&b@email.com</a><br/>
                    </div>
                </div>
            </div>
        </div>
    );
  }

}

export default Footer;