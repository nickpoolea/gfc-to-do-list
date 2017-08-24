// ToDoItemRepository.java
package com.liberymutual.goforcode.todolist.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.commons.csv.*;
import org.springframework.stereotype.Service;

import com.liberymutual.goforcode.todolist.models.ToDoItem;

@Service
public class ToDoItemRepository {

    private int nextId = 1;
    CSVFormat csvFormat = CSVFormat.DEFAULT;


    /**
     * Get all the items from the file. 
     * @return A list of the items. If no items exist, returns an empty list.
     */
    public List<ToDoItem> getAll() {
        
		try (CSVParser csvParser = CSVParser.parse("to-do.csv", csvFormat)) {
			for (CSVRecord csvRecord : csvParser) {
	    		System.out.println(csvRecord);
	    	 }
		} catch (IOException e) {
	
		}
    	 
        return Collections.emptyList();
    }
    /**
     * Assigns a new id to the ToDoItem and saves it to the file.
     * @param item The to-do item to save to the file.
     */
    public void create(ToDoItem item) {
        item.setId(nextId);
        nextId += 1;
        item.setComplete(false);
        
        
        ArrayList<String[]> data = new ArrayList<String[]>() ;
        data.add(new String[] {String.valueOf(item.getId()), 
        					   item.getText(), 
        					   String.valueOf(item.getIsIncomplete())});
        System.out.println(data);
        
    	try (FileWriter fileWriter = new FileWriter("todo.csv");
			 CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat)) {
				csvPrinter.printRecords(data);
		        csvPrinter.close();
		} catch (IOException e) {
			System.out.println("Error rading the file");
		}


    }

    /**
     * Gets a specific ToDoItem by its id.
     * @param id The id of the ToDoItem.
     * @return The ToDoItem with the specified id or null if none is found.
     */
    public ToDoItem getById(int id) {
        // Replace this with something meaningful
        return null;
    }

    /**
     * Updates the given to-do item in the file.
     * @param item The item to update.
     */
    public void update(ToDoItem item) {
        // Fill this in with something meaningful
    }

}
