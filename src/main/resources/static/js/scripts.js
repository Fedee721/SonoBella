//VARIABLES CALENDARIO Y AGENDA----------------------------------------------------------------
let monthNames = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo','Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];

let currentDate = new Date();
let currentDay = currentDate.getDate();
let monthNumber = currentDate.getMonth();
let currentYear = currentDate.getFullYear();

let dates = document.getElementById('dates');
let month = document.getElementById('month');
let year = document.getElementById('year');
let horario = document.getElementById('horario');
let turno = document.getElementById('turno');
let day = document.getElementById('day');
let dayNumber = document.getElementById('dayNumber');

let prevMonthDOM = document.getElementById('prev__month');
let nextMonthDOM = document.getElementById('next__month');
let prevDayDOM = document.getElementById('prev__day');
let nextDayDOM = document.getElementById('next__day');
let dias = document.querySelectorAll('calendar__item')
let dayText;

month.textContent = monthNames[monthNumber];

year.textContent = currentYear.toString();

let daySelected = currentDay;

dayNumber.textContent = daySelected.toString();
day.textContent = weekDay();

let arrDays = [];

prevMonthDOM.addEventListener('click', ()=>lastMonth());
nextMonthDOM.addEventListener('click', ()=>nextMonth());
prevDayDOM.addEventListener('click', ()=>lastDay());
nextDayDOM.addEventListener('click', ()=>nextDay());

writeDay(dayNumber);
writeMonth(monthNumber);
addEventListenerToControls();


//FUNCIONES CALENDARIO----------------------------------------------------------------
function writeMonth(month) {
    for(let i=startDay(); i>0; i--) {
        dates.innerHTML += `<div class="calendar__dates calendar__item calendar__last-days">
        ${getTotalDays(monthNumber-1)-(i-1)}
        </div>`;
    }
    for(let i=1; i<=getTotalDays(month); i++) {
        if(i===currentDay) {
            dates.innerHTML += `<div class="calendar__dates calendar__item unselect calendar__today" id="item${i}">${i}</div>`;
        }else{
            dates.innerHTML += `<div class="calendar__dates calendar__item unselect" id="item${i}">${i}</div>`;
        }
        dayText= "item"+i;
        arrDays[i]=document.getElementById(dayText);
    }
}
function addEventListenerToControls() {
    let elControls = dates.querySelectorAll('.calendar__item');
    elControls.forEach(elControl => {
        elControl.addEventListener('click', ()=>selectDay(elControl.textContent));
    })
}
function getTotalDays(month) {
    if(month===-1) month=11;
    if(month==0 || month==2 || month==4 || month==6 || month==7 || month==9 || month==11) {
        return 31;
    }else if(month==3 || month==5 || month==8 || month==10) {
        return 30;
    }else{
        return isLeap()? 29 : 28;
    }
}
function isLeap() {
    return (currentYear % 4 === 0 && currentYear % 100!== 0 || currentYear % 400 === 0);
 
}
function startDay() {
    let start = new Date(currentYear, monthNumber, 1);
    return ((start.getDay()-1) === -1) ? 6 : (start.getDay()-1);
}
function weekDay() {
    let start = new Date(currentYear, monthNumber,daySelected);
    return (week(start));
}
function week(dayStart) {
    let dayN;
    if((dayStart.getDay()-1)===-1) {
        dayN=6;
    }else{dayN=dayStart.getDay()-1;}

    if(dayN===0) {return "Lunes";}
    else if(dayN===1) {return "Martes";} 
    else if(dayN===2) {return "Miercoles";}
    else if(dayN===3) {return "Jueves";}
    else if(dayN===4) {return "Viernes";}
    else if(dayN===5) {return "Sabado";}
    else if(dayN===6) {return "Domingo";}
}
function lastMonth() {
    if(monthNumber!==0) {
        monthNumber--;
    }else{
        monthNumber = 11;
        currentYear--;
    }
    setNewDate();
}
function nextMonth() {
    if(monthNumber!==11) {
        monthNumber++;
    }else{
        monthNumber = 0;
        currentYear++;
    }
    setNewDate();
}
function setNewDate() {
    currentDate.setFullYear(currentYear, monthNumber, currentDay);
    month.textContent = monthNames[monthNumber];
    year.textContent = currentYear.toString();
    dates.textContent = '';
    writeMonth(monthNumber);
}


//FUNCIONES AGENDA----------------------------------------------------------------
function writeDay(dayNumber) {
    for(let i=0; i<=23; i++) {
        horario.innerHTML += `<div class="agenda__hora">${i}:00</div>`;
        turno.innerHTML += `<div class="agenda__turno"></div>`;
    }
}
function selectDay(num) {
    daySelected=num.toString();
    setNewDay();
}
function lastDay() {
    daySelected--;
    setNewDay();
}
function nextDay() {
    daySelected++;
    setNewDay();
}
function styleDay() {
    for(let i=1; i<=getTotalDays(monthNumber); i++) {
        let idElement = "item"+i;
        let el = document.getElementById(idElement);
        if(el.className=="select"){
            el.className="unselect";
        }else{ 
            if(daySelected==i){
                el.className="select";
            }else{el.className="unselect";}
        }
    }
}
function setNewDay() {
    dayNumber.textContent=daySelected;
    day.textContent=weekDay();
    styleDay();
    writeDay(dayNumber);
    
}