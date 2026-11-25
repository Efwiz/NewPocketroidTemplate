package org.jetbrains.kotlin.js.translate.declaration;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import org.jetbrains.kotlin.backend.common.DataClassMethodGenerator;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: loaded from: classes5.dex */
abstract class JsEqualsHashcodeToStringGenerator extends DataClassMethodGenerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected final TranslationContext context;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 3 || i == 5) {
            objArr[0] = "classProperties";
        } else if (i == 7) {
            objArr[0] = "parameter";
        } else if (i == 9) {
            objArr[0] = "constructorParameters";
        } else if (i != 10) {
            objArr[0] = "function";
        } else {
            objArr[0] = "functionDescriptor";
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/declaration/JsEqualsHashcodeToStringGenerator";
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "generateHashCodeMethod";
                break;
            case 4:
            case 5:
                objArr[2] = "generateEqualsMethod";
                break;
            case 6:
            case 7:
                objArr[2] = "generateComponentFunction";
                break;
            case 8:
            case 9:
                objArr[2] = "generateCopyFunction";
                break;
            case 10:
                objArr[2] = "generateJsMethod";
                break;
            default:
                objArr[2] = "generateToStringMethod";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.backend.common.DataClassMethodGenerator
    protected void generateComponentFunction(FunctionDescriptor functionDescriptor, ValueParameterDescriptor valueParameterDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(7);
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.DataClassMethodGenerator
    protected void generateCopyFunction(FunctionDescriptor functionDescriptor, List<? extends KtParameter> list) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        if (list == null) {
            $$$reportNull$$$0(9);
        }
    }

    protected JsEqualsHashcodeToStringGenerator(KtClassOrObject ktClassOrObject, TranslationContext translationContext) {
        super(ktClassOrObject, translationContext.bindingContext());
        this.context = translationContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    public void generateToStringMethod(FunctionDescriptor functionDescriptor, List<? extends PropertyDescriptor> list) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        if (getDeclaration() instanceof KtObjectDeclaration) {
            generateJsMethod(functionDescriptor).getBody().getStatements().add(new JsReturn(new JsStringLiteral(getDeclaration().getMyName())));
            return;
        }
        JsFunction jsFunctionGenerateJsMethod = generateJsMethod(functionDescriptor);
        JsExpression jsExpressionSum = null;
        int i = 0;
        while (i < list.size()) {
            JsExpression jsExpressionSource = JsAstUtils.sum(new JsStringLiteral((i == 0 ? getClassDescriptor().getShortName() + "(" : ", ") + list.get(i).getShortName().asString() + ContainerUtils.KEY_VALUE_DELIMITER), new JsInvocation(this.context.namer().kotlin("toString"), new JsNameRef(this.context.getNameForDescriptor(list.get(i)), new JsThisRef()))).source((Object) PsiSourceElementKt.getPsi(list.get(i).getSourceElement()));
            jsExpressionSum = jsExpressionSum == null ? jsExpressionSource : JsAstUtils.sum(jsExpressionSum, jsExpressionSource);
            i++;
        }
        JsReturn jsReturn = new JsReturn(JsAstUtils.sum(jsExpressionSum, new JsStringLiteral(Constant.AFTER_QUTO)));
        jsReturn.setSource(getDeclaration());
        jsFunctionGenerateJsMethod.getBody().getStatements().add(jsReturn);
    }

    @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    public void generateHashCodeMethod(FunctionDescriptor functionDescriptor, List<? extends PropertyDescriptor> list) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        JsFunction jsFunctionGenerateJsMethod = generateJsMethod(functionDescriptor);
        if (list.isEmpty()) {
            FqName fqName = getDeclaration().mo7391getFqName();
            JsReturn jsReturn = new JsReturn(new JsIntLiteral(fqName != null ? fqName.hashCode() : 0));
            jsReturn.setSource(getDeclaration());
            jsFunctionGenerateJsMethod.getBody().getStatements().add(jsReturn);
            return;
        }
        List<JsStatement> statements = jsFunctionGenerateJsMethod.getBody().getStatements();
        JsName jsNameDeclareName = jsFunctionGenerateJsMethod.getScope().declareName("result");
        JsVars jsVars = new JsVars(new JsVars.JsVar(jsNameDeclareName, new JsIntLiteral(0)));
        jsVars.setSource(getDeclaration());
        statements.add(jsVars);
        for (PropertyDescriptor propertyDescriptor : list) {
            statements.add(JsAstUtils.assignment(new JsNameRef(jsNameDeclareName), new JsBinaryOperation(JsBinaryOperator.BIT_OR, JsAstUtils.sum(JsAstUtils.mul(new JsNameRef(jsNameDeclareName), new JsIntLiteral(31)), new JsInvocation(this.context.namer().kotlin("hashCode"), new JsNameRef(this.context.getNameForDescriptor(propertyDescriptor), new JsThisRef()))), new JsIntLiteral(0))).source((Object) PsiSourceElementKt.getPsi(propertyDescriptor.getSourceElement())).makeStmt());
        }
        JsReturn jsReturn2 = new JsReturn(new JsNameRef(jsNameDeclareName));
        jsReturn2.setSource(getDeclaration());
        statements.add(jsReturn2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    public void generateEqualsMethod(FunctionDescriptor functionDescriptor, List<? extends PropertyDescriptor> list) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        JsFunction jsFunctionGenerateJsMethod = generateJsMethod(functionDescriptor);
        JsName jsNameDeclareName = jsFunctionGenerateJsMethod.getScope().declareName("other");
        jsFunctionGenerateJsMethod.getParameters().add(new JsParameter(jsNameDeclareName));
        JsBinaryOperation jsBinaryOperationEquality = JsAstUtils.equality(new JsThisRef(), new JsNameRef(jsNameDeclareName));
        JsBinaryOperation jsBinaryOperationInequality = JsAstUtils.inequality(new JsNameRef(jsNameDeclareName), new JsNullLiteral());
        JsBinaryOperation jsBinaryOperationTypeOfIs = JsAstUtils.typeOfIs(jsNameDeclareName.makeRef(), new JsStringLiteral("object"));
        JsBinaryOperation jsBinaryOperationEquality2 = JsAstUtils.equality(new JsInvocation(new JsNameRef("getPrototypeOf", new JsNameRef(CommonClassNames.JAVA_LANG_OBJECT_SHORT)), new JsThisRef()), new JsInvocation(new JsNameRef("getPrototypeOf", new JsNameRef(CommonClassNames.JAVA_LANG_OBJECT_SHORT)), new JsNameRef(jsNameDeclareName)));
        JsExpression jsExpression = null;
        for (PropertyDescriptor propertyDescriptor : list) {
            JsName nameForDescriptor = this.context.getNameForDescriptor(propertyDescriptor);
            JsExpression jsExpressionSource = new JsInvocation(this.context.namer().kotlin(Namer.EQUALS_METHOD_NAME), new JsNameRef(nameForDescriptor, new JsThisRef()), new JsNameRef(nameForDescriptor, new JsNameRef(jsNameDeclareName))).source((Object) PsiSourceElementKt.getPsi(propertyDescriptor.getSourceElement()));
            if (jsExpression != null) {
                jsExpressionSource = JsAstUtils.and(jsExpression, jsExpressionSource);
            }
            jsExpression = jsExpressionSource;
        }
        if (jsExpression != null) {
            jsBinaryOperationEquality2 = JsAstUtils.and(jsBinaryOperationEquality2, jsExpression);
        }
        JsReturn jsReturn = new JsReturn(JsAstUtils.or(jsBinaryOperationEquality, JsAstUtils.and(jsBinaryOperationInequality, JsAstUtils.and(jsBinaryOperationTypeOfIs, jsBinaryOperationEquality2))));
        jsReturn.setSource(getDeclaration());
        jsFunctionGenerateJsMethod.getBody().getStatements().add(jsReturn);
    }

    protected JsFunction generateJsMethod(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        JsFunction jsFunctionCreateRootScopedFunction = this.context.createRootScopedFunction(functionDescriptor);
        jsFunctionCreateRootScopedFunction.setSource(getDeclaration());
        ClassDescriptor classDescriptor = (ClassDescriptor) functionDescriptor.getContainingDeclaration();
        TranslationContext translationContext = this.context;
        translationContext.addDeclarationStatement(UtilsKt.addFunctionToPrototype(translationContext, classDescriptor, functionDescriptor, jsFunctionCreateRootScopedFunction));
        return jsFunctionCreateRootScopedFunction;
    }
}
