package org.jetbrains.kotlin.com.intellij.util.io.keyStorage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableNotNullFunction;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.BufferExposingByteArrayOutputStream;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.com.intellij.util.io.DataOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.DirectBufferWrapper;
import org.jetbrains.kotlin.com.intellij.util.io.LimitedInputStream;
import org.jetbrains.kotlin.com.intellij.util.io.PagedFileStorage;
import org.jetbrains.kotlin.com.intellij.util.io.ResizeableMappedFile;
import org.jetbrains.kotlin.com.intellij.util.io.StorageLockContext;
import org.jetbrains.kotlin.com.intellij.util.io.UnsyncByteArrayInputStream;
import org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException;

/* JADX INFO: loaded from: classes3.dex */
public class AppendableStorageBackedByResizableMappedFile<Data> extends ResizeableMappedFile implements AppendableObjectStorage<Data> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile byte[] myAppendBuffer;
    private volatile int myBufferPosition;
    private final DataExternalizer<Data> myDataDescriptor;
    private volatile int myFileLength;
    private static final ThreadLocal<MyDataIS> ourReadStream = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableStorageBackedByResizableMappedFile$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            return AppendableStorageBackedByResizableMappedFile.lambda$static$0();
        }
    });
    private static final InputStream TOMBSTONE = new InputStream() { // from class: org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableStorageBackedByResizableMappedFile.4
        @Override // java.io.InputStream
        public int read() {
            throw new IllegalStateException("should not happen");
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "processor";
        } else if (i != 2) {
            objArr[0] = "dataDescriptor";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/keyStorage/AppendableStorageBackedByResizableMappedFile";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/keyStorage/AppendableStorageBackedByResizableMappedFile";
        } else {
            objArr[1] = "buildOldComparerStream";
        }
        if (i == 1) {
            objArr[2] = "processAll";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    static /* synthetic */ MyDataIS lambda$static$0() {
        return new MyDataIS();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppendableStorageBackedByResizableMappedFile(Path path, int i, StorageLockContext storageLockContext, int i2, boolean z, DataExternalizer<Data> dataExternalizer) throws IOException {
        super(path, i, storageLockContext, i2, z);
        if (dataExternalizer == null) {
            $$$reportNull$$$0(0);
        }
        this.myDataDescriptor = dataExternalizer;
        this.myFileLength = (int) length();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.ResizeableMappedFile, org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage
    public void clear() throws IOException {
        super.clear();
        this.myFileLength = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: flushKeyStoreBuffer, reason: merged with bridge method [inline-methods] */
    public void m6737x7857b691() throws IOException {
        if (this.myBufferPosition > 0) {
            put(this.myFileLength, this.myAppendBuffer, 0, this.myBufferPosition);
            this.myFileLength += this.myBufferPosition;
            this.myBufferPosition = 0;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.ResizeableMappedFile, org.jetbrains.kotlin.com.intellij.openapi.Forceable
    /* JADX INFO: renamed from: force */
    public void m6720xd71ec63f() throws IOException {
        m6737x7857b691();
        super.m6720xd71ec63f();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.ResizeableMappedFile, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            SmartList smartList = new SmartList();
            ContainerUtil.addIfNotNull(smartList, ExceptionUtil.runAndCatch(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableStorageBackedByResizableMappedFile$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                public final void run() throws Exception {
                    this.f$0.m6737x7857b691();
                }
            }));
            ContainerUtil.addIfNotNull(smartList, ExceptionUtil.runAndCatch(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableStorageBackedByResizableMappedFile$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                public final void run() throws Exception {
                    this.f$0.m6738x9711ee52();
                }
            }));
            if (!smartList.isEmpty()) {
                throw new IOException(new CompoundRuntimeException(smartList));
            }
            ourReadStream.remove();
        } catch (Throwable th) {
            ourReadStream.remove();
            throw th;
        }
    }

    /* JADX INFO: renamed from: lambda$close$2$org-jetbrains-kotlin-com-intellij-util-io-keyStorage-AppendableStorageBackedByResizableMappedFile, reason: not valid java name */
    /* synthetic */ void m6738x9711ee52() throws Exception {
        super.close();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage
    public Data read(int i) throws IOException {
        if (this.myFileLength <= i) {
            if (this.myAppendBuffer == null) {
                throw new NoDataException("requested address points to un-existed data");
            }
            int i2 = i - this.myFileLength;
            if (i2 > this.myBufferPosition) {
                throw new NoDataException("requested address points to un-existed data");
            }
            return this.myDataDescriptor.read(new DataInputStream(new UnsyncByteArrayInputStream(this.myAppendBuffer, i2, this.myBufferPosition)));
        }
        MyDataIS myDataIS = ourReadStream.get();
        myDataIS.setup(this, i, this.myFileLength);
        return this.myDataDescriptor.read(myDataIS);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage
    public boolean processAll(final AppendableObjectStorage.StorageObjectProcessor<? super Data> storageObjectProcessor) throws IOException {
        if (storageObjectProcessor == null) {
            $$$reportNull$$$0(1);
        }
        if (this.myFileLength == 0) {
            return true;
        }
        return ((Boolean) readInputStream(new ThrowableNotNullFunction() { // from class: org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableStorageBackedByResizableMappedFile$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableNotNullFunction
            public final Object fun(Object obj) {
                return this.f$0.m6739x98fb4ae7(storageObjectProcessor, (InputStream) obj);
            }
        })).booleanValue();
    }

    /* JADX INFO: renamed from: lambda$processAll$3$org-jetbrains-kotlin-com-intellij-util-io-keyStorage-AppendableStorageBackedByResizableMappedFile, reason: not valid java name */
    /* synthetic */ Boolean m6739x98fb4ae7(AppendableObjectStorage.StorageObjectProcessor storageObjectProcessor, InputStream inputStream) throws IOException {
        LimitedInputStream limitedInputStream = new LimitedInputStream(new BufferedInputStream(inputStream), this.myFileLength) { // from class: org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableStorageBackedByResizableMappedFile.1
            @Override // org.jetbrains.kotlin.com.intellij.util.io.LimitedInputStream, java.io.FilterInputStream, java.io.InputStream
            public int available() {
                return remainingLimit();
            }
        };
        do {
            try {
            } catch (EOFException unused) {
                return true;
            }
        } while (storageObjectProcessor.process(limitedInputStream.getBytesRead(), this.myDataDescriptor.read(new DataInputStream(limitedInputStream))));
        return false;
    }

    public int getCurrentLength() {
        return this.myBufferPosition + this.myFileLength;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage
    public int append(Data data) throws IOException {
        BufferExposingByteArrayOutputStream bufferExposingByteArrayOutputStream = new BufferExposingByteArrayOutputStream();
        this.myDataDescriptor.save(new DataOutputStream(bufferExposingByteArrayOutputStream), data);
        int size = bufferExposingByteArrayOutputStream.size();
        byte[] internalBuffer = bufferExposingByteArrayOutputStream.getInternalBuffer();
        int currentLength = getCurrentLength();
        if (size > 4096) {
            m6737x7857b691();
            put(currentLength, internalBuffer, 0, size);
            this.myFileLength += size;
        } else {
            if (size > 4096 - this.myBufferPosition) {
                m6737x7857b691();
            }
            if (this.myAppendBuffer == null) {
                this.myAppendBuffer = new byte[4096];
            }
            System.arraycopy(internalBuffer, 0, this.myAppendBuffer, this.myBufferPosition, size);
            this.myBufferPosition += size;
        }
        return currentLength;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableObjectStorage
    public boolean checkBytesAreTheSame(int i, Data data) throws IOException {
        boolean[] zArr = new boolean[1];
        OutputStream outputStreamBuildOldComparerStream = buildOldComparerStream(i, zArr);
        this.myDataDescriptor.save(new DataOutputStream(outputStreamBuildOldComparerStream), data);
        outputStreamBuildOldComparerStream.close();
        return zArr[0];
    }

    private OutputStream buildOldComparerStream(int i, boolean[] zArr) throws IOException {
        PagedFileStorage pagedFileStorage = getPagedFileStorage();
        if (this.myFileLength <= i) {
            return new OutputStream(i, zArr) { // from class: org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableStorageBackedByResizableMappedFile.2
                int address;
                boolean same = true;
                final /* synthetic */ int val$addr;
                final /* synthetic */ boolean[] val$sameValue;

                {
                    this.val$addr = i;
                    this.val$sameValue = zArr;
                    this.address = i - AppendableStorageBackedByResizableMappedFile.this.myFileLength;
                }

                /* JADX WARN: Code duplicated, block: B:9:0x0021  */
                @Override // java.io.OutputStream
                public void write(int i2) {
                    boolean z;
                    if (this.same) {
                        if (this.address < AppendableStorageBackedByResizableMappedFile.this.myBufferPosition) {
                            byte[] bArr = AppendableStorageBackedByResizableMappedFile.this.myAppendBuffer;
                            int i3 = this.address;
                            this.address = i3 + 1;
                            if (bArr[i3] == ((byte) i2)) {
                                z = true;
                            } else {
                                z = false;
                            }
                        } else {
                            z = false;
                        }
                        this.same = z;
                    }
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() {
                    this.val$sameValue[0] = this.same;
                }
            };
        }
        return new OutputStream(i, pagedFileStorage, zArr) { // from class: org.jetbrains.kotlin.com.intellij.util.io.keyStorage.AppendableStorageBackedByResizableMappedFile.3
            int address;
            int base;
            DirectBufferWrapper buffer;
            final int myPageSize;
            boolean same = true;
            final /* synthetic */ int val$addr;
            final /* synthetic */ boolean[] val$sameValue;
            final /* synthetic */ PagedFileStorage val$storage;

            {
                this.val$addr = i;
                this.val$storage = pagedFileStorage;
                this.val$sameValue = zArr;
                this.base = i;
                this.address = pagedFileStorage.getOffsetInPage(i);
                this.buffer = pagedFileStorage.getByteBuffer(i, false);
                this.myPageSize = pagedFileStorage.getPageSize();
            }

            @Override // java.io.OutputStream
            public void write(int i2) throws IOException {
                if (this.same) {
                    int i3 = this.myPageSize;
                    int i4 = this.address;
                    boolean z = false;
                    if (i3 == i4 && i4 < AppendableStorageBackedByResizableMappedFile.this.myFileLength) {
                        int i5 = this.base + this.address;
                        this.base = i5;
                        this.buffer = this.val$storage.getByteBuffer(i5, false);
                        this.address = 0;
                    }
                    if (this.address < AppendableStorageBackedByResizableMappedFile.this.myFileLength) {
                        DirectBufferWrapper directBufferWrapper = this.buffer;
                        int i6 = this.address;
                        this.address = i6 + 1;
                        if (directBufferWrapper.get(i6) == ((byte) i2)) {
                            z = true;
                        }
                    }
                    this.same = z;
                }
            }

            @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                this.val$sameValue[0] = this.same;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class MyDataIS extends DataInputStream {
        private MyDataIS() {
            super(new MyBufferedIS());
        }

        void setup(ResizeableMappedFile resizeableMappedFile, long j, long j2) {
            ((MyBufferedIS) this.in).setup(resizeableMappedFile, j, j2);
        }
    }

    private static class MyBufferedIS extends BufferedInputStream {
        MyBufferedIS() {
            super(AppendableStorageBackedByResizableMappedFile.TOMBSTONE, 512);
        }

        void setup(ResizeableMappedFile resizeableMappedFile, long j, long j2) {
            this.pos = 0;
            this.count = 0;
            this.in = new MappedFileInputStream(resizeableMappedFile, j, j2);
        }
    }
}
