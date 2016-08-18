package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
//        soundDetector.run();
//        try {
//            Thread.sleep(3000);
//        } catch(InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }
//        System.out.println("Stop!!!");
//        soundDetector.stop();
        List<Integer> listRange = new ArrayList<>();
        listRange.add(199);
        soundDetector.setFrequency(list -> listRange);
        if (soundDetector.detectSound()) {
            System.out.println("Deteted");
        }
        ;
    }

}
