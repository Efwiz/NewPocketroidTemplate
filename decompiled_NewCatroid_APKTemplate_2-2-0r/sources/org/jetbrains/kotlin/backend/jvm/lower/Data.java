package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: RemoveDuplicatedInlinedLocalClassesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00032\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\nR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\b\"\u0004\b\u000e\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/Data;", "", "classDeclaredOnCallSiteOrIsDefaultLambda", "", "insideInlineBlock", "modifyTree", "(ZZZ)V", "getClassDeclaredOnCallSiteOrIsDefaultLambda", "()Z", "setClassDeclaredOnCallSiteOrIsDefaultLambda", "(Z)V", "getInsideInlineBlock", "setInsideInlineBlock", "getModifyTree", "setModifyTree", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class Data {
    private boolean classDeclaredOnCallSiteOrIsDefaultLambda;
    private boolean insideInlineBlock;
    private boolean modifyTree;

    public Data() {
        this(false, false, false, 7, null);
    }

    public static /* synthetic */ Data copy$default(Data data, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 1) != 0) {
            z = data.classDeclaredOnCallSiteOrIsDefaultLambda;
        }
        if ((i & 2) != 0) {
            z2 = data.insideInlineBlock;
        }
        if ((i & 4) != 0) {
            z3 = data.modifyTree;
        }
        return data.copy(z, z2, z3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final boolean getClassDeclaredOnCallSiteOrIsDefaultLambda() {
        return this.classDeclaredOnCallSiteOrIsDefaultLambda;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getInsideInlineBlock() {
        return this.insideInlineBlock;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getModifyTree() {
        return this.modifyTree;
    }

    public final Data copy(boolean classDeclaredOnCallSiteOrIsDefaultLambda, boolean insideInlineBlock, boolean modifyTree) {
        return new Data(classDeclaredOnCallSiteOrIsDefaultLambda, insideInlineBlock, modifyTree);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Data)) {
            return false;
        }
        Data data = (Data) other;
        return this.classDeclaredOnCallSiteOrIsDefaultLambda == data.classDeclaredOnCallSiteOrIsDefaultLambda && this.insideInlineBlock == data.insideInlineBlock && this.modifyTree == data.modifyTree;
    }

    public int hashCode() {
        return (((Boolean.hashCode(this.classDeclaredOnCallSiteOrIsDefaultLambda) * 31) + Boolean.hashCode(this.insideInlineBlock)) * 31) + Boolean.hashCode(this.modifyTree);
    }

    public String toString() {
        return "Data(classDeclaredOnCallSiteOrIsDefaultLambda=" + this.classDeclaredOnCallSiteOrIsDefaultLambda + ", insideInlineBlock=" + this.insideInlineBlock + ", modifyTree=" + this.modifyTree + ')';
    }

    public Data(boolean z, boolean z2, boolean z3) {
        this.classDeclaredOnCallSiteOrIsDefaultLambda = z;
        this.insideInlineBlock = z2;
        this.modifyTree = z3;
    }

    public /* synthetic */ Data(boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? true : z3);
    }

    public final boolean getClassDeclaredOnCallSiteOrIsDefaultLambda() {
        return this.classDeclaredOnCallSiteOrIsDefaultLambda;
    }

    public final void setClassDeclaredOnCallSiteOrIsDefaultLambda(boolean z) {
        this.classDeclaredOnCallSiteOrIsDefaultLambda = z;
    }

    public final boolean getInsideInlineBlock() {
        return this.insideInlineBlock;
    }

    public final void setInsideInlineBlock(boolean z) {
        this.insideInlineBlock = z;
    }

    public final boolean getModifyTree() {
        return this.modifyTree;
    }

    public final void setModifyTree(boolean z) {
        this.modifyTree = z;
    }
}
