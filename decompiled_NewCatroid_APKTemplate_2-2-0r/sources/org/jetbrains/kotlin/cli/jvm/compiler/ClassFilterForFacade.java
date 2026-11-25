package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtScript;

/* JADX INFO: compiled from: LightClassDataProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/ClassFilterForFacade;", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$GenerateClassFilter;", "()V", "shouldAnnotateClass", "", "processingClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "shouldGenerateClass", "shouldGenerateCodeFragment", "script", "Lorg/jetbrains/kotlin/psi/KtCodeFragment;", "shouldGeneratePackagePart", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "shouldGenerateScript", "Lorg/jetbrains/kotlin/psi/KtScript;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassFilterForFacade extends GenerationState.GenerateClassFilter {
    public static final ClassFilterForFacade INSTANCE = new ClassFilterForFacade();

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldGenerateCodeFragment(KtCodeFragment script) {
        Intrinsics.checkNotNullParameter(script, "script");
        return false;
    }

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldGeneratePackagePart(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        return true;
    }

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldGenerateScript(KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        return false;
    }

    private ClassFilterForFacade() {
    }

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldAnnotateClass(KtClassOrObject processingClassOrObject) {
        Intrinsics.checkNotNullParameter(processingClassOrObject, "processingClassOrObject");
        return shouldGenerateClass(processingClassOrObject);
    }

    @Override // org.jetbrains.kotlin.codegen.state.GenerationState.GenerateClassFilter
    public boolean shouldGenerateClass(KtClassOrObject processingClassOrObject) {
        Intrinsics.checkNotNullParameter(processingClassOrObject, "processingClassOrObject");
        return KtPsiUtil.isLocal(processingClassOrObject);
    }
}
