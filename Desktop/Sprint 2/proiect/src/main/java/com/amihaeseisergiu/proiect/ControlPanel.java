/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amihaeseisergiu.proiect;

/**
 *
 * @author Alex
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ControlPanel extends VBox {
    
    ComboBox comboBox;
    Button eraserBtn;
    List<VBox> comboBoxItems;
    boolean erase;
    MainFrame frame;
    List<Integer> existingFloors = new ArrayList<>();
    Building building;
    
    public ControlPanel(MainFrame frame, Building building)
    {
        this.frame = frame;
        this.building = building;
        
        eraserBtn = new Button("Eraser: Off");
        comboBox = new ComboBox();
        comboBoxItems = new ArrayList<>();
        VBox item1 = new VBox();
        item1.setSpacing(10);
        item1.setAlignment(Pos.TOP_CENTER);
        Label item1Label = new Label("Rectangle");
        item1Label.setTextFill(Color.BLACK);
        item1.getChildren().addAll(item1Label, new Rectangle(0, 0));
        comboBoxItems.add(item1);
        
        ScrollPane addFloorScrollPane = new ScrollPane();
        Label nrFloorsLabel = new Label("Floors: 0");
        VBox addFloorVBox = new VBox();
        Button addFloorBtn = new Button("Add Floor");
        addFloorBtn.setMaxWidth(Double.MAX_VALUE);
        addFloorScrollPane.setContent(addFloorVBox);
        addFloorScrollPane.setStyle("-fx-background-color:transparent;");
        addFloorScrollPane.setFitToWidth(true);
        addFloorVBox.setAlignment(Pos.TOP_CENTER);
        addFloorVBox.setSpacing(10);
        
        
        comboBox.setItems(FXCollections.observableArrayList(comboBoxItems));
        comboBox.setVisibleRowCount(2);
        comboBox.setValue("Select Shape");
        comboBox.setMaxWidth(Double.MAX_VALUE);
        eraserBtn.setMaxWidth(Double.MAX_VALUE);
        
        Button jsonBuildingBtn = new Button("Print Json");
        jsonBuildingBtn.setMaxWidth(Double.MAX_VALUE);
        this.getChildren().addAll(eraserBtn, jsonBuildingBtn, comboBox, nrFloorsLabel, addFloorBtn, addFloorScrollPane);
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(10,10,10,10));
        this.setSpacing(10);
        
        jsonBuildingBtn.setOnAction(event -> {
            System.out.println(building.toJson());
        });
        
        addFloorBtn.setOnAction(event -> {
            AtomicBoolean popupOpen = new AtomicBoolean(false);
            int nr = 0;
            while(existingFloors.contains(nr)) {
                nr++;
            }
            existingFloors.add(nr);
            Button newFloorBtn = new Button("Floor " + nr);
            Floor floor = new Floor();
            frame.building.getFloors().put(nr, floor);
            newFloorBtn.setMaxWidth(Double.MAX_VALUE);
            addFloorVBox.getChildren().add(newFloorBtn);
            nrFloorsLabel.setText("Floors: " + (addFloorVBox.getChildren().size()));
            
            if(addFloorVBox.getChildren().size() >= 1)
            {
                frame.drawingPanel.canvas.setVisible(true);
                frame.drawingPanel.setStyle("-fx-background-color: white");
            }
            else 
            {
                frame.drawingPanel.canvas.setVisible(false);
                frame.drawingPanel.setStyle("");
            }
            
            newFloorBtn.setOnMousePressed(event2 -> {
                if(event2.isPrimaryButtonDown())
                {
                    String nrFloor = newFloorBtn.getText().substring(6, 7);
                    Floor f = frame.building.getFloors().get(Integer.parseInt(nrFloor));
                    frame.drawingPanel.graph = f.getGraph();
                    frame.drawingPanel.setShapes(f.getShapes());
                    frame.drawingPanel.resetCanvas();
                    frame.drawingPanel.drawAll();
                }
                else if(event2.isSecondaryButtonDown() && !popupOpen.get())
                {
                    popupOpen.set(true);
                    UpdateFloorPopUp popup  = new UpdateFloorPopUp(frame, Integer.parseInt(newFloorBtn.getText().substring(6, 7)), addFloorVBox, newFloorBtn, nrFloorsLabel, popupOpen, event2.getScreenX(), event2.getScreenY());
                    popup.start(new Stage());
                }
            });
        });
        
        eraserBtn.setOnAction(event -> {
            if(erase)
            {
                erase = false;
                eraserBtn.setText("Eraser: Off");
            }
            else
            {
                erase = true;
                eraserBtn.setText("Eraser: On");
            }
        });
    }

    public ComboBox getComboBox() {
        return comboBox;
    }

    public void setComboBox(ComboBox comboBox) {
        this.comboBox = comboBox;
    }

    public List<VBox> getComboBoxItems() {
        return comboBoxItems;
    }

    public void setComboBoxItems(List<VBox> comboBoxItems) {
        this.comboBoxItems = comboBoxItems;
    }
    
}