package pitch;

import io.AudioEvent;
import io.AudioProcessor;

public class PitchProcessor implements AudioProcessor {
	public enum PitchEstimationAlgorithm {

		YIN,

		FFT_YIN;

		public PitchDetector getDetector(float sampleRate,int bufferSize){
			PitchDetector detector;
			if(this == FFT_YIN){
				detector = new FastYin(sampleRate, bufferSize);
			}else {
				detector = new Yin(sampleRate, bufferSize);
			}
			return detector;
		}
		
	};
	
	/**
	 * The underlying pitch detector;
	 */
	private final PitchDetector detector;
	
	private final PitchDetectionHandler handler;

	public PitchProcessor(PitchEstimationAlgorithm algorithm, float sampleRate,
			int bufferSize,
			PitchDetectionHandler handler) {
		detector = algorithm.getDetector(sampleRate, bufferSize);
		this.handler = handler;	
	}
	
	@Override
	public boolean process(AudioEvent audioEvent) {
		float[] audioFloatBuffer = audioEvent.getFloatBuffer();
		
		PitchDetectionResult result = detector.getPitch(audioFloatBuffer);
		
		
		handler.handlePitch(result,audioEvent);
		return true;
	}

	@Override
	public void processingFinished() {
	}

	
}
