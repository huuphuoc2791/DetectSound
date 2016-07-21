package io;

public interface AudioProcessor {

    boolean process(AudioEvent audioEvent);

    void processingFinished();
}
