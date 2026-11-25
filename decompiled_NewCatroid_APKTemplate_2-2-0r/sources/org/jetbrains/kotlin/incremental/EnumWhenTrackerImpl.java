package org.jetbrains.kotlin.incremental;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;

/* JADX INFO: compiled from: EnumWhenTrackerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010#\n\u0000\n\u0002\u0010$\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0016R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/incremental/EnumWhenTrackerImpl;", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "()V", "whenExpressionFilePathToEnumClass", "Ljava/util/concurrent/ConcurrentHashMap;", "", "", "whenExpressionFilePathToEnumClassMap", "", "", "getWhenExpressionFilePathToEnumClassMap", "()Ljava/util/Map;", "report", "", "whenExpressionFilePath", "enumClassFqName", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumWhenTrackerImpl implements EnumWhenTracker {
    private final ConcurrentHashMap<String, Set<String>> whenExpressionFilePathToEnumClass = new ConcurrentHashMap<>();

    public final Map<String, Collection<String>> getWhenExpressionFilePathToEnumClassMap() {
        return this.whenExpressionFilePathToEnumClass;
    }

    @Override // org.jetbrains.kotlin.incremental.components.EnumWhenTracker
    public void report(String whenExpressionFilePath, String enumClassFqName) {
        Set<String> setPutIfAbsent;
        Intrinsics.checkNotNullParameter(whenExpressionFilePath, "whenExpressionFilePath");
        Intrinsics.checkNotNullParameter(enumClassFqName, "enumClassFqName");
        ConcurrentHashMap<String, Set<String>> concurrentHashMap = this.whenExpressionFilePathToEnumClass;
        HashSet hashSet = concurrentHashMap.get(whenExpressionFilePath);
        if (hashSet == null && (setPutIfAbsent = concurrentHashMap.putIfAbsent(whenExpressionFilePath, (hashSet = new HashSet()))) != null) {
            hashSet = setPutIfAbsent;
        }
        hashSet.add(enumClassFqName);
    }
}
