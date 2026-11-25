package org.catrobat.catroid.utils;

import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/PcmToWavConverter.class
  classes14.dex
 */
/* JADX INFO: compiled from: PcmToWavConverter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\n\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bJ\"\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u000bH\u0007J \u0010\u0014\u001a\u00020\u00062\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0019J\u0018\u0010\u001a\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u000bH\u0002J\u0018\u0010\u001a\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010\u001a\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0004H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/utils/PcmToWavConverter;", "", "()V", "TAG", "", "convertPcmToWav", "", "input", "Ljava/io/File;", "output", "channelCount", "", "sampleRate", "bitsPerSample", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "", FirebaseAnalytics.Param.SOURCE, "Ljava/io/InputStream;", "Ljava/io/OutputStream;", "bufferSize", "writePcmToFile", "buffer", "", "file", "append", "", "writeToOutput", "data", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PcmToWavConverter {
    public static final PcmToWavConverter INSTANCE = new PcmToWavConverter();
    private static final String TAG = Reflection.getOrCreateKotlinClass(PcmToWavConverter.class).getSimpleName();

    public final long copy(InputStream source, OutputStream output) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(output, "output");
        return copy$default(this, source, output, 0, 4, null);
    }

    private PcmToWavConverter() {
    }

    public final void convertPcmToWav(File input, File output, int channelCount, int sampleRate, int bitsPerSample) throws IOException {
        Intrinsics.checkNotNullParameter(input, "input");
        int length = (int) input.length();
        new FileOutputStream(output).close();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(output);
            try {
                FileOutputStream fileOutputStream2 = fileOutputStream;
                PcmToWavConverter pcmToWavConverter = INSTANCE;
                pcmToWavConverter.writeToOutput(fileOutputStream2, "RIFF");
                pcmToWavConverter.writeToOutput(fileOutputStream2, length + 36);
                pcmToWavConverter.writeToOutput(fileOutputStream2, "WAVE");
                pcmToWavConverter.writeToOutput(fileOutputStream2, "fmt ");
                pcmToWavConverter.writeToOutput(fileOutputStream2, 16);
                pcmToWavConverter.writeToOutput((OutputStream) fileOutputStream2, (short) 1);
                pcmToWavConverter.writeToOutput((OutputStream) fileOutputStream2, (short) channelCount);
                pcmToWavConverter.writeToOutput(fileOutputStream2, sampleRate);
                pcmToWavConverter.writeToOutput(fileOutputStream2, ((sampleRate * channelCount) * bitsPerSample) / 8);
                pcmToWavConverter.writeToOutput((OutputStream) fileOutputStream2, (short) ((channelCount * bitsPerSample) / 8));
                pcmToWavConverter.writeToOutput((OutputStream) fileOutputStream2, (short) bitsPerSample);
                pcmToWavConverter.writeToOutput(fileOutputStream2, "data");
                pcmToWavConverter.writeToOutput(fileOutputStream2, length);
                copy$default(pcmToWavConverter, new FileInputStream(input), fileOutputStream2, 0, 4, null);
                CloseableKt.closeFinally(fileOutputStream, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to convert file " + input.getName() + " to WAV for Huawei text-to-speech", e);
        }
    }

    public static /* synthetic */ long copy$default(PcmToWavConverter pcmToWavConverter, InputStream inputStream, OutputStream outputStream, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 10240;
        }
        return pcmToWavConverter.copy(inputStream, outputStream, i);
    }

    public final long copy(InputStream source, OutputStream output, int bufferSize) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(output, "output");
        byte[] bArr = new byte[bufferSize];
        long j = 0;
        while (true) {
            int i = source.read(bArr);
            if (i == -1) {
                return j;
            }
            output.write(bArr, 0, i);
            j += (long) i;
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0041 A[Catch: IOException -> 0x003d, TRY_LEAVE, TryCatch #8 {IOException -> 0x003d, blocks: (B:14:0x0039, B:18:0x0041), top: B:55:0x0039 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x008a A[Catch: IOException -> 0x0093, TryCatch #5 {IOException -> 0x0093, blocks: (B:29:0x0085, B:31:0x008a, B:33:0x008f), top: B:49:0x0085 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x008f A[Catch: IOException -> 0x0093, TRY_LEAVE, TryCatch #5 {IOException -> 0x0093, blocks: (B:29:0x0085, B:31:0x008a, B:33:0x008f), top: B:49:0x0085 }] */
    /* JADX WARN: Code duplicated, block: B:55:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:62:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r10v6, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r10v9, types: [java.io.FileOutputStream] */
    public final void writePcmToFile(byte[] buffer, File file, boolean append) throws Throwable {
        ?? fileOutputStream;
        String str;
        String name2;
        StringBuilder sb;
        ?? r10;
        Intrinsics.checkNotNullParameter(file, "file");
        RandomAccessFile randomAccessFile = null;
        try {
            try {
                if (append == 0) {
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        fileOutputStream.write(buffer);
                        fileOutputStream.flush();
                        r10 = fileOutputStream;
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }
                        if (r10 != 0) {
                            r10.close();
                        }
                        return;
                    } catch (IOException e) {
                        e = e;
                        Log.e(TAG, "Failed to write data " + file.getName() + " to WAV file for Huawei text-to-speech", e);
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }
                        if (fileOutputStream != 0) {
                            fileOutputStream.close();
                            return;
                        }
                        return;
                    }
                }
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
                try {
                    randomAccessFile2.seek(file.length());
                    randomAccessFile2.write(buffer);
                    randomAccessFile = randomAccessFile2;
                    r10 = 0;
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                        } catch (IOException e2) {
                            e = e2;
                            str = TAG;
                            name2 = file.getName();
                            sb = new StringBuilder("Failed to write data ");
                        }
                    }
                    if (r10 != 0) {
                        r10.close();
                    }
                    return;
                } catch (IOException e3) {
                    e = e3;
                    randomAccessFile = randomAccessFile2;
                    fileOutputStream = 0;
                    Log.e(TAG, "Failed to write data " + file.getName() + " to WAV file for Huawei text-to-speech", e);
                    try {
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }
                        if (fileOutputStream != 0) {
                            fileOutputStream.close();
                            return;
                        }
                        return;
                    } catch (IOException e4) {
                        e = e4;
                        str = TAG;
                        name2 = file.getName();
                        sb = new StringBuilder("Failed to write data ");
                    }
                } catch (Throwable th) {
                    th = th;
                    randomAccessFile = randomAccessFile2;
                    append = 0;
                    try {
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }
                        if (append != 0) {
                            append.close();
                        }
                    } catch (IOException e5) {
                        Log.e(TAG, "Failed to write data " + file.getName() + " to WAV file for Huawei text-to-speech", e5);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e6) {
            e = e6;
            fileOutputStream = randomAccessFile;
        } catch (Throwable th3) {
            th = th3;
            append = randomAccessFile;
        }
        Log.e(str, sb.append(name2).append(" to WAV file for Huawei text-to-speech").toString(), e);
    }

    private final void writeToOutput(OutputStream output, String data) throws IOException {
        int length = data.length();
        for (int i = 0; i < length; i++) {
            output.write(data.charAt(i));
        }
    }

    private final void writeToOutput(OutputStream output, int data) throws IOException {
        output.write(data);
        output.write(data >> 8);
        output.write(data >> 16);
        output.write(data >> 24);
    }

    private final void writeToOutput(OutputStream output, short data) throws IOException {
        output.write(data);
        output.write(data >> 8);
    }
}
