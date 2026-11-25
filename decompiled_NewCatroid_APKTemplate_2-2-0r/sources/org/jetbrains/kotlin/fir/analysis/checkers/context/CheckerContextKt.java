package org.jetbrains.kotlin.fir.analysis.checkers.context;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;

/* JADX INFO: compiled from: CheckerContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a9\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00060\u0005H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0007\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\b"}, d2 = {"findClosest", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/FirElement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "check", "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/FirElement;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CheckerContextKt {
    public static /* synthetic */ FirElement findClosest$default(CheckerContext checkerContext, Function1 check, int i, Object obj) {
        FirDeclaration firDeclaration;
        if ((i & 1) != 0) {
            Intrinsics.needClassReification();
            check = AnonymousClass1.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(checkerContext, "<this>");
        Intrinsics.checkNotNullParameter(check, "check");
        Iterator it = CollectionsKt.asReversed(checkerContext.getContainingDeclarations()).iterator();
        while (true) {
            firDeclaration = null;
            if (!it.hasNext()) {
                break;
            }
            FirDeclaration firDeclaration2 = (FirDeclaration) it.next();
            Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
            FirDeclaration firDeclaration3 = firDeclaration2;
            if (firDeclaration3 != null) {
                firDeclaration = ((Boolean) check.invoke(firDeclaration3)).booleanValue() ? firDeclaration3 : null;
                if (firDeclaration != null) {
                    break;
                }
            }
        }
        return firDeclaration;
    }

    public static final /* synthetic */ <T extends FirElement> T findClosest(CheckerContext checkerContext, Function1<? super T, Boolean> check) {
        FirDeclaration firDeclaration;
        Intrinsics.checkNotNullParameter(checkerContext, "<this>");
        Intrinsics.checkNotNullParameter(check, "check");
        Iterator it = CollectionsKt.asReversed(checkerContext.getContainingDeclarations()).iterator();
        while (true) {
            firDeclaration = null;
            if (!it.hasNext()) {
                break;
            }
            FirDeclaration firDeclaration2 = (FirDeclaration) it.next();
            Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
            FirDeclaration firDeclaration3 = firDeclaration2;
            if (firDeclaration3 != null) {
                firDeclaration = check.invoke(firDeclaration3).booleanValue() ? firDeclaration3 : null;
                if (firDeclaration != null) {
                    break;
                }
            }
        }
        return firDeclaration;
    }
}
