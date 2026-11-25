package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowUtilsKt;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactoryImpl;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ieee754.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t\u001a*\u0010\n\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e¨\u0006\u000f"}, d2 = {"calcProperTypeForIeee754ArithmeticIfNeeded", "Lorg/jetbrains/kotlin/codegen/TypeAndNullability;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "inferredPrimitiveType", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "legacyCalcTypeForIeee754ArithmeticIfNeeded", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class Ieee754Kt {
    public static final TypeAndNullability calcProperTypeForIeee754ArithmeticIfNeeded(KtExpression expression, BindingContext bindingContext, KotlinType kotlinType, KotlinTypeMapper typeMapper) {
        KotlinType kotlinTypeForComparison;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        if (kotlinType == null || (kotlinTypeForComparison = DataFlowUtilsKt.getKotlinTypeForComparison(expression, bindingContext)) == null) {
            return null;
        }
        boolean zIsNullableType = TypeUtils.isNullableType(kotlinTypeForComparison);
        Type typeMapType$default = KotlinTypeMapper.mapType$default(typeMapper, kotlinType, null, null, 6, null);
        if (AsmUtil.isPrimitive(typeMapType$default)) {
            return new TypeAndNullability(typeMapType$default, zIsNullableType);
        }
        return null;
    }

    public static final TypeAndNullability legacyCalcTypeForIeee754ArithmeticIfNeeded(KtExpression ktExpression, BindingContext bindingContext, DeclarationDescriptor descriptor, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        KotlinType kotlinTypeWithPossibleSmartCastToFP$default = DataFlowUtilsKt.getKotlinTypeWithPossibleSmartCastToFP$default(ktExpression, bindingContext, descriptor, languageVersionSettings, new DataFlowValueFactoryImpl(languageVersionSettings), null, 16, null);
        if (kotlinTypeWithPossibleSmartCastToFP$default == null) {
            return null;
        }
        if (KotlinBuiltIns.isDoubleOrNullableDouble(kotlinTypeWithPossibleSmartCastToFP$default)) {
            Type DOUBLE_TYPE = Type.DOUBLE_TYPE;
            Intrinsics.checkNotNullExpressionValue(DOUBLE_TYPE, "DOUBLE_TYPE");
            return new TypeAndNullability(DOUBLE_TYPE, TypeUtils.isNullableType(kotlinTypeWithPossibleSmartCastToFP$default));
        }
        if (!KotlinBuiltIns.isFloatOrNullableFloat(kotlinTypeWithPossibleSmartCastToFP$default)) {
            return null;
        }
        Type FLOAT_TYPE = Type.FLOAT_TYPE;
        Intrinsics.checkNotNullExpressionValue(FLOAT_TYPE, "FLOAT_TYPE");
        return new TypeAndNullability(FLOAT_TYPE, TypeUtils.isNullableType(kotlinTypeWithPossibleSmartCastToFP$default));
    }
}
