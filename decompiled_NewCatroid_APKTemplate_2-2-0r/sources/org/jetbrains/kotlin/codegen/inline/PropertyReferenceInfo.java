package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;

/* JADX INFO: compiled from: PropertyReferenceInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/PropertyReferenceInfo;", "", "target", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "getFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "getGetFunction", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getTarget", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyReferenceInfo {
    private final FunctionDescriptor getFunction;
    private final VariableDescriptor target;

    public PropertyReferenceInfo(VariableDescriptor target, FunctionDescriptor getFunction) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(getFunction, "getFunction");
        this.target = target;
        this.getFunction = getFunction;
    }

    public final VariableDescriptor getTarget() {
        return this.target;
    }

    public final FunctionDescriptor getGetFunction() {
        return this.getFunction;
    }
}
