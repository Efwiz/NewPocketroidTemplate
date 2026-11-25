package androidx.room;

import java.util.Set;
import java.util.concurrent.Callable;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.FlowCollector;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [R] */
/* JADX INFO: compiled from: CoroutinesRoom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0010\u0012\f\u0012\n \u0004*\u0004\u0018\u0001H\u0002H\u00020\u0003H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "R", "Lkotlinx/coroutines/flow/FlowCollector;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
@DebugMetadata(c = "androidx.room.CoroutinesRoom$Companion$createFlow$1", f = "CoroutinesRoom.kt", i = {}, l = {116}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class CoroutinesRoom$Companion$createFlow$1<R> extends SuspendLambda implements Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Callable $callable;
    final /* synthetic */ RoomDatabase $db;
    final /* synthetic */ boolean $inTransaction;
    final /* synthetic */ String[] $tableNames;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CoroutinesRoom$Companion$createFlow$1(String[] strArr, boolean z, RoomDatabase roomDatabase, Callable callable, Continuation continuation) {
        super(2, continuation);
        this.$tableNames = strArr;
        this.$inTransaction = z;
        this.$db = roomDatabase;
        this.$callable = callable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> completion) {
        Intrinsics.checkNotNullParameter(completion, "completion");
        CoroutinesRoom$Companion$createFlow$1 coroutinesRoom$Companion$createFlow$1 = new CoroutinesRoom$Companion$createFlow$1(this.$tableNames, this.$inTransaction, this.$db, this.$callable, completion);
        coroutinesRoom$Companion$createFlow$1.L$0 = obj;
        return coroutinesRoom$Companion$createFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Continuation<? super Unit> continuation) {
        return ((CoroutinesRoom$Companion$createFlow$1) create(obj, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r11v3, types: [T, androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1] */
    /* JADX WARN: Type inference failed for: r11v5, types: [T, kotlin.coroutines.CoroutineContext] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineDispatcher transactionDispatcher;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final Channel channelChannel$default = ChannelKt.Channel$default(-1, null, null, 6, null);
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            final String[] strArr = this.$tableNames;
            objectRef.element = new InvalidationTracker.Observer(strArr) { // from class: androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1
                @Override // androidx.room.InvalidationTracker.Observer
                public void onInvalidated(Set<String> tables) {
                    Intrinsics.checkNotNullParameter(tables, "tables");
                    channelChannel$default.offer(Unit.INSTANCE);
                }
            };
            channelChannel$default.offer(Unit.INSTANCE);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = get$context();
            TransactionElement transactionElement = (TransactionElement) get$context().get(TransactionElement.INSTANCE);
            if (transactionElement == null || (transactionDispatcher = transactionElement.getTransactionDispatcher()) == null) {
                transactionDispatcher = this.$inTransaction ? CoroutinesRoomKt.getTransactionDispatcher(this.$db) : CoroutinesRoomKt.getQueryDispatcher(this.$db);
            }
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, objectRef, channelChannel$default, objectRef2, null);
            this.label = 1;
            if (BuildersKt.withContext(transactionDispatcher, anonymousClass1, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: androidx.room.CoroutinesRoom$Companion$createFlow$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: CoroutinesRoom.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "R", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "androidx.room.CoroutinesRoom$Companion$createFlow$1$1", f = "CoroutinesRoom.kt", i = {}, l = {121, 123}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.ObjectRef $flowContext;
        final /* synthetic */ Ref.ObjectRef $observer;
        final /* synthetic */ Channel $observerChannel;
        final /* synthetic */ FlowCollector $this_flow;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(FlowCollector flowCollector, Ref.ObjectRef objectRef, Channel channel, Ref.ObjectRef objectRef2, Continuation continuation) {
            super(2, continuation);
            this.$this_flow = flowCollector;
            this.$observer = objectRef;
            this.$observerChannel = channel;
            this.$flowContext = objectRef2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new AnonymousClass1(this.$this_flow, this.$observer, this.$observerChannel, this.$flowContext, completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:18:0x004d A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:19:0x004e  */
        /* JADX WARN: Code duplicated, block: B:22:0x0059 A[Catch: all -> 0x009d, TRY_LEAVE, TryCatch #0 {all -> 0x009d, blocks: (B:7:0x0012, B:16:0x0043, B:20:0x0051, B:22:0x0059, B:12:0x0023, B:15:0x003d), top: B:30:0x0008 }] */
        /* JADX WARN: Code duplicated, block: B:24:0x0086 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:25:0x0087  */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v2, types: [T, java.lang.Object] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0084 -> B:8:0x0015). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L27
                if (r1 == r3) goto L1f
                if (r1 != r2) goto L17
                java.lang.Object r1 = r8.L$0
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L9d
            L15:
                r9 = r1
                goto L43
            L17:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L1f:
                java.lang.Object r1 = r8.L$0
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L9d
                goto L51
            L27:
                kotlin.ResultKt.throwOnFailure(r9)
                androidx.room.CoroutinesRoom$Companion$createFlow$1 r9 = androidx.room.CoroutinesRoom$Companion$createFlow$1.this
                androidx.room.RoomDatabase r9 = r9.$db
                androidx.room.InvalidationTracker r9 = r9.getInvalidationTracker()
                kotlin.jvm.internal.Ref$ObjectRef r1 = r8.$observer
                T r1 = r1.element
                androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1 r1 = (androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1) r1
                androidx.room.InvalidationTracker$Observer r1 = (androidx.room.InvalidationTracker.Observer) r1
                r9.addObserver(r1)
                kotlinx.coroutines.channels.Channel r9 = r8.$observerChannel     // Catch: java.lang.Throwable -> L9d
                kotlinx.coroutines.channels.ChannelIterator r9 = r9.iterator()     // Catch: java.lang.Throwable -> L9d
            L43:
                r8.L$0 = r9     // Catch: java.lang.Throwable -> L9d
                r8.label = r3     // Catch: java.lang.Throwable -> L9d
                java.lang.Object r1 = r9.hasNext(r8)     // Catch: java.lang.Throwable -> L9d
                if (r1 != r0) goto L4e
                return r0
            L4e:
                r7 = r1
                r1 = r9
                r9 = r7
            L51:
                java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch: java.lang.Throwable -> L9d
                boolean r9 = r9.booleanValue()     // Catch: java.lang.Throwable -> L9d
                if (r9 == 0) goto L87
                java.lang.Object r9 = r1.next()     // Catch: java.lang.Throwable -> L9d
                kotlin.Unit r9 = (kotlin.Unit) r9     // Catch: java.lang.Throwable -> L9d
                kotlin.jvm.internal.Ref$ObjectRef r9 = new kotlin.jvm.internal.Ref$ObjectRef     // Catch: java.lang.Throwable -> L9d
                r9.<init>()     // Catch: java.lang.Throwable -> L9d
                androidx.room.CoroutinesRoom$Companion$createFlow$1 r4 = androidx.room.CoroutinesRoom$Companion$createFlow$1.this     // Catch: java.lang.Throwable -> L9d
                java.util.concurrent.Callable r4 = r4.$callable     // Catch: java.lang.Throwable -> L9d
                java.lang.Object r4 = r4.call()     // Catch: java.lang.Throwable -> L9d
                r9.element = r4     // Catch: java.lang.Throwable -> L9d
                kotlin.jvm.internal.Ref$ObjectRef r4 = r8.$flowContext     // Catch: java.lang.Throwable -> L9d
                T r4 = r4.element     // Catch: java.lang.Throwable -> L9d
                kotlin.coroutines.CoroutineContext r4 = (kotlin.coroutines.CoroutineContext) r4     // Catch: java.lang.Throwable -> L9d
                androidx.room.CoroutinesRoom$Companion$createFlow$1$1$1 r5 = new androidx.room.CoroutinesRoom$Companion$createFlow$1$1$1     // Catch: java.lang.Throwable -> L9d
                r6 = 0
                r5.<init>(r9, r6)     // Catch: java.lang.Throwable -> L9d
                kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5     // Catch: java.lang.Throwable -> L9d
                r8.L$0 = r1     // Catch: java.lang.Throwable -> L9d
                r8.label = r2     // Catch: java.lang.Throwable -> L9d
                java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r8)     // Catch: java.lang.Throwable -> L9d
                if (r9 != r0) goto L15
                return r0
            L87:
                androidx.room.CoroutinesRoom$Companion$createFlow$1 r9 = androidx.room.CoroutinesRoom$Companion$createFlow$1.this
                androidx.room.RoomDatabase r9 = r9.$db
                androidx.room.InvalidationTracker r9 = r9.getInvalidationTracker()
                kotlin.jvm.internal.Ref$ObjectRef r0 = r8.$observer
                T r0 = r0.element
                androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1 r0 = (androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1) r0
                androidx.room.InvalidationTracker$Observer r0 = (androidx.room.InvalidationTracker.Observer) r0
                r9.removeObserver(r0)
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            L9d:
                r9 = move-exception
                androidx.room.CoroutinesRoom$Companion$createFlow$1 r0 = androidx.room.CoroutinesRoom$Companion$createFlow$1.this
                androidx.room.RoomDatabase r0 = r0.$db
                androidx.room.InvalidationTracker r0 = r0.getInvalidationTracker()
                kotlin.jvm.internal.Ref$ObjectRef r1 = r8.$observer
                T r1 = r1.element
                androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1 r1 = (androidx.room.CoroutinesRoom$Companion$createFlow$1$observer$1) r1
                androidx.room.InvalidationTracker$Observer r1 = (androidx.room.InvalidationTracker.Observer) r1
                r0.removeObserver(r1)
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.room.CoroutinesRoom$Companion$createFlow$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: renamed from: androidx.room.CoroutinesRoom$Companion$createFlow$1$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: CoroutinesRoom.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "R", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
        @DebugMetadata(c = "androidx.room.CoroutinesRoom$Companion$createFlow$1$1$1", f = "CoroutinesRoom.kt", i = {}, l = {123}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C00151 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.ObjectRef $result;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00151(Ref.ObjectRef objectRef, Continuation continuation) {
                super(2, continuation);
                this.$result = objectRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> completion) {
                Intrinsics.checkNotNullParameter(completion, "completion");
                return AnonymousClass1.this.new C00151(this.$result, completion);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00151) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowCollector flowCollector = AnonymousClass1.this.$this_flow;
                    T t = this.$result.element;
                    this.label = 1;
                    if (flowCollector.emit(t, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }
    }
}
