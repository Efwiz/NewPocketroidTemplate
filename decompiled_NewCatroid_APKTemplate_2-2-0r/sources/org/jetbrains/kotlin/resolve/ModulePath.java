package org.jetbrains.kotlin.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: ModuleStructureOracle.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001b\b\u0016\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ModulePath;", "", "nodes", "", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "([Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "", "(Ljava/util/List;)V", "getNodes", "()Ljava/util/List;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModulePath {
    private final List<ModuleDescriptor> nodes;

    /* JADX WARN: Multi-variable type inference failed */
    public ModulePath(List<? extends ModuleDescriptor> nodes) {
        Intrinsics.checkNotNullParameter(nodes, "nodes");
        this.nodes = nodes;
    }

    public final List<ModuleDescriptor> getNodes() {
        return this.nodes;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ModulePath(ModuleDescriptor... nodes) {
        this((List<? extends ModuleDescriptor>) ArraysKt.toList(nodes));
        Intrinsics.checkNotNullParameter(nodes, "nodes");
    }
}
