package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssertStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiBlockStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiBreakStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElementList;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiContinueStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDefaultCaseLabelElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDoWhileStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEmptyStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionListStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiForStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiInstanceOfExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIntersectionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiLabeledStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiLoopStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPostfixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimaryPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceListElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSuperExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabeledRuleStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSynchronizedStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiThisExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiThrowStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeCastExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeTestPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhileStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiYieldStatement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.JavaPsiPatternUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.JavaPsiRecordUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArrayList;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList;

/* JADX INFO: loaded from: classes3.dex */
final class ControlFlowAnalyzer extends JavaElementVisitor {
    private static final Logger LOG = Logger.getInstance((Class<?>) ControlFlowAnalyzer.class);
    private final Map<PsiElement, List<PsiElement>> finallyBlockToUnhandledExceptions;
    private final Stack<IntList> intArrayPool;
    private final boolean myAssignmentTargetsAreElements;
    private final Stack<PsiElement> myCatchBlocks;
    private final Stack<PsiParameter> myCatchParameters;
    private final PsiElement myCodeFragment;
    private final PsiConstantEvaluationHelper myConstantEvaluationHelper;
    private final ControlFlowFactory myControlFlowFactory;
    private ControlFlowImpl myCurrentFlow;
    private final Stack<BranchingInstruction.Role> myEndJumpRoles;
    private final StatementStack myEndStatementStack;
    private final Stack<FinallyBlockSubroutine> myFinallyBlocks;
    private final Map<PsiField, PsiParameter> myImplicitCompactConstructorAssignments;
    private final ControlFlowOptions myOptions;
    private final ControlFlowPolicy myPolicy;
    private final Stack<BranchingInstruction.Role> myStartJumpRoles;
    private final StatementStack myStartStatementStack;
    private final List<SubRangeInfo> mySubRanges;
    private final Stack<PsiElement> myUnhandledExceptionCatchBlocks;
    private final Map<PsiElement, IntList> offsetsAddElementEnd;
    private final Map<PsiElement, IntList> offsetsAddElementStart;

    private enum Shortcut {
        NO_SHORTCUT,
        SKIP_CURRENT_OPERAND,
        STOP_EXPRESSION
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 7 || i == 32 || i == 33 || i == 35) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 7 || i == 32 || i == 33 || i == 35) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "policy";
                break;
            case 2:
            case 5:
                objArr[0] = "options";
                break;
            case 3:
            case 24:
            default:
                objArr[0] = "codeFragment";
                break;
            case 6:
            case 7:
            case 32:
            case 33:
            case 35:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowAnalyzer";
                break;
            case 8:
                objArr[0] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 27:
            case 40:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 17:
                objArr[0] = "unhandledException";
                break;
            case 18:
            case 20:
            case 22:
                objArr[0] = "throwingElement";
                break;
            case 19:
            case 21:
                objArr[0] = "instruction";
                break;
            case 23:
                objArr[0] = "elementToJumpTo";
                break;
            case 25:
                objArr[0] = "file";
                break;
            case 26:
                objArr[0] = "sourceElement";
                break;
            case 28:
            case 29:
            case 31:
                objArr[0] = "statement";
                break;
            case 30:
                objArr[0] = "blocks";
                break;
            case 34:
                objArr[0] = "throwType";
                break;
            case 36:
                objArr[0] = "lOperand";
                break;
            case 37:
                objArr[0] = "signTokenType";
                break;
            case 38:
            case 39:
                objArr[0] = "expression";
                break;
            case 41:
                objArr[0] = "array";
                break;
            case 42:
                objArr[0] = "scope";
                break;
            case 43:
            case 44:
                objArr[0] = "variable";
                break;
            case 45:
                objArr[0] = "refExpr";
                break;
        }
        if (i == 6) {
            objArr[1] = "buildControlFlow";
        } else if (i == 7) {
            objArr[1] = "getEmptyIntArray";
        } else if (i == 32 || i == 33 || i == 35) {
            objArr[1] = "findThrowToBlocks";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowAnalyzer";
        }
        switch (i) {
            case 6:
            case 7:
            case 32:
            case 33:
            case 35:
                break;
            case 8:
                objArr[2] = "poolIntArray";
                break;
            case 9:
                objArr[2] = "addElementOffsetLater";
                break;
            case 10:
                objArr[2] = "patchInstructionOffsets";
                break;
            case 11:
                objArr[2] = "startElement";
                break;
            case 12:
                objArr[2] = "generateUncheckedExceptionJumpsIfNeeded";
                break;
            case 13:
                objArr[2] = "finishElement";
                break;
            case 14:
                objArr[2] = "generateUncheckedExceptionJumps";
                break;
            case 15:
                objArr[2] = "generateCheckedExceptionJumps";
                break;
            case 16:
                objArr[2] = "generateExceptionJumps";
                break;
            case 17:
            case 18:
                objArr[2] = "generateThrow";
                break;
            case 19:
            case 20:
                objArr[2] = "patchCheckedThrowInstructionIfInsideFinally";
                break;
            case 21:
            case 22:
            case 23:
                objArr[2] = "patchUncheckedThrowInstructionIfInsideFinally";
                break;
            case 24:
                objArr[2] = "registerSubRange";
                break;
            case 25:
                objArr[2] = "visitFile";
                break;
            case 26:
                objArr[2] = "findEnclosingFinallyBlockElement";
                break;
            case 27:
                objArr[2] = "processVariable";
                break;
            case 28:
                objArr[2] = "generateConditionalStatementInstructions";
                break;
            case 29:
                objArr[2] = "addReturnInstruction";
                break;
            case 30:
                objArr[2] = "addThrowInstructions";
                break;
            case 31:
            case 34:
                objArr[2] = "findThrowToBlocks";
                break;
            case 36:
            case 37:
                objArr[2] = "generateLOperand";
                break;
            case 38:
                objArr[2] = "isInsideIfCondition";
                break;
            case 39:
                objArr[2] = "shouldCalculateConstantExpression";
                break;
            case 40:
                objArr[2] = "visitChildren";
                break;
            case 41:
            case 42:
                objArr[2] = "addUsedVariables";
                break;
            case 43:
                objArr[2] = "generateReadInstruction";
                break;
            case 44:
                objArr[2] = "generateWriteInstruction";
                break;
            case 45:
                objArr[2] = "getUsedVariable";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7 && i != 32 && i != 33 && i != 35) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    ControlFlowAnalyzer(PsiElement psiElement, ControlFlowPolicy controlFlowPolicy, ControlFlowOptions controlFlowOptions) {
        this(psiElement, controlFlowPolicy, controlFlowOptions, false);
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (controlFlowPolicy == null) {
            $$$reportNull$$$0(1);
        }
        if (controlFlowOptions == null) {
            $$$reportNull$$$0(2);
        }
    }

    private ControlFlowAnalyzer(PsiElement psiElement, ControlFlowPolicy controlFlowPolicy, ControlFlowOptions controlFlowOptions, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (controlFlowPolicy == null) {
            $$$reportNull$$$0(4);
        }
        if (controlFlowOptions == null) {
            $$$reportNull$$$0(5);
        }
        this.myCatchParameters = new Stack<>();
        this.myCatchBlocks = new Stack<>();
        this.myFinallyBlocks = new Stack<>();
        this.myUnhandledExceptionCatchBlocks = new Stack<>();
        AnonymousClass1 anonymousClass1 = null;
        this.myStartStatementStack = new StatementStack(anonymousClass1);
        this.myEndStatementStack = new StatementStack(anonymousClass1);
        this.myStartJumpRoles = new Stack<>();
        this.myEndJumpRoles = new Stack<>();
        this.intArrayPool = new Stack<>();
        this.offsetsAddElementStart = new HashMap();
        this.offsetsAddElementEnd = new HashMap();
        this.mySubRanges = new ArrayList();
        this.finallyBlockToUnhandledExceptions = new HashMap();
        this.myCodeFragment = psiElement;
        this.myPolicy = controlFlowPolicy;
        this.myOptions = controlFlowOptions;
        this.myAssignmentTargetsAreElements = z;
        Project project = psiElement.getProject();
        this.myControlFlowFactory = ControlFlowFactory.getInstance(project);
        this.myConstantEvaluationHelper = JavaPsiFacade.getInstance(project).getConstantEvaluationHelper();
        this.myImplicitCompactConstructorAssignments = getImplicitCompactConstructorAssignmentsMap();
    }

    private Map<PsiField, PsiParameter> getImplicitCompactConstructorAssignmentsMap() {
        PsiMethod psiMethod = (PsiMethod) ObjectUtils.tryCast(this.myCodeFragment.getMyParent(), PsiMethod.class);
        if (psiMethod == null || !JavaPsiRecordUtil.isCompactConstructor(psiMethod)) {
            return Collections.emptyMap();
        }
        PsiClass containingClass = psiMethod.mo6092getContainingClass();
        if (containingClass == null) {
            return Collections.emptyMap();
        }
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        PsiRecordComponent[] recordComponents = containingClass.getRecordComponents();
        HashMap map = new HashMap();
        for (int i = 0; i < Math.min(recordComponents.length, parameters.length); i++) {
            map.put(JavaPsiRecordUtil.getFieldForComponent(recordComponents[i]), parameters[i]);
        }
        return map;
    }

    ControlFlow buildControlFlow() throws AnalysisCanceledException {
        this.myStartJumpRoles.push(BranchingInstruction.Role.END);
        this.myEndJumpRoles.push(BranchingInstruction.Role.END);
        this.myCurrentFlow = new ControlFlowImpl();
        this.myStartStatementStack.pushStatement(this.myCodeFragment, false);
        this.myEndStatementStack.pushStatement(this.myCodeFragment, false);
        try {
            this.myCodeFragment.accept(this);
            ControlFlow controlFlowCleanup = cleanup();
            if (controlFlowCleanup == null) {
                $$$reportNull$$$0(6);
            }
            return controlFlowCleanup;
        } catch (AnalysisCanceledSoftException e) {
            throw new AnalysisCanceledException(e.getErrorElement());
        }
    }

    private void generateCompactConstructorAssignments() {
        Stream<PsiParameter> stream = this.myImplicitCompactConstructorAssignments.values().stream();
        final ControlFlowPolicy controlFlowPolicy = this.myPolicy;
        Objects.requireNonNull(controlFlowPolicy);
        stream.filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowAnalyzer$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return controlFlowPolicy.isParameterAccepted((PsiParameter) obj);
            }
        }).forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowAnalyzer$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.generateReadInstruction((PsiParameter) obj);
            }
        });
    }

    private static class StatementStack {
        private final IntList myAtStart;
        private final Stack<PsiElement> myStatements;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[i != 1 ? 2 : 3];
            if (i != 1) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowAnalyzer$StatementStack";
            } else {
                objArr[0] = "statement";
            }
            if (i != 1) {
                objArr[1] = "peekElement";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowAnalyzer$StatementStack";
            }
            if (i == 1) {
                objArr[2] = "pushStatement";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalArgumentException(str2);
            }
        }

        private StatementStack() {
            this.myStatements = new Stack<>();
            this.myAtStart = new IntArrayList();
        }

        /* synthetic */ StatementStack(AnonymousClass1 anonymousClass1) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void popStatement() {
            IntList intList = this.myAtStart;
            intList.removeInt(intList.size() - 1);
            this.myStatements.pop();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public PsiElement peekElement() {
            PsiElement psiElementPeek = this.myStatements.peek();
            if (psiElementPeek == null) {
                $$$reportNull$$$0(0);
            }
            return psiElementPeek;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean peekAtStart() {
            IntList intList = this.myAtStart;
            return intList.getInt(intList.size() - 1) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void pushStatement(PsiElement psiElement, boolean z) {
            if (psiElement == null) {
                $$$reportNull$$$0(1);
            }
            this.myStatements.push(psiElement);
            this.myAtStart.add(z ? 1 : 0);
        }
    }

    private IntList getEmptyIntArray() {
        if (this.intArrayPool.isEmpty()) {
            return new IntArrayList(1);
        }
        IntList intListPop = this.intArrayPool.pop();
        intListPop.clear();
        if (intListPop == null) {
            $$$reportNull$$$0(7);
        }
        return intListPop;
    }

    private void poolIntArray(IntList intList) {
        if (intList == null) {
            $$$reportNull$$$0(8);
        }
        this.intArrayPool.add(intList);
    }

    private void addElementOffsetLater(PsiElement psiElement, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
        Map<PsiElement, IntList> map = z ? this.offsetsAddElementStart : this.offsetsAddElementEnd;
        IntList emptyIntArray = map.get(psiElement);
        if (emptyIntArray == null) {
            emptyIntArray = getEmptyIntArray();
            map.put(psiElement, emptyIntArray);
        }
        emptyIntArray.add(this.myCurrentFlow.getSize() - 1);
        if (this.myCurrentFlow.getEndOffset(psiElement) != -1) {
            patchInstructionOffsets(psiElement);
        }
    }

    private void patchInstructionOffsets(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        patchInstructionOffsets(this.offsetsAddElementStart.get(psiElement), this.myCurrentFlow.getStartOffset(psiElement));
        this.offsetsAddElementStart.put(psiElement, null);
        patchInstructionOffsets(this.offsetsAddElementEnd.get(psiElement), this.myCurrentFlow.getEndOffset(psiElement));
        this.offsetsAddElementEnd.put(psiElement, null);
    }

    private void patchInstructionOffsets(IntList intList, int i) {
        if (intList == null) {
            return;
        }
        for (int i2 = 0; i2 < intList.size(); i2++) {
            BranchingInstruction branchingInstruction = (BranchingInstruction) this.myCurrentFlow.getInstructions().get(intList.getInt(i2));
            branchingInstruction.offset += i;
            LOG.assertTrue(branchingInstruction.offset >= 0);
        }
        poolIntArray(intList);
    }

    private ControlFlow cleanup() {
        Iterator<IntList> it = this.offsetsAddElementStart.values().iterator();
        while (it.hasNext()) {
            patchInstructionOffsets(it.next(), this.myCurrentFlow.getEndOffset(this.myCodeFragment));
        }
        Iterator<IntList> it2 = this.offsetsAddElementEnd.values().iterator();
        while (it2.hasNext()) {
            patchInstructionOffsets(it2.next(), this.myCurrentFlow.getEndOffset(this.myCodeFragment));
        }
        ControlFlow controlFlowImmutableCopy = this.myCurrentFlow.immutableCopy();
        for (SubRangeInfo subRangeInfo : this.mySubRanges) {
            ProgressManager.checkCanceled();
            this.myControlFlowFactory.registerSubRange(subRangeInfo.myElement, new ControlFlowSubRange(controlFlowImmutableCopy, subRangeInfo.myStart, subRangeInfo.myEnd), this.myOptions, this.myPolicy);
        }
        return controlFlowImmutableCopy;
    }

    private void startElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            ProgressManager.checkCanceled();
            if ((firstChild instanceof PsiErrorElement) && !Comparing.strEqual(((PsiErrorElement) firstChild).getErrorDescription(), JavaPsiBundle.message("expected.semicolon", new Object[0]))) {
                throw new AnalysisCanceledSoftException(psiElement);
            }
        }
        ProgressManager.checkCanceled();
        this.myCurrentFlow.startElement(psiElement);
        generateUncheckedExceptionJumpsIfNeeded(psiElement, true);
    }

    private void generateUncheckedExceptionJumpsIfNeeded(PsiElement psiElement, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        boolean z2 = false;
        if (!this.myOptions.isExceptionAfterAssignment() && !z) {
            if (psiElement instanceof PsiExpression) {
                PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiElement.getMyParent());
                if ((psiElementSkipParenthesizedExprUp instanceof PsiAssignmentExpression) && (psiElementSkipParenthesizedExprUp.getMyParent() instanceof PsiExpressionStatement)) {
                    generateUncheckedExceptionJumps(psiElement, false);
                    return;
                }
            }
            if (psiElement instanceof PsiCodeBlock) {
                return;
            }
            if ((psiElement instanceof PsiExpressionStatement) && (((PsiExpressionStatement) psiElement).getExpression() instanceof PsiAssignmentExpression)) {
                return;
            }
        }
        boolean z3 = (psiElement instanceof PsiStatement) && !(psiElement instanceof PsiSwitchLabelStatement);
        if ((psiElement instanceof PsiCodeBlock) && !(psiElement.getMyParent() instanceof PsiSwitchStatement)) {
            z2 = true;
        }
        if (z3 || z2) {
            generateUncheckedExceptionJumps(psiElement, z);
        }
    }

    private void finishElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        generateUncheckedExceptionJumpsIfNeeded(psiElement, false);
        this.myCurrentFlow.finishElement(psiElement);
        patchInstructionOffsets(psiElement);
    }

    private void generateUncheckedExceptionJumps(PsiElement psiElement, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        if (z && (psiElement instanceof PsiStatement) && (psiElement.getMyParent() instanceof PsiCodeBlock) && psiElement.getPrevSibling() != null) {
            return;
        }
        for (int size = this.myUnhandledExceptionCatchBlocks.size() - 1; size >= 0; size--) {
            ProgressManager.checkCanceled();
            PsiElement psiElement2 = this.myUnhandledExceptionCatchBlocks.get(size);
            if (psiElement2 == null) {
                if (!this.myFinallyBlocks.isEmpty()) {
                    break;
                }
            } else {
                ConditionalThrowToInstruction conditionalThrowToInstruction = new ConditionalThrowToInstruction(-1);
                this.myCurrentFlow.addInstruction(conditionalThrowToInstruction);
                if (!patchUncheckedThrowInstructionIfInsideFinally(conditionalThrowToInstruction, psiElement, psiElement2)) {
                    addElementOffsetLater(psiElement2, true);
                }
            }
        }
        if (this.myFinallyBlocks.isEmpty()) {
            return;
        }
        PsiElement element = this.myFinallyBlocks.peek().getElement();
        ConditionalThrowToInstruction conditionalThrowToInstruction2 = new ConditionalThrowToInstruction(-2);
        this.myCurrentFlow.addInstruction(conditionalThrowToInstruction2);
        if (patchUncheckedThrowInstructionIfInsideFinally(conditionalThrowToInstruction2, psiElement, element)) {
            return;
        }
        addElementOffsetLater(element, true);
    }

    private void generateCheckedExceptionJumps(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(15);
        }
        generateExceptionJumps(psiElement, ExceptionUtil.collectUnhandledExceptions(psiElement, psiElement.getMyParent()));
    }

    private void generateExceptionJumps(PsiElement psiElement, Collection<? extends PsiClassType> collection) {
        if (psiElement == null) {
            $$$reportNull$$$0(16);
        }
        for (PsiClassType psiClassType : collection) {
            ProgressManager.checkCanceled();
            generateThrow(psiClassType, psiElement);
        }
    }

    private void generateThrow(PsiClassType psiClassType, PsiElement psiElement) {
        if (psiClassType == null) {
            $$$reportNull$$$0(17);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(18);
        }
        for (PsiElement psiElement2 : findThrowToBlocks(psiClassType)) {
            ProgressManager.checkCanceled();
            ConditionalThrowToInstruction conditionalThrowToInstruction = new ConditionalThrowToInstruction(0);
            this.myCurrentFlow.addInstruction(conditionalThrowToInstruction);
            if (!patchCheckedThrowInstructionIfInsideFinally(conditionalThrowToInstruction, psiElement, psiElement2)) {
                if (psiElement2 == null) {
                    addElementOffsetLater(this.myCodeFragment, false);
                } else {
                    conditionalThrowToInstruction.offset--;
                    addElementOffsetLater(psiElement2, true);
                }
            }
        }
    }

    private boolean patchCheckedThrowInstructionIfInsideFinally(ConditionalThrowToInstruction conditionalThrowToInstruction, PsiElement psiElement, PsiElement psiElement2) {
        if (conditionalThrowToInstruction == null) {
            $$$reportNull$$$0(19);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(20);
        }
        PsiElement psiElementFindEnclosingFinallyBlockElement = findEnclosingFinallyBlockElement(psiElement, psiElement2);
        if (psiElementFindEnclosingFinallyBlockElement == null) {
            return false;
        }
        List<PsiElement> listComputeIfAbsent = this.finallyBlockToUnhandledExceptions.computeIfAbsent(psiElementFindEnclosingFinallyBlockElement, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowAnalyzer$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ControlFlowAnalyzer.lambda$patchCheckedThrowInstructionIfInsideFinally$0((PsiElement) obj);
            }
        });
        int iIndexOf = listComputeIfAbsent.indexOf(psiElement2);
        if (iIndexOf == -1) {
            iIndexOf = listComputeIfAbsent.size();
            listComputeIfAbsent.add(psiElement2);
        }
        conditionalThrowToInstruction.offset = iIndexOf + 3;
        addElementOffsetLater(psiElementFindEnclosingFinallyBlockElement, false);
        return true;
    }

    static /* synthetic */ List lambda$patchCheckedThrowInstructionIfInsideFinally$0(PsiElement psiElement) {
        return new ArrayList();
    }

    private boolean patchUncheckedThrowInstructionIfInsideFinally(ConditionalThrowToInstruction conditionalThrowToInstruction, PsiElement psiElement, PsiElement psiElement2) {
        if (conditionalThrowToInstruction == null) {
            $$$reportNull$$$0(21);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(22);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(23);
        }
        PsiElement psiElementFindEnclosingFinallyBlockElement = findEnclosingFinallyBlockElement(psiElement, psiElement2);
        if (psiElementFindEnclosingFinallyBlockElement == null) {
            return false;
        }
        conditionalThrowToInstruction.offset = 2;
        addElementOffsetLater(psiElementFindEnclosingFinallyBlockElement, false);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitCodeFragment(JavaCodeFragment javaCodeFragment) {
        startElement(javaCodeFragment);
        int size = this.myCurrentFlow.getSize();
        for (PsiElement psiElement : javaCodeFragment.getChildren()) {
            ProgressManager.checkCanceled();
            psiElement.accept(this);
        }
        finishElement(javaCodeFragment);
        registerSubRange(javaCodeFragment, size);
    }

    private void registerSubRange(PsiElement psiElement, int i) {
        if (psiElement == null) {
            $$$reportNull$$$0(24);
        }
        this.mySubRanges.add(new SubRangeInfo(psiElement, i, this.myCurrentFlow.getSize(), null));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitCodeBlock(PsiCodeBlock psiCodeBlock) {
        startElement(psiCodeBlock);
        int size = this.myCurrentFlow.getSize();
        for (PsiStatement psiStatement : psiCodeBlock.getStatements()) {
            ProgressManager.checkCanceled();
            psiStatement.accept(this);
        }
        int size2 = this.myCurrentFlow.getSize();
        if (!(psiCodeBlock.getMyParent() instanceof PsiSwitchStatement) && size == size2) {
            emitEmptyInstruction();
        }
        if (psiCodeBlock == this.myCodeFragment) {
            generateCompactConstructorAssignments();
        }
        finishElement(psiCodeBlock);
        if (size != 0) {
            registerSubRange(psiCodeBlock, size);
        }
    }

    private void emitEmptyInstruction() {
        this.myCurrentFlow.addInstruction(EmptyInstruction.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
    public void visitFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(25);
        }
        visitChildren(psiFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitBlockStatement(PsiBlockStatement psiBlockStatement) {
        startElement(psiBlockStatement);
        psiBlockStatement.getCodeBlock().accept(this);
        finishElement(psiBlockStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitBreakStatement(PsiBreakStatement psiBreakStatement) {
        generateYieldInstructions(psiBreakStatement, null, psiBreakStatement.findExitedStatement());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitYieldStatement(PsiYieldStatement psiYieldStatement) {
        generateYieldInstructions(psiYieldStatement, psiYieldStatement.getExpression(), psiYieldStatement.findEnclosingExpression());
    }

    private void generateYieldInstructions(PsiStatement psiStatement, PsiExpression psiExpression, PsiElement psiElement) {
        Instruction goToInstruction;
        startElement(psiStatement);
        generateExpressionInstructions(psiExpression);
        if (psiElement != null) {
            callFinallyBlocksOnExit(psiElement);
            PsiElement psiElementFindEnclosingFinallyBlockElement = findEnclosingFinallyBlockElement(psiStatement, psiElement);
            int startOffset = psiElementFindEnclosingFinallyBlockElement == null ? -1 : this.myCurrentFlow.getStartOffset(psiElementFindEnclosingFinallyBlockElement);
            if (psiElementFindEnclosingFinallyBlockElement != null && startOffset != -1) {
                goToInstruction = new ReturnInstruction(0, (CallInstruction) this.myCurrentFlow.getInstructions().get(startOffset - 2));
            } else {
                goToInstruction = new GoToInstruction(0, BranchingInstruction.Role.END, PsiTreeUtil.isAncestor(psiElement, this.myCodeFragment, true));
            }
            this.myCurrentFlow.addInstruction(goToInstruction);
            addElementOffsetLater(psiElement, false);
        }
        finishElement(psiStatement);
    }

    private void callFinallyBlocksOnExit(PsiElement psiElement) {
        FinallyBlockSubroutine finallyBlockSubroutinePrevious;
        PsiElement element;
        PsiElement myParent;
        Stack<FinallyBlockSubroutine> stack = this.myFinallyBlocks;
        ListIterator<FinallyBlockSubroutine> listIterator = stack.listIterator(stack.size());
        while (listIterator.hasPrevious() && (myParent = (element = (finallyBlockSubroutinePrevious = listIterator.previous()).getElement()).getMyParent()) != null && PsiTreeUtil.isAncestor(psiElement, myParent, false)) {
            CallInstruction callInstruction = new CallInstruction(0, 0);
            finallyBlockSubroutinePrevious.addCall(callInstruction);
            this.myCurrentFlow.addInstruction(callInstruction);
            addElementOffsetLater(element, true);
        }
    }

    private PsiElement findEnclosingFinallyBlockElement(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(26);
        }
        while (psiElement != null && !(psiElement instanceof PsiFile)) {
            if ((psiElement instanceof PsiCodeBlock) && (psiElement.getMyParent() instanceof PsiTryStatement) && ((PsiTryStatement) psiElement.getMyParent()).getFinallyBlock() == psiElement) {
                if (this.myCurrentFlow.getStartOffset(psiElement.getMyParent()) == -1) {
                    return null;
                }
                if (psiElement2 == null || !PsiTreeUtil.isAncestor(psiElement, psiElement2, false)) {
                    return psiElement;
                }
            }
            psiElement = psiElement.getMyParent();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitContinueStatement(PsiContinueStatement psiContinueStatement) {
        Instruction goToInstruction;
        startElement(psiContinueStatement);
        PsiStatement psiStatementFindContinuedStatement = psiContinueStatement.findContinuedStatement();
        if (psiStatementFindContinuedStatement != null) {
            PsiElement body = psiStatementFindContinuedStatement instanceof PsiLoopStatement ? ((PsiLoopStatement) psiStatementFindContinuedStatement).getBody() : null;
            if (body == null) {
                body = this.myCodeFragment;
            }
            callFinallyBlocksOnExit(psiStatementFindContinuedStatement);
            PsiElement psiElementFindEnclosingFinallyBlockElement = findEnclosingFinallyBlockElement(psiContinueStatement, psiStatementFindContinuedStatement);
            int startOffset = psiElementFindEnclosingFinallyBlockElement == null ? -1 : this.myCurrentFlow.getStartOffset(psiElementFindEnclosingFinallyBlockElement);
            if (psiElementFindEnclosingFinallyBlockElement != null && startOffset != -1) {
                goToInstruction = new ReturnInstruction(0, (CallInstruction) this.myCurrentFlow.getInstructions().get(startOffset - 2));
            } else {
                goToInstruction = new GoToInstruction(0, BranchingInstruction.Role.END, PsiTreeUtil.isAncestor(body, this.myCodeFragment, true));
            }
            this.myCurrentFlow.addInstruction(goToInstruction);
            addElementOffsetLater(body, false);
        }
        finishElement(psiContinueStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitDeclarationStatement(PsiDeclarationStatement psiDeclarationStatement) {
        startElement(psiDeclarationStatement);
        int size = this.myCurrentFlow.getSize();
        for (PsiElement psiElement : psiDeclarationStatement.getDeclaredElements()) {
            ProgressManager.checkCanceled();
            if (psiElement instanceof PsiClass) {
                psiElement.accept(this);
            } else if (psiElement instanceof PsiVariable) {
                processVariable((PsiVariable) psiElement);
            }
        }
        if (size == this.myCurrentFlow.getSize()) {
            emitEmptyInstruction();
        }
        finishElement(psiDeclarationStatement);
    }

    private void processVariable(PsiVariable psiVariable) {
        if (psiVariable == null) {
            $$$reportNull$$$0(27);
        }
        PsiExpression initializer = psiVariable.getInitializer();
        generateExpressionInstructions(initializer);
        boolean z = psiVariable instanceof PsiLocalVariable;
        if ((!z || initializer == null) && !(psiVariable instanceof PsiField)) {
            return;
        }
        if (!z || this.myPolicy.isLocalVariableAccepted((PsiLocalVariable) psiVariable)) {
            if (this.myAssignmentTargetsAreElements) {
                startElement(psiVariable);
            }
            generateWriteInstruction(psiVariable);
            if (this.myAssignmentTargetsAreElements) {
                finishElement(psiVariable);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitDoWhileStatement(PsiDoWhileStatement psiDoWhileStatement) {
        startElement(psiDoWhileStatement);
        PsiStatement body = psiDoWhileStatement.getBody();
        this.myStartStatementStack.pushStatement(body == null ? psiDoWhileStatement : body, true);
        this.myEndStatementStack.pushStatement(psiDoWhileStatement, false);
        if (body != null) {
            body.accept(this);
        }
        PsiExpression condition = psiDoWhileStatement.getCondition();
        if (condition != null) {
            condition.accept(this);
        }
        int startOffset = this.myCurrentFlow.getStartOffset(psiDoWhileStatement);
        Object objComputeConstantExpression = this.myConstantEvaluationHelper.computeConstantExpression(psiDoWhileStatement.getCondition());
        if (objComputeConstantExpression instanceof Boolean) {
            if (((Boolean) objComputeConstantExpression).booleanValue()) {
                this.myCurrentFlow.addInstruction(new GoToInstruction(startOffset));
            } else {
                emitEmptyInstruction();
            }
        } else {
            this.myCurrentFlow.addInstruction(new ConditionalGoToInstruction(startOffset, psiDoWhileStatement.getCondition()));
        }
        this.myStartStatementStack.popStatement();
        this.myEndStatementStack.popStatement();
        finishElement(psiDoWhileStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitEmptyStatement(PsiEmptyStatement psiEmptyStatement) {
        startElement(psiEmptyStatement);
        emitEmptyInstruction();
        finishElement(psiEmptyStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitExpressionStatement(PsiExpressionStatement psiExpressionStatement) {
        startElement(psiExpressionStatement);
        psiExpressionStatement.getExpression().accept(this);
        for (PsiParameter psiParameter : this.myCatchParameters) {
            ProgressManager.checkCanceled();
            if (!this.myUnhandledExceptionCatchBlocks.contains(((PsiCatchSection) psiParameter.getMethod()).getCatchBlock())) {
                PsiType type = psiParameter.getType();
                for (PsiType psiType : type instanceof PsiDisjunctionType ? ((PsiDisjunctionType) type).getDisjunctions() : Collections.singletonList(type)) {
                    if (psiType instanceof PsiClassType) {
                        generateThrow((PsiClassType) psiType, psiExpressionStatement);
                    }
                }
            }
        }
        finishElement(psiExpressionStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitExpressionListStatement(PsiExpressionListStatement psiExpressionListStatement) {
        startElement(psiExpressionListStatement);
        for (PsiExpression psiExpression : psiExpressionListStatement.getExpressionList().getExpressions()) {
            ProgressManager.checkCanceled();
            psiExpression.accept(this);
        }
        finishElement(psiExpressionListStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitField(PsiField psiField) {
        PsiExpression initializer = psiField.getInitializer();
        if (initializer != null) {
            startElement(psiField);
            initializer.accept(this);
            finishElement(psiField);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitForStatement(PsiForStatement psiForStatement) {
        int startOffset;
        startElement(psiForStatement);
        PsiStatement body = psiForStatement.getBody();
        this.myStartStatementStack.pushStatement(body == null ? psiForStatement : body, false);
        this.myEndStatementStack.pushStatement(psiForStatement, false);
        PsiStatement initialization = psiForStatement.getInitialization();
        if (initialization != null) {
            initialization.accept(this);
        }
        PsiExpression condition = psiForStatement.getCondition();
        if (condition != null) {
            condition.accept(this);
        }
        Object objComputeConstantExpression = this.myConstantEvaluationHelper.computeConstantExpression(condition);
        if ((objComputeConstantExpression instanceof Boolean) || condition == null) {
            if (condition == null || ((Boolean) objComputeConstantExpression).booleanValue()) {
                emitEmptyInstruction();
            } else {
                this.myCurrentFlow.addInstruction(new GoToInstruction(0));
                addElementOffsetLater(psiForStatement, false);
            }
        } else {
            this.myCurrentFlow.addInstruction(new ConditionalGoToInstruction(0, psiForStatement.getCondition()));
            addElementOffsetLater(psiForStatement, false);
        }
        if (body != null) {
            body.accept(this);
        }
        PsiStatement update = psiForStatement.getUpdate();
        if (update != null) {
            update.accept(this);
        }
        if (initialization != null) {
            startOffset = this.myCurrentFlow.getEndOffset(initialization);
        } else {
            startOffset = this.myCurrentFlow.getStartOffset(psiForStatement);
        }
        this.myCurrentFlow.addInstruction(new GoToInstruction(startOffset));
        this.myStartStatementStack.popStatement();
        this.myEndStatementStack.popStatement();
        finishElement(psiForStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitForeachStatement(PsiForeachStatement psiForeachStatement) {
        startElement(psiForeachStatement);
        PsiStatement body = psiForeachStatement.getBody();
        this.myStartStatementStack.pushStatement(body == null ? psiForeachStatement : body, false);
        this.myEndStatementStack.pushStatement(psiForeachStatement, false);
        PsiExpression iteratedValue = psiForeachStatement.getIteratedValue();
        if (iteratedValue != null) {
            iteratedValue.accept(this);
        }
        int size = this.myCurrentFlow.getSize();
        this.myCurrentFlow.addInstruction(new ConditionalGoToInstruction(0, psiForeachStatement.getIteratedValue()));
        addElementOffsetLater(psiForeachStatement, false);
        PsiParameter iterationParameter = psiForeachStatement.getIterationParameter();
        if (this.myPolicy.isParameterAccepted(iterationParameter)) {
            generateWriteInstruction(iterationParameter);
        }
        if (body != null) {
            body.accept(this);
        }
        this.myCurrentFlow.addInstruction(new GoToInstruction(size));
        this.myStartStatementStack.popStatement();
        this.myEndStatementStack.popStatement();
        finishElement(psiForeachStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitIfStatement(PsiIfStatement psiIfStatement) {
        startElement(psiIfStatement);
        PsiStatement elseBranch = psiIfStatement.getElseBranch();
        generateConditionalStatementInstructions(psiIfStatement, psiIfStatement.getCondition(), psiIfStatement.getThenBranch(), elseBranch);
        finishElement(psiIfStatement);
    }

    /* JADX WARN: Code duplicated, block: B:27:0x005f  */
    private void generateConditionalStatementInstructions(PsiElement psiElement, PsiExpression psiExpression, PsiElement psiElement2, PsiElement psiElement3) {
        boolean zBooleanValue;
        boolean z;
        Instruction goToInstruction;
        if (psiElement == null) {
            $$$reportNull$$$0(28);
        }
        if (psiElement2 == null) {
            this.myStartStatementStack.pushStatement(psiElement, false);
        } else {
            this.myStartStatementStack.pushStatement(psiElement2, true);
        }
        if (psiElement3 == null) {
            this.myEndStatementStack.pushStatement(psiElement, false);
        } else {
            this.myEndStatementStack.pushStatement(psiElement3, true);
        }
        this.myEndJumpRoles.push(psiElement3 == null ? BranchingInstruction.Role.END : BranchingInstruction.Role.ELSE);
        this.myStartJumpRoles.push(psiElement2 == null ? BranchingInstruction.Role.END : BranchingInstruction.Role.THEN);
        if (psiExpression != null) {
            psiExpression.accept(this);
        }
        if (this.myOptions.shouldEvaluateConstantIfCondition()) {
            Object objComputeConstantExpression = this.myConstantEvaluationHelper.computeConstantExpression(psiExpression);
            if (objComputeConstantExpression instanceof Boolean) {
                zBooleanValue = ((Boolean) objComputeConstantExpression).booleanValue();
                this.myCurrentFlow.setConstantConditionOccurred(true);
                z = false;
            } else {
                zBooleanValue = true;
                z = true;
            }
        } else {
            zBooleanValue = true;
            z = true;
        }
        if (z || !zBooleanValue) {
            BranchingInstruction.Role role = psiElement3 == null ? BranchingInstruction.Role.END : BranchingInstruction.Role.ELSE;
            if (z) {
                goToInstruction = new ConditionalGoToInstruction(0, role, psiExpression);
            } else {
                goToInstruction = new GoToInstruction(0, role);
            }
            this.myCurrentFlow.addInstruction(goToInstruction);
            if (psiElement3 == null) {
                addElementOffsetLater(psiElement, false);
            } else {
                addElementOffsetLater(psiElement3, true);
            }
        }
        if (psiElement2 != null) {
            psiElement2.accept(this);
        }
        if (psiElement3 != null) {
            this.myCurrentFlow.addInstruction(new GoToInstruction(0));
            addElementOffsetLater(psiElement, false);
            psiElement3.accept(this);
        }
        this.myStartJumpRoles.pop();
        this.myEndJumpRoles.pop();
        this.myStartStatementStack.popStatement();
        this.myEndStatementStack.popStatement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitLabeledStatement(PsiLabeledStatement psiLabeledStatement) {
        startElement(psiLabeledStatement);
        PsiStatement statement = psiLabeledStatement.getStatement();
        if (statement != null) {
            statement.accept(this);
        }
        finishElement(psiLabeledStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitReturnStatement(PsiReturnStatement psiReturnStatement) {
        startElement(psiReturnStatement);
        PsiExpression returnValue = psiReturnStatement.getReturnValue();
        if (returnValue != null) {
            this.myStartStatementStack.pushStatement(returnValue, false);
            this.myEndStatementStack.pushStatement(returnValue, false);
            returnValue.accept(this);
        }
        addReturnInstruction(psiReturnStatement);
        if (returnValue != null) {
            this.myStartStatementStack.popStatement();
            this.myEndStatementStack.popStatement();
        }
        finishElement(psiReturnStatement);
    }

    private void addReturnInstruction(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(29);
        }
        PsiElement psiElementFindEnclosingFinallyBlockElement = findEnclosingFinallyBlockElement(psiElement, null);
        int startOffset = psiElementFindEnclosingFinallyBlockElement == null ? -1 : this.myCurrentFlow.getStartOffset(psiElementFindEnclosingFinallyBlockElement);
        if (psiElementFindEnclosingFinallyBlockElement != null && startOffset != -1) {
            this.myCurrentFlow.addInstruction(new GoToInstruction(1, BranchingInstruction.Role.END, true));
            addElementOffsetLater(psiElementFindEnclosingFinallyBlockElement, false);
            return;
        }
        GoToInstruction goToInstruction = new GoToInstruction(0, BranchingInstruction.Role.END, true);
        this.myCurrentFlow.addInstruction(goToInstruction);
        if (this.myFinallyBlocks.isEmpty()) {
            addElementOffsetLater(this.myCodeFragment, false);
        } else {
            goToInstruction.offset = -4;
            addElementOffsetLater(this.myFinallyBlocks.peek().getElement(), true);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitSwitchLabelStatement(PsiSwitchLabelStatement psiSwitchLabelStatement) {
        startElement(psiSwitchLabelStatement);
        generateCaseValueInstructions(psiSwitchLabelStatement.getCaseValues());
        finishElement(psiSwitchLabelStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitSwitchLabeledRuleStatement(PsiSwitchLabeledRuleStatement psiSwitchLabeledRuleStatement) {
        startElement(psiSwitchLabeledRuleStatement);
        generateCaseValueInstructions(psiSwitchLabeledRuleStatement.getCaseValues());
        PsiStatement body = psiSwitchLabeledRuleStatement.getBody();
        if (body != null) {
            body.accept(this);
        }
        PsiSwitchBlock enclosingSwitchBlock = psiSwitchLabeledRuleStatement.getEnclosingSwitchBlock();
        if (enclosingSwitchBlock != null) {
            this.myCurrentFlow.addInstruction(new GoToInstruction(0, BranchingInstruction.Role.END, PsiTreeUtil.isAncestor(enclosingSwitchBlock, this.myCodeFragment, true)));
            addElementOffsetLater(enclosingSwitchBlock, false);
        }
        finishElement(psiSwitchLabeledRuleStatement);
    }

    private void generateCaseValueInstructions(PsiExpressionList psiExpressionList) {
        if (psiExpressionList != null) {
            for (PsiExpression psiExpression : psiExpressionList.getExpressions()) {
                ProgressManager.checkCanceled();
                generateExpressionInstructions(psiExpression);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitSwitchStatement(PsiSwitchStatement psiSwitchStatement) {
        generateSwitchBlockInstructions(psiSwitchStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitSwitchExpression(PsiSwitchExpression psiSwitchExpression) {
        generateSwitchBlockInstructions(psiSwitchExpression);
    }

    public void generateSwitchBlockInstructions(PsiSwitchBlock psiSwitchBlock) {
        startElement(psiSwitchBlock);
        PsiExpression expression = psiSwitchBlock.getExpression();
        if (expression != null) {
            expression.accept(this);
        }
        PsiCodeBlock body = psiSwitchBlock.getBody();
        if (body != null) {
            PsiStatement[] statements = body.getStatements();
            PsiType type = expression == null ? null : expression.getType();
            boolean z = false;
            for (PsiStatement psiStatement : statements) {
                ProgressManager.checkCanceled();
                if (psiStatement instanceof PsiSwitchLabelStatementBase) {
                    PsiSwitchLabelStatementBase psiSwitchLabelStatementBase = (PsiSwitchLabelStatementBase) psiStatement;
                    if (psiSwitchLabelStatementBase.isDefaultCase()) {
                        z = true;
                    }
                    PsiCaseLabelElementList caseLabelElementList = psiSwitchLabelStatementBase.getCaseLabelElementList();
                    if (caseLabelElementList != null) {
                        for (PsiCaseLabelElement psiCaseLabelElement : caseLabelElementList.getElements()) {
                            if ((psiCaseLabelElement instanceof PsiDefaultCaseLabelElement) || ((psiCaseLabelElement instanceof PsiPattern) && type != null && JavaPsiPatternUtil.isTotalForType((PsiPattern) psiCaseLabelElement, type))) {
                                z = true;
                                break;
                            }
                        }
                    }
                    this.myCurrentFlow.addInstruction(new ConditionalGoToInstruction(0, expression));
                    addElementOffsetLater(psiStatement, true);
                }
            }
            if (!z) {
                this.myCurrentFlow.addInstruction(new GoToInstruction(0));
                addElementOffsetLater(body, false);
            }
            body.accept(this);
        }
        finishElement(psiSwitchBlock);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitSynchronizedStatement(PsiSynchronizedStatement psiSynchronizedStatement) {
        startElement(psiSynchronizedStatement);
        PsiExpression lockExpression = psiSynchronizedStatement.getLockExpression();
        if (lockExpression != null) {
            lockExpression.accept(this);
        }
        PsiCodeBlock body = psiSynchronizedStatement.getBody();
        if (body != null) {
            body.accept(this);
        }
        finishElement(psiSynchronizedStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitThrowStatement(PsiThrowStatement psiThrowStatement) {
        startElement(psiThrowStatement);
        PsiExpression exception = psiThrowStatement.getException();
        if (exception != null) {
            exception.accept(this);
        }
        addThrowInstructions(findThrowToBlocks(psiThrowStatement));
        finishElement(psiThrowStatement);
    }

    private void addThrowInstructions(List<? extends PsiElement> list) {
        BranchingInstruction conditionalThrowToInstruction;
        if (list == null) {
            $$$reportNull$$$0(30);
        }
        if (list.isEmpty() || list.get(0) == null) {
            ThrowToInstruction throwToInstruction = new ThrowToInstruction(0);
            this.myCurrentFlow.addInstruction(throwToInstruction);
            if (this.myFinallyBlocks.isEmpty()) {
                addElementOffsetLater(this.myCodeFragment, false);
                return;
            } else {
                throwToInstruction.offset = -2;
                addElementOffsetLater(this.myFinallyBlocks.peek().getElement(), true);
                return;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            ProgressManager.checkCanceled();
            PsiElement psiElement = list.get(i);
            if (i == list.size() - 1) {
                conditionalThrowToInstruction = new ThrowToInstruction(0);
            } else {
                conditionalThrowToInstruction = new ConditionalThrowToInstruction(0);
            }
            this.myCurrentFlow.addInstruction(conditionalThrowToInstruction);
            conditionalThrowToInstruction.offset = -1;
            addElementOffsetLater(psiElement, true);
        }
    }

    private List<PsiElement> findThrowToBlocks(PsiThrowStatement psiThrowStatement) {
        if (psiThrowStatement == null) {
            $$$reportNull$$$0(31);
        }
        PsiExpression exception = psiThrowStatement.getException();
        if (exception == null) {
            List<PsiElement> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(32);
            }
            return listEmptyList;
        }
        PsiType type = exception.getType();
        if (type instanceof PsiClassType) {
            return findThrowToBlocks((PsiClassType) type);
        }
        List<PsiElement> listEmptyList2 = Collections.emptyList();
        if (listEmptyList2 == null) {
            $$$reportNull$$$0(33);
        }
        return listEmptyList2;
    }

    private List<PsiElement> findThrowToBlocks(PsiClassType psiClassType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(34);
        }
        ArrayList arrayList = new ArrayList();
        for (int size = this.myCatchParameters.size() - 1; size >= 0; size--) {
            ProgressManager.checkCanceled();
            if (ControlFlowUtil.isCaughtExceptionType(psiClassType, this.myCatchParameters.get(size).getType())) {
                arrayList.add(this.myCatchBlocks.get(size));
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(null);
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0042  */
    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitAssertStatement(PsiAssertStatement psiAssertStatement) {
        boolean z;
        startElement(psiAssertStatement);
        this.myStartStatementStack.pushStatement(psiAssertStatement, false);
        this.myEndStatementStack.pushStatement(psiAssertStatement, false);
        this.myCurrentFlow.addInstruction(new ConditionalGoToInstruction(0, BranchingInstruction.Role.END, null));
        addElementOffsetLater(psiAssertStatement, false);
        PsiExpression assertCondition = psiAssertStatement.getAssertCondition();
        boolean z2 = true;
        if (this.myOptions.shouldEvaluateConstantIfCondition()) {
            Object objComputeConstantExpression = this.myConstantEvaluationHelper.computeConstantExpression(assertCondition);
            if (objComputeConstantExpression instanceof Boolean) {
                boolean zBooleanValue = true ^ ((Boolean) objComputeConstantExpression).booleanValue();
                emitEmptyInstruction();
                z = zBooleanValue;
                z2 = false;
            } else {
                z = true;
            }
        } else {
            z = true;
        }
        if (z2) {
            if (assertCondition != null) {
                this.myStartStatementStack.pushStatement(psiAssertStatement, false);
                this.myEndStatementStack.pushStatement(psiAssertStatement, false);
                this.myEndJumpRoles.push(BranchingInstruction.Role.END);
                this.myStartJumpRoles.push(BranchingInstruction.Role.END);
                assertCondition.accept(this);
                this.myStartJumpRoles.pop();
                this.myEndJumpRoles.pop();
                this.myStartStatementStack.popStatement();
                this.myEndStatementStack.popStatement();
            }
            this.myCurrentFlow.addInstruction(new ConditionalGoToInstruction(0, BranchingInstruction.Role.END, psiAssertStatement.getAssertCondition()));
            addElementOffsetLater(psiAssertStatement, false);
        } else if (!z) {
            this.myCurrentFlow.addInstruction(new GoToInstruction(0, BranchingInstruction.Role.END));
            addElementOffsetLater(psiAssertStatement, false);
        }
        PsiExpression assertDescription = psiAssertStatement.getAssertDescription();
        if (assertDescription != null) {
            assertDescription.accept(this);
        }
        addThrowInstructions(findThrowToBlocks(JavaPsiFacade.getElementFactory(psiAssertStatement.getProject()).createTypeByFQClassName(CommonClassNames.JAVA_LANG_THROWABLE, psiAssertStatement.getResolveScope())));
        this.myStartStatementStack.popStatement();
        this.myEndStatementStack.popStatement();
        finishElement(psiAssertStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitTryStatement(PsiTryStatement psiTryStatement) {
        startElement(psiTryStatement);
        PsiCodeBlock[] catchBlocks = psiTryStatement.getCatchBlocks();
        PsiParameter[] catchBlockParameters = psiTryStatement.getCatchBlockParameters();
        int iMin = Math.min(catchBlocks.length, catchBlockParameters.length);
        FinallyBlockSubroutine finallyBlockSubroutine = null;
        this.myUnhandledExceptionCatchBlocks.push(null);
        int i = iMin - 1;
        int i2 = i;
        while (true) {
            if (i2 < 0) {
                break;
            }
            ProgressManager.checkCanceled();
            this.myCatchParameters.push(catchBlockParameters[i2]);
            this.myCatchBlocks.push(catchBlocks[i2]);
            PsiType type = catchBlockParameters[i2].getType();
            if ((type instanceof PsiClassType) && ExceptionUtil.isUncheckedExceptionOrSuperclass((PsiClassType) type)) {
                this.myUnhandledExceptionCatchBlocks.push(catchBlocks[i2]);
            } else if (type instanceof PsiDisjunctionType) {
                PsiType leastUpperBound = ((PsiDisjunctionType) type).getLeastUpperBound();
                if ((leastUpperBound instanceof PsiClassType) && ExceptionUtil.isUncheckedExceptionOrSuperclass((PsiClassType) leastUpperBound)) {
                    this.myUnhandledExceptionCatchBlocks.push(catchBlocks[i2]);
                } else if (leastUpperBound instanceof PsiIntersectionType) {
                    for (PsiType psiType : ((PsiIntersectionType) leastUpperBound).getConjuncts()) {
                        if ((psiType instanceof PsiClassType) && ExceptionUtil.isUncheckedExceptionOrSuperclass((PsiClassType) psiType)) {
                            this.myUnhandledExceptionCatchBlocks.push(catchBlocks[i2]);
                            break;
                        }
                    }
                }
            }
            i2--;
        }
        PsiCodeBlock finallyBlock = psiTryStatement.getFinallyBlock();
        if (finallyBlock != null) {
            finallyBlockSubroutine = new FinallyBlockSubroutine(finallyBlock);
            this.myFinallyBlocks.push(finallyBlockSubroutine);
        }
        PsiResourceList resourceList = psiTryStatement.getResourceList();
        if (resourceList != null) {
            generateCheckedExceptionJumps(resourceList);
            resourceList.accept(this);
        }
        PsiCodeBlock tryBlock = psiTryStatement.getTryBlock();
        if (tryBlock != null) {
            generateCheckedExceptionJumps(tryBlock);
            tryBlock.accept(this);
        }
        while (this.myUnhandledExceptionCatchBlocks.pop() != null) {
        }
        this.myCurrentFlow.addInstruction(new GoToInstruction(finallyBlock == null ? 0 : -6));
        if (finallyBlock == null) {
            addElementOffsetLater(psiTryStatement, false);
        } else {
            addElementOffsetLater(finallyBlock, true);
        }
        for (int i3 = 0; i3 < iMin; i3++) {
            this.myCatchParameters.pop();
            this.myCatchBlocks.pop();
        }
        while (i >= 0) {
            ProgressManager.checkCanceled();
            if (this.myPolicy.isParameterAccepted(catchBlockParameters[i])) {
                generateWriteInstruction(catchBlockParameters[i]);
            }
            PsiCodeBlock psiCodeBlock = catchBlocks[i];
            if (psiCodeBlock != null) {
                psiCodeBlock.accept(this);
            } else {
                LOG.error("Catch body is null (" + i + ") " + psiTryStatement.getText());
            }
            this.myCurrentFlow.addInstruction(new GoToInstruction(finallyBlock == null ? 0 : -6));
            if (finallyBlock == null) {
                addElementOffsetLater(psiTryStatement, false);
            } else {
                addElementOffsetLater(finallyBlock, true);
            }
            i--;
        }
        if (finallyBlock != null) {
            this.myFinallyBlocks.pop();
        }
        if (finallyBlock != null) {
            CallInstruction callInstruction = new CallInstruction(0, 0);
            finallyBlockSubroutine.addCall(callInstruction);
            this.myCurrentFlow.addInstruction(callInstruction);
            addElementOffsetLater(finallyBlock, true);
            this.myCurrentFlow.addInstruction(new GoToInstruction(0));
            addElementOffsetLater(psiTryStatement, false);
            CallInstruction callInstruction2 = new CallInstruction(0, 0);
            finallyBlockSubroutine.addCall(callInstruction2);
            this.myCurrentFlow.addInstruction(callInstruction2);
            addElementOffsetLater(finallyBlock, true);
            addReturnInstruction(psiTryStatement);
            CallInstruction callInstruction3 = new CallInstruction(0, 0);
            finallyBlockSubroutine.addCall(callInstruction3);
            this.myCurrentFlow.addInstruction(callInstruction3);
            addElementOffsetLater(finallyBlock, true);
            GoToInstruction goToInstruction = new GoToInstruction(0);
            this.myCurrentFlow.addInstruction(goToInstruction);
            addElementOffsetLater(finallyBlock, false);
            finallyBlock.accept(this);
            int startOffset = this.myCurrentFlow.getStartOffset(finallyBlock);
            int endOffset = this.myCurrentFlow.getEndOffset(finallyBlock);
            for (CallInstruction callInstruction4 : finallyBlockSubroutine.getCalls()) {
                callInstruction4.procBegin = startOffset;
                callInstruction4.procEnd = endOffset;
            }
            this.myCurrentFlow.addInstruction(new ReturnInstruction(0, callInstruction));
            this.myCurrentFlow.addInstruction(new ReturnInstruction(startOffset - 3, callInstruction2));
            int i4 = startOffset - 1;
            this.myCurrentFlow.addInstruction(new ReturnInstruction(i4, callInstruction3));
            List<PsiElement> listRemove = this.finallyBlockToUnhandledExceptions.remove(finallyBlock);
            for (int i5 = 0; listRemove != null && i5 < listRemove.size(); i5++) {
                ProgressManager.checkCanceled();
                PsiElement psiElement = listRemove.get(i5);
                ReturnInstruction returnInstruction = new ReturnInstruction(0, callInstruction3);
                returnInstruction.setRethrowFromFinally();
                this.myCurrentFlow.addInstruction(returnInstruction);
                if (psiElement == null) {
                    returnInstruction.offset = i4;
                } else {
                    returnInstruction.offset--;
                    addElementOffsetLater(psiElement, true);
                }
            }
            goToInstruction.offset = this.myCurrentFlow.getSize();
            generateUncheckedExceptionJumps(psiTryStatement, false);
            this.myCurrentFlow.addInstruction(new ThrowToInstruction(0));
            addElementOffsetLater(this.myCodeFragment, false);
        }
        finishElement(psiTryStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitResourceList(PsiResourceList psiResourceList) {
        startElement(psiResourceList);
        for (PsiResourceListElement psiResourceListElement : psiResourceList) {
            ProgressManager.checkCanceled();
            if (psiResourceListElement instanceof PsiResourceVariable) {
                processVariable((PsiVariable) psiResourceListElement);
            } else if (psiResourceListElement instanceof PsiResourceExpression) {
                ((PsiResourceExpression) psiResourceListElement).getExpression().accept(this);
            }
        }
        finishElement(psiResourceList);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitWhileStatement(PsiWhileStatement psiWhileStatement) {
        startElement(psiWhileStatement);
        PsiStatement body = psiWhileStatement.getBody();
        if (body == null) {
            this.myStartStatementStack.pushStatement(psiWhileStatement, false);
        } else {
            this.myStartStatementStack.pushStatement(body, true);
        }
        this.myEndStatementStack.pushStatement(psiWhileStatement, false);
        PsiExpression condition = psiWhileStatement.getCondition();
        if (condition != null) {
            condition.accept(this);
        }
        Object objComputeConstantExpression = this.myConstantEvaluationHelper.computeConstantExpression(psiWhileStatement.getCondition());
        if (objComputeConstantExpression instanceof Boolean) {
            if (((Boolean) objComputeConstantExpression).booleanValue()) {
                emitEmptyInstruction();
            } else {
                this.myCurrentFlow.addInstruction(new GoToInstruction(0));
                addElementOffsetLater(psiWhileStatement, false);
            }
        } else {
            this.myCurrentFlow.addInstruction(new ConditionalGoToInstruction(0, psiWhileStatement.getCondition()));
            addElementOffsetLater(psiWhileStatement, false);
        }
        if (body != null) {
            body.accept(this);
        }
        this.myCurrentFlow.addInstruction(new GoToInstruction(this.myCurrentFlow.getStartOffset(psiWhileStatement)));
        this.myStartStatementStack.popStatement();
        this.myEndStatementStack.popStatement();
        finishElement(psiWhileStatement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitExpressionList(PsiExpressionList psiExpressionList) {
        for (PsiExpression psiExpression : psiExpressionList.getExpressions()) {
            ProgressManager.checkCanceled();
            generateExpressionInstructions(psiExpression);
        }
    }

    private void generateExpressionInstructions(PsiExpression psiExpression) {
        if (psiExpression != null) {
            this.myStartStatementStack.pushStatement(psiExpression, false);
            this.myEndStatementStack.pushStatement(psiExpression, false);
            psiExpression.accept(this);
            this.myStartStatementStack.popStatement();
            this.myEndStatementStack.popStatement();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitArrayAccessExpression(PsiArrayAccessExpression psiArrayAccessExpression) {
        startElement(psiArrayAccessExpression);
        psiArrayAccessExpression.getArrayExpression().accept(this);
        PsiExpression indexExpression = psiArrayAccessExpression.getIndexExpression();
        if (indexExpression != null) {
            indexExpression.accept(this);
        }
        finishElement(psiArrayAccessExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitArrayInitializerExpression(PsiArrayInitializerExpression psiArrayInitializerExpression) {
        startElement(psiArrayInitializerExpression);
        for (PsiExpression psiExpression : psiArrayInitializerExpression.getInitializers()) {
            ProgressManager.checkCanceled();
            psiExpression.accept(this);
        }
        finishElement(psiArrayInitializerExpression);
    }

    /* JADX WARN: Code duplicated, block: B:48:0x00ae A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:49:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:51:0x00b5  */
    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitAssignmentExpression(PsiAssignmentExpression psiAssignmentExpression) {
        startElement(psiAssignmentExpression);
        PsiExpression rExpression = psiAssignmentExpression.getRExpression();
        this.myStartStatementStack.pushStatement(rExpression == null ? psiAssignmentExpression : rExpression, false);
        this.myEndStatementStack.pushStatement(rExpression == null ? psiAssignmentExpression : rExpression, false);
        PsiExpression psiExpressionSkipParenthesizedExprDown = PsiUtil.skipParenthesizedExprDown(psiAssignmentExpression.getLExpression());
        if (psiExpressionSkipParenthesizedExprDown instanceof PsiReferenceExpression) {
            if (!this.myImplicitCompactConstructorAssignments.isEmpty()) {
                PsiElement psiElementResolve = ((PsiReferenceExpression) psiExpressionSkipParenthesizedExprDown).resolve();
                if (psiElementResolve instanceof PsiField) {
                    this.myImplicitCompactConstructorAssignments.remove(psiElementResolve);
                }
            }
            PsiReferenceExpression psiReferenceExpression = (PsiReferenceExpression) psiExpressionSkipParenthesizedExprDown;
            PsiVariable usedVariable = getUsedVariable(psiReferenceExpression);
            if (usedVariable != null) {
                if (this.myAssignmentTargetsAreElements) {
                    startElement(psiExpressionSkipParenthesizedExprDown);
                }
                PsiExpression qualifierExpression = psiReferenceExpression.getQualifierExpression();
                if (qualifierExpression != null) {
                    qualifierExpression.accept(this);
                }
                if (psiAssignmentExpression.getOperationTokenType() != JavaTokenType.EQ) {
                    generateReadInstruction(usedVariable);
                }
                if (rExpression != null) {
                    rExpression.accept(this);
                }
                generateWriteInstruction(usedVariable);
                if (this.myAssignmentTargetsAreElements) {
                    finishElement(psiExpressionSkipParenthesizedExprDown);
                }
            } else {
                if (rExpression != null) {
                    rExpression.accept(this);
                }
                psiExpressionSkipParenthesizedExprDown.accept(this);
            }
            this.myStartStatementStack.popStatement();
            this.myEndStatementStack.popStatement();
            finishElement(psiAssignmentExpression);
        }
        if (psiExpressionSkipParenthesizedExprDown instanceof PsiArrayAccessExpression) {
            PsiArrayAccessExpression psiArrayAccessExpression = (PsiArrayAccessExpression) psiExpressionSkipParenthesizedExprDown;
            if (psiArrayAccessExpression.getArrayExpression() instanceof PsiReferenceExpression) {
                PsiVariable usedVariable2 = getUsedVariable((PsiReferenceExpression) psiArrayAccessExpression.getArrayExpression());
                if (usedVariable2 != null) {
                    generateReadInstruction(usedVariable2);
                    PsiExpression indexExpression = psiArrayAccessExpression.getIndexExpression();
                    if (indexExpression != null) {
                        indexExpression.accept(this);
                    }
                } else {
                    psiExpressionSkipParenthesizedExprDown.accept(this);
                }
                if (rExpression != null) {
                    rExpression.accept(this);
                }
            } else if (psiExpressionSkipParenthesizedExprDown != null) {
                psiExpressionSkipParenthesizedExprDown.accept(this);
                if (rExpression != null) {
                    rExpression.accept(this);
                }
            }
        } else if (psiExpressionSkipParenthesizedExprDown != null) {
            psiExpressionSkipParenthesizedExprDown.accept(this);
            if (rExpression != null) {
                rExpression.accept(this);
            }
        }
        emitEmptyInstruction();
        this.myStartStatementStack.popStatement();
        this.myEndStatementStack.popStatement();
        finishElement(psiAssignmentExpression);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x004d  */
    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitPolyadicExpression(PsiPolyadicExpression psiPolyadicExpression) {
        Shortcut shortcut;
        startElement(psiPolyadicExpression);
        IElementType operationTokenType = psiPolyadicExpression.getOperationTokenType();
        boolean z = operationTokenType == JavaTokenType.ANDAND;
        boolean z2 = operationTokenType == JavaTokenType.OROR;
        PsiExpression[] operands = psiPolyadicExpression.getOperands();
        Boolean boolValueOf = Boolean.valueOf(z);
        int i = 0;
        PsiExpression psiExpression = null;
        Boolean bool = null;
        while (i < operands.length) {
            PsiExpression psiExpression2 = operands[i];
            if ((z || z2) && this.myOptions.enableShortCircuit()) {
                Object objComputeConstantExpression = this.myConstantEvaluationHelper.computeConstantExpression(psiExpression2);
                if (objComputeConstantExpression instanceof Boolean) {
                    this.myCurrentFlow.setConstantConditionOccurred(true);
                    if (shouldCalculateConstantExpression(psiPolyadicExpression)) {
                        bool = (Boolean) objComputeConstantExpression;
                    } else {
                        bool = null;
                    }
                } else {
                    bool = null;
                }
                BranchingInstruction.Role rolePeek = (z ? this.myEndJumpRoles : this.myStartJumpRoles).peek();
                PsiElement psiElementPeekElement = (z ? this.myEndStatementStack : this.myStartStatementStack).peekElement();
                boolean zPeekAtStart = (z ? this.myEndStatementStack : this.myStartStatementStack).peekAtStart();
                if (boolValueOf != null) {
                    shortcut = boolValueOf.booleanValue() == z2 ? Shortcut.STOP_EXPRESSION : Shortcut.SKIP_CURRENT_OPERAND;
                } else if (bool != null && bool.booleanValue() == z2) {
                    shortcut = Shortcut.STOP_EXPRESSION;
                } else {
                    shortcut = Shortcut.NO_SHORTCUT;
                }
                int i2 = AnonymousClass1.$SwitchMap$com$intellij$psi$controlFlow$ControlFlowAnalyzer$Shortcut[shortcut.ordinal()];
                if (i2 == 1) {
                    this.myCurrentFlow.addInstruction(new ConditionalGoToInstruction(0, rolePeek, psiExpression));
                    addElementOffsetLater(psiElementPeekElement, zPeekAtStart);
                } else if (i2 == 2) {
                    this.myCurrentFlow.addInstruction(new GoToInstruction(0, rolePeek));
                    addElementOffsetLater(psiElementPeekElement, zPeekAtStart);
                    bool = null;
                }
            }
            generateLOperand(psiExpression2, i == operands.length - 1 ? null : operands[i + 1], operationTokenType);
            i++;
            boolValueOf = bool;
            psiExpression = psiExpression2;
        }
        finishElement(psiPolyadicExpression);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowAnalyzer$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$controlFlow$ControlFlowAnalyzer$Shortcut;

        static {
            int[] iArr = new int[Shortcut.values().length];
            $SwitchMap$com$intellij$psi$controlFlow$ControlFlowAnalyzer$Shortcut = iArr;
            try {
                iArr[Shortcut.NO_SHORTCUT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$controlFlow$ControlFlowAnalyzer$Shortcut[Shortcut.STOP_EXPRESSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$controlFlow$ControlFlowAnalyzer$Shortcut[Shortcut.SKIP_CURRENT_OPERAND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void generateLOperand(PsiExpression psiExpression, PsiExpression psiExpression2, IElementType iElementType) {
        if (psiExpression == null) {
            $$$reportNull$$$0(36);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(37);
        }
        if (psiExpression2 != null) {
            this.myStartJumpRoles.push(BranchingInstruction.Role.END);
            this.myEndJumpRoles.push(BranchingInstruction.Role.END);
            this.myStartStatementStack.pushStatement(iElementType == JavaTokenType.OROR ? this.myStartStatementStack.peekElement() : psiExpression2, iElementType != JavaTokenType.OROR || this.myStartStatementStack.peekAtStart());
            this.myEndStatementStack.pushStatement(iElementType == JavaTokenType.ANDAND ? this.myEndStatementStack.peekElement() : psiExpression2, iElementType != JavaTokenType.ANDAND || this.myEndStatementStack.peekAtStart());
        }
        psiExpression.accept(this);
        if (psiExpression2 != null) {
            this.myStartStatementStack.popStatement();
            this.myEndStatementStack.popStatement();
            this.myStartJumpRoles.pop();
            this.myEndJumpRoles.pop();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [org.jetbrains.kotlin.com.intellij.psi.PsiExpression] */
    /* JADX WARN: Type inference failed for: r2v1, types: [org.jetbrains.kotlin.com.intellij.psi.PsiElement] */
    /* JADX WARN: Type inference failed for: r2v3 */
    private static boolean isInsideIfCondition(PsiExpression psiExpression) {
        if (psiExpression == 0) {
            $$$reportNull$$$0(38);
        }
        while (psiExpression instanceof PsiExpression) {
            PsiElement myParent = psiExpression.getMyParent();
            if ((myParent instanceof PsiIfStatement) && psiExpression == ((PsiIfStatement) myParent).getCondition()) {
                return true;
            }
            psiExpression = myParent;
        }
        return false;
    }

    private boolean shouldCalculateConstantExpression(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(39);
        }
        return this.myOptions.shouldEvaluateConstantIfCondition() || !isInsideIfCondition(psiExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitClassObjectAccessExpression(PsiClassObjectAccessExpression psiClassObjectAccessExpression) {
        visitChildren(psiClassObjectAccessExpression);
    }

    private void visitChildren(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(40);
        }
        startElement(psiElement);
        for (PsiElement psiElement2 : psiElement.getChildren()) {
            ProgressManager.checkCanceled();
            psiElement2.accept(this);
        }
        finishElement(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitConditionalExpression(PsiConditionalExpression psiConditionalExpression) {
        startElement(psiConditionalExpression);
        generateConditionalStatementInstructions(psiConditionalExpression, psiConditionalExpression.getCondition(), psiConditionalExpression.getThenExpression(), psiConditionalExpression.getElseExpression());
        finishElement(psiConditionalExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitInstanceOfExpression(PsiInstanceOfExpression psiInstanceOfExpression) {
        PsiPatternVariable patternVariable;
        startElement(psiInstanceOfExpression);
        psiInstanceOfExpression.getOperand().accept(this);
        PsiPrimaryPattern pattern = psiInstanceOfExpression.getPattern();
        if ((pattern instanceof PsiTypeTestPattern) && (patternVariable = ((PsiTypeTestPattern) pattern).getPatternVariable()) != null) {
            this.myCurrentFlow.addInstruction(new WriteVariableInstruction(patternVariable));
        }
        finishElement(psiInstanceOfExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitLiteralExpression(PsiLiteralExpression psiLiteralExpression) {
        startElement(psiLiteralExpression);
        finishElement(psiLiteralExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitLambdaExpression(PsiLambdaExpression psiLambdaExpression) {
        startElement(psiLambdaExpression);
        PsiElement body = psiLambdaExpression.getBody();
        if (body != null) {
            ArrayList arrayList = new ArrayList();
            addUsedVariables(arrayList, body);
            for (PsiVariable psiVariable : arrayList) {
                ProgressManager.checkCanceled();
                generateReadInstruction(psiVariable);
            }
        }
        finishElement(psiLambdaExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitMethodCallExpression(PsiMethodCallExpression psiMethodCallExpression) {
        PsiExpression qualifierExpression;
        ArrayDeque<PsiMethodCallExpression> arrayDeque = new ArrayDeque();
        while (true) {
            arrayDeque.addFirst(psiMethodCallExpression);
            startElement(psiMethodCallExpression);
            qualifierExpression = psiMethodCallExpression.getMethodExpression().getQualifierExpression();
            PsiMethodCallExpression psiMethodCallExpression2 = (PsiMethodCallExpression) ObjectUtils.tryCast(PsiUtil.skipParenthesizedExprDown(qualifierExpression), PsiMethodCallExpression.class);
            if (psiMethodCallExpression2 == null) {
                break;
            } else {
                psiMethodCallExpression = psiMethodCallExpression2;
            }
        }
        if (qualifierExpression != null) {
            qualifierExpression.accept(this);
        }
        for (PsiMethodCallExpression psiMethodCallExpression3 : arrayDeque) {
            psiMethodCallExpression3.getArgumentList().accept(this);
            emitEmptyInstruction();
            generateExceptionJumps(psiMethodCallExpression3, ExceptionUtil.getUnhandledExceptions(psiMethodCallExpression3, psiMethodCallExpression3.getMyParent()));
            finishElement(psiMethodCallExpression3);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitNewExpression(PsiNewExpression psiNewExpression) {
        startElement(psiNewExpression);
        int size = this.myCurrentFlow.getSize();
        for (PsiElement psiElement : psiNewExpression.getChildren()) {
            ProgressManager.checkCanceled();
            psiElement.accept(this);
        }
        generateExceptionJumps(psiNewExpression, ExceptionUtil.getUnhandledExceptions(psiNewExpression, psiNewExpression.getMyParent()));
        if (size == this.myCurrentFlow.getSize()) {
            emitEmptyInstruction();
        }
        finishElement(psiNewExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitParenthesizedExpression(PsiParenthesizedExpression psiParenthesizedExpression) {
        visitChildren(psiParenthesizedExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitPostfixExpression(PsiPostfixExpression psiPostfixExpression) {
        PsiVariable usedVariable;
        startElement(psiPostfixExpression);
        IElementType operationTokenType = psiPostfixExpression.getOperationTokenType();
        PsiExpression psiExpressionSkipParenthesizedExprDown = PsiUtil.skipParenthesizedExprDown(psiPostfixExpression.getOperand());
        if (psiExpressionSkipParenthesizedExprDown != null) {
            psiExpressionSkipParenthesizedExprDown.accept(this);
            if ((operationTokenType == JavaTokenType.PLUSPLUS || operationTokenType == JavaTokenType.MINUSMINUS) && (psiExpressionSkipParenthesizedExprDown instanceof PsiReferenceExpression) && (usedVariable = getUsedVariable((PsiReferenceExpression) psiExpressionSkipParenthesizedExprDown)) != null) {
                generateWriteInstruction(usedVariable);
            }
        }
        finishElement(psiPostfixExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitPrefixExpression(PsiPrefixExpression psiPrefixExpression) {
        PsiVariable usedVariable;
        startElement(psiPrefixExpression);
        PsiExpression psiExpressionSkipParenthesizedExprDown = PsiUtil.skipParenthesizedExprDown(psiPrefixExpression.getOperand());
        if (psiExpressionSkipParenthesizedExprDown != null) {
            IElementType operationTokenType = psiPrefixExpression.getOperationTokenType();
            if (operationTokenType == JavaTokenType.EXCL) {
                PsiElement psiElementPeekElement = this.myStartStatementStack.peekElement();
                boolean zPeekAtStart = this.myStartStatementStack.peekAtStart();
                this.myStartStatementStack.pushStatement(this.myEndStatementStack.peekElement(), this.myEndStatementStack.peekAtStart());
                this.myEndStatementStack.pushStatement(psiElementPeekElement, zPeekAtStart);
            }
            psiExpressionSkipParenthesizedExprDown.accept(this);
            if (operationTokenType == JavaTokenType.EXCL) {
                this.myStartStatementStack.popStatement();
                this.myEndStatementStack.popStatement();
            }
            if ((psiExpressionSkipParenthesizedExprDown instanceof PsiReferenceExpression) && ((operationTokenType == JavaTokenType.PLUSPLUS || operationTokenType == JavaTokenType.MINUSMINUS) && (usedVariable = getUsedVariable((PsiReferenceExpression) psiExpressionSkipParenthesizedExprDown)) != null)) {
                generateWriteInstruction(usedVariable);
            }
        }
        finishElement(psiPrefixExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
        startElement(psiReferenceExpression);
        PsiExpression qualifierExpression = psiReferenceExpression.getQualifierExpression();
        if (qualifierExpression != null) {
            qualifierExpression.accept(this);
        }
        PsiVariable usedVariable = getUsedVariable(psiReferenceExpression);
        if (usedVariable != null) {
            generateReadInstruction(usedVariable);
        }
        finishElement(psiReferenceExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitSuperExpression(PsiSuperExpression psiSuperExpression) {
        startElement(psiSuperExpression);
        finishElement(psiSuperExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitThisExpression(PsiThisExpression psiThisExpression) {
        startElement(psiThisExpression);
        finishElement(psiThisExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitTypeCastExpression(PsiTypeCastExpression psiTypeCastExpression) {
        startElement(psiTypeCastExpression);
        PsiExpression operand = psiTypeCastExpression.getOperand();
        if (operand != null) {
            operand.accept(this);
        }
        finishElement(psiTypeCastExpression);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitClass(PsiClass psiClass) {
        startElement(psiClass);
        ArrayList arrayList = new ArrayList();
        if (psiClass instanceof PsiAnonymousClass) {
            PsiElement childOfType = PsiTreeUtil.getChildOfType(psiClass, PsiExpressionList.class);
            if (childOfType != null) {
                childOfType.accept(this);
            }
            for (PsiElement nextSibling = childOfType != null ? childOfType.getNextSibling() : psiClass.getFirstChild(); nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
                addUsedVariables(arrayList, nextSibling);
            }
        } else {
            addUsedVariables(arrayList, psiClass);
        }
        for (PsiVariable psiVariable : arrayList) {
            ProgressManager.checkCanceled();
            generateReadInstruction(psiVariable);
        }
        finishElement(psiClass);
    }

    private void addUsedVariables(List<? super PsiVariable> list, PsiElement psiElement) {
        PsiVariable usedVariable;
        if (list == null) {
            $$$reportNull$$$0(41);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(42);
        }
        if ((psiElement instanceof PsiReferenceExpression) && (usedVariable = getUsedVariable((PsiReferenceExpression) psiElement)) != null && !list.contains(usedVariable)) {
            list.add(usedVariable);
        }
        for (PsiElement psiElement2 : psiElement.getChildren()) {
            ProgressManager.checkCanceled();
            addUsedVariables(list, psiElement2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void generateReadInstruction(PsiVariable psiVariable) {
        if (psiVariable == null) {
            $$$reportNull$$$0(43);
        }
        this.myCurrentFlow.addInstruction(new ReadVariableInstruction(psiVariable));
    }

    private void generateWriteInstruction(PsiVariable psiVariable) {
        if (psiVariable == null) {
            $$$reportNull$$$0(44);
        }
        this.myCurrentFlow.addInstruction(new WriteVariableInstruction(psiVariable));
    }

    private PsiVariable getUsedVariable(PsiReferenceExpression psiReferenceExpression) {
        if (psiReferenceExpression == null) {
            $$$reportNull$$$0(45);
        }
        if (psiReferenceExpression.getMyParent() instanceof PsiMethodCallExpression) {
            return null;
        }
        return this.myPolicy.getUsedVariable(psiReferenceExpression);
    }

    private static class FinallyBlockSubroutine {
        private final List<CallInstruction> myCalls;
        private final PsiElement myElement;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
            if (i == 1 || i == 2) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowAnalyzer$FinallyBlockSubroutine";
            } else if (i != 3) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else {
                objArr[0] = "callInstruction";
            }
            if (i == 1) {
                objArr[1] = "getElement";
            } else if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowAnalyzer$FinallyBlockSubroutine";
            } else {
                objArr[1] = "getCalls";
            }
            if (i != 1 && i != 2) {
                if (i != 3) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "addCall";
                }
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        FinallyBlockSubroutine(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(0);
            }
            this.myElement = psiElement;
            this.myCalls = new ArrayList();
        }

        public PsiElement getElement() {
            PsiElement psiElement = this.myElement;
            if (psiElement == null) {
                $$$reportNull$$$0(1);
            }
            return psiElement;
        }

        public List<CallInstruction> getCalls() {
            List<CallInstruction> list = this.myCalls;
            if (list == null) {
                $$$reportNull$$$0(2);
            }
            return list;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addCall(CallInstruction callInstruction) {
            if (callInstruction == null) {
                $$$reportNull$$$0(3);
            }
            this.myCalls.add(callInstruction);
        }
    }

    private static final class SubRangeInfo {
        final PsiElement myElement;
        final int myEnd;
        final int myStart;

        /* synthetic */ SubRangeInfo(PsiElement psiElement, int i, int i2, AnonymousClass1 anonymousClass1) {
            this(psiElement, i, i2);
        }

        private SubRangeInfo(PsiElement psiElement, int i, int i2) {
            this.myElement = psiElement;
            this.myStart = i;
            this.myEnd = i2;
        }
    }
}
