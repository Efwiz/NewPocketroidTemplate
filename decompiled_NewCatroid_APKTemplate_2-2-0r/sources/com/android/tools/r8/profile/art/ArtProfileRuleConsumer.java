package com.android.tools.r8.profile.art;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ArtProfileRuleConsumer {
    void acceptClassRule(ClassReference classReference, ArtProfileClassRuleInfo artProfileClassRuleInfo);

    void acceptMethodRule(MethodReference methodReference, ArtProfileMethodRuleInfo artProfileMethodRuleInfo);
}
