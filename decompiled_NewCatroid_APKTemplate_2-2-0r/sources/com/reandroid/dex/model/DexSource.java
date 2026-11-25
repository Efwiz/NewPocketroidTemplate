package com.reandroid.dex.model;

import com.reandroid.archive.ByteInputSource;
import com.reandroid.archive.InputSource;
import com.reandroid.archive.ZipEntryMap;
import com.reandroid.utils.io.FileUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes11.dex */
public interface DexSource<T> extends Comparable<DexSource<?>>, Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close() throws IOException;

    boolean delete();

    T get();

    String getName();

    boolean isClosed();

    InputStream openStream() throws IOException;

    void set(T t);

    void write(byte[] bArr) throws IOException;

    default int getDexFileNumber() {
        return DexFile.getDexFileNumber(getName());
    }

    default String getSimpleName() {
        String name2 = getName();
        int iLastIndexOf = name2.lastIndexOf(47);
        if (iLastIndexOf < 0) {
            iLastIndexOf = name2.lastIndexOf(92);
        }
        return iLastIndexOf >= 0 ? name2.substring(iLastIndexOf + 1) : name2;
    }

    default DexSource<T> createNext() {
        throw new RuntimeException("Method not implemented");
    }

    default DexSource<T> initializeNew() {
        throw new RuntimeException("Method not implemented");
    }

    @Override // java.lang.Comparable
    default int compareTo(DexSource<?> dexSource) {
        return Integer.compare(getDexFileNumber(), dexSource.getDexFileNumber());
    }

    static <T> DexSource<T> create(ZipEntryMap zipEntryMap, String str) {
        return new ZipDexSource(zipEntryMap, str);
    }

    static <T> DexSource<T> create(ZipEntryMap zipEntryMap, String str, T t) {
        return new ZipDexSource(zipEntryMap, str, t);
    }

    static <T> DexSource<T> create(File file) {
        return new FileDexSource(file);
    }

    public static abstract class DexSourceImpl<T> implements DexSource<T> {
        private boolean closed;
        private T item;

        abstract boolean onDelete();

        @Override // com.reandroid.dex.model.DexSource
        public T get() {
            return this.item;
        }

        @Override // com.reandroid.dex.model.DexSource
        public void set(T t) {
            if (isClosed()) {
                return;
            }
            this.item = t;
        }

        @Override // com.reandroid.dex.model.DexSource, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.closed = true;
            T t = this.item;
            this.item = null;
            if (t instanceof Closeable) {
                ((Closeable) t).close();
            }
        }

        @Override // com.reandroid.dex.model.DexSource
        public boolean isClosed() {
            return this.closed;
        }

        @Override // com.reandroid.dex.model.DexSource
        public boolean delete() {
            set(null);
            return onDelete();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DexSource) && getDexFileNumber() == ((DexSource) obj).getDexFileNumber();
        }

        public int hashCode() {
            return getDexFileNumber();
        }

        public String toString() {
            return getSimpleName();
        }
    }

    public static class FileDexSource<T> extends DexSourceImpl<T> {
        private final File file;

        @Override // com.reandroid.dex.model.DexSource
        public DexSource<T> initializeNew() {
            return null;
        }

        public FileDexSource(File file) {
            this.file = file;
        }

        public File getFile() {
            return this.file;
        }

        @Override // com.reandroid.dex.model.DexSource
        public String getSimpleName() {
            return getFile().getName();
        }

        @Override // com.reandroid.dex.model.DexSource
        public String getName() {
            return getFile().getAbsolutePath();
        }

        @Override // com.reandroid.dex.model.DexSource.DexSourceImpl
        boolean onDelete() {
            if (isClosed()) {
                return false;
            }
            File file = getFile();
            if (file.isFile()) {
                return file.delete();
            }
            return true;
        }

        @Override // com.reandroid.dex.model.DexSource
        public InputStream openStream() throws IOException {
            if (isClosed()) {
                throw new IOException("Closed: " + getName());
            }
            return new FileInputStream(getFile());
        }

        @Override // com.reandroid.dex.model.DexSource
        public void write(byte[] bArr) throws IOException {
            if (isClosed()) {
                throw new IOException("Closed: " + getName());
            }
            OutputStream outputStream = FileUtil.outputStream(getFile());
            outputStream.write(bArr, 0, bArr.length);
            outputStream.close();
        }

        @Override // com.reandroid.dex.model.DexSource
        public FileDexSource<T> createNext() {
            if (isClosed()) {
                return null;
            }
            int dexFileNumber = getDexFileNumber() + 1;
            File file = getFile(dexFileNumber);
            while (file.isFile()) {
                dexFileNumber++;
                file = getFile(dexFileNumber);
            }
            return new FileDexSource<>(file);
        }

        private File getFile(int i) {
            String dexName = DexFile.getDexName(i);
            File parentFile = getFile().getParentFile();
            if (parentFile == null) {
                return new File(dexName);
            }
            return new File(parentFile, dexName);
        }
    }

    public static class ZipDexSource<T> extends DexSourceImpl<T> {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f245name;
        private final ZipEntryMap zipEntryMap;

        public ZipDexSource(ZipEntryMap zipEntryMap, String str) {
            this.zipEntryMap = zipEntryMap;
            this.f245name = str;
        }

        public ZipDexSource(ZipEntryMap zipEntryMap, String str, T t) {
            this(zipEntryMap, str);
            set(t);
        }

        @Override // com.reandroid.dex.model.DexSource
        public String getName() {
            return this.f245name;
        }

        @Override // com.reandroid.dex.model.DexSource.DexSourceImpl
        boolean onDelete() {
            if (isClosed()) {
                return false;
            }
            this.zipEntryMap.remove(getName());
            return true;
        }

        @Override // com.reandroid.dex.model.DexSource
        public InputStream openStream() throws IOException {
            if (isClosed()) {
                throw new IOException("Closed: " + getName());
            }
            InputSource inputSource = this.zipEntryMap.getInputSource(getName());
            if (inputSource == null) {
                throw new IOException("Zip input source not found: " + getName());
            }
            return inputSource.openStream();
        }

        @Override // com.reandroid.dex.model.DexSource
        public void write(byte[] bArr) throws IOException {
            if (isClosed()) {
                throw new IOException("Closed: " + getName());
            }
            this.zipEntryMap.add(new ByteInputSource(bArr, getName()));
        }

        @Override // com.reandroid.dex.model.DexSource
        public ZipDexSource<T> createNext() {
            if (isClosed()) {
                return null;
            }
            int dexFileNumber = getDexFileNumber() + 1;
            String path = getPath(dexFileNumber);
            ZipEntryMap zipEntryMap = this.zipEntryMap;
            while (zipEntryMap.contains(path)) {
                dexFileNumber++;
                path = getPath(dexFileNumber);
            }
            return new ZipDexSource<>(zipEntryMap, path);
        }

        private String getPath(int i) {
            return FileUtil.combineUnixPath(FileUtil.getParent(getName()), DexFile.getDexName(i));
        }

        @Override // com.reandroid.dex.model.DexSource.DexSourceImpl
        public String toString() {
            return this.zipEntryMap.getModuleName() + ":/" + getSimpleName();
        }
    }
}
