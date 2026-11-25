package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: containerUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0000\u001a6\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00060\u0005¨\u0006\u0007"}, d2 = {"bfs", "Lkotlin/sequences/Sequence;", ExifInterface.GPS_DIRECTION_TRUE, "", "getNeighbors", "Lkotlin/Function1;", "", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ContainerUtilsKt {
    public static final <T> Sequence<T> bfs(Collection<? extends T> collection, Function1<? super T, ? extends Iterator<? extends T>> getNeighbors) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(getNeighbors, "getNeighbors");
        return SequencesKt.sequence(new AnonymousClass1(new ArrayDeque(collection), new LinkedHashSet(), getNeighbors, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: org.jetbrains.kotlin.util.ContainerUtilsKt$bfs$1, reason: invalid class name */
    /* JADX INFO: compiled from: containerUtils.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.util.ContainerUtilsKt$bfs$1", f = "containerUtils.kt", i = {0, 0}, l = {19}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence", "current"}, s = {"L$0", "L$1"})
    static final class AnonymousClass1<T> extends RestrictedSuspendLambda implements Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1<T, Iterator<T>> $getNeighbors;
        final /* synthetic */ ArrayDeque<T> $queue;
        final /* synthetic */ Set<T> $visited;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(ArrayDeque<T> arrayDeque, Set<T> set, Function1<? super T, ? extends Iterator<? extends T>> function1, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$queue = arrayDeque;
            this.$visited = set;
            this.$getNeighbors = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$queue, this.$visited, this.$getNeighbors, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super T> sequenceScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x002f  */
        /* JADX WARN: Code duplicated, block: B:15:0x0051 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:24:0x003d A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:26:? A[LOOP:1: B:9:0x0025->B:26:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x004f -> B:16:0x0052). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r5.label
                r2 = 1
                if (r1 == 0) goto L1d
                if (r1 != r2) goto L15
                java.lang.Object r1 = r5.L$1
                java.lang.Object r3 = r5.L$0
                kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
                kotlin.ResultKt.throwOnFailure(r6)
                goto L52
            L15:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r0)
                throw r6
            L1d:
                kotlin.ResultKt.throwOnFailure(r6)
                java.lang.Object r6 = r5.L$0
                kotlin.sequences.SequenceScope r6 = (kotlin.sequences.SequenceScope) r6
                r3 = r6
            L25:
                kotlin.collections.ArrayDeque<T> r6 = r5.$queue
                java.util.Collection r6 = (java.util.Collection) r6
                boolean r6 = r6.isEmpty()
                if (r6 != 0) goto L6a
                kotlin.collections.ArrayDeque<T> r6 = r5.$queue
                java.lang.Object r1 = r6.removeFirst()
                java.util.Set<T> r6 = r5.$visited
                boolean r6 = r6.contains(r1)
                if (r6 != 0) goto L25
                java.util.Set<T> r6 = r5.$visited
                r6.add(r1)
                r6 = r5
                kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
                r5.L$0 = r3
                r5.L$1 = r1
                r5.label = r2
                java.lang.Object r6 = r3.yield(r1, r6)
                if (r6 != r0) goto L52
                return r0
            L52:
                kotlin.jvm.functions.Function1<T, java.util.Iterator<T>> r6 = r5.$getNeighbors
                java.lang.Object r6 = r6.invoke(r1)
                java.util.Iterator r6 = (java.util.Iterator) r6
                kotlin.collections.ArrayDeque<T> r1 = r5.$queue
            L5c:
                boolean r4 = r6.hasNext()
                if (r4 == 0) goto L25
                java.lang.Object r4 = r6.next()
                r1.add(r4)
                goto L5c
            L6a:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.util.ContainerUtilsKt.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
