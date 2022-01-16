package com.example.optic;

import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.bean.ReportBean;
import com.example.optic.entities.Event;
import com.example.optic.entities.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerReportList extends GraphicController {
    @FXML
    private Pane id2;
    @FXML
    private ListView reports;
    @FXML
    private Label player;

    @Override
    public void setUserVariables(String user) throws IOException {
        player.setText("Lista Report di: "+user);
        List<ReportBean> list;
        list = UserProfileAppController.getReportList(user);
        this.setList(list);
    }


    public void setList(List<ReportBean> list) throws IOException {
        this.reports.getItems().clear();
        if(!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                reports.getItems().add(list.get(i).getFormattedText());
            }
            reports.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> stringListView) {
                    return new ListCell<String>() {
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty) {
                                Text text = new Text(item);
                                text.wrappingWidthProperty().bind(reports.widthProperty().subtract(40));
                                setGraphic(text);
                            }
                        }
                    };
                }
            });
        }else{
            reports.getItems().add("Nessun Report da mostrare");
        }
    }

    public void exitListButton(){
        Stage list = (Stage) id2.getScene().getWindow();
        list.close();
    }
}
