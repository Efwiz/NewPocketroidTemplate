package org.jetbrains.kotlin.cli.js.klib;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.linkage.issues.ChecksKt;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: irForKlib.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class IrForKlibKt$generateIrForKlibSerialization$psi2Ir$1 extends FunctionReference implements Function2<SymbolTable, String, Unit> {
    IrForKlibKt$generateIrForKlibSerialization$psi2Ir$1(Object obj) {
        super(2, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "checkNoUnboundSymbols(Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Ljava/lang/String;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "checkNoUnboundSymbols";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(ChecksKt.class, "cli-js");
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(SymbolTable symbolTable, String str) {
        invoke2(symbolTable, str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(SymbolTable p0, String p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        ChecksKt.checkNoUnboundSymbols((IrMessageLogger) this.receiver, p0, p1);
    }
}
