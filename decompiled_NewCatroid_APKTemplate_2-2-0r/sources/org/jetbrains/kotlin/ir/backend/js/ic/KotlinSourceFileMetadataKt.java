package org.jetbrains.kotlin.ir.backend.js.ic;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\r0\f\"\u0004\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\r0\f\u001a\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\f\u001a\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\r0\u0002\"\u0004\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\f\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\u0013"}, d2 = {"addNewMetadata", "Lorg/jetbrains/kotlin/ir/backend/js/ic/UpdatedDependenciesMetadata;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;", "libFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "srcFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "oldMetadata", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "addNewMetadata-K7-jKXY", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;)Lorg/jetbrains/kotlin/ir/backend/js/ic/UpdatedDependenciesMetadata;", "combineWith", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", ExifInterface.GPS_DIRECTION_TRUE, "other", "flatSignatures", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "toMutable", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinSourceFileMetadataKt {
    public static final <T> KotlinSourceFileMutableMap<T> toMutable(KotlinSourceFileMap<? extends T> kotlinSourceFileMap) {
        Intrinsics.checkNotNullParameter(kotlinSourceFileMap, "<this>");
        Set<Map.Entry<KotlinLibraryFile, Map<KotlinSourceFile, ? extends T>>> setEntrySet = kotlinSourceFileMap.entrySet();
        HashMap map = new HashMap(kotlinSourceFileMap.entrySet().size());
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to(entry.getKey(), new HashMap((Map) entry.getValue()));
            map.put(pair.getFirst(), pair.getSecond());
        }
        return new KotlinSourceFileMutableMap<>(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> KotlinSourceFileMap<T> combineWith(KotlinSourceFileMap<? extends T> kotlinSourceFileMap, KotlinSourceFileMap<? extends T> other) {
        Intrinsics.checkNotNullParameter(kotlinSourceFileMap, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (kotlinSourceFileMap.isEmpty()) {
            return other;
        }
        if (other.isEmpty()) {
            return kotlinSourceFileMap;
        }
        KotlinSourceFileMutableMap mutable = toMutable(kotlinSourceFileMap);
        mutable.copyFilesFrom(other);
        return mutable;
    }

    public static final Set<IdSignature> flatSignatures(KotlinSourceFileMap<? extends Set<? extends IdSignature>> kotlinSourceFileMap) {
        Intrinsics.checkNotNullParameter(kotlinSourceFileMap, "<this>");
        HashSet hashSet = new HashSet();
        Iterator it = kotlinSourceFileMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ((KotlinLibraryFile) entry.getKey()).m7157unboximpl();
            for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                ((KotlinSourceFile) entry2.getKey()).m7171unboximpl();
                CollectionsKt.addAll(hashSet, (Set) entry2.getValue());
            }
        }
        return hashSet;
    }

    /* JADX INFO: renamed from: addNewMetadata-K7-jKXY, reason: not valid java name */
    public static final UpdatedDependenciesMetadata m7184addNewMetadataK7jKXY(KotlinSourceFileMutableMap<UpdatedDependenciesMetadata> addNewMetadata, String libFile, String srcFile, KotlinSourceFileMetadata oldMetadata) {
        Intrinsics.checkNotNullParameter(addNewMetadata, "$this$addNewMetadata");
        Intrinsics.checkNotNullParameter(libFile, "libFile");
        Intrinsics.checkNotNullParameter(srcFile, "srcFile");
        Intrinsics.checkNotNullParameter(oldMetadata, "oldMetadata");
        UpdatedDependenciesMetadata updatedDependenciesMetadata = addNewMetadata.m7178getIlDO4Ro(libFile, srcFile);
        if (updatedDependenciesMetadata != null) {
            return updatedDependenciesMetadata;
        }
        UpdatedDependenciesMetadata updatedDependenciesMetadata2 = new UpdatedDependenciesMetadata(oldMetadata);
        addNewMetadata.m7188setuEadwyg(libFile, srcFile, updatedDependenciesMetadata2);
        return updatedDependenciesMetadata2;
    }
}
