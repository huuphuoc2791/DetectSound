
package jvm;

import io.AudioFormat;
import io.IAudioInputStream;

import javax.sound.sampled.AudioFormat.Encoding;
import java.io.IOException;

public class JVMIAudioInputStream implements IAudioInputStream {
	
	private final javax.sound.sampled.AudioInputStream underlyingStream;
	private final AudioFormat tarsosDSPAudioFormat;
	public JVMIAudioInputStream(javax.sound.sampled.AudioInputStream stream){
		this.underlyingStream = stream;
		this.tarsosDSPAudioFormat = JVMIAudioInputStream.toTarsosDSPFormat(stream.getFormat());
	}

	@Override
	public long skip(long bytesToSkip) throws IOException {
		return underlyingStream.skip(bytesToSkip);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return underlyingStream.read(b, off, len);
	}

	@Override
	public void close() throws IOException {
		underlyingStream.close();
	}

	@Override
	public long getFrameLength() {

		return underlyingStream.getFrameLength();
	}

	@Override
	public AudioFormat getFormat() {
		return tarsosDSPAudioFormat;
	}
	
	/**
	 * Converts a {@link javax.sound.sampled.AudioFormat} to a {@link AudioFormat}.
	 * 
	 * @param format
	 *            The {@link javax.sound.sampled.AudioFormat}
	 * @return A {@link AudioFormat}
	 */
	public static AudioFormat toTarsosDSPFormat(javax.sound.sampled.AudioFormat format) {
		boolean isSigned = format.getEncoding() == Encoding.PCM_SIGNED;
		AudioFormat tarsosDSPFormat = new AudioFormat(format.getSampleRate(), format.getSampleSizeInBits(), format.getChannels(), isSigned, format.isBigEndian());
		return tarsosDSPFormat;
	}

	/**
	 * Converts a {@link AudioFormat} to a {@link javax.sound.sampled.AudioFormat}.
	 * 
	 * @param format
	 *            The {@link AudioFormat}
	 * @return A {@link javax.sound.sampled.AudioFormat}
	 */
	public static AudioFormat toAudioFormat(AudioFormat format) {
		boolean isSigned = format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED;
		AudioFormat audioFormat = new AudioFormat(format.getSampleRate(), format.getSampleSizeInBits(), format.getChannels(), isSigned, format.isBigEndian());
		return audioFormat;
	}
	
}
