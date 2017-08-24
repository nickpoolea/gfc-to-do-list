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
    /**
     * Get all the items from the file. 
     * @return A list of the items. If no items exist, returns an empty list.
     */
    public List<ToDoItem> getAll() {
        
        toDos = new ArrayList<ToDoItem>();
    	
		try (FileReader fileReader = new FileReader("todo.csv");
			 CSVParser csvParser = new CSVParser(fileReader, csvFormat)) {
			
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
		
		for (ToDoItem td: toDos) {
			System.out.println(td.getText());
		}
		
		nextId = toDos.size() + 1;
		return toDos;
        //return Collections.emptyList();
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
        
        for (ToDoItem td: toDos) {
        	data.add(new String[] {String.valueOf(td.getId()), 
        						   td.getText(), 
            					   String.valueOf(td.isComplete())});
        }
        
	        data.add(new String[] {String.valueOf(item.getId()), 
	        					   item.getText(), 
	        					   String.valueOf(item.isComplete())});
        
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
    	ToDoItem toDoToReturn = null;
        for (ToDoItem td: toDos) {
        	if (td.getId() == id) {
        		toDoToReturn = td;
        	}
        }
        return toDoToReturn;
    }

    /**
     * Updates the given to-do item in the file.
     * @param item The item to update.
     */
    public void update(ToDoItem item) {
    	
    	for (ToDoItem td: toDos) {
    		if (td.getId() == item.getId()) {
    			toDos.get(td.getId()-1).setComplete(item.isComplete());
    		}
    	}
    	
    	ArrayList<String[]> data = new ArrayList<String[]>() ;
        
        for (ToDoItem td: toDos) {
        	data.add(new String[] {String.valueOf(td.getId()), 
        						   td.getText(), 
            					   String.valueOf(td.isComplete())});
        }
        
    	try (FileWriter fileWriter = new FileWriter("todo.csv");
			 CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat)) {
				csvPrinter.printRecords(data);
		        csvPrinter.close();
		} catch (IOException e) {
			System.out.println("Error rading the file");
		}
    	
    }

}
