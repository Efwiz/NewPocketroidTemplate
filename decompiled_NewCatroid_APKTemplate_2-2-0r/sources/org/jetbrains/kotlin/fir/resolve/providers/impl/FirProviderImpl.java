package org.jetbrains.kotlin.fir.resolve.providers.impl;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirNameConflictsTrackerComponent;
import org.jetbrains.kotlin.fir.FirNameConflictsTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirProviderImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0004,-./B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u00152\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0016J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020!H\u0016J\u0012\u0010#\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020!H\u0016J\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0012\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010'\u001a\u00020(H\u0016J\u0012\u0010)\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u001c\u001a\u00020&H\u0016J\u000e\u0010*\u001a\u00020\u00122\u0006\u0010+\u001a\u00020\u0015J\u0018\u0010*\u001a\u00020\u00122\u0006\u0010+\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;)V", "getKotlinScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "state", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl$State;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getSymbolProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "ensureConsistent", "", "files", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getClassNamesInPackage", "", "Lorg/jetbrains/kotlin/name/Name;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFirCallableContainerFile", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getFirClassifierByFqName", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getFirClassifierContainerFile", "getFirClassifierContainerFileIfAny", "getFirFilesByPackage", "getFirScriptByFilePath", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "path", "", "getFirScriptContainerFile", "recordFile", "file", "FirRecorder", "FirRecorderData", "State", "SymbolProvider", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirProviderImpl extends FirProvider {
    private final FirKotlinScopeProvider kotlinScopeProvider;
    private final FirSession session;
    private final State state;
    private final FirSymbolProvider symbolProvider;

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirFile getFirClassifierContainerFile(ClassId fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        FirFile firFile = this.state.getClassifierContainerFileMap().get(fqName);
        if (firFile != null) {
            return firFile;
        }
        throw new IllegalStateException(("Couldn't find container for " + fqName).toString());
    }

    public FirProviderImpl(FirSession session, FirKotlinScopeProvider kotlinScopeProvider) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        this.session = session;
        this.kotlinScopeProvider = kotlinScopeProvider;
        this.symbolProvider = new SymbolProvider();
        this.state = new State();
    }

    public final FirKotlinScopeProvider getKotlinScopeProvider() {
        return this.kotlinScopeProvider;
    }

    public final FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirSymbolProvider getSymbolProvider() {
        return this.symbolProvider;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirFile getFirScriptContainerFile(FirScriptSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this.state.getScriptContainerMap().get(symbol);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirScriptSymbol getFirScriptByFilePath(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return this.state.getScriptByFilePathMap().get(path);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirFile getFirClassifierContainerFileIfAny(ClassId fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.state.getClassifierContainerFileMap().get(fqName);
    }

    public final void recordFile(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        recordFile(file, this.state);
    }

    /* JADX INFO: compiled from: FirProviderImpl.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\fH\u0016J*\u0010\u000e\u001a\u00020\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\u00112\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0017J&\u0010\u0016\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00170\u00112\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0017J&\u0010\u0018\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00190\u00112\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0017R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl$SymbolProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "(Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl;)V", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getClassLikeSymbolByClassId", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getPackage", "Lorg/jetbrains/kotlin/name/FqName;", "fqName", "getTopLevelCallableSymbolsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "packageFqName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelFunctionSymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getTopLevelPropertySymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class SymbolProvider extends FirSymbolProvider {
        private final FirSymbolNamesProvider symbolNamesProvider;

        public SymbolProvider() {
            super(FirProviderImpl.this.getSession());
            this.symbolNamesProvider = new FirSymbolNamesProvider() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirProviderImpl$SymbolProvider$symbolNamesProvider$1
                @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
                public Set<String> getPackageNamesWithTopLevelCallables() {
                    Set<FqName> allSubPackages = firProviderImpl.state.getAllSubPackages();
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    Iterator<T> it = allSubPackages.iterator();
                    while (it.hasNext()) {
                        linkedHashSet.add(((FqName) it.next()).asString());
                    }
                    return linkedHashSet;
                }

                @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
                public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
                    Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                    Set<Name> setEmptySet = firProviderImpl.state.getClassifierInPackage().get(packageFqName);
                    if (setEmptySet == null) {
                        setEmptySet = SetsKt.emptySet();
                    }
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    Iterator<T> it = setEmptySet.iterator();
                    while (it.hasNext()) {
                        linkedHashSet.add(((Name) it.next()).asString());
                    }
                    return linkedHashSet;
                }

                @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
                public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
                    Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                    FirProviderImpl firProviderImpl = firProviderImpl;
                    Set setCreateSetBuilder = SetsKt.createSetBuilder();
                    for (CallableId callableId : firProviderImpl.state.getFunctionMap().keySet()) {
                        if (Intrinsics.areEqual(callableId.getPackageName(), packageFqName)) {
                            setCreateSetBuilder.add(callableId.getCallableName());
                        }
                    }
                    for (CallableId callableId2 : firProviderImpl.state.getPropertyMap().keySet()) {
                        if (Intrinsics.areEqual(callableId2.getPackageName(), packageFqName)) {
                            setCreateSetBuilder.add(callableId2.getCallableName());
                        }
                    }
                    return SetsKt.build(setCreateSetBuilder);
                }
            };
        }

        @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
        public FirClassLikeSymbol<?> getClassLikeSymbolByClassId(ClassId classId) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            FirClassLikeDeclaration firClassifierByFqName = FirProviderImpl.this.getFirClassifierByFqName(classId);
            if (firClassifierByFqName != null) {
                return firClassifierByFqName.getSymbol();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
        @FirSymbolProviderInternals
        public void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2) {
            Intrinsics.checkNotNullParameter(destination, "destination");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(name2, "name");
            List<FirCallableSymbol<?>> list = destination;
            List<FirNamedFunctionSymbol> listEmptyList = FirProviderImpl.this.state.getFunctionMap().get(new CallableId(packageFqName, null, name2, null, 8, null));
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(list, listEmptyList);
            List<FirPropertySymbol> listEmptyList2 = FirProviderImpl.this.state.getPropertyMap().get(new CallableId(packageFqName, null, name2, null, 8, null));
            if (listEmptyList2 == null) {
                listEmptyList2 = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(list, listEmptyList2);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
        @FirSymbolProviderInternals
        public void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
            Intrinsics.checkNotNullParameter(destination, "destination");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(name2, "name");
            List<FirNamedFunctionSymbol> list = destination;
            List<FirNamedFunctionSymbol> listEmptyList = FirProviderImpl.this.state.getFunctionMap().get(new CallableId(packageFqName, null, name2, null, 8, null));
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(list, listEmptyList);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
        @FirSymbolProviderInternals
        public void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2) {
            Intrinsics.checkNotNullParameter(destination, "destination");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(name2, "name");
            List<FirPropertySymbol> list = destination;
            List<FirPropertySymbol> listEmptyList = FirProviderImpl.this.state.getPropertyMap().get(new CallableId(packageFqName, null, name2, null, 8, null));
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(list, listEmptyList);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
        public FqName getPackage(FqName fqName) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            if (FirProviderImpl.this.state.getAllSubPackages().contains(fqName)) {
                return fqName;
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
        public FirSymbolNamesProvider getSymbolNamesProvider() {
            return this.symbolNamesProvider;
        }
    }

    private final void recordFile(FirFile file, State state) {
        FqName packageFqName = UtilsKt.getPackageFqName(file);
        Map<FqName, List<FirFile>> fileMap = state.getFileMap();
        List<FirFile> listListOf = CollectionsKt.listOf(file);
        final AnonymousClass1 anonymousClass1 = new Function2<List<? extends FirFile>, List<? extends FirFile>, List<? extends FirFile>>() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirProviderImpl.recordFile.1
            @Override // kotlin.jvm.functions.Function2
            public final List<FirFile> invoke(List<? extends FirFile> a, List<? extends FirFile> b) {
                Intrinsics.checkNotNullParameter(a, "a");
                Intrinsics.checkNotNullParameter(b, "b");
                return CollectionsKt.plus((Collection) a, (Iterable) b);
            }
        };
        fileMap.merge(packageFqName, listListOf, new BiFunction() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirProviderImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return FirProviderImpl.recordFile$lambda$1(anonymousClass1, obj, obj2);
            }
        });
        Sequence sequenceGenerateSequence = SequencesKt.generateSequence(packageFqName, new Function1<FqName, FqName>() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirProviderImpl.recordFile.2
            @Override // kotlin.jvm.functions.Function1
            public final FqName invoke(FqName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FqNamesUtilKt.parentOrNull(it);
            }
        });
        Set<FqName> allSubPackages = state.getAllSubPackages();
        Iterator it = sequenceGenerateSequence.iterator();
        while (it.hasNext()) {
            allSubPackages.add((FqName) it.next());
        }
        file.acceptChildren(FirRecorder.INSTANCE, new FirRecorderData(state, file, FirNameConflictsTrackerComponentKt.getNameConflictsTracker(this.session)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List recordFile$lambda$1(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (List) tmp0.invoke(obj, obj2);
    }

    /* JADX INFO: compiled from: FirProviderImpl.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl$FirRecorderData;", "", "state", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl$State;", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "nameConflictsTracker", "Lorg/jetbrains/kotlin/fir/FirNameConflictsTrackerComponent;", "(Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl$State;Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lorg/jetbrains/kotlin/fir/FirNameConflictsTrackerComponent;)V", "getFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getNameConflictsTracker", "()Lorg/jetbrains/kotlin/fir/FirNameConflictsTrackerComponent;", "getState", "()Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl$State;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class FirRecorderData {
        private final FirFile file;
        private final FirNameConflictsTrackerComponent nameConflictsTracker;
        private final State state;

        public FirRecorderData(State state, FirFile file, FirNameConflictsTrackerComponent firNameConflictsTrackerComponent) {
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(file, "file");
            this.state = state;
            this.file = file;
            this.nameConflictsTracker = firNameConflictsTrackerComponent;
        }

        public final State getState() {
            return this.state;
        }

        public final FirFile getFile() {
            return this.file;
        }

        public final FirNameConflictsTrackerComponent getNameConflictsTracker() {
            return this.nameConflictsTracker;
        }
    }

    /* JADX INFO: compiled from: FirProviderImpl.kt */
    @Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004JT\u0010\u0005\u001a\u00020\u0002\"\n\b\u0000\u0010\u0006\u0018\u0001*\u00020\u0007\"\u000e\b\u0001\u0010\b*\b\u0012\u0004\u0012\u0002H\u00060\t2\u0006\u0010\n\u001a\u0002H\b2\u0006\u0010\u000b\u001a\u00020\u00032\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u000f0\rH\u0082\b¢\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010 \u001a\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020%2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010&\u001a\u00020\u00022\u0006\u0010'\u001a\u00020(2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0018\u0010)\u001a\u00020\u00022\u0006\u0010*\u001a\u00020+2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl$FirRecorder;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl$FirRecorderData;", "()V", "registerCallable", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", NativeSymbol.TYPE_NAME, "data", "map", "", "Lorg/jetbrains/kotlin/name/CallableId;", "", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl$FirRecorderData;Ljava/util/Map;)V", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "visitProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "visitPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitScript", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class FirRecorder extends FirDefaultVisitor<Unit, FirRecorderData> {
        public static final FirRecorder INSTANCE = new FirRecorder();

        public void visitElement(FirElement element, FirRecorderData data) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(data, "data");
        }

        private FirRecorder() {
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitConstructor(FirConstructor firConstructor, Object obj) {
            visitConstructor(firConstructor, (FirRecorderData) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitElement(FirElement firElement, Object obj) {
            visitElement(firElement, (FirRecorderData) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitEnumEntry(FirEnumEntry firEnumEntry, Object obj) {
            visitEnumEntry(firEnumEntry, (FirRecorderData) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitProperty(FirProperty firProperty, Object obj) {
            visitProperty(firProperty, (FirRecorderData) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitPropertyAccessor(FirPropertyAccessor firPropertyAccessor, Object obj) {
            visitPropertyAccessor(firPropertyAccessor, (FirRecorderData) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitRegularClass(FirRegularClass firRegularClass, Object obj) {
            visitRegularClass(firRegularClass, (FirRecorderData) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitScript(FirScript firScript, Object obj) {
            visitScript(firScript, (FirRecorderData) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitSimpleFunction(FirSimpleFunction firSimpleFunction, Object obj) {
            visitSimpleFunction(firSimpleFunction, (FirRecorderData) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitTypeAlias(FirTypeAlias firTypeAlias, Object obj) {
            visitTypeAlias(firTypeAlias, (FirRecorderData) obj);
            return Unit.INSTANCE;
        }

        public void visitRegularClass(FirRegularClass regularClass, FirRecorderData data) {
            FirNameConflictsTrackerComponent nameConflictsTracker;
            Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            Intrinsics.checkNotNullParameter(data, "data");
            ClassId classId = regularClass.getSymbol().getClassId();
            FirFile firFilePut = data.getState().getClassifierContainerFileMap().put(classId, data.getFile());
            FirClassLikeDeclaration firClassLikeDeclarationPut = data.getState().getClassifierMap().put(classId, regularClass);
            if (firClassLikeDeclarationPut != null && (nameConflictsTracker = data.getNameConflictsTracker()) != null) {
                nameConflictsTracker.registerClassifierRedeclaration(classId, regularClass.getSymbol(), data.getFile(), firClassLikeDeclarationPut.getSymbol(), firFilePut);
            }
            if (!classId.isNestedClass() && !classId.isLocal()) {
                Map<FqName, Set<Name>> classesInPackage = data.getState().getClassesInPackage();
                FqName packageFqName = classId.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                LinkedHashSet linkedHashSet = classesInPackage.get(packageFqName);
                if (linkedHashSet == null) {
                    linkedHashSet = new LinkedHashSet();
                    classesInPackage.put(packageFqName, linkedHashSet);
                }
                Name shortClassName = classId.getShortClassName();
                Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                linkedHashSet.add(shortClassName);
                Map<FqName, Set<Name>> classifierInPackage = data.getState().getClassifierInPackage();
                FqName packageFqName2 = classId.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName2, "getPackageFqName(...)");
                LinkedHashSet linkedHashSet2 = classifierInPackage.get(packageFqName2);
                if (linkedHashSet2 == null) {
                    linkedHashSet2 = new LinkedHashSet();
                    classifierInPackage.put(packageFqName2, linkedHashSet2);
                }
                Name shortClassName2 = classId.getShortClassName();
                Intrinsics.checkNotNullExpressionValue(shortClassName2, "getShortClassName(...)");
                linkedHashSet2.add(shortClassName2);
            }
            regularClass.acceptChildren(this, data);
        }

        public void visitTypeAlias(FirTypeAlias typeAlias, FirRecorderData data) {
            FirNameConflictsTrackerComponent nameConflictsTracker;
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            Intrinsics.checkNotNullParameter(data, "data");
            ClassId classId = typeAlias.getSymbol().getClassId();
            FirFile firFilePut = data.getState().getClassifierContainerFileMap().put(classId, data.getFile());
            FirClassLikeDeclaration firClassLikeDeclarationPut = data.getState().getClassifierMap().put(classId, typeAlias);
            if (firClassLikeDeclarationPut != null && (nameConflictsTracker = data.getNameConflictsTracker()) != null) {
                nameConflictsTracker.registerClassifierRedeclaration(classId, typeAlias.getSymbol(), data.getFile(), firClassLikeDeclarationPut.getSymbol(), firFilePut);
            }
            Map<FqName, Set<Name>> classifierInPackage = data.getState().getClassifierInPackage();
            FqName packageFqName = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            LinkedHashSet linkedHashSet = classifierInPackage.get(packageFqName);
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet();
                classifierInPackage.put(packageFqName, linkedHashSet);
            }
            Name shortClassName = classId.getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            linkedHashSet.add(shortClassName);
        }

        public void visitPropertyAccessor(FirPropertyAccessor propertyAccessor, FirRecorderData data) {
            Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
            Intrinsics.checkNotNullParameter(data, "data");
            data.getState().getCallableContainerMap().put(propertyAccessor.getSymbol(), data.getFile());
        }

        public void visitConstructor(FirConstructor constructor, FirRecorderData data) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Intrinsics.checkNotNullParameter(data, "data");
            FirConstructorSymbol symbol = constructor.getSymbol();
            data.getState().getConstructorMap().merge(symbol.getCallableId(), CollectionsKt.listOf(symbol), new FirProviderImplKt$sam$java_util_function_BiFunction$0(FirProviderImpl$FirRecorder$registerCallable$1.INSTANCE));
            data.getState().getCallableContainerMap().put(symbol, data.getFile());
        }

        public void visitSimpleFunction(FirSimpleFunction simpleFunction, FirRecorderData data) {
            Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
            Intrinsics.checkNotNullParameter(data, "data");
            FirNamedFunctionSymbol symbol = simpleFunction.getSymbol();
            data.getState().getFunctionMap().merge(symbol.getCallableId(), CollectionsKt.listOf(symbol), new FirProviderImplKt$sam$java_util_function_BiFunction$0(FirProviderImpl$FirRecorder$registerCallable$1.INSTANCE));
            data.getState().getCallableContainerMap().put(symbol, data.getFile());
        }

        public void visitProperty(FirProperty property, FirRecorderData data) {
            Intrinsics.checkNotNullParameter(property, "property");
            Intrinsics.checkNotNullParameter(data, "data");
            FirPropertySymbol symbol = property.getSymbol();
            data.getState().getPropertyMap().merge(symbol.getCallableId(), CollectionsKt.listOf(symbol), new FirProviderImplKt$sam$java_util_function_BiFunction$0(FirProviderImpl$FirRecorder$registerCallable$1.INSTANCE));
            data.getState().getCallableContainerMap().put(symbol, data.getFile());
            FirPropertyAccessor getter = property.getGetter();
            if (getter != null) {
                INSTANCE.visitPropertyAccessor(getter, data);
            }
            FirPropertyAccessor setter = property.getSetter();
            if (setter != null) {
                INSTANCE.visitPropertyAccessor(setter, data);
            }
        }

        public void visitEnumEntry(FirEnumEntry enumEntry, FirRecorderData data) {
            Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
            Intrinsics.checkNotNullParameter(data, "data");
            data.getState().getCallableContainerMap().put(enumEntry.getSymbol(), data.getFile());
        }

        public void visitScript(FirScript script, FirRecorderData data) {
            String path;
            Intrinsics.checkNotNullParameter(script, "script");
            Intrinsics.checkNotNullParameter(data, "data");
            FirScriptSymbol symbol = script.getSymbol();
            data.getState().getScriptContainerMap().put(symbol, data.getFile());
            KtSourceFile sourceFile = data.getFile().getSourceFile();
            if (sourceFile != null && (path = sourceFile.getPath()) != null) {
                data.getState().getScriptByFilePathMap().put(path, symbol);
            }
            script.acceptChildren(this, data);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FirProviderImpl.kt */
    @Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u0000R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R!\u0010\b\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR#\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00040\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u001d\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR#\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00040\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\rR\u001d\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00170\t¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\rR#\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001b0\t¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\rR#\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u001b0\t¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\rR#\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\u001b0\t¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\rR#\u0010#\u001a\u0014\u0012\u0004\u0012\u00020\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020$0\u001b0\t¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\rR\u001d\u0010&\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020(0\t¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\rR\u001d\u0010*\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\b\n\u0000\u001a\u0004\b+\u0010\r¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirProviderImpl$State;", "", "()V", "allSubPackages", "", "Lorg/jetbrains/kotlin/name/FqName;", "getAllSubPackages", "()Ljava/util/Set;", "callableContainerMap", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getCallableContainerMap", "()Ljava/util/Map;", "classesInPackage", "Lorg/jetbrains/kotlin/name/Name;", "getClassesInPackage", "classifierContainerFileMap", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassifierContainerFileMap", "classifierInPackage", "getClassifierInPackage", "classifierMap", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "getClassifierMap", "constructorMap", "Lorg/jetbrains/kotlin/name/CallableId;", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "getConstructorMap", "fileMap", "getFileMap", "functionMap", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getFunctionMap", "propertyMap", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getPropertyMap", "scriptByFilePathMap", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "getScriptByFilePathMap", "scriptContainerMap", "getScriptContainerMap", "setFrom", "", "other", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class State {
        private final Map<FqName, List<FirFile>> fileMap = new LinkedHashMap();
        private final Set<FqName> allSubPackages = new LinkedHashSet();
        private final Map<ClassId, FirClassLikeDeclaration> classifierMap = new LinkedHashMap();
        private final Map<ClassId, FirFile> classifierContainerFileMap = new LinkedHashMap();
        private final Map<FqName, Set<Name>> classifierInPackage = new LinkedHashMap();
        private final Map<FqName, Set<Name>> classesInPackage = new LinkedHashMap();
        private final Map<CallableId, List<FirNamedFunctionSymbol>> functionMap = new LinkedHashMap();
        private final Map<CallableId, List<FirPropertySymbol>> propertyMap = new LinkedHashMap();
        private final Map<CallableId, List<FirConstructorSymbol>> constructorMap = new LinkedHashMap();
        private final Map<FirCallableSymbol<?>, FirFile> callableContainerMap = new LinkedHashMap();
        private final Map<FirScriptSymbol, FirFile> scriptContainerMap = new LinkedHashMap();
        private final Map<String, FirScriptSymbol> scriptByFilePathMap = new LinkedHashMap();

        public final Map<FqName, List<FirFile>> getFileMap() {
            return this.fileMap;
        }

        public final Set<FqName> getAllSubPackages() {
            return this.allSubPackages;
        }

        public final Map<ClassId, FirClassLikeDeclaration> getClassifierMap() {
            return this.classifierMap;
        }

        public final Map<ClassId, FirFile> getClassifierContainerFileMap() {
            return this.classifierContainerFileMap;
        }

        public final Map<FqName, Set<Name>> getClassifierInPackage() {
            return this.classifierInPackage;
        }

        public final Map<FqName, Set<Name>> getClassesInPackage() {
            return this.classesInPackage;
        }

        public final Map<CallableId, List<FirNamedFunctionSymbol>> getFunctionMap() {
            return this.functionMap;
        }

        public final Map<CallableId, List<FirPropertySymbol>> getPropertyMap() {
            return this.propertyMap;
        }

        public final Map<CallableId, List<FirConstructorSymbol>> getConstructorMap() {
            return this.constructorMap;
        }

        public final Map<FirCallableSymbol<?>, FirFile> getCallableContainerMap() {
            return this.callableContainerMap;
        }

        public final Map<FirScriptSymbol, FirFile> getScriptContainerMap() {
            return this.scriptContainerMap;
        }

        public final Map<String, FirScriptSymbol> getScriptByFilePathMap() {
            return this.scriptByFilePathMap;
        }

        public final void setFrom(State other) {
            Intrinsics.checkNotNullParameter(other, "other");
            this.fileMap.clear();
            this.allSubPackages.clear();
            this.classifierMap.clear();
            this.classifierContainerFileMap.clear();
            this.functionMap.clear();
            this.propertyMap.clear();
            this.constructorMap.clear();
            this.callableContainerMap.clear();
            this.scriptContainerMap.clear();
            this.scriptByFilePathMap.clear();
            this.fileMap.putAll(other.fileMap);
            this.allSubPackages.addAll(other.allSubPackages);
            this.classifierMap.putAll(other.classifierMap);
            this.classifierContainerFileMap.putAll(other.classifierContainerFileMap);
            this.functionMap.putAll(other.functionMap);
            this.propertyMap.putAll(other.propertyMap);
            this.constructorMap.putAll(other.constructorMap);
            this.callableContainerMap.putAll(other.callableContainerMap);
            this.scriptContainerMap.putAll(other.scriptContainerMap);
            this.scriptByFilePathMap.putAll(other.scriptByFilePathMap);
            this.classesInPackage.putAll(other.classesInPackage);
            this.classifierInPackage.putAll(other.classifierInPackage);
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public List<FirFile> getFirFilesByPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        List<FirFile> list = this.state.getFileMap().get(fqName);
        return list == null ? CollectionsKt.emptyList() : list;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirClassLikeDeclaration getFirClassifierByFqName(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (classId.isLocal()) {
            throw new IllegalArgumentException(("Local " + classId + " should never be used to find its corresponding classifier").toString());
        }
        return this.state.getClassifierMap().get(classId);
    }

    public final void ensureConsistent(List<? extends FirFile> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        State state = new State();
        Iterator<T> it = files.iterator();
        while (it.hasNext()) {
            recordFile((FirFile) it.next(), state);
        }
        ArrayList arrayList = new ArrayList();
        ensureConsistent$checkMMapDiff(arrayList, "fileMap", this.state.getFileMap(), state.getFileMap());
        ensureConsistent$checkMapDiff$default(arrayList, "classifierMap", this.state.getClassifierMap(), state.getClassifierMap(), null, 16, null);
        ensureConsistent$checkMapDiff$default(arrayList, "classifierContainerFileMap", this.state.getClassifierContainerFileMap(), state.getClassifierContainerFileMap(), null, 16, null);
        ensureConsistent$checkMMapDiff(arrayList, "callableMap", this.state.getFunctionMap(), state.getFunctionMap());
        ensureConsistent$checkMMapDiff(arrayList, "callableMap", this.state.getPropertyMap(), state.getPropertyMap());
        ensureConsistent$checkMMapDiff(arrayList, "callableMap", this.state.getConstructorMap(), state.getConstructorMap());
        ensureConsistent$checkMapDiff$default(arrayList, "callableContainerMap", this.state.getCallableContainerMap(), state.getCallableContainerMap(), null, 16, null);
        this.state.setFrom(state);
    }

    static /* synthetic */ void ensureConsistent$checkMapDiff$default(List list, String str, Map map, Map map2, Function2 function2, int i, Object obj) {
        if ((i & 16) != 0) {
            function2 = new Function2<V, V, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirProviderImpl$ensureConsistent$checkMapDiff$1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function2
                public final Boolean invoke(V v, V v2) {
                    return Boolean.valueOf(v == v2);
                }
            };
        }
        ensureConsistent$checkMapDiff(list, str, map, map2, function2);
    }

    private static final <K, V> void ensureConsistent$checkMapDiff(List<String> list, String str, Map<K, ? extends V> map, Map<K, ? extends V> map2, Function2<? super V, ? super V, Boolean> function2) {
        Set setPlus = SetsKt.plus((Set) map.keySet(), (Iterable) map2.keySet());
        ArrayList<Triple> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setPlus, 10));
        for (Object obj : setPlus) {
            arrayList.add(new Triple(obj, map.get(obj), map2.get(obj)));
        }
        boolean z = false;
        for (Triple triple : arrayList) {
            Object objComponent1 = triple.component1();
            Object objComponent2 = triple.component2();
            Object objComponent3 = triple.component3();
            if (!function2.invoke(objComponent2, objComponent3).booleanValue()) {
                if (!z) {
                    list.add(str);
                    z = true;
                }
                list.add("diff at key = '" + objComponent1 + "': was: '" + objComponent2 + "', become: '" + objComponent3 + '\'');
            }
        }
    }

    private static final <K, V> void ensureConsistent$checkMMapDiff(List<String> list, String str, Map<K, ? extends List<? extends V>> map, Map<K, ? extends List<? extends V>> map2) {
        Set setPlus = SetsKt.plus((Set) map.keySet(), (Iterable) map2.keySet());
        ArrayList<Triple> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setPlus, 10));
        for (Object obj : setPlus) {
            arrayList.add(new Triple(obj, map.get(obj), map2.get(obj)));
        }
        boolean z = false;
        for (Triple triple : arrayList) {
            Object objComponent1 = triple.component1();
            List list2 = (List) triple.component2();
            List list3 = (List) triple.component3();
            if (list2 == null || list3 == null) {
                if (!z) {
                    list.add(str);
                    z = true;
                }
                list.add("diff at key = '" + objComponent1 + "': was: " + list2 + ", become: " + list3);
            } else {
                Set set = CollectionsKt.toSet(list2);
                Set set2 = CollectionsKt.toSet(list3);
                Set setMinus = SetsKt.minus(set, (Iterable) set2);
                Set setMinus2 = SetsKt.minus(set2, (Iterable) set);
                if (!setMinus.isEmpty() || !setMinus2.isEmpty()) {
                    List<String> list4 = list;
                    list4.add("diff at key = '" + objComponent1 + "':");
                    list4.add("    Lost:");
                    Iterator it = setMinus.iterator();
                    while (it.hasNext()) {
                        list4.add("     " + it.next());
                    }
                    list4.add("    New:");
                    Iterator it2 = setMinus2.iterator();
                    while (it2.hasNext()) {
                        list4.add("     " + it2.next());
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public Set<Name> getClassNamesInPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Set<Name> set = this.state.getClassesInPackage().get(fqName);
        return set == null ? SetsKt.emptySet() : set;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirFile getFirCallableContainerFile(FirCallableSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) symbol.getFir();
        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
        if (originalForSubstitutionOverrideAttr == null) {
            originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null;
        if (symbol2 != null) {
            return FirProviderKt.getFirProvider(symbol2.getModuleData().getSession()).getFirCallableContainerFile(symbol2);
        }
        if (symbol instanceof FirBackingFieldSymbol) {
            return getFirCallableContainerFile(((FirBackingField) ((FirBackingFieldSymbol) symbol).getFir()).getPropertySymbol());
        }
        if (symbol instanceof FirSyntheticPropertySymbol) {
            FirProperty firProperty = (FirProperty) ((FirSyntheticPropertySymbol) symbol).getFir();
            if (firProperty instanceof FirSyntheticProperty) {
                return getFirCallableContainerFile(((FirSyntheticProperty) firProperty).getGetter().getDelegate().getSymbol());
            }
        }
        return this.state.getCallableContainerMap().get(symbol);
    }
}
