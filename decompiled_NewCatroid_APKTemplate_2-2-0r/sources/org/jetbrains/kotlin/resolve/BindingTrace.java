package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public interface BindingTrace extends DiagnosticSink {
    <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k);

    BindingContext getBindingContext();

    <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice);

    KotlinType getType(KtExpression ktExpression);

    <K> void record(WritableSlice<K, Boolean> writableSlice, K k);

    <K, V> void record(WritableSlice<K, V> writableSlice, K k, V v);

    void recordType(KtExpression ktExpression, KotlinType kotlinType);
}
