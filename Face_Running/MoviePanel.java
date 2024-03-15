import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javax.swing.JFrame;

import java.net.URL;

class MoviePanel extends JFXPanel{
    private final Media media;
    private final MediaPlayer player;

    MoviePanel(String str,JFrame Frame){
        StackPane root = new StackPane();
        URL url = this.getClass().getResource(str);
        media = new Media(url.toString());
        player = new MediaPlayer(media);
        MediaView mediaView = new MediaView(player);
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root);
        scene.setOnMouseClicked(e->Frame.requestFocusInWindow());
        setScene(scene);
    }

    Media getMedia(){
        return media;
    }

    MediaPlayer getPlayer(){
        return player;
    }
}
