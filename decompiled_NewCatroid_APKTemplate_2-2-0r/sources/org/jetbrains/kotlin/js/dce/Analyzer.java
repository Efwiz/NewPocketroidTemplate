package org.jetbrains.kotlin.js.dce;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitor;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Analyzer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u000e2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!H\u0002J\"\u0010#\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u000e2\u0010\u0010 \u001a\f\u0012\b\u0012\u00060\u0010R\u00020\u00030!H\u0002J\u0016\u0010$\u001a\b\u0018\u00010\u0010R\u00020\u00032\u0006\u0010%\u001a\u00020\"H\u0002J\u001e\u0010&\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020\u00122\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!H\u0002J\u001e\u0010(\u001a\u00020\u001e2\n\u0010)\u001a\u00060\u0010R\u00020\u00032\b\u0010*\u001a\u0004\u0018\u00010\"H\u0002J.\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020\u00152\b\u0010)\u001a\u0004\u0018\u00010\"2\b\u0010-\u001a\u0004\u0018\u00010\"2\b\u0010.\u001a\u0004\u0018\u00010\"H\u0002J\u0018\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u00152\u0006\u00102\u001a\u000203H\u0002J(\u00104\u001a\b\u0018\u00010\u0010R\u00020\u00032\b\u00105\u001a\u0004\u0018\u00010\u000b2\u0006\u00106\u001a\u00020\"2\u0006\u00107\u001a\u00020\"H\u0002J\u0010\u00108\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0002J(\u00109\u001a\u001a\u0012\u0004\u0012\u00020\u000e\u0012\u000e\u0012\f\u0012\b\u0012\u00060\u0010R\u00020\u00030!\u0018\u00010:2\u0006\u0010%\u001a\u00020\"H\u0002J\u0010\u0010;\u001a\u00020\u001e2\u0006\u0010<\u001a\u00020=H\u0016J\u0010\u0010>\u001a\u00020\u001e2\u0006\u0010<\u001a\u00020?H\u0016J\u0010\u0010@\u001a\u00020\u001e2\u0006\u0010<\u001a\u00020AH\u0016J\u0010\u0010B\u001a\u00020\u001e2\u0006\u0010<\u001a\u00020CH\u0016J\u0010\u0010D\u001a\u00020\u001e2\u0006\u0010<\u001a\u00020EH\u0016J\u0010\u0010F\u001a\u00020\u001e2\u0006\u0010<\u001a\u00020GH\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000f\u001a\f\u0012\b\u0012\u00060\u0010R\u00020\u00030\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001e\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\b\u0012\u00060\u0010R\u00020\u00030\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u000e0\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/js/dce/Analyzer;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitor;", "context", "Lorg/jetbrains/kotlin/js/dce/Context;", "(Lorg/jetbrains/kotlin/js/dce/Context;)V", "analysisResult", "Lorg/jetbrains/kotlin/js/dce/AnalysisResult;", "getAnalysisResult", "()Lorg/jetbrains/kotlin/js/dce/AnalysisResult;", "astNodesToEliminate", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "astNodesToSkip", "functionsToEnter", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "functionsToSkip", "Lorg/jetbrains/kotlin/js/dce/Context$Node;", "invocationsToSkip", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "moduleMapping", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "", "getModuleMapping", "()Ljava/util/Map;", "nodeMap", "postponedFunctions", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "processedFunctions", "enterFunction", "", "function", "arguments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "enterFunctionWithGivenNodes", "extractVariableInitializedByEmptyObject", "expression", "handleAmdDefine", "invocation", "handleObjectCreate", "target", ParameterDescription.NAME_PREFIX, "handleObjectDefineProperty", "statement", "propertyName", "propertyDescriptor", "isProperFunctionalParameter", "", "body", "parameter", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "processAssignment", "node", "lhs", "rhs", "processFunction", "tryExtractFunction", "Lkotlin/Pair;", "visit", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "visitBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "visitExpressionStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "visitIf", "Lorg/jetbrains/kotlin/js/backend/ast/JsIf;", "visitReturn", "Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;", "visitVars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "js.dce"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Analyzer extends JsVisitor {
    private final AnalysisResult analysisResult;
    private final Set<JsNode> astNodesToEliminate;
    private final Set<JsNode> astNodesToSkip;
    private final Context context;
    private final Set<JsFunction> functionsToEnter;
    private final Set<Context.Node> functionsToSkip;
    private final Set<JsInvocation> invocationsToSkip;
    private final Map<JsStatement, String> moduleMapping;
    private final Map<JsNode, Context.Node> nodeMap;
    private final Map<JsName, JsFunction> postponedFunctions;
    private final Set<JsFunction> processedFunctions;

    public Analyzer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.processedFunctions = new LinkedHashSet();
        this.postponedFunctions = new LinkedHashMap();
        this.nodeMap = new LinkedHashMap();
        this.astNodesToEliminate = new LinkedHashSet();
        this.astNodesToSkip = new LinkedHashSet();
        this.invocationsToSkip = new LinkedHashSet();
        this.moduleMapping = new LinkedHashMap();
        this.functionsToEnter = new LinkedHashSet();
        this.functionsToSkip = new LinkedHashSet();
        this.analysisResult = new AnalysisResult() { // from class: org.jetbrains.kotlin.js.dce.Analyzer$analysisResult$1
            @Override // org.jetbrains.kotlin.js.dce.AnalysisResult
            public Map<JsNode, Context.Node> getNodeMap() {
                return this.this$0.nodeMap;
            }

            @Override // org.jetbrains.kotlin.js.dce.AnalysisResult
            public Set<JsNode> getAstNodesToEliminate() {
                return this.this$0.astNodesToEliminate;
            }

            @Override // org.jetbrains.kotlin.js.dce.AnalysisResult
            public Set<JsNode> getAstNodesToSkip() {
                return this.this$0.astNodesToSkip;
            }

            @Override // org.jetbrains.kotlin.js.dce.AnalysisResult
            public Set<JsFunction> getFunctionsToEnter() {
                return this.this$0.functionsToEnter;
            }

            @Override // org.jetbrains.kotlin.js.dce.AnalysisResult
            public Set<JsInvocation> getInvocationsToSkip() {
                return this.this$0.invocationsToSkip;
            }

            @Override // org.jetbrains.kotlin.js.dce.AnalysisResult
            public Set<Context.Node> getFunctionsToSkip() {
                return this.this$0.functionsToSkip;
            }
        };
    }

    public final Map<JsStatement, String> getModuleMapping() {
        return this.moduleMapping;
    }

    public final AnalysisResult getAnalysisResult() {
        return this.analysisResult;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitVars(JsVars x) {
        Intrinsics.checkNotNullParameter(x, "x");
        List<JsVars.JsVar> vars = x.getVars();
        Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
        Iterator<T> it = vars.iterator();
        while (it.hasNext()) {
            accept((JsVars.JsVar) it.next());
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visit(JsVars.JsVar x) {
        Intrinsics.checkNotNullParameter(x, "x");
        JsExpression initExpression = x.getInitExpression();
        if (initExpression != null) {
            JsNameRef jsNameRefMakeRef = x.getName().makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
            Context.Node nodeProcessAssignment = processAssignment(x, jsNameRefMakeRef, initExpression);
            if (nodeProcessAssignment != null) {
                this.nodeMap.put(x, nodeProcessAssignment);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitExpressionStatement(JsExpressionStatement x) {
        Intrinsics.checkNotNullParameter(x, "x");
        JsExpression expression = x.getExpression();
        Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
        if (expression instanceof JsBinaryOperation) {
            JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) expression;
            if (jsBinaryOperation.getOperator() == JsBinaryOperator.ASG) {
                JsExpression arg1 = jsBinaryOperation.getArg1();
                Intrinsics.checkNotNullExpressionValue(arg1, "getArg1(...)");
                JsExpression arg2 = jsBinaryOperation.getArg2();
                Intrinsics.checkNotNullExpressionValue(arg2, "getArg2(...)");
                Context.Node nodeProcessAssignment = processAssignment(x, arg1, arg2);
                if (nodeProcessAssignment != null) {
                    this.nodeMap.put(x, nodeProcessAssignment);
                    return;
                }
                return;
            }
            return;
        }
        if (expression instanceof JsFunction) {
            JsFunction jsFunction = (JsFunction) expression;
            JsName name2 = jsFunction.getName();
            if (name2 != null) {
                Context.Node node = this.context.getNodes().get(name2);
                Context.Node original = node != null ? node.getOriginal() : null;
                if (original != null) {
                    this.nodeMap.put(x, original);
                    original.addFunction(jsFunction);
                    return;
                }
                return;
            }
            return;
        }
        if (expression instanceof JsInvocation) {
            JsInvocation jsInvocation = (JsInvocation) expression;
            JsExpression qualifier = jsInvocation.getQualifier();
            Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
            if (qualifier instanceof JsFunction) {
                List<JsExpression> arguments = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                enterFunction((JsFunction) qualifier, arguments);
                return;
            }
            if (qualifier instanceof JsNameRef) {
                JsNameRef jsNameRef = (JsNameRef) qualifier;
                if (jsNameRef.getQualifier() == null) {
                    JsName name3 = jsNameRef.getName();
                    JsFunction jsFunction2 = name3 != null ? this.postponedFunctions.get(name3) : null;
                    if (jsFunction2 != null) {
                        List<JsExpression> arguments2 = jsInvocation.getArguments();
                        Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
                        enterFunction(jsFunction2, arguments2);
                        this.invocationsToSkip.add(expression);
                        return;
                    }
                }
            }
            if (UtilKt.isObjectDefineProperty(this.context, qualifier)) {
                List<JsExpression> arguments3 = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments3, "getArguments(...)");
                JsExpression jsExpression = (JsExpression) CollectionsKt.getOrNull(arguments3, 0);
                List<JsExpression> arguments4 = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments4, "getArguments(...)");
                JsExpression jsExpression2 = (JsExpression) CollectionsKt.getOrNull(arguments4, 1);
                List<JsExpression> arguments5 = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments5, "getArguments(...)");
                handleObjectDefineProperty(x, jsExpression, jsExpression2, (JsExpression) CollectionsKt.getOrNull(arguments5, 2));
                return;
            }
            if (UtilKt.isDefineModule(this.context, qualifier)) {
                this.astNodesToEliminate.add(x);
            } else if (UtilKt.isAmdDefine(this.context, qualifier)) {
                List<JsExpression> arguments6 = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments6, "getArguments(...)");
                handleAmdDefine(jsInvocation, arguments6);
            }
        }
    }

    private final void handleObjectDefineProperty(JsStatement statement, JsExpression target, JsExpression propertyName, JsExpression propertyDescriptor) {
        Context.Node nodeExtractNode;
        if (target == null || !(propertyName instanceof JsStringLiteral) || propertyDescriptor == null || (nodeExtractNode = this.context.extractNode(target)) == null) {
            return;
        }
        JsStringLiteral jsStringLiteral = (JsStringLiteral) propertyName;
        String value = jsStringLiteral.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        Context.Node nodeMember = nodeExtractNode.member(value);
        this.nodeMap.put(statement, nodeMember);
        nodeMember.setHasSideEffects(true);
        if (propertyDescriptor instanceof JsObjectLiteral) {
            for (JsPropertyInitializer jsPropertyInitializer : ((JsObjectLiteral) propertyDescriptor).getPropertyInitializers()) {
                JsNameRef jsNameRef = new JsNameRef(jsStringLiteral.getValue(), target);
                JsExpression valueExpr = jsPropertyInitializer.getValueExpr();
                Intrinsics.checkNotNullExpressionValue(valueExpr, "getValueExpr(...)");
                processAssignment(statement, jsNameRef, valueExpr);
            }
            return;
        }
        if (propertyDescriptor instanceof JsInvocation) {
            JsInvocation jsInvocation = (JsInvocation) propertyDescriptor;
            JsExpression qualifier = jsInvocation.getQualifier();
            Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
            if (UtilKt.isObjectGetOwnPropertyDescriptor(this.context, qualifier)) {
                List<JsExpression> arguments = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                JsExpression jsExpression = (JsExpression) CollectionsKt.getOrNull(arguments, 0);
                List<JsExpression> arguments2 = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
                JsExpression jsExpression2 = (JsExpression) CollectionsKt.getOrNull(arguments2, 1);
                if (jsExpression == null || !(jsExpression2 instanceof JsStringLiteral)) {
                    return;
                }
                processAssignment(statement, new JsNameRef(jsStringLiteral.getValue(), target), new JsNameRef(((JsStringLiteral) jsExpression2).getValue(), jsExpression));
            }
        }
    }

    private final void handleAmdDefine(JsInvocation invocation, List<? extends JsExpression> arguments) {
        JsFunction jsFunction;
        Context.Node nodeMember;
        int size = arguments.size();
        if (size != 2) {
            if (size != 3) {
                return;
            } else {
                arguments = CollectionsKt.drop(arguments, 1);
            }
        }
        JsExpression jsExpression = arguments.get(0);
        JsArrayLiteral jsArrayLiteral = jsExpression instanceof JsArrayLiteral ? (JsArrayLiteral) jsExpression : null;
        if (jsArrayLiteral == null) {
            return;
        }
        JsExpression jsExpression2 = arguments.get(1);
        if (jsExpression2 instanceof JsFunction) {
            jsFunction = (JsFunction) jsExpression2;
        } else {
            if (!(jsExpression2 instanceof JsNameRef)) {
                return;
            }
            JsNameRef jsNameRef = (JsNameRef) jsExpression2;
            if (jsNameRef.getQualifier() != null || (jsFunction = this.postponedFunctions.get(jsNameRef.getName())) == null) {
                return;
            }
        }
        List<JsExpression> expressions = jsArrayLiteral.getExpressions();
        Intrinsics.checkNotNullExpressionValue(expressions, "getExpressions(...)");
        List<JsExpression> list = expressions;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (JsExpression jsExpression3 : list) {
            JsStringLiteral jsStringLiteral = jsExpression3 instanceof JsStringLiteral ? (JsStringLiteral) jsExpression3 : null;
            if (jsStringLiteral == null) {
                return;
            } else {
                arrayList.add(jsStringLiteral);
            }
        }
        ArrayList<JsStringLiteral> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (JsStringLiteral jsStringLiteral2 : arrayList2) {
            if (Intrinsics.areEqual(jsStringLiteral2.getValue(), PsiKeyword.EXPORTS)) {
                nodeMember = this.context.getCurrentModule();
            } else {
                Context.Node globalScope = this.context.getGlobalScope();
                String value = jsStringLiteral2.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                nodeMember = globalScope.member(value);
            }
            arrayList3.add(nodeMember);
        }
        enterFunctionWithGivenNodes(jsFunction, arrayList3);
        this.astNodesToSkip.add(invocation.getQualifier());
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitBlock(JsBlock x) {
        Intrinsics.checkNotNullParameter(x, "x");
        String str = this.moduleMapping.get(x);
        if (str != null) {
            Context context = this.context;
            context.setCurrentModule(context.getGlobalScope().member(str));
        }
        List<JsStatement> statements = x.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        Iterator<T> it = statements.iterator();
        while (it.hasNext()) {
            accept((JsStatement) it.next());
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitIf(JsIf x) {
        Intrinsics.checkNotNullParameter(x, "x");
        accept(x.getThenStatement());
        JsStatement elseStatement = x.getElseStatement();
        if (elseStatement != null) {
            elseStatement.accept(this);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitReturn(JsReturn x) {
        Context.Node nodeExtractNode;
        Intrinsics.checkNotNullParameter(x, "x");
        JsExpression expression = x.getExpression();
        if (expression == null || (nodeExtractNode = this.context.extractNode(expression)) == null) {
            return;
        }
        this.nodeMap.put(x, nodeExtractNode);
    }

    private final Context.Node processAssignment(JsNode node, JsExpression lhs, JsExpression rhs) {
        JsName name2;
        JsFunction jsFunction;
        Context.Node nodeExtractNode = this.context.extractNode(lhs);
        Context.Node nodeExtractNode2 = this.context.extractNode(rhs);
        if (nodeExtractNode != null && nodeExtractNode2 != null) {
            nodeExtractNode.alias(nodeExtractNode2);
            return nodeExtractNode;
        }
        if (nodeExtractNode != null) {
            if (rhs instanceof JsInvocation) {
                JsInvocation jsInvocation = (JsInvocation) rhs;
                JsExpression qualifier = jsInvocation.getQualifier();
                Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
                if (qualifier instanceof JsFunction) {
                    List<JsExpression> arguments = jsInvocation.getArguments();
                    Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                    enterFunction((JsFunction) qualifier, arguments);
                    this.astNodesToSkip.add(lhs);
                    return null;
                }
                if (qualifier instanceof JsNameRef) {
                    JsNameRef jsNameRef = (JsNameRef) qualifier;
                    if (jsNameRef.getQualifier() == null && (name2 = jsNameRef.getName()) != null && (jsFunction = this.postponedFunctions.get(name2)) != null) {
                        List<JsExpression> arguments2 = jsInvocation.getArguments();
                        Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
                        enterFunction(jsFunction, arguments2);
                        this.astNodesToSkip.add(lhs);
                        return null;
                    }
                }
                if (UtilKt.isObjectFunction(this.context, qualifier, CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME)) {
                    List<JsExpression> arguments3 = jsInvocation.getArguments();
                    Intrinsics.checkNotNullExpressionValue(arguments3, "getArguments(...)");
                    handleObjectCreate(nodeExtractNode, (JsExpression) CollectionsKt.getOrNull(arguments3, 0));
                    return nodeExtractNode;
                }
                if (UtilKt.isDefineInlineFunction(this.context, qualifier) && jsInvocation.getArguments().size() == 2) {
                    JsExpression jsExpression = jsInvocation.getArguments().get(1);
                    Intrinsics.checkNotNullExpressionValue(jsExpression, "get(...)");
                    Pair<JsFunction, List<Context.Node>> pairTryExtractFunction = tryExtractFunction(jsExpression);
                    if (pairTryExtractFunction != null) {
                        JsFunction jsFunctionComponent1 = pairTryExtractFunction.component1();
                        List<Context.Node> listComponent2 = pairTryExtractFunction.component2();
                        nodeExtractNode.addFunction(jsFunctionComponent1);
                        Context.Node nodeExtractNode3 = this.context.extractNode(qualifier);
                        if (nodeExtractNode3 != null) {
                            nodeExtractNode.addDependency(nodeExtractNode3);
                        }
                        Iterator<T> it = listComponent2.iterator();
                        while (it.hasNext()) {
                            nodeExtractNode.addDependency((Context.Node) it.next());
                        }
                        return nodeExtractNode;
                    }
                }
                Pair<JsFunction, List<Context.Node>> pairTryExtractFunction2 = tryExtractFunction(rhs);
                if (pairTryExtractFunction2 != null) {
                    JsFunction jsFunctionComponent2 = pairTryExtractFunction2.component1();
                    List<Context.Node> listComponent3 = pairTryExtractFunction2.component2();
                    nodeExtractNode.addFunction(jsFunctionComponent2);
                    Iterator<T> it2 = listComponent3.iterator();
                    while (it2.hasNext()) {
                        nodeExtractNode.addDependency((Context.Node) it2.next());
                    }
                    return nodeExtractNode;
                }
            } else if (rhs instanceof JsBinaryOperation) {
                JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) rhs;
                if (jsBinaryOperation.getOperator() == JsBinaryOperator.OR) {
                    Context context = this.context;
                    JsExpression arg1 = jsBinaryOperation.getArg1();
                    Intrinsics.checkNotNullExpressionValue(arg1, "getArg1(...)");
                    Context.Node nodeExtractNode4 = context.extractNode(arg1);
                    JsExpression arg2 = jsBinaryOperation.getArg2();
                    if (arg2 instanceof JsBinaryOperation) {
                        JsBinaryOperation jsBinaryOperation2 = (JsBinaryOperation) arg2;
                        if (jsBinaryOperation2.getOperator() == JsBinaryOperator.ASG) {
                            Context context2 = this.context;
                            JsExpression arg3 = jsBinaryOperation2.getArg1();
                            Intrinsics.checkNotNullExpressionValue(arg3, "getArg1(...)");
                            Context.Node nodeExtractNode5 = context2.extractNode(arg3);
                            JsExpression arg4 = jsBinaryOperation2.getArg2();
                            if (Intrinsics.areEqual(nodeExtractNode5, nodeExtractNode4) && nodeExtractNode5 != null && (arg4 instanceof JsObjectLiteral) && ((JsObjectLiteral) arg4).getPropertyInitializers().isEmpty()) {
                                JsExpression arg5 = jsBinaryOperation.getArg1();
                                Intrinsics.checkNotNullExpressionValue(arg5, "getArg1(...)");
                                return processAssignment(node, lhs, arg5);
                            }
                        }
                    }
                }
            } else {
                if (rhs instanceof JsFunction) {
                    nodeExtractNode.addFunction((JsFunction) rhs);
                    return nodeExtractNode;
                }
                if (Intrinsics.areEqual(nodeExtractNode.getMemberName(), Namer.METADATA)) {
                    nodeExtractNode.addExpression(rhs);
                    return nodeExtractNode;
                }
                if ((rhs instanceof JsObjectLiteral) && ((JsObjectLiteral) rhs).getPropertyInitializers().isEmpty()) {
                    return nodeExtractNode;
                }
            }
            Context.Node nodeExtractVariableInitializedByEmptyObject = extractVariableInitializedByEmptyObject(rhs);
            if (nodeExtractVariableInitializedByEmptyObject != null) {
                this.astNodesToSkip.add(rhs);
                nodeExtractNode.alias(nodeExtractVariableInitializedByEmptyObject);
                return nodeExtractNode;
            }
        }
        return null;
    }

    private final Pair<JsFunction, List<Context.Node>> tryExtractFunction(JsExpression expression) {
        if (expression instanceof JsFunction) {
            return new Pair<>(expression, CollectionsKt.emptyList());
        }
        Context.Node node = null;
        if (expression instanceof JsInvocation) {
            Context context = this.context;
            JsInvocation jsInvocation = (JsInvocation) expression;
            JsExpression qualifier = jsInvocation.getQualifier();
            Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
            if (UtilKt.isWrapFunction(context, qualifier)) {
                List<JsExpression> arguments = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                Object orNull = CollectionsKt.getOrNull(arguments, 0);
                JsFunction jsFunction = orNull instanceof JsFunction ? (JsFunction) orNull : null;
                if (jsFunction != null) {
                    List<JsStatement> statements = jsFunction.getBody().getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : statements) {
                        if (!(((JsStatement) obj) instanceof JsReturn)) {
                            arrayList.add(obj);
                        }
                    }
                    JsBlock jsBlock = new JsBlock(arrayList);
                    this.context.addNodesForLocalVars(CollectUtilsKt.collectDefinedNames(jsBlock));
                    accept(jsBlock);
                    Context context2 = this.context;
                    JsExpression qualifier2 = jsInvocation.getQualifier();
                    Intrinsics.checkNotNullExpressionValue(qualifier2, "getQualifier(...)");
                    Context.Node nodeExtractNode = context2.extractNode(qualifier2);
                    if (nodeExtractNode != null) {
                        this.functionsToSkip.add(nodeExtractNode);
                        node = nodeExtractNode;
                    }
                    List<JsStatement> statements2 = jsFunction.getBody().getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj2 : statements2) {
                        if (obj2 instanceof JsReturn) {
                            arrayList2.add(obj2);
                        }
                    }
                    JsExpression expression2 = ((JsReturn) CollectionsKt.first((List) arrayList2)).getExpression();
                    Intrinsics.checkNotNull(expression2, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsFunction");
                    return new Pair<>((JsFunction) expression2, CollectionsKt.listOfNotNull(node));
                }
            }
        }
        return null;
    }

    private final void handleObjectCreate(Context.Node target, JsExpression arg) {
        Context.Node nodeExtractNode;
        if (arg == null || (nodeExtractNode = this.context.extractNode(arg)) == null) {
            return;
        }
        target.addDependency(nodeExtractNode.getOriginal());
        target.addExpression(arg);
    }

    private final Context.Node extractVariableInitializedByEmptyObject(JsExpression expression) {
        if (!(expression instanceof JsConditional)) {
            return null;
        }
        JsConditional jsConditional = (JsConditional) expression;
        JsExpression testExpression = jsConditional.getTestExpression();
        JsBinaryOperation jsBinaryOperation = testExpression instanceof JsBinaryOperation ? (JsBinaryOperation) testExpression : null;
        if (jsBinaryOperation == null || jsBinaryOperation.getOperator() != JsBinaryOperator.REF_EQ) {
            return null;
        }
        JsExpression arg1 = jsBinaryOperation.getArg1();
        JsPrefixOperation jsPrefixOperation = arg1 instanceof JsPrefixOperation ? (JsPrefixOperation) arg1 : null;
        if (jsPrefixOperation == null || jsPrefixOperation.getOperator() != JsUnaryOperator.TYPEOF) {
            return null;
        }
        Context context = this.context;
        JsExpression arg = jsPrefixOperation.getArg();
        Intrinsics.checkNotNullExpressionValue(arg, "getArg(...)");
        Context.Node nodeExtractNode = context.extractNode(arg);
        if (nodeExtractNode == null) {
            return null;
        }
        JsExpression arg2 = jsBinaryOperation.getArg2();
        JsStringLiteral jsStringLiteral = arg2 instanceof JsStringLiteral ? (JsStringLiteral) arg2 : null;
        if (jsStringLiteral == null || !Intrinsics.areEqual(jsStringLiteral.getValue(), "undefined")) {
            return null;
        }
        JsExpression thenExpression = jsConditional.getThenExpression();
        JsObjectLiteral jsObjectLiteral = thenExpression instanceof JsObjectLiteral ? (JsObjectLiteral) thenExpression : null;
        if (jsObjectLiteral == null) {
            return null;
        }
        List<JsPropertyInitializer> propertyInitializers = jsObjectLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
        if (!propertyInitializers.isEmpty()) {
            return null;
        }
        Context context2 = this.context;
        JsExpression elseExpression = jsConditional.getElseExpression();
        Intrinsics.checkNotNullExpressionValue(elseExpression, "getElseExpression(...)");
        Context.Node nodeExtractNode2 = context2.extractNode(elseExpression);
        if (nodeExtractNode2 != null && Intrinsics.areEqual(nodeExtractNode.getOriginal(), nodeExtractNode2.getOriginal())) {
            return nodeExtractNode.getOriginal();
        }
        return null;
    }

    private final void enterFunction(JsFunction function, List<? extends JsExpression> arguments) {
        this.functionsToEnter.add(function);
        this.context.addNodesForLocalVars(CollectUtilsKt.collectLocalVariables$default(function, false, 1, null));
        Context context = this.context;
        JsBlock body = function.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        context.markSpecialFunctions(body);
        List<JsParameter> parameters = function.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        for (Pair pair : CollectionsKt.zip(parameters, arguments)) {
            JsParameter jsParameter = (JsParameter) pair.component1();
            JsExpression jsExpression = (JsExpression) pair.component2();
            if (jsExpression instanceof JsFunction) {
                JsFunction jsFunction = (JsFunction) jsExpression;
                if (jsFunction.getName() == null) {
                    JsBlock body2 = jsFunction.getBody();
                    Intrinsics.checkNotNullExpressionValue(body2, "getBody(...)");
                    Intrinsics.checkNotNull(jsParameter);
                    if (isProperFunctionalParameter(body2, jsParameter)) {
                        Map<JsName, JsFunction> map = this.postponedFunctions;
                        JsName name2 = jsParameter.getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        map.put(name2, (JsFunction) jsExpression);
                    }
                }
            }
            JsNameRef jsNameRefMakeRef = jsParameter.getName().makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
            if (processAssignment(function, jsNameRefMakeRef, jsExpression) != null) {
                this.astNodesToSkip.add(jsExpression);
            }
        }
        processFunction(function);
    }

    private final void enterFunctionWithGivenNodes(JsFunction function, List<Context.Node> arguments) {
        this.functionsToEnter.add(function);
        this.context.addNodesForLocalVars(CollectUtilsKt.collectLocalVariables$default(function, false, 1, null));
        Context context = this.context;
        JsBlock body = function.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        context.markSpecialFunctions(body);
        List<JsParameter> parameters = function.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        for (Pair pair : CollectionsKt.zip(parameters, arguments)) {
            JsParameter jsParameter = (JsParameter) pair.component1();
            Context.Node node = (Context.Node) pair.component2();
            Context.Node node2 = this.context.getNodes().get(jsParameter.getName());
            Intrinsics.checkNotNull(node2);
            node2.alias(node);
        }
        processFunction(function);
    }

    private final void processFunction(JsFunction function) {
        if (this.processedFunctions.add(function)) {
            accept(function.getBody());
        }
    }

    private final boolean isProperFunctionalParameter(JsStatement body, final JsParameter parameter) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        booleanRef.element = true;
        body.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.dce.Analyzer.isProperFunctionalParameter.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitInvocation(JsInvocation invocation) {
                Intrinsics.checkNotNullParameter(invocation, "invocation");
                JsExpression qualifier = invocation.getQualifier();
                Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
                if (qualifier instanceof JsNameRef) {
                    JsNameRef jsNameRef = (JsNameRef) qualifier;
                    if (jsNameRef.getQualifier() == null && Intrinsics.areEqual(jsNameRef.getName(), parameter.getName())) {
                        List<JsExpression> arguments = invocation.getArguments();
                        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                        List<JsExpression> list = arguments;
                        Analyzer analyzer = this;
                        if ((list instanceof Collection) && list.isEmpty()) {
                            return;
                        }
                        for (JsExpression jsExpression : list) {
                            Context context = analyzer.context;
                            Intrinsics.checkNotNull(jsExpression);
                            if (context.extractNode(jsExpression) != null) {
                            }
                        }
                        return;
                    }
                }
                if (UtilKt.isAmdDefine(this.context, qualifier)) {
                    return;
                }
                super.visitInvocation(invocation);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNameRef(JsNameRef nameRef) {
                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                if (Intrinsics.areEqual(nameRef.getName(), parameter.getName())) {
                    booleanRef.element = false;
                }
                super.visitNameRef(nameRef);
            }
        });
        return booleanRef.element;
    }
}
