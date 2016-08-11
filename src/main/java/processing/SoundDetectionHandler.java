package processing;

import io.AudioEvent;

public interface SoundDetectionHandler {
    void handleSound(SoundDetectionResult soundDetectionResult, AudioEvent audioEvent);
}
