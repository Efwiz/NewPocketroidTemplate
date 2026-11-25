package org.jetbrains.kotlin.js.inline.clean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.js.backend.ast.HasName;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.CollectionUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: removeDefaultInitializers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u0012\u0010\n\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u0012\u0010\n\u001a\u0004\u0018\u00010\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a\u0018\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u000eH\u0002\u001a\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001a*\u0010\u0016\u001a\u00020\u00132\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\u0019\u001a\u00020\u001a¨\u0006\u001b"}, d2 = {"getDefaultParamsNames", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "args", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "params", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "initialized", "", "getNameFromInitializer", "isInitializedExpr", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;", "statement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "isNameInitialized", "name", "initializer", "markAssignmentAsStaticRef", "", "node", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "removeDefaultInitializers", "arguments", "parameters", "body", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RemoveDefaultInitializersKt {
    public static final void removeDefaultInitializers(List<? extends JsExpression> arguments, List<JsParameter> parameters, JsBlock body) {
        List<JsStatement> listListOf;
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(body, "body");
        Set<JsName> defaultParamsNames = getDefaultParamsNames(arguments, parameters, true);
        Set<JsName> defaultParamsNames2 = getDefaultParamsNames(arguments, parameters, false);
        List<JsStatement> statements = body.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        ArrayList arrayList = new ArrayList();
        for (JsStatement jsStatement : statements) {
            Intrinsics.checkNotNull(jsStatement);
            JsName nameFromInitializer = getNameFromInitializer(jsStatement);
            if (nameFromInitializer != null && !isNameInitialized(nameFromInitializer, jsStatement)) {
                throw new AssertionError("Unexpected initializer structure");
            }
            if (nameFromInitializer != null && defaultParamsNames.contains(nameFromInitializer)) {
                listListOf = CollectionsKt.emptyList();
            } else if (nameFromInitializer != null && defaultParamsNames2.contains(nameFromInitializer)) {
                JsStatement thenStatement = ((JsIf) jsStatement).getThenStatement();
                Intrinsics.checkNotNullExpressionValue(thenStatement, "getThenStatement(...)");
                markAssignmentAsStaticRef(nameFromInitializer, thenStatement);
                listListOf = JsAstUtils.flattenStatement(thenStatement);
                Intrinsics.checkNotNull(listListOf);
            } else {
                listListOf = CollectionsKt.listOf(jsStatement);
            }
            CollectionsKt.addAll(arrayList, listListOf);
        }
        statements.clear();
        statements.addAll(arrayList);
    }

    private static final void markAssignmentAsStaticRef(final JsName jsName, JsNode jsNode) {
        jsNode.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.RemoveDefaultInitializersKt.markAssignmentAsStaticRef.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBinaryExpression(JsBinaryOperation x) {
                Intrinsics.checkNotNullParameter(x, "x");
                Pair<JsName, JsExpression> pairDecomposeAssignmentToVariable = JsAstUtils.decomposeAssignmentToVariable(x);
                if (pairDecomposeAssignmentToVariable != null) {
                    JsName jsName2 = jsName;
                    JsName jsNameComponent1 = pairDecomposeAssignmentToVariable.component1();
                    JsExpression jsExpressionComponent2 = pairDecomposeAssignmentToVariable.component2();
                    if (Intrinsics.areEqual(jsNameComponent1, jsName2)) {
                        Intrinsics.checkNotNull(jsNameComponent1);
                        MetadataProperties.setStaticRef(jsNameComponent1, jsExpressionComponent2);
                    }
                }
                super.visitBinaryExpression(x);
            }
        });
    }

    private static final JsName getNameFromInitializer(JsStatement jsStatement) {
        JsIf jsIf = jsStatement instanceof JsIf ? (JsIf) jsStatement : null;
        JsExpression ifExpression = jsIf != null ? jsIf.getIfExpression() : null;
        if ((jsIf != null ? jsIf.getElseStatement() : null) == null && (ifExpression instanceof JsBinaryOperation)) {
            return getNameFromInitializer((JsBinaryOperation) ifExpression);
        }
        return null;
    }

    private static final JsName getNameFromInitializer(JsBinaryOperation jsBinaryOperation) {
        JsExpression arg1 = jsBinaryOperation.getArg1();
        JsExpression arg2 = jsBinaryOperation.getArg2();
        JsBinaryOperator operator = jsBinaryOperation.getOperator();
        Intrinsics.checkNotNullExpressionValue(operator, "getOperator(...)");
        if (arg1 == null || arg2 == null || operator != JsBinaryOperator.REF_EQ || !JsAstUtils.isUndefinedExpression(arg2)) {
            return null;
        }
        JsNameRef jsNameRef = arg1 instanceof JsNameRef ? (JsNameRef) arg1 : null;
        if (jsNameRef != null) {
            return jsNameRef.getName();
        }
        return null;
    }

    private static final boolean isNameInitialized(JsName jsName, JsStatement jsStatement) {
        Intrinsics.checkNotNull(jsStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsIf");
        JsStatement thenStatement = ((JsIf) jsStatement).getThenStatement();
        Intrinsics.checkNotNullExpressionValue(thenStatement, "getThenStatement(...)");
        List<JsStatement> listFlattenStatement = JsAstUtils.flattenStatement(thenStatement);
        Intrinsics.checkNotNullExpressionValue(listFlattenStatement, "flattenStatement(...)");
        JsStatement jsStatement2 = (JsStatement) CollectionsKt.last((List) listFlattenStatement);
        JsExpressionStatement jsExpressionStatement = jsStatement2 instanceof JsExpressionStatement ? (JsExpressionStatement) jsStatement2 : null;
        JsExpression expression = jsExpressionStatement != null ? jsExpressionStatement.getExpression() : null;
        if (!(expression instanceof JsBinaryOperation)) {
            return false;
        }
        JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) expression;
        JsBinaryOperator operator = jsBinaryOperation.getOperator();
        Intrinsics.checkNotNullExpressionValue(operator, "getOperator(...)");
        if (!operator.isAssignment()) {
            return false;
        }
        JsNode arg1 = jsBinaryOperation.getArg1();
        return (arg1 instanceof HasName) && ((HasName) arg1).getName() == jsName;
    }

    private static final Set<JsName> getDefaultParamsNames(List<? extends JsExpression> list, List<JsParameter> list2, final boolean z) {
        JsExpression undefinedExpression = Namer.getUndefinedExpression();
        Intrinsics.checkNotNullExpressionValue(undefinedExpression, "getUndefinedExpression(...)");
        return SequencesKt.toSet(SequencesKt.map(SequencesKt.filter(SequencesKt.filter(CollectionsKt.asSequence(CollectionUtilsKt.zipWithDefault(list, list2, undefinedExpression)), new Function1<Pair<? extends JsExpression, ? extends JsParameter>, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.RemoveDefaultInitializersKt$getDefaultParamsNames$relevantParams$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Boolean invoke2(Pair<? extends JsExpression, JsParameter> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(MetadataProperties.getHasDefaultValue(it.getSecond()));
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Pair<? extends JsExpression, ? extends JsParameter> pair) {
                return invoke2((Pair<? extends JsExpression, JsParameter>) pair);
            }
        }), new Function1<Pair<? extends JsExpression, ? extends JsParameter>, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.RemoveDefaultInitializersKt$getDefaultParamsNames$relevantParams$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Boolean invoke2(Pair<? extends JsExpression, JsParameter> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(z == (JsAstUtils.isUndefinedExpression(it.getFirst()) ^ true));
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Pair<? extends JsExpression, ? extends JsParameter> pair) {
                return invoke2((Pair<? extends JsExpression, JsParameter>) pair);
            }
        }), new Function1<Pair<? extends JsExpression, ? extends JsParameter>, JsName>() { // from class: org.jetbrains.kotlin.js.inline.clean.RemoveDefaultInitializersKt$getDefaultParamsNames$names$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ JsName invoke(Pair<? extends JsExpression, ? extends JsParameter> pair) {
                return invoke2((Pair<? extends JsExpression, JsParameter>) pair);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final JsName invoke2(Pair<? extends JsExpression, JsParameter> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getSecond().getName();
            }
        }));
    }
}
