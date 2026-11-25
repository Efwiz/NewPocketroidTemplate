package org.jetbrains.kotlin.fir.resolve.providers.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.deserialization.ClassDeserializationKt;
import org.jetbrains.kotlin.fir.deserialization.FirBuiltinAnnotationDeserializer;
import org.jetbrains.kotlin.fir.deserialization.FirConstDeserializer;
import org.jetbrains.kotlin.fir.deserialization.FirDeserializationContext;
import org.jetbrains.kotlin.fir.deserialization.FirMemberDeserializer;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoBasedClassDataFinder;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: FirBuiltinSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001:\u0001,B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001d\u001a\u00020\u000bH\u0016J*\u0010\u001e\u001a\u00020\u001f2\u0010\u0010 \u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\"0!2\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%H\u0017J&\u0010&\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020'0!2\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%H\u0017J&\u0010(\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020'0!2\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%H\u0004J&\u0010)\u001a\u00020\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020*0!2\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%H\u0017J\u000e\u0010+\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002R \u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirBuiltinSymbolProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;)V", "allPackageFragments", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirBuiltinSymbolProvider$BuiltInsPackageFragment;", "getKotlinScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "syntheticFunctionInterfaceProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirBuiltinSyntheticFunctionInterfaceProvider;", "getClassLikeSymbolByClassId", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getPackage", "fqName", "getTopLevelCallableSymbolsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "packageFqName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelFunctionSymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getTopLevelFunctionSymbolsToByPackageFragments", "getTopLevelPropertySymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "loadBuiltIns", "BuiltInsPackageFragment", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirBuiltinSymbolProvider extends FirSymbolProvider {
    private final Map<FqName, List<BuiltInsPackageFragment>> allPackageFragments;
    private final FirKotlinScopeProvider kotlinScopeProvider;
    private final FirModuleData moduleData;
    private final FirSymbolNamesProvider symbolNamesProvider;
    private final FirBuiltinSyntheticFunctionInterfaceProvider syntheticFunctionInterfaceProvider;

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    public final FirModuleData getModuleData() {
        return this.moduleData;
    }

    public final FirKotlinScopeProvider getKotlinScopeProvider() {
        return this.kotlinScopeProvider;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirBuiltinSymbolProvider(FirSession session, FirModuleData moduleData, FirKotlinScopeProvider kotlinScopeProvider) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        this.moduleData = moduleData;
        this.kotlinScopeProvider = kotlinScopeProvider;
        this.syntheticFunctionInterfaceProvider = new FirBuiltinSyntheticFunctionInterfaceProvider(session, moduleData, kotlinScopeProvider);
        List<BuiltInsPackageFragment> listLoadBuiltIns = loadBuiltIns();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : listLoadBuiltIns) {
            FqName fqName = ((BuiltInsPackageFragment) obj).getFqName();
            Object obj2 = linkedHashMap.get(fqName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(fqName, obj2);
            }
            ((List) obj2).add(obj);
        }
        this.allPackageFragments = linkedHashMap;
        this.symbolNamesProvider = new FirSymbolNamesProvider() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSymbolProvider$symbolNamesProvider$1
            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public boolean getMayHaveSyntheticFunctionTypes() {
                return true;
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getPackageNamesWithTopLevelCallables() {
                Set setKeySet = this.this$0.allPackageFragments.keySet();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator it = setKeySet.iterator();
                while (it.hasNext()) {
                    linkedHashSet.add(((FqName) it.next()).asString());
                }
                return linkedHashSet;
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
                LinkedHashSet linkedHashSet;
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                List list = (List) this.this$0.allPackageFragments.get(packageFqName);
                if (list != null) {
                    LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        Collection<ClassId> allClassIds = ((FirBuiltinSymbolProvider.BuiltInsPackageFragment) it.next()).getClassDataFinder().getAllClassIds();
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(allClassIds, 10));
                        Iterator<T> it2 = allClassIds.iterator();
                        while (it2.hasNext()) {
                            arrayList.add(((ClassId) it2.next()).getShortClassName().asString());
                        }
                        CollectionsKt.addAll(linkedHashSet2, arrayList);
                    }
                    linkedHashSet = linkedHashSet2;
                } else {
                    linkedHashSet = null;
                }
                return linkedHashSet == null ? SetsKt.emptySet() : linkedHashSet;
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
                LinkedHashSet linkedHashSet;
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                List list = (List) this.this$0.allPackageFragments.get(packageFqName);
                if (list != null) {
                    LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        CollectionsKt.addAll(linkedHashSet2, ((FirBuiltinSymbolProvider.BuiltInsPackageFragment) it.next()).getTopLevelCallableNames());
                    }
                    linkedHashSet = linkedHashSet2;
                } else {
                    linkedHashSet = null;
                }
                return linkedHashSet == null ? SetsKt.emptySet() : linkedHashSet;
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public boolean mayHaveSyntheticFunctionType(ClassId classId) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                return this.this$0.syntheticFunctionInterfaceProvider.getSymbolNamesProvider().mayHaveSyntheticFunctionType(classId);
            }
        };
    }

    private final List<BuiltInsPackageFragment> loadBuiltIns() {
        final ClassLoader classLoader = getClass().getClassLoader();
        Function1<String, InputStream> function1 = new Function1<String, InputStream>() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSymbolProvider$loadBuiltIns$streamProvider$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final InputStream invoke(String path) {
                InputStream resourceAsStream;
                Intrinsics.checkNotNullParameter(path, "path");
                ClassLoader classLoader2 = classLoader;
                return (classLoader2 == null || (resourceAsStream = classLoader2.getResourceAsStream(path)) == null) ? ClassLoader.getSystemResourceAsStream(path) : resourceAsStream;
            }
        };
        Set<FqName> builtInsPackages = StandardClassIds.INSTANCE.getBuiltInsPackages();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(builtInsPackages, 10));
        for (FqName fqName : builtInsPackages) {
            String builtInsFilePath = BuiltInSerializerProtocol.INSTANCE.getBuiltInsFilePath(fqName);
            InputStream inputStreamInvoke = function1.invoke(builtInsFilePath);
            if (inputStreamInvoke == null) {
                throw new IllegalStateException("Resource not found in classpath: " + builtInsFilePath);
            }
            Intrinsics.checkNotNull(inputStreamInvoke);
            arrayList.add(new BuiltInsPackageFragment(inputStreamInvoke, fqName, this.moduleData, this.kotlinScopeProvider));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (this.allPackageFragments.containsKey(fqName)) {
            return fqName;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirRegularClassSymbol getClassLikeSymbolByClassId(ClassId classId) {
        FirRegularClassSymbol classLikeSymbolByClassId;
        Intrinsics.checkNotNullParameter(classId, "classId");
        List<BuiltInsPackageFragment> list = this.allPackageFragments.get(classId.getPackageFqName());
        if (list != null) {
            Iterator<T> it = list.iterator();
            do {
                if (!it.hasNext()) {
                    classLikeSymbolByClassId = null;
                    break;
                }
                classLikeSymbolByClassId = ((BuiltInsPackageFragment) it.next()).getClassLikeSymbolByClassId(classId);
            } while (classLikeSymbolByClassId == null);
            if (classLikeSymbolByClassId != null) {
                return classLikeSymbolByClassId;
            }
        }
        return this.syntheticFunctionInterfaceProvider.getClassLikeSymbolByClassId(classId);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirSymbolNamesProvider getSymbolNamesProvider() {
        return this.symbolNamesProvider;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        List<BuiltInsPackageFragment> list = this.allPackageFragments.get(packageFqName);
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(destination, ((BuiltInsPackageFragment) it.next()).getTopLevelCallableSymbols(name2));
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        getTopLevelFunctionSymbolsToByPackageFragments(destination, packageFqName, name2);
    }

    protected final void getTopLevelFunctionSymbolsToByPackageFragments(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        List<BuiltInsPackageFragment> list = this.allPackageFragments.get(packageFqName);
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(destination, ((BuiltInsPackageFragment) it.next()).getTopLevelFunctionSymbols(name2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FirBuiltinSymbolProvider.kt */
    @Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u001e\u0010,\u001a\u0004\u0018\u00010\u00182\u0006\u0010-\u001a\u00020\u00172\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\u0019H\u0002J\u0010\u0010/\u001a\u0004\u0018\u00010\u00182\u0006\u0010-\u001a\u00020\u0017J\f\u00100\u001a\b\u0012\u0004\u0012\u00020201J\u0018\u00103\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u000305042\u0006\u00106\u001a\u000202J\u0014\u00107\u001a\b\u0012\u0004\u0012\u000208042\u0006\u00106\u001a\u000202R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\"\u0010\u0015\u001a\u0016\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001a\u001a\u00020\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u000e\u0010\"\u001a\u00020#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010$\u001a\u00020%8F¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0011\u0010(\u001a\u00020)8F¢\u0006\u0006\u001a\u0004\b*\u0010+¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirBuiltinSymbolProvider$BuiltInsPackageFragment;", "", "stream", "Ljava/io/InputStream;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "(Ljava/io/InputStream;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;)V", "binaryVersionAndPackageFragment", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/BinaryVersionAndPackageFragment;", "classDataFinder", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBasedClassDataFinder;", "getClassDataFinder", "()Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBasedClassDataFinder;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getKotlinScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "lookup", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "memberDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/FirMemberDeserializer;", "getMemberDeserializer", "()Lorg/jetbrains/kotlin/fir/deserialization/FirMemberDeserializer;", "memberDeserializer$delegate", "Lkotlin/Lazy;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolverImpl;", "packageProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "getPackageProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "version", "Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "getVersion", "()Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "findAndDeserializeClass", "classId", "parentContext", "getClassLikeSymbolByClassId", "getTopLevelCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelCallableSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "name", "getTopLevelFunctionSymbols", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class BuiltInsPackageFragment {
        private final BinaryVersionAndPackageFragment binaryVersionAndPackageFragment;
        private final ProtoBasedClassDataFinder classDataFinder;
        private final FqName fqName;
        private final FirKotlinScopeProvider kotlinScopeProvider;
        private final FirCache<ClassId, FirRegularClassSymbol, FirDeserializationContext> lookup;

        /* JADX INFO: renamed from: memberDeserializer$delegate, reason: from kotlin metadata */
        private final Lazy memberDeserializer;
        private final FirModuleData moduleData;
        private final NameResolverImpl nameResolver;

        public BuiltInsPackageFragment(InputStream stream, FqName fqName, FirModuleData moduleData, FirKotlinScopeProvider kotlinScopeProvider) {
            Intrinsics.checkNotNullParameter(stream, "stream");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(moduleData, "moduleData");
            Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
            this.fqName = fqName;
            this.moduleData = moduleData;
            this.kotlinScopeProvider = kotlinScopeProvider;
            this.binaryVersionAndPackageFragment = BinaryVersionAndPackageFragment.INSTANCE.createFromStream(stream);
            ProtoBuf.StringTable strings = getPackageProto().getStrings();
            Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
            ProtoBuf.QualifiedNameTable qualifiedNames = getPackageProto().getQualifiedNames();
            Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
            NameResolverImpl nameResolverImpl = new NameResolverImpl(strings, qualifiedNames);
            this.nameResolver = nameResolverImpl;
            this.classDataFinder = new ProtoBasedClassDataFinder(getPackageProto(), nameResolverImpl, getVersion(), new Function1<ClassId, SourceElement>() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSymbolProvider$BuiltInsPackageFragment$classDataFinder$1
                @Override // kotlin.jvm.functions.Function1
                public final SourceElement invoke(ClassId it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
                    Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
                    return NO_SOURCE;
                }
            });
            this.memberDeserializer = LazyKt.lazy(new Function0<FirMemberDeserializer>() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSymbolProvider$BuiltInsPackageFragment$memberDeserializer$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final FirMemberDeserializer invoke() {
                    FirDeserializationContext.Companion companion = FirDeserializationContext.INSTANCE;
                    FqName fqName2 = this.this$0.getFqName();
                    ProtoBuf.Package r2 = this.this$0.getPackageProto().getPackage();
                    Intrinsics.checkNotNullExpressionValue(r2, "getPackage(...)");
                    return companion.createForPackage(fqName2, r2, this.this$0.nameResolver, this.this$0.getModuleData(), new FirBuiltinAnnotationDeserializer(this.this$0.getModuleData().getSession()), new FirConstDeserializer(this.this$0.getModuleData().getSession(), BuiltInSerializerProtocol.INSTANCE), null).getMemberDeserializer();
                }
            });
            this.lookup = FirCachesFactoryKt.getFirCachesFactory(moduleData.getSession()).createCacheWithPostCompute(new Function2<ClassId, FirDeserializationContext, Pair<? extends FirRegularClassSymbol, ? extends FirDeserializationContext>>() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSymbolProvider$BuiltInsPackageFragment$lookup$1
                @Override // kotlin.jvm.functions.Function2
                public final Pair<FirRegularClassSymbol, FirDeserializationContext> invoke(ClassId classId, FirDeserializationContext firDeserializationContext) {
                    Intrinsics.checkNotNullParameter(classId, "classId");
                    return TuplesKt.to(new FirRegularClassSymbol(classId), firDeserializationContext);
                }
            }, new Function3<ClassId, FirRegularClassSymbol, FirDeserializationContext, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSymbolProvider$BuiltInsPackageFragment$lookup$2
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ClassId classId, FirRegularClassSymbol firRegularClassSymbol, FirDeserializationContext firDeserializationContext) {
                    invoke2(classId, firRegularClassSymbol, firDeserializationContext);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ClassId classId, FirRegularClassSymbol symbol, FirDeserializationContext firDeserializationContext) {
                    Intrinsics.checkNotNullParameter(classId, "classId");
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    ClassData classDataFindClassData = this.this$0.getClassDataFinder().findClassData(classId);
                    Intrinsics.checkNotNull(classDataFindClassData);
                    ClassDeserializationKt.deserializeClassToSymbol(classId, classDataFindClassData.getClassProto(), symbol, this.this$0.nameResolver, this.this$0.getModuleData().getSession(), this.this$0.getModuleData(), null, this.this$0.getKotlinScopeProvider(), BuiltInSerializerProtocol.INSTANCE, firDeserializationContext, null, FirDeclarationOrigin.BuiltIns.INSTANCE, new AnonymousClass1(this.this$0));
                }

                /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.providers.impl.FirBuiltinSymbolProvider$BuiltInsPackageFragment$lookup$2$1, reason: invalid class name */
                /* JADX INFO: compiled from: FirBuiltinSymbolProvider.kt */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function2<ClassId, FirDeserializationContext, FirRegularClassSymbol> {
                    AnonymousClass1(Object obj) {
                        super(2, obj);
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public final String getSignature() {
                        return "findAndDeserializeClass(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;";
                    }

                    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                    public final String getName() {
                        return "findAndDeserializeClass";
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public final KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinClass(FirBuiltinSymbolProvider.BuiltInsPackageFragment.class);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final FirRegularClassSymbol invoke(ClassId p0, FirDeserializationContext firDeserializationContext) {
                        Intrinsics.checkNotNullParameter(p0, "p0");
                        return ((FirBuiltinSymbolProvider.BuiltInsPackageFragment) this.receiver).findAndDeserializeClass(p0, firDeserializationContext);
                    }
                }
            });
        }

        public final FqName getFqName() {
            return this.fqName;
        }

        public final FirModuleData getModuleData() {
            return this.moduleData;
        }

        public final FirKotlinScopeProvider getKotlinScopeProvider() {
            return this.kotlinScopeProvider;
        }

        public final BuiltInsBinaryVersion getVersion() {
            return this.binaryVersionAndPackageFragment.getVersion();
        }

        public final ProtoBuf.PackageFragment getPackageProto() {
            return this.binaryVersionAndPackageFragment.getPackageFragment();
        }

        public final ProtoBasedClassDataFinder getClassDataFinder() {
            return this.classDataFinder;
        }

        private final FirMemberDeserializer getMemberDeserializer() {
            return (FirMemberDeserializer) this.memberDeserializer.getValue();
        }

        public final FirRegularClassSymbol getClassLikeSymbolByClassId(ClassId classId) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            return findAndDeserializeClass$default(this, classId, null, 2, null);
        }

        static /* synthetic */ FirRegularClassSymbol findAndDeserializeClass$default(BuiltInsPackageFragment builtInsPackageFragment, ClassId classId, FirDeserializationContext firDeserializationContext, int i, Object obj) {
            if ((i & 2) != 0) {
                firDeserializationContext = null;
            }
            return builtInsPackageFragment.findAndDeserializeClass(classId, firDeserializationContext);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final FirRegularClassSymbol findAndDeserializeClass(ClassId classId, FirDeserializationContext parentContext) {
            if (this.classDataFinder.getAllClassIds().contains(classId)) {
                return this.lookup.getValue(classId, parentContext);
            }
            return null;
        }

        public final List<FirCallableSymbol<?>> getTopLevelCallableSymbols(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return getTopLevelFunctionSymbols(name2);
        }

        public final Collection<Name> getTopLevelCallableNames() {
            List<ProtoBuf.Function> functionList = getPackageProto().getPackage().getFunctionList();
            Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
            List<ProtoBuf.Function> list = functionList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(NameResolverUtilKt.getName(this.nameResolver, ((ProtoBuf.Function) it.next()).getName()));
            }
            return arrayList;
        }

        public final List<FirNamedFunctionSymbol> getTopLevelFunctionSymbols(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            List<ProtoBuf.Function> functionList = getPackageProto().getPackage().getFunctionList();
            Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
            ArrayList arrayList = new ArrayList();
            for (Object obj : functionList) {
                if (Intrinsics.areEqual(NameResolverUtilKt.getName(this.nameResolver, ((ProtoBuf.Function) obj).getName()), name2)) {
                    arrayList.add(obj);
                }
            }
            ArrayList<ProtoBuf.Function> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (ProtoBuf.Function function : arrayList2) {
                FirMemberDeserializer memberDeserializer = getMemberDeserializer();
                Intrinsics.checkNotNull(function);
                arrayList3.add(FirMemberDeserializer.loadFunction$default(memberDeserializer, function, null, null, null, 14, null).getSymbol());
            }
            return arrayList3;
        }
    }
}
