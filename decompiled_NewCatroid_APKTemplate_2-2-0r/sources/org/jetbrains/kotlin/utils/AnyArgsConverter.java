package org.jetbrains.kotlin.utils;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.KTypesJvm;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import net.bytebuddy.description.method.ParameterDescription;

/* JADX INFO: compiled from: parametersMap.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\tH\u0016J2\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\t2\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\t0\rH\u0016J2\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\t2\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\t0\rH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/utils/AnyArgsConverter;", "Lorg/jetbrains/kotlin/utils/ArgsConverter;", "", "()V", "tryConvertSingle", "Lorg/jetbrains/kotlin/utils/ArgsConverter$Result;", "parameter", "Lkotlin/reflect/KParameter;", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/utils/NamedArgument;", "tryConvertTail", "firstArg", "restArgs", "Lkotlin/sequences/Sequence;", "tryConvertVararg", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class AnyArgsConverter implements ArgsConverter<Object> {
    @Override // org.jetbrains.kotlin.utils.ArgsConverter
    public ArgsConverter.Result tryConvertSingle(KParameter parameter, NamedArgument<? extends Object> arg) {
        Object objConvertAnyArrayImpl;
        Object objTryConvertSingle$evaluateValue;
        Class javaClass;
        Class<?> componentType;
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(arg, "arg");
        Object value = arg.getValue();
        KClass kotlinClass = null;
        if (value == null) {
            return new ArgsConverter.Result.Success(null);
        }
        Object objTryConvertSingle$evaluateValue2 = tryConvertSingle$evaluateValue(parameter, value);
        if (objTryConvertSingle$evaluateValue2 != null) {
            return new ArgsConverter.Result.Success(objTryConvertSingle$evaluateValue2);
        }
        KClassifier classifier = parameter.getType().getClassifier();
        KClass kClass = classifier instanceof KClass ? (KClass) classifier : null;
        if (kClass != null && (javaClass = JvmClassMappingKt.getJavaClass(kClass)) != null) {
            if (!javaClass.isArray()) {
                javaClass = null;
            }
            if (javaClass != null && (componentType = javaClass.getComponentType()) != null) {
                kotlinClass = JvmClassMappingKt.getKotlinClass(componentType);
            }
        }
        if ((value instanceof Object[]) && kotlinClass != null) {
            Sequence sequenceAsSequence = ArraysKt.asSequence((Object[]) value);
            KClass kClass2 = kotlinClass;
            if (!Intrinsics.areEqual(kClass2, Reflection.getOrCreateKotlinClass(Object.class))) {
                objConvertAnyArrayImpl = ParametersMapKt.convertAnyArrayImpl(kClass2, sequenceAsSequence);
            } else {
                objConvertAnyArrayImpl = SequencesKt.toList(sequenceAsSequence).toArray(new Object[0]);
            }
            if (objConvertAnyArrayImpl != null && (objTryConvertSingle$evaluateValue = tryConvertSingle$evaluateValue(parameter, objConvertAnyArrayImpl)) != null) {
                return new ArgsConverter.Result.Success(objTryConvertSingle$evaluateValue);
            }
        }
        return ArgsConverter.Result.Failure.INSTANCE;
    }

    private static final Object tryConvertSingle$convertPrimitivesArray(KType kType, Object obj) {
        KClassifier classifier = kType != null ? kType.getClassifier() : null;
        if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(int[].class))) {
            Integer[] numArr = obj instanceof Integer[] ? (Integer[]) obj : null;
            if (numArr != null) {
                return ArraysKt.toIntArray(numArr);
            }
            return null;
        }
        if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(long[].class))) {
            Long[] lArr = obj instanceof Long[] ? (Long[]) obj : null;
            if (lArr != null) {
                return ArraysKt.toLongArray(lArr);
            }
            return null;
        }
        if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(short[].class))) {
            Short[] shArr = obj instanceof Short[] ? (Short[]) obj : null;
            if (shArr != null) {
                return ArraysKt.toShortArray(shArr);
            }
            return null;
        }
        if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(byte[].class))) {
            Byte[] bArr = obj instanceof Byte[] ? (Byte[]) obj : null;
            if (bArr != null) {
                return ArraysKt.toByteArray(bArr);
            }
            return null;
        }
        if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(char[].class))) {
            Character[] chArr = obj instanceof Character[] ? (Character[]) obj : null;
            if (chArr != null) {
                return ArraysKt.toCharArray(chArr);
            }
            return null;
        }
        if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(float[].class))) {
            Float[] fArr = obj instanceof Float[] ? (Float[]) obj : null;
            if (fArr != null) {
                return ArraysKt.toFloatArray(fArr);
            }
            return null;
        }
        if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(double[].class))) {
            Double[] dArr = obj instanceof Double[] ? (Double[]) obj : null;
            if (dArr != null) {
                return ArraysKt.toDoubleArray(dArr);
            }
            return null;
        }
        if (!Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(boolean[].class))) {
            return null;
        }
        Boolean[] boolArr = obj instanceof Boolean[] ? (Boolean[]) obj : null;
        if (boolArr != null) {
            return ArraysKt.toBooleanArray(boolArr);
        }
        return null;
    }

    private static final Object tryConvertSingle$evaluateValue(KParameter kParameter, Object obj) {
        return KClasses.isSubclassOf(Reflection.getOrCreateKotlinClass(obj.getClass()), KTypesJvm.getJvmErasure(kParameter.getType())) ? obj : tryConvertSingle$convertPrimitivesArray(kParameter.getType(), obj);
    }

    @Override // org.jetbrains.kotlin.utils.ArgsConverter
    public ArgsConverter.Result tryConvertVararg(KParameter parameter, NamedArgument<? extends Object> firstArg, Sequence<? extends NamedArgument<? extends Object>> restArgs) {
        Object objConvertAnyArrayImpl;
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(firstArg, "firstArg");
        Intrinsics.checkNotNullParameter(restArgs, "restArgs");
        KType type = parameter.getType();
        if (JvmClassMappingKt.getJavaClass((KClass) KTypesJvm.getJvmErasure(type)).isArray()) {
            Sequence sequencePlus = SequencesKt.plus(SequencesKt.sequenceOf(firstArg.getValue()), SequencesKt.map(restArgs, new Function1<NamedArgument<? extends Object>, Object>() { // from class: org.jetbrains.kotlin.utils.AnyArgsConverter$tryConvertVararg$argsSequence$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(NamedArgument<? extends Object> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getValue();
                }
            }));
            KTypeProjection kTypeProjection = (KTypeProjection) kotlin.collections.CollectionsKt.firstOrNull((List) type.getArguments());
            KType type2 = kTypeProjection != null ? kTypeProjection.getType() : null;
            KClassifier classifier = type2 != null ? type2.getClassifier() : null;
            if (!Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Object.class))) {
                objConvertAnyArrayImpl = ParametersMapKt.convertAnyArrayImpl(classifier, sequencePlus);
            } else {
                objConvertAnyArrayImpl = SequencesKt.toList(sequencePlus).toArray(new Object[0]);
            }
            if (objConvertAnyArrayImpl != null) {
                return new ArgsConverter.Result.Success(objConvertAnyArrayImpl);
            }
        }
        return ArgsConverter.Result.Failure.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.utils.ArgsConverter
    public ArgsConverter.Result tryConvertTail(KParameter parameter, NamedArgument<? extends Object> firstArg, Sequence<? extends NamedArgument<? extends Object>> restArgs) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(firstArg, "firstArg");
        Intrinsics.checkNotNullParameter(restArgs, "restArgs");
        return tryConvertSingle(parameter, firstArg);
    }
}
