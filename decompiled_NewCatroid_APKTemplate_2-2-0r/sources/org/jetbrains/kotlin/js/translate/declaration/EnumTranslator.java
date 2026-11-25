package org.jetbrains.kotlin.js.translate.declaration;

import com.reandroid.arsc.chunk.TypeBlock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsCase;
import org.jetbrains.kotlin.js.backend.ast.JsDefault;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: EnumTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u00020\u0014H\u0002J\b\u0010\u0016\u001a\u00020\u0014H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/EnumTranslator;", "Lorg/jetbrains/kotlin/js/translate/general/AbstractTranslator;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", TypeBlock.NAME_entries, "", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Ljava/util/List;Lcom/intellij/psi/PsiElement;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getEntries", "()Ljava/util/List;", "createFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "generateStandardMethods", "", "generateValueOfFunction", "generateValuesFunction", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumTranslator extends AbstractTranslator {
    private final ClassDescriptor descriptor;
    private final List<ClassDescriptor> entries;
    private final PsiElement psi;

    public final ClassDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final List<ClassDescriptor> getEntries() {
        return this.entries;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public EnumTranslator(TranslationContext context, ClassDescriptor descriptor, List<? extends ClassDescriptor> entries, PsiElement psi) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(psi, "psi");
        this.descriptor = descriptor;
        this.entries = entries;
        this.psi = psi;
    }

    public final void generateStandardMethods() {
        generateValuesFunction();
        generateValueOfFunction();
    }

    private final void generateValuesFunction() {
        FunctionDescriptor functionByName = DescriptorUtils.getFunctionByName(this.descriptor.getStaticScope(), StandardNames.ENUM_VALUES);
        Intrinsics.checkNotNullExpressionValue(functionByName, "getFunctionByName(...)");
        JsFunction jsFunctionCreateFunction = createFunction(functionByName);
        List<ClassDescriptor> list = this.entries;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new JsInvocation(JsAstUtils.pureFqn(context().getNameForObjectInstance((ClassDescriptor) it.next()), (JsExpression) null), new JsExpression[0]).source((Object) this.psi));
        }
        List<JsStatement> statements = jsFunctionCreateFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        JsReturn jsReturn = new JsReturn(new JsArrayLiteral(arrayList).source((Object) this.psi));
        jsReturn.setSource(this.psi);
        statements.add(jsReturn);
    }

    private final void generateValueOfFunction() {
        FunctionDescriptor functionByName = DescriptorUtils.getFunctionByName(this.descriptor.getStaticScope(), StandardNames.ENUM_VALUE_OF);
        Intrinsics.checkNotNullExpressionValue(functionByName, "getFunctionByName(...)");
        JsFunction jsFunctionCreateFunction = createFunction(functionByName);
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName("name");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        List<JsParameter> parameters = jsFunctionCreateFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        parameters.add(new JsParameter(jsNameDeclareTemporaryName));
        List<ClassDescriptor> list = this.entries;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ClassDescriptor classDescriptor : list) {
            JsCase jsCase = new JsCase();
            jsCase.setCaseExpression(new JsStringLiteral(classDescriptor.getShortName().asString()).source((Object) this.psi));
            List<JsStatement> statements = jsCase.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            JsReturn jsReturn = new JsReturn(new JsInvocation(JsAstUtils.pureFqn(context().getNameForObjectInstance(classDescriptor), (JsExpression) null), new JsExpression[0]).source((Object) this.psi));
            jsReturn.setSource(this.psi);
            statements.add(jsReturn);
            jsCase.setSource(this.psi);
            arrayList.add(jsCase);
        }
        JsBinaryOperation jsBinaryOperation = new JsBinaryOperation(JsBinaryOperator.ADD, new JsStringLiteral("No enum constant " + DescriptorUtilsKt.getFqNameSafe(this.descriptor) + '.'), jsNameDeclareTemporaryName.makeRef());
        JsExpression referenceToIntrinsic = context().getReferenceToIntrinsic(Namer.THROW_ILLEGAL_ARGUMENT_EXCEPTION_FUN_NAME);
        Intrinsics.checkNotNullExpressionValue(referenceToIntrinsic, "getReferenceToIntrinsic(...)");
        JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(new JsInvocation(referenceToIntrinsic, jsBinaryOperation).source((Object) this.psi));
        ArrayList arrayList2 = arrayList;
        if (!arrayList2.isEmpty()) {
            JsDefault jsDefault = new JsDefault();
            List<JsStatement> statements2 = jsDefault.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            statements2.add(jsExpressionStatement);
            jsDefault.setSource(this.psi);
            List<JsStatement> statements3 = jsFunctionCreateFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
            JsSwitch jsSwitch = new JsSwitch(jsNameDeclareTemporaryName.makeRef().source((Object) this.psi), CollectionsKt.plus((Collection<? extends JsDefault>) arrayList2, jsDefault));
            jsSwitch.setSource(this.psi);
            statements3.add(jsSwitch);
            return;
        }
        List<JsStatement> statements4 = jsFunctionCreateFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
        statements4.add(jsExpressionStatement);
    }

    private final JsFunction createFunction(FunctionDescriptor functionDescriptor) {
        JsFunction functionObject = context().getFunctionObject(functionDescriptor);
        Intrinsics.checkNotNullExpressionValue(functionObject, "getFunctionObject(...)");
        FunctionDescriptor functionDescriptor2 = functionDescriptor;
        functionObject.setName(context().getInnerNameForDescriptor(functionDescriptor2));
        functionObject.setSource(this.psi);
        context().addDeclarationStatement(functionObject.makeStmt());
        JsExpression innerReference = context().getInnerReference(this.descriptor);
        Intrinsics.checkNotNullExpressionValue(innerReference, "getInnerReference(...)");
        JsNameRef jsNameRefMakeRef = functionObject.getName().makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(new JsNameRef(context().getNameForDescriptor(functionDescriptor2), innerReference), jsNameRefMakeRef);
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAssignment, "assignment(...)");
        context().addDeclarationStatement(jsBinaryOperationAssignment.makeStmt());
        return functionObject;
    }
}
