package org.jetbrains.kotlin.ir.backend.js.ic;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0012\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012 \b\u0002\u0010\u0003\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00000\u00040\u0004¢\u0006\u0002\u0010\u0007J\u0006\u0010\b\u001a\u00020\tJ\u0014\u0010\n\u001a\u00020\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002J$\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\r\u001a\u00020\u0005ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fJ \u0010\u0010\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J=\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u00052\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00000\u0004H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017J-\u0010\u0014\u001a\u0004\u0018\u00018\u00002\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00028\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001aR&\u0010\u0003\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00028\u00000\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "files", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "(Ljava/util/Map;)V", LineReader.CLEAR, "", "copyFilesFrom", "other", "getOrPutFiles", "libFile", "getOrPutFiles-5h9WozY", "(Ljava/lang/String;)Ljava/util/Map;", "removeFile", "sourceFile", "removeFile-IlDO4Ro", "(Ljava/lang/String;Ljava/lang/String;)V", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "sourceFiles", "set-BOFFYcU", "(Ljava/lang/String;Ljava/util/Map;)Ljava/util/Map;", "data", "set-uEadwyg", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinSourceFileMutableMap<T> extends KotlinSourceFileMap<T> {
    private final Map<KotlinLibraryFile, Map<KotlinSourceFile, T>> files;

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinSourceFileMutableMap() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ KotlinSourceFileMutableMap(HashMap map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new HashMap() : map);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinSourceFileMutableMap(Map<KotlinLibraryFile, Map<KotlinSourceFile, T>> files) {
        super(files);
        Intrinsics.checkNotNullParameter(files, "files");
        this.files = files;
    }

    /* JADX INFO: renamed from: set-uEadwyg, reason: not valid java name */
    public final T m7188setuEadwyg(String libFile, String sourceFile, T data) {
        Intrinsics.checkNotNullParameter(libFile, "libFile");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        return m7185getOrPutFiles5h9WozY(libFile).put(KotlinSourceFile.m7163boximpl(sourceFile), data);
    }

    /* JADX INFO: renamed from: set-BOFFYcU, reason: not valid java name */
    public final Map<KotlinSourceFile, T> m7187setBOFFYcU(String libFile, Map<KotlinSourceFile, T> sourceFiles) {
        Intrinsics.checkNotNullParameter(libFile, "libFile");
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        return this.files.put(KotlinLibraryFile.m7149boximpl(libFile), sourceFiles);
    }

    /* JADX INFO: renamed from: getOrPutFiles-5h9WozY, reason: not valid java name */
    public final Map<KotlinSourceFile, T> m7185getOrPutFiles5h9WozY(String libFile) {
        Intrinsics.checkNotNullParameter(libFile, "libFile");
        Map<KotlinLibraryFile, Map<KotlinSourceFile, T>> map = this.files;
        KotlinLibraryFile kotlinLibraryFileM7149boximpl = KotlinLibraryFile.m7149boximpl(libFile);
        HashMap map2 = map.get(kotlinLibraryFileM7149boximpl);
        if (map2 == null) {
            map2 = new HashMap();
            map.put(kotlinLibraryFileM7149boximpl, map2);
        }
        return map2;
    }

    public final void copyFilesFrom(KotlinSourceFileMap<? extends T> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        for (Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, ? extends T>> entry : other.entrySet()) {
            String strM7157unboximpl = entry.getKey().m7157unboximpl();
            Map<KotlinSourceFile, ? extends T> value = entry.getValue();
            Map<KotlinLibraryFile, Map<KotlinSourceFile, T>> map = this.files;
            KotlinLibraryFile kotlinLibraryFileM7149boximpl = KotlinLibraryFile.m7149boximpl(strM7157unboximpl);
            HashMap map2 = map.get(kotlinLibraryFileM7149boximpl);
            if (map2 == null) {
                map2 = new HashMap();
                map.put(kotlinLibraryFileM7149boximpl, map2);
            }
            map2.putAll(value);
        }
    }

    /* JADX INFO: renamed from: removeFile-IlDO4Ro, reason: not valid java name */
    public final void m7186removeFileIlDO4Ro(String libFile, String sourceFile) {
        Intrinsics.checkNotNullParameter(libFile, "libFile");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Map<KotlinSourceFile, T> map = this.files.get(KotlinLibraryFile.m7149boximpl(libFile));
        if (map != null) {
            map.remove(KotlinSourceFile.m7163boximpl(sourceFile));
            if (map.isEmpty()) {
                this.files.remove(KotlinLibraryFile.m7149boximpl(libFile));
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileMap, java.util.Map
    public final void clear() {
        this.files.clear();
    }
}
