package org.jetbrains.kotlin.fir.types.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;

/* JADX INFO: compiled from: FirErrorTypeRefBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildErrorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/types/builder/FirErrorTypeRefBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildErrorTypeRefCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirErrorTypeRefBuilderKt {
    public static final FirErrorTypeRef buildErrorTypeRef(Function1<? super FirErrorTypeRefBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
        init.invoke(firErrorTypeRefBuilder);
        return firErrorTypeRefBuilder.build();
    }

    public static final FirErrorTypeRef buildErrorTypeRefCopy(FirErrorTypeRef original, Function1<? super FirErrorTypeRefBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder.setSource(original.getSource());
        firErrorTypeRefBuilder.setType(original.getType());
        firErrorTypeRefBuilder.setDelegatedTypeRef(original.getDelegatedTypeRef());
        firErrorTypeRefBuilder.setDiagnostic(original.getDiagnostic());
        init.invoke(firErrorTypeRefBuilder);
        return firErrorTypeRefBuilder.build();
    }
}
