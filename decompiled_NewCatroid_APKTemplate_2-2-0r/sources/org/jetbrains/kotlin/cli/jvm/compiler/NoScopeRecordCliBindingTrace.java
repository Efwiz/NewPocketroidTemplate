package org.jetbrains.kotlin.cli.jvm.compiler;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: CliTrace.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J=\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u0002H\u00052\u0006\u0010\n\u001a\u0002H\u0006H\u0016¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/NoScopeRecordCliBindingTrace;", "Lorg/jetbrains/kotlin/cli/jvm/compiler/CliBindingTrace;", "()V", PsiKeyword.RECORD, "", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "slice", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;Ljava/lang/Object;Ljava/lang/Object;)V", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NoScopeRecordCliBindingTrace extends CliBindingTrace {
    @Override // org.jetbrains.kotlin.resolve.BindingTraceContext, org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> void record(WritableSlice<K, V> slice, K key, V value) {
        Intrinsics.checkNotNullParameter(slice, "slice");
        if (Intrinsics.areEqual(slice, BindingContext.LEXICAL_SCOPE) || Intrinsics.areEqual(slice, BindingContext.DATA_FLOW_INFO_BEFORE)) {
            return;
        }
        super.record(slice, key, value);
    }

    @Override // org.jetbrains.kotlin.cli.jvm.compiler.CliBindingTrace
    public String toString() {
        String name2 = NoScopeRecordCliBindingTrace.class.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }
}
