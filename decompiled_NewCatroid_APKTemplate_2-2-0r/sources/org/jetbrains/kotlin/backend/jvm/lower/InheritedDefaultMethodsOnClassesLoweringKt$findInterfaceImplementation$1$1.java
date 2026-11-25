package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: InheritedDefaultMethodsOnClassesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class InheritedDefaultMethodsOnClassesLoweringKt$findInterfaceImplementation$1$1 extends FunctionReference implements Function1<IrSimpleFunction, Boolean> {
    public static final InheritedDefaultMethodsOnClassesLoweringKt$findInterfaceImplementation$1$1 INSTANCE = new InheritedDefaultMethodsOnClassesLoweringKt$findInterfaceImplementation$1$1();

    InheritedDefaultMethodsOnClassesLoweringKt$findInterfaceImplementation$1$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "isDefaultImplsBridge(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Z";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "isDefaultImplsBridge";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(InheritedDefaultMethodsOnClassesLoweringKt.class, "backend.jvm.lower");
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(IrSimpleFunction p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(InheritedDefaultMethodsOnClassesLoweringKt.isDefaultImplsBridge(p0));
    }
}
