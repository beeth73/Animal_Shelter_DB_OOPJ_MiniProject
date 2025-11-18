import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.List;

public class ShelterView {

    public static VBox getView() {
        ShelterDAO dao = new ShelterDAO();

        TableView<Shelter> table = new TableView<>();

        TableColumn<Shelter, Integer> col1 = new TableColumn<>("Sid");
        col1.setCellValueFactory(new PropertyValueFactory<>("Sid"));

        TableColumn<Shelter, String> col2 = new TableColumn<>("Sname");
        col2.setCellValueFactory(new PropertyValueFactory<>("Sname"));

        TableColumn<Shelter, String> col3 = new TableColumn<>("Location");
        col3.setCellValueFactory(new PropertyValueFactory<>("Location"));

        table.getColumns().addAll(col1, col2, col3);

        ObservableList<Shelter> items = FXCollections.observableArrayList();

        // load initial data (DB if available else sample)
        try {
            List<Shelter> fromDb = dao.getAll();
            items.addAll(fromDb);
        } catch (SQLException e) {
            // fallback to sample data
            items.addAll(getSampleShelters());
        }

        table.setItems(items);

        // Form fields
        TextField tfId = new TextField(); tfId.setPromptText("Sid (int)");
        TextField tfName = new TextField(); tfName.setPromptText("Name");
        TextField tfLocation = new TextField(); tfLocation.setPromptText("Location");

        Button btnAdd = new Button("Add");
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");
        Button btnRefresh = new Button("Refresh");

        HBox form = new HBox(8, new Label("ID:"), tfId, new Label("Name:"), tfName, new Label("Location:"), tfLocation, btnAdd, btnUpdate, btnDelete, btnRefresh);
        form.setPadding(new Insets(8));
        HBox.setHgrow(tfName, Priority.ALWAYS);
        HBox.setHgrow(tfLocation, Priority.ALWAYS);

        // Wire selection -> form
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                tfId.setText(String.valueOf(newSel.getSid()));
                tfName.setText(newSel.getSname());
                tfLocation.setText(newSel.getLocation());
            }
        });

        // Add action
        btnAdd.setOnAction(ev -> {
            try {
                int sid = Integer.parseInt(tfId.getText().trim());
                Shelter s = new Shelter(sid, tfName.getText().trim(), tfLocation.getText().trim());
                try {
                    boolean ok = dao.insert(s);
                    if (!ok) throw new SQLException("Insert returned false");
                    items.add(s);
                    showInfo("Inserted shelter " + sid);
                } catch (SQLException ex) {
                    // fallback: add in-memory
                    items.add(s);
                    showWarning("DB insert failed, added locally: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                showWarning("Sid must be an integer");
            }
        });

        // Update action
        btnUpdate.setOnAction(ev -> {
            Shelter sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { showWarning("Select a shelter to update"); return; }
            try {
                int sid = Integer.parseInt(tfId.getText().trim());
                Shelter updated = new Shelter(sid, tfName.getText().trim(), tfLocation.getText().trim());
                try {
                    boolean ok = dao.update(updated);
                    if (!ok) throw new SQLException("Update returned false");
                    // replace in list
                    int idx = items.indexOf(sel);
                    if (idx >= 0) items.set(idx, updated);
                    showInfo("Updated shelter " + sid);
                } catch (SQLException ex) {
                    // fallback: update in-memory
                    int idx = items.indexOf(sel);
                    if (idx >= 0) items.set(idx, updated);
                    showWarning("DB update failed, updated locally: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                showWarning("Sid must be an integer");
            }
        });

        // Delete action
        btnDelete.setOnAction(ev -> {
            Shelter sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) { showWarning("Select a shelter to delete"); return; }
            try {
                try {
                    boolean ok = dao.delete(sel.getSid());
                    if (!ok) throw new SQLException("Delete returned false");
                    items.remove(sel);
                    showInfo("Deleted shelter " + sel.getSid());
                } catch (SQLException ex) {
                    // fallback: remove in-memory
                    items.remove(sel);
                    showWarning("DB delete failed, removed locally: " + ex.getMessage());
                }
            } catch (Exception ex) {
                showWarning("Delete failed: " + ex.getMessage());
            }
        });

        // Refresh action
        btnRefresh.setOnAction(ev -> {
            items.clear();
            try {
                List<Shelter> fromDb = dao.getAll();
                items.addAll(fromDb);
            } catch (SQLException ex) {
                items.addAll(getSampleShelters());
                showWarning("Refresh from DB failed, showing sample data: " + ex.getMessage());
            }
        });

        VBox root = new VBox(8, table, form);
        root.setPadding(new Insets(8));
        return root;
    }

    private static ObservableList<Shelter> getShelters() {
        // retained for backward-compatibility; now replaced by getSampleShelters in new UI
        return FXCollections.observableArrayList(getSampleShelters());
    }

    private static List<Shelter> getSampleShelters() {
        return List.of(
            new Shelter(1, "Pawsitive Homes Mumbai", "Bandra West"),
            new Shelter(2, "Andheri Animal Rescue", "Lokhandwala")
        );
    }

    private static void showWarning(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Warning");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }

    private static void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Info");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}
