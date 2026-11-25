package org.jetbrains.kotlin.backend.common.phaser;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;

/* JADX INFO: compiled from: DumperVerifier.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DumperVerifierKt$dumpIrElement$dump$2 extends FunctionReference implements Function1<IrElement, String> {
    public static final DumperVerifierKt$dumpIrElement$dump$2 INSTANCE = new DumperVerifierKt$dumpIrElement$dump$2();

    DumperVerifierKt$dumpIrElement$dump$2() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "dumpIrElement$dump(Lorg/jetbrains/kotlin/ir/IrElement;)Ljava/lang/String;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return ArchiveStreamFactory.DUMP;
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final String invoke(IrElement p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return DumpIrTreeKt.dump$default(p0, null, 1, null);
    }
}
