package org.jetbrains.kotlin.fir.declarations.comparators;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.types.FirTypeRefComparator;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: FirValueParameterComparator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/comparators/FirValueParameterComparator;", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lkotlin/Comparator;", "()V", "compare", "", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirValueParameterComparator implements Comparator<FirValueParameter> {
    public static final FirValueParameterComparator INSTANCE = new FirValueParameterComparator();

    private FirValueParameterComparator() {
    }

    @Override // java.util.Comparator
    public int compare(FirValueParameter a, FirValueParameter b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        int iCompareTo = a.getName().compareTo(b.getName());
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        int iCompare = FirTypeRefComparator.INSTANCE.compare(a.getReturnTypeRef(), b.getReturnTypeRef());
        if (iCompare != 0) {
            return iCompare;
        }
        int i = (a.getDefaultValue() != null ? 1 : 0) - (b.getDefaultValue() == null ? 0 : 1);
        if (i != 0) {
            return i;
        }
        return (a.getIsVararg() ? 1 : 0) - (b.getIsVararg() ? 1 : 0);
    }
}
