package org.jetbrains.kotlin.fir.extensions;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.FirGeneratedDeclarationsUtilsKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.caches.FirLazyValue;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.GeneratedDeclarationValidationKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirExtensionDeclarationsSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 ?2\u00020\u00012\u00020\u0002:\u0001?B%\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJm\u0010%\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H&0\u000f0\r\"\u0004\b\u0000\u0010'\"\u0004\b\u0001\u0010&2\u001d\u0010(\u001a\u0019\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0*0)¢\u0006\u0002\b+2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u0002H'\u0012\u0004\u0012\u00020\u000e0)2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H'\u0012\u0004\u0012\u0002H&0)H\u0002J\u0016\u0010.\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00142\u0006\u0010/\u001a\u00020\u0013H\u0002J\u0016\u00100\u001a\b\u0012\u0004\u0012\u00020\u001c0\b2\u0006\u00101\u001a\u00020\u0019H\u0002J\u0016\u00102\u001a\b\u0012\u0004\u0012\u00020 0\b2\u0006\u00101\u001a\u00020\u0019H\u0002J\u0016\u00103\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00142\u0006\u0010/\u001a\u00020\u0013H\u0016J\u0012\u00104\u001a\u0004\u0018\u00010\u000e2\u0006\u00105\u001a\u00020\u000eH\u0016J*\u00106\u001a\u0002072\u0010\u00108\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030:092\u0006\u0010,\u001a\u00020\u000e2\u0006\u0010;\u001a\u00020\u0010H\u0017J&\u0010<\u001a\u0002072\f\u00108\u001a\b\u0012\u0004\u0012\u00020\u001c092\u0006\u0010,\u001a\u00020\u000e2\u0006\u0010;\u001a\u00020\u0010H\u0017J&\u0010=\u001a\u0002072\f\u00108\u001a\b\u0012\u0004\u0012\u00020 092\u0006\u0010,\u001a\u00020\u000e2\u0006\u0010;\u001a\u00020\u0010H\u0017J\u0010\u0010>\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020\u000eH\u0002R&\u0010\u000b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\u0011\u001a\u001c\u0012\u0004\u0012\u00020\u0013\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0016\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u000f0\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0018\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u001a\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\u001b\u001a\u001c\u0012\u0004\u0012\u00020\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\b\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u001d\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010\u001f\u001a\u001c\u0012\u0004\u0012\u00020\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\b\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\"X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtensionDeclarationsSymbolProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "extensions", "", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;Ljava/util/List;)V", "callableNamesInPackageCache", "Lorg/jetbrains/kotlin/fir/caches/FirLazyValue;", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "Lorg/jetbrains/kotlin/name/Name;", "classCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "", "classNamesInPackageCache", "", "extensionsByTopLevelCallableId", "Lorg/jetbrains/kotlin/name/CallableId;", "extensionsByTopLevelClassId", "functionCache", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "packageCache", "", "propertyCache", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "computeNamesGroupedByPackage", "N", "I", "ids", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "packageFqName", "shortName", "generateClassLikeDeclaration", "classId", "generateTopLevelFunctions", "callableId", "generateTopLevelProperties", "getClassLikeSymbolByClassId", "getPackage", "fqName", "getTopLevelCallableSymbolsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "name", "getTopLevelFunctionSymbolsTo", "getTopLevelPropertySymbolsTo", "hasPackage", "Companion", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExtensionDeclarationsSymbolProvider extends FirSymbolProvider implements FirSessionComponent {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FirLazyValue<Map<FqName, Set<Name>>> callableNamesInPackageCache;
    private final FirCache classCache;
    private final FirLazyValue<Map<FqName, Set<String>>> classNamesInPackageCache;
    private final List<FirDeclarationGenerationExtension> extensions;
    private final FirLazyValue<Map<CallableId, List<FirDeclarationGenerationExtension>>> extensionsByTopLevelCallableId;
    private final FirLazyValue<Map<ClassId, List<FirDeclarationGenerationExtension>>> extensionsByTopLevelClassId;
    private final FirCache functionCache;
    private final FirCache packageCache;
    private final FirCache propertyCache;
    private final FirSymbolNamesProvider symbolNamesProvider;

    public /* synthetic */ FirExtensionDeclarationsSymbolProvider(FirSession firSession, FirCachesFactory firCachesFactory, List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firCachesFactory, list);
    }

    /* JADX INFO: compiled from: FirExtensionDeclarationsSymbolProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtensionDeclarationsSymbolProvider$Companion;", "", "()V", "createIfNeeded", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionDeclarationsSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirExtensionDeclarationsSymbolProvider createIfNeeded(FirSession session) {
            Intrinsics.checkNotNullParameter(session, "session");
            List<FirDeclarationGenerationExtension> declarationGenerators = FirDeclarationGenerationExtensionKt.getDeclarationGenerators(FirExtensionServiceKt.getExtensionService(session));
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (declarationGenerators.isEmpty()) {
                return null;
            }
            return new FirExtensionDeclarationsSymbolProvider(session, FirCachesFactoryKt.getFirCachesFactory(session), declarationGenerators, defaultConstructorMarker);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private FirExtensionDeclarationsSymbolProvider(FirSession firSession, FirCachesFactory firCachesFactory, List<? extends FirDeclarationGenerationExtension> list) {
        super(firSession);
        this.extensions = list;
        this.classCache = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$classCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final FirClassLikeSymbol<?> invoke(ClassId classId, Void r2) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                return this.this$0.generateClassLikeDeclaration(classId);
            }
        });
        this.functionCache = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$functionCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final List<FirNamedFunctionSymbol> invoke(CallableId callableId, Void r2) {
                Intrinsics.checkNotNullParameter(callableId, "callableId");
                return this.this$0.generateTopLevelFunctions(callableId);
            }
        });
        this.propertyCache = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$propertyCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final List<FirPropertySymbol> invoke(CallableId callableId, Void r2) {
                Intrinsics.checkNotNullParameter(callableId, "callableId");
                return this.this$0.generateTopLevelProperties(callableId);
            }
        });
        this.packageCache = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$packageCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FqName packageFqName, Void r2) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                return Boolean.valueOf(this.this$0.hasPackage(packageFqName));
            }
        });
        this.callableNamesInPackageCache = firCachesFactory.createLazyValue(new Function0<Map<FqName, ? extends Set<? extends Name>>>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$callableNamesInPackageCache$1
            {
                super(0);
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$callableNamesInPackageCache$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: FirExtensionDeclarationsSymbolProvider.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<FirDeclarationGenerationExtension, Set<? extends CallableId>> {
                public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

                AnonymousClass1() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "getTopLevelCallableIds()Ljava/util/Set;";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "getTopLevelCallableIds";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(FirDeclarationGenerationExtension.class);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Set<CallableId> invoke(FirDeclarationGenerationExtension p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return p0.getTopLevelCallableIds();
                }
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<FqName, ? extends Set<? extends Name>> invoke() {
                return this.this$0.computeNamesGroupedByPackage(AnonymousClass1.INSTANCE, new PropertyReference1() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$callableNamesInPackageCache$1.2
                    @Override // kotlin.jvm.internal.CallableReference
                    public String getSignature() {
                        return "getPackageName()Lorg/jetbrains/kotlin/name/FqName;";
                    }

                    @Override // kotlin.reflect.KProperty1
                    public Object get(Object obj) {
                        return ((CallableId) obj).getPackageName();
                    }

                    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                    public String getName() {
                        return "packageName";
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinClass(CallableId.class);
                    }
                }, new PropertyReference1() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$callableNamesInPackageCache$1.3
                    @Override // kotlin.jvm.internal.CallableReference
                    public String getSignature() {
                        return "getCallableName()Lorg/jetbrains/kotlin/name/Name;";
                    }

                    @Override // kotlin.reflect.KProperty1
                    public Object get(Object obj) {
                        return ((CallableId) obj).getCallableName();
                    }

                    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                    public String getName() {
                        return "callableName";
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinClass(CallableId.class);
                    }
                });
            }
        });
        this.classNamesInPackageCache = firCachesFactory.createLazyValue(new Function0<Map<FqName, ? extends Set<? extends String>>>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$classNamesInPackageCache$1
            {
                super(0);
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$classNamesInPackageCache$1$1, reason: invalid class name */
            /* JADX INFO: compiled from: FirExtensionDeclarationsSymbolProvider.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<FirDeclarationGenerationExtension, Set<? extends ClassId>> {
                public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

                AnonymousClass1() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "getTopLevelClassIds()Ljava/util/Set;";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "getTopLevelClassIds";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(FirDeclarationGenerationExtension.class);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Set<ClassId> invoke(FirDeclarationGenerationExtension p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return p0.getTopLevelClassIds();
                }
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<FqName, ? extends Set<? extends String>> invoke() {
                return this.this$0.computeNamesGroupedByPackage(AnonymousClass1.INSTANCE, AnonymousClass2.INSTANCE, new Function1<ClassId, String>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$classNamesInPackageCache$1.3
                    @Override // kotlin.jvm.functions.Function1
                    public final String invoke(ClassId it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        String strAsString = it.getShortClassName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        return strAsString;
                    }
                });
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$classNamesInPackageCache$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: FirExtensionDeclarationsSymbolProvider.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<ClassId, FqName> {
                public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

                AnonymousClass2() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "getPackageFqName()Lorg/jetbrains/kotlin/name/FqName;";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "getPackageFqName";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(ClassId.class);
                }

                @Override // kotlin.jvm.functions.Function1
                public final FqName invoke(ClassId p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return p0.getPackageFqName();
                }
            }
        });
        this.extensionsByTopLevelClassId = FirCachesFactoryKt.getFirCachesFactory(firSession).createLazyValue(new Function0<Map<ClassId, ? extends List<? extends FirDeclarationGenerationExtension>>>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$extensionsByTopLevelClassId$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<ClassId, ? extends List<? extends FirDeclarationGenerationExtension>> invoke() {
                List list2 = this.this$0.extensions;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Object obj : list2) {
                    for (ClassId classId : ((FirDeclarationGenerationExtension) obj).getTopLevelClassIdsCache().getValue()) {
                        ArrayList arrayList = (List) linkedHashMap.get(classId);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            linkedHashMap.put(classId, arrayList);
                        }
                        arrayList.add(obj);
                    }
                }
                return linkedHashMap;
            }
        });
        this.extensionsByTopLevelCallableId = FirCachesFactoryKt.getFirCachesFactory(firSession).createLazyValue(new Function0<Map<CallableId, ? extends List<? extends FirDeclarationGenerationExtension>>>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$extensionsByTopLevelCallableId$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<CallableId, ? extends List<? extends FirDeclarationGenerationExtension>> invoke() {
                List list2 = this.this$0.extensions;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Object obj : list2) {
                    for (CallableId callableId : ((FirDeclarationGenerationExtension) obj).getTopLevelCallableIdsCache().getValue()) {
                        ArrayList arrayList = (List) linkedHashMap.get(callableId);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            linkedHashMap.put(callableId, arrayList);
                        }
                        arrayList.add(obj);
                    }
                }
                return linkedHashMap;
            }
        });
        this.symbolNamesProvider = new FirSymbolNamesProvider() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$symbolNamesProvider$1
            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                Set<String> set = (Set) ((Map) this.this$0.classNamesInPackageCache.getValue()).get(packageFqName);
                return set == null ? SetsKt.emptySet() : set;
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getPackageNamesWithTopLevelCallables() {
                List list2 = this.this$0.extensions;
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    Set<CallableId> value = ((FirDeclarationGenerationExtension) it.next()).getTopLevelCallableIdsCache().getValue();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(value, 10));
                    Iterator<T> it2 = value.iterator();
                    while (it2.hasNext()) {
                        String strAsString = ((CallableId) it2.next()).getPackageName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        arrayList.add(strAsString);
                    }
                    CollectionsKt.addAll(linkedHashSet, arrayList);
                }
                return linkedHashSet;
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                Set<Name> set = (Set) ((Map) this.this$0.callableNamesInPackageCache.getValue()).get(packageFqName);
                return set == null ? SetsKt.emptySet() : set;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <I, N> Map<FqName, Set<N>> computeNamesGroupedByPackage(Function1<? super FirDeclarationGenerationExtension, ? extends Collection<? extends I>> ids, Function1<? super I, FqName> packageFqName, Function1<? super I, ? extends N> shortName) {
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        Iterator<FirDeclarationGenerationExtension> it = this.extensions.iterator();
        while (it.hasNext()) {
            for (I i : ids.invoke(it.next())) {
                FqName fqNameInvoke = packageFqName.invoke(i);
                Object obj = mapCreateMapBuilder.get(fqNameInvoke);
                if (obj == null) {
                    obj = (Set) new LinkedHashSet();
                    mapCreateMapBuilder.put(fqNameInvoke, obj);
                }
                ((Set) obj).add(shortName.invoke(i));
            }
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final FirClassLikeSymbol<?> generateClassLikeDeclaration(ClassId classId) {
        FirNestedClassifierScope firNestedClassifierScopeNestedClassifierScope;
        if (classId.isLocal()) {
            return null;
        }
        if (classId.isNestedClass()) {
            FirSymbolProvider symbolProvider = FirSymbolProviderKt.getSymbolProvider(getSession());
            ClassId outerClassId = classId.getOuterClassId();
            Intrinsics.checkNotNull(outerClassId);
            FirClassLikeSymbol<?> classLikeSymbolByClassId = symbolProvider.getClassLikeSymbolByClassId(outerClassId);
            FirClassSymbol firClassSymbol = classLikeSymbolByClassId instanceof FirClassSymbol ? (FirClassSymbol) classLikeSymbolByClassId : null;
            if (firClassSymbol == null || (firNestedClassifierScopeNestedClassifierScope = FirDeclaredMemberScopeProviderKt.nestedClassifierScope(getSession(), (FirClass) firClassSymbol.getFir())) == null) {
                return null;
            }
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            Name shortClassName = classId.getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            final Function1<FirClassifierSymbol<?>, Unit> function1 = new Function1<FirClassifierSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider.generateClassLikeDeclaration.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol) {
                    invoke2(firClassifierSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirClassifierSymbol<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof FirClassLikeSymbol) {
                        objectRef.element = it;
                    }
                }
            };
            firNestedClassifierScopeNestedClassifierScope.processClassifiersByNameWithSubstitution(shortClassName, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider$generateClassLikeDeclaration$$inlined$processClassifiersByName$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                    invoke2(firClassifierSymbol, coneSubstitutor);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor coneSubstitutor) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    Intrinsics.checkNotNullParameter(coneSubstitutor, "<anonymous parameter 1>");
                    function1.invoke(symbol);
                }
            });
            return (FirClassLikeSymbol) objectRef.element;
        }
        List<FirDeclarationGenerationExtension> list = this.extensionsByTopLevelClassId.getValue().get(classId);
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (FirDeclarationGenerationExtension firDeclarationGenerationExtension : list) {
            FirClassLikeSymbol<?> firClassLikeSymbolGenerateTopLevelClassLikeDeclaration = firDeclarationGenerationExtension.generateTopLevelClassLikeDeclaration(classId);
            if (firClassLikeSymbolGenerateTopLevelClassLikeDeclaration != null) {
                FirGeneratedDeclarationsUtilsKt.setOwnerGenerator((FirClassLikeDeclaration) firClassLikeSymbolGenerateTopLevelClassLikeDeclaration.getFir(), firDeclarationGenerationExtension);
            } else {
                firClassLikeSymbolGenerateTopLevelClassLikeDeclaration = null;
            }
            if (firClassLikeSymbolGenerateTopLevelClassLikeDeclaration != null) {
                arrayList.add(firClassLikeSymbolGenerateTopLevelClassLikeDeclaration);
            }
        }
        ArrayList arrayList2 = arrayList;
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            GeneratedDeclarationValidationKt.validate(((FirClassLikeSymbol) it.next()).getFir());
        }
        ArrayList arrayList3 = arrayList2;
        int size = arrayList3.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return (FirClassLikeSymbol) CollectionsKt.first((List) arrayList3);
        }
        throw new IllegalStateException(("Multiple plugins generated classes with same classId " + classId + '\n' + CollectionsKt.joinToString$default(arrayList3, "\n", null, null, 0, null, new Function1<FirClassLikeSymbol<?>, CharSequence>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionDeclarationsSymbolProvider.generateClassLikeDeclaration.2
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(FirClassLikeSymbol<?> it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return UtilsKt.render(it2.getFir());
            }
        }, 30, null)).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirNamedFunctionSymbol> generateTopLevelFunctions(CallableId callableId) {
        List<FirDeclarationGenerationExtension> listEmptyList = this.extensionsByTopLevelCallableId.getValue().get(callableId);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listEmptyList.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((FirDeclarationGenerationExtension) it.next()).generateFunctions(callableId, null));
        }
        ArrayList arrayList2 = arrayList;
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            GeneratedDeclarationValidationKt.validate(((FirNamedFunctionSymbol) it2.next()).getFir());
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirPropertySymbol> generateTopLevelProperties(CallableId callableId) {
        List<FirDeclarationGenerationExtension> listEmptyList = this.extensionsByTopLevelCallableId.getValue().get(callableId);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listEmptyList.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((FirDeclarationGenerationExtension) it.next()).generateProperties(callableId, null));
        }
        ArrayList arrayList2 = arrayList;
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            GeneratedDeclarationValidationKt.validate(((FirPropertySymbol) it2.next()).getFir());
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean hasPackage(FqName packageFqName) {
        List<FirDeclarationGenerationExtension> list = this.extensions;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((FirDeclarationGenerationExtension) it.next()).hasPackage(packageFqName)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirSymbolNamesProvider getSymbolNamesProvider() {
        return this.symbolNamesProvider;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirClassLikeSymbol<?> getClassLikeSymbolByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return (FirClassLikeSymbol) this.classCache.getValue(classId, null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        CallableId callableId = new CallableId(packageFqName, name2);
        List<FirCallableSymbol<?>> list = destination;
        CollectionsKt.addAll(list, (Iterable) this.functionCache.getValue(callableId, null));
        CollectionsKt.addAll(list, (Iterable) this.propertyCache.getValue(callableId, null));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        CollectionsKt.addAll(destination, (Iterable) this.functionCache.getValue(new CallableId(packageFqName, name2), null));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        CollectionsKt.addAll(destination, (Iterable) this.propertyCache.getValue(new CallableId(packageFqName, name2), null));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (((Boolean) this.packageCache.getValue(fqName, null)).booleanValue()) {
            return fqName;
        }
        return null;
    }
}
