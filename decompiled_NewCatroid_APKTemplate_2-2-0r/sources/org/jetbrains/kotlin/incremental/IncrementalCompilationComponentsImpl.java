package org.jetbrains.kotlin.incremental;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.modules.TargetId;

/* JADX INFO: compiled from: IncrementalCompilationComponentsImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0004H\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalCompilationComponentsImpl;", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCompilationComponents;", "caches", "", "Lorg/jetbrains/kotlin/modules/TargetId;", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "(Ljava/util/Map;)V", "getIncrementalCache", "target", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalCompilationComponentsImpl implements IncrementalCompilationComponents {
    private final Map<TargetId, IncrementalCache> caches;

    /* JADX WARN: Multi-variable type inference failed */
    public IncrementalCompilationComponentsImpl(Map<TargetId, ? extends IncrementalCache> caches) {
        Intrinsics.checkNotNullParameter(caches, "caches");
        this.caches = caches;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents
    public IncrementalCache getIncrementalCache(TargetId target) throws Exception {
        Intrinsics.checkNotNullParameter(target, "target");
        IncrementalCache incrementalCache = this.caches.get(target);
        if (incrementalCache != null) {
            return incrementalCache;
        }
        throw new Exception("Incremental cache for target " + target.getName() + " not found");
    }
}
