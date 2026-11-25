package org.jetbrains.kotlin.konan.file;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;

/* JADX INFO: compiled from: ZipFileSystemAccessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0000*\u0001\u0000\b\n\u0018\u00002\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bH\u0014¨\u0006\t"}, d2 = {"org/jetbrains/kotlin/konan/file/ZipFileSystemCacheableAccessor$openedFileSystems$1", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/konan/file/File;", "Ljava/nio/file/FileSystem;", "Lkotlin/collections/LinkedHashMap;", "removeEldestEntry", "", "eldest", "", "kotlin-util-io"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ZipFileSystemCacheableAccessor$openedFileSystems$1 extends LinkedHashMap<File, FileSystem> {
    final /* synthetic */ ZipFileSystemCacheableAccessor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ZipFileSystemCacheableAccessor$openedFileSystems$1(ZipFileSystemCacheableAccessor zipFileSystemCacheableAccessor, int i, float f) {
        super(i, f, true);
        this.this$0 = zipFileSystemCacheableAccessor;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof File) {
            return containsKey((File) obj);
        }
        return false;
    }

    public /* bridge */ boolean containsKey(File file) {
        return super.containsKey((Object) file);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsValue(Object obj) {
        if (obj instanceof FileSystem) {
            return containsValue((FileSystem) obj);
        }
        return false;
    }

    public /* bridge */ boolean containsValue(FileSystem fileSystem) {
        return super.containsValue((Object) fileSystem);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Set<Map.Entry<File, FileSystem>> entrySet() {
        return getEntries();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object get(Object obj) {
        if (obj instanceof File) {
            return get((File) obj);
        }
        return null;
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ FileSystem get(Object obj) {
        if (obj instanceof File) {
            return get((File) obj);
        }
        return null;
    }

    public /* bridge */ FileSystem get(File file) {
        return (FileSystem) super.get((Object) file);
    }

    public /* bridge */ Set<Map.Entry<File, FileSystem>> getEntries() {
        return super.entrySet();
    }

    public /* bridge */ Set<File> getKeys() {
        return super.keySet();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
        return !(obj instanceof File) ? obj2 : getOrDefault((File) obj, (FileSystem) obj2);
    }

    public final /* bridge */ FileSystem getOrDefault(Object obj, FileSystem fileSystem) {
        return !(obj instanceof File) ? fileSystem : getOrDefault((File) obj, fileSystem);
    }

    public /* bridge */ FileSystem getOrDefault(File file, FileSystem fileSystem) {
        return (FileSystem) super.getOrDefault((Object) file, fileSystem);
    }

    public /* bridge */ int getSize() {
        return super.size();
    }

    public /* bridge */ Collection<FileSystem> getValues() {
        return super.values();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Set<File> keySet() {
        return getKeys();
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object remove(Object obj) {
        if (obj instanceof File) {
            return remove((File) obj);
        }
        return null;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ FileSystem remove(Object obj) {
        if (obj instanceof File) {
            return remove((File) obj);
        }
        return null;
    }

    public /* bridge */ FileSystem remove(File file) {
        return (FileSystem) super.remove((Object) file);
    }

    @Override // java.util.HashMap, java.util.Map
    public final /* bridge */ boolean remove(Object obj, Object obj2) {
        if ((obj instanceof File) && (obj2 instanceof FileSystem)) {
            return remove((File) obj, (FileSystem) obj2);
        }
        return false;
    }

    public /* bridge */ boolean remove(File file, FileSystem fileSystem) {
        return super.remove((Object) file, (Object) fileSystem);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Collection<FileSystem> values() {
        return getValues();
    }

    @Override // java.util.LinkedHashMap
    protected boolean removeEldestEntry(Map.Entry<File, FileSystem> eldest) throws IOException {
        FileSystem value;
        if (size() <= this.this$0.cacheLimit) {
            return false;
        }
        if (eldest == null || (value = eldest.getValue()) == null) {
            return true;
        }
        value.close();
        return true;
    }
}
