package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AbstractCallInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCallInfo;", "", "()V", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "callSite", "Lorg/jetbrains/kotlin/fir/FirElement;", "getCallSite", "()Lorg/jetbrains/kotlin/fir/FirElement;", "containingFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getContainingFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getExplicitReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isImplicitInvoke", "", "()Z", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractCallInfo {
    public abstract FirArgumentList getArgumentList();

    public abstract FirElement getCallSite();

    public abstract FirFile getContainingFile();

    public abstract FirExpression getExplicitReceiver();

    public abstract Name getName();

    public abstract boolean isImplicitInvoke();
}
