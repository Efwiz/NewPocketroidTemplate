package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.util.Textifier;
import org.jetbrains.org.objectweb.asm.util.TraceMethodVisitor;

/* JADX INFO: compiled from: TransformationMethodVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001:\u0001\u0019BK\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\u0010\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0004¢\u0006\u0002\u0010\fJ\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH$J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000eH\u0002J\b\u0010\u0018\u001a\u00020\u0014H\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/codegen/TransformationMethodVisitor;", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "access", "", "name", "", "desc", "signature", "exceptions", "", "api", "(Lorg/jetbrains/org/objectweb/asm/MethodVisitor;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;I)V", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "traceMethodVisitorIfPossible", "Lorg/jetbrains/org/objectweb/asm/util/TraceMethodVisitor;", "getTraceMethodVisitorIfPossible", "()Lorg/jetbrains/org/objectweb/asm/util/TraceMethodVisitor;", "performTransformations", "", "shouldBeTransformed", "", "node", "visitEnd", "EndIgnoringMethodVisitorDecorator", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TransformationMethodVisitor extends MethodVisitor {
    private final MethodVisitor delegate;
    private final MethodNode methodNode;

    protected abstract void performTransformations(MethodNode methodNode);

    public /* synthetic */ TransformationMethodVisitor(MethodVisitor methodVisitor, int i, String str, String str2, String str3, String[] strArr, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(methodVisitor, i, str, str2, str3, strArr, (i3 & 64) != 0 ? 589824 : i2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TransformationMethodVisitor(MethodVisitor delegate, int i, String name2, String desc, String str, String[] strArr, int i2) {
        super(i2);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        this.delegate = delegate;
        MethodNode methodNode = new MethodNode(i, name2, desc, str, strArr);
        methodNode.localVariables = new ArrayList(5);
        this.methodNode = methodNode;
        this.mv = InlineCodegenUtilsKt.wrapWithMaxLocalCalc(methodNode);
    }

    public final TraceMethodVisitor getTraceMethodVisitorIfPossible() {
        TraceMethodVisitor traceMethodVisitor = new TraceMethodVisitor(new Textifier());
        try {
            this.methodNode.accept(traceMethodVisitor);
            return traceMethodVisitor;
        } catch (Throwable unused) {
            return null;
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitEnd() {
        if (this.methodNode.maxLocals <= 0 || this.methodNode.maxStack <= 0) {
            this.mv.visitMaxs(-1, -1);
        }
        super.visitEnd();
        try {
            if (shouldBeTransformed(this.methodNode)) {
                performTransformations(this.methodNode);
            }
            this.methodNode.accept(new EndIgnoringMethodVisitorDecorator(589824, this.delegate));
            if (this.methodNode.instructions.size() == 0 && (!(this.delegate instanceof MethodNode) || this.methodNode.localVariables != null)) {
                List<LocalVariableNode> list = this.methodNode.localVariables;
                int size = list != null ? list.size() : 0;
                for (int i = 0; i < size; i++) {
                    Intrinsics.checkNotNull(list);
                    list.get(i).accept(this.delegate);
                }
            }
            this.delegate.visitEnd();
        } catch (Throwable th) {
            throw new CompilationException("Couldn't transform method node:\n" + InlineCodegenUtilsKt.getNodeText(this.methodNode), th, null);
        }
    }

    /* JADX INFO: compiled from: TransformationMethodVisitor.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/TransformationMethodVisitor$EndIgnoringMethodVisitorDecorator;", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "api", "", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "(ILorg/jetbrains/org/objectweb/asm/MethodVisitor;)V", "visitEnd", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class EndIgnoringMethodVisitorDecorator extends MethodVisitor {
        @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
        public void visitEnd() {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EndIgnoringMethodVisitorDecorator(int i, MethodVisitor mv) {
            super(i, mv);
            Intrinsics.checkNotNullParameter(mv, "mv");
        }
    }

    private final boolean shouldBeTransformed(MethodNode node) {
        return node.instructions.size() > 0;
    }
}
