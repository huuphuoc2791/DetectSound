package processing;

import io.AudioEvent;
import io.AudioProcessor;

public class SoundProcessor implements AudioProcessor {
	public enum ESoundEstimationAlgorithm {

		YIN,

		FFT_YIN;

		public ISoundDetector getDetector(float sampleRate, int bufferSize){
			ISoundDetector detector;
			if(this == FFT_YIN){
				detector = new FastYin(sampleRate, bufferSize);
			}else {
				detector = new Yin(sampleRate, bufferSize);
			}
			return detector;
		}
		
	};
	
	/**
	 * The underlying processing detector;
	 */
	private final ISoundDetector detector;
	
	private final SoundDetectionHandler handler;

	public SoundProcessor(ESoundEstimationAlgorithm algorithm, float sampleRate,
						  int bufferSize,
						  SoundDetectionHandler handler) {
		detector = algorithm.getDetector(sampleRate, bufferSize);
		this.handler = handler;	
	}
	
	@Override
	public boolean process(AudioEvent audioEvent) {
		float[] audioFloatBuffer = audioEvent.getFloatBuffer();
		
		SoundDetectionResult result = detector.getSound(audioFloatBuffer);
		
		
		handler.handleSound(result,audioEvent);
		return true;
	}

	@Override
	public void processingFinished() {
	}

	
}
