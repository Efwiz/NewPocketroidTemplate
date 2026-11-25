package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;

/* JADX INFO: compiled from: InvokeProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u001fB!\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003¢\u0006\u0002\u0010\u0007J\u001d\u0010\u0013\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00032\u0006\u0010\u0014\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0015J!\u0010\u0016\u001a\u000e\u0018\u00010\rR\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0014\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0019H\u0004J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0010H$J\u001c\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f0\u001d2\u0006\u0010\u001b\u001a\u00020\u0010H\u0016J0\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f0\u001d*\u0012\u0012\u000e\u0012\f0\rR\b\u0012\u0004\u0012\u00028\u00000\u00000\f2\u0006\u0010\u001b\u001a\u00020\u0010H\u0002R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR$\u0010\n\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f0\rR\b\u0012\u0004\u0012\u00028\u00000\u00000\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractInvokeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "factoryProviderForInvoke", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactoryProviderForInvoke;", "variableProcessor", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactoryProviderForInvoke;Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;)V", "getFactoryProviderForInvoke", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactoryProviderForInvoke;", "invokeProcessors", "", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractInvokeTowerProcessor$VariableInvokeProcessor;", "previousData", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "getVariableProcessor", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "createInvokeProcessor", "variableCandidate", "(Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;)Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "createVariableInvokeProcessor", "(Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;)Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractInvokeTowerProcessor$VariableInvokeProcessor;", "hasInvokeProcessors", "", "mayDataBeApplicable", "data", "process", "", "processVariableGroup", "VariableInvokeProcessor", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractInvokeTowerProcessor<C extends Candidate> implements ScopeTowerProcessor<C> {
    private final CandidateFactoryProviderForInvoke<C> factoryProviderForInvoke;
    private final List<Collection<AbstractInvokeTowerProcessor<C>.VariableInvokeProcessor>> invokeProcessors;
    private final ArrayList<TowerData> previousData;
    private final ScopeTowerProcessor<C> variableProcessor;

    protected abstract ScopeTowerProcessor<C> createInvokeProcessor(C variableCandidate);

    protected abstract boolean mayDataBeApplicable(TowerData data);

    /* JADX WARN: Multi-variable type inference failed */
    public AbstractInvokeTowerProcessor(CandidateFactoryProviderForInvoke<C> factoryProviderForInvoke, ScopeTowerProcessor<? extends C> variableProcessor) {
        Intrinsics.checkNotNullParameter(factoryProviderForInvoke, "factoryProviderForInvoke");
        Intrinsics.checkNotNullParameter(variableProcessor, "variableProcessor");
        this.factoryProviderForInvoke = factoryProviderForInvoke;
        this.variableProcessor = variableProcessor;
        this.previousData = new ArrayList<>();
        this.invokeProcessors = new ArrayList();
    }

    protected final CandidateFactoryProviderForInvoke<C> getFactoryProviderForInvoke() {
        return this.factoryProviderForInvoke;
    }

    protected final ScopeTowerProcessor<C> getVariableProcessor() {
        return this.variableProcessor;
    }

    protected final boolean hasInvokeProcessors() {
        return !this.invokeProcessors.isEmpty();
    }

    /* JADX INFO: compiled from: InvokeProcessors.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00028\u0000\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\u0002\u0010\u0004J\u001c\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\r0\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001e\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0002\u001a\u00028\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000b\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractInvokeTowerProcessor$VariableInvokeProcessor;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "variableCandidate", "invokeProcessor", "(Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractInvokeTowerProcessor;Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;)V", "getInvokeProcessor", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "getVariableCandidate", "()Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "setVariableCandidate", "(Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;)V", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "process", "", "data", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "recordLookups", "", "skippedData", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class VariableInvokeProcessor implements ScopeTowerProcessor<C> {
        private final ScopeTowerProcessor<C> invokeProcessor;
        final /* synthetic */ AbstractInvokeTowerProcessor<C> this$0;
        private C variableCandidate;

        /* JADX WARN: Multi-variable type inference failed */
        public VariableInvokeProcessor(AbstractInvokeTowerProcessor abstractInvokeTowerProcessor, C variableCandidate, ScopeTowerProcessor<? extends C> invokeProcessor) {
            Intrinsics.checkNotNullParameter(variableCandidate, "variableCandidate");
            Intrinsics.checkNotNullParameter(invokeProcessor, "invokeProcessor");
            this.this$0 = abstractInvokeTowerProcessor;
            this.variableCandidate = variableCandidate;
            this.invokeProcessor = invokeProcessor;
        }

        public final C getVariableCandidate() {
            return this.variableCandidate;
        }

        public final void setVariableCandidate(C c) {
            Intrinsics.checkNotNullParameter(c, "<set-?>");
            this.variableCandidate = c;
        }

        public final ScopeTowerProcessor<C> getInvokeProcessor() {
            return this.invokeProcessor;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
        public List<List<C>> process(TowerData data) {
            Intrinsics.checkNotNullParameter(data, "data");
            List<Collection<C>> listProcess = this.invokeProcessor.process(data);
            AbstractInvokeTowerProcessor<C> abstractInvokeTowerProcessor = this.this$0;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listProcess, 10));
            Iterator<T> it = listProcess.iterator();
            while (it.hasNext()) {
                Collection collection = (Collection) it.next();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
                Iterator it2 = collection.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(abstractInvokeTowerProcessor.getFactoryProviderForInvoke().transformCandidate(this.variableCandidate, (Candidate) it2.next()));
                }
                arrayList.add(arrayList2);
            }
            return arrayList;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
        public void recordLookups(Collection<? extends TowerData> skippedData, Name name2) {
            Intrinsics.checkNotNullParameter(skippedData, "skippedData");
            Intrinsics.checkNotNullParameter(name2, "name");
            this.invokeProcessor.recordLookups(skippedData, name2);
        }
    }

    private final AbstractInvokeTowerProcessor<C>.VariableInvokeProcessor createVariableInvokeProcessor(C variableCandidate) {
        ScopeTowerProcessor<C> scopeTowerProcessorCreateInvokeProcessor = createInvokeProcessor(variableCandidate);
        if (scopeTowerProcessorCreateInvokeProcessor != null) {
            return new VariableInvokeProcessor(this, variableCandidate, scopeTowerProcessorCreateInvokeProcessor);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public List<Collection<C>> process(TowerData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        ArrayList arrayList = new ArrayList(0);
        if (mayDataBeApplicable(data)) {
            this.previousData.add(data);
            Iterator<Collection<AbstractInvokeTowerProcessor<C>.VariableInvokeProcessor>> it = this.invokeProcessors.iterator();
            while (it.hasNext()) {
                arrayList.addAll(processVariableGroup(it.next(), data));
            }
        }
        for (Collection<C> collection : this.variableProcessor.process(data)) {
            ArrayList arrayList2 = new ArrayList();
            for (C c : collection) {
                AbstractInvokeTowerProcessor<C>.VariableInvokeProcessor variableInvokeProcessorCreateVariableInvokeProcessor = c.isSuccessful() ? createVariableInvokeProcessor(c) : null;
                if (variableInvokeProcessorCreateVariableInvokeProcessor != null) {
                    arrayList2.add(variableInvokeProcessorCreateVariableInvokeProcessor);
                }
            }
            ArrayList arrayList3 = arrayList2;
            ArrayList arrayList4 = arrayList3;
            if (!arrayList4.isEmpty()) {
                this.invokeProcessors.add(arrayList3);
                for (TowerData towerData : this.previousData) {
                    Intrinsics.checkNotNull(towerData);
                    arrayList.addAll(processVariableGroup(arrayList4, towerData));
                }
            }
        }
        return arrayList;
    }

    private final List<Collection<C>> processVariableGroup(Collection<AbstractInvokeTowerProcessor<C>.VariableInvokeProcessor> collection, TowerData towerData) {
        int size = collection.size();
        if (size == 0) {
            return CollectionsKt.emptyList();
        }
        if (size == 1) {
            return ((VariableInvokeProcessor) CollectionsKt.single(collection)).process(towerData);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, CollectionsKt.flatten(((VariableInvokeProcessor) it.next()).process(towerData)));
        }
        return CollectionsKt.listOf(arrayList);
    }
}
