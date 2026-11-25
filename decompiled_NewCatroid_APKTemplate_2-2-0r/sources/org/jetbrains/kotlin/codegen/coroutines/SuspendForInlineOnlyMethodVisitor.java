package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.TransformationMethodVisitor;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: SuspendFunctionGenerationStrategy.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/SuspendForInlineOnlyMethodVisitor;", "Lorg/jetbrains/kotlin/codegen/TransformationMethodVisitor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "access", "", "name", "", "desc", "(Lorg/jetbrains/org/objectweb/asm/MethodVisitor;ILjava/lang/String;Ljava/lang/String;)V", "performTransformations", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SuspendForInlineOnlyMethodVisitor extends TransformationMethodVisitor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuspendForInlineOnlyMethodVisitor(MethodVisitor delegate, int i, String name2, String desc) {
        super(delegate, i, name2, desc, null, null, 0, 64, null);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
    }

    @Override // org.jetbrains.kotlin.codegen.TransformationMethodVisitor
    protected void performTransformations(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        InlineCodegenUtilsKt.preprocessSuspendMarkers(methodNode, true, false);
    }
}
