package org.jetbrains.kotlin.resolve.calls.util;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: functionTypeResolveUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001\u001a\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"createValueParametersForInvokeInFunctionType", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "parameterTypes", "Lorg/jetbrains/kotlin/types/TypeProjection;", "getValueParametersCountFromFunctionType", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionTypeResolveUtilsKt {
    public static final List<ValueParameterDescriptor> createValueParametersForInvokeInFunctionType(FunctionDescriptor functionDescriptor, List<? extends TypeProjection> parameterTypes) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        List<? extends TypeProjection> list = parameterTypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Annotations empty = Annotations.INSTANCE.getEMPTY();
            Name nameIdentifier = Name.identifier("p" + i2);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            KotlinType nullableAnyType = ((TypeProjection) obj).getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
            SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
            arrayList.add(new ValueParameterDescriptorImpl(functionDescriptor, null, i, empty, nameIdentifier, nullableAnyType, false, false, false, null, NO_SOURCE));
            i = i2;
        }
        return arrayList;
    }

    public static final int getValueParametersCountFromFunctionType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        FunctionTypesKt.isBuiltinFunctionalType(type);
        return (type.getArguments().size() - (FunctionTypesKt.isBuiltinExtensionFunctionalType(type) ? 1 : 0)) - 1;
    }
}
