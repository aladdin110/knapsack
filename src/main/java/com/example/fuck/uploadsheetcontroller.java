package com.example.fuck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class uploadsheetcontroller {
    private KP kp;
    private boolean parametersAreSet = false;
    static GeneticAlgorithm ga;
    private Stage stage;
    private Scene scene;
    private Parent root;

    int popSize;
    int tournamentSize;
    double crossOverRate;
    double mutationRate;
    int numberOfIterations;
    boolean elitism;
    @FXML
    private TextField popSizeField;
    @FXML private TextField tournamentSizeField;
    @FXML private TextField crossOverRateField;
    @FXML private TextField mutationRateField;
    @FXML private TextField numberOfIterationsField;
    @FXML private CheckBox elitismBox;

    @FXML private TextField capacityField;
    // dial upload
    @FXML private CheckBox headersCheckBox;
    @FXML private TextField separatorField;
    private FileChooser fc = new FileChooser();
    private boolean headersExist;
    private String separator;
    private File file;

    private ArrayList<Item> items = new ArrayList<>();





    //these are the functions
   public void submitParameters() {
        popSize = Integer.parseInt(popSizeField.getText());
        tournamentSize = Integer.parseInt(tournamentSizeField.getText());
        crossOverRate = Double.parseDouble(crossOverRateField.getText());
        mutationRate = Double.parseDouble(mutationRateField.getText());
        numberOfIterations = Integer.parseInt(numberOfIterationsField.getText());
        elitism = elitismBox.isSelected();

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
    public void switchtosolvingUploaded(ActionEvent event) {
        try {
            double capacity = Double.parseDouble(capacityField.getText());
            kp = new KP(items,capacity);
            if (parametersAreSet) {
                ga=gaSolve(kp,popSize,tournamentSize,crossOverRate,mutationRate,numberOfIterations,elitism);
            } else {
                ga=gaSolve(kp);
            }
            root = FXMLLoader.load(getClass().getResource("SolvingUploadedSheet-scene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println(ga.getObjValue());

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

    public void upload() {
        headersExist = headersCheckBox.isSelected();
        separator = separatorField.getText();
        file = fc.showOpenDialog(null);
        if (file == null) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            line= br.readLine();
            int nameOffset = 0;
            int weightOffset = 1;
            int valueOffset = 2;
            if(headersExist) {
                String[] values = line.split(separator);
                for (int i=0; i<3; i++) {
                    values[i] = values[i].replaceAll("\\s", "");
                    if (values[i].charAt(0) == 'n' || values[i].charAt(0) == 'N') {
                        nameOffset = i;
                    } else if (values[i].charAt(0) == 'v' || values[i].charAt(0) == 'V') {
                        valueOffset = i;
                    } else if (values[i].charAt(0) == 'w' || values[i].charAt(0) == 'W') {
                        weightOffset = i;
                    }
                }
            }
            while ((line=br.readLine()) != null) {
                String[] values = line.split(separator);
                String name = values[nameOffset];
                double weight = Double.parseDouble(values[weightOffset]);
                double value = Double.parseDouble(values[valueOffset]);
                items.add(new Item(name, weight, value));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
