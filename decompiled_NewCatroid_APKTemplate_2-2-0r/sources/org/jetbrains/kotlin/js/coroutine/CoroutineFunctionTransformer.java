package org.jetbrains.kotlin.js.coroutine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.StringTemplate;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsCase;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsDefault;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsFunctionScope;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.CoroutineMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.inline.util.FunctionUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: CoroutineFunctionTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J4\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0019H\u0002J$\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001bH\u0002J\u0010\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\nH\u0002J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b2\u0006\u0010 \u001a\u00020\nH\u0002J,\u0010\"\u001a\u00020\u00102\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001b2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\b\u0010$\u001a\u00020%H\u0002J\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00150\u001bJ\b\u0010'\u001a\u00020\u0010H\u0002J,\u0010(\u001a\u00020\u0010*\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-H\u0002J\"\u0010.\u001a\u00020\u0010*\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020+H\u0002J\f\u0010/\u001a\u00020\u0010*\u00020\bH\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/js/coroutine/CoroutineFunctionTransformer;", "", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "name", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Ljava/lang/String;)V", "body", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "className", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "functionWithBody", "innerFunction", "localVariables", "", "generateContinuationConstructor", "", "context", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineTransformationContext;", "statements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "globalCatchBlockIndex", "", "survivingLocalVars", "", "generateCoroutineBody", "", "blocks", "Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "generateCoroutineInstantiation", "generateCoroutineMetadata", "constructorName", "generateCoroutinePrototype", "generateDoResume", "coroutineBlocks", "isTailCall", "", "transform", "transformSimple", "assignToField", "fieldName", "value", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "assignToPrototype", "collectAdditionalLocalVariables", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineFunctionTransformer {
    private final JsBlock body;
    private final JsName className;
    private final JsFunction function;
    private final JsFunction functionWithBody;
    private final JsFunction innerFunction;
    private final Set<JsName> localVariables;

    public CoroutineFunctionTransformer(JsFunction function, String str) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.function = function;
        JsFunction innerFunction = FunctionUtilsKt.getInnerFunction(function);
        this.innerFunction = innerFunction;
        innerFunction = innerFunction == null ? function : innerFunction;
        this.functionWithBody = innerFunction;
        JsBlock body = innerFunction.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        this.body = body;
        Set setPlus = SetsKt.plus(CollectUtilsKt.collectLocalVariables$default(function, false, 1, null), (Iterable) CollectUtilsKt.collectLocalVariables$default(innerFunction, false, 1, null));
        List<JsParameter> parameters = innerFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        JsName name2 = ((JsParameter) CollectionsKt.last((List) parameters)).getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        this.localVariables = CollectionsKt.toMutableSet(SetsKt.minus((Set<? extends JsName>) setPlus, name2));
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName("Coroutine$" + (str == null ? StringTemplate.ANONYMOUS_ST_NAME : str));
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        this.className = jsNameDeclareTemporaryName;
    }

    public final List<JsStatement> transform() {
        if (isTailCall() && !MetadataProperties.getForceStateMachine(this.function)) {
            transformSimple();
            return CollectionsKt.emptyList();
        }
        JsFunctionScope scope = this.function.getScope();
        Intrinsics.checkNotNullExpressionValue(scope, "getScope(...)");
        CoroutineTransformationContext coroutineTransformationContext = new CoroutineTransformationContext(scope, this.function);
        CoroutineBodyTransformer coroutineBodyTransformer = new CoroutineBodyTransformer(coroutineTransformationContext);
        coroutineBodyTransformer.preProcess(this.body);
        List<JsStatement> statements = this.body.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        Iterator<T> it = statements.iterator();
        while (it.hasNext()) {
            ((JsStatement) it.next()).accept(coroutineBodyTransformer);
        }
        List<CoroutineBlock> listPostProcess = coroutineBodyTransformer.postProcess();
        int iIndexOf = listPostProcess.indexOf(coroutineTransformationContext.getGlobalCatchBlock());
        List<CoroutineBlock> list = listPostProcess;
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            collectAdditionalLocalVariables(((CoroutineBlock) it2.next()).getJsBlock());
        }
        Set<JsName> set = this.localVariables;
        List<JsParameter> parameters = this.function.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<JsParameter> list2 = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it3 = list2.iterator();
        while (it3.hasNext()) {
            arrayList.add(((JsParameter) it3.next()).getName());
        }
        Set<JsName> setCollectVariablesSurvivingBetweenBlocks = CoroutinePassesKt.collectVariablesSurvivingBetweenBlocks(listPostProcess, set, CollectionsKt.toSet(arrayList));
        Iterator<T> it4 = list.iterator();
        while (it4.hasNext()) {
            CoroutinePassesKt.replaceLocalVariables(((CoroutineBlock) it4.next()).getJsBlock(), coroutineTransformationContext, setCollectVariablesSurvivingBetweenBlocks);
        }
        ArrayList arrayList2 = new ArrayList();
        generateDoResume(listPostProcess, coroutineTransformationContext, arrayList2);
        generateContinuationConstructor(coroutineTransformationContext, arrayList2, iIndexOf, setCollectVariablesSurvivingBetweenBlocks);
        generateCoroutineInstantiation(coroutineTransformationContext);
        return arrayList2;
    }

    private final boolean isTailCall() {
        final HashSet hashSet = new HashSet();
        this.body.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineFunctionTransformer.isTailCall.1
            @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
            protected void visitElement(JsNode node) {
                Intrinsics.checkNotNullParameter(node, "node");
                if ((node instanceof JsExpression) && MetadataProperties.isSuspend((JsExpression) node)) {
                    hashSet.add(node);
                }
                super.visitElement(node);
            }
        });
        if (hashSet.isEmpty()) {
            return true;
        }
        this.body.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineFunctionTransformer.isTailCall.2
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBlock(JsBlock x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visitBlock(x);
                if (CoroutineFunctionTransformer.this.body.getStatements().size() < 2) {
                    return;
                }
                List<JsStatement> statements = CoroutineFunctionTransformer.this.body.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                Object objLast = CollectionsKt.last((List<? extends Object>) statements);
                JsReturn jsReturn = objLast instanceof JsReturn ? (JsReturn) objLast : null;
                if (jsReturn != null && CoroutinePassesKt.isStateMachineResult(jsReturn.getExpression())) {
                    List<JsStatement> statements2 = CoroutineFunctionTransformer.this.body.getStatements();
                    List<JsStatement> statements3 = CoroutineFunctionTransformer.this.body.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                    JsStatement jsStatement = statements2.get(CollectionsKt.getLastIndex(statements3) - 1);
                    JsExpressionStatement jsExpressionStatement = jsStatement instanceof JsExpressionStatement ? (JsExpressionStatement) jsStatement : null;
                    if (jsExpressionStatement == null) {
                        return;
                    }
                    JsExpression expression = jsExpressionStatement.getExpression();
                    Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                    if (hashSet.contains(expression)) {
                        hashSet.remove(expression);
                        return;
                    }
                    Pair<JsExpression, JsExpression> pairDecomposeAssignment = JsAstUtils.decomposeAssignment(expression);
                    if (pairDecomposeAssignment != null) {
                        HashSet<JsExpression> hashSet2 = hashSet;
                        JsExpression jsExpressionComponent1 = pairDecomposeAssignment.component1();
                        JsExpression jsExpressionComponent2 = pairDecomposeAssignment.component2();
                        if (hashSet2.contains(jsExpressionComponent2) && CoroutinePassesKt.isStateMachineResult(jsExpressionComponent1)) {
                            hashSet2.remove(jsExpressionComponent2);
                        }
                    }
                }
            }
        });
        return hashSet.isEmpty();
    }

    private final void transformSimple() {
        List<JsParameter> parameters = this.function.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        JsParameter jsParameter = (JsParameter) CollectionsKt.last((List) parameters);
        final JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(CoroutineConstantsKt.SUSPEND_CALL_RESULT_NAME);
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        JsBlock jsBlock = this.body;
        Intrinsics.checkNotNull(jsParameter);
        CoroutinePassesKt.replaceSpecialReferencesInSimpleFunction(jsBlock, jsParameter, jsNameDeclareTemporaryName);
        List<JsStatement> statements = this.body.getStatements();
        JsVars jsVarsNewVar = JsAstUtils.newVar(jsNameDeclareTemporaryName, null);
        Intrinsics.checkNotNull(jsVarsNewVar);
        MetadataProperties.setSynthetic(jsVarsNewVar, true);
        Unit unit = Unit.INSTANCE;
        statements.add(0, jsVarsNewVar);
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineFunctionTransformer.transformSimple.2
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsExpressionStatement x, JsContext<? super JsStatement> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                if (MetadataProperties.isSuspend(expression)) {
                    ctx.replaceMe(JsAstUtils.assignment(JsAstUtils.pureFqn(jsNameDeclareTemporaryName, (JsExpression) null), x.getExpression()).source(x.getSource()).makeStmt());
                }
                super.endVisit(x, ctx);
            }
        }.accept(this.body);
        new FunctionPostProcessor(this.functionWithBody).apply();
    }

    private final void generateContinuationConstructor(CoroutineTransformationContext context, List<JsStatement> statements, int globalCatchBlockIndex, Set<? extends JsName> survivingLocalVars) {
        JsName jsNameDeclareTemporaryName;
        List<JsParameter> parameters;
        PsiElement psiElement = context.getMetadata().getPsiElement();
        JsFunction jsFunction = new JsFunction(this.function.getScope().getParent(), new JsBlock(), "Continuation");
        jsFunction.setSource(psiElement);
        JsBlock body = jsFunction.getBody();
        ArrayList arrayListEmptyList = null;
        PsiElement finalElement = psiElement != null ? UtilsKt.getFinalElement(psiElement) : null;
        body.setSource(finalElement instanceof LeafPsiElement ? (LeafPsiElement) finalElement : null);
        jsFunction.setName(this.className);
        if (context.getMetadata().getHasReceiver()) {
            List<JsParameter> parameters2 = jsFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
            parameters2.add(new JsParameter(context.getReceiverFieldName()));
        }
        List<JsParameter> parameters3 = this.function.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters3, "getParameters(...)");
        List<JsParameter> list = parameters3;
        JsFunction jsFunction2 = this.innerFunction;
        List<JsParameter> parameters4 = jsFunction2 != null ? jsFunction2.getParameters() : null;
        if (parameters4 == null) {
            parameters4 = CollectionsKt.emptyList();
        }
        List listPlus = CollectionsKt.plus((Collection) list, (Iterable) parameters4);
        List<JsParameter> parameters5 = jsFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters5, "getParameters(...)");
        List<JsParameter> list2 = parameters5;
        List list3 = listPlus;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator it = list3.iterator();
        while (it.hasNext()) {
            arrayList.add(new JsParameter(((JsParameter) it.next()).getName()));
        }
        CollectionsKt.addAll(list2, arrayList);
        JsParameter jsParameter = (JsParameter) CollectionsKt.lastOrNull(listPlus);
        JsName name2 = jsParameter != null ? jsParameter.getName() : null;
        if (context.getMetadata().getHasController()) {
            jsNameDeclareTemporaryName = JsScope.declareTemporaryName("controller");
            List<JsParameter> parameters6 = jsFunction.getParameters();
            List<JsParameter> parameters7 = jsFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters7, "getParameters(...)");
            parameters6.add(CollectionsKt.getLastIndex(parameters7), new JsParameter(jsNameDeclareTemporaryName));
        } else {
            jsNameDeclareTemporaryName = null;
        }
        Intrinsics.checkNotNull(name2);
        JsNameRef jsNameRefMakeRef = name2.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        List<JsParameter> parameters8 = this.function.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters8, "getParameters(...)");
        List<JsParameter> list4 = parameters8;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
        Iterator<T> it2 = list4.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((JsParameter) it2.next()).getName());
        }
        ArrayList arrayList3 = arrayList2;
        JsFunction jsFunction3 = this.innerFunction;
        if (jsFunction3 != null && (parameters = jsFunction3.getParameters()) != null) {
            List<JsParameter> list5 = parameters;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
            Iterator<T> it3 = list5.iterator();
            while (it3.hasNext()) {
                arrayList4.add(((JsParameter) it3.next()).getName());
            }
            arrayListEmptyList = arrayList4;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        Set set = CollectionsKt.toSet(CollectionsKt.plus((Collection) arrayList3, (Iterable) arrayListEmptyList));
        List<JsStatement> statements2 = jsFunction.getBody().getStatements();
        JsExpression jsExpressionDeepCopy = context.getMetadata().getBaseClassRef().deepCopy();
        Intrinsics.checkNotNullExpressionValue(jsExpressionDeepCopy, "deepCopy(...)");
        Intrinsics.checkNotNull(statements2);
        statements2.add(new JsInvocation(Namer.getFunctionCallRef(jsExpressionDeepCopy), new JsThisRef(), jsNameRefMakeRef).source((Object) psiElement).makeStmt());
        if (jsNameDeclareTemporaryName != null) {
            JsName controllerFieldName = context.getControllerFieldName();
            Intrinsics.checkNotNullExpressionValue(controllerFieldName, "<get-controllerFieldName>(...)");
            JsNameRef jsNameRefMakeRef2 = jsNameDeclareTemporaryName.makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
            assignToField(statements2, controllerFieldName, jsNameRefMakeRef2, psiElement);
        }
        assignToField(statements2, context.getMetadata().getExceptionStateName(), new JsIntLiteral(globalCatchBlockIndex), psiElement);
        if (context.getMetadata().getHasReceiver()) {
            JsName receiverFieldName = context.getReceiverFieldName();
            Intrinsics.checkNotNullExpressionValue(receiverFieldName, "<get-receiverFieldName>(...)");
            JsNameRef jsNameRefMakeRef3 = context.getReceiverFieldName().makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef3, "makeRef(...)");
            assignToField(statements2, receiverFieldName, jsNameRefMakeRef3, psiElement);
        }
        for (JsName jsName : survivingLocalVars) {
            JsNameRef undefinedExpression = !set.contains(jsName) ? Namer.getUndefinedExpression() : jsName.makeRef();
            Intrinsics.checkNotNull(undefinedExpression);
            assignToField(statements2, context.getFieldName(jsName), undefinedExpression, psiElement);
        }
        JsName name3 = jsFunction.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        List listListOf = CollectionsKt.listOf((Object[]) new JsStatement[]{jsFunction.makeStmt(), generateCoroutineMetadata(name3)});
        JsName name4 = jsFunction.getName();
        Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
        statements.addAll(0, CollectionsKt.plus((Collection) listListOf, (Iterable) generateCoroutinePrototype(name4)));
    }

    private final List<JsStatement> generateCoroutinePrototype(JsName constructorName) {
        JsExpression jsExpressionPrototypeOf = JsAstUtils.prototypeOf(new JsNameRef(constructorName));
        Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf, "prototypeOf(...)");
        CoroutineMetadata coroutineMetadata = MetadataProperties.getCoroutineMetadata(this.function);
        Intrinsics.checkNotNull(coroutineMetadata);
        JsInvocation jsInvocationCreateObjectWithPrototypeFrom = Namer.createObjectWithPrototypeFrom(coroutineMetadata.getBaseClassRef().deepCopy());
        Intrinsics.checkNotNullExpressionValue(jsInvocationCreateObjectWithPrototypeFrom, "createObjectWithPrototypeFrom(...)");
        JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(jsExpressionPrototypeOf, jsInvocationCreateObjectWithPrototypeFrom);
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAssignment, "assignment(...)");
        JsBinaryOperation jsBinaryOperationAssignment2 = JsAstUtils.assignment(new JsNameRef(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, jsExpressionPrototypeOf.deepCopy()), new JsNameRef(constructorName));
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAssignment2, "assignment(...)");
        JsStatement jsStatementMakeStmt = jsBinaryOperationAssignment.makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        JsStatement jsStatementMakeStmt2 = jsBinaryOperationAssignment2.makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt2, "makeStmt(...)");
        return CollectionsKt.listOf((Object[]) new JsStatement[]{jsStatementMakeStmt, jsStatementMakeStmt2});
    }

    private final JsStatement generateCoroutineMetadata(JsName constructorName) {
        CoroutineMetadata coroutineMetadata = MetadataProperties.getCoroutineMetadata(this.function);
        Intrinsics.checkNotNull(coroutineMetadata);
        JsExpression jsExpressionDeepCopy = coroutineMetadata.getBaseClassRef().deepCopy();
        Intrinsics.checkNotNullExpressionValue(jsExpressionDeepCopy, "deepCopy(...)");
        JsObjectLiteral jsObjectLiteral = new JsObjectLiteral(true);
        List<JsPropertyInitializer> propertyInitializers = jsObjectLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
        propertyInitializers.add(new JsPropertyInitializer(new JsNameRef(Namer.METADATA_CLASS_KIND), new JsNameRef("CLASS", new JsNameRef(Namer.CLASS_KIND_ENUM, "Kotlin"))));
        List<JsPropertyInitializer> propertyInitializers2 = jsObjectLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers2, "getPropertyInitializers(...)");
        propertyInitializers2.add(new JsPropertyInitializer(new JsNameRef(Namer.METADATA_SIMPLE_NAME), new JsNullLiteral()));
        List<JsPropertyInitializer> propertyInitializers3 = jsObjectLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers3, "getPropertyInitializers(...)");
        propertyInitializers3.add(new JsPropertyInitializer(new JsNameRef(Namer.METADATA_SUPERTYPES), new JsArrayLiteral(CollectionsKt.listOf(jsExpressionDeepCopy))));
        JsStatement jsStatementMakeStmt = JsAstUtils.assignment(new JsNameRef(Namer.METADATA, constructorName.makeRef()), jsObjectLiteral).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    private final void generateDoResume(List<CoroutineBlock> coroutineBlocks, CoroutineTransformationContext context, List<JsStatement> statements) {
        JsFunction jsFunction = new JsFunction(this.function.getScope().getParent(), new JsBlock(), "resume function");
        PsiElement psiElement = context.getMetadata().getPsiElement();
        jsFunction.setSource(psiElement);
        JsBlock body = jsFunction.getBody();
        PsiElement finalElement = psiElement != null ? UtilsKt.getFinalElement(psiElement) : null;
        body.setSource(finalElement instanceof LeafPsiElement ? (LeafPsiElement) finalElement : null);
        List<JsStatement> listGenerateCoroutineBody = generateCoroutineBody(context, coroutineBlocks);
        this.functionWithBody.getBody().getStatements().clear();
        List<JsStatement> statements2 = jsFunction.getBody().getStatements();
        Intrinsics.checkNotNull(statements2);
        CollectionsKt.addAll(statements2, listGenerateCoroutineBody);
        assignToPrototype(statements, context.getMetadata().getDoResumeName(), jsFunction);
        new FunctionPostProcessor(jsFunction).apply();
    }

    private final void generateCoroutineInstantiation(CoroutineTransformationContext context) {
        PsiElement psiElement = context.getMetadata().getPsiElement();
        JsNew jsNew = new JsNew(this.className.makeRef());
        jsNew.setSource(psiElement);
        if (context.getMetadata().getHasReceiver()) {
            List<JsExpression> arguments = jsNew.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            arguments.add(new JsThisRef());
        }
        List<JsParameter> parameters = this.function.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<JsParameter> list = parameters;
        JsFunction jsFunction = this.innerFunction;
        List<JsParameter> parameters2 = jsFunction != null ? jsFunction.getParameters() : null;
        if (parameters2 == null) {
            parameters2 = CollectionsKt.emptyList();
        }
        List listPlus = CollectionsKt.plus((Collection) list, (Iterable) parameters2);
        List<JsExpression> arguments2 = jsNew.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
        List<JsExpression> list2 = arguments2;
        List listDropLast = CollectionsKt.dropLast(listPlus, 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
        Iterator it = listDropLast.iterator();
        while (it.hasNext()) {
            arrayList.add(((JsParameter) it.next()).getName().makeRef());
        }
        CollectionsKt.addAll(list2, arrayList);
        CoroutineMetadata coroutineMetadata = MetadataProperties.getCoroutineMetadata(this.function);
        Intrinsics.checkNotNull(coroutineMetadata);
        if (coroutineMetadata.getHasController()) {
            List<JsExpression> arguments3 = jsNew.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments3, "getArguments(...)");
            arguments3.add(new JsThisRef());
        }
        List<JsExpression> arguments4 = jsNew.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments4, "getArguments(...)");
        arguments4.add(((JsParameter) CollectionsKt.last(listPlus)).getName().makeRef());
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName("suspended");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        List<JsParameter> parameters3 = this.functionWithBody.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters3, "getParameters(...)");
        parameters3.add(new JsParameter(jsNameDeclareTemporaryName));
        JsName jsNameDeclareTemporaryName2 = JsScope.declareTemporaryName("instance");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName2, "declareTemporaryName(...)");
        List<JsStatement> statements = this.functionWithBody.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        statements.add(JsAstUtils.newVar(jsNameDeclareTemporaryName2, jsNew));
        JsReturn jsReturn = new JsReturn(new JsInvocation(new JsNameRef(context.getMetadata().getDoResumeName(), jsNameDeclareTemporaryName2.makeRef()), new JsNullLiteral()).source((Object) psiElement));
        List<JsStatement> statements2 = this.functionWithBody.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        statements2.add(new JsIf(jsNameDeclareTemporaryName.makeRef().source((Object) psiElement), new JsReturn(jsNameDeclareTemporaryName2.makeRef().source((Object) psiElement)), jsReturn));
    }

    private final List<JsStatement> generateCoroutineBody(CoroutineTransformationContext context, List<CoroutineBlock> blocks) {
        int iIndexOf = blocks.indexOf(context.getGlobalCatchBlock());
        JsNameRef jsNameRef = new JsNameRef(context.getMetadata().getStateName(), new JsThisRef());
        JsNameRef jsNameRef2 = new JsNameRef(context.getMetadata().getExceptionStateName(), new JsThisRef());
        JsNameRef jsNameRef3 = jsNameRef;
        JsBinaryOperation jsBinaryOperationEquality = JsAstUtils.equality(jsNameRef3, new JsIntLiteral(iIndexOf));
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationEquality, "equality(...)");
        JsCatch jsCatch = new JsCatch(this.functionWithBody.getScope(), "e");
        JsBlock jsBlock = new JsBlock(JsAstUtils.assignment(jsNameRef.deepCopy(), jsNameRef2.deepCopy()).makeStmt(), JsAstUtils.assignment(new JsNameRef(context.getMetadata().getExceptionName(), new JsThisRef()), jsCatch.getParameter().getName().makeRef()).makeStmt());
        JsStatement jsStatementMakeStmt = JsAstUtils.assignment(jsNameRef2.deepCopy(), jsNameRef.deepCopy()).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        jsCatch.setBody(new JsBlock(new JsIf(jsBinaryOperationEquality, new JsBlock(jsStatementMakeStmt, new JsThrow(jsCatch.getParameter().getName().makeRef())), jsBlock)));
        context.getGlobalCatchBlock().getStatements().add(new JsThrow(new JsNameRef(context.getMetadata().getExceptionName(), new JsThisRef())));
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(blocks);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10));
        for (IndexedValue indexedValue : iterableWithIndex) {
            int index = indexedValue.getIndex();
            CoroutineBlock coroutineBlock = (CoroutineBlock) indexedValue.component2();
            JsCase jsCase = new JsCase();
            jsCase.setCaseExpression(new JsIntLiteral(index));
            List<JsStatement> statements = jsCase.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            CollectionsKt.addAll(statements, coroutineBlock.getStatements());
            arrayList.add(jsCase);
        }
        JsDefault jsDefault = new JsDefault();
        JsBlock jsBlock2 = new JsBlock(JsAstUtils.assignment(jsNameRef3, new JsIntLiteral(iIndexOf)).makeStmt(), new JsThrow(new JsNew(new JsNameRef("Error"), CollectionsKt.listOf(new JsStringLiteral("State Machine Unreachable execution")))));
        List<JsStatement> statements2 = jsDefault.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        statements2.add(jsBlock2);
        return CollectionsKt.listOf(new JsDoWhile(new JsBooleanLiteral(true), new JsTry(new JsBlock(new JsSwitch(jsNameRef.deepCopy(), CollectionsKt.plus((Collection<? extends JsDefault>) arrayList, jsDefault))), jsCatch, (JsBlock) null)));
    }

    private final void collectAdditionalLocalVariables(JsBlock jsBlock) {
        jsBlock.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.coroutine.CoroutineFunctionTransformer.collectAdditionalLocalVariables.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsVars.JsVar x) {
                Intrinsics.checkNotNullParameter(x, "x");
                super.visit(x);
                CoroutineFunctionTransformer.this.localVariables.add(x.getName());
            }
        });
    }

    private final void assignToField(List<JsStatement> list, JsName jsName, JsExpression jsExpression, PsiElement psiElement) {
        list.add(JsAstUtils.assignment(new JsNameRef(jsName, new JsThisRef()), jsExpression).source((Object) psiElement).makeStmt());
    }

    private final void assignToPrototype(List<JsStatement> list, JsName jsName, JsExpression jsExpression) {
        list.add(JsAstUtils.assignment(new JsNameRef(jsName, JsAstUtils.prototypeOf(this.className.makeRef())), jsExpression).makeStmt());
    }
}
