package com.example.optic.first_ui;

import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.bean.ReportBean;
import com.example.optic.utilities.EmptyReportListException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.List;

public class ControllerReportList extends GraphicController {
    @FXML
    private Pane id2;
    @FXML
    private ListView reports;
    @FXML
    private Label player;

    private UserProfileAppController userProfileAppController;

    @Override
    public void setUserVariables(String user) {
        userProfileAppController = new UserProfileAppController();
        player.setText("Lista Report di: "+user);
        try {
            List<ReportBean> list;
            list = userProfileAppController.getReportList(user);
            this.setList(list);
        }catch (IOException e) {
            e.printStackTrace();
        }catch (EmptyReportListException e){
            reports.getItems().add("Nessun report da mostrare.");
        }
    }


    public void setList(List<ReportBean> list) throws IOException {
        this.reports.getItems().clear();

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
    }

    public void exitListButton(){
        Stage list = (Stage) id2.getScene().getWindow();
        list.close();
    }
}
