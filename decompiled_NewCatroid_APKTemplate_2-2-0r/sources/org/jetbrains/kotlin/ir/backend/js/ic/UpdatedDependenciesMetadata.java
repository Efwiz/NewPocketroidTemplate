package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012H\u0016J\u0006\u0010\u001c\u001a\u00020\u001dR&\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R \u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00120\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\nR!\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00120\u001aX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/UpdatedDependenciesMetadata;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "oldMetadata", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;)V", "directDependencies", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "getDirectDependencies", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMutableMap;", "importedSignaturesState", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ImportedSignaturesState;", "getImportedSignaturesState", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/ImportedSignaturesState;", "setImportedSignaturesState", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/ImportedSignaturesState;)V", "inverseDependencies", "", "getInverseDependencies", "newExportedSignatures", "getNewExportedSignatures", "()Ljava/util/Set;", "newExportedSignatures$delegate", "Lkotlin/Lazy;", "oldInverseDependencies", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "getExportedSignatures", "isExportedSignaturesUpdated", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UpdatedDependenciesMetadata extends KotlinSourceFileMetadata {
    private final KotlinSourceFileMutableMap<Map<IdSignature, ICHash>> directDependencies;
    private ImportedSignaturesState importedSignaturesState;
    private final KotlinSourceFileMutableMap<Set<IdSignature>> inverseDependencies;

    /* JADX INFO: renamed from: newExportedSignatures$delegate, reason: from kotlin metadata */
    private final Lazy newExportedSignatures;
    private final KotlinSourceFileMap<Set<IdSignature>> oldInverseDependencies;

    public UpdatedDependenciesMetadata(KotlinSourceFileMetadata oldMetadata) {
        Intrinsics.checkNotNullParameter(oldMetadata, "oldMetadata");
        this.oldInverseDependencies = oldMetadata.getInverseDependencies();
        this.newExportedSignatures = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Set<? extends IdSignature>>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.UpdatedDependenciesMetadata$newExportedSignatures$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends IdSignature> invoke() {
                return KotlinSourceFileMetadataKt.flatSignatures(this.this$0.getInverseDependencies());
            }
        });
        this.importedSignaturesState = ImportedSignaturesState.UNKNOWN;
        this.inverseDependencies = KotlinSourceFileMetadataKt.toMutable(oldMetadata.getInverseDependencies());
        this.directDependencies = KotlinSourceFileMetadataKt.toMutable(oldMetadata.getDirectDependencies());
    }

    private final Set<IdSignature> getNewExportedSignatures() {
        return (Set) this.newExportedSignatures.getValue();
    }

    public final ImportedSignaturesState getImportedSignaturesState() {
        return this.importedSignaturesState;
    }

    public final void setImportedSignaturesState(ImportedSignaturesState importedSignaturesState) {
        Intrinsics.checkNotNullParameter(importedSignaturesState, "<set-?>");
        this.importedSignaturesState = importedSignaturesState;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileExports
    public KotlinSourceFileMutableMap<Set<IdSignature>> getInverseDependencies() {
        return this.inverseDependencies;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileMetadata
    public KotlinSourceFileMutableMap<Map<IdSignature, ICHash>> getDirectDependencies() {
        return this.directDependencies;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.ic.KotlinSourceFileExports
    public Set<IdSignature> getExportedSignatures() {
        return getNewExportedSignatures();
    }

    public final boolean isExportedSignaturesUpdated() {
        return !Intrinsics.areEqual(getNewExportedSignatures(), KotlinSourceFileMetadataKt.flatSignatures(this.oldInverseDependencies));
    }
}
