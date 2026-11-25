package org.jetbrains.kotlin.cfg.variable;

import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.Iterator;
import javaslang.collection.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cfg.ControlFlowInfo;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.util.javaslang.JavaslangAdaptersKt;

/* JADX INFO: compiled from: VariableControlFlowInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002$\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0003`\u00042\u00020\u0005B+\u0012$\b\u0002\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0007j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005H\u0016J,\u0010\r\u001a\u00020\u00002\"\u0010\u000e\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0007j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\bH\u0014¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/VariableInitControlFlowInfo;", "Lorg/jetbrains/kotlin/cfg/ControlFlowInfo;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/cfg/variable/VariableControlFlowState;", "Lorg/jetbrains/kotlin/cfg/variable/VariableUsageControlFlowInfo;", "Lorg/jetbrains/kotlin/cfg/variable/VariableInitReadOnlyControlFlowInfo;", "map", "Ljavaslang/collection/Map;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", "(Ljavaslang/collection/Map;)V", "checkDefiniteInitializationInWhen", "", "merge", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newMap", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableInitControlFlowInfo extends ControlFlowInfo<VariableInitControlFlowInfo, VariableDescriptor, VariableControlFlowState> implements VariableInitReadOnlyControlFlowInfo {
    /* JADX WARN: Multi-variable type inference failed */
    public VariableInitControlFlowInfo() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ VariableInitControlFlowInfo(Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            HashMap hashMapEmpty = HashMap.empty();
            Intrinsics.checkNotNullExpressionValue(hashMapEmpty, "empty(...)");
            map = hashMapEmpty;
        }
        this(map);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VariableInitControlFlowInfo(Map<VariableDescriptor, VariableControlFlowState> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cfg.ControlFlowInfo
    public VariableInitControlFlowInfo copy(Map<VariableDescriptor, VariableControlFlowState> newMap) {
        Intrinsics.checkNotNullParameter(newMap, "newMap");
        return new VariableInitControlFlowInfo(newMap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.cfg.variable.VariableInitReadOnlyControlFlowInfo
    public boolean checkDefiniteInitializationInWhen(VariableInitReadOnlyControlFlowInfo merge) {
        Intrinsics.checkNotNullParameter(merge, "merge");
        Iterator it = iterator().iterator();
        while (it.hasNext()) {
            Tuple2 tuple2 = (Tuple2) it.next();
            Intrinsics.checkNotNull(tuple2);
            VariableDescriptor variableDescriptor = (VariableDescriptor) JavaslangAdaptersKt.component1(tuple2);
            if (((VariableControlFlowState) JavaslangAdaptersKt.component2(tuple2)).getInitState() == InitState.INITIALIZED_EXHAUSTIVELY) {
                Intrinsics.checkNotNull(variableDescriptor);
                VariableControlFlowState orNull = merge.getOrNull(variableDescriptor);
                if ((orNull != null ? orNull.getInitState() : null) == InitState.INITIALIZED) {
                    return true;
                }
            }
        }
        return false;
    }
}
