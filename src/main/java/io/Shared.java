
package io;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer.Info;
import java.util.Vector;

public class Shared {
	
	public static Vector<Info> getMixerInfo(
			final boolean supportsPlayback, final boolean supportsRecording) {
		final Vector<Info> infos = new Vector<Info>();
		final Info[] mixers = AudioSystem.getMixerInfo();
		for (final Info mixerinfo : mixers) {
			if (supportsRecording
					&& AudioSystem.getMixer(mixerinfo).getTargetLineInfo().length != 0) {
				// Mixer capable of recording audio if target LineWavelet length != 0
				infos.add(mixerinfo);
			}
		}
		return infos;
	}

	private static String OS = null;
	public static String getOsName()
	{
		if(OS == null)
			OS = System.getProperty("os.name");
	    return OS;
	}
}
