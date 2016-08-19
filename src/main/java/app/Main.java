package app;

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

//        for (int i = 1260; i < 1320; i++) {
//            listRange.add(i);
//        }
        for (int i = 2200; i < 2270; i++) {
            listRange.add(i);
        }
        for (int i = 2430; i < 2470; i++) {
            listRange.add(i);
        }
        for (int i = 2330; i < 2390; i++) {
            listRange.add(i);
        }
        for (int i = 3700; i < 3900; i++) {
            listRange.add(i);
        }
        for (int i = 3550; i < 3670; i++) {
            listRange.add(i);
        }
        for (int i = 1670; i < 1700; i++) {
            listRange.add(i);
        }


        soundDetector.setFrequencyRange(list -> listRange);
        soundDetector.setListFrequency(listRange);
        soundDetector.run();
        try {
            Thread.sleep(3000);

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Stop!!!");
        soundDetector.stop();
    }


}
