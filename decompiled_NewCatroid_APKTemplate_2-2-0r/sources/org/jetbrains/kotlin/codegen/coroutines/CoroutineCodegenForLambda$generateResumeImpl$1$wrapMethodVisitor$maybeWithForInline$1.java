package org.jetbrains.kotlin.codegen.coroutines;

import kotlin.Metadata;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.codegen.FunctionCodegen;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: compiled from: CoroutineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CoroutineCodegenForLambda$generateResumeImpl$1$wrapMethodVisitor$maybeWithForInline$1 extends FunctionReference implements Function6<JvmDeclarationOrigin, Integer, String, String, String, String[], MethodVisitor> {
    CoroutineCodegenForLambda$generateResumeImpl$1$wrapMethodVisitor$maybeWithForInline$1(Object obj) {
        super(6, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "newMethod(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "newMethod";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(FunctionCodegen.class);
    }

    @Override // kotlin.jvm.functions.Function6
    public /* bridge */ /* synthetic */ MethodVisitor invoke(JvmDeclarationOrigin jvmDeclarationOrigin, Integer num, String str, String str2, String str3, String[] strArr) {
        return invoke(jvmDeclarationOrigin, num.intValue(), str, str2, str3, strArr);
    }

    public final MethodVisitor invoke(JvmDeclarationOrigin p0, int i, String p2, String p3, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p2, "p2");
        Intrinsics.checkNotNullParameter(p3, "p3");
        return ((FunctionCodegen) this.receiver).newMethod(p0, i, p2, p3, str, strArr);
    }
}
