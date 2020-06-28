package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AnnoAvvistamenti;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<AnnoAvvistamenti> boxAnno;

    @FXML
    private ComboBox<String> boxStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleAnalizza(ActionEvent event) {
    	this.txtResult.clear();
    	String stato = this.boxStato.getValue();
    	if(stato == null) {
    		this.txtResult.appendText("Devi selezionare uno stato");
    		return;
    	}
    	
    	List<String> predecessori = this.model.getPredecessori(stato);
    	List<String> successori = this.model.getSuccessori(stato);
    	List<String> raggiungibili = this.model.getRaggiungibili(stato);
    	
    	this.txtResult.appendText("Predecessori:\n");
    	for(String s : predecessori)
    		this.txtResult.appendText(s+"\n");
    	
    	this.txtResult.appendText("Successori:\n");
    	for(String s : successori)
    		this.txtResult.appendText(s+"\n");
    	
    	this.txtResult.appendText("Raggiungibili:\n");
    	for(String s : raggiungibili)
    		this.txtResult.appendText(s+"\n");
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	AnnoAvvistamenti anno = this.boxAnno.getValue();
    	if(anno == null) {
    		this.txtResult.appendText("Devi selezionare un anno");
    		return;
    	}
    	
    	this.model.creaGrafo(anno.getYear());
    	this.txtResult.appendText("Grafo creato!\n");
    	this.txtResult.appendText("# Vertici: "+this.model.nVertici()+"\n");
    	this.txtResult.appendText("# Archi: "+this.model.nArchi()+"\n");
    	
    	this.boxStato.getItems().addAll(this.model.getStati());
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	this.txtResult.clear();
    	String stato = this.boxStato.getValue();
    	if(stato == null) {
    		this.txtResult.appendText("Devi selezionare uno stato");
    		return;
    	}
    	
    	List<String> percorsoMax = this.model.getPercorso(stato);
    	this.txtResult.appendText("Percorso massimo:\n");
    	
    	for(String s : percorsoMax)
    		this.txtResult.appendText(s+"-");
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(this.model.getAnniAvvistamenti());
	}
}
