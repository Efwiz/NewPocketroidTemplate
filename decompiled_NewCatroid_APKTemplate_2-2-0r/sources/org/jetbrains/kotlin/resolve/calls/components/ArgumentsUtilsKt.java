package org.jetbrains.kotlin.resolve.calls.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.CollectionLiteralKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolverKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.IntersectionTypeKt;
import org.jetbrains.kotlin.types.checker.NewCapturedTypeKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: ArgumentsUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u001a \u0010\u0013\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0015H\u0002\u001a\u001c\u0010\u0016\u001a\u00020\t*\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0019H\u0000\u001a\n\u0010\u001a\u001a\u00020\u0001*\u00020\u0002\u001a\u001c\u0010\u001b\u001a\u00020\u0001*\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0019H\u0002\u001a\u001c\u0010\u001c\u001a\u00020\u0001*\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0019H\u0002\u001a\n\u0010\u001d\u001a\u00020\u0001*\u00020\u0012\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003\"\u0015\u0010\b\u001a\u00020\t*\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u001a\u0010\r\u001a\u0004\u0018\u00010\t*\u00020\n8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\f¨\u0006\u001e"}, d2 = {"isActualParameterWithAnyExpectedDefault", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;)Z", "isActualParameterWithCorrespondingExpectedDefault", "isVararg", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;)Z", "stableType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getStableType", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)Lorg/jetbrains/kotlin/types/UnwrappedType;", "unstableType", "getUnstableType", "unexpectedArgument", "", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "checkExpectedParameter", "checker", "Lkotlin/Function1;", "getExpectedType", "parameter", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "hasDefaultValue", "isArrayAssignedAsNamedArgumentInAnnotation", "isArrayAssignedAsNamedArgumentInFunction", "isArrayOrArrayLiteral", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ArgumentsUtilsKt {
    public static final Void unexpectedArgument(KotlinCallArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        throw new IllegalStateException(("Unexpected argument type: " + argument + ", " + argument.getClass().getCanonicalName() + '.').toString());
    }

    public static final UnwrappedType getUnstableType(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
        Intrinsics.checkNotNullParameter(receiverValueWithSmartCastInfo, "<this>");
        if (receiverValueWithSmartCastInfo.getIsStable() || !receiverValueWithSmartCastInfo.hasTypesFromSmartCasts()) {
            if (receiverValueWithSmartCastInfo.getIsStable()) {
                return null;
            }
            return receiverValueWithSmartCastInfo.getReceiverValue().getType().unwrap();
        }
        UnwrappedType unwrappedTypeIntersectWrappedTypes = IntersectionTypeKt.intersectWrappedTypes(receiverValueWithSmartCastInfo.getAllOriginalTypes());
        UnwrappedType unwrappedTypePrepareArgumentTypeRegardingCaptureTypes = NewCapturedTypeKt.prepareArgumentTypeRegardingCaptureTypes(unwrappedTypeIntersectWrappedTypes);
        return unwrappedTypePrepareArgumentTypeRegardingCaptureTypes == null ? unwrappedTypeIntersectWrappedTypes : unwrappedTypePrepareArgumentTypeRegardingCaptureTypes;
    }

    public static final UnwrappedType getStableType(ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo) {
        Intrinsics.checkNotNullParameter(receiverValueWithSmartCastInfo, "<this>");
        if (!receiverValueWithSmartCastInfo.getIsStable() || !receiverValueWithSmartCastInfo.hasTypesFromSmartCasts()) {
            return receiverValueWithSmartCastInfo.getReceiverValue().getType().unwrap();
        }
        UnwrappedType unwrappedTypeIntersectWrappedTypes = IntersectionTypeKt.intersectWrappedTypes(receiverValueWithSmartCastInfo.getAllOriginalTypes());
        UnwrappedType unwrappedType = unwrappedTypeIntersectWrappedTypes;
        if (TypeUtilsKt.isNullableNothing(unwrappedType) && !unwrappedTypeIntersectWrappedTypes.getIsMarkedNullable()) {
            return TypeUtilsKt.makeNullable(unwrappedType).unwrap();
        }
        UnwrappedType unwrappedTypePrepareArgumentTypeRegardingCaptureTypes = NewCapturedTypeKt.prepareArgumentTypeRegardingCaptureTypes(unwrappedTypeIntersectWrappedTypes);
        return unwrappedTypePrepareArgumentTypeRegardingCaptureTypes == null ? unwrappedTypeIntersectWrappedTypes : unwrappedTypePrepareArgumentTypeRegardingCaptureTypes;
    }

    public static final UnwrappedType getExpectedType(KotlinCallArgument kotlinCallArgument, ParameterDescriptor parameter, LanguageVersionSettings languageVersionSettings) {
        KotlinType varargElementType;
        UnwrappedType unwrappedTypeUnwrap;
        Intrinsics.checkNotNullParameter(kotlinCallArgument, "<this>");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (kotlinCallArgument.isSpread() || isArrayAssignedAsNamedArgumentInAnnotation(kotlinCallArgument, parameter, languageVersionSettings) || isArrayAssignedAsNamedArgumentInFunction(kotlinCallArgument, parameter, languageVersionSettings)) {
            return parameter.getType().unwrap();
        }
        ValueParameterDescriptor valueParameterDescriptor = parameter instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) parameter : null;
        return (valueParameterDescriptor == null || (varargElementType = valueParameterDescriptor.getVarargElementType()) == null || (unwrappedTypeUnwrap = varargElementType.unwrap()) == null) ? parameter.getType().unwrap() : unwrappedTypeUnwrap;
    }

    public static final boolean isVararg(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        return valueParameterDescriptor.getVarargElementType() != null;
    }

    public static final boolean isVararg(ParameterDescriptor parameterDescriptor) {
        Intrinsics.checkNotNullParameter(parameterDescriptor, "<this>");
        ValueParameterDescriptor valueParameterDescriptor = parameterDescriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) parameterDescriptor : null;
        if (valueParameterDescriptor != null) {
            return isVararg(valueParameterDescriptor);
        }
        return false;
    }

    public static final boolean hasDefaultValue(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(valueParameterDescriptor), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return ArgumentsUtilsKt.hasDefaultValue$lambda$0((ValueParameterDescriptor) obj);
            }
        }, new Function1<ValueParameterDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt.hasDefaultValue.2
            /* JADX WARN: Code duplicated, block: B:8:0x0012  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ValueParameterDescriptor valueParameterDescriptor2) {
                boolean z;
                if (valueParameterDescriptor2.declaresDefaultValue()) {
                    z = true;
                } else {
                    Intrinsics.checkNotNull(valueParameterDescriptor2);
                    if (ArgumentsUtilsKt.isActualParameterWithCorrespondingExpectedDefault(valueParameterDescriptor2)) {
                        z = true;
                    } else {
                        z = false;
                    }
                }
                return Boolean.valueOf(z);
            }
        });
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable hasDefaultValue$lambda$0(ValueParameterDescriptor valueParameterDescriptor) {
        Collection<ValueParameterDescriptor> overriddenDescriptors = valueParameterDescriptor.getOverriddenDescriptors();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenDescriptors, 10));
        Iterator<T> it = overriddenDescriptors.iterator();
        while (it.hasNext()) {
            arrayList.add(((ValueParameterDescriptor) it.next()).getOriginal());
        }
        return arrayList;
    }

    private static final boolean checkExpectedParameter(ValueParameterDescriptor valueParameterDescriptor, Function1<? super ValueParameterDescriptor, Boolean> function1) {
        CallableDescriptor packageFragment = valueParameterDescriptor.getContainingDeclaration();
        if (!(packageFragment instanceof FunctionDescriptor) || !((FunctionDescriptor) packageFragment).isActual()) {
            return false;
        }
        MemberDescriptor memberDescriptor = (MemberDescriptor) CollectionsKt.firstOrNull(ExpectedActualResolverKt.findCompatibleExpectsForActual$default((MemberDescriptor) packageFragment, null, 1, null));
        if (!(memberDescriptor instanceof FunctionDescriptor)) {
            return false;
        }
        ValueParameterDescriptor valueParameterDescriptor2 = ((FunctionDescriptor) memberDescriptor).getValueParameters().get(valueParameterDescriptor.getIndex());
        Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor2, "get(...)");
        return function1.invoke(valueParameterDescriptor2).booleanValue();
    }

    public static final boolean isActualParameterWithAnyExpectedDefault(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        return checkExpectedParameter(valueParameterDescriptor, new Function1<ValueParameterDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt.isActualParameterWithAnyExpectedDefault.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ValueParameterDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(ArgumentsUtilsKt.hasDefaultValue(it));
            }
        });
    }

    public static final boolean isActualParameterWithCorrespondingExpectedDefault(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        return checkExpectedParameter(valueParameterDescriptor, new Function1<ValueParameterDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt.isActualParameterWithCorrespondingExpectedDefault.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ValueParameterDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.declaresDefaultValue());
            }
        });
    }

    private static final boolean isArrayAssignedAsNamedArgumentInAnnotation(KotlinCallArgument kotlinCallArgument, ParameterDescriptor parameterDescriptor, LanguageVersionSettings languageVersionSettings) {
        if (languageVersionSettings.supportsFeature(LanguageFeature.AssigningArraysToVarargsInNamedFormInAnnotations)) {
            return (languageVersionSettings.supportsFeature(LanguageFeature.ProhibitAssigningSingleElementsToVarargsInNamedForm) || isArrayOrArrayLiteral(kotlinCallArgument)) && kotlinCallArgument.getArgumentName() != null && isVararg(parameterDescriptor) && DescriptorUtilsKt.isParameterOfAnnotation(parameterDescriptor);
        }
        return false;
    }

    private static final boolean isArrayAssignedAsNamedArgumentInFunction(KotlinCallArgument kotlinCallArgument, ParameterDescriptor parameterDescriptor, LanguageVersionSettings languageVersionSettings) {
        if (languageVersionSettings.supportsFeature(LanguageFeature.AllowAssigningArrayElementsToVarargsInNamedFormForFunctions)) {
            return (languageVersionSettings.supportsFeature(LanguageFeature.ProhibitAssigningSingleElementsToVarargsInNamedForm) || isArrayOrArrayLiteral(kotlinCallArgument)) && kotlinCallArgument.getArgumentName() != null && isVararg(parameterDescriptor);
        }
        return false;
    }

    public static final boolean isArrayOrArrayLiteral(KotlinCallArgument kotlinCallArgument) {
        Intrinsics.checkNotNullParameter(kotlinCallArgument, "<this>");
        if (kotlinCallArgument instanceof CollectionLiteralKotlinCallArgument) {
            return true;
        }
        if (!(kotlinCallArgument instanceof SimpleKotlinCallArgument)) {
            return false;
        }
        KotlinType type = ((SimpleKotlinCallArgument) kotlinCallArgument).getReceiver().getReceiverValue().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return KotlinBuiltIns.isArrayOrPrimitiveArray(type);
    }
}
