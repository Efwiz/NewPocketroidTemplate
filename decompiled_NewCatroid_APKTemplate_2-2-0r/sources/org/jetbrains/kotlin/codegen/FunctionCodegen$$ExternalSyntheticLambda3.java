package org.jetbrains.kotlin.codegen;

import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class FunctionCodegen$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ KotlinTypeMapper f$0;

    public /* synthetic */ FunctionCodegen$$ExternalSyntheticLambda3(KotlinTypeMapper kotlinTypeMapper) {
        this.f$0 = kotlinTypeMapper;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return this.f$0.mapAsmMethod((FunctionDescriptor) obj);
    }
}
