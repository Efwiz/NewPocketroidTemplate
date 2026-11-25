package org.jetbrains.kotlin.incremental.storage;

import java.io.DataInput;
import java.io.DataOutput;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer;
import org.jetbrains.kotlin.inline.InlineFunction;

/* JADX INFO: compiled from: externalizers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/InlineFunctionExternalizer;", "Lorg/jetbrains/kotlin/com/intellij/util/io/DataExternalizer;", "Lorg/jetbrains/kotlin/inline/InlineFunction;", "()V", "read", "input", "Ljava/io/DataInput;", "save", "", "output", "Ljava/io/DataOutput;", "function", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class InlineFunctionExternalizer implements DataExternalizer<InlineFunction> {
    public static final InlineFunctionExternalizer INSTANCE = new InlineFunctionExternalizer();

    private InlineFunctionExternalizer() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public void save(DataOutput output, InlineFunction function) {
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(function, "function");
        JvmMethodSignatureExternalizer.INSTANCE.save(output, function.getJvmMethodSignature());
        StringExternalizer.INSTANCE.save(output, function.getKotlinFunctionName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.io.DataExternalizer
    public InlineFunction read(DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new InlineFunction(JvmMethodSignatureExternalizer.INSTANCE.read(input), StringExternalizer.INSTANCE.read(input));
    }
}
