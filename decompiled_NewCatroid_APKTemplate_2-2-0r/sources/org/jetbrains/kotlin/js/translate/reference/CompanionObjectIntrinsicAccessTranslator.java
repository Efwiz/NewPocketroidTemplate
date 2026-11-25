package org.jetbrains.kotlin.js.translate.reference;

import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;

/* JADX INFO: loaded from: classes5.dex */
public class CompanionObjectIntrinsicAccessTranslator extends AbstractTranslator implements AccessTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final JsExpression referenceToCompanionObject;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 8 || i == 9 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 8 || i == 9 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 7:
                objArr[0] = "context";
                break;
            case 2:
            default:
                objArr[0] = "expression";
                break;
            case 4:
            case 6:
                objArr[0] = "descriptor";
                break;
            case 8:
            case 9:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/js/translate/reference/CompanionObjectIntrinsicAccessTranslator";
                break;
            case 10:
                objArr[0] = "toSetTo";
                break;
        }
        if (i == 8) {
            objArr[1] = "generateReferenceToCompanionObject";
        } else if (i == 9) {
            objArr[1] = "translateAsGet";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/reference/CompanionObjectIntrinsicAccessTranslator";
        } else {
            objArr[1] = "getCached";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "isCompanionObjectReference";
                break;
            case 4:
            case 5:
                objArr[2] = "<init>";
                break;
            case 6:
            case 7:
                objArr[2] = "generateReferenceToCompanionObject";
                break;
            case 8:
            case 9:
            case 11:
                break;
            case 10:
                objArr[2] = "translateAsSet";
                break;
            default:
                objArr[2] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 8 && i != 9 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public AccessTranslator getCached() {
        return this;
    }

    static CompanionObjectIntrinsicAccessTranslator newInstance(KtSimpleNameExpression ktSimpleNameExpression, TranslationContext translationContext) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        return new CompanionObjectIntrinsicAccessTranslator(BindingUtils.getDescriptorForReferenceExpression(translationContext.bindingContext(), ktSimpleNameExpression), translationContext);
    }

    static boolean isCompanionObjectReference(KtReferenceExpression ktReferenceExpression, TranslationContext translationContext) {
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
        DeclarationDescriptor descriptorForReferenceExpression = BindingUtils.getDescriptorForReferenceExpression(translationContext.bindingContext(), ktReferenceExpression);
        return (descriptorForReferenceExpression instanceof ClassDescriptor) && translationContext.intrinsics().getObjectIntrinsic((ClassDescriptor) descriptorForReferenceExpression) != null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private CompanionObjectIntrinsicAccessTranslator(DeclarationDescriptor declarationDescriptor, TranslationContext translationContext) {
        super(translationContext);
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(5);
        }
        this.referenceToCompanionObject = generateReferenceToCompanionObject(declarationDescriptor, translationContext);
    }

    private static JsExpression generateReferenceToCompanionObject(DeclarationDescriptor declarationDescriptor, TranslationContext translationContext) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(7);
        }
        JsExpression jsExpressionApply = translationContext.intrinsics().getObjectIntrinsic((ClassDescriptor) declarationDescriptor).apply(translationContext);
        if (jsExpressionApply == null) {
            $$$reportNull$$$0(8);
        }
        return jsExpressionApply;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsGet() {
        JsExpression jsExpression = this.referenceToCompanionObject;
        if (jsExpression == null) {
            $$$reportNull$$$0(9);
        }
        return jsExpression;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsSet(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(10);
        }
        throw new IllegalStateException("companion object can't be set");
    }
}
