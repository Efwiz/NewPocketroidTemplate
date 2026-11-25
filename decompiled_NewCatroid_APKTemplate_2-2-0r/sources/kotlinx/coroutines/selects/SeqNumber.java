package kotlinx.coroutines.selects;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: Select.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/selects/SeqNumber.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0000\b\u0000\u0018\u00002\u00020\u0007B\u0007¢\u0006\u0004\b\u0001\u0010\u0002J\r\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lkotlinx/coroutines/selects/SeqNumber;", "<init>", "()V", "", ES6Iterator.NEXT_METHOD, "()J", "kotlinx-coroutines-core", ""})
public final class SeqNumber {

    @NotNull
    private volatile /* synthetic */ long number = 1;
    private static final /* synthetic */ AtomicLongFieldUpdater number$FU = AtomicLongFieldUpdater.newUpdater(SeqNumber.class, "number");

    public final long next() {
        return number$FU.incrementAndGet(this);
    }
}
