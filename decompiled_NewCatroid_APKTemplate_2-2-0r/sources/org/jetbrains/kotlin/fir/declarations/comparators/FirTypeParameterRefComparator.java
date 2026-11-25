package org.jetbrains.kotlin.fir.declarations.comparators;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirConstructedClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirOuterClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRefComparator;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: FirTypeParameterRefComparator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/comparators/FirTypeParameterRefComparator;", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lkotlin/Comparator;", "()V", "priority", "", "getPriority", "(Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;)I", "compare", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeParameterRefComparator implements Comparator<FirTypeParameterRef> {
    public static final FirTypeParameterRefComparator INSTANCE = new FirTypeParameterRefComparator();

    private FirTypeParameterRefComparator() {
    }

    private final int getPriority(FirTypeParameterRef firTypeParameterRef) {
        if (firTypeParameterRef instanceof FirConstructedClassTypeParameterRef) {
            return 3;
        }
        if (firTypeParameterRef instanceof FirOuterClassTypeParameterRef) {
            return 2;
        }
        return firTypeParameterRef instanceof FirTypeParameter ? 1 : 0;
    }

    @Override // java.util.Comparator
    public int compare(FirTypeParameterRef a, FirTypeParameterRef b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        int priority = getPriority(a) - getPriority(b);
        if (priority != 0) {
            return priority;
        }
        if (a instanceof FirConstructedClassTypeParameterRef) {
            if (!(b instanceof FirConstructedClassTypeParameterRef)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            return a.getSymbol().getName().compareTo(b.getSymbol().getName());
        }
        if (a instanceof FirOuterClassTypeParameterRef) {
            if (!(b instanceof FirOuterClassTypeParameterRef)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            return a.getSymbol().getName().compareTo(b.getSymbol().getName());
        }
        if (a instanceof FirTypeParameter) {
            if (!(b instanceof FirTypeParameter)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            int iCompareTo = a.getSymbol().getName().compareTo(b.getSymbol().getName());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
            FirTypeParameter firTypeParameter = (FirTypeParameter) a;
            FirTypeParameter firTypeParameter2 = (FirTypeParameter) b;
            int iOrdinal = firTypeParameter.getVariance().ordinal() - firTypeParameter2.getVariance().ordinal();
            if (iOrdinal != 0) {
                return iOrdinal;
            }
            int size = firTypeParameter.getBounds().size() - firTypeParameter2.getBounds().size();
            if (size != 0) {
                return size;
            }
            for (Pair pair : CollectionsKt.zip(firTypeParameter.getBounds(), firTypeParameter2.getBounds())) {
                int iCompare = FirTypeRefComparator.INSTANCE.compare((FirTypeRef) pair.component1(), (FirTypeRef) pair.component2());
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            return 0;
        }
        throw new IllegalStateException(("Unsupported type parameter reference comparison: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
    }
}
