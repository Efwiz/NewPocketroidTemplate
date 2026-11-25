package com.esotericsoftware.kryo.io;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes8.dex */
public class OutputChunked extends Output {
    public OutputChunked() {
    }

    public OutputChunked(int i) {
        super(i);
    }

    public OutputChunked(OutputStream outputStream) {
        super(outputStream);
    }

    public OutputChunked(OutputStream outputStream, int i) {
        super(outputStream, i);
    }

    @Override // com.esotericsoftware.kryo.io.Output, java.io.OutputStream, java.io.Flushable
    public void flush() throws KryoException {
        if (position() > 0) {
            try {
                writeChunkSize();
                super.flush();
            } catch (IOException e) {
                throw new KryoException(e);
            }
        }
        super.flush();
    }

    private void writeChunkSize() throws IOException {
        int iPosition = position();
        if (Log.TRACE) {
            Log.trace("kryo", "Write chunk: " + iPosition + Util.pos(iPosition));
        }
        OutputStream outputStream = getOutputStream();
        if ((iPosition & (-128)) == 0) {
            outputStream.write(iPosition);
            return;
        }
        outputStream.write((iPosition & 127) | 128);
        int i = iPosition >>> 7;
        if ((i & (-128)) == 0) {
            outputStream.write(i);
            return;
        }
        outputStream.write((i & 127) | 128);
        int i2 = iPosition >>> 14;
        if ((i2 & (-128)) == 0) {
            outputStream.write(i2);
            return;
        }
        outputStream.write((i2 & 127) | 128);
        int i3 = iPosition >>> 21;
        if ((i3 & (-128)) == 0) {
            outputStream.write(i3);
        } else {
            outputStream.write((i3 & 127) | 128);
            outputStream.write(iPosition >>> 28);
        }
    }

    public void endChunk() {
        flush();
        if (Log.TRACE) {
            Log.trace("kryo", "End chunk.");
        }
        try {
            getOutputStream().write(0);
        } catch (IOException e) {
            throw new KryoException(e);
        }
    }
}
