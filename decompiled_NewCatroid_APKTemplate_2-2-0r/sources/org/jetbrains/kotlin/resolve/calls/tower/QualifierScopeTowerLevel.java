package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J \u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/QualifierScopeTowerLevel;", "Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractScopeTowerLevel;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "qualifier", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;)V", "getQualifier", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/QualifierReceiver;", "getFunctions", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "name", "Lorg/jetbrains/kotlin/name/Name;", "extensionReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getObjects", "getVariables", "recordLookup", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class QualifierScopeTowerLevel extends AbstractScopeTowerLevel {
    private final QualifierReceiver qualifier;

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public void recordLookup(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    public final QualifierReceiver getQualifier() {
        return this.qualifier;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QualifierScopeTowerLevel(ImplicitScopeTower scopeTower, QualifierReceiver qualifier) {
        super(scopeTower);
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        this.qualifier = qualifier;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public List<CandidateWithBoundDispatchReceiver> getVariables(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection contributedVariablesAndIntercept = TowerLevelsKt.getContributedVariablesAndIntercept(this.qualifier.getStaticScope(), name2, getLocation(), this.qualifier.getClassValueReceiverWithSmartCastInfo(), extensionReceiver, getScopeTower());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedVariablesAndIntercept, 10));
        Iterator it = contributedVariablesAndIntercept.iterator();
        while (it.hasNext()) {
            arrayList.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (VariableDescriptor) it.next(), null, null, null, 12, null));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public List<CandidateWithBoundDispatchReceiver> getObjects(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection contributedObjectVariables = TowerLevelsKt.getContributedObjectVariables(this.qualifier.getStaticScope(), name2, getLocation());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedObjectVariables, 10));
        Iterator it = contributedObjectVariables.iterator();
        while (it.hasNext()) {
            arrayList.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (VariableDescriptor) it.next(), null, null, null, 12, null));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public List<CandidateWithBoundDispatchReceiver> getFunctions(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection contributedFunctionsAndConstructors = TowerLevelsKt.getContributedFunctionsAndConstructors(this.qualifier.getStaticScope(), name2, getLocation(), this.qualifier.getClassValueReceiverWithSmartCastInfo(), extensionReceiver, getScopeTower());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedFunctionsAndConstructors, 10));
        Iterator it = contributedFunctionsAndConstructors.iterator();
        while (it.hasNext()) {
            arrayList.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (FunctionDescriptor) it.next(), null, null, null, 12, null));
        }
        return arrayList;
    }
}
