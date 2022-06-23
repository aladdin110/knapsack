package com.example.fuck;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;

public class SolvingManualController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void goingbackToManualTypingScene(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("manual-typing-scene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DirectoryChooser dc = new DirectoryChooser();
    private File chosenDir;

    public void download() {
        chosenDir = dc.showDialog(null);
        File file = new File(chosenDir + "/solution.csv");
        try {
            if (file.createNewFile()) {
                try (PrintWriter writer = new PrintWriter(file)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Solution:\n");
                    sb.append("The objective value is : ");
                    sb.append(ManualTypingController.ga.getObjValue());
                    sb.append("\n");
                    for (Item item : ManualTypingController.ga.getSolution()) {
                        sb.append(item.getName());
                        sb.append(",");
                        sb.append(item.getWeight());
                        sb.append(",");
                        sb.append(item.getValue());
                        sb.append("\n");
                    }
                    writer.write(sb.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
