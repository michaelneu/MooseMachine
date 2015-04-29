package com.cs.moose.ui.controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@SuppressWarnings({"unchecked", "rawtypes"})
public class NullTableViewSelectionModel extends TableView.TableViewSelectionModel {

	public NullTableViewSelectionModel(TableView tableView) {
		super(tableView);
	}

	@Override
	public void clearAndSelect(int row, TableColumn column) {
		
	}

	@Override
	public void clearSelection(int row, TableColumn column) {
		
	}

	@Override
	public ObservableList getSelectedCells() {
		return FXCollections.emptyObservableList();
	}

	@Override
	public boolean isSelected(int row, TableColumn column) {
		return false;
	}

	@Override
	public void select(int row, TableColumn column) {
		
	}

	@Override
	public void selectAboveCell() {
		
	}

	@Override
	public void selectBelowCell() {
		
	}

	@Override
	public void selectLeftCell() {
		
	}

	@Override
	public void selectRightCell() {
		
	}

}
