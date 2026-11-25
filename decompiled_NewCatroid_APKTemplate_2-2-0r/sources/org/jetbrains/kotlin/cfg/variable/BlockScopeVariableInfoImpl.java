package org.jetbrains.kotlin.cfg.variable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;

/* JADX INFO: compiled from: PseudocodeVariableDataCollector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0006R0\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR<\u0010\n\u001a*\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\u0004j\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b`\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/BlockScopeVariableInfoImpl;", "Lorg/jetbrains/kotlin/cfg/variable/BlockScopeVariableInfo;", "()V", "declaredIn", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/BlockScope;", "Lkotlin/collections/HashMap;", "getDeclaredIn", "()Ljava/util/HashMap;", "scopeVariables", "", "getScopeVariables", "registerVariableDeclaredInScope", "", "variable", "blockScope", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BlockScopeVariableInfoImpl implements BlockScopeVariableInfo {
    private final HashMap<VariableDescriptor, BlockScope> declaredIn = new HashMap<>();
    private final HashMap<BlockScope, Collection<VariableDescriptor>> scopeVariables = new HashMap<>();

    @Override // org.jetbrains.kotlin.cfg.variable.BlockScopeVariableInfo
    public HashMap<VariableDescriptor, BlockScope> getDeclaredIn() {
        return this.declaredIn;
    }

    @Override // org.jetbrains.kotlin.cfg.variable.BlockScopeVariableInfo
    public HashMap<BlockScope, Collection<VariableDescriptor>> getScopeVariables() {
        return this.scopeVariables;
    }

    public final void registerVariableDeclaredInScope(VariableDescriptor variable, BlockScope blockScope) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(blockScope, "blockScope");
        getDeclaredIn().put(variable, blockScope);
        HashMap<BlockScope, Collection<VariableDescriptor>> scopeVariables = getScopeVariables();
        ArrayList arrayList = scopeVariables.get(blockScope);
        if (arrayList == null) {
            arrayList = new ArrayList();
            scopeVariables.put(blockScope, arrayList);
        }
        arrayList.add(variable);
    }
}
