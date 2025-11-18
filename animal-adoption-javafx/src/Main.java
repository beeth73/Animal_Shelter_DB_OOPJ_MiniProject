import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        TabPane tabs = new TabPane();

        Tab animalsTab = new Tab("Animals", AnimalView.getView());
        animalsTab.setClosable(false);

        Tab sheltersTab = new Tab("Shelters", ShelterView.getView());
        sheltersTab.setClosable(false);

        Tab adoptersTab = new Tab("Adopters", AdopterView.getView());
        adoptersTab.setClosable(false);

        Tab adoptionTab = new Tab("Adoption Records", AdoptionView.getView());
        adoptionTab.setClosable(false);

        tabs.getTabs().addAll(animalsTab, sheltersTab, adoptersTab, adoptionTab);

        Scene scene = new Scene(tabs, 800, 500);

        stage.setTitle("Animal Adoption Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
