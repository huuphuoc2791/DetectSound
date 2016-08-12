package processing;


public interface ISoundDetection {
	SoundDetectionResult getSound(final float[] audioBuffer);

}
