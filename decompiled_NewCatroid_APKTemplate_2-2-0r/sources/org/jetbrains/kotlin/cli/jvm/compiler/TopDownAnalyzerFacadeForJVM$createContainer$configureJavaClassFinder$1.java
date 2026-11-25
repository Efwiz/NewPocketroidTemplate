package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.container.StorageComponentContainer;

/* JADX INFO: compiled from: TopDownAnalyzerFacadeForJVM.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class TopDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$1 extends FunctionReference implements Function1<StorageComponentContainer, Unit> {
    public static final TopDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$1 INSTANCE = new TopDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$1();

    TopDownAnalyzerFacadeForJVM$createContainer$configureJavaClassFinder$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "createContainer$useJavac(Lorg/jetbrains/kotlin/container/StorageComponentContainer;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "useJavac";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(StorageComponentContainer storageComponentContainer) {
        invoke2(storageComponentContainer);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(StorageComponentContainer p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        TopDownAnalyzerFacadeForJVM.createContainer$useJavac(p0);
    }
}
