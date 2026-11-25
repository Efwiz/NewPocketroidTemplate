package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicIterator;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicLevel;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicLevels;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.util.collections.HandleList;
import com.hivemq.client.internal.util.collections.Index;
import com.hivemq.client.internal.util.collections.NodeList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class MqttSubscribedPublishFlowTree implements MqttSubscribedPublishFlows {
    private TopicTreeNode rootNode;

    MqttSubscribedPublishFlowTree() {
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlows
    public void subscribe(MqttSubscription mqttSubscription, int i, MqttSubscribedPublishFlow mqttSubscribedPublishFlow) {
        TopicTreeEntry topicTreeEntry = new TopicTreeEntry(mqttSubscription, i, mqttSubscribedPublishFlow);
        MqttTopicIterator mqttTopicIteratorOf = MqttTopicIterator.of(mqttSubscription.getTopicFilter());
        TopicTreeNode topicTreeNodeSubscribe = this.rootNode;
        if (topicTreeNodeSubscribe == null) {
            topicTreeNodeSubscribe = new TopicTreeNode(null, null);
            this.rootNode = topicTreeNodeSubscribe;
        }
        while (topicTreeNodeSubscribe != null) {
            topicTreeNodeSubscribe = topicTreeNodeSubscribe.subscribe(mqttTopicIteratorOf, topicTreeEntry);
        }
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlows
    public void suback(MqttTopicFilterImpl mqttTopicFilterImpl, int i, boolean z) {
        MqttTopicIterator mqttTopicIteratorOf = MqttTopicIterator.of(mqttTopicFilterImpl);
        TopicTreeNode topicTreeNodeSuback = this.rootNode;
        while (topicTreeNodeSuback != null) {
            topicTreeNodeSuback = topicTreeNodeSuback.suback(mqttTopicIteratorOf, mqttTopicFilterImpl, i, z);
        }
        compact();
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlows
    public void unsubscribe(MqttTopicFilterImpl mqttTopicFilterImpl) {
        MqttTopicIterator mqttTopicIteratorOf = MqttTopicIterator.of(mqttTopicFilterImpl);
        TopicTreeNode topicTreeNodeUnsubscribe = this.rootNode;
        while (topicTreeNodeUnsubscribe != null) {
            topicTreeNodeUnsubscribe = topicTreeNodeUnsubscribe.unsubscribe(mqttTopicIteratorOf, mqttTopicFilterImpl);
        }
        compact();
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlows
    public void cancel(MqttSubscribedPublishFlow mqttSubscribedPublishFlow) {
        Object first = mqttSubscribedPublishFlow.getTopicFilters().getFirst();
        while (true) {
            HandleList.Handle handle = (HandleList.Handle) first;
            if (handle == null) {
                return;
            }
            MqttTopicIterator mqttTopicIteratorOf = MqttTopicIterator.of((MqttTopicFilterImpl) handle.getElement());
            TopicTreeNode topicTreeNodeCancel = this.rootNode;
            while (topicTreeNodeCancel != null) {
                topicTreeNodeCancel = topicTreeNodeCancel.cancel(mqttTopicIteratorOf, mqttSubscribedPublishFlow);
            }
            first = handle.getNext();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlows
    public void findMatching(MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows) {
        MqttTopicIterator mqttTopicIteratorOf = MqttTopicIterator.of(((MqttPublish) mqttStatefulPublishWithFlows.publish.stateless()).getTopic());
        TopicTreeNode topicTreeNodeFindMatching = this.rootNode;
        while (topicTreeNodeFindMatching != null) {
            topicTreeNodeFindMatching = topicTreeNodeFindMatching.findMatching(mqttTopicIteratorOf, mqttStatefulPublishWithFlows);
        }
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlows
    public void clear(Throwable th) {
        TopicTreeNode topicTreeNodeClear = this.rootNode;
        while (topicTreeNodeClear != null) {
            topicTreeNodeClear = topicTreeNodeClear.clear(th);
        }
        this.rootNode = null;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlows
    public Map<Integer, List<MqttSubscription>> getSubscriptions() {
        TreeMap treeMap = new TreeMap(Comparator.reverseOrder());
        if (this.rootNode != null) {
            LinkedList linkedList = new LinkedList();
            linkedList.add(new IteratorNode(this.rootNode, null));
            while (!linkedList.isEmpty()) {
                IteratorNode iteratorNode = (IteratorNode) linkedList.poll();
                iteratorNode.node.getSubscriptions(iteratorNode.parentTopicLevels, treeMap, linkedList);
            }
        }
        return treeMap;
    }

    private void compact() {
        TopicTreeNode topicTreeNode = this.rootNode;
        if (topicTreeNode == null || !topicTreeNode.isEmpty()) {
            return;
        }
        this.rootNode = null;
    }

    private static class TopicTreeEntry extends NodeList.Node<TopicTreeEntry> {
        boolean acknowledged;
        MqttSubscribedPublishFlow flow;
        HandleList.Handle<MqttTopicFilterImpl> handle;
        final int subscriptionIdentifier;
        final byte subscriptionOptions;
        final byte[] topicFilterPrefix;

        TopicTreeEntry(MqttSubscription mqttSubscription, int i, MqttSubscribedPublishFlow mqttSubscribedPublishFlow) {
            this.subscriptionIdentifier = i;
            this.subscriptionOptions = mqttSubscription.encodeSubscriptionOptions();
            MqttTopicFilterImpl topicFilter = mqttSubscription.getTopicFilter();
            this.topicFilterPrefix = topicFilter.getPrefix();
            this.flow = mqttSubscribedPublishFlow;
            this.handle = mqttSubscribedPublishFlow == null ? null : mqttSubscribedPublishFlow.getTopicFilters().add(topicFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TopicTreeNode {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final Index.Spec<TopicTreeNode, MqttTopicLevel> INDEX_SPEC = new Index.Spec<>(new Function() { // from class: com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlowTree$TopicTreeNode$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MqttSubscribedPublishFlowTree.TopicTreeNode) obj).topicLevel;
            }
        }, 4);
        private NodeList<TopicTreeEntry> entries;
        private NodeList<TopicTreeEntry> multiLevelEntries;
        private Index<TopicTreeNode, MqttTopicLevel> next;
        private TopicTreeNode parent;
        private TopicTreeNode singleLevel;
        private MqttTopicLevel topicLevel;

        TopicTreeNode(TopicTreeNode topicTreeNode, MqttTopicLevel mqttTopicLevel) {
            this.parent = topicTreeNode;
            this.topicLevel = mqttTopicLevel;
        }

        TopicTreeNode subscribe(MqttTopicIterator mqttTopicIterator, TopicTreeEntry topicTreeEntry) {
            TopicTreeNode topicTreeNode = null;
            if (mqttTopicIterator.hasNext()) {
                MqttTopicLevel next = mqttTopicIterator.next();
                if (next.isSingleLevelWildcard()) {
                    TopicTreeNode topicTreeNode2 = this.singleLevel;
                    if (topicTreeNode2 == null) {
                        TopicTreeNode topicTreeNode3 = new TopicTreeNode(this, next.trim());
                        this.singleLevel = topicTreeNode3;
                        return topicTreeNode3;
                    }
                    return getNext(topicTreeNode2, mqttTopicIterator);
                }
                Index<TopicTreeNode, MqttTopicLevel> index = this.next;
                if (index == null) {
                    this.next = new Index<>(INDEX_SPEC);
                } else {
                    topicTreeNode = index.get(next);
                }
                if (topicTreeNode == null) {
                    TopicTreeNode topicTreeNode4 = new TopicTreeNode(this, next.trim());
                    this.next.put(topicTreeNode4);
                    return topicTreeNode4;
                }
                return getNext(topicTreeNode, mqttTopicIterator);
            }
            if (mqttTopicIterator.hasMultiLevelWildcard()) {
                if (this.multiLevelEntries == null) {
                    this.multiLevelEntries = new NodeList<>();
                }
                this.multiLevelEntries.add(topicTreeEntry);
            } else {
                if (this.entries == null) {
                    this.entries = new NodeList<>();
                }
                this.entries.add(topicTreeEntry);
            }
            return null;
        }

        TopicTreeNode suback(MqttTopicIterator mqttTopicIterator, MqttTopicFilterImpl mqttTopicFilterImpl, int i, boolean z) {
            if (mqttTopicIterator.hasNext()) {
                return traverseNext(mqttTopicIterator);
            }
            if (mqttTopicIterator.hasMultiLevelWildcard()) {
                if (suback(this.multiLevelEntries, mqttTopicFilterImpl, i, z)) {
                    this.multiLevelEntries = null;
                }
            } else if (suback(this.entries, mqttTopicFilterImpl, i, z)) {
                this.entries = null;
            }
            compact();
            return null;
        }

        private static boolean suback(NodeList<TopicTreeEntry> nodeList, MqttTopicFilterImpl mqttTopicFilterImpl, int i, boolean z) {
            if (nodeList == null) {
                return false;
            }
            byte[] prefix = mqttTopicFilterImpl.getPrefix();
            NodeList.Node first = nodeList.getFirst();
            while (true) {
                TopicTreeEntry topicTreeEntry = (TopicTreeEntry) first;
                if (topicTreeEntry != null) {
                    if (topicTreeEntry.subscriptionIdentifier == i && Arrays.equals(prefix, topicTreeEntry.topicFilterPrefix)) {
                        if (!z) {
                            topicTreeEntry.acknowledged = true;
                        } else {
                            if (topicTreeEntry.flow != null) {
                                topicTreeEntry.flow.getTopicFilters().remove(topicTreeEntry.handle);
                            }
                            nodeList.remove(topicTreeEntry);
                        }
                    }
                    first = topicTreeEntry.getNext();
                } else {
                    return nodeList.isEmpty();
                }
            }
        }

        TopicTreeNode unsubscribe(MqttTopicIterator mqttTopicIterator, MqttTopicFilterImpl mqttTopicFilterImpl) {
            if (mqttTopicIterator.hasNext()) {
                return traverseNext(mqttTopicIterator);
            }
            if (mqttTopicIterator.hasMultiLevelWildcard()) {
                if (unsubscribe(this.multiLevelEntries, mqttTopicFilterImpl)) {
                    this.multiLevelEntries = null;
                }
            } else if (unsubscribe(this.entries, mqttTopicFilterImpl)) {
                this.entries = null;
            }
            compact();
            return null;
        }

        private static boolean unsubscribe(NodeList<TopicTreeEntry> nodeList, MqttTopicFilterImpl mqttTopicFilterImpl) {
            if (nodeList == null) {
                return false;
            }
            byte[] prefix = mqttTopicFilterImpl.getPrefix();
            NodeList.Node first = nodeList.getFirst();
            while (true) {
                TopicTreeEntry topicTreeEntry = (TopicTreeEntry) first;
                if (topicTreeEntry != null) {
                    if (Arrays.equals(prefix, topicTreeEntry.topicFilterPrefix) && topicTreeEntry.acknowledged) {
                        if (topicTreeEntry.flow != null) {
                            topicTreeEntry.flow.getTopicFilters().remove(topicTreeEntry.handle);
                            if (topicTreeEntry.flow.getTopicFilters().isEmpty()) {
                                topicTreeEntry.flow.onComplete();
                            }
                        }
                        nodeList.remove(topicTreeEntry);
                    }
                    first = topicTreeEntry.getNext();
                } else {
                    return nodeList.isEmpty();
                }
            }
        }

        TopicTreeNode cancel(MqttTopicIterator mqttTopicIterator, MqttSubscribedPublishFlow mqttSubscribedPublishFlow) {
            if (mqttTopicIterator.hasNext()) {
                return traverseNext(mqttTopicIterator);
            }
            if (mqttTopicIterator.hasMultiLevelWildcard()) {
                cancel(this.multiLevelEntries, mqttSubscribedPublishFlow);
                return null;
            }
            cancel(this.entries, mqttSubscribedPublishFlow);
            return null;
        }

        private static void cancel(NodeList<TopicTreeEntry> nodeList, MqttSubscribedPublishFlow mqttSubscribedPublishFlow) {
            if (nodeList == null) {
                return;
            }
            NodeList.Node first = nodeList.getFirst();
            while (true) {
                TopicTreeEntry topicTreeEntry = (TopicTreeEntry) first;
                if (topicTreeEntry == null) {
                    return;
                }
                if (topicTreeEntry.flow != mqttSubscribedPublishFlow) {
                    first = topicTreeEntry.getNext();
                } else {
                    topicTreeEntry.flow = null;
                    topicTreeEntry.handle = null;
                    return;
                }
            }
        }

        TopicTreeNode findMatching(MqttTopicIterator mqttTopicIterator, MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows) {
            if (mqttTopicIterator.hasNext()) {
                add(mqttStatefulPublishWithFlows, this.multiLevelEntries);
                MqttTopicLevel next = mqttTopicIterator.next();
                Index<TopicTreeNode, MqttTopicLevel> index = this.next;
                TopicTreeNode topicTreeNode = index != null ? index.get(next) : null;
                TopicTreeNode topicTreeNode2 = this.singleLevel;
                if (topicTreeNode == null) {
                    return findNext(topicTreeNode2, mqttTopicIterator);
                }
                if (topicTreeNode2 == null) {
                    return findNext(topicTreeNode, mqttTopicIterator);
                }
                MqttTopicIterator mqttTopicIteratorFork = mqttTopicIterator.fork();
                TopicTreeNode topicTreeNodeFindNext = findNext(topicTreeNode, mqttTopicIterator);
                if (topicTreeNodeFindNext == null) {
                    return findNext(topicTreeNode2, mqttTopicIterator);
                }
                TopicTreeNode topicTreeNodeFindNext2 = findNext(topicTreeNode2, mqttTopicIteratorFork);
                if (topicTreeNodeFindNext2 == null) {
                    return topicTreeNodeFindNext;
                }
                while (topicTreeNodeFindNext2 != null) {
                    topicTreeNodeFindNext2 = topicTreeNodeFindNext2.findMatching(mqttTopicIteratorFork, mqttStatefulPublishWithFlows);
                }
                return topicTreeNodeFindNext;
            }
            add(mqttStatefulPublishWithFlows, this.entries);
            add(mqttStatefulPublishWithFlows, this.multiLevelEntries);
            return null;
        }

        private static void add(MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows, NodeList<TopicTreeEntry> nodeList) {
            if (nodeList == null) {
                return;
            }
            mqttStatefulPublishWithFlows.subscriptionFound = true;
            NodeList.Node first = nodeList.getFirst();
            while (true) {
                TopicTreeEntry topicTreeEntry = (TopicTreeEntry) first;
                if (topicTreeEntry == null) {
                    return;
                }
                if (topicTreeEntry.flow != null) {
                    mqttStatefulPublishWithFlows.add((MqttIncomingPublishFlow) topicTreeEntry.flow);
                }
                first = topicTreeEntry.getNext();
            }
        }

        TopicTreeNode clear(Throwable th) {
            Index<TopicTreeNode, MqttTopicLevel> index = this.next;
            if (index != null) {
                return index.any();
            }
            TopicTreeNode topicTreeNode = this.singleLevel;
            if (topicTreeNode != null) {
                return topicTreeNode;
            }
            NodeList<TopicTreeEntry> nodeList = this.entries;
            if (nodeList != null) {
                clear(nodeList, th);
                this.entries = null;
            }
            NodeList<TopicTreeEntry> nodeList2 = this.multiLevelEntries;
            if (nodeList2 != null) {
                clear(nodeList2, th);
                this.multiLevelEntries = null;
            }
            TopicTreeNode topicTreeNode2 = this.parent;
            if (topicTreeNode2 != null) {
                topicTreeNode2.removeNext(this);
            }
            return this.parent;
        }

        private static void clear(NodeList<TopicTreeEntry> nodeList, Throwable th) {
            NodeList.Node first = nodeList.getFirst();
            while (true) {
                TopicTreeEntry topicTreeEntry = (TopicTreeEntry) first;
                if (topicTreeEntry == null) {
                    return;
                }
                if (topicTreeEntry.flow != null && topicTreeEntry.acknowledged) {
                    topicTreeEntry.flow.onError(th);
                }
                first = topicTreeEntry.getNext();
            }
        }

        private TopicTreeNode getNext(TopicTreeNode topicTreeNode, MqttTopicIterator mqttTopicIterator) {
            MqttTopicLevels mqttTopicLevels;
            int iForwardWhileEqual;
            MqttTopicLevel mqttTopicLevelBefore;
            MqttTopicLevel mqttTopicLevel = topicTreeNode.topicLevel;
            if (!(mqttTopicLevel instanceof MqttTopicLevels) || (mqttTopicLevelBefore = mqttTopicLevels.before((iForwardWhileEqual = mqttTopicIterator.forwardWhileEqual((mqttTopicLevels = (MqttTopicLevels) mqttTopicLevel))))) == mqttTopicLevels) {
                return topicTreeNode;
            }
            MqttTopicLevel mqttTopicLevelAfter = mqttTopicLevels.after(iForwardWhileEqual);
            TopicTreeNode topicTreeNode2 = new TopicTreeNode(this, mqttTopicLevelBefore);
            if (mqttTopicLevelBefore.isSingleLevelWildcard()) {
                this.singleLevel = topicTreeNode2;
            } else {
                this.next.put(topicTreeNode2);
            }
            topicTreeNode.parent = topicTreeNode2;
            topicTreeNode.topicLevel = mqttTopicLevelAfter;
            if (mqttTopicLevelAfter.isSingleLevelWildcard()) {
                topicTreeNode2.singleLevel = topicTreeNode;
            } else {
                Index<TopicTreeNode, MqttTopicLevel> index = new Index<>(INDEX_SPEC);
                topicTreeNode2.next = index;
                index.put(topicTreeNode);
            }
            return topicTreeNode2;
        }

        private TopicTreeNode traverseNext(MqttTopicIterator mqttTopicIterator) {
            MqttTopicLevel next = mqttTopicIterator.next();
            if (next.isSingleLevelWildcard()) {
                return traverseNext(this.singleLevel, mqttTopicIterator);
            }
            Index<TopicTreeNode, MqttTopicLevel> index = this.next;
            if (index != null) {
                return traverseNext(index.get(next), mqttTopicIterator);
            }
            return null;
        }

        private static TopicTreeNode traverseNext(TopicTreeNode topicTreeNode, MqttTopicIterator mqttTopicIterator) {
            if (topicTreeNode == null) {
                return null;
            }
            MqttTopicLevel mqttTopicLevel = topicTreeNode.topicLevel;
            if (!(mqttTopicLevel instanceof MqttTopicLevels) || mqttTopicIterator.forwardIfEqual((MqttTopicLevels) mqttTopicLevel)) {
                return topicTreeNode;
            }
            return null;
        }

        private static TopicTreeNode findNext(TopicTreeNode topicTreeNode, MqttTopicIterator mqttTopicIterator) {
            if (topicTreeNode == null) {
                return null;
            }
            MqttTopicLevel mqttTopicLevel = topicTreeNode.topicLevel;
            if (!(mqttTopicLevel instanceof MqttTopicLevels) || mqttTopicIterator.forwardIfMatch((MqttTopicLevels) mqttTopicLevel)) {
                return topicTreeNode;
            }
            return null;
        }

        private void compact() {
            TopicTreeNode topicTreeNode = this.parent;
            if (topicTreeNode != null && this.entries == null && this.multiLevelEntries == null) {
                TopicTreeNode topicTreeNode2 = this.singleLevel;
                boolean z = topicTreeNode2 != null;
                Index<TopicTreeNode, MqttTopicLevel> index = this.next;
                boolean z2 = index != null;
                if (!z && !z2) {
                    topicTreeNode.removeNext(this);
                    this.parent.compact();
                } else if (z && !z2) {
                    fuse(topicTreeNode2);
                } else {
                    if (z || index.size() != 1) {
                        return;
                    }
                    fuse(this.next.any());
                }
            }
        }

        private void fuse(TopicTreeNode topicTreeNode) {
            TopicTreeNode topicTreeNode2 = this.parent;
            MqttTopicLevels mqttTopicLevelsConcat = MqttTopicLevels.concat(this.topicLevel, topicTreeNode.topicLevel);
            topicTreeNode.parent = topicTreeNode2;
            topicTreeNode.topicLevel = mqttTopicLevelsConcat;
            if (mqttTopicLevelsConcat.isSingleLevelWildcard()) {
                topicTreeNode2.singleLevel = topicTreeNode;
            } else {
                topicTreeNode2.next.put(topicTreeNode);
            }
        }

        private void removeNext(TopicTreeNode topicTreeNode) {
            if (topicTreeNode.topicLevel.isSingleLevelWildcard()) {
                this.singleLevel = null;
                return;
            }
            this.next.remove(topicTreeNode.topicLevel);
            if (this.next.size() == 0) {
                this.next = null;
            }
        }

        boolean isEmpty() {
            return this.next == null && this.singleLevel == null && this.entries == null && this.multiLevelEntries == null;
        }

        void getSubscriptions(MqttTopicLevel mqttTopicLevel, Map<Integer, List<MqttSubscription>> map, final Queue<IteratorNode> queue) {
            final MqttTopicLevel mqttTopicLevelConcat;
            MqttTopicLevel mqttTopicLevel2;
            if (mqttTopicLevel == null || (mqttTopicLevel2 = this.topicLevel) == null) {
                mqttTopicLevelConcat = this.topicLevel;
            } else {
                mqttTopicLevelConcat = MqttTopicLevels.concat(mqttTopicLevel, mqttTopicLevel2);
            }
            NodeList<TopicTreeEntry> nodeList = this.entries;
            if (nodeList != null) {
                getSubscriptions(nodeList, mqttTopicLevelConcat, false, map);
            }
            NodeList<TopicTreeEntry> nodeList2 = this.multiLevelEntries;
            if (nodeList2 != null) {
                getSubscriptions(nodeList2, mqttTopicLevelConcat, true, map);
            }
            Index<TopicTreeNode, MqttTopicLevel> index = this.next;
            if (index != null) {
                index.forEach(new Consumer() { // from class: com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlowTree$TopicTreeNode$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        queue.add(new MqttSubscribedPublishFlowTree.IteratorNode((MqttSubscribedPublishFlowTree.TopicTreeNode) obj, mqttTopicLevelConcat));
                    }
                });
            }
            if (this.singleLevel != null) {
                queue.add(new IteratorNode(this.singleLevel, mqttTopicLevelConcat));
            }
        }

        private static void getSubscriptions(NodeList<TopicTreeEntry> nodeList, MqttTopicLevel mqttTopicLevel, boolean z, Map<Integer, List<MqttSubscription>> map) {
            boolean z2 = false;
            for (TopicTreeEntry prev = (TopicTreeEntry) nodeList.getLast(); prev != null; prev = prev.getPrev()) {
                if (prev.acknowledged) {
                    if (prev.topicFilterPrefix != null) {
                        map.computeIfAbsent(Integer.valueOf(prev.subscriptionIdentifier), new Function() { // from class: com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlowTree$TopicTreeNode$$ExternalSyntheticLambda1
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return MqttSubscribedPublishFlowTree.TopicTreeNode.lambda$getSubscriptions$2((Integer) obj);
                            }
                        }).add(new MqttSubscription(MqttTopicLevel.toFilter(prev.topicFilterPrefix, mqttTopicLevel, z), MqttSubscription.decodeQos(prev.subscriptionOptions), MqttSubscription.decodeNoLocal(prev.subscriptionOptions), MqttSubscription.decodeRetainHandling(prev.subscriptionOptions), MqttSubscription.decodeRetainAsPublished(prev.subscriptionOptions)));
                    } else if (!z2) {
                        z2 = true;
                        map.computeIfAbsent(Integer.valueOf(prev.subscriptionIdentifier), new Function() { // from class: com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlowTree$TopicTreeNode$$ExternalSyntheticLambda1
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return MqttSubscribedPublishFlowTree.TopicTreeNode.lambda$getSubscriptions$2((Integer) obj);
                            }
                        }).add(new MqttSubscription(MqttTopicLevel.toFilter(prev.topicFilterPrefix, mqttTopicLevel, z), MqttSubscription.decodeQos(prev.subscriptionOptions), MqttSubscription.decodeNoLocal(prev.subscriptionOptions), MqttSubscription.decodeRetainHandling(prev.subscriptionOptions), MqttSubscription.decodeRetainAsPublished(prev.subscriptionOptions)));
                    }
                }
            }
        }

        static /* synthetic */ List lambda$getSubscriptions$2(Integer num) {
            return new LinkedList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IteratorNode {
        final TopicTreeNode node;
        final MqttTopicLevel parentTopicLevels;

        IteratorNode(TopicTreeNode topicTreeNode, MqttTopicLevel mqttTopicLevel) {
            this.node = topicTreeNode;
            this.parentTopicLevels = mqttTopicLevel;
        }
    }
}
