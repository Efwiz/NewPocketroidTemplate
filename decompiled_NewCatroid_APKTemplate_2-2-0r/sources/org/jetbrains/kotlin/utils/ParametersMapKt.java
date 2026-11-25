package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.jvm.KTypesJvm;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: parametersMap.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a-\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0006H\u0082\b\u001a*\u0010\u0007\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0006H\u0002\u001a\"\u0010\b\u001a\u0004\u0018\u00010\u00012\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u001aL\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u000e\"\u0004\b\u0000\u0010\u00022\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00130\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015H\u0002\u001a2\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u000e2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000b\u001a@\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u000e2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u001c\u0010\u0005\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00170\u000b\u001a0\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u000e2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u001a\f\u0010\u0019\u001a\u00020\u001a*\u00020\u001bH\u0002\u001a2\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u001a0\u001fH\u0002¨\u0006 "}, d2 = {"convertAnyArray", "", ExifInterface.GPS_DIRECTION_TRUE, "classifier", "Lkotlin/reflect/KClassifier;", "args", "Lkotlin/sequences/Sequence;", "convertAnyArrayImpl", "tryConstructClassFromStringArgs", "clazz", "Ljava/lang/Class;", "", "", "tryCreateCallableMapping", "", "Lkotlin/reflect/KParameter;", "callable", "Lkotlin/reflect/KCallable;", "", "Lorg/jetbrains/kotlin/utils/NamedArgument;", "converter", "Lorg/jetbrains/kotlin/utils/ArgsConverter;", "tryCreateCallableMappingFromNamedArgs", "Lkotlin/Pair;", "tryCreateCallableMappingFromStringArgs", "allowsNulls", "", "Lkotlin/reflect/KType;", "sequenceUntil", "Lorg/jetbrains/kotlin/utils/LookAheadIterator;", "predicate", "Lkotlin/Function1;", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ParametersMapKt {

    /* JADX INFO: compiled from: parametersMap.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ArgsTraversalState.values().length];
            try {
                iArr[ArgsTraversalState.UNNAMED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ArgsTraversalState.NAMED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ArgsTraversalState.TAIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final Object tryConstructClassFromStringArgs(Class<?> clazz, List<String> args) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(args, "args");
        try {
            return clazz.getConstructor(String[].class).newInstance(args.toArray(new String[0]));
        } catch (NoSuchMethodException unused) {
            Iterator it = JvmClassMappingKt.getKotlinClass(clazz).getConstructors().iterator();
            while (it.hasNext()) {
                KFunction kFunction = (KFunction) it.next();
                Map<KParameter, ? extends Object> mapTryCreateCallableMappingFromStringArgs = tryCreateCallableMappingFromStringArgs(kFunction, args);
                if (mapTryCreateCallableMappingFromStringArgs != null) {
                    try {
                        return kFunction.callBy(mapTryCreateCallableMappingFromStringArgs);
                    } catch (Exception unused2) {
                        continue;
                    }
                }
            }
            return null;
        }
    }

    public static final Map<KParameter, Object> tryCreateCallableMapping(KCallable<?> callable, List<? extends Object> args) {
        Intrinsics.checkNotNullParameter(callable, "callable");
        Intrinsics.checkNotNullParameter(args, "args");
        List<? extends Object> list = args;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new NamedArgument(null, it.next()));
        }
        return tryCreateCallableMapping(callable, arrayList.iterator(), new AnyArgsConverter());
    }

    public static final Map<KParameter, Object> tryCreateCallableMappingFromStringArgs(KCallable<?> callable, List<String> args) {
        Intrinsics.checkNotNullParameter(callable, "callable");
        Intrinsics.checkNotNullParameter(args, "args");
        List<String> list = args;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new NamedArgument(null, (String) it.next()));
        }
        return tryCreateCallableMapping(callable, arrayList.iterator(), new StringArgsConverter());
    }

    public static final Map<KParameter, Object> tryCreateCallableMappingFromNamedArgs(KCallable<?> callable, List<? extends Pair<String, ? extends Object>> args) {
        Intrinsics.checkNotNullParameter(callable, "callable");
        Intrinsics.checkNotNullParameter(args, "args");
        List<? extends Pair<String, ? extends Object>> list = args;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            arrayList.add(new NamedArgument((String) pair.getFirst(), pair.getSecond()));
        }
        return tryCreateCallableMapping(callable, arrayList.iterator(), new AnyArgsConverter());
    }

    private static final <T> Map<KParameter, Object> tryCreateCallableMapping(KCallable<?> kCallable, Iterator<? extends NamedArgument<? extends T>> it, ArgsConverter<T> argsConverter) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ArgsTraversalState argsTraversalState = ArgsTraversalState.UNNAMED;
        List mutableList = kotlin.collections.CollectionsKt.toMutableList((Collection) kCallable.getParameters());
        LookAheadIterator lookAheadIterator = new LookAheadIterator(it);
        while (lookAheadIterator.hasNext()) {
            if (mutableList.isEmpty()) {
                return null;
            }
            NamedArgument<? extends T> namedArgument = (NamedArgument) lookAheadIterator.next();
            int i = WhenMappings.$EnumSwitchMapping$0[argsTraversalState.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    if (namedArgument.getName() == null) {
                        argsTraversalState = ArgsTraversalState.TAIL;
                    }
                } else if (i == 3 && namedArgument.getName() != null) {
                    throw new IllegalArgumentException("Illegal mix of named and unnamed arguments");
                }
            } else if (namedArgument.getName() != null) {
                argsTraversalState = ArgsTraversalState.NAMED;
            }
            int i2 = WhenMappings.$EnumSwitchMapping$0[argsTraversalState.ordinal()];
            int i3 = 0;
            if (i2 == 1) {
                KParameter kParameter = (KParameter) mutableList.remove(0);
                ArgsConverter.Result resultTryConvertSingle = argsConverter.tryConvertSingle(kParameter, namedArgument);
                if (resultTryConvertSingle instanceof ArgsConverter.Result.Success) {
                    ArgsConverter.Result.Success success = (ArgsConverter.Result.Success) resultTryConvertSingle;
                    if (success.getV() == null && !allowsNulls(kParameter.getType())) {
                        return null;
                    }
                    linkedHashMap.put(kParameter, success.getV());
                } else {
                    if (JvmClassMappingKt.getJavaClass((KClass) KTypesJvm.getJvmErasure(kParameter.getType())).isArray()) {
                        ArgsConverter.Result resultTryConvertVararg = argsConverter.tryConvertVararg(kParameter, namedArgument, sequenceUntil(lookAheadIterator, new Function1<NamedArgument<? extends T>, Boolean>() { // from class: org.jetbrains.kotlin.utils.ParametersMapKt$tryCreateCallableMapping$unnamed$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(NamedArgument<? extends T> it2) {
                                Intrinsics.checkNotNullParameter(it2, "it");
                                return Boolean.valueOf(it2.getName() != null);
                            }
                        }));
                        if (resultTryConvertVararg instanceof ArgsConverter.Result.Success) {
                            linkedHashMap.put(kParameter, ((ArgsConverter.Result.Success) resultTryConvertVararg).getV());
                        }
                    }
                    return null;
                }
            } else {
                if (i2 == 2) {
                    namedArgument.getName();
                    Iterator it2 = mutableList.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            i3 = -1;
                            break;
                        }
                        if (Intrinsics.areEqual(((KParameter) it2.next()).getName(), namedArgument.getName())) {
                            break;
                        }
                        i3++;
                    }
                    Integer numValueOf = Integer.valueOf(i3);
                    if (numValueOf.intValue() < 0) {
                        numValueOf = null;
                    }
                    if (numValueOf != null) {
                        KParameter kParameter2 = (KParameter) mutableList.remove(numValueOf.intValue());
                        ArgsConverter.Result resultTryConvertSingle2 = argsConverter.tryConvertSingle(kParameter2, namedArgument);
                        if (resultTryConvertSingle2 instanceof ArgsConverter.Result.Success) {
                            linkedHashMap.put(kParameter2, ((ArgsConverter.Result.Success) resultTryConvertSingle2).getV());
                        }
                    }
                    return null;
                }
                if (i2 != 3) {
                    continue;
                } else {
                    namedArgument.getName();
                    KParameter kParameter3 = (KParameter) mutableList.remove(kotlin.collections.CollectionsKt.getLastIndex(mutableList));
                    ArgsConverter.Result resultTryConvertTail = argsConverter.tryConvertTail(kParameter3, namedArgument, SequencesKt.asSequence(lookAheadIterator));
                    if (!(resultTryConvertTail instanceof ArgsConverter.Result.Success) || lookAheadIterator.hasNext()) {
                        return null;
                    }
                    linkedHashMap.put(kParameter3, ((ArgsConverter.Result.Success) resultTryConvertTail).getV());
                }
            }
        }
        List<KParameter> list = mutableList;
        if ((list instanceof Collection) && list.isEmpty()) {
            return linkedHashMap;
        }
        for (KParameter kParameter4 : list) {
            if (!kParameter4.isOptional() && !kParameter4.isVararg()) {
                return null;
            }
        }
        return linkedHashMap;
    }

    private static final boolean allowsNulls(KType kType) {
        if (!kType.isMarkedNullable()) {
            KClassifier classifier = kType.getClassifier();
            if (classifier instanceof KTypeParameter) {
                List<KType> upperBounds = ((KTypeParameter) classifier).getUpperBounds();
                if (!(upperBounds instanceof Collection) || !upperBounds.isEmpty()) {
                    Iterator<T> it = upperBounds.iterator();
                    while (it.hasNext()) {
                        if (allowsNulls((KType) it.next())) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> Object convertAnyArrayImpl(KClassifier kClassifier, Sequence<? extends T> sequence) {
        KClass kClass = kClassifier instanceof KClass ? (KClass) kClassifier : null;
        if (kClass == null) {
            return null;
        }
        List list = SequencesKt.toList(sequence);
        Object objNewInstance = Array.newInstance((Class<?>) JvmClassMappingKt.getJavaClass(kClass), list.size());
        int i = 0;
        for (T t : list) {
            int i2 = i + 1;
            if (i < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            try {
                Array.set(objNewInstance, i, t);
                i = i2;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
        return objNewInstance;
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: org.jetbrains.kotlin.utils.ParametersMapKt$sequenceUntil$1, reason: invalid class name */
    /* JADX INFO: compiled from: parametersMap.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.utils.ParametersMapKt$sequenceUntil$1", f = "parametersMap.kt", i = {0}, l = {320}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence"}, s = {"L$0"})
    static final class AnonymousClass1<T> extends RestrictedSuspendLambda implements Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1<T, Boolean> $predicate;
        final /* synthetic */ LookAheadIterator<T> $this_sequenceUntil;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(LookAheadIterator<T> lookAheadIterator, Function1<? super T, Boolean> function1, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$this_sequenceUntil = lookAheadIterator;
            this.$predicate = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_sequenceUntil, this.$predicate, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super T> sequenceScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            SequenceScope sequenceScope;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                sequenceScope = (SequenceScope) this.L$0;
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            while (this.$this_sequenceUntil.hasNext() && !((Boolean) this.$predicate.invoke(this.$this_sequenceUntil.nextWithoutConsuming())).booleanValue()) {
                this.L$0 = sequenceScope;
                this.label = 1;
                if (sequenceScope.yield(this.$this_sequenceUntil.next(), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }
    }

    private static final <T> Sequence<T> sequenceUntil(LookAheadIterator<T> lookAheadIterator, Function1<? super T, Boolean> function1) {
        return SequencesKt.sequence(new AnonymousClass1(lookAheadIterator, function1, null));
    }
}
