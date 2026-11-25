package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R&\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR \u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u000b0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadataNotExist;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "()V", "directDependencies", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "getDirectDependencies", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "inverseDependencies", "", "getInverseDependencies", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinSourceFileMetadataNotExist extends KotlinSourceFileMetadata {
    public static final KotlinSourceFileMetadataNotExist INSTANCE = new KotlinSourceFileMetadataNotExist();
    private static final KotlinSourceFileMap<Set<IdSignature>> inverseDependencies = new KotlinSourceFileMap<>(MapsKt.emptyMap());
    private static final KotlinSourceFileMap<Map<IdSignature, ICHash>> directDependencies = new KotlinSourceFileMap<>(MapsKt.emptyMap());

    private KotlinSourceFileMetadataNotExist() {
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileExports
    public KotlinSourceFileMap<Set<IdSignature>> getInverseDependencies() {
        return inverseDependencies;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileMetadata
    public KotlinSourceFileMap<Map<IdSignature, ICHash>> getDirectDependencies() {
        return directDependencies;
    }
}
