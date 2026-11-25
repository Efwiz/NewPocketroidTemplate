package org.jetbrains.kotlin.fir.extensions;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.common.collect.LinkedHashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate;
import org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate;
import org.jetbrains.kotlin.fir.extensions.predicate.LookupPredicate;
import org.jetbrains.kotlin.fir.extensions.predicate.PredicateVisitor;
import org.jetbrains.kotlin.fir.resolve.CallableIdUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirPredicateBasedProviderImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002 !B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u001c\u0010\u0012\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0014\u0018\u00010\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u001a\u0010\u0017\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00140\u00132\u0006\u0010\u0018\u001a\u00020\u000bH\u0016J\u001c\u0010\u0019\u001a\u00020\u000f2\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u001a2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u001e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u001eH\u0017J\u001e\u0010\u001f\u001a\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u001eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\f\u0012\u0004\u0012\u00020\t0\bR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\n\u001a\f\u0012\u0004\u0012\u00020\u000b0\bR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirPredicateBasedProviderImpl;", "Lorg/jetbrains/kotlin/fir/extensions/FirPredicateBasedProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/fir/extensions/FirPredicateBasedProviderImpl$Cache;", "declarationPredicateMatcher", "Lorg/jetbrains/kotlin/fir/extensions/FirPredicateBasedProviderImpl$Matcher;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "lookupPredicateMatcher", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate;", "registeredPluginAnnotations", "Lorg/jetbrains/kotlin/fir/extensions/FirRegisteredPluginAnnotations;", "fileHasPluginAnnotations", "", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getOwnersOfDeclaration", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getSymbolsByPredicate", "predicate", "matches", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "registerAnnotatedDeclaration", "", "owners", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "registerOwnersDeclarations", "Cache", "Matcher", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPredicateBasedProviderImpl extends FirPredicateBasedProvider {
    private final Cache cache;
    private final Matcher<DeclarationPredicate> declarationPredicateMatcher;
    private final Matcher<LookupPredicate> lookupPredicateMatcher;
    private final FirRegisteredPluginAnnotations registeredPluginAnnotations;
    private final FirSession session;

    public FirPredicateBasedProviderImpl(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.registeredPluginAnnotations = FirRegisteredPluginAnnotationsKt.getRegisteredPluginAnnotations(session);
        this.cache = new Cache();
        this.declarationPredicateMatcher = new Matcher<>();
        this.lookupPredicateMatcher = new Matcher<>();
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider
    public List<FirBasedSymbol<?>> getSymbolsByPredicate(LookupPredicate predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Set<FqName> annotations = predicate.getAnnotations();
        if (annotations.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (FqName fqName : annotations) {
            Collection<FirDeclaration> collection = this.cache.getDeclarationByAnnotation().get(fqName);
            Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
            Collection<FirDeclaration> collection2 = this.cache.getDeclarationsUnderAnnotated().get(fqName);
            Intrinsics.checkNotNullExpressionValue(collection2, "get(...)");
            CollectionsKt.addAll(linkedHashSet, CollectionsKt.plus((Collection) collection, (Iterable) collection2));
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : linkedHashSet) {
            FirDeclaration firDeclaration = (FirDeclaration) obj;
            Intrinsics.checkNotNull(firDeclaration);
            if (matches(predicate, firDeclaration)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((FirDeclaration) it.next()).getSymbol());
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider
    public boolean fileHasPluginAnnotations(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return this.cache.getFilesWithPluginAnnotations().contains(file);
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider
    @FirExtensionApiInternals
    public void registerAnnotatedDeclaration(FirDeclaration declaration, PersistentList<? extends FirDeclaration> owners) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(owners, "owners");
        this.cache.getOwnersForDeclaration().put(declaration, owners);
        registerOwnersDeclarations(declaration, owners);
        if (declaration.getAnnotations().isEmpty()) {
            return;
        }
        List<FirAnnotation> annotations = declaration.getAnnotations();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            FqName fqName = CallableIdUtilsKt.fqName((FirAnnotation) it.next(), this.session);
            if (fqName != null) {
                arrayList.add(fqName);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (this.registeredPluginAnnotations.getAnnotations().contains((FqName) obj)) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        if (arrayList3.isEmpty()) {
            arrayList3 = null;
        }
        if (arrayList3 == null) {
            return;
        }
        PersistentList<? extends FirDeclaration> persistentList = owners;
        FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) persistentList);
        if (firDeclaration != null) {
            ArrayList arrayList4 = arrayList3;
            Iterator it2 = arrayList4.iterator();
            while (it2.hasNext()) {
                this.cache.getDeclarationsHasAnnotated().put((FqName) it2.next(), firDeclaration);
            }
            this.cache.getAnnotationsOfHasAnnotated().putAll(firDeclaration, arrayList4);
        }
        ArrayList arrayList5 = arrayList3;
        Iterator it3 = arrayList5.iterator();
        while (it3.hasNext()) {
            this.cache.getDeclarationByAnnotation().put((FqName) it3.next(), declaration);
        }
        this.cache.getAnnotationsOfDeclaration().putAll(declaration, arrayList5);
        Object objFirst = CollectionsKt.first((List<? extends Object>) persistentList);
        Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirFile");
        this.cache.getFilesWithPluginAnnotations().add((FirFile) objFirst);
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider
    public List<FirBasedSymbol<?>> getOwnersOfDeclaration(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        PersistentList<FirDeclaration> persistentList = this.cache.getOwnersForDeclaration().get(declaration);
        if (persistentList == null) {
            return null;
        }
        PersistentList<FirDeclaration> persistentList2 = persistentList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(persistentList2, 10));
        Iterator<FirDeclaration> it = persistentList2.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getSymbol());
        }
        return arrayList;
    }

    private final void registerOwnersDeclarations(FirDeclaration declaration, PersistentList<? extends FirDeclaration> owners) {
        FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) owners);
        if (firDeclaration == null) {
            return;
        }
        Set set = this.cache.getAnnotationsOfDeclaration().get((Object) firDeclaration);
        Set set2 = this.cache.getAnnotationsOfUnderAnnotated().get((Object) firDeclaration);
        Intrinsics.checkNotNull(set);
        Set set3 = set;
        Iterator it = set3.iterator();
        while (it.hasNext()) {
            this.cache.getDeclarationsParentAnnotated().put((FqName) it.next(), declaration);
        }
        this.cache.getAnnotationsOfParentAnnotated().putAll(declaration, set3);
        Intrinsics.checkNotNull(set2);
        Set setPlus = SetsKt.plus(set, (Iterable) set2);
        Iterator it2 = setPlus.iterator();
        while (it2.hasNext()) {
            this.cache.getDeclarationsUnderAnnotated().put((FqName) it2.next(), declaration);
        }
        this.cache.getAnnotationsOfUnderAnnotated().putAll(declaration, setPlus);
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider
    public boolean matches(AbstractPredicate<?> predicate, FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FirSession session = declaration.getModuleData().getSession();
        if (session.getKind() == FirSession.Kind.Source && session != this.session) {
            return FirPredicateBasedProviderKt.getPredicateBasedProvider(session).matches(predicate, declaration);
        }
        if (predicate instanceof DeclarationPredicate) {
            return ((Boolean) ((DeclarationPredicate) predicate).accept(this.declarationPredicateMatcher, declaration)).booleanValue();
        }
        if (predicate instanceof LookupPredicate) {
            return ((Boolean) ((LookupPredicate) predicate).accept(this.lookupPredicateMatcher, declaration)).booleanValue();
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: compiled from: FirPredicateBasedProviderImpl.kt */
    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003B\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00052\u0010\u0010\t\u001a\f\u0012\b\u0012\u00060\u000bj\u0002`\f0\nH\u0002J\"\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00052\u0010\u0010\t\u001a\f\u0012\b\u0012\u00060\u000bj\u0002`\f0\nH\u0002J\"\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00052\u0010\u0010\t\u001a\f\u0012\b\u0012\u00060\u000bj\u0002`\f0\nH\u0002J\"\u0010\u000f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00052\u0010\u0010\t\u001a\f\u0012\b\u0012\u00060\u000bj\u0002`\f0\nH\u0002J\"\u0010\u0010\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00052\u0010\u0010\t\u001a\f\u0012\b\u0012\u00060\u000bj\u0002`\f0\nH\u0002J#\u0010\u0011\u001a\u00020\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u00132\u0006\u0010\u0014\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\u0015J#\u0010\u0016\u001a\u00020\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u00172\u0006\u0010\u0014\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\u0018J#\u0010\u0019\u001a\u00020\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u001a2\u0006\u0010\u0014\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\u001bJ#\u0010\u001c\u001a\u00020\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u001d2\u0006\u0010\u0014\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\u001eJ#\u0010\u001f\u001a\u00020\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000 2\u0006\u0010\u0014\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010!J#\u0010\"\u001a\u00020\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000#2\u0006\u0010\u0014\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010$J#\u0010%\u001a\u00020\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000&2\u0006\u0010\u0014\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010'J#\u0010(\u001a\u00020\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\u0014\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010)¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirPredicateBasedProviderImpl$Matcher;", "P", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "Lorg/jetbrains/kotlin/fir/extensions/predicate/PredicateVisitor;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "(Lorg/jetbrains/kotlin/fir/extensions/FirPredicateBasedProviderImpl;)V", "matchHasAnnotatedWith", "declaration", "annotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "matchNonIndexedDeclaration", "matchParentWith", "matchUnder", "matchWith", "visitAncestorAnnotatedWith", "predicate", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$AncestorAnnotatedWith;", "data", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$AncestorAnnotatedWith;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/lang/Boolean;", "visitAnd", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$And;", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$And;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/lang/Boolean;", "visitAnnotatedWith", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$AnnotatedWith;", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$AnnotatedWith;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/lang/Boolean;", "visitHasAnnotatedWith", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$HasAnnotatedWith;", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$HasAnnotatedWith;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/lang/Boolean;", "visitMetaAnnotatedWith", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$MetaAnnotatedWith;", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$MetaAnnotatedWith;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/lang/Boolean;", "visitOr", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Or;", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$Or;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/lang/Boolean;", "visitParentAnnotatedWith", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$ParentAnnotatedWith;", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate$ParentAnnotatedWith;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/lang/Boolean;", "visitPredicate", "(Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/lang/Boolean;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Matcher<P extends AbstractPredicate<P>> extends PredicateVisitor<P, Boolean, FirDeclaration> {
        public Matcher() {
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.PredicateVisitor
        public Boolean visitPredicate(AbstractPredicate<P> predicate, FirDeclaration data) {
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            Intrinsics.checkNotNullParameter(data, "data");
            throw new IllegalStateException("Should not be there");
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.PredicateVisitor
        public Boolean visitAnd(AbstractPredicate.And<P> predicate, FirDeclaration data) {
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            Intrinsics.checkNotNullParameter(data, "data");
            Matcher<P> matcher = this;
            return Boolean.valueOf(((Boolean) predicate.getA().accept(matcher, data)).booleanValue() && ((Boolean) predicate.getB().accept(matcher, data)).booleanValue());
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.PredicateVisitor
        public Boolean visitOr(AbstractPredicate.Or<P> predicate, FirDeclaration data) {
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            Intrinsics.checkNotNullParameter(data, "data");
            Matcher<P> matcher = this;
            return Boolean.valueOf(((Boolean) predicate.getA().accept(matcher, data)).booleanValue() || ((Boolean) predicate.getB().accept(matcher, data)).booleanValue());
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.PredicateVisitor
        public Boolean visitAnnotatedWith(AbstractPredicate.AnnotatedWith<P> predicate, FirDeclaration data) {
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            Intrinsics.checkNotNullParameter(data, "data");
            return Boolean.valueOf(matchWith(data, predicate.getAnnotations()));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.PredicateVisitor
        public Boolean visitAncestorAnnotatedWith(AbstractPredicate.AncestorAnnotatedWith<P> predicate, FirDeclaration data) {
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            Intrinsics.checkNotNullParameter(data, "data");
            return Boolean.valueOf(matchUnder(data, predicate.getAnnotations()));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.PredicateVisitor
        public Boolean visitParentAnnotatedWith(AbstractPredicate.ParentAnnotatedWith<P> predicate, FirDeclaration data) {
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            Intrinsics.checkNotNullParameter(data, "data");
            return Boolean.valueOf(matchParentWith(data, predicate.getAnnotations()));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.PredicateVisitor
        public Boolean visitHasAnnotatedWith(AbstractPredicate.HasAnnotatedWith<P> predicate, FirDeclaration data) {
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            Intrinsics.checkNotNullParameter(data, "data");
            return Boolean.valueOf(matchHasAnnotatedWith(data, predicate.getAnnotations()));
        }

        @Override // org.jetbrains.kotlin.fir.extensions.predicate.PredicateVisitor
        public Boolean visitMetaAnnotatedWith(AbstractPredicate.MetaAnnotatedWith<P> predicate, FirDeclaration data) {
            Intrinsics.checkNotNullParameter(predicate, "predicate");
            Intrinsics.checkNotNullParameter(data, "data");
            List<FirAnnotation> annotations = data.getAnnotations();
            FirPredicateBasedProviderImpl firPredicateBasedProviderImpl = FirPredicateBasedProviderImpl.this;
            boolean z = false;
            if (!(annotations instanceof Collection) || !annotations.isEmpty()) {
                Iterator<T> it = annotations.iterator();
                while (it.hasNext()) {
                    if (FirPredicateBasedProviderImplKt.markedWithMetaAnnotation((FirAnnotation) it.next(), firPredicateBasedProviderImpl.session, data, predicate.getMetaAnnotations(), predicate.getIncludeItself())) {
                        z = true;
                        break;
                    }
                }
            }
            return Boolean.valueOf(z);
        }

        private final boolean matchWith(FirDeclaration declaration, Set<FqName> annotations) {
            FirDeclarationOrigin origin = declaration.getOrigin();
            if (Intrinsics.areEqual(origin, FirDeclarationOrigin.Library.INSTANCE) ? true : origin instanceof FirDeclarationOrigin.Java) {
                return matchNonIndexedDeclaration(declaration, annotations);
            }
            boolean z = (declaration instanceof FirClass) && ((FirClassLikeDeclaration) declaration).getSymbol().getClassId().isLocal();
            if (z) {
                return matchNonIndexedDeclaration(declaration, annotations);
            }
            if (!z) {
                Set set = FirPredicateBasedProviderImpl.this.cache.getAnnotationsOfDeclaration().get((Object) declaration);
                Intrinsics.checkNotNullExpressionValue(set, "get(...)");
                Set set2 = set;
                if (!(set2 instanceof Collection) || !set2.isEmpty()) {
                    Iterator it = set2.iterator();
                    while (it.hasNext()) {
                        if (annotations.contains((FqName) it.next())) {
                            return true;
                        }
                    }
                }
                return false;
            }
            throw new NoWhenBranchMatchedException();
        }

        private final boolean matchNonIndexedDeclaration(FirDeclaration declaration, Set<FqName> annotations) {
            List<FirAnnotation> annotations2 = declaration.getAnnotations();
            FirPredicateBasedProviderImpl firPredicateBasedProviderImpl = FirPredicateBasedProviderImpl.this;
            if ((annotations2 instanceof Collection) && annotations2.isEmpty()) {
                return false;
            }
            Iterator<T> it = annotations2.iterator();
            while (it.hasNext()) {
                if (CollectionsKt.contains(annotations, CallableIdUtilsKt.fqName((FirAnnotation) it.next(), firPredicateBasedProviderImpl.session))) {
                    return true;
                }
            }
            return false;
        }

        private final boolean matchUnder(FirDeclaration declaration, Set<FqName> annotations) {
            Set set = FirPredicateBasedProviderImpl.this.cache.getAnnotationsOfUnderAnnotated().get((Object) declaration);
            Intrinsics.checkNotNullExpressionValue(set, "get(...)");
            Set set2 = set;
            if ((set2 instanceof Collection) && set2.isEmpty()) {
                return false;
            }
            Iterator it = set2.iterator();
            while (it.hasNext()) {
                if (annotations.contains((FqName) it.next())) {
                    return true;
                }
            }
            return false;
        }

        private final boolean matchParentWith(FirDeclaration declaration, Set<FqName> annotations) {
            Collection<FqName> collection = FirPredicateBasedProviderImpl.this.cache.getAnnotationsOfParentAnnotated().get(declaration);
            Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
            Collection<FqName> collection2 = collection;
            if (collection2.isEmpty()) {
                return false;
            }
            Iterator<T> it = collection2.iterator();
            while (it.hasNext()) {
                if (annotations.contains((FqName) it.next())) {
                    return true;
                }
            }
            return false;
        }

        private final boolean matchHasAnnotatedWith(FirDeclaration declaration, Set<FqName> annotations) {
            Collection<FqName> collection = FirPredicateBasedProviderImpl.this.cache.getAnnotationsOfHasAnnotated().get(declaration);
            Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
            Collection<FqName> collection2 = collection;
            if (collection2.isEmpty()) {
                return false;
            }
            Iterator<T> it = collection2.iterator();
            while (it.hasNext()) {
                if (annotations.contains((FqName) it.next())) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: compiled from: FirPredicateBasedProviderImpl.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\b\u0012\u00060\u0006j\u0002`\u00070\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR!\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\b\u0012\u00060\u0006j\u0002`\u00070\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR!\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\b\u0012\u00060\u0006j\u0002`\u00070\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR!\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\b\u0012\u00060\u0006j\u0002`\u00070\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR!\u0010\u0012\u001a\u0012\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\u00050\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR!\u0010\u0014\u001a\u0012\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\u00050\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\rR!\u0010\u0016\u001a\u0012\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\u00050\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\rR!\u0010\u0018\u001a\u0012\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\u00050\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\rR\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR#\u0010\u001f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050!0 ¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirPredicateBasedProviderImpl$Cache;", "", "()V", "annotationsOfDeclaration", "Lorg/jetbrains/kotlin/com/google/common/collect/LinkedHashMultimap;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "getAnnotationsOfDeclaration", "()Lcom/google/common/collect/LinkedHashMultimap;", "annotationsOfHasAnnotated", "Lorg/jetbrains/kotlin/com/google/common/collect/Multimap;", "getAnnotationsOfHasAnnotated", "()Lcom/google/common/collect/Multimap;", "annotationsOfParentAnnotated", "getAnnotationsOfParentAnnotated", "annotationsOfUnderAnnotated", "getAnnotationsOfUnderAnnotated", "declarationByAnnotation", "getDeclarationByAnnotation", "declarationsHasAnnotated", "getDeclarationsHasAnnotated", "declarationsParentAnnotated", "getDeclarationsParentAnnotated", "declarationsUnderAnnotated", "getDeclarationsUnderAnnotated", "filesWithPluginAnnotations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getFilesWithPluginAnnotations", "()Ljava/util/Set;", "ownersForDeclaration", "", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "getOwnersForDeclaration", "()Ljava/util/Map;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Cache {
        private final LinkedHashMultimap<FirDeclaration, FqName> annotationsOfDeclaration;
        private final Multimap<FirDeclaration, FqName> annotationsOfHasAnnotated;
        private final Multimap<FirDeclaration, FqName> annotationsOfParentAnnotated;
        private final LinkedHashMultimap<FirDeclaration, FqName> annotationsOfUnderAnnotated;
        private final Multimap<FqName, FirDeclaration> declarationByAnnotation;
        private final Multimap<FqName, FirDeclaration> declarationsHasAnnotated;
        private final Multimap<FqName, FirDeclaration> declarationsParentAnnotated;
        private final Multimap<FqName, FirDeclaration> declarationsUnderAnnotated;
        private final Set<FirFile> filesWithPluginAnnotations;
        private final Map<FirDeclaration, PersistentList<FirDeclaration>> ownersForDeclaration;

        public Cache() {
            LinkedHashMultimap linkedHashMultimapCreate = LinkedHashMultimap.create();
            Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate, "create(...)");
            this.declarationByAnnotation = linkedHashMultimapCreate;
            LinkedHashMultimap<FirDeclaration, FqName> linkedHashMultimapCreate2 = LinkedHashMultimap.create();
            Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate2, "create(...)");
            this.annotationsOfDeclaration = linkedHashMultimapCreate2;
            LinkedHashMultimap linkedHashMultimapCreate3 = LinkedHashMultimap.create();
            Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate3, "create(...)");
            this.declarationsUnderAnnotated = linkedHashMultimapCreate3;
            LinkedHashMultimap<FirDeclaration, FqName> linkedHashMultimapCreate4 = LinkedHashMultimap.create();
            Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate4, "create(...)");
            this.annotationsOfUnderAnnotated = linkedHashMultimapCreate4;
            LinkedHashMultimap linkedHashMultimapCreate5 = LinkedHashMultimap.create();
            Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate5, "create(...)");
            this.declarationsParentAnnotated = linkedHashMultimapCreate5;
            LinkedHashMultimap linkedHashMultimapCreate6 = LinkedHashMultimap.create();
            Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate6, "create(...)");
            this.annotationsOfParentAnnotated = linkedHashMultimapCreate6;
            LinkedHashMultimap linkedHashMultimapCreate7 = LinkedHashMultimap.create();
            Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate7, "create(...)");
            this.declarationsHasAnnotated = linkedHashMultimapCreate7;
            LinkedHashMultimap linkedHashMultimapCreate8 = LinkedHashMultimap.create();
            Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate8, "create(...)");
            this.annotationsOfHasAnnotated = linkedHashMultimapCreate8;
            this.ownersForDeclaration = new LinkedHashMap();
            this.filesWithPluginAnnotations = new LinkedHashSet();
        }

        public final Multimap<FqName, FirDeclaration> getDeclarationByAnnotation() {
            return this.declarationByAnnotation;
        }

        public final LinkedHashMultimap<FirDeclaration, FqName> getAnnotationsOfDeclaration() {
            return this.annotationsOfDeclaration;
        }

        public final Multimap<FqName, FirDeclaration> getDeclarationsUnderAnnotated() {
            return this.declarationsUnderAnnotated;
        }

        public final LinkedHashMultimap<FirDeclaration, FqName> getAnnotationsOfUnderAnnotated() {
            return this.annotationsOfUnderAnnotated;
        }

        public final Multimap<FqName, FirDeclaration> getDeclarationsParentAnnotated() {
            return this.declarationsParentAnnotated;
        }

        public final Multimap<FirDeclaration, FqName> getAnnotationsOfParentAnnotated() {
            return this.annotationsOfParentAnnotated;
        }

        public final Multimap<FqName, FirDeclaration> getDeclarationsHasAnnotated() {
            return this.declarationsHasAnnotated;
        }

        public final Multimap<FirDeclaration, FqName> getAnnotationsOfHasAnnotated() {
            return this.annotationsOfHasAnnotated;
        }

        public final Map<FirDeclaration, PersistentList<FirDeclaration>> getOwnersForDeclaration() {
            return this.ownersForDeclaration;
        }

        public final Set<FirFile> getFilesWithPluginAnnotations() {
            return this.filesWithPluginAnnotations;
        }
    }
}
