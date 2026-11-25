package ar.com.hjg.pngj;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;

/* JADX INFO: loaded from: classes.dex */
public final class PngHelperInternal {
    public static final String KEY_LOGGER = "ar.com.pngj";
    public static final Logger LOGGER = Logger.getLogger(KEY_LOGGER);
    public static String charsetLatin1name = "ISO-8859-1";
    public static Charset charsetLatin1 = Charset.forName("ISO-8859-1");
    public static String charsetUTF8name = "UTF-8";
    public static Charset charsetUTF8 = Charset.forName("UTF-8");
    private static ThreadLocal<Boolean> DEBUG = new ThreadLocal<Boolean>() { // from class: ar.com.hjg.pngj.PngHelperInternal.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    public static int doubleToInt100000(double d) {
        return (int) ((d * 100000.0d) + 0.5d);
    }

    static final int filterPaethPredictor(int i, int i2, int i3) {
        int i4 = (i + i2) - i3;
        int i5 = i4 >= i ? i4 - i : i - i4;
        int i6 = i4 >= i2 ? i4 - i2 : i2 - i4;
        int i7 = i4 >= i3 ? i4 - i3 : i3 - i4;
        if (i5 > i6 || i5 > i7) {
            return i6 <= i7 ? i2 : i3;
        }
        return i;
    }

    public static int filterRowNone(int i) {
        return i & 255;
    }

    public static int filterRowSub(int i, int i2) {
        return (i - i2) & 255;
    }

    public static int filterRowUp(int i, int i2) {
        return (i - i2) & 255;
    }

    public static double intToDouble100000(int i) {
        return ((double) i) / 100000.0d;
    }

    public static byte[] getPngIdSignature() {
        return new byte[]{Opcodes.OPC_l2f, 80, 78, 71, 13, 10, 26, 10};
    }

    public static int readByte(InputStream inputStream) {
        try {
            return inputStream.read();
        } catch (IOException e) {
            throw new PngjInputException("error reading byte", e);
        }
    }

    public static int readInt2(InputStream inputStream) {
        try {
            int i = inputStream.read();
            int i2 = inputStream.read();
            if (i == -1 || i2 == -1) {
                return -1;
            }
            return i2 | (i << 8);
        } catch (IOException e) {
            throw new PngjInputException("error reading Int2", e);
        }
    }

    public static int readInt4(InputStream inputStream) {
        try {
            int i = inputStream.read();
            int i2 = inputStream.read();
            int i3 = inputStream.read();
            int i4 = inputStream.read();
            if (i == -1 || i2 == -1 || i3 == -1 || i4 == -1) {
                return -1;
            }
            return (i << 24) | (i2 << 16) | ((i3 << 8) + i4);
        } catch (IOException e) {
            throw new PngjInputException("error reading Int4", e);
        }
    }

    public static int readInt1fromByte(byte[] bArr, int i) {
        return bArr[i] & 255;
    }

    public static int readInt2fromBytes(byte[] bArr, int i) {
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    public static final int readInt4fromBytes(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    public static void writeByte(OutputStream outputStream, byte b) {
        try {
            outputStream.write(b);
        } catch (IOException e) {
            throw new PngjOutputException(e);
        }
    }

    public static void writeByte(OutputStream outputStream, byte[] bArr) {
        try {
            outputStream.write(bArr);
        } catch (IOException e) {
            throw new PngjOutputException(e);
        }
    }

    public static void writeInt2(OutputStream outputStream, int i) {
        writeBytes(outputStream, new byte[]{(byte) ((i >> 8) & 255), (byte) (i & 255)});
    }

    public static void writeInt4(OutputStream outputStream, int i) {
        byte[] bArr = new byte[4];
        writeInt4tobytes(i, bArr, 0);
        writeBytes(outputStream, bArr);
    }

    public static void writeInt2tobytes(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) ((i >> 8) & 255);
        bArr[i2 + 1] = (byte) (i & 255);
    }

    public static void writeInt4tobytes(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) ((i >> 24) & 255);
        bArr[i2 + 1] = (byte) ((i >> 16) & 255);
        bArr[i2 + 2] = (byte) ((i >> 8) & 255);
        bArr[i2 + 3] = (byte) (i & 255);
    }

    public static void readBytes(InputStream inputStream, byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return;
        }
        int i3 = 0;
        while (i3 < i2) {
            try {
                int i4 = inputStream.read(bArr, i + i3, i2 - i3);
                if (i4 < 1) {
                    throw new PngjInputException("error reading bytes, " + i4 + " !=" + i2);
                }
                i3 += i4;
            } catch (IOException e) {
                throw new PngjInputException("error reading", e);
            }
        }
    }

    public static void skipBytes(InputStream inputStream, long j) {
        while (j > 0) {
            try {
                long jSkip = inputStream.skip(j);
                if (jSkip > 0) {
                    j -= jSkip;
                } else if (jSkip == 0) {
                    if (inputStream.read() == -1) {
                        return;
                    } else {
                        j--;
                    }
                } else {
                    throw new IOException("skip() returned a negative value ???");
                }
            } catch (IOException e) {
                throw new PngjInputException(e);
            }
        }
    }

    public static void writeBytes(OutputStream outputStream, byte[] bArr) {
        try {
            outputStream.write(bArr);
        } catch (IOException e) {
            throw new PngjOutputException(e);
        }
    }

    public static void writeBytes(OutputStream outputStream, byte[] bArr, int i, int i2) {
        try {
            outputStream.write(bArr, i, i2);
        } catch (IOException e) {
            throw new PngjOutputException(e);
        }
    }

    public static void logdebug(String str) {
        if (isDebug()) {
            System.err.println("logdebug: " + str);
        }
    }

    public static int filterRowAverage(int i, int i2, int i3) {
        return (i - ((i2 + i3) / 2)) & 255;
    }

    public static int filterRowPaeth(int i, int i2, int i3, int i4) {
        return (i - filterPaethPredictor(i2, i3, i4)) & 255;
    }

    public static void debug(Object obj) {
        debug(obj, 1, true);
    }

    static void debug(Object obj, int i) {
        debug(obj, i, true);
    }

    public static InputStream istreamFromFile(File file) {
        try {
            return new FileInputStream(file);
        } catch (Exception e) {
            throw new PngjInputException("Could not open " + file, e);
        }
    }

    static OutputStream ostreamFromFile(File file) {
        return ostreamFromFile(file, true);
    }

    static OutputStream ostreamFromFile(File file, boolean z) {
        return PngHelperInternal2.ostreamFromFile(file, z);
    }

    static void debug(Object obj, int i, boolean z) {
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[i + 1];
        String className = stackTraceElement.getClassName();
        System.err.println(className.substring(className.lastIndexOf(46) + 1) + "." + stackTraceElement.getMethodName() + "(" + stackTraceElement.getLineNumber() + "): " + (obj == null ? null : obj.toString()));
    }

    public static void setDebug(boolean z) {
        DEBUG.set(Boolean.valueOf(z));
    }

    public static boolean isDebug() {
        return DEBUG.get().booleanValue();
    }

    public static long getDigest(PngReader pngReader) {
        return pngReader.getSimpleDigest();
    }

    public static void initCrcForTests(PngReader pngReader) {
        pngReader.prepareSimpleDigestComputation();
    }

    public static long getRawIdatBytes(PngReader pngReader) {
        return pngReader.interlaced ? pngReader.getChunkseq().getDeinterlacer().getTotalRawBytes() : pngReader.imgInfo.getTotalRawBytes();
    }
}
