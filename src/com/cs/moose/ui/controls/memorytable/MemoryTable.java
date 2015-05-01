package com.cs.moose.ui.controls.memorytable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MemoryTable extends AnchorPane implements Initializable {
	private final double firstColumnPercentage = 75 / 570.0;
	@FXML
	private TableView table;
	
	public MemoryTable() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MemoryTable.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// initialize table columns
		table.setSelectionModel(new NullTableViewSelectionModel(table));
		Object[] columns = table.getColumns().toArray();
		
		for (int i = 0; i < columns.length; i++) {
			TableColumn column = (TableColumn)columns[i];
			
			column.setSortable(false);
			column.setCellValueFactory(new PropertyValueFactory<MemoryTableRow, String>("column" + i));
		}
		
		
		setMemory(new short[65536]);
		

		// add resize listener to keep columns the right size
		this.widthProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
				double width = newWidth.doubleValue() - 7,
						firstWidth = width * firstColumnPercentage,
						otherWidth = (width - firstWidth) / 9;
				
				TableColumn first = (TableColumn)columns[0];
				first.prefWidthProperty().set(firstWidth);
				
				for (int i = 1; i < columns.length; i++) {
					TableColumn column = (TableColumn)columns[i];
					column.prefWidthProperty().set(otherWidth);
				}
			}
		});
	}
	
	
	public void setMemory(short[] memory) {
		ObservableList<MemoryTableRow> data = FXCollections.observableArrayList();
		for (MemoryTableRow row : MemoryTableRow.getRows(memory)) {
			data.add(row);
		}
		
		table.setItems(data);
	}
}
