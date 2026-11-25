package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeFunctionExpectedError;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "expression", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "getExpression", "()Ljava/lang/String;", "reason", "getReason", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeFunctionExpectedError implements ConeDiagnostic {
    private final String expression;
    private final ConeKotlinType type;

    public ConeFunctionExpectedError(String expression, ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        this.expression = expression;
        this.type = type;
    }

    public final String getExpression() {
        return this.expression;
    }

    public final ConeKotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Expression '" + this.expression + "' of type '" + this.type + "' cannot be invoked as a function";
    }
}
