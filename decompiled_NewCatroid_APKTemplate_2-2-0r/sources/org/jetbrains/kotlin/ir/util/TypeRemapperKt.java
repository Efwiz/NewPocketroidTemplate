package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;

/* JADX INFO: compiled from: TypeRemapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0006H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0007\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\b"}, d2 = {"withinScope", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "irTypeParametersContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "fn", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/ir/util/TypeRemapper;Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeRemapperKt {
    public static final <T> T withinScope(TypeRemapper typeRemapper, IrTypeParametersContainer irTypeParametersContainer, Function0<? extends T> fn) {
        Intrinsics.checkNotNullParameter(typeRemapper, "<this>");
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "irTypeParametersContainer");
        Intrinsics.checkNotNullParameter(fn, "fn");
        typeRemapper.enterScope(irTypeParametersContainer);
        T tInvoke = fn.invoke();
        typeRemapper.leaveScope();
        return tInvoke;
    }
}
