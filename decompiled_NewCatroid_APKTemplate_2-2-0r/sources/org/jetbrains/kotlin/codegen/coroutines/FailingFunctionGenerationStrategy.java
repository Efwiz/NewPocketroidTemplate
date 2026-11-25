package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.FrameMap;
import org.jetbrains.kotlin.codegen.FunctionGenerationStrategy;
import org.jetbrains.kotlin.codegen.MemberCodegen;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

/* JADX INFO: compiled from: CoroutineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/FailingFunctionGenerationStrategy;", "Lorg/jetbrains/kotlin/codegen/FunctionGenerationStrategy;", "()V", "generateBody", "", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "context", "Lorg/jetbrains/kotlin/codegen/context/MethodContext;", "parentCodegen", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "skipNotNullAssertionsForParameters", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FailingFunctionGenerationStrategy extends FunctionGenerationStrategy {
    public static final FailingFunctionGenerationStrategy INSTANCE = new FailingFunctionGenerationStrategy();

    private FailingFunctionGenerationStrategy() {
    }

    @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
    public boolean skipNotNullAssertionsForParameters() {
        throw new IllegalStateException("This functions must not be called".toString());
    }

    @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
    public void generateBody(MethodVisitor mv, FrameMap frameMap, JvmMethodSignature signature, MethodContext context, MemberCodegen<?> parentCodegen) {
        Intrinsics.checkNotNullParameter(mv, "mv");
        Intrinsics.checkNotNullParameter(frameMap, "frameMap");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(parentCodegen, "parentCodegen");
        throw new IllegalStateException("This functions must not be called".toString());
    }
}
