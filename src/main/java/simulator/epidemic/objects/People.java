package simulator.epidemic.objects;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import simulator.epidemic.objects.animation.Coordinate;

public class People {

    private String id;
    private Coordinate coordinate;
    private PeopleState state;
    private final ImageView imageView;

    public People(String id, Coordinate coordinate, PeopleState state) {
        this.id = id;
        this.coordinate = coordinate;
        this.state = state;
        imageView = new ImageView();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public ImageView getImageDisplay() {
        return imageView;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getState() {
        return state.getState();
    }

    public void setState(PeopleState state) {
        this.state = state;
    }

    public void setImageDisplay(Image image) {
        imageView.setImage(image);
    }
}
