import React from "react";
import {format, addMonths, subMonths, addDays, startOfWeek, endOfWeek, startOfMonth, endOfMonth, isSameMonth, isSameDay, isBefore, isAfter, addHours, differenceInDays} from "date-fns";
import it from 'date-fns/locale/it';
import $ from "jquery";
import "./Calendar.css";

class Calendar extends React.Component 
{
  componentDidMount()
  {
    $("#date1").val("dd/mm/yyyy");
    $("#date2").val("dd/mm/yyyy");
    $("#calendarbox").hide();
  }

  constructor(props) 
  {
    super(props);
    
    this.state = {
      currentMonth: new Date(),
      selectedDate: new Date(),
      guests:1,
      checkInTime:"09:00",
      checkOutTime:"09:00"
    }
  }

  showCalendar = () =>
  {
    $("#calendarbox").slideToggle()
  }

  renderHeader(flag) 
  {
    const locale = it;
    const dateFormat = "MMMM yyyy";  
    return (
      <div className="header row flex-middle">
        <div className="col col-start">
          {!flag && 
            <div className="icon" onClick={this.prevMonth}>chevron_left</div>
          }
        </div>
        <div className="col col-center">
          <span>
            {format(addMonths(this.state.currentMonth, (flag ? 1 : 0)), dateFormat)}
          </span>
        </div>
        <div className="col col-end">
          {flag &&   
            <div className="icon" onClick={this.nextMonth}>chevron_right</div>       
          }
        </div>
      </div>
    );
  }

  renderDays(flag) 
  {
    const locale = it;
    const dateFormat = "eee";
    const days = [];  let startDate = startOfWeek(addMonths(this.state.currentMonth, (flag ? 1 : 0)));  
    for (let i = 0; i < 7; i++) 
    {
      days.push(
        <div className="col col-center" key={i} style={{fontSize:"105%",color:"rgb(130,130,130)"}}>
          {format(addDays(startDate, i), dateFormat)}
        </div>
      );
    }  return <div className="days row">{days}</div>;
  }

  renderCells(flag) 
  {
    const { currentMonth, selectedDate} = this.state;
    const checkIn = new Date(this.state.checkIn);
    const checkOut = new Date(this.state.checkOut);
    const monthStart = startOfMonth(addMonths(this.state.currentMonth, (flag ? 1 : 0)));
    const monthEnd = endOfMonth(monthStart);
    const startDate = startOfWeek(monthStart);
    const endDate = endOfWeek(monthEnd);

    const dateFormat = "d";
    const rows = [];
    let days = [];
    let day = startDate;
    let formattedDate = "";

    while (day <= endDate) {
      for (let i = 0; i < 7; i++) {
        formattedDate = format(day, dateFormat);
        const cloneDay = day;

        if(isAfter(day, checkIn) && isBefore(day, checkOut) && !this.isAvailable(day))
        {
          this.setState({checkOut:null});
          $("#date2").val("dd/mm/yyyy");
        }
        days.push(
          <div
            className={`col cell ${
                (isSameDay(day, checkOut) && isSameMonth(day, monthStart)) ? "bookingend disabled"
                : (isAfter(day, checkIn) && isBefore(day, checkOut) && isSameMonth(day, monthStart) && this.isAvailable(day)) ||
                  (isSameDay(day, checkIn) && isSameMonth(day, monthStart)) ? "bookingrange disabled"
                : !isSameMonth(day, monthStart) || !this.isAvailable(day) ? "disabled"
                : isSameDay(day, selectedDate) ? "selected" 
                : ""
            }`}
            key={day}
            onClick={() => this.onDateClick(addDays(cloneDay, 1).toISOString().slice(0, 10))}
          >
            <span className="number">{formattedDate}</span>
            <span className="bg">{formattedDate}</span>
          </div>
        );
        day = addDays(day, 1);
      }
      rows.push(
        <div className="row" key={day}>
          {days}
        </div>
      );
      days = [];
    }
    return <div className="body">{rows}</div>;
  }

  nextMonth = () => 
  {
    this.setState({
      currentMonth: addMonths(this.state.currentMonth, 1)
    });
  };prevMonth = () => 
  {
    this.setState({
      currentMonth: subMonths(this.state.currentMonth, 1)
    });
  };

  isAvailable = day => //2023-11-08T23:00:00.000Z
  {
    let ref = addHours(new Date(day), 1);
    let toCheck = ref.getDate();
    let refDate = ref.toISOString().slice(0, 8) +"01"; //2023-11-01

    if(isBefore(ref, new Date()))
      return false;

    if(this.state.checkIn && isBefore(ref, addDays(new Date(this.state.checkIn), 1)))
      return false
    
    let monthAvailability = this.props.availability.filter(el => el.ref.slice(0, -1)==refDate)[0];
    if(!monthAvailability)
      return false;

    monthAvailability = monthAvailability.availability.split("");
    if(monthAvailability[toCheck-1]=="1")
      return false;

    return true;
  }

  onDateClick = day => 
  {
    const locale = it;
    let formatted = format(new Date(day), "eeee, d MMMM yyyy");

    if(!this.state.checkIn || (this.state.checkIn && this.state.checkOut))
    {
      $("#date1").val(formatted)
      this.setState({
        checkIn: day,
        checkOut: null
      });
    }
    else  
    {
      if(differenceInDays(new Date(day), new Date(this.state.checkIn))>21)
      {
        alert("Invalid booking. Stay must not exceed 21 days. (checkout not included)");
        return;
      }
      $("#date2").val(formatted)
      this.setState({
        checkOut: day,
      });
    };
  }

  clearDates = (e) =>
  {
    if(e.target.id=="date1")
    {
      $("#date1").val("dd/mm/yyyy");
      $("#date2").val("dd/mm/yyyy");
      this.setState({
        checkIn: null,
        checkOut: null
      });
    }
    else
    {
      $("#date2").val("dd/mm/yyyy");
      this.setState({
        checkOut: null
      });
    }
  }

  handleGuests = (e) =>
  {
    let guests = e.target.value;
    this.setState({guests:guests});
  }

  handleTime = (e) =>
  {
    let time = e.target.value;

    if(e.target.id=="time1")
      this.setState({checkInTime:time});
    else
      this.setState({checkOutTime:time});
  }

  submitBooking = () =>
  {
    if(localStorage.getItem("admin"))
    {
      alert("Attention, login as customer to book a room");
      return;
    }
    
    if(!this.state.checkIn || !this.state.checkOut)
    {
      alert("Attention, please fill all fields before click 'confirm'.")
      return;
    }

    let booking =
      {
        checkIn: this.state.checkIn +"T" +this.state.checkInTime +":00",
        checkOut: this.state.checkOut +"T" +this.state.checkOutTime  +":00",
        guests: this.state.guests,
        notes: "",
        price: 0.0,
        confirmed: false,
        id_customer: this.props.iduser,
        id_room: this.props.idroom
      };
    console.log(booking);

    var settings = {
      "url": "/api/booking/customer/" + this.props.iduser + "/room/" + this.props.idroom,
      "method": "POST",
      "timeout": 0,
      "data": JSON.stringify(booking),
      "headers": {
        "Content-Type": "application/json"
      }
    };

    
    $.ajax(settings).done(response => {
      console.log(response);
      window.location.replace("/");
      alert("Bookings saved succesfully, go to your profile page to confirm.");
    }).fail(() => {
      alert("Please login to book a room")
    }
    )
  }


  render() {
    return (     
      <div id="bookingbox">
        <img id="calendaricon1" src="/img/calendaricon.png" onClick={this.showCalendar}/>
        <input id="date1" className="clearbtn" type="button" onClick={(e) => this.clearDates(e)}/>
        <input id="time1" className="time"type="time" value="09:00"/>
        <input id="date2" className="clearbtn" type="button" onClick={(e) => this.clearDates(e)}/>
        <input id="time2" className="time" type="time" value="09:00" onChange={(e) => this.handleTime(e)}/>
        <img id="guest" src="/img/guest.png"/>
        <input id="guests" type="number" defaultValue={1} min="1" max={this.props.maxcapacity} onChange={(e) => this.handleGuests(e)}/>
        <input id="submit" type="button" className="submit" value="BOOK NOW" onClick={this.submitBooking}/>
        <div id="calendarbox">
          <div className="calendar">
            {this.renderHeader()}
            {this.renderDays()}
            {this.renderCells()}
          </div>
          <div className="calendar">
            {this.renderHeader(true)}
            {this.renderDays(true)}
            {this.renderCells(true)}
          </div>
        </div>
      </div>   
    );
  }
}

export default Calendar;