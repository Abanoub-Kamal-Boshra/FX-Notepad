/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package notepadapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    MenuItem textHelp;

    BorderPane pane1;

    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void init(){

        MenuBar bar = new MenuBar();
        // file
        Menu file = new Menu("File");
        newItem = new MenuItem("New");
        newItem.setAccelerator(KeyCombination.keyCombination("Alt+n"));
        openItem = new MenuItem("Open");
        openItem.setAccelerator(KeyCombination.keyCombination("Alt+o"));
//        Image image = new Image("../images/icon.png", 4, 4, false, false);
//        openItem.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../images/icon.png"))));
        saveItem = new MenuItem("Save");
        saveItem.setAccelerator(KeyCombination.keyCombination("Alt+s"));
        exitItem = new MenuItem("Exit");
        exitItem.setAccelerator(KeyCombination.keyCombination("Alt+e"));
        file.getItems().addAll(newItem, openItem, saveItem, exitItem);
        // create sperator menu item
        SeparatorMenuItem fileSep = new SeparatorMenuItem();
        file.getItems().add(3, fileSep);

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
        // create sperator menu item
        SeparatorMenuItem editSep1 = new SeparatorMenuItem();
        SeparatorMenuItem editSep2 = new SeparatorMenuItem();
        edit.getItems().add(1, editSep1);
        edit.getItems().add(6, editSep2);

        // help
        Menu help = new Menu("Help");
        textHelp = new MenuItem("About FX NotePad");
        help.getItems().addAll(textHelp);

        // copy write
        Label copyright = new Label(" copyright \u00a9 Abanoub Kamal 2022");

        // pane1 for pane top
        pane1 = new BorderPane();
        pane1.setTop(bar);
        pane1.setBottom(copyright);

        bar.getMenus().addAll(file, edit, help);
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void start(Stage primaryStage){

        TextArea myTextArea = new TextArea();
        pane1.setCenter(myTextArea);

        newItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                if(myTextArea.getText() != null){
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("FX NotePad");
                    alert.setHeaderText("Do you want to save changes?");
                    ButtonType buttonTypeSave = new ButtonType("Save");
                    ButtonType buttonTypeDontSave = new ButtonType("Don't Save");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeDontSave, buttonTypeCancel);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeSave){

                        FileChooser fileChooser = new FileChooser();
                        //Set extension filter for text files
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                        fileChooser.getExtensionFilters().add(extFilter);
                        //Show save file dialog
                        File myFile = fileChooser.showSaveDialog(primaryStage);
                        //Its important towrite showSaveDialog!!!
                        if(myFile != null){
                            try {
                                myFile.createNewFile();
                            } catch(IOException ex){
                                ex.fillInStackTrace();
                            }
                            String filePath = myFile.getAbsolutePath();

                            String fileContent = myTextArea.getText();
                            DataOutputStream dataOut = null;
                            try {
                                dataOut = new DataOutputStream( new FileOutputStream(filePath));
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(NotePadApp.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                dataOut.writeUTF(fileContent);
                                dataOut.close();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(NotePadApp.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(NotePadApp.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else if (result.get() == buttonTypeDontSave) {
                        // ... user chose
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }
                }
                myTextArea.clear();
            }    
        });

        openItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                FileChooser fileChooser = new FileChooser();
                //Set extension filter for text files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                //Show 
                File myFile = fileChooser.showOpenDialog(primaryStage);
                if(myFile != null){       // myFile.isFile()
                    if(myFile.exists()){
                        String filePath = myFile.getAbsolutePath();
                        DataInputStream dataIn = null;
                        try{
                            dataIn = new DataInputStream(new FileInputStream(filePath)  );
                        } catch(IOException ex){
                            ex.fillInStackTrace();
                        }

                        try{
                            while(dataIn.available()>0){
                                String k = dataIn.readUTF();
                                myTextArea.appendText(k+" ");
                            }
                            dataIn.close();
                        }catch(IOException ex){

                        }
                    }
                }
            }
        });

        saveItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                FileChooser fileChooser = new FileChooser();
                //Set extension filter for text files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                //Show save file dialog
                File myFile = fileChooser.showSaveDialog(primaryStage);
                //Its important towrite showSaveDialog!!!
                if(myFile  != null){
                    try {
                        myFile.createNewFile();
                    } catch(IOException ex){
                        ex.fillInStackTrace();
                    }
                    String filePath = myFile.getAbsolutePath();

                    String fileContent = myTextArea.getText();
                    DataOutputStream dataOut = null;
                    try {
                        dataOut = new DataOutputStream( new FileOutputStream(filePath));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(NotePadApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        dataOut.writeUTF(fileContent);
                        dataOut.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(NotePadApp.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(NotePadApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    System.out.println("entrer a file");
                }
            }

        });

        exitItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                if(myTextArea.getText() != null){
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("FX NotePad");
                    alert.setHeaderText("Do you want to save changes?");
                    ButtonType buttonTypeSave = new ButtonType("Save");
                    ButtonType buttonTypeDontSave = new ButtonType("Don't Save");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeDontSave, buttonTypeCancel);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeSave){

                        FileChooser fileChooser = new FileChooser();
                        //Set extension filter for text files
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                        fileChooser.getExtensionFilters().add(extFilter);
                        //Show save file dialog
                        File myFile = fileChooser.showSaveDialog(primaryStage);
                        //Its important towrite showSaveDialog!!!
                        if(myFile != null){
                            try {
                                myFile.createNewFile();
                            } catch(IOException ex){
                                ex.fillInStackTrace();
                            }
                            String filePath = myFile.getAbsolutePath();

                            String fileContent = myTextArea.getText();
                            DataOutputStream dataOut = null;
                            try {
                                dataOut = new DataOutputStream( new FileOutputStream(filePath));
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(NotePadApp.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                dataOut.writeUTF(fileContent);
                                dataOut.close();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(NotePadApp.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(NotePadApp.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Platform.exit();
                        }
                    } else if (result.get() == buttonTypeDontSave) {
                            Platform.exit();
                    } else {
                            // ... user chose CANCEL or closed the dialog
                    }
                }
            }
        });

    /////////////////////////////////////////////////////////////////////////////////////////
        undoItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                myTextArea.undo();
            }
        });

        cutItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                myTextArea.cut();
            }
        });

        copyItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                myTextArea.copy();
            }
        });

        pasteItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                myTextArea.paste();
            }
        });

        deleteItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
               myTextArea.replaceSelection("");
            }
        });

        selectAllItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                myTextArea.selectAll();
            }
        });
    /////////////////////////////////////////////////////////////////////////////////////////
        
        textHelp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ev){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("About FX NotePad");
                alert.setHeaderText("Author: Abanoub Kamal Boshra. \nStudent at Information Technology Institute (ITI). \nSection: Internet of Things (IoT).");
                alert.setContentText("FX NotePad V01 using JavaFX. \nDate: Jan 2020");
                 alert.showAndWait();
            }
        });
    /////////////////////////////////////////////////////////////////////////////////////////

        Scene myScene = new Scene(pane1, 600, 500);

        primaryStage.setTitle("FX Notepad");
        primaryStage.setScene(myScene);
        primaryStage.show();
    }    
}
