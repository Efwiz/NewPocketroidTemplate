package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: CodeFragmentCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u000eR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CalculatedCodeFragmentCodegenInfo;", "", "parameters", "", "Lorg/jetbrains/kotlin/codegen/CalculatedCodeFragmentCodegenInfo$CalculatedParameter;", "returnAsmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Ljava/util/List;Lorg/jetbrains/org/objectweb/asm/Type;)V", "getParameters", "()Ljava/util/List;", "getReturnAsmType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "findParameter", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "CalculatedParameter", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CalculatedCodeFragmentCodegenInfo {
    private final List<CalculatedParameter> parameters;
    private final Type returnAsmType;

    /* JADX INFO: compiled from: CodeFragmentCodegen.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0012\u0010\u0017\u001a\u00020\u0018X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CalculatedCodeFragmentCodegenInfo$CalculatedParameter;", "Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegenInfo$IParameter;", "parameter", "asmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "stackValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "(Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegenInfo$IParameter;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;)V", "getAsmType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "isLValue", "", "()Z", "getParameterDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getStackValue", "()Lorg/jetbrains/kotlin/codegen/StackValue;", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getTargetDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "targetType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getTargetType", "()Lorg/jetbrains/kotlin/types/KotlinType;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CalculatedParameter implements CodeFragmentCodegenInfo.IParameter {
        private final /* synthetic */ CodeFragmentCodegenInfo.IParameter $$delegate_0;
        private final Type asmType;
        private final ValueParameterDescriptor parameterDescriptor;
        private final StackValue stackValue;

        @Override // org.jetbrains.kotlin.codegen.CodeFragmentCodegenInfo.IParameter
        public DeclarationDescriptor getTargetDescriptor() {
            return this.$$delegate_0.getTargetDescriptor();
        }

        @Override // org.jetbrains.kotlin.codegen.CodeFragmentCodegenInfo.IParameter
        public KotlinType getTargetType() {
            return this.$$delegate_0.getTargetType();
        }

        @Override // org.jetbrains.kotlin.codegen.CodeFragmentCodegenInfo.IParameter
        public boolean isLValue() {
            return this.$$delegate_0.isLValue();
        }

        public CalculatedParameter(CodeFragmentCodegenInfo.IParameter parameter, Type asmType, StackValue stackValue, ValueParameterDescriptor parameterDescriptor) {
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            Intrinsics.checkNotNullParameter(asmType, "asmType");
            Intrinsics.checkNotNullParameter(stackValue, "stackValue");
            Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
            this.asmType = asmType;
            this.stackValue = stackValue;
            this.parameterDescriptor = parameterDescriptor;
            this.$$delegate_0 = parameter;
        }

        public final Type getAsmType() {
            return this.asmType;
        }

        public final StackValue getStackValue() {
            return this.stackValue;
        }

        public final ValueParameterDescriptor getParameterDescriptor() {
            return this.parameterDescriptor;
        }
    }

    public CalculatedCodeFragmentCodegenInfo(List<CalculatedParameter> parameters, Type returnAsmType) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(returnAsmType, "returnAsmType");
        this.parameters = parameters;
        this.returnAsmType = returnAsmType;
    }

    public final List<CalculatedParameter> getParameters() {
        return this.parameters;
    }

    public final Type getReturnAsmType() {
        return this.returnAsmType;
    }

    public final CalculatedParameter findParameter(DeclarationDescriptor target) {
        Intrinsics.checkNotNullParameter(target, "target");
        for (CalculatedParameter calculatedParameter : this.parameters) {
            if (Intrinsics.areEqual(calculatedParameter.getTargetDescriptor(), target) || Intrinsics.areEqual(calculatedParameter.getParameterDescriptor(), target)) {
                return calculatedParameter;
            }
        }
        return null;
    }
}
