package processing;

import io.AudioEvent;

public interface ISoundDetectionHandler {
    void handleSound(SoundDetectionResult soundDetectionResult, AudioEvent audioEvent);
}
