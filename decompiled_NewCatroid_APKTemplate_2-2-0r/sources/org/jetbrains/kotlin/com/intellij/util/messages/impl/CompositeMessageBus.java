package org.jetbrains.kotlin.com.intellij.util.messages.impl;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionNotApplicableException;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.EventDispatcher;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.messages.ListenerDescriptor;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBusOwner;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
class CompositeMessageBus extends MessageBusImpl implements MessageBusEx {
    private final List<MessageBusImpl> childBuses;
    private volatile Map<String, List<ListenerDescriptor>> topicClassToListenerDescriptor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 9 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 9 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "parentBus";
                break;
            case 2:
            default:
                objArr[0] = "owner";
                break;
            case 3:
                objArr[0] = "map";
                break;
            case 4:
                objArr[0] = "bus";
                break;
            case 5:
                objArr[0] = "childBus";
                break;
            case 6:
            case 8:
            case 11:
            case 13:
            case 14:
                objArr[0] = "topic";
                break;
            case 7:
                objArr[0] = HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION;
                break;
            case 9:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/CompositeMessageBus";
                break;
            case 12:
                objArr[0] = "result";
                break;
            case 15:
            case 16:
                objArr[0] = "topicAndHandlerPairs";
                break;
            case 17:
                objArr[0] = "module";
                break;
            case 18:
                objArr[0] = "listenerDescriptors";
                break;
            case 19:
                objArr[0] = "predicate";
                break;
        }
        if (i == 9 || i == 10) {
            objArr[1] = "computeSubscribers";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/CompositeMessageBus";
        }
        switch (i) {
            case 3:
                objArr[2] = "setLazyListeners";
                break;
            case 4:
                objArr[2] = "addChild";
                break;
            case 5:
                objArr[2] = "onChildBusDisposed";
                break;
            case 6:
            case 7:
                objArr[2] = "createPublisher";
                break;
            case 8:
                objArr[2] = "computeSubscribers";
                break;
            case 9:
            case 10:
                break;
            case 11:
            case 12:
                objArr[2] = "doComputeSubscribers";
                break;
            case 13:
                objArr[2] = "subscribeLazyListeners";
                break;
            case 14:
                objArr[2] = "notifyOnSubscriptionToTopicToChildren";
                break;
            case 15:
                objArr[2] = "notifyConnectionTerminated";
                break;
            case 16:
                objArr[2] = "clearSubscriberCache";
                break;
            case 17:
            case 18:
                objArr[2] = "unsubscribeLazyListeners";
                break;
            case 19:
                objArr[2] = "disconnectPluginConnections";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 9 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl, org.jetbrains.kotlin.com.intellij.util.messages.MessageBus
    public /* bridge */ /* synthetic */ MessageBusConnection connect(Disposable disposable) {
        return super.connect(disposable);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CompositeMessageBus(MessageBusOwner messageBusOwner) {
        super(messageBusOwner);
        if (messageBusOwner == null) {
            $$$reportNull$$$0(2);
        }
        this.childBuses = ContainerUtil.createLockFreeCopyOnWriteList();
        this.topicClassToListenerDescriptor = Collections.emptyMap();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl
    final boolean hasChildren() {
        return !this.childBuses.isEmpty();
    }

    final void onChildBusDisposed(MessageBusImpl messageBusImpl) {
        if (messageBusImpl == null) {
            $$$reportNull$$$0(5);
        }
        boolean zRemove = this.childBuses.remove(messageBusImpl);
        this.rootBus.waitingBuses.get().remove(messageBusImpl);
        CompositeMessageBus compositeMessageBus = this;
        do {
            compositeMessageBus.subscriberCache.clear();
            compositeMessageBus = compositeMessageBus.parentBus;
        } while (compositeMessageBus != null);
        LOG.assertTrue(zRemove);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl
    final <L> MessageBusImpl.MessagePublisher<L> createPublisher(Topic<L> topic, Topic.BroadcastDirection broadcastDirection) {
        if (topic == null) {
            $$$reportNull$$$0(6);
        }
        if (broadcastDirection == null) {
            $$$reportNull$$$0(7);
        }
        if (broadcastDirection == Topic.BroadcastDirection.TO_PARENT) {
            return new MessageBusImpl.ToParentMessagePublisher(topic, this);
        }
        if (broadcastDirection == Topic.BroadcastDirection.TO_DIRECT_CHILDREN) {
            if (this.parentBus != null) {
                throw new IllegalArgumentException("Broadcast direction TO_DIRECT_CHILDREN is allowed only for app level message bus. Please publish to app level message bus or change topic " + topic.getListenerClass() + " broadcast direction to NONE or TO_PARENT");
            }
            return new ToDirectChildrenMessagePublisher(topic, this);
        }
        return new MessageBusImpl.MessagePublisher<>(topic, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ToDirectChildrenMessagePublisher<L> extends MessageBusImpl.MessagePublisher<L> implements InvocationHandler {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "bus";
            } else if (i != 2) {
                objArr[0] = "topic";
            } else {
                objArr[0] = "method";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/CompositeMessageBus$ToDirectChildrenMessagePublisher";
            if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "publish";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ToDirectChildrenMessagePublisher(Topic<L> topic, CompositeMessageBus compositeMessageBus) {
            super(topic, compositeMessageBus);
            if (topic == null) {
                $$$reportNull$$$0(0);
            }
            if (compositeMessageBus == null) {
                $$$reportNull$$$0(1);
            }
        }

        /* JADX INFO: renamed from: lambda$publish$0$org-jetbrains-kotlin-com-intellij-util-messages-impl-CompositeMessageBus$ToDirectChildrenMessagePublisher, reason: not valid java name */
        /* synthetic */ Object[] m6745x292df4af(Topic topic) {
            return this.bus.computeSubscribers(topic);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl.MessagePublisher
        boolean publish(Method method, Object[] objArr, MessageQueue messageQueue) {
            List<Throwable> listExecuteOrAddToQueue;
            boolean z;
            if (method == null) {
                $$$reportNull$$$0(2);
            }
            Object[] objArrComputeIfAbsent = this.bus.subscriberCache.computeIfAbsent((Topic<?>) this.topic, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.CompositeMessageBus$ToDirectChildrenMessagePublisher$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m6745x292df4af((Topic) obj);
                }
            });
            if (objArrComputeIfAbsent.length != 0) {
                listExecuteOrAddToQueue = executeOrAddToQueue(this.topic, method, objArr, objArrComputeIfAbsent, messageQueue, this.bus.messageDeliveryListener, null);
                z = true;
            } else {
                listExecuteOrAddToQueue = null;
                z = false;
            }
            List<Throwable> listExecuteOrAddToQueue2 = listExecuteOrAddToQueue;
            for (final MessageBusImpl messageBusImpl : ((CompositeMessageBus) this.bus).childBuses) {
                if (!messageBusImpl.owner.isDisposed()) {
                    Object[] objArrComputeIfAbsent2 = messageBusImpl.subscriberCache.computeIfAbsent((Topic<?>) this.topic, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.CompositeMessageBus$ToDirectChildrenMessagePublisher$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return CompositeMessageBus.ToDirectChildrenMessagePublisher.lambda$publish$1(messageBusImpl, (Topic) obj);
                        }
                    });
                    if (objArrComputeIfAbsent2.length != 0) {
                        listExecuteOrAddToQueue2 = executeOrAddToQueue(this.topic, method, objArr, objArrComputeIfAbsent2, messageQueue, this.bus.messageDeliveryListener, listExecuteOrAddToQueue2);
                        z = true;
                    }
                }
            }
            if (listExecuteOrAddToQueue2 != null) {
                EventDispatcher.throwExceptions(listExecuteOrAddToQueue2);
            }
            return z;
        }

        static /* synthetic */ Object[] lambda$publish$1(MessageBusImpl messageBusImpl, Topic topic) {
            ArrayList arrayList = new ArrayList();
            messageBusImpl.doComputeSubscribers(topic, arrayList, !messageBusImpl.owner.isParentLazyListenersIgnored());
            return arrayList.isEmpty() ? ArrayUtilRt.EMPTY_OBJECT_ARRAY : arrayList.toArray();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl
    final Object[] computeSubscribers(Topic<?> topic) {
        if (topic == null) {
            $$$reportNull$$$0(8);
        }
        if (this.owner.isDisposed()) {
            Object[] objArr = ArrayUtilRt.EMPTY_OBJECT_ARRAY;
            if (objArr == null) {
                $$$reportNull$$$0(9);
            }
            return objArr;
        }
        Object[] objArrComputeSubscribers = super.computeSubscribers(topic);
        if (objArrComputeSubscribers == null) {
            $$$reportNull$$$0(10);
        }
        return objArrComputeSubscribers;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl
    final void doComputeSubscribers(Topic<?> topic, List<Object> list, boolean z) {
        if (topic == null) {
            $$$reportNull$$$0(11);
        }
        if (list == null) {
            $$$reportNull$$$0(12);
        }
        if (z) {
            subscribeLazyListeners(topic);
        }
        super.doComputeSubscribers(topic, list, z);
        if (topic.getBroadcastDirection() == Topic.BroadcastDirection.TO_CHILDREN) {
            for (MessageBusImpl messageBusImpl : this.childBuses) {
                if (!messageBusImpl.isDisposed()) {
                    messageBusImpl.doComputeSubscribers(topic, list, !messageBusImpl.owner.isParentLazyListenersIgnored());
                }
            }
        }
    }

    private <L> void subscribeLazyListeners(final Topic<L> topic) {
        List<ListenerDescriptor> listRemove;
        if (topic == null) {
            $$$reportNull$$$0(13);
        }
        if (topic.getListenerClass() == Runnable.class || (listRemove = this.topicClassToListenerDescriptor.remove(topic.getListenerClass().getName())) == null) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (ListenerDescriptor listenerDescriptor : listRemove) {
            try {
                ((List) linkedHashMap.computeIfAbsent(listenerDescriptor.pluginDescriptor, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.CompositeMessageBus$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return CompositeMessageBus.lambda$subscribeLazyListeners$0((PluginDescriptor) obj);
                    }
                })).add(this.owner.createListener(listenerDescriptor));
            } catch (ExtensionNotApplicableException unused) {
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Throwable th) {
                LOG.error("Cannot create listener", th);
            }
        }
        linkedHashMap.forEach(new BiConsumer() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.CompositeMessageBus$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.m6744x718b3ac0(topic, (PluginDescriptor) obj, (List) obj2);
            }
        });
    }

    static /* synthetic */ List lambda$subscribeLazyListeners$0(PluginDescriptor pluginDescriptor) {
        return new ArrayList();
    }

    /* JADX INFO: renamed from: lambda$subscribeLazyListeners$1$org-jetbrains-kotlin-com-intellij-util-messages-impl-CompositeMessageBus, reason: not valid java name */
    /* synthetic */ void m6744x718b3ac0(Topic topic, PluginDescriptor pluginDescriptor, List list) {
        this.subscribers.add(new DescriptorBasedMessageBusConnection(pluginDescriptor, topic, list));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl
    final void notifyOnSubscriptionToTopicToChildren(Topic<?> topic) {
        if (topic == null) {
            $$$reportNull$$$0(14);
        }
        for (MessageBusImpl messageBusImpl : this.childBuses) {
            messageBusImpl.subscriberCache.remove(topic);
            messageBusImpl.notifyOnSubscriptionToTopicToChildren(topic);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl
    final boolean notifyConnectionTerminated(final Object[] objArr) {
        if (objArr == null) {
            $$$reportNull$$$0(15);
        }
        if (!super.notifyConnectionTerminated(objArr)) {
            return false;
        }
        this.childBuses.forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.CompositeMessageBus$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((MessageBusImpl) obj).clearSubscriberCache(objArr);
            }
        });
        Set<MessageBusImpl> set = this.rootBus.waitingBuses.get();
        if (!set.isEmpty()) {
            set.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.CompositeMessageBus$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return CompositeMessageBus.lambda$notifyConnectionTerminated$4(objArr, (MessageBusImpl) obj);
                }
            });
        }
        return false;
    }

    static /* synthetic */ boolean lambda$notifyConnectionTerminated$4(final Object[] objArr, MessageBusImpl messageBusImpl) {
        MessageQueue messageQueue = messageBusImpl.messageQueue.get();
        Deque<Message> deque = messageQueue.queue;
        return !deque.isEmpty() && deque.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.CompositeMessageBus$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MessageBusConnectionImpl.nullizeHandlersFromMessage((Message) obj, objArr);
            }
        }) && messageQueue.current == null && deque.isEmpty();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl
    final void clearSubscriberCache(final Object[] objArr) {
        if (objArr == null) {
            $$$reportNull$$$0(16);
        }
        super.clearSubscriberCache(objArr);
        this.childBuses.forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.CompositeMessageBus$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((MessageBusImpl) obj).clearSubscriberCache(objArr);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl
    final void removeEmptyConnectionsRecursively() {
        super.removeEmptyConnectionsRecursively();
        this.childBuses.forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.CompositeMessageBus$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((MessageBusImpl) obj).removeEmptyConnectionsRecursively();
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl
    protected final void disposeChildren() {
        this.childBuses.forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.CompositeMessageBus$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Disposer.dispose((MessageBusImpl) obj);
            }
        });
    }
}
