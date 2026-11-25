package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: compiled from: AbstractChannel.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/channels/AbstractSendChannel.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u000006:\u0004defgB)\u0012 \u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u000e\u001a\u00020\rH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ#\u0010\u0013\u001a\u000e\u0012\u0002\b\u00030\u0011j\u0006\u0012\u0002\b\u0003`\u00122\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0013\u0010\u0014J\u001d\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u00152\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0016\u0010\u0017J\u0019\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0019\u001a\u00020\u0018H\u0014¢\u0006\u0004\b\u001b\u0010\u001cJ\u001b\u0010\u001f\u001a\u00020\u00032\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b\u001f\u0010 J#\u0010!\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b!\u0010\"J\u001b\u0010!\u001a\u00020\b2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b!\u0010#J)\u0010&\u001a\u00020\u00032\u0018\u0010%\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\u00030\u0002j\u0002`$H\u0016¢\u0006\u0004\b&\u0010\u0007J\u0019\u0010'\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0002¢\u0006\u0004\b'\u0010(J\u0017\u0010)\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0004\b)\u0010*J\u0017\u0010+\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00028\u0000H\u0014¢\u0006\u0004\b+\u0010,J#\u0010/\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010.\u001a\u0006\u0012\u0002\b\u00030-H\u0014¢\u0006\u0004\b/\u00100J\u0017\u00102\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u000201H\u0014¢\u0006\u0004\b2\u00103JX\u00109\u001a\u00020\u0003\"\u0004\b\u0001\u001042\f\u0010.\u001a\b\u0012\u0004\u0012\u00028\u00010-2\u0006\u0010\u0010\u001a\u00028\u00002(\u00108\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000107\u0012\u0006\u0012\u0004\u0018\u00010\u001a05H\u0002ø\u0001\u0000¢\u0006\u0004\b9\u0010:J\u001b\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00028\u0000H\u0086@ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010;J\u001d\u0010=\u001a\b\u0012\u0002\b\u0003\u0018\u00010<2\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b=\u0010>J\u001b\u0010?\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00028\u0000H\u0082@ø\u0001\u0000¢\u0006\u0004\b?\u0010;J\u0017\u0010@\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010<H\u0014¢\u0006\u0004\b@\u0010AJ\u0011\u0010B\u001a\u0004\u0018\u00010\u0018H\u0004¢\u0006\u0004\bB\u0010CJ\u000f\u0010E\u001a\u00020DH\u0016¢\u0006\u0004\bE\u0010FJ$\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00030G2\u0006\u0010\u0010\u001a\u00028\u0000ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\bH\u0010,J+\u0010J\u001a\u00020\u0003*\u0006\u0012\u0002\b\u0003072\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\bJ\u0010KR\u0014\u0010M\u001a\u00020D8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\bL\u0010FR\u001a\u0010P\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\bN\u0010OR\u001a\u0010R\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010OR\u0014\u0010S\u001a\u00020\n8$X¤\u0004¢\u0006\u0006\u001a\u0004\bS\u0010TR\u0014\u0010U\u001a\u00020\n8$X¤\u0004¢\u0006\u0006\u001a\u0004\bU\u0010TR\u0011\u0010V\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\bV\u0010TR\u0014\u0010W\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bW\u0010TR#\u0010[\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u0000060X8F¢\u0006\u0006\u001a\u0004\bY\u0010ZR.\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u00048\u0004X\u0085\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\\R\u001a\u0010^\u001a\u00020]8\u0004X\u0084\u0004¢\u0006\f\n\u0004\b^\u0010_\u001a\u0004\b`\u0010aR\u0014\u0010c\u001a\u00020D8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bb\u0010F\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006h"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "", "cause", "", "close", "(Ljava/lang/Throwable;)Z", "", "countQueueSize", "()I", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/AddLastDesc;", "describeSendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "describeTryOffer", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "Lkotlinx/coroutines/channels/Send;", "send", "", "enqueueSend", "(Lkotlinx/coroutines/channels/Send;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/Closed;", "closed", "helpClose", "(Lkotlinx/coroutines/channels/Closed;)V", "helpCloseAndGetSendException", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "(Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "offer", "(Ljava/lang/Object;)Z", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onClosedIdempotent", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendSuspend", "takeFirstReceiveOrPeekClosed", "()Lkotlinx/coroutines/channels/ReceiveOrClosed;", "takeFirstSendOrPeekClosed", "()Lkotlinx/coroutines/channels/Send;", "", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "trySend", "helpCloseAndResumeWithSendException", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "getBufferDebugString", "bufferDebugString", "getClosedForReceive", "()Lkotlinx/coroutines/channels/Closed;", "closedForReceive", "getClosedForSend", "closedForSend", "isBufferAlwaysFull", "()Z", "isBufferFull", "isClosedForSend", "isFullImpl", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueue", "()Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueueDebugStateString", "queueDebugStateString", "SendBuffered", "SendBufferedDesc", "SendSelect", "TryOfferDesc", "kotlinx-coroutines-core"})
public abstract class AbstractSendChannel<E> implements SendChannel<E> {

    @JvmField
    @Nullable
    protected final Function1<E, Unit> onUndeliveredElement;

    @NotNull
    private final LockFreeLinkedListHead queue = new LockFreeLinkedListHead();

    @NotNull
    private volatile /* synthetic */ Object onCloseHandler = null;
    private static final /* synthetic */ AtomicReferenceFieldUpdater onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(AbstractSendChannel.class, Object.class, "onCloseHandler");

    protected abstract boolean isBufferAlwaysFull();

    protected abstract boolean isBufferFull();

    /* JADX WARN: Multi-variable type inference failed */
    public AbstractSendChannel(@Nullable Function1<? super E, Unit> function1) {
        this.onUndeliveredElement = function1;
    }

    @NotNull
    protected final LockFreeLinkedListHead getQueue() {
        return this.queue;
    }

    @NotNull
    protected Object offerInternal(E element) {
        ReceiveOrClosed<E> receiveOrClosedTakeFirstReceiveOrPeekClosed;
        Symbol token;
        do {
            receiveOrClosedTakeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
            if (receiveOrClosedTakeFirstReceiveOrPeekClosed == null) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            token = receiveOrClosedTakeFirstReceiveOrPeekClosed.tryResumeReceive(element, null);
        } while (token == null);
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(token == CancellableContinuationImplKt.RESUME_TOKEN)) {
                throw new AssertionError();
            }
        }
        receiveOrClosedTakeFirstReceiveOrPeekClosed.completeResumeReceive(element);
        return receiveOrClosedTakeFirstReceiveOrPeekClosed.getOfferResult();
    }

    @NotNull
    protected Object offerSelectInternal(E element, @NotNull SelectInstance<?> select) {
        TryOfferDesc<E> tryOfferDescDescribeTryOffer = describeTryOffer(element);
        Object failure = select.performAtomicTrySelect(tryOfferDescDescribeTryOffer);
        if (failure != null) {
            return failure;
        }
        ReceiveOrClosed<? super E> result = tryOfferDescDescribeTryOffer.getResult();
        result.completeResumeReceive(element);
        return result.getOfferResult();
    }

    @Nullable
    protected final Closed<?> getClosedForSend() {
        LockFreeLinkedListNode prevNode = this.queue.getPrevNode();
        Closed<?> closed = prevNode instanceof Closed ? (Closed) prevNode : null;
        if (closed == null) {
            return null;
        }
        Closed<?> closed2 = closed;
        helpClose(closed2);
        return closed2;
    }

    @Nullable
    protected final Closed<?> getClosedForReceive() {
        LockFreeLinkedListNode nextNode = this.queue.getNextNode();
        Closed<?> closed = nextNode instanceof Closed ? (Closed) nextNode : null;
        if (closed == null) {
            return null;
        }
        Closed<?> closed2 = closed;
        helpClose(closed2);
        return closed2;
    }

    @Nullable
    protected final Send takeFirstSendOrPeekClosed() {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        LockFreeLinkedListNode this_$iv = this.queue;
        while (true) {
            LockFreeLinkedListNode first$iv = (LockFreeLinkedListNode) this_$iv.getNext();
            if (first$iv != this_$iv) {
                if (!(first$iv instanceof Send)) {
                    lockFreeLinkedListNode = null;
                    break;
                }
                Send it = (Send) first$iv;
                if ((it instanceof Closed) && !first$iv.isRemoved()) {
                    lockFreeLinkedListNode = first$iv;
                    break;
                }
                LockFreeLinkedListNode next$iv = first$iv.removeOrNext();
                if (next$iv == null) {
                    lockFreeLinkedListNode = first$iv;
                    break;
                }
                next$iv.helpRemovePrev();
            } else {
                lockFreeLinkedListNode = null;
                break;
            }
        }
        return (Send) lockFreeLinkedListNode;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    protected final ReceiveOrClosed<?> sendBuffered(E element) {
        LockFreeLinkedListNode prevNode;
        LockFreeLinkedListNode this_$iv = this.queue;
        LockFreeLinkedListNode node$iv = new SendBuffered(element);
        do {
            prevNode = this_$iv.getPrevNode();
            if (!(prevNode instanceof ReceiveOrClosed)) {
                if (1 == 0) {
                    return null;
                }
            } else {
                return (ReceiveOrClosed) prevNode;
            }
        } while (!prevNode.addNext(node$iv, this_$iv));
        return null;
    }

    @NotNull
    protected final LockFreeLinkedListNode.AddLastDesc<?> describeSendBuffered(E element) {
        return new SendBufferedDesc(this.queue, element);
    }

    /* JADX INFO: compiled from: AbstractChannel.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/channels/AbstractSendChannel$SendBufferedDesc.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0012\u0018\u0000*\u0004\b\u0001\u0010\u00012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0003`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0002\u0010\bJ\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014¨\u0006\r"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBufferedDesc;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "Lkotlinx/coroutines/internal/AddLastDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;Ljava/lang/Object;)V", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "kotlinx-coroutines-core"})
    private static class SendBufferedDesc<E> extends LockFreeLinkedListNode.AddLastDesc<SendBuffered<? extends E>> {
        public SendBufferedDesc(@NotNull LockFreeLinkedListHead queue, E e) {
            super(queue, new SendBuffered(e));
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected Object failure(@NotNull LockFreeLinkedListNode affected) {
            if (affected instanceof Closed) {
                return affected;
            }
            if (affected instanceof ReceiveOrClosed) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            return null;
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public final boolean isClosedForSend() {
        return getClosedForSend() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isFullImpl() {
        return !(this.queue.getNextNode() instanceof ReceiveOrClosed) && isBufferFull();
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @Nullable
    public final Object send(E e, @NotNull Continuation<? super Unit> continuation) {
        if (offerInternal(e) == AbstractChannelKt.OFFER_SUCCESS) {
            return Unit.INSTANCE;
        }
        Object objSendSuspend = sendSuspend(e, continuation);
        return objSendSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objSendSuspend : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean offer(E element) {
        UndeliveredElementException it;
        try {
            return SendChannel.DefaultImpls.offer(this, element);
        } catch (Throwable e) {
            Function1<E, Unit> function1 = this.onUndeliveredElement;
            if (function1 != null && (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, null, 2, null)) != null) {
                ExceptionsKt.addSuppressed(it, e);
                throw it;
            }
            throw e;
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    /* JADX INFO: renamed from: trySend-JP2dKIU, reason: not valid java name */
    public final Object mo4432trySendJP2dKIU(E element) {
        Object result = offerInternal(element);
        if (result == AbstractChannelKt.OFFER_SUCCESS) {
            return ChannelResult.INSTANCE.m4457successJP2dKIU(Unit.INSTANCE);
        }
        if (result == AbstractChannelKt.OFFER_FAILED) {
            Closed<?> closedForSend = getClosedForSend();
            return closedForSend == null ? ChannelResult.INSTANCE.m4456failurePtdJZtk() : ChannelResult.INSTANCE.m4455closedJP2dKIU(helpCloseAndGetSendException(closedForSend));
        }
        if (result instanceof Closed) {
            return ChannelResult.INSTANCE.m4455closedJP2dKIU(helpCloseAndGetSendException((Closed) result));
        }
        throw new IllegalStateException(("trySend returned " + result).toString());
    }

    private final Throwable helpCloseAndGetSendException(Closed<?> closed) {
        helpClose(closed);
        return closed.getSendException();
    }

    private final Throwable helpCloseAndGetSendException(E element, Closed<?> closed) {
        UndeliveredElementException it;
        helpClose(closed);
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null && (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, element, null, 2, null)) != null) {
            ExceptionsKt.addSuppressed(it, closed.getSendException());
            throw it;
        }
        return closed.getSendException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:24:0x00da  */
    /* JADX WARN: Code duplicated, block: B:28:0x00ea A[EDGE_INSN: B:28:0x00ea->B:31:0x011c BREAK  A[LOOP:0: B:3:0x001c->B:44:?]] */
    /* JADX WARN: Code duplicated, block: B:29:0x00fc  */
    /* JADX WARN: Code duplicated, block: B:42:0x00c3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x00e2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:? A[LOOP:0: B:3:0x001c->B:44:?, LOOP_END, SYNTHETIC] */
    public final Object sendSuspend(E e, Continuation<? super Unit> continuation) {
        Object offerResult;
        SendElement sendElementWithUndeliveredHandler;
        CancellableContinuationImpl cancellable$iv = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        CancellableContinuationImpl cont = cancellable$iv;
        while (true) {
            if (isFullImpl()) {
                if (this.onUndeliveredElement == null) {
                    sendElementWithUndeliveredHandler = new SendElement(e, cont);
                } else {
                    sendElementWithUndeliveredHandler = new SendElementWithUndeliveredHandler(e, cont, this.onUndeliveredElement);
                }
                SendElement send = sendElementWithUndeliveredHandler;
                Object enqueueResult = enqueueSend(send);
                if (enqueueResult == null) {
                    CancellableContinuationKt.removeOnCancellation(cont, send);
                    break;
                }
                if (enqueueResult instanceof Closed) {
                    helpCloseAndResumeWithSendException(cont, e, (Closed) enqueueResult);
                    break;
                }
                if (enqueueResult != AbstractChannelKt.ENQUEUE_FAILED && !(enqueueResult instanceof Receive)) {
                    throw new IllegalStateException(("enqueueSend returned " + enqueueResult).toString());
                }
                offerResult = offerInternal(e);
                if (offerResult == AbstractChannelKt.OFFER_SUCCESS) {
                    Result.Companion companion = Result.INSTANCE;
                    cont.resumeWith(Result.m2898constructorimpl(Unit.INSTANCE));
                    break;
                }
                if (offerResult != AbstractChannelKt.OFFER_FAILED) {
                    if (offerResult instanceof Closed) {
                        helpCloseAndResumeWithSendException(cont, e, (Closed) offerResult);
                        break;
                    }
                    throw new IllegalStateException(("offerInternal returned " + offerResult).toString());
                }
            } else {
                offerResult = offerInternal(e);
                if (offerResult == AbstractChannelKt.OFFER_SUCCESS) {
                    Result.Companion companion2 = Result.INSTANCE;
                    cont.resumeWith(Result.m2898constructorimpl(Unit.INSTANCE));
                    break;
                }
                if (offerResult != AbstractChannelKt.OFFER_FAILED) {
                    if (offerResult instanceof Closed) {
                        helpCloseAndResumeWithSendException(cont, e, (Closed) offerResult);
                        break;
                    }
                    throw new IllegalStateException(("offerInternal returned " + offerResult).toString());
                }
            }
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void helpCloseAndResumeWithSendException(Continuation<?> continuation, E e, Closed<?> closed) {
        UndeliveredElementException it;
        helpClose(closed);
        Throwable sendException = closed.getSendException();
        Function1<E, Unit> function1 = this.onUndeliveredElement;
        if (function1 != null && (it = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(function1, e, null, 2, null)) != null) {
            ExceptionsKt.addSuppressed(it, sendException);
            Result.Companion companion = Result.INSTANCE;
            continuation.resumeWith(Result.m2898constructorimpl(ResultKt.createFailure(it)));
        } else {
            Result.Companion companion2 = Result.INSTANCE;
            continuation.resumeWith(Result.m2898constructorimpl(ResultKt.createFailure(sendException)));
        }
    }

    @Nullable
    protected Object enqueueSend(@NotNull Send send) {
        boolean z;
        LockFreeLinkedListNode prev$iv;
        if (isBufferAlwaysFull()) {
            LockFreeLinkedListHead this_$iv = this.queue;
            do {
                prev$iv = this_$iv.getPrevNode();
                if (!(prev$iv instanceof ReceiveOrClosed)) {
                    if (1 == 0) {
                        return null;
                    }
                } else {
                    return prev$iv;
                }
            } while (!prev$iv.addNext(send, this_$iv));
            return null;
        }
        LockFreeLinkedListHead this_$iv2 = this.queue;
        final Send send2 = send;
        LockFreeLinkedListNode.CondAddOp condAdd$iv = new LockFreeLinkedListNode.CondAddOp(send2) { // from class: kotlinx.coroutines.channels.AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1
            @Override // kotlinx.coroutines.internal.AtomicOp
            @Nullable
            public Object prepare(@NotNull LockFreeLinkedListNode affected) {
                if (this.isBufferFull()) {
                    return null;
                }
                return LockFreeLinkedListKt.getCONDITION_FALSE();
            }
        };
        while (true) {
            LockFreeLinkedListNode prev$iv2 = this_$iv2.getPrevNode();
            if (!(prev$iv2 instanceof ReceiveOrClosed)) {
                if (1 != 0) {
                    switch (prev$iv2.tryCondAddNext(send, this_$iv2, condAdd$iv)) {
                        case 1:
                            z = true;
                            break;
                        case 2:
                            z = false;
                            break;
                        default:
                            break;
                    }
                } else {
                    z = false;
                }
                if (!z) {
                    return AbstractChannelKt.ENQUEUE_FAILED;
                }
                return null;
            }
            return prev$iv2;
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public boolean close(@Nullable Throwable cause) {
        boolean z;
        Closed<?> closed = new Closed<>(cause);
        LockFreeLinkedListHead this_$iv = this.queue;
        while (true) {
            LockFreeLinkedListNode prev$iv = this_$iv.getPrevNode();
            if (!(!(prev$iv instanceof Closed))) {
                z = false;
                break;
            }
            if (prev$iv.addNext(closed, this_$iv)) {
                z = true;
                break;
            }
        }
        boolean closeAdded = z;
        helpClose(closeAdded ? closed : (Closed) this.queue.getPrevNode());
        if (closeAdded) {
            invokeOnCloseHandler(cause);
        }
        return closeAdded;
    }

    private final void invokeOnCloseHandler(Throwable cause) {
        Object handler = this.onCloseHandler;
        if (handler != null && handler != AbstractChannelKt.HANDLER_INVOKED && onCloseHandler$FU.compareAndSet(this, handler, AbstractChannelKt.HANDLER_INVOKED)) {
            ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(handler, 1)).invoke(cause);
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    public void invokeOnClose(@NotNull Function1<? super Throwable, Unit> handler) {
        if (!onCloseHandler$FU.compareAndSet(this, null, handler)) {
            Object value = this.onCloseHandler;
            if (value == AbstractChannelKt.HANDLER_INVOKED) {
                throw new IllegalStateException("Another handler was already registered and successfully invoked");
            }
            throw new IllegalStateException("Another handler was already registered: " + value);
        }
        Closed<?> closedForSend = getClosedForSend();
        if (closedForSend != null && onCloseHandler$FU.compareAndSet(this, handler, AbstractChannelKt.HANDLER_INVOKED)) {
            handler.invoke(closedForSend.closeCause);
        }
    }

    private final void helpClose(Closed<?> closed) {
        Object closedList = InlineList.m4481constructorimpl$default(null, 1, null);
        while (true) {
            LockFreeLinkedListNode prevNode = closed.getPrevNode();
            Receive receive = prevNode instanceof Receive ? (Receive) prevNode : null;
            if (receive == null) {
                break;
            }
            Receive previous = receive;
            if (!previous.mo4489remove()) {
                previous.helpRemove();
            } else {
                closedList = InlineList.m4486plusFjFbRPM(closedList, previous);
            }
        }
        Object obj = closedList;
        if (obj != null) {
            if (!(obj instanceof ArrayList)) {
                Receive it = (Receive) closedList;
                it.resumeReceiveClosed(closed);
            } else {
                Object obj2 = closedList;
                if (obj2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
                }
                ArrayList list$iv = (ArrayList) obj2;
                for (int i$iv = list$iv.size() - 1; -1 < i$iv; i$iv--) {
                    Receive it2 = (Receive) list$iv.get(i$iv);
                    it2.resumeReceiveClosed(closed);
                }
            }
        }
        onClosedIdempotent(closed);
    }

    protected void onClosedIdempotent(@NotNull LockFreeLinkedListNode closed) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    protected ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed() {
        Object obj;
        LockFreeLinkedListNode this_$iv = this.queue;
        while (true) {
            LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) this_$iv.getNext();
            if (lockFreeLinkedListNode != this_$iv) {
                if (!(lockFreeLinkedListNode instanceof ReceiveOrClosed)) {
                    obj = null;
                    break;
                }
                ReceiveOrClosed it = (ReceiveOrClosed) lockFreeLinkedListNode;
                if ((it instanceof Closed) && !lockFreeLinkedListNode.isRemoved()) {
                    obj = lockFreeLinkedListNode;
                    break;
                }
                LockFreeLinkedListNode next$iv = lockFreeLinkedListNode.removeOrNext();
                if (next$iv == null) {
                    obj = lockFreeLinkedListNode;
                    break;
                }
                next$iv.helpRemovePrev();
            } else {
                obj = null;
                break;
            }
        }
        return (ReceiveOrClosed) obj;
    }

    @NotNull
    protected final TryOfferDesc<E> describeTryOffer(E element) {
        return new TryOfferDesc<>(element, this.queue);
    }

    /* JADX INFO: compiled from: AbstractChannel.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0003`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00028\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\n\u0010\u000f\u001a\u00060\u0010j\u0002`\u0011H\u0016R\u0012\u0010\u0005\u001a\u00028\u00018\u0006X\u0087\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "kotlinx-coroutines-core"})
    protected static final class TryOfferDesc<E> extends LockFreeLinkedListNode.RemoveFirstDesc<ReceiveOrClosed<? super E>> {

        @JvmField
        public final E element;

        public TryOfferDesc(E e, @NotNull LockFreeLinkedListHead queue) {
            super(queue);
            this.element = e;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.RemoveFirstDesc, kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        protected Object failure(@NotNull LockFreeLinkedListNode affected) {
            if (affected instanceof Closed) {
                return affected;
            }
            if (affected instanceof ReceiveOrClosed) {
                return null;
            }
            return AbstractChannelKt.OFFER_FAILED;
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode.AbstractAtomicDesc
        @Nullable
        public Object onPrepare(@NotNull LockFreeLinkedListNode.PrepareOp prepareOp) {
            ReceiveOrClosed affected = (ReceiveOrClosed) prepareOp.affected;
            Symbol token = affected.tryResumeReceive(this.element, prepareOp);
            if (token == null) {
                return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
            }
            if (token == AtomicKt.RETRY_ATOMIC) {
                return AtomicKt.RETRY_ATOMIC;
            }
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (token == CancellableContinuationImplKt.RESUME_TOKEN) {
                    return null;
                }
                throw new AssertionError();
            }
            return null;
        }
    }

    @Override // kotlinx.coroutines.channels.SendChannel
    @NotNull
    public final SelectClause2<E, SendChannel<E>> getOnSend() {
        return new SelectClause2<E, SendChannel<? super E>>(this) { // from class: kotlinx.coroutines.channels.AbstractSendChannel$onSend$1
            final /* synthetic */ AbstractSendChannel<E> this$0;

            {
                this.this$0 = this;
            }

            public <R> void registerSelectClause2(@NotNull SelectInstance<? super R> select, E param, @NotNull Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) throws Throwable {
                this.this$0.registerSelectSend(select, param, block);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <R> void registerSelectSend(SelectInstance<? super R> select, E element, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) throws Throwable {
        while (!select.isSelected()) {
            if (isFullImpl()) {
                SendSelect node = new SendSelect(element, this, select, block);
                Object enqueueResult = enqueueSend(node);
                if (enqueueResult == null) {
                    select.disposeOnSelect(node);
                    return;
                } else {
                    if (enqueueResult instanceof Closed) {
                        throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException(element, (Closed) enqueueResult));
                    }
                    if (enqueueResult != AbstractChannelKt.ENQUEUE_FAILED && !(enqueueResult instanceof Receive)) {
                        throw new IllegalStateException(("enqueueSend returned " + enqueueResult + StringUtil.SPACE).toString());
                    }
                }
            }
            Object offerResult = offerSelectInternal(element, select);
            if (offerResult == SelectKt.getALREADY_SELECTED()) {
                return;
            }
            if (offerResult != AbstractChannelKt.OFFER_FAILED && offerResult != AtomicKt.RETRY_ATOMIC) {
                if (offerResult == AbstractChannelKt.OFFER_SUCCESS) {
                    UndispatchedKt.startCoroutineUnintercepted(block, this, select.getCompletion());
                    return;
                } else {
                    if (!(offerResult instanceof Closed)) {
                        throw new IllegalStateException(("offerSelectInternal returned " + offerResult).toString());
                    }
                    throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException(element, (Closed) offerResult));
                }
            }
        }
    }

    @NotNull
    public String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this) + '{' + getQueueDebugStateString() + '}' + getBufferDebugString();
    }

    private final String getQueueDebugStateString() {
        String string;
        LockFreeLinkedListNode head = this.queue.getNextNode();
        if (head == this.queue) {
            return "EmptyQueue";
        }
        if (head instanceof Closed) {
            string = head.toString();
        } else if (head instanceof Receive) {
            string = "ReceiveQueued";
        } else {
            string = head instanceof Send ? "SendQueued" : "UNEXPECTED:" + head;
        }
        String result = string;
        LockFreeLinkedListNode tail = this.queue.getPrevNode();
        if (tail != head) {
            result = result + ",queueSize=" + countQueueSize();
            if (tail instanceof Closed) {
                result = result + ",closedForSend=" + tail;
            }
        }
        return result;
    }

    private final int countQueueSize() {
        int size = 0;
        LockFreeLinkedListHead this_$iv = this.queue;
        LockFreeLinkedListNode nextNode = (LockFreeLinkedListNode) this_$iv.getNext();
        while (true) {
            LockFreeLinkedListNode cur$iv = nextNode;
            if (Intrinsics.areEqual(cur$iv, this_$iv)) {
                return size;
            }
            if (cur$iv instanceof LockFreeLinkedListNode) {
                size++;
            }
            nextNode = cur$iv.getNextNode();
        }
    }

    @NotNull
    protected String getBufferDebugString() {
        return "";
    }

    /* JADX INFO: compiled from: AbstractChannel.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/channels/AbstractSendChannel$SendSelect.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u0001*\u0004\b\u0002\u0010\u00022\u00020\u00032\u00020\u0004BV\u0012\u0006\u0010\u0005\u001a\u00028\u0001\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\t\u0012(\u0010\n\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u000bø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0014\u0010\u0017\u001a\u00020\u00152\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0015H\u0016R7\u0010\n\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u000b8\u0006X\u0087\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0010R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006!"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendSelect;", ExifInterface.LONGITUDE_EAST, "R", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/DisposableHandle;", "pollResult", "channel", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/AbstractSendChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "getPollResult", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "completeResumeSend", "", "dispose", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "undeliveredElement", "kotlinx-coroutines-core"})
    private static final class SendSelect<E, R> extends Send implements DisposableHandle {
        private final E pollResult;

        @JvmField
        @NotNull
        public final AbstractSendChannel<E> channel;

        @JvmField
        @NotNull
        public final SelectInstance<R> select;

        @JvmField
        @NotNull
        public final Function2<SendChannel<? super E>, Continuation<? super R>, Object> block;

        @Override // kotlinx.coroutines.channels.Send
        public E getPollResult() {
            return this.pollResult;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public SendSelect(E e, @NotNull AbstractSendChannel<E> abstractSendChannel, @NotNull SelectInstance<? super R> selectInstance, @NotNull Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> function2) {
            this.pollResult = e;
            this.channel = abstractSendChannel;
            this.select = selectInstance;
            this.block = function2;
        }

        @Override // kotlinx.coroutines.channels.Send
        @Nullable
        public Symbol tryResumeSend(@Nullable LockFreeLinkedListNode.PrepareOp otherOp) {
            return (Symbol) this.select.trySelectOther(otherOp);
        }

        @Override // kotlinx.coroutines.channels.Send
        public void completeResumeSend() throws Throwable {
            CancellableKt.startCoroutineCancellable$default(this.block, this.channel, this.select.getCompletion(), null, 4, null);
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public void dispose() {
            if (mo4489remove()) {
                undeliveredElement();
            }
        }

        @Override // kotlinx.coroutines.channels.Send
        public void resumeSendClosed(@NotNull Closed<?> closed) {
            if (this.select.trySelect()) {
                this.select.resumeSelectWithException(closed.getSendException());
            }
        }

        @Override // kotlinx.coroutines.channels.Send
        public void undeliveredElement() {
            Function1<E, Unit> function1 = this.channel.onUndeliveredElement;
            if (function1 != null) {
                OnUndeliveredElementKt.callUndeliveredElement(function1, getPollResult(), this.select.getCompletion().get$context());
            }
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            return "SendSelect@" + DebugStringsKt.getHexAddress(this) + '(' + getPollResult() + ")[" + this.channel + ", " + this.select + ']';
        }
    }

    /* JADX INFO: compiled from: AbstractChannel.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/channels/AbstractSendChannel$SendBuffered.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u0012\u0010\u0003\u001a\u00028\u00018\u0006X\u0087\u0004¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/Send;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "pollResult", "", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"})
    public static final class SendBuffered<E> extends Send {

        @JvmField
        public final E element;

        public SendBuffered(E e) {
            this.element = e;
        }

        @Override // kotlinx.coroutines.channels.Send
        @Nullable
        public Object getPollResult() {
            return this.element;
        }

        @Override // kotlinx.coroutines.channels.Send
        @Nullable
        public Symbol tryResumeSend(@Nullable LockFreeLinkedListNode.PrepareOp otherOp) {
            Symbol symbol = CancellableContinuationImplKt.RESUME_TOKEN;
            if (otherOp != null) {
                otherOp.finishPrepare();
            }
            return symbol;
        }

        @Override // kotlinx.coroutines.channels.Send
        public void completeResumeSend() {
        }

        @Override // kotlinx.coroutines.channels.Send
        public void resumeSendClosed(@NotNull Closed<?> closed) {
            if (DebugKt.getASSERTIONS_ENABLED() && 0 == 0) {
                throw new AssertionError();
            }
        }

        @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
        @NotNull
        public String toString() {
            return "SendBuffered@" + DebugStringsKt.getHexAddress(this) + '(' + this.element + ')';
        }
    }
}
