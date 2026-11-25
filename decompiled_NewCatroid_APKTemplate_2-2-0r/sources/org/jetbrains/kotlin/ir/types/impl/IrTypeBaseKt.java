package org.jetbrains.kotlin.ir.types.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrTypeBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"originalKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getOriginalKotlinType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/types/KotlinType;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTypeBaseKt {
    public static final KotlinType getOriginalKotlinType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrTypeBase irTypeBase = irType instanceof IrTypeBase ? (IrTypeBase) irType : null;
        if (irTypeBase != null) {
            return irTypeBase.getKotlinType();
        }
        return null;
    }
}
