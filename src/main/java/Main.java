import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image ;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;

import java.awt.*;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

class DragContext {

    double mouseAnchorX;
    double mouseAnchorY;

    double translateAnchorX;
    double translateAnchorY;

}


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Path currentDir = Paths.get(".");
        JsonReader reader = new JsonReader(currentDir.toAbsolutePath() + "/input/simpleroadnetwork.json");

        ArrayList<landParcel> landParcels  = reader.getParcels();

        LandParcelOptimizer landParcelOptimizer = new LandParcelOptimizer();
        Geometry[] boundingBox = landParcelOptimizer.BoundingBoxOptimization(landParcels.get(0));
        landParcel splitParcel = new landParcel(landParcelOptimizer.splitPolygon(boundingBox[0], landParcels.get(0).polygon));
        landParcels.add(splitParcel);

        Geometry[] boundingBox1 = landParcelOptimizer.BoundingBoxOptimization(landParcels.get(3));
        landParcel splitParcel1 = new landParcel(landParcelOptimizer.splitPolygon(boundingBox1[0], landParcels.get(3).polygon));
        landParcels.add(splitParcel1);

        ArrayList<Geometry> boundingBoxes = new ArrayList<>(Arrays.asList(boundingBox));
        ArrayList<Geometry> boundingBoxes1 = new ArrayList<>(Arrays.asList(boundingBox1));

        SceneRenderer sceneRenderer = new SceneRenderer();
        SceneRenderer.render(landParcels.toArray(new landParcel[4]));
        SceneRenderer.render(boundingBoxes.toArray(new Geometry[0]));
        SceneRenderer.render(boundingBoxes.get(0).getCoordinates());
        SceneRenderer.render(boundingBoxes.get(1).getCoordinates());
        SceneRenderer.render(boundingBoxes1.get(0).getCoordinates());
        SceneRenderer.render(boundingBoxes1.get(1).getCoordinates());

        sceneRenderer.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
