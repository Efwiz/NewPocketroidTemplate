package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FunctionsFromAny.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J2\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00042\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\u0014J\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u000eJ\u000e\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u000eJ\u000e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u000eJ\u0018\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0004H\u0002J*\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00042\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00110\u00142\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\u0014J8\u0010\u001d\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00110\u00142\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\u00142\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u001b0\u001fH\u0002J*\u0010!\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00042\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00110\u00142\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\u0014J*\u0010\"\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00042\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00110\u00142\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\u0014R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/resolve/FunctionsFromAny;", "", "()V", "EQUALS_METHOD_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getEQUALS_METHOD_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "HASH_CODE_METHOD_NAME", "getHASH_CODE_METHOD_NAME", "TO_STRING_METHOD_NAME", "getTO_STRING_METHOD_NAME", "addFunctionFromAnyIfNeeded", "", "thisDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "result", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "name", "fromSupertypes", "", "createEqualsFunctionDescriptor", "classDescriptor", "createHashCodeFunctionDescriptor", "createToStringFunctionDescriptor", "doCreateFunctionFromAny", "shouldAddEquals", "", "declaredFunctions", "shouldAddFunctionFromAny", "checkParameters", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "shouldAddHashCode", "shouldAddToString", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionsFromAny {
    public static final FunctionsFromAny INSTANCE = new FunctionsFromAny();
    private static final Name EQUALS_METHOD_NAME = OperatorNameConventions.EQUALS;
    private static final Name HASH_CODE_METHOD_NAME = OperatorNameConventions.HASH_CODE;
    private static final Name TO_STRING_METHOD_NAME = OperatorNameConventions.TO_STRING;

    private FunctionsFromAny() {
    }

    public final Name getEQUALS_METHOD_NAME() {
        return EQUALS_METHOD_NAME;
    }

    public final Name getHASH_CODE_METHOD_NAME() {
        return HASH_CODE_METHOD_NAME;
    }

    public final Name getTO_STRING_METHOD_NAME() {
        return TO_STRING_METHOD_NAME;
    }

    public final void addFunctionFromAnyIfNeeded(ClassDescriptor thisDescriptor, Collection<SimpleFunctionDescriptor> result, Name name2, Collection<? extends SimpleFunctionDescriptor> fromSupertypes) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(fromSupertypes, "fromSupertypes");
        if (shouldAddEquals(name2, result, fromSupertypes)) {
            result.add(createEqualsFunctionDescriptor(thisDescriptor));
        }
        if (shouldAddHashCode(name2, result, fromSupertypes)) {
            result.add(createHashCodeFunctionDescriptor(thisDescriptor));
        }
        if (shouldAddToString(name2, result, fromSupertypes)) {
            result.add(createToStringFunctionDescriptor(thisDescriptor));
        }
    }

    public final boolean shouldAddEquals(Name name2, Collection<? extends SimpleFunctionDescriptor> declaredFunctions, Collection<? extends SimpleFunctionDescriptor> fromSupertypes) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(declaredFunctions, "declaredFunctions");
        Intrinsics.checkNotNullParameter(fromSupertypes, "fromSupertypes");
        return Intrinsics.areEqual(name2, EQUALS_METHOD_NAME) && shouldAddFunctionFromAny(declaredFunctions, fromSupertypes, new Function1<FunctionDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.FunctionsFromAny.shouldAddEquals.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FunctionDescriptor function) {
                Intrinsics.checkNotNullParameter(function, "function");
                List<ValueParameterDescriptor> valueParameters = function.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                return Boolean.valueOf(valueParameters.size() == 1 && KotlinBuiltIns.isNullableAny(((ValueParameterDescriptor) CollectionsKt.first((List) valueParameters)).getType()));
            }
        });
    }

    public final boolean shouldAddHashCode(Name name2, Collection<? extends SimpleFunctionDescriptor> declaredFunctions, Collection<? extends SimpleFunctionDescriptor> fromSupertypes) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(declaredFunctions, "declaredFunctions");
        Intrinsics.checkNotNullParameter(fromSupertypes, "fromSupertypes");
        return Intrinsics.areEqual(name2, HASH_CODE_METHOD_NAME) && shouldAddFunctionFromAny(declaredFunctions, fromSupertypes, new Function1<FunctionDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.FunctionsFromAny.shouldAddHashCode.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FunctionDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getValueParameters().isEmpty());
            }
        });
    }

    public final boolean shouldAddToString(Name name2, Collection<? extends SimpleFunctionDescriptor> declaredFunctions, Collection<? extends SimpleFunctionDescriptor> fromSupertypes) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(declaredFunctions, "declaredFunctions");
        Intrinsics.checkNotNullParameter(fromSupertypes, "fromSupertypes");
        return Intrinsics.areEqual(name2, TO_STRING_METHOD_NAME) && shouldAddFunctionFromAny(declaredFunctions, fromSupertypes, new Function1<FunctionDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.FunctionsFromAny.shouldAddToString.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FunctionDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getValueParameters().isEmpty());
            }
        });
    }

    public final SimpleFunctionDescriptor createEqualsFunctionDescriptor(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return doCreateFunctionFromAny(classDescriptor, EQUALS_METHOD_NAME);
    }

    public final SimpleFunctionDescriptor createHashCodeFunctionDescriptor(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return doCreateFunctionFromAny(classDescriptor, HASH_CODE_METHOD_NAME);
    }

    public final SimpleFunctionDescriptor createToStringFunctionDescriptor(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return doCreateFunctionFromAny(classDescriptor, TO_STRING_METHOD_NAME);
    }

    private final SimpleFunctionDescriptor doCreateFunctionFromAny(ClassDescriptor classDescriptor, Name name2) {
        ClassDescriptor classDescriptor2 = classDescriptor;
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(classDescriptor2, Annotations.INSTANCE.getEMPTY(), name2, CallableMemberDescriptor.Kind.SYNTHESIZED, classDescriptor.getSourceElement());
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImplCreate, "create(...)");
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) CollectionsKt.single(DescriptorUtilsKt.getBuiltIns(classDescriptor2).getAny().getMemberScope(CollectionsKt.emptyList()).getContributedFunctions(name2, NoLookupLocation.FROM_BUILTINS));
        ReceiverParameterDescriptor thisAsReceiverParameter = classDescriptor.getThisAsReceiverParameter();
        List<ReceiverParameterDescriptor> listEmptyList = CollectionsKt.emptyList();
        List<TypeParameterDescriptor> typeParameters = simpleFunctionDescriptor.getTypeParameters();
        List<ValueParameterDescriptor> valueParameters = simpleFunctionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ValueParameterDescriptor valueParameterDescriptor : list) {
            Name name3 = valueParameterDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            arrayList.add(valueParameterDescriptor.mo7204copy(simpleFunctionDescriptorImplCreate, name3, valueParameterDescriptor.getIndex()));
        }
        simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, thisAsReceiverParameter, listEmptyList, (List<? extends TypeParameterDescriptor>) typeParameters, (List<ValueParameterDescriptor>) arrayList, simpleFunctionDescriptor.getReturnType(), Modality.OPEN, DescriptorVisibilities.PUBLIC);
        return simpleFunctionDescriptorImplCreate;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x003b  */
    /* JADX WARN: Code duplicated, block: B:21:0x0045  */
    /* JADX WARN: Code duplicated, block: B:29:0x0057 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:32:0x003f A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    private final boolean shouldAddFunctionFromAny(Collection<? extends SimpleFunctionDescriptor> declaredFunctions, Collection<? extends SimpleFunctionDescriptor> fromSupertypes, Function1<? super FunctionDescriptor, Boolean> checkParameters) {
        Collection<? extends SimpleFunctionDescriptor> collection;
        Collection<? extends SimpleFunctionDescriptor> collection2 = declaredFunctions;
        if ((collection2 instanceof Collection) && collection2.isEmpty()) {
            collection = fromSupertypes;
            if (collection instanceof Collection) {
                for (SimpleFunctionDescriptor simpleFunctionDescriptor : collection) {
                    if (checkParameters.invoke(simpleFunctionDescriptor).booleanValue()) {
                    }
                }
            } else {
                while (r2.hasNext()) {
                    if (checkParameters.invoke(simpleFunctionDescriptor).booleanValue()) {
                    }
                }
            }
            return true;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            if (checkParameters.invoke(it.next()).booleanValue()) {
            }
        }
        collection = fromSupertypes;
        if ((collection instanceof Collection) || !collection.isEmpty()) {
            while (r2.hasNext()) {
                if (checkParameters.invoke(simpleFunctionDescriptor).booleanValue() || simpleFunctionDescriptor.getModality() != Modality.FINAL) {
                }
            }
        }
        return true;
        return false;
    }
}
