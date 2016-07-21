
package io;

import java.io.IOException;

public interface AudioInputStream {


	long skip(long bytesToSkip) throws IOException;

	int read(byte[] b, int off, int len) throws IOException ;

    public void close() throws IOException;

	AudioFormat getFormat();

	long getFrameLength();
}
