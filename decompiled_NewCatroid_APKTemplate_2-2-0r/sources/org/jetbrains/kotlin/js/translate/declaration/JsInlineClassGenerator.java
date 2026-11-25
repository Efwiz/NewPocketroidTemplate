package org.jetbrains.kotlin.js.translate.declaration;

import java.util.List;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: loaded from: classes5.dex */
public class JsInlineClassGenerator extends JsEqualsHashcodeToStringGenerator {
    @Override // org.jetbrains.kotlin.js.translate.declaration.JsEqualsHashcodeToStringGenerator, org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    public /* bridge */ /* synthetic */ void generateEqualsMethod(FunctionDescriptor functionDescriptor, List list) {
        super.generateEqualsMethod(functionDescriptor, list);
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.JsEqualsHashcodeToStringGenerator, org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    public /* bridge */ /* synthetic */ void generateHashCodeMethod(FunctionDescriptor functionDescriptor, List list) {
        super.generateHashCodeMethod(functionDescriptor, list);
    }

    @Override // org.jetbrains.kotlin.js.translate.declaration.JsEqualsHashcodeToStringGenerator, org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    public /* bridge */ /* synthetic */ void generateToStringMethod(FunctionDescriptor functionDescriptor, List list) {
        super.generateToStringMethod(functionDescriptor, list);
    }

    protected JsInlineClassGenerator(KtClassOrObject ktClassOrObject, TranslationContext translationContext) {
        super(ktClassOrObject, translationContext);
    }

    @Override // org.jetbrains.kotlin.backend.common.DataClassMethodGenerator, org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    public void generate() {
        generateUnboxFunction();
        super.generate();
    }

    private void generateUnboxFunction() {
        PropertyDescriptor propertyDescriptor = getPrimaryConstructorProperties().get(0);
        JsFunction jsFunctionCreateRootScopedFunction = this.context.createRootScopedFunction("unbox");
        JsNameRef jsNameRef = new JsNameRef("unbox", JsAstUtils.prototypeOf(this.context.getInnerReference(getClassDescriptor())));
        jsFunctionCreateRootScopedFunction.getBody().getStatements().add(new JsReturn(JsAstUtils.pureFqn(this.context.getNameForDescriptor(propertyDescriptor), new JsThisRef())));
        this.context.addDeclarationStatement(JsAstUtils.assignment(jsNameRef, jsFunctionCreateRootScopedFunction).makeStmt());
    }
}
