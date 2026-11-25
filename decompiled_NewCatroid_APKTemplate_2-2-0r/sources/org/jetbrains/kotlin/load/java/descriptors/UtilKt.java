package org.jetbrains.kotlin.load.java.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaStaticClassScope;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: util.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a0\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b\u001a\f\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b\u001a\f\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u000e\u001a\n\u0010\u000f\u001a\u00020\u0010*\u00020\u000b¨\u0006\u0011"}, d2 = {"copyValueParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "newValueParameterTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "oldValueParameters", "newOwner", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getImplClassNameForDeserialized", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DescriptorWithContainerSource;", "getParentJavaStaticClassScope", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaStaticClassScope;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isFromJvmPackagePart", "", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilKt {
    public static final List<ValueParameterDescriptor> copyValueParameters(Collection<? extends KotlinType> newValueParameterTypes, Collection<? extends ValueParameterDescriptor> oldValueParameters, CallableDescriptor newOwner) {
        Intrinsics.checkNotNullParameter(newValueParameterTypes, "newValueParameterTypes");
        Intrinsics.checkNotNullParameter(oldValueParameters, "oldValueParameters");
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        newValueParameterTypes.size();
        oldValueParameters.size();
        List<Pair> listZip = CollectionsKt.zip(newValueParameterTypes, oldValueParameters);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
        for (Pair pair : listZip) {
            KotlinType kotlinType = (KotlinType) pair.component1();
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component2();
            int index = valueParameterDescriptor.getIndex();
            Annotations annotations = valueParameterDescriptor.getAnnotations();
            Name name2 = valueParameterDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            boolean zDeclaresDefaultValue = valueParameterDescriptor.declaresDefaultValue();
            boolean isCrossinline = valueParameterDescriptor.getIsCrossinline();
            boolean isNoinline = valueParameterDescriptor.getIsNoinline();
            KotlinType arrayElementType = valueParameterDescriptor.getVarargElementType() != null ? DescriptorUtilsKt.getModule(newOwner).getBuiltIns().getArrayElementType(kotlinType) : null;
            SourceElement source = valueParameterDescriptor.getSourceElement();
            Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
            arrayList.add(new ValueParameterDescriptorImpl(newOwner, null, index, annotations, name2, kotlinType, zDeclaresDefaultValue, isCrossinline, isNoinline, arrayElementType, source));
        }
        return arrayList;
    }

    public static final LazyJavaStaticClassScope getParentJavaStaticClassScope(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        ClassDescriptor superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(classDescriptor);
        if (superClassNotAny == null) {
            return null;
        }
        MemberScope staticScope = superClassNotAny.getStaticScope();
        LazyJavaStaticClassScope lazyJavaStaticClassScope = staticScope instanceof LazyJavaStaticClassScope ? (LazyJavaStaticClassScope) staticScope : null;
        return lazyJavaStaticClassScope == null ? getParentJavaStaticClassScope(superClassNotAny) : lazyJavaStaticClassScope;
    }

    public static final JvmClassName getImplClassNameForDeserialized(DescriptorWithContainerSource descriptorWithContainerSource) {
        Intrinsics.checkNotNullParameter(descriptorWithContainerSource, "<this>");
        DeserializedContainerSource containerSource = descriptorWithContainerSource.getContainerSource();
        JvmPackagePartSource jvmPackagePartSource = containerSource instanceof JvmPackagePartSource ? (JvmPackagePartSource) containerSource : null;
        if (jvmPackagePartSource != null) {
            return jvmPackagePartSource.getClassName();
        }
        return null;
    }

    public static final boolean isFromJvmPackagePart(DescriptorWithContainerSource descriptorWithContainerSource) {
        Intrinsics.checkNotNullParameter(descriptorWithContainerSource, "<this>");
        return descriptorWithContainerSource.getContainerSource() instanceof JvmPackagePartSource;
    }
}
