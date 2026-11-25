package org.jetbrains.kotlin.fir.references;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;

/* JADX INFO: compiled from: FirReferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001b\u0010\u0005\u001a\u00020\u0006*\u00020\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0000\u001a\u001a\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\t*\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u001a\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f*\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u001a\u0010\u0011\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0012*\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u0016*\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a3\u0010\u0017\u001a\t\u0018\u0001H\u0018¢\u0006\u0002\b\u0019\"\u000e\b\u0000\u0010\u0018\u0018\u0001*\u0006\u0012\u0002\b\u00030\t*\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006H\u0086\b¢\u0006\u0002\u0010\u001a\u001a\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u001e*\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006\u001a\u001a\u0010\u001f\u001a\b\u0012\u0002\b\u0003\u0018\u00010 *\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u0006\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004ò\u0001\u0004\n\u00020\u0007¨\u0006!"}, d2 = {"resolved", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "getResolved", "(Lorg/jetbrains/kotlin/fir/references/FirReference;)Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "isError", "", "Lorg/jetbrains/kotlin/fir/diagnostics/FirDiagnosticHolder;", "toResolvedBaseSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "discardErrorReference", "toResolvedCallableSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "toResolvedConstructorSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "toResolvedEnumEntrySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirEnumEntrySymbol;", "toResolvedFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "toResolvedNamedFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "toResolvedPropertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "toResolvedSymbol", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/internal/NoInfer;", "(Lorg/jetbrains/kotlin/fir/references/FirReference;Z)Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "toResolvedTypeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "toResolvedValueParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "toResolvedVariableSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirReferenceUtilsKt {
    public static final FirResolvedNamedReference getResolved(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        if (firReference instanceof FirResolvedNamedReference) {
            return (FirResolvedNamedReference) firReference;
        }
        return null;
    }

    public static /* synthetic */ FirBasedSymbol toResolvedSymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        if (resolved == null) {
            return null;
        }
        if (z && (resolved instanceof FirResolvedErrorReference)) {
            return null;
        }
        FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return resolvedSymbol;
    }

    public static final /* synthetic */ <T extends FirBasedSymbol<?>> T toResolvedSymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        if (resolved == null) {
            return null;
        }
        if (z && (resolved instanceof FirResolvedErrorReference)) {
            return null;
        }
        T t = (T) resolved.getResolvedSymbol();
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return t;
    }

    public static /* synthetic */ FirBasedSymbol toResolvedBaseSymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toResolvedBaseSymbol(firReference, z);
    }

    public static /* synthetic */ FirCallableSymbol toResolvedCallableSymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toResolvedCallableSymbol(firReference, z);
    }

    public static /* synthetic */ FirTypeParameterSymbol toResolvedTypeParameterSymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toResolvedTypeParameterSymbol(firReference, z);
    }

    public static /* synthetic */ FirVariableSymbol toResolvedVariableSymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toResolvedVariableSymbol(firReference, z);
    }

    public static /* synthetic */ FirPropertySymbol toResolvedPropertySymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toResolvedPropertySymbol(firReference, z);
    }

    public static /* synthetic */ FirValueParameterSymbol toResolvedValueParameterSymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toResolvedValueParameterSymbol(firReference, z);
    }

    public static /* synthetic */ FirFunctionSymbol toResolvedFunctionSymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toResolvedFunctionSymbol(firReference, z);
    }

    public static /* synthetic */ FirNamedFunctionSymbol toResolvedNamedFunctionSymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toResolvedNamedFunctionSymbol(firReference, z);
    }

    public static /* synthetic */ FirConstructorSymbol toResolvedConstructorSymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toResolvedConstructorSymbol(firReference, z);
    }

    public static /* synthetic */ FirEnumEntrySymbol toResolvedEnumEntrySymbol$default(FirReference firReference, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return toResolvedEnumEntrySymbol(firReference, z);
    }

    public static final boolean isError(FirReference firReference) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        if (firReference instanceof FirResolvedErrorReference) {
            return true;
        }
        return firReference instanceof FirErrorNamedReference;
    }

    public static final FirBasedSymbol<?> toResolvedBaseSymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        if (resolved == null) {
            return null;
        }
        if (z && (resolved instanceof FirResolvedErrorReference)) {
            return null;
        }
        FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
        if (resolvedSymbol instanceof FirBasedSymbol) {
            return resolvedSymbol;
        }
        return null;
    }

    public static final FirCallableSymbol<?> toResolvedCallableSymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        FirCallableSymbol firCallableSymbol = null;
        if (resolved != null && (!z || !(resolved instanceof FirResolvedErrorReference))) {
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            firCallableSymbol = (FirCallableSymbol) (resolvedSymbol instanceof FirCallableSymbol ? resolvedSymbol : null);
        }
        return (FirCallableSymbol) firCallableSymbol;
    }

    public static final FirTypeParameterSymbol toResolvedTypeParameterSymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        FirTypeParameterSymbol firTypeParameterSymbol = null;
        if (resolved != null && (!z || !(resolved instanceof FirResolvedErrorReference))) {
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            firTypeParameterSymbol = (FirTypeParameterSymbol) (resolvedSymbol instanceof FirTypeParameterSymbol ? resolvedSymbol : null);
        }
        return (FirTypeParameterSymbol) firTypeParameterSymbol;
    }

    public static final FirVariableSymbol<?> toResolvedVariableSymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        FirVariableSymbol firVariableSymbol = null;
        if (resolved != null && (!z || !(resolved instanceof FirResolvedErrorReference))) {
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            firVariableSymbol = (FirVariableSymbol) (resolvedSymbol instanceof FirVariableSymbol ? resolvedSymbol : null);
        }
        return (FirVariableSymbol) firVariableSymbol;
    }

    public static final FirPropertySymbol toResolvedPropertySymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        FirPropertySymbol firPropertySymbol = null;
        if (resolved != null && (!z || !(resolved instanceof FirResolvedErrorReference))) {
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            firPropertySymbol = (FirPropertySymbol) (resolvedSymbol instanceof FirPropertySymbol ? resolvedSymbol : null);
        }
        return (FirPropertySymbol) firPropertySymbol;
    }

    public static final FirValueParameterSymbol toResolvedValueParameterSymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        FirValueParameterSymbol firValueParameterSymbol = null;
        if (resolved != null && (!z || !(resolved instanceof FirResolvedErrorReference))) {
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            firValueParameterSymbol = (FirValueParameterSymbol) (resolvedSymbol instanceof FirValueParameterSymbol ? resolvedSymbol : null);
        }
        return (FirValueParameterSymbol) firValueParameterSymbol;
    }

    public static final FirFunctionSymbol<?> toResolvedFunctionSymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        FirFunctionSymbol firFunctionSymbol = null;
        if (resolved != null && (!z || !(resolved instanceof FirResolvedErrorReference))) {
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            firFunctionSymbol = (FirFunctionSymbol) (resolvedSymbol instanceof FirFunctionSymbol ? resolvedSymbol : null);
        }
        return (FirFunctionSymbol) firFunctionSymbol;
    }

    public static final FirNamedFunctionSymbol toResolvedNamedFunctionSymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        FirNamedFunctionSymbol firNamedFunctionSymbol = null;
        if (resolved != null && (!z || !(resolved instanceof FirResolvedErrorReference))) {
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            firNamedFunctionSymbol = (FirNamedFunctionSymbol) (resolvedSymbol instanceof FirNamedFunctionSymbol ? resolvedSymbol : null);
        }
        return (FirNamedFunctionSymbol) firNamedFunctionSymbol;
    }

    public static final FirConstructorSymbol toResolvedConstructorSymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        FirConstructorSymbol firConstructorSymbol = null;
        if (resolved != null && (!z || !(resolved instanceof FirResolvedErrorReference))) {
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            firConstructorSymbol = (FirConstructorSymbol) (resolvedSymbol instanceof FirConstructorSymbol ? resolvedSymbol : null);
        }
        return (FirConstructorSymbol) firConstructorSymbol;
    }

    public static final FirEnumEntrySymbol toResolvedEnumEntrySymbol(FirReference firReference, boolean z) {
        Intrinsics.checkNotNullParameter(firReference, "<this>");
        FirResolvedNamedReference resolved = getResolved(firReference);
        FirEnumEntrySymbol firEnumEntrySymbol = null;
        if (resolved != null && (!z || !(resolved instanceof FirResolvedErrorReference))) {
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            firEnumEntrySymbol = (FirEnumEntrySymbol) (resolvedSymbol instanceof FirEnumEntrySymbol ? resolvedSymbol : null);
        }
        return (FirEnumEntrySymbol) firEnumEntrySymbol;
    }
}
