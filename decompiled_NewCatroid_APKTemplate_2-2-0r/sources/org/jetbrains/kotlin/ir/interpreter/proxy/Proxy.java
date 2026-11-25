package org.jetbrains.kotlin.ir.interpreter.proxy;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Proxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b`\u0018\u00002\u00020\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H¦\u0002J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/Proxy;", "", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "getCallInterceptor", "()Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getEnvironment", "()Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "getState", "()Lorg/jetbrains/kotlin/ir/interpreter/state/State;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Proxy {
    boolean equals(Object other);

    CallInterceptor getCallInterceptor();

    State getState();

    int hashCode();

    String toString();

    default IrInterpreterEnvironment getEnvironment() {
        return getCallInterceptor().getEnvironment();
    }
}
