package org.jetbrains.kotlin.cfg.variable;

import kotlin.Metadata;
import org.jetbrains.kotlin.cfg.ReadOnlyControlFlowInfo;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;

/* JADX INFO: compiled from: VariableControlFlowInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0000H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/VariableInitReadOnlyControlFlowInfo;", "Lorg/jetbrains/kotlin/cfg/ReadOnlyControlFlowInfo;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/cfg/variable/VariableControlFlowState;", "checkDefiniteInitializationInWhen", "", "merge", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface VariableInitReadOnlyControlFlowInfo extends ReadOnlyControlFlowInfo<VariableDescriptor, VariableControlFlowState> {
    boolean checkDefiniteInitializationInWhen(VariableInitReadOnlyControlFlowInfo merge);
}
