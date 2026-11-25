package org.jetbrains.kotlin.fir.expressions.builder;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirConstExpressionImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBooleanTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitByteTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitCharTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitDoubleTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitFloatTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitIntTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitLongTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitNullableAnyTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitShortTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitStringTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUByteTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUIntTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitULongTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUShortTypeRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.ConstantValueKind;

/* JADX INFO: compiled from: FirConstExpressionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001aO\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00062\u0006\u0010\u0007\u001a\u0002H\u00022\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"buildConstExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", ExifInterface.GPS_DIRECTION_TRUE, FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "value", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "setType", "", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/types/ConstantValueKind;Ljava/lang/Object;Ljava/util/List;Z)Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirConstExpressionBuilderKt {
    public static /* synthetic */ FirConstExpression buildConstExpression$default(KtSourceElement ktSourceElement, ConstantValueKind constantValueKind, Object obj, List list, boolean z, int i, Object obj2) {
        if ((i & 8) != 0) {
            list = new ArrayList();
        }
        return buildConstExpression(ktSourceElement, constantValueKind, obj, list, z);
    }

    public static final <T> FirConstExpression<T> buildConstExpression(KtSourceElement ktSourceElement, ConstantValueKind<T> kind, T t, List<FirAnnotation> annotations, boolean z) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        FirConstExpressionImpl firConstExpressionImpl = new FirConstExpressionImpl(ktSourceElement, FirBuilderDslKt.toMutableOrEmpty(annotations), kind, t, null);
        if (z) {
            if (Intrinsics.areEqual(kind, ConstantValueKind.Boolean.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitBooleanTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.Byte.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitByteTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.Char.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitCharTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.Double.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitDoubleTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.Float.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitFloatTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.Int.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitIntTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.Long.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitLongTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.Null.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitNullableAnyTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.Short.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitShortTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.String.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitStringTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.UnsignedByte.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitUByteTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.UnsignedInt.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitUIntTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.UnsignedLong.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitULongTypeRef(null));
            } else if (Intrinsics.areEqual(kind, ConstantValueKind.UnsignedShort.INSTANCE)) {
                firConstExpressionImpl.setTypeRef(new FirImplicitUShortTypeRef(null));
            } else {
                if (!(Intrinsics.areEqual(kind, ConstantValueKind.IntegerLiteral.INSTANCE) ? true : Intrinsics.areEqual(kind, ConstantValueKind.UnsignedIntegerLiteral.INSTANCE))) {
                    Intrinsics.areEqual(kind, ConstantValueKind.Error.INSTANCE);
                }
            }
        }
        return firConstExpressionImpl;
    }
}
