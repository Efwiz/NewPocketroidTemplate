package org.jetbrains.kotlin.com.intellij.psi.scope;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.infos.CandidateInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiConflictResolver {
    CandidateInfo resolveConflict(List<CandidateInfo> list);
}
