package it.polito.tdp.ufo.model;

import java.time.Year;

public class AnnoAvvistamenti {

	private Year year;
	private Integer cont;
	
	/**
	 * @param year
	 * @param cont
	 */
	public AnnoAvvistamenti(Year year, Integer cont) {
		this.year = year;
		this.cont = cont;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public Integer getCont() {
		return cont;
	}

	public void setCont(Integer cont) {
		this.cont = cont;
	}

	@Override
	public String toString() {
		return year + " " + cont;
	}
	
}
