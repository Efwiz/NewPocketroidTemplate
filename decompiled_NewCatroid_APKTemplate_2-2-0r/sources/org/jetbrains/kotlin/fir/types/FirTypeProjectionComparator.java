package org.jetbrains.kotlin.fir.types;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: FirTypeProjectionComparator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0002H\u0016R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionComparator;", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "Lkotlin/Comparator;", "()V", "priority", "", "getPriority", "(Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;)I", "compare", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeProjectionComparator implements Comparator<FirTypeProjection> {
    public static final FirTypeProjectionComparator INSTANCE = new FirTypeProjectionComparator();

    private FirTypeProjectionComparator() {
    }

    private final int getPriority(FirTypeProjection firTypeProjection) {
        if (firTypeProjection instanceof FirTypeProjectionWithVariance) {
            return 2;
        }
        return firTypeProjection instanceof FirStarProjection ? 1 : 0;
    }

    @Override // java.util.Comparator
    public int compare(FirTypeProjection a, FirTypeProjection b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        int priority = getPriority(a) - getPriority(b);
        if (priority != 0) {
            return priority;
        }
        if (a instanceof FirTypeProjectionWithVariance) {
            if (!(b instanceof FirTypeProjectionWithVariance)) {
                throw new IllegalArgumentException(("priority is inconsistent: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
            }
            FirTypeProjectionWithVariance firTypeProjectionWithVariance = (FirTypeProjectionWithVariance) a;
            FirTypeProjectionWithVariance firTypeProjectionWithVariance2 = (FirTypeProjectionWithVariance) b;
            int iCompare2 = FirTypeRefComparator.INSTANCE.compare(firTypeProjectionWithVariance.getTypeRef(), firTypeProjectionWithVariance2.getTypeRef());
            return iCompare2 != 0 ? iCompare2 : firTypeProjectionWithVariance.getVariance().ordinal() - firTypeProjectionWithVariance2.getVariance().ordinal();
        }
        if (a instanceof FirStarProjection) {
            return 0;
        }
        throw new IllegalStateException(("Unsupported type projection comparison: " + UtilsKt.render(a) + " v.s. " + UtilsKt.render(b)).toString());
    }
}
