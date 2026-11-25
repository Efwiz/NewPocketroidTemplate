package org.jetbrains.kotlin.descriptors.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: loaded from: classes4.dex */
public class PropertySetterDescriptorImpl extends PropertyAccessorDescriptorImpl implements PropertySetterDescriptor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final PropertySetterDescriptor original;
    private ValueParameterDescriptor parameter;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 9:
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
                objArr[0] = "parameter";
                break;
            case 7:
                objArr[0] = "setterDescriptor";
                break;
            case 8:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/descriptors/impl/PropertySetterDescriptorImpl";
                break;
            default:
                objArr[0] = "correspondingProperty";
                break;
        }
        switch (i) {
            case 10:
                objArr[1] = "getOverriddenDescriptors";
                break;
            case 11:
                objArr[1] = "getValueParameters";
                break;
            case 12:
                objArr[1] = "getReturnType";
                break;
            case 13:
                objArr[1] = "getOriginal";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/descriptors/impl/PropertySetterDescriptorImpl";
                break;
        }
        switch (i) {
            case 6:
                objArr[2] = "initialize";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "createSetterParameter";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [org.jetbrains.kotlin.descriptors.impl.PropertySetterDescriptorImpl] */
    /* JADX WARN: Type inference failed for: r1v6 */
    public PropertySetterDescriptorImpl(PropertyDescriptor propertyDescriptor, Annotations annotations, Modality modality, DescriptorVisibility descriptorVisibility, boolean z, boolean z2, boolean z3, CallableMemberDescriptor.Kind kind, PropertySetterDescriptor propertySetterDescriptor, SourceElement sourceElement) {
        PropertySetterDescriptor propertySetterDescriptor2;
        ?? r1;
        super(modality, descriptorVisibility, propertyDescriptor, annotations, Name.special("<set-" + propertyDescriptor.getShortName() + ">"), z, z2, z3, kind, sourceElement);
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
        if (propertySetterDescriptor != null) {
            r1 = this;
            propertySetterDescriptor2 = propertySetterDescriptor;
        } else {
            propertySetterDescriptor2 = this;
            r1 = propertySetterDescriptor2;
        }
        r1.original = propertySetterDescriptor2;
    }

    public void initialize(ValueParameterDescriptor valueParameterDescriptor) {
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        this.parameter = valueParameterDescriptor;
    }

    public void initializeDefault() {
        initialize(createSetterParameter(this, getCorrespondingProperty().getType(), Annotations.INSTANCE.getEMPTY()));
    }

    public static ValueParameterDescriptorImpl createSetterParameter(PropertySetterDescriptor propertySetterDescriptor, KotlinType kotlinType, Annotations annotations) {
        if (propertySetterDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(8);
        }
        if (annotations == null) {
            $$$reportNull$$$0(9);
        }
        return new ValueParameterDescriptorImpl(propertySetterDescriptor, null, 0, annotations, SpecialNames.IMPLICIT_SET_PARAMETER, kotlinType, false, false, false, null, SourceElement.NO_SOURCE);
    }

    @Override // org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public Collection<? extends PropertySetterDescriptor> getOverriddenDescriptors() {
        Collection<PropertyAccessorDescriptor> overriddenDescriptors = super.getOverriddenDescriptors(false);
        if (overriddenDescriptors == null) {
            $$$reportNull$$$0(10);
        }
        return overriddenDescriptors;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<ValueParameterDescriptor> getValueParameters() {
        ValueParameterDescriptor valueParameterDescriptor = this.parameter;
        if (valueParameterDescriptor == null) {
            throw new IllegalStateException();
        }
        List<ValueParameterDescriptor> listSingletonList = Collections.singletonList(valueParameterDescriptor);
        if (listSingletonList == null) {
            $$$reportNull$$$0(11);
        }
        return listSingletonList;
    }

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    public KotlinType getReturnType() {
        SimpleType unitType = DescriptorUtilsKt.getBuiltIns(this).getUnitType();
        if (unitType == null) {
            $$$reportNull$$$0(12);
        }
        return unitType;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d) {
        return declarationDescriptorVisitor.visitPropertySetterDescriptor(this, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.PropertyAccessorDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public PropertySetterDescriptor getOriginal() {
        PropertySetterDescriptor propertySetterDescriptor = this.original;
        if (propertySetterDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        return propertySetterDescriptor;
    }
}
