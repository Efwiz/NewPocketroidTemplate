package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicVerifier;

/* JADX INFO: compiled from: MethodVerifier.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/MethodVerifier;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "checkPoint", "", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "transform", "", "internalClassName", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MethodVerifier extends MethodTransformer {
    private final String checkPoint;
    private final GenerationState generationState;

    public MethodVerifier(String checkPoint, GenerationState generationState) {
        Intrinsics.checkNotNullParameter(checkPoint, "checkPoint");
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        this.checkPoint = checkPoint;
        this.generationState = generationState;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        if (this.generationState.getConfig().getShouldValidateBytecode()) {
            try {
                MethodTransformer.analyze(internalClassName, methodNode, new BasicVerifier());
            } catch (Throwable th) {
                throw new AssertionError(this.checkPoint + ": incorrect bytecode", th);
            }
        }
    }
}
