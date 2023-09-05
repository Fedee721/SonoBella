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
let horaSel;
let reservado = [];
let reservadoTituloOferta = [];
let reservadoNombre = [];
let controlForm = document.getElementById('controlForm');
let agendaControlForm = controlForm.getAttribute('agenda');


let prevMonthDOM = document.getElementById('prev__month');
let nextMonthDOM = document.getElementById('next__month');
let prevDayDOM = document.getElementById('prev__day');
let nextDayDOM = document.getElementById('next__day');

month.textContent = monthNames[monthNumber];
year.textContent = currentYear.toString();

let daySelected = currentDay;
let hourSelected = 0;
let turnoSelected = 0;

dayNumber.textContent = daySelected.toString();
day.textContent = weekDay();

prevMonthDOM.addEventListener('click', ()=>lastMonth());
nextMonthDOM.addEventListener('click', ()=>nextMonth());
prevDayDOM.addEventListener('click', ()=>lastDay());
nextDayDOM.addEventListener('click', ()=>nextDay());

writeDay(dayNumber);
writeMonth(monthNumber);
addEventListenerToControls();
reservar();
//FUNCIONES CALENDARIO----------------------------------------------------------------
function writeMonth(month) {
    for(let i=startDay(); i>0; i--) {
        dates.innerHTML += `<div class="calendar__dates calendar__item calendar__last-days">
        ${getTotalDays(monthNumber-1)-(i-1)}</div>`;
    }
    for(let i=1; i<=getTotalDays(month); i++) {
        if(i===currentDay) {
            if(month==new Date().getMonth()) {
                dates.innerHTML += `<div class="calendar__dates calendar__item unselect calendar__today" id="item${i}">${i}</div>`;
            }else{
                dates.innerHTML += `<div class="calendar__dates calendar__item unselect" id="item${i}">${i}</div>`;
            }
        }else{
            dates.innerHTML += `<div class="calendar__dates calendar__item unselect" id="item${i}">${i}</div>`;
        }
    }
}
function addEventListenerToControls() {
    let elControls = dates.querySelectorAll('.calendar__item');
    elControls.forEach(elControl => {
        elControl.addEventListener('click', ()=>selectDay(elControl.textContent));
    })
    let hControls = horario.querySelectorAll('.agenda__hora');
    hControls.forEach(hControl => {
        hControl.addEventListener('click', ()=>selectHour(hControl.id));
    })
    let tControls = turno.querySelectorAll('.agenda__turno');
    tControls.forEach(tControl => {
        tControl.addEventListener('click', ()=>selectHour(tControl.id));
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
    addEventListenerToControls();
    setNewDay();
    reservar();
}
//FUNCIONES AGENDA----------------------------------------------------------------
function writeDay(dayNumber) { 
    horario.innerHTML += `<div class="aunselect agenda__hora" id="horario_10">10:00</div>`;
    horario.innerHTML += `<div class="aunselect agenda__hora" id="horario_12">12:30</div>`;
    horario.innerHTML += `<div class="aunselect agenda__hora" id="horario_15">15:00</div>`;
    horario.innerHTML += `<div class="aunselect agenda__hora" id="horario_17">17:00</div>`;
    turno.innerHTML += `<div class="aunselect agenda__turno"  id="turno_10"></div>`;
    turno.innerHTML += `<div class="aunselect agenda__turno"  id="turno_12"></div>`;
    turno.innerHTML += `<div class="aunselect agenda__turno"  id="turno_15"></div>`;
    turno.innerHTML += `<div class="aunselect agenda__turno"  id="turno_17"></div>`;
}
function turnosReservados() {
    let reserva = document.getElementById('reserva');
    //AÑO
    let resAnoControl = reserva.querySelectorAll('.res__ano');
    resAnoControl.forEach(rAControl => {
        rAControl.className = `res__ano res__${rAControl.textContent}`;
    })
    //MES
    let resMesControl = reserva.querySelectorAll('.res__mes');
    resMesControl.forEach(rMControl => {
        rMControl.className = `res__mes res__${rMControl.textContent}`;
    })
    //DIA
    let resDiaControl = reserva.querySelectorAll('.res__dia');
    resDiaControl.forEach(rDControl => {
        rDControl.className = `res__dia res__${rDControl.textContent}`;
    })
    //HORARIO
    let resHorarioControl = reserva.querySelectorAll('.res__horario');
    resHorarioControl.forEach(rHControl => {
        rHControl.className = `res__horario res__${rHControl.textContent}`;
    })
    reservado[daySelected,10]="noReservado";
    reservado[daySelected,12]="noReservado";
    reservado[daySelected,14]="noReservado";
    reservado[daySelected,15]="noReservado";
    reservado[daySelected,17]="noReservado";
    checkearReservados(resAnoControl, resMesControl, resDiaControl, resHorarioControl)
}
function checkearReservados(resAnoControl, resMesControl, resDiaControl, resHorarioControl) {
    resAnoControl.forEach(rAControl => {
        if(rAControl.textContent.toString()===currentYear.toString()) {
            resMesControl.forEach(rMControl => {
                if(rMControl.textContent.toString()===monthNumber.toString()) {
                    resDiaControl.forEach(rDControl => {
                        if(rDControl.textContent.toString()===daySelected.toString()) {
                            resHorarioControl.forEach(rHControl => {
                                if((rHControl.getAttribute('turno')===rDControl.getAttribute('turno'))
                                && (rDControl.getAttribute('turno')===rMControl.getAttribute('turno'))
                                && (rMControl.getAttribute('turno')===rAControl.getAttribute('turno'))) {
                                    reservado[daySelected,rHControl.textContent] = "reservado";
                                    reservadoTituloOferta[daySelected,rHControl.textContent] = rHControl.getAttribute('titulooferta');
                                    reservadoNombre[daySelected,rHControl.textContent] = rHControl.getAttribute('nombre');
                                    if(reservado[daySelected,14]==="reservado") {
                                        reservado[daySelected,15]="reservado";
                                        reservado[daySelected,17]="reservado";
                                        reservadoTituloOferta[daySelected,15] = rHControl.getAttribute('titulooferta');
                                        reservadoNombre[daySelected,15] = rHControl.getAttribute('nombre');
                                        reservadoTituloOferta[daySelected,17] = rHControl.getAttribute('titulooferta');
                                        reservadoNombre[daySelected,17] = rHControl.getAttribute('nombre');
                                    }
                                }
                            })
                        }
                    })
                }
            })
        }
    })
}
function selectDay(num) {
    daySelected=num;
    hourSelected=0;
    turnoSelected=0;
    setNewDay();
}
function lastDay() {
    if(parseInt(daySelected)!==1){
        daySelected--;
    }else{
        monthNumber--;
        setNewDate();
        daySelected=getTotalDays(monthNumber);
    }
    setNewDay();
}
function nextDay() {
    if(parseInt(daySelected)!==getTotalDays(monthNumber)){
        daySelected++;
    }else{
        monthNumber++;
        setNewDate();
        daySelected=1;
    }
    setNewDay();
}
function styleDay() {
    for(let i=1; i<=getTotalDays(monthNumber); i++) {
        let idElement = "item"+i;
        let el = document.getElementById(idElement);
        if(el.className=="select"){
            if(daySelected!=i){
                el.className="unselect";
            }
        }else{ 
            if(daySelected==i){
                el.className="select";
            }else{el.className="unselect";}
        }
        if(i===currentDay) {
            if(monthNumber==new Date().getMonth()) {
                el.className="calendar__today";
            }
        }
    }
}
function styleHour() {
    for(let i=10; i<=17; i++) {
        if((i!=10) && (i!=12) && (i!=15) && (i!=17)) {
            continue;
        }else{
            let idHora = "horario_"+i;
            let idTurno = "turno_"+i;
            let elHora = document.getElementById(idHora);
            let elTurno = document.getElementById(idTurno);
            if (reservado[daySelected,i]!="reservado") {
                if((elHora.className=="agenda__hora aselect") || (elTurno.className=="agenda__turno aselect")) {
                    elHora.className="agenda__hora aunselect";
                    elTurno.className="agenda__turno aunselect";
                }else{
                    if((hourSelected.toString()==idHora) || (turnoSelected.toString()==idTurno)) {
                        elHora.className="agenda__hora aselect";
                        elTurno.className="agenda__turno aselect";
                        hourSelected=i;
                        turnoSelected=i;
                    }else{
                        elHora.className="agenda__hora aunselect";
                        elTurno.className="agenda__turno aunselect";
                    }
                }
            }else{
                elHora.className="agenda__hora aunselect reservado";
                elTurno.className="agenda__turno aunselect reservado";
                if (agendaControlForm=="false") 
                    {elTurno.textContent="";}else{
                    elTurno.textContent = reservadoTituloOferta[daySelected,i]+": reservado por "+reservadoNombre[daySelected,i];
                }
            }
        }
    }
}
function selectHour(num) {
    if(reservado[daySelected,num]==="reservado") {
        hourSelected=0;
        turnoSelected=0;
    }else{
        hourSelected=num;
        turnoSelected=num;
    }
    setNewDay();
}
function setNewDay() {
    dayNumber.textContent=daySelected.toString();
    day.textContent=weekDay();
    horario.textContent = '';
    turno.textContent = '';
    writeDay(dayNumber);
    styleDay();
    turnosReservados();
    styleHour();
    addEventListenerToControls();
    reservar();
}
//FUNCION RESERVA----------------------------------------------------------------
function reservar() {
    let pDay = parseInt(daySelected);
    let pMonth = parseInt(monthNumber);
    let pYear = parseInt(currentYear);
    let pHour;
    if (agendaControlForm=="false") {
        let esCurso = document.getElementById('esCurso');
        let esCursoControl = esCurso.getAttribute('ec');
        if(esCursoControl=="true") { pHour=14;}
        else{ pHour = parseInt(hourSelected);}
        let info = document.getElementById('info__reserva');
        info.innerHTML=`
        <div class="form-group mb-3 border-black">
            <label hidden for="formdia" class="mb-2">Dia</label> 
            <input hidden value="${pDay}" type="Number" id="formdia" class="form-control border-black" name="dia">
        </div>
        <div class="form-group mb-3 border-black">
            <label hidden for="formmes" class="mb-2">Mes</label> 
            <input hidden value="${pMonth}" type="Number" id="formmes" class="form-control border-black" name="mes">
        </div>
        <div class="form-group mb-3 border-black">
            <label hidden for="formano" class="mb-2">Año</label> 
            <input hidden value="${pYear}" type="Number" id="formano" class="form-control border-black" name="ano">
        </div>
        <div class="form-group mb-3 border-black">
            <label hidden for="formhora" class="mb-2">Hora</label> 
            <input hidden value="${pHour}" type="Number" id="formhora" class="form-control border-black" name="hora">
        </div>`
    }else{ pHour = parseInt(hourSelected);}
}