package org.jetbrains.kotlin.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.common.collect.HashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.resolve.lazy.FileScopeProvider;
import org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: DeclarationResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\"\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fJ&\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u001c\u0010\u0018\u001a\u00020\b2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00140\u000fH\u0002J\u0016\u0010\u001b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001dR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DeclarationResolver;", "", "annotationResolver", "Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "(Lorg/jetbrains/kotlin/resolve/AnnotationResolver;Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "checkRedeclarations", "", "c", "Lorg/jetbrains/kotlin/resolve/TopDownAnalysisContext;", "checkRedeclarationsInPackages", "topLevelDescriptorProvider", "Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;", "topLevelFqNames", "Lorg/jetbrains/kotlin/com/google/common/collect/Multimap;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/psi/KtElement;", "getTopLevelDescriptorsByFqName", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "fqName", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "reportRedeclarationsWithClassifiers", "descriptorMap", "Lorg/jetbrains/kotlin/name/Name;", "resolveAnnotationsOnFiles", "scopeProvider", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeProvider;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationResolver {
    private final AnnotationResolver annotationResolver;
    private final BindingTrace trace;

    public DeclarationResolver(AnnotationResolver annotationResolver, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(annotationResolver, "annotationResolver");
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.annotationResolver = annotationResolver;
        this.trace = trace;
    }

    public final void resolveAnnotationsOnFiles(TopDownAnalysisContext c, final FileScopeProvider scopeProvider) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(scopeProvider, "scopeProvider");
        Collection<KtFile> files = c.getFiles();
        Intrinsics.checkNotNullExpressionValue(files, "getFiles(...)");
        for (Map.Entry entry : CollectionsKt.keysToMap(files, new Function1<KtFile, LexicalScope>() { // from class: org.jetbrains.kotlin.resolve.DeclarationResolver$resolveAnnotationsOnFiles$filesToScope$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final LexicalScope invoke(KtFile ktFile) {
                FileScopeProvider fileScopeProvider = scopeProvider;
                Intrinsics.checkNotNull(ktFile);
                return fileScopeProvider.getFileResolutionScope(ktFile);
            }
        }).entrySet()) {
            KtFile ktFile = (KtFile) entry.getKey();
            LexicalScope lexicalScope = (LexicalScope) entry.getValue();
            this.annotationResolver.resolveAnnotationsWithArguments(lexicalScope, ktFile.getAnnotationEntries(), this.trace);
            this.annotationResolver.resolveAnnotationsWithArguments(lexicalScope, ktFile.getDanglingAnnotations(), this.trace);
        }
    }

    public final void checkRedeclarations(TopDownAnalysisContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        for (ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes : c.getDeclaredClasses().values()) {
            Multimap<Name, DeclarationDescriptor> multimapCreate = HashMultimap.create();
            MemberScope unsubstitutedMemberScope = classDescriptorWithResolutionScopes.getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
            for (DeclarationDescriptor declarationDescriptor : ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, null, null, 3, null)) {
                if ((declarationDescriptor instanceof ClassifierDescriptor) || (declarationDescriptor instanceof PropertyDescriptor)) {
                    multimapCreate.put(declarationDescriptor.getShortName(), declarationDescriptor);
                }
            }
            Intrinsics.checkNotNull(multimapCreate);
            reportRedeclarationsWithClassifiers(multimapCreate);
        }
    }

    private final void reportRedeclarationsWithClassifiers(Multimap<Name, DeclarationDescriptor> descriptorMap) {
        Iterator<Name> it = descriptorMap.keySet().iterator();
        while (it.hasNext()) {
            Collection<DeclarationDescriptor> collection = descriptorMap.get(it.next());
            if (collection.size() > 1) {
                Intrinsics.checkNotNull(collection);
                Collection<DeclarationDescriptor> collection2 = collection;
                if (!collection2.isEmpty()) {
                    Iterator<T> it2 = collection2.iterator();
                    while (it2.hasNext()) {
                        if (((DeclarationDescriptor) it2.next()) instanceof ClassifierDescriptor) {
                            for (DeclarationDescriptor declarationDescriptor : collection) {
                                BindingTrace bindingTrace = this.trace;
                                Intrinsics.checkNotNull(declarationDescriptor);
                                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptor);
                                if (psiElementDescriptorToDeclaration != null) {
                                    Diagnostic diagnosticOn = Errors.REDECLARATION.on(psiElementDescriptorToDeclaration, collection);
                                    Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                                    bindingTrace.report(diagnosticOn);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    public final void checkRedeclarationsInPackages(TopLevelDescriptorProvider topLevelDescriptorProvider, Multimap<FqName, KtElement> topLevelFqNames) {
        KtElement nameIdentifier;
        Intrinsics.checkNotNullParameter(topLevelDescriptorProvider, "topLevelDescriptorProvider");
        Intrinsics.checkNotNullParameter(topLevelFqNames, "topLevelFqNames");
        Map<FqName, Collection<KtElement>> mapAsMap = topLevelFqNames.asMap();
        Intrinsics.checkNotNullExpressionValue(mapAsMap, "asMap(...)");
        for (Map.Entry<FqName, Collection<KtElement>> entry : mapAsMap.entrySet()) {
            FqName key = entry.getKey();
            Collection<KtElement> value = entry.getValue();
            if (!key.isRoot()) {
                Intrinsics.checkNotNull(key);
                Set<DeclarationDescriptor> topLevelDescriptorsByFqName = getTopLevelDescriptorsByFqName(topLevelDescriptorProvider, key, NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS);
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : topLevelDescriptorsByFqName) {
                    DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj;
                    if ((declarationDescriptor instanceof MemberDescriptor) && ((MemberDescriptor) declarationDescriptor).get$isExpect()) {
                        arrayList.add(obj);
                    } else {
                        arrayList2.add(obj);
                    }
                }
                Pair pair = new Pair(arrayList, arrayList2);
                Iterator it = kotlin.collections.CollectionsKt.listOf((Object[]) new List[]{(List) pair.component1(), (List) pair.component2()}).iterator();
                while (it.hasNext()) {
                    if (((List) it.next()).size() > 1) {
                        for (KtElement ktElement : value) {
                            KtPackageDirective ktPackageDirective = ktElement instanceof KtPackageDirective ? (KtPackageDirective) ktElement : null;
                            if (ktPackageDirective == null || (nameIdentifier = ktPackageDirective.getNameIdentifier()) == null) {
                                nameIdentifier = ktElement;
                            }
                            this.trace.report(Errors.PACKAGE_OR_CLASSIFIER_REDECLARATION.on(nameIdentifier, key.shortName().asString()));
                        }
                    }
                }
            }
        }
    }

    private final Set<DeclarationDescriptor> getTopLevelDescriptorsByFqName(TopLevelDescriptorProvider topLevelDescriptorProvider, FqName fqName, LookupLocation location) {
        HashSet hashSet = new HashSet();
        CollectionsKt.addIfNotNull(hashSet, topLevelDescriptorProvider.getPackageFragment(fqName));
        hashSet.addAll(topLevelDescriptorProvider.getTopLevelClassifierDescriptors(fqName, location));
        return hashSet;
    }
}
