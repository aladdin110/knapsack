package com.example.fuck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ManualTypingController {
    private KP kp;
    private boolean parametersAreSet = false;
    static GeneticAlgorithm ga;
    private Stage stage;
    private Scene scene;
    private Parent root;

    // this is for the table view
    @FXML private TextField nameField;
    @FXML private TextField valueField;
    @FXML private TextField weightField;

    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> nameCol;
    @FXML private TableColumn<Item, Double> valueCol;
    @FXML private TableColumn<Item, Double> weightCol;
    private ArrayList<Item> items = new ArrayList<>();

    // this is for the set of parameters
    @FXML private TextField popSizeField;
    @FXML private TextField tournamentSizeField;
    @FXML private TextField crossOverRateField;
    @FXML private TextField mutationRateField;
    @FXML private TextField numberOfIterationsField;
    @FXML private CheckBox elitismBox;

    @FXML private TextField capacityField;
    int popSize;
    int tournamentSize;
    double crossOverRate;
    double mutationRate;
    int numberOfIterations;
    boolean elitism;

    //these are the functions
    public void submitParameters() {
        popSize = Integer.parseInt(popSizeField.getText());
        tournamentSize = Integer.parseInt(tournamentSizeField.getText());
        crossOverRate = Double.parseDouble(crossOverRateField.getText());
        mutationRate = Double.parseDouble(mutationRateField.getText());
        numberOfIterations = Integer.parseInt(numberOfIterationsField.getText());
        elitism = elitismBox.isSelected();
        parametersAreSet = true;
    }
    public void submit(ActionEvent event) {
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        valueCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("Value"));
        weightCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("Weight"));


        String name = nameField.getText();
        double value = Double.parseDouble(valueField.getText());
        double weight = Double.parseDouble(weightField.getText());
        Item item = new Item(name, weight, value);
        tableView.getItems().add(item);
        items.add(item);
    }

    public void remove(ActionEvent event) {
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selectedItem);
        items.remove(selectedItem);
    }

    public void switchtosolvingmunual(ActionEvent event) {
        try {
            double capacity = Double.parseDouble(capacityField.getText());
            kp = new KP(items,capacity);
            if (parametersAreSet) {
                ga=gaSolve(kp,popSize,tournamentSize,crossOverRate,mutationRate,numberOfIterations,elitism);
            } else {
                ga=gaSolve(kp);
            }
            root = FXMLLoader.load(getClass().getResource("SolvingManual-scene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goingbackToChoseScene(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("chose-scene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GeneticAlgorithm gaSolve(KP kp) {

        GeneticAlgorithm ga = new GeneticAlgorithm(kp);
        ga.runAlgorithm();
        return ga;
    }

    public static GeneticAlgorithm gaSolve(KP kp, int populationSize, int tournamentSize, double uniformRate, double mutationRate, int maxIterations, boolean elitism) {
        GeneticAlgorithm ga = new GeneticAlgorithm(kp, populationSize, tournamentSize, uniformRate, mutationRate, maxIterations, elitism);
        ga.runAlgorithm();
        return ga;
    }

}
