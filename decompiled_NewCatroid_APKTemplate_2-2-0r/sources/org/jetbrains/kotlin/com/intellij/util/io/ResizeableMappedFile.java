package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import org.jetbrains.kotlin.com.intellij.openapi.Forceable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableNotNullFunction;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException;

/* JADX INFO: loaded from: classes3.dex */
public class ResizeableMappedFile implements Forceable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) ResizeableMappedFile.class);
    private static final boolean truncateOnClose = SystemProperties.getBooleanProperty("idea.resizeable.file.truncate.on.close", false);
    private final int myInitialSize;
    private volatile long myLastWrittenLogicalSize;
    private volatile long myLogicalSize;
    private int myRoundFactor;
    private final PagedFileStorage myStorage;

    /* JADX WARN: Code duplicated, block: B:20:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5) ? 2 : 3];
        if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/ResizeableMappedFile";
        } else if (i == 4) {
            objArr[0] = "consumer";
        } else if (i != 5) {
            objArr[0] = "file";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/ResizeableMappedFile";
        }
        if (i == 2) {
            objArr[1] = "getPagedFileStorage";
        } else if (i == 3) {
            objArr[1] = "getStorageLockContext";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/ResizeableMappedFile";
        } else {
            objArr[1] = "readInputStream";
        }
        if (i != 2 && i != 3) {
            if (i == 4) {
                objArr[2] = "readInputStream";
            } else if (i != 5) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ResizeableMappedFile(Path path, int i, StorageLockContext storageLockContext, int i2, boolean z) throws IOException {
        this(path, i, storageLockContext, i2, z, false);
        if (path == null) {
            $$$reportNull$$$0(0);
        }
    }

    public ResizeableMappedFile(Path path, int i, StorageLockContext storageLockContext, int i2, boolean z, boolean z2) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(1);
        }
        this.myRoundFactor = 4096;
        this.myStorage = new PagedFileStorage(path, storageLockContext, i2, z, z2);
        ensureParentDirectoryExists();
        this.myInitialSize = i;
        long length = readLength();
        this.myLogicalSize = length;
        this.myLastWrittenLogicalSize = length;
    }

    public void clear() throws IOException {
        this.myStorage.resize(0L);
        this.myLogicalSize = 0L;
        this.myLastWrittenLogicalSize = 0L;
    }

    public long length() {
        return this.myLogicalSize;
    }

    private long realSize() {
        return this.myStorage.length();
    }

    void ensureSize(long j) {
        this.myLogicalSize = Math.max(j, this.myLogicalSize);
        expand(j);
    }

    public void setRoundFactor(int i) {
        this.myRoundFactor = i;
    }

    private void expand(long j) {
        long jDoRoundToFactor;
        long jRealSize = realSize();
        if (j <= jRealSize) {
            return;
        }
        if (jRealSize == 0) {
            jDoRoundToFactor = doRoundToFactor(Math.max(this.myInitialSize, j));
        } else {
            long jMax = Math.max(jRealSize + 1, 2L);
            while (j > jMax) {
                long j2 = (13 * jMax) >> 3;
                jMax = j2 >= 2147483647L ? jMax + (jMax / 5) : j2;
            }
            jDoRoundToFactor = doRoundToFactor(jMax);
        }
        try {
            this.myStorage.resize(jDoRoundToFactor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long doRoundToFactor(long j) {
        long j2 = this.myRoundFactor;
        return j % j2 != 0 ? ((j / j2) + 1) * j2 : j;
    }

    private Path getLengthFile() {
        Path file = this.myStorage.getFile();
        return file.resolveSibling(file.getFileName() + ".len");
    }

    private void writeLength(long j) {
        final Path lengthFile = getLengthFile();
        try {
            DataOutputStream dataOutputStream = (DataOutputStream) FileUtilRt.doIOOperation(new FileUtilRt.RepeatableIOOperation() { // from class: org.jetbrains.kotlin.com.intellij.util.io.ResizeableMappedFile$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.RepeatableIOOperation
                public final Object execute(boolean z) {
                    return this.f$0.m6734xe71304c7(lengthFile, z);
                }
            });
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.writeLong(j);
                } catch (Throwable th) {
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
        } catch (IOException e) {
            LOG.error((Throwable) e);
        }
    }

    /* JADX INFO: renamed from: lambda$writeLength$0$org-jetbrains-kotlin-com-intellij-util-io-ResizeableMappedFile, reason: not valid java name */
    /* synthetic */ DataOutputStream m6734xe71304c7(Path path, boolean z) throws IOException {
        try {
            return new DataOutputStream(Files.newOutputStream(path, new OpenOption[0]));
        } catch (NoSuchFileException e) {
            ensureParentDirectoryExists();
            if (z) {
                throw e;
            }
            return null;
        }
    }

    public boolean isDirty() {
        return this.myStorage.isDirty();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Forceable
    /* JADX INFO: renamed from: force */
    public void m6720xd71ec63f() throws IOException {
        ensureLengthWritten();
        this.myStorage.m6720xd71ec63f();
    }

    private void ensureLengthWritten() {
        if (this.myLastWrittenLogicalSize != this.myLogicalSize) {
            writeLength(this.myLogicalSize);
            this.myLastWrittenLogicalSize = this.myLogicalSize;
        }
    }

    private void ensureParentDirectoryExists() throws IOException {
        Path parent = getLengthFile().getParent();
        if (Files.exists(parent, new LinkOption[0])) {
            return;
        }
        Files.createDirectories(parent, new FileAttribute[0]);
    }

    private long readLength() throws IOException {
        Path lengthFile = getLengthFile();
        if (!Files.exists(lengthFile, new LinkOption[0]) && (!Files.exists(this.myStorage.getFile(), new LinkOption[0]) || Files.size(this.myStorage.getFile()) == 0)) {
            writeLength(0L);
            return 0L;
        }
        try {
            DataInputStream dataInputStream = new DataInputStream(Files.newInputStream(lengthFile, StandardOpenOption.READ));
            try {
                long j = dataInputStream.readLong();
                dataInputStream.close();
                return j;
            } catch (Throwable th) {
                try {
                    dataInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            long jRealSize = realSize();
            writeLength(jRealSize);
            LOG.error("storage size = " + jRealSize + ", file size = " + Files.size(this.myStorage.getFile()), e);
            return jRealSize;
        }
    }

    public int getInt(long j) throws IOException {
        return this.myStorage.getInt(j);
    }

    public void putInt(long j, int i) throws IOException {
        ensureSize(4 + j);
        this.myStorage.putInt(j, i);
    }

    public long getLong(long j) throws IOException {
        return this.myStorage.getLong(j);
    }

    public void putLong(long j, long j2) throws IOException {
        ensureSize(8 + j);
        this.myStorage.putLong(j, j2);
    }

    public byte get(long j) throws IOException {
        return this.myStorage.get(j);
    }

    public void get(long j, byte[] bArr, int i, int i2) throws IOException {
        this.myStorage.get(j, bArr, i, i2);
    }

    public void put(long j, byte[] bArr, int i, int i2) throws IOException {
        ensureSize(((long) i2) + j);
        this.myStorage.put(j, bArr, i, i2);
    }

    public void close() throws IOException {
        SmartList smartList = new SmartList();
        ContainerUtil.addIfNotNull(smartList, ExceptionUtil.runAndCatch(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.ResizeableMappedFile$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() throws Exception {
                this.f$0.m6732x8842ba73();
            }
        }));
        ContainerUtil.addIfNotNull(smartList, ExceptionUtil.runAndCatch(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.ResizeableMappedFile$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() throws Exception {
                this.f$0.m6733x6a3be52();
            }
        }));
        if (!smartList.isEmpty()) {
            throw new IOException(new CompoundRuntimeException(smartList));
        }
    }

    /* JADX INFO: renamed from: lambda$close$1$org-jetbrains-kotlin-com-intellij-util-io-ResizeableMappedFile, reason: not valid java name */
    /* synthetic */ void m6732x8842ba73() throws Exception {
        if (!getLengthFile().getFileSystem().isReadOnly()) {
            writeLength(this.myLogicalSize);
        }
        this.myStorage.m6720xd71ec63f();
        if (!truncateOnClose || this.myLogicalSize >= this.myStorage.length()) {
            return;
        }
        this.myStorage.resize(this.myLogicalSize);
    }

    /* JADX INFO: renamed from: lambda$close$2$org-jetbrains-kotlin-com-intellij-util-io-ResizeableMappedFile, reason: not valid java name */
    /* synthetic */ void m6733x6a3be52() throws Exception {
        this.myStorage.close();
    }

    public PagedFileStorage getPagedFileStorage() {
        PagedFileStorage pagedFileStorage = this.myStorage;
        if (pagedFileStorage == null) {
            $$$reportNull$$$0(2);
        }
        return pagedFileStorage;
    }

    public StorageLockContext getStorageLockContext() {
        StorageLockContext storageLockContext = this.myStorage.getStorageLockContext();
        if (storageLockContext == null) {
            $$$reportNull$$$0(3);
        }
        return storageLockContext;
    }

    public <R> R readInputStream(ThrowableNotNullFunction<? super InputStream, R, ? extends IOException> throwableNotNullFunction) throws IOException {
        if (throwableNotNullFunction == null) {
            $$$reportNull$$$0(4);
        }
        R r = (R) this.myStorage.readInputStream(throwableNotNullFunction);
        if (r == null) {
            $$$reportNull$$$0(5);
        }
        return r;
    }

    public void lockRead() {
        this.myStorage.lockRead();
    }

    public void unlockRead() {
        this.myStorage.unlockRead();
    }

    public void lockWrite() {
        this.myStorage.lockWrite();
    }

    public void unlockWrite() {
        this.myStorage.unlockWrite();
    }
}
