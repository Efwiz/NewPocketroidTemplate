package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: LateinitIntrinsics.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/LateinitIsInitialized;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicPropertyGetter;", "()V", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/codegen/StackValue;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "receiver", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LateinitIsInitialized extends IntrinsicPropertyGetter {
    public static final LateinitIsInitialized INSTANCE = new LateinitIsInitialized();

    private LateinitIsInitialized() {
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicPropertyGetter
    public StackValue generate(ResolvedCall<?> resolvedCall, ExpressionCodegen codegen, Type returnType, StackValue receiver) {
        StackValue stackValue;
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        if (resolvedCall == null || (stackValue = LateinitIntrinsicsKt.getStackValue(resolvedCall, codegen)) == null) {
            return null;
        }
        return StackValue.compareWithNull(stackValue, 198);
    }
}
