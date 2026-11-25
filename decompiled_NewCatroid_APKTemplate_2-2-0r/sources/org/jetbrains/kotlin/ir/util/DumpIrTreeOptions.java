package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: DumpIrTree.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003JE\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DumpIrTreeOptions;", "", "normalizeNames", "", "stableOrder", "verboseErrorTypes", "printFacadeClassInFqNames", "printFlagsInDeclarationReferences", "printSignatures", "(ZZZZZZ)V", "getNormalizeNames", "()Z", "getPrintFacadeClassInFqNames", "getPrintFlagsInDeclarationReferences", "getPrintSignatures", "getStableOrder", "getVerboseErrorTypes", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DumpIrTreeOptions {
    private final boolean normalizeNames;
    private final boolean printFacadeClassInFqNames;
    private final boolean printFlagsInDeclarationReferences;
    private final boolean printSignatures;
    private final boolean stableOrder;
    private final boolean verboseErrorTypes;

    public DumpIrTreeOptions() {
        this(false, false, false, false, false, false, 63, null);
    }

    public static /* synthetic */ DumpIrTreeOptions copy$default(DumpIrTreeOptions dumpIrTreeOptions, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, Object obj) {
        if ((i & 1) != 0) {
            z = dumpIrTreeOptions.normalizeNames;
        }
        if ((i & 2) != 0) {
            z2 = dumpIrTreeOptions.stableOrder;
        }
        boolean z7 = z2;
        if ((i & 4) != 0) {
            z3 = dumpIrTreeOptions.verboseErrorTypes;
        }
        boolean z8 = z3;
        if ((i & 8) != 0) {
            z4 = dumpIrTreeOptions.printFacadeClassInFqNames;
        }
        boolean z9 = z4;
        if ((i & 16) != 0) {
            z5 = dumpIrTreeOptions.printFlagsInDeclarationReferences;
        }
        boolean z10 = z5;
        if ((i & 32) != 0) {
            z6 = dumpIrTreeOptions.printSignatures;
        }
        return dumpIrTreeOptions.copy(z, z7, z8, z9, z10, z6);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final boolean getNormalizeNames() {
        return this.normalizeNames;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getStableOrder() {
        return this.stableOrder;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getVerboseErrorTypes() {
        return this.verboseErrorTypes;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getPrintFacadeClassInFqNames() {
        return this.printFacadeClassInFqNames;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getPrintFlagsInDeclarationReferences() {
        return this.printFlagsInDeclarationReferences;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final boolean getPrintSignatures() {
        return this.printSignatures;
    }

    public final DumpIrTreeOptions copy(boolean normalizeNames, boolean stableOrder, boolean verboseErrorTypes, boolean printFacadeClassInFqNames, boolean printFlagsInDeclarationReferences, boolean printSignatures) {
        return new DumpIrTreeOptions(normalizeNames, stableOrder, verboseErrorTypes, printFacadeClassInFqNames, printFlagsInDeclarationReferences, printSignatures);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DumpIrTreeOptions)) {
            return false;
        }
        DumpIrTreeOptions dumpIrTreeOptions = (DumpIrTreeOptions) other;
        return this.normalizeNames == dumpIrTreeOptions.normalizeNames && this.stableOrder == dumpIrTreeOptions.stableOrder && this.verboseErrorTypes == dumpIrTreeOptions.verboseErrorTypes && this.printFacadeClassInFqNames == dumpIrTreeOptions.printFacadeClassInFqNames && this.printFlagsInDeclarationReferences == dumpIrTreeOptions.printFlagsInDeclarationReferences && this.printSignatures == dumpIrTreeOptions.printSignatures;
    }

    public int hashCode() {
        return (((((((((Boolean.hashCode(this.normalizeNames) * 31) + Boolean.hashCode(this.stableOrder)) * 31) + Boolean.hashCode(this.verboseErrorTypes)) * 31) + Boolean.hashCode(this.printFacadeClassInFqNames)) * 31) + Boolean.hashCode(this.printFlagsInDeclarationReferences)) * 31) + Boolean.hashCode(this.printSignatures);
    }

    public String toString() {
        return "DumpIrTreeOptions(normalizeNames=" + this.normalizeNames + ", stableOrder=" + this.stableOrder + ", verboseErrorTypes=" + this.verboseErrorTypes + ", printFacadeClassInFqNames=" + this.printFacadeClassInFqNames + ", printFlagsInDeclarationReferences=" + this.printFlagsInDeclarationReferences + ", printSignatures=" + this.printSignatures + ')';
    }

    public DumpIrTreeOptions(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.normalizeNames = z;
        this.stableOrder = z2;
        this.verboseErrorTypes = z3;
        this.printFacadeClassInFqNames = z4;
        this.printFlagsInDeclarationReferences = z5;
        this.printSignatures = z6;
    }

    public /* synthetic */ DumpIrTreeOptions(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? true : z3, (i & 8) != 0 ? true : z4, (i & 16) != 0 ? true : z5, (i & 32) != 0 ? false : z6);
    }

    public final boolean getNormalizeNames() {
        return this.normalizeNames;
    }

    public final boolean getStableOrder() {
        return this.stableOrder;
    }

    public final boolean getVerboseErrorTypes() {
        return this.verboseErrorTypes;
    }

    public final boolean getPrintFacadeClassInFqNames() {
        return this.printFacadeClassInFqNames;
    }

    public final boolean getPrintFlagsInDeclarationReferences() {
        return this.printFlagsInDeclarationReferences;
    }

    public final boolean getPrintSignatures() {
        return this.printSignatures;
    }
}
