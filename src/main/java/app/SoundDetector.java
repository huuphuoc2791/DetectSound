package app;

import io.AudioDispatcher;
import io.AudioEvent;
import io.Shared;
import jvm.JVMIAudioInputStream;
import processing.ISoundDetectionHandler;
import processing.SoundDetectionResult;
import processing.SoundProcessor;
import processing.SoundProcessor.ESoundEstimationAlgorithm;

import javax.sound.sampled.*;
import java.util.*;

public class SoundDetector implements ISoundDetectionHandler, IConditionDetection {

    private AudioDispatcher dispatcher;
    private ISoundDetector iSoundDetector;
    private ESoundEstimationAlgorithm algo;
    private int result;
    private final static int range = 20;
    private IConditionDetection frequency;
    private List<Integer> listFrequency = new ArrayList<>();
    private boolean isDetected = false;

    public void stop() {
        dispatcher.stop();
    }

    public void run() {
        algo = ESoundEstimationAlgorithm.YIN;
        System.out.println("Start:");
        Mixer newValue = null;
        for (Mixer.Info info : Shared.getMixerInfo(false, true)) {
            if (info.getName().contains("Built-in Microphone") || info.getName().contains("Default Audio Device")) {
                newValue = AudioSystem.getMixer(info);
            }
        }

        this.iSoundDetector.onDetected();
        try {
            setNewMixer(newValue);
        } catch (LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void setOnSoundDetected(ISoundDetector iSoundDetector) {
        this.iSoundDetector = iSoundDetector;
    }

    private void setNewMixer(Mixer mixer) throws LineUnavailableException,
            UnsupportedAudioFileException {

        if (dispatcher != null) {
            dispatcher.stop();
        }
        float sampleRate = 41000;
        int bufferSize = 1024;
        int overlap = 0;

        final AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,
                true);
        final DataLine.Info dataLineInfo = new DataLine.Info(
                TargetDataLine.class, format);
        TargetDataLine line;
        line = (TargetDataLine) mixer.getLine(dataLineInfo);
        line.open(format, bufferSize);
        line.start();
        final AudioInputStream stream = new AudioInputStream(line);

        JVMIAudioInputStream audioStream = new JVMIAudioInputStream(stream);
        // create a new dispatcher
        dispatcher = new AudioDispatcher(audioStream, bufferSize,
                overlap);

        // add a processor
        dispatcher.addAudioProcessor(new SoundProcessor(algo, sampleRate, bufferSize, this));

        new Thread(dispatcher, "Audio dispatching").start();
    }


    @Override
    public void handleSound(SoundDetectionResult soundDetectionResult, AudioEvent audioEvent) {
        if (soundDetectionResult.getFrequency() != -1) {
            float pitch = soundDetectionResult.getFrequency();
            result = (int) Math.floor(pitch);
            System.out.println(result);
            if (detectSound()) {
                if (setTimeDetection()) {
                    isDetected = true;
                    System.out.println("Trigger Event");
                }
            }
        }
    }

    //add condition of sample sound to compare when record. We can add out side.
    private void addCondition() {
        if (listFrequency.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            list.add(2586);
            list.add(2620);
            list.add(2720);
            setListFrequency(list);
        }
    }
    // this function uses to detect sound, then return result in boolean form


    private boolean detectSound() {
        addCondition();
        for (int frequencyRange : listFrequency) {
            if (result == frequencyRange) {
                return true;
            }
        }
        return false;
    }

    public boolean setTimeDetection() {
        long tStart = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        long tEnd = System.currentTimeMillis();

        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;
        if (elapsedSeconds < 1.1)
            return true;
        return false;

    }

    @Override
    public List<Integer> addConditionDetection(List<Integer> listFrequency) {
        Collections.copy(this.listFrequency, listFrequency);
        return this.listFrequency;
    }

    public void setListFrequency(List<Integer> listFrequency) {
        this.listFrequency = listFrequency;
    }

    //add condition Frequency of sound.
    public void setFrequencyRange(IConditionDetection frequency) {
        this.frequency = frequency;
    }

    public boolean isDetected() {
        return isDetected;
    }

    public void setDetected(boolean detected) {
        isDetected = detected;
    }
}
