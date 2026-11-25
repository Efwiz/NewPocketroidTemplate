package org.jetbrains.kotlin.psi2ir.generators;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;

/* JADX INFO: compiled from: Generator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a?\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0001*\u00020\u0003*\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00062\u0006\u0010\u0007\u001a\u0002H\u0002H\u0000¢\u0006\u0002\u0010\b\u001a\u0016\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u000bH\u0000\u001a\u0014\u0010\f\u001a\u00020\n*\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u000bH\u0000\u001a=\u0010\r\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0001*\u00020\u0003*\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00062\u0006\u0010\u0007\u001a\u0002H\u0002H\u0000¢\u0006\u0002\u0010\b\u001aU\u0010\r\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0001*\u00020\u0003*\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00062\u0006\u0010\u0007\u001a\u0002H\u00022\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00100\u000fH\u0080\bø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a\u001e\u0010\u0012\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0014\u0018\u00010\u0013*\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0015H\u0000\u001a\u0016\u0010\u0016\u001a\u0004\u0018\u00010\n*\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u000bH\u0000\u001a\u0014\u0010\u0017\u001a\u00020\n*\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u000bH\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0018"}, d2 = {"get", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "", "Lorg/jetbrains/kotlin/psi2ir/generators/Generator;", "slice", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/psi2ir/generators/Generator;Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;Ljava/lang/Object;)Ljava/lang/Object;", "getExpressionTypeWithCoercionToUnit", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getExpressionTypeWithCoercionToUnitOrFail", "getOrFail", CrashHianalyticsData.MESSAGE, "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/psi2ir/generators/Generator;Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getResolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/psi/KtElement;", "getTypeInferredByFrontend", "getTypeInferredByFrontendOrFail", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GeneratorKt {
    public static final <K, V> V get(Generator generator, ReadOnlySlice<K, V> slice, K k) {
        Intrinsics.checkNotNullParameter(generator, "<this>");
        Intrinsics.checkNotNullParameter(slice, "slice");
        return (V) generator.getContext().getBindingContext().get(slice, k);
    }

    public static final <K, V> V getOrFail(Generator generator, ReadOnlySlice<K, V> slice, K k) {
        Intrinsics.checkNotNullParameter(generator, "<this>");
        Intrinsics.checkNotNullParameter(slice, "slice");
        V v = (V) generator.getContext().getBindingContext().get(slice, k);
        if (v != null) {
            return v;
        }
        throw new RuntimeException("No " + slice + " for " + k);
    }

    public static final <K, V> V getOrFail(Generator generator, ReadOnlySlice<K, V> slice, K k, Function1<? super K, String> message) {
        Intrinsics.checkNotNullParameter(generator, "<this>");
        Intrinsics.checkNotNullParameter(slice, "slice");
        Intrinsics.checkNotNullParameter(message, "message");
        V v = (V) generator.getContext().getBindingContext().get(slice, k);
        if (v != null) {
            return v;
        }
        throw new RuntimeException(message.invoke(k));
    }

    public static final KotlinType getTypeInferredByFrontend(Generator generator, KtExpression key) {
        Intrinsics.checkNotNullParameter(generator, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return generator.getContext().getBindingContext().getType(key);
    }

    public static final KotlinType getTypeInferredByFrontendOrFail(Generator generator, KtExpression key) {
        Intrinsics.checkNotNullParameter(generator, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        KotlinType typeInferredByFrontend = getTypeInferredByFrontend(generator, key);
        if (typeInferredByFrontend != null) {
            return typeInferredByFrontend;
        }
        throw new RuntimeException("No type for expression: " + key.getText());
    }

    public static final KotlinType getExpressionTypeWithCoercionToUnit(Generator generator, KtExpression key) {
        Intrinsics.checkNotNullParameter(generator, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        if (BindingContextUtilsKt.isUsedAsExpression(key, generator.getContext().getBindingContext())) {
            return getTypeInferredByFrontend(generator, key);
        }
        IrBuiltIns irBuiltIns = generator.getContext().getIrBuiltIns();
        Intrinsics.checkNotNull(irBuiltIns, "null cannot be cast to non-null type org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors");
        return ((IrBuiltInsOverDescriptors) irBuiltIns).getUnit();
    }

    public static final KotlinType getExpressionTypeWithCoercionToUnitOrFail(Generator generator, KtExpression key) {
        Intrinsics.checkNotNullParameter(generator, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        KotlinType expressionTypeWithCoercionToUnit = getExpressionTypeWithCoercionToUnit(generator, key);
        if (expressionTypeWithCoercionToUnit != null) {
            return expressionTypeWithCoercionToUnit;
        }
        throw new RuntimeException("No type for expression: " + key.getText());
    }

    public static final ResolvedCall<? extends CallableDescriptor> getResolvedCall(Generator generator, KtElement key) {
        Intrinsics.checkNotNullParameter(generator, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return CallUtilKt.getResolvedCall(key, generator.getContext().getBindingContext());
    }
}
