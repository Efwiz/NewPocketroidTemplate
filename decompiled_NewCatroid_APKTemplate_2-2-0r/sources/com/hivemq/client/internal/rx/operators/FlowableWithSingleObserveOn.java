package com.hivemq.client.internal.rx.operators;

import com.hivemq.client.rx.FlowableWithSingle;
import com.hivemq.client.rx.reactivestreams.WithSingleSubscriber;
import io.reactivex.Scheduler;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes10.dex */
public class FlowableWithSingleObserveOn<F, S> extends FlowableWithSingleOperator<F, S, F, S> {
    private final int bufferSize;
    private final boolean delayError;
    private final Scheduler scheduler;

    public FlowableWithSingleObserveOn(FlowableWithSingle<F, S> flowableWithSingle, Scheduler scheduler, boolean z, int i) {
        super(flowableWithSingle);
        this.scheduler = scheduler;
        this.delayError = z;
        this.bufferSize = i;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super F> subscriber) {
        this.source.observeOn(this.scheduler, this.delayError, this.bufferSize).subscribe(subscriber);
    }

    @Override // com.hivemq.client.rx.FlowableWithSingle
    protected void subscribeBothActual(WithSingleSubscriber<? super F, ? super S> withSingleSubscriber) {
        FlowableWithSingleCombine.split(new FlowableWithSingleCombine(this.source).observeOn(this.scheduler, this.delayError, this.bufferSize), withSingleSubscriber);
    }
}
