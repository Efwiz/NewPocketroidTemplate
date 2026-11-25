package org.jetbrains.kotlin.fir.analysis.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirJvmAnnotationHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u0000\u001a\u00020\u0001\"\f\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u0004*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"isCompiledToJvmDefault", "", "D", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "checkers.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirJvmAnnotationHelperKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final <D extends FirDeclaration & FirAnnotationContainer> boolean isCompiledToJvmDefault(FirBasedSymbol<? extends D> firBasedSymbol, FirSession session, JvmDefaultMode jvmDefaultMode) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        if (FirAnnotationUtilsKt.getAnnotationByClassId(firBasedSymbol, StandardClassIds.Annotations.INSTANCE.getJvmDefault(), session) != null) {
            return true;
        }
        FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firBasedSymbol, session);
        if (!(containingClassSymbol instanceof FirRegularClassSymbol) || containingClassSymbol.getOrigin().getFromSource()) {
            return jvmDefaultMode.getForAllMethodsWithBody();
        }
        return Intrinsics.areEqual((Object) ClassMembersKt.isNewPlaceForBodyGeneration((FirRegularClass) ((FirRegularClassSymbol) containingClassSymbol).getFir()), (Object) true);
    }
}
