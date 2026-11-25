package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrValidator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003JE\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/IrValidatorConfig;", "", "abortOnError", "", "ensureAllNodesAreDifferent", "checkTypes", "checkDescriptors", "checkProperties", "checkScopes", "(ZZZZZZ)V", "getAbortOnError", "()Z", "getCheckDescriptors", "getCheckProperties", "getCheckScopes", "getCheckTypes", "getEnsureAllNodesAreDifferent", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IrValidatorConfig {
    private final boolean abortOnError;
    private final boolean checkDescriptors;
    private final boolean checkProperties;
    private final boolean checkScopes;
    private final boolean checkTypes;
    private final boolean ensureAllNodesAreDifferent;

    public static /* synthetic */ IrValidatorConfig copy$default(IrValidatorConfig irValidatorConfig, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, Object obj) {
        if ((i & 1) != 0) {
            z = irValidatorConfig.abortOnError;
        }
        if ((i & 2) != 0) {
            z2 = irValidatorConfig.ensureAllNodesAreDifferent;
        }
        boolean z7 = z2;
        if ((i & 4) != 0) {
            z3 = irValidatorConfig.checkTypes;
        }
        boolean z8 = z3;
        if ((i & 8) != 0) {
            z4 = irValidatorConfig.checkDescriptors;
        }
        boolean z9 = z4;
        if ((i & 16) != 0) {
            z5 = irValidatorConfig.checkProperties;
        }
        boolean z10 = z5;
        if ((i & 32) != 0) {
            z6 = irValidatorConfig.checkScopes;
        }
        return irValidatorConfig.copy(z, z7, z8, z9, z10, z6);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final boolean getAbortOnError() {
        return this.abortOnError;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getEnsureAllNodesAreDifferent() {
        return this.ensureAllNodesAreDifferent;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getCheckTypes() {
        return this.checkTypes;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getCheckDescriptors() {
        return this.checkDescriptors;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getCheckProperties() {
        return this.checkProperties;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final boolean getCheckScopes() {
        return this.checkScopes;
    }

    public final IrValidatorConfig copy(boolean abortOnError, boolean ensureAllNodesAreDifferent, boolean checkTypes, boolean checkDescriptors, boolean checkProperties, boolean checkScopes) {
        return new IrValidatorConfig(abortOnError, ensureAllNodesAreDifferent, checkTypes, checkDescriptors, checkProperties, checkScopes);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IrValidatorConfig)) {
            return false;
        }
        IrValidatorConfig irValidatorConfig = (IrValidatorConfig) other;
        return this.abortOnError == irValidatorConfig.abortOnError && this.ensureAllNodesAreDifferent == irValidatorConfig.ensureAllNodesAreDifferent && this.checkTypes == irValidatorConfig.checkTypes && this.checkDescriptors == irValidatorConfig.checkDescriptors && this.checkProperties == irValidatorConfig.checkProperties && this.checkScopes == irValidatorConfig.checkScopes;
    }

    public int hashCode() {
        return (((((((((Boolean.hashCode(this.abortOnError) * 31) + Boolean.hashCode(this.ensureAllNodesAreDifferent)) * 31) + Boolean.hashCode(this.checkTypes)) * 31) + Boolean.hashCode(this.checkDescriptors)) * 31) + Boolean.hashCode(this.checkProperties)) * 31) + Boolean.hashCode(this.checkScopes);
    }

    public String toString() {
        return "IrValidatorConfig(abortOnError=" + this.abortOnError + ", ensureAllNodesAreDifferent=" + this.ensureAllNodesAreDifferent + ", checkTypes=" + this.checkTypes + ", checkDescriptors=" + this.checkDescriptors + ", checkProperties=" + this.checkProperties + ", checkScopes=" + this.checkScopes + ')';
    }

    public IrValidatorConfig(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.abortOnError = z;
        this.ensureAllNodesAreDifferent = z2;
        this.checkTypes = z3;
        this.checkDescriptors = z4;
        this.checkProperties = z5;
        this.checkScopes = z6;
    }

    public /* synthetic */ IrValidatorConfig(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, (i & 4) != 0 ? true : z3, (i & 8) != 0 ? true : z4, (i & 16) != 0 ? false : z5, (i & 32) != 0 ? false : z6);
    }

    public final boolean getAbortOnError() {
        return this.abortOnError;
    }

    public final boolean getEnsureAllNodesAreDifferent() {
        return this.ensureAllNodesAreDifferent;
    }

    public final boolean getCheckTypes() {
        return this.checkTypes;
    }

    public final boolean getCheckDescriptors() {
        return this.checkDescriptors;
    }

    public final boolean getCheckProperties() {
        return this.checkProperties;
    }

    public final boolean getCheckScopes() {
        return this.checkScopes;
    }
}
