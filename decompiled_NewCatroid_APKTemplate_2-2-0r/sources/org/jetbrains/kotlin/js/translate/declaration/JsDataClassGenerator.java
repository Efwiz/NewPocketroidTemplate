package org.jetbrains.kotlin.js.translate.declaration;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: loaded from: classes5.dex */
class JsDataClassGenerator extends JsEqualsHashcodeToStringGenerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "parameter";
        } else if (i != 3) {
            objArr[0] = "function";
        } else {
            objArr[0] = "constructorParameters";
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/declaration/JsDataClassGenerator";
        if (i == 2 || i == 3) {
            objArr[2] = "generateCopyFunction";
        } else {
            objArr[2] = "generateComponentFunction";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    JsDataClassGenerator(KtClassOrObject ktClassOrObject, TranslationContext translationContext) {
        super(ktClassOrObject, translationContext);
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.JsEqualsHashcodeToStringGenerator, org.jetbrains.kotlin.backend.common.DataClassMethodGenerator
    public void generateComponentFunction(FunctionDescriptor functionDescriptor, ValueParameterDescriptor valueParameterDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) this.context.bindingContext().get(BindingContext.VALUE_PARAMETER_AS_PROPERTY, valueParameterDescriptor);
        JsFunction jsFunctionGenerateJsMethod = generateJsMethod(functionDescriptor);
        JsReturn jsReturn = new JsReturn(JsAstUtils.pureFqn(this.context.getNameForDescriptor(propertyDescriptor), new JsThisRef()));
        jsReturn.setSource(PsiSourceElementKt.getPsi(valueParameterDescriptor.getSourceElement()));
        jsFunctionGenerateJsMethod.getBody().getStatements().add(jsReturn);
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.JsEqualsHashcodeToStringGenerator, org.jetbrains.kotlin.backend.common.DataClassMethodGenerator
    public void generateCopyFunction(FunctionDescriptor functionDescriptor, List<? extends KtParameter> list) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        JsFunction jsFunctionGenerateJsMethod = generateJsMethod(functionDescriptor);
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            KtParameter ktParameter = list.get(i);
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) BindingContextUtils.getNotNull(this.context.bindingContext(), BindingContext.VALUE_PARAMETER, ktParameter);
            JsName nameForDescriptor = this.context.getNameForDescriptor((PropertyDescriptor) BindingContextUtils.getNotNull(this.context.bindingContext(), BindingContext.VALUE_PARAMETER_AS_PROPERTY, valueParameterDescriptor));
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(this.context.getNameForDescriptor(valueParameterDescriptor).getIdent());
            jsFunctionGenerateJsMethod.getParameters().add(new JsParameter(jsNameDeclareTemporaryName));
            JsExpression jsNameRef = new JsNameRef(jsNameDeclareTemporaryName);
            if (ktParameter.hasValOrVar()) {
                jsNameRef = new JsConditional(JsAstUtils.equality(new JsNameRef(jsNameDeclareTemporaryName), Namer.getUndefinedExpression()), new JsNameRef(nameForDescriptor, new JsThisRef()), jsNameRef);
            }
            arrayList.add(jsNameRef.source((Object) ktParameter));
        }
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = ((ClassDescriptor) functionDescriptor.getContainingModule()).mo6347getUnsubstitutedPrimaryConstructor();
        JsNew jsNew = new JsNew(this.context.getInnerReference(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor), arrayList);
        if (this.context.shouldBeDeferred(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor)) {
            this.context.deferConstructorCall(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, jsNew.getArguments());
        }
        jsNew.setSource(getDeclaration());
        JsReturn jsReturn = new JsReturn(jsNew);
        jsReturn.setSource(getDeclaration());
        jsFunctionGenerateJsMethod.getBody().getStatements().add(jsReturn);
    }
}
