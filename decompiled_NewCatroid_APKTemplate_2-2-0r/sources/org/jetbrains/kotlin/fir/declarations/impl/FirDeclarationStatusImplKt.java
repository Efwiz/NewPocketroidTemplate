package org.jetbrains.kotlin.fir.declarations.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;

/* JADX INFO: compiled from: FirDeclarationStatusImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001b\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"modifiersRepresentation", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getModifiersRepresentation$annotations", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)V", "getModifiersRepresentation", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirDeclarationStatusImplKt {
    public static /* synthetic */ void getModifiersRepresentation$annotations(FirDeclarationStatus firDeclarationStatus) {
    }

    public static final Object getModifiersRepresentation(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<this>");
        if (firDeclarationStatus instanceof FirDeclarationStatusImpl) {
            return Integer.valueOf(((FirDeclarationStatusImpl) firDeclarationStatus).getRawFlags$tree());
        }
        throw new IllegalStateException(("Generating modifier representations for " + Reflection.getOrCreateKotlinClass(firDeclarationStatus.getClass()).getSimpleName() + " is not supported").toString());
    }
}
