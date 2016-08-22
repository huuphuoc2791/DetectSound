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

        for (int i = 1200; i < 1240; i++) {
            listRange.add(i);
        }
//        for (int i = 2220; i < 2270; i++) {
//            listRange.add(i);
//        }
//        for (int i = 2280; i < 2300; i++) {
//            listRange.add(i);
//        }
//        for (int i = 2430; i < 2470; i++) {
//            listRange.add(i);
//        }
//        for (int i = 2340; i < 2390; i++) {
//            listRange.add(i);
//        }
//        for (int i = 2650; i < 2720; i++) {
//            listRange.add(i);
//        }
//        for (int i = 3770; i < 3840; i++) {
//            listRange.add(i);
//        }
//        for (int i = 3680; i < 3760; i++) {
//            listRange.add(i);
//        }
//        for (int i = 3510; i < 3610; i++) {
//            listRange.add(i);
//        }
//        for (int i = 3850; i < 3900; i++) {
//            listRange.add(i);
////        }
//        for (int i = 1820; i < 1890; i++) {
//            listRange.add(i);
//        }

        for (int i = 2340; i < 2430; i++) {
            listRange.add(i);
        }

        soundDetector.setFrequencyRange(list -> listRange);
        soundDetector.setListFrequency(listRange);
        soundDetector.run();
        try {
            Thread.sleep(5008);

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Stop!!!");
        System.out.println(soundDetector.isDetected());
        soundDetector.stop();
    }


}
