package org.jetbrains.kotlin.js.translate.reference;

import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptorKt;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;

/* JADX INFO: loaded from: classes5.dex */
public final class BackingFieldAccessTranslator extends AbstractTranslator implements AccessTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final PropertyDescriptor descriptor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "context";
                break;
            case 2:
                objArr[0] = "descriptor";
                break;
            case 4:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/js/translate/reference/BackingFieldAccessTranslator";
                break;
            case 5:
                objArr[0] = "setTo";
                break;
            default:
                objArr[0] = "expression";
                break;
        }
        if (i == 4) {
            objArr[1] = "translateAsGet";
        } else if (i == 6) {
            objArr[1] = "translateAsSet";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/reference/BackingFieldAccessTranslator";
        } else {
            objArr[1] = "getCached";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "<init>";
                break;
            case 4:
            case 6:
            case 7:
                break;
            case 5:
                objArr[2] = "translateAsSet";
                break;
            default:
                objArr[2] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public AccessTranslator getCached() {
        return this;
    }

    public static BackingFieldAccessTranslator newInstance(KtSimpleNameExpression ktSimpleNameExpression, TranslationContext translationContext) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        return new BackingFieldAccessTranslator(SyntheticFieldDescriptorKt.getReferencedProperty(BindingUtils.getDescriptorForReferenceExpression(translationContext.bindingContext(), ktSimpleNameExpression)), translationContext);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private BackingFieldAccessTranslator(PropertyDescriptor propertyDescriptor, TranslationContext translationContext) {
        super(translationContext);
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
        this.descriptor = propertyDescriptor;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsGet() {
        JsNameRef jsNameRefBackingFieldReference = TranslationUtils.backingFieldReference(context(), this.descriptor);
        if (jsNameRefBackingFieldReference == null) {
            $$$reportNull$$$0(4);
        }
        return jsNameRefBackingFieldReference;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsSet(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(5);
        }
        JsExpression jsExpressionAssignmentToBackingField = TranslationUtils.assignmentToBackingField(context(), this.descriptor, jsExpression);
        if (jsExpressionAssignmentToBackingField == null) {
            $$$reportNull$$$0(6);
        }
        return jsExpressionAssignmentToBackingField;
    }
}
