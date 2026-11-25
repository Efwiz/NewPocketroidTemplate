package org.jetbrains.kotlin.descriptors.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes4.dex */
public class FunctionExpressionDescriptor extends SimpleFunctionDescriptorImpl {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "annotations";
        } else if (i == 2) {
            objArr[0] = "name";
        } else if (i == 3) {
            objArr[0] = Namer.METADATA_CLASS_KIND;
        } else if (i != 4) {
            objArr[0] = "containingDeclaration";
        } else {
            objArr[0] = FirebaseAnalytics.Param.SOURCE;
        }
        objArr[1] = "org/jetbrains/kotlin/descriptors/impl/FunctionExpressionDescriptor";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionExpressionDescriptor(DeclarationDescriptor declarationDescriptor, Annotations annotations, Name name2, CallableMemberDescriptor.Kind kind, SourceElement sourceElement) {
        super(declarationDescriptor, null, annotations, name2, kind, sourceElement);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (annotations == null) {
            $$$reportNull$$$0(1);
        }
        if (name2 == null) {
            $$$reportNull$$$0(2);
        }
        if (kind == null) {
            $$$reportNull$$$0(3);
        }
        if (sourceElement == null) {
            $$$reportNull$$$0(4);
        }
    }
}
