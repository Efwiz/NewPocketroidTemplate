package org.jetbrains.kotlin.cfg.variable;

import javaslang.collection.HashMap;
import javaslang.collection.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cfg.ControlFlowInfo;
import org.jetbrains.kotlin.cfg.ReadOnlyControlFlowInfo;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;

/* JADX INFO: compiled from: VariableControlFlowInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002$\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0003`\u00042\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0005j\u0002`\u0006B+\u0012$\b\u0002\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\bj\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\t¢\u0006\u0002\u0010\nJ,\u0010\u000b\u001a\u00020\u00002\"\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\bj\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\tH\u0014¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/UsageVariableControlFlowInfo;", "Lorg/jetbrains/kotlin/cfg/ControlFlowInfo;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/cfg/variable/VariableUseState;", "Lorg/jetbrains/kotlin/cfg/variable/VariableUsageControlFlowInfo;", "Lorg/jetbrains/kotlin/cfg/ReadOnlyControlFlowInfo;", "Lorg/jetbrains/kotlin/cfg/variable/VariableUsageReadOnlyControlInfo;", "map", "Ljavaslang/collection/Map;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", "(Ljavaslang/collection/Map;)V", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newMap", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UsageVariableControlFlowInfo extends ControlFlowInfo<UsageVariableControlFlowInfo, VariableDescriptor, VariableUseState> implements ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> {
    /* JADX WARN: Multi-variable type inference failed */
    public UsageVariableControlFlowInfo() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ UsageVariableControlFlowInfo(Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            HashMap hashMapEmpty = HashMap.empty();
            Intrinsics.checkNotNullExpressionValue(hashMapEmpty, "empty(...)");
            map = hashMapEmpty;
        }
        this(map);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UsageVariableControlFlowInfo(Map<VariableDescriptor, VariableUseState> map) {
        super(map);
        Intrinsics.checkNotNullParameter(map, "map");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cfg.ControlFlowInfo
    public UsageVariableControlFlowInfo copy(Map<VariableDescriptor, VariableUseState> newMap) {
        Intrinsics.checkNotNullParameter(newMap, "newMap");
        return new UsageVariableControlFlowInfo(newMap);
    }
}
