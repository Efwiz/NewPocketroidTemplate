package org.jetbrains.kotlin.js.inline;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.backend.ast.HasArguments;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.inline.util.rewriters.ContinueReplacingVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: ExpressionDecomposer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u0000 72\u00020\u0001:\u000545678B#\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0002J\u001e\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u000bH\u0002J\u0016\u0010\u0012\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u000bH\u0002J$\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0016J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001f2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0016J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020 2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0016J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020!2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0016J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\"2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0016J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020#2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0016J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020$2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0016J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020%2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0016J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020&2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0016J\u001e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020'2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0016J\"\u0010(\u001a\u0002H)\"\u0004\b\u0000\u0010)2\f\u0010*\u001a\b\u0012\u0004\u0012\u0002H)0+H\u0082\b¢\u0006\u0002\u0010,J\f\u0010-\u001a\u00020\u0018*\u00020\u0018H\u0002J\f\u0010.\u001a\u00020\u0016*\u00020\u001cH\u0002J\f\u0010.\u001a\u00020\u0016*\u00020 H\u0002J\u001a\u0010.\u001a\u00020\u0016*\u00020!2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0002J\u001e\u0010.\u001a\u00020\u0016*\u00020'2\u0006\u0010/\u001a\u00020\u00102\b\u00100\u001a\u0004\u0018\u000101H\u0002J\f\u0010.\u001a\u00020\u0016*\u000202H\u0002J\u001a\u00103\u001a\u00020\u0016*\u00020 2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u001eH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b*\b\u0012\u0004\u0012\u00020\u00040\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ExpressionDecomposer;", "Lorg/jetbrains/kotlin/js/inline/JsExpressionVisitor;", "containsExtractable", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "containsNodeWithSideEffect", "(Ljava/util/Set;Ljava/util/Set;)V", "additionalStatements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "indicesOfExtractable", "", "", "getIndicesOfExtractable", "(Ljava/util/List;)Ljava/util/List;", "addStatement", "", "statement", "addStatements", "index", "statements", "processByIndices", "", PathTree.NAME_elements, "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "matchedIndices", "visit", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayAccess;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayLiteral;", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;", "Lorg/jetbrains/kotlin/js/backend/ast/JsConditional;", "Lorg/jetbrains/kotlin/js/backend/ast/JsDoWhile;", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "Lorg/jetbrains/kotlin/js/backend/ast/JsLabel;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNew;", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "Lorg/jetbrains/kotlin/js/backend/ast/JsWhile;", "withNewAdditionalStatements", ExifInterface.GPS_DIRECTION_TRUE, "fn", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "extractToTemporary", "process", "addBreakToBegin", "loopLabel", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/inline/ExpressionDecomposer$Callable;", "processOrAnd", "Callable", "CallableInvocationAdapter", "CallableNewAdapter", "Companion", "Temporary", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpressionDecomposer extends JsExpressionVisitor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private List<JsStatement> additionalStatements;
    private final Set<JsNode> containsExtractable;
    private final Set<JsNode> containsNodeWithSideEffect;

    /* JADX INFO: compiled from: ExpressionDecomposer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JsBinaryOperator.values().length];
            try {
                iArr[JsBinaryOperator.AND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JsBinaryOperator.OR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ ExpressionDecomposer(Set set, Set set2, DefaultConstructorMarker defaultConstructorMarker) {
        this(set, set2);
    }

    @JvmStatic
    public static final List<JsStatement> preserveEvaluationOrder(JsStatement jsStatement, Function1<? super JsNode, Boolean> function1) {
        return INSTANCE.preserveEvaluationOrder(jsStatement, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ExpressionDecomposer(Set<? extends JsNode> set, Set<? extends JsNode> set2) {
        this.containsExtractable = set;
        this.containsNodeWithSideEffect = set2;
        this.additionalStatements = new SmartList();
    }

    /* JADX INFO: compiled from: ExpressionDecomposer.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bH\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ExpressionDecomposer$Companion;", "", "()V", "preserveEvaluationOrder", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "statement", "canBeExtractedByInliner", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final List<JsStatement> preserveEvaluationOrder(JsStatement statement, Function1<? super JsNode, Boolean> canBeExtractedByInliner) {
            Intrinsics.checkNotNullParameter(statement, "statement");
            Intrinsics.checkNotNullParameter(canBeExtractedByInliner, "canBeExtractedByInliner");
            JsStatement jsStatement = statement;
            ExpressionDecomposer expressionDecomposer = new ExpressionDecomposer(ExpressionDecomposerKt.withParentsOfNodes(jsStatement, ExpressionDecomposerKt.match(jsStatement, canBeExtractedByInliner)), ExpressionDecomposerKt.withParentsOfNodes(jsStatement, ExpressionDecomposerKt.match(jsStatement, new Function1<JsNode, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.ExpressionDecomposer$Companion$preserveEvaluationOrder$decomposer$1$nodesWithSideEffect$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(JsNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(((it instanceof JsLiteral.JsValueLiteral) || ((it instanceof JsExpression) && MetadataProperties.getSideEffects((HasMetadata) it) == SideEffectKind.PURE)) ? false : true);
                }
            })), null);
            expressionDecomposer.accept(jsStatement);
            return expressionDecomposer.additionalStatements;
        }
    }

    @Override // org.jetbrains.kotlin.js.inline.JsExpressionVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsVars x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        List<JsVars.JsVar> vars = x.getVars();
        SmartList smartList = new SmartList();
        for (JsVars.JsVar jsVar : vars) {
            if (this.containsExtractable.contains(jsVar) && !smartList.isEmpty()) {
                SmartList smartList2 = smartList;
                JsVars jsVars = new JsVars(smartList2, x.isMultiline());
                jsVars.setSource(((JsVars.JsVar) CollectionsKt.first((List) smartList2)).getSource());
                addStatement(jsVars);
                smartList = new SmartList();
            }
            jsVar.setInitExpression((JsExpression) accept(jsVar.getInitExpression()));
            smartList.add(jsVar);
        }
        if (vars.size() == smartList.size()) {
            return false;
        }
        vars.clear();
        vars.addAll(smartList);
        x.setSource(((JsVars.JsVar) CollectionsKt.first((List) smartList)).getSource());
        return false;
    }

    @Override // org.jetbrains.kotlin.js.inline.JsExpressionVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsLabel x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsStatement statement = x.getStatement();
        if (statement instanceof JsDoWhile) {
            Intrinsics.checkNotNull(statement);
            process((JsWhile) statement, false, x.getName());
        } else if (statement instanceof JsWhile) {
            Intrinsics.checkNotNull(statement);
            process((JsWhile) statement, true, x.getName());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.js.inline.JsExpressionVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsWhile x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        process(x, true, null);
        return false;
    }

    @Override // org.jetbrains.kotlin.js.inline.JsExpressionVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsDoWhile x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        process(x, false, null);
        return false;
    }

    private final void process(JsWhile jsWhile, boolean z, JsName jsName) {
        if (this.containsExtractable.contains(AstUtilsKt.getTest(jsWhile))) {
            List<JsStatement> list = this.additionalStatements;
            this.additionalStatements = new SmartList();
            JsNode jsNodeAccept = accept(AstUtilsKt.getTest(jsWhile));
            Intrinsics.checkNotNullExpressionValue(jsNodeAccept, "accept(...)");
            AstUtilsKt.setTest(jsWhile, (JsExpression) jsNodeAccept);
            JsIf jsIf = new JsIf(JsAstUtils.not(AstUtilsKt.getTest(jsWhile)), new JsBreak());
            List<JsStatement> listFlattenStatement = JsAstUtils.flattenStatement(jsWhile.getBody());
            Intrinsics.checkNotNullExpressionValue(listFlattenStatement, "flattenStatement(...)");
            if (z) {
                addStatement(jsIf);
                addStatements(listFlattenStatement);
            } else {
                StringBuilder sb = new StringBuilder("guard$");
                String ident = jsName != null ? jsName.getIdent() : null;
                if (ident == null) {
                    ident = "";
                }
                JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(sb.append(ident).toString());
                Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
                JsLabel jsLabel = new JsLabel(jsNameDeclareTemporaryName);
                MetadataProperties.setSynthetic(jsLabel, true);
                jsLabel.setStatement(new JsBlock(listFlattenStatement));
                addStatements(0, CollectionsKt.listOf(jsLabel));
                new ContinueReplacingVisitor(jsName, jsNameDeclareTemporaryName).acceptList(listFlattenStatement);
                addStatement(jsIf);
            }
            jsWhile.setBody(ExpressionDecomposerKt.toStatement(this.additionalStatements));
            AstUtilsKt.setTest(jsWhile, new JsBooleanLiteral(true));
            Unit unit = Unit.INSTANCE;
            this.additionalStatements = list;
        }
    }

    @Override // org.jetbrains.kotlin.js.inline.JsExpressionVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsBinaryOperation x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        x.setArg1((JsExpression) accept(x.getArg1()));
        int i = WhenMappings.$EnumSwitchMapping$0[x.getOperator().ordinal()];
        if (i == 1 || i == 2) {
            processOrAnd(x, ctx);
            return false;
        }
        process(x);
        return false;
    }

    private final void processOrAnd(JsBinaryOperation jsBinaryOperation, JsContext<JsNode> jsContext) {
        JsPrefixOperation nameRef;
        if (this.containsExtractable.contains(jsBinaryOperation.getArg2())) {
            Temporary temporary = new Temporary(this, jsBinaryOperation.getArg1(), null, 2, null);
            addStatement(temporary.getVariable());
            if (jsBinaryOperation.getOperator() == JsBinaryOperator.OR) {
                JsPrefixOperation jsPrefixOperationNot = JsAstUtils.not(temporary.getNameRef());
                Intrinsics.checkNotNullExpressionValue(jsPrefixOperationNot, "not(...)");
                nameRef = jsPrefixOperationNot;
            } else {
                nameRef = temporary.getNameRef();
            }
            List<JsStatement> list = this.additionalStatements;
            this.additionalStatements = new SmartList();
            jsBinaryOperation.setArg2((JsExpression) accept(jsBinaryOperation.getArg2()));
            JsExpression arg2 = jsBinaryOperation.getArg2();
            Intrinsics.checkNotNullExpressionValue(arg2, "getArg2(...)");
            addStatement(temporary.assign(arg2));
            JsStatement statement = ExpressionDecomposerKt.toStatement(this.additionalStatements);
            this.additionalStatements = list;
            JsIf jsIf = new JsIf(nameRef, statement);
            jsIf.setSource(jsBinaryOperation.getSource());
            addStatement(jsIf);
            jsContext.replaceMe(temporary.getNameRef());
        }
    }

    private final void process(JsBinaryOperation jsBinaryOperation) {
        if (!this.containsNodeWithSideEffect.contains(jsBinaryOperation.getArg1()) || !this.containsExtractable.contains(jsBinaryOperation.getArg2())) {
            jsBinaryOperation.setArg2((JsExpression) accept(jsBinaryOperation.getArg2()));
            return;
        }
        if (jsBinaryOperation.getOperator().isAssignment()) {
            JsExpression arg1 = jsBinaryOperation.getArg1();
            if (arg1 instanceof JsNameRef) {
                JsNameRef jsNameRef = (JsNameRef) arg1;
                JsExpression qualifier = jsNameRef.getQualifier();
                jsNameRef.setQualifier(qualifier != null ? extractToTemporary(qualifier) : null);
            } else if (arg1 instanceof JsArrayAccess) {
                Intrinsics.checkNotNull(arg1);
                JsArrayAccess jsArrayAccess = (JsArrayAccess) arg1;
                AstUtilsKt.setArray(jsArrayAccess, extractToTemporary(AstUtilsKt.getArray(jsArrayAccess)));
            } else {
                throw new IllegalStateException(("Valid JavaScript left-hand side must be either JsNameRef or JsArrayAccess, got: " + jsBinaryOperation).toString());
            }
        } else {
            JsExpression arg2 = jsBinaryOperation.getArg1();
            Intrinsics.checkNotNullExpressionValue(arg2, "getArg1(...)");
            jsBinaryOperation.setArg1(extractToTemporary(arg2));
        }
        jsBinaryOperation.setArg2((JsExpression) accept(jsBinaryOperation.getArg2()));
    }

    @Override // org.jetbrains.kotlin.js.inline.JsExpressionVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsArrayLiteral x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        List<JsExpression> expressions = x.getExpressions();
        Intrinsics.checkNotNull(expressions);
        processByIndices(expressions, getIndicesOfExtractable(expressions));
        return false;
    }

    @Override // org.jetbrains.kotlin.js.inline.JsExpressionVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsArrayAccess x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        process(x);
        return false;
    }

    private final void process(JsArrayAccess jsArrayAccess) {
        JsNode jsNodeAccept = accept(AstUtilsKt.getArray(jsArrayAccess));
        Intrinsics.checkNotNullExpressionValue(jsNodeAccept, "accept(...)");
        AstUtilsKt.setArray(jsArrayAccess, (JsExpression) jsNodeAccept);
        if (this.containsNodeWithSideEffect.contains(AstUtilsKt.getArray(jsArrayAccess)) && this.containsExtractable.contains(AstUtilsKt.getIndex(jsArrayAccess))) {
            AstUtilsKt.setArray(jsArrayAccess, extractToTemporary(AstUtilsKt.getArray(jsArrayAccess)));
        }
        JsNode jsNodeAccept2 = accept(AstUtilsKt.getIndex(jsArrayAccess));
        Intrinsics.checkNotNullExpressionValue(jsNodeAccept2, "accept(...)");
        AstUtilsKt.setIndex(jsArrayAccess, (JsExpression) jsNodeAccept2);
    }

    @Override // org.jetbrains.kotlin.js.inline.JsExpressionVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsConditional x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        process(x, ctx);
        return false;
    }

    private final void process(JsConditional jsConditional, JsContext<JsNode> jsContext) {
        JsNode jsNodeAccept = accept(AstUtilsKt.getTest(jsConditional));
        Intrinsics.checkNotNullExpressionValue(jsNodeAccept, "accept(...)");
        AstUtilsKt.setTest(jsConditional, (JsExpression) jsNodeAccept);
        if (this.containsExtractable.contains(AstUtilsKt.getThen(jsConditional)) || this.containsExtractable.contains(AstUtilsKt.getOtherwise(jsConditional))) {
            Temporary temporary = new Temporary(this, null, jsConditional.getSource(), 1, null);
            addStatement(temporary.getVariable());
            List<JsStatement> list = this.additionalStatements;
            this.additionalStatements = new SmartList();
            JsNode jsNodeAccept2 = accept(AstUtilsKt.getThen(jsConditional));
            Intrinsics.checkNotNullExpressionValue(jsNodeAccept2, "accept(...)");
            AstUtilsKt.setThen(jsConditional, (JsExpression) jsNodeAccept2);
            addStatement(temporary.assign(AstUtilsKt.getThen(jsConditional)));
            JsStatement statement = ExpressionDecomposerKt.toStatement(this.additionalStatements);
            this.additionalStatements = list;
            this.additionalStatements = new SmartList();
            JsNode jsNodeAccept3 = accept(AstUtilsKt.getOtherwise(jsConditional));
            Intrinsics.checkNotNullExpressionValue(jsNodeAccept3, "accept(...)");
            AstUtilsKt.setOtherwise(jsConditional, (JsExpression) jsNodeAccept3);
            addStatement(temporary.assign(AstUtilsKt.getOtherwise(jsConditional)));
            JsStatement statement2 = ExpressionDecomposerKt.toStatement(this.additionalStatements);
            this.additionalStatements = list;
            JsIf jsIf = new JsIf(AstUtilsKt.getTest(jsConditional), statement, statement2);
            jsIf.setSource(jsConditional.getSource());
            MetadataProperties.setSynthetic(jsIf, true);
            addStatement(jsIf);
            jsContext.replaceMe(temporary.getNameRef());
        }
    }

    @Override // org.jetbrains.kotlin.js.inline.JsExpressionVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsInvocation x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        process(new CallableInvocationAdapter(x));
        return false;
    }

    @Override // org.jetbrains.kotlin.js.inline.JsExpressionVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    public boolean visit(JsNew x, JsContext<JsNode> ctx) {
        Intrinsics.checkNotNullParameter(x, "x");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        process(new CallableNewAdapter(x));
        return false;
    }

    /* JADX INFO: compiled from: ExpressionDecomposer.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR5\u0010\t\u001a&\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b \f*\u0012\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0018\u00010\r0\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0010\u001a\u00020\u000bX¦\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ExpressionDecomposer$Callable;", "", "hasArguments", "Lorg/jetbrains/kotlin/js/backend/ast/HasArguments;", "(Lorg/jetbrains/kotlin/js/backend/ast/HasArguments;)V", "applyBindIfNecessary", "", "getApplyBindIfNecessary", "()Z", "arguments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "getArguments", "()Ljava/util/List;", "qualifier", "getQualifier", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "setQualifier", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class Callable {
        private final List<JsExpression> arguments;

        public abstract boolean getApplyBindIfNecessary();

        public abstract JsExpression getQualifier();

        public abstract void setQualifier(JsExpression jsExpression);

        public Callable(HasArguments hasArguments) {
            Intrinsics.checkNotNullParameter(hasArguments, "hasArguments");
            this.arguments = hasArguments.getArguments();
        }

        public final List<JsExpression> getArguments() {
            return this.arguments;
        }
    }

    /* JADX INFO: compiled from: ExpressionDecomposer.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR$\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ExpressionDecomposer$CallableInvocationAdapter;", "Lorg/jetbrains/kotlin/js/inline/ExpressionDecomposer$Callable;", "invocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)V", "applyBindIfNecessary", "", "getApplyBindIfNecessary", "()Z", "getInvocation", "()Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "value", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "qualifier", "getQualifier", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "setQualifier", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CallableInvocationAdapter extends Callable {
        private final boolean applyBindIfNecessary;
        private final JsInvocation invocation;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CallableInvocationAdapter(JsInvocation invocation) {
            super(invocation);
            Intrinsics.checkNotNullParameter(invocation, "invocation");
            this.invocation = invocation;
            this.applyBindIfNecessary = true;
        }

        public final JsInvocation getInvocation() {
            return this.invocation;
        }

        @Override // org.jetbrains.kotlin.js.inline.ExpressionDecomposer.Callable
        public JsExpression getQualifier() {
            JsExpression qualifier = this.invocation.getQualifier();
            Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
            return qualifier;
        }

        @Override // org.jetbrains.kotlin.js.inline.ExpressionDecomposer.Callable
        public void setQualifier(JsExpression value) {
            Intrinsics.checkNotNullParameter(value, "value");
            this.invocation.setQualifier(value);
        }

        @Override // org.jetbrains.kotlin.js.inline.ExpressionDecomposer.Callable
        public boolean getApplyBindIfNecessary() {
            return this.applyBindIfNecessary;
        }
    }

    /* JADX INFO: compiled from: ExpressionDecomposer.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR$\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ExpressionDecomposer$CallableNewAdapter;", "Lorg/jetbrains/kotlin/js/inline/ExpressionDecomposer$Callable;", "jsnew", "Lorg/jetbrains/kotlin/js/backend/ast/JsNew;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNew;)V", "applyBindIfNecessary", "", "getApplyBindIfNecessary", "()Z", "getJsnew", "()Lorg/jetbrains/kotlin/js/backend/ast/JsNew;", "value", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "qualifier", "getQualifier", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "setQualifier", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CallableNewAdapter extends Callable {
        private final boolean applyBindIfNecessary;
        private final JsNew jsnew;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CallableNewAdapter(JsNew jsnew) {
            super(jsnew);
            Intrinsics.checkNotNullParameter(jsnew, "jsnew");
            this.jsnew = jsnew;
        }

        public final JsNew getJsnew() {
            return this.jsnew;
        }

        @Override // org.jetbrains.kotlin.js.inline.ExpressionDecomposer.Callable
        public JsExpression getQualifier() {
            JsExpression constructorExpression = this.jsnew.getConstructorExpression();
            Intrinsics.checkNotNullExpressionValue(constructorExpression, "getConstructorExpression(...)");
            return constructorExpression;
        }

        @Override // org.jetbrains.kotlin.js.inline.ExpressionDecomposer.Callable
        public void setQualifier(JsExpression value) {
            Intrinsics.checkNotNullParameter(value, "value");
            this.jsnew.setConstructorExpression(value);
        }

        @Override // org.jetbrains.kotlin.js.inline.ExpressionDecomposer.Callable
        public boolean getApplyBindIfNecessary() {
            return this.applyBindIfNecessary;
        }
    }

    private final void process(Callable callable) {
        JsNode jsNodeAccept = accept(callable.getQualifier());
        Intrinsics.checkNotNullExpressionValue(jsNodeAccept, "accept(...)");
        callable.setQualifier((JsExpression) jsNodeAccept);
        List<JsExpression> arguments = callable.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "<get-arguments>(...)");
        ArrayList indicesOfExtractable = getIndicesOfExtractable(arguments);
        if (indicesOfExtractable.isEmpty()) {
            return;
        }
        if (this.containsNodeWithSideEffect.contains(callable.getQualifier())) {
            JsExpression qualifier = callable.getQualifier();
            JsNameRef jsNameRef = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
            JsExpression qualifier2 = jsNameRef != null ? jsNameRef.getQualifier() : null;
            if (MetadataProperties.getSideEffects(callable.getQualifier()) == SideEffectKind.PURE && jsNameRef != null && qualifier2 != null && this.containsNodeWithSideEffect.contains(qualifier2)) {
                jsNameRef.setQualifier(extractToTemporary(qualifier2));
            } else if (qualifier2 != null && callable.getApplyBindIfNecessary()) {
                JsExpression jsExpressionExtractToTemporary = extractToTemporary(qualifier2);
                JsNameRef jsNameRef2 = new JsNameRef(jsNameRef.getIdent(), jsExpressionExtractToTemporary);
                JsNameRef jsNameRef3 = jsNameRef2;
                MetadataProperties.setSynthetic(jsNameRef3, true);
                JsName name2 = jsNameRef.getName();
                if (name2 != null) {
                    Intrinsics.checkNotNull(name2);
                    MetadataProperties.setSideEffects(jsNameRef3, MetadataProperties.getSideEffects(name2));
                }
                callable.setQualifier(extractToTemporary(jsNameRef2));
                JsNameRef functionCallRef = Namer.getFunctionCallRef(callable.getQualifier());
                Intrinsics.checkNotNullExpressionValue(functionCallRef, "getFunctionCallRef(...)");
                callable.setQualifier(functionCallRef);
                callable.getArguments().add(0, jsExpressionExtractToTemporary);
                List<Integer> list = indicesOfExtractable;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(((Number) it.next()).intValue() + 1));
                }
                indicesOfExtractable = arrayList;
            } else {
                callable.setQualifier(extractToTemporary(callable.getQualifier()));
            }
        }
        List<JsExpression> arguments2 = callable.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments2, "<get-arguments>(...)");
        processByIndices(arguments2, indicesOfExtractable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void processByIndices(List<JsExpression> elements, List<Integer> matchedIndices) {
        Iterator<Integer> it = matchedIndices.iterator();
        int i = 0;
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            int i2 = iIntValue - 1;
            if (i <= i2) {
                while (true) {
                    JsExpression jsExpression = (JsExpression) elements.get(i);
                    if (this.containsNodeWithSideEffect.contains(jsExpression)) {
                        elements.set(i, extractToTemporary(jsExpression));
                    }
                    if (i != i2) {
                        i++;
                    }
                }
            }
            JsNode jsNodeAccept = accept((JsNode) elements.get(iIntValue));
            Intrinsics.checkNotNullExpressionValue(jsNodeAccept, "accept(...)");
            elements.set(iIntValue, jsNodeAccept);
            i = iIntValue;
        }
    }

    private final boolean addStatement(JsStatement statement) {
        return this.additionalStatements.add(statement);
    }

    private final boolean addStatements(List<? extends JsStatement> statements) {
        return this.additionalStatements.addAll(statements);
    }

    private final boolean addStatements(int index, List<? extends JsStatement> statements) {
        return this.additionalStatements.addAll(index, statements);
    }

    private final JsExpression extractToTemporary(JsExpression jsExpression) {
        Temporary temporary = new Temporary(this, jsExpression, null, 2, null);
        addStatement(temporary.getVariable());
        return temporary.getNameRef();
    }

    /* JADX INFO: compiled from: ExpressionDecomposer.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u0003R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/ExpressionDecomposer$Temporary;", "", "value", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "sourceInfo", "(Lorg/jetbrains/kotlin/js/inline/ExpressionDecomposer;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Ljava/lang/Object;)V", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "nameRef", "getNameRef", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getSourceInfo", "()Ljava/lang/Object;", "getValue", "variable", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "getVariable", "()Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "assign", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Temporary {
        private final JsName name;
        private final Object sourceInfo;
        private final JsExpression value;
        private final JsVars variable;

        public Temporary(JsExpression jsExpression, Object obj) {
            this.value = jsExpression;
            this.sourceInfo = obj;
            JsName jsNameDeclareTemporary = JsScope.declareTemporary();
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporary, "declareTemporary(...)");
            this.name = jsNameDeclareTemporary;
            JsVars jsVarsNewVar = JsAstUtils.newVar(jsNameDeclareTemporary, jsExpression);
            Intrinsics.checkNotNullExpressionValue(jsVarsNewVar, "newVar(...)");
            MetadataProperties.setSynthetic(jsVarsNewVar, true);
            MetadataProperties.setStaticRef(jsNameDeclareTemporary, jsExpression);
            jsVarsNewVar.setSource(obj == null ? jsExpression != null ? jsExpression.getSource() : null : obj);
            this.variable = jsVarsNewVar;
        }

        public /* synthetic */ Temporary(ExpressionDecomposer expressionDecomposer, JsExpression jsExpression, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : jsExpression, (i & 2) != 0 ? null : obj);
        }

        public final Object getSourceInfo() {
            return this.sourceInfo;
        }

        public final JsExpression getValue() {
            return this.value;
        }

        public final JsName getName() {
            return this.name;
        }

        public final JsVars getVariable() {
            return this.variable;
        }

        public final JsExpression getNameRef() {
            JsNameRef jsNameRefMakeRef = this.name.makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
            return jsNameRefMakeRef;
        }

        public final JsStatement assign(JsExpression value) {
            Intrinsics.checkNotNullParameter(value, "value");
            JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(JsAstUtils.assignment(getNameRef(), value));
            MetadataProperties.setSynthetic(jsExpressionStatement, true);
            JsExpression expression = jsExpressionStatement.getExpression();
            Object source = this.sourceInfo;
            if (source == null) {
                source = value.getSource();
            }
            expression.setSource(source);
            return jsExpressionStatement;
        }
    }

    private final List<Integer> getIndicesOfExtractable(List<? extends JsNode> list) {
        IntRange indices = CollectionsKt.getIndices(list);
        ArrayList arrayList = new ArrayList();
        for (Integer num : indices) {
            if (this.containsExtractable.contains(list.get(num.intValue()))) {
                arrayList.add(num);
            }
        }
        return arrayList;
    }
}
