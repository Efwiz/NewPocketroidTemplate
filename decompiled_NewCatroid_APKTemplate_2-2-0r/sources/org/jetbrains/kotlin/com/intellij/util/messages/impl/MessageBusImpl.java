package org.jetbrains.kotlin.com.intellij.util.messages.impl;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnectHandler;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.codeWithMe.ClientId;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.EventDispatcher;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.concurrency.AppExecutorUtil;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBusOwner;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageHandler;
import org.jetbrains.kotlin.com.intellij.util.messages.SimpleMessageBusConnection;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
public class MessageBusImpl implements MessageBus {
    static final Logger LOG = Logger.getInstance((Class<?>) MessageBusImpl.class);
    private static final Object NA = new Object();
    private Disposable connectionDisposable;
    private int disposeState;
    MessageDeliveryListener messageDeliveryListener;
    final ThreadLocal<MessageQueue> messageQueue;
    final MessageBusOwner owner;
    final CompositeMessageBus parentBus;
    final ConcurrentMap<Topic<?>, Object> publisherCache;
    final RootBus rootBus;
    final Map<Topic<?>, Object[]> subscriberCache;
    final Collection<MessageHandlerHolder> subscribers;

    interface MessageHandlerHolder {
        <L> void collectHandlers(Topic<L> topic, List<? super L> list);

        boolean isDisposed();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 7 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 7 || i == 14) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "parentBus";
                break;
            case 2:
            default:
                objArr[0] = "owner";
                break;
            case 3:
                objArr[0] = "parentDisposable";
                break;
            case 4:
            case 5:
            case 7:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/MessageBusImpl";
                break;
            case 6:
            case 8:
            case 10:
            case 11:
            case 13:
            case 20:
            case 21:
            case 33:
            case 37:
                objArr[0] = "topic";
                break;
            case 9:
                objArr[0] = HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION;
                break;
            case 12:
                objArr[0] = "result";
                break;
            case 15:
                objArr[0] = "messageQueue";
                break;
            case 16:
                objArr[0] = "waitingBuses";
                break;
            case 17:
                objArr[0] = "buses";
                break;
            case 18:
                objArr[0] = "job";
                break;
            case 19:
                objArr[0] = "jobQueue";
                break;
            case 22:
            case 24:
            case 25:
            case 27:
                objArr[0] = "topicAndHandlerPairs";
                break;
            case 23:
            case 26:
                objArr[0] = "bus";
                break;
            case 28:
            case 29:
                objArr[0] = "connection";
                break;
            case 30:
                objArr[0] = "jobs";
                break;
            case 31:
                objArr[0] = "methodHandle";
                break;
            case 32:
                objArr[0] = "methodName";
                break;
            case 34:
            case 35:
            case 38:
                objArr[0] = "handler";
                break;
            case 36:
                objArr[0] = "predicate";
                break;
        }
        if (i == 4) {
            objArr[1] = MqttConnectHandler.NAME;
        } else if (i == 5) {
            objArr[1] = "simpleConnect";
        } else if (i == 7) {
            objArr[1] = "syncPublisher";
        } else if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/MessageBusImpl";
        } else {
            objArr[1] = "computeSubscribers";
        }
        switch (i) {
            case 3:
                objArr[2] = MqttConnectHandler.NAME;
                break;
            case 4:
            case 5:
            case 7:
            case 14:
                break;
            case 6:
                objArr[2] = "syncPublisher";
                break;
            case 8:
            case 9:
                objArr[2] = "createPublisher";
                break;
            case 10:
                objArr[2] = "hasUndeliveredEvents";
                break;
            case 11:
            case 12:
                objArr[2] = "doComputeSubscribers";
                break;
            case 13:
                objArr[2] = "computeSubscribers";
                break;
            case 15:
                objArr[2] = "messageRemoved";
                break;
            case 16:
                objArr[2] = "pumpMessages";
                break;
            case 17:
                objArr[2] = "pumpWaitingBuses";
                break;
            case 18:
            case 19:
                objArr[2] = "deliverMessage";
                break;
            case 20:
                objArr[2] = "notifyOnSubscription";
                break;
            case 21:
                objArr[2] = "notifyOnSubscriptionToTopicToChildren";
                break;
            case 22:
                objArr[2] = "notifyConnectionTerminated";
                break;
            case 23:
            case 24:
                objArr[2] = "clearSubscriberCacheOnConnectionTerminated";
                break;
            case 25:
            case 26:
            case 37:
            case 38:
                objArr[2] = "removeDisposedHandlers";
                break;
            case 27:
                objArr[2] = "clearSubscriberCache";
                break;
            case 28:
            case 29:
            case 30:
                objArr[2] = "deliverImmediately";
                break;
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[2] = "invokeListener";
                break;
            case 35:
                objArr[2] = "invokeMethod";
                break;
            case 36:
                objArr[2] = "disconnectPluginConnections";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 7 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    void disposeChildren() {
    }

    boolean hasChildren() {
        return false;
    }

    void notifyOnSubscriptionToTopicToChildren(Topic<?> topic) {
        if (topic == null) {
            $$$reportNull$$$0(21);
        }
    }

    MessageBusImpl(MessageBusOwner messageBusOwner) {
        if (messageBusOwner == null) {
            $$$reportNull$$$0(2);
        }
        this.messageQueue = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return new MessageQueue();
            }
        });
        this.publisherCache = new ConcurrentHashMap();
        this.subscribers = new ConcurrentLinkedQueue();
        this.subscriberCache = new ConcurrentHashMap();
        this.connectionDisposable = Disposer.newDisposable();
        this.owner = messageBusOwner;
        this.rootBus = (RootBus) this;
        this.parentBus = null;
    }

    public final String toString() {
        return "MessageBus(owner=" + this.owner + ", disposeState= " + this.disposeState + Constant.AFTER_QUTO;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.MessageBus
    public final MessageBusConnection connect() {
        return connect(this.connectionDisposable);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.MessageBus
    public final MessageBusConnectionImpl connect(Disposable disposable) {
        if (disposable == null) {
            $$$reportNull$$$0(3);
        }
        checkNotDisposed();
        MessageBusConnectionImpl messageBusConnectionImpl = new MessageBusConnectionImpl(this);
        this.subscribers.add(messageBusConnectionImpl);
        Disposer.register(disposable, messageBusConnectionImpl);
        return messageBusConnectionImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.MessageBus
    public final SimpleMessageBusConnection simpleConnect() {
        checkNotDisposed();
        SimpleMessageBusConnectionImpl simpleMessageBusConnectionImpl = new SimpleMessageBusConnectionImpl(this);
        this.subscribers.add(simpleMessageBusConnectionImpl);
        return simpleMessageBusConnectionImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.MessageBus
    public final <L> L syncPublisher(Topic<L> topic) {
        if (topic == null) {
            $$$reportNull$$$0(6);
        }
        if (isDisposed()) {
            PluginException.logPluginError(LOG, "Already disposed: " + this, null, topic.getClass());
        }
        L l = (L) this.publisherCache.computeIfAbsent(topic, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6746xa641f0de((Topic) obj);
            }
        });
        if (l == null) {
            $$$reportNull$$$0(7);
        }
        return l;
    }

    /* JADX INFO: renamed from: lambda$syncPublisher$0$org-jetbrains-kotlin-com-intellij-util-messages-impl-MessageBusImpl, reason: not valid java name */
    /* synthetic */ Object m6746xa641f0de(Topic topic) {
        Class listenerClass = topic.getListenerClass();
        return Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class[]{listenerClass}, createPublisher(topic, topic.getBroadcastDirection()));
    }

    <L> MessagePublisher<L> createPublisher(Topic<L> topic, Topic.BroadcastDirection broadcastDirection) {
        if (topic == null) {
            $$$reportNull$$$0(8);
        }
        if (broadcastDirection == null) {
            $$$reportNull$$$0(9);
        }
        if (broadcastDirection == Topic.BroadcastDirection.TO_PARENT) {
            return new ToParentMessagePublisher(topic, this);
        }
        if (broadcastDirection == Topic.BroadcastDirection.TO_DIRECT_CHILDREN) {
            throw new IllegalArgumentException("Broadcast direction TO_DIRECT_CHILDREN is allowed only for app level message bus. Please publish to app level message bus or change topic broadcast direction to NONE or TO_PARENT");
        }
        LOG.error("Topic " + topic.getListenerClass().getName() + " broadcast direction TO_CHILDREN is not allowed for module level message bus. Please change to NONE or TO_PARENT");
        return new MessagePublisher<>(topic, this);
    }

    static class MessagePublisher<L> implements InvocationHandler {
        protected final MessageBusImpl bus;
        protected final Topic<L> topic;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "bus";
            } else if (i == 2 || i == 4) {
                objArr[0] = "method";
            } else if (i != 5) {
                objArr[0] = "topic";
            } else {
                objArr[0] = "handlers";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/MessageBusImpl$MessagePublisher";
            if (i == 2) {
                objArr[2] = "publish";
            } else if (i == 3 || i == 4 || i == 5) {
                objArr[2] = "executeOrAddToQueue";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        MessagePublisher(Topic<L> topic, MessageBusImpl messageBusImpl) {
            if (topic == null) {
                $$$reportNull$$$0(0);
            }
            if (messageBusImpl == null) {
                $$$reportNull$$$0(1);
            }
            this.topic = topic;
            this.bus = messageBusImpl;
        }

        @Override // java.lang.reflect.InvocationHandler
        public final Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            if (method.getDeclaringClass() != Object.class) {
                this.bus.checkNotDisposed();
                if (this.topic.isImmediateDelivery()) {
                    publish(method, objArr, null);
                    return MessageBusImpl.NA;
                }
                Set<MessageBusImpl> set = this.bus.rootBus.waitingBuses.get();
                if (!set.isEmpty()) {
                    MessageBusImpl.pumpMessages(set);
                }
                if (publish(method, objArr, this.bus.messageQueue.get())) {
                    set.add(this.bus);
                    MessageBusImpl.pumpMessages(set);
                }
                return MessageBusImpl.NA;
            }
            return EventDispatcher.handleObjectMethod(obj, objArr, method.getName());
        }

        /* JADX INFO: renamed from: lambda$publish$0$org-jetbrains-kotlin-com-intellij-util-messages-impl-MessageBusImpl$MessagePublisher, reason: not valid java name */
        /* synthetic */ Object[] m6747xaa6c4b6d(Topic topic) {
            return this.bus.computeSubscribers(topic);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        boolean publish(Method method, Object[] objArr, MessageQueue messageQueue) {
            if (method == null) {
                $$$reportNull$$$0(2);
            }
            Object[] objArrComputeIfAbsent = this.bus.subscriberCache.computeIfAbsent((Topic<?>) this.topic, new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl$MessagePublisher$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m6747xaa6c4b6d((Topic) obj);
                }
            });
            if (objArrComputeIfAbsent.length == 0) {
                return false;
            }
            List<Throwable> listExecuteOrAddToQueue = executeOrAddToQueue(this.topic, method, objArr, objArrComputeIfAbsent, messageQueue, this.bus.messageDeliveryListener, null);
            if (listExecuteOrAddToQueue == null) {
                return true;
            }
            EventDispatcher.throwExceptions(listExecuteOrAddToQueue);
            return true;
        }

        static List<Throwable> executeOrAddToQueue(Topic<?> topic, Method method, Object[] objArr, Object[] objArr2, MessageQueue messageQueue, MessageDeliveryListener messageDeliveryListener, List<Throwable> list) {
            if (topic == null) {
                $$$reportNull$$$0(3);
            }
            if (method == null) {
                $$$reportNull$$$0(4);
            }
            if (objArr2 == null) {
                $$$reportNull$$$0(5);
            }
            MethodHandle methodHandleCompute = MethodHandleCache.compute(method, objArr);
            if (messageQueue == null) {
                List<Throwable> listInvokeListener = list;
                for (Object obj : objArr2) {
                    if (obj != null) {
                        listInvokeListener = MessageBusImpl.invokeListener(methodHandleCompute, method.getName(), objArr, topic, obj, messageDeliveryListener, listInvokeListener);
                    }
                }
                return listInvokeListener;
            }
            messageQueue.queue.offerLast(new Message(topic, methodHandleCompute, method.getName(), objArr, objArr2));
            return list;
        }
    }

    static final class ToParentMessagePublisher<L> extends MessagePublisher<L> implements InvocationHandler {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "bus";
            } else if (i != 2) {
                objArr[0] = "topic";
            } else {
                objArr[0] = "method";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/impl/MessageBusImpl$ToParentMessagePublisher";
            if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "publish";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ToParentMessagePublisher(Topic<L> topic, MessageBusImpl messageBusImpl) {
            super(topic, messageBusImpl);
            if (topic == null) {
                $$$reportNull$$$0(0);
            }
            if (messageBusImpl == null) {
                $$$reportNull$$$0(1);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1, types: [org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl] */
        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl.MessagePublisher
        boolean publish(Method method, Object[] objArr, MessageQueue messageQueue) {
            Object[] objArrPutIfAbsent;
            if (method == null) {
                $$$reportNull$$$0(2);
            }
            CompositeMessageBus compositeMessageBus = this.bus;
            boolean z = false;
            List<Throwable> listExecuteOrAddToQueue = null;
            do {
                Object[] objArrComputeSubscribers = compositeMessageBus.subscriberCache.get(this.topic);
                Object[] objArr2 = (objArrComputeSubscribers != null || (objArrPutIfAbsent = compositeMessageBus.subscriberCache.putIfAbsent((Topic<?>) this.topic, (objArrComputeSubscribers = compositeMessageBus.computeSubscribers(this.topic)))) == null) ? objArrComputeSubscribers : objArrPutIfAbsent;
                if (objArr2.length != 0) {
                    z = true;
                    listExecuteOrAddToQueue = executeOrAddToQueue(this.topic, method, objArr, objArr2, messageQueue, this.bus.messageDeliveryListener, listExecuteOrAddToQueue);
                }
                compositeMessageBus = compositeMessageBus.parentBus;
            } while (compositeMessageBus != 0);
            if (listExecuteOrAddToQueue != null) {
                EventDispatcher.throwExceptions(listExecuteOrAddToQueue);
            }
            return z;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.MessageBus, org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        if (this.disposeState == 2) {
            LOG.error("Already disposed: " + this);
        }
        this.disposeState = 2;
        disposeChildren();
        Disposable disposable = this.connectionDisposable;
        if (disposable != null) {
            Disposer.dispose(disposable);
        }
        MessageQueue messageQueue = this.messageQueue.get();
        this.messageQueue.remove();
        if (!messageQueue.queue.isEmpty()) {
            LOG.error("Not delivered events in the queue: " + messageQueue.queue);
        }
        CompositeMessageBus compositeMessageBus = this.parentBus;
        if (compositeMessageBus == null) {
            this.rootBus.waitingBuses.remove();
        } else {
            compositeMessageBus.onChildBusDisposed(this);
        }
    }

    public final boolean isDisposed() {
        return this.disposeState == 2 || this.owner.isDisposed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkNotDisposed() {
        if (isDisposed()) {
            LOG.error("Already disposed: " + this);
        }
    }

    void doComputeSubscribers(Topic<?> topic, List<Object> list, boolean z) {
        if (topic == null) {
            $$$reportNull$$$0(11);
        }
        if (list == null) {
            $$$reportNull$$$0(12);
        }
        for (MessageHandlerHolder messageHandlerHolder : this.subscribers) {
            if (!messageHandlerHolder.isDisposed()) {
                messageHandlerHolder.collectHandlers(topic, list);
            }
        }
    }

    Object[] computeSubscribers(Topic<?> topic) {
        if (topic == null) {
            $$$reportNull$$$0(13);
        }
        ArrayList arrayList = new ArrayList();
        doComputeSubscribers(topic, arrayList, true);
        Object[] array = arrayList.isEmpty() ? ArrayUtilRt.EMPTY_OBJECT_ARRAY : arrayList.toArray();
        if (array == null) {
            $$$reportNull$$$0(14);
        }
        return array;
    }

    private void messageRemoved(MessageQueue messageQueue) {
        if (messageQueue == null) {
            $$$reportNull$$$0(15);
        }
        if (messageQueue.current == null && messageQueue.queue.isEmpty()) {
            this.rootBus.waitingBuses.get().remove(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void pumpMessages(Set<? extends MessageBusImpl> set) throws Throwable {
        if (set == null) {
            $$$reportNull$$$0(16);
        }
        ArrayList arrayList = new ArrayList(set.size());
        Iterator<? extends MessageBusImpl> it = set.iterator();
        while (it.hasNext()) {
            MessageBusImpl next = it.next();
            if (next.isDisposed()) {
                it.remove();
                LOG.error("Accessing disposed message bus " + next);
            } else {
                arrayList.add(next);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        pumpWaitingBuses(arrayList);
    }

    private static void pumpWaitingBuses(List<? extends MessageBusImpl> list) throws Throwable {
        if (list == null) {
            $$$reportNull$$$0(17);
        }
        List<Throwable> listDeliverMessage = null;
        for (MessageBusImpl messageBusImpl : list) {
            if (!messageBusImpl.isDisposed()) {
                MessageQueue messageQueue = messageBusImpl.messageQueue.get();
                Message message = messageQueue.current;
                if (message != null) {
                    listDeliverMessage = messageBusImpl.deliverMessage(message, messageQueue, messageBusImpl.messageDeliveryListener, listDeliverMessage);
                }
                while (true) {
                    Message messagePollFirst = messageQueue.queue.pollFirst();
                    if (messagePollFirst != null) {
                        listDeliverMessage = messageBusImpl.deliverMessage(messagePollFirst, messageQueue, messageBusImpl.messageDeliveryListener, listDeliverMessage);
                    }
                }
            }
        }
        if (listDeliverMessage != null) {
            EventDispatcher.throwExceptions(listDeliverMessage);
        }
    }

    private List<Throwable> deliverMessage(Message message, MessageQueue messageQueue, MessageDeliveryListener messageDeliveryListener, List<Throwable> list) throws Throwable {
        if (message == null) {
            $$$reportNull$$$0(18);
        }
        if (messageQueue == null) {
            $$$reportNull$$$0(19);
        }
        ClientId currentOrNull = ClientId.getCurrentOrNull();
        try {
            ClientId.trySetCurrentClientId(message.clientId);
            messageQueue.current = message;
            Object[] objArr = message.handlers;
            int i = message.currentHandlerIndex;
            int length = objArr.length;
            int i2 = length - 1;
            List<Throwable> listInvokeListener = list;
            while (i < length) {
                if (i == i2) {
                    messageQueue.current = null;
                    try {
                        messageRemoved(messageQueue);
                    } catch (Throwable th) {
                        th = th;
                        ClientId.trySetCurrentClientId(currentOrNull);
                        throw th;
                    }
                }
                message.currentHandlerIndex++;
                Object obj = objArr[i];
                if (obj != null) {
                    listInvokeListener = invokeListener(message.method, message.methodName, message.args, message.topic, obj, messageDeliveryListener, listInvokeListener);
                }
                i++;
                if (i != message.currentHandlerIndex) {
                    ClientId.trySetCurrentClientId(currentOrNull);
                    return listInvokeListener;
                }
            }
            ClientId.trySetCurrentClientId(currentOrNull);
            return listInvokeListener;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    final void notifyOnSubscription(Topic<?> topic) {
        if (topic == null) {
            $$$reportNull$$$0(20);
        }
        this.subscriberCache.remove(topic);
        if (topic.getBroadcastDirection() != Topic.BroadcastDirection.TO_CHILDREN) {
            return;
        }
        MessageBusImpl messageBusImpl = this;
        while (true) {
            messageBusImpl = messageBusImpl.parentBus;
            if (messageBusImpl == null) {
                break;
            } else {
                messageBusImpl.subscriberCache.remove(topic);
            }
        }
        if (hasChildren()) {
            notifyOnSubscriptionToTopicToChildren(topic);
        }
    }

    void removeEmptyConnectionsRecursively() {
        this.subscribers.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((MessageBusImpl.MessageHandlerHolder) obj).isDisposed();
            }
        });
    }

    boolean notifyConnectionTerminated(Object[] objArr) {
        if (objArr == null) {
            $$$reportNull$$$0(22);
        }
        if (this.disposeState != 0) {
            return false;
        }
        this.rootBus.scheduleEmptyConnectionRemoving();
        return clearSubscriberCacheOnConnectionTerminated(objArr, this);
    }

    private static boolean clearSubscriberCacheOnConnectionTerminated(Object[] objArr, MessageBusImpl messageBusImpl) {
        if (messageBusImpl == null) {
            $$$reportNull$$$0(23);
        }
        if (objArr == null) {
            $$$reportNull$$$0(24);
        }
        boolean z = false;
        for (int i = 0; i < objArr.length; i += 2) {
            Topic topic = (Topic) objArr[i];
            removeDisposedHandlers(objArr, i, topic, messageBusImpl);
            if (topic.getBroadcastDirection() == Topic.BroadcastDirection.TO_CHILDREN) {
                MessageBusImpl messageBusImpl2 = messageBusImpl;
                while (true) {
                    messageBusImpl2 = messageBusImpl2.parentBus;
                    if (messageBusImpl2 == null) {
                        break;
                    }
                    removeDisposedHandlers(objArr, i, topic, messageBusImpl2);
                }
                if (messageBusImpl.hasChildren()) {
                    z = true;
                }
            }
        }
        return z;
    }

    private static void removeDisposedHandlers(Object[] objArr, int i, Topic<?> topic, MessageBusImpl messageBusImpl) {
        if (objArr == null) {
            $$$reportNull$$$0(25);
        }
        if (messageBusImpl == null) {
            $$$reportNull$$$0(26);
        }
        Object[] objArrRemove = messageBusImpl.subscriberCache.remove(topic);
        if (objArrRemove == null) {
            return;
        }
        Object obj = objArr[i + 1];
        if (topic.isImmediateDelivery()) {
            int length = objArrRemove.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (objArrRemove[i2] == obj) {
                    objArrRemove[i2] = null;
                }
            }
        }
        messageBusImpl.removeDisposedHandlers(topic, obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void clearSubscriberCache(Object[] objArr) {
        if (objArr == null) {
            $$$reportNull$$$0(27);
        }
        for (int i = 0; i < objArr.length; i += 2) {
            this.subscriberCache.remove(objArr[i]);
        }
    }

    final void deliverImmediately(MessageBusConnectionImpl messageBusConnectionImpl) {
        List<Message> listDeliverImmediately;
        if (messageBusConnectionImpl == null) {
            $$$reportNull$$$0(28);
        }
        if (this.disposeState == 2) {
            LOG.error("Already disposed: " + this);
        }
        if (this.owner.isDisposed()) {
            return;
        }
        MessageQueue messageQueue = this.messageQueue.get();
        Deque<Message> deque = messageQueue.queue;
        if (deque.isEmpty() || (listDeliverImmediately = deliverImmediately(messageBusConnectionImpl, deque)) == null) {
            return;
        }
        for (int size = listDeliverImmediately.size() - 1; size >= 0; size--) {
            deque.addFirst(listDeliverImmediately.get(size));
        }
        List<Throwable> listDeliverMessage = null;
        for (Message message : listDeliverImmediately) {
            deque.removeFirstOccurrence(message);
            listDeliverMessage = deliverMessage(message, messageQueue, this.messageDeliveryListener, listDeliverMessage);
        }
        if (listDeliverMessage != null) {
            EventDispatcher.throwExceptions(listDeliverMessage);
        }
    }

    private static List<Message> deliverImmediately(MessageBusConnectionImpl messageBusConnectionImpl, Deque<Message> deque) {
        if (messageBusConnectionImpl == null) {
            $$$reportNull$$$0(29);
        }
        if (deque == null) {
            $$$reportNull$$$0(30);
        }
        Iterator<Message> it = deque.iterator();
        SmartList smartList = null;
        while (it.hasNext()) {
            Message next = it.next();
            Object[] objArr = next.handlers;
            int length = objArr.length;
            SmartList smartList2 = null;
            for (int i = 0; i < length; i++) {
                Object obj = objArr[i];
                if (obj != null && messageBusConnectionImpl.isMyHandler(next.topic, obj)) {
                    objArr[i] = null;
                    if (smartList2 == null) {
                        smartList2 = new SmartList();
                    }
                    smartList2.add(obj);
                }
            }
            if (smartList2 != null) {
                if (objArr.length == smartList2.size()) {
                    it.remove();
                }
                Message message = new Message(next.topic, next.method, next.methodName, next.args, smartList2.toArray());
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.add(message);
            }
        }
        return smartList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<Throwable> invokeListener(MethodHandle methodHandle, String str, Object[] objArr, Topic<?> topic, Object obj, MessageDeliveryListener messageDeliveryListener, List<Throwable> list) {
        if (methodHandle == null) {
            $$$reportNull$$$0(31);
        }
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        if (topic == null) {
            $$$reportNull$$$0(33);
        }
        if (obj == null) {
            $$$reportNull$$$0(34);
        }
        try {
            if (obj instanceof MessageHandler) {
                ((MessageHandler) obj).handle(methodHandle, objArr);
            } else if (messageDeliveryListener == null) {
                invokeMethod(obj, objArr, methodHandle);
            } else {
                long jNanoTime = System.nanoTime();
                invokeMethod(obj, objArr, methodHandle);
                messageDeliveryListener.messageDelivered(topic, str, obj, System.nanoTime() - jNanoTime);
            }
        } catch (AbstractMethodError unused) {
        } catch (Throwable th) {
            List<Throwable> arrayList = list == null ? new ArrayList<>() : list;
            arrayList.add(th);
            return arrayList;
        }
        return list;
    }

    private static void invokeMethod(Object obj, Object[] objArr, MethodHandle methodHandle) throws Throwable {
        if (obj == null) {
            $$$reportNull$$$0(35);
        }
        if (objArr == null) {
            (void) methodHandle.invoke(obj);
        } else {
            (void) methodHandle.bindTo(obj).invokeExact(objArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final class RootBus extends CompositeMessageBus {
        private final AtomicReference<CompletableFuture<?>> compactionFutureRef;
        private final AtomicInteger emptyConnectionCounter;
        final ThreadLocal<Set<MessageBusImpl>> waitingBuses;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "owner", "org/jetbrains/kotlin/com/intellij/util/messages/impl/MessageBusImpl$RootBus", "<init>"));
        }

        static /* synthetic */ Set lambda$new$0() {
            return new LinkedHashSet();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        RootBus(MessageBusOwner messageBusOwner) {
            super(messageBusOwner);
            if (messageBusOwner == null) {
                $$$reportNull$$$0(0);
            }
            this.compactionFutureRef = new AtomicReference<>();
            this.emptyConnectionCounter = new AtomicInteger();
            this.waitingBuses = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl$RootBus$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return MessageBusImpl.RootBus.lambda$new$0();
                }
            });
        }

        void scheduleEmptyConnectionRemoving() {
            int iIncrementAndGet = this.emptyConnectionCounter.incrementAndGet();
            if (iIncrementAndGet >= 128 && this.emptyConnectionCounter.compareAndSet(iIncrementAndGet, 0) && this.compactionFutureRef.get() == null) {
                CompletableFuture<Void> completableFutureRunAsync = CompletableFuture.runAsync(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl$RootBus$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m6748xb710444d();
                    }
                }, AppExecutorUtil.getAppExecutorService());
                if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.compactionFutureRef, null, completableFutureRunAsync)) {
                    return;
                }
                completableFutureRunAsync.cancel(false);
            }
        }

        /* JADX INFO: renamed from: lambda$scheduleEmptyConnectionRemoving$1$org-jetbrains-kotlin-com-intellij-util-messages-impl-MessageBusImpl$RootBus, reason: not valid java name */
        /* synthetic */ void m6748xb710444d() {
            removeEmptyConnectionsRecursively();
            this.compactionFutureRef.set(null);
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl, org.jetbrains.kotlin.com.intellij.util.messages.MessageBus, org.jetbrains.kotlin.com.intellij.openapi.Disposable
        public void dispose() {
            CompletableFuture<?> andSet = this.compactionFutureRef.getAndSet(null);
            if (andSet != null) {
                andSet.cancel(false);
            }
            super.dispose();
        }
    }

    private void removeDisposedHandlers(final Topic<?> topic, final Object obj) {
        if (topic == null) {
            $$$reportNull$$$0(37);
        }
        if (obj == null) {
            $$$reportNull$$$0(38);
        }
        MessageQueue messageQueue = this.messageQueue.get();
        if (messageQueue.queue.isEmpty() || !messageQueue.queue.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj2) {
                return MessageBusImpl.lambda$removeDisposedHandlers$1(topic, obj, (Message) obj2);
            }
        })) {
            return;
        }
        messageRemoved(messageQueue);
    }

    static /* synthetic */ boolean lambda$removeDisposedHandlers$1(Topic topic, Object obj, Message message) {
        Object obj2;
        for (int i = 0; i < message.handlers.length && (obj2 = message.handlers[i]) != null; i++) {
            if (message.topic == topic && obj2 == obj) {
                message.handlers[i] = null;
                return message.handlers.length == 1;
            }
        }
        return false;
    }
}
