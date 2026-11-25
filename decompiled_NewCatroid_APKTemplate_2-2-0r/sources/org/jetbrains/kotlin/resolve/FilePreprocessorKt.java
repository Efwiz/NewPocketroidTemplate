package org.jetbrains.kotlin.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.Sets;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: FilePreprocessor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001f\n\u0002\b\u0004\u001aE\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u00020\u00042\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u00070\u00062\u0006\u0010\b\u001a\u0002H\u00022\u0006\u0010\t\u001a\u0002H\u0003¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"addElementToSlice", "", "K", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "slice", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "", ASTExpr.DEFAULT_MAP_KEY_NAME, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;Ljava/lang/Object;Ljava/lang/Object;)V", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FilePreprocessorKt {
    public static final <K, T> void addElementToSlice(BindingTrace bindingTrace, WritableSlice<K, Collection<T>> slice, K k, T t) {
        Intrinsics.checkNotNullParameter(bindingTrace, "<this>");
        Intrinsics.checkNotNullParameter(slice, "slice");
        Set setNewIdentityHashSet = (Collection) bindingTrace.get(slice, k);
        if (setNewIdentityHashSet == null) {
            setNewIdentityHashSet = Sets.newIdentityHashSet();
        }
        setNewIdentityHashSet.add(t);
        bindingTrace.record(slice, k, setNewIdentityHashSet);
    }
}
