package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertySetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: fakeDescriptorsForReferences.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a)\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0002\u0010\u0006\u001a\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u001a\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u000b2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u001a \u0010\f\u001a\u00020\r*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0002\u001a\u001e\u0010\f\u001a\u0004\u0018\u00010\u0010*\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\rH\u0002\u001a\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u0015*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0010H\u0002¨\u0006\u0017"}, d2 = {"createFreeDescriptor", "D", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "descriptor", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lorg/jetbrains/kotlin/types/TypeApproximator;)Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "createFreeFakeLambdaDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "createFreeFakeLocalPropertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor;", "approximate", "", "builder", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$CopyBuilder;", "Lorg/jetbrains/kotlin/types/KotlinType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/UnwrappedType;", "toSuper", "substituteTopLevelType", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "newType", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FakeDescriptorsForReferencesKt {
    public static final FunctionDescriptor createFreeFakeLambdaDescriptor(FunctionDescriptor descriptor, TypeApproximator typeApproximator) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return (FunctionDescriptor) createFreeDescriptor(descriptor, typeApproximator);
    }

    private static final <D extends CallableMemberDescriptor> D createFreeDescriptor(D d, TypeApproximator typeApproximator) {
        CallableMemberDescriptor.CopyBuilder<? extends CallableMemberDescriptor> copyBuilderNewCopyBuilder = d.newCopyBuilder();
        Intrinsics.checkNotNull(copyBuilderNewCopyBuilder, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder<D of org.jetbrains.kotlin.codegen.FakeDescriptorsForReferencesKt.createFreeDescriptor>");
        ArrayList arrayList = new ArrayList(0);
        copyBuilderNewCopyBuilder.setTypeParameters(arrayList);
        for (DeclarationDescriptor containingDeclaration = d.getContainingModule(); containingDeclaration != null; containingDeclaration = containingDeclaration.getContainingModule()) {
            if (containingDeclaration instanceof ClassDescriptor) {
                arrayList.addAll(((ClassDescriptor) containingDeclaration).getDeclaredTypeParameters());
            } else if ((containingDeclaration instanceof CallableDescriptor) && !(containingDeclaration instanceof ConstructorDescriptor)) {
                arrayList.addAll(((CallableDescriptor) containingDeclaration).getTypeParameters());
            }
        }
        boolean zApproximate = typeApproximator != null ? approximate(typeApproximator, d, copyBuilderNewCopyBuilder) : false;
        if (arrayList.isEmpty() && !zApproximate) {
            return d;
        }
        D d2 = (D) copyBuilderNewCopyBuilder.build();
        Intrinsics.checkNotNull(d2);
        return d2;
    }

    private static final boolean approximate(TypeApproximator typeApproximator, CallableMemberDescriptor callableMemberDescriptor, CallableMemberDescriptor.CopyBuilder<?> copyBuilder) {
        boolean z;
        KotlinType kotlinTypeApproximate;
        KotlinType kotlinTypeApproximate2;
        KotlinType returnType = callableMemberDescriptor.getReturnType();
        if (returnType == null || (kotlinTypeApproximate2 = approximate(typeApproximator, returnType.unwrap(), true)) == null) {
            z = false;
        } else {
            copyBuilder.setReturnType(kotlinTypeApproximate2);
            z = true;
        }
        if (!(copyBuilder instanceof FunctionDescriptor.CopyBuilder)) {
            return z;
        }
        ReceiverParameterDescriptor extensionReceiverParameter = callableMemberDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null && (kotlinTypeApproximate = approximate(typeApproximator, extensionReceiverParameter.getType().unwrap(), false)) != null) {
            ((FunctionDescriptor.CopyBuilder) copyBuilder).setExtensionReceiverParameter(substituteTopLevelType(extensionReceiverParameter, kotlinTypeApproximate));
            z = true;
        }
        List<ValueParameterDescriptor> valueParameters = callableMemberDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        boolean z2 = false;
        for (ValueParameterDescriptorImpl valueParameterDescriptorImpl : list) {
            KotlinType kotlinTypeApproximate3 = approximate(typeApproximator, valueParameterDescriptorImpl.getType().unwrap(), false);
            if (kotlinTypeApproximate3 != null) {
                CallableDescriptor containingDeclaration = valueParameterDescriptorImpl.getContainingModule();
                ValueParameterDescriptor original = valueParameterDescriptorImpl.getOriginal();
                int index = valueParameterDescriptorImpl.getIndex();
                Annotations annotations = valueParameterDescriptorImpl.getAnnotations();
                Name name2 = valueParameterDescriptorImpl.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                boolean zDeclaresDefaultValue = valueParameterDescriptorImpl.declaresDefaultValue();
                boolean isCrossinline = valueParameterDescriptorImpl.getIsCrossinline();
                boolean isNoinline = valueParameterDescriptorImpl.getIsNoinline();
                KotlinType varargElementType = valueParameterDescriptorImpl.getVarargElementType();
                SourceElement source = valueParameterDescriptorImpl.getSourceElement();
                Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
                valueParameterDescriptorImpl = new ValueParameterDescriptorImpl(containingDeclaration, original, index, annotations, name2, kotlinTypeApproximate3, zDeclaresDefaultValue, isCrossinline, isNoinline, varargElementType, source);
                z2 = true;
            }
            arrayList.add(valueParameterDescriptorImpl);
        }
        ArrayList arrayList2 = arrayList;
        if (!z2) {
            return z;
        }
        ((FunctionDescriptor.CopyBuilder) copyBuilder).setValueParameters(arrayList2);
        return true;
    }

    private static final ReceiverParameterDescriptor substituteTopLevelType(ReceiverParameterDescriptor receiverParameterDescriptor, final KotlinType kotlinType) {
        return receiverParameterDescriptor.substitute(TypeSubstitutor.create(new TypeSubstitution() { // from class: org.jetbrains.kotlin.codegen.FakeDescriptorsForReferencesKt$substituteTopLevelType$wrappedSubstitution$1
            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo7475get(KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return null;
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public KotlinType prepareTopLevelType(KotlinType topLevelType, Variance position) {
                Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
                Intrinsics.checkNotNullParameter(position, "position");
                return kotlinType;
            }
        }));
    }

    private static final KotlinType approximate(TypeApproximator typeApproximator, UnwrappedType unwrappedType, boolean z) {
        if (unwrappedType.getArguments().isEmpty() && unwrappedType.getConstructor().isDenotable()) {
            return null;
        }
        if (z) {
            return typeApproximator.approximateToSuperType(unwrappedType, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.PublicDeclaration.SaveAnonymousTypes.INSTANCE);
        }
        return typeApproximator.approximateToSubType(unwrappedType, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.PublicDeclaration.SaveAnonymousTypes.INSTANCE);
    }

    public static final PropertyDescriptor createFreeFakeLocalPropertyDescriptor(LocalVariableDescriptor descriptor, TypeApproximator typeApproximator) {
        PropertyGetterDescriptorImpl propertyGetterDescriptorImpl;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        PropertyDescriptorImpl propertyDescriptorImplCreate = PropertyDescriptorImpl.create(descriptor.getContainingModule(), descriptor.getAnnotations(), Modality.FINAL, descriptor.getVisibilityImpl(), descriptor.isVar(), descriptor.getShortName(), CallableMemberDescriptor.Kind.DECLARATION, descriptor.getSourceElement(), false, descriptor.isConst(), false, false, false, descriptor.getIsDelegated());
        Intrinsics.checkNotNullExpressionValue(propertyDescriptorImplCreate, "create(...)");
        propertyDescriptorImplCreate.setType(descriptor.getType(), descriptor.getTypeParameters(), descriptor.mo6753getDispatchReceiverParameter(), descriptor.getExtensionReceiverParameter(), descriptor.getContextReceiverParameters());
        LocalVariableAccessorDescriptor.Getter getter = descriptor.getGetter();
        PropertySetterDescriptorImpl propertySetterDescriptorImpl = null;
        if (getter != null) {
            propertyGetterDescriptorImpl = new PropertyGetterDescriptorImpl(propertyDescriptorImplCreate, getter.getAnnotations(), getter.getModality(), getter.getVisibilityImpl(), true, getter.isExternal(), getter.isInline(), getter.getKind(), null, getter.getSourceElement());
            propertyGetterDescriptorImpl.initialize(getter.getReturnType());
        } else {
            propertyGetterDescriptorImpl = null;
        }
        LocalVariableAccessorDescriptor.Setter setter = descriptor.getSetter();
        if (setter != null) {
            propertySetterDescriptorImpl = new PropertySetterDescriptorImpl(propertyDescriptorImplCreate, setter.getAnnotations(), setter.getModality(), setter.getVisibilityImpl(), true, setter.isExternal(), setter.isInline(), setter.getKind(), null, setter.getSourceElement());
            List<ValueParameterDescriptor> valueParameters = setter.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            propertySetterDescriptorImpl.initialize((ValueParameterDescriptor) CollectionsKt.single((List) valueParameters));
        }
        propertyDescriptorImplCreate.initialize(propertyGetterDescriptorImpl, propertySetterDescriptorImpl);
        return (PropertyDescriptor) createFreeDescriptor(propertyDescriptorImplCreate, typeApproximator);
    }
}
