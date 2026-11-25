package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: compiled from: OptimizationClassBuilder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\u0003H\u0016JI\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u000f2\u0010\u0010\u0012\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000f\u0018\u00010\u0013H\u0016¢\u0006\u0002\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/OptimizationClassBuilder;", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/ClassBuilder;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "getDelegate", "newMethod", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "origin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "access", "", "name", "", "desc", "signature", "exceptions", "", "(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OptimizationClassBuilder extends DelegatingClassBuilder {
    private final ClassBuilder delegate;
    private final GenerationState generationState;

    public OptimizationClassBuilder(ClassBuilder delegate, GenerationState generationState) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        this.delegate = delegate;
        this.generationState = generationState;
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder
    public ClassBuilder getDelegate() {
        return this.delegate;
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public MethodVisitor newMethod(JvmDeclarationOrigin origin, int access, String name2, String desc, String signature, String[] exceptions) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        MethodVisitor methodVisitorNewMethod = super.newMethod(origin, access, name2, desc, signature, exceptions);
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        return new OptimizationMethodVisitor(methodVisitorNewMethod, origin.getOriginKind() == JvmDeclarationOriginKind.INLINE_VERSION_OF_SUSPEND_FUN, this.generationState, access, name2, desc, signature, exceptions);
    }
}
