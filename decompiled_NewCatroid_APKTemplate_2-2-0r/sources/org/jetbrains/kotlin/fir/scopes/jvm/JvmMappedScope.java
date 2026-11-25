package org.jetbrains.kotlin.fir.scopes.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltInsSignatures;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractOverrideCheckerKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirFakeOverrideGenerator;
import org.jetbrains.kotlin.fir.scopes.impl.FirStandardOverrideChecker;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.load.java.BuiltinSpecialProperties;
import org.jetbrains.kotlin.load.java.PropertiesConventionUtilKt;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.load.kotlin.SignatureBuildingComponents;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JvmMappedScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 O2\u00020\u0001:\u0002OPB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0001¢\u0006\u0002\u0010\nJ\u000e\u0010)\u001a\b\u0012\u0004\u0012\u00020!0 H\u0016J\u000e\u0010*\u001a\b\u0012\u0004\u0012\u00020!0 H\u0016J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020\u00142\u0006\u00100\u001a\u00020\u0014H\u0002J\u0018\u00101\u001a\u00020\u001a2\u0006\u00100\u001a\u00020\u001a2\u0006\u00102\u001a\u00020,H\u0002J\u0010\u00103\u001a\u00020\u001c2\u0006\u00104\u001a\u00020\u001aH\u0002J\u0018\u00105\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u00106\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\u001aH\u0002J\u0010\u00107\u001a\u00020\u001c2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u00108\u001a\u00020\u001c2\u0006\u00109\u001a\u00020!H\u0002J.\u0010:\u001a\u00020;2\u0006\u00109\u001a\u00020!2\u001c\u0010<\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030>\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020;0=H\u0016J\u001c\u0010?\u001a\u00020;2\u0012\u0010<\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020;0@H\u0016J*\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u001a2\u0018\u0010<\u001a\u0014\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020B0=H\u0016J*\u0010D\u001a\u00020B2\u0006\u0010E\u001a\u00020F2\u0018\u0010<\u001a\u0014\u0012\u0004\u0012\u00020F\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020B0=H\u0016J$\u0010G\u001a\u00020;2\u0006\u00109\u001a\u00020!2\u0012\u0010<\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020;0@H\u0016J(\u0010H\u001a\u00020;2\u0006\u00109\u001a\u00020!2\u0016\u0010<\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030I\u0012\u0004\u0012\u00020;0@H\u0016J\b\u0010J\u001a\u00020.H\u0016J\f\u0010K\u001a\u00020\u001c*\u00020\u001aH\u0002J\f\u0010L\u001a\u00020\u001c*\u00020MH\u0002J\f\u0010N\u001a\u00020\u001c*\u00020\u001aH\u0002R!\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00140\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0011\u001a\u0004\b\u0017\u0010\u000fR\u001a\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001a0\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u0011\u001a\u0004\b\"\u0010#R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Q²\u0006\u0010\u0010R\u001a\b\u0012\u0004\u0012\u00020.0 X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/jvm/JvmMappedScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "firKotlinClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "firJavaClass", "declaredMemberScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "javaMappedClassUseSiteScope", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)V", "allJavaMappedSuperClassIds", "", "Lorg/jetbrains/kotlin/name/ClassId;", "getAllJavaMappedSuperClassIds", "()Ljava/util/List;", "allJavaMappedSuperClassIds$delegate", "Lkotlin/Lazy;", "constructorsCache", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "declaredScopeOfMutableVersion", "firKotlinClassConstructors", "getFirKotlinClassConstructors", "firKotlinClassConstructors$delegate", "functionsCache", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "isMutableContainer", "", "kotlinDispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "myCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getMyCallableNames", "()Ljava/util/Set;", "myCallableNames$delegate", "overrideChecker", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirStandardOverrideChecker;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "getCallableNames", "getClassifierNames", "getJdkMethodStatus", "Lorg/jetbrains/kotlin/fir/scopes/jvm/JvmMappedScope$JDKMemberStatus;", "jvmDescriptor", "", "getOrCreateCopy", NativeSymbol.TYPE_NAME, "getOrCreateSubstitutedCopy", "jdkMemberStatus", "isDeclaredInBuiltinClass", "it", "isMutabilityViolation", "isOverrideOfKotlinDeclaredFunction", "isRenamedJdkMethod", "isTherePropertyWithNameInKotlinClass", "name", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "processDeclaredConstructors", "Lkotlin/Function1;", "processDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "functionSymbol", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "processFunctionsByName", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "toString", "isDeclaredInMappedJavaClass", "isDeprecated", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isOverrideOfKotlinBuiltinPropertyGetter", "Companion", "JDKMemberStatus", SuffixConstants.EXTENSION_java, "declaredSignatures"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmMappedScope extends FirTypeScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: allJavaMappedSuperClassIds$delegate, reason: from kotlin metadata */
    private final Lazy allJavaMappedSuperClassIds;
    private final Map<FirConstructorSymbol, FirConstructorSymbol> constructorsCache;
    private final FirContainingNamesAwareScope declaredMemberScope;
    private final FirContainingNamesAwareScope declaredScopeOfMutableVersion;
    private final FirRegularClass firJavaClass;
    private final FirRegularClass firKotlinClass;

    /* JADX INFO: renamed from: firKotlinClassConstructors$delegate, reason: from kotlin metadata */
    private final Lazy firKotlinClassConstructors;
    private final Map<FirNamedFunctionSymbol, FirNamedFunctionSymbol> functionsCache;
    private final boolean isMutableContainer;
    private final FirTypeScope javaMappedClassUseSiteScope;
    private final ConeClassLikeType kotlinDispatchReceiverType;

    /* JADX INFO: renamed from: myCallableNames$delegate, reason: from kotlin metadata */
    private final Lazy myCallableNames;
    private final FirStandardOverrideChecker overrideChecker;
    private final FirSession session;
    private final ConeSubstitutor substitutor;

    /* JADX INFO: compiled from: JvmMappedScope.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/jvm/JvmMappedScope$JDKMemberStatus;", "", "(Ljava/lang/String;I)V", VirtualFile.PROP_HIDDEN, "VISIBLE", "DROP", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum JDKMemberStatus {
        HIDDEN,
        VISIBLE,
        DROP;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<JDKMemberStatus> getEntries() {
            return $ENTRIES;
        }
    }

    public JvmMappedScope(FirSession session, FirRegularClass firKotlinClass, FirRegularClass firJavaClass, FirContainingNamesAwareScope declaredMemberScope, FirTypeScope javaMappedClassUseSiteScope) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(firKotlinClass, "firKotlinClass");
        Intrinsics.checkNotNullParameter(firJavaClass, "firJavaClass");
        Intrinsics.checkNotNullParameter(declaredMemberScope, "declaredMemberScope");
        Intrinsics.checkNotNullParameter(javaMappedClassUseSiteScope, "javaMappedClassUseSiteScope");
        this.session = session;
        this.firKotlinClass = firKotlinClass;
        this.firJavaClass = firJavaClass;
        this.declaredMemberScope = declaredMemberScope;
        this.javaMappedClassUseSiteScope = javaMappedClassUseSiteScope;
        this.functionsCache = new LinkedHashMap();
        this.constructorsCache = new LinkedHashMap();
        this.overrideChecker = new FirStandardOverrideChecker(session);
        this.substitutor = INSTANCE.createMappingSubstitutor(firJavaClass, firKotlinClass, session);
        this.kotlinDispatchReceiverType = ScopeUtilsKt.defaultType(firKotlinClass);
        ClassId onlyToMutable = JavaToKotlinClassMap.INSTANCE.readOnlyToMutable(FirDeclarationUtilKt.getClassId(firKotlinClass));
        FirContainingNamesAwareScope firContainingNamesAwareScopeDeclaredMemberScope = null;
        if (onlyToMutable != null) {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(session).getClassLikeSymbolByClassId(onlyToMutable);
            FirClassSymbol firClassSymbol = classLikeSymbolByClassId instanceof FirClassSymbol ? (FirClassSymbol) classLikeSymbolByClassId : null;
            if (firClassSymbol != null) {
                firContainingNamesAwareScopeDeclaredMemberScope = FirDeclaredMemberScopeProviderKt.declaredMemberScope(session, (FirClassSymbol<?>) firClassSymbol, (FirResolvePhase) null);
            }
        }
        this.declaredScopeOfMutableVersion = firContainingNamesAwareScopeDeclaredMemberScope;
        this.isMutableContainer = JavaToKotlinClassMap.INSTANCE.isMutable(FirDeclarationUtilKt.getClassId(firKotlinClass));
        this.allJavaMappedSuperClassIds = LazyKt.lazy(new Function0<List<? extends ClassId>>() { // from class: org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope$allJavaMappedSuperClassIds$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends ClassId> invoke() {
                JvmMappedScope jvmMappedScope = this.this$0;
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                listCreateListBuilder.add(FirDeclarationUtilKt.getClassId(jvmMappedScope.firJavaClass));
                Iterator<T> it = SupertypeUtilsKt.lookupSuperTypes(jvmMappedScope.firJavaClass.getSymbol(), true, true, jvmMappedScope.session).iterator();
                while (it.hasNext()) {
                    List list = listCreateListBuilder;
                    ClassId classId = ((ConeClassLikeType) it.next()).getLookupTag().getClassId();
                    JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
                    FqNameUnsafe unsafe = classId.asSingleFqName().toUnsafe();
                    Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
                    ClassId classIdMapKotlinToJava = javaToKotlinClassMap.mapKotlinToJava(unsafe);
                    if (classIdMapKotlinToJava != null) {
                        classId = classIdMapKotlinToJava;
                    }
                    list.add(classId);
                }
                return CollectionsKt.build(listCreateListBuilder);
            }
        });
        this.myCallableNames = LazyKt.lazy(new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope$myCallableNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                Modality modality = this.this$0.firKotlinClass.getStatus().getModality();
                if (modality != null && modality != Modality.FINAL) {
                    return SetsKt.plus((Set) this.this$0.declaredMemberScope.getCallableNames(), (Iterable) this.this$0.javaMappedClassUseSiteScope.getCallableNames());
                }
                String string = this.this$0.firJavaClass.getSymbol().getClassId().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                Set<String> visible_method_signatures = JvmBuiltInsSignatures.INSTANCE.getVISIBLE_METHOD_SIGNATURES();
                JvmMappedScope jvmMappedScope = this.this$0;
                ArrayList arrayList = new ArrayList();
                for (Object obj : visible_method_signatures) {
                    String str = (String) obj;
                    if (JvmBuiltInsSignatures.INSTANCE.getMUTABLE_METHOD_SIGNATURES().contains(str) == jvmMappedScope.isMutableContainer && StringsKt.startsWith$default(str, string, false, 2, (Object) null)) {
                        arrayList.add(obj);
                    }
                }
                ArrayList<String> arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                for (String str2 : arrayList2) {
                    String strSubstring = str2.substring(string.length() + 1, StringsKt.indexOf$default((CharSequence) str2, "(", 0, false, 6, (Object) null));
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    arrayList3.add(Name.identifier(strSubstring));
                }
                return SetsKt.plus((Set) this.this$0.declaredMemberScope.getCallableNames(), (Iterable) arrayList3);
            }
        });
        this.firKotlinClassConstructors = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends FirConstructorSymbol>>() { // from class: org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope$firKotlinClassConstructors$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirConstructorSymbol> invoke() {
                return DeclarationUtilsKt.constructors(this.this$0.firKotlinClass, this.this$0.session);
            }
        });
    }

    private final List<ClassId> getAllJavaMappedSuperClassIds() {
        return (List) this.allJavaMappedSuperClassIds.getValue();
    }

    private final Set<Name> getMyCallableNames() {
        return (Set) this.myCallableNames.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(final Name name2, final Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        final ArrayList arrayList = new ArrayList();
        this.declaredMemberScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope.processFunctionsByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                arrayList.add(symbol);
                processor.invoke(symbol);
            }
        });
        final Lazy lazy = LazyKt.lazy(new Function0<Set<? extends String>>() { // from class: org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope$processFunctionsByName$declaredSignatures$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends String> invoke() {
                List<FirNamedFunctionSymbol> list = arrayList;
                JvmMappedScope jvmMappedScope = this;
                Name name3 = name2;
                final Set setCreateSetBuilder = SetsKt.createSetBuilder();
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    setCreateSetBuilder.add(SignatureUtilsKt.computeJvmDescriptor$default((FirFunction) ((FirNamedFunctionSymbol) it.next()).getFir(), null, false, null, 7, null));
                }
                FirContainingNamesAwareScope firContainingNamesAwareScope = jvmMappedScope.declaredScopeOfMutableVersion;
                if (firContainingNamesAwareScope != null) {
                    firContainingNamesAwareScope.processFunctionsByName(name3, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope$processFunctionsByName$declaredSignatures$2$1$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                            invoke2(firNamedFunctionSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirNamedFunctionSymbol it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            setCreateSetBuilder.add(SignatureUtilsKt.computeJvmDescriptor$default((FirFunction) it2.getFir(), null, false, null, 7, null));
                        }
                    });
                }
                return SetsKt.build(setCreateSetBuilder);
            }
        });
        this.javaMappedClassUseSiteScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope.processFunctionsByName.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol symbol) {
                JDKMemberStatus jdkMethodStatus;
                Modality modality;
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                if (JvmMappedScope.this.isDeclaredInMappedJavaClass(symbol)) {
                    FirDeclarationStatus status = ((FirSimpleFunction) symbol.getFir()).getStatus();
                    Intrinsics.checkNotNull(status, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus");
                    if (((FirResolvedDeclarationStatus) status).getVisibility().getIsPublicAPI()) {
                        String strComputeJvmDescriptor$default = SignatureUtilsKt.computeJvmDescriptor$default((FirFunction) symbol.getFir(), null, false, null, 7, null);
                        if (JvmMappedScope.processFunctionsByName$lambda$2(lazy).contains(strComputeJvmDescriptor$default) || JvmMappedScope.this.isRenamedJdkMethod(strComputeJvmDescriptor$default) || JvmMappedScope.this.isOverrideOfKotlinBuiltinPropertyGetter(symbol) || JvmMappedScope.this.isOverrideOfKotlinDeclaredFunction(symbol) || JvmMappedScope.this.isMutabilityViolation(symbol, strComputeJvmDescriptor$default) || (jdkMethodStatus = JvmMappedScope.this.getJdkMethodStatus(strComputeJvmDescriptor$default)) == JDKMemberStatus.DROP) {
                            return;
                        }
                        if (jdkMethodStatus == JDKMemberStatus.HIDDEN && ((modality = JvmMappedScope.this.firKotlinClass.getStatus().getModality()) == null || modality == Modality.FINAL)) {
                            return;
                        }
                        processor.invoke(JvmMappedScope.this.getOrCreateSubstitutedCopy(symbol, jdkMethodStatus));
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set<String> processFunctionsByName$lambda$2(Lazy<? extends Set<String>> lazy) {
        return lazy.getValue();
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope$isOverrideOfKotlinDeclaredFunction$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: JvmMappedScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C46171 extends FunctionReference implements Function1<FirNamedFunctionSymbol, Boolean> {
        C46171(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "isDeclaredInBuiltinClass(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;)Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "isDeclaredInBuiltinClass";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(JvmMappedScope.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(FirNamedFunctionSymbol p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(((JvmMappedScope) this.receiver).isDeclaredInBuiltinClass(p0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isOverrideOfKotlinDeclaredFunction(FirNamedFunctionSymbol symbol) {
        return FirTypeScopeKt.anyOverriddenOf(this.javaMappedClassUseSiteScope, symbol, new C46171(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isMutabilityViolation(FirNamedFunctionSymbol symbol, String jvmDescriptor) {
        if (JvmBuiltInsSignatures.INSTANCE.getMUTABLE_METHOD_SIGNATURES().contains(SignatureBuildingComponents.INSTANCE.signature(FirDeclarationUtilKt.getClassId(this.firJavaClass), jvmDescriptor)) != this.isMutableContainer) {
            return true;
        }
        return FirTypeScopeKt.anyOverriddenOf(this.javaMappedClassUseSiteScope, symbol, new Function1<FirNamedFunctionSymbol, Boolean>() { // from class: org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope.isMutabilityViolation.1
            /* JADX WARN: Code duplicated, block: B:11:0x0023  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FirNamedFunctionSymbol it) {
                boolean z;
                ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag;
                ClassId classId;
                Intrinsics.checkNotNullParameter(it, "it");
                FirNamedFunctionSymbol firNamedFunctionSymbol = it;
                if (!ClassMembersKt.isSubstitutionOrIntersectionOverride(firNamedFunctionSymbol) && (coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firNamedFunctionSymbol)) != null && (classId = coneClassLikeLookupTagContainingClassLookupTag.getClassId()) != null) {
                    z = JavaToKotlinClassMap.INSTANCE.isMutable(classId);
                }
                return Boolean.valueOf(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isOverrideOfKotlinBuiltinPropertyGetter(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        FqName fqNameChild = FirDeclarationUtilKt.getClassId(this.firJavaClass).asSingleFqName().child(firNamedFunctionSymbol.getName());
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        if (!firNamedFunctionSymbol.getValueParameterSymbols().isEmpty()) {
            return false;
        }
        if (BuiltinSpecialProperties.INSTANCE.getGETTER_FQ_NAMES().contains(fqNameChild)) {
            return true;
        }
        List<Name> propertyNamesCandidatesByAccessorName = PropertiesConventionUtilKt.getPropertyNamesCandidatesByAccessorName(firNamedFunctionSymbol.getName());
        if ((propertyNamesCandidatesByAccessorName instanceof Collection) && propertyNamesCandidatesByAccessorName.isEmpty()) {
            return false;
        }
        Iterator<T> it = propertyNamesCandidatesByAccessorName.iterator();
        while (it.hasNext()) {
            if (isTherePropertyWithNameInKotlinClass((Name) it.next())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isRenamedJdkMethod(String jvmDescriptor) {
        return SpecialGenericSignatures.INSTANCE.getJVM_SIGNATURES_FOR_RENAMED_BUILT_INS().contains(SignatureBuildingComponents.INSTANCE.signature(FirDeclarationUtilKt.getClassId(this.firJavaClass), jvmDescriptor));
    }

    private final boolean isTherePropertyWithNameInKotlinClass(Name name2) {
        if (this.declaredMemberScope.getCallableNames().contains(name2)) {
            return !FirScopeKt.getProperties(this.declaredMemberScope, name2).isEmpty();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isDeclaredInBuiltinClass(FirNamedFunctionSymbol it) {
        return Intrinsics.areEqual(it.getOrigin(), FirDeclarationOrigin.Library.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isDeclaredInMappedJavaClass(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        return !ClassMembersKt.isSubstitutionOrIntersectionOverride((FirCallableDeclaration) firNamedFunctionSymbol.getFir()) && Intrinsics.areEqual(ClassMembersKt.dispatchReceiverClassLookupTagOrNull((FirCallableDeclaration) firNamedFunctionSymbol.getFir()), this.firJavaClass.getSymbol().getLookupTag());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JDKMemberStatus getJdkMethodStatus(String jvmDescriptor) {
        Iterator<ClassId> it = getAllJavaMappedSuperClassIds().iterator();
        while (it.hasNext()) {
            String strSignature = SignatureBuildingComponents.INSTANCE.signature(it.next(), jvmDescriptor);
            if (JvmBuiltInsSignatures.INSTANCE.getHIDDEN_METHOD_SIGNATURES().contains(strSignature)) {
                return JDKMemberStatus.HIDDEN;
            }
            if (JvmBuiltInsSignatures.INSTANCE.getVISIBLE_METHOD_SIGNATURES().contains(strSignature)) {
                return JDKMemberStatus.VISIBLE;
            }
            if (JvmBuiltInsSignatures.INSTANCE.getDROP_LIST_METHOD_SIGNATURES().contains(strSignature)) {
                return JDKMemberStatus.DROP;
            }
        }
        return JDKMemberStatus.HIDDEN;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.declaredMemberScope.processPropertiesByName(name2, processor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final FirNamedFunctionSymbol getOrCreateSubstitutedCopy(FirNamedFunctionSymbol symbol, JDKMemberStatus jdkMemberStatus) {
        Map<FirNamedFunctionSymbol, FirNamedFunctionSymbol> map = this.functionsCache;
        FirNamedFunctionSymbol firNamedFunctionSymbol = map.get(symbol);
        if (firNamedFunctionSymbol == null) {
            FirSimpleFunction firSimpleFunction = (FirSimpleFunction) symbol.getFir();
            FirNamedFunctionSymbol firNamedFunctionSymbol2 = new FirNamedFunctionSymbol(new CallableId(FirDeclarationUtilKt.getClassId(this.firKotlinClass), symbol.getCallableId().getCallableName()));
            FirFakeOverrideGenerator firFakeOverrideGenerator = FirFakeOverrideGenerator.INSTANCE;
            FirSimpleFunction firSimpleFunction2 = (FirSimpleFunction) symbol.getFir();
            ConeClassLikeLookupTag lookupTag = this.firKotlinClass.getSymbol().getLookupTag();
            FirSession firSession = this.session;
            FirDeclarationOrigin origin = ((FirSimpleFunction) symbol.getFir()).getOrigin();
            ConeClassLikeType coneClassLikeType = this.kotlinDispatchReceiverType;
            List<FirValueParameter> valueParameters = firSimpleFunction.getValueParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(this.substitutor.substituteOrSelf(FirTypeUtilsKt.getConeType(((FirValueParameter) it.next()).getReturnTypeRef())));
            }
            ConeKotlinType coneKotlinTypeSubstituteOrSelf = this.substitutor.substituteOrSelf(FirTypeUtilsKt.getConeType(firSimpleFunction.getReturnTypeRef()));
            firNamedFunctionSymbol = firNamedFunctionSymbol2;
            FirSimpleFunction firSimpleFunctionCreateCopyForFirFunction$default = FirFakeOverrideGenerator.createCopyForFirFunction$default(firFakeOverrideGenerator, firNamedFunctionSymbol2, firSimpleFunction2, lookupTag, firSession, origin, false, coneClassLikeType, arrayList, null, null, null, coneKotlinTypeSubstituteOrSelf, null, null, null, 30496, null);
            if (jdkMemberStatus == JDKMemberStatus.HIDDEN) {
                DeprecationUtilsKt.setHiddenEverywhereBesideSuperCalls(firSimpleFunctionCreateCopyForFirFunction$default, true);
            }
            map.put(symbol, firNamedFunctionSymbol);
        }
        return firNamedFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return ProcessorAction.NONE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirConstructorSymbol> getFirKotlinClassConstructors() {
        return (List) this.firKotlinClassConstructors.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(final Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.javaMappedClassUseSiteScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.jvm.JvmMappedScope.processDeclaredConstructors.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke2(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            private static final boolean invoke$isShadowedBy(FirConstructor firConstructor, JvmMappedScope jvmMappedScope, FirConstructorSymbol firConstructorSymbol) {
                ConeSubstitutor coneSubstitutorBuildSubstitutorForOverridesCheck;
                List<FirValueParameter> valueParameters = firConstructor.getValueParameters();
                List<FirValueParameter> valueParameters2 = ((FirConstructor) firConstructorSymbol.getFir()).getValueParameters();
                if (valueParameters.size() != valueParameters2.size() || (coneSubstitutorBuildSubstitutorForOverridesCheck = FirAbstractOverrideCheckerKt.buildSubstitutorForOverridesCheck((FirCallableDeclaration) firConstructorSymbol.getFir(), firConstructor, jvmMappedScope.session)) == null) {
                    return false;
                }
                List<Pair> listZip = CollectionsKt.zip(valueParameters2, valueParameters);
                if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
                    for (Pair pair : listZip) {
                        if (!jvmMappedScope.overrideChecker.isEqualTypes(((FirValueParameter) pair.component1()).getReturnTypeRef(), ((FirValueParameter) pair.component2()).getReturnTypeRef(), coneSubstitutorBuildSubstitutorForOverridesCheck)) {
                            return false;
                        }
                    }
                }
                return true;
            }

            private static final boolean invoke$isTrivialCopyConstructor(FirConstructor firConstructor, JvmMappedScope jvmMappedScope) {
                FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.singleOrNull((List) firConstructor.getValueParameters());
                if (firValueParameter == null) {
                    return false;
                }
                ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef()));
                ConeClassLikeType coneClassLikeType = coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeClassLikeType ? (ConeClassLikeType) coneSimpleKotlinTypeLowerBoundIfFlexible : null;
                return Intrinsics.areEqual(coneClassLikeType != null ? coneClassLikeType.getLookupTag() : null, jvmMappedScope.firKotlinClass.getSymbol().getLookupTag());
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirConstructorSymbol javaCtorSymbol) {
                Intrinsics.checkNotNullParameter(javaCtorSymbol, "javaCtorSymbol");
                FirConstructor firConstructor = (FirConstructor) javaCtorSymbol.getFir();
                if (!firConstructor.getStatus().getVisibility().getIsPublicAPI() || JvmMappedScope.this.isDeprecated(firConstructor)) {
                    return;
                }
                if (JvmBuiltInsSignatures.INSTANCE.getHIDDEN_CONSTRUCTOR_SIGNATURES().contains(SignatureBuildingComponents.INSTANCE.signature(FirDeclarationUtilKt.getClassId(JvmMappedScope.this.firJavaClass), SignatureUtilsKt.computeJvmDescriptor$default(firConstructor, null, false, null, 7, null))) || invoke$isTrivialCopyConstructor(firConstructor, JvmMappedScope.this)) {
                    return;
                }
                List firKotlinClassConstructors = JvmMappedScope.this.getFirKotlinClassConstructors();
                JvmMappedScope jvmMappedScope = JvmMappedScope.this;
                if (!(firKotlinClassConstructors instanceof Collection) || !firKotlinClassConstructors.isEmpty()) {
                    Iterator it = firKotlinClassConstructors.iterator();
                    while (it.hasNext()) {
                        if (invoke$isShadowedBy(firConstructor, jvmMappedScope, (FirConstructorSymbol) it.next())) {
                            return;
                        }
                    }
                }
                processor.invoke(JvmMappedScope.this.getOrCreateCopy(javaCtorSymbol));
            }
        });
        this.declaredMemberScope.processDeclaredConstructors(processor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isDeprecated(FirDeclaration firDeclaration) {
        return DeprecationUtilsKt.getDeprecation(firDeclaration.getSymbol(), this.session, null) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final FirConstructorSymbol getOrCreateCopy(FirConstructorSymbol symbol) {
        Map<FirConstructorSymbol, FirConstructorSymbol> map = this.constructorsCache;
        FirConstructorSymbol firConstructorSymbol = map.get(symbol);
        if (firConstructorSymbol == null) {
            FirConstructor firConstructor = (FirConstructor) symbol.getFir();
            ClassId classId = FirDeclarationUtilKt.getClassId(this.firKotlinClass);
            Name shortClassName = classId.getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            FirConstructorSymbol firConstructorSymbol2 = new FirConstructorSymbol(new CallableId(classId, shortClassName));
            FirFakeOverrideGenerator firFakeOverrideGenerator = FirFakeOverrideGenerator.INSTANCE;
            FirSession firSession = this.session;
            ConeClassLikeLookupTag lookupTag = this.firKotlinClass.getSymbol().getLookupTag();
            FirDeclarationOrigin origin = ((FirConstructor) symbol.getFir()).getOrigin();
            ConeKotlinType coneKotlinTypeSubstituteOrSelf = this.substitutor.substituteOrSelf(FirTypeUtilsKt.getConeType(firConstructor.getReturnTypeRef()));
            List<FirValueParameter> valueParameters = firConstructor.getValueParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(this.substitutor.substituteOrSelf(FirTypeUtilsKt.getConeType(((FirValueParameter) it.next()).getReturnTypeRef())));
            }
            firFakeOverrideGenerator.createCopyForFirConstructor(firConstructorSymbol2, firSession, firConstructor, lookupTag, origin, null, coneKotlinTypeSubstituteOrSelf, arrayList, CollectionsKt.emptyList(), null, false, null);
            firConstructorSymbol = firConstructorSymbol2;
            map.put(symbol, firConstructorSymbol);
        }
        return firConstructorSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return ProcessorAction.NONE;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.declaredMemberScope.processClassifiersByNameWithSubstitution(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return getMyCallableNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.declaredMemberScope.getClassifierNames();
    }

    /* JADX INFO: compiled from: JvmMappedScope.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/jvm/JvmMappedScope$Companion;", "", "()V", "createMappingSubstitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "fromClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "toClass", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ConeSubstitutor createMappingSubstitutor(FirRegularClass fromClass, FirRegularClass toClass, FirSession session) {
            List<Pair> listZip = CollectionsKt.zip(fromClass.getTypeParameters(), toClass.getTypeParameters());
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listZip, 10)), 16));
            for (Pair pair : listZip) {
                Pair pair2 = TuplesKt.to(((FirTypeParameterRef) pair.component1()).getSymbol(), new ConeTypeParameterTypeImpl(new ConeTypeParameterLookupTag(((FirTypeParameterRef) pair.component2()).getSymbol()), false, null, 4, null));
                linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
            }
            return new ConeSubstitutorByMap(linkedHashMap, session);
        }
    }

    public String toString() {
        return "JVM mapped scope for " + FirDeclarationUtilKt.getClassId(this.firKotlinClass);
    }
}
