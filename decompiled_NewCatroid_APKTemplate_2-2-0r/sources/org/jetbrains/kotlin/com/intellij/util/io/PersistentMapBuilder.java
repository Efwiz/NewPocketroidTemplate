package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;

/* JADX INFO: loaded from: classes3.dex */
public final class PersistentMapBuilder<Key, Value> {
    private Boolean myCompactOnClose;
    private boolean myEnableWal;
    private final Path myFile;
    private Boolean myHasChunks;
    private Integer myInitialSize;
    private Boolean myInlineValues;
    private Boolean myIsReadOnly;
    private final KeyDescriptor<Key> myKeyDescriptor;
    private StorageLockContext myLockContext;
    private final DataExternalizer<Value> myValueExternalizer;
    private Integer myVersion;
    private ExecutorService myWalExecutor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 8:
            case 9:
            case 10:
            case 16:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                i2 = 2;
                break;
            case 8:
            case 9:
            case 10:
            case 16:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 9:
                objArr[0] = "keyDescriptor";
                break;
            case 2:
            case 10:
                objArr[0] = "valueExternalizer";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapBuilder";
                break;
            case 8:
            default:
                objArr[0] = "file";
                break;
            case 16:
                objArr[0] = "service";
                break;
        }
        switch (i) {
            case 3:
            case 4:
                objArr[1] = "buildImplementation";
                break;
            case 5:
                objArr[1] = "getFile";
                break;
            case 6:
                objArr[1] = "getKeyDescriptor";
                break;
            case 7:
                objArr[1] = "getValueExternalizer";
                break;
            case 8:
            case 9:
            case 10:
            case 16:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/PersistentMapBuilder";
                break;
            case 11:
                objArr[1] = "withInitialSize";
                break;
            case 12:
                objArr[1] = "withVersion";
                break;
            case 13:
                objArr[1] = "withReadonly";
                break;
            case 14:
                objArr[1] = "readonly";
                break;
            case 15:
                objArr[1] = "withWal";
                break;
            case 17:
                objArr[1] = "setWalExecutor";
                break;
            case 18:
            case 19:
                objArr[1] = "inlineValues";
                break;
            case 20:
                objArr[1] = "withStorageLockContext";
                break;
            case 21:
                objArr[1] = "hasChunks";
                break;
            case 22:
                objArr[1] = "hasNoChunks";
                break;
            case 23:
                objArr[1] = "withCompactOnClose";
                break;
            case 24:
                objArr[1] = "compactOnClose";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "newBuilder";
                break;
            case 16:
                objArr[2] = "setWalExecutor";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                throw new IllegalStateException(str2);
            case 8:
            case 9:
            case 10:
            case 16:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    private PersistentMapBuilder(Path path, KeyDescriptor<Key> keyDescriptor, DataExternalizer<Value> dataExternalizer) {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        if (keyDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (dataExternalizer == null) {
            $$$reportNull$$$0(2);
        }
        this.myCompactOnClose = null;
        this.myWalExecutor = ConcurrencyUtil.newSameThreadExecutorService();
        this.myFile = path;
        this.myKeyDescriptor = keyDescriptor;
        this.myValueExternalizer = dataExternalizer;
    }

    public PersistentHashMap<Key, Value> build() throws IOException {
        return new PersistentHashMap<>(buildImplementation());
    }

    private PersistentMapBase<Key, Value> buildImplementation() throws IOException {
        Boolean bool;
        if (this.myHasChunks != null) {
            bool = PersistentHashMapValueStorage.CreationTimeOptions.HAS_NO_CHUNKS.get();
            PersistentHashMapValueStorage.CreationTimeOptions.HAS_NO_CHUNKS.set(Boolean.valueOf(!this.myHasChunks.booleanValue()));
        } else {
            bool = null;
        }
        Boolean bool2 = PersistentHashMapValueStorage.CreationTimeOptions.READONLY.get();
        PersistentHashMapValueStorage.CreationTimeOptions.READONLY.set(this.myIsReadOnly);
        try {
            if (SystemProperties.getBooleanProperty("idea.use.in.memory.persistent.map", false)) {
                return new PersistentMapInMemory(this);
            }
            return new PersistentMapImpl(this);
        } finally {
            if (this.myHasChunks != null) {
                PersistentHashMapValueStorage.CreationTimeOptions.HAS_NO_CHUNKS.set(bool);
            }
            PersistentHashMapValueStorage.CreationTimeOptions.READONLY.set(bool2);
        }
    }

    public Path getFile() {
        Path path = this.myFile;
        if (path == null) {
            $$$reportNull$$$0(5);
        }
        return path;
    }

    public KeyDescriptor<Key> getKeyDescriptor() {
        KeyDescriptor<Key> keyDescriptor = this.myKeyDescriptor;
        if (keyDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        return keyDescriptor;
    }

    public DataExternalizer<Value> getValueExternalizer() {
        DataExternalizer<Value> dataExternalizer = this.myValueExternalizer;
        if (dataExternalizer == null) {
            $$$reportNull$$$0(7);
        }
        return dataExternalizer;
    }

    public static <Key, Value> PersistentMapBuilder<Key, Value> newBuilder(Path path, KeyDescriptor<Key> keyDescriptor, DataExternalizer<Value> dataExternalizer) {
        if (path == null) {
            $$$reportNull$$$0(8);
        }
        if (keyDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        if (dataExternalizer == null) {
            $$$reportNull$$$0(10);
        }
        return new PersistentMapBuilder<>(path, keyDescriptor, dataExternalizer);
    }

    public PersistentMapBuilder<Key, Value> withInitialSize(int i) {
        this.myInitialSize = Integer.valueOf(i);
        return this;
    }

    public PersistentMapBuilder<Key, Value> withVersion(int i) {
        this.myVersion = Integer.valueOf(i);
        return this;
    }

    public PersistentMapBuilder<Key, Value> withReadonly(boolean z) {
        this.myIsReadOnly = Boolean.valueOf(z);
        return this;
    }

    public PersistentMapBuilder<Key, Value> inlineValues(boolean z) {
        if (z && !(this.myValueExternalizer instanceof IntInlineKeyDescriptor)) {
            throw new IllegalStateException("can't inline values for externalizer " + this.myValueExternalizer.getClass());
        }
        this.myInlineValues = Boolean.valueOf(z);
        return this;
    }

    public PersistentMapBuilder<Key, Value> withStorageLockContext(StorageLockContext storageLockContext) {
        this.myLockContext = storageLockContext;
        return this;
    }

    public int getInitialSize(int i) {
        Integer num = this.myInitialSize;
        return num != null ? num.intValue() : i;
    }

    public int getVersion(int i) {
        Integer num = this.myVersion;
        return num != null ? num.intValue() : i;
    }

    public boolean getInlineValues(boolean z) {
        Boolean bool = this.myInlineValues;
        return bool != null ? bool.booleanValue() : z;
    }

    public boolean getReadOnly(boolean z) {
        Boolean bool = this.myIsReadOnly;
        return bool != null ? bool.booleanValue() : z;
    }

    public boolean getCompactOnClose(boolean z) {
        Boolean bool = this.myCompactOnClose;
        return bool != null ? bool.booleanValue() : z;
    }

    public boolean isEnableWal() {
        return this.myEnableWal;
    }

    public ExecutorService getWalExecutor() {
        return this.myWalExecutor;
    }

    public StorageLockContext getLockContext() {
        return this.myLockContext;
    }
}
