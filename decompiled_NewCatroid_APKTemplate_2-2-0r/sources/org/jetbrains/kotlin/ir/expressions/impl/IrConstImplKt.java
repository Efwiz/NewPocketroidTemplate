package org.jetbrains.kotlin.ir.expressions.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrConst;

/* JADX INFO: compiled from: IrConstImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a,\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"copyWithOffsets", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "startOffset", "", "endOffset", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrConstImplKt {
    public static final <T> IrConstImpl<T> copyWithOffsets(IrConst<T> irConst, int i, int i2) {
        Intrinsics.checkNotNullParameter(irConst, "<this>");
        return new IrConstImpl<>(i, i2, irConst.getType(), irConst.getKind(), irConst.getValue());
    }
}
