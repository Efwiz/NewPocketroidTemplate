package org.jetbrains.kotlin.load.java.sam;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassConstructorDescriptor;

/* JADX INFO: loaded from: classes5.dex */
class SamAdapterClassConstructorDescriptor extends JavaClassConstructorDescriptor implements SamAdapterDescriptor<JavaClassConstructorDescriptor> {
    private final JavaClassConstructorDescriptor declaration;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 10 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 10 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "containingDeclaration";
                break;
            case 2:
            case 9:
                objArr[0] = "annotations";
                break;
            case 3:
            case 7:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 4:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 5:
            default:
                objArr[0] = "declaration";
                break;
            case 6:
                objArr[0] = "newOwner";
                break;
            case 8:
                objArr[0] = "sourceElement";
                break;
            case 10:
                objArr[0] = "org/jetbrains/kotlin/load/java/sam/SamAdapterClassConstructorDescriptor";
                break;
        }
        if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/load/java/sam/SamAdapterClassConstructorDescriptor";
        } else {
            objArr[1] = "getBaseDescriptorForSynthetic";
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "createDescriptor";
                break;
            case 10:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 10) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SamAdapterClassConstructorDescriptor(JavaClassConstructorDescriptor javaClassConstructorDescriptor) {
        super(javaClassConstructorDescriptor.getPackageFragment(), null, javaClassConstructorDescriptor.getAnnotations(), javaClassConstructorDescriptor.isPrimary(), CallableMemberDescriptor.Kind.SYNTHESIZED, javaClassConstructorDescriptor.getSourceElement());
        if (javaClassConstructorDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        this.declaration = javaClassConstructorDescriptor;
        setHasStableParameterNames(javaClassConstructorDescriptor.hasStableParameterNames());
        setHasSynthesizedParameterNames(javaClassConstructorDescriptor.hasSynthesizedParameterNames());
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private SamAdapterClassConstructorDescriptor(ClassDescriptor classDescriptor, JavaClassConstructorDescriptor javaClassConstructorDescriptor, Annotations annotations, boolean z, CallableMemberDescriptor.Kind kind, SourceElement sourceElement, JavaClassConstructorDescriptor javaClassConstructorDescriptor2) {
        super(classDescriptor, javaClassConstructorDescriptor, annotations, z, kind, sourceElement);
        if (classDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (annotations == null) {
            $$$reportNull$$$0(2);
        }
        if (kind == null) {
            $$$reportNull$$$0(3);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(4);
        }
        if (javaClassConstructorDescriptor2 == null) {
            $$$reportNull$$$0(5);
        }
        this.declaration = javaClassConstructorDescriptor2;
    }

    @Override // org.jetbrains.kotlin.load.java.descriptors.JavaClassConstructorDescriptor
    protected JavaClassConstructorDescriptor createDescriptor(ClassDescriptor classDescriptor, JavaClassConstructorDescriptor javaClassConstructorDescriptor, CallableMemberDescriptor.Kind kind, SourceElement sourceElement, Annotations annotations) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (kind == null) {
            $$$reportNull$$$0(7);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(8);
        }
        if (annotations == null) {
            $$$reportNull$$$0(9);
        }
        return new SamAdapterClassConstructorDescriptor(classDescriptor, javaClassConstructorDescriptor, annotations, this.isPrimary, kind, sourceElement, this.declaration);
    }

    @Override // org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor
    public JavaClassConstructorDescriptor getBaseDescriptorForSynthetic() {
        JavaClassConstructorDescriptor javaClassConstructorDescriptor = this.declaration;
        if (javaClassConstructorDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        return javaClassConstructorDescriptor;
    }
}
