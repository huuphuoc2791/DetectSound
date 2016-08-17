package processing;

import io.AudioEvent;

public interface ISoundDetectionHandler {
    int handleSound(SoundDetectionResult soundDetectionResult, AudioEvent audioEvent);
}
