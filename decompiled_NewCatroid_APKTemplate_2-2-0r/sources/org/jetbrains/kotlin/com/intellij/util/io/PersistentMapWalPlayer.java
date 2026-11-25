package org.jetbrains.kotlin.com.intellij.util.io;

import androidx.exifinterface.media.ExifInterface;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.util.CompressionUtil;

/* JADX INFO: compiled from: writeAheadLog.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B)\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u0015\u001a\u00020\u0016H\u0016J \u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\fH\u0002J\u0016\u0010\u001c\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u001dH\u0002J\u0018\u0010\u001e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001d0\u001fR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000eX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u0012X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/PersistentMapWalPlayer;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Ljava/io/Closeable;", "keyDescriptor", "Lorg/jetbrains/kotlin/com/intellij/util/io/KeyDescriptor;", "valueExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "file", "Ljava/nio/file/Path;", "(Lcom/intellij/util/io/KeyDescriptor;Lcom/intellij/util/io/DataExternalizer;Ljava/nio/file/Path;)V", "input", "Ljava/io/DataInputStream;", "useCompression", "", "getUseCompression$intellij_platform_util", "()Z", "version", "", "getVersion", "()I", "close", "", "ensureCompatible", "expectedVersion", "readByteArray", "", "inputStream", "readNextEvent", "Lorg/jetbrains/kotlin/com/intellij/util/io/WalEvent;", "readWal", "Lkotlin/sequences/Sequence;", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
public final class PersistentMapWalPlayer<K, V> implements Closeable {
    private final DataInputStream input;
    private final KeyDescriptor<K> keyDescriptor;
    private final boolean useCompression;
    private final DataExternalizer<V> valueExternalizer;
    private final int version;

    @Metadata(k = 3, mv = {1, 5, 1})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WalOpCode.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[WalOpCode.PUT.ordinal()] = 1;
            iArr[WalOpCode.REMOVE.ordinal()] = 2;
            iArr[WalOpCode.APPEND.ordinal()] = 3;
        }
    }

    public PersistentMapWalPlayer(KeyDescriptor<K> keyDescriptor, DataExternalizer<V> valueExternalizer, Path file) throws IOException {
        Intrinsics.checkNotNullParameter(keyDescriptor, "keyDescriptor");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        Intrinsics.checkNotNullParameter(file, "file");
        this.keyDescriptor = keyDescriptor;
        this.valueExternalizer = valueExternalizer;
        if (!Files.exists(file, new LinkOption[0])) {
            throw new FileNotFoundException(file.toString());
        }
        InputStream inputStreamNewInputStream = Files.newInputStream(file, new OpenOption[0]);
        Intrinsics.checkNotNullExpressionValue(inputStreamNewInputStream, "Files.newInputStream(file)");
        DataInputStream dataInputStream = new DataInputStream(inputStreamNewInputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStreamNewInputStream : new BufferedInputStream(inputStreamNewInputStream, 8192));
        this.input = dataInputStream;
        ensureCompatible(this.version, dataInputStream, file);
        this.useCompression = dataInputStream.readBoolean();
    }

    /* JADX INFO: renamed from: getUseCompression$intellij_platform_util, reason: from getter */
    public final boolean getUseCompression() {
        return this.useCompression;
    }

    private final void ensureCompatible(int expectedVersion, DataInputStream input, Path file) throws IOException {
        int i = DataInputOutputUtil.readINT(input);
        if (i != expectedVersion) {
            throw new VersionUpdatedException(file, Integer.valueOf(expectedVersion), Integer.valueOf(i));
        }
    }

    public final Sequence<WalEvent<K, V>> readWal() {
        return SequencesKt.generateSequence(new Function0<WalEvent<K, V>>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapWalPlayer.readWal.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final WalEvent<K, V> invoke() {
                return PersistentMapWalPlayer.this.readNextEvent();
            }
        });
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.input.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final WalEvent<K, V> readNextEvent() throws IOException {
        try {
            WalRecord walRecord = WalRecord.INSTANCE.read(this.input);
            if (WriteAheadLogKt.getDebugWalRecords()) {
                System.out.println((Object) ("read: " + walRecord));
            }
            DataInputStream inputStream = walRecord.getPayload().toInputStream();
            Intrinsics.checkNotNullExpressionValue(inputStream, "record.payload.toInputStream()");
            int i = WhenMappings.$EnumSwitchMapping$0[walRecord.getOpCode().ordinal()];
            if (i == 1) {
                return new WalEvent.PutEvent(this.keyDescriptor.read(inputStream), WriteAheadLogKt.readData(readByteArray(inputStream), this.valueExternalizer));
            }
            if (i == 2) {
                return new WalEvent.RemoveEvent(this.keyDescriptor.read(inputStream));
            }
            if (i == 3) {
                return new WalEvent.AppendEvent(this.keyDescriptor.read(inputStream), readByteArray(inputStream));
            }
            throw new NoWhenBranchMatchedException();
        } catch (EndOfLog unused) {
            return null;
        } catch (IOException unused2) {
            WalEvent.CorruptionEvent corruptionEvent = WalEvent.CorruptionEvent.INSTANCE;
            if (corruptionEvent != null) {
                return corruptionEvent;
            }
            throw new NullPointerException("null cannot be cast to non-null type com.intellij.util.io.WalEvent<K, V>");
        }
    }

    private final byte[] readByteArray(DataInputStream inputStream) throws IOException {
        if (this.useCompression) {
            byte[] compressed = CompressionUtil.readCompressed(inputStream);
            Intrinsics.checkNotNullExpressionValue(compressed, "CompressionUtil.readCompressed(inputStream)");
            return compressed;
        }
        byte[] bArr = new byte[inputStream.readInt()];
        inputStream.readFully(bArr);
        return bArr;
    }
}
