package org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class JavaVariableConflictResolver implements PsiConflictResolver {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "conflicts", "org/jetbrains/kotlin/com/intellij/psi/scope/conflictResolvers/JavaVariableConflictResolver", "resolveConflict"));
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:54:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:55:0x00bc  */
    /* JADX WARN: Code duplicated, block: B:57:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:59:0x00cc A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:60:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:61:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:62:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:63:0x00d6 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:67:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:71:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:78:0x00f2 A[SYNTHETIC] */
    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver
    public CandidateInfo resolveConflict(List<CandidateInfo> list) {
        Boolean boolValueOf;
        boolean z;
        PsiElement element;
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        int size = list.size();
        if (size == 1) {
            return list.get(0);
        }
        if (size == 0) {
            return null;
        }
        CandidateInfo[] candidateInfoArr = (CandidateInfo[]) list.toArray(new CandidateInfo[size]);
        CandidateInfo candidateInfo = candidateInfoArr[0];
        PsiElement element2 = candidateInfo.getElement();
        if (element2 instanceof PsiField) {
            for (int i = 1; i < candidateInfoArr.length; i++) {
                CandidateInfo candidateInfo2 = candidateInfoArr[i];
                PsiElement element3 = candidateInfo2.getElement();
                if (!(element3 instanceof PsiField)) {
                    if (PsiUtil.isJvmLocalVariable(element3) || !candidateInfo.isAccessible()) {
                        return candidateInfo2;
                    }
                    list.remove(candidateInfo2);
                } else {
                    PsiClass containingClass = ((PsiField) element3).mo6092getContainingClass();
                    PsiClass containingClass2 = ((PsiField) element2).mo6092getContainingClass();
                    PsiElement currentFileResolveScope = candidateInfo.getCurrentFileResolveScope();
                    if (containingClass != null && containingClass2 != null) {
                        if (containingClass.isInheritor(containingClass2, true)) {
                            if ((currentFileResolveScope instanceof PsiClass) && (currentFileResolveScope.equals(containingClass2) || currentFileResolveScope.equals(containingClass) || !((PsiClass) currentFileResolveScope).isInheritorDeep(containingClass2, containingClass))) {
                                list.remove(candidateInfo);
                                element = candidateInfo2.getElement();
                            } else {
                                boolValueOf = null;
                                if (!candidateInfo2.isAccessible()) {
                                    list.remove(candidateInfo2);
                                } else if (!candidateInfo.isAccessible()) {
                                    list.remove(candidateInfo);
                                    element = candidateInfo2.getElement();
                                } else if (element3 == element2) {
                                    list.remove(candidateInfo2);
                                } else {
                                    if (boolValueOf == null) {
                                        if (containingClass2 == null) {
                                            z = false;
                                        } else {
                                            z = false;
                                        }
                                        boolValueOf = Boolean.valueOf(z);
                                    }
                                    if (!boolValueOf.booleanValue()) {
                                        return null;
                                    }
                                    list.remove(candidateInfo2);
                                }
                            }
                            element2 = element;
                            candidateInfo = candidateInfo2;
                        } else {
                            boolValueOf = Boolean.valueOf(containingClass2.isInheritor(containingClass, true));
                            if (boolValueOf.booleanValue() && (!(currentFileResolveScope instanceof PsiClass) || currentFileResolveScope.equals(containingClass2) || currentFileResolveScope.equals(containingClass) || !((PsiClass) currentFileResolveScope).isInheritorDeep(containingClass, containingClass2))) {
                                list.remove(candidateInfo2);
                            } else if (!candidateInfo2.isAccessible()) {
                                list.remove(candidateInfo2);
                            } else if (!candidateInfo.isAccessible()) {
                                list.remove(candidateInfo);
                                element = candidateInfo2.getElement();
                                element2 = element;
                                candidateInfo = candidateInfo2;
                            } else if (element3 == element2) {
                                list.remove(candidateInfo2);
                            } else {
                                if (boolValueOf == null) {
                                    if (containingClass2 == null) {
                                        z = false;
                                    } else {
                                        z = false;
                                    }
                                    boolValueOf = Boolean.valueOf(z);
                                }
                                if (!boolValueOf.booleanValue()) {
                                    return null;
                                }
                                list.remove(candidateInfo2);
                            }
                        }
                    } else {
                        boolValueOf = null;
                        if (!candidateInfo2.isAccessible()) {
                            list.remove(candidateInfo2);
                        } else if (!candidateInfo.isAccessible()) {
                            list.remove(candidateInfo);
                            element = candidateInfo2.getElement();
                            element2 = element;
                            candidateInfo = candidateInfo2;
                        } else if (element3 == element2) {
                            list.remove(candidateInfo2);
                        } else {
                            if (boolValueOf == null) {
                                if (containingClass2 == null && containingClass != null && containingClass2.isInheritor(containingClass, true)) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                boolValueOf = Boolean.valueOf(z);
                            }
                            if (!boolValueOf.booleanValue()) {
                                return null;
                            }
                            list.remove(candidateInfo2);
                        }
                    }
                }
            }
        }
        return candidateInfo;
    }
}
