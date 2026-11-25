package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ScriptLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\bHÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\nHÆ\u0003JC\u0010\u0019\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptToClassTransformerContext;", "", "valueParameterForScriptThis", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "fieldForScriptThis", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "valueParameterForFieldReceiver", "isInScriptConstructor", "", "topLevelDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;ZLorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "getFieldForScriptThis", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "()Z", "getTopLevelDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getValueParameterForFieldReceiver", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getValueParameterForScriptThis", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScriptToClassTransformerContext {
    private final IrFieldSymbol fieldForScriptThis;
    private final boolean isInScriptConstructor;
    private final IrDeclaration topLevelDeclaration;
    private final IrValueParameterSymbol valueParameterForFieldReceiver;
    private final IrValueParameterSymbol valueParameterForScriptThis;

    public static /* synthetic */ ScriptToClassTransformerContext copy$default(ScriptToClassTransformerContext scriptToClassTransformerContext, IrValueParameterSymbol irValueParameterSymbol, IrFieldSymbol irFieldSymbol, IrValueParameterSymbol irValueParameterSymbol2, boolean z, IrDeclaration irDeclaration, int i, Object obj) {
        if ((i & 1) != 0) {
            irValueParameterSymbol = scriptToClassTransformerContext.valueParameterForScriptThis;
        }
        if ((i & 2) != 0) {
            irFieldSymbol = scriptToClassTransformerContext.fieldForScriptThis;
        }
        IrFieldSymbol irFieldSymbol2 = irFieldSymbol;
        if ((i & 4) != 0) {
            irValueParameterSymbol2 = scriptToClassTransformerContext.valueParameterForFieldReceiver;
        }
        IrValueParameterSymbol irValueParameterSymbol3 = irValueParameterSymbol2;
        if ((i & 8) != 0) {
            z = scriptToClassTransformerContext.isInScriptConstructor;
        }
        boolean z2 = z;
        if ((i & 16) != 0) {
            irDeclaration = scriptToClassTransformerContext.topLevelDeclaration;
        }
        return scriptToClassTransformerContext.copy(irValueParameterSymbol, irFieldSymbol2, irValueParameterSymbol3, z2, irDeclaration);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final IrValueParameterSymbol getValueParameterForScriptThis() {
        return this.valueParameterForScriptThis;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final IrFieldSymbol getFieldForScriptThis() {
        return this.fieldForScriptThis;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final IrValueParameterSymbol getValueParameterForFieldReceiver() {
        return this.valueParameterForFieldReceiver;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getIsInScriptConstructor() {
        return this.isInScriptConstructor;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final IrDeclaration getTopLevelDeclaration() {
        return this.topLevelDeclaration;
    }

    public final ScriptToClassTransformerContext copy(IrValueParameterSymbol valueParameterForScriptThis, IrFieldSymbol fieldForScriptThis, IrValueParameterSymbol valueParameterForFieldReceiver, boolean isInScriptConstructor, IrDeclaration topLevelDeclaration) {
        return new ScriptToClassTransformerContext(valueParameterForScriptThis, fieldForScriptThis, valueParameterForFieldReceiver, isInScriptConstructor, topLevelDeclaration);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScriptToClassTransformerContext)) {
            return false;
        }
        ScriptToClassTransformerContext scriptToClassTransformerContext = (ScriptToClassTransformerContext) other;
        return Intrinsics.areEqual(this.valueParameterForScriptThis, scriptToClassTransformerContext.valueParameterForScriptThis) && Intrinsics.areEqual(this.fieldForScriptThis, scriptToClassTransformerContext.fieldForScriptThis) && Intrinsics.areEqual(this.valueParameterForFieldReceiver, scriptToClassTransformerContext.valueParameterForFieldReceiver) && this.isInScriptConstructor == scriptToClassTransformerContext.isInScriptConstructor && Intrinsics.areEqual(this.topLevelDeclaration, scriptToClassTransformerContext.topLevelDeclaration);
    }

    public int hashCode() {
        IrValueParameterSymbol irValueParameterSymbol = this.valueParameterForScriptThis;
        int iHashCode = (irValueParameterSymbol == null ? 0 : irValueParameterSymbol.hashCode()) * 31;
        IrFieldSymbol irFieldSymbol = this.fieldForScriptThis;
        int iHashCode2 = (iHashCode + (irFieldSymbol == null ? 0 : irFieldSymbol.hashCode())) * 31;
        IrValueParameterSymbol irValueParameterSymbol2 = this.valueParameterForFieldReceiver;
        int iHashCode3 = (((iHashCode2 + (irValueParameterSymbol2 == null ? 0 : irValueParameterSymbol2.hashCode())) * 31) + Boolean.hashCode(this.isInScriptConstructor)) * 31;
        IrDeclaration irDeclaration = this.topLevelDeclaration;
        return iHashCode3 + (irDeclaration != null ? irDeclaration.hashCode() : 0);
    }

    public String toString() {
        return "ScriptToClassTransformerContext(valueParameterForScriptThis=" + this.valueParameterForScriptThis + ", fieldForScriptThis=" + this.fieldForScriptThis + ", valueParameterForFieldReceiver=" + this.valueParameterForFieldReceiver + ", isInScriptConstructor=" + this.isInScriptConstructor + ", topLevelDeclaration=" + this.topLevelDeclaration + ')';
    }

    public ScriptToClassTransformerContext(IrValueParameterSymbol irValueParameterSymbol, IrFieldSymbol irFieldSymbol, IrValueParameterSymbol irValueParameterSymbol2, boolean z, IrDeclaration irDeclaration) {
        this.valueParameterForScriptThis = irValueParameterSymbol;
        this.fieldForScriptThis = irFieldSymbol;
        this.valueParameterForFieldReceiver = irValueParameterSymbol2;
        this.isInScriptConstructor = z;
        this.topLevelDeclaration = irDeclaration;
    }

    public /* synthetic */ ScriptToClassTransformerContext(IrValueParameterSymbol irValueParameterSymbol, IrFieldSymbol irFieldSymbol, IrValueParameterSymbol irValueParameterSymbol2, boolean z, IrDeclaration irDeclaration, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irValueParameterSymbol, irFieldSymbol, irValueParameterSymbol2, z, (i & 16) != 0 ? null : irDeclaration);
    }

    public final IrValueParameterSymbol getValueParameterForScriptThis() {
        return this.valueParameterForScriptThis;
    }

    public final IrFieldSymbol getFieldForScriptThis() {
        return this.fieldForScriptThis;
    }

    public final IrValueParameterSymbol getValueParameterForFieldReceiver() {
        return this.valueParameterForFieldReceiver;
    }

    public final boolean isInScriptConstructor() {
        return this.isInScriptConstructor;
    }

    public final IrDeclaration getTopLevelDeclaration() {
        return this.topLevelDeclaration;
    }
}
