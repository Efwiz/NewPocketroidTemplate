package com.reandroid.dex.model;

import com.android.dex.DexFormat;
import com.reandroid.archive.InputSource;
import com.reandroid.archive.ZipEntryMap;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.io.FileUtil;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexFileSourceSet implements Iterable<DexSource<DexFile>>, Closeable {
    private boolean mReadStringsMode;
    private final ArrayCollection<DexSource<DexFile>> sourceList = new ArrayCollection<>();
    private ZipEntryMap zipEntryMap;

    public ZipEntryMap getZipEntryMap() {
        return this.zipEntryMap;
    }

    public void setZipEntryMap(ZipEntryMap zipEntryMap) {
        this.zipEntryMap = zipEntryMap;
    }

    public void merge(DexFileSourceSet dexFileSourceSet) {
        if (dexFileSourceSet == this) {
            throw new IllegalArgumentException("Cyclic merge");
        }
        for (DexSource<DexFile> dexSource : dexFileSourceSet) {
            if (!isEmpty(dexSource)) {
                DexSource<DexFile> dexSourceCreateNext = createNext();
                dexSourceCreateNext.set(dexSource.get());
                try {
                    save(dexSourceCreateNext);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void removeEmpty() {
        Iterator<DexSource<DexFile>> itClonedIterator = this.sourceList.clonedIterator();
        while (itClonedIterator.hasNext()) {
            DexSource<DexFile> next = itClonedIterator.next();
            if (isEmpty(next)) {
                remove(next);
            }
        }
    }

    public void saveAll(File file) throws IOException {
        Iterator<DexSource<DexFile>> itClonedIterator = this.sourceList.clonedIterator();
        while (itClonedIterator.hasNext()) {
            DexSource<DexFile> next = itClonedIterator.next();
            DexSource<DexFile> dexSourceCreate = DexSource.create(new File(file, next.getSimpleName()));
            dexSourceCreate.set(next.get());
            if (isEmpty(dexSourceCreate)) {
                dexSourceCreate.delete();
            } else {
                dexSourceCreate.write(next.get().getBytes());
            }
        }
    }

    public void saveAll() throws IOException {
        Iterator<DexSource<DexFile>> itClonedIterator = this.sourceList.clonedIterator();
        while (itClonedIterator.hasNext()) {
            save(itClonedIterator.next());
        }
    }

    private void save(DexSource<DexFile> dexSource) throws IOException {
        if (isEmpty(dexSource)) {
            delete(dexSource);
        } else {
            dexSource.write(dexSource.get().getBytes());
        }
    }

    private boolean isEmpty(DexSource<DexFile> dexSource) {
        DexFile dexFile = dexSource.get();
        return dexFile == null || dexFile.isEmpty();
    }

    public Iterator<DexFile> getClonedDexFiles() {
        return ComputeIterator.of(clonedIterator(), new DexFileSourceSet$$ExternalSyntheticLambda0());
    }

    public Iterator<DexFile> getDexFiles() {
        return ComputeIterator.of(iterator(), new DexFileSourceSet$$ExternalSyntheticLambda0());
    }

    @Override // java.lang.Iterable
    public Iterator<DexSource<DexFile>> iterator() {
        return this.sourceList.iterator();
    }

    public Iterator<DexSource<DexFile>> clonedIterator() {
        return this.sourceList.clonedIterator();
    }

    public void addAll(ZipEntryMap zipEntryMap) throws IOException {
        addAll(zipEntryMap, (String) null);
    }

    public void addAll(ZipEntryMap zipEntryMap, String str) throws IOException {
        if (str != null && str.length() > 0) {
            if (str.charAt(0) == '/') {
                str = str.substring(1);
            }
        } else {
            str = "";
        }
        if (str.length() > 0 && !str.endsWith("/")) {
            str = str + "/";
        }
        final String str2 = str + "classes";
        addAll(zipEntryMap, new Predicate() { // from class: com.reandroid.dex.model.DexFileSourceSet$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DexFileSourceSet.lambda$addAll$0(str2, (InputSource) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$addAll$0(String str, InputSource inputSource) {
        String alias = inputSource.getAlias();
        return alias.startsWith(str) && DexFile.getDexFileNumber(alias) >= 0;
    }

    public void addAll(ZipEntryMap zipEntryMap, Predicate<InputSource> predicate) throws IOException {
        addAll(zipEntryMap, zipEntryMap.iterator(predicate));
    }

    public void addAll(ZipEntryMap zipEntryMap, Iterator<InputSource> it) throws IOException {
        while (it.hasNext()) {
            add(zipEntryMap, it.next().getAlias());
        }
    }

    public void add(ZipEntryMap zipEntryMap, InputSource inputSource) throws IOException {
        String alias = inputSource.getAlias();
        if (zipEntryMap.getInputSource(alias) == null) {
            zipEntryMap.add(inputSource);
        }
        add(zipEntryMap, alias);
    }

    public void add(ZipEntryMap zipEntryMap, String str) throws IOException {
        add(DexSource.create(zipEntryMap, str));
        if (getZipEntryMap() == null) {
            setZipEntryMap(zipEntryMap);
        }
    }

    public void add(ZipEntryMap zipEntryMap, String str, DexFile dexFile) throws IOException {
        add(DexSource.create(zipEntryMap, str, dexFile));
        if (getZipEntryMap() == null) {
            setZipEntryMap(zipEntryMap);
        }
    }

    public void addAll(File file) throws IOException {
        if (!file.isDirectory()) {
            throw new IOException("No such directory: " + file);
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isFile() && DexFile.getDexFileNumber(file2.getName()) >= 0) {
                add(file2);
            }
        }
    }

    public DexSource<DexFile> add(File file) throws IOException {
        return add(DexSource.create(file));
    }

    public DexSource<DexFile> add(DexSource<DexFile> dexSource) throws IOException {
        DexSource<DexFile> element = this.sourceList.getElement(dexSource);
        if (element != null) {
            if (element == dexSource) {
                load(dexSource);
                return element;
            }
            throw new IOException("Duplicate dex source: " + dexSource);
        }
        load(dexSource);
        this.sourceList.remove(dexSource);
        this.sourceList.add(dexSource);
        this.sourceList.sort(CompareUtil.getComparableComparator());
        return dexSource;
    }

    public DexFile getDexFile(int i) {
        if (i < 0 || i >= size()) {
            return null;
        }
        return getSource(i).get();
    }

    public DexSource<DexFile> getSource(int i) {
        return this.sourceList.get(i);
    }

    public DexSource<DexFile> getSource(DexFile dexFile) {
        if (dexFile == null) {
            return null;
        }
        for (DexSource<DexFile> dexSource : this) {
            if (dexFile == dexSource.get()) {
                return dexSource;
            }
        }
        return null;
    }

    public DexSource<DexFile> getFirst() {
        return this.sourceList.getFirst();
    }

    public DexSource<DexFile> getLast() {
        this.sourceList.sort(CompareUtil.getComparableComparator());
        return this.sourceList.getLast();
    }

    public DexSource<DexFile> createNext() {
        DexSource<DexFile> last = getLast();
        if (last == null) {
            ZipEntryMap zipEntryMap = getZipEntryMap();
            if (zipEntryMap == null) {
                throw new NullPointerException("Null ZipEntryMap");
            }
            DexSource<DexFile> dexSourceCreate = DexSource.create(zipEntryMap, DexFormat.DEX_IN_JAR_NAME, DexFile.createDefault());
            this.sourceList.add(dexSourceCreate);
            return dexSourceCreate;
        }
        DexSource<DexFile> dexSourceCreateNext = last.createNext();
        this.sourceList.add(dexSourceCreateNext);
        this.sourceList.sort(CompareUtil.getComparableComparator());
        return dexSourceCreateNext;
    }

    public String buildNextName() {
        DexSource<DexFile> last = getLast();
        if (last == null) {
            return DexFile.getDexName(0);
        }
        return FileUtil.combineUnixPath(FileUtil.getParent(last.getName()), DexFile.getDexName(last.getDexFileNumber() + 1));
    }

    public int size() {
        return this.sourceList.size();
    }

    public void delete(DexSource<DexFile> dexSource) {
        remove(dexSource);
        dexSource.delete();
    }

    public void remove(DexSource<DexFile> dexSource) {
        this.sourceList.remove(dexSource);
        dexSource.set(null);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Iterator<DexSource<DexFile>> it = this.sourceList.iterator();
        while (it.hasNext()) {
            it.next().close();
        }
        this.sourceList.clear();
    }

    public boolean isReadStringsMode() {
        return this.mReadStringsMode;
    }

    public void setReadStringsMode(boolean z) {
        this.mReadStringsMode = z;
    }

    private void load(DexSource<DexFile> dexSource) throws IOException {
        DexFile strings;
        if (dexSource.get() != null) {
            return;
        }
        if (this.mReadStringsMode) {
            strings = DexFile.readStrings(dexSource.openStream());
        } else {
            strings = DexFile.read(dexSource.openStream());
        }
        dexSource.set(strings);
        strings.setSimpleName(dexSource.toString());
    }

    public String toString() {
        return "size = " + size();
    }
}
