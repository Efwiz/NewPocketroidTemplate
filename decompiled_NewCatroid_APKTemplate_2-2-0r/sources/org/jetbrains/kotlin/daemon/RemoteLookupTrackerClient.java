package org.jetbrains.kotlin.daemon;

import gnu.trove.THashMap;
import gnu.trove.THashSet;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;
import org.jetbrains.kotlin.daemon.common.CompilerCallbackServicesFacade;
import org.jetbrains.kotlin.daemon.common.DummyProfiler;
import org.jetbrains.kotlin.daemon.common.Profiler;
import org.jetbrains.kotlin.incremental.components.LookupInfo;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.components.Position;
import org.jetbrains.kotlin.incremental.components.ScopeKind;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.utils.PlatformUtilsKt;

/* JADX INFO: compiled from: RemoteLookupTrackerClient.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0010#\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001bH\u0002J0\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\r2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R,\u0010\u0010\u001a \u0012\u0004\u0012\u00020\r\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/daemon/RemoteLookupTrackerClient;", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "facade", "Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "eventManager", "Lorg/jetbrains/kotlin/daemon/EventManager;", "profiler", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "(Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;Lorg/jetbrains/kotlin/daemon/EventManager;Lorg/jetbrains/kotlin/daemon/common/Profiler;)V", "getFacade", "()Lorg/jetbrains/kotlin/daemon/common/CompilerCallbackServicesFacade;", "interner", "Lorg/jetbrains/kotlin/com/intellij/util/containers/Interner;", "", "isDoNothing", "", "lookups", "Lgnu/trove/THashMap;", "", "", "", "getProfiler", "()Lorg/jetbrains/kotlin/daemon/common/Profiler;", "requiresPosition", "getRequiresPosition", "()Z", LineReader.CLEAR, "", "flush", PsiKeyword.RECORD, "filePath", "position", "Lorg/jetbrains/kotlin/incremental/components/Position;", "scopeFqName", "scopeKind", "Lorg/jetbrains/kotlin/incremental/components/ScopeKind;", "name", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteLookupTrackerClient implements LookupTracker {
    private final CompilerCallbackServicesFacade facade;
    private final Interner<String> interner;
    private final boolean isDoNothing;
    private final THashMap<String, Map<String, Set<Object>>> lookups;
    private final Profiler profiler;
    private final boolean requiresPosition;

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    public RemoteLookupTrackerClient(CompilerCallbackServicesFacade facade, EventManager eventManager, Profiler profiler) throws RemoteException {
        Intrinsics.checkNotNullParameter(facade, "facade");
        Intrinsics.checkNotNullParameter(eventManager, "eventManager");
        Intrinsics.checkNotNullParameter(profiler, "profiler");
        this.facade = facade;
        this.profiler = profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        boolean zLookupTracker_isDoNothing = facade.lookupTracker_isDoNothing();
        profiler.endMeasure(this, listBeginMeasure);
        this.isDoNothing = zLookupTracker_isDoNothing;
        this.lookups = new THashMap<>();
        this.interner = PlatformUtilsKt.createStringInterner();
        List<Long> listBeginMeasure2 = profiler.beginMeasure(this);
        boolean zLookupTracker_requiresPosition = facade.lookupTracker_requiresPosition();
        profiler.endMeasure(this, listBeginMeasure2);
        this.requiresPosition = zLookupTracker_requiresPosition;
        eventManager.onCompilationFinished(new Function0<Unit>() { // from class: org.jetbrains.kotlin.daemon.RemoteLookupTrackerClient.1
            {
                super(0);
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() throws RemoteException {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() throws RemoteException {
                RemoteLookupTrackerClient.this.flush();
            }
        });
    }

    public final CompilerCallbackServicesFacade getFacade() {
        return this.facade;
    }

    public /* synthetic */ RemoteLookupTrackerClient(CompilerCallbackServicesFacade compilerCallbackServicesFacade, EventManager eventManager, DummyProfiler dummyProfiler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerCallbackServicesFacade, eventManager, (i & 4) != 0 ? new DummyProfiler() : dummyProfiler);
    }

    public final Profiler getProfiler() {
        return this.profiler;
    }

    @Override // org.jetbrains.kotlin.incremental.components.LookupTracker
    public boolean getRequiresPosition() {
        return this.requiresPosition;
    }

    @Override // org.jetbrains.kotlin.incremental.components.LookupTracker
    public void record(String filePath, Position position, String scopeFqName, ScopeKind scopeKind, String name2) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(scopeFqName, "scopeFqName");
        Intrinsics.checkNotNullParameter(scopeKind, "scopeKind");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this.isDoNothing) {
            return;
        }
        String strIntern = this.interner.intern(scopeFqName);
        Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
        String str = strIntern;
        String strIntern2 = this.interner.intern(name2);
        Intrinsics.checkNotNullExpressionValue(strIntern2, "intern(...)");
        String lookupInfo = getRequiresPosition() ? new LookupInfo(filePath, position, scopeFqName, scopeKind, name2) : strIntern2;
        THashMap<String, Map<String, Set<Object>>> tHashMap = this.lookups;
        Map<String, Set<Object>> tHashMap2 = tHashMap.get(filePath);
        if (tHashMap2 == null) {
            tHashMap2 = new THashMap<>();
            tHashMap.put(filePath, tHashMap2);
        }
        Map<String, Set<Object>> map = tHashMap2;
        Set<Object> tHashSet = map.get(str);
        if (tHashSet == null) {
            tHashSet = new THashSet<>();
            map.put(str, tHashSet);
        }
        tHashSet.add(lookupInfo);
    }

    @Override // org.jetbrains.kotlin.incremental.components.LookupTracker
    public void clear() {
        this.lookups.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    public final void flush() throws RemoteException {
        LookupInfo lookupInfo;
        if (this.isDoNothing || this.lookups.isEmpty()) {
            return;
        }
        Profiler profiler = this.profiler;
        List<Long> listBeginMeasure = profiler.beginMeasure(this);
        CompilerCallbackServicesFacade compilerCallbackServicesFacade = this.facade;
        THashMap<String, Map<String, Set<Object>>> tHashMap = this.lookups;
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, Map<String, Set<Object>>>> it = tHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Map<String, Set<Object>>> next = it.next();
            String key = next.getKey();
            Map<String, Set<Object>> value = next.getValue();
            Intrinsics.checkNotNull(value);
            ArrayList arrayList2 = new ArrayList();
            for (Map.Entry<String, Set<Object>> entry : value.entrySet()) {
                String key2 = entry.getKey();
                Set<Object> value2 = entry.getValue();
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(value2, 10));
                for (Object obj : value2) {
                    if (getRequiresPosition()) {
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.components.LookupInfo");
                        lookupInfo = (LookupInfo) obj;
                    } else {
                        Intrinsics.checkNotNull(key);
                        Position no_position = Position.INSTANCE.getNO_POSITION();
                        ScopeKind scopeKind = ScopeKind.CLASSIFIER;
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                        lookupInfo = new LookupInfo(key, no_position, key2, scopeKind, (String) obj);
                    }
                    arrayList3.add(lookupInfo);
                    arrayList3 = arrayList3;
                    it = it;
                }
                CollectionsKt.addAll(arrayList2, arrayList3);
                it = it;
            }
            CollectionsKt.addAll(arrayList, arrayList2);
        }
        compilerCallbackServicesFacade.lookupTracker_record(arrayList);
        Unit unit = Unit.INSTANCE;
        profiler.endMeasure(this, listBeginMeasure);
        this.lookups.clear();
    }
}
