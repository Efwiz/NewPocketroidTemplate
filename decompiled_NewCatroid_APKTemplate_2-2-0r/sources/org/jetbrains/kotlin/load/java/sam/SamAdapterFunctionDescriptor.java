package org.jetbrains.kotlin.load.java.sam;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes5.dex */
class SamAdapterFunctionDescriptor extends JavaMethodDescriptor implements SamAdapterDescriptor<JavaMethodDescriptor> {
    private final JavaMethodDescriptor declaration;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 8 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 8 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "containingDeclaration";
                break;
            case 2:
            case 5:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 3:
            default:
                objArr[0] = "declaration";
                break;
            case 4:
                objArr[0] = "newOwner";
                break;
            case 6:
                objArr[0] = "annotations";
                break;
            case 7:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 8:
                objArr[0] = "org/jetbrains/kotlin/load/java/sam/SamAdapterFunctionDescriptor";
                break;
        }
        if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/load/java/sam/SamAdapterFunctionDescriptor";
        } else {
            objArr[1] = "getBaseDescriptorForSynthetic";
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "createSubstitutedCopy";
                break;
            case 8:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 8) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SamAdapterFunctionDescriptor(JavaMethodDescriptor javaMethodDescriptor) {
        this(javaMethodDescriptor.getContainingDeclaration(), null, CallableMemberDescriptor.Kind.SYNTHESIZED, javaMethodDescriptor);
        if (javaMethodDescriptor == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private SamAdapterFunctionDescriptor(DeclarationDescriptor declarationDescriptor, SimpleFunctionDescriptor simpleFunctionDescriptor, CallableMemberDescriptor.Kind kind, JavaMethodDescriptor javaMethodDescriptor) {
        super(declarationDescriptor, simpleFunctionDescriptor, javaMethodDescriptor.getAnnotations(), javaMethodDescriptor.getShortName(), kind, javaMethodDescriptor.getSourceElement(), false);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (kind == null) {
            $$$reportNull$$$0(2);
        }
        if (javaMethodDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        this.declaration = javaMethodDescriptor;
        setParameterNamesStatus(javaMethodDescriptor.hasStableParameterNames(), javaMethodDescriptor.hasSynthesizedParameterNames());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor, org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
    public JavaMethodDescriptor createSubstitutedCopy(DeclarationDescriptor declarationDescriptor, FunctionDescriptor functionDescriptor, CallableMemberDescriptor.Kind kind, Name name2, Annotations annotations, SourceElement sourceElement) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (kind == null) {
            $$$reportNull$$$0(5);
        }
        if (annotations == null) {
            $$$reportNull$$$0(6);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(7);
        }
        return new SamAdapterFunctionDescriptor(declarationDescriptor, (SimpleFunctionDescriptor) functionDescriptor, kind, this.declaration);
    }

    @Override // org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor
    public JavaMethodDescriptor getBaseDescriptorForSynthetic() {
        JavaMethodDescriptor javaMethodDescriptor = this.declaration;
        if (javaMethodDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        return javaMethodDescriptor;
    }
}
