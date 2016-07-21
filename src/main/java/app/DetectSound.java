package app;

import io.AudioDispatcher;
import io.AudioEvent;
import io.Shared;
import jvm.JVMAudioInputStream;
import pitch.PitchDetectionHandler;
import pitch.PitchDetectionResult;
import pitch.PitchProcessor;
import pitch.PitchProcessor.PitchEstimationAlgorithm;

import javax.sound.sampled.*;

public class DetectSound implements PitchDetectionHandler {

    private AudioDispatcher dispatcher;

    private PitchEstimationAlgorithm algo;
    private int result;

    private void start() {
        algo = PitchEstimationAlgorithm.YIN;
        System.out.println("Start:");
        Mixer newValue = null;
        for (Mixer.Info info : Shared.getMixerInfo(false, true)) {
            if (info.getName().contains("Built-in Microphone") || info.getName().contains("Default Audio Device")) {
                newValue = AudioSystem.getMixer(info);
            }
        }
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

        JVMAudioInputStream audioStream = new JVMAudioInputStream(stream);
        // create a new dispatcher
        dispatcher = new AudioDispatcher(audioStream, bufferSize,
                overlap);

        // add a processor
        dispatcher.addAudioProcessor(new PitchProcessor(algo, sampleRate, bufferSize, this));

        new Thread(dispatcher, "Audio dispatching").start();
    }

    public static void main(String[] args) {
        DetectSound detectSound = new DetectSound();
        detectSound.start();
    }


    @Override
    public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
        if (pitchDetectionResult.getPitch() != -1) {
            float pitch = pitchDetectionResult.getPitch();
            result = (int) Math.floor(pitch);
            System.out.println(result);
        }
    }

    public int detect() {
        start();
        return result;
    }

}
