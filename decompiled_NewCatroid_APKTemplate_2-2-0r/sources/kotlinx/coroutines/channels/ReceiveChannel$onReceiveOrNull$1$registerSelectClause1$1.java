package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [R, E] */
/* JADX INFO: compiled from: Channel.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/channels/ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1.class */
@Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\b\n\u0002\b\u0004\n\u0002\u0018\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001\"\u0006\b\u0001\u0010\u0002 \u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "R", ExifInterface.LONGITUDE_EAST, "it", "Lkotlinx/coroutines/channels/ChannelResult;"})
@DebugMetadata(f = "Channel.kt", l = {375}, i = {}, s = {}, n = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, c = "kotlinx.coroutines.channels.ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1")
final class ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1<E, R> extends SuspendLambda implements Function2<ChannelResult<? extends E>, Continuation<? super R>, Object> {
    int label;
    /* synthetic */ Object L$0;
    final /* synthetic */ Function2<E, Continuation<? super R>, Object> $block;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1(Function2<? super E, ? super Continuation<? super R>, ? extends Object> function2, Continuation<? super ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1> continuation) {
        super(2, continuation);
        this.$block = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> continuation) {
        ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1 receiveChannel$onReceiveOrNull$1$registerSelectClause1$1 = new ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1(this.$block, continuation);
        receiveChannel$onReceiveOrNull$1$registerSelectClause1$1.L$0 = value;
        return receiveChannel$onReceiveOrNull$1$registerSelectClause1$1;
    }

    @Nullable
    /* JADX INFO: renamed from: invoke-WpGqRn0, reason: not valid java name */
    public final Object m4461invokeWpGqRn0(@NotNull Object p1, @Nullable Continuation<? super R> continuation) {
        return ((ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1) create(ChannelResult.m4442boximpl(p1), continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
        return m4461invokeWpGqRn0(((ChannelResult) p1).getHolder(), (Continuation) p2);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't change immutable type java.lang.Object to kotlinx.coroutines.channels.ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1<E, R> for r6v1 'this'  java.lang.Object
        	at jadx.core.dex.instructions.args.SSAVar.setType(SSAVar.java:114)
        	at jadx.core.dex.instructions.args.RegisterArg.setType(RegisterArg.java:52)
        	at jadx.core.dex.visitors.ModVisitor.removeCheckCast(ModVisitor.java:417)
        	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:152)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
        */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
        /*
            r6 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r11 = r0
            r0 = r6
            int r0 = r0.label
            switch(r0) {
                case 0: goto L20;
                case 1: goto L5a;
                default: goto L60;
            }
        L20:
            r0 = r7
            kotlin.ResultKt.throwOnFailure(r0)
            r0 = r6
            java.lang.Object r0 = r0.L$0
            kotlinx.coroutines.channels.ChannelResult r0 = (kotlinx.coroutines.channels.ChannelResult) r0
            java.lang.Object r0 = r0.getHolder()
            r8 = r0
            r0 = r8
            java.lang.Throwable r0 = kotlinx.coroutines.channels.ChannelResult.m4446exceptionOrNullimpl(r0)
            r1 = r0
            if (r1 == 0) goto L3d
            r9 = r0
            r0 = 0
            r10 = r0
            r0 = r9
            throw r0
        L3d:
            r0 = r6
            kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super R>, java.lang.Object> r0 = r0.$block
            r1 = r8
            java.lang.Object r1 = kotlinx.coroutines.channels.ChannelResult.m4447getOrNullimpl(r1)
            r2 = r6
            r3 = r6
            r4 = 1
            r3.label = r4
            java.lang.Object r0 = r0.invoke(r1, r2)
            r1 = r0
            r2 = r11
            if (r1 != r2) goto L5f
            r1 = r11
            return r1
        L5a:
            r0 = r7
            kotlin.ResultKt.throwOnFailure(r0)
            r0 = r7
        L5f:
            return r0
        L60:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r1 = r0
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
