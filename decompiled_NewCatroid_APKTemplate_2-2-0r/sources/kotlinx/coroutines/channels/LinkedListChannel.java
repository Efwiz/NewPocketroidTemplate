package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.OnUndeliveredElementKt;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: compiled from: LinkedListChannel.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/channels/LinkedListChannel.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012 \u0010\u0003\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0006¢\u0006\u0002\u0010\u0007J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0014¢\u0006\u0002\u0010\u0015J/\u0010\u0016\u001a\u00020\u00052\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001bH\u0014ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001c\u0010\u001dR\u0014\u0010\b\u001a\u00020\t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0014\u0010\u000b\u001a\u00020\t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\f\u001a\u00020\t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\n\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u001e"}, d2 = {"Lkotlinx/coroutines/channels/LinkedListChannel;", ExifInterface.LONGITUDE_EAST, "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "offerInternal", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "onCancelIdempotentList", SchemaSymbols.ATTVAL_LIST, "Lkotlinx/coroutines/internal/InlineList;", "Lkotlinx/coroutines/channels/Send;", "closed", "Lkotlinx/coroutines/channels/Closed;", "onCancelIdempotentList-w-w6eGU", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "kotlinx-coroutines-core"})
public class LinkedListChannel<E> extends AbstractChannel<E> {
    public LinkedListChannel(@Nullable Function1<? super E, Unit> function1) {
        super(function1);
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    protected final boolean isBufferAlwaysEmpty() {
        return true;
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    protected final boolean isBufferEmpty() {
        return true;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected final boolean isBufferAlwaysFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected final boolean isBufferFull() {
        return false;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    protected Object offerInternal(E element) {
        ReceiveOrClosed<?> receiveOrClosedSendBuffered;
        do {
            Object result = super.offerInternal(element);
            if (result == AbstractChannelKt.OFFER_SUCCESS) {
                return AbstractChannelKt.OFFER_SUCCESS;
            }
            if (result == AbstractChannelKt.OFFER_FAILED) {
                receiveOrClosedSendBuffered = sendBuffered(element);
                if (receiveOrClosedSendBuffered == null) {
                    return AbstractChannelKt.OFFER_SUCCESS;
                }
            } else {
                if (result instanceof Closed) {
                    return result;
                }
                throw new IllegalStateException(("Invalid offerInternal result " + result).toString());
            }
        } while (!(receiveOrClosedSendBuffered instanceof Closed));
        return receiveOrClosedSendBuffered;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    @NotNull
    protected Object offerSelectInternal(E element, @NotNull SelectInstance<?> select) {
        Object objPerformAtomicTrySelect;
        while (true) {
            if (getHasReceiveOrClosed()) {
                objPerformAtomicTrySelect = super.offerSelectInternal(element, select);
            } else {
                objPerformAtomicTrySelect = select.performAtomicTrySelect(describeSendBuffered(element));
                if (objPerformAtomicTrySelect == null) {
                    objPerformAtomicTrySelect = AbstractChannelKt.OFFER_SUCCESS;
                }
            }
            Object result = objPerformAtomicTrySelect;
            if (result == SelectKt.getALREADY_SELECTED()) {
                return SelectKt.getALREADY_SELECTED();
            }
            if (result == AbstractChannelKt.OFFER_SUCCESS) {
                return AbstractChannelKt.OFFER_SUCCESS;
            }
            if (result != AbstractChannelKt.OFFER_FAILED && result != AtomicKt.RETRY_ATOMIC) {
                if (result instanceof Closed) {
                    return result;
                }
                throw new IllegalStateException(("Invalid result " + result).toString());
            }
        }
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    /* JADX INFO: renamed from: onCancelIdempotentList-w-w6eGU */
    protected void mo4430onCancelIdempotentListww6eGU(@NotNull Object list, @NotNull Closed<?> closed) {
        UndeliveredElementException undeliveredElementExceptionCallUndeliveredElementCatchingException = null;
        if (list != null) {
            if (!(list instanceof ArrayList)) {
                Send it = (Send) list;
                if (it instanceof AbstractSendChannel.SendBuffered) {
                    Function1<E, Unit> function1 = this.onUndeliveredElement;
                    undeliveredElementExceptionCallUndeliveredElementCatchingException = function1 != null ? OnUndeliveredElementKt.callUndeliveredElementCatchingException(function1, ((AbstractSendChannel.SendBuffered) it).element, (UndeliveredElementException) null) : null;
                } else {
                    it.resumeSendClosed(closed);
                }
            } else {
                if (list == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
                }
                ArrayList list$iv = (ArrayList) list;
                for (int i$iv = list$iv.size() - 1; -1 < i$iv; i$iv--) {
                    Send it2 = (Send) list$iv.get(i$iv);
                    if (it2 instanceof AbstractSendChannel.SendBuffered) {
                        Function1<E, Unit> function2 = this.onUndeliveredElement;
                        undeliveredElementExceptionCallUndeliveredElementCatchingException = function2 != null ? OnUndeliveredElementKt.callUndeliveredElementCatchingException(function2, ((AbstractSendChannel.SendBuffered) it2).element, undeliveredElementExceptionCallUndeliveredElementCatchingException) : null;
                    } else {
                        it2.resumeSendClosed(closed);
                    }
                }
            }
        }
        UndeliveredElementException it3 = undeliveredElementExceptionCallUndeliveredElementCatchingException;
        if (it3 != null) {
            throw it3;
        }
    }
}
