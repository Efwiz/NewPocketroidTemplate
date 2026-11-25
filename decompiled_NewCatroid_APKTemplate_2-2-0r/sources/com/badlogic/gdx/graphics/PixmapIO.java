package com.badlogic.gdx.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/PixmapIO.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PixmapIO {
    public static void writeCIM(FileHandle fileHandle, Pixmap pixmap) throws Throwable {
        CIM.write(fileHandle, pixmap);
    }

    public static Pixmap readCIM(FileHandle fileHandle) {
        return CIM.read(fileHandle);
    }

    public static void writePNG(FileHandle fileHandle, Pixmap pixmap, int i, boolean z) {
        try {
            PNG png = new PNG((int) (pixmap.getWidth() * pixmap.getHeight() * 1.5f));
            try {
                png.setFlipY(z);
                png.setCompression(i);
                png.write(fileHandle, pixmap);
            } finally {
                png.dispose();
            }
        } catch (IOException e) {
            throw new GdxRuntimeException("Error writing PNG: " + fileHandle, e);
        }
    }

    public static void writePNG(FileHandle fileHandle, Pixmap pixmap) {
        writePNG(fileHandle, pixmap, -1, false);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/PixmapIO$CIM.class
      classes8.dex
     */
    private static class CIM {
        private static final int BUFFER_SIZE = 32000;
        private static final byte[] writeBuffer = new byte[BUFFER_SIZE];
        private static final byte[] readBuffer = new byte[BUFFER_SIZE];

        private CIM() {
        }

        public static void write(FileHandle fileHandle, Pixmap pixmap) throws Throwable {
            DataOutputStream dataOutputStream = null;
            try {
                try {
                    DataOutputStream dataOutputStream2 = new DataOutputStream(new DeflaterOutputStream(fileHandle.write(false)));
                    try {
                        dataOutputStream2.writeInt(pixmap.getWidth());
                        dataOutputStream2.writeInt(pixmap.getHeight());
                        dataOutputStream2.writeInt(Pixmap.Format.toGdx2DPixmapFormat(pixmap.getFormat()));
                        ByteBuffer pixels = pixmap.getPixels();
                        pixels.position(0);
                        pixels.limit(pixels.capacity());
                        int iCapacity = pixels.capacity() % BUFFER_SIZE;
                        int iCapacity2 = pixels.capacity() / BUFFER_SIZE;
                        synchronized (writeBuffer) {
                            for (int i = 0; i < iCapacity2; i++) {
                                byte[] bArr = writeBuffer;
                                pixels.get(bArr);
                                dataOutputStream2.write(bArr);
                            }
                            byte[] bArr2 = writeBuffer;
                            pixels.get(bArr2, 0, iCapacity);
                            dataOutputStream2.write(bArr2, 0, iCapacity);
                        }
                        pixels.position(0);
                        pixels.limit(pixels.capacity());
                        StreamUtils.closeQuietly(dataOutputStream2);
                    } catch (Exception e) {
                        e = e;
                        throw new GdxRuntimeException("Couldn't write Pixmap to file '" + fileHandle + FormatHelper.QUOTE, e);
                    } catch (Throwable th) {
                        th = th;
                        dataOutputStream = dataOutputStream2;
                        StreamUtils.closeQuietly(dataOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        }

        /* JADX WARN: Not initialized variable reg: 1, insn: 0x0081: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:25:0x0081 */
        public static Pixmap read(FileHandle fileHandle) throws Throwable {
            Exception e;
            Closeable closeable;
            Closeable closeable2 = null;
            try {
                try {
                    DataInputStream dataInputStream = new DataInputStream(new InflaterInputStream(new BufferedInputStream(fileHandle.read())));
                    try {
                        Pixmap pixmap = new Pixmap(dataInputStream.readInt(), dataInputStream.readInt(), Pixmap.Format.fromGdx2DPixmapFormat(dataInputStream.readInt()));
                        ByteBuffer pixels = pixmap.getPixels();
                        pixels.position(0);
                        pixels.limit(pixels.capacity());
                        synchronized (readBuffer) {
                            while (true) {
                                byte[] bArr = readBuffer;
                                int i = dataInputStream.read(bArr);
                                if (i > 0) {
                                    pixels.put(bArr, 0, i);
                                }
                            }
                        }
                        pixels.position(0);
                        pixels.limit(pixels.capacity());
                        StreamUtils.closeQuietly(dataInputStream);
                        return pixmap;
                    } catch (Exception e2) {
                        e = e2;
                        throw new GdxRuntimeException("Couldn't read Pixmap from file '" + fileHandle + FormatHelper.QUOTE, e);
                    }
                } catch (Throwable th) {
                    th = th;
                    closeable2 = closeable;
                    StreamUtils.closeQuietly(closeable2);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
            } catch (Throwable th2) {
                th = th2;
                StreamUtils.closeQuietly(closeable2);
                throw th;
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/PixmapIO$PNG.class
      classes8.dex
     */
    public static class PNG implements Disposable {
        private static final byte COLOR_ARGB = 6;
        private static final byte COMPRESSION_DEFLATE = 0;
        private static final byte FILTER_NONE = 0;
        private static final int IDAT = 1229209940;
        private static final int IEND = 1229278788;
        private static final int IHDR = 1229472850;
        private static final byte INTERLACE_NONE = 0;
        private static final byte PAETH = 4;
        private static final byte[] SIGNATURE = {Opcodes.OPC_l2f, 80, 78, 71, 13, 10, 26, 10};
        private final ChunkBuffer buffer;
        private ByteArray curLineBytes;
        private final Deflater deflater;
        private boolean flipY;
        private int lastLineLen;
        private ByteArray lineOutBytes;
        private ByteArray prevLineBytes;

        public PNG() {
            this(16384);
        }

        public PNG(int i) {
            this.flipY = true;
            this.buffer = new ChunkBuffer(i);
            this.deflater = new Deflater();
        }

        public void setFlipY(boolean z) {
            this.flipY = z;
        }

        public void setCompression(int i) {
            this.deflater.setLevel(i);
        }

        public void write(FileHandle fileHandle, Pixmap pixmap) throws IOException {
            OutputStream outputStreamWrite = fileHandle.write(false);
            try {
                write(outputStreamWrite, pixmap);
            } finally {
                StreamUtils.closeQuietly(outputStreamWrite);
            }
        }

        public void write(OutputStream outputStream, Pixmap pixmap) throws IOException {
            byte[] bArrEnsureCapacity;
            byte[] bArrEnsureCapacity2;
            byte[] bArrEnsureCapacity3;
            boolean z;
            int i;
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(this.buffer, this.deflater);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.write(SIGNATURE);
            this.buffer.writeInt(IHDR);
            this.buffer.writeInt(pixmap.getWidth());
            this.buffer.writeInt(pixmap.getHeight());
            this.buffer.writeByte(8);
            this.buffer.writeByte(6);
            int i2 = 0;
            this.buffer.writeByte(0);
            this.buffer.writeByte(0);
            this.buffer.writeByte(0);
            this.buffer.endChunk(dataOutputStream);
            this.buffer.writeInt(IDAT);
            this.deflater.reset();
            int width = pixmap.getWidth() * 4;
            ByteArray byteArray = this.lineOutBytes;
            if (byteArray == null) {
                ByteArray byteArray2 = new ByteArray(width);
                this.lineOutBytes = byteArray2;
                bArrEnsureCapacity = byteArray2.items;
                ByteArray byteArray3 = new ByteArray(width);
                this.curLineBytes = byteArray3;
                bArrEnsureCapacity2 = byteArray3.items;
                ByteArray byteArray4 = new ByteArray(width);
                this.prevLineBytes = byteArray4;
                bArrEnsureCapacity3 = byteArray4.items;
            } else {
                bArrEnsureCapacity = byteArray.ensureCapacity(width);
                bArrEnsureCapacity2 = this.curLineBytes.ensureCapacity(width);
                bArrEnsureCapacity3 = this.prevLineBytes.ensureCapacity(width);
                int i3 = this.lastLineLen;
                for (int i4 = 0; i4 < i3; i4++) {
                    bArrEnsureCapacity3[i4] = 0;
                }
            }
            this.lastLineLen = width;
            ByteBuffer pixels = pixmap.getPixels();
            int iPosition = pixels.position();
            int i5 = 1;
            boolean z2 = pixmap.getFormat() == Pixmap.Format.RGBA8888;
            int height = pixmap.getHeight();
            int i6 = 0;
            while (i6 < height) {
                int i7 = this.flipY ? (height - i6) - i5 : i6;
                if (z2) {
                    pixels.position(i7 * width);
                    pixels.get(bArrEnsureCapacity2, i2, width);
                    i = i2;
                    z = z2;
                } else {
                    int i8 = i2;
                    int i9 = i8;
                    while (i8 < pixmap.getWidth()) {
                        int pixel = pixmap.getPixel(i8, i7);
                        bArrEnsureCapacity2[i9] = (byte) ((pixel >> 24) & 255);
                        int i10 = i7;
                        bArrEnsureCapacity2[i9 + 1] = (byte) ((pixel >> 16) & 255);
                        int i11 = i9 + 3;
                        bArrEnsureCapacity2[i9 + 2] = (byte) ((pixel >> 8) & 255);
                        i9 += 4;
                        bArrEnsureCapacity2[i11] = (byte) (pixel & 255);
                        i8++;
                        z2 = z2;
                        i7 = i10;
                    }
                    z = z2;
                    i = 0;
                }
                bArrEnsureCapacity[i] = (byte) (bArrEnsureCapacity2[i] - bArrEnsureCapacity3[i]);
                bArrEnsureCapacity[1] = (byte) (bArrEnsureCapacity2[1] - bArrEnsureCapacity3[1]);
                bArrEnsureCapacity[2] = (byte) (bArrEnsureCapacity2[2] - bArrEnsureCapacity3[2]);
                bArrEnsureCapacity[3] = (byte) (bArrEnsureCapacity2[3] - bArrEnsureCapacity3[3]);
                int i12 = 4;
                while (i12 < width) {
                    int i13 = i12 - 4;
                    int i14 = bArrEnsureCapacity2[i13] & 255;
                    int i15 = bArrEnsureCapacity3[i12] & 255;
                    int i16 = bArrEnsureCapacity3[i13] & 255;
                    int i17 = (i14 + i15) - i16;
                    int i18 = i17 - i14;
                    if (i18 < 0) {
                        i18 = -i18;
                    }
                    byte[] bArr = bArrEnsureCapacity3;
                    int i19 = i17 - i15;
                    if (i19 < 0) {
                        i19 = -i19;
                    }
                    int i20 = i17 - i16;
                    if (i20 < 0) {
                        i20 = -i20;
                    }
                    bArrEnsureCapacity[i12] = (byte) (bArrEnsureCapacity2[i12] - ((i18 > i19 || i18 > i20) ? i19 <= i20 ? i15 : i16 : i14));
                    i12++;
                    bArrEnsureCapacity3 = bArr;
                }
                byte[] bArr2 = bArrEnsureCapacity3;
                deflaterOutputStream.write(4);
                i2 = 0;
                deflaterOutputStream.write(bArrEnsureCapacity, 0, width);
                i6++;
                bArrEnsureCapacity3 = bArrEnsureCapacity2;
                z2 = z;
                bArrEnsureCapacity2 = bArr2;
                i5 = 1;
            }
            pixels.position(iPosition);
            deflaterOutputStream.finish();
            this.buffer.endChunk(dataOutputStream);
            this.buffer.writeInt(IEND);
            this.buffer.endChunk(dataOutputStream);
            outputStream.flush();
        }

        @Override // com.badlogic.gdx.utils.Disposable
        public void dispose() {
            this.deflater.end();
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/PixmapIO$PNG$ChunkBuffer.class
          classes8.dex
         */
        static class ChunkBuffer extends DataOutputStream {
            final ByteArrayOutputStream buffer;
            final CRC32 crc;

            ChunkBuffer(int i) {
                this(new ByteArrayOutputStream(i), new CRC32());
            }

            private ChunkBuffer(ByteArrayOutputStream byteArrayOutputStream, CRC32 crc32) {
                super(new CheckedOutputStream(byteArrayOutputStream, crc32));
                this.buffer = byteArrayOutputStream;
                this.crc = crc32;
            }

            public void endChunk(DataOutputStream dataOutputStream) throws IOException {
                flush();
                dataOutputStream.writeInt(this.buffer.size() - 4);
                this.buffer.writeTo(dataOutputStream);
                dataOutputStream.writeInt((int) this.crc.getValue());
                this.buffer.reset();
                this.crc.reset();
            }
        }
    }
}
