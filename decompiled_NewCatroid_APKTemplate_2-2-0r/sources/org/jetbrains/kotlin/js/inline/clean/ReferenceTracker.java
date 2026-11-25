package org.jetbrains.kotlin.js.inline.clean;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.inline.util.CollectionUtilsKt;

/* JADX INFO: compiled from: ReferenceTracker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0010\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0000*\b\b\u0001\u0010\u0002*\u00020\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u001b\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00028\u0001¢\u0006\u0002\u0010\u0015J\u001b\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00028\u0000¢\u0006\u0002\u0010\u0019J\u001b\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u0017\u001a\u00028\u0000H\b¢\u0006\u0002\u0010\u001bJ\u0015\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001eJ\u0013\u0010\u001f\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00028\u0000¢\u0006\u0002\u0010\u001eJ\u0013\u0010 \u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00028\u0000¢\u0006\u0002\u0010!R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u0007X\u0088\u0004¢\u0006\u0002\n\u0000R \u0010\t\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\n0\u0007X\u0088\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00010\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007X\u0088\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\nX\u0088\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/ReferenceTracker;", "Reference", "RemoveCandidate", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "", "()V", "reachable", "Ljava/util/IdentityHashMap;", "", "referenceFromTo", "", "removable", "", "getRemovable", "()Ljava/util/List;", "removableCandidates", "visited", "addCandidateForRemoval", "", "reference", "candidate", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/js/backend/ast/JsNode;)V", "addRemovableReference", "referrer", "referenced", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getReferencedBy", "(Ljava/lang/Object;)Ljava/util/Set;", "isReachable", "ref", "(Ljava/lang/Object;)Z", "isReferenceToRemovableCandidate", "markReachable", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReferenceTracker<Reference, RemoveCandidate extends JsNode> {
    private final IdentityHashMap<Reference, Boolean> reachable = new IdentityHashMap<>();
    private final IdentityHashMap<Reference, RemoveCandidate> removableCandidates = new IdentityHashMap<>();
    private final IdentityHashMap<Reference, Set<Reference>> referenceFromTo = new IdentityHashMap<>();
    private final Set<Reference> visited = CollectionUtilsKt.IdentitySet();

    public final List<RemoveCandidate> getRemovable() {
        IdentityHashMap<Reference, Boolean> identityHashMap = this.reachable;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<Reference, Boolean> entry : identityHashMap.entrySet()) {
            if (!entry.getValue().booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        Iterator it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            RemoveCandidate removecandidate = this.removableCandidates.get(((Map.Entry) it.next()).getKey());
            Intrinsics.checkNotNull(removecandidate);
            arrayList.add(removecandidate);
        }
        return arrayList;
    }

    public final void addCandidateForRemoval(Reference reference, RemoveCandidate candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        isReferenceToRemovableCandidate(reference);
        this.removableCandidates.put(reference, candidate);
        this.reachable.put(reference, false);
    }

    public final void addRemovableReference(Reference referrer, Reference referenced) {
        if (isReferenceToRemovableCandidate(referenced)) {
            getReferencedBy(referrer).add(referenced);
            if (isReachable(referrer)) {
                markReachable(referenced);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void markReachable(Reference reference) {
        if (isReferenceToRemovableCandidate(reference)) {
            this.visited.add(reference);
            Set referencedBy = getReferencedBy(reference);
            ArrayList arrayList = new ArrayList();
            for (Object obj : referencedBy) {
                if (!this.visited.contains(obj)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : arrayList) {
                if (isReferenceToRemovableCandidate(obj2) && !isReachable(obj2)) {
                    arrayList2.add(obj2);
                }
            }
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                markReachable(it.next());
            }
            this.visited.remove(reference);
            this.reachable.put(reference, true);
        }
    }

    private final Set<Reference> getReferencedBy(Reference referrer) {
        IdentityHashMap<Reference, Set<Reference>> identityHashMap = this.referenceFromTo;
        Set<Reference> setIdentitySet = identityHashMap.get(referrer);
        if (setIdentitySet == null) {
            setIdentitySet = CollectionUtilsKt.IdentitySet();
            identityHashMap.put(referrer, setIdentitySet);
        }
        Intrinsics.checkNotNullExpressionValue(setIdentitySet, "getOrPut(...)");
        return setIdentitySet;
    }

    public final boolean isReferenceToRemovableCandidate(Reference ref) {
        return this.removableCandidates.containsKey(ref);
    }

    private final boolean isReachable(Reference ref) {
        Boolean bool = this.reachable.get(ref);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }
}
