package org.jetbrains.kotlin.codegen;

import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;

/* JADX INFO: loaded from: classes3.dex */
public interface DefaultParameterValueLoader {
    public static final DefaultParameterValueLoader DEFAULT;

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.DefaultParameterValueLoader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ boolean $assertionsDisabled = false;
    }

    StackValue genValue(ValueParameterDescriptor valueParameterDescriptor, ExpressionCodegen expressionCodegen);

    static {
        boolean z = AnonymousClass1.$assertionsDisabled;
        DEFAULT = new DefaultParameterValueLoader() { // from class: org.jetbrains.kotlin.codegen.DefaultParameterValueLoader$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.codegen.DefaultParameterValueLoader
            public final StackValue genValue(ValueParameterDescriptor valueParameterDescriptor, ExpressionCodegen expressionCodegen) {
                return DefaultParameterValueLoader.lambda$static$0(valueParameterDescriptor, expressionCodegen);
            }
        };
    }

    static /* synthetic */ StackValue lambda$static$0(ValueParameterDescriptor valueParameterDescriptor, ExpressionCodegen expressionCodegen) {
        KtParameter ktParameter = (KtParameter) DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor);
        if (AnonymousClass1.$assertionsDisabled || ktParameter != null) {
            return expressionCodegen.gen(ktParameter.getDefaultValue());
        }
        throw new AssertionError();
    }
}
