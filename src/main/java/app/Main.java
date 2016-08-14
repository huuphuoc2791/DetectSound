package app;

/**
 * Created by huuphuoc on 8/11/16.
 */
public class Main {
    public static void main(String[] args) {
        SoundDetector soundDetector = new SoundDetector();
        soundDetector.setOnSoundDetected(()-> {
            // TODO: 8/11/16
            System.out.print("Detected");
        });

        soundDetector.detect();
    }
}
