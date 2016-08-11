package processing;

public interface ISoundDetector {
	SoundDetectionResult getSound(final float[] audioBuffer);
}
