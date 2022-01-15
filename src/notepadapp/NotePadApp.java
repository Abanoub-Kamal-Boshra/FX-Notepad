/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package notepadapp;

import java.io.File;
import java.io.PrintWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Abanoub Kamal
 */
public class NotePadApp extends Application{

MenuItem newItem;
MenuItem openItem;
MenuItem saveItem;
MenuItem exitItem;
MenuItem undoItem;
MenuItem cutItem;
MenuItem copyItem;
MenuItem pasteItem;
MenuItem deleteItem;
MenuItem selectAllItem;

BorderPane pane1;

@Override
public void init(){

MenuBar bar = new MenuBar();
// file
Menu file = new Menu("File");
newItem = new MenuItem("New");
newItem.setAccelerator(KeyCombination.keyCombination("Alt+n"));
openItem = new MenuItem("Open");
openItem.setAccelerator(KeyCombination.keyCombination("Alt+o"));
saveItem = new MenuItem("Save");
saveItem.setAccelerator(KeyCombination.keyCombination("Alt+s"));
exitItem = new MenuItem("Exit");
exitItem.setAccelerator(KeyCombination.keyCombination("Alt+e"));
file.getItems().addAll(newItem, openItem, saveItem, exitItem);

// edit (undo, cut, copy, paste, delete, selectAll)
Menu edit = new Menu("Edit");
undoItem = new MenuItem("Undo");
undoItem.setAccelerator(KeyCombination.keyCombination("Alt+u"));
cutItem = new MenuItem("Cut");
cutItem.setAccelerator(KeyCombination.keyCombination("Alt+x"));
copyItem = new MenuItem("Copy");
copyItem.setAccelerator(KeyCombination.keyCombination("Alt+c"));
pasteItem = new MenuItem("Paste");
pasteItem.setAccelerator(KeyCombination.keyCombination("Alt+v"));
deleteItem = new MenuItem("Delete");
deleteItem.setAccelerator(KeyCombination.keyCombination("Alt+d"));
selectAllItem = new MenuItem("Select All");
selectAllItem.setAccelerator(KeyCombination.keyCombination("Alt+a"));
edit.getItems().addAll(undoItem, cutItem, copyItem, pasteItem, deleteItem, selectAllItem);

// help
Menu help = new Menu("Help");
MenuItem textHelp = new MenuItem("Auther: Abanoub Kamal");
help.getItems().addAll(textHelp);

// pane1 for pane top
pane1 = new BorderPane();
pane1.setTop(bar);

bar.getMenus().addAll(file, edit, help);

}

@Override
public void start(Stage primaryStage){

    TextArea myTextArea = new TextArea();
    pane1.setCenter(myTextArea);

    newItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent ev){

            myTextArea.clear();

        }
    });




    Scene myScene = new Scene(pane1, 400, 400);

    primaryStage.setTitle("FX Notepad");
    primaryStage.setScene(myScene);
    primaryStage.show();

}


public void saveFile(File file, TextArea myTextArea){

}

    
}
