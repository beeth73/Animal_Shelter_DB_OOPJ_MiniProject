import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdoptionView {

    public static VBox getView() {

        TableView<Adoption> table = new TableView<>();

        TableColumn<Adoption, Integer> col1 = new TableColumn<>("AnimalId");
        col1.setCellValueFactory(new PropertyValueFactory<>("AnimalId"));

        TableColumn<Adoption, Integer> col2 = new TableColumn<>("AdopterId");
        col2.setCellValueFactory(new PropertyValueFactory<>("AdopterId"));

        TableColumn<Adoption, String> col3 = new TableColumn<>("Date");
        col3.setCellValueFactory(new PropertyValueFactory<>("Date"));

        TableColumn<Adoption, Double> col4 = new TableColumn<>("Fee");
        col4.setCellValueFactory(new PropertyValueFactory<>("Fee"));

        table.getColumns().addAll(col1, col2, col3, col4);

        table.setItems(getAdoptionRecords());

        return new VBox(table);
    }

    private static ObservableList<Adoption> getAdoptionRecords() {
        ObservableList<Adoption> list = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                String sql = "SELECT AnimalId, AdopterId, Adoption_Date, fee FROM ADOPTIONS";
                try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new Adoption(
                            rs.getInt("AnimalId"),
                            rs.getInt("AdopterId"),
                            rs.getString("Adoption_Date"),
                            rs.getDouble("fee")
                        ));
                    }
                }
                if (!list.isEmpty()) return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Fallback sample data
        list.addAll(
            new Adoption(1, 101, "2025-01-10", 500),
            new Adoption(3, 105, "2025-02-15", 750)
        );
        return list;
    }
}
