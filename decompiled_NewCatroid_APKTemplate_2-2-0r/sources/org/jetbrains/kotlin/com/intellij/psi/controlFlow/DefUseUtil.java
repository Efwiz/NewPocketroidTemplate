package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import com.beust.jcommander.Parameters;
import com.reandroid.apkeditor.refactor.RefactorUtil;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArrayList;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList;

/* JADX INFO: loaded from: classes3.dex */
public final class DefUseUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) DefUseUtil.class);
    private static final ControlFlowPolicy ourPolicy = new ControlFlowPolicy() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.3
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "psiParameter";
            } else if (i != 2) {
                objArr[0] = "refExpr";
            } else {
                objArr[0] = "psiVariable";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$3";
            if (i == 1) {
                objArr[2] = "isParameterAccepted";
            } else if (i != 2) {
                objArr[2] = "getUsedVariable";
            } else {
                objArr[2] = "isLocalVariableAccepted";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
        public boolean isLocalVariableAccepted(PsiLocalVariable psiLocalVariable) {
            if (psiLocalVariable != null) {
                return true;
            }
            $$$reportNull$$$0(2);
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
        public boolean isParameterAccepted(PsiParameter psiParameter) {
            if (psiParameter == null) {
                $$$reportNull$$$0(1);
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
        public PsiVariable getUsedVariable(PsiReferenceExpression psiReferenceExpression) {
            if (psiReferenceExpression == null) {
                $$$reportNull$$$0(0);
            }
            if (psiReferenceExpression.isQualified()) {
                return null;
            }
            PsiElement psiElementResolve = psiReferenceExpression.resolve();
            if ((psiElementResolve instanceof PsiLocalVariable) || (psiElementResolve instanceof PsiParameter)) {
                return (PsiVariable) psiElementResolve;
            }
            return null;
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 7 || i == 11 || i == 16) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 7 || i == 11 || i == 16) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 9:
            case 13:
                objArr[0] = "def";
                break;
            case 2:
            case 5:
            case 10:
            case 14:
                objArr[0] = "ref";
                break;
            case 3:
            case 8:
            case 12:
            default:
                objArr[0] = "body";
                break;
            case 6:
            case 7:
            case 11:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil";
                break;
            case 15:
                objArr[0] = "instructions";
                break;
        }
        if (i == 6 || i == 7) {
            objArr[1] = "getDefs";
        } else if (i == 11) {
            objArr[1] = "getRefs";
        } else if (i != 16) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil";
        } else {
            objArr[1] = "getBackwardTraces";
        }
        switch (i) {
            case 6:
            case 7:
            case 11:
            case 16:
                break;
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
                objArr[2] = "getRefs";
                break;
            case 15:
                objArr[2] = "getBackwardTraces";
                break;
            default:
                objArr[2] = "getDefs";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7 && i != 11 && i != 16) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private DefUseUtil() {
    }

    public static class Info {
        private final PsiElement myContext;
        private final boolean myIsRead;
        private final PsiVariable myVariable;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "context";
            } else if (i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$Info";
            } else {
                objArr[0] = "variable";
            }
            if (i == 2) {
                objArr[1] = "getVariable";
            } else if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$Info";
            } else {
                objArr[1] = "getContext";
            }
            if (i != 2 && i != 3) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        public Info(PsiVariable psiVariable, PsiElement psiElement, boolean z) {
            if (psiVariable == null) {
                $$$reportNull$$$0(0);
            }
            if (psiElement == null) {
                $$$reportNull$$$0(1);
            }
            this.myVariable = psiVariable;
            this.myContext = psiElement;
            this.myIsRead = z;
        }

        public PsiVariable getVariable() {
            PsiVariable psiVariable = this.myVariable;
            if (psiVariable == null) {
                $$$reportNull$$$0(2);
            }
            return psiVariable;
        }

        public PsiElement getContext() {
            PsiElement psiElement = this.myContext;
            if (psiElement == null) {
                $$$reportNull$$$0(3);
            }
            return psiElement;
        }

        public boolean isRead() {
            return this.myIsRead;
        }
    }

    private static class InstructionState implements Comparable<InstructionState> {
        private final List<InstructionKey> myBackwardTraces;
        private final InstructionKey myInstructionKey;
        private boolean myIsVisited;
        private Set<PsiVariable> myUsed;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
            if (i == 1) {
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
            } else if (i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$InstructionState";
            } else if (i == 4) {
                objArr[0] = "psiVariable";
            } else if (i != 5) {
                objArr[0] = "instructionKey";
            } else {
                objArr[0] = "other";
            }
            if (i == 2) {
                objArr[1] = "getBackwardTraces";
            } else if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$InstructionState";
            } else {
                objArr[1] = "getInstructionKey";
            }
            if (i == 1) {
                objArr[2] = "addBackwardTrace";
            } else if (i != 2 && i != 3) {
                if (i == 4) {
                    objArr[2] = "addUsed";
                } else if (i != 5) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "compareTo";
                }
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        InstructionState(InstructionKey instructionKey) {
            if (instructionKey == null) {
                $$$reportNull$$$0(0);
            }
            this.myInstructionKey = instructionKey;
            this.myBackwardTraces = new ArrayList(2);
            this.myUsed = null;
        }

        void addBackwardTrace(InstructionKey instructionKey) {
            if (instructionKey == null) {
                $$$reportNull$$$0(1);
            }
            this.myBackwardTraces.add(instructionKey);
        }

        List<InstructionKey> getBackwardTraces() {
            List<InstructionKey> list = this.myBackwardTraces;
            if (list == null) {
                $$$reportNull$$$0(2);
            }
            return list;
        }

        InstructionKey getInstructionKey() {
            InstructionKey instructionKey = this.myInstructionKey;
            if (instructionKey == null) {
                $$$reportNull$$$0(3);
            }
            return instructionKey;
        }

        void addUsed(PsiVariable psiVariable) {
            if (psiVariable == null) {
                $$$reportNull$$$0(4);
            }
            touch();
            this.myUsed.add(psiVariable);
        }

        boolean removeUsed(PsiVariable psiVariable) {
            touch();
            return this.myUsed.remove(psiVariable);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void touch() {
            if (this.myUsed == null) {
                this.myUsed = new HashSet();
            }
        }

        void addUsedFrom(InstructionState instructionState) {
            touch();
            this.myUsed.addAll(instructionState.myUsed);
        }

        public boolean contains(InstructionState instructionState) {
            Set<PsiVariable> set;
            Set<PsiVariable> set2 = this.myUsed;
            return (set2 == null || (set = instructionState.myUsed) == null || !set2.containsAll(set)) ? false : true;
        }

        void markVisited() {
            this.myIsVisited = true;
        }

        public boolean isVisited() {
            return this.myIsVisited;
        }

        @Override // java.lang.Comparable
        public int compareTo(InstructionState instructionState) {
            if (instructionState == null) {
                $$$reportNull$$$0(5);
            }
            return this.myInstructionKey.compareTo(instructionState.myInstructionKey);
        }

        public String toString() {
            StringBuilder sbAppend = new StringBuilder().append(this.myInstructionKey).append(" ").append(this.myBackwardTraces).append(this.myIsVisited ? "(v)" : "(n)").append(" ");
            Object obj = this.myUsed;
            if (obj == null) {
                obj = Parameters.DEFAULT_OPTION_PREFIXES;
            }
            return sbAppend.append(obj).toString();
        }
    }

    public static List<Info> getUnusedDefs(PsiCodeBlock psiCodeBlock, Set<? super PsiVariable> set) {
        if (psiCodeBlock == null) {
            return null;
        }
        try {
            ControlFlow controlFlow = ControlFlowFactory.getControlFlow(psiCodeBlock, ourPolicy, ControlFlowOptions.create(true, false, false));
            List<Instruction> instructions = controlFlow.getInstructions();
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug(controlFlow.toString());
            }
            HashSet<PsiVariable> hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            for (int i = 0; i < instructions.size(); i++) {
                Instruction instruction = instructions.get(i);
                ProgressManager.checkCanceled();
                if (instruction instanceof WriteVariableInstruction) {
                    PsiElement parentOfType = PsiTreeUtil.getParentOfType(controlFlow.getElement(i), PsiStatement.class, false);
                    PsiVariable psiVariable = ((WriteVariableInstruction) instruction).variable;
                    if (parentOfType != null && (!(parentOfType instanceof PsiDeclarationStatement) || psiVariable.getInitializer() != null)) {
                        hashSet.add(psiVariable);
                    }
                } else if (instruction instanceof ReadVariableInstruction) {
                    hashSet2.add(((ReadVariableInstruction) instruction).variable);
                }
            }
            try {
                Map<InstructionKey, InstructionState> states = InstructionStateWalker.getStates(instructions);
                InstructionState[] instructionStateArr = (InstructionState[]) states.values().toArray(new InstructionState[0]);
                Arrays.sort(instructionStateArr);
                BitSet bitSet = new BitSet(instructions.size());
                ArrayDeque arrayDeque = new ArrayDeque(8);
                for (int length = instructionStateArr.length - 1; length >= 0; length--) {
                    InstructionState instructionState = instructionStateArr[length];
                    if (!instructionState.isVisited()) {
                        instructionState.touch();
                        for (PsiVariable psiVariable2 : hashSet) {
                            if (psiVariable2 instanceof PsiField) {
                                instructionState.addUsed(psiVariable2);
                            }
                        }
                        arrayDeque.addLast(instructionState);
                        while (!arrayDeque.isEmpty()) {
                            ProgressManager.checkCanceled();
                            InstructionState instructionState2 = (InstructionState) arrayDeque.removeFirst();
                            instructionState2.markVisited();
                            InstructionKey instructionKey = instructionState2.getInstructionKey();
                            if (instructionKey.getOffset() < instructions.size()) {
                                Instruction instruction2 = instructions.get(instructionKey.getOffset());
                                if (instruction2 instanceof WriteVariableInstruction) {
                                    PsiVariable psiVariable3 = ((WriteVariableInstruction) instruction2).variable;
                                    set.add(psiVariable3);
                                    if (instructionState2.removeUsed(psiVariable3)) {
                                        bitSet.set(instructionKey.getOffset());
                                    }
                                } else if (!(instruction2 instanceof ReadVariableInstruction)) {
                                    instructionState2.touch();
                                } else {
                                    ReadVariableInstruction readVariableInstruction = (ReadVariableInstruction) instruction2;
                                    instructionState2.addUsed(readVariableInstruction.variable);
                                    set.add(readVariableInstruction.variable);
                                }
                            }
                            Iterator<InstructionKey> it = instructionState2.getBackwardTraces().iterator();
                            while (it.hasNext()) {
                                InstructionState instructionState3 = states.get(it.next());
                                if (instructionState3 != null && !instructionState3.contains(instructionState2)) {
                                    instructionState3.addUsedFrom(instructionState2);
                                    arrayDeque.addLast(instructionState3);
                                }
                            }
                        }
                    }
                }
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < instructions.size(); i2++) {
                    Instruction instruction3 = instructions.get(i2);
                    if (instruction3 instanceof WriteVariableInstruction) {
                        WriteVariableInstruction writeVariableInstruction = (WriteVariableInstruction) instruction3;
                        if (!bitSet.get(i2)) {
                            PsiElement nonStrictParentOfType = PsiTreeUtil.getNonStrictParentOfType(controlFlow.getElement(i2), PsiStatement.class, PsiAssignmentExpression.class, PsiUnaryExpression.class);
                            PsiVariable psiVariable4 = writeVariableInstruction.variable;
                            if (nonStrictParentOfType != null && !(nonStrictParentOfType instanceof PsiTryStatement)) {
                                if ((nonStrictParentOfType instanceof PsiDeclarationStatement) && psiVariable4.getInitializer() == null) {
                                    if (!hashSet.contains(psiVariable4)) {
                                        arrayList.add(new Info(psiVariable4, nonStrictParentOfType, false));
                                    }
                                } else {
                                    arrayList.add(new Info(psiVariable4, nonStrictParentOfType, hashSet2.contains(psiVariable4)));
                                }
                            }
                        }
                    }
                }
                return arrayList;
            } catch (InstructionKey.OverflowException e) {
                LOG.error("Failed to compute paths in the control flow graph", e, controlFlow.toString());
                return null;
            }
        } catch (AnalysisCanceledException unused) {
        }
    }

    public static PsiElement[] getDefs(PsiCodeBlock psiCodeBlock, PsiVariable psiVariable, PsiElement psiElement) {
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(0);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        return getDefs(psiCodeBlock, psiVariable, psiElement, false);
    }

    public static PsiElement[] getDefs(PsiCodeBlock psiCodeBlock, PsiVariable psiVariable, PsiElement psiElement, boolean z) {
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(3);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        if ((psiVariable instanceof PsiLocalVariable) && (psiElement instanceof PsiReferenceExpression) && ((PsiReferenceExpression) psiElement).resolve() == psiVariable) {
            PsiElement containingClassOrLambda = LambdaUtil.getContainingClassOrLambda(psiVariable);
            PsiElement containingClassOrLambda2 = LambdaUtil.getContainingClassOrLambda(psiElement);
            while (containingClassOrLambda != containingClassOrLambda2 && containingClassOrLambda2 != null) {
                PsiElement psiElement2 = containingClassOrLambda2;
                containingClassOrLambda2 = LambdaUtil.getContainingClassOrLambda(containingClassOrLambda2.getLightParent());
                psiElement = psiElement2;
            }
        }
        try {
            PsiElement[] psiElementArr = new AnonymousClass1(psiCodeBlock, psiVariable).get(psiVariable, psiElement);
            if (psiElementArr == null) {
                $$$reportNull$$$0(6);
            }
            return psiElementArr;
        } catch (AnalysisCanceledException e) {
            if (z) {
                ExceptionUtil.rethrowAllAsUnchecked(e);
            }
            PsiElement[] psiElementArr2 = PsiElement.EMPTY_ARRAY;
            if (psiElementArr2 == null) {
                $$$reportNull$$$0(7);
            }
            return psiElementArr2;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil$1, reason: invalid class name */
    class AnonymousClass1 extends RefsDefs {
        private final IntList[] myBackwardTraces;
        final PsiManager psiManager;
        final /* synthetic */ PsiVariable val$def;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = RefactorUtil.RES_DIR;
            } else {
                objArr[0] = "instruction";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$1";
            objArr[2] = "processInstruction";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.RefsDefs
        protected boolean defs() {
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(PsiCodeBlock psiCodeBlock, PsiVariable psiVariable) throws AnalysisCanceledException {
            super(psiCodeBlock);
            this.val$def = psiVariable;
            this.psiManager = psiVariable.getManager();
            this.myBackwardTraces = DefUseUtil.getBackwardTraces(this.instructions);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.RefsDefs
        protected int nNext(int i) {
            return this.myBackwardTraces[i].size();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.RefsDefs
        protected int getNext(int i, int i2) {
            return this.myBackwardTraces[i].getInt(i2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.RefsDefs
        protected void processInstruction(final Set<? super PsiElement> set, Instruction instruction, int i) {
            if (set == null) {
                $$$reportNull$$$0(0);
            }
            if (instruction == null) {
                $$$reportNull$$$0(1);
            }
            if ((instruction instanceof WriteVariableInstruction) && this.psiManager.areElementsEquivalent(((WriteVariableInstruction) instruction).variable, this.val$def)) {
                this.flow.getElement(i).accept(new JavaRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.1.1
                    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
                    public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
                        if (PsiUtil.isAccessedForWriting(psiReferenceExpression) && AnonymousClass1.this.psiManager.areElementsEquivalent(psiReferenceExpression.resolve(), AnonymousClass1.this.val$def)) {
                            set.add(psiReferenceExpression);
                        }
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
                    public void visitVariable(PsiVariable psiVariable) {
                        if (((psiVariable instanceof PsiParameter) || psiVariable.hasInitializer()) && AnonymousClass1.this.psiManager.areElementsEquivalent(psiVariable, AnonymousClass1.this.val$def)) {
                            set.add(psiVariable);
                        }
                    }
                });
            }
        }
    }

    public static PsiElement[] getRefs(PsiCodeBlock psiCodeBlock, PsiVariable psiVariable, PsiElement psiElement) {
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(8);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        PsiElement[] refs = getRefs(psiCodeBlock, psiVariable, psiElement, false);
        if (refs == null) {
            $$$reportNull$$$0(11);
        }
        return refs;
    }

    public static PsiElement[] getRefs(PsiCodeBlock psiCodeBlock, PsiVariable psiVariable, PsiElement psiElement, boolean z) {
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(12);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(13);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        try {
            return new RefsDefs(psiCodeBlock, psiVariable) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.2
                final PsiManager psiManager;
                final /* synthetic */ PsiVariable val$def;

                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i != 1) {
                        objArr[0] = RefactorUtil.RES_DIR;
                    } else {
                        objArr[0] = "instruction";
                    }
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$2";
                    objArr[2] = "processInstruction";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.RefsDefs
                protected boolean defs() {
                    return false;
                }

                {
                    this.val$def = psiVariable;
                    this.psiManager = psiVariable.getManager();
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.RefsDefs
                protected int nNext(int i) {
                    return this.instructions.get(i).nNext();
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.RefsDefs
                protected int getNext(int i, int i2) {
                    return this.instructions.get(i).getNext(i, i2);
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.RefsDefs
                protected void processInstruction(final Set<? super PsiElement> set, Instruction instruction, int i) {
                    if (set == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (instruction == null) {
                        $$$reportNull$$$0(1);
                    }
                    if ((instruction instanceof ReadVariableInstruction) && this.psiManager.areElementsEquivalent(((ReadVariableInstruction) instruction).variable, this.val$def)) {
                        this.flow.getElement(i).accept(new JavaRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.DefUseUtil.2.1
                            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
                            public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
                                if (psiReferenceExpression.isReferenceTo(AnonymousClass2.this.val$def)) {
                                    set.add(psiReferenceExpression);
                                }
                            }
                        });
                    }
                }
            }.get(psiVariable, psiElement);
        } catch (AnalysisCanceledException e) {
            if (z) {
                ExceptionUtil.rethrowAllAsUnchecked(e);
            }
            return PsiElement.EMPTY_ARRAY;
        }
    }

    private static abstract class RefsDefs {
        final PsiCodeBlock body;
        final ControlFlow flow;
        final List<Instruction> instructions;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "def";
            } else if (i == 2) {
                objArr[0] = "refOrDef";
            } else if (i == 3 || i == 4 || i == 5) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$RefsDefs";
            } else {
                objArr[0] = "body";
            }
            if (i == 3 || i == 4 || i == 5) {
                objArr[1] = "get";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$RefsDefs";
            }
            if (i == 1 || i == 2) {
                objArr[2] = "get";
            } else if (i != 3 && i != 4 && i != 5) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4 && i != 5) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        protected abstract boolean defs();

        protected abstract int getNext(int i, int i2);

        protected abstract int nNext(int i);

        protected abstract void processInstruction(Set<? super PsiElement> set, Instruction instruction, int i);

        RefsDefs(PsiCodeBlock psiCodeBlock) throws AnalysisCanceledException {
            if (psiCodeBlock == null) {
                $$$reportNull$$$0(0);
            }
            this.body = psiCodeBlock;
            ControlFlow controlFlow = ControlFlowFactory.getControlFlow(psiCodeBlock, DefUseUtil.ourPolicy, ControlFlowOptions.NO_CONST_EVALUATE);
            this.flow = controlFlow;
            this.instructions = controlFlow.getInstructions();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public PsiElement[] get(PsiVariable psiVariable, PsiElement psiElement) {
            PsiIdentifier psiIdentifierMo7392getNameIdentifier;
            if (psiVariable == null) {
                $$$reportNull$$$0(1);
            }
            if (psiElement == null) {
                $$$reportNull$$$0(2);
            }
            if (this.body == null) {
                PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
                if (psiElementArr == null) {
                    $$$reportNull$$$0(3);
                }
                return psiElementArr;
            }
            int size = this.instructions.size();
            boolean[] zArr = new boolean[size + 1];
            zArr[size] = true;
            boolean zDefs = defs();
            ControlFlow controlFlow = this.flow;
            int startOffset = zDefs ? controlFlow.getStartOffset(psiElement) : controlFlow.getEndOffset(psiElement);
            if (startOffset == -1 && (psiVariable instanceof PsiParameter)) {
                startOffset = 0;
            }
            if (startOffset != -1) {
                if (!zDefs && (this.instructions.get(startOffset) instanceof ReadVariableInstruction)) {
                    DefUseUtil.LOG.assertTrue(nNext(startOffset) == 1);
                    Logger logger = DefUseUtil.LOG;
                    int next = getNext(startOffset, 0);
                    startOffset++;
                    logger.assertTrue(next == startOffset);
                }
                HashSet hashSet = new HashSet();
                IntArrayList intArrayList = new IntArrayList();
                intArrayList.add(startOffset);
                PsiManager manager = this.body.getManager();
                while (!intArrayList.isEmpty()) {
                    int iRemoveInt = intArrayList.removeInt(intArrayList.size() - 1);
                    if (!zArr[iRemoveInt]) {
                        zArr[iRemoveInt] = true;
                        if (zDefs) {
                            Instruction instruction = this.instructions.get(iRemoveInt);
                            processInstruction(hashSet, instruction, iRemoveInt);
                            if (!(instruction instanceof WriteVariableInstruction) || !manager.areElementsEquivalent(((WriteVariableInstruction) instruction).variable, psiVariable)) {
                                if (iRemoveInt == 0 && (psiVariable instanceof PsiParameter) && (psiIdentifierMo7392getNameIdentifier = psiVariable.mo7392getNameIdentifier()) != null) {
                                    hashSet.add(psiIdentifierMo7392getNameIdentifier);
                                }
                            }
                        }
                        int iNNext = nNext(iRemoveInt);
                        for (int i = 0; i < iNNext; i++) {
                            int next2 = getNext(iRemoveInt, i);
                            if (!zArr[next2]) {
                                if (!zDefs) {
                                    Instruction instruction2 = this.instructions.get(next2);
                                    if (instruction2 instanceof WriteVariableInstruction) {
                                        if (manager.areElementsEquivalent(((WriteVariableInstruction) instruction2).variable, psiVariable)) {
                                        }
                                    } else {
                                        processInstruction(hashSet, instruction2, next2);
                                    }
                                    intArrayList.add(next2);
                                } else {
                                    intArrayList.add(next2);
                                }
                            }
                        }
                    }
                }
                PsiElement[] psiElementArray = PsiUtilCore.toPsiElementArray(hashSet);
                if (psiElementArray == null) {
                    $$$reportNull$$$0(4);
                }
                return psiElementArray;
            }
            PsiElement[] psiElementArr2 = PsiElement.EMPTY_ARRAY;
            if (psiElementArr2 == null) {
                $$$reportNull$$$0(5);
            }
            return psiElementArr2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IntList[] getBackwardTraces(List<? extends Instruction> list) {
        if (list == null) {
            $$$reportNull$$$0(15);
        }
        int size = list.size();
        IntList[] intListArr = new IntList[size];
        for (int i = 0; i < size; i++) {
            intListArr[i] = new IntArrayList();
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            Instruction instruction = list.get(i2);
            for (int i3 = 0; i3 != instruction.nNext(); i3++) {
                int next = instruction.getNext(i2, i3);
                if (next < size) {
                    intListArr[next].add(i2);
                }
            }
        }
        return intListArr;
    }

    private static class WalkThroughStack {
        private final Stack<InstructionKey> myFrom;
        private final Stack<InstructionKey> myNext;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "nextKey";
            } else if (i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$WalkThroughStack";
            } else {
                objArr[0] = "fromKey";
            }
            if (i == 2) {
                objArr[1] = "peekFrom";
            } else if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$WalkThroughStack";
            } else {
                objArr[1] = "popNext";
            }
            if (i != 2 && i != 3) {
                objArr[2] = "push";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        WalkThroughStack(int i) {
            i = i < 2 ? 2 : i;
            this.myFrom = new Stack<>(i);
            this.myNext = new Stack<>(i);
        }

        void push(InstructionKey instructionKey, InstructionKey instructionKey2) {
            if (instructionKey == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionKey2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myFrom.push(instructionKey);
            this.myNext.push(instructionKey2);
        }

        InstructionKey peekFrom() {
            InstructionKey instructionKeyPeek = this.myFrom.peek();
            if (instructionKeyPeek == null) {
                $$$reportNull$$$0(2);
            }
            return instructionKeyPeek;
        }

        InstructionKey popNext() {
            this.myFrom.pop();
            InstructionKey instructionKeyPop = this.myNext.pop();
            if (instructionKeyPop == null) {
                $$$reportNull$$$0(3);
            }
            return instructionKeyPop;
        }

        boolean isEmpty() {
            return this.myFrom.isEmpty();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            int iMin = Math.min(this.myFrom.size(), this.myNext.size());
            for (int i = 0; i < iMin; i++) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(this.myFrom.get(i)).append("->").append(this.myNext.get(i));
            }
            return sb.toString();
        }
    }

    private static final class InstructionStateWalker {
        private final List<? extends Instruction> myInstructions;
        private final Map<InstructionKey, InstructionState> myStates;
        private final WalkThroughStack myWalkThroughStack;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$InstructionStateWalker";
            } else if (i == 2 || i == 3) {
                objArr[0] = "fromKey";
            } else if (i != 4) {
                objArr[0] = "instructions";
            } else {
                objArr[0] = "nextKey";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/DefUseUtil$InstructionStateWalker";
            } else {
                objArr[1] = "walk";
            }
            if (i != 1) {
                if (i == 2) {
                    objArr[2] = "visit";
                } else if (i == 3 || i == 4) {
                    objArr[2] = "addBackwardTrace";
                } else if (i != 5) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "getStates";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        private InstructionStateWalker(List<? extends Instruction> list) {
            if (list == null) {
                $$$reportNull$$$0(0);
            }
            this.myStates = new HashMap(list.size());
            this.myWalkThroughStack = new WalkThroughStack(list.size() / 2);
            this.myInstructions = list;
        }

        private Map<InstructionKey, InstructionState> walk() {
            InstructionKey instructionKeyCreate = InstructionKey.create(0);
            this.myStates.put(instructionKeyCreate, new InstructionState(instructionKeyCreate));
            this.myWalkThroughStack.push(InstructionKey.create(-1), instructionKeyCreate);
            InstructionKeySet instructionKeySet = new InstructionKeySet(this.myInstructions.size() + 1);
            while (!this.myWalkThroughStack.isEmpty()) {
                ProgressManager.checkCanceled();
                InstructionKey instructionKeyPeekFrom = this.myWalkThroughStack.peekFrom();
                InstructionKey instructionKeyPopNext = this.myWalkThroughStack.popNext();
                addBackwardTrace(instructionKeyPeekFrom, instructionKeyPopNext);
                if (!instructionKeySet.contains(instructionKeyPopNext)) {
                    visit(instructionKeyPopNext);
                    instructionKeySet.add(instructionKeyPopNext);
                }
            }
            Map<InstructionKey, InstructionState> map = this.myStates;
            if (map == null) {
                $$$reportNull$$$0(1);
            }
            return map;
        }

        private void visit(InstructionKey instructionKey) {
            if (instructionKey == null) {
                $$$reportNull$$$0(2);
            }
            if (instructionKey.getOffset() >= this.myInstructions.size()) {
                return;
            }
            Instruction instruction = this.myInstructions.get(instructionKey.getOffset());
            if (instruction instanceof CallInstruction) {
                int i = ((CallInstruction) instruction).offset;
                DefUseUtil.LOG.assertTrue(i != 0);
                this.myWalkThroughStack.push(instructionKey, instructionKey.push(i, instructionKey.getOffset() + 1));
            } else {
                if (instruction instanceof ReturnInstruction) {
                    this.myWalkThroughStack.push(instructionKey, instructionKey.pop(((ReturnInstruction) instruction).offset));
                    return;
                }
                for (int i2 = 0; i2 != instruction.nNext(); i2++) {
                    this.myWalkThroughStack.push(instructionKey, instructionKey.next(instruction.getNext(instructionKey.getOffset(), i2)));
                }
            }
        }

        private void addBackwardTrace(InstructionKey instructionKey, InstructionKey instructionKey2) {
            if (instructionKey == null) {
                $$$reportNull$$$0(3);
            }
            if (instructionKey2 == null) {
                $$$reportNull$$$0(4);
            }
            if (instructionKey.getOffset() < 0 || instructionKey2.getOffset() >= this.myInstructions.size()) {
                return;
            }
            InstructionState instructionState = this.myStates.get(instructionKey2);
            if (instructionState == null) {
                Map<InstructionKey, InstructionState> map = this.myStates;
                InstructionState instructionState2 = new InstructionState(instructionKey2);
                map.put(instructionKey2, instructionState2);
                instructionState = instructionState2;
            }
            instructionState.addBackwardTrace(instructionKey);
        }

        static Map<InstructionKey, InstructionState> getStates(List<? extends Instruction> list) {
            if (list == null) {
                $$$reportNull$$$0(5);
            }
            return new InstructionStateWalker(list).walk();
        }
    }
}
