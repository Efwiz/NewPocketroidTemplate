package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016R!\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR \u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/DirtyFileExports;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileExports;", "()V", "allExportedSignatures", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lkotlin/collections/HashSet;", "getAllExportedSignatures", "()Ljava/util/HashSet;", "inverseDependencies", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;", "", "getInverseDependencies", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;", "getExportedSignatures", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DirtyFileExports extends KotlinSourceFileExports {
    private final HashSet<IdSignature> allExportedSignatures = new HashSet<>();
    private final KotlinSourceFileMutableMap<Set<IdSignature>> inverseDependencies = new KotlinSourceFileMutableMap<>(null, 1, null);

    public final HashSet<IdSignature> getAllExportedSignatures() {
        return this.allExportedSignatures;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileExports
    public KotlinSourceFileMutableMap<Set<IdSignature>> getInverseDependencies() {
        return this.inverseDependencies;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileExports
    public Set<IdSignature> getExportedSignatures() {
        return this.allExportedSignatures;
    }
}
