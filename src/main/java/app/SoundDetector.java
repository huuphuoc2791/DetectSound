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

public class SoundDetector implements ISoundDetectionHandler {

    private AudioDispatcher dispatcher;
    private ISoundDetector iSoundDetector;

    public void start() {

    }

    public void stop() {
        dispatcher.stop();
    }

    public void setOnSoundDetected(ISoundDetector soundDetector) {
        this.iSoundDetector = soundDetector;
    }

    private ESoundEstimationAlgorithm algo;
    private int result;

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

    private void setNewMixer(Mixer mixer) throws LineUnavailableException,
            UnsupportedAudioFileException {

        if (dispatcher != null) {
            dispatcher.stop();
        }
        float sampleRate = 44100;
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
        if (soundDetectionResult.getPitch() != -1) {
            float pitch = soundDetectionResult.getPitch();
            result = (int) Math.floor(pitch);
            System.out.println(result);
        }
    }



}
