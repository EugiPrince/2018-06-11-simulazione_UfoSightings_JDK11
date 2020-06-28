package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		
		List<AnnoAvvistamenti> lista = new ArrayList<>();
		lista = m.getAnniAvvistamenti();
		
		for(AnnoAvvistamenti a : lista) 
			System.out.println(a);
	}

}
