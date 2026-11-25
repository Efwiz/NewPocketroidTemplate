package org.jetbrains.kotlin.fir.types;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: FirTypeRefComparator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/FirTypeRefComparator;", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lkotlin/Comparator;", "()V", "priority", "", "getPriority", "(Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)I", "compare", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeRefComparator implements Comparator<FirTypeRef> {
    public static final FirTypeRefComparator INSTANCE = new FirTypeRefComparator();

    private FirTypeRefComparator() {
    }

    private final int getPriority(FirTypeRef firTypeRef) {
        if (firTypeRef instanceof FirUserTypeRef) {
            return 3;
        }
        if (firTypeRef instanceof FirImplicitBuiltinTypeRef) {
            return 2;
        }
        return firTypeRef instanceof FirResolvedTypeRef ? 1 : 0;
    }

    @Override // java.util.Comparator
    public int compare(FirTypeRef a, FirTypeRef b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        int priority = getPriority(a) - getPriority(b);
        if (priority != 0) {
            return priority;
        }
        if (a instanceof FirUserTypeRef) {
            if (!(b instanceof FirUserTypeRef)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            FirUserTypeRef firUserTypeRef = (FirUserTypeRef) a;
            FirUserTypeRef firUserTypeRef2 = (FirUserTypeRef) b;
            int size = firUserTypeRef.getQualifier().size() - firUserTypeRef2.getQualifier().size();
            if (size != 0) {
                return size;
            }
            for (Pair pair : CollectionsKt.zip(firUserTypeRef.getQualifier(), firUserTypeRef2.getQualifier())) {
                FirQualifierPart firQualifierPart = (FirQualifierPart) pair.component1();
                FirQualifierPart firQualifierPart2 = (FirQualifierPart) pair.component2();
                int iCompareTo = firQualifierPart.getName().compareTo(firQualifierPart2.getName());
                if (iCompareTo != 0) {
                    return iCompareTo;
                }
                int size2 = firQualifierPart.getTypeArgumentList().getTypeArguments().size() - firQualifierPart2.getTypeArgumentList().getTypeArguments().size();
                if (size2 != 0) {
                    return size2;
                }
                for (Pair pair2 : CollectionsKt.zip(firQualifierPart.getTypeArgumentList().getTypeArguments(), firQualifierPart2.getTypeArgumentList().getTypeArguments())) {
                    int iCompare = FirTypeProjectionComparator.INSTANCE.compare((FirTypeProjection) pair2.component1(), (FirTypeProjection) pair2.component2());
                    if (iCompare != 0) {
                        return iCompare;
                    }
                }
            }
            return 0;
        }
        if (a instanceof FirImplicitBuiltinTypeRef) {
            if (!(b instanceof FirImplicitBuiltinTypeRef)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            return ((FirImplicitBuiltinTypeRef) a).getId().getShortClassName().compareTo(((FirImplicitBuiltinTypeRef) b).getId().getShortClassName());
        }
        if (a instanceof FirResolvedTypeRef) {
            if (!(b instanceof FirResolvedTypeRef)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            return ConeKotlinTypeComparator.INSTANCE.compare(((FirResolvedTypeRef) a).getType(), ((FirResolvedTypeRef) b).getType());
        }
        throw new IllegalStateException(("Unsupported type reference comparison: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
    }
}
