package org.jf.dexlib2.dexbacked;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.lang3.CharEncoding;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.raw.OdexHeaderItem;
import org.jf.dexlib2.dexbacked.util.VariableSizeList;
import org.jf.dexlib2.util.DexUtil;
import org.jf.util.io.ByteStreams;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedOdexFile extends DexBackedDexFile {
    private static final int DEPENDENCY_COUNT_OFFSET = 12;
    private static final int DEPENDENCY_START_OFFSET = 16;
    private final byte[] odexBuf;

    @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile
    public boolean supportsOptimizedOpcodes() {
        return true;
    }

    public DexBackedOdexFile(Opcodes opcodes, byte[] bArr, byte[] bArr2) {
        super(opcodes, bArr2);
        this.odexBuf = bArr;
    }

    public List<String> getDependencies() {
        int dependenciesOffset = OdexHeaderItem.getDependenciesOffset(this.odexBuf) - OdexHeaderItem.getDexOffset(this.odexBuf);
        final DexBuffer dexBuffer = new DexBuffer(getBuffer().buf, 0);
        return new VariableSizeList<String>(getDataBuffer(), dependenciesOffset + 16, dexBuffer.readInt(dependenciesOffset + 12)) { // from class: org.jf.dexlib2.dexbacked.DexBackedOdexFile.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeList
            public String readNextItem(DexReader dexReader, int i) {
                int i2 = dexReader.readInt();
                int offset = dexReader.getOffset();
                dexReader.moveRelative(i2 + 20);
                try {
                    return new String(dexBuffer.buf, offset, i2 - 1, CharEncoding.US_ASCII);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public static DexBackedOdexFile fromInputStream(Opcodes opcodes, InputStream inputStream) throws IOException {
        DexUtil.verifyOdexHeader(inputStream);
        inputStream.reset();
        byte[] bArr = new byte[40];
        ByteStreams.readFully(inputStream, bArr);
        int dexOffset = OdexHeaderItem.getDexOffset(bArr);
        if (dexOffset > 40) {
            ByteStreams.skipFully(inputStream, dexOffset - 40);
        }
        return new DexBackedOdexFile(opcodes, bArr, ByteStreams.toByteArray(inputStream));
    }

    public int getOdexVersion() {
        return OdexHeaderItem.getVersion(this.odexBuf, 0);
    }

    public static class NotAnOdexFile extends RuntimeException {
        public NotAnOdexFile() {
        }

        public NotAnOdexFile(Throwable th) {
            super(th);
        }

        public NotAnOdexFile(String str) {
            super(str);
        }

        public NotAnOdexFile(String str, Throwable th) {
            super(str, th);
        }
    }
}
