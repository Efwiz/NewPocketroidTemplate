package org.jetbrains.kotlin.com.intellij.util.io;

import androidx.exifinterface.media.ExifInterface;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.zip.CRC32;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;

/* JADX INFO: compiled from: writeAheadLog.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000n\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a)\u0010\u0012\u001a\u0002H\u0013\"\u0004\b\u0000\u0010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0017H\u0002¢\u0006\u0002\u0010\u0018\u001a]\u0010\u0019\u001a\u0002H\u001a\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u0013\"\u0004\b\u0002\u0010\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001b0\u001f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00130\u00172\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u001a0!H\u0002¢\u0006\u0002\u0010\"\u001aB\u0010#\u001a\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00130$\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001b0\u001f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0017\u001a(\u0010%\u001a\b\u0012\u0004\u0012\u0002H'0&\"\u0004\b\u0000\u0010'2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0\u001f\u001a0\u0010)\u001a\b\u0012\u0004\u0012\u0002H'0*\"\u0004\b\u0000\u0010'2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010+\u001a\u00020\u001d2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0\u001f\u001aJ\u0010,\u001a\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00130-\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010+\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001b0\u001f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0017\u001a:\u0010.\u001a\u0004\u0018\u00010\u001d\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u00132\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001b0\u001f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0017H\u0002\u001a)\u0010/\u001a\u00020\u0015\"\u0004\b\u0000\u0010\u00132\u0006\u00100\u001a\u0002H\u00132\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0017H\u0002¢\u0006\u0002\u00101\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\"\u0014\u0010\u000b\u001a\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\"\u0013\u0010\u000f\u001a\u00070\u0010¢\u0006\u0002\b\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"VERSION", "", "checksumGen", "Lkotlin/Function0;", "Ljava/util/zip/CRC32;", "debugWalRecords", "", "getDebugWalRecords", "()Z", "setDebugWalRecords", "(Z)V", "integerExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/EnumeratorIntegerDescriptor;", "getIntegerExternalizer", "()Lcom/intellij/util/io/EnumeratorIntegerDescriptor;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "readData", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "array", "", "valueExternalizer", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "([BLcom/intellij/util/io/DataExternalizer;)Ljava/lang/Object;", "restoreFromWal", "R", "K", "walFile", "Ljava/nio/file/Path;", "keyDescriptor", "Lorg/jetbrains/kotlin/com/intellij/util/io/KeyDescriptor;", "accumulator", "Lorg/jetbrains/kotlin/com/intellij/util/io/Accumulator;", "(Ljava/nio/file/Path;Lcom/intellij/util/io/KeyDescriptor;Lcom/intellij/util/io/DataExternalizer;Lcom/intellij/util/io/Accumulator;)Ljava/lang/Object;", "restoreHashMapFromWal", "", "restoreMemoryEnumeratorFromWal", "", "Data", "dataDescriptor", "restorePersistentEnumeratorFromWal", "Lorg/jetbrains/kotlin/com/intellij/util/io/PersistentEnumerator;", "outputMapFile", "restorePersistentMapFromWal", "Lorg/jetbrains/kotlin/com/intellij/util/io/PersistentMap;", "tryCompact", "writeData", "value", "(Ljava/lang/Object;Lcom/intellij/util/io/DataExternalizer;)[B", "intellij.platform.util"}, k = 2, mv = {1, 5, 1})
public final class WriteAheadLogKt {
    private static final Function0<CRC32> checksumGen = new Function0<CRC32>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.WriteAheadLogKt$checksumGen$1
        @Override // kotlin.jvm.functions.Function0
        public final CRC32 invoke() {
            return new CRC32();
        }
    };
    private static volatile boolean debugWalRecords;
    private static final Logger log;

    static {
        Logger logger = Logger.getInstance((Class<?>) WalRecord.class);
        Intrinsics.checkNotNullExpressionValue(logger, "Logger.getInstance(T::class.java)");
        log = logger;
    }

    public static final boolean getDebugWalRecords() {
        return debugWalRecords;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final EnumeratorIntegerDescriptor getIntegerExternalizer() {
        EnumeratorIntegerDescriptor enumeratorIntegerDescriptor = EnumeratorIntegerDescriptor.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(enumeratorIntegerDescriptor, "EnumeratorIntegerDescriptor.INSTANCE");
        return enumeratorIntegerDescriptor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final <K, V> Path tryCompact(Path path, final KeyDescriptor<K> keyDescriptor, DataExternalizer<V> dataExternalizer) {
        int i = 0;
        if (!Files.exists(path, new LinkOption[0])) {
            return null;
        }
        Object2ObjectOpenCustomHashMap object2ObjectOpenCustomHashMap = new Object2ObjectOpenCustomHashMap(new Hash.Strategy<K>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.WriteAheadLogKt$tryCompact$keyToLastEvent$1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
            public boolean equals(K a, K b) {
                if (Intrinsics.areEqual(a, b)) {
                    return true;
                }
                if (a == null || b == null) {
                    return false;
                }
                return keyDescriptor.isEqual(a, b);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
            public int hashCode(K o) {
                return keyDescriptor.getHashCode(o);
            }
        });
        PersistentMapWalPlayer persistentMapWalPlayer = new PersistentMapWalPlayer(keyDescriptor, dataExternalizer, path);
        try {
            int i2 = 0;
            for (WalEvent<K, V> walEvent : persistentMapWalPlayer.readWal()) {
                if (walEvent instanceof WalEvent.AppendEvent) {
                    ((IntSet) object2ObjectOpenCustomHashMap.computeIfAbsent(walEvent.getKey(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.io.WriteAheadLogKt$tryCompact$shouldCompact$1$1
                        @Override // java.util.function.Function
                        public final IntSet apply(K k) {
                            return new IntLinkedOpenHashSet();
                        }
                    })).add(i2);
                } else if (walEvent instanceof WalEvent.PutEvent) {
                    K key = walEvent.getKey();
                    IntLinkedOpenHashSet intLinkedOpenHashSet = new IntLinkedOpenHashSet();
                    intLinkedOpenHashSet.add(i2);
                    Unit unit = Unit.INSTANCE;
                    object2ObjectOpenCustomHashMap.put(key, intLinkedOpenHashSet);
                } else if (walEvent instanceof WalEvent.RemoveEvent) {
                    object2ObjectOpenCustomHashMap.put(walEvent.getKey(), new IntLinkedOpenHashSet());
                } else if (walEvent instanceof WalEvent.CorruptionEvent) {
                    throw new CorruptionException("wal has been corrupted");
                }
                ((IntSet) object2ObjectOpenCustomHashMap.computeIfAbsent(walEvent.getKey(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.io.WriteAheadLogKt$tryCompact$shouldCompact$1$3
                    @Override // java.util.function.Function
                    public final IntSet apply(K k) {
                        return new IntLinkedOpenHashSet();
                    }
                })).add(i2);
                i2++;
            }
            boolean z = object2ObjectOpenCustomHashMap.size() * 2 < i2;
            CloseableKt.closeFinally(persistentMapWalPlayer, null);
            if (!z) {
                return null;
            }
            Path compactedWalFile = path.resolveSibling(path.getFileName() + "_compacted");
            PersistentMapWalPlayer persistentMapWalPlayer2 = new PersistentMapWalPlayer(keyDescriptor, dataExternalizer, path);
            try {
                PersistentMapWalPlayer persistentMapWalPlayer3 = persistentMapWalPlayer2;
                boolean useCompression$intellij_platform_util = persistentMapWalPlayer3.getUseCompression$intellij_platform_util();
                Intrinsics.checkNotNullExpressionValue(compactedWalFile, "compactedWalFile");
                ExecutorService executorServiceNewSameThreadExecutorService = ConcurrencyUtil.newSameThreadExecutorService();
                Intrinsics.checkNotNullExpressionValue(executorServiceNewSameThreadExecutorService, "ConcurrencyUtil.newSameThreadExecutorService()");
                PersistentMapWal persistentMapWal = new PersistentMapWal(keyDescriptor, dataExternalizer, useCompression$intellij_platform_util, compactedWalFile, executorServiceNewSameThreadExecutorService, false, 32, null);
                try {
                    PersistentMapWal persistentMapWal2 = persistentMapWal;
                    for (WalEvent<K, V> walEvent2 : persistentMapWalPlayer3.readWal()) {
                        int i3 = i + 1;
                        if (i < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        final WalEvent<K, V> walEvent3 = walEvent2;
                        K key2 = walEvent3.getKey();
                        IntSet intSet = (IntSet) object2ObjectOpenCustomHashMap.get(key2);
                        if (intSet == null) {
                            throw new IOException("No events found for key =  " + key2);
                        }
                        Intrinsics.checkNotNullExpressionValue(intSet, "keyToLastEvent.get(key) …s found for key =  $key\")");
                        if (intSet.contains(i)) {
                            if (walEvent3 instanceof WalEvent.AppendEvent) {
                                persistentMapWal2.appendData(key2, new AppendablePersistentMap.ValueDataAppender() { // from class: org.jetbrains.kotlin.com.intellij.util.io.WriteAheadLogKt$tryCompact$1$1$1$1
                                    @Override // org.jetbrains.kotlin.com.intellij.util.io.AppendablePersistentMap.ValueDataAppender
                                    public final void append(DataOutput out) throws IOException {
                                        Intrinsics.checkNotNullParameter(out, "out");
                                        out.write(((WalEvent.AppendEvent) walEvent3).getData());
                                    }
                                });
                            } else if (walEvent3 instanceof WalEvent.PutEvent) {
                                persistentMapWal2.put(key2, ((WalEvent.PutEvent) walEvent3).getValue());
                            } else if (!(walEvent3 instanceof WalEvent.RemoveEvent) && (walEvent3 instanceof WalEvent.CorruptionEvent)) {
                                throw new CorruptionException("wal has been corrupted");
                            }
                        }
                        i = i3;
                    }
                    Unit unit2 = Unit.INSTANCE;
                    CloseableKt.closeFinally(persistentMapWal, null);
                    Unit unit3 = Unit.INSTANCE;
                    CloseableKt.closeFinally(persistentMapWalPlayer2, null);
                    return compactedWalFile;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(persistentMapWal, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(persistentMapWalPlayer2, th3);
                    throw th4;
                }
            }
        } catch (Throwable th5) {
            try {
                throw th5;
            } catch (Throwable th6) {
                CloseableKt.closeFinally(persistentMapWalPlayer, th5);
                throw th6;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <V> V readData(byte[] bArr, DataExternalizer<V> dataExternalizer) {
        return dataExternalizer.read(new DataInputStream(new ByteArrayInputStream(bArr)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <V> byte[] writeData(V v, DataExternalizer<V> dataExternalizer) throws IOException {
        UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
        dataExternalizer.save(new DataOutputStream(unsyncByteArrayOutputStream), v);
        byte[] byteArray = unsyncByteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "baos.toByteArray()");
        return byteArray;
    }
}
