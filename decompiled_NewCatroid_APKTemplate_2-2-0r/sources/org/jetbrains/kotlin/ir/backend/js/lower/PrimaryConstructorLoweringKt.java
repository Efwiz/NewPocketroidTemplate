package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: PrimaryConstructorLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0004"}, d2 = {"isSyntheticPrimaryConstructor", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PrimaryConstructorLoweringKt {
    public static final boolean isSyntheticPrimaryConstructor(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return Intrinsics.areEqual(irDeclaration.getOrigin(), PrimaryConstructorLowering.SYNTHETIC_PRIMARY_CONSTRUCTOR.INSTANCE);
    }
}
