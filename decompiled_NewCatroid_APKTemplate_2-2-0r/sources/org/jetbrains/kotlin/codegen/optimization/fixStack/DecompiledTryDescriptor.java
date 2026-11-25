package org.jetbrains.kotlin.codegen.optimization.fixStack;

import com.reandroid.dex.sections.Marker;
import java.util.HashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: AnalyzeTryCatchBlocks.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR!\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\u00030\fj\b\u0012\u0004\u0012\u00020\u0003`\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/DecompiledTryDescriptor;", "", "tryStartLabel", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;)V", "defaultHandlerTcb", "Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "getDefaultHandlerTcb", "()Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "setDefaultHandlerTcb", "(Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;)V", "handlerStartLabels", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "getHandlerStartLabels", "()Ljava/util/HashSet;", "getTryStartLabel", "()Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DecompiledTryDescriptor {
    private TryCatchBlockNode defaultHandlerTcb;
    private final HashSet<LabelNode> handlerStartLabels;
    private final LabelNode tryStartLabel;

    public DecompiledTryDescriptor(LabelNode tryStartLabel) {
        Intrinsics.checkNotNullParameter(tryStartLabel, "tryStartLabel");
        this.tryStartLabel = tryStartLabel;
        this.handlerStartLabels = new HashSet<>();
    }

    public final LabelNode getTryStartLabel() {
        return this.tryStartLabel;
    }

    public final TryCatchBlockNode getDefaultHandlerTcb() {
        return this.defaultHandlerTcb;
    }

    public final void setDefaultHandlerTcb(TryCatchBlockNode tryCatchBlockNode) {
        this.defaultHandlerTcb = tryCatchBlockNode;
    }

    public final HashSet<LabelNode> getHandlerStartLabels() {
        return this.handlerStartLabels;
    }
}
