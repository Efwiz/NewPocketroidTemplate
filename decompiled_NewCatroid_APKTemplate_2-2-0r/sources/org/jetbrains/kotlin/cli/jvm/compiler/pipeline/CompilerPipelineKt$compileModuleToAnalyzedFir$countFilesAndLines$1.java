package org.jetbrains.kotlin.cli.jvm.compiler.pipeline;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;

/* JADX INFO: compiled from: compilerPipeline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CompilerPipelineKt$compileModuleToAnalyzedFir$countFilesAndLines$1 extends FunctionReference implements Function2<Integer, Integer, Unit> {
    CompilerPipelineKt$compileModuleToAnalyzedFir$countFilesAndLines$1(Object obj) {
        super(2, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "addSourcesStats(II)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "addSourcesStats";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(CommonCompilerPerformanceManager.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(Integer num, Integer num2) {
        invoke(num.intValue(), num2.intValue());
        return Unit.INSTANCE;
    }

    public final void invoke(int i, int i2) {
        ((CommonCompilerPerformanceManager) this.receiver).addSourcesStats(i, i2);
    }
}
