package app;

import io.AudioEvent;
import processing.SoundDetectionResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huuphuoc on 8/11/16.
 */
public class Main {
    public static void main(String[] args) {
        SoundDetector soundDetector = new SoundDetector();
        soundDetector.setOnSoundDetected(() -> {
            // TODO: 8/11/16
            System.out.println("Detected");
        });

        List<Integer> listRange = new ArrayList<>();
        listRange.add(1973);
        soundDetector.setFrequency(list -> listRange);
        soundDetector.setListFrequency(listRange);
        if (soundDetector.detectSound()) {
            System.out.println("Trigger");
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Stop!!!");
        soundDetector.stop();
    }


}
