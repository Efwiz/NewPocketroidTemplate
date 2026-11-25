package org.jetbrains.kotlin.codegen.state;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: BuilderFactoryForDuplicateSignatureDiagnostics.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class BuilderFactoryForDuplicateSignatureDiagnostics$mapAsmMethod$1 extends FunctionReference implements Function1<FunctionDescriptor, Method> {
    BuilderFactoryForDuplicateSignatureDiagnostics$mapAsmMethod$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "mapAsmMethod(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)Lorg/jetbrains/org/objectweb/asm/commons/Method;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "mapAsmMethod";
    }

    @Override // kotlin.jvm.functions.Function1
    public final Method invoke(FunctionDescriptor p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((KotlinTypeMapper) this.receiver).mapAsmMethod(p0);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(KotlinTypeMapper.class);
    }
}
