package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.ConcurrentKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: compiled from: ArrayBroadcastChannel.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/channels/ArrayBroadcastChannel.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000L2\b\u0012\u0004\u0012\u00028\u00000M:\u0001JB\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\t\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0017¢\u0006\u0004\b\t\u0010\nJ\u001f\u0010\t\u001a\u00020\r2\u000e\u0010\u0007\u001a\n\u0018\u00010\u000bj\u0004\u0018\u0001`\fH\u0016¢\u0006\u0004\b\t\u0010\u000eJ\u0019\u0010\u000f\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\u000f\u0010\nJ\u000f\u0010\u0010\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0010\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016¢\u0006\u0004\b\u0012\u0010\nJ\u000f\u0010\u0014\u001a\u00020\u0013H\u0002¢\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u0013H\u0002¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00028\u0000H\u0014¢\u0006\u0004\b\u001b\u0010\u001cJ#\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0014¢\u0006\u0004\b\u001f\u0010 J\u0015\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000!H\u0016¢\u0006\u0004\b\"\u0010#J4\u0010'\u001a\u00020\r2\u0010\b\u0002\u0010%\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$2\u0010\b\u0002\u0010&\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$H\u0082\u0010¢\u0006\u0004\b'\u0010(R\u001c\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0)8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b*\u0010+R\u0014\u0010/\u001a\u00020,8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0018\u00102\u001a\u000600j\u0002`18\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b2\u00103R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u00104\u001a\u0004\b5\u00106R$\u0010;\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u00138B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b8\u0010\u0015\"\u0004\b9\u0010:R\u0014\u0010<\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0014\u0010>\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b>\u0010=R$\u0010A\u001a\u00020\u00022\u0006\u00107\u001a\u00020\u00028B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b?\u00106\"\u0004\b@\u0010\u0005R6\u0010D\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000$0Bj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000$`C8\u0002X\u0082\u0004¢\u0006\f\n\u0004\bD\u0010E\u0012\u0004\bF\u0010\u0011R$\u0010I\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u00138B@BX\u0082\u000e¢\u0006\f\u001a\u0004\bG\u0010\u0015\"\u0004\bH\u0010:¨\u0006K"}, d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", ExifInterface.LONGITUDE_EAST, "", "capacity", "<init>", "(I)V", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "cancelInternal", "checkSubOffers", "()V", "close", "", "computeMinHead", "()J", "index", "elementAt", "(J)Ljava/lang/Object;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "addSub", "removeSub", "updateHead", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;)V", "", "buffer", "[Ljava/lang/Object;", "", "getBufferDebugString", "()Ljava/lang/String;", "bufferDebugString", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "bufferLock", "Ljava/util/concurrent/locks/ReentrantLock;", "I", "getCapacity", "()I", "value", "getHead", "setHead", "(J)V", "head", "isBufferAlwaysFull", "()Z", "isBufferFull", "getSize", "setSize", "size", "", "Lkotlinx/coroutines/internal/SubscribersList;", "subscribers", "Ljava/util/List;", "getSubscribers$annotations", "getTail", "setTail", "tail", "Subscriber", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/BroadcastChannel;"})
public final class ArrayBroadcastChannel<E> extends AbstractSendChannel<E> implements BroadcastChannel<E> {
    private final int capacity;

    @NotNull
    private final ReentrantLock bufferLock;

    @NotNull
    private final Object[] buffer;

    @NotNull
    private volatile /* synthetic */ long _head;

    @NotNull
    private volatile /* synthetic */ long _tail;

    @NotNull
    private volatile /* synthetic */ int _size;

    @NotNull
    private final List<Subscriber<E>> subscribers;

    private static /* synthetic */ void getSubscribers$annotations() {
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public ArrayBroadcastChannel(int capacity) {
        super(null);
        this.capacity = capacity;
        if (!(this.capacity >= 1)) {
            throw new IllegalArgumentException(("ArrayBroadcastChannel capacity must be at least 1, but " + this.capacity + " was specified").toString());
        }
        this.bufferLock = new ReentrantLock();
        this.buffer = new Object[this.capacity];
        this._head = 0L;
        this._tail = 0L;
        this._size = 0;
        this.subscribers = ConcurrentKt.subscriberList();
    }

    private final long getHead() {
        return this._head;
    }

    private final void setHead(long value) {
        this._head = value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long getTail() {
        return this._tail;
    }

    private final void setTail(long value) {
        this._tail = value;
    }

    private final int getSize() {
        return this._size;
    }

    private final void setSize(int value) {
        this._size = value;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected boolean isBufferAlwaysFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected boolean isBufferFull() {
        return getSize() >= this.capacity;
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    @NotNull
    public ReceiveChannel<E> openSubscription() {
        Subscriber it = new Subscriber(this);
        updateHead$default(this, it, null, 2, null);
        return it;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel, kotlinx.coroutines.channels.SendChannel
    public boolean close(@Nullable Throwable cause) {
        if (!super.close(cause)) {
            return false;
        }
        checkSubOffers();
        return true;
    }

    @Override // kotlinx.coroutines.channels.BroadcastChannel
    public void cancel(@Nullable CancellationException cause) {
        cancel(cause);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // kotlinx.coroutines.channels.BroadcastChannel
    /* JADX INFO: renamed from: cancelInternal, reason: merged with bridge method [inline-methods] */
    public final boolean cancel(Throwable cause) {
        boolean zClose = close(cause);
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        while (it.hasNext()) {
            it.next().cancel(cause);
        }
        return zClose;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    protected Object offerInternal(E element) {
        ReentrantLock $this$withLock$iv = this.bufferLock;
        ReentrantLock reentrantLock = $this$withLock$iv;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                reentrantLock.unlock();
                return closedForSend;
            }
            int size = getSize();
            if (size >= this.capacity) {
                Symbol symbol = AbstractChannelKt.OFFER_FAILED;
                reentrantLock.unlock();
                return symbol;
            }
            long tail = getTail();
            this.buffer[(int) (tail % ((long) this.capacity))] = element;
            setSize(size + 1);
            setTail(tail + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            checkSubOffers();
            return AbstractChannelKt.OFFER_SUCCESS;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    protected Object offerSelectInternal(E element, @NotNull SelectInstance<?> select) {
        ReentrantLock $this$withLock$iv = this.bufferLock;
        ReentrantLock reentrantLock = $this$withLock$iv;
        reentrantLock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                reentrantLock.unlock();
                return closedForSend;
            }
            int size = getSize();
            if (size >= this.capacity) {
                Symbol symbol = AbstractChannelKt.OFFER_FAILED;
                reentrantLock.unlock();
                return symbol;
            }
            if (!select.trySelect()) {
                Object already_selected = SelectKt.getALREADY_SELECTED();
                reentrantLock.unlock();
                return already_selected;
            }
            long tail = getTail();
            this.buffer[(int) (tail % ((long) this.capacity))] = element;
            setSize(size + 1);
            setTail(tail + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            checkSubOffers();
            return AbstractChannelKt.OFFER_SUCCESS;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    private final void checkSubOffers() {
        boolean updated = false;
        boolean hasSubs = false;
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        while (it.hasNext()) {
            hasSubs = true;
            if (it.next().checkOffer()) {
                updated = true;
            }
        }
        if (updated || !hasSubs) {
            updateHead$default(this, null, null, 3, null);
        }
    }

    private final void updateHead(Subscriber<E> addSub, Subscriber<E> removeSub) {
        Send sendTakeFirstSendOrPeekClosed;
        Symbol token;
        ArrayBroadcastChannel<E> arrayBroadcastChannel = this;
        Subscriber<E> subscriber = addSub;
        Subscriber<E> subscriber2 = removeSub;
        while (true) {
            Subscriber<E> subscriber3 = subscriber2;
            ArrayBroadcastChannel<E> arrayBroadcastChannel2 = arrayBroadcastChannel;
            Subscriber<E> subscriber4 = subscriber;
            ReentrantLock $this$withLock$iv = arrayBroadcastChannel2.bufferLock;
            ReentrantLock reentrantLock = $this$withLock$iv;
            reentrantLock.lock();
            if (subscriber4 != null) {
                try {
                    subscriber4.setSubHead(arrayBroadcastChannel2.getTail());
                    boolean wasEmpty = arrayBroadcastChannel2.subscribers.isEmpty();
                    arrayBroadcastChannel2.subscribers.add(subscriber4);
                    if (!wasEmpty) {
                        reentrantLock.unlock();
                        return;
                    }
                } catch (Throwable th) {
                    reentrantLock.unlock();
                    throw th;
                }
            }
            if (subscriber3 != null) {
                arrayBroadcastChannel2.subscribers.remove(subscriber3);
                if (arrayBroadcastChannel2.getHead() != subscriber3.getSubHead()) {
                    reentrantLock.unlock();
                    return;
                }
            }
            long minHead = arrayBroadcastChannel2.computeMinHead();
            long tail = arrayBroadcastChannel2.getTail();
            long head = arrayBroadcastChannel2.getHead();
            long targetHead = RangesKt.coerceAtMost(minHead, tail);
            if (targetHead <= head) {
                reentrantLock.unlock();
                return;
            }
            int size = arrayBroadcastChannel2.getSize();
            while (true) {
                if (head >= targetHead) {
                    reentrantLock.unlock();
                    return;
                }
                arrayBroadcastChannel2.buffer[(int) (head % ((long) arrayBroadcastChannel2.capacity))] = null;
                boolean wasFull = size >= arrayBroadcastChannel2.capacity;
                head++;
                arrayBroadcastChannel2.setHead(head);
                size--;
                arrayBroadcastChannel2.setSize(size);
                if (wasFull) {
                    while (true) {
                        sendTakeFirstSendOrPeekClosed = arrayBroadcastChannel2.takeFirstSendOrPeekClosed();
                        if (sendTakeFirstSendOrPeekClosed != null && !(sendTakeFirstSendOrPeekClosed instanceof Closed)) {
                            Intrinsics.checkNotNull(sendTakeFirstSendOrPeekClosed);
                            token = sendTakeFirstSendOrPeekClosed.tryResumeSend(null);
                            if (token != null) {
                                break;
                            }
                        }
                    }
                }
            }
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(token == CancellableContinuationImplKt.RESUME_TOKEN)) {
                    throw new AssertionError();
                }
            }
            arrayBroadcastChannel2.buffer[(int) (tail % ((long) arrayBroadcastChannel2.capacity))] = sendTakeFirstSendOrPeekClosed.getPollResult();
            arrayBroadcastChannel2.setSize(size + 1);
            arrayBroadcastChannel2.setTail(tail + 1);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
            sendTakeFirstSendOrPeekClosed.completeResumeSend();
            arrayBroadcastChannel2.checkSubOffers();
            arrayBroadcastChannel = arrayBroadcastChannel2;
            subscriber = null;
            subscriber2 = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void updateHead$default(ArrayBroadcastChannel arrayBroadcastChannel, Subscriber subscriber, Subscriber subscriber2, int i, Object obj) {
        if ((i & 1) != 0) {
            subscriber = null;
        }
        if ((i & 2) != 0) {
            subscriber2 = null;
        }
        arrayBroadcastChannel.updateHead(subscriber, subscriber2);
    }

    private final long computeMinHead() {
        long minHead = Long.MAX_VALUE;
        Iterator<Subscriber<E>> it = this.subscribers.iterator();
        while (it.hasNext()) {
            minHead = RangesKt.coerceAtMost(minHead, it.next().getSubHead());
        }
        return minHead;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final E elementAt(long index) {
        return (E) this.buffer[(int) (index % ((long) this.capacity))];
    }

    /* JADX INFO: compiled from: ArrayBroadcastChannel.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u00028\u00010'2\b\u0012\u0004\u0012\u00028\u00010(B\u0015\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\u000b\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\r\u0010\bJ\u0011\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u0011\u0010\u0011\u001a\u0004\u0018\u00010\u000eH\u0014¢\u0006\u0004\b\u0011\u0010\u0010J\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0014¢\u0006\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\bR\u0014\u0010\u0018\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\bR\u0014\u0010\u0019\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\bR\u0014\u0010\u001a\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\bR$\u0010!\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0018\u0010$\u001a\u00060\"j\u0002`#8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b$\u0010%¨\u0006&"}, d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "broadcastChannel", "<init>", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel;)V", "", "checkOffer", "()Z", "", "cause", "close", "(Ljava/lang/Throwable;)Z", "needsToCheckOfferWithoutLock", "", "peekUnderLock", "()Ljava/lang/Object;", "pollInternal", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "pollSelectInternal", "(Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "isBufferAlwaysEmpty", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "", "value", "getSubHead", "()J", "setSubHead", "(J)V", "subHead", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "subLock", "Ljava/util/concurrent/locks/ReentrantLock;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;"})
    private static final class Subscriber<E> extends AbstractChannel<E> implements ReceiveChannel<E> {

        @NotNull
        private final ArrayBroadcastChannel<E> broadcastChannel;

        @NotNull
        private final ReentrantLock subLock;

        @NotNull
        private volatile /* synthetic */ long _subHead;

        public Subscriber(@NotNull ArrayBroadcastChannel<E> arrayBroadcastChannel) {
            super(null);
            this.broadcastChannel = arrayBroadcastChannel;
            this.subLock = new ReentrantLock();
            this._subHead = 0L;
        }

        public final long getSubHead() {
            return this._subHead;
        }

        public final void setSubHead(long value) {
            this._subHead = value;
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        protected boolean isBufferAlwaysEmpty() {
            return false;
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        protected boolean isBufferEmpty() {
            return getSubHead() >= this.broadcastChannel.getTail();
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel
        protected boolean isBufferAlwaysFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel
        protected boolean isBufferFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        @Override // kotlinx.coroutines.channels.AbstractSendChannel, kotlinx.coroutines.channels.SendChannel
        public boolean close(@Nullable Throwable cause) {
            boolean wasClosed = super.close(cause);
            if (wasClosed) {
                ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, this, 1, null);
                ReentrantLock $this$withLock$iv = this.subLock;
                ReentrantLock reentrantLock = $this$withLock$iv;
                reentrantLock.lock();
                try {
                    setSubHead(this.broadcastChannel.getTail());
                    Unit unit = Unit.INSTANCE;
                } finally {
                    reentrantLock.unlock();
                }
            }
            return wasClosed;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final boolean checkOffer() {
            boolean z = false;
            Closed closed = null;
            while (needsToCheckOfferWithoutLock() && this.subLock.tryLock()) {
                try {
                    Object objPeekUnderLock = peekUnderLock();
                    if (objPeekUnderLock == AbstractChannelKt.POLL_FAILED) {
                        this.subLock.unlock();
                    } else {
                        if (objPeekUnderLock instanceof Closed) {
                            closed = (Closed) objPeekUnderLock;
                            this.subLock.unlock();
                            break;
                        }
                        ReceiveOrClosed<E> receiveOrClosedTakeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                        if (receiveOrClosedTakeFirstReceiveOrPeekClosed == 0) {
                            this.subLock.unlock();
                            break;
                        }
                        if (receiveOrClosedTakeFirstReceiveOrPeekClosed instanceof Closed) {
                            this.subLock.unlock();
                            break;
                        }
                        Symbol symbolTryResumeReceive = receiveOrClosedTakeFirstReceiveOrPeekClosed.tryResumeReceive(objPeekUnderLock, null);
                        if (symbolTryResumeReceive == null) {
                            this.subLock.unlock();
                        } else {
                            if (DebugKt.getASSERTIONS_ENABLED()) {
                                if (!(symbolTryResumeReceive == CancellableContinuationImplKt.RESUME_TOKEN)) {
                                    throw new AssertionError();
                                }
                            }
                            setSubHead(getSubHead() + 1);
                            z = true;
                            this.subLock.unlock();
                            receiveOrClosedTakeFirstReceiveOrPeekClosed.completeResumeReceive(objPeekUnderLock);
                        }
                    }
                } catch (Throwable th) {
                    this.subLock.unlock();
                    throw th;
                }
            }
            Closed closed2 = closed;
            if (closed2 != null) {
                close(closed2.closeCause);
            }
            return z;
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        @Nullable
        protected Object pollInternal() {
            boolean updated = false;
            ReentrantLock $this$withLock$iv = this.subLock;
            ReentrantLock reentrantLock = $this$withLock$iv;
            reentrantLock.lock();
            try {
                Object result = peekUnderLock();
                if (!(result instanceof Closed) && result != AbstractChannelKt.POLL_FAILED) {
                    long subHead = getSubHead();
                    setSubHead(subHead + 1);
                    updated = true;
                }
                reentrantLock.unlock();
                Closed closed = result instanceof Closed ? (Closed) result : null;
                if (closed != null) {
                    Closed it = closed;
                    close(it.closeCause);
                }
                if (checkOffer()) {
                    updated = true;
                }
                if (updated) {
                    ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3, null);
                }
                return result;
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }

        @Override // kotlinx.coroutines.channels.AbstractChannel
        @Nullable
        protected Object pollSelectInternal(@NotNull SelectInstance<?> select) {
            boolean updated = false;
            ReentrantLock $this$withLock$iv = this.subLock;
            ReentrantLock reentrantLock = $this$withLock$iv;
            reentrantLock.lock();
            try {
                Object result = peekUnderLock();
                if (!(result instanceof Closed) && result != AbstractChannelKt.POLL_FAILED) {
                    if (select.trySelect()) {
                        long subHead = getSubHead();
                        setSubHead(subHead + 1);
                        updated = true;
                    } else {
                        result = SelectKt.getALREADY_SELECTED();
                    }
                }
                Object result2 = result;
                reentrantLock.unlock();
                Closed closed = result2 instanceof Closed ? (Closed) result2 : null;
                if (closed != null) {
                    Closed it = closed;
                    close(it.closeCause);
                }
                if (checkOffer()) {
                    updated = true;
                }
                if (updated) {
                    ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3, null);
                }
                return result2;
            } catch (Throwable th) {
                reentrantLock.unlock();
                throw th;
            }
        }

        private final boolean needsToCheckOfferWithoutLock() {
            if (getClosedForReceive() != null) {
                return false;
            }
            if (isBufferEmpty() && this.broadcastChannel.getClosedForReceive() == null) {
                return false;
            }
            return true;
        }

        private final Object peekUnderLock() {
            long subHead = getSubHead();
            Closed<?> closedForReceive = this.broadcastChannel.getClosedForReceive();
            long tail = this.broadcastChannel.getTail();
            if (subHead < tail) {
                Object result = this.broadcastChannel.elementAt(subHead);
                Closed<?> closedForReceive2 = getClosedForReceive();
                return closedForReceive2 != null ? closedForReceive2 : result;
            }
            if (closedForReceive != null) {
                return closedForReceive;
            }
            Closed<?> closedForReceive3 = getClosedForReceive();
            return closedForReceive3 == null ? AbstractChannelKt.POLL_FAILED : closedForReceive3;
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    protected String getBufferDebugString() {
        return "(buffer:capacity=" + this.buffer.length + ",size=" + getSize() + ')';
    }
}
