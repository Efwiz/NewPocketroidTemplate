package org.jetbrains.kotlin.resolve.scopes;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: Scopes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001:\u0001\u0018R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0019À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "contextReceiversGroup", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getContextReceiversGroup", "()Ljava/util/List;", "implicitReceiver", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "isOwnerDescriptorAccessibleByLabel", "", "()Z", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "parent", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "Base", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LexicalScope extends HierarchicalScope {
    List<ReceiverParameterDescriptor> getContextReceiversGroup();

    ReceiverParameterDescriptor getImplicitReceiver();

    LexicalScopeKind getKind();

    DeclarationDescriptor getOwnerDescriptor();

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    HierarchicalScope getParent();

    boolean isOwnerDescriptorAccessibleByLabel();

    /* JADX INFO: compiled from: Scopes.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean definitelyDoesNotContainName(LexicalScope lexicalScope, Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return HierarchicalScope.DefaultImpls.definitelyDoesNotContainName(lexicalScope, name2);
        }

        public static DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(LexicalScope lexicalScope, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return HierarchicalScope.DefaultImpls.getContributedClassifierIncludeDeprecated(lexicalScope, name2, location);
        }

        public static void recordLookup(LexicalScope lexicalScope, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            HierarchicalScope.DefaultImpls.recordLookup(lexicalScope, name2, location);
        }
    }

    /* JADX INFO: compiled from: Scopes.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope$Base;", "Lorg/jetbrains/kotlin/resolve/scopes/BaseHierarchicalScope;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "parent", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "contextReceiversGroup", "", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getContextReceiversGroup", "()Ljava/util/List;", "implicitReceiver", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "isOwnerDescriptorAccessibleByLabel", "", "()Z", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "definitelyDoesNotContainName", "name", "Lorg/jetbrains/kotlin/name/Name;", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Base extends BaseHierarchicalScope implements LexicalScope {
        private final DeclarationDescriptor ownerDescriptor;

        @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public boolean definitelyDoesNotContainName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return true;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
        public ReceiverParameterDescriptor getImplicitReceiver() {
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
        public boolean isOwnerDescriptorAccessibleByLabel() {
            return false;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
        public DeclarationDescriptor getOwnerDescriptor() {
            return this.ownerDescriptor;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Base(HierarchicalScope parent, DeclarationDescriptor ownerDescriptor) {
            super(parent);
            Intrinsics.checkNotNullParameter(parent, "parent");
            Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
            this.ownerDescriptor = ownerDescriptor;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
        public HierarchicalScope getParent() {
            HierarchicalScope parent = super.getParent();
            Intrinsics.checkNotNull(parent);
            return parent;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
        public List<ReceiverParameterDescriptor> getContextReceiversGroup() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
        public LexicalScopeKind getKind() {
            return LexicalScopeKind.EMPTY;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
        public void printStructure(Printer p) {
            Intrinsics.checkNotNullParameter(p, "p");
            p.println("Base lexical scope with owner = " + getOwnerDescriptor() + " and parent = " + getParent());
        }
    }
}
