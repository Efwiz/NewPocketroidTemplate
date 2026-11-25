package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.TransformationMethodVisitor;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: CoroutineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/AddEndLabelMethodVisitor;", "Lorg/jetbrains/kotlin/codegen/TransformationMethodVisitor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "access", "", "name", "", "desc", "endLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "(Lorg/jetbrains/org/objectweb/asm/MethodVisitor;ILjava/lang/String;Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/Label;)V", "performTransformations", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class AddEndLabelMethodVisitor extends TransformationMethodVisitor {
    private final Label endLabel;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AddEndLabelMethodVisitor(MethodVisitor delegate, int i, String name2, String desc, Label endLabel) {
        super(delegate, i, name2, desc, null, null, 0, 64, null);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.checkNotNullParameter(endLabel, "endLabel");
        this.endLabel = endLabel;
    }

    @Override // org.jetbrains.kotlin.codegen.TransformationMethodVisitor
    protected void performTransformations(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        InsnList insnList = methodNode.instructions;
        MethodNode methodNode2 = new MethodNode();
        new InstructionAdapter(methodNode2).mark(this.endLabel);
        InsnList instructions = methodNode2.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        insnList.add(instructions);
    }
}
