package org.jetbrains.kotlin.resolve.calls.smartcasts;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DataFlowUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0000\u001a\u0014\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001aR\u0010\u0005\u001a\u0004\u0018\u00010\u0001*\u0004\u0018\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2 \b\u0002\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000e\u0012\u0004\u0012\u00020\u00010\r¨\u0006\u000f"}, d2 = {"getKotlinTypeForComparison", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getKotlinTypeWithPossibleSmartCastToFP", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "defaultType", "Lkotlin/Function2;", "", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DataFlowUtilsKt {
    public static final KotlinType getKotlinTypeForComparison(KtExpression ktExpression, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        if (!(ktExpression instanceof KtProperty)) {
            return bindingContext.getType(ktExpression);
        }
        VariableDescriptor variableDescriptor = (VariableDescriptor) bindingContext.get(BindingContext.VARIABLE, ktExpression);
        if (variableDescriptor != null) {
            return variableDescriptor.getType();
        }
        return null;
    }

    public static /* synthetic */ KotlinType getKotlinTypeWithPossibleSmartCastToFP$default(KtExpression ktExpression, BindingContext bindingContext, DeclarationDescriptor declarationDescriptor, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, Function2 function2, int i, Object obj) {
        if ((i & 16) != 0) {
            function2 = new Function2<KotlinType, Set<? extends KotlinType>, KotlinType>() { // from class: org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowUtilsKt.getKotlinTypeWithPossibleSmartCastToFP.1
                @Override // kotlin.jvm.functions.Function2
                public final KotlinType invoke(KotlinType givenType, Set<? extends KotlinType> set) {
                    Intrinsics.checkNotNullParameter(givenType, "givenType");
                    Intrinsics.checkNotNullParameter(set, "<anonymous parameter 1>");
                    return givenType;
                }
            };
        }
        return getKotlinTypeWithPossibleSmartCastToFP(ktExpression, bindingContext, declarationDescriptor, languageVersionSettings, dataFlowValueFactory, function2);
    }

    public static final KotlinType getKotlinTypeWithPossibleSmartCastToFP(KtExpression ktExpression, BindingContext bindingContext, DeclarationDescriptor declarationDescriptor, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, Function2<? super KotlinType, ? super Set<? extends KotlinType>, ? extends KotlinType> defaultType) {
        KotlinType kotlinTypeForComparison;
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(defaultType, "defaultType");
        KotlinType kotlinType = null;
        if (ktExpression == null || (kotlinTypeForComparison = getKotlinTypeForComparison(ktExpression, bindingContext)) == null) {
            return null;
        }
        if (KotlinBuiltIns.isDoubleOrNullableDouble(kotlinTypeForComparison) || KotlinBuiltIns.isFloatOrNullableFloat(kotlinTypeForComparison) || declarationDescriptor == null) {
            return kotlinTypeForComparison;
        }
        Set<KotlinType> stableTypes = BindingContextUtilsKt.getDataFlowInfoBefore(bindingContext, ktExpression).getStableTypes(dataFlowValueFactory.createDataFlowValue(ktExpression, kotlinTypeForComparison, bindingContext, declarationDescriptor), languageVersionSettings);
        for (KotlinType kotlinType2 : stableTypes) {
            if (!KotlinBuiltIns.isDoubleOrNullableDouble(kotlinType2) && !KotlinBuiltIns.isFloatOrNullableFloat(kotlinType2)) {
                kotlinType2 = null;
            }
            if (kotlinType2 != null) {
                kotlinType = kotlinType2;
                break;
            }
        }
        return kotlinType == null ? defaultType.invoke(kotlinTypeForComparison, stableTypes) : kotlinType;
    }
}
