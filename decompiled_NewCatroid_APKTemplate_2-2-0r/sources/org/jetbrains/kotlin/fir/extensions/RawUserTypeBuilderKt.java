package org.jetbrains.kotlin.fir.extensions;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirUserTypeRefBuilder;

/* JADX INFO: compiled from: RawUserTypeBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\b\b¨\u0006\t"}, d2 = {"buildUserTypeFromQualifierParts", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "isMarkedNullable", "", "builder", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/extensions/QualifierPartBuilder;", "", "Lkotlin/ExtensionFunctionType;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RawUserTypeBuilderKt {
    public static final FirUserTypeRef buildUserTypeFromQualifierParts(boolean z, Function1<? super QualifierPartBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        FirUserTypeRefBuilder firUserTypeRefBuilder = new FirUserTypeRefBuilder();
        firUserTypeRefBuilder.setMarkedNullable(z);
        builder.invoke(new QualifierPartBuilder(firUserTypeRefBuilder.getQualifier()));
        return firUserTypeRefBuilder.build();
    }
}
