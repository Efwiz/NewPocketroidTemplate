package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;

/* JADX INFO: compiled from: compat.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a\u0006\u0010\u0000\u001a\u00020\u0001¨\u0006\u0002"}, d2 = {"setupIdeaStandaloneExecution", "", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompatKt {
    public static final void setupIdeaStandaloneExecution() {
        IdeaStandaloneExecutionSetup.INSTANCE.doSetup();
    }
}
