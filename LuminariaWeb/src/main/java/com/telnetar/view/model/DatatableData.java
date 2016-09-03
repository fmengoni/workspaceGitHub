package com.telnetar.view.model;

import java.util.ArrayList;
import java.util.List;

public class DatatableData {
	private List<List<String>> data;

	public List<List<String>> getData() {
		if(data == null){
			data = new ArrayList<List<String>>();
		}
		return data;
	}

	public void setData(List<List<String>> data) {
		this.data = data;
	}

}
