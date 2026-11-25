package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
class ControlFlowImpl extends AbstractControlFlow {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) ControlFlowImpl.class);
    private boolean myConstantConditionOccurred;
    private final Stack<PsiElement> myElementStack;
    private final List<PsiElement> myElementsForInstructions;
    private final Map<Instruction, Instruction> myInstructionCache;
    private final List<Instruction> myInstructions;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowImpl", "getInstructions"));
    }

    protected ControlFlowImpl() {
        super(new Object2LongOpenHashMap());
        this.myInstructions = new ArrayList();
        this.myElementsForInstructions = new ArrayList();
        this.myInstructionCache = new HashMap();
        this.myElementStack = new Stack<>();
    }

    void addInstruction(Instruction instruction) {
        Instruction instructionPutIfAbsent;
        if (((instruction instanceof ReadVariableInstruction) || (instruction instanceof WriteVariableInstruction)) && (instructionPutIfAbsent = this.myInstructionCache.putIfAbsent(instruction, instruction)) != null) {
            instruction = instructionPutIfAbsent;
        }
        this.myInstructions.add(instruction);
        this.myElementsForInstructions.add(this.myElementStack.peek());
    }

    public void startElement(PsiElement psiElement) {
        this.myElementStack.push(psiElement);
        this.myElementToOffsetMap.put(psiElement, ((long) this.myInstructions.size()) | (-4294967296L));
    }

    void finishElement(PsiElement psiElement) {
        LOG.assertTrue(this.myElementStack.pop().equals(psiElement));
        this.myElementToOffsetMap.computeLong(psiElement, new BiFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.m6540x2b8b6038((PsiElement) obj, (Long) obj2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$finishElement$0$org-jetbrains-kotlin-com-intellij-psi-controlFlow-ControlFlowImpl, reason: not valid java name */
    /* synthetic */ Long m6540x2b8b6038(PsiElement psiElement, Long l) {
        return Long.valueOf((((long) this.myInstructions.size()) << 32) | (l != null ? 4294967295L & l.longValue() : 4294967295L));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public List<Instruction> getInstructions() {
        List<Instruction> list = this.myInstructions;
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        return list;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public int getSize() {
        return this.myInstructions.size();
    }

    ControlFlow immutableCopy() {
        return new ImmutableControlFlow((Instruction[]) this.myInstructions.toArray(new Instruction[0]), new Object2LongOpenHashMap(this.myElementToOffsetMap), (PsiElement[]) this.myElementsForInstructions.toArray(PsiElement.EMPTY_ARRAY), this.myConstantConditionOccurred);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public PsiElement getElement(int i) {
        return this.myElementsForInstructions.get(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
    public boolean isConstantConditionOccurred() {
        return this.myConstantConditionOccurred;
    }

    void setConstantConditionOccurred(boolean z) {
        this.myConstantConditionOccurred = z;
    }

    private static final class ImmutableControlFlow extends AbstractControlFlow {
        private final boolean myConstantConditionOccurred;
        private final PsiElement[] myElementsForInstructions;
        private final List<Instruction> myInstructions;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "myElementToOffsetMap";
            } else if (i == 2) {
                objArr[0] = "elementsForInstructions";
            } else if (i != 3) {
                objArr[0] = "instructions";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowImpl$ImmutableControlFlow";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowImpl$ImmutableControlFlow";
            } else {
                objArr[1] = "getInstructions";
            }
            if (i != 3) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private ImmutableControlFlow(Instruction[] instructionArr, Object2LongMap<PsiElement> object2LongMap, PsiElement[] psiElementArr, boolean z) {
            super(object2LongMap);
            if (instructionArr == null) {
                $$$reportNull$$$0(0);
            }
            if (object2LongMap == null) {
                $$$reportNull$$$0(1);
            }
            if (psiElementArr == null) {
                $$$reportNull$$$0(2);
            }
            this.myInstructions = Arrays.asList(instructionArr);
            this.myElementsForInstructions = psiElementArr;
            this.myConstantConditionOccurred = z;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
        public List<Instruction> getInstructions() {
            List<Instruction> list = this.myInstructions;
            if (list == null) {
                $$$reportNull$$$0(3);
            }
            return list;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
        public int getSize() {
            return this.myInstructions.size();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
        public PsiElement getElement(int i) {
            return this.myElementsForInstructions[i];
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow
        public boolean isConstantConditionOccurred() {
            return this.myConstantConditionOccurred;
        }
    }
}
