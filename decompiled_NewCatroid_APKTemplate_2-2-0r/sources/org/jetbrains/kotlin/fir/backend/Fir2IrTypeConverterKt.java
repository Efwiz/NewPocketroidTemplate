package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: Fir2IrTypeConverter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "typeConverter", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrTypeConverter;", "typeOrigin", "Lorg/jetbrains/kotlin/fir/backend/ConversionTypeOrigin;", "fir2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrTypeConverterKt {
    public static /* synthetic */ IrType toIrType$default(FirTypeRef firTypeRef, Fir2IrTypeConverter fir2IrTypeConverter, ConversionTypeOrigin conversionTypeOrigin, int i, Object obj) {
        if ((i & 2) != 0) {
            conversionTypeOrigin = ConversionTypeOrigin.DEFAULT;
        }
        return toIrType(firTypeRef, fir2IrTypeConverter, conversionTypeOrigin);
    }

    public static final IrType toIrType(FirTypeRef firTypeRef, Fir2IrTypeConverter typeConverter, ConversionTypeOrigin typeOrigin) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(typeConverter, "typeConverter");
        Intrinsics.checkNotNullParameter(typeOrigin, "typeOrigin");
        return typeConverter.toIrType(firTypeRef, typeOrigin);
    }
}
