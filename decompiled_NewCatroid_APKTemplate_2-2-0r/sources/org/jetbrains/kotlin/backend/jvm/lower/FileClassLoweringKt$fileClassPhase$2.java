package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: FileClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FileClassLoweringKt$fileClassPhase$2 extends FunctionReference implements Function1<IrModuleFragment, Unit> {
    public static final FileClassLoweringKt$fileClassPhase$2 INSTANCE = new FileClassLoweringKt$fileClassPhase$2();

    FileClassLoweringKt$fileClassPhase$2() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "checkAllFileLevelDeclarationsAreClasses(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "checkAllFileLevelDeclarationsAreClasses";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(FileClassLoweringKt.class, "backend.jvm.lower");
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(IrModuleFragment irModuleFragment) {
        invoke2(irModuleFragment);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(IrModuleFragment p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        FileClassLoweringKt.checkAllFileLevelDeclarationsAreClasses(p0);
    }
}
