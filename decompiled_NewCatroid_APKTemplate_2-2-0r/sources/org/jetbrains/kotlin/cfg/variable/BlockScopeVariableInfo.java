package org.jetbrains.kotlin.cfg.variable;

import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;

/* JADX INFO: compiled from: PseudocodeVariableDataCollector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001R\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R$\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\t0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/BlockScopeVariableInfo;", "", "declaredIn", "", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "getDeclaredIn", "()Ljava/util/Map;", "scopeVariables", "", "getScopeVariables", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BlockScopeVariableInfo {
    Map<VariableDescriptor, BlockScope> getDeclaredIn();

    Map<BlockScope, Collection<VariableDescriptor>> getScopeVariables();
}
