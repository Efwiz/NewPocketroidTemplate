package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016R\u001e\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileExports;", "", "()V", "inverseDependencies", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getInverseDependencies", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "getExportedSignatures", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinSourceFileExports {
    public abstract KotlinSourceFileMap<Set<IdSignature>> getInverseDependencies();

    public Set<IdSignature> getExportedSignatures() {
        return KotlinSourceFileMetadataKt.flatSignatures(getInverseDependencies());
    }
}
