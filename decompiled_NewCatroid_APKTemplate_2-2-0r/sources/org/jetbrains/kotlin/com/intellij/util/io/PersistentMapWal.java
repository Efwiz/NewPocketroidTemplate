package org.jetbrains.kotlin.com.intellij.util.io;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.concurrent.ExecutorService;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.util.CompressionUtil;

/* JADX INFO: compiled from: writeAheadLog.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0012\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003BE\b\u0007\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\t¢\u0006\u0002\u0010\u000fJ\u001b\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00020\u001a¢\u0006\u0002\u0010\u001bJ\u001d\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00020\u001aH\u0002¢\u0006\u0002\u0010\u001eJ\b\u0010\u001f\u001a\u00020\u0017H\u0016J\u0006\u0010 \u001a\u00020\u0017J \u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0006\u0010#\u001a\u00020\u0017J\u001b\u0010$\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u00002\u0006\u0010%\u001a\u00028\u0001¢\u0006\u0002\u0010&J\u001d\u0010'\u001a\u00020\u001d2\u0006\u0010\u0018\u001a\u00028\u00002\u0006\u0010%\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010(J\u0013\u0010)\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u0000¢\u0006\u0002\u0010*J\u0015\u0010+\u001a\u00020\u001d2\u0006\u0010\u0018\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010,J\f\u0010-\u001a\u00020\u0017*\u00020\u001dH\u0002J\u0014\u0010.\u001a\u00020\u0017*\u00020/2\u0006\u00100\u001a\u00020\u0011H\u0002J\f\u00101\u001a\u00020/*\u00020\u001aH\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u0013X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/PersistentMapWal;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Ljava/io/Closeable;", "keyDescriptor", "Lorg/jetbrains/kotlin/com/intellij/util/io/KeyDescriptor;", "valueExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "useCompression", "", "file", "Ljava/nio/file/Path;", "walIoExecutor", "Ljava/util/concurrent/ExecutorService;", "compact", "(Lcom/intellij/util/io/KeyDescriptor;Lcom/intellij/util/io/DataExternalizer;ZLjava/nio/file/Path;Ljava/util/concurrent/ExecutorService;Z)V", "out", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataOutputStream;", "version", "", "getVersion", "()I", "appendData", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "appender", "Lorg/jetbrains/kotlin/com/intellij/util/io/AppendablePersistentMap$ValueDataAppender;", "(Ljava/lang/Object;Lcom/intellij/util/io/AppendablePersistentMap$ValueDataAppender;)V", "appendRecord", "Lorg/jetbrains/kotlin/com/intellij/util/io/WalRecord;", "(Ljava/lang/Object;Lcom/intellij/util/io/AppendablePersistentMap$ValueDataAppender;)Lcom/intellij/util/io/WalRecord;", "close", "closeAndDelete", "ensureCompatible", "expectedVersion", "flush", "put", "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "putRecord", "(Ljava/lang/Object;Ljava/lang/Object;)Lcom/intellij/util/io/WalRecord;", ProductAction.ACTION_REMOVE, ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "removeRecord", "(Ljava/lang/Object;)Lcom/intellij/util/io/WalRecord;", "submitWrite", "write", "", "outputStream", "writeToByteArray", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
public final class PersistentMapWal<K, V> implements Closeable {
    private final Path file;
    private final KeyDescriptor<K> keyDescriptor;
    private final DataOutputStream out;
    private final boolean useCompression;
    private final DataExternalizer<V> valueExternalizer;
    private final int version;
    private final ExecutorService walIoExecutor;

    public PersistentMapWal(KeyDescriptor<K> keyDescriptor, DataExternalizer<V> valueExternalizer, boolean z, Path file, ExecutorService walIoExecutor, boolean z2) throws IOException {
        Path pathTryCompact;
        Intrinsics.checkNotNullParameter(keyDescriptor, "keyDescriptor");
        Intrinsics.checkNotNullParameter(valueExternalizer, "valueExternalizer");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(walIoExecutor, "walIoExecutor");
        this.keyDescriptor = keyDescriptor;
        this.valueExternalizer = valueExternalizer;
        this.useCompression = z;
        this.file = file;
        this.walIoExecutor = walIoExecutor;
        if (z2 && (pathTryCompact = WriteAheadLogKt.tryCompact(file, keyDescriptor, valueExternalizer)) != null) {
            FileUtil.deleteWithRenaming(file);
            FileUtil.rename(pathTryCompact.toFile(), file.toFile());
        }
        ensureCompatible(this.version, z, file);
        OutputStream outputStreamNewOutputStream = Files.newOutputStream(file, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        Intrinsics.checkNotNullExpressionValue(outputStreamNewOutputStream, "Files.newOutputStream(fi…tandardOpenOption.APPEND)");
        this.out = new DataOutputStream(outputStreamNewOutputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStreamNewOutputStream : new BufferedOutputStream(outputStreamNewOutputStream, 8192));
    }

    public /* synthetic */ PersistentMapWal(KeyDescriptor keyDescriptor, DataExternalizer dataExternalizer, boolean z, Path path, ExecutorService executorService, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) throws IOException {
        this(keyDescriptor, dataExternalizer, z, path, executorService, (i & 32) != 0 ? false : z2);
    }

    private final void ensureCompatible(int expectedVersion, boolean useCompression, Path file) throws IOException {
        if (!Files.exists(file, new LinkOption[0])) {
            Files.createDirectories(file.getParent(), new FileAttribute[0]);
            DataOutputStream dataOutputStream = new DataOutputStream(Files.newOutputStream(file, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE));
            try {
                DataOutputStream dataOutputStream2 = dataOutputStream;
                DataInputOutputUtil.writeINT(dataOutputStream2, expectedVersion);
                dataOutputStream2.writeBoolean(useCompression);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(dataOutputStream, null);
                return;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(dataOutputStream, th);
                    throw th2;
                }
            }
        }
        DataInputStream dataInputStream = new DataInputStream(Files.newInputStream(file, StandardOpenOption.READ));
        try {
            DataInputStream dataInputStream2 = dataInputStream;
            Pair pair = TuplesKt.to(Integer.valueOf(DataInputOutputUtil.readINT(dataInputStream2)), Boolean.valueOf(dataInputStream2.readBoolean()));
            CloseableKt.closeFinally(dataInputStream, null);
            int iIntValue = ((Number) pair.component1()).intValue();
            boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
            if (iIntValue != expectedVersion) {
                throw new VersionUpdatedException(file, Integer.valueOf(expectedVersion), Integer.valueOf(iIntValue));
            }
            if (zBooleanValue != useCompression) {
                throw new VersionUpdatedException(file, Boolean.valueOf(useCompression), Boolean.valueOf(zBooleanValue));
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(dataInputStream, th3);
                throw th4;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void write(byte[] bArr, DataOutputStream dataOutputStream) throws IOException {
        if (this.useCompression) {
            CompressionUtil.writeCompressed(dataOutputStream, bArr, 0, bArr.length);
        } else {
            dataOutputStream.writeInt(bArr.length);
            dataOutputStream.write(bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final byte[] writeToByteArray(AppendablePersistentMap.ValueDataAppender valueDataAppender) throws IOException {
        UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
        valueDataAppender.append(new DataOutputStream(unsyncByteArrayOutputStream));
        byte[] byteArray = unsyncByteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "baos.toByteArray()");
        return byteArray;
    }

    private final WalRecord appendRecord(final K k, final AppendablePersistentMap.ValueDataAppender valueDataAppender) {
        return WalRecord.INSTANCE.writeRecord(WalOpCode.APPEND, new Function1<DataOutputStream, Unit>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapWal.appendRecord.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DataOutputStream dataOutputStream) throws IOException {
                invoke2(dataOutputStream);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DataOutputStream it) throws IOException {
                Intrinsics.checkNotNullParameter(it, "it");
                PersistentMapWal.this.keyDescriptor.save(it, k);
                PersistentMapWal persistentMapWal = PersistentMapWal.this;
                persistentMapWal.write(persistentMapWal.writeToByteArray(valueDataAppender), it);
            }
        });
    }

    private final WalRecord putRecord(final K k, final V v) {
        return WalRecord.INSTANCE.writeRecord(WalOpCode.PUT, new Function1<DataOutputStream, Unit>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapWal.putRecord.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DataOutputStream dataOutputStream) throws IOException {
                invoke2(dataOutputStream);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DataOutputStream it) throws IOException {
                Intrinsics.checkNotNullParameter(it, "it");
                PersistentMapWal.this.keyDescriptor.save(it, k);
                PersistentMapWal persistentMapWal = PersistentMapWal.this;
                persistentMapWal.write(WriteAheadLogKt.writeData(v, persistentMapWal.valueExternalizer), it);
            }
        });
    }

    private final WalRecord removeRecord(final K k) {
        return WalRecord.INSTANCE.writeRecord(WalOpCode.REMOVE, new Function1<DataOutputStream, Unit>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapWal.removeRecord.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DataOutputStream dataOutputStream) throws IOException {
                invoke2(dataOutputStream);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DataOutputStream it) throws IOException {
                Intrinsics.checkNotNullParameter(it, "it");
                PersistentMapWal.this.keyDescriptor.save(it, k);
            }
        });
    }

    private final void submitWrite(final WalRecord walRecord) {
        this.walIoExecutor.submit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapWal.submitWrite.1
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                if (WriteAheadLogKt.getDebugWalRecords()) {
                    System.out.println((Object) ("write: " + walRecord));
                }
                walRecord.write(PersistentMapWal.this.out);
            }
        });
    }

    public final void appendData(K k, AppendablePersistentMap.ValueDataAppender appender) throws IOException {
        Intrinsics.checkNotNullParameter(appender, "appender");
        submitWrite(appendRecord(k, appender));
    }

    public final void put(K key, V value) throws IOException {
        submitWrite(putRecord(key, value));
    }

    public final void remove(K key) throws IOException {
        submitWrite(removeRecord(key));
    }

    public final void flush() throws IOException {
        this.walIoExecutor.submit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapWal.flush.1
            @Override // java.lang.Runnable
            public final void run() {
                PersistentMapWal.this.out.flush();
            }
        }).get();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.walIoExecutor.submit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.PersistentMapWal.close.1
            @Override // java.lang.Runnable
            public final void run() {
                PersistentMapWal.this.out.close();
            }
        }).get();
    }

    public final void closeAndDelete() throws IOException {
        close();
        FileUtil.deleteWithRenaming(this.file);
    }
}
