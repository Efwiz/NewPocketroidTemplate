package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;

/* JADX INFO: compiled from: ScopeTowerProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B!\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001e\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\f\u0010\u0011\u001a\u00020\u0012*\u00020\u0002H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/VariableAndObjectScopeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "variableProcessor", "objectProcessor", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;)V", "process", "", "", "data", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "recordLookups", "", "skippedData", "name", "Lorg/jetbrains/kotlin/name/Name;", "isEnumEntryCandidate", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableAndObjectScopeTowerProcessor<C extends Candidate> implements ScopeTowerProcessor<C> {
    private final ScopeTowerProcessor<C> objectProcessor;
    private final ScopeTowerProcessor<C> variableProcessor;

    /* JADX WARN: Multi-variable type inference failed */
    public VariableAndObjectScopeTowerProcessor(ScopeTowerProcessor<? extends C> variableProcessor, ScopeTowerProcessor<? extends C> objectProcessor) {
        Intrinsics.checkNotNullParameter(variableProcessor, "variableProcessor");
        Intrinsics.checkNotNullParameter(objectProcessor, "objectProcessor");
        this.variableProcessor = variableProcessor;
        this.objectProcessor = objectProcessor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public List<Collection<C>> process(TowerData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        List<Collection<C>> listProcess = this.variableProcessor.process(data);
        List<Collection<C>> listProcess2 = this.objectProcessor.process(data);
        if (listProcess2.isEmpty()) {
            return listProcess;
        }
        List<Collection<C>> list = listProcess2;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                Collection collection = (Collection) it.next();
                if (!(collection instanceof Collection) || !collection.isEmpty()) {
                    Iterator it2 = collection.iterator();
                    while (it2.hasNext()) {
                        if (isEnumEntryCandidate((Candidate) it2.next())) {
                            ArrayList arrayList = new ArrayList();
                            List<Collection<C>> list2 = listProcess;
                            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                            Iterator<T> it3 = list2.iterator();
                            while (it3.hasNext()) {
                                arrayList2.add(CollectionsKt.toMutableList((Collection) it3.next()));
                            }
                            arrayList.addAll(arrayList2);
                            int i = 0;
                            for (Collection<C> collection2 : listProcess2) {
                                int i2 = i + 1;
                                ArrayList arrayList3 = new ArrayList();
                                for (Object obj : collection2) {
                                    if (isEnumEntryCandidate((Candidate) obj)) {
                                        arrayList3.add(obj);
                                    }
                                }
                                ArrayList arrayList4 = arrayList3;
                                if (!arrayList4.isEmpty()) {
                                    if (i < listProcess.size()) {
                                        Object obj2 = arrayList.get(i);
                                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.collections.MutableList<C of org.jetbrains.kotlin.resolve.calls.tower.VariableAndObjectScopeTowerProcessor>");
                                        TypeIntrinsics.asMutableList(obj2).addAll(arrayList4);
                                    } else {
                                        arrayList.add(arrayList4);
                                    }
                                }
                                i = i2;
                            }
                            for (Collection<C> collection3 : listProcess2) {
                                ArrayList arrayList5 = new ArrayList();
                                for (Object obj3 : collection3) {
                                    if (!isEnumEntryCandidate((Candidate) obj3)) {
                                        arrayList5.add(obj3);
                                    }
                                }
                                ArrayList arrayList6 = arrayList5;
                                if (!arrayList6.isEmpty()) {
                                    arrayList.add(arrayList6);
                                }
                            }
                            return arrayList;
                        }
                    }
                }
            }
        }
        return CollectionsKt.plus((Collection) listProcess, (Iterable) list);
    }

    private final boolean isEnumEntryCandidate(Candidate candidate) {
        if (!(candidate instanceof ResolutionCandidate)) {
            return false;
        }
        CallableDescriptor callableDescriptor = ((ResolutionCandidate) candidate).getResolvedCall().get_candidateDescriptor();
        FakeCallableDescriptorForObject fakeCallableDescriptorForObject = callableDescriptor instanceof FakeCallableDescriptorForObject ? (FakeCallableDescriptorForObject) callableDescriptor : null;
        return fakeCallableDescriptorForObject != null && fakeCallableDescriptorForObject.getClassDescriptor().getKind() == ClassKind.ENUM_ENTRY;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public void recordLookups(Collection<? extends TowerData> skippedData, Name name2) {
        Intrinsics.checkNotNullParameter(skippedData, "skippedData");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.variableProcessor.recordLookups(skippedData, name2);
        this.objectProcessor.recordLookups(skippedData, name2);
    }
}
