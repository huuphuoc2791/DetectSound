package pitch;

public interface PitchDetector {
	PitchDetectionResult getPitch(final float[] audioBuffer);
}
