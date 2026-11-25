package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Substitutable;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: SubstitutingScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u0019H\u0016J\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J*\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00150\"H\u0016J#\u0010#\u001a\r\u0012\t\u0012\u00070$¢\u0006\u0002\b%0\u00072\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J#\u0010&\u001a\r\u0012\t\u0012\u00070'¢\u0006\u0002\b%0\u00072\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u000e\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00170\u0019H\u0016J\u000e\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00170\u0019H\u0016J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0016J\u001f\u0010.\u001a\u0002H/\"\b\b\u0000\u0010/*\u00020\b2\u0006\u00100\u001a\u0002H/H\u0002¢\u0006\u0002\u00101J&\u0010.\u001a\b\u0012\u0004\u0012\u0002H/0\u0007\"\b\b\u0000\u0010/*\u00020\b2\f\u00102\u001a\b\u0012\u0004\u0012\u0002H/0\u0007H\u0002J\u000e\u0010.\u001a\u0002032\u0006\u00104\u001a\u000203R!\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0010\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\f\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/SubstitutingScope;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "workerScope", "givenSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "(Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;Lorg/jetbrains/kotlin/types/TypeSubstitutor;)V", "_allDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "get_allDescriptors", "()Ljava/util/Collection;", "_allDescriptors$delegate", "Lkotlin/Lazy;", "capturingSubstitutor", "substitutedDescriptors", "", "substitutor", "getSubstitutor", "()Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "substitutor$delegate", "definitelyDoesNotContainName", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getFunctionNames", "getVariableNames", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "substitute", "D", "descriptor", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors", "Lorg/jetbrains/kotlin/types/KotlinType;", ModuleXmlParser.TYPE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubstitutingScope implements MemberScope {

    /* JADX INFO: renamed from: _allDescriptors$delegate, reason: from kotlin metadata */
    private final Lazy _allDescriptors;
    private final TypeSubstitutor capturingSubstitutor;
    private Map<DeclarationDescriptor, DeclarationDescriptor> substitutedDescriptors;

    /* JADX INFO: renamed from: substitutor$delegate, reason: from kotlin metadata */
    private final Lazy substitutor;
    private final MemberScope workerScope;

    public SubstitutingScope(MemberScope workerScope, final TypeSubstitutor givenSubstitutor) {
        Intrinsics.checkNotNullParameter(workerScope, "workerScope");
        Intrinsics.checkNotNullParameter(givenSubstitutor, "givenSubstitutor");
        this.workerScope = workerScope;
        this.substitutor = LazyKt.lazy(new Function0<TypeSubstitutor>() { // from class: org.jetbrains.kotlin.resolve.scopes.SubstitutingScope$substitutor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final TypeSubstitutor invoke() {
                return givenSubstitutor.getSubstitution().buildSubstitutor();
            }
        });
        TypeSubstitution substitution = givenSubstitutor.getSubstitution();
        Intrinsics.checkNotNullExpressionValue(substitution, "getSubstitution(...)");
        this.capturingSubstitutor = CapturedTypeConstructorKt.wrapWithCapturingSubstitution$default(substitution, false, 1, null).buildSubstitutor();
        this._allDescriptors = LazyKt.lazy(new Function0<Collection<? extends DeclarationDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.scopes.SubstitutingScope$_allDescriptors$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends DeclarationDescriptor> invoke() {
                SubstitutingScope substitutingScope = this.this$0;
                return substitutingScope.substitute(ResolutionScope.DefaultImpls.getContributedDescriptors$default(substitutingScope.workerScope, null, null, 3, null));
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation lookupLocation) {
        return MemberScope.DefaultImpls.getContributedClassifierIncludeDeprecated(this, name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation lookupLocation) {
        MemberScope.DefaultImpls.recordLookup(this, name2, lookupLocation);
    }

    public final TypeSubstitutor getSubstitutor() {
        return (TypeSubstitutor) this.substitutor.getValue();
    }

    private final Collection<DeclarationDescriptor> get_allDescriptors() {
        return (Collection) this._allDescriptors.getValue();
    }

    public final KotlinType substitute(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (this.capturingSubstitutor.isEmpty()) {
            return type;
        }
        KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = SimpleClassicTypeSystemContext.INSTANCE.safeSubstitute(this.capturingSubstitutor, type);
        Intrinsics.checkNotNull(kotlinTypeMarkerSafeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
        return (KotlinType) kotlinTypeMarkerSafeSubstitute;
    }

    private final <D extends DeclarationDescriptor> D substitute(D descriptor) {
        if (this.capturingSubstitutor.isEmpty()) {
            return descriptor;
        }
        if (this.substitutedDescriptors == null) {
            this.substitutedDescriptors = new HashMap();
        }
        Map<DeclarationDescriptor, DeclarationDescriptor> map = this.substitutedDescriptors;
        Intrinsics.checkNotNull(map);
        DeclarationDescriptorNonRoot declarationDescriptorNonRoot = map.get(descriptor);
        if (declarationDescriptorNonRoot == null) {
            if (!(descriptor instanceof Substitutable)) {
                throw new IllegalStateException(("Unknown descriptor in scope: " + descriptor).toString());
            }
            DeclarationDescriptorNonRoot declarationDescriptorNonRootSubstitute = ((Substitutable) descriptor).substitute(this.capturingSubstitutor);
            if (declarationDescriptorNonRootSubstitute != null) {
                declarationDescriptorNonRoot = declarationDescriptorNonRootSubstitute;
                map.put(descriptor, declarationDescriptorNonRoot);
            } else {
                throw new AssertionError("We expect that no conflict should happen while substitution is guaranteed to generate invariant projection, but " + descriptor + " substitution fails");
            }
        }
        D d = (D) declarationDescriptorNonRoot;
        Intrinsics.checkNotNull(d, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.scopes.SubstitutingScope.substitute");
        return d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final <D extends DeclarationDescriptor> Collection<D> substitute(Collection<? extends D> descriptors) {
        if (this.capturingSubstitutor.isEmpty() || descriptors.isEmpty()) {
            return descriptors;
        }
        LinkedHashSet linkedHashSetNewLinkedHashSetWithExpectedSize = CollectionsKt.newLinkedHashSetWithExpectedSize(descriptors.size());
        Iterator it = descriptors.iterator();
        while (it.hasNext()) {
            linkedHashSetNewLinkedHashSetWithExpectedSize.add(substitute((DeclarationDescriptor) it.next()));
        }
        return linkedHashSetNewLinkedHashSetWithExpectedSize;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<? extends PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return substitute(this.workerScope.getContributedVariables(name2, location));
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        ClassifierDescriptor contributedClassifier = this.workerScope.mo7465getContributedClassifier(name2, location);
        if (contributedClassifier != null) {
            return (ClassifierDescriptor) substitute(contributedClassifier);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return substitute(this.workerScope.getContributedFunctions(name2, location));
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return get_allDescriptors();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        return this.workerScope.getFunctionNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        return this.workerScope.getVariableNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getClassifierNames() {
        return this.workerScope.getClassifierNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.workerScope.definitelyDoesNotContainName(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(getClass().getSimpleName(), " {");
        p.pushIndent();
        p.println("substitutor = ");
        p.pushIndent();
        p.println(this.capturingSubstitutor);
        p.popIndent();
        p.print("workerScope = ");
        MemberScope memberScope = this.workerScope;
        Printer printerWithholdIndentOnce = p.withholdIndentOnce();
        Intrinsics.checkNotNullExpressionValue(printerWithholdIndentOnce, "withholdIndentOnce(...)");
        memberScope.mo6105printScopeStructure(printerWithholdIndentOnce);
        p.popIndent();
        p.println("}");
    }
}
