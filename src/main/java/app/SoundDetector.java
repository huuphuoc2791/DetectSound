package app;

import io.AudioDispatcher;
import io.AudioEvent;
import io.Shared;
import jvm.JVMIAudioInputStream;


import processing.*;
import processing.SoundProcessor.ESoundEstimationAlgorithm;
import util.FFT;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SoundDetector implements ISoundDetectionHandler, IConditionDetection {

    private AudioDispatcher dispatcher;
    private ISoundDetector iSoundDetector;
    private ESoundEstimationAlgorithm algo;
    private int result;
    private final static int range = 5;
    private IConditionDetection frequency;
    private List<Integer> listFrequency = new ArrayList<>();

    public void stop() {
        dispatcher.stop();
    }

    private void run() {
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
    public int handleSound(SoundDetectionResult soundDetectionResult, AudioEvent audioEvent) {
        if (soundDetectionResult.getFrequency() != -1) {
            float pitch = soundDetectionResult.getFrequency();
            result = (int) Math.floor(pitch);
            System.out.println(result);
        }
        return result;
    }

    // this function uses to detect sound, then return result in boolean form
    public boolean detectSound() {
        run();
        List<Integer> list = new ArrayList<>();
        list.add(138);
        this.frequency.addConditionDetection(list);
        for (int frequencyRange : listFrequency) {
            System.out.println(frequencyRange);
            if ((result > frequencyRange - range) && (result < frequencyRange + range)) {
                return true;
            }
        }
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
    public void setFrequency(IConditionDetection frequency) {
        this.frequency = frequency;
    }
}
