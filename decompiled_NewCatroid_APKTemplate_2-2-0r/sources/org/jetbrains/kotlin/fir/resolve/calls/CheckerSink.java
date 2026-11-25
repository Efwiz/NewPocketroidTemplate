package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: CheckerSink.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u000e\u0010\u000b\u001a\u00020\bH§@¢\u0006\u0002\u0010\fR\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "", "()V", "needYielding", "", "getNeedYielding", "()Z", "reportDiagnostic", "", "diagnostic", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", PsiKeyword.YIELD, "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CheckerSink {
    public abstract boolean getNeedYielding();

    public abstract void reportDiagnostic(ResolutionDiagnostic diagnostic);

    @PrivateForInline
    public abstract Object yield(Continuation<? super Unit> continuation);
}
