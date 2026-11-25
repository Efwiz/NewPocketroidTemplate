package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.DeprecatedLexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0010\u0018\u00002\u00020\u0001B\u0017\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J \u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeBasedTowerLevel;", "Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractScopeTowerLevel;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "lexicalScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;)V", "resolutionScope", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;)V", "deprecationDiagnosticOfThisScope", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "getDeprecationDiagnosticOfThisScope", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "getFunctions", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "name", "Lorg/jetbrains/kotlin/name/Name;", "extensionReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getObjects", "getVariables", "recordLookup", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ScopeBasedTowerLevel extends AbstractScopeTowerLevel {
    private final ResolutionDiagnostic deprecationDiagnosticOfThisScope;
    private final ResolutionScope resolutionScope;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected ScopeBasedTowerLevel(ImplicitScopeTower scopeTower, ResolutionScope resolutionScope) {
        super(scopeTower);
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
        this.resolutionScope = resolutionScope;
        this.deprecationDiagnosticOfThisScope = resolutionScope instanceof DeprecatedLexicalScope ? new ResolvedUsingDeprecatedVisibility(resolutionScope, getLocation()) : null;
    }

    public final ResolutionDiagnostic getDeprecationDiagnosticOfThisScope() {
        return this.deprecationDiagnosticOfThisScope;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ScopeBasedTowerLevel(ImplicitScopeTower scopeTower, LexicalScope lexicalScope) {
        this(scopeTower, (ResolutionScope) lexicalScope);
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(lexicalScope, "lexicalScope");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getVariables(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection contributedVariablesAndIntercept = TowerLevelsKt.getContributedVariablesAndIntercept(this.resolutionScope, name2, getLocation(), null, extensionReceiver, getScopeTower());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedVariablesAndIntercept, 10));
        Iterator it = contributedVariablesAndIntercept.iterator();
        while (it.hasNext()) {
            arrayList.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (VariableDescriptor) it.next(), null, this.deprecationDiagnosticOfThisScope, null, 8, null));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getObjects(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Collection<DescriptorWithDeprecation> contributedObjectVariablesIncludeDeprecated = TowerLevelsKt.getContributedObjectVariablesIncludeDeprecated(this.resolutionScope, name2, getLocation());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contributedObjectVariablesIncludeDeprecated, 10));
        for (DescriptorWithDeprecation descriptorWithDeprecation : contributedObjectVariablesIncludeDeprecated) {
            arrayList.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (VariableDescriptor) descriptorWithDeprecation.component1(), null, descriptorWithDeprecation.getIsDeprecated() ? new ResolvedUsingDeprecatedVisibility(this.resolutionScope, getLocation()) : null, null, 8, null));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getFunctions(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        ArrayList arrayList = new ArrayList();
        Iterator it = TowerLevelsKt.getContributedFunctionsAndConstructors(this.resolutionScope, name2, getLocation(), null, extensionReceiver, getScopeTower()).iterator();
        while (it.hasNext()) {
            arrayList.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (FunctionDescriptor) it.next(), null, this.deprecationDiagnosticOfThisScope, null, 8, null));
        }
        ArrayList arrayList2 = arrayList;
        DescriptorWithDeprecation<ClassifierDescriptor> contributedClassifierIncludeDeprecated = this.resolutionScope.getContributedClassifierIncludeDeprecated(name2, getLocation());
        if (contributedClassifierIncludeDeprecated != null && contributedClassifierIncludeDeprecated.isDeprecated()) {
            Iterator it2 = TowerLevelsKt.getConstructorsOfClassifier((ClassifierDescriptor) contributedClassifierIncludeDeprecated.getDescriptor()).iterator();
            while (it2.hasNext()) {
                arrayList2.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (ConstructorDescriptor) it2.next(), null, new ResolvedUsingDeprecatedVisibility(this.resolutionScope, getLocation()), null, 8, null));
            }
        }
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public void recordLookup(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.resolutionScope.mo7479recordLookup(name2, getLocation());
    }
}
