package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.util.CommonProcessors;
import org.jetbrains.kotlin.com.intellij.util.Processor;

/* JADX INFO: loaded from: classes3.dex */
public class PersistentHashMap<Key, Value> implements AppendablePersistentMap<Key, Value> {
    static String DATA_FILE_EXTENSION = ".values";
    private final PersistentMapBase<Key, Value> myImpl;

    @Deprecated
    public interface ValueDataAppender extends AppendablePersistentMap.ValueDataAppender {
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 21 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 21 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "impl";
                break;
            case 2:
            case 5:
            case 8:
            case 11:
            case 14:
                objArr[0] = "file";
                break;
            case 3:
            case 6:
            case 9:
            case 12:
            case 15:
                objArr[0] = "keyDescriptor";
                break;
            case 4:
            case 7:
            case 10:
            case 13:
            case 16:
                objArr[0] = "valueExternalizer";
                break;
            case 17:
                objArr[0] = "prefixFile";
                break;
            case 18:
            case 19:
                objArr[0] = "appender";
                break;
            case 20:
            case 22:
                objArr[0] = "processor";
                break;
            case 21:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMap";
                break;
            default:
                objArr[0] = "builder";
                break;
        }
        if (i != 21) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentHashMap";
        } else {
            objArr[1] = "getAllKeysWithExistingMapping";
        }
        switch (i) {
            case 17:
                objArr[2] = "deleteFilesStartingWith";
                break;
            case 18:
            case 19:
                objArr[2] = "appendData";
                break;
            case 20:
                objArr[2] = "processKeys";
                break;
            case 21:
                break;
            case 22:
                objArr[2] = "processKeysWithExistingMapping";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 21) {
            throw new IllegalStateException(str2);
        }
    }

    PersistentHashMap(PersistentMapBuilder<Key, Value> persistentMapBuilder, boolean z) throws IOException {
        if (persistentMapBuilder == null) {
            $$$reportNull$$$0(0);
        }
        if (z) {
            persistentMapBuilder.withReadonly(false);
            persistentMapBuilder.inlineValues(false);
        }
        this.myImpl = persistentMapBuilder.build().myImpl;
    }

    public PersistentHashMap(PersistentMapBase<Key, Value> persistentMapBase) {
        if (persistentMapBase == null) {
            $$$reportNull$$$0(1);
        }
        this.myImpl = persistentMapBase;
    }

    public final void closeAndClean() throws IOException {
        this.myImpl.closeAndDelete();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PersistentHashMap(File file, KeyDescriptor<Key> keyDescriptor, DataExternalizer<Value> dataExternalizer) throws IOException {
        this(PersistentMapBuilder.newBuilder(file.toPath(), keyDescriptor, dataExternalizer), true);
        if (file == null) {
            $$$reportNull$$$0(2);
        }
        if (keyDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (dataExternalizer == null) {
            $$$reportNull$$$0(4);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PersistentHashMap(Path path, KeyDescriptor<Key> keyDescriptor, DataExternalizer<Value> dataExternalizer) throws IOException {
        this(PersistentMapBuilder.newBuilder(path, keyDescriptor, dataExternalizer), true);
        if (path == null) {
            $$$reportNull$$$0(5);
        }
        if (keyDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (dataExternalizer == null) {
            $$$reportNull$$$0(7);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PersistentHashMap(Path path, KeyDescriptor<Key> keyDescriptor, DataExternalizer<Value> dataExternalizer, int i) throws IOException {
        this(PersistentMapBuilder.newBuilder(path, keyDescriptor, dataExternalizer).withInitialSize(i), true);
        if (path == null) {
            $$$reportNull$$$0(8);
        }
        if (keyDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        if (dataExternalizer == null) {
            $$$reportNull$$$0(10);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PersistentHashMap(Path path, KeyDescriptor<Key> keyDescriptor, DataExternalizer<Value> dataExternalizer, int i, int i2) throws IOException {
        this(PersistentMapBuilder.newBuilder(path, keyDescriptor, dataExternalizer).withInitialSize(i).withVersion(i2), true);
        if (path == null) {
            $$$reportNull$$$0(11);
        }
        if (keyDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        if (dataExternalizer == null) {
            $$$reportNull$$$0(13);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PersistentHashMap(Path path, KeyDescriptor<Key> keyDescriptor, DataExternalizer<Value> dataExternalizer, int i, int i2, StorageLockContext storageLockContext) throws IOException {
        this(PersistentMapBuilder.newBuilder(path, keyDescriptor, dataExternalizer).withInitialSize(i).withVersion(i2).withStorageLockContext(storageLockContext), true);
        if (path == null) {
            $$$reportNull$$$0(14);
        }
        if (keyDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        if (dataExternalizer == null) {
            $$$reportNull$$$0(16);
        }
    }

    public final void dropMemoryCaches() {
        force();
    }

    @Deprecated
    public static void deleteFilesStartingWith(File file) {
        if (file == null) {
            $$$reportNull$$$0(17);
        }
        IOUtil.deleteAllFilesStartingWith(file);
    }

    public final void put(Key key, Value value) throws IOException {
        this.myImpl.put(key, value);
    }

    @Deprecated
    public final void appendData(Key key, ValueDataAppender valueDataAppender) throws IOException {
        if (valueDataAppender == null) {
            $$$reportNull$$$0(18);
        }
        this.myImpl.appendData(key, valueDataAppender);
    }

    public final void appendData(Key key, AppendablePersistentMap.ValueDataAppender valueDataAppender) throws IOException {
        if (valueDataAppender == null) {
            $$$reportNull$$$0(19);
        }
        this.myImpl.appendData(key, valueDataAppender);
    }

    public final boolean processKeys(Processor<? super Key> processor) throws IOException {
        if (processor == null) {
            $$$reportNull$$$0(20);
        }
        return this.myImpl.processKeys(processor);
    }

    public final boolean isClosed() {
        return this.myImpl.isClosed();
    }

    public final boolean isDirty() {
        return this.myImpl.isDirty();
    }

    public final void markDirty() throws IOException {
        this.myImpl.markDirty();
    }

    @Deprecated
    public final Collection<Key> getAllKeysWithExistingMapping() throws IOException {
        ArrayList arrayList = new ArrayList();
        this.myImpl.processExistingKeys(new CommonProcessors.CollectProcessor(arrayList));
        return arrayList;
    }

    public final boolean processKeysWithExistingMapping(Processor<? super Key> processor) throws IOException {
        if (processor == null) {
            $$$reportNull$$$0(22);
        }
        return this.myImpl.processExistingKeys(processor);
    }

    public final Value get(Key key) throws IOException {
        return this.myImpl.get(key);
    }

    public final boolean containsMapping(Key key) throws IOException {
        return this.myImpl.containsKey(key);
    }

    public final void remove(Key key) throws IOException {
        this.myImpl.remove(key);
    }

    public final void force() {
        try {
            this.myImpl.force();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.myImpl.close();
    }

    public final String toString() {
        return this.myImpl.toString();
    }
}
