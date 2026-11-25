package org.jetbrains.kotlin.backend.common.serialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: DeclarationTable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"knownBuiltins", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getKnownBuiltins", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;)Ljava/util/List;", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationTableKt {
    public static final List<IrDeclaration> getKnownBuiltins(IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "<this>");
        return irBuiltIns.getOperatorsPackageFragment().getDeclarations();
    }
}
