package org.jetbrains.kotlin.resolve.calls.components;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.ExplicitTypeParameterConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableForLambdaReturnType;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.CollectionLiteralKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.EagerCallableReferenceAtom;
import org.jetbrains.kotlin.resolve.calls.model.FunctionExpression;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.LambdaWithTypeVariableAsExpectedTypeAtom;
import org.jetbrains.kotlin.resolve.calls.model.NotCallableExpectedType;
import org.jetbrains.kotlin.resolve.calls.model.NotEnoughInformationForLambdaParameter;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCollectionLiteralAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: PostponeArgumentsChecks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000l\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a(\u0010\n\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002\u001a*\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u000f2\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u001a\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002\u001a@\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002\u001aP\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00172\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u00032\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u001a\u001e\u0010\u001f\u001a\u0002H \"\n\b\u0000\u0010 \u0018\u0001*\u00020!*\u00020!H\u0080\b¢\u0006\u0002\u0010\"\u001a2\u0010#\u001a\u00020\u0001*\u00020$2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a.\u0010#\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¨\u0006%"}, d2 = {"extraLambdaInfo", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "extractLambdaInfoFromFunctionalType", "returnTypeVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableForLambdaReturnType;", "preprocessCallableReference", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "preprocessCollectionLiteralArgument", "collectionLiteralArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/CollectionLiteralKotlinCallArgument;", "preprocessLambdaArgument", "forceResolution", "", "resolveKtPrimitive", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "receiverInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/ReceiverInfo;", "convertedType", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "selectorCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "requireIs", ExifInterface.GPS_DIRECTION_TRUE, "", "(Ljava/lang/Object;)Ljava/lang/Object;", "transformToResolvedLambda", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaWithTypeVariableAsExpectedTypeAtom;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PostponeArgumentsChecksKt {
    public static final ResolvedAtom resolveKtPrimitive(ConstraintSystemBuilder csBuilder, KotlinCallArgument argument, UnwrappedType unwrappedType, KotlinDiagnosticsHolder diagnosticsHolder, ReceiverInfo receiverInfo, UnwrappedType unwrappedType2, InferenceSession inferenceSession, KotlinCall kotlinCall) {
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        Intrinsics.checkNotNullParameter(receiverInfo, "receiverInfo");
        if (argument instanceof SimpleKotlinCallArgument) {
            return SimpleArgumentsChecksKt.checkSimpleArgument(csBuilder, (SimpleKotlinCallArgument) argument, unwrappedType, diagnosticsHolder, receiverInfo, unwrappedType2, inferenceSession, kotlinCall);
        }
        if (argument instanceof LambdaKotlinCallArgument) {
            return preprocessLambdaArgument$default(csBuilder, (LambdaKotlinCallArgument) argument, unwrappedType, diagnosticsHolder, false, null, 48, null);
        }
        if (argument instanceof CallableReferenceKotlinCallArgument) {
            return preprocessCallableReference(csBuilder, (CallableReferenceKotlinCallArgument) argument, unwrappedType, diagnosticsHolder);
        }
        if (argument instanceof CollectionLiteralKotlinCallArgument) {
            return preprocessCollectionLiteralArgument((CollectionLiteralKotlinCallArgument) argument, unwrappedType);
        }
        ArgumentsUtilsKt.unexpectedArgument(argument);
        throw null;
    }

    static /* synthetic */ ResolvedAtom preprocessLambdaArgument$default(ConstraintSystemBuilder constraintSystemBuilder, LambdaKotlinCallArgument lambdaKotlinCallArgument, UnwrappedType unwrappedType, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, boolean z, TypeVariableForLambdaReturnType typeVariableForLambdaReturnType, int i, Object obj) {
        if ((i & 16) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i & 32) != 0) {
            typeVariableForLambdaReturnType = null;
        }
        return preprocessLambdaArgument(constraintSystemBuilder, lambdaKotlinCallArgument, unwrappedType, kotlinDiagnosticsHolder, z2, typeVariableForLambdaReturnType);
    }

    private static final ResolvedAtom preprocessLambdaArgument(ConstraintSystemBuilder constraintSystemBuilder, LambdaKotlinCallArgument lambdaKotlinCallArgument, UnwrappedType unwrappedType, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, boolean z, TypeVariableForLambdaReturnType typeVariableForLambdaReturnType) {
        VariableWithConstraints variableWithConstraints;
        Object next;
        if (unwrappedType != null && !z && (variableWithConstraints = constraintSystemBuilder.currentStorage().getNotFixedTypeVariables().get(unwrappedType.getConstructor())) != null) {
            Iterator<T> it = variableWithConstraints.getConstraints().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                Constraint constraint = (Constraint) next;
                if (constraint.getKind() == ConstraintKind.EQUALITY && (constraint.getPosition().getFrom() instanceof ExplicitTypeParameterConstraintPosition)) {
                    break;
                }
            }
            Constraint constraint2 = (Constraint) next;
            KotlinTypeMarker type = constraint2 != null ? constraint2.getType() : null;
            KotlinType kotlinType = type instanceof KotlinType ? (KotlinType) type : null;
            if (kotlinType == null || !kotlinType.getArguments().isEmpty()) {
                return new LambdaWithTypeVariableAsExpectedTypeAtom(lambdaKotlinCallArgument, unwrappedType);
            }
        }
        ResolvedLambdaAtom resolvedLambdaAtomExtractLambdaInfoFromFunctionalType = extractLambdaInfoFromFunctionalType(unwrappedType, lambdaKotlinCallArgument, typeVariableForLambdaReturnType);
        if (resolvedLambdaAtomExtractLambdaInfoFromFunctionalType == null) {
            resolvedLambdaAtomExtractLambdaInfoFromFunctionalType = extraLambdaInfo(unwrappedType, lambdaKotlinCallArgument, constraintSystemBuilder, kotlinDiagnosticsHolder);
        }
        if (unwrappedType != null) {
            constraintSystemBuilder.addSubtypeConstraint(FunctionTypesKt.createFunctionType(BuiltInsProviderKt.getBuiltIns(constraintSystemBuilder), Annotations.INSTANCE.getEMPTY(), resolvedLambdaAtomExtractLambdaInfoFromFunctionalType.getReceiver(), resolvedLambdaAtomExtractLambdaInfoFromFunctionalType.getContextReceivers(), resolvedLambdaAtomExtractLambdaInfoFromFunctionalType.getParameters(), null, resolvedLambdaAtomExtractLambdaInfoFromFunctionalType.getReturnType(), resolvedLambdaAtomExtractLambdaInfoFromFunctionalType.getIsSuspend()), unwrappedType, new ArgumentConstraintPositionImpl(lambdaKotlinCallArgument));
        }
        return resolvedLambdaAtomExtractLambdaInfoFromFunctionalType;
    }

    private static final ResolvedLambdaAtom extraLambdaInfo(UnwrappedType unwrappedType, LambdaKotlinCallArgument lambdaKotlinCallArgument, ConstraintSystemBuilder constraintSystemBuilder, KotlinDiagnosticsHolder kotlinDiagnosticsHolder) {
        SimpleType defaultType;
        List<TypeProjection> arguments;
        TypeProjection typeProjection;
        KotlinType type;
        ArrayList arrayListEmptyList;
        ArrayList arrayListEmptyList2;
        UnwrappedType[] contextReceiversTypes;
        KotlinBuiltIns builtIns = BuiltInsProviderKt.getBuiltIns(constraintSystemBuilder);
        boolean zIsSuspendFunctionType = unwrappedType != null ? FunctionTypesKt.isSuspendFunctionType(unwrappedType) : false;
        boolean z = unwrappedType != null && KotlinBuiltIns.isNotNullOrNullableFunctionSupertype(unwrappedType);
        FunctionExpression functionExpression = lambdaKotlinCallArgument instanceof FunctionExpression ? (FunctionExpression) lambdaKotlinCallArgument : null;
        TypeVariableForLambdaReturnType typeVariableForLambdaReturnType = new TypeVariableForLambdaReturnType(builtIns, "_L");
        UnwrappedType receiverType = functionExpression != null ? functionExpression.getReceiverType() : null;
        if (functionExpression == null || (defaultType = functionExpression.getReturnType()) == null) {
            if (unwrappedType == null || (arguments = unwrappedType.getArguments()) == null || (typeProjection = (TypeProjection) CollectionsKt.singleOrNull((List) arguments)) == null || (type = typeProjection.getNullableAnyType()) == null || (defaultType = type.unwrap()) == null || !z) {
                defaultType = null;
            }
            if (defaultType == null) {
                defaultType = typeVariableForLambdaReturnType.getDefaultType();
            }
        }
        UnwrappedType unwrappedType2 = defaultType;
        if (functionExpression == null || (contextReceiversTypes = functionExpression.getContextReceiversTypes()) == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else {
            ArrayList arrayList = new ArrayList(contextReceiversTypes.length);
            int length = contextReceiversTypes.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                ErrorType errorTypeCreateErrorType = contextReceiversTypes[i];
                int i3 = i2 + 1;
                if (errorTypeCreateErrorType == null) {
                    kotlinDiagnosticsHolder.addDiagnostic(new NotEnoughInformationForLambdaParameter(lambdaKotlinCallArgument, i2));
                    errorTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.UNINFERRED_LAMBDA_CONTEXT_RECEIVER_TYPE, new String[0]);
                }
                arrayList.add(errorTypeCreateErrorType);
                i++;
                i2 = i3;
            }
            arrayListEmptyList = arrayList;
        }
        UnwrappedType[] parametersTypes = lambdaKotlinCallArgument.getParametersTypes();
        if (parametersTypes == null) {
            arrayListEmptyList2 = CollectionsKt.emptyList();
        } else {
            ArrayList arrayList2 = new ArrayList(parametersTypes.length);
            int length2 = parametersTypes.length;
            int i4 = 0;
            int i5 = 0;
            while (i4 < length2) {
                ErrorType errorTypeCreateErrorType2 = parametersTypes[i4];
                int i6 = i5 + 1;
                if (errorTypeCreateErrorType2 == null) {
                    kotlinDiagnosticsHolder.addDiagnostic(new NotEnoughInformationForLambdaParameter(lambdaKotlinCallArgument, i5));
                    errorTypeCreateErrorType2 = ErrorUtils.createErrorType(ErrorTypeKind.UNINFERRED_LAMBDA_PARAMETER_TYPE, new String[0]);
                }
                arrayList2.add(errorTypeCreateErrorType2);
                i4++;
                i5 = i6;
            }
            arrayListEmptyList2 = arrayList2;
        }
        boolean zAreEqual = Intrinsics.areEqual(unwrappedType2, typeVariableForLambdaReturnType.getDefaultType());
        if (zAreEqual) {
            constraintSystemBuilder.registerVariable(typeVariableForLambdaReturnType);
        }
        return new ResolvedLambdaAtom(lambdaKotlinCallArgument, zIsSuspendFunctionType, receiverType, arrayListEmptyList, arrayListEmptyList2, unwrappedType2, zAreEqual ? typeVariableForLambdaReturnType : null, unwrappedType);
    }

    private static final ResolvedLambdaAtom extractLambdaInfoFromFunctionalType(UnwrappedType unwrappedType, LambdaKotlinCallArgument lambdaKotlinCallArgument, TypeVariableForLambdaReturnType typeVariableForLambdaReturnType) {
        ArrayList arrayList;
        Pair pair;
        ArrayList arrayList2;
        KotlinType type;
        ArrayList arrayList3;
        UnwrappedType unwrappedTypeUnwrap;
        UnwrappedType[] contextReceiversTypes;
        ArrayList arrayListEmptyList;
        if (unwrappedType != null) {
            UnwrappedType unwrappedType2 = unwrappedType;
            if (FunctionTypesKt.isBuiltinFunctionalType(unwrappedType2)) {
                UnwrappedType[] parametersTypes = lambdaKotlinCallArgument.getParametersTypes();
                List<TypeProjection> valueParameterTypesFromFunctionType = FunctionTypesKt.getValueParameterTypesFromFunctionType(unwrappedType2);
                KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(unwrappedType2);
                UnwrappedType unwrappedTypeUnwrap2 = receiverTypeFromFunctionType != null ? receiverTypeFromFunctionType.unwrap() : null;
                List<KotlinType> contextReceiverTypesFromFunctionType = FunctionTypesKt.getContextReceiverTypesFromFunctionType(unwrappedType2);
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceiverTypesFromFunctionType, 10));
                Iterator<T> it = contextReceiverTypesFromFunctionType.iterator();
                while (it.hasNext()) {
                    arrayList4.add(((KotlinType) it.next()).unwrap());
                }
                int i = 0;
                UnwrappedType[] unwrappedTypeArr = (UnwrappedType[]) arrayList4.toArray(new UnwrappedType[0]);
                FunctionExpression functionExpression = lambdaKotlinCallArgument instanceof FunctionExpression ? (FunctionExpression) lambdaKotlinCallArgument : null;
                boolean z = (functionExpression != null ? functionExpression.getReceiverType() : null) == null && unwrappedTypeUnwrap2 != null;
                if (functionExpression != null) {
                    if (parametersTypes == null) {
                        arrayListEmptyList = CollectionsKt.emptyList();
                    } else {
                        ArrayList arrayList5 = new ArrayList(parametersTypes.length);
                        int length = parametersTypes.length;
                        int i2 = 0;
                        while (i < length) {
                            arrayList5.add(extractLambdaInfoFromFunctionalType$orExpected(parametersTypes[i], valueParameterTypesFromFunctionType, unwrappedType, i2));
                            i++;
                            i2++;
                        }
                        arrayListEmptyList = arrayList5;
                    }
                    pair = TuplesKt.to(arrayListEmptyList, functionExpression.getReceiverType());
                } else {
                    if ((parametersTypes != null ? parametersTypes.length : 0) == valueParameterTypesFromFunctionType.size() && z) {
                        if (parametersTypes == null) {
                            List<TypeProjection> list = valueParameterTypesFromFunctionType;
                            ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                            Iterator<T> it2 = list.iterator();
                            while (it2.hasNext()) {
                                arrayList6.add(((TypeProjection) it2.next()).getNullableAnyType().unwrap());
                            }
                            arrayList3 = arrayList6;
                        } else {
                            ArrayList arrayList7 = new ArrayList(parametersTypes.length);
                            int length2 = parametersTypes.length;
                            int i3 = 0;
                            while (i < length2) {
                                arrayList7.add(extractLambdaInfoFromFunctionalType$orExpected(parametersTypes[i], valueParameterTypesFromFunctionType, unwrappedType, i3));
                                i++;
                                i3++;
                            }
                            arrayList3 = arrayList7;
                        }
                        pair = TuplesKt.to(arrayList3, unwrappedTypeUnwrap2);
                    } else {
                        if ((parametersTypes != null ? parametersTypes.length : 0) - valueParameterTypesFromFunctionType.size() == 1 && z) {
                            if (parametersTypes == null) {
                                List<TypeProjection> list2 = valueParameterTypesFromFunctionType;
                                ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                                Iterator<T> it3 = list2.iterator();
                                while (it3.hasNext()) {
                                    arrayList8.add(((TypeProjection) it3.next()).getNullableAnyType().unwrap());
                                }
                                arrayList2 = arrayList8;
                            } else {
                                ArrayList arrayList9 = new ArrayList(parametersTypes.length);
                                int length3 = parametersTypes.length;
                                int i4 = 0;
                                while (i < length3) {
                                    SimpleType simpleTypeUnwrap = parametersTypes[i];
                                    int i5 = i4 + 1;
                                    if (simpleTypeUnwrap == null) {
                                        TypeProjection typeProjection = (TypeProjection) CollectionsKt.getOrNull(valueParameterTypesFromFunctionType, i4);
                                        simpleTypeUnwrap = (typeProjection == null || (type = typeProjection.getNullableAnyType()) == null) ? null : type.unwrap();
                                        if (simpleTypeUnwrap == null) {
                                            SimpleType nullableAnyType = TypeUtilsKt.getBuiltIns(unwrappedType2).getNullableAnyType();
                                            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
                                            simpleTypeUnwrap = nullableAnyType;
                                        }
                                    }
                                    arrayList9.add(simpleTypeUnwrap);
                                    i++;
                                    i4 = i5;
                                }
                                arrayList2 = arrayList9;
                            }
                            pair = TuplesKt.to(arrayList2, unwrappedTypeUnwrap2 != null ? unwrappedTypeUnwrap2.unwrap() : null);
                        } else {
                            if (parametersTypes == null) {
                                List<TypeProjection> list3 = valueParameterTypesFromFunctionType;
                                ArrayList arrayList10 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                                Iterator<T> it4 = list3.iterator();
                                while (it4.hasNext()) {
                                    arrayList10.add(((TypeProjection) it4.next()).getNullableAnyType().unwrap());
                                }
                                arrayList = arrayList10;
                            } else {
                                ArrayList arrayList11 = new ArrayList(parametersTypes.length);
                                int length4 = parametersTypes.length;
                                int i6 = 0;
                                while (i < length4) {
                                    arrayList11.add(extractLambdaInfoFromFunctionalType$orExpected(parametersTypes[i], valueParameterTypesFromFunctionType, unwrappedType, i6));
                                    i++;
                                    i6++;
                                }
                                arrayList = arrayList11;
                            }
                            pair = TuplesKt.to(arrayList, z ? unwrappedTypeUnwrap2 : null);
                        }
                    }
                }
                List list4 = (List) pair.component1();
                UnwrappedType unwrappedType3 = (UnwrappedType) pair.component2();
                if (functionExpression != null && (contextReceiversTypes = functionExpression.getContextReceiversTypes()) != null) {
                    unwrappedTypeArr = contextReceiversTypes;
                }
                List listFilterNotNull = ArraysKt.filterNotNull(unwrappedTypeArr);
                if (functionExpression == null || (unwrappedTypeUnwrap = functionExpression.getReturnType()) == null) {
                    unwrappedTypeUnwrap = FunctionTypesKt.getReturnTypeFromFunctionType(unwrappedType2).unwrap();
                }
                return new ResolvedLambdaAtom(lambdaKotlinCallArgument, FunctionTypesKt.isSuspendFunctionType(unwrappedType2), unwrappedType3, listFilterNotNull, list4, unwrappedTypeUnwrap, typeVariableForLambdaReturnType, unwrappedType);
            }
        }
        return null;
    }

    private static final UnwrappedType extractLambdaInfoFromFunctionalType$orExpected(UnwrappedType unwrappedType, List<? extends TypeProjection> list, UnwrappedType unwrappedType2, int i) {
        KotlinType type;
        if (unwrappedType != null) {
            return unwrappedType;
        }
        TypeProjection typeProjection = (TypeProjection) CollectionsKt.getOrNull(list, i);
        UnwrappedType unwrappedTypeUnwrap = (typeProjection == null || (type = typeProjection.getNullableAnyType()) == null) ? null : type.unwrap();
        if (unwrappedTypeUnwrap != null) {
            return unwrappedTypeUnwrap;
        }
        SimpleType nullableAnyType = TypeUtilsKt.getBuiltIns(unwrappedType2).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
        return nullableAnyType;
    }

    public static /* synthetic */ ResolvedLambdaAtom transformToResolvedLambda$default(LambdaWithTypeVariableAsExpectedTypeAtom lambdaWithTypeVariableAsExpectedTypeAtom, ConstraintSystemBuilder constraintSystemBuilder, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, UnwrappedType unwrappedType, TypeVariableForLambdaReturnType typeVariableForLambdaReturnType, int i, Object obj) {
        if ((i & 4) != 0) {
            unwrappedType = null;
        }
        if ((i & 8) != 0) {
            typeVariableForLambdaReturnType = null;
        }
        return transformToResolvedLambda(lambdaWithTypeVariableAsExpectedTypeAtom, constraintSystemBuilder, kotlinDiagnosticsHolder, unwrappedType, typeVariableForLambdaReturnType);
    }

    public static final ResolvedLambdaAtom transformToResolvedLambda(LambdaWithTypeVariableAsExpectedTypeAtom lambdaWithTypeVariableAsExpectedTypeAtom, ConstraintSystemBuilder csBuilder, KotlinDiagnosticsHolder diagnosticsHolder, UnwrappedType unwrappedType, TypeVariableForLambdaReturnType typeVariableForLambdaReturnType) {
        Intrinsics.checkNotNullParameter(lambdaWithTypeVariableAsExpectedTypeAtom, "<this>");
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = csBuilder.buildCurrentSubstitutor();
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor");
        NewTypeSubstitutor newTypeSubstitutor = (NewTypeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor;
        if (unwrappedType == null) {
            unwrappedType = lambdaWithTypeVariableAsExpectedTypeAtom.getExpectedType();
        }
        ResolvedAtom resolvedAtomPreprocessLambdaArgument = preprocessLambdaArgument(csBuilder, lambdaWithTypeVariableAsExpectedTypeAtom.getAtom(), newTypeSubstitutor.safeSubstitute(unwrappedType), diagnosticsHolder, true, typeVariableForLambdaReturnType);
        Intrinsics.checkNotNull(resolvedAtomPreprocessLambdaArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom");
        ResolvedLambdaAtom resolvedLambdaAtom = (ResolvedLambdaAtom) resolvedAtomPreprocessLambdaArgument;
        lambdaWithTypeVariableAsExpectedTypeAtom.setAnalyzed(resolvedLambdaAtom);
        return resolvedLambdaAtom;
    }

    public static /* synthetic */ ResolvedLambdaAtom transformToResolvedLambda$default(ResolvedLambdaAtom resolvedLambdaAtom, ConstraintSystemBuilder constraintSystemBuilder, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, UnwrappedType unwrappedType, TypeVariableForLambdaReturnType typeVariableForLambdaReturnType, int i, Object obj) {
        if ((i & 8) != 0) {
            typeVariableForLambdaReturnType = null;
        }
        return transformToResolvedLambda(resolvedLambdaAtom, constraintSystemBuilder, kotlinDiagnosticsHolder, unwrappedType, typeVariableForLambdaReturnType);
    }

    public static final ResolvedLambdaAtom transformToResolvedLambda(ResolvedLambdaAtom resolvedLambdaAtom, ConstraintSystemBuilder csBuilder, KotlinDiagnosticsHolder diagnosticsHolder, UnwrappedType expectedType, TypeVariableForLambdaReturnType typeVariableForLambdaReturnType) {
        Intrinsics.checkNotNullParameter(resolvedLambdaAtom, "<this>");
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        ResolvedAtom resolvedAtomPreprocessLambdaArgument = preprocessLambdaArgument(csBuilder, resolvedLambdaAtom.getAtom(), expectedType, diagnosticsHolder, true, typeVariableForLambdaReturnType);
        resolvedLambdaAtom.setAnalyzedResults(null, CollectionsKt.listOf(resolvedAtomPreprocessLambdaArgument));
        Intrinsics.checkNotNull(resolvedAtomPreprocessLambdaArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom");
        return (ResolvedLambdaAtom) resolvedAtomPreprocessLambdaArgument;
    }

    private static final ResolvedAtom preprocessCallableReference(ConstraintSystemBuilder constraintSystemBuilder, CallableReferenceKotlinCallArgument callableReferenceKotlinCallArgument, UnwrappedType unwrappedType, KotlinDiagnosticsHolder kotlinDiagnosticsHolder) {
        Object next;
        TypeConstructorMarker typeConstructorMarker;
        ReflectionTypes.Companion companion;
        EagerCallableReferenceAtom eagerCallableReferenceAtom = new EagerCallableReferenceAtom(callableReferenceKotlinCallArgument, unwrappedType);
        if (unwrappedType == null) {
            return eagerCallableReferenceAtom;
        }
        Iterator<T> it = constraintSystemBuilder.getProperSuperTypeConstructors(unwrappedType).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            typeConstructorMarker = (TypeConstructorMarker) next;
            companion = ReflectionTypes.INSTANCE;
            if (!(typeConstructorMarker instanceof TypeConstructor)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        } while (companion.isPossibleExpectedCallableType((TypeConstructor) typeConstructorMarker));
        TypeConstructorMarker typeConstructorMarker2 = (TypeConstructorMarker) next;
        if (typeConstructorMarker2 != null) {
            if (typeConstructorMarker2 instanceof TypeConstructor) {
                kotlinDiagnosticsHolder.addDiagnostic(new NotCallableExpectedType(callableReferenceKotlinCallArgument, unwrappedType, (TypeConstructor) typeConstructorMarker2));
            } else {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        }
        return eagerCallableReferenceAtom;
    }

    private static final ResolvedAtom preprocessCollectionLiteralArgument(CollectionLiteralKotlinCallArgument collectionLiteralKotlinCallArgument, UnwrappedType unwrappedType) {
        return new ResolvedCollectionLiteralAtom(collectionLiteralKotlinCallArgument, unwrappedType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ <T> T requireIs(Object obj) {
        Intrinsics.checkNotNullParameter(obj, "<this>");
        Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
        if (obj instanceof Object) {
            return obj;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }
}
