package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import java.util.Map;
import java.util.function.Function;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.components.Service;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.AnyPsiChangeListener;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
@Service
public final class ControlFlowFactory implements Disposable {
    private final Map<PsiElement, ConcurrentList<ControlFlowContext>> cachedFlows;

    /* JADX WARN: Code duplicated, block: B:11:0x0014  */
    /* JADX WARN: Code duplicated, block: B:6:0x000a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 22) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 22) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 8:
            case 17:
            case 21:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
            case 4:
            case 6:
            case 9:
            case 15:
            case 20:
                objArr[0] = "policy";
                break;
            case 7:
            case 10:
            case 14:
            case 19:
                objArr[0] = "options";
                break;
            case 11:
            case 12:
            case 13:
            case 22:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowFactory";
                break;
            case 16:
                objArr[0] = "controlFlow";
                break;
            case 18:
                objArr[0] = "flow";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i != 22) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    objArr[1] = "doGetControlFlow";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowFactory";
                    break;
            }
        } else {
            objArr[1] = "getOrCreateCachedFlowsForElement";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "getControlFlow";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "doGetControlFlow";
                break;
            case 11:
            case 12:
            case 13:
            case 22:
                break;
            case 14:
            case 15:
            case 16:
                objArr[2] = "createContext";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[2] = "registerControlFlow";
                break;
            case 21:
                objArr[2] = "getOrCreateCachedFlowsForElement";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 22) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
    }

    public static ControlFlowFactory getInstance(Project project) {
        return (ControlFlowFactory) project.getService(ControlFlowFactory.class);
    }

    public ControlFlowFactory(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.cachedFlows = ContainerUtil.createConcurrentWeakKeySoftValueMap();
        project.getMessageBus().connect(this).subscribe(PsiManagerImpl.ANY_PSI_CHANGE_TOPIC, new AnyPsiChangeListener() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowFactory.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.AnyPsiChangeListener
            public void beforePsiChanged(boolean z) {
                if (z) {
                    ControlFlowFactory.this.clearCache();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearCache() {
        this.cachedFlows.clear();
    }

    void registerSubRange(PsiElement psiElement, ControlFlowSubRange controlFlowSubRange, ControlFlowOptions controlFlowOptions, ControlFlowPolicy controlFlowPolicy) {
        registerControlFlow(psiElement, controlFlowSubRange, controlFlowOptions, controlFlowPolicy);
    }

    private static final class ControlFlowContext {
        private final ControlFlow controlFlow;
        private final long modificationCount;
        private final ControlFlowOptions options;
        private final ControlFlowPolicy policy;

        /* JADX WARN: Code duplicated, block: B:10:0x001e  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "policy";
            } else if (i == 2) {
                objArr[0] = "controlFlow";
            } else if (i == 3) {
                objArr[0] = "policy";
            } else if (i != 5) {
                objArr[0] = "options";
            } else {
                objArr[0] = "that";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowFactory$ControlFlowContext";
            if (i == 3 || i == 4 || i == 5) {
                objArr[2] = "isFor";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private ControlFlowContext(ControlFlowOptions controlFlowOptions, ControlFlowPolicy controlFlowPolicy, long j, ControlFlow controlFlow) {
            if (controlFlowOptions == null) {
                $$$reportNull$$$0(0);
            }
            if (controlFlowPolicy == null) {
                $$$reportNull$$$0(1);
            }
            if (controlFlow == null) {
                $$$reportNull$$$0(2);
            }
            this.options = controlFlowOptions;
            this.policy = controlFlowPolicy;
            this.modificationCount = j;
            this.controlFlow = controlFlow;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return isFor((ControlFlowContext) obj);
        }

        public int hashCode() {
            int iHashCode = ((this.policy.hashCode() * 31) + this.options.hashCode()) * 31;
            long j = this.modificationCount;
            return iHashCode + ((int) (j ^ (j >>> 32)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isFor(ControlFlowPolicy controlFlowPolicy, ControlFlowOptions controlFlowOptions, long j) {
            if (controlFlowPolicy == null) {
                $$$reportNull$$$0(3);
            }
            if (controlFlowOptions == null) {
                $$$reportNull$$$0(4);
            }
            if (j != this.modificationCount || !controlFlowPolicy.equals(this.policy)) {
                return false;
            }
            if (controlFlowOptions.equals(this.options)) {
                return true;
            }
            return !this.controlFlow.isConstantConditionOccurred() && this.options.dontEvaluateConstantIfCondition().equals(controlFlowOptions);
        }

        private boolean isFor(ControlFlowContext controlFlowContext) {
            if (controlFlowContext == null) {
                $$$reportNull$$$0(5);
            }
            return isFor(controlFlowContext.policy, controlFlowContext.options, controlFlowContext.modificationCount);
        }
    }

    public ControlFlow getControlFlow(PsiElement psiElement, ControlFlowPolicy controlFlowPolicy) throws AnalysisCanceledException {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (controlFlowPolicy == null) {
            $$$reportNull$$$0(2);
        }
        return doGetControlFlow(psiElement, controlFlowPolicy, ControlFlowOptions.create(true, true, true));
    }

    public ControlFlow getControlFlow(PsiElement psiElement, ControlFlowPolicy controlFlowPolicy, boolean z) throws AnalysisCanceledException {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (controlFlowPolicy == null) {
            $$$reportNull$$$0(4);
        }
        return doGetControlFlow(psiElement, controlFlowPolicy, ControlFlowOptions.create(true, z, true));
    }

    public static ControlFlow getControlFlow(PsiElement psiElement, ControlFlowPolicy controlFlowPolicy, ControlFlowOptions controlFlowOptions) throws AnalysisCanceledException {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        if (controlFlowPolicy == null) {
            $$$reportNull$$$0(6);
        }
        if (controlFlowOptions == null) {
            $$$reportNull$$$0(7);
        }
        return getInstance(psiElement.getProject()).doGetControlFlow(psiElement, controlFlowPolicy, controlFlowOptions);
    }

    private ControlFlow doGetControlFlow(PsiElement psiElement, ControlFlowPolicy controlFlowPolicy, ControlFlowOptions controlFlowOptions) throws AnalysisCanceledException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        if (controlFlowPolicy == null) {
            $$$reportNull$$$0(9);
        }
        if (controlFlowOptions == null) {
            $$$reportNull$$$0(10);
        }
        if (!psiElement.getIsPhysical()) {
            ControlFlow controlFlowBuildControlFlow = new ControlFlowAnalyzer(psiElement, controlFlowPolicy, controlFlowOptions).buildControlFlow();
            if (controlFlowBuildControlFlow == null) {
                $$$reportNull$$$0(11);
            }
            return controlFlowBuildControlFlow;
        }
        long modificationCount = psiElement.getManager().getModificationTracker().getModificationCount();
        ConcurrentList<ControlFlowContext> orCreateCachedFlowsForElement = getOrCreateCachedFlowsForElement(psiElement);
        for (ControlFlowContext controlFlowContext : orCreateCachedFlowsForElement) {
            if (controlFlowContext.isFor(controlFlowPolicy, controlFlowOptions, modificationCount)) {
                ControlFlow controlFlow = controlFlowContext.controlFlow;
                if (controlFlow == null) {
                    $$$reportNull$$$0(12);
                }
                return controlFlow;
            }
        }
        ControlFlow controlFlowBuildControlFlow2 = new ControlFlowAnalyzer(psiElement, controlFlowPolicy, controlFlowOptions).buildControlFlow();
        orCreateCachedFlowsForElement.addIfAbsent(createContext(controlFlowOptions, controlFlowPolicy, controlFlowBuildControlFlow2, modificationCount));
        if (controlFlowBuildControlFlow2 == null) {
            $$$reportNull$$$0(13);
        }
        return controlFlowBuildControlFlow2;
    }

    private static ControlFlowContext createContext(ControlFlowOptions controlFlowOptions, ControlFlowPolicy controlFlowPolicy, ControlFlow controlFlow, long j) {
        if (controlFlowOptions == null) {
            $$$reportNull$$$0(14);
        }
        if (controlFlowPolicy == null) {
            $$$reportNull$$$0(15);
        }
        if (controlFlow == null) {
            $$$reportNull$$$0(16);
        }
        return new ControlFlowContext(controlFlowOptions, controlFlowPolicy, j, controlFlow);
    }

    private void registerControlFlow(PsiElement psiElement, ControlFlow controlFlow, ControlFlowOptions controlFlowOptions, ControlFlowPolicy controlFlowPolicy) {
        if (psiElement == null) {
            $$$reportNull$$$0(17);
        }
        if (controlFlow == null) {
            $$$reportNull$$$0(18);
        }
        if (controlFlowOptions == null) {
            $$$reportNull$$$0(19);
        }
        if (controlFlowPolicy == null) {
            $$$reportNull$$$0(20);
        }
        getOrCreateCachedFlowsForElement(psiElement).addIfAbsent(createContext(controlFlowOptions, controlFlowPolicy, controlFlow, psiElement.getManager().getModificationTracker().getModificationCount()));
    }

    private ConcurrentList<ControlFlowContext> getOrCreateCachedFlowsForElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(21);
        }
        ConcurrentList<ControlFlowContext> concurrentListComputeIfAbsent = this.cachedFlows.computeIfAbsent(psiElement, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowFactory$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ContainerUtil.createConcurrentList();
            }
        });
        if (concurrentListComputeIfAbsent == null) {
            $$$reportNull$$$0(22);
        }
        return concurrentListComputeIfAbsent;
    }
}
