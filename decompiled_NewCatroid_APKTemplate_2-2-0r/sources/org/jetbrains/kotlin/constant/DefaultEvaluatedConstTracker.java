package org.jetbrains.kotlin.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EvaluatedConstTracker.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\t\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0005H\u0016J.\u0010\t\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0018\u00010\r2\u0006\u0010\f\u001a\u00020\u0005H\u0016J,\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00052\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\bH\u0016R6\u0010\u0003\u001a*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/constant/DefaultEvaluatedConstTracker;", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "()V", "storage", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "load", "start", "end", "file", "", "save", "", "constant", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DefaultEvaluatedConstTracker extends EvaluatedConstTracker {
    private final ConcurrentHashMap<String, ConcurrentHashMap<Pair<Integer, Integer>, ConstantValue<?>>> storage = new ConcurrentHashMap<>();

    @Override // org.jetbrains.kotlin.constant.EvaluatedConstTracker
    public void save(int start, int end, String file, ConstantValue<?> constant) {
        ConcurrentHashMap<Pair<Integer, Integer>, ConstantValue<?>> concurrentHashMapPutIfAbsent;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(constant, "constant");
        ConcurrentHashMap<String, ConcurrentHashMap<Pair<Integer, Integer>, ConstantValue<?>>> concurrentHashMap = this.storage;
        ConcurrentHashMap<Pair<Integer, Integer>, ConstantValue<?>> concurrentHashMap2 = concurrentHashMap.get(file);
        if (concurrentHashMap2 == null && (concurrentHashMapPutIfAbsent = concurrentHashMap.putIfAbsent(file, (concurrentHashMap2 = new ConcurrentHashMap<>()))) != null) {
            concurrentHashMap2 = concurrentHashMapPutIfAbsent;
        }
        ConcurrentHashMap<Pair<Integer, Integer>, ConstantValue<?>> concurrentHashMap3 = concurrentHashMap2;
        Intrinsics.checkNotNull(concurrentHashMap3);
        concurrentHashMap3.put(TuplesKt.to(Integer.valueOf(start), Integer.valueOf(end)), constant);
    }

    @Override // org.jetbrains.kotlin.constant.EvaluatedConstTracker
    public ConstantValue<?> load(int start, int end, String file) {
        Intrinsics.checkNotNullParameter(file, "file");
        ConcurrentHashMap<Pair<Integer, Integer>, ConstantValue<?>> concurrentHashMap = this.storage.get(file);
        if (concurrentHashMap != null) {
            return concurrentHashMap.get(TuplesKt.to(Integer.valueOf(start), Integer.valueOf(end)));
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.constant.EvaluatedConstTracker
    public Map<Pair<Integer, Integer>, ConstantValue<?>> load(String file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return this.storage.get(file);
    }
}
