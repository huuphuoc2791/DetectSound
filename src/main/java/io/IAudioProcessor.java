package io;

public interface IAudioProcessor {

    boolean process(AudioEvent audioEvent);

    void processingFinished();
}
