package org.jetbrains.kotlin.incremental;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;

/* JADX INFO: compiled from: InlineConstTrackerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005H\u0016R6\u0010\u0003\u001a*\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004j\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006`\bX\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000b0\n8F¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/incremental/InlineConstTrackerImpl;", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "()V", "inlineConst", "Ljava/util/HashMap;", "", "", "Lorg/jetbrains/kotlin/incremental/ConstantRef;", "Lkotlin/collections/HashMap;", "inlineConstMap", "", "", "getInlineConstMap", "()Ljava/util/Map;", "report", "", "filePath", "owner", "name", "constType", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineConstTrackerImpl implements InlineConstTracker {
    private final HashMap<String, Set<ConstantRef>> inlineConst = new HashMap<>();

    public final Map<String, Collection<ConstantRef>> getInlineConstMap() {
        return this.inlineConst;
    }

    @Override // org.jetbrains.kotlin.incremental.components.InlineConstTracker
    public void report(String filePath, String owner, String name2, String constType) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(constType, "constType");
        HashMap<String, Set<ConstantRef>> map = this.inlineConst;
        HashSet hashSet = map.get(filePath);
        if (hashSet == null) {
            hashSet = new HashSet();
            map.put(filePath, hashSet);
        }
        hashSet.add(new ConstantRef(owner, name2, constType));
    }
}
