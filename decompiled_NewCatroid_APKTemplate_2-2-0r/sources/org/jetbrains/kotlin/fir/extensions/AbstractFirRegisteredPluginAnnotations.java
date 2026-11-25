package org.jetbrains.kotlin.fir.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate;
import org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirRegisteredPluginAnnotations.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010#\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0012\u001a\f\u0012\b\u0012\u00060\tj\u0002`\n0\b2\u0006\u0010\u0013\u001a\u00020\u0007H\u0002J\u0018\u0010\u0014\u001a\f\u0012\b\u0012\u00060\tj\u0002`\n0\b2\u0006\u0010\u0013\u001a\u00020\u0007J\b\u0010\u0015\u001a\u00020\u0016H\u0007J\u001a\u0010\u0017\u001a\u00020\u00162\u0010\u0010\u0018\u001a\f\u0012\b\u0012\u00060\tj\u0002`\n0\u0019H$R,\u0010\u0005\u001a \u0012\u0004\u0012\u00020\u0007\u0012\u000e\u0012\f\u0012\b\u0012\u00060\tj\u0002`\n0\b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\f\u0012\b\u0012\u00060\tj\u0002`\n0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/AbstractFirRegisteredPluginAnnotations;", "Lorg/jetbrains/kotlin/fir/extensions/FirRegisteredPluginAnnotations;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "annotationsForPredicateCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "", "metaAnnotations", "", "getMetaAnnotations", "()Ljava/util/Set;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "collectAnnotations", "predicate", "getAnnotationsForPredicate", "initialize", "", "saveAnnotationsFromPlugin", "annotations", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFirRegisteredPluginAnnotations extends FirRegisteredPluginAnnotations {
    private final FirCache annotationsForPredicateCache;
    private final Set<FqName> metaAnnotations;
    private final FirSession session;

    protected abstract void saveAnnotationsFromPlugin(Collection<FqName> annotations);

    public AbstractFirRegisteredPluginAnnotations(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.metaAnnotations = new LinkedHashSet();
        this.annotationsForPredicateCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.extensions.AbstractFirRegisteredPluginAnnotations$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((DeclarationPredicate) obj, (Void) obj2);
            }

            public final Set<? extends FqName> invoke(DeclarationPredicate key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.this$0.collectAnnotations(key);
            }
        });
    }

    protected final FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
    public final Set<FqName> getMetaAnnotations() {
        return this.metaAnnotations;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
    public final Set<FqName> getAnnotationsForPredicate(DeclarationPredicate predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return (Set) this.annotationsForPredicateCache.getValue(predicate, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<FqName> collectAnnotations(DeclarationPredicate predicate) {
        Set<FqName> metaAnnotations = predicate.getMetaAnnotations();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = metaAnnotations.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(linkedHashSet, getAnnotationsWithMetaAnnotation((FqName) it.next()));
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        if (linkedHashSet2.isEmpty()) {
            return predicate.getAnnotations();
        }
        CollectionsKt.addAll(linkedHashSet2, predicate.getAnnotations());
        return linkedHashSet2;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.jetbrains.kotlin.fir.extensions.AbstractFirRegisteredPluginAnnotations$initialize$registrar$1] */
    @Override // org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotations
    @PluginServicesInitialization
    public final void initialize() {
        ?? r0 = new FirDeclarationPredicateRegistrar() { // from class: org.jetbrains.kotlin.fir.extensions.AbstractFirRegisteredPluginAnnotations$initialize$registrar$1
            private final List<AbstractPredicate<?>> predicates = new ArrayList();

            public final List<AbstractPredicate<?>> getPredicates() {
                return this.predicates;
            }

            @Override // org.jetbrains.kotlin.fir.extensions.FirDeclarationPredicateRegistrar
            public void register(AbstractPredicate<?>... predicates) {
                Intrinsics.checkNotNullParameter(predicates, "predicates");
                CollectionsKt.addAll(this.predicates, predicates);
            }

            @Override // org.jetbrains.kotlin.fir.extensions.FirDeclarationPredicateRegistrar
            public void register(Collection<? extends AbstractPredicate<?>> predicates) {
                Intrinsics.checkNotNullParameter(predicates, "predicates");
                CollectionsKt.addAll(this.predicates, predicates);
            }
        };
        Iterator<FirExtension> it = FirExtensionServiceKt.getExtensionService(this.session).getAllExtensions().iterator();
        while (it.hasNext()) {
            it.next().registerPredicates((FirDeclarationPredicateRegistrar) r0);
        }
        for (AbstractPredicate<?> abstractPredicate : r0.getPredicates()) {
            saveAnnotationsFromPlugin(abstractPredicate.getAnnotations());
            CollectionsKt.addAll(this.metaAnnotations, abstractPredicate.getMetaAnnotations());
        }
    }
}
