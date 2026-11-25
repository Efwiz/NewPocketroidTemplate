package org.jetbrains.kotlin.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.lazy.DelegationFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: DelegationResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 %*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0003%&'BS\b\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J#\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00028\u00002\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0002¢\u0006\u0002\u0010\u001aJ\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0002J$\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\"\u001a\u00020\u001fH\u0002J\u0011\u0010#\u001a\u00020\u0017*\u00028\u0000H\u0002¢\u0006\u0002\u0010$R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/resolve/DelegationResolver;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "existingMembers", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "memberExtractor", "Lorg/jetbrains/kotlin/resolve/DelegationResolver$MemberExtractor;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/DelegationResolver$TypeResolver;", "delegationFilter", "Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Ljava/util/Collection;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/DelegationResolver$MemberExtractor;Lorg/jetbrains/kotlin/resolve/DelegationResolver$TypeResolver;Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "checkClashWithOtherDelegatedMember", "", "candidate", "delegatedMembers", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Ljava/util/Collection;)Z", "generateDelegatedMembers", "generateDelegatesForInterface", "existingDelegates", "delegatedInterfaceType", "Lorg/jetbrains/kotlin/types/KotlinType;", "generateDelegationCandidates", "getDelegatableMembers", "interfaceType", "overridesClassMembersOnly", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Z", "Companion", "MemberExtractor", "TypeResolver", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegationResolver<T extends CallableMemberDescriptor> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KtPureClassOrObject classOrObject;
    private final DelegationFilter delegationFilter;
    private final Collection<CallableDescriptor> existingMembers;
    private final LanguageVersionSettings languageVersionSettings;
    private final MemberExtractor<T> memberExtractor;
    private final ClassDescriptor ownerDescriptor;
    private final BindingTrace trace;
    private final TypeResolver typeResolver;

    /* JADX INFO: compiled from: DelegationResolver.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\n\b\u0001\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DelegationResolver$MemberExtractor;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "", "getMembersByType", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface MemberExtractor<T extends CallableMemberDescriptor> {
        Collection<T> getMembersByType(KotlinType type);
    }

    /* JADX INFO: compiled from: DelegationResolver.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DelegationResolver$TypeResolver;", "", "resolve", "Lorg/jetbrains/kotlin/types/KotlinType;", "reference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface TypeResolver {
        KotlinType resolve(KtTypeReference reference);
    }

    public /* synthetic */ DelegationResolver(KtPureClassOrObject ktPureClassOrObject, ClassDescriptor classDescriptor, Collection collection, BindingTrace bindingTrace, MemberExtractor memberExtractor, TypeResolver typeResolver, DelegationFilter delegationFilter, LanguageVersionSettings languageVersionSettings, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktPureClassOrObject, classDescriptor, collection, bindingTrace, memberExtractor, typeResolver, delegationFilter, languageVersionSettings);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private DelegationResolver(KtPureClassOrObject ktPureClassOrObject, ClassDescriptor classDescriptor, Collection<? extends CallableDescriptor> collection, BindingTrace bindingTrace, MemberExtractor<? extends T> memberExtractor, TypeResolver typeResolver, DelegationFilter delegationFilter, LanguageVersionSettings languageVersionSettings) {
        this.classOrObject = ktPureClassOrObject;
        this.ownerDescriptor = classDescriptor;
        this.existingMembers = collection;
        this.trace = bindingTrace;
        this.memberExtractor = memberExtractor;
        this.typeResolver = typeResolver;
        this.delegationFilter = delegationFilter;
        this.languageVersionSettings = languageVersionSettings;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<T> generateDelegatedMembers() {
        KtTypeReference typeReference;
        KotlinType kotlinTypeResolve;
        HashSet hashSet = new HashSet();
        for (KtSuperTypeListEntry ktSuperTypeListEntry : this.classOrObject.getSuperTypeListEntries()) {
            if ((ktSuperTypeListEntry instanceof KtDelegatedSuperTypeEntry) && (typeReference = ((KtDelegatedSuperTypeEntry) ktSuperTypeListEntry).getTypeReference()) != null && (kotlinTypeResolve = this.typeResolver.resolve(typeReference)) != null && !KotlinTypeKt.isError(kotlinTypeResolve)) {
                hashSet.addAll(generateDelegatesForInterface(hashSet, kotlinTypeResolve));
            }
        }
        return hashSet;
    }

    private final Collection<T> generateDelegatesForInterface(Collection<? extends T> existingDelegates, KotlinType delegatedInterfaceType) {
        Collection<T> collectionGenerateDelegationCandidates = generateDelegationCandidates(delegatedInterfaceType);
        ArrayList arrayList = new ArrayList();
        for (Object obj : collectionGenerateDelegationCandidates) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) obj;
            if (!INSTANCE.isOverridingAnyOf(callableMemberDescriptor, this.existingMembers) && !checkClashWithOtherDelegatedMember(callableMemberDescriptor, existingDelegates)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final Collection<T> generateDelegationCandidates(KotlinType delegatedInterfaceType) {
        Modality modality;
        Collection<T> delegatableMembers = getDelegatableMembers(delegatedInterfaceType);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(delegatableMembers, 10));
        for (T t : delegatableMembers) {
            if (t.getModality() == Modality.ABSTRACT) {
                modality = Modality.OPEN;
            } else {
                modality = t.getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
            }
            CallableMemberDescriptor callableMemberDescriptorBuild = t.newCopyBuilder().setOwner(this.ownerDescriptor).setDispatchReceiverParameter(this.ownerDescriptor.getThisAsReceiverParameter()).setModality(modality).setVisibility(DescriptorVisibilities.INHERITED).setKind(CallableMemberDescriptor.Kind.DELEGATION).setCopyOverrides(false).build();
            Intrinsics.checkNotNull(callableMemberDescriptorBuild, "null cannot be cast to non-null type T of org.jetbrains.kotlin.resolve.DelegationResolver.generateDelegationCandidates$lambda$1");
            arrayList.add(callableMemberDescriptorBuild);
        }
        return arrayList;
    }

    private final boolean checkClashWithOtherDelegatedMember(T candidate, Collection<? extends T> delegatedMembers) {
        Object next;
        Iterator<T> it = delegatedMembers.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!INSTANCE.isOverridableBy((CallableMemberDescriptor) next, candidate));
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) next;
        if (callableMemberDescriptor == null) {
            return false;
        }
        if (!(this.classOrObject instanceof KtClassOrObject)) {
            return true;
        }
        BindingTrace bindingTrace = this.trace;
        DiagnosticFactory2<KtClassOrObject, KtClassOrObject, CallableMemberDescriptor> diagnosticFactory2 = Errors.MANY_IMPL_MEMBER_NOT_IMPLEMENTED;
        KtPureClassOrObject ktPureClassOrObject = this.classOrObject;
        bindingTrace.report(diagnosticFactory2.on((PsiElement) ktPureClassOrObject, (KtClassOrObject) ktPureClassOrObject, callableMemberDescriptor));
        return true;
    }

    private final Collection<T> getDelegatableMembers(KotlinType interfaceType) {
        Collection<T> membersByType = this.memberExtractor.getMembersByType(interfaceType);
        ArrayList arrayList = new ArrayList();
        for (Object obj : membersByType) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) obj;
            if (ModalityUtilsKt.isOverridable(callableMemberDescriptor) && (callableMemberDescriptor.getKind().isReal() || !overridesClassMembersOnly(callableMemberDescriptor))) {
                if (this.delegationFilter.filter(callableMemberDescriptor, this.languageVersionSettings)) {
                    arrayList.add(obj);
                }
            }
        }
        return arrayList;
    }

    private final boolean overridesClassMembersOnly(T t) {
        Set allOverriddenDeclarations = DescriptorUtils.getAllOverriddenDeclarations(t);
        Intrinsics.checkNotNullExpressionValue(allOverriddenDeclarations, "getAllOverriddenDeclarations(...)");
        Set set = allOverriddenDeclarations;
        if ((set instanceof Collection) && set.isEmpty()) {
            return true;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (!DescriptorUtils.isClass(((CallableMemberDescriptor) it.next()).getContainingModule())) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: compiled from: DelegationResolver.kt */
    @Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jb\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0001\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00042\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J.\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00182\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\fH\u0002J\u001e\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020\u00062\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\f0\u0004H\u0002¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DelegationResolver$Companion;", "", "()V", "generateDelegatedMembers", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "existingMembers", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "memberExtractor", "Lorg/jetbrains/kotlin/resolve/DelegationResolver$MemberExtractor;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/DelegationResolver$TypeResolver;", "delegationFilter", "Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getDelegates", "", "descriptor", "toInterface", "delegateExpressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "isOverridableBy", "", "memberOne", "memberTwo", "isOverridingAnyOf", "candidate", "possiblyOverriddenBy", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final <T extends CallableMemberDescriptor> Collection<T> generateDelegatedMembers(KtPureClassOrObject classOrObject, ClassDescriptor ownerDescriptor, Collection<? extends CallableDescriptor> existingMembers, BindingTrace trace, MemberExtractor<? extends T> memberExtractor, TypeResolver typeResolver, DelegationFilter delegationFilter, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
            Intrinsics.checkNotNullParameter(existingMembers, "existingMembers");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(memberExtractor, "memberExtractor");
            Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
            Intrinsics.checkNotNullParameter(delegationFilter, "delegationFilter");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            return new DelegationResolver(classOrObject, ownerDescriptor, existingMembers, trace, memberExtractor, typeResolver, delegationFilter, languageVersionSettings, null).generateDelegatedMembers();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isOverridingAnyOf(CallableMemberDescriptor candidate, Collection<? extends CallableDescriptor> possiblyOverriddenBy) {
            Collection<? extends CallableDescriptor> collection = possiblyOverriddenBy;
            if ((collection instanceof Collection) && collection.isEmpty()) {
                return false;
            }
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                if (DelegationResolver.INSTANCE.isOverridableBy((CallableDescriptor) it.next(), candidate)) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isOverridableBy(CallableDescriptor memberOne, CallableDescriptor memberTwo) {
            return OverridingUtil.DEFAULT.isOverridableBy(memberOne, memberTwo, null).getResult() == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE;
        }

        public static /* synthetic */ Map getDelegates$default(Companion companion, ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2, KotlinType kotlinType, int i, Object obj) {
            if ((i & 4) != 0) {
                kotlinType = null;
            }
            return companion.getDelegates(classDescriptor, classDescriptor2, kotlinType);
        }

        /* JADX WARN: Code duplicated, block: B:16:0x0061  */
        public final Map<CallableMemberDescriptor, CallableMemberDescriptor> getDelegates(ClassDescriptor descriptor, final ClassDescriptor toInterface, final KotlinType delegateExpressionType) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(toInterface, "toInterface");
            if (delegateExpressionType != null ? DynamicTypesKt.isDynamic(delegateExpressionType) : false) {
                return MapsKt.emptyMap();
            }
            Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(ResolutionScope.DefaultImpls.getContributedDescriptors$default(descriptor.getDefaultType().getMemberScope(), null, null, 3, null)), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DelegationResolver$Companion$getDelegates$$inlined$filterIsInstance$1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof CallableMemberDescriptor);
                }
            });
            Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
            Iterable iterableAsIterable = SequencesKt.asIterable(SequencesKt.filter(sequenceFilter, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.DelegationResolver$Companion$getDelegates$delegatedMembers$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it.getKind() == CallableMemberDescriptor.Kind.DELEGATION);
                }
            }));
            MemberComparator INSTANCE = MemberComparator.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
            List listSortedWith = CollectionsKt.sortedWith(iterableAsIterable, INSTANCE);
            if (delegateExpressionType == null) {
                SimpleType defaultType = toInterface.getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                delegateExpressionType = defaultType;
            } else {
                if (TypeUtilsKt.isNothing(delegateExpressionType)) {
                    delegateExpressionType = null;
                }
                if (delegateExpressionType == null) {
                    SimpleType defaultType2 = toInterface.getDefaultType();
                    Intrinsics.checkNotNullExpressionValue(defaultType2, "getDefaultType(...)");
                    delegateExpressionType = defaultType2;
                }
            }
            final MemberScope memberScope = delegateExpressionType.getMemberScope();
            return org.jetbrains.kotlin.utils.CollectionsKt.keysToMapExceptNulls(listSortedWith, new Function1<CallableMemberDescriptor, CallableMemberDescriptor>() { // from class: org.jetbrains.kotlin.resolve.DelegationResolver$Companion$getDelegates$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CallableMemberDescriptor invoke(CallableMemberDescriptor delegatingMember) {
                    Object next;
                    CallableMemberDescriptor callableMemberDescriptor;
                    Intrinsics.checkNotNullParameter(delegatingMember, "delegatingMember");
                    Set allOverriddenDescriptors = DescriptorUtils.getAllOverriddenDescriptors(delegatingMember);
                    Intrinsics.checkNotNullExpressionValue(allOverriddenDescriptors, "getAllOverriddenDescriptors(...)");
                    ClassDescriptor classDescriptor = toInterface;
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : allOverriddenDescriptors) {
                        if (Intrinsics.areEqual(((CallableMemberDescriptor) obj).getContainingModule(), classDescriptor)) {
                            arrayList.add(obj);
                        }
                    }
                    ArrayList<CallableMemberDescriptor> arrayList2 = arrayList;
                    KotlinType kotlinType = delegateExpressionType;
                    MemberScope memberScope2 = memberScope;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                    for (CallableMemberDescriptor callableMemberDescriptor2 : arrayList2) {
                        if (!KotlinTypeKt.isError(kotlinType)) {
                            Name name2 = callableMemberDescriptor2.getShortName();
                            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                            Iterator it = CollectionsKt.plus((Collection) memberScope2.getContributedFunctions(name2, NoLookupLocation.WHEN_CHECK_OVERRIDES), (Iterable) memberScope2.getContributedVariables(name2, NoLookupLocation.WHEN_CHECK_OVERRIDES)).iterator();
                            do {
                                if (!it.hasNext()) {
                                    next = null;
                                    break;
                                }
                                next = it.next();
                                callableMemberDescriptor = (CallableMemberDescriptor) next;
                                if (Intrinsics.areEqual(callableMemberDescriptor, callableMemberDescriptor2)) {
                                    break;
                                }
                            } while (!OverridingUtil.overrides(callableMemberDescriptor, callableMemberDescriptor2, DescriptorUtilsKt.isTypeRefinementEnabled(DescriptorUtilsKt.getModule(callableMemberDescriptor)), true));
                            callableMemberDescriptor2 = (CallableMemberDescriptor) next;
                        }
                        arrayList3.add(callableMemberDescriptor2);
                    }
                    return (CallableMemberDescriptor) CollectionsKt.firstOrNull((List) arrayList3);
                }
            });
        }
    }
}
