package org.jetbrains.kotlin.resolve.calls.results;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;

/* JADX INFO: compiled from: FlatSignatureUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aG\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\u00020\b2\u0006\u0010\t\u001a\u0002H\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000fH\u0007¢\u0006\u0004\b\u0011\u0010\u0012\u001aC\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\u00020\b2\u0006\u0010\t\u001a\u0002H\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f¢\u0006\u0002\u0010\u0012\u001a'\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0006\"\b\b\u0000\u0010\u0014*\u00020\u000b*\u00020\b2\u0006\u0010\n\u001a\u0002H\u0014¢\u0006\u0002\u0010\u0015\u001a'\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0006\"\b\b\u0000\u0010\u0014*\u00020\u000b*\u00020\b2\u0006\u0010\n\u001a\u0002H\u0014¢\u0006\u0002\u0010\u0015\u001aC\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\u00020\b2\u0006\u0010\t\u001a\u0002H\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b¢\u0006\u0002\u0010\u001c\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u001d"}, d2 = {"argumentValueType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getArgumentValueType", "(Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;)Lorg/jetbrains/kotlin/types/KotlinType;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature$Companion;", "origin", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "numDefaults", "", "parameterTypes", "", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeWithConversion;", "createWithConvertedTypes", "(Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature$Companion;Ljava/lang/Object;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;ILjava/util/List;)Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "createForPossiblyShadowedExtension", "D", "(Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature$Companion;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "createFromCallableDescriptor", "createFromReflectionType", "hasBoundExtensionReceiver", "", "reflectionType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature$Companion;Ljava/lang/Object;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;IZLorg/jetbrains/kotlin/types/UnwrappedType;)Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FlatSignatureUtilsKt {
    public static final <T> FlatSignature<T> createFromReflectionType(FlatSignature.Companion companion, T t, CallableDescriptor descriptor, int i, boolean z, UnwrappedType reflectionType) {
        ArrayList arrayListEmptyList;
        boolean z2;
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(reflectionType, "reflectionType");
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        KotlinType type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = contextReceiverParameters.iterator();
        while (it.hasNext()) {
            KotlinType type2 = ((ReceiverParameterDescriptor) it.next()).getType();
            if (type2 != null) {
                arrayList.add(type2);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (descriptor instanceof VariableDescriptor) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else {
            List<TypeProjection> valueParameterTypesFromCallableReflectionType = FunctionTypesKt.getValueParameterTypesFromCallableReflectionType(reflectionType, (type == null || z) ? false : true);
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameterTypesFromCallableReflectionType, 10));
            Iterator<T> it2 = valueParameterTypesFromCallableReflectionType.iterator();
            while (it2.hasNext()) {
                arrayList3.add(((TypeProjection) it2.next()).getNullableAnyType());
            }
            arrayListEmptyList = arrayList3;
        }
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) arrayList2, (Iterable) CollectionsKt.listOfNotNull(type)), arrayListEmptyList);
        boolean z3 = type != null;
        int size = arrayList2.size();
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list2 = valueParameters;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it3 = list2.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    z2 = false;
                    break;
                }
                if (((ValueParameterDescriptor) it3.next()).getVarargElementType() != null) {
                    z2 = true;
                    break;
                }
            }
        } else {
            z2 = false;
            break;
        }
        return new FlatSignature<>(t, list, (List<? extends KotlinTypeMarker>) listPlus, z3, size, z2, i, (descriptor instanceof MemberDescriptor) && ((MemberDescriptor) descriptor).get$isExpect(), descriptor instanceof SyntheticMemberDescriptor);
    }

    public static final <T> FlatSignature<T> createWithConvertedTypes(FlatSignature.Companion companion, T t, CallableDescriptor descriptor, int i, List<TypeWithConversion> parameterTypes) {
        boolean z;
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        KotlinType type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = contextReceiverParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(new TypeWithConversion(((ReceiverParameterDescriptor) it.next()).getType(), null, 2, null));
        }
        ArrayList arrayList2 = arrayList;
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        ArrayList arrayList3 = arrayList2;
        List listListOf = type != null ? CollectionsKt.listOf(new TypeWithConversion(type, null, 2, null)) : null;
        if (listListOf == null) {
            listListOf = CollectionsKt.emptyList();
        }
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) arrayList3, (Iterable) listListOf), (Iterable) parameterTypes);
        boolean z2 = type != null;
        int size = arrayList2.size();
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                if (((ValueParameterDescriptor) it2.next()).getVarargElementType() != null) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        boolean z3 = (descriptor instanceof MemberDescriptor) && ((MemberDescriptor) descriptor).get$isExpect();
        Intrinsics.checkNotNull(typeParameters);
        return new FlatSignature<>(t, typeParameters, z2, size, z, i, z3, descriptor instanceof SyntheticMemberDescriptor, (List<TypeWithConversion>) listPlus);
    }

    public static final <T> FlatSignature<T> create(FlatSignature.Companion companion, T t, CallableDescriptor descriptor, int i, List<? extends KotlinType> parameterTypes) {
        boolean z;
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        KotlinType type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = contextReceiverParameters.iterator();
        while (it.hasNext()) {
            KotlinType type2 = ((ReceiverParameterDescriptor) it.next()).getType();
            if (type2 != null) {
                arrayList.add(type2);
            }
        }
        ArrayList arrayList2 = arrayList;
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        List listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) arrayList2, (Iterable) CollectionsKt.listOfNotNull(type)), (Iterable) parameterTypes);
        boolean z2 = type != null;
        int size = arrayList2.size();
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list2 = valueParameters;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it2 = list2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                if (((ValueParameterDescriptor) it2.next()).getVarargElementType() != null) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        return new FlatSignature<>(t, list, (List<? extends KotlinTypeMarker>) listPlus, z2, size, z, i, (descriptor instanceof MemberDescriptor) && ((MemberDescriptor) descriptor).get$isExpect(), descriptor instanceof SyntheticMemberDescriptor);
    }

    public static final <D extends CallableDescriptor> FlatSignature<D> createFromCallableDescriptor(FlatSignature.Companion companion, D descriptor) {
        boolean z;
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list2 = contextReceiverParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((ReceiverParameterDescriptor) it.next()).getType());
        }
        ArrayList arrayList2 = arrayList;
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        List listPlus = CollectionsKt.plus((Collection) arrayList2, (Iterable) CollectionsKt.listOfNotNull(extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null));
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list3 = valueParameters;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        for (ValueParameterDescriptor valueParameterDescriptor : list3) {
            Intrinsics.checkNotNull(valueParameterDescriptor);
            arrayList3.add(getArgumentValueType(valueParameterDescriptor));
        }
        List listPlus2 = CollectionsKt.plus((Collection) listPlus, (Iterable) arrayList3);
        ReceiverParameterDescriptor extensionReceiverParameter2 = descriptor.getExtensionReceiverParameter();
        boolean z2 = (extensionReceiverParameter2 != null ? extensionReceiverParameter2.getType() : null) != null;
        int size = descriptor.getContextReceiverParameters().size();
        List<ValueParameterDescriptor> valueParameters2 = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        List<ValueParameterDescriptor> list4 = valueParameters2;
        if (!(list4 instanceof Collection) || !list4.isEmpty()) {
            Iterator<T> it2 = list4.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                if (((ValueParameterDescriptor) it2.next()).getVarargElementType() != null) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        return new FlatSignature<>(descriptor, list, (List<? extends KotlinTypeMarker>) listPlus2, z2, size, z, 0, (descriptor instanceof MemberDescriptor) && ((MemberDescriptor) descriptor).get$isExpect(), descriptor instanceof SyntheticMemberDescriptor);
    }

    public static final <D extends CallableDescriptor> FlatSignature<D> createForPossiblyShadowedExtension(FlatSignature.Companion companion, D descriptor) {
        boolean z;
        int i;
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list2 = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (ValueParameterDescriptor valueParameterDescriptor : list2) {
            Intrinsics.checkNotNull(valueParameterDescriptor);
            arrayList.add(getArgumentValueType(valueParameterDescriptor));
        }
        ArrayList arrayList2 = arrayList;
        List<ValueParameterDescriptor> valueParameters2 = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        List<ValueParameterDescriptor> list3 = valueParameters2;
        if (!(list3 instanceof Collection) || !list3.isEmpty()) {
            Iterator<T> it = list3.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (((ValueParameterDescriptor) it.next()).getVarargElementType() != null) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        List<ValueParameterDescriptor> valueParameters3 = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters3, "getValueParameters(...)");
        List<ValueParameterDescriptor> list4 = valueParameters3;
        if ((list4 instanceof Collection) && list4.isEmpty()) {
            i = 0;
        } else {
            int i2 = 0;
            for (ValueParameterDescriptor valueParameterDescriptor2 : list4) {
                Intrinsics.checkNotNull(valueParameterDescriptor2);
                if (ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor2) && (i2 = i2 + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
            i = i2;
        }
        return new FlatSignature<>(descriptor, (Collection<? extends TypeParameterMarker>) list, (List<? extends KotlinTypeMarker>) arrayList2, false, 0, z, i, (descriptor instanceof MemberDescriptor) && ((MemberDescriptor) descriptor).get$isExpect(), descriptor instanceof SyntheticMemberDescriptor);
    }

    public static final KotlinType getArgumentValueType(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
        if (varargElementType != null) {
            return varargElementType;
        }
        KotlinType type = valueParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return type;
    }
}
