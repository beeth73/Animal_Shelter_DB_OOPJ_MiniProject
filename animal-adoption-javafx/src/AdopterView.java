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

public class AdopterView {

    public static VBox getView() {

        TableView<Adopter> table = new TableView<>();

        TableColumn<Adopter, Integer> col1 = new TableColumn<>("Adid");
        col1.setCellValueFactory(new PropertyValueFactory<>("Adid"));

        TableColumn<Adopter, String> col2 = new TableColumn<>("Name");
        col2.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<Adopter, String> col3 = new TableColumn<>("Address");
        col3.setCellValueFactory(new PropertyValueFactory<>("Address"));

        TableColumn<Adopter, String> col4 = new TableColumn<>("Email");
        col4.setCellValueFactory(new PropertyValueFactory<>("Email"));

        TableColumn<Adopter, String> col5 = new TableColumn<>("Phone");
        col5.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        table.getColumns().addAll(col1, col2, col3, col4, col5);

        table.setItems(getAdopters());

        return new VBox(table);
    }

    private static ObservableList<Adopter> getAdopters() {
        ObservableList<Adopter> list = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                String sql = "SELECT `Adid`, `Name`, `Add`, `Email`, `Phone_No` FROM `ADOPTER`";
                try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new Adopter(
                            rs.getInt("Adid"),
                            rs.getString("Name"),
                            rs.getString("Add"),
                            rs.getString("Email"),
                            rs.getString("Phone_No")
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
            new Adopter(101, "Rohan Sharma", "Bandra West", "rohan@example.com", "+91 98200xxxx"),
            new Adopter(102, "Priya Patel", "Andheri West", "priya@example.com", "+91 99201xxxx")
        );
        return list;
    }
}
