package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: LexicalChainedScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 <2\u00020\u0001:\u0001<BW\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0006¢\u0006\u0002\u0010\u0011J\u0010\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020#H\u0016J\u001a\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0016J \u0010(\u001a\n\u0012\u0004\u0012\u00020%\u0018\u00010)2\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0016J*\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00040+2\u0006\u0010,\u001a\u00020-2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00060/H\u0016J#\u00100\u001a\r\u0012\t\u0012\u000701¢\u0006\u0002\b20+2\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0016J#\u00103\u001a\r\u0012\t\u0012\u000704¢\u0006\u0002\b20+2\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0016J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u000208H\u0016J\u0018\u00109\u001a\u0002062\u0006\u0010\"\u001a\u00020#2\u0006\u0010&\u001a\u00020'H\u0016J\b\u0010:\u001a\u00020;H\u0016R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0016R\u001c\u0010\u0010\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0010\u0010\u0016R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u0002\u001a\u00020\u001eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LexicalChainedScope;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "parent", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isOwnerDescriptorAccessibleByLabel", "", "implicitReceiver", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "contextReceiversGroup", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "memberScopes", "", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "isStaticScope", "(Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;ZLorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;[Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;Z)V", "getContextReceiversGroup", "()Ljava/util/List;", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "()Z", "isStaticScope$annotations", "()V", "getKind", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "[Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "definitelyDoesNotContainName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedClassifierIncludeDeprecated", "Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation;", "getContributedDescriptors", "", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "recordLookup", "toString", "", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LexicalChainedScope implements LexicalScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<ReceiverParameterDescriptor> contextReceiversGroup;
    private final ReceiverParameterDescriptor implicitReceiver;
    private final boolean isOwnerDescriptorAccessibleByLabel;
    private final boolean isStaticScope;
    private final LexicalScopeKind kind;
    private final MemberScope[] memberScopes;
    private final DeclarationDescriptor ownerDescriptor;
    private final HierarchicalScope parent;

    public /* synthetic */ LexicalChainedScope(LexicalScope lexicalScope, DeclarationDescriptor declarationDescriptor, boolean z, ReceiverParameterDescriptor receiverParameterDescriptor, List list, LexicalScopeKind lexicalScopeKind, MemberScope[] memberScopeArr, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(lexicalScope, declarationDescriptor, z, receiverParameterDescriptor, list, lexicalScopeKind, memberScopeArr, z2);
    }

    @Deprecated(message = "This value is temporary hack for resolve -- don't use it!")
    public static /* synthetic */ void isStaticScope$annotations() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    private LexicalChainedScope(LexicalScope lexicalScope, DeclarationDescriptor declarationDescriptor, boolean z, ReceiverParameterDescriptor receiverParameterDescriptor, List<? extends ReceiverParameterDescriptor> list, LexicalScopeKind lexicalScopeKind, MemberScope[] memberScopeArr, boolean z2) {
        this.ownerDescriptor = declarationDescriptor;
        this.isOwnerDescriptorAccessibleByLabel = z;
        this.implicitReceiver = receiverParameterDescriptor;
        this.contextReceiversGroup = list;
        this.kind = lexicalScopeKind;
        this.memberScopes = memberScopeArr;
        this.isStaticScope = z2;
        this.parent = ScopeUtilsKt.takeSnapshot(lexicalScope);
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

    /* JADX INFO: renamed from: isStaticScope, reason: from getter */
    public final boolean getIsStaticScope() {
        return this.isStaticScope;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope, org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public HierarchicalScope getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        MemberScope[] memberScopeArr = this.memberScopes;
        int length = memberScopeArr.length;
        if (length == 0) {
            return CollectionsKt.emptyList();
        }
        if (length == 1) {
            return ResolutionScope.DefaultImpls.getContributedDescriptors$default(memberScopeArr[0], null, null, 3, null);
        }
        Collection<DeclarationDescriptor> collectionConcat = null;
        for (MemberScope memberScope : memberScopeArr) {
            collectionConcat = org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt.concat(collectionConcat, ResolutionScope.DefaultImpls.getContributedDescriptors$default(memberScope, null, null, 3, null));
        }
        return collectionConcat == null ? SetsKt.emptySet() : collectionConcat;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        ClassifierDescriptor classifierDescriptor = null;
        for (MemberScope memberScope : this.memberScopes) {
            ClassifierDescriptor contributedClassifier = memberScope.mo7465getContributedClassifier(name2, location);
            if (contributedClassifier != null) {
                if (!(contributedClassifier instanceof ClassifierDescriptorWithTypeParameters) || !((ClassifierDescriptorWithTypeParameters) contributedClassifier).get$isExpect()) {
                    return contributedClassifier;
                }
                if (classifierDescriptor == null) {
                    classifierDescriptor = contributedClassifier;
                }
            }
        }
        return classifierDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation location) {
        DescriptorWithDeprecation<ClassifierDescriptor> contributedClassifierIncludeDeprecated;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        MemberScope[] memberScopeArr = this.memberScopes;
        int length = memberScopeArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                contributedClassifierIncludeDeprecated = null;
                break;
            }
            contributedClassifierIncludeDeprecated = memberScopeArr[i].getContributedClassifierIncludeDeprecated(name2, location);
            if (contributedClassifierIncludeDeprecated != null) {
                break;
            }
            i++;
        }
        if (contributedClassifierIncludeDeprecated == null) {
            return null;
        }
        ClassifierDescriptor classifierDescriptor = (ClassifierDescriptor) contributedClassifierIncludeDeprecated.component1();
        if (!contributedClassifierIncludeDeprecated.getIsDeprecated()) {
            return DescriptorWithDeprecation.INSTANCE.createNonDeprecated(classifierDescriptor);
        }
        for (MemberScope memberScope : this.memberScopes) {
            DescriptorWithDeprecation<ClassifierDescriptor> contributedClassifierIncludeDeprecated2 = memberScope.getContributedClassifierIncludeDeprecated(name2, location);
            if (contributedClassifierIncludeDeprecated2 != null) {
                ClassifierDescriptor classifierDescriptor2 = (ClassifierDescriptor) contributedClassifierIncludeDeprecated2.component1();
                boolean isDeprecated = contributedClassifierIncludeDeprecated2.getIsDeprecated();
                if (Intrinsics.areEqual(classifierDescriptor2, classifierDescriptor) && !isDeprecated) {
                    return DescriptorWithDeprecation.INSTANCE.createNonDeprecated(classifierDescriptor2);
                }
            }
        }
        return DescriptorWithDeprecation.INSTANCE.createDeprecated(classifierDescriptor);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<? extends PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        MemberScope[] memberScopeArr = this.memberScopes;
        int length = memberScopeArr.length;
        if (length == 0) {
            return CollectionsKt.emptyList();
        }
        if (length == 1) {
            return memberScopeArr[0].getContributedVariables(name2, location);
        }
        Collection<? extends PropertyDescriptor> collectionConcat = null;
        for (MemberScope memberScope : memberScopeArr) {
            collectionConcat = org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt.concat(collectionConcat, memberScope.getContributedVariables(name2, location));
        }
        return collectionConcat == null ? SetsKt.emptySet() : collectionConcat;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        MemberScope[] memberScopeArr = this.memberScopes;
        int length = memberScopeArr.length;
        if (length == 0) {
            return CollectionsKt.emptyList();
        }
        if (length == 1) {
            return memberScopeArr[0].getContributedFunctions(name2, location);
        }
        Collection<? extends SimpleFunctionDescriptor> collectionConcat = null;
        for (MemberScope memberScope : memberScopeArr) {
            collectionConcat = org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt.concat(collectionConcat, memberScope.getContributedFunctions(name2, location));
        }
        return collectionConcat == null ? SetsKt.emptySet() : collectionConcat;
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
        p.println(simpleName, ": ", kind, "; for descriptor: ", name2, " with implicitReceiver: ", value == null ? "NONE" : value, " with contextReceiversGroup: ", getContextReceiversGroup().isEmpty() ? "NONE" : CollectionsKt.joinToString$default(getContextReceiversGroup(), null, null, null, 0, null, new Function1<ReceiverParameterDescriptor, CharSequence>() { // from class: org.jetbrains.kotlin.resolve.scopes.LexicalChainedScope.printStructure.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ReceiverParameterDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getValue().toString();
            }
        }, 31, null), " {");
        p.pushIndent();
        for (MemberScope memberScope : this.memberScopes) {
            memberScope.mo6105printScopeStructure(p);
        }
        p.print("parent = ");
        HierarchicalScope parent = getParent();
        Printer printerWithholdIndentOnce = p.withholdIndentOnce();
        Intrinsics.checkNotNullExpressionValue(printerWithholdIndentOnce, "withholdIndentOnce(...)");
        parent.printStructure(printerWithholdIndentOnce);
        p.popIndent();
        p.println("}");
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        for (MemberScope memberScope : this.memberScopes) {
            if (!memberScope.definitelyDoesNotContainName(name2)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        for (MemberScope memberScope : this.memberScopes) {
            memberScope.mo7479recordLookup(name2, location);
        }
    }

    /* JADX INFO: compiled from: LexicalChainedScope.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jg\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0016\u0010\u0010\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00120\u0011\"\u0004\u0018\u00010\u00122\b\b\u0002\u0010\u0013\u001a\u00020\tH\u0007¢\u0006\u0002\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LexicalChainedScope$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "parent", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isOwnerDescriptorAccessibleByLabel", "", "implicitReceiver", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "contextReceiversGroup", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "memberScopes", "", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "isStaticScope", "(Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;ZLorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;[Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;Z)Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final LexicalScope create(LexicalScope parent, DeclarationDescriptor ownerDescriptor, boolean z, ReceiverParameterDescriptor receiverParameterDescriptor, List<? extends ReceiverParameterDescriptor> contextReceiversGroup, LexicalScopeKind kind, MemberScope... memberScopes) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
            Intrinsics.checkNotNullParameter(contextReceiversGroup, "contextReceiversGroup");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(memberScopes, "memberScopes");
            return create$default(this, parent, ownerDescriptor, z, receiverParameterDescriptor, contextReceiversGroup, kind, memberScopes, false, 128, null);
        }

        private Companion() {
        }

        public static /* synthetic */ LexicalScope create$default(Companion companion, LexicalScope lexicalScope, DeclarationDescriptor declarationDescriptor, boolean z, ReceiverParameterDescriptor receiverParameterDescriptor, List list, LexicalScopeKind lexicalScopeKind, MemberScope[] memberScopeArr, boolean z2, int i, Object obj) {
            return companion.create(lexicalScope, declarationDescriptor, z, receiverParameterDescriptor, list, lexicalScopeKind, memberScopeArr, (i & 128) != 0 ? false : z2);
        }

        public final LexicalScope create(LexicalScope parent, DeclarationDescriptor ownerDescriptor, boolean isOwnerDescriptorAccessibleByLabel, ReceiverParameterDescriptor implicitReceiver, List<? extends ReceiverParameterDescriptor> contextReceiversGroup, LexicalScopeKind kind, MemberScope[] memberScopes, boolean isStaticScope) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
            Intrinsics.checkNotNullParameter(contextReceiversGroup, "contextReceiversGroup");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(memberScopes, "memberScopes");
            return new LexicalChainedScope(parent, ownerDescriptor, isOwnerDescriptorAccessibleByLabel, implicitReceiver, contextReceiversGroup, kind, (MemberScope[]) org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt.listOfNonEmptyScopes((MemberScope[]) Arrays.copyOf(memberScopes, memberScopes.length)).toArray(new MemberScope[0]), isStaticScope, null);
        }
    }
}
