package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;

/* JADX INFO: compiled from: ExportModelGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ExportModelGenerator$exportEnumClass$enumEntriesToOrdinal$1 extends FunctionReference implements Function1<IrEnumEntry, Integer> {
    ExportModelGenerator$exportEnumClass$enumEntriesToOrdinal$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "indexOf(Ljava/lang/Object;)I";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "indexOf";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(List.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Integer invoke(IrEnumEntry p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Integer.valueOf(((List) this.receiver).indexOf(p0));
    }
}
