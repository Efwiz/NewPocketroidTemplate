package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.naming.SuggestedName;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorEquivalenceForOverrides;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: JsNameClashChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 12\u00020\u0001:\u00011B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0002J \u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J(\u0010 \u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J,\u0010%\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00172\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\f0\u000f2\u0006\u0010#\u001a\u00020$H\u0002J,\u0010'\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\f2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\f0\u000f2\u0006\u0010#\u001a\u00020$H\u0002J,\u0010'\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020)2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\f0\u000f2\u0006\u0010#\u001a\u00020$H\u0002J$\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\f0\u000f2\u0006\u0010\u001d\u001a\u00020\f2\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010+\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u0017H\u0002J\u0010\u0010,\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\fH\u0002J\u0014\u0010-\u001a\u00020\u0012*\u00020$2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\"\u0010.\u001a\b\u0012\u0004\u0012\u0002000/*\u00020\u00032\u0006\u0010\u001d\u001a\u00020\f2\u0006\u0010#\u001a\u00020$H\u0002R \u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u000e\u001a\u001a\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000b0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0010\u001a\u001a\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\f0\u000f0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0011\u001a\u00020\u0012*\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0018\u0010\u0014\u001a\u00020\u0012*\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0013¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/AbstractNameClashChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "nameSuggestion", "Lorg/jetbrains/kotlin/js/naming/NameSuggestion;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lorg/jetbrains/kotlin/js/naming/NameSuggestion;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "clashedDescriptors", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "", "clashedFakeOverrides", "", "scopes", "isActual", "", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Z", "isExpect", "areDescriptorsEquivalent", "existing", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "overrideDescriptor", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkDescriptor", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "checkOverrideClashes", "target", "collect", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getScope", "isFakeOverridingNative", "presentsInGeneratedCode", "isCommonDiagnosticReported", "suggestAllPossibleNames", "", "Lorg/jetbrains/kotlin/js/naming/SuggestedName;", "Companion", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractNameClashChecker implements DeclarationChecker {
    private final Set<Pair<DeclarationDescriptor, String>> clashedDescriptors;
    private final Map<DeclarationDescriptor, Pair<DeclarationDescriptor, DeclarationDescriptor>> clashedFakeOverrides;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final LanguageVersionSettings languageVersionSettings;
    private final NameSuggestion nameSuggestion;
    private final Map<DeclarationDescriptor, Map<String, DeclarationDescriptor>> scopes;
    private static final Set<DiagnosticFactory1<PsiElement, ? extends Object>> COMMON_DIAGNOSTICS = SetsKt.setOf((Object[]) new DiagnosticFactory1[]{Errors.REDECLARATION, Errors.CONFLICTING_OVERLOADS, Errors.PACKAGE_OR_CLASSIFIER_REDECLARATION});

    public AbstractNameClashChecker(NameSuggestion nameSuggestion, LanguageVersionSettings languageVersionSettings, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(nameSuggestion, "nameSuggestion");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.nameSuggestion = nameSuggestion;
        this.languageVersionSettings = languageVersionSettings;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        this.scopes = new LinkedHashMap();
        this.clashedFakeOverrides = new LinkedHashMap();
        this.clashedDescriptors = new LinkedHashSet();
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (DescriptorUtilsKt.isExtensionProperty(descriptor)) {
            return;
        }
        BindingTrace trace = context.getTrace();
        BindingContext context2 = context.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        checkDescriptor(descriptor, declaration, trace, context2);
    }

    private final void checkDescriptor(DeclarationDescriptor descriptor, KtDeclaration declaration, DiagnosticSink diagnosticHolder, BindingContext bindingContext) {
        if ((descriptor instanceof ConstructorDescriptor) && ((ConstructorDescriptor) descriptor).isPrimary()) {
            return;
        }
        for (SuggestedName suggestedName : suggestAllPossibleNames(this.nameSuggestion, descriptor, bindingContext)) {
            if (suggestedName.getStable() && (suggestedName.getScope() instanceof ClassOrPackageFragmentDescriptor) && presentsInGeneratedCode(suggestedName.getDescriptor())) {
                Map<String, DeclarationDescriptor> scope = getScope(suggestedName.getScope(), bindingContext);
                String str = (String) CollectionsKt.last((List) suggestedName.getNames());
                DeclarationDescriptor declarationDescriptor = scope.get(str);
                if (declarationDescriptor != null && !Intrinsics.areEqual(declarationDescriptor, descriptor) && isActual(declarationDescriptor) == isActual(descriptor) && isExpect(declarationDescriptor) == isExpect(descriptor) && !isCommonDiagnosticReported(bindingContext, declaration)) {
                    diagnosticHolder.report(ErrorsJs.JS_NAME_CLASH.on(declaration, str, declarationDescriptor));
                    PsiElement psiElementFindPsi = SourceLocationUtilsKt.findPsi(declarationDescriptor);
                    if (this.clashedDescriptors.add(TuplesKt.to(declarationDescriptor, str)) && (psiElementFindPsi instanceof KtDeclaration) && !Intrinsics.areEqual(psiElementFindPsi, declaration)) {
                        diagnosticHolder.report(ErrorsJs.JS_NAME_CLASH.on(psiElementFindPsi, str, descriptor));
                    }
                }
            }
        }
        if (descriptor instanceof ClassDescriptor) {
            MemberScope unsubstitutedMemberScope = ((ClassDescriptor) descriptor).getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
            for (CallableMemberDescriptor callableMemberDescriptor : SequencesKt.filter(SequencesKt.mapNotNull(CollectionsKt.asSequence(ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, null, null, 3, null)), new Function1<DeclarationDescriptor, CallableMemberDescriptor>() { // from class: org.jetbrains.kotlin.js.resolve.diagnostics.AbstractNameClashChecker$checkDescriptor$fakeOverrides$1
                @Override // kotlin.jvm.functions.Function1
                public final CallableMemberDescriptor invoke(DeclarationDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof CallableMemberDescriptor) {
                        return (CallableMemberDescriptor) it;
                    }
                    return null;
                }
            }), new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.js.resolve.diagnostics.AbstractNameClashChecker$checkDescriptor$fakeOverrides$2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE);
                }
            })) {
                SuggestedName suggestedNameSuggest = this.nameSuggestion.suggest(callableMemberDescriptor, bindingContext);
                Intrinsics.checkNotNull(suggestedNameSuggest);
                Map<String, DeclarationDescriptor> scope2 = getScope(suggestedNameSuggest.getScope(), bindingContext);
                String str2 = (String) CollectionsKt.last((List) suggestedNameSuggest.getNames());
                DeclarationDescriptor declarationDescriptor2 = scope2.get(str2);
                CallableMemberDescriptor callableMemberDescriptor2 = declarationDescriptor2 instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) declarationDescriptor2 : null;
                DeclarationDescriptor descriptor2 = suggestedNameSuggest.getDescriptor();
                CallableMemberDescriptor callableMemberDescriptor3 = descriptor2 instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) descriptor2 : null;
                if (callableMemberDescriptor2 != null && callableMemberDescriptor3 != null && !areDescriptorsEquivalent(callableMemberDescriptor2, callableMemberDescriptor3) && !isFakeOverridingNative(callableMemberDescriptor2)) {
                    diagnosticHolder.report(ErrorsJs.JS_FAKE_NAME_CLASH.on(declaration, str2, callableMemberDescriptor, callableMemberDescriptor2));
                    return;
                }
                Pair<DeclarationDescriptor, DeclarationDescriptor> pair = this.clashedFakeOverrides.get(callableMemberDescriptor);
                if (pair != null) {
                    diagnosticHolder.report(ErrorsJs.JS_FAKE_NAME_CLASH.on(declaration, str2, pair.component1(), pair.component2()));
                    return;
                }
            }
        }
    }

    private final boolean areDescriptorsEquivalent(CallableMemberDescriptor existing, CallableMemberDescriptor overrideDescriptor) {
        if (this.kotlinTypeRefiner == KotlinTypeRefiner.Default.INSTANCE) {
            return Intrinsics.areEqual(existing, overrideDescriptor);
        }
        return DescriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent$default(DescriptorEquivalenceForOverrides.INSTANCE, existing, overrideDescriptor, true, false, false, this.kotlinTypeRefiner, 24, null);
    }

    private final Collection<SuggestedName> suggestAllPossibleNames(NameSuggestion nameSuggestion, DeclarationDescriptor declarationDescriptor, BindingContext bindingContext) {
        ArrayList arrayListEmptyList;
        if (declarationDescriptor instanceof CallableMemberDescriptor) {
            SuggestedName suggestedNameSuggest = nameSuggestion.suggest(declarationDescriptor, bindingContext);
            if (suggestedNameSuggest != null) {
                Collection<? extends CallableMemberDescriptor> overriddenDescriptors = ((CallableMemberDescriptor) declarationDescriptor).getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                ArrayList arrayList = new ArrayList();
                for (CallableMemberDescriptor callableMemberDescriptor : overriddenDescriptors) {
                    Intrinsics.checkNotNull(callableMemberDescriptor);
                    Collection<SuggestedName> collectionSuggestAllPossibleNames = suggestAllPossibleNames(nameSuggestion, callableMemberDescriptor, bindingContext);
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionSuggestAllPossibleNames, 10));
                    for (SuggestedName suggestedName : collectionSuggestAllPossibleNames) {
                        arrayList2.add(new SuggestedName(suggestedName.getNames(), suggestedName.getStable(), suggestedNameSuggest.getDescriptor(), suggestedNameSuggest.getScope()));
                    }
                    CollectionsKt.addAll(arrayList, arrayList2);
                }
                List listPlus = CollectionsKt.plus((Collection<? extends SuggestedName>) arrayList, suggestedNameSuggest);
                HashSet hashSet = new HashSet();
                ArrayList arrayList3 = new ArrayList();
                for (Object obj : listPlus) {
                    if (hashSet.add(((SuggestedName) obj).getNames())) {
                        arrayList3.add(obj);
                    }
                }
                arrayListEmptyList = arrayList3;
            } else {
                arrayListEmptyList = CollectionsKt.emptyList();
            }
            return arrayListEmptyList;
        }
        return CollectionsKt.listOfNotNull(nameSuggestion.suggest(declarationDescriptor, bindingContext));
    }

    private final boolean isCommonDiagnosticReported(BindingContext bindingContext, KtDeclaration ktDeclaration) {
        Collection<Diagnostic> collectionForElement = bindingContext.getDiagnostics().forElement(ktDeclaration);
        if ((collectionForElement instanceof Collection) && collectionForElement.isEmpty()) {
            return false;
        }
        Iterator<T> it = collectionForElement.iterator();
        while (it.hasNext()) {
            if (CollectionsKt.contains(COMMON_DIAGNOSTICS, ((Diagnostic) it.next()).getFactory())) {
                return true;
            }
        }
        return false;
    }

    private final boolean isActual(DeclarationDescriptor declarationDescriptor) {
        return ((declarationDescriptor instanceof MemberDescriptor) && ((MemberDescriptor) declarationDescriptor).isActual()) || ((declarationDescriptor instanceof PropertyAccessorDescriptor) && ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty().isActual());
    }

    private final boolean isExpect(DeclarationDescriptor declarationDescriptor) {
        return ((declarationDescriptor instanceof MemberDescriptor) && ((MemberDescriptor) declarationDescriptor).get$isExpect()) || ((declarationDescriptor instanceof PropertyAccessorDescriptor) && ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty().get$isExpect());
    }

    private final boolean isFakeOverridingNative(CallableMemberDescriptor descriptor) {
        if (descriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = descriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
            if (!collection.isEmpty()) {
                for (CallableMemberDescriptor callableMemberDescriptor : collection) {
                    Intrinsics.checkNotNull(callableMemberDescriptor);
                    if (presentsInGeneratedCode(callableMemberDescriptor)) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    private final Map<String, DeclarationDescriptor> getScope(DeclarationDescriptor descriptor, BindingContext bindingContext) {
        Map<DeclarationDescriptor, Map<String, DeclarationDescriptor>> map = this.scopes;
        Map<String, DeclarationDescriptor> linkedHashMap = map.get(descriptor);
        if (linkedHashMap == null) {
            linkedHashMap = new LinkedHashMap<>();
            if (descriptor instanceof PackageFragmentDescriptor) {
                PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor) descriptor;
                collect(packageFragmentDescriptor.getScope(), linkedHashMap, bindingContext);
                ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(descriptor);
                Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
                Collection<FqName> subPackagesOf = containingModule.getSubPackagesOf(packageFragmentDescriptor.getFqName(), new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.js.resolve.diagnostics.AbstractNameClashChecker$getScope$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(Name it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return true;
                    }
                });
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = subPackagesOf.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, containingModule.getPackage((FqName) it.next()).getFragments());
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    collect((PackageFragmentDescriptor) it2.next(), linkedHashMap, bindingContext);
                }
            } else if (descriptor instanceof ClassDescriptor) {
                collect(((ClassDescriptor) descriptor).getDefaultType().getMemberScope(), linkedHashMap, bindingContext);
            }
            map.put(descriptor, linkedHashMap);
        }
        return linkedHashMap;
    }

    private final void collect(MemberScope scope, Map<String, DeclarationDescriptor> target, BindingContext bindingContext) {
        Iterator it = ResolutionScope.DefaultImpls.getContributedDescriptors$default(scope, null, null, 3, null).iterator();
        while (it.hasNext()) {
            collect((DeclarationDescriptor) it.next(), target, bindingContext);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void collect(DeclarationDescriptor descriptor, Map<String, DeclarationDescriptor> target, BindingContext bindingContext) {
        if ((descriptor instanceof PropertyDescriptor) && (DescriptorUtilsKt.isExtension(descriptor) || AnnotationsUtils.hasJsNameInAccessors((PropertyDescriptor) descriptor))) {
            List<PropertyAccessorDescriptor> accessors = ((PropertyDescriptor) descriptor).getAccessors();
            Intrinsics.checkNotNullExpressionValue(accessors, "getAccessors(...)");
            for (PropertyAccessorDescriptor propertyAccessorDescriptor : accessors) {
                Intrinsics.checkNotNull(propertyAccessorDescriptor);
                collect(propertyAccessorDescriptor, target, bindingContext);
            }
            return;
        }
        for (SuggestedName suggestedName : suggestAllPossibleNames(this.nameSuggestion, descriptor, bindingContext)) {
            if (suggestedName.getStable() && presentsInGeneratedCode(suggestedName.getDescriptor())) {
                target.put(CollectionsKt.last((List) suggestedName.getNames()), suggestedName.getDescriptor());
                DeclarationDescriptor descriptor2 = suggestedName.getDescriptor();
                CallableMemberDescriptor callableMemberDescriptor = descriptor2 instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) descriptor2 : null;
                if (callableMemberDescriptor != null) {
                    checkOverrideClashes(callableMemberDescriptor, target, bindingContext);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void checkOverrideClashes(CallableMemberDescriptor descriptor, Map<String, DeclarationDescriptor> target, BindingContext bindingContext) {
        for (CallableMemberDescriptor callableMemberDescriptor : DescriptorUtils.getAllOverriddenDeclarations(descriptor)) {
            NameSuggestion nameSuggestion = this.nameSuggestion;
            Intrinsics.checkNotNull(callableMemberDescriptor);
            SuggestedName suggestedNameSuggest = nameSuggestion.suggest(callableMemberDescriptor, bindingContext);
            Intrinsics.checkNotNull(suggestedNameSuggest);
            if (suggestedNameSuggest.getStable()) {
                DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) target.get(CollectionsKt.last((List) suggestedNameSuggest.getNames()));
                if (declarationDescriptor != null) {
                    if (!Intrinsics.areEqual(declarationDescriptor, descriptor) && descriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                        this.clashedFakeOverrides.put(descriptor, new Pair<>(declarationDescriptor, callableMemberDescriptor));
                    }
                } else {
                    target.put(CollectionsKt.last((List) suggestedNameSuggest.getNames()), descriptor);
                }
            }
        }
    }

    private final boolean presentsInGeneratedCode(DeclarationDescriptor descriptor) {
        return (AnnotationsUtils.isNativeObject(descriptor) || AnnotationsUtils.isLibraryObject(descriptor)) ? false : true;
    }
}
