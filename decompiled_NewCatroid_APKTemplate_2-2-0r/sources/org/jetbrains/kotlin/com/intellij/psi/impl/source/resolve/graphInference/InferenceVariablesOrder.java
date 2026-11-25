package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;

/* JADX INFO: loaded from: classes3.dex */
public final class InferenceVariablesOrder {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/InferenceVariablesOrder", "buildInferenceGraph"));
    }

    public static List<InferenceVariable> resolveOrder(List<InferenceVariable> list, Map<InferenceVariable, Set<InferenceVariable>> map) {
        if (list.size() < 2) {
            return list;
        }
        InferenceVariable inferenceVariableResolveOrderFast = resolveOrderFast(list, map);
        if (inferenceVariableResolveOrderFast != null) {
            return Collections.singletonList(inferenceVariableResolveOrderFast);
        }
        Collection<InferenceGraphNode<InferenceVariable>> collectionValues = buildInferenceGraph(list, map).values();
        return InferenceGraphNode.merge((List) tarjan(collectionValues, 1).get(0), collectionValues).getValue();
    }

    private static InferenceVariable resolveOrderFast(final List<InferenceVariable> list, Map<InferenceVariable, Set<InferenceVariable>> map) {
        InferenceVariable inferenceVariable;
        InferenceVariable inferenceVariable2 = list.get(0);
        if (inferenceVariable2.getInstantiation() != PsiType.NULL || map.get(inferenceVariable2).isEmpty()) {
            return inferenceVariable2;
        }
        HashSet hashSet = new HashSet();
        while (hashSet.add(inferenceVariable2)) {
            if (inferenceVariable2.getInstantiation() != PsiType.NULL) {
                return inferenceVariable2;
            }
            Set<InferenceVariable> set = map.get(inferenceVariable2);
            if (set.isEmpty() || (inferenceVariable = (InferenceVariable) ContainerUtil.find(set, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariablesOrder$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return list.contains((InferenceVariable) obj);
                }
            })) == null) {
                return inferenceVariable2;
            }
            inferenceVariable2 = inferenceVariable;
        }
        return null;
    }

    public static Iterator<List<InferenceVariable>> resolveOrderIterator(Collection<? extends InferenceVariable> collection, InferenceSession inferenceSession) {
        return ContainerUtil.map((Collection) initNodes(buildInferenceGraph(collection, inferenceSession).values()), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariablesOrder$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((InferenceVariablesOrder.InferenceGraphNode) obj).getValue();
            }
        }).iterator();
    }

    public static Map<InferenceVariable, Set<InferenceVariable>> getDependencies(Collection<? extends InferenceVariable> collection, InferenceSession inferenceSession) {
        HashMap map = new HashMap();
        for (InferenceVariable inferenceVariable : collection) {
            map.put(inferenceVariable, inferenceVariable.getDependencies(inferenceSession));
        }
        return map;
    }

    private static Map<InferenceVariable, InferenceGraphNode<InferenceVariable>> buildInferenceGraph(Collection<? extends InferenceVariable> collection, InferenceSession inferenceSession) {
        return buildInferenceGraph(collection, getDependencies(collection, inferenceSession));
    }

    private static Map<InferenceVariable, InferenceGraphNode<InferenceVariable>> buildInferenceGraph(Collection<? extends InferenceVariable> collection, Map<InferenceVariable, Set<InferenceVariable>> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap((collection.size() * 4) / 3);
        for (InferenceVariable inferenceVariable : collection) {
            linkedHashMap.put(inferenceVariable, new InferenceGraphNode(inferenceVariable));
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            InferenceVariable inferenceVariable2 = (InferenceVariable) entry.getKey();
            if (inferenceVariable2.getInstantiation() == PsiType.NULL) {
                InferenceGraphNode inferenceGraphNode = (InferenceGraphNode) entry.getValue();
                Iterator<InferenceVariable> it = map.get(inferenceVariable2).iterator();
                while (it.hasNext()) {
                    InferenceGraphNode inferenceGraphNode2 = (InferenceGraphNode) linkedHashMap.get(it.next());
                    if (inferenceGraphNode2 != null) {
                        inferenceGraphNode.addDependency(inferenceGraphNode2);
                    }
                }
            }
        }
        return linkedHashMap;
    }

    public static <T> List<List<InferenceGraphNode<T>>> tarjan(Collection<? extends InferenceGraphNode<T>> collection) {
        return tarjan(collection, Integer.MAX_VALUE);
    }

    public static <T> List<List<InferenceGraphNode<T>>> tarjan(Collection<? extends InferenceGraphNode<T>> collection, int i) {
        ArrayList arrayList = new ArrayList();
        Stack stack = new Stack();
        int iStrongConnect = 0;
        for (InferenceGraphNode<T> inferenceGraphNode : collection) {
            if (((InferenceGraphNode) inferenceGraphNode).index == -1) {
                iStrongConnect += InferenceGraphNode.strongConnect(inferenceGraphNode, iStrongConnect, stack, arrayList);
                if (arrayList.size() >= i) {
                    break;
                }
            }
        }
        return arrayList;
    }

    public static <T> ArrayList<InferenceGraphNode<T>> initNodes(Collection<? extends InferenceGraphNode<T>> collection) {
        List listTarjan = tarjan(collection);
        ArrayList<InferenceGraphNode<T>> arrayList = new ArrayList<>();
        Iterator it = listTarjan.iterator();
        while (it.hasNext()) {
            arrayList.add(InferenceGraphNode.merge((List) it.next(), collection));
        }
        return arrayList;
    }

    public static class InferenceGraphNode<T> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int index;
        private int lowlink;
        private final Set<InferenceGraphNode<T>> myDependencies;
        private final List<T> myValue;

        public InferenceGraphNode(T t) {
            ArrayList arrayList = new ArrayList();
            this.myValue = arrayList;
            this.myDependencies = new LinkedHashSet();
            this.index = -1;
            arrayList.add(t);
        }

        public List<T> getValue() {
            return this.myValue;
        }

        public Set<InferenceGraphNode<T>> getDependencies() {
            return this.myDependencies;
        }

        public void addDependency(InferenceGraphNode<T> inferenceGraphNode) {
            this.myDependencies.add(inferenceGraphNode);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T> InferenceGraphNode<T> merge(List<? extends InferenceGraphNode<T>> list, Collection<? extends InferenceGraphNode<T>> collection) {
            InferenceGraphNode<T> inferenceGraphNode = list.get(0);
            if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    InferenceGraphNode<T> inferenceGraphNode2 = list.get(i);
                    inferenceGraphNode.copyFrom(inferenceGraphNode2);
                    inferenceGraphNode.filterInterCycleDependencies();
                    for (InferenceGraphNode<T> inferenceGraphNode3 : collection) {
                        if (((InferenceGraphNode) inferenceGraphNode3).myDependencies.remove(inferenceGraphNode2)) {
                            ((InferenceGraphNode) inferenceGraphNode3).myDependencies.add(inferenceGraphNode);
                        }
                    }
                }
            }
            return inferenceGraphNode;
        }

        private void filterInterCycleDependencies() {
            Iterator<InferenceGraphNode<T>> it = this.myDependencies.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (this.myValue.contains(it.next().myValue.get(0))) {
                    it.remove();
                    z = true;
                }
            }
            if (z) {
                this.myDependencies.add(this);
            }
        }

        private void copyFrom(InferenceGraphNode<T> inferenceGraphNode) {
            this.myValue.addAll(inferenceGraphNode.myValue);
            this.myDependencies.addAll(inferenceGraphNode.myDependencies);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T> int strongConnect(InferenceGraphNode<T> inferenceGraphNode, int i, Stack<InferenceGraphNode<T>> stack, ArrayList<? super List<InferenceGraphNode<T>>> arrayList) {
            InferenceGraphNode<T> inferenceGraphNodePop;
            ((InferenceGraphNode) inferenceGraphNode).index = i;
            ((InferenceGraphNode) inferenceGraphNode).lowlink = i;
            int i2 = i + 1;
            stack.push(inferenceGraphNode);
            for (InferenceGraphNode<T> inferenceGraphNode2 : inferenceGraphNode.getDependencies()) {
                if (((InferenceGraphNode) inferenceGraphNode2).index == -1) {
                    strongConnect(inferenceGraphNode2, i2, stack, arrayList);
                    ((InferenceGraphNode) inferenceGraphNode).lowlink = Math.min(((InferenceGraphNode) inferenceGraphNode).lowlink, ((InferenceGraphNode) inferenceGraphNode2).lowlink);
                } else if (stack.contains(inferenceGraphNode2)) {
                    ((InferenceGraphNode) inferenceGraphNode).lowlink = Math.min(((InferenceGraphNode) inferenceGraphNode).lowlink, ((InferenceGraphNode) inferenceGraphNode2).index);
                }
            }
            if (((InferenceGraphNode) inferenceGraphNode).lowlink == ((InferenceGraphNode) inferenceGraphNode).index) {
                ArrayList arrayList2 = new ArrayList();
                do {
                    inferenceGraphNodePop = stack.pop();
                    arrayList2.add(inferenceGraphNodePop);
                } while (inferenceGraphNodePop != inferenceGraphNode);
                arrayList.add(arrayList2);
            }
            return i2;
        }
    }
}
