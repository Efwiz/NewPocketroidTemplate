package org.jetbrains.kotlin.utils;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.jvm.KTypesJvm;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import net.bytebuddy.description.method.ParameterDescription;

/* JADX INFO: compiled from: parametersMap.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\tH\u0016J2\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\t2\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\t0\rH\u0016J2\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\t2\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\t0\rH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/utils/StringArgsConverter;", "Lorg/jetbrains/kotlin/utils/ArgsConverter;", "", "()V", "tryConvertSingle", "Lorg/jetbrains/kotlin/utils/ArgsConverter$Result;", "parameter", "Lkotlin/reflect/KParameter;", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/utils/NamedArgument;", "tryConvertTail", "firstArg", "restArgs", "Lkotlin/sequences/Sequence;", "tryConvertVararg", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class StringArgsConverter implements ArgsConverter<String> {
    @Override // org.jetbrains.kotlin.utils.ArgsConverter
    public ArgsConverter.Result tryConvertSingle(KParameter parameter, NamedArgument<? extends String> arg) {
        Object objValueOf;
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(arg, "arg");
        String value = arg.getValue();
        if (value == null) {
            return new ArgsConverter.Result.Success(null);
        }
        KClassifier classifier = parameter.getType().getClassifier();
        if (!Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(String.class))) {
            if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Integer.TYPE))) {
                objValueOf = value;
                objValueOf = kotlin.text.StringsKt.toIntOrNull(value);
            } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Long.TYPE))) {
                objValueOf = value;
                objValueOf = kotlin.text.StringsKt.toLongOrNull(value);
            } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Short.TYPE))) {
                objValueOf = value;
                objValueOf = kotlin.text.StringsKt.toShortOrNull(value);
            } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Byte.TYPE))) {
                objValueOf = value;
                objValueOf = kotlin.text.StringsKt.toByteOrNull(value);
            } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Character.TYPE))) {
                objValueOf = value;
                objValueOf = kotlin.text.StringsKt.singleOrNull(value);
            } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Float.TYPE))) {
                objValueOf = value;
                objValueOf = kotlin.text.StringsKt.toFloatOrNull(value);
            } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Double.TYPE))) {
                objValueOf = value;
                objValueOf = kotlin.text.StringsKt.toDoubleOrNull(value);
            } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                objValueOf = value;
                objValueOf = Boolean.valueOf(Boolean.parseBoolean(value));
            } else {
                objValueOf = value;
                objValueOf = null;
            }
        }
        return objValueOf != null ? new ArgsConverter.Result.Success(objValueOf) : ArgsConverter.Result.Failure.INSTANCE;
    }

    private static final Object tryConvertVararg$convertPrimitivesArray(KType kType, Sequence<String> sequence) {
        Sequence map;
        List list;
        KClassifier classifier = kType.getClassifier();
        if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(int[].class))) {
            map = SequencesKt.map(sequence, new Function1<String, Integer>() { // from class: org.jetbrains.kotlin.utils.StringArgsConverter$tryConvertVararg$convertPrimitivesArray$1
                @Override // kotlin.jvm.functions.Function1
                public final Integer invoke(String str) {
                    if (str != null) {
                        return kotlin.text.StringsKt.toIntOrNull(str);
                    }
                    return null;
                }
            });
        } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(long[].class))) {
            map = SequencesKt.map(sequence, new Function1<String, Long>() { // from class: org.jetbrains.kotlin.utils.StringArgsConverter$tryConvertVararg$convertPrimitivesArray$2
                @Override // kotlin.jvm.functions.Function1
                public final Long invoke(String str) {
                    if (str != null) {
                        return kotlin.text.StringsKt.toLongOrNull(str);
                    }
                    return null;
                }
            });
        } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(short[].class))) {
            map = SequencesKt.map(sequence, new Function1<String, Short>() { // from class: org.jetbrains.kotlin.utils.StringArgsConverter$tryConvertVararg$convertPrimitivesArray$3
                @Override // kotlin.jvm.functions.Function1
                public final Short invoke(String str) {
                    if (str != null) {
                        return kotlin.text.StringsKt.toShortOrNull(str);
                    }
                    return null;
                }
            });
        } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(byte[].class))) {
            map = SequencesKt.map(sequence, new Function1<String, Byte>() { // from class: org.jetbrains.kotlin.utils.StringArgsConverter$tryConvertVararg$convertPrimitivesArray$4
                @Override // kotlin.jvm.functions.Function1
                public final Byte invoke(String str) {
                    if (str != null) {
                        return kotlin.text.StringsKt.toByteOrNull(str);
                    }
                    return null;
                }
            });
        } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(char[].class))) {
            map = SequencesKt.map(sequence, new Function1<String, Character>() { // from class: org.jetbrains.kotlin.utils.StringArgsConverter$tryConvertVararg$convertPrimitivesArray$5
                @Override // kotlin.jvm.functions.Function1
                public final Character invoke(String str) {
                    if (str != null) {
                        return kotlin.text.StringsKt.singleOrNull(str);
                    }
                    return null;
                }
            });
        } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(float[].class))) {
            map = SequencesKt.map(sequence, new Function1<String, Float>() { // from class: org.jetbrains.kotlin.utils.StringArgsConverter$tryConvertVararg$convertPrimitivesArray$6
                @Override // kotlin.jvm.functions.Function1
                public final Float invoke(String str) {
                    if (str != null) {
                        return kotlin.text.StringsKt.toFloatOrNull(str);
                    }
                    return null;
                }
            });
        } else if (Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(double[].class))) {
            map = SequencesKt.map(sequence, new Function1<String, Double>() { // from class: org.jetbrains.kotlin.utils.StringArgsConverter$tryConvertVararg$convertPrimitivesArray$7
                @Override // kotlin.jvm.functions.Function1
                public final Double invoke(String str) {
                    if (str != null) {
                        return kotlin.text.StringsKt.toDoubleOrNull(str);
                    }
                    return null;
                }
            });
        } else {
            map = Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(boolean[].class)) ? SequencesKt.map(sequence, new Function1<String, Boolean>() { // from class: org.jetbrains.kotlin.utils.StringArgsConverter$tryConvertVararg$convertPrimitivesArray$8
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(String str) {
                    if (str != null) {
                        return Boolean.valueOf(Boolean.parseBoolean(str));
                    }
                    return null;
                }
            }) : null;
        }
        if (map == null || (list = SequencesKt.toList(map)) == null) {
            return null;
        }
        if (list.contains(null)) {
            list = null;
        }
        if (list != null) {
            return list.toArray(new Object[0]);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.utils.ArgsConverter
    public ArgsConverter.Result tryConvertVararg(KParameter parameter, NamedArgument<? extends String> firstArg, Sequence<? extends NamedArgument<? extends String>> restArgs) {
        Object objConvertAnyArrayImpl;
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(firstArg, "firstArg");
        Intrinsics.checkNotNullParameter(restArgs, "restArgs");
        KType type = parameter.getType();
        if (JvmClassMappingKt.getJavaClass((KClass) KTypesJvm.getJvmErasure(type)).isArray()) {
            Sequence sequencePlus = SequencesKt.plus(SequencesKt.sequenceOf(firstArg.getValue()), SequencesKt.map(restArgs, new Function1<NamedArgument<? extends String>, String>() { // from class: org.jetbrains.kotlin.utils.StringArgsConverter$tryConvertVararg$argsSequence$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ String invoke(NamedArgument<? extends String> namedArgument) {
                    return invoke2((NamedArgument<String>) namedArgument);
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final String invoke2(NamedArgument<String> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getValue();
                }
            }));
            Object objTryConvertVararg$convertPrimitivesArray = tryConvertVararg$convertPrimitivesArray(type, sequencePlus);
            if (objTryConvertVararg$convertPrimitivesArray != null) {
                return new ArgsConverter.Result.Success(objTryConvertVararg$convertPrimitivesArray);
            }
            KTypeProjection kTypeProjection = (KTypeProjection) kotlin.collections.CollectionsKt.firstOrNull((List) type.getArguments());
            KType type2 = kTypeProjection != null ? kTypeProjection.getType() : null;
            KClassifier classifier = type2 != null ? type2.getClassifier() : null;
            if (!Intrinsics.areEqual(classifier, Reflection.getOrCreateKotlinClass(String.class))) {
                objConvertAnyArrayImpl = ParametersMapKt.convertAnyArrayImpl(classifier, sequencePlus);
            } else {
                objConvertAnyArrayImpl = SequencesKt.toList(sequencePlus).toArray(new String[0]);
            }
            if (objConvertAnyArrayImpl != null) {
                return new ArgsConverter.Result.Success(objConvertAnyArrayImpl);
            }
        }
        return ArgsConverter.Result.Failure.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.utils.ArgsConverter
    public ArgsConverter.Result tryConvertTail(KParameter parameter, NamedArgument<? extends String> firstArg, Sequence<? extends NamedArgument<? extends String>> restArgs) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(firstArg, "firstArg");
        Intrinsics.checkNotNullParameter(restArgs, "restArgs");
        return tryConvertVararg(parameter, firstArg, restArgs);
    }
}
