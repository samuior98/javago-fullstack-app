import React from "react";
import './App.css';
import Flickity from "react-flickity-component";
import "./flickity.css";
import BestRoom from './BestRoom/BestRoom.js';
import Footer from './Footer/Footer.js';



class Home extends React.Component {

  constructor(props) {
    super(props);
  }


  render() {
    return (
      <div id='box'>
        <Flickity
          className={'carousel'}
          elementType={'div'}
          options={{
            autoPlay:4500,
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
        
        <br/><br/>

        <BestRoom/>

        <hr className="solid"/>

        <Footer/>

        <hr className="solid"/>

        <br/>
      </div>
    );
  }

}

export default Home;