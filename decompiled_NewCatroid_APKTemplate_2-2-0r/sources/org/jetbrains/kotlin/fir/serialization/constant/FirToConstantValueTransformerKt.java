package org.jetbrains.kotlin.fir.serialization.constant;

import androidx.exifinterface.media.ExifInterface;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirExpression;

/* JADX INFO: compiled from: FirToConstantValueTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0003\u001a\u00020\u0004*\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0000\u001a8\u0010\b\u001a\u0004\u0018\u0001H\t\"\u000e\b\u0000\u0010\t\u0018\u0001*\u0006\u0012\u0002\b\u00030\n*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0080\b¢\u0006\u0002\u0010\r\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"constantIntrinsicCalls", "", "", "hasConstantValue", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "toConstantValue", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/constant/ConstantValue;", "constValueProvider", "Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;)Lorg/jetbrains/kotlin/constant/ConstantValue;", "fir-serialization"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirToConstantValueTransformerKt {
    private static final Set<String> constantIntrinsicCalls = SetsKt.setOf((Object[]) new String[]{"toByte", "toLong", "toShort", "toFloat", "toDouble", "toChar", "unaryMinus"});

    public static /* synthetic */ ConstantValue toConstantValue$default(FirExpression firExpression, FirSession session, ConstValueProvider constValueProvider, int i, Object obj) {
        if ((i & 2) != 0) {
            constValueProvider = null;
        }
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConstantValue<?> constantValueFindConstantValueFor = constValueProvider != null ? constValueProvider.findConstantValueFor(firExpression) : null;
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        if (constantValueFindConstantValueFor != null) {
            return constantValueFindConstantValueFor;
        }
        Object objAccept = firExpression.accept(FirToConstantValueTransformer.INSTANCE, new FirToConstantValueTransformerData(session, constValueProvider));
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return (ConstantValue) objAccept;
    }

    public static final /* synthetic */ <T extends ConstantValue<?>> T toConstantValue(FirExpression firExpression, FirSession session, ConstValueProvider constValueProvider) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        T t = constValueProvider != null ? (T) constValueProvider.findConstantValueFor(firExpression) : null;
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        if (t != null) {
            return t;
        }
        Object objAccept = firExpression.accept(FirToConstantValueTransformer.INSTANCE, new FirToConstantValueTransformerData(session, constValueProvider));
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) objAccept;
    }

    public static final boolean hasConstantValue(FirExpression firExpression, FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        return firExpression != null && ((Boolean) firExpression.accept(FirToConstantValueChecker.INSTANCE, session)).booleanValue();
    }
}
