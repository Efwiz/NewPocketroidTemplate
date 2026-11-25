package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;

/* JADX INFO: compiled from: KotlinResolutionCallbacksImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\tHÆ\u0003J9\u0010\u001f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tHÆ\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020$HÖ\u0001J\t\u0010%\u001a\u00020&HÖ\u0001R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/LambdaContextInfo;", "", "typeInfo", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "dataFlowInfoAfter", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "lexicalScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "(Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "getDataFlowInfoAfter", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "setDataFlowInfoAfter", "(Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;)V", "getLexicalScope", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "setLexicalScope", "(Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;)V", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "setTrace", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "getTypeInfo", "()Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "setTypeInfo", "(Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;)V", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LambdaContextInfo {
    private DataFlowInfo dataFlowInfoAfter;
    private LexicalScope lexicalScope;
    private BindingTrace trace;
    private KotlinTypeInfo typeInfo;

    public LambdaContextInfo() {
        this(null, null, null, null, 15, null);
    }

    public static /* synthetic */ LambdaContextInfo copy$default(LambdaContextInfo lambdaContextInfo, KotlinTypeInfo kotlinTypeInfo, DataFlowInfo dataFlowInfo, LexicalScope lexicalScope, BindingTrace bindingTrace, int i, Object obj) {
        if ((i & 1) != 0) {
            kotlinTypeInfo = lambdaContextInfo.typeInfo;
        }
        if ((i & 2) != 0) {
            dataFlowInfo = lambdaContextInfo.dataFlowInfoAfter;
        }
        if ((i & 4) != 0) {
            lexicalScope = lambdaContextInfo.lexicalScope;
        }
        if ((i & 8) != 0) {
            bindingTrace = lambdaContextInfo.trace;
        }
        return lambdaContextInfo.copy(kotlinTypeInfo, dataFlowInfo, lexicalScope, bindingTrace);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KotlinTypeInfo getTypeInfo() {
        return this.typeInfo;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final DataFlowInfo getDataFlowInfoAfter() {
        return this.dataFlowInfoAfter;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final LexicalScope getLexicalScope() {
        return this.lexicalScope;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final BindingTrace getTrace() {
        return this.trace;
    }

    public final LambdaContextInfo copy(KotlinTypeInfo typeInfo, DataFlowInfo dataFlowInfoAfter, LexicalScope lexicalScope, BindingTrace trace) {
        return new LambdaContextInfo(typeInfo, dataFlowInfoAfter, lexicalScope, trace);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LambdaContextInfo)) {
            return false;
        }
        LambdaContextInfo lambdaContextInfo = (LambdaContextInfo) other;
        return Intrinsics.areEqual(this.typeInfo, lambdaContextInfo.typeInfo) && Intrinsics.areEqual(this.dataFlowInfoAfter, lambdaContextInfo.dataFlowInfoAfter) && Intrinsics.areEqual(this.lexicalScope, lambdaContextInfo.lexicalScope) && Intrinsics.areEqual(this.trace, lambdaContextInfo.trace);
    }

    public int hashCode() {
        KotlinTypeInfo kotlinTypeInfo = this.typeInfo;
        int iHashCode = (kotlinTypeInfo == null ? 0 : kotlinTypeInfo.hashCode()) * 31;
        DataFlowInfo dataFlowInfo = this.dataFlowInfoAfter;
        int iHashCode2 = (iHashCode + (dataFlowInfo == null ? 0 : dataFlowInfo.hashCode())) * 31;
        LexicalScope lexicalScope = this.lexicalScope;
        int iHashCode3 = (iHashCode2 + (lexicalScope == null ? 0 : lexicalScope.hashCode())) * 31;
        BindingTrace bindingTrace = this.trace;
        return iHashCode3 + (bindingTrace != null ? bindingTrace.hashCode() : 0);
    }

    public String toString() {
        return "LambdaContextInfo(typeInfo=" + this.typeInfo + ", dataFlowInfoAfter=" + this.dataFlowInfoAfter + ", lexicalScope=" + this.lexicalScope + ", trace=" + this.trace + ')';
    }

    public LambdaContextInfo(KotlinTypeInfo kotlinTypeInfo, DataFlowInfo dataFlowInfo, LexicalScope lexicalScope, BindingTrace bindingTrace) {
        this.typeInfo = kotlinTypeInfo;
        this.dataFlowInfoAfter = dataFlowInfo;
        this.lexicalScope = lexicalScope;
        this.trace = bindingTrace;
    }

    public /* synthetic */ LambdaContextInfo(KotlinTypeInfo kotlinTypeInfo, DataFlowInfo dataFlowInfo, LexicalScope lexicalScope, BindingTrace bindingTrace, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : kotlinTypeInfo, (i & 2) != 0 ? null : dataFlowInfo, (i & 4) != 0 ? null : lexicalScope, (i & 8) != 0 ? null : bindingTrace);
    }

    public final KotlinTypeInfo getTypeInfo() {
        return this.typeInfo;
    }

    public final void setTypeInfo(KotlinTypeInfo kotlinTypeInfo) {
        this.typeInfo = kotlinTypeInfo;
    }

    public final DataFlowInfo getDataFlowInfoAfter() {
        return this.dataFlowInfoAfter;
    }

    public final void setDataFlowInfoAfter(DataFlowInfo dataFlowInfo) {
        this.dataFlowInfoAfter = dataFlowInfo;
    }

    public final LexicalScope getLexicalScope() {
        return this.lexicalScope;
    }

    public final void setLexicalScope(LexicalScope lexicalScope) {
        this.lexicalScope = lexicalScope;
    }

    public final BindingTrace getTrace() {
        return this.trace;
    }

    public final void setTrace(BindingTrace bindingTrace) {
        this.trace = bindingTrace;
    }
}
