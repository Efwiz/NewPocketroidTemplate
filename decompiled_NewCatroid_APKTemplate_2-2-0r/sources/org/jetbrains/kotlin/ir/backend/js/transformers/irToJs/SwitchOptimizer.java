package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCase;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDefault;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;

/* JADX INFO: compiled from: SwitchOptimizer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u001b\u001cB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0018\u0010\u0007\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\u0014J\f\u0010\u0017\u001a\u00020\u0006*\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u001aH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer;", "", "context", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;)V", "isExpression", "", "lastStatementTransformer", "Lkotlin/Function1;", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "(Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;ZLkotlin/jvm/functions/Function1;)V", "jsEqeq", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "jsEqeqeq", "buildJsSwitch", PsiKeyword.SWITCH, "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer$SwitchData;", "detectSwitch", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "tryOptimize", "irWhen", "isSuitableForSwitch", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isTrueConstant", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "SwitchBranchData", "SwitchData", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SwitchOptimizer {
    private final JsGenerationContext context;
    private final boolean isExpression;
    private final IrSimpleFunctionSymbol jsEqeq;
    private final IrSimpleFunctionSymbol jsEqeqeq;
    private final Function1<Function0<? extends JsStatement>, JsStatement> lastStatementTransformer;

    /* JADX WARN: Multi-variable type inference failed */
    public SwitchOptimizer(JsGenerationContext context, boolean z, Function1<? super Function0<? extends JsStatement>, ? extends JsStatement> lastStatementTransformer) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(lastStatementTransformer, "lastStatementTransformer");
        this.context = context;
        this.isExpression = z;
        this.lastStatementTransformer = lastStatementTransformer;
        this.jsEqeqeq = context.getStaticContext().getBackendContext().getIntrinsics().getJsEqeqeq();
        this.jsEqeq = context.getStaticContext().getBackendContext().getIntrinsics().getJsEqeq();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SwitchOptimizer(JsGenerationContext context) {
        this(context, false, new Function1<Function0<? extends JsStatement>, JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.SwitchOptimizer.1
            @Override // kotlin.jvm.functions.Function1
            public final JsStatement invoke(Function0<? extends JsStatement> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.invoke();
            }
        });
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final boolean isTrueConstant(IrConst<?> irConst) {
        if (irConst.getKind() != IrConstKind.Boolean.INSTANCE) {
            return false;
        }
        Object value = irConst.getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
        return ((Boolean) value).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: SwitchOptimizer.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer$SwitchBranchData;", "", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "SwitchCaseData", "SwitchDefaultData", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer$SwitchBranchData$SwitchCaseData;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer$SwitchBranchData$SwitchDefaultData;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static abstract class SwitchBranchData {
        private final IrExpression body;

        public /* synthetic */ SwitchBranchData(IrExpression irExpression, DefaultConstructorMarker defaultConstructorMarker) {
            this(irExpression);
        }

        /* JADX INFO: compiled from: SwitchOptimizer.kt */
        @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0010\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u001b\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer$SwitchBranchData$SwitchCaseData;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer$SwitchBranchData;", "cases", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getCases", "()Ljava/util/Collection;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SwitchCaseData extends SwitchBranchData {
            private final Collection<IrConst<?>> cases;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public SwitchCaseData(Collection<? extends IrConst<?>> cases, IrExpression body) {
                super(body, null);
                Intrinsics.checkNotNullParameter(cases, "cases");
                Intrinsics.checkNotNullParameter(body, "body");
                this.cases = cases;
            }

            public final Collection<IrConst<?>> getCases() {
                return this.cases;
            }
        }

        private SwitchBranchData(IrExpression irExpression) {
            this.body = irExpression;
        }

        public final IrExpression getBody() {
            return this.body;
        }

        /* JADX INFO: compiled from: SwitchOptimizer.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer$SwitchBranchData$SwitchDefaultData;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer$SwitchBranchData;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SwitchDefaultData extends SwitchBranchData {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public SwitchDefaultData(IrExpression body) {
                super(body, null);
                Intrinsics.checkNotNullParameter(body, "body");
            }
        }
    }

    /* JADX INFO: compiled from: SwitchOptimizer.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer$SwitchData;", "", "subject", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "cases", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/SwitchOptimizer$SwitchBranchData;", "(Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;Ljava/util/Collection;)V", "getCases", "()Ljava/util/Collection;", "getSubject", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SwitchData {
        private final Collection<SwitchBranchData> cases;
        private final IrValueSymbol subject;

        /* JADX WARN: Multi-variable type inference failed */
        public SwitchData(IrValueSymbol subject, Collection<? extends SwitchBranchData> cases) {
            Intrinsics.checkNotNullParameter(subject, "subject");
            Intrinsics.checkNotNullParameter(cases, "cases");
            this.subject = subject;
            this.cases = cases;
        }

        public final Collection<SwitchBranchData> getCases() {
            return this.cases;
        }

        public final IrValueSymbol getSubject() {
            return this.subject;
        }
    }

    private final SwitchData detectSwitch(IrWhen expression) {
        IrValueDeclaration owner;
        IrType type;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        ArrayList arrayList = new ArrayList();
        int size = 0;
        for (IrBranch irBranch : expression.getBranches()) {
            IrExpression condition = irBranch.getCondition();
            if (condition instanceof IrCall) {
                IrConst<?> irConstDetectSwitch$tryToExtractEqeqeqConst = detectSwitch$tryToExtractEqeqeqConst(this, objectRef, (IrCall) condition);
                if (irConstDetectSwitch$tryToExtractEqeqeqConst == null) {
                    return null;
                }
                size++;
                arrayList.add(new SwitchBranchData.SwitchCaseData(CollectionsKt.listOf(irConstDetectSwitch$tryToExtractEqeqeqConst), irBranch.getResult()));
            } else if (condition instanceof IrWhen) {
                ArrayList arrayList2 = new ArrayList();
                if (!detectSwitch$checkForPrimitiveOrPattern(this, objectRef, (IrWhen) condition, arrayList2)) {
                    return null;
                }
                size += arrayList2.size();
                arrayList.add(new SwitchBranchData.SwitchCaseData(arrayList2, irBranch.getResult()));
            } else {
                if (!(condition instanceof IrConst)) {
                    return null;
                }
                if (isTrueConstant((IrConst) condition)) {
                    size++;
                    arrayList.add(new SwitchBranchData.SwitchDefaultData(irBranch.getResult()));
                    break;
                }
            }
        }
        IrValueSymbol irValueSymbol = (IrValueSymbol) objectRef.element;
        if (size < 3 || irValueSymbol == null || (owner = irValueSymbol.getOwner()) == null || (type = owner.getType()) == null || !isSuitableForSwitch(type)) {
            return null;
        }
        return new SwitchData(irValueSymbol, arrayList);
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [T, org.jetbrains.kotlin.ir.symbols.IrValueSymbol] */
    private static final IrConst<?> detectSwitch$tryToExtractEqeqeqConst(SwitchOptimizer switchOptimizer, Ref.ObjectRef<IrValueSymbol> objectRef, IrCall irCall) {
        if (irCall.getSymbol() != switchOptimizer.jsEqeqeq && irCall.getSymbol() != switchOptimizer.jsEqeq) {
            return null;
        }
        IrExpression valueArgument = irCall.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        IrExpression valueArgument2 = irCall.getValueArgument(1);
        Intrinsics.checkNotNull(valueArgument2);
        IrConst<?> irConst = valueArgument instanceof IrConst ? (IrConst) valueArgument : null;
        if (irConst == null) {
            irConst = valueArgument2 instanceof IrConst ? (IrConst) valueArgument2 : null;
            if (irConst == null) {
                return null;
            }
        }
        IrGetValue irGetValue = valueArgument instanceof IrGetValue ? (IrGetValue) valueArgument : null;
        if (irGetValue == null) {
            irGetValue = valueArgument2 instanceof IrGetValue ? (IrGetValue) valueArgument2 : null;
            if (irGetValue == null) {
                return null;
            }
        }
        if (objectRef.element == null) {
            objectRef.element = irGetValue.getSymbol();
        }
        if (objectRef.element != irGetValue.getSymbol()) {
            return null;
        }
        return irConst;
    }

    private static final boolean detectSwitch$checkForPrimitiveOrPattern(SwitchOptimizer switchOptimizer, Ref.ObjectRef<IrValueSymbol> objectRef, IrWhen irWhen, List<IrConst<?>> list) {
        if (irWhen.getBranches().size() != 2) {
            return false;
        }
        IrBranch irBranch = irWhen.getBranches().get(0);
        IrBranch irBranch2 = irWhen.getBranches().get(1);
        return detectSwitch$checkForPrimitiveOrPattern$checkBranchIsOrPattern(switchOptimizer, list, objectRef, irBranch.getResult(), irBranch.getCondition()) && detectSwitch$checkForPrimitiveOrPattern$checkBranchIsOrPattern(switchOptimizer, list, objectRef, irBranch2.getCondition(), irBranch2.getResult());
    }

    private static final boolean detectSwitch$checkForPrimitiveOrPattern$checkBranchIsOrPattern(SwitchOptimizer switchOptimizer, List<IrConst<?>> list, Ref.ObjectRef<IrValueSymbol> objectRef, IrExpression irExpression, IrExpression irExpression2) {
        IrConst<?> irConstDetectSwitch$tryToExtractEqeqeqConst;
        if (!(irExpression instanceof IrConst) || !switchOptimizer.isTrueConstant((IrConst) irExpression)) {
            return false;
        }
        if (irExpression2 instanceof IrWhen) {
            return detectSwitch$checkForPrimitiveOrPattern(switchOptimizer, objectRef, (IrWhen) irExpression2, list);
        }
        if (!(irExpression2 instanceof IrCall) || (irConstDetectSwitch$tryToExtractEqeqeqConst = detectSwitch$tryToExtractEqeqeqConst(switchOptimizer, objectRef, (IrCall) irExpression2)) == null) {
            return false;
        }
        list.add(irConstDetectSwitch$tryToExtractEqeqeqConst);
        return true;
    }

    private final JsStatement buildJsSwitch(SwitchData switchData) {
        JsDefault jsDefault;
        JsStatement jsStatementInvoke;
        final IrElementToJsExpressionTransformer irElementToJsExpressionTransformer = new IrElementToJsExpressionTransformer();
        IrElementToJsStatementTransformer irElementToJsStatementTransformer = new IrElementToJsStatementTransformer();
        JsNameRef jsNameRefMakeRef = this.context.getNameForValueDeclaration(switchData.getSubject().getOwner()).makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        ArrayList arrayList = new ArrayList();
        for (final SwitchBranchData switchBranchData : switchData.getCases()) {
            if (switchBranchData instanceof SwitchBranchData.SwitchCaseData) {
                ArrayList arrayList2 = arrayList;
                Collection<IrConst<?>> cases = ((SwitchBranchData.SwitchCaseData) switchBranchData).getCases();
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(cases, 10));
                Iterator<T> it = cases.iterator();
                while (it.hasNext()) {
                    IrConst irConst = (IrConst) it.next();
                    JsCase jsCase = new JsCase();
                    jsCase.setCaseExpression((JsExpression) irConst.accept(irElementToJsExpressionTransformer, this.context));
                    arrayList3.add(jsCase);
                }
                CollectionsKt.addAll(arrayList2, arrayList3);
                jsDefault = (JsSwitchMember) CollectionsKt.last((List) arrayList);
            } else {
                JsDefault jsDefault2 = new JsDefault();
                arrayList.add(jsDefault2);
                jsDefault = jsDefault2;
            }
            if (this.isExpression) {
                jsStatementInvoke = this.lastStatementTransformer.invoke(new Function0<JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.SwitchOptimizer$buildJsSwitch$lastStatement$lastStatement$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final JsStatement invoke() {
                        JsStatement jsStatementMakeStmt = ((JsExpression) switchBranchData.getBody().accept(irElementToJsExpressionTransformer, this.context)).makeStmt();
                        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
                        return jsStatementMakeStmt;
                    }
                });
                List<JsStatement> statements = jsDefault.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                statements.add(jsStatementInvoke);
            } else {
                JsBlock jsBlockAsBlock = JsAstUtilsKt.asBlock((JsStatement) switchBranchData.getBody().accept(irElementToJsStatementTransformer, this.context));
                List<JsStatement> statements2 = jsBlockAsBlock.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                final JsStatement jsStatement = (JsStatement) CollectionsKt.lastOrNull((List) statements2);
                JsStatement jsStatementInvoke2 = jsStatement != null ? this.lastStatementTransformer.invoke(new Function0<JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.SwitchOptimizer$buildJsSwitch$lastStatement$lastStatement$2$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final JsStatement invoke() {
                        return jsStatement;
                    }
                }) : null;
                List<JsStatement> statements3 = jsDefault.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                List<JsStatement> statements4 = jsBlockAsBlock.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
                CollectionsKt.addAll(statements3, statements4);
                if (jsStatementInvoke2 != null) {
                    List<JsStatement> statements5 = jsDefault.getStatements();
                    List<JsStatement> statements6 = jsDefault.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements6, "getStatements(...)");
                    statements5.set(CollectionsKt.getLastIndex(statements6), jsStatementInvoke2);
                }
                jsStatementInvoke = jsStatementInvoke2;
            }
            if (!(jsStatementInvoke instanceof JsBreak) && !(jsStatementInvoke instanceof JsContinue) && !(jsStatementInvoke instanceof JsReturn) && !(jsStatementInvoke instanceof JsThrow)) {
                List<JsStatement> statements7 = jsDefault.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements7, "getStatements(...)");
                statements7.add(new JsBreak());
            }
        }
        return new JsSwitch(jsNameRefMakeRef, arrayList);
    }

    private final boolean isSuitableForSwitch(IrType irType) {
        IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(irType);
        return IrTypePredicatesKt.isBoolean(irTypeMakeNotNull) || IrTypePredicatesKt.isByte(irTypeMakeNotNull) || IrTypePredicatesKt.isShort(irTypeMakeNotNull) || IrTypePredicatesKt.isInt(irTypeMakeNotNull) || IrTypePredicatesKt.isFloat(irTypeMakeNotNull) || IrTypePredicatesKt.isDouble(irTypeMakeNotNull) || IrTypePredicatesKt.isString(irTypeMakeNotNull);
    }

    public final JsStatement tryOptimize(IrWhen irWhen) {
        Intrinsics.checkNotNullParameter(irWhen, "irWhen");
        SwitchData switchDataDetectSwitch = detectSwitch(irWhen);
        if (switchDataDetectSwitch != null) {
            return (JsStatement) JsAstUtilsKt.withSource$default(buildJsSwitch(switchDataDetectSwitch), irWhen, this.context, null, null, 12, null);
        }
        return null;
    }
}
