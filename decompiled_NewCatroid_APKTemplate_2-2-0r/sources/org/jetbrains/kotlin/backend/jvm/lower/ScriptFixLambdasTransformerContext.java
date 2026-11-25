package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ScriptLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptFixLambdasTransformerContext;", "", "insideTopLevelDestructuringDeclaration", "", "valueParameterToReplaceWithScript", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(ZLorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "getInsideTopLevelDestructuringDeclaration", "()Z", "getValueParameterToReplaceWithScript", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScriptFixLambdasTransformerContext {
    private final boolean insideTopLevelDestructuringDeclaration;
    private final IrValueParameter valueParameterToReplaceWithScript;

    /* JADX WARN: Multi-variable type inference failed */
    public ScriptFixLambdasTransformerContext() {
        this(false, null, 3, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ ScriptFixLambdasTransformerContext copy$default(ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext, boolean z, IrValueParameter irValueParameter, int i, Object obj) {
        if ((i & 1) != 0) {
            z = scriptFixLambdasTransformerContext.insideTopLevelDestructuringDeclaration;
        }
        if ((i & 2) != 0) {
            irValueParameter = scriptFixLambdasTransformerContext.valueParameterToReplaceWithScript;
        }
        return scriptFixLambdasTransformerContext.copy(z, irValueParameter);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final boolean getInsideTopLevelDestructuringDeclaration() {
        return this.insideTopLevelDestructuringDeclaration;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final IrValueParameter getValueParameterToReplaceWithScript() {
        return this.valueParameterToReplaceWithScript;
    }

    public final ScriptFixLambdasTransformerContext copy(boolean insideTopLevelDestructuringDeclaration, IrValueParameter valueParameterToReplaceWithScript) {
        return new ScriptFixLambdasTransformerContext(insideTopLevelDestructuringDeclaration, valueParameterToReplaceWithScript);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScriptFixLambdasTransformerContext)) {
            return false;
        }
        ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext = (ScriptFixLambdasTransformerContext) other;
        return this.insideTopLevelDestructuringDeclaration == scriptFixLambdasTransformerContext.insideTopLevelDestructuringDeclaration && Intrinsics.areEqual(this.valueParameterToReplaceWithScript, scriptFixLambdasTransformerContext.valueParameterToReplaceWithScript);
    }

    public int hashCode() {
        int iHashCode = Boolean.hashCode(this.insideTopLevelDestructuringDeclaration) * 31;
        IrValueParameter irValueParameter = this.valueParameterToReplaceWithScript;
        return iHashCode + (irValueParameter == null ? 0 : irValueParameter.hashCode());
    }

    public String toString() {
        return "ScriptFixLambdasTransformerContext(insideTopLevelDestructuringDeclaration=" + this.insideTopLevelDestructuringDeclaration + ", valueParameterToReplaceWithScript=" + this.valueParameterToReplaceWithScript + ')';
    }

    public ScriptFixLambdasTransformerContext(boolean z, IrValueParameter irValueParameter) {
        this.insideTopLevelDestructuringDeclaration = z;
        this.valueParameterToReplaceWithScript = irValueParameter;
    }

    public /* synthetic */ ScriptFixLambdasTransformerContext(boolean z, IrValueParameter irValueParameter, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? null : irValueParameter);
    }

    public final boolean getInsideTopLevelDestructuringDeclaration() {
        return this.insideTopLevelDestructuringDeclaration;
    }

    public final IrValueParameter getValueParameterToReplaceWithScript() {
        return this.valueParameterToReplaceWithScript;
    }
}
