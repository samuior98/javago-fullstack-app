import React from "react";
import 'bootstrap/dist/css/bootstrap.css';
import './Surrounding.css';

export default class Surrounding extends React.Component {

    constructor(props){
        super(props)
    }


    render() {
        return(
            <div id="divSurr">
                <header className="masthead" id="head">
                    <div className="container h-100">
                        <div className="row h-100 align-items-center">
                            <div className="col-12 text-center">
                                <h1 className="fw-normal">Surroundings of Alberobello</h1>
                                <p className="lead">what to see nearby</p>
                            </div>
                        </div>
                    </div>
                </header>

                <hr className="solid"/>

                <div className="container text-center">
                    <div className="row g-2">
                        
                        <div className="col-6">
                            <div className="p-3">
                                <div className="row gx-5">
                                    <div className="col">
                                        <div className="p-3">
                                            <div className="card mb-3 h-100" style={{"width": "540px", "height":"240px"}}>
                                                <div className="row g-0">
                                                    <div className="col-md-4">
                                                        <img src="https://i.pinimg.com/736x/56/1b/9a/561b9a00cd4f99132796850f0da8bda2.jpg" className="img-fluid rounded-start" alt="..." style={{"minHeight":"240px"}}/>
                                                    </div>

                                                    <div className="col-md-8">
                                                        <div className="card-body">
                                                            <h5 className="card-title">Cisternino</h5>
                                                            <p className="card-text">
                                                                Il borgo della Valle d'Itria, in Puglia, conserva molti tesori d'arte. Ma è il suo 
                                                                dedalo di vie e case bianche a stupire.
                                                                Il Comune fa parte di:
                                                                <b>"I Borghi più belli d'Italia"</b>
                                                                "Città slow"
                                                                "Città per la pace".
                                                                Il centro storico è un affascinante esempio di architettura spontanea, dove si trovano 
                                                                meravigliosi trulli perfettamente conservati e <b>palazzi storici</b>, come ad esempio il 
                                                                palazzo del Governatore, il palazzo Vescovile, la torre Capece. Il territorio attorno 
                                                                è caratterizzato dalla presenza dei Boschi Comunali e dalla Ciclovia dell'Acqua, 
                                                                il <b>secondo percorso ciclabile su acquedotto d'Europa</b>.
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="col-6">
                            <div className="p-3"><div className="row gx-5">
                                    <div className="col">
                                        <div className="p-3">
                                            <div className="card mb-3 h-100" style={{"width": "540px", "height":"240px"}}>
                                                <div className="row g-0">
                                                    <div className="col-md-4">
                                                        <img src="https://www.cooperativaserapia.it/new/wp-content/uploads/2023/07/trekking-urbano-Martina-Franca-coop-Serapia-2.jpeg" className="img-fluid rounded-start" alt="..." style={{"maxHeight":"240px", "minHeight":"240px"}}/>
                                                    </div>

                                                    <div className="col-md-8">
                                                        <div className="card-body">
                                                            <h5 className="card-title">Martina Franca</h5>
                                                            <p className="card-text ">
                                                                Situata nella <b>Val d’Itria</b> tra verde vegetazione della macchia mediterranea, 
                                                                Martina Franca è un borgo che è riuscito a mantenere intatto il 
                                                                suo fascino senza tempo. Il centro storico sorge su un’altura a 400 
                                                                m d’altitudine e camminare per le sue stradine è come visitare un <b>museo a cielo 
                                                                aperto</b>.
                                                                Ritrovamenti, risalenti all’epoca preistorica, dimostrano come questa terra 
                                                                fosse già abitata in periodi davvero antichi.
                                                                Quì si possono trovare molti prodotti locali, come vino, <b>olio</b>, formaggi, 
                                                                carni e salumi prodotti da <b>artigiani locali</b>.
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="col-6">
                            <div className="p-3"><div className="row gx-5">
                                    <div className="col">
                                        <div className="p-3">
                                            <div className="card mb-3 h-100" style={{"width": "540px", "height":"240px"}}>
                                                <div className="row g-0">
                                                    <div className="col-md-4">
                                                        <img src="https://www.paesidelgusto.it/wp-content/uploads/2022/01/zoosafari.jpg" className="img-fluid rounded-start" alt="..." style={{"maxHeight":"240px", "minHeight":"240px"}}/>
                                                    </div>

                                                    <div className="col-md-8">
                                                        <div className="card-body">
                                                            <h5 className="card-title">Parco Zoo di Fasano</h5>
                                                            <p className="card-text ">
                                                                Lo Zoosafari Fasano, incarnando la moderna vocazione dei Giardini Zoologici, 
                                                                svolge un ruolo di primo piano nell’attività di conservazione delle specie minacciate 
                                                                di estinzione. Con i suoi <b>140 ettari di estensione</b>, è il regno della biodiversità, 
                                                                un’oasi naturale che accoglie e conserva, in piena libertà, <b>animali</b> di tutte le specie.
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="col-6">
                            <div className="p-3"><div className="row gx-5">
                                    <div className="col">
                                        <div className="p-3">
                                            <div className="card mb-3 h-100" style={{"width": "540px", "height":"240px"}}>
                                                <div className="row g-0">
                                                    <div className="col-md-4">
                                                        <img src="https://www.vologratis.org/wp-content/uploads/2020/09/polignano-a-mare-lama-monachile.jpg" className="img-fluid rounded-start" alt="..." style={{"maxHeight":"240px","minHeight":"240px"}}/>
                                                    </div>

                                                    <div className="col-md-8">
                                                        <div className="card-body">
                                                            <h5 className="card-title">Polignano a Mare</h5>
                                                            <p className="card-text">
                                                                Polignano a Mare è una graziosa località balneare della costa adriatica 
                                                                pugliese che attira ogni anno visitatori per le sue <b>spiagge</b> e le sue bellissime 
                                                                grotte. <br/><br/> La città ha origini che sembrano risalire al Neolitico e pare che proprio 
                                                                qui a Polignano Dionigi II di Siracusa costruì nel IV secolo a.C. la storica città 
                                                                di Neapolis. Nel centro della città, infatti, si possono trovare numerosi reperti 
                                                                dell’<b>epoca romana</b> come una parte della via Traiana che è tuttora percorribile.
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="col-6">
                            <div className="p-3"><div className="row gx-5">
                                    <div className="col">
                                        <div className="p-3">
                                            <div className="card mb-3 h-100" style={{"width": "540px", "height":"240px"}}>
                                                <div className="row g-0">
                                                    <div className="col-md-4">
                                                        <img src="https://upload.wikimedia.org/wikipedia/commons/a/a8/Grotte_Castellana_03apr06_04.jpg" className="img-fluid rounded-start" alt="..." style={{"maxHeight":"240px", "minHeight":"240px"}}/>
                                                    </div>

                                                    <div className="col-md-8">
                                                        <div className="card-body">
                                                            <h5 className="card-title">Grotte di Castellana</h5>
                                                            <p className="card-text ">
                                                                Le Grotte di Castellana, un complesso di cavità sotterranee di origine carsica, 
                                                                di notevole interesse turistico, tra i <b>più belli e spettacolari d’Italia</b>, 
                                                                sono ubicate nel Comune di Castellana-Grotte.
                                                                La visita alle Grotte si snoda lungo un percorso di 3 Km: una straordinaria 
                                                                escursione guidata, a circa 70 metri di profondità, in uno scenario stupefacente, 
                                                                dove caverne dai nomi fantastici, canyon, profondi abissi, fossili, <b>stalattiti</b>, 
                                                                stalagmiti, concrezioni dalle forme incredibili e dai colori sorprendenti sollecitano 
                                                                la <b>fantasia di bambini e adulti</b>.
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="col-6">
                            <div className="p-3"><div className="row gx-5">
                                    <div className="col">
                                        <div className="p-3">
                                            <div className="card mb-3 h-100" style={{"width": "540px", "height":"240px"}}>
                                                <div className="row g-0">
                                                    <div className="col-md-4">
                                                        <img src="https://hips.hearstapps.com/hmg-prod/images/ostuni-white-town-street-view-the-house-with-the-royalty-free-image-1698392675.jpg?crop=0.66635xw:1xh;center,top&resize=640:*" className="img-fluid rounded-start" alt="..." style={{"maxHeight":"240px", "minHeight":"240px"}}/>
                                                    </div>

                                                    <div className="col-md-8">
                                                        <div className="card-body">
                                                            <h5 className="card-title">Ostuni</h5>
                                                            <p className="card-text ">
                                                                Ostuni, la <b>"Città bianca"</b>. Questo borgo svetta su una collina delle Murge.
                                                                <br/><br/>
                                                                Il bianco di Ostuni deriva dalla calce utilizzata fino dal medioevo perché 
                                                                era facile da reperire e perché quel colore riusciva a dar luce alla anguste 
                                                                strade del centro storico.
                                                                La città si visita (rigorosamente a piedi) in un giorno, prima di andare alla 
                                                                scoperta delle spiagge più belle della zona. 
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="col-6">
                            <div className="p-3"><div className="row gx-5">
                                    <div className="col">
                                        <div className="p-3">
                                            <div className="card mb-3 h-100" style={{"width": "540px", "height":"240px", "marginLeft": "350px"}}>
                                                <div className="row g-0">
                                                    <div className="col-md-4">
                                                        <img src="https://www.anticaricettadipuglia.it/wp-content/uploads/2017/01/antica-ricetta-di-puglia-prodotti-tipici-regionali_ricetta-orecchiette-cime-di-rapa.png" className="img-fluid rounded-start" alt="..." style={{"maxHeight":"240px", "minHeight":"240px"}}/>
                                                    </div>

                                                    <div className="col-md-8">
                                                        <div className="card-body">
                                                            <h5 className="card-title">RESTAURANT</h5>
                                                            <p className="card-text ">
                                                                Una <b>cucina</b> con origini povere ma di altissima qualità che oggi viene considerata 
                                                                una delle <b>migliori d'Italia</b>. <br/><br/> Tra le 20 migliori <b>ricette</b> tradizionali 
                                                                (e rivisitate) troviamo:
                                                                le <b>cozze fritte</b>, le <b>orecchiette con cime di rapa</b>, la tiella barese,
                                                                la puccia salentina e la <b>focaccia barese</b>. 
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <br/><br/>
                <hr className="solid"/>
                <br/>
            </div>
        );
    }

}