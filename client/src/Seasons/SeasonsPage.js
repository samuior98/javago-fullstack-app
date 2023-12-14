import React from "react";
import $ from "jquery"
import { format } from "date-fns";
import "./SeasonsPage.css";

class SeasonsPage extends React.Component {

    constructor(props){
        super(props);
        this.state= {loaded:false}
    }

    componentDidMount(){
        $.getJSON("/api/season", (data) => {
            this.setState({"seasons":data, loaded:true});
        })
    }

    render() {
        if(!this.state.loaded)
            return(<div></div>)
        
        return(
            <div id="divSeason">
                <div className="card">
                    <b id="title">All seasons</b>

                    {this.state.seasons.map(s => 
                        <div className="card">
                            <div className="card-body" id="divcardbody">
                                <p>
                                    id: <b>{s.id}</b> | &nbsp;
                                    <b>{s.name}:</b> &nbsp;
                                    Begin date: <b>{format(new Date(s.beginDate.slice(0,10)), "eee, dd MMM yyyy")}</b>  &nbsp;
                                    End date: <b>{format(new Date(s.endDate.slice(0,10)), "eee, dd MMM yyyy")}</b> &nbsp; | &nbsp;
                                    Price variation: <b>{s.factor * 100} %</b>
                                </p>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        )
    }
}

export default SeasonsPage;