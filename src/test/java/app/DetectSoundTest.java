package app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by huuphuoc on 7/20/16.
 */
public class DetectSoundTest {
    @Test
    public void test(){
        DetectSound detectSound =new DetectSound();
        detectSound.detect();
    }

}