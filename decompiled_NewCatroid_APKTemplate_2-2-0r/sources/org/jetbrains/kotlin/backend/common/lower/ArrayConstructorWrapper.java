package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;

/* JADX INFO: compiled from: ArrayConstructorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ArrayConstructorWrapper;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ArrayConstructorWrapper extends IrDeclarationOriginImpl {
    public static final ArrayConstructorWrapper INSTANCE = new ArrayConstructorWrapper();

    private ArrayConstructorWrapper() {
        super("arrayConstructorWrapper", false, 2, null);
    }
}
