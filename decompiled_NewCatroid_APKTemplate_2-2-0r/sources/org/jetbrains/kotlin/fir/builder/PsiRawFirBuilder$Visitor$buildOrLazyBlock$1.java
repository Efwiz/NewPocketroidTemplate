package org.jetbrains.kotlin.fir.builder;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.expressions.FirLazyBlock;
import org.jetbrains.kotlin.fir.expressions.builder.FirLazyBlockBuilderKt;

/* JADX INFO: compiled from: PsiRawFirBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
public /* synthetic */ class PsiRawFirBuilder$Visitor$buildOrLazyBlock$1 extends FunctionReference implements Function0<FirLazyBlock> {
    public static final PsiRawFirBuilder$Visitor$buildOrLazyBlock$1 INSTANCE = new PsiRawFirBuilder$Visitor$buildOrLazyBlock$1();

    public PsiRawFirBuilder$Visitor$buildOrLazyBlock$1() {
        super(0);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "buildLazyBlock()Lorg/jetbrains/kotlin/fir/expressions/FirLazyBlock;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "buildLazyBlock";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(FirLazyBlockBuilderKt.class, "psi2fir");
    }

    @Override // kotlin.jvm.functions.Function0
    public final FirLazyBlock invoke() {
        return FirLazyBlockBuilderKt.buildLazyBlock();
    }
}
