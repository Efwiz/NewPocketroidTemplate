package io.github.rosemoe.sora.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes11.dex */
public final class EventManager {
    private final EventReceiver<?>[][] caches;
    private final List<EventManager> children;
    private boolean detached;
    private boolean enabled;
    private final ReadWriteLock lock;
    private final EventManager parent;
    private final Map<Class<?>, Receivers> receivers;

    public interface NoUnsubscribeReceiver<T extends Event> {
        void onReceive(T t);
    }

    public EventManager() {
        this(null);
    }

    public EventManager(EventManager eventManager) {
        this.caches = new EventReceiver[5][];
        this.enabled = true;
        this.detached = false;
        this.receivers = new HashMap();
        this.parent = eventManager;
        this.lock = new ReentrantReadWriteLock();
        this.children = new Vector();
        if (eventManager != null) {
            eventManager.children.add(this);
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean z) {
        if (this.parent == null && !z) {
            throw new IllegalStateException("The event manager is set to be root, and can not be disabled");
        }
        this.enabled = z;
    }

    public EventManager getRootManager() {
        checkDetached();
        EventManager eventManager = this.parent;
        return eventManager == null ? this : eventManager.getRootManager();
    }

    public <T extends Event> int dispatchEventFromRoot(T t) {
        return getRootManager().dispatchEvent(t);
    }

    public void detach() {
        if (this.parent == null) {
            throw new IllegalStateException("root manager can not be detached");
        }
        checkDetached();
        this.detached = true;
        this.parent.children.remove(this);
    }

    private void checkDetached() {
        if (this.detached) {
            throw new IllegalStateException("already detached");
        }
    }

    <T extends Event> Receivers<T> getReceivers(Class<T> cls) {
        this.lock.readLock().lock();
        try {
            Receivers receivers = this.receivers.get(cls);
            this.lock.readLock().unlock();
            if (receivers == null) {
                this.lock.writeLock().lock();
                try {
                    receivers = this.receivers.get(cls);
                    if (receivers == null) {
                        receivers = new Receivers<>();
                        this.receivers.put(cls, receivers);
                    }
                } finally {
                    this.lock.writeLock().unlock();
                }
            }
            return receivers;
        } catch (Throwable th) {
            this.lock.readLock().unlock();
            throw th;
        }
    }

    public <T extends Event> SubscriptionReceipt<T> subscribe(Class<T> cls, EventReceiver<T> eventReceiver) {
        return subscribeEvent(cls, eventReceiver);
    }

    public <T extends Event> SubscriptionReceipt<T> subscribeAlways(Class<T> cls, final NoUnsubscribeReceiver<T> noUnsubscribeReceiver) {
        return subscribeEvent(cls, new EventReceiver() { // from class: io.github.rosemoe.sora.event.EventManager$$ExternalSyntheticLambda0
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                noUnsubscribeReceiver.onReceive(event);
            }
        });
    }

    public <T extends Event> SubscriptionReceipt<T> subscribeEvent(Class<T> cls, EventReceiver<T> eventReceiver) {
        Receivers<T> receivers = getReceivers(cls);
        receivers.lock.writeLock().lock();
        try {
            List<EventReceiver<T>> list = receivers.receivers;
            if (list.contains(eventReceiver)) {
                return new SubscriptionReceipt<>(this, cls, eventReceiver);
            }
            list.add(eventReceiver);
            return new SubscriptionReceipt<>(this, cls, eventReceiver);
        } finally {
            receivers.lock.writeLock().unlock();
        }
    }

    public <T extends Event> int dispatchEvent(T t) throws Throwable {
        EventManager eventManager;
        if (!this.enabled) {
            return t.getInterceptTargets();
        }
        Receivers receivers = getReceivers(t.getClass());
        receivers.lock.readLock().lock();
        try {
            int size = receivers.receivers.size();
            EventReceiver[] eventReceiverArrObtainBuffer = obtainBuffer(size);
            receivers.receivers.toArray(eventReceiverArrObtainBuffer);
            receivers.lock.readLock().unlock();
            LinkedList linkedList = null;
            try {
                Unsubscribe unsubscribe = new Unsubscribe();
                LinkedList linkedList2 = null;
                for (int i = 0; i < size; i++) {
                    try {
                        if ((t.getInterceptTargets() & 1) != 0) {
                            break;
                        }
                        EventReceiver eventReceiver = eventReceiverArrObtainBuffer[i];
                        eventReceiver.onReceive(t, unsubscribe);
                        if (unsubscribe.isUnsubscribed()) {
                            if (linkedList2 == null) {
                                linkedList2 = new LinkedList();
                            }
                            linkedList2.add(eventReceiver);
                        }
                        unsubscribe.reset();
                    } catch (Throwable th) {
                        th = th;
                        linkedList = linkedList2;
                        if (linkedList != null) {
                            receivers.lock.writeLock().lock();
                            try {
                                receivers.receivers.removeAll(linkedList);
                                receivers.lock.writeLock().unlock();
                            } catch (Throwable th2) {
                                receivers.lock.writeLock().unlock();
                                throw th2;
                            }
                        }
                        recycleBuffer(eventReceiverArrObtainBuffer);
                        throw th;
                    }
                }
                if (linkedList2 != null) {
                    receivers.lock.writeLock().lock();
                    try {
                        receivers.receivers.removeAll(linkedList2);
                        receivers.lock.writeLock().unlock();
                    } catch (Throwable th3) {
                        receivers.lock.writeLock().unlock();
                        throw th3;
                    }
                }
                recycleBuffer(eventReceiverArrObtainBuffer);
                for (int i2 = 0; i2 < this.children.size() && (t.getInterceptTargets() & 1) == 0; i2++) {
                    try {
                        eventManager = this.children.get(i2);
                    } catch (IndexOutOfBoundsException unused) {
                        eventManager = null;
                    }
                    if (eventManager != null) {
                        eventManager.dispatchEvent(t);
                    }
                }
                return t.getInterceptTargets();
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Throwable th5) {
            receivers.lock.readLock().unlock();
            throw th5;
        }
    }

    private <V extends Event> EventReceiver<V>[] obtainBuffer(int i) {
        EventReceiver<V>[] eventReceiverArr;
        synchronized (this) {
            int i2 = 0;
            while (true) {
                EventReceiver<?>[][] eventReceiverArr2 = this.caches;
                eventReceiverArr = null;
                if (i2 < eventReceiverArr2.length) {
                    EventReceiver<?>[] eventReceiverArr3 = eventReceiverArr2[i2];
                    if (eventReceiverArr3 != null && eventReceiverArr3.length >= i) {
                        eventReceiverArr2[i2] = null;
                        eventReceiverArr = (EventReceiver<V>[]) eventReceiverArr3;
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
        }
        return eventReceiverArr == null ? new EventReceiver[i] : eventReceiverArr;
    }

    private synchronized void recycleBuffer(EventReceiver<?>[] eventReceiverArr) {
        if (eventReceiverArr == null) {
            return;
        }
        int i = 0;
        while (true) {
            EventReceiver<?>[][] eventReceiverArr2 = this.caches;
            if (i >= eventReceiverArr2.length) {
                break;
            }
            if (eventReceiverArr2[i] == null) {
                Arrays.fill(eventReceiverArr, (Object) null);
                this.caches[i] = eventReceiverArr;
                break;
            }
            i++;
        }
    }

    static class Receivers<T extends Event> {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        List<EventReceiver<T>> receivers = new ArrayList();

        Receivers() {
        }
    }
}
