package org.jetbrains.kotlin.resolve.jvm;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice;
import org.jetbrains.kotlin.util.slicedMap.RewritePolicy;
import org.jetbrains.kotlin.util.slicedMap.Slices;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: JvmBindingContextSlices.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00060\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmBindingContextSlices;", "", "()V", "BODY_RUNTIME_ASSERTION_INFO", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionInfo;", "LOAD_FROM_JAVA_SIGNATURE_ERRORS", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "", "", "RECEIVER_RUNTIME_ASSERTION_INFO", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver;", "RUNTIME_ASSERTION_INFO", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmBindingContextSlices {
    public static final WritableSlice<DeclarationDescriptor, List<String>> LOAD_FROM_JAVA_SIGNATURE_ERRORS;
    public static final JvmBindingContextSlices INSTANCE = new JvmBindingContextSlices();
    public static final WritableSlice<KtExpression, RuntimeAssertionInfo> RUNTIME_ASSERTION_INFO = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<ExpressionReceiver, RuntimeAssertionInfo> RECEIVER_RUNTIME_ASSERTION_INFO = new BasicWritableSlice(RewritePolicy.DO_NOTHING);
    public static final WritableSlice<KtExpression, RuntimeAssertionInfo> BODY_RUNTIME_ASSERTION_INFO = new BasicWritableSlice(RewritePolicy.DO_NOTHING);

    private JvmBindingContextSlices() {
    }

    static {
        WritableSlice<DeclarationDescriptor, List<String>> writableSliceCreateCollectiveSlice = Slices.createCollectiveSlice();
        Intrinsics.checkNotNullExpressionValue(writableSliceCreateCollectiveSlice, "createCollectiveSlice(...)");
        LOAD_FROM_JAVA_SIGNATURE_ERRORS = writableSliceCreateCollectiveSlice;
        BasicWritableSlice.initSliceDebugNames(JvmBindingContextSlices.class);
    }
}
