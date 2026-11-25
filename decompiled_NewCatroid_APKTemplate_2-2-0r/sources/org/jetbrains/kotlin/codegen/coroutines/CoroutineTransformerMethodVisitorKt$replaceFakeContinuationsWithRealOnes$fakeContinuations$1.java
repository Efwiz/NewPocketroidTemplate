package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;

/* JADX INFO: compiled from: CoroutineTransformerMethodVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CoroutineTransformerMethodVisitorKt$replaceFakeContinuationsWithRealOnes$fakeContinuations$1 extends FunctionReference implements Function1<AbstractInsnNode, Boolean> {
    public static final CoroutineTransformerMethodVisitorKt$replaceFakeContinuationsWithRealOnes$fakeContinuations$1 INSTANCE = new CoroutineTransformerMethodVisitorKt$replaceFakeContinuationsWithRealOnes$fakeContinuations$1();

    CoroutineTransformerMethodVisitorKt$replaceFakeContinuationsWithRealOnes$fakeContinuations$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "isFakeContinuationMarker(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Z";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "isFakeContinuationMarker";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(InlineCodegenUtilsKt.class, Marker.BACKEND);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(AbstractInsnNode p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(InlineCodegenUtilsKt.isFakeContinuationMarker(p0));
    }
}
