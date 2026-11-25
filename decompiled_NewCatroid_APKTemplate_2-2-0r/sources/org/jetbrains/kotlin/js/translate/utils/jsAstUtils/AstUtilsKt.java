package org.jetbrains.kotlin.js.translate.utils.jsAstUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: compiled from: astUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000n\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0005\u001a#\u0010\u001a\u001a\u00020\u001b*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u001f¢\u0006\u0002\u0010 \u001a\u0012\u0010!\u001a\u00020\"*\u00020\u001c2\u0006\u0010#\u001a\u00020$\u001a\u001e\u0010%\u001a\u00020&*\u00020'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020&0)\u001a+\u0010*\u001a\u00020+*\u00020,2\u0006\u0010-\u001a\u00020\u001e2\u0012\u0010.\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010/\"\u00020\u0001¢\u0006\u0002\u00100\u001a\u0012\u00101\u001a\u00020+*\u00020,2\u0006\u00102\u001a\u00020\u0001\u001a\u0012\u00103\u001a\u00020+*\u00020,2\u0006\u00102\u001a\u00020\u0001\u001a(\u00104\u001a\u00020\u0001*\u00020\u00012\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u0001062\u0006\u00107\u001a\u00020\u001f2\u0006\u00108\u001a\u00020\u0001\u001a\u0012\u00109\u001a\u00020+*\u00020,2\u0006\u00102\u001a\u00020\u0001\u001a\u0012\u0010:\u001a\u00020+*\u00020,2\u0006\u00102\u001a\u00020\u0001\"(\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\"(\u0010\b\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007\"(\u0010\u000b\u001a\u00020\u0001*\u00020\f2\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\"(\u0010\u0011\u001a\u00020\u0001*\u00020\f2\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0010\"(\u0010\u0011\u001a\u00020\u0001*\u00020\u00142\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0015\"\u0004\b\u0013\u0010\u0016\"(\u0010\u0017\u001a\u00020\u0001*\u00020\f2\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u000e\"\u0004\b\u0019\u0010\u0010¨\u0006;"}, d2 = {"value", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "array", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayAccess;", "getArray", "(Lorg/jetbrains/kotlin/js/backend/ast/JsArrayAccess;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "setArray", "(Lorg/jetbrains/kotlin/js/backend/ast/JsArrayAccess;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "index", "getIndex", "setIndex", "otherwise", "Lorg/jetbrains/kotlin/js/backend/ast/JsConditional;", "getOtherwise", "(Lorg/jetbrains/kotlin/js/backend/ast/JsConditional;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "setOtherwise", "(Lorg/jetbrains/kotlin/js/backend/ast/JsConditional;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "test", "getTest", "setTest", "Lorg/jetbrains/kotlin/js/backend/ast/JsWhile;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsWhile;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsWhile;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "then", "getThen", "setThen", "addParameter", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "identifier", "", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Ljava/lang/String;Ljava/lang/Integer;)Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "addStatement", "", "stmt", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "any", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "predicate", "Lkotlin/Function1;", "invokeKotlinFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "functionName", "arguments", "", "(Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;Ljava/lang/String;[Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "toByte", "expression", "toChar", "toInvocationWith", "leadingExtraArgs", "", "parameterCount", "thisExpr", "toLong", "toShort", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AstUtilsKt {
    public static final void addStatement(JsFunction jsFunction, JsStatement stmt) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        Intrinsics.checkNotNullParameter(stmt, "stmt");
        jsFunction.getBody().getStatements().add(stmt);
    }

    public static /* synthetic */ JsParameter addParameter$default(JsFunction jsFunction, String str, Integer num, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        return addParameter(jsFunction, str, num);
    }

    public static final JsParameter addParameter(JsFunction jsFunction, String identifier, Integer num) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        Intrinsics.checkNotNullParameter(identifier, "identifier");
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(identifier);
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        JsParameter jsParameter = new JsParameter(jsNameDeclareTemporaryName);
        if (num == null) {
            jsFunction.getParameters().add(jsParameter);
        } else {
            jsFunction.getParameters().add(num.intValue(), jsParameter);
        }
        return jsParameter;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt$any$visitor$1] */
    public static final boolean any(JsNode jsNode, final Function1<? super JsNode, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(jsNode, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        ?? r0 = new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt$any$visitor$1
            private boolean matched;

            public final boolean getMatched() {
                return this.matched;
            }

            public final void setMatched(boolean z) {
                this.matched = z;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
            protected void visitElement(JsNode node) {
                Intrinsics.checkNotNullParameter(node, "node");
                boolean z = this.matched || predicate.invoke(node).booleanValue();
                this.matched = z;
                if (z) {
                    return;
                }
                super.visitElement(node);
            }
        };
        r0.accept(jsNode);
        return r0.getMatched();
    }

    private static final List<JsExpression> toInvocationWith$padArguments(int i, List<? extends JsExpression> list) {
        List<? extends JsExpression> list2 = list;
        IntRange intRange = new IntRange(1, i - list.size());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
        Iterator<Integer> it = intRange.iterator();
        while (it.hasNext()) {
            ((IntIterator) it).nextInt();
            JsExpression undefinedExpression = Namer.getUndefinedExpression();
            Intrinsics.checkNotNullExpressionValue(undefinedExpression, "getUndefinedExpression(...)");
            arrayList.add(undefinedExpression);
        }
        return CollectionsKt.plus((Collection) list2, (Iterable) arrayList);
    }

    public static final JsExpression toInvocationWith(JsExpression jsExpression, List<? extends JsExpression> leadingExtraArgs, int i, JsExpression thisExpr) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        Intrinsics.checkNotNullParameter(leadingExtraArgs, "leadingExtraArgs");
        Intrinsics.checkNotNullParameter(thisExpr, "thisExpr");
        if (jsExpression instanceof JsNew) {
            JsNew jsNew = (JsNew) jsExpression;
            JsNameRef functionCallRef = Namer.getFunctionCallRef(jsNew.getConstructorExpression());
            Intrinsics.checkNotNullExpressionValue(functionCallRef, "getFunctionCallRef(...)");
            List listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOf(thisExpr), (Iterable) leadingExtraArgs);
            List<JsExpression> arguments = jsNew.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            JsExpression jsExpressionSource = new JsInvocation(functionCallRef, (List<? extends JsExpression>) CollectionsKt.plus((Collection) listPlus, (Iterable) arguments)).source(jsNew.getSource());
            Intrinsics.checkNotNull(jsExpressionSource);
            return jsExpressionSource;
        }
        if (jsExpression instanceof JsInvocation) {
            JsInvocation jsInvocation = (JsInvocation) jsExpression;
            JsExpression qualifier = jsInvocation.getQualifier();
            Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
            List<JsExpression> arguments2 = jsInvocation.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
            JsExpression jsExpressionSource2 = new JsInvocation(qualifier, (List<? extends JsExpression>) CollectionsKt.plus((Collection<? extends JsExpression>) CollectionsKt.plus((Collection) leadingExtraArgs, (Iterable) toInvocationWith$padArguments(i, arguments2)), thisExpr)).source(jsInvocation.getSource());
            Intrinsics.checkNotNull(jsExpressionSource2);
            return jsExpressionSource2;
        }
        throw new IllegalStateException("Unexpected node type: " + jsExpression.getClass());
    }

    public static final JsExpression getTest(JsWhile jsWhile) {
        Intrinsics.checkNotNullParameter(jsWhile, "<this>");
        JsExpression condition = jsWhile.getCondition();
        Intrinsics.checkNotNullExpressionValue(condition, "getCondition(...)");
        return condition;
    }

    public static final void setTest(JsWhile jsWhile, JsExpression value) {
        Intrinsics.checkNotNullParameter(jsWhile, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        jsWhile.setCondition(value);
    }

    public static final JsExpression getIndex(JsArrayAccess jsArrayAccess) {
        Intrinsics.checkNotNullParameter(jsArrayAccess, "<this>");
        JsExpression indexExpression = jsArrayAccess.getIndexExpression();
        Intrinsics.checkNotNullExpressionValue(indexExpression, "getIndexExpression(...)");
        return indexExpression;
    }

    public static final void setIndex(JsArrayAccess jsArrayAccess, JsExpression value) {
        Intrinsics.checkNotNullParameter(jsArrayAccess, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        jsArrayAccess.setIndexExpression(value);
    }

    public static final JsExpression getArray(JsArrayAccess jsArrayAccess) {
        Intrinsics.checkNotNullParameter(jsArrayAccess, "<this>");
        JsExpression arrayExpression = jsArrayAccess.getArrayExpression();
        Intrinsics.checkNotNullExpressionValue(arrayExpression, "getArrayExpression(...)");
        return arrayExpression;
    }

    public static final void setArray(JsArrayAccess jsArrayAccess, JsExpression value) {
        Intrinsics.checkNotNullParameter(jsArrayAccess, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        jsArrayAccess.setArrayExpression(value);
    }

    public static final JsExpression getTest(JsConditional jsConditional) {
        Intrinsics.checkNotNullParameter(jsConditional, "<this>");
        JsExpression testExpression = jsConditional.getTestExpression();
        Intrinsics.checkNotNullExpressionValue(testExpression, "getTestExpression(...)");
        return testExpression;
    }

    public static final void setTest(JsConditional jsConditional, JsExpression value) {
        Intrinsics.checkNotNullParameter(jsConditional, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        jsConditional.setTestExpression(value);
    }

    public static final JsExpression getThen(JsConditional jsConditional) {
        Intrinsics.checkNotNullParameter(jsConditional, "<this>");
        JsExpression thenExpression = jsConditional.getThenExpression();
        Intrinsics.checkNotNullExpressionValue(thenExpression, "getThenExpression(...)");
        return thenExpression;
    }

    public static final void setThen(JsConditional jsConditional, JsExpression value) {
        Intrinsics.checkNotNullParameter(jsConditional, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        jsConditional.setThenExpression(value);
    }

    public static final JsExpression getOtherwise(JsConditional jsConditional) {
        Intrinsics.checkNotNullParameter(jsConditional, "<this>");
        JsExpression elseExpression = jsConditional.getElseExpression();
        Intrinsics.checkNotNullExpressionValue(elseExpression, "getElseExpression(...)");
        return elseExpression;
    }

    public static final void setOtherwise(JsConditional jsConditional, JsExpression value) {
        Intrinsics.checkNotNullParameter(jsConditional, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        jsConditional.setElseExpression(value);
    }

    public static final JsInvocation invokeKotlinFunction(TranslationContext translationContext, String functionName, JsExpression... arguments) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(functionName, "functionName");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return new JsInvocation(translationContext.getReferenceToIntrinsic(functionName), (List<? extends JsExpression>) ArraysKt.toList(arguments));
    }

    public static final JsInvocation toByte(TranslationContext translationContext, JsExpression expression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        String identifier = OperatorConventions.BYTE.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return invokeKotlinFunction(translationContext, identifier, expression);
    }

    public static final JsInvocation toShort(TranslationContext translationContext, JsExpression expression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        String identifier = OperatorConventions.SHORT.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return invokeKotlinFunction(translationContext, identifier, expression);
    }

    public static final JsInvocation toChar(TranslationContext translationContext, JsExpression expression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        String identifier = OperatorConventions.CHAR.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return invokeKotlinFunction(translationContext, identifier, expression);
    }

    public static final JsInvocation toLong(TranslationContext translationContext, JsExpression expression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        String identifier = OperatorConventions.LONG.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return invokeKotlinFunction(translationContext, identifier, expression);
    }
}
