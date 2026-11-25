package org.jetbrains.kotlin.js.inline.clean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: compiled from: RedundantStatementElimination.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\n\u001a\u00020\u0006J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/RedundantStatementElimination;", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)V", "hasChanges", "", "localVars", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "apply", "process", "", SchemaSymbols.ATTVAL_REPLACE, "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "expression", "replaceMany", "expressions", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantStatementElimination {
    private boolean hasChanges;
    private final Set<JsName> localVars;
    private final JsFunction root;

    /* JADX INFO: compiled from: RedundantStatementElimination.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[JsUnaryOperator.values().length];
            try {
                iArr[JsUnaryOperator.BIT_NOT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JsUnaryOperator.NOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JsUnaryOperator.TYPEOF.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[JsUnaryOperator.VOID.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[JsUnaryOperator.POS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[JsUnaryOperator.NEG.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[JsUnaryOperator.DEC.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[JsUnaryOperator.INC.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[JsUnaryOperator.DELETE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[JsBinaryOperator.values().length];
            try {
                iArr2[JsBinaryOperator.BIT_AND.ordinal()] = 1;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[JsBinaryOperator.BIT_OR.ordinal()] = 2;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[JsBinaryOperator.BIT_XOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[JsBinaryOperator.COMMA.ordinal()] = 4;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[JsBinaryOperator.ADD.ordinal()] = 5;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr2[JsBinaryOperator.SUB.ordinal()] = 6;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr2[JsBinaryOperator.MUL.ordinal()] = 7;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr2[JsBinaryOperator.DIV.ordinal()] = 8;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr2[JsBinaryOperator.MOD.ordinal()] = 9;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr2[JsBinaryOperator.EQ.ordinal()] = 10;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr2[JsBinaryOperator.NEQ.ordinal()] = 11;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr2[JsBinaryOperator.REF_EQ.ordinal()] = 12;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr2[JsBinaryOperator.REF_NEQ.ordinal()] = 13;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr2[JsBinaryOperator.GT.ordinal()] = 14;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr2[JsBinaryOperator.GTE.ordinal()] = 15;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr2[JsBinaryOperator.LT.ordinal()] = 16;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr2[JsBinaryOperator.LTE.ordinal()] = 17;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr2[JsBinaryOperator.SHL.ordinal()] = 18;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr2[JsBinaryOperator.SHR.ordinal()] = 19;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr2[JsBinaryOperator.SHRU.ordinal()] = 20;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr2[JsBinaryOperator.AND.ordinal()] = 21;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr2[JsBinaryOperator.OR.ordinal()] = 22;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr2[JsBinaryOperator.INOP.ordinal()] = 23;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr2[JsBinaryOperator.INSTANCEOF.ordinal()] = 24;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr2[JsBinaryOperator.ASG.ordinal()] = 25;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_ADD.ordinal()] = 26;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_SUB.ordinal()] = 27;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_MUL.ordinal()] = 28;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_DIV.ordinal()] = 29;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_MOD.ordinal()] = 30;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_BIT_AND.ordinal()] = 31;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_BIT_OR.ordinal()] = 32;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_BIT_XOR.ordinal()] = 33;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_SHL.ordinal()] = 34;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_SHR.ordinal()] = 35;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_SHRU.ordinal()] = 36;
            } catch (NoSuchFieldError unused45) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public RedundantStatementElimination(JsFunction root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
        this.localVars = CollectUtilsKt.collectLocalVariables$default(root, false, 1, null);
    }

    public final boolean apply() {
        process();
        return this.hasChanges;
    }

    private final void process() {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.RedundantStatementElimination.process.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsExpressionStatement x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                if (!MetadataProperties.isSuspend(expression)) {
                    JsExpression expression2 = x.getExpression();
                    Intrinsics.checkNotNullExpressionValue(expression2, "getExpression(...)");
                    JsExpressionStatement jsExpressionStatement = x;
                    if (MetadataProperties.getSynthetic(jsExpressionStatement) || MetadataProperties.getSynthetic(expression2)) {
                        RedundantStatementElimination redundantStatementElimination = RedundantStatementElimination.this;
                        JsExpression expression3 = x.getExpression();
                        Intrinsics.checkNotNullExpressionValue(expression3, "getExpression(...)");
                        List listReplace = redundantStatementElimination.replace(expression3);
                        if (listReplace.size() != 1 || !Intrinsics.areEqual(listReplace.get(0), x.getExpression())) {
                            RedundantStatementElimination.this.hasChanges = true;
                            List list = listReplace;
                            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                JsExpressionStatement jsExpressionStatement2 = new JsExpressionStatement((JsExpression) it.next());
                                MetadataProperties.setSynthetic(jsExpressionStatement2, true);
                                arrayList.add(jsExpressionStatement2);
                            }
                            ctx.addPrevious(arrayList);
                            ctx.removeMe();
                        }
                    } else if ((expression2 instanceof JsBinaryOperation) && ((JsBinaryOperation) expression2).getOperator() == JsBinaryOperator.COMMA) {
                        JsExpression arg1 = expression2;
                        while (arg1 instanceof JsBinaryOperation) {
                            JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) arg1;
                            if (jsBinaryOperation.getOperator() != JsBinaryOperator.COMMA) {
                                break;
                            }
                            RedundantStatementElimination redundantStatementElimination2 = RedundantStatementElimination.this;
                            JsExpression arg2 = jsBinaryOperation.getArg2();
                            Intrinsics.checkNotNullExpressionValue(arg2, "getArg2(...)");
                            if (!redundantStatementElimination2.replace(arg2).isEmpty()) {
                                break;
                            }
                            arg1 = jsBinaryOperation.getArg1();
                            Intrinsics.checkNotNullExpressionValue(arg1, "getArg1(...)");
                        }
                        if (!Intrinsics.areEqual(arg1, expression2)) {
                            JsExpressionStatement jsExpressionStatement3 = new JsExpressionStatement(arg1);
                            jsExpressionStatement3.copyMetadataFrom(jsExpressionStatement);
                            ctx.replaceMe(jsExpressionStatement3);
                        }
                    }
                }
                return super.visit(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsBinaryOperation x, JsContext<JsNode> ctx) {
                JsExpression jsExpressionNewSequence;
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (!MetadataProperties.isSuspend(x) && x.getOperator() == JsBinaryOperator.COMMA) {
                    RedundantStatementElimination redundantStatementElimination = RedundantStatementElimination.this;
                    JsExpression arg1 = x.getArg1();
                    Intrinsics.checkNotNullExpressionValue(arg1, "getArg1(...)");
                    List listReplace = redundantStatementElimination.replace(arg1);
                    if (listReplace.isEmpty()) {
                        jsExpressionNewSequence = x.getArg2();
                    } else {
                        jsExpressionNewSequence = JsAstUtils.newSequence(CollectionsKt.plus((Collection<? extends JsExpression>) listReplace, x.getArg2()));
                    }
                    ctx.replaceMe(jsExpressionNewSequence);
                }
                return super.visit(x, ctx);
            }
        }.accept(this.root.getBody());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<JsExpression> replace(JsExpression expression) {
        if (expression instanceof JsNameRef) {
            JsNameRef jsNameRef = (JsNameRef) expression;
            if (CollectionsKt.contains(this.localVars, jsNameRef.getName())) {
                return CollectionsKt.emptyList();
            }
            if (MetadataProperties.getSideEffects(expression) != SideEffectKind.AFFECTS_STATE) {
                JsExpression qualifier = jsNameRef.getQualifier();
                return qualifier != null ? replace(qualifier) : CollectionsKt.emptyList();
            }
            return CollectionsKt.listOf(expression);
        }
        if (expression instanceof JsUnaryOperation) {
            JsUnaryOperation jsUnaryOperation = (JsUnaryOperation) expression;
            JsUnaryOperator operator = jsUnaryOperation.getOperator();
            Intrinsics.checkNotNull(operator);
            switch (WhenMappings.$EnumSwitchMapping$0[operator.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    JsExpression arg = jsUnaryOperation.getArg();
                    Intrinsics.checkNotNullExpressionValue(arg, "getArg(...)");
                    return replace(arg);
                case 7:
                case 8:
                case 9:
                    return CollectionsKt.listOf(expression);
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
        if (expression instanceof JsBinaryOperation) {
            JsBinaryOperation jsBinaryOperation = (JsBinaryOperation) expression;
            switch (WhenMappings.$EnumSwitchMapping$1[jsBinaryOperation.getOperator().ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    JsExpression arg1 = jsBinaryOperation.getArg1();
                    Intrinsics.checkNotNullExpressionValue(arg1, "getArg1(...)");
                    List<JsExpression> listReplace = replace(arg1);
                    JsExpression arg2 = jsBinaryOperation.getArg2();
                    Intrinsics.checkNotNullExpressionValue(arg2, "getArg2(...)");
                    return CollectionsKt.plus((Collection) listReplace, (Iterable) replace(arg2));
                case 21:
                case 22:
                    JsExpression arg3 = jsBinaryOperation.getArg2();
                    Intrinsics.checkNotNullExpressionValue(arg3, "getArg2(...)");
                    if (!replace(arg3).isEmpty()) {
                        return CollectionsKt.listOf(expression);
                    }
                    JsExpression arg4 = jsBinaryOperation.getArg1();
                    Intrinsics.checkNotNullExpressionValue(arg4, "getArg1(...)");
                    return replace(arg4);
                case 23:
                case 24:
                    return CollectionsKt.listOf(expression);
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                    return CollectionsKt.listOf(expression);
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
        if (expression instanceof JsInvocation) {
            if (MetadataProperties.getSideEffects(expression) != SideEffectKind.AFFECTS_STATE) {
                JsInvocation jsInvocation = (JsInvocation) expression;
                JsExpression qualifier2 = jsInvocation.getQualifier();
                Intrinsics.checkNotNullExpressionValue(qualifier2, "getQualifier(...)");
                List<JsExpression> listReplace2 = replace(qualifier2);
                List<JsExpression> arguments = jsInvocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                return CollectionsKt.plus((Collection) listReplace2, (Iterable) replaceMany(arguments));
            }
            return CollectionsKt.listOf(expression);
        }
        if (expression instanceof JsNew) {
            if (MetadataProperties.getSideEffects(expression) != SideEffectKind.AFFECTS_STATE) {
                JsNew jsNew = (JsNew) expression;
                JsExpression constructorExpression = jsNew.getConstructorExpression();
                Intrinsics.checkNotNullExpressionValue(constructorExpression, "getConstructorExpression(...)");
                List<JsExpression> listReplace3 = replace(constructorExpression);
                List<JsExpression> arguments2 = jsNew.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
                return CollectionsKt.plus((Collection) listReplace3, (Iterable) replaceMany(arguments2));
            }
            return CollectionsKt.listOf(expression);
        }
        if (expression instanceof JsConditional) {
            JsConditional jsConditional = (JsConditional) expression;
            JsExpression thenExpression = jsConditional.getThenExpression();
            Intrinsics.checkNotNullExpressionValue(thenExpression, "getThenExpression(...)");
            List<JsExpression> listReplace4 = replace(thenExpression);
            JsExpression elseExpression = jsConditional.getElseExpression();
            Intrinsics.checkNotNullExpressionValue(elseExpression, "getElseExpression(...)");
            List<JsExpression> listReplace5 = replace(elseExpression);
            if (listReplace4.isEmpty() && listReplace5.isEmpty()) {
                JsExpression testExpression = jsConditional.getTestExpression();
                Intrinsics.checkNotNullExpressionValue(testExpression, "getTestExpression(...)");
                return replace(testExpression);
            }
            if (listReplace4.isEmpty()) {
                return CollectionsKt.listOf(JsAstUtils.or(jsConditional.getTestExpression(), jsConditional.getElseExpression()));
            }
            return listReplace5.isEmpty() ? CollectionsKt.listOf(JsAstUtils.and(jsConditional.getTestExpression(), jsConditional.getThenExpression())) : CollectionsKt.listOf(expression);
        }
        if (expression instanceof JsArrayAccess) {
            if (MetadataProperties.getSideEffects(expression) != SideEffectKind.AFFECTS_STATE) {
                JsArrayAccess jsArrayAccess = (JsArrayAccess) expression;
                JsExpression arrayExpression = jsArrayAccess.getArrayExpression();
                Intrinsics.checkNotNullExpressionValue(arrayExpression, "getArrayExpression(...)");
                List<JsExpression> listReplace6 = replace(arrayExpression);
                JsExpression indexExpression = jsArrayAccess.getIndexExpression();
                Intrinsics.checkNotNullExpressionValue(indexExpression, "getIndexExpression(...)");
                return CollectionsKt.plus((Collection) listReplace6, (Iterable) replace(indexExpression));
            }
            return CollectionsKt.listOf(expression);
        }
        if (expression instanceof JsLiteral.JsValueLiteral) {
            return CollectionsKt.emptyList();
        }
        if (expression instanceof JsArrayLiteral) {
            List<JsExpression> expressions = ((JsArrayLiteral) expression).getExpressions();
            Intrinsics.checkNotNullExpressionValue(expressions, "getExpressions(...)");
            return replaceMany(expressions);
        }
        if (!(expression instanceof JsObjectLiteral)) {
            if (expression instanceof JsFunction) {
                return ((JsFunction) expression).getName() == null ? CollectionsKt.emptyList() : CollectionsKt.listOf(expression);
            }
            return CollectionsKt.listOf(expression);
        }
        List<JsPropertyInitializer> propertyInitializers = ((JsObjectLiteral) expression).getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
        ArrayList arrayList = new ArrayList();
        for (JsPropertyInitializer jsPropertyInitializer : propertyInitializers) {
            JsExpression labelExpr = jsPropertyInitializer.getLabelExpr();
            Intrinsics.checkNotNullExpressionValue(labelExpr, "getLabelExpr(...)");
            List<JsExpression> listReplace7 = replace(labelExpr);
            JsExpression valueExpr = jsPropertyInitializer.getValueExpr();
            Intrinsics.checkNotNullExpressionValue(valueExpr, "getValueExpr(...)");
            CollectionsKt.addAll(arrayList, CollectionsKt.plus((Collection) listReplace7, (Iterable) replace(valueExpr)));
        }
        return arrayList;
    }

    private final List<JsExpression> replaceMany(List<? extends JsExpression> expressions) {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = expressions.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, replace((JsExpression) it.next()));
        }
        return arrayList;
    }
}
