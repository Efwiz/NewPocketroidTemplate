package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.analysis.providers.KotlinAnnotationsResolver;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.VisitorWrappersKt;

/* JADX INFO: compiled from: KotlinStaticAnnotationsResolver.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00112\u0006\u0010\u0016\u001a\u00020\u0012H\u0016R!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticAnnotationsResolver;", "Lorg/jetbrains/kotlin/analysis/providers/KotlinAnnotationsResolver;", "ktFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "(Ljava/util/Collection;Lcom/intellij/psi/search/GlobalSearchScope;)V", "allDeclarations", "", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getAllDeclarations", "()Ljava/util/List;", "allDeclarations$delegate", "Lkotlin/Lazy;", "filesInScope", "annotationsOnDeclaration", "", "Lorg/jetbrains/kotlin/name/ClassId;", "declaration", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "declarationsByAnnotation", "queriedAnnotation", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class KotlinStaticAnnotationsResolver implements KotlinAnnotationsResolver {

    /* JADX INFO: renamed from: allDeclarations$delegate, reason: from kotlin metadata */
    private final Lazy allDeclarations;
    private final List<KtFile> filesInScope;

    public KotlinStaticAnnotationsResolver(Collection<? extends KtFile> ktFiles, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(ktFiles, "ktFiles");
        Intrinsics.checkNotNullParameter(scope, "scope");
        ArrayList arrayList = new ArrayList();
        for (Object obj : ktFiles) {
            if (scope.contains(((KtFile) obj).getVirtualFile())) {
                arrayList.add(obj);
            }
        }
        this.filesInScope = arrayList;
        this.allDeclarations = LazyKt.lazy(new Function0<List<KtDeclaration>>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticAnnotationsResolver$allDeclarations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<KtDeclaration> invoke() {
                final ArrayList arrayList2 = new ArrayList();
                KtTreeVisitorVoid ktTreeVisitorVoidDeclarationRecursiveVisitor = VisitorWrappersKt.declarationRecursiveVisitor(new Function1<KtDeclaration, Unit>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticAnnotationsResolver$allDeclarations$2$visitor$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(KtDeclaration ktDeclaration) {
                        invoke2(ktDeclaration);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(KtDeclaration it) {
                        boolean zIsLocal;
                        Intrinsics.checkNotNullParameter(it, "it");
                        if (it instanceof KtClassOrObject) {
                            zIsLocal = ((KtClassOrObject) it).isLocal();
                        } else if (it instanceof KtFunction) {
                            zIsLocal = ((KtFunction) it).isLocal();
                        } else if (!(it instanceof KtProperty)) {
                            return;
                        } else {
                            zIsLocal = ((KtProperty) it).isLocal();
                        }
                        if (zIsLocal) {
                            return;
                        }
                        arrayList2.add(it);
                    }
                });
                Iterator it = this.this$0.filesInScope.iterator();
                while (it.hasNext()) {
                    ((KtFile) it.next()).accept(ktTreeVisitorVoidDeclarationRecursiveVisitor);
                }
                return arrayList2;
            }
        });
    }

    private final List<KtDeclaration> getAllDeclarations() {
        return (List) this.allDeclarations.getValue();
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinAnnotationsResolver
    public Set<KtAnnotated> declarationsByAnnotation(final ClassId queriedAnnotation) {
        Intrinsics.checkNotNullParameter(queriedAnnotation, "queriedAnnotation");
        return SequencesKt.toSet(SequencesKt.filter(CollectionsKt.asSequence(getAllDeclarations()), new Function1<KtDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticAnnotationsResolver.declarationsByAnnotation.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(KotlinStaticAnnotationsResolver.this.annotationsOnDeclaration(it).contains(queriedAnnotation));
            }
        }));
    }

    @Override // org.jetbrains.kotlin.analysis.providers.KotlinAnnotationsResolver
    public Set<ClassId> annotationsOnDeclaration(KtAnnotated declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        List<KtAnnotationEntry> annotationEntries = declaration.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        return SequencesKt.toSet(SequencesKt.map(SequencesKt.mapNotNull(CollectionsKt.asSequence(annotationEntries), new Function1<KtAnnotationEntry, String>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticAnnotationsResolver.annotationsOnDeclaration.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(KtAnnotationEntry ktAnnotationEntry) {
                KtTypeReference typeReference = ktAnnotationEntry.getTypeReference();
                if (typeReference != null) {
                    return typeReference.getText();
                }
                return null;
            }
        }), new Function1<String, ClassId>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.KotlinStaticAnnotationsResolver.annotationsOnDeclaration.2
            @Override // kotlin.jvm.functions.Function1
            public final ClassId invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ClassId.topLevel(new FqName(it));
            }
        }));
    }
}
