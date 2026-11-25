package org.jetbrains.kotlin.js.inline.clean;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCase;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDefault;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsForIn;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsLoop;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.inline.util.CollectUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;

/* JADX INFO: compiled from: TemporaryVariableElimination.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\b\u0000\u0018\u00002\u00020\u0001:\u0001&B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\u0006\u0010\u001b\u001a\u00020\u000eJ\u0018\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\nH\u0002J\b\u0010\u001f\u001a\u00020\u001aH\u0002J\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\nH\u0002J\b\u0010\"\u001a\u00020\u001aH\u0002J\u0010\u0010#\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u0010$\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u0010%\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\f0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\f0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/TemporaryVariableElimination;", "", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)V", "capturedInClosure", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "definedValues", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "definitions", "", "hasChanges", "", "localVariables", "", "namesToSubstitute", "namesWithSideEffects", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "statementsToRemove", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "temporary", "usages", "analyze", "", "apply", "assignVariable", "name", "value", "cleanUp", "isTrivial", "expr", "perform", "shouldConsiderTemporary", "shouldConsiderUnused", "useVariable", "SubstitutionCandidateFinder", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TemporaryVariableElimination {
    private final Set<JsName> capturedInClosure;
    private final Map<JsName, JsExpression> definedValues;
    private final Map<JsName, Integer> definitions;
    private final JsFunction function;
    private boolean hasChanges;
    private final Set<JsName> localVariables;
    private final Set<JsName> namesToSubstitute;
    private final Set<JsName> namesWithSideEffects;
    private final JsBlock root;
    private final Set<JsNode> statementsToRemove;
    private final Set<JsName> temporary;
    private final Map<JsName, Integer> usages;

    public TemporaryVariableElimination(JsFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.function = function;
        JsBlock body = function.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        this.root = body;
        this.definitions = new LinkedHashMap();
        this.usages = new LinkedHashMap();
        this.definedValues = new LinkedHashMap();
        this.temporary = new LinkedHashSet();
        this.capturedInClosure = new LinkedHashSet();
        this.localVariables = CollectUtilsKt.collectLocalVariables$default(function, false, 1, null);
        this.namesToSubstitute = new LinkedHashSet();
        this.statementsToRemove = new LinkedHashSet();
        this.namesWithSideEffects = new LinkedHashSet();
    }

    public final boolean apply() {
        analyze();
        perform();
        cleanUp();
        return this.hasChanges;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.js.inline.clean.TemporaryVariableElimination$analyze$1, reason: invalid class name */
    /* JADX INFO: compiled from: TemporaryVariableElimination.kt */
    @Metadata(d1 = {"\u0000\u007f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\u0002J&\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0010H\u0016J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\f2\u0006\u0010*\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020/H\u0016J\"\u00100\u001a\u0002H1\"\u0004\b\u0000\u001012\f\u00102\u001a\b\u0012\u0004\u0012\u0002H10\u0014H\u0082\b¢\u0006\u0002\u00103R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0006\"\u0004\b\t\u0010\n¨\u00064"}, d2 = {"org/jetbrains/kotlin/js/inline/clean/TemporaryVariableElimination$analyze$1", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "currentScope", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getCurrentScope", "()Ljava/util/Set;", "localVars", "getLocalVars", "setLocalVars", "(Ljava/util/Set;)V", "addVar", "", "name", "processBinaryExpression", "expression", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;", "synthetic", "", "orElse", "Lkotlin/Function0;", "visitBinaryExpression", "x", "visitBreak", "Lorg/jetbrains/kotlin/js/backend/ast/JsBreak;", "visitCase", "Lorg/jetbrains/kotlin/js/backend/ast/JsCase;", "visitCatch", "Lorg/jetbrains/kotlin/js/backend/ast/JsCatch;", "visitContinue", "Lorg/jetbrains/kotlin/js/backend/ast/JsContinue;", "visitDefault", "Lorg/jetbrains/kotlin/js/backend/ast/JsDefault;", "visitExpressionStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "visitFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "visitIf", "Lorg/jetbrains/kotlin/js/backend/ast/JsIf;", "visitLoop", "Lorg/jetbrains/kotlin/js/backend/ast/JsLoop;", "visitNameRef", "nameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "visitObjectLiteral", "Lorg/jetbrains/kotlin/js/backend/ast/JsObjectLiteral;", "visitVars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "withNewScope", ExifInterface.GPS_DIRECTION_TRUE, "block", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 extends RecursiveJsVisitor {
        private final Set<JsName> currentScope;
        private Set<JsName> localVars;

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitBreak(JsBreak x) {
            Intrinsics.checkNotNullParameter(x, "x");
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitContinue(JsContinue x) {
            Intrinsics.checkNotNullParameter(x, "x");
        }

        AnonymousClass1() {
            List<JsParameter> parameters = TemporaryVariableElimination.this.function.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            this.currentScope = SequencesKt.toMutableSet(SequencesKt.map(CollectionsKt.asSequence(parameters), new Function1<JsParameter, JsName>() { // from class: org.jetbrains.kotlin.js.inline.clean.TemporaryVariableElimination$analyze$1$currentScope$1
                @Override // kotlin.jvm.functions.Function1
                public final JsName invoke(JsParameter jsParameter) {
                    return jsParameter.getName();
                }
            }));
            this.localVars = new LinkedHashSet();
        }

        public final Set<JsName> getCurrentScope() {
            return this.currentScope;
        }

        public final Set<JsName> getLocalVars() {
            return this.localVars;
        }

        public final void setLocalVars(Set<JsName> set) {
            Intrinsics.checkNotNullParameter(set, "<set-?>");
            this.localVars = set;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitExpressionStatement(final JsExpressionStatement x) {
            Intrinsics.checkNotNullParameter(x, "x");
            JsExpression expression = x.getExpression();
            JsBinaryOperation jsBinaryOperation = expression instanceof JsBinaryOperation ? (JsBinaryOperation) expression : null;
            if (jsBinaryOperation != null) {
                processBinaryExpression(jsBinaryOperation, MetadataProperties.getSynthetic(x), new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.inline.clean.TemporaryVariableElimination$analyze$1$visitExpressionStatement$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        super/*org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor*/.visitExpressionStatement(x);
                    }
                });
            } else {
                super.visitExpressionStatement(x);
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitBinaryExpression(final JsBinaryOperation x) {
            Intrinsics.checkNotNullParameter(x, "x");
            processBinaryExpression(x, false, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.inline.clean.TemporaryVariableElimination$analyze$1$visitBinaryExpression$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    super/*org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor*/.visitBinaryExpression(x);
                }
            });
        }

        private final void processBinaryExpression(JsBinaryOperation expression, boolean synthetic, Function0<Unit> orElse) {
            Pair<JsName, JsExpression> pairDecomposeAssignmentToVariable = JsAstUtils.decomposeAssignmentToVariable(expression);
            if (pairDecomposeAssignmentToVariable != null) {
                JsName jsNameComponent1 = pairDecomposeAssignmentToVariable.component1();
                JsExpression jsExpressionComponent2 = pairDecomposeAssignmentToVariable.component2();
                if (TemporaryVariableElimination.this.localVariables.contains(jsNameComponent1)) {
                    TemporaryVariableElimination temporaryVariableElimination = TemporaryVariableElimination.this;
                    Intrinsics.checkNotNull(jsNameComponent1);
                    Intrinsics.checkNotNull(jsExpressionComponent2);
                    temporaryVariableElimination.assignVariable(jsNameComponent1, jsExpressionComponent2);
                    addVar(jsNameComponent1);
                    accept(jsExpressionComponent2);
                    if (synthetic) {
                        TemporaryVariableElimination.this.temporary.add(jsNameComponent1);
                        return;
                    }
                    return;
                }
            }
            orElse.invoke();
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitVars(JsVars x) {
            Intrinsics.checkNotNullParameter(x, "x");
            for (JsVars.JsVar jsVar : x.getVars()) {
                JsName name2 = jsVar.getName();
                JsExpression initExpression = jsVar.getInitExpression();
                if (TemporaryVariableElimination.this.localVariables.contains(name2)) {
                    if (MetadataProperties.getSynthetic(x)) {
                        TemporaryVariableElimination.this.temporary.add(name2);
                    }
                    if (initExpression != null) {
                        TemporaryVariableElimination temporaryVariableElimination = TemporaryVariableElimination.this;
                        Intrinsics.checkNotNull(name2);
                        temporaryVariableElimination.assignVariable(name2, initExpression);
                        addVar(name2);
                        accept(initExpression);
                    }
                }
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitNameRef(JsNameRef nameRef) {
            Intrinsics.checkNotNullParameter(nameRef, "nameRef");
            JsName name2 = nameRef.getName();
            if (name2 != null && TemporaryVariableElimination.this.localVariables.contains(name2)) {
                TemporaryVariableElimination.this.useVariable(name2);
                if (this.currentScope.contains(name2)) {
                    return;
                }
                TemporaryVariableElimination temporaryVariableElimination = TemporaryVariableElimination.this;
                JsExpression undefinedExpression = Namer.getUndefinedExpression();
                Intrinsics.checkNotNullExpressionValue(undefinedExpression, "getUndefinedExpression(...)");
                temporaryVariableElimination.assignVariable(name2, undefinedExpression);
                return;
            }
            super.visitNameRef(nameRef);
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitObjectLiteral(JsObjectLiteral x) {
            Intrinsics.checkNotNullParameter(x, "x");
            Iterator<JsPropertyInitializer> it = x.getPropertyInitializers().iterator();
            while (it.hasNext()) {
                accept(it.next().getValueExpr());
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitIf(JsIf x) {
            Intrinsics.checkNotNullParameter(x, "x");
            accept(x.getIfExpression());
            Set<JsName> set = this.localVars;
            try {
                this.localVars = new LinkedHashSet();
                accept(x.getThenStatement());
                Unit unit = Unit.INSTANCE;
                CollectionsKt.removeAll((Collection) this.currentScope, (Iterable) this.localVars);
                this.localVars = set;
                JsStatement elseStatement = x.getElseStatement();
                if (elseStatement != null) {
                    Set<JsName> set2 = this.localVars;
                    try {
                        this.localVars = new LinkedHashSet();
                        accept(elseStatement);
                        Unit unit2 = Unit.INSTANCE;
                    } finally {
                        CollectionsKt.removeAll((Collection) this.currentScope, (Iterable) this.localVars);
                        this.localVars = set2;
                    }
                }
            } catch (Throwable th) {
                CollectionsKt.removeAll((Collection) this.currentScope, (Iterable) this.localVars);
                this.localVars = set;
                throw th;
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitFunction(JsFunction x) {
            Intrinsics.checkNotNullParameter(x, "x");
            for (JsName jsName : CollectUtilsKt.collectFreeVariables(x)) {
                TemporaryVariableElimination.this.useVariable(jsName);
                TemporaryVariableElimination.this.useVariable(jsName);
                TemporaryVariableElimination.this.capturedInClosure.add(jsName);
            }
        }

        private final void addVar(JsName name2) {
            this.currentScope.add(name2);
            this.localVars.add(name2);
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitLoop(JsLoop x) {
            Intrinsics.checkNotNullParameter(x, "x");
            Set<JsName> set = this.localVars;
            try {
                this.localVars = new LinkedHashSet();
                super.visitLoop(x);
                Unit unit = Unit.INSTANCE;
            } finally {
                CollectionsKt.removeAll((Collection) this.currentScope, (Iterable) this.localVars);
                this.localVars = set;
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitCase(JsCase x) {
            Intrinsics.checkNotNullParameter(x, "x");
            Set<JsName> set = this.localVars;
            try {
                this.localVars = new LinkedHashSet();
                super.visitCase(x);
                Unit unit = Unit.INSTANCE;
            } finally {
                CollectionsKt.removeAll((Collection) this.currentScope, (Iterable) this.localVars);
                this.localVars = set;
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitDefault(JsDefault x) {
            Intrinsics.checkNotNullParameter(x, "x");
            Set<JsName> set = this.localVars;
            try {
                this.localVars = new LinkedHashSet();
                super.visitDefault(x);
                Unit unit = Unit.INSTANCE;
            } finally {
                CollectionsKt.removeAll((Collection) this.currentScope, (Iterable) this.localVars);
                this.localVars = set;
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitCatch(JsCatch x) {
            Intrinsics.checkNotNullParameter(x, "x");
            Set<JsName> set = this.localVars;
            try {
                this.localVars = new LinkedHashSet();
                super.visitCatch(x);
                Unit unit = Unit.INSTANCE;
            } finally {
                CollectionsKt.removeAll((Collection) this.currentScope, (Iterable) this.localVars);
                this.localVars = set;
            }
        }
    }

    private final void analyze() {
        new AnonymousClass1().accept(this.root);
    }

    private final void perform() {
        new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.TemporaryVariableElimination.perform.1
            private final List<Pair<JsName, JsNode>> lastAssignedVars = new ArrayList();

            public final List<Pair<JsName, JsNode>> getLastAssignedVars() {
                return this.lastAssignedVars;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitExpressionStatement(JsExpressionStatement x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                Pair<JsName, JsExpression> pairDecomposeAssignmentToVariable = JsAstUtils.decomposeAssignmentToVariable(expression);
                if (pairDecomposeAssignmentToVariable != null) {
                    JsName jsNameComponent1 = pairDecomposeAssignmentToVariable.component1();
                    JsExpression jsExpressionComponent2 = pairDecomposeAssignmentToVariable.component2();
                    Intrinsics.checkNotNull(jsNameComponent1);
                    Intrinsics.checkNotNull(jsExpressionComponent2);
                    handleDefinition(jsNameComponent1, jsExpressionComponent2, x);
                    return;
                }
                if (handleExpression(expression)) {
                    invalidateTemporaries();
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitVars(JsVars x) {
                Intrinsics.checkNotNullParameter(x, "x");
                for (JsVars.JsVar jsVar : x.getVars()) {
                    JsExpression initExpression = jsVar.getInitExpression();
                    if (initExpression != null) {
                        JsName name2 = jsVar.getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        Intrinsics.checkNotNull(jsVar);
                        handleDefinition(name2, initExpression, jsVar);
                    }
                }
            }

            private final void handleDefinition(JsName name2, JsExpression value, JsNode node) {
                boolean z = handleExpression(value) || !TemporaryVariableElimination.this.localVariables.contains(name2);
                if (!TemporaryVariableElimination.this.shouldConsiderTemporary(name2)) {
                    if (z) {
                        invalidateTemporaries();
                        return;
                    } else {
                        invalidateTemporariesUsingName(name2);
                        return;
                    }
                }
                if (TemporaryVariableElimination.this.isTrivial(value)) {
                    TemporaryVariableElimination.this.statementsToRemove.add(node);
                    TemporaryVariableElimination.this.namesToSubstitute.add(name2);
                } else {
                    this.lastAssignedVars.add(new Pair<>(name2, node));
                    if (z) {
                        TemporaryVariableElimination.this.namesWithSideEffects.add(name2);
                    }
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitIf(JsIf x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression ifExpression = x.getIfExpression();
                Intrinsics.checkNotNullExpressionValue(ifExpression, "getIfExpression(...)");
                handleExpression(ifExpression);
                invalidateTemporaries();
                accept(x.getThenStatement());
                invalidateTemporaries();
                JsStatement elseStatement = x.getElseStatement();
                if (elseStatement != null) {
                    accept(elseStatement);
                    invalidateTemporaries();
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitReturn(JsReturn x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression expression = x.getExpression();
                if (expression != null) {
                    handleExpression(expression);
                }
                invalidateTemporaries();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitThrow(JsThrow x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                handleExpression(expression);
                invalidateTemporaries();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsSwitch x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                handleExpression(expression);
                invalidateTemporaries();
                List<JsSwitchMember> cases = x.getCases();
                Intrinsics.checkNotNullExpressionValue(cases, "getCases(...)");
                Iterator<T> it = cases.iterator();
                while (it.hasNext()) {
                    accept((JsSwitchMember) it.next());
                    invalidateTemporaries();
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitObjectLiteral(JsObjectLiteral x) {
                Intrinsics.checkNotNullParameter(x, "x");
                Iterator<JsPropertyInitializer> it = x.getPropertyInitializers().iterator();
                while (it.hasNext()) {
                    accept(it.next().getValueExpr());
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitWhile(JsWhile x) {
                Intrinsics.checkNotNullParameter(x, "x");
                invalidateTemporaries();
                super.visitWhile(x);
                invalidateTemporaries();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDoWhile(JsDoWhile x) {
                Intrinsics.checkNotNullParameter(x, "x");
                invalidateTemporaries();
                super.visitDoWhile(x);
                invalidateTemporaries();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitForIn(JsForIn x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsExpression objectExpression = x.getObjectExpression();
                Intrinsics.checkNotNullExpressionValue(objectExpression, "getObjectExpression(...)");
                handleExpression(objectExpression);
                invalidateTemporaries();
                accept(x.getBody());
                invalidateTemporaries();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFor(JsFor x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsVars initVars = x.getInitVars();
                if (initVars != null) {
                    accept(initVars);
                }
                JsExpression initExpression = x.getInitExpression();
                if (initExpression != null) {
                    handleExpression(initExpression);
                }
                invalidateTemporaries();
                JsExpression condition = x.getCondition();
                if (condition != null) {
                    accept(condition);
                }
                JsExpression incrementExpression = x.getIncrementExpression();
                if (incrementExpression != null) {
                    accept(incrementExpression);
                }
                invalidateTemporaries();
                accept(x.getBody());
                invalidateTemporaries();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitTry(JsTry x) {
                Intrinsics.checkNotNullParameter(x, "x");
                invalidateTemporaries();
                super.visitTry(x);
                invalidateTemporaries();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitCatch(JsCatch x) {
                Intrinsics.checkNotNullParameter(x, "x");
                invalidateTemporaries();
                super.visitCatch(x);
                invalidateTemporaries();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitLabel(JsLabel x) {
                Intrinsics.checkNotNullParameter(x, "x");
                invalidateTemporaries();
                super.visitLabel(x);
                invalidateTemporaries();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBreak(JsBreak x) {
                Intrinsics.checkNotNullParameter(x, "x");
                invalidateTemporaries();
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitContinue(JsContinue x) {
                Intrinsics.checkNotNullParameter(x, "x");
                invalidateTemporaries();
            }

            private final void invalidateTemporaries() {
                this.lastAssignedVars.clear();
            }

            private final void invalidateTemporariesUsingName(final JsName name2) {
                CollectionsKt.removeAll((List) this.lastAssignedVars, (Function1) new Function1<Pair<? extends JsName, ? extends JsNode>, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.TemporaryVariableElimination$perform$1$invalidateTemporariesUsingName$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(Pair<? extends JsName, ? extends JsNode> pair) {
                        Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                        JsNode jsNodeComponent2 = pair.component2();
                        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                        final JsName jsName = name2;
                        new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.TemporaryVariableElimination$perform$1$invalidateTemporariesUsingName$1.1
                            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
                            public void visitNameRef(JsNameRef nameRef) {
                                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                                if (Intrinsics.areEqual(nameRef.getName(), jsName)) {
                                    booleanRef.element = true;
                                }
                                super.visitNameRef(nameRef);
                            }
                        }.accept(jsNodeComponent2);
                        return Boolean.valueOf(booleanRef.element);
                    }
                });
            }

            private final boolean handleExpression(JsExpression expression) {
                SubstitutionCandidateFinder substitutionCandidateFinder = TemporaryVariableElimination.this.new SubstitutionCandidateFinder();
                substitutionCandidateFinder.accept(expression);
                List<JsName> substitutableVariableReferences = substitutionCandidateFinder.getSubstitutableVariableReferences();
                while (!this.lastAssignedVars.isEmpty()) {
                    Pair pair = (Pair) CollectionsKt.last((List) this.lastAssignedVars);
                    JsName jsName = (JsName) pair.component1();
                    JsNode jsNode = (JsNode) pair.component2();
                    int iLastIndexOf = substitutableVariableReferences.lastIndexOf(jsName);
                    if (iLastIndexOf < 0) {
                        break;
                    }
                    TemporaryVariableElimination.this.namesToSubstitute.add(jsName);
                    TemporaryVariableElimination.this.statementsToRemove.add(jsNode);
                    if (TemporaryVariableElimination.this.namesWithSideEffects.contains(jsName)) {
                        substitutionCandidateFinder.setSideEffectOccurred(true);
                    }
                    substitutableVariableReferences = substitutableVariableReferences.subList(0, iLastIndexOf);
                    List<Pair<JsName, JsNode>> list = this.lastAssignedVars;
                    list.remove(CollectionsKt.getLastIndex(list));
                }
                return substitutionCandidateFinder.getSideEffectOccurred();
            }
        }.accept(this.root);
    }

    /* JADX INFO: compiled from: TemporaryVariableElimination.kt */
    @Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020'H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/TemporaryVariableElimination$SubstitutionCandidateFinder;", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "(Lorg/jetbrains/kotlin/js/inline/clean/TemporaryVariableElimination;)V", "sideEffectOccurred", "", "getSideEffectOccurred", "()Z", "setSideEffectOccurred", "(Z)V", "substitutableVariableReferences", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getSubstitutableVariableReferences", "()Ljava/util/List;", "visitArray", "", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayLiteral;", "visitArrayAccess", "Lorg/jetbrains/kotlin/js/backend/ast/JsArrayAccess;", "visitBinaryExpression", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperation;", "visitConditional", "Lorg/jetbrains/kotlin/js/backend/ast/JsConditional;", "visitFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "visitInvocation", "invocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "visitNameRef", "nameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "visitNew", "Lorg/jetbrains/kotlin/js/backend/ast/JsNew;", "visitObjectLiteral", "Lorg/jetbrains/kotlin/js/backend/ast/JsObjectLiteral;", "visitPostfixOperation", "Lorg/jetbrains/kotlin/js/backend/ast/JsPostfixOperation;", "visitPrefixOperation", "Lorg/jetbrains/kotlin/js/backend/ast/JsPrefixOperation;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class SubstitutionCandidateFinder extends RecursiveJsVisitor {
        private boolean sideEffectOccurred;
        private final List<JsName> substitutableVariableReferences = new ArrayList();

        /* JADX INFO: compiled from: TemporaryVariableElimination.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[JsUnaryOperator.values().length];
                try {
                    iArr[JsUnaryOperator.INC.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[JsUnaryOperator.DEC.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public SubstitutionCandidateFinder() {
        }

        public final List<JsName> getSubstitutableVariableReferences() {
            return this.substitutableVariableReferences;
        }

        public final boolean getSideEffectOccurred() {
            return this.sideEffectOccurred;
        }

        public final void setSideEffectOccurred(boolean z) {
            this.sideEffectOccurred = z;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitFunction(JsFunction x) {
            Intrinsics.checkNotNullParameter(x, "x");
            this.sideEffectOccurred = true;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitObjectLiteral(JsObjectLiteral x) {
            Intrinsics.checkNotNullParameter(x, "x");
            Iterator<JsPropertyInitializer> it = x.getPropertyInitializers().iterator();
            while (it.hasNext()) {
                accept(it.next().getValueExpr());
            }
            this.sideEffectOccurred = true;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitNew(JsNew x) {
            Intrinsics.checkNotNullParameter(x, "x");
            super.visitNew(x);
            if (MetadataProperties.getSideEffects(x) == SideEffectKind.AFFECTS_STATE) {
                this.sideEffectOccurred = true;
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitInvocation(JsInvocation invocation) {
            Intrinsics.checkNotNullParameter(invocation, "invocation");
            super.visitInvocation(invocation);
            if (MetadataProperties.getSideEffects(invocation) == SideEffectKind.AFFECTS_STATE) {
                this.sideEffectOccurred = true;
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitConditional(JsConditional x) {
            Intrinsics.checkNotNullParameter(x, "x");
            accept(x.getTestExpression());
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitArrayAccess(JsArrayAccess x) {
            Intrinsics.checkNotNullParameter(x, "x");
            super.visitArrayAccess(x);
            if (MetadataProperties.getSideEffects(x) == SideEffectKind.AFFECTS_STATE) {
                this.sideEffectOccurred = true;
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitArray(JsArrayLiteral x) {
            Intrinsics.checkNotNullParameter(x, "x");
            super.visitArray(x);
            this.sideEffectOccurred = true;
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitPrefixOperation(JsPrefixOperation x) {
            Intrinsics.checkNotNullParameter(x, "x");
            super.visitPrefixOperation(x);
            JsUnaryOperator operator = x.getOperator();
            int i = operator == null ? -1 : WhenMappings.$EnumSwitchMapping$0[operator.ordinal()];
            if (i == 1 || i == 2) {
                this.sideEffectOccurred = true;
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitPostfixOperation(JsPostfixOperation x) {
            Intrinsics.checkNotNullParameter(x, "x");
            super.visitPostfixOperation(x);
            JsUnaryOperator operator = x.getOperator();
            int i = operator == null ? -1 : WhenMappings.$EnumSwitchMapping$0[operator.ordinal()];
            if (i == 1 || i == 2) {
                this.sideEffectOccurred = true;
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitNameRef(JsNameRef nameRef) {
            Intrinsics.checkNotNullParameter(nameRef, "nameRef");
            JsName name2 = nameRef.getName();
            if (name2 != null && TemporaryVariableElimination.this.localVariables.contains(name2)) {
                if (TemporaryVariableElimination.this.namesToSubstitute.contains(name2) || !TemporaryVariableElimination.this.shouldConsiderTemporary(name2) || this.sideEffectOccurred) {
                    return;
                }
                this.substitutableVariableReferences.add(name2);
                return;
            }
            super.visitNameRef(nameRef);
            if (MetadataProperties.getSideEffects(nameRef) == SideEffectKind.AFFECTS_STATE) {
                this.sideEffectOccurred = true;
            }
        }

        @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
        public void visitBinaryExpression(JsBinaryOperation x) {
            Intrinsics.checkNotNullParameter(x, "x");
            if (x.getOperator() == JsBinaryOperator.ASG) {
                JsExpression arg1 = x.getArg1();
                JsExpression arg2 = x.getArg2();
                if (arg1 instanceof JsNameRef) {
                    JsExpression qualifier = ((JsNameRef) arg1).getQualifier();
                    if (qualifier != null) {
                        accept(qualifier);
                    }
                } else if (arg1 instanceof JsArrayAccess) {
                    JsArrayAccess jsArrayAccess = (JsArrayAccess) arg1;
                    accept(jsArrayAccess.getArrayExpression());
                    accept(jsArrayAccess.getIndexExpression());
                }
                accept(arg2);
                this.sideEffectOccurred = true;
                return;
            }
            if (x.getOperator() == JsBinaryOperator.AND || x.getOperator() == JsBinaryOperator.OR) {
                accept(x.getArg1());
                this.sideEffectOccurred = true;
                accept(x.getArg2());
                return;
            }
            super.visitBinaryExpression(x);
        }
    }

    private final void cleanUp() {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.TemporaryVariableElimination.cleanUp.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsBreak x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsContinue x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsFunction x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsVars x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                List<JsVars.JsVar> vars = x.getVars();
                Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
                if (TypeIntrinsics.asMutableCollection(vars).removeAll(TemporaryVariableElimination.this.statementsToRemove)) {
                    TemporaryVariableElimination.this.hasChanges = true;
                }
                List<JsVars.JsVar> vars2 = x.getVars();
                Intrinsics.checkNotNullExpressionValue(vars2, "getVars(...)");
                final TemporaryVariableElimination temporaryVariableElimination = TemporaryVariableElimination.this;
                List<Pair> listSplitToRanges = UtilsKt.splitToRanges(vars2, new Function1<JsVars.JsVar, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.TemporaryVariableElimination$cleanUp$1$visit$ranges$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(JsVars.JsVar jsVar) {
                        TemporaryVariableElimination temporaryVariableElimination2 = temporaryVariableElimination;
                        JsName name2 = jsVar.getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        return Boolean.valueOf(temporaryVariableElimination2.shouldConsiderUnused(name2));
                    }
                });
                if (listSplitToRanges.size() == 1 && !((Boolean) ((Pair) listSplitToRanges.get(0)).getSecond()).booleanValue()) {
                    return super.visit(x, ctx);
                }
                TemporaryVariableElimination.this.hasChanges = true;
                for (Pair pair : listSplitToRanges) {
                    List list = (List) pair.component1();
                    boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        JsExpression initExpression = ((JsVars.JsVar) it.next()).getInitExpression();
                        if (initExpression != null) {
                            arrayList.add(initExpression);
                        }
                    }
                    ArrayList arrayList2 = arrayList;
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        accept((JsExpression) it2.next());
                    }
                    if (zBooleanValue) {
                        Iterator it3 = arrayList2.iterator();
                        while (it3.hasNext()) {
                            JsExpressionStatement jsExpressionStatement = new JsExpressionStatement((JsExpression) accept((JsExpression) it3.next()));
                            MetadataProperties.setSynthetic(jsExpressionStatement, true);
                            ctx.addPrevious(jsExpressionStatement);
                        }
                    } else {
                        JsVars.JsVar[] jsVarArr = (JsVars.JsVar[]) list.toArray(new JsVars.JsVar[0]);
                        JsVars jsVars = new JsVars((JsVars.JsVar[]) Arrays.copyOf(jsVarArr, jsVarArr.length));
                        MetadataProperties.setSynthetic(jsVars, true);
                        ctx.addPrevious(jsVars);
                    }
                }
                ctx.removeMe();
                return false;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsExpressionStatement x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (TemporaryVariableElimination.this.statementsToRemove.contains(x)) {
                    ctx.removeMe();
                    TemporaryVariableElimination.this.hasChanges = true;
                    return false;
                }
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                if (expression instanceof JsNameRef) {
                    JsNameRef jsNameRef = (JsNameRef) expression;
                    if (jsNameRef.getQualifier() == null && CollectionsKt.contains(TemporaryVariableElimination.this.localVariables, jsNameRef.getName())) {
                        MetadataProperties.setSynthetic(x, true);
                    }
                }
                Pair<JsName, JsExpression> pairDecomposeAssignmentToVariable = JsAstUtils.decomposeAssignmentToVariable(expression);
                if (pairDecomposeAssignmentToVariable != null) {
                    JsName jsNameComponent1 = pairDecomposeAssignmentToVariable.component1();
                    JsExpression jsExpressionComponent2 = pairDecomposeAssignmentToVariable.component2();
                    TemporaryVariableElimination temporaryVariableElimination = TemporaryVariableElimination.this;
                    Intrinsics.checkNotNull(jsNameComponent1);
                    if (temporaryVariableElimination.shouldConsiderUnused(jsNameComponent1)) {
                        TemporaryVariableElimination.this.hasChanges = true;
                        JsNode jsNodeAccept = accept(new JsExpressionStatement(jsExpressionComponent2));
                        JsExpressionStatement jsExpressionStatement = (JsExpressionStatement) jsNodeAccept;
                        Intrinsics.checkNotNull(jsExpressionStatement);
                        MetadataProperties.setSynthetic(jsExpressionStatement, true);
                        ctx.replaceMe(jsNodeAccept);
                        return false;
                    }
                }
                return super.visit(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsObjectLiteral x, JsContext<?> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                Iterator<JsPropertyInitializer> it = x.getPropertyInitializers().iterator();
                while (it.hasNext()) {
                    accept(it.next().getValueExpr());
                }
                return super.visit(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsNameRef x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsName name2 = x.getName();
                if (name2 != null && x.getQualifier() == null && TemporaryVariableElimination.this.namesToSubstitute.contains(name2)) {
                    Object obj = TemporaryVariableElimination.this.definedValues.get(name2);
                    Intrinsics.checkNotNull(obj);
                    JsExpression jsExpressionDeepCopy = ((JsExpression) accept((JsNode) obj)).deepCopy();
                    Intrinsics.checkNotNull(jsExpressionDeepCopy);
                    MetadataProperties.setSynthetic(jsExpressionDeepCopy, true);
                    ctx.replaceMe(jsExpressionDeepCopy);
                    return false;
                }
                return super.visit(x, ctx);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsBinaryOperation x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                Pair<JsName, JsExpression> pairDecomposeAssignmentToVariable = JsAstUtils.decomposeAssignmentToVariable(x);
                if (pairDecomposeAssignmentToVariable != null) {
                    JsName first = pairDecomposeAssignmentToVariable.getFirst();
                    TemporaryVariableElimination temporaryVariableElimination = TemporaryVariableElimination.this;
                    Intrinsics.checkNotNull(first);
                    if (temporaryVariableElimination.shouldConsiderUnused(first)) {
                        JsNode jsNodeAccept = accept(x.getArg2());
                        JsExpression jsExpression = (JsExpression) jsNodeAccept;
                        Intrinsics.checkNotNull(jsExpression);
                        MetadataProperties.setSynthetic(jsExpression, true);
                        ctx.replaceMe(jsNodeAccept);
                    }
                }
                super.endVisit(x, (JsContext) ctx);
            }
        }.accept(this.root);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void assignVariable(JsName name2, JsExpression value) {
        Map<JsName, Integer> map = this.definitions;
        Integer num = map.get(name2);
        map.put(name2, Integer.valueOf((num != null ? num.intValue() : 0) + 1));
        this.definedValues.put(name2, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void useVariable(JsName name2) {
        Map<JsName, Integer> map = this.usages;
        Integer num = map.get(name2);
        map.put(name2, Integer.valueOf((num != null ? num.intValue() : 0) + 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldConsiderUnused(JsName name2) {
        Integer num = this.definitions.get(name2);
        if ((num != null ? num.intValue() : 0) <= 0) {
            return false;
        }
        Integer num2 = this.usages.get(name2);
        return (num2 != null ? num2.intValue() : 0) == 0 && this.temporary.contains(name2) && !MetadataProperties.getImported(name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldConsiderTemporary(JsName name2) {
        Integer num;
        Integer num2 = this.definitions.get(name2);
        if (num2 == null || num2.intValue() != 1 || !this.temporary.contains(name2) || this.capturedInClosure.contains(name2)) {
            return false;
        }
        JsExpression jsExpression = this.definedValues.get(name2);
        return (jsExpression != null && isTrivial(jsExpression)) || ((num = this.usages.get(name2)) != null && num.intValue() == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isTrivial(JsExpression expr) {
        if (expr instanceof JsNameRef) {
            JsNameRef jsNameRef = (JsNameRef) expr;
            JsExpression qualifier = jsNameRef.getQualifier();
            if (MetadataProperties.getSideEffects(expr) == SideEffectKind.PURE && (qualifier == null || isTrivial(qualifier))) {
                if (!CollectionsKt.contains(this.temporary, jsNameRef.getName())) {
                    return true;
                }
            } else {
                JsName name2 = jsNameRef.getName();
                if (CollectionsKt.contains(this.localVariables, name2)) {
                    Integer num = this.definitions.get(name2);
                    if (num == null || num.intValue() == 0) {
                        return true;
                    }
                    if (num != null && num.intValue() == 1) {
                        if (!CollectionsKt.contains(this.namesToSubstitute, name2)) {
                            return true;
                        }
                        JsExpression jsExpression = this.definedValues.get(name2);
                        if (jsExpression != null ? isTrivial(jsExpression) : false) {
                            return true;
                        }
                    }
                }
            }
        } else if (expr instanceof JsLiteral.JsValueLiteral) {
            if (expr.toString().length() < 10) {
                return true;
            }
        } else if (expr instanceof JsInvocation) {
            if (MetadataProperties.getSideEffects(expr) == SideEffectKind.PURE) {
                JsInvocation jsInvocation = (JsInvocation) expr;
                JsExpression qualifier2 = jsInvocation.getQualifier();
                Intrinsics.checkNotNullExpressionValue(qualifier2, "getQualifier(...)");
                if (isTrivial(qualifier2)) {
                    List<JsExpression> arguments = jsInvocation.getArguments();
                    Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                    List<JsExpression> list = arguments;
                    if ((list instanceof Collection) && list.isEmpty()) {
                        return true;
                    }
                    for (JsExpression jsExpression2 : list) {
                        Intrinsics.checkNotNull(jsExpression2);
                        if (!isTrivial(jsExpression2)) {
                        }
                    }
                    return true;
                }
            }
        } else if (expr instanceof JsArrayAccess) {
            JsArrayAccess jsArrayAccess = (JsArrayAccess) expr;
            JsExpression arrayExpression = jsArrayAccess.getArrayExpression();
            Intrinsics.checkNotNullExpressionValue(arrayExpression, "getArrayExpression(...)");
            if (isTrivial(arrayExpression)) {
                JsExpression indexExpression = jsArrayAccess.getIndexExpression();
                Intrinsics.checkNotNullExpressionValue(indexExpression, "getIndexExpression(...)");
                if (isTrivial(indexExpression) && MetadataProperties.getSideEffects(expr) == SideEffectKind.PURE) {
                    return true;
                }
            }
        }
        return false;
    }
}
