package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: PerformanceCounter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 \u00142\u00020\u0001:\u0002\u0013\u0014B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0005\"\u00020\u0001¢\u0006\u0002\u0010\u0006J!\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\f0\u000eH\u0014¢\u0006\u0002\u0010\u000fJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0011R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/util/CounterWithExclude;", "Lorg/jetbrains/kotlin/util/PerformanceCounter;", "name", "", "excludedCounters", "", "(Ljava/lang/String;[Lorg/jetbrains/kotlin/util/PerformanceCounter;)V", "callStack", "Lorg/jetbrains/kotlin/util/CounterWithExclude$CallStackWithTime;", "getCallStack", "()Lorg/jetbrains/kotlin/util/CounterWithExclude$CallStackWithTime;", "countTime", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "enterExcludedMethod", "", "exitExcludedMethod", "CallStackWithTime", "Companion", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CounterWithExclude extends PerformanceCounter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ThreadLocal<Map<CounterWithExclude, CallStackWithTime>> counterToCallStackMapThreadLocal = new ThreadLocal<>();

    /* JADX INFO: compiled from: PerformanceCounter.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0006H\u0002R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/util/CounterWithExclude$Companion;", "", "()V", "counterToCallStackMapThreadLocal", "Ljava/lang/ThreadLocal;", "", "Lorg/jetbrains/kotlin/util/CounterWithExclude;", "Lorg/jetbrains/kotlin/util/CounterWithExclude$CallStackWithTime;", "getCallStack", "counter", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final CallStackWithTime getCallStack(CounterWithExclude counter) {
            PerformanceCounter.Companion companion = PerformanceCounter.INSTANCE;
            ThreadLocal threadLocal = CounterWithExclude.counterToCallStackMapThreadLocal;
            Object obj = threadLocal.get();
            if (obj == null) {
                obj = (Map) new HashMap();
                threadLocal.set(obj);
            }
            Map map = (Map) obj;
            Object callStackWithTime = map.get(counter);
            if (callStackWithTime == null) {
                callStackWithTime = new CallStackWithTime();
                map.put(counter, callStackWithTime);
            }
            return (CallStackWithTime) callStackWithTime;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CounterWithExclude(String name2, PerformanceCounter... excludedCounters) {
        super(name2);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(excludedCounters, "excludedCounters");
        for (PerformanceCounter performanceCounter : excludedCounters) {
            performanceCounter.getExcludedFrom$util_runtime().add(this);
        }
    }

    private final CallStackWithTime getCallStack() {
        return INSTANCE.getCallStack(this);
    }

    @Override // org.jetbrains.kotlin.util.PerformanceCounter
    protected <T> T countTime(Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        incrementTime(getCallStack().push(true));
        try {
            return block.invoke();
        } finally {
            incrementTime(getCallStack().pop(true));
        }
    }

    public final void enterExcludedMethod() {
        incrementTime(getCallStack().push(false));
    }

    public final void exitExcludedMethod() {
        incrementTime(getCallStack().pop(false));
    }

    /* JADX INFO: compiled from: PerformanceCounter.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J'\u0010\b\u001a\u00020\u00072\u001d\u0010\t\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\b\fH\u0002J\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0005J\u000e\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0005J\u001d\u0010\u0011\u001a\n \u0012*\u0004\u0018\u00010\u00050\u0005*\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0013R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/util/CounterWithExclude$CallStackWithTime;", "", "()V", "callStack", "Ljava/util/Stack;", "", "intervalStartTime", "", "intervalUsefulTime", "callStackUpdate", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "isEnteredCounter", "pop", "usefulCall", "push", "peekOrFalse", JvmProtoBufUtil.PLATFORM_TYPE_ID, "(Ljava/util/Stack;)Ljava/lang/Boolean;", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CallStackWithTime {
        private final Stack<Boolean> callStack = new Stack<>();
        private long intervalStartTime;

        public final Boolean peekOrFalse(Stack<Boolean> stack) {
            Intrinsics.checkNotNullParameter(stack, "<this>");
            if (stack.isEmpty()) {
                return false;
            }
            return stack.peek();
        }

        private final long intervalUsefulTime(Function1<? super Stack<Boolean>, Unit> callStackUpdate) {
            Boolean boolPeekOrFalse = peekOrFalse(this.callStack);
            Intrinsics.checkNotNullExpressionValue(boolPeekOrFalse, "peekOrFalse(...)");
            long jCurrentTime = boolPeekOrFalse.booleanValue() ? PerformanceCounter.INSTANCE.currentTime() - this.intervalStartTime : 0L;
            callStackUpdate.invoke(this.callStack);
            this.intervalStartTime = PerformanceCounter.INSTANCE.currentTime();
            return jCurrentTime;
        }

        public final long push(final boolean usefulCall) {
            if (isEnteredCounter() || usefulCall) {
                return intervalUsefulTime(new Function1<Stack<Boolean>, Unit>() { // from class: org.jetbrains.kotlin.util.CounterWithExclude$CallStackWithTime$push$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Stack<Boolean> stack) {
                        invoke2(stack);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Stack<Boolean> intervalUsefulTime) {
                        Intrinsics.checkNotNullParameter(intervalUsefulTime, "$this$intervalUsefulTime");
                        intervalUsefulTime.push(Boolean.valueOf(usefulCall));
                    }
                });
            }
            return 0L;
        }

        public final long pop(boolean usefulCall) {
            if (!isEnteredCounter()) {
                return 0L;
            }
            Intrinsics.areEqual(this.callStack.peek(), Boolean.valueOf(usefulCall));
            return intervalUsefulTime(new Function1<Stack<Boolean>, Unit>() { // from class: org.jetbrains.kotlin.util.CounterWithExclude$CallStackWithTime$pop$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Stack<Boolean> stack) {
                    invoke2(stack);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Stack<Boolean> intervalUsefulTime) {
                    Intrinsics.checkNotNullParameter(intervalUsefulTime, "$this$intervalUsefulTime");
                    intervalUsefulTime.pop();
                }
            });
        }

        public final boolean isEnteredCounter() {
            return !this.callStack.isEmpty();
        }
    }
}
