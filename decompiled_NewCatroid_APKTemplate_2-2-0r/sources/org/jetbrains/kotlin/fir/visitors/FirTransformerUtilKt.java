package org.jetbrains.kotlin.fir.visitors;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirPureAbstractElement;

/* JADX INFO: compiled from: FirTransformerUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aN\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0004\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00072\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u000b0\tH\u0086\bø\u0001\u0000\u001a;\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0004\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00072\u0006\u0010\f\u001a\u0002H\u0004¢\u0006\u0002\u0010\r\u001a@\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0004\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00020\u000e2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00072\u0006\u0010\f\u001a\u0002H\u0004ø\u0001\u0001¢\u0006\u0004\b\u000f\u0010\r\u001a5\u0010\u0010\u001a\u0002H\u0002\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0004\b\u0001\u0010\u0004*\u0002H\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00072\u0006\u0010\f\u001a\u0002H\u0004¢\u0006\u0002\u0010\u0011\u0082\u0002\u000e\n\u0005\b\u009920\u0001\n\u0005\b¡\u001e0\u0001¨\u0006\u0012"}, d2 = {"transformInplace", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/FirElement;", "D", "", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "dataProducer", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/visitors/TransformData;", "data", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)V", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "transformInplace-aLnlfrU", "transformSingle", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirTransformerUtilKt {
    public static final <T extends FirElement, D> T transformSingle(T t, FirTransformer<? super D> transformer, D d) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return (T) ((FirPureAbstractElement) t).transform(transformer, d);
    }

    public static final <T extends FirElement, D> void transformInplace(List<T> list, FirTransformer<? super D> transformer, D d) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        ListIterator<T> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            Object next = listIterator.next();
            Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.FirPureAbstractElement");
            FirPureAbstractElement firPureAbstractElement = (FirPureAbstractElement) next;
            FirElement firElementTransform = firPureAbstractElement.transform(transformer, d);
            if (firElementTransform != firPureAbstractElement) {
                listIterator.set(firElementTransform);
            }
        }
    }

    /* JADX INFO: renamed from: transformInplace-aLnlfrU, reason: not valid java name */
    public static final <T extends FirElement, D> void m7082transformInplaceaLnlfrU(List<T> list, FirTransformer<? super D> transformer, D d) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        if (list != null) {
            transformInplace(list, transformer, d);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T extends FirElement, D> void transformInplace(List<T> list, FirTransformer<? super D> transformer, Function1<? super Integer, ? extends TransformData<? extends D>> dataProducer) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Intrinsics.checkNotNullParameter(dataProducer, "dataProducer");
        ListIterator<T> listIterator = list.listIterator();
        int i = 0;
        while (listIterator.hasNext()) {
            Object next = listIterator.next();
            Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.FirPureAbstractElement");
            FirPureAbstractElement firPureAbstractElement = (FirPureAbstractElement) next;
            int i2 = i + 1;
            TransformData<? extends D> transformDataInvoke = dataProducer.invoke(Integer.valueOf(i));
            if (!(transformDataInvoke instanceof TransformData.Data)) {
                if (!Intrinsics.areEqual(transformDataInvoke, TransformData.Nothing.INSTANCE)) {
                    throw new NoWhenBranchMatchedException();
                }
            } else {
                FirElement firElementTransform = firPureAbstractElement.transform(transformer, ((TransformData.Data) transformDataInvoke).getValue());
                if (firElementTransform != firPureAbstractElement) {
                    listIterator.set(firElementTransform);
                }
            }
            i = i2;
        }
    }
}
