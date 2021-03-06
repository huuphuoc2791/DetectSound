package processing;

import io.AudioEvent;
import io.IAudioProcessor;

public class SoundProcessor implements IAudioProcessor {
	public enum ESoundEstimationAlgorithm {

		YIN,

		FFT_YIN;

		public ISoundDetection getDetector(float sampleRate, int bufferSize){
			ISoundDetection detector;
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
	private final ISoundDetection detector;
	
	private final ISoundDetectionHandler handler;

	public SoundProcessor(ESoundEstimationAlgorithm algorithm, float sampleRate,
                          int bufferSize,
                          ISoundDetectionHandler handler) {
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
