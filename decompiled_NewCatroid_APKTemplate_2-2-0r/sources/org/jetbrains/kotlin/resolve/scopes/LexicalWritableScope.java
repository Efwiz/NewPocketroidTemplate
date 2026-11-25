package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: LexicalWritableScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001/B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020#J\u000e\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020&J\b\u0010'\u001a\u00020\u001eH\u0002J\u0006\u0010(\u001a\u00020\u001eJ\u0010\u0010)\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020+H\u0016J\u0006\u0010,\u001a\u00020\u0003J\b\u0010-\u001a\u00020.H\u0016R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0016R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\b\u0018\u00010\u001aR\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeStorage;", "parent", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isOwnerDescriptorAccessibleByLabel", "", "redeclarationChecker", "Lorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "(Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;ZLorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;)V", "canWrite", "contextReceiversGroup", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getContextReceiversGroup", "()Ljava/util/List;", "implicitReceiver", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "()Z", "getKind", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "lastSnapshot", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope$Snapshot;", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "addClassifierDescriptor", "", "classifierDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "addFunctionDescriptor", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "addVariableDescriptor", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "checkMayWrite", "freeze", "printStructure", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "takeSnapshot", "toString", "", "Snapshot", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LexicalWritableScope extends LexicalScopeStorage {
    private boolean canWrite;
    private final boolean isOwnerDescriptorAccessibleByLabel;
    private final LexicalScopeKind kind;
    private Snapshot lastSnapshot;
    private final DeclarationDescriptor ownerDescriptor;

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public ReceiverParameterDescriptor getImplicitReceiver() {
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public DeclarationDescriptor getOwnerDescriptor() {
        return this.ownerDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    /* JADX INFO: renamed from: isOwnerDescriptorAccessibleByLabel, reason: from getter */
    public boolean getIsOwnerDescriptorAccessibleByLabel() {
        return this.isOwnerDescriptorAccessibleByLabel;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public LexicalScopeKind getKind() {
        return this.kind;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LexicalWritableScope(LexicalScope parent, DeclarationDescriptor ownerDescriptor, boolean z, LocalRedeclarationChecker redeclarationChecker, LexicalScopeKind kind) {
        super(parent, redeclarationChecker);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        Intrinsics.checkNotNullParameter(redeclarationChecker, "redeclarationChecker");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.ownerDescriptor = ownerDescriptor;
        this.isOwnerDescriptorAccessibleByLabel = z;
        this.kind = kind;
        this.canWrite = true;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public List<ReceiverParameterDescriptor> getContextReceiversGroup() {
        return CollectionsKt.emptyList();
    }

    public final void freeze() {
        this.canWrite = false;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0015  */
    public final LexicalScope takeSnapshot() {
        Snapshot snapshot = this.lastSnapshot;
        if (snapshot != null) {
            Intrinsics.checkNotNull(snapshot);
            if (snapshot.getDescriptorLimit() != getAddedDescriptors().size()) {
                this.lastSnapshot = new Snapshot(getAddedDescriptors().size());
            }
        } else {
            this.lastSnapshot = new Snapshot(getAddedDescriptors().size());
        }
        Snapshot snapshot2 = this.lastSnapshot;
        Intrinsics.checkNotNull(snapshot2);
        return snapshot2;
    }

    public final void addVariableDescriptor(VariableDescriptor variableDescriptor) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        checkMayWrite();
        addVariableOrClassDescriptor(variableDescriptor);
    }

    public final void addFunctionDescriptor(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        checkMayWrite();
        addFunctionDescriptorInternal(functionDescriptor);
    }

    public final void addClassifierDescriptor(ClassifierDescriptor classifierDescriptor) {
        Intrinsics.checkNotNullParameter(classifierDescriptor, "classifierDescriptor");
        checkMayWrite();
        addVariableOrClassDescriptor(classifierDescriptor);
    }

    private final void checkMayWrite() {
        if (!this.canWrite) {
            throw new IllegalStateException("Cannot write into freezed scope:" + this);
        }
    }

    /* JADX INFO: compiled from: LexicalWritableScope.kt */
    @Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020 H\u0096\u0001J\u001a\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$H\u0016J \u0010%\u001a\n\u0012\u0004\u0012\u00020\"\u0018\u00010&2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$H\u0016J*\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00170(2\u0006\u0010)\u001a\u00020*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00100,H\u0016J\u001e\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u00062\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$H\u0016J\u001e\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u00062\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0016J\u0019\u00105\u001a\u0002022\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$H\u0096\u0001J\b\u00106\u001a\u000207H\u0016R\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u0004\u0018\u00010\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0017X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\u001a\u001a\u00020\u001bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope$Snapshot;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "descriptorLimit", "", "(Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope;I)V", "contextReceiversGroup", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getContextReceiversGroup", "()Ljava/util/List;", "getDescriptorLimit", "()I", "implicitReceiver", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "isOwnerDescriptorAccessibleByLabel", "", "()Z", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "parent", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "definitelyDoesNotContainName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedClassifierIncludeDeprecated", "Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation;", "getContributedDescriptors", "", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "recordLookup", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Snapshot implements LexicalScope {
        private final /* synthetic */ LexicalWritableScope $$delegate_0;
        private final int descriptorLimit;

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public boolean definitelyDoesNotContainName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return this.$$delegate_0.definitelyDoesNotContainName(name2);
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
        public List<ReceiverParameterDescriptor> getContextReceiversGroup() {
            return this.$$delegate_0.getContextReceiversGroup();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
        public ReceiverParameterDescriptor getImplicitReceiver() {
            return this.$$delegate_0.getImplicitReceiver();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
        public LexicalScopeKind getKind() {
            return this.$$delegate_0.getKind();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
        public DeclarationDescriptor getOwnerDescriptor() {
            return this.$$delegate_0.getOwnerDescriptor();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope, org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
        public HierarchicalScope getParent() {
            return this.$$delegate_0.getParent();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
        /* JADX INFO: renamed from: isOwnerDescriptorAccessibleByLabel */
        public boolean getIsOwnerDescriptorAccessibleByLabel() {
            return this.$$delegate_0.getIsOwnerDescriptorAccessibleByLabel();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        /* JADX INFO: renamed from: recordLookup */
        public void mo7479recordLookup(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            this.$$delegate_0.mo7479recordLookup(name2, location);
        }

        public Snapshot(int i) {
            this.descriptorLimit = i;
            this.$$delegate_0 = LexicalWritableScope.this;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public /* bridge */ /* synthetic */ Collection getContributedDescriptors(DescriptorKindFilter descriptorKindFilter, Function1 function1) {
            return getContributedDescriptors(descriptorKindFilter, (Function1<? super Name, Boolean>) function1);
        }

        public final int getDescriptorLimit() {
            return this.descriptorLimit;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public List<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
            Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            return LexicalWritableScope.this.getAddedDescriptors().subList(0, this.descriptorLimit);
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        /* JADX INFO: renamed from: getContributedClassifier */
        public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            DeclarationDescriptor declarationDescriptorVariableOrClassDescriptorByName = LexicalWritableScope.this.variableOrClassDescriptorByName(name2, this.descriptorLimit);
            if (declarationDescriptorVariableOrClassDescriptorByName instanceof ClassifierDescriptor) {
                return (ClassifierDescriptor) declarationDescriptorVariableOrClassDescriptorByName;
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            DeclarationDescriptor declarationDescriptorVariableOrClassDescriptorByName = LexicalWritableScope.this.variableOrClassDescriptorByName(name2, this.descriptorLimit);
            ClassifierDescriptor classifierDescriptor = declarationDescriptorVariableOrClassDescriptorByName instanceof ClassifierDescriptor ? (ClassifierDescriptor) declarationDescriptorVariableOrClassDescriptorByName : null;
            if (classifierDescriptor != null) {
                return DescriptorWithDeprecation.INSTANCE.createNonDeprecated(classifierDescriptor);
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public List<VariableDescriptor> getContributedVariables(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            DeclarationDescriptor declarationDescriptorVariableOrClassDescriptorByName = LexicalWritableScope.this.variableOrClassDescriptorByName(name2, this.descriptorLimit);
            return CollectionsKt.listOfNotNull(declarationDescriptorVariableOrClassDescriptorByName instanceof VariableDescriptor ? (VariableDescriptor) declarationDescriptorVariableOrClassDescriptorByName : null);
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public List<FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return LexicalWritableScope.this.functionsByName(name2, this.descriptorLimit);
        }

        public String toString() {
            return "Snapshot(" + this.descriptorLimit + ") for " + getKind();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
        public void printStructure(Printer p) {
            Intrinsics.checkNotNullParameter(p, "p");
            p.println("Snapshot with descriptorLimit = " + this.descriptorLimit + " for scope:");
            LexicalWritableScope.this.printStructure(p);
        }
    }

    public String toString() {
        return getKind().toString();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public void printStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        String simpleName = getClass().getSimpleName();
        LexicalScopeKind kind = getKind();
        Name name2 = getOwnerDescriptor().getShortName();
        ReceiverParameterDescriptor implicitReceiver = getImplicitReceiver();
        ReceiverValue value = implicitReceiver != null ? implicitReceiver.getValue() : null;
        p.println(simpleName, ": ", kind, "; for descriptor: ", name2, " with implicitReceivers: ", value == null ? "NONE" : value, " with contextReceiversGroup: ", getContextReceiversGroup().isEmpty() ? "NONE" : CollectionsKt.joinToString$default(getContextReceiversGroup(), null, null, null, 0, null, new Function1<ReceiverParameterDescriptor, CharSequence>() { // from class: org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope.printStructure.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ReceiverParameterDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getValue().toString();
            }
        }, 31, null), " {");
        p.pushIndent();
        p.print("parent = ");
        HierarchicalScope parent = getParent();
        Printer printerWithholdIndentOnce = p.withholdIndentOnce();
        Intrinsics.checkNotNullExpressionValue(printerWithholdIndentOnce, "withholdIndentOnce(...)");
        parent.printStructure(printerWithholdIndentOnce);
        p.popIndent();
        p.println("}");
    }
}
