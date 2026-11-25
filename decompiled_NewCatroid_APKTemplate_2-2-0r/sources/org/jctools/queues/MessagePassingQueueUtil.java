package org.jctools.queues;

/* JADX INFO: loaded from: classes14.dex */
public final class MessagePassingQueueUtil {
    private MessagePassingQueueUtil() {
    }

    public static <E> int drain(MessagePassingQueue<? extends E> messagePassingQueue, MessagePassingQueue.Consumer<? super E> consumer, int i) {
        int i2 = 0;
        while (i2 < i) {
            E eRelaxedPoll = messagePassingQueue.relaxedPoll();
            if (eRelaxedPoll == null) {
                break;
            }
            consumer.accept(eRelaxedPoll);
            i2++;
        }
        return i2;
    }

    public static <E> int drain(MessagePassingQueue<? extends E> messagePassingQueue, MessagePassingQueue.Consumer<? super E> consumer) {
        int i = 0;
        while (true) {
            E eRelaxedPoll = messagePassingQueue.relaxedPoll();
            if (eRelaxedPoll == null) {
                return i;
            }
            i++;
            consumer.accept(eRelaxedPoll);
        }
    }

    public static <E> void drain(MessagePassingQueue<? extends E> messagePassingQueue, MessagePassingQueue.Consumer<? super E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        while (true) {
            int iIdle = 0;
            while (exitCondition.keepRunning()) {
                E eRelaxedPoll = messagePassingQueue.relaxedPoll();
                if (eRelaxedPoll == null) {
                    iIdle = waitStrategy.idle(iIdle);
                } else {
                    consumer.accept(eRelaxedPoll);
                }
            }
            return;
        }
    }
}
