package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirResolvePhase.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0004"}, d2 = {"isBodyResolve", "", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)Z", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirResolvePhaseKt {

    /* JADX INFO: compiled from: FirResolvePhase.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FirResolvePhase.values().length];
            try {
                iArr[FirResolvePhase.BODY_RESOLVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirResolvePhase.IMPLICIT_TYPES_BODY_RESOLVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean isBodyResolve(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[firResolvePhase.ordinal()];
        return i == 1 || i == 2;
    }
}
