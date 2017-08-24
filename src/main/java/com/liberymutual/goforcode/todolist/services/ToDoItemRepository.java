// ToDoItemRepository.java
package com.liberymutual.goforcode.todolist.services;

import java.io.*;
import java.util.*;

import org.apache.commons.csv.*;
import org.springframework.stereotype.Service;

import com.liberymutual.goforcode.todolist.models.ToDoItem;

@Service
public class ToDoItemRepository {

	private int nextId = 1;
	CSVFormat csvFormat = CSVFormat.DEFAULT;
	List<ToDoItem> toDos;
	ArrayList<String[]> toDosToPrint;

	/**
	 * Get all the items from the file.
	 * 
	 * @return A list of the items. If no items exist, returns an empty list.
	 */
	public List<ToDoItem> getAll() {
		// A list of to-do's to manage in the class
		toDos = new ArrayList<ToDoItem>();
		// A list to manage String values to print to CSV
		toDosToPrint = new ArrayList<String[]>();

		// Read the file if it exists
		try (FileReader fileReader = new FileReader("todo.csv");
				CSVParser csvParser = new CSVParser(fileReader, csvFormat)) {

		// Loop through the records, convert the values and
	    // add them to the toDo list
			for (CSVRecord csvRecord : csvParser) {

				ToDoItem toDoToAdd = new ToDoItem();
				toDoToAdd.setId(Integer.parseInt(csvRecord.get(0)));
				toDoToAdd.setText(csvRecord.get(1));
				toDoToAdd.setComplete(Boolean.valueOf(csvRecord.get(2)));
				toDos.add(toDoToAdd);

			}

		} catch (IOException e) {
			System.out.print("Could not read the file.");
		}

		
		// From the toDo list populate the string values to print
		for (ToDoItem td : toDos) {
			toDosToPrint.add(new String[] {String.valueOf(td.getId()), 
										   td.getText(), 
										   String.valueOf(td.isComplete())});
		}

		nextId = toDos.size() + 1;
		return toDos;
	}

	/**
	 * Assigns a new id to the ToDoItem and saves it to the file.
	 * 
	 * @param item
	 *            The to-do item to save to the file.
	 */
	public void create(ToDoItem item) {
		item.setId(nextId);
		nextId += 1;
		item.setComplete(false);
		toDosToPrint.add(new String[] {String.valueOf(item.getId()), 
									   item.getText(), 
									   String.valueOf(item.isComplete())});

		try (FileWriter fileWriter = new FileWriter("todo.csv");
				CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat)) {
			csvPrinter.printRecords(toDosToPrint);
		} catch (IOException e) {
			System.out.println("Error rading the file");
		}

	}

	/**
	 * Gets a specific ToDoItem by its id.
	 * 
	 * @param id
	 *            The id of the ToDoItem.
	 * @return The ToDoItem with the specified id or null if none is found.
	 */
	public ToDoItem getById(int id) {
		ToDoItem toDoToReturn = null;
		for (ToDoItem td : toDos) {
			if (td.getId() == id) {
				toDoToReturn = td;
			}
		}
		return toDoToReturn;
	}

	/**
	 * Updates the given to-do item in the file.
	 * 
	 * @param item
	 *            The item to update.
	 */
	public void update(ToDoItem item) {
		
		// Find the updated item and update it's values in the
		// list of to-do's and the list to print to the CSV
		for (ToDoItem td : toDos) {
			if (td.getId() == item.getId()) {
				toDos.get(td.getId() - 1).setComplete(item.isComplete());
				toDosToPrint.get(td.getId() - 1)[2] = String.valueOf(td.isComplete());
			}
		}
		
		// Re-print the whole list of to-do items. 
		try (FileWriter fileWriter = new FileWriter("todo.csv");
				CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat)) {
			csvPrinter.printRecords(toDosToPrint);
		} catch (IOException e) {
			System.out.println("Error rading the file");
		}

	}

}
