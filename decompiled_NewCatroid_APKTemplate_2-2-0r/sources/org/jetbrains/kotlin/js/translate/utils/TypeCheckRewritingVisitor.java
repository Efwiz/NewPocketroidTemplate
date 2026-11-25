package org.jetbrains.kotlin.js.translate.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.TypeCheck;
import org.jetbrains.kotlin.js.inline.util.CollectionUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: expandIsCalls.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0016J$\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u000b0\u000b0\u00172\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J\u001c\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\u00172\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J(\u0010\u001b\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001f2\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J \u0010 \u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020\u000bH\u0002J\u0018\u0010#\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u000bH\u0002J\u001c\u0010%\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0016J\u001e\u0010%\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u001d2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020&0\u0015H\u0016J\u001c\u0010%\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020'2\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0015H\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\fR\u0018\u0010\r\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0018\u0010\u000e\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\f¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/js/translate/utils/TypeCheckRewritingVisitor;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "()V", "localVars", "Ljava/util/Stack;", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "scopes", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "isAssignmentToLocalVar", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Z", "isLocalVar", "needsAlias", "getNeedsAlias", "endVisit", "", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "expandArgumentForTwoInvocations", "Lkotlin/Pair;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "argument", "generateAlias", "getReplacement", "callee", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "calleeArguments", "", "getReplacementForAndPredicate", "p1", "p2", "getReplacementForOrNull", "calleeArgument", "visit", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars$JsVar;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TypeCheckRewritingVisitor extends JsVisitorWithContextImpl {
    private final Stack<Set<JsName>> localVars;
    private final Stack<JsScope> scopes = new Stack<>();

    /* JADX INFO: compiled from: expandIsCalls.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeCheck.values().length];
            try {
                iArr[TypeCheck.TYPEOF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeCheck.INSTANCEOF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeCheck.OR_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TypeCheck.AND_PREDICATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public TypeCheckRewritingVisitor() {
        Stack<Set<JsName>> stack = new Stack<>();
        stack.push(new LinkedHashSet());
        this.localVars = stack;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsFunction x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.scopes.push(x.getScope());
        Stack<Set<JsName>> stack = this.localVars;
        Set<JsName> setIdentitySet = CollectionUtilsKt.IdentitySet();
        Set<JsName> set = setIdentitySet;
        List<JsParameter> parameters = x.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<JsParameter> list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            JsName name2 = ((JsParameter) it.next()).getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            arrayList.add(name2);
        }
        CollectionsKt.addAll(set, arrayList);
        stack.push(setIdentitySet);
        return super.visit(x, ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsVars.JsVar x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Set<JsName> setPeek = this.localVars.peek();
        JsName name2 = x.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        setPeek.add(name2);
        return super.visit(x, ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public void endVisit(JsFunction x, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.scopes.pop();
        this.localVars.pop();
        super.endVisit(x, (JsContext) ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsInvocation x, JsContext<JsNode> ctx) {
        JsExpression replacement;
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsExpression qualifier = x.getQualifier();
        JsInvocation jsInvocation = qualifier instanceof JsInvocation ? (JsInvocation) qualifier : null;
        List<JsExpression> arguments = jsInvocation != null ? jsInvocation.getArguments() : null;
        List<JsExpression> arguments2 = x.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
        JsExpression jsExpression = (JsExpression) CollectionsKt.firstOrNull((List) arguments2);
        if (jsInvocation == null || jsExpression == null || arguments == null || (replacement = getReplacement(jsInvocation, arguments, jsExpression)) == null) {
            return true;
        }
        ctx.replaceMe(((JsExpression) accept(replacement)).source(x.getSource()));
        return false;
    }

    private final JsExpression getReplacement(JsInvocation callee, List<? extends JsExpression> calleeArguments, JsExpression argument) {
        TypeCheck typeCheck = MetadataProperties.getTypeCheck(callee);
        int i = typeCheck == null ? -1 : WhenMappings.$EnumSwitchMapping$0[typeCheck.ordinal()];
        JsBinaryOperation jsBinaryOperationTypeOfIs = null;
        if (i == -1) {
            return null;
        }
        if (i == 1) {
            if (calleeArguments.size() == 1) {
                JsExpression jsExpression = calleeArguments.get(0);
                Intrinsics.checkNotNull(jsExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsStringLiteral");
                jsBinaryOperationTypeOfIs = JsAstUtils.typeOfIs(argument, (JsStringLiteral) jsExpression);
            }
            return jsBinaryOperationTypeOfIs;
        }
        if (i == 2) {
            if (calleeArguments.size() == 1) {
                return Namer.isInstanceOf(argument, calleeArguments.get(0));
            }
            return null;
        }
        if (i == 3) {
            if (calleeArguments.size() == 1) {
                return getReplacementForOrNull(argument, calleeArguments.get(0));
            }
            return null;
        }
        if (i == 4) {
            if (calleeArguments.size() == 2) {
                return getReplacementForAndPredicate(argument, calleeArguments.get(0), calleeArguments.get(1));
            }
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final JsExpression getReplacementForOrNull(JsExpression argument, JsExpression calleeArgument) {
        if ((calleeArgument instanceof JsInvocation) && MetadataProperties.getTypeCheck((JsInvocation) calleeArgument) == TypeCheck.OR_NULL) {
            return new JsInvocation(calleeArgument, argument);
        }
        Pair<JsExpression, JsExpression> pairExpandArgumentForTwoInvocations = expandArgumentForTwoInvocations(argument);
        JsExpression jsExpressionComponent1 = pairExpandArgumentForTwoInvocations.component1();
        JsExpression jsExpressionComponent2 = pairExpandArgumentForTwoInvocations.component2();
        JsBinaryOperation jsBinaryOperationIsNullCheck = TranslationUtils.isNullCheck(jsExpressionComponent1);
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationIsNullCheck, "isNullCheck(...)");
        JsBinaryOperation jsBinaryOperationOr = JsAstUtils.or(jsBinaryOperationIsNullCheck, new JsInvocation(calleeArgument, jsExpressionComponent2));
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationOr, "or(...)");
        return jsBinaryOperationOr;
    }

    private final JsExpression getReplacementForAndPredicate(JsExpression argument, JsExpression p1, JsExpression p2) {
        Pair<JsExpression, JsExpression> pairExpandArgumentForTwoInvocations = expandArgumentForTwoInvocations(argument);
        JsBinaryOperation jsBinaryOperationAnd = JsAstUtils.and((JsExpression) accept(new JsInvocation(p1, pairExpandArgumentForTwoInvocations.component1())), (JsExpression) accept(new JsInvocation(p2, pairExpandArgumentForTwoInvocations.component2())));
        Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAnd, "and(...)");
        return jsBinaryOperationAnd;
    }

    private final Pair<JsExpression, JsExpression> expandArgumentForTwoInvocations(JsExpression argument) {
        if (!isAssignmentToLocalVar(argument)) {
            return getNeedsAlias(argument) ? generateAlias(argument) : new Pair<>(argument, argument);
        }
        Intrinsics.checkNotNull(argument, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation");
        return new Pair<>(argument, ((JsBinaryOperation) argument).getArg1());
    }

    private final Pair<JsExpression, JsExpression> generateAlias(JsExpression argument) {
        JsName jsNameDeclareTemporary = JsScope.declareTemporary();
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporary, "declareTemporary(...)");
        JsContext<JsStatement> lastStatementLevelContext = getLastStatementLevelContext();
        Intrinsics.checkNotNullExpressionValue(lastStatementLevelContext, "getLastStatementLevelContext(...)");
        lastStatementLevelContext.addPrevious(JsAstUtils.newVar(jsNameDeclareTemporary, null));
        return new Pair<>(JsAstUtils.assignment(jsNameDeclareTemporary.makeRef(), argument), jsNameDeclareTemporary.makeRef());
    }

    private final boolean getNeedsAlias(JsExpression jsExpression) {
        return ((jsExpression instanceof JsLiteral.JsValueLiteral) || isLocalVar(jsExpression)) ? false : true;
    }

    private final boolean isLocalVar(JsExpression jsExpression) {
        JsName name2;
        return !this.localVars.isEmpty() && (jsExpression instanceof JsNameRef) && (name2 = ((JsNameRef) jsExpression).getName()) != null && this.localVars.peek().contains(name2);
    }

    private final boolean isAssignmentToLocalVar(JsExpression jsExpression) {
        Pair<JsName, JsExpression> pairDecomposeAssignmentToVariable;
        return (this.localVars.isEmpty() || (pairDecomposeAssignmentToVariable = JsAstUtils.decomposeAssignmentToVariable(jsExpression)) == null || !this.localVars.peek().contains(pairDecomposeAssignmentToVariable.getFirst())) ? false : true;
    }
}
