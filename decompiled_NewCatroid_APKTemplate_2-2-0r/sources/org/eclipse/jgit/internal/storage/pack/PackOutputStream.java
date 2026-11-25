package org.eclipse.jgit.internal.storage.pack;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.util.NB;

/* JADX INFO: loaded from: classes13.dex */
public final class PackOutputStream extends OutputStream {
    private static final int BYTES_TO_WRITE_BEFORE_CANCEL_CHECK = 131072;
    private long count;
    private boolean ofsDelta;
    private final OutputStream out;
    private final PackWriter packWriter;
    private final ProgressMonitor writeMonitor;
    private final MessageDigest md = Constants.newMessageDigest();
    private final byte[] headerBuffer = new byte[32];
    private final byte[] copyBuffer = new byte[65536];
    private long checkCancelAt = 131072;

    private static final int ofsDeltaVarIntLength(long j) {
        int i = 1;
        while (true) {
            long j2 = j >>> 7;
            if (j2 == 0) {
                return i;
            }
            j = j2 - 1;
            i++;
        }
    }

    public PackOutputStream(ProgressMonitor progressMonitor, OutputStream outputStream, PackWriter packWriter) {
        this.writeMonitor = progressMonitor;
        this.out = outputStream;
        this.packWriter = packWriter;
    }

    @Override // java.io.OutputStream
    public final void write(int i) throws IOException {
        this.count++;
        this.out.write(i);
        this.md.update((byte) i);
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr, int i, int i2) throws IOException {
        while (i2 > 0) {
            int iMin = Math.min(i2, 131072);
            long j = this.count + ((long) iMin);
            this.count = j;
            if (this.checkCancelAt <= j) {
                if (this.writeMonitor.isCancelled()) {
                    throw new IOException(JGitText.get().packingCancelledDuringObjectsWriting);
                }
                this.checkCancelAt = this.count + 131072;
            }
            this.out.write(bArr, i, iMin);
            this.md.update(bArr, i, iMin);
            i += iMin;
            i2 -= iMin;
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    final void writeFileHeader(int i, long j) throws IOException {
        System.arraycopy(Constants.PACK_SIGNATURE, 0, this.headerBuffer, 0, 4);
        NB.encodeInt32(this.headerBuffer, 4, i);
        NB.encodeInt32(this.headerBuffer, 8, (int) j);
        write(this.headerBuffer, 0, 12);
        this.ofsDelta = this.packWriter.isDeltaBaseAsOffset();
    }

    public final void writeObject(ObjectToPack objectToPack) throws IOException {
        this.packWriter.writeObject(this, objectToPack);
    }

    public final void writeHeader(ObjectToPack objectToPack, long j) throws IOException {
        ObjectToPack deltaBase = objectToPack.getDeltaBase();
        if (deltaBase != null && (deltaBase.isWritten() & this.ofsDelta)) {
            write(this.headerBuffer, 0, ofsDelta(this.count - deltaBase.getOffset(), this.headerBuffer, objectHeader(j, 6, this.headerBuffer)));
        } else if (objectToPack.isDeltaRepresentation()) {
            int iObjectHeader = objectHeader(j, 7, this.headerBuffer);
            objectToPack.getDeltaBaseId().copyRawTo(this.headerBuffer, iObjectHeader);
            write(this.headerBuffer, 0, iObjectHeader + 20);
        } else {
            write(this.headerBuffer, 0, objectHeader(j, objectToPack.getType(), this.headerBuffer));
        }
    }

    private static final int objectHeader(long j, int i, byte[] bArr) {
        byte b = (byte) (((long) (i << 4)) | (15 & j));
        long j2 = j >>> 4;
        int i2 = 0;
        while (j2 != 0) {
            bArr[i2] = (byte) (b | 128);
            b = (byte) (127 & j2);
            j2 >>>= 7;
            i2++;
        }
        int i3 = i2 + 1;
        bArr[i2] = b;
        return i3;
    }

    private static final int ofsDelta(long j, byte[] bArr, int i) {
        int iOfsDeltaVarIntLength = i + ofsDeltaVarIntLength(j);
        int i2 = iOfsDeltaVarIntLength - 1;
        bArr[i2] = (byte) (j & 127);
        while (true) {
            long j2 = j >>> 7;
            if (j2 == 0) {
                return iOfsDeltaVarIntLength;
            }
            i2--;
            j = j2 - 1;
            bArr[i2] = (byte) ((j & 127) | 128);
        }
    }

    public final byte[] getCopyBuffer() {
        return this.copyBuffer;
    }

    void endObject() {
        this.writeMonitor.update(1);
    }

    public final long length() {
        return this.count;
    }

    final byte[] getDigest() {
        return this.md.digest();
    }
}
