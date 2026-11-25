package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;
import org.jetbrains.kotlin.com.intellij.util.graph.CachingSemiGraph;
import org.jetbrains.kotlin.com.intellij.util.graph.DFSTBuilder;
import org.jetbrains.kotlin.com.intellij.util.graph.GraphGenerator;
import org.jetbrains.kotlin.com.intellij.util.graph.InboundSemiGraph;

/* JADX INFO: loaded from: classes3.dex */
public final class LoadingOrder {
    public static final LoadingOrder ANY = new LoadingOrder();
    public static final LoadingOrder FIRST = new LoadingOrder("first");
    public static final LoadingOrder LAST = new LoadingOrder("last");
    private final Set<String> myAfter;
    private final Set<String> myBefore;
    private final boolean myFirst;
    private final boolean myLast;
    private final String myName;

    public interface Orderable {
        LoadingOrder getOrder();

        String getOrderId();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "orderable";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/LoadingOrder";
        } else {
            objArr[0] = "text";
        }
        if (i == 3 || i == 4 || i == 5) {
            objArr[1] = "readOrder";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/LoadingOrder";
        }
        if (i == 1 || i == 2) {
            objArr[2] = "sort";
        } else if (i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private LoadingOrder() {
        this.myName = "ANY";
        this.myFirst = false;
        this.myLast = false;
        this.myBefore = Collections.emptySet();
        this.myAfter = Collections.emptySet();
    }

    public LoadingOrder(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myName = str;
        boolean z = false;
        Set<String> linkedHashSet = null;
        Set<String> linkedHashSet2 = null;
        boolean z2 = false;
        for (String str2 : str.split(",")) {
            String strTrim = str2.trim();
            if (strTrim.equalsIgnoreCase("first")) {
                z2 = true;
            } else if (strTrim.equalsIgnoreCase("last")) {
                z = true;
            } else if (StringUtilRt.startsWithIgnoreCase(strTrim, "before ")) {
                linkedHashSet = linkedHashSet == null ? new LinkedHashSet<>(2) : linkedHashSet;
                linkedHashSet.add(strTrim.substring("before ".length()).trim());
            } else if (StringUtilRt.startsWithIgnoreCase(strTrim, "before:")) {
                linkedHashSet = linkedHashSet == null ? new LinkedHashSet<>(2) : linkedHashSet;
                linkedHashSet.add(strTrim.substring("before:".length()).trim());
            } else if (StringUtilRt.startsWithIgnoreCase(strTrim, "after ")) {
                linkedHashSet2 = linkedHashSet2 == null ? new LinkedHashSet<>(2) : linkedHashSet2;
                linkedHashSet2.add(strTrim.substring("after ".length()).trim());
            } else if (StringUtilRt.startsWithIgnoreCase(strTrim, "after:")) {
                linkedHashSet2 = linkedHashSet2 == null ? new LinkedHashSet<>(2) : linkedHashSet2;
                linkedHashSet2.add(strTrim.substring("after:".length()).trim());
            } else {
                throw new AssertionError("Invalid specification: " + strTrim + "; should be one of FIRST, LAST, BEFORE <id> or AFTER <id>");
            }
        }
        this.myBefore = linkedHashSet == null ? Collections.emptySet() : linkedHashSet;
        this.myAfter = linkedHashSet2 == null ? Collections.emptySet() : linkedHashSet2;
        this.myFirst = z2;
        this.myLast = z;
    }

    public String toString() {
        return this.myName;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LoadingOrder)) {
            return false;
        }
        LoadingOrder loadingOrder = (LoadingOrder) obj;
        return this.myFirst == loadingOrder.myFirst && this.myLast == loadingOrder.myLast && this.myAfter.equals(loadingOrder.myAfter) && this.myBefore.equals(loadingOrder.myBefore);
    }

    public int hashCode() {
        return ((((((this.myFirst ? 1 : 0) * 31) + (this.myLast ? 1 : 0)) * 31) + this.myBefore.hashCode()) * 31) + this.myAfter.hashCode();
    }

    public static void sort(final List<? extends Orderable> list) {
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        if (list.size() < 2) {
            return;
        }
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        final LinkedHashSet linkedHashSet = new LinkedHashSet(1);
        final LinkedHashSet linkedHashSet2 = new LinkedHashSet(list.size());
        for (Orderable orderable : list) {
            String orderId = orderable.getOrderId();
            if (orderId != null && !orderId.isEmpty()) {
                linkedHashMap.put(orderId, orderable);
            }
            LoadingOrder order = orderable.getOrder();
            if (order != ANY) {
                linkedHashMap2.put(orderable, order);
                if (order.myFirst) {
                    linkedHashSet.add(orderable);
                }
                if (!order.myBefore.isEmpty()) {
                    linkedHashSet2.add(orderable);
                }
            }
        }
        if (linkedHashMap2.isEmpty()) {
            return;
        }
        DFSTBuilder dFSTBuilder = new DFSTBuilder(GraphGenerator.generate(CachingSemiGraph.cache(new InboundSemiGraph<Orderable>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/LoadingOrder$1";
                if (i != 1) {
                    objArr[1] = "getNodes";
                } else {
                    objArr[1] = "getIn";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.graph.InboundSemiGraph
            public Collection<Orderable> getNodes() {
                ArrayList arrayList = new ArrayList(list);
                Collections.reverse(arrayList);
                return arrayList;
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.graph.InboundSemiGraph
            public Iterator<Orderable> getIn(Orderable orderable2) {
                LoadingOrder loadingOrder = (LoadingOrder) linkedHashMap2.getOrDefault(orderable2, LoadingOrder.ANY);
                LinkedHashSet linkedHashSet3 = new LinkedHashSet();
                Iterator it = loadingOrder.myAfter.iterator();
                while (it.hasNext()) {
                    Orderable orderable3 = (Orderable) linkedHashMap.get((String) it.next());
                    if (orderable3 != null) {
                        linkedHashSet3.add(orderable3);
                    }
                }
                String orderId2 = orderable2.getOrderId();
                if (orderId2 != null && !orderId2.isEmpty()) {
                    for (Orderable orderable4 : linkedHashSet2) {
                        if (((LoadingOrder) linkedHashMap2.getOrDefault(orderable4, LoadingOrder.ANY)).myBefore.contains(orderId2)) {
                            linkedHashSet3.add(orderable4);
                        }
                    }
                }
                if (loadingOrder.myLast) {
                    for (Orderable orderable5 : list) {
                        if (!((LoadingOrder) linkedHashMap2.getOrDefault(orderable5, LoadingOrder.ANY)).myLast) {
                            linkedHashSet3.add(orderable5);
                        }
                    }
                }
                if (!loadingOrder.myFirst) {
                    linkedHashSet3.addAll(linkedHashSet);
                }
                Iterator<Orderable> it2 = linkedHashSet3.iterator();
                if (it2 == null) {
                    $$$reportNull$$$0(1);
                }
                return it2;
            }
        })));
        if (!dFSTBuilder.isAcyclic()) {
            Map.Entry entry = (Map.Entry) Objects.requireNonNull(dFSTBuilder.getCircularDependency());
            throw new SortingException("Could not satisfy sorting requirements", (Orderable) entry.getKey(), (Orderable) entry.getValue());
        }
        list.sort(dFSTBuilder.comparator());
    }
}
