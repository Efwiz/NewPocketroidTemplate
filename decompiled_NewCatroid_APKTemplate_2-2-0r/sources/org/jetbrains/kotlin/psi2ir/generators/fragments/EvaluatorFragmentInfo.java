package org.jetbrains.kotlin.psi2ir.generators.fragments;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;

/* JADX INFO: compiled from: EvaluatorFragmentInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "methodDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "parameters", "", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentParameterInfo;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Ljava/util/List;)V", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getMethodDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getParameters", "()Ljava/util/List;", "Companion", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EvaluatorFragmentInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ClassDescriptor classDescriptor;
    private final FunctionDescriptor methodDescriptor;
    private final List<EvaluatorFragmentParameterInfo> parameters;

    public EvaluatorFragmentInfo(ClassDescriptor classDescriptor, FunctionDescriptor methodDescriptor, List<EvaluatorFragmentParameterInfo> parameters) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(methodDescriptor, "methodDescriptor");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.classDescriptor = classDescriptor;
        this.methodDescriptor = methodDescriptor;
        this.parameters = parameters;
    }

    public final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    public final FunctionDescriptor getMethodDescriptor() {
        return this.methodDescriptor;
    }

    public final List<EvaluatorFragmentParameterInfo> getParameters() {
        return this.parameters;
    }

    /* JADX INFO: compiled from: EvaluatorFragmentInfo.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo$Companion;", "", "()V", "createWithFragmentParameterInfo", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "methodDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "parametersWithInfo", "", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentParameterInfo;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final EvaluatorFragmentInfo createWithFragmentParameterInfo(ClassDescriptor classDescriptor, FunctionDescriptor methodDescriptor, List<EvaluatorFragmentParameterInfo> parametersWithInfo) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(methodDescriptor, "methodDescriptor");
            Intrinsics.checkNotNullParameter(parametersWithInfo, "parametersWithInfo");
            return new EvaluatorFragmentInfo(classDescriptor, methodDescriptor, parametersWithInfo);
        }
    }
}
