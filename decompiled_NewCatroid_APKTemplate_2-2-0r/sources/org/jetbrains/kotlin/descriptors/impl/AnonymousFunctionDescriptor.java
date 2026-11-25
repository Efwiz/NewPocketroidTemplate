package org.jetbrains.kotlin.descriptors.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: loaded from: classes4.dex */
public class AnonymousFunctionDescriptor extends SimpleFunctionDescriptorImpl {
    private final boolean isSuspend;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 5:
            case 11:
                objArr[0] = "annotations";
                break;
            case 2:
            case 7:
            case 10:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 3:
            case 8:
            case 12:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 4:
                objArr[0] = "declarationDescriptor";
                break;
            case 6:
                objArr[0] = "name";
                break;
            case 9:
                objArr[0] = "newOwner";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/descriptors/impl/AnonymousFunctionDescriptor";
        switch (i) {
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = "createSubstitutedCopy";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AnonymousFunctionDescriptor(DeclarationDescriptor declarationDescriptor, Annotations annotations, CallableMemberDescriptor.Kind kind, SourceElement sourceElement, boolean z) {
        this(declarationDescriptor, null, annotations, SpecialNames.ANONYMOUS, kind, sourceElement, z);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        if (kind == null) {
            $$$reportNull$$$0(2);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(3);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private AnonymousFunctionDescriptor(DeclarationDescriptor declarationDescriptor, SimpleFunctionDescriptor simpleFunctionDescriptor, Annotations annotations, Name name2, CallableMemberDescriptor.Kind kind, SourceElement sourceElement, boolean z) {
        super(declarationDescriptor, simpleFunctionDescriptor, annotations, name2, kind, sourceElement);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (annotations == null) {
            $$$reportNull$$$0(5);
        }
        if (name2 == null) {
            $$$reportNull$$$0(6);
        }
        if (kind == null) {
            $$$reportNull$$$0(7);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(8);
        }
        this.isSuspend = z;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
    protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor declarationDescriptor, FunctionDescriptor functionDescriptor, CallableMemberDescriptor.Kind kind, Name name2, Annotations annotations, SourceElement sourceElement) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        if (kind == null) {
            $$$reportNull$$$0(10);
        }
        if (annotations == null) {
            $$$reportNull$$$0(11);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(12);
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) functionDescriptor;
        if (name2 == null) {
            name2 = getName();
        }
        return new AnonymousFunctionDescriptor(declarationDescriptor, simpleFunctionDescriptor, annotations, name2, kind, sourceElement, this.isSuspend);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.FunctionDescriptor
    public boolean isSuspend() {
        return this.isSuspend;
    }
}
