package pitch;

import io.AudioEvent;

public interface PitchDetectionHandler {
    void  handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent);
}
