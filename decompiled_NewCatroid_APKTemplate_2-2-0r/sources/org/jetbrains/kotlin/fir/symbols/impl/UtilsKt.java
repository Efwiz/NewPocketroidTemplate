package org.jetbrains.kotlin.fir.symbols.impl;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0000\u001a\f\u0010\b\u001a\u00020\t*\u00020\nH\u0000¨\u0006\u000b"}, d2 = {"errorInLazyResolve", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "name", "", "actualClass", "Lkotlin/reflect/KClass;", "expected", "resolvedStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final Void errorInLazyResolve(FirBasedSymbol<?> firBasedSymbol, String name2, KClass<?> actualClass, KClass<?> expected) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(actualClass, "actualClass");
        Intrinsics.checkNotNullParameter(expected, "expected");
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unexpected " + name2 + ". Expected is " + expected.getSimpleName() + ", but was " + actualClass.getSimpleName(), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "firElement", firBasedSymbol.getFir());
        FirExceptionUtilsKt.withFirSymbolIdEntry(exceptionAttachmentBuilder, "firSymbol", firBasedSymbol);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final FirResolvedDeclarationStatus resolvedStatus(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firMemberDeclaration, FirResolvePhase.STATUS);
        FirDeclarationStatus status = firMemberDeclaration.getStatus();
        if (!(status instanceof FirResolvedDeclarationStatus)) {
            errorInLazyResolve(firMemberDeclaration.getSymbol(), NotificationCompat.CATEGORY_STATUS, Reflection.getOrCreateKotlinClass(status.getClass()), Reflection.getOrCreateKotlinClass(FirResolvedDeclarationStatus.class));
            throw null;
        }
        return (FirResolvedDeclarationStatus) status;
    }
}
