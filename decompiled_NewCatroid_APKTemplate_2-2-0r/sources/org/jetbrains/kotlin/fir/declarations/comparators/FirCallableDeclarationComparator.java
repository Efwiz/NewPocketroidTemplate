package org.jetbrains.kotlin.fir.declarations.comparators;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.types.FirTypeRefComparator;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: FirCallableDeclarationComparator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/comparators/FirCallableDeclarationComparator;", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lkotlin/Comparator;", "()V", "compare", "", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCallableDeclarationComparator implements Comparator<FirCallableDeclaration> {
    public static final FirCallableDeclarationComparator INSTANCE = new FirCallableDeclarationComparator();

    private FirCallableDeclarationComparator() {
    }

    @Override // java.util.Comparator
    public int compare(FirCallableDeclaration a, FirCallableDeclaration b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        int iCompare2 = FirMemberDeclarationComparator.TypeAndNameComparator.INSTANCE.compare((FirMemberDeclaration) a, (FirMemberDeclaration) b);
        if (iCompare2 != 0) {
            return iCompare2;
        }
        FirReceiverParameter receiverParameter = a.getReceiverParameter();
        FirReceiverParameter receiverParameter2 = b.getReceiverParameter();
        if (receiverParameter != null || receiverParameter2 != null) {
            int i = (receiverParameter != null ? 1 : 0) - (receiverParameter2 == null ? 0 : 1);
            if (i != 0) {
                return i;
            }
        }
        int iCompare = FirTypeRefComparator.INSTANCE.compare(a.getReturnTypeRef(), b.getReturnTypeRef());
        if (iCompare != 0) {
            return iCompare;
        }
        if (a instanceof FirFunction) {
            if (!(b instanceof FirFunction)) {
                throw new IllegalArgumentException(("TypeAndNameComparator is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            FirFunction firFunction = (FirFunction) a;
            FirFunction firFunction2 = (FirFunction) b;
            int size = firFunction.getValueParameters().size() - firFunction2.getValueParameters().size();
            if (size != 0) {
                return size;
            }
            for (Pair pair : CollectionsKt.zip(firFunction.getValueParameters(), firFunction2.getValueParameters())) {
                int iCompare3 = FirValueParameterComparator.INSTANCE.compare((FirValueParameter) pair.component1(), (FirValueParameter) pair.component2());
                if (iCompare3 != 0) {
                    return iCompare3;
                }
            }
        }
        int size2 = a.getTypeParameters().size() - b.getTypeParameters().size();
        if (size2 != 0) {
            return size2;
        }
        for (Pair pair2 : CollectionsKt.zip(a.getTypeParameters(), b.getTypeParameters())) {
            int iCompare4 = FirTypeParameterRefComparator.INSTANCE.compare((FirTypeParameterRef) pair2.component1(), (FirTypeParameterRef) pair2.component2());
            if (iCompare4 != 0) {
                return iCompare4;
            }
        }
        String strAsString = a.getSymbol().getCallableId().getPackageName().asString();
        String strAsString2 = b.getSymbol().getCallableId().getPackageName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        return strAsString.compareTo(strAsString2);
    }
}
