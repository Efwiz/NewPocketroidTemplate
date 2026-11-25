package org.jetbrains.kotlin.fir.java;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProviderWithoutCallables;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JavaSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001:\u0001#B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\tH\u0016J\u001a\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0016J*\u0010\u0017\u001a\u00020\u00182\u0010\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J&\u0010\u001f\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020 0\u001a2\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J&\u0010!\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\"0\u001a2\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R$\u0010\u0007\u001a\u0018\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/JavaSymbolProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "javaFacade", "Lorg/jetbrains/kotlin/fir/java/FirJavaFacade;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/java/FirJavaFacade;)V", "classCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "Lorg/jetbrains/kotlin/fir/java/JavaSymbolProvider$ClassCacheContext;", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getClassLikeSymbolByClassId", "classId", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getPackage", "Lorg/jetbrains/kotlin/name/FqName;", "fqName", "getTopLevelCallableSymbolsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "packageFqName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelFunctionSymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getTopLevelPropertySymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "ClassCacheContext", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JavaSymbolProvider extends FirSymbolProvider {
    private final FirCache<ClassId, FirRegularClassSymbol, ClassCacheContext> classCache;
    private final FirJavaFacade javaFacade;
    private final FirSymbolNamesProvider symbolNamesProvider;

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: JavaSymbolProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/JavaSymbolProvider$ClassCacheContext;", "", "parentClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "foundJavaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;)V", "getFoundJavaClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getParentClassSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class ClassCacheContext {
        private final JavaClass foundJavaClass;
        private final FirRegularClassSymbol parentClassSymbol;

        /* JADX WARN: Multi-variable type inference failed */
        public ClassCacheContext() {
            this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }

        public ClassCacheContext(FirRegularClassSymbol firRegularClassSymbol, JavaClass javaClass) {
            this.parentClassSymbol = firRegularClassSymbol;
            this.foundJavaClass = javaClass;
        }

        public /* synthetic */ ClassCacheContext(FirRegularClassSymbol firRegularClassSymbol, JavaClass javaClass, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : firRegularClassSymbol, (i & 2) != 0 ? null : javaClass);
        }

        public final FirRegularClassSymbol getParentClassSymbol() {
            return this.parentClassSymbol;
        }

        public final JavaClass getFoundJavaClass() {
            return this.foundJavaClass;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaSymbolProvider(FirSession session, FirJavaFacade javaFacade) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(javaFacade, "javaFacade");
        this.javaFacade = javaFacade;
        this.classCache = FirCachesFactoryKt.getFirCachesFactory(session).createCacheWithPostCompute(new Function2<ClassId, ClassCacheContext, Pair<? extends FirRegularClassSymbol, ? extends Pair<? extends JavaClass, ? extends FirRegularClassSymbol>>>() { // from class: org.jetbrains.kotlin.fir.java.JavaSymbolProvider$classCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Pair<FirRegularClassSymbol, Pair<JavaClass, FirRegularClassSymbol>> invoke(ClassId classId, JavaSymbolProvider.ClassCacheContext classCacheContext) {
                JavaClass javaClassFindClass$default;
                Intrinsics.checkNotNullParameter(classId, "classId");
                if ((classCacheContext == null || (javaClassFindClass$default = classCacheContext.getFoundJavaClass()) == null) && (javaClassFindClass$default = FirJavaFacade.findClass$default(this.this$0.javaFacade, classId, null, 2, null)) == null) {
                    return TuplesKt.to(null, TuplesKt.to(null, null));
                }
                return TuplesKt.to(new FirRegularClassSymbol(classId), TuplesKt.to(javaClassFindClass$default, classCacheContext != null ? classCacheContext.getParentClassSymbol() : null));
            }
        }, new Function3<ClassId, FirRegularClassSymbol, Pair<? extends JavaClass, ? extends FirRegularClassSymbol>, Unit>() { // from class: org.jetbrains.kotlin.fir.java.JavaSymbolProvider$classCache$2
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ClassId classId, FirRegularClassSymbol firRegularClassSymbol, Pair<? extends JavaClass, ? extends FirRegularClassSymbol> pair) {
                invoke2(classId, firRegularClassSymbol, (Pair<? extends JavaClass, FirRegularClassSymbol>) pair);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ClassId classId, FirRegularClassSymbol firRegularClassSymbol, Pair<? extends JavaClass, FirRegularClassSymbol> pair) {
                Intrinsics.checkNotNullParameter(classId, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 2>");
                JavaClass javaClassComponent1 = pair.component1();
                FirRegularClassSymbol firRegularClassSymbolComponent2 = pair.component2();
                if (firRegularClassSymbol == null || javaClassComponent1 == null) {
                    return;
                }
                this.this$0.javaFacade.convertJavaClassToFir(firRegularClassSymbol, firRegularClassSymbolComponent2, javaClassComponent1);
            }
        });
        this.symbolNamesProvider = new FirSymbolNamesProviderWithoutCallables() { // from class: org.jetbrains.kotlin.fir.java.JavaSymbolProvider$symbolNamesProvider$1
            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                return this.this$0.javaFacade.knownClassNamesInPackage(packageFqName);
            }
        };
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirRegularClassSymbol getClassLikeSymbolByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (this.javaFacade.hasTopLevelClassOf(classId)) {
            return getClassLikeSymbolByClassId(classId, null);
        }
        return null;
    }

    public final FirRegularClassSymbol getClassLikeSymbolByClassId(ClassId classId, JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        FirCache<ClassId, FirRegularClassSymbol, ClassCacheContext> firCache = this.classCache;
        ClassId outerClassId = classId.getOuterClassId();
        return firCache.getValue(classId, new ClassCacheContext(outerClassId != null ? getClassLikeSymbolByClassId(outerClassId, null) : null, javaClass));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.javaFacade.getPackage(fqName);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirSymbolNamesProvider getSymbolNamesProvider() {
        return this.symbolNamesProvider;
    }
}
