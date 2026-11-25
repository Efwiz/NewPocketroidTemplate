package org.jetbrains.kotlin.com.intellij.psi.scope.conflictResolvers;

import java.util.HashMap;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;

/* JADX INFO: loaded from: classes3.dex */
public final class DuplicateConflictResolver implements PsiConflictResolver {
    public static final DuplicateConflictResolver INSTANCE = new DuplicateConflictResolver();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "conflicts", "org/jetbrains/kotlin/com/intellij/psi/scope/conflictResolvers/DuplicateConflictResolver", "resolveConflict"));
    }

    private DuplicateConflictResolver() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiConflictResolver
    public CandidateInfo resolveConflict(List<CandidateInfo> list) {
        Object name2;
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        HashMap map = new HashMap();
        for (CandidateInfo candidateInfo : list) {
            PsiElement element = candidateInfo.getElement();
            if (candidateInfo instanceof MethodCandidateInfo) {
                name2 = ((PsiMethod) element).getSignature(((MethodCandidateInfo) candidateInfo).getSubstitutor(false));
            } else {
                name2 = PsiUtilCore.getName(element);
            }
            if (!map.containsKey(name2)) {
                map.put(name2, candidateInfo);
            }
        }
        if (map.size() == 1) {
            return (CandidateInfo) map.values().iterator().next();
        }
        return null;
    }
}
