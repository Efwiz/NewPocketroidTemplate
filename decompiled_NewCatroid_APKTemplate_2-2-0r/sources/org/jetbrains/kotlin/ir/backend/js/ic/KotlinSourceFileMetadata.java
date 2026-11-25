package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: KotlinSourceFileMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bR$\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMetadata;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileExports;", "()V", "directDependencies", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "getDirectDependencies", "()Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFileMap;", "isEmpty", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinSourceFileMetadata extends KotlinSourceFileExports {
    public abstract KotlinSourceFileMap<Map<IdSignature, ICHash>> getDirectDependencies();

    public final boolean isEmpty() {
        return getInverseDependencies().isEmpty() && getDirectDependencies().isEmpty();
    }
}
