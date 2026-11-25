package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DataClassDescriptorResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\nJ$\u0010\u0015\u001a\u00020\b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00172\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0004R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DataClassDescriptorResolver;", "", "()V", "COPY_METHOD_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getCOPY_METHOD_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "createComponentFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "parameterIndex", "", "property", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "createComponentName", "index", "createCopyFunctionDescriptor", "constructorParameters", "", "getComponentIndex", "componentName", "", "isComponentLike", "", "name", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataClassDescriptorResolver {
    private static final Name COPY_METHOD_NAME;
    public static final DataClassDescriptorResolver INSTANCE = new DataClassDescriptorResolver();

    private DataClassDescriptorResolver() {
    }

    static {
        Name nameIdentifier = Name.identifier(ConfigConstants.CONFIG_RENAMELIMIT_COPY);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        COPY_METHOD_NAME = nameIdentifier;
    }

    public final Name getCOPY_METHOD_NAME() {
        return COPY_METHOD_NAME;
    }

    public final Name createComponentName(int index) {
        return DataClassResolver.INSTANCE.createComponentName(index);
    }

    public final int getComponentIndex(String componentName) {
        Intrinsics.checkNotNullParameter(componentName, "componentName");
        return DataClassResolver.INSTANCE.getComponentIndex(componentName);
    }

    public final boolean isComponentLike(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return DataClassResolver.INSTANCE.isComponentLike(name2);
    }

    public final SimpleFunctionDescriptor createComponentFunctionDescriptor(int parameterIndex, PropertyDescriptor property, ValueParameterDescriptor parameter, ClassDescriptor classDescriptor, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(classDescriptor, Annotations.INSTANCE.getEMPTY(), createComponentName(parameterIndex), CallableMemberDescriptor.Kind.SYNTHESIZED, parameter.getSourceElement());
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImplCreate, "create(...)");
        simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, classDescriptor.getThisAsReceiverParameter(), CollectionsKt.emptyList(), CollectionsKt.emptyList(), CollectionsKt.emptyList(), property.getType(), Modality.FINAL, property.getVisibilityImpl());
        simpleFunctionDescriptorImplCreate.setOperator(true);
        trace.record(BindingContext.DATA_CLASS_COMPONENT_FUNCTION, parameter, simpleFunctionDescriptorImplCreate);
        return simpleFunctionDescriptorImplCreate;
    }

    public final SimpleFunctionDescriptor createCopyFunctionDescriptor(Collection<? extends ValueParameterDescriptor> constructorParameters, ClassDescriptor classDescriptor, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(constructorParameters, "constructorParameters");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(classDescriptor, Annotations.INSTANCE.getEMPTY(), COPY_METHOD_NAME, CallableMemberDescriptor.Kind.SYNTHESIZED, classDescriptor.getSourceElement());
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImplCreate, "create(...)");
        ArrayList arrayList = new ArrayList();
        for (ValueParameterDescriptor valueParameterDescriptor : constructorParameters) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) trace.getContext().get(BindingContext.VALUE_PARAMETER_AS_PROPERTY, valueParameterDescriptor);
            boolean z = propertyDescriptor != null;
            int index = valueParameterDescriptor.getIndex();
            Annotations annotations = valueParameterDescriptor.getAnnotations();
            Name name2 = valueParameterDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            KotlinType type = valueParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            boolean isCrossinline = valueParameterDescriptor.getIsCrossinline();
            boolean isNoinline = valueParameterDescriptor.getIsNoinline();
            KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
            SourceElement source = valueParameterDescriptor.getSourceElement();
            Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
            ValueParameterDescriptorImpl valueParameterDescriptorImpl = new ValueParameterDescriptorImpl(simpleFunctionDescriptorImplCreate, null, index, annotations, name2, type, z, isCrossinline, isNoinline, varargElementType, source);
            arrayList.add(valueParameterDescriptorImpl);
            if (z) {
                trace.record(BindingContext.VALUE_PARAMETER_AS_PROPERTY, valueParameterDescriptorImpl, propertyDescriptor);
            }
        }
        simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, classDescriptor.getThisAsReceiverParameter(), CollectionsKt.emptyList(), CollectionsKt.emptyList(), (List<ValueParameterDescriptor>) arrayList, (KotlinType) classDescriptor.getDefaultType(), Modality.FINAL, DescriptorVisibilities.PUBLIC);
        trace.record(BindingContext.DATA_CLASS_COPY_FUNCTION, classDescriptor, simpleFunctionDescriptorImplCreate);
        return simpleFunctionDescriptorImplCreate;
    }
}
