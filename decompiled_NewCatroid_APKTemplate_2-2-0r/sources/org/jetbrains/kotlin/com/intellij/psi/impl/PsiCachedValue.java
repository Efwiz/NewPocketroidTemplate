package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager;
import org.jetbrains.kotlin.com.intellij.model.ModelBranch;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.ProjectRootModificationTracker;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.CachedValueBase;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiCachedValue<T> extends CachedValueBase<T> {
    private static final Key<?> PSI_MOD_COUNT_OPTIMIZATION = Key.create("PSI_MOD_COUNT_OPTIMIZATION");
    private final PsiManager myManager;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "result";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiCachedValue";
                break;
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
                objArr[0] = "dependency";
                break;
            case 9:
                objArr[0] = "data";
                break;
            case 12:
                objArr[0] = "project";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[1] = "normalizeDependencies";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiCachedValue";
        }
        switch (i) {
            case 1:
                objArr[2] = "normalizeDependencies";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                break;
            case 6:
                objArr[2] = "anyChangeImpliesPsiCounterChange";
                break;
            case 7:
                objArr[2] = "isPsiModificationCount";
                break;
            case 8:
                objArr[2] = "isVeryPhysical";
                break;
            case 9:
                objArr[2] = "isUpToDate";
                break;
            case 10:
                objArr[2] = "isDependencyOutOfDate";
                break;
            case 11:
                objArr[2] = "getTimeStamp";
                break;
            case 12:
                objArr[2] = "isFromMyProject";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PsiCachedValue(PsiManager psiManager, boolean z) {
        super(z);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        this.myManager = psiManager;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    protected Object[] normalizeDependencies(CachedValueProvider.Result<T> result) {
        final ModelBranch psiBranch;
        if (result == null) {
            $$$reportNull$$$0(1);
        }
        Object[] objArrNormalizeDependencies = super.normalizeDependencies(result);
        if (ContainerUtil.exists(objArrNormalizeDependencies, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiCachedValue$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiCachedValue.isPsiModificationCount(obj);
            }
        })) {
            for (Object obj : objArrNormalizeDependencies) {
                if ((obj instanceof PsiElement) && (psiBranch = ModelBranch.getPsiBranch((PsiElement) obj)) != null) {
                    Object[] objArrPrepend = ArrayUtil.prepend(new ModificationTracker() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiCachedValue$$ExternalSyntheticLambda1
                        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
                        public final long getModificationCount() {
                            return psiBranch.getBranchedPsiModificationCount();
                        }
                    }, objArrNormalizeDependencies);
                    if (objArrPrepend == null) {
                        $$$reportNull$$$0(2);
                    }
                    return objArrPrepend;
                }
            }
        }
        if (objArrNormalizeDependencies.length == 1 && isPsiModificationCount(objArrNormalizeDependencies[0])) {
            if (objArrNormalizeDependencies == null) {
                $$$reportNull$$$0(3);
            }
            return objArrNormalizeDependencies;
        }
        if (objArrNormalizeDependencies.length <= 0 || !ContainerUtil.and(objArrNormalizeDependencies, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiCachedValue$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj2) {
                return this.f$0.anyChangeImpliesPsiCounterChange(obj2);
            }
        })) {
            if (objArrNormalizeDependencies == null) {
                $$$reportNull$$$0(5);
            }
            return objArrNormalizeDependencies;
        }
        Object[] objArrPrepend2 = ArrayUtil.prepend(PSI_MOD_COUNT_OPTIMIZATION, objArrNormalizeDependencies);
        if (objArrPrepend2 == null) {
            $$$reportNull$$$0(4);
        }
        return objArrPrepend2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean anyChangeImpliesPsiCounterChange(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(6);
        }
        return ((obj instanceof PsiElement) && isVeryPhysical((PsiElement) obj)) || (obj instanceof ProjectRootModificationTracker) || isPsiModificationCount(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPsiModificationCount(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(7);
        }
        return (obj instanceof PsiModificationTracker) || obj == PsiModificationTracker.MODIFICATION_COUNT;
    }

    private boolean isVeryPhysical(PsiElement psiElement) {
        PsiFile topLevelFile;
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        return psiElement.isValid() && psiElement.getIsPhysical() && (topLevelFile = InjectedLanguageManager.getInstance(this.myManager.getProject()).getTopLevelFile(psiElement)) != null && topLevelFile.getIsPhysical();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    protected boolean isUpToDate(CachedValueBase.Data<T> data) {
        if (data == null) {
            $$$reportNull$$$0(9);
        }
        if (this.myManager.isDisposed()) {
            return false;
        }
        Object[] dependencies = data.getDependencies();
        if (dependencies.length > 0 && dependencies[0] == PSI_MOD_COUNT_OPTIMIZATION && data.getTimeStamps()[0] == this.myManager.getModificationTracker().getModificationCount()) {
            return true;
        }
        return super.isUpToDate(data);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    protected boolean isDependencyOutOfDate(Object obj, long j) {
        if (obj == null) {
            $$$reportNull$$$0(10);
        }
        if (obj == PSI_MOD_COUNT_OPTIMIZATION) {
            return false;
        }
        return super.isDependencyOutOfDate(obj, j);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    protected long getTimeStamp(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(11);
        }
        if (obj instanceof PsiDirectory) {
            return this.myManager.getModificationTracker().getModificationCount();
        }
        if (obj instanceof PsiElement) {
            PsiElement psiElement = (PsiElement) obj;
            if (!psiElement.isValid()) {
                return -1L;
            }
            PsiFile containingFile = psiElement.getContainingFile();
            if (containingFile != null) {
                return containingFile.getModificationStamp();
            }
        }
        if (obj == PsiModificationTracker.MODIFICATION_COUNT || obj == PSI_MOD_COUNT_OPTIMIZATION) {
            return this.myManager.getModificationTracker().getModificationCount();
        }
        return super.getTimeStamp(obj);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    public boolean isFromMyProject(Project project) {
        if (project == null) {
            $$$reportNull$$$0(12);
        }
        return this.myManager.getProject() == project;
    }
}
