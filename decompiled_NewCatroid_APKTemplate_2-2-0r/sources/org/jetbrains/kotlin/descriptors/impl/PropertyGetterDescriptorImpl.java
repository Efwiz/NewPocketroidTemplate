package org.jetbrains.kotlin.descriptors.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes4.dex */
public class PropertyGetterDescriptorImpl extends PropertyAccessorDescriptorImpl implements PropertyGetterDescriptor {
    private final PropertyGetterDescriptor original;
    private KotlinType returnType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 7 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "annotations";
                break;
            case 2:
                objArr[0] = "modality";
                break;
            case 3:
                objArr[0] = "visibility";
                break;
            case 4:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 5:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/descriptors/impl/PropertyGetterDescriptorImpl";
                break;
            default:
                objArr[0] = "correspondingProperty";
                break;
        }
        if (i == 6) {
            objArr[1] = "getOverriddenDescriptors";
        } else if (i == 7) {
            objArr[1] = "getValueParameters";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/descriptors/impl/PropertyGetterDescriptorImpl";
        } else {
            objArr[1] = "getOriginal";
        }
        if (i != 6 && i != 7 && i != 8) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl] */
    /* JADX WARN: Type inference failed for: r1v6 */
    public PropertyGetterDescriptorImpl(PropertyDescriptor propertyDescriptor, Annotations annotations, Modality modality, DescriptorVisibility descriptorVisibility, boolean z, boolean z2, boolean z3, CallableMemberDescriptor.Kind kind, PropertyGetterDescriptor propertyGetterDescriptor, SourceElement sourceElement) {
        PropertyGetterDescriptor propertyGetterDescriptor2;
        ?? r1;
        super(modality, descriptorVisibility, propertyDescriptor, annotations, Name.special("<get-" + propertyDescriptor.getShortName() + ">"), z, z2, z3, kind, sourceElement);
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        if (modality == null) {
            $$$reportNull$$$0(2);
        }
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(3);
        }
        if (kind == null) {
            $$$reportNull$$$0(4);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(5);
        }
        if (propertyGetterDescriptor != null) {
            r1 = this;
            propertyGetterDescriptor2 = propertyGetterDescriptor;
        } else {
            propertyGetterDescriptor2 = this;
            r1 = propertyGetterDescriptor2;
        }
        r1.original = propertyGetterDescriptor2;
    }

    public void initialize(KotlinType kotlinType) {
        if (kotlinType == null) {
            kotlinType = getCorrespondingProperty().getType();
        }
        this.returnType = kotlinType;
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<? extends PropertyGetterDescriptor> getOverriddenDescriptors() {
        Collection<PropertyAccessorDescriptor> overriddenDescriptors = super.getOverriddenDescriptors(true);
        if (overriddenDescriptors == null) {
            $$$reportNull$$$0(6);
        }
        return overriddenDescriptors;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ValueParameterDescriptor> getValueParameters() {
        List<ValueParameterDescriptor> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(7);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        return this.returnType;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d) {
        return declarationDescriptorVisitor.visitPropertyGetterDescriptor(this, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.PropertyAccessorDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public PropertyGetterDescriptor getOriginal() {
        PropertyGetterDescriptor propertyGetterDescriptor = this.original;
        if (propertyGetterDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        return propertyGetterDescriptor;
    }
}
