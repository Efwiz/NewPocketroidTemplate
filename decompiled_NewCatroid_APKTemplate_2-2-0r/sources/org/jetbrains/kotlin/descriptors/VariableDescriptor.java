package org.jetbrains.kotlin.descriptors;

import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
public interface VariableDescriptor extends ValueDescriptor {
    void cleanCompileTimeInitializerCache();

    /* JADX INFO: renamed from: getCompileTimeInitializer */
    ConstantValue<?> mo6754getCompileTimeInitializer();

    boolean isConst();

    boolean isLateInit();

    boolean isVar();

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    VariableDescriptor substitute(TypeSubstitutor typeSubstitutor);
}
