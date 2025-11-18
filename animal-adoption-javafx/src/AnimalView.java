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

public class AnimalView {

    public static VBox getView() {

        TableView<Animal> table = new TableView<>();

        TableColumn<Animal, Integer> col1 = new TableColumn<>("Aid");
        col1.setCellValueFactory(new PropertyValueFactory<>("Aid"));

        TableColumn<Animal, String> col2 = new TableColumn<>("Health Status");
        col2.setCellValueFactory(new PropertyValueFactory<>("HealthStatus"));

        TableColumn<Animal, String> col3 = new TableColumn<>("Type");
        col3.setCellValueFactory(new PropertyValueFactory<>("Type"));

        TableColumn<Animal, String> col4 = new TableColumn<>("Breed");
        col4.setCellValueFactory(new PropertyValueFactory<>("Breed"));

        TableColumn<Animal, Integer> col5 = new TableColumn<>("Age");
        col5.setCellValueFactory(new PropertyValueFactory<>("Age"));

        TableColumn<Animal, Integer> col6 = new TableColumn<>("ShelterId");
        col6.setCellValueFactory(new PropertyValueFactory<>("ShelterId"));

        table.getColumns().addAll(col1, col2, col3, col4, col5, col6);

        table.setItems(getAnimals());

        return new VBox(table);
    }

    private static ObservableList<Animal> getAnimals() {
        ObservableList<Animal> list = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                String sql = "SELECT Aid, health_status, Type, Breed, age, ShelterId FROM ANIMAL";
                try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new Animal(
                            rs.getInt("Aid"),
                            rs.getString("health_status"),
                            rs.getString("Type"),
                            rs.getString("Breed"),
                            rs.getInt("age"),
                            rs.getInt("ShelterId")
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
            new Animal(1, "Healthy", "Dog", "Labrador", 3, 1),
            new Animal(2, "Vaccinated", "Cat", "Persian", 2, 1),
            new Animal(3, "Injured", "Dog", "Indie", 5, 2)
        );
        return list;
    }
}
