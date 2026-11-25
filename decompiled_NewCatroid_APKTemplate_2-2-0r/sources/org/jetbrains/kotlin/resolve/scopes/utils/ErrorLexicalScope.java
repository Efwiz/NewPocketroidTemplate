package org.jetbrains.kotlin.resolve.scopes.utils;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
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
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.types.error.ErrorClassDescriptor;
import org.jetbrains.kotlin.types.error.ErrorEntity;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: ScopeUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J*\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00130!2\u0006\u0010\"\u001a\u00020#2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\f0%H\u0016J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020'0!2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u001e\u0010(\u001a\b\u0012\u0004\u0012\u00020)0!2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/utils/ErrorLexicalScope;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "()V", "contextReceiversGroup", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getContextReceiversGroup", "()Ljava/util/List;", "implicitReceiver", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "isOwnerDescriptorAccessibleByLabel", "", "()Z", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "parent", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorLexicalScope implements LexicalScope {
    private final List<ReceiverParameterDescriptor> contextReceiversGroup;
    private final ReceiverParameterDescriptor implicitReceiver;
    private final boolean isOwnerDescriptorAccessibleByLabel;
    private final LexicalScopeKind kind;
    private final DeclarationDescriptor ownerDescriptor;
    private final HierarchicalScope parent = new HierarchicalScope() { // from class: org.jetbrains.kotlin.resolve.scopes.utils.ErrorLexicalScope$parent$1
        private final HierarchicalScope parent;

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        /* JADX INFO: renamed from: getContributedClassifier */
        public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public boolean definitelyDoesNotContainName(Name name2) {
            return HierarchicalScope.DefaultImpls.definitelyDoesNotContainName(this, name2);
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation lookupLocation) {
            return HierarchicalScope.DefaultImpls.getContributedClassifierIncludeDeprecated(this, name2, lookupLocation);
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        /* JADX INFO: renamed from: recordLookup */
        public void mo7479recordLookup(Name name2, LookupLocation lookupLocation) {
            HierarchicalScope.DefaultImpls.recordLookup(this, name2, lookupLocation);
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
        public HierarchicalScope getParent() {
            return this.parent;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
        public void printStructure(Printer p) {
            Intrinsics.checkNotNullParameter(p, "p");
            p.print(ErrorEntity.PARENT_OF_ERROR_SCOPE.getDebugText());
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public Collection<VariableDescriptor> getContributedVariables(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public Collection<FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
            Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            return SetsKt.emptySet();
        }
    };

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return null;
    }

    public ErrorLexicalScope() {
        String str = String.format(ErrorEntity.ERROR_CLASS.getDebugText(), Arrays.copyOf(new Object[]{"unknown"}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        Name nameSpecial = Name.special(str);
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        this.ownerDescriptor = new ErrorClassDescriptor(nameSpecial);
        this.contextReceiversGroup = CollectionsKt.emptyList();
        this.kind = LexicalScopeKind.THROWING;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        return LexicalScope.DefaultImpls.definitelyDoesNotContainName(this, name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation lookupLocation) {
        return LexicalScope.DefaultImpls.getContributedClassifierIncludeDeprecated(this, name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation lookupLocation) {
        LexicalScope.DefaultImpls.recordLookup(this, name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope, org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public HierarchicalScope getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public void printStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.print(ErrorEntity.ERROR_SCOPE.getDebugText());
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
    public ReceiverParameterDescriptor getImplicitReceiver() {
        return this.implicitReceiver;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public List<ReceiverParameterDescriptor> getContextReceiversGroup() {
        return this.contextReceiversGroup;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public LexicalScopeKind getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<VariableDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return SetsKt.emptySet();
    }
}
