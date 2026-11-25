package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: SMAP.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "classSMAP", "Lorg/jetbrains/kotlin/codegen/inline/SMAP;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lorg/jetbrains/kotlin/codegen/inline/SMAP;)V", "getClassSMAP", "()Lorg/jetbrains/kotlin/codegen/inline/SMAP;", "getNode", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SMAPAndMethodNode {
    private final SMAP classSMAP;
    private final MethodNode node;

    public static /* synthetic */ SMAPAndMethodNode copy$default(SMAPAndMethodNode sMAPAndMethodNode, MethodNode methodNode, SMAP smap, int i, Object obj) {
        if ((i & 1) != 0) {
            methodNode = sMAPAndMethodNode.node;
        }
        if ((i & 2) != 0) {
            smap = sMAPAndMethodNode.classSMAP;
        }
        return sMAPAndMethodNode.copy(methodNode, smap);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final MethodNode getNode() {
        return this.node;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final SMAP getClassSMAP() {
        return this.classSMAP;
    }

    public final SMAPAndMethodNode copy(MethodNode node, SMAP classSMAP) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(classSMAP, "classSMAP");
        return new SMAPAndMethodNode(node, classSMAP);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SMAPAndMethodNode)) {
            return false;
        }
        SMAPAndMethodNode sMAPAndMethodNode = (SMAPAndMethodNode) other;
        return Intrinsics.areEqual(this.node, sMAPAndMethodNode.node) && Intrinsics.areEqual(this.classSMAP, sMAPAndMethodNode.classSMAP);
    }

    public int hashCode() {
        return (this.node.hashCode() * 31) + this.classSMAP.hashCode();
    }

    public String toString() {
        return "SMAPAndMethodNode(node=" + this.node + ", classSMAP=" + this.classSMAP + ')';
    }

    public SMAPAndMethodNode(MethodNode node, SMAP classSMAP) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(classSMAP, "classSMAP");
        this.node = node;
        this.classSMAP = classSMAP;
    }

    public final SMAP getClassSMAP() {
        return this.classSMAP;
    }

    public final MethodNode getNode() {
        return this.node;
    }
}
