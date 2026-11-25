package org.jetbrains.kotlin.descriptors.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
public class LocalVariableDescriptor extends VariableDescriptorWithInitializerImpl implements VariableDescriptorWithAccessors {
    private LocalVariableAccessorDescriptor.Getter getter;
    private final boolean isDelegated;
    private final boolean isLateInit;
    private LocalVariableAccessorDescriptor.Setter setter;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 13 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 13 || i == 14) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
            case 9:
                objArr[0] = "annotations";
                break;
            case 2:
            case 6:
            case 10:
                objArr[0] = "name";
                break;
            case 3:
            case 7:
            case 11:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 4:
            case 8:
            default:
                objArr[0] = "containingDeclaration";
                break;
            case 12:
                objArr[0] = "substitutor";
                break;
            case 13:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor";
                break;
        }
        if (i == 13) {
            objArr[1] = "substitute";
        } else if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor";
        } else {
            objArr[1] = "getVisibility";
        }
        switch (i) {
            case 12:
                objArr[2] = "substitute";
                break;
            case 13:
            case 14:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalVariableDescriptor(DeclarationDescriptor declarationDescriptor, Annotations annotations, Name name2, KotlinType kotlinType, boolean z, boolean z2, boolean z3, SourceElement sourceElement) {
        super(declarationDescriptor, annotations, name2, kotlinType, z, sourceElement);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        if (name2 == null) {
            $$$reportNull$$$0(2);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(3);
        }
        this.isDelegated = z2;
        this.isLateInit = z3;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LocalVariableDescriptor(DeclarationDescriptor declarationDescriptor, Annotations annotations, Name name2, KotlinType kotlinType, boolean z, boolean z2, SourceElement sourceElement) {
        this(declarationDescriptor, annotations, name2, kotlinType, z, z2, false, sourceElement);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (annotations == null) {
            $$$reportNull$$$0(5);
        }
        if (name2 == null) {
            $$$reportNull$$$0(6);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(7);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LocalVariableDescriptor(DeclarationDescriptor declarationDescriptor, Annotations annotations, Name name2, KotlinType kotlinType, SourceElement sourceElement) {
        this(declarationDescriptor, annotations, name2, kotlinType, false, false, false, sourceElement);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        if (annotations == null) {
            $$$reportNull$$$0(9);
        }
        if (name2 == null) {
            $$$reportNull$$$0(10);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(11);
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.VariableDescriptorImpl
    public void setOutType(KotlinType kotlinType) {
        super.setOutType(kotlinType);
        if (this.isDelegated) {
            this.getter = new LocalVariableAccessorDescriptor.Getter(this);
            if (isVar()) {
                this.setter = new LocalVariableAccessorDescriptor.Setter(this);
            }
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public LocalVariableDescriptor substitute(TypeSubstitutor typeSubstitutor) {
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(12);
        }
        if (typeSubstitutor.isEmpty()) {
            return this;
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d) {
        return declarationDescriptorVisitor.visitVariableDescriptor(this, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        DescriptorVisibility descriptorVisibility = DescriptorVisibilities.LOCAL;
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(14);
        }
        return descriptorVisibility;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public LocalVariableAccessorDescriptor.Getter getGetter() {
        return this.getter;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public LocalVariableAccessorDescriptor.Setter getSetter() {
        return this.setter;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors
    public boolean isDelegated() {
        return this.isDelegated;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableDescriptor
    public boolean isLateInit() {
        return this.isLateInit;
    }
}
