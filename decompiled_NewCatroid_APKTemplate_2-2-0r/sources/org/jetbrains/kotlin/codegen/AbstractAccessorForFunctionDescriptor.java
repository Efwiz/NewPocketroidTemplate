package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AbstractAccessorForFunctionDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0004J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0004¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/AbstractAccessorForFunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/name/Name;)V", "copyTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "copyValueParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AbstractAccessorForFunctionDescriptor extends SimpleFunctionDescriptorImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractAccessorForFunctionDescriptor(DeclarationDescriptor containingDeclaration, Name name2) {
        super(containingDeclaration, null, Annotations.INSTANCE.getEMPTY(), name2, CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE);
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    protected final List<TypeParameterDescriptor> copyTypeParameters(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            TypeParameterDescriptorImpl typeParameterDescriptorImplCreateForFurtherModification = TypeParameterDescriptorImpl.createForFurtherModification(this, typeParameterDescriptor.getAnnotations(), typeParameterDescriptor.isReified(), typeParameterDescriptor.getVariance(), typeParameterDescriptor.getShortName(), typeParameterDescriptor.getIndex(), SourceElement.NO_SOURCE, typeParameterDescriptor.getStorageManager());
            Iterator<KotlinType> it = typeParameterDescriptor.getUpperBounds().iterator();
            while (it.hasNext()) {
                typeParameterDescriptorImplCreateForFurtherModification.addUpperBound(it.next());
            }
            typeParameterDescriptorImplCreateForFurtherModification.setInitialized();
            arrayList.add(typeParameterDescriptorImplCreateForFurtherModification);
        }
        return arrayList;
    }

    protected final List<ValueParameterDescriptor> copyValueParameters(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ValueParameterDescriptor valueParameterDescriptor : list) {
            Name name2 = valueParameterDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            arrayList.add(valueParameterDescriptor.mo7204copy(this, name2, valueParameterDescriptor.getIndex()));
        }
        return arrayList;
    }
}
