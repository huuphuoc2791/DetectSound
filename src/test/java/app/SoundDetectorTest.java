package app;

import org.junit.Test;

/**
 * Created by huuphuoc on 7/20/16.
 */
public class SoundDetectorTest {
    SoundDetector soundDetector =new SoundDetector();
    @Test
    public void setTimeDetection() throws Exception {
    soundDetector.setTimeDetection();
    }

    @Test
    public void test(){
        soundDetector.run();
    }

}