package org.jetbrains.kotlin.resolve.scopes.utils;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
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
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: ScopeUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0011\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020#H\u0096\u0001J\u001b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0096\u0001J!\u0010(\u001a\n\u0012\u0004\u0012\u00020%\u0018\u00010)2\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0096\u0001J+\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00180+2\u0006\u0010,\u001a\u00020-2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00110/H\u0096\u0001J$\u00100\u001a\r\u0012\t\u0012\u000701¢\u0006\u0002\b20+2\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0096\u0001J$\u00103\u001a\r\u0012\t\u0012\u000704¢\u0006\u0002\b20+2\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0096\u0001J\u0011\u00105\u001a\u0002062\u0006\u00107\u001a\u000208H\u0096\u0001J\u0019\u00109\u001a\u0002062\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0096\u0001J\b\u0010:\u001a\u00020;H\u0016R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u0004\u0018\u00010\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0012\u0010\u0013\u001a\u00020\u0014X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0017\u001a\u00020\u0018X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u001b\u0010\u001b\u001a\u00020\u001c8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b\u001d\u0010\u001e¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/utils/LexicalScopeWrapper;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "newImportingScopeChain", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "(Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;)V", "contextReceiversGroup", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getContextReceiversGroup", "()Ljava/util/List;", "getDelegate", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "implicitReceiver", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "isOwnerDescriptorAccessibleByLabel", "", "()Z", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "parent", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "parent$delegate", "Lkotlin/Lazy;", "definitelyDoesNotContainName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedClassifierIncludeDeprecated", "Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation;", "getContributedDescriptors", "", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "recordLookup", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class LexicalScopeWrapper implements LexicalScope {
    private final LexicalScope delegate;
    private final ImportingScope newImportingScopeChain;

    /* JADX INFO: renamed from: parent$delegate, reason: from kotlin metadata */
    private final Lazy parent;

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.delegate.definitelyDoesNotContainName(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public List<ReceiverParameterDescriptor> getContextReceiversGroup() {
        return this.delegate.getContextReceiversGroup();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.delegate.mo7465getContributedClassifier(name2, location);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.delegate.getContributedClassifierIncludeDeprecated(name2, location);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.delegate.getContributedDescriptors(kindFilter, nameFilter);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<? extends FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.delegate.getContributedFunctions(name2, location);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<? extends VariableDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.delegate.getContributedVariables(name2, location);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public ReceiverParameterDescriptor getImplicitReceiver() {
        return this.delegate.getImplicitReceiver();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public LexicalScopeKind getKind() {
        return this.delegate.getKind();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public DeclarationDescriptor getOwnerDescriptor() {
        return this.delegate.getOwnerDescriptor();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    /* JADX INFO: renamed from: isOwnerDescriptorAccessibleByLabel */
    public boolean getIsOwnerDescriptorAccessibleByLabel() {
        return this.delegate.getIsOwnerDescriptorAccessibleByLabel();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public void printStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        this.delegate.printStructure(p);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.delegate.mo7479recordLookup(name2, location);
    }

    public LexicalScopeWrapper(LexicalScope delegate, ImportingScope newImportingScopeChain) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(newImportingScopeChain, "newImportingScopeChain");
        this.delegate = delegate;
        this.newImportingScopeChain = newImportingScopeChain;
        boolean z = delegate instanceof LexicalScopeWrapper;
        this.parent = LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<HierarchicalScope>() { // from class: org.jetbrains.kotlin.resolve.scopes.utils.LexicalScopeWrapper$parent$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final HierarchicalScope invoke() {
                boolean z2 = this.this$0.getDelegate() instanceof ImportingScope;
                HierarchicalScope parent = this.this$0.getDelegate().getParent();
                return parent instanceof LexicalScope ? ScopeUtilsKt.replaceImportingScopes((LexicalScope) parent, this.this$0.newImportingScopeChain) : this.this$0.newImportingScopeChain;
            }
        });
    }

    public final LexicalScope getDelegate() {
        return this.delegate;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope, org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public HierarchicalScope getParent() {
        return (HierarchicalScope) this.parent.getValue();
    }

    public String toString() {
        return getKind().toString();
    }
}
