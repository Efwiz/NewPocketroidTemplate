package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\u0002\u0010\u0007J0\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u000bø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001eJ(\u0010\u001f\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0004\b \u0010!R&\u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000b0\n0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR \u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u000f0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/DirtyFileMetadata;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "maybeImportedSignatures", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "oldDirectDependencies", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;)V", "directDependencies", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "getDirectDependencies", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;", "inverseDependencies", "", "getInverseDependencies", "getMaybeImportedSignatures", "()Ljava/util/Collection;", "getOldDirectDependencies", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "addDirectDependency", "", "lib", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "src", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "signature", "hash", "addDirectDependency-cWck5Sc", "(Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/ir/util/IdSignature;Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;)V", "addInverseDependency", "addInverseDependency-uEadwyg", "(Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DirtyFileMetadata extends KotlinSourceFileMetadata {
    private final KotlinSourceFileMutableMap<Map<IdSignature, ICHash>> directDependencies;
    private final KotlinSourceFileMutableMap<Set<IdSignature>> inverseDependencies;
    private final Collection<IdSignature> maybeImportedSignatures;
    private final KotlinSourceFileMap<?> oldDirectDependencies;

    public final Collection<IdSignature> getMaybeImportedSignatures() {
        return this.maybeImportedSignatures;
    }

    public final KotlinSourceFileMap<?> getOldDirectDependencies() {
        return this.oldDirectDependencies;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DirtyFileMetadata(Collection<? extends IdSignature> maybeImportedSignatures, KotlinSourceFileMap<?> oldDirectDependencies) {
        Intrinsics.checkNotNullParameter(maybeImportedSignatures, "maybeImportedSignatures");
        Intrinsics.checkNotNullParameter(oldDirectDependencies, "oldDirectDependencies");
        this.maybeImportedSignatures = maybeImportedSignatures;
        this.oldDirectDependencies = oldDirectDependencies;
        this.inverseDependencies = new KotlinSourceFileMutableMap<>(null, 1, null);
        this.directDependencies = new KotlinSourceFileMutableMap<>(null, 1, null);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileExports
    public KotlinSourceFileMutableMap<Set<IdSignature>> getInverseDependencies() {
        return this.inverseDependencies;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileMetadata
    public KotlinSourceFileMutableMap<Map<IdSignature, ICHash>> getDirectDependencies() {
        return this.directDependencies;
    }

    /* JADX INFO: renamed from: addInverseDependency-uEadwyg, reason: not valid java name */
    public final void m7105addInverseDependencyuEadwyg(String lib, String src, IdSignature signature) {
        Intrinsics.checkNotNullParameter(lib, "lib");
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Set<IdSignature> set = getInverseDependencies().m7178getIlDO4Ro(lib, src);
        if (set == null) {
            getInverseDependencies().m7188setuEadwyg(lib, src, SetsKt.hashSetOf(signature));
        } else {
            set.add(signature);
        }
    }

    /* JADX INFO: renamed from: addDirectDependency-cWck5Sc, reason: not valid java name */
    public final void m7104addDirectDependencycWck5Sc(String lib, String src, IdSignature signature, Hash128Bits hash) {
        Intrinsics.checkNotNullParameter(lib, "lib");
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(hash, "hash");
        Map<IdSignature, ICHash> map = getDirectDependencies().m7178getIlDO4Ro(lib, src);
        if (map == null) {
            getDirectDependencies().m7188setuEadwyg(lib, src, MapsKt.hashMapOf(TuplesKt.to(signature, ICHash.m7107boximpl(hash))));
        } else {
            map.put(signature, ICHash.m7107boximpl(hash));
        }
    }
}
