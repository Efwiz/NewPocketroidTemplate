package org.jetbrains.kotlin.fir.declarations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;

/* JADX INFO: compiled from: deprecationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a%\u0010\u000e\u001a\u00020\u000f2\u0017\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011¢\u0006\u0002\b\u0014H\u0086\bø\u0001\u0000\u001aF\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0010\b\u0002\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u00192\u0010\b\u0002\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b\u001a\"\u0010\u001f\u001a\u00020 2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001d\u001a\u0004\u0018\u00010\u0019\u001a*\u0010!\u001a\u00020\u000f*\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\"\u001a\u00020\u00062\u000e\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010\u001bH\u0002\u001aH\u0010%\u001a\u00020\u000f*\u00020&2\u0006\u0010\u0016\u001a\u00020\u00172\u0010\b\u0002\u0010'\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b2\u0010\b\u0002\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b2\u0010\b\u0002\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b\u001a\"\u0010(\u001a\u0004\u0018\u00010)*\u0006\u0012\u0002\b\u00030*2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010+\u001a\u0004\u0018\u00010,\u001a1\u0010-\u001a\u0004\u0018\u00010)*\u0006\u0012\u0002\b\u00030*2\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010.\u001a\n\u0012\u0006\b\u0001\u0012\u0002000/\"\u000200¢\u0006\u0002\u00101\u001a\u000e\u00102\u001a\u0004\u0018\u000103*\u00020\u001cH\u0002\u001a\u0012\u00104\u001a\u00020 *\u00020&2\u0006\u0010\u0016\u001a\u00020\u0017\u001a2\u00105\u001a\u00020 *\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u00062\u0010\b\u0002\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010\u001b\u001a\"\u00106\u001a\u0004\u0018\u00010)*\u0006\u0012\u0002\b\u00030*2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010+\u001a\u0004\u0018\u00010,\u001a3\u00107\u001a\u0004\u0018\u00010)*\u0006\u0012\u0002\b\u00030*2\u0006\u00108\u001a\u0002092\u0012\u0010.\u001a\n\u0012\u0006\b\u0001\u0012\u0002000/\"\u000200H\u0002¢\u0006\u0002\u0010:\u001a%\u0010;\u001a\b\u0012\u0004\u0012\u0002000/*\u0006\u0012\u0002\b\u00030*2\b\u0010+\u001a\u0004\u0018\u00010,H\u0002¢\u0006\u0002\u0010<\u001a\u0016\u0010=\u001a\u0004\u0018\u00010>*\u00020\u001c2\u0006\u0010?\u001a\u00020@H\u0002\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"3\u0010\u0007\u001a\u0004\u0018\u00010\u0006*\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00068F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u0007\u0010\t\"\u0004\b\n\u0010\u000b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006A"}, d2 = {"deprecationAnnotationSimpleNames", "", "", "getDeprecationAnnotationSimpleNames", "()Ljava/util/Set;", "<set-?>", "", "isHiddenEverywhereBesideSuperCalls", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Ljava/lang/Boolean;", "setHiddenEverywhereBesideSuperCalls", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Ljava/lang/Boolean;)V", "isHiddenEverywhereBesideSuperCalls$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "buildDeprecationAnnotationInfoPerUseSiteStorage", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfoPerUseSiteStorage;", "builder", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfoPerUseSiteStorageBuilder;", "", "Lkotlin/ExtensionFunctionType;", "getDeprecationsAnnotationInfoByUseSiteFromAccessors", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "getterAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "setter", "setterAnnotations", "getDeprecationsProviderFromAccessors", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "extractDeprecationAnnotationInfoPerUseSite", "fromJava", "versionRequirements", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "extractDeprecationInfoPerUseSite", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "customAnnotations", "getDeprecation", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "callSite", "Lorg/jetbrains/kotlin/fir/FirElement;", "getDeprecationForCallSite", "sites", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/FirSession;[Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;)Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "getDeprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "getDeprecationsProvider", "getDeprecationsProviderFromAnnotations", "getOwnDeprecation", "getOwnDeprecationForCallSite", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;[Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;)Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "getUseSitesForCallSite", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/FirElement;)[Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "getVersionFromArgument", "Lorg/jetbrains/kotlin/config/ApiVersion;", "name", "Lorg/jetbrains/kotlin/name/Name;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeprecationUtilsKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeprecationUtilsKt.class, "providers"), "isHiddenEverywhereBesideSuperCalls", "isHiddenEverywhereBesideSuperCalls(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Ljava/lang/Boolean;"))};
    private static final Set<String> deprecationAnnotationSimpleNames;
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor isHiddenEverywhereBesideSuperCalls$delegate;

    public static final DeprecationAnnotationInfoPerUseSiteStorage buildDeprecationAnnotationInfoPerUseSiteStorage(Function1<? super DeprecationAnnotationInfoPerUseSiteStorageBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        DeprecationAnnotationInfoPerUseSiteStorageBuilder deprecationAnnotationInfoPerUseSiteStorageBuilder = new DeprecationAnnotationInfoPerUseSiteStorageBuilder();
        builder.invoke(deprecationAnnotationInfoPerUseSiteStorageBuilder);
        return deprecationAnnotationInfoPerUseSiteStorageBuilder.build();
    }

    private static final AnnotationUseSiteTarget[] getUseSitesForCallSite(FirBasedSymbol<?> firBasedSymbol, FirElement firElement) {
        if (!(firBasedSymbol instanceof FirPropertySymbol)) {
            return new AnnotationUseSiteTarget[0];
        }
        if (firElement instanceof FirVariableAssignment) {
            return new AnnotationUseSiteTarget[]{AnnotationUseSiteTarget.PROPERTY_SETTER, AnnotationUseSiteTarget.PROPERTY};
        }
        return firElement instanceof FirPropertyAccessExpression ? new AnnotationUseSiteTarget[]{AnnotationUseSiteTarget.PROPERTY_GETTER, AnnotationUseSiteTarget.PROPERTY} : new AnnotationUseSiteTarget[]{AnnotationUseSiteTarget.PROPERTY};
    }

    public static final DeprecationInfo getOwnDeprecation(FirBasedSymbol<?> firBasedSymbol, FirSession session, FirElement firElement) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        LanguageVersionSettings languageVersionSettings = FirLanguageSettingsComponentKt.getLanguageVersionSettings(session);
        AnnotationUseSiteTarget[] useSitesForCallSite = getUseSitesForCallSite(firBasedSymbol, firElement);
        return getOwnDeprecationForCallSite(firBasedSymbol, languageVersionSettings, (AnnotationUseSiteTarget[]) Arrays.copyOf(useSitesForCallSite, useSitesForCallSite.length));
    }

    public static final DeprecationInfo getDeprecation(FirBasedSymbol<?> firBasedSymbol, FirSession session, FirElement firElement) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        AnnotationUseSiteTarget[] useSitesForCallSite = getUseSitesForCallSite(firBasedSymbol, firElement);
        return getDeprecationForCallSite(firBasedSymbol, session, (AnnotationUseSiteTarget[]) Arrays.copyOf(useSitesForCallSite, useSitesForCallSite.length));
    }

    public static final DeprecationsProvider getDeprecationsProvider(FirAnnotationContainer firAnnotationContainer, FirSession session) {
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return extractDeprecationInfoPerUseSite$default(firAnnotationContainer, session, null, null, null, 14, null).toDeprecationsProvider(FirCachesFactoryKt.getFirCachesFactory(session));
    }

    public static /* synthetic */ DeprecationAnnotationInfoPerUseSiteStorage extractDeprecationInfoPerUseSite$default(FirAnnotationContainer firAnnotationContainer, FirSession firSession, List list, List list2, List list3, int i, Object obj) {
        if ((i & 2) != 0) {
            list = firAnnotationContainer.getAnnotations();
        }
        if ((i & 4) != 0) {
            list2 = null;
        }
        if ((i & 8) != 0) {
            list3 = null;
        }
        return extractDeprecationInfoPerUseSite(firAnnotationContainer, firSession, list, list2, list3);
    }

    public static final DeprecationAnnotationInfoPerUseSiteStorage extractDeprecationInfoPerUseSite(FirAnnotationContainer firAnnotationContainer, FirSession session, List<? extends FirAnnotation> list, List<? extends FirAnnotation> list2, List<? extends FirAnnotation> list3) {
        boolean zIsJavaOrEnhancement;
        List<VersionRequirement> versionRequirements;
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (firAnnotationContainer instanceof FirDeclaration) {
            FirDeclaration firDeclaration = (FirDeclaration) firAnnotationContainer;
            zIsJavaOrEnhancement = DeclarationUtilsKt.isJavaOrEnhancement(firDeclaration);
            versionRequirements = FirVersionRequirementsTableKeyKt.getVersionRequirements(firDeclaration);
        } else {
            zIsJavaOrEnhancement = false;
            versionRequirements = null;
        }
        DeprecationAnnotationInfoPerUseSiteStorageBuilder deprecationAnnotationInfoPerUseSiteStorageBuilder = new DeprecationAnnotationInfoPerUseSiteStorageBuilder();
        if (list == null) {
            list = firAnnotationContainer.getAnnotations();
        }
        deprecationAnnotationInfoPerUseSiteStorageBuilder.add(extractDeprecationAnnotationInfoPerUseSite(list, zIsJavaOrEnhancement, versionRequirements));
        if (firAnnotationContainer instanceof FirProperty) {
            FirProperty firProperty = (FirProperty) firAnnotationContainer;
            deprecationAnnotationInfoPerUseSiteStorageBuilder.add(getDeprecationsAnnotationInfoByUseSiteFromAccessors(session, firProperty.getGetter(), list2, firProperty.getSetter(), list3));
        }
        return deprecationAnnotationInfoPerUseSiteStorageBuilder.build();
    }

    public static final DeprecationsProvider getDeprecationsProviderFromAccessors(FirSession session, FirFunction firFunction, FirFunction firFunction2) {
        Intrinsics.checkNotNullParameter(session, "session");
        return getDeprecationsAnnotationInfoByUseSiteFromAccessors$default(session, firFunction, null, firFunction2, null, 20, null).toDeprecationsProvider(FirCachesFactoryKt.getFirCachesFactory(session));
    }

    public static /* synthetic */ DeprecationAnnotationInfoPerUseSiteStorage getDeprecationsAnnotationInfoByUseSiteFromAccessors$default(FirSession firSession, FirFunction firFunction, List list, FirFunction firFunction2, List list2, int i, Object obj) {
        if ((i & 4) != 0) {
            list = firFunction != null ? firFunction.getAnnotations() : null;
        }
        if ((i & 16) != 0) {
            list2 = firFunction2 != null ? firFunction2.getAnnotations() : null;
        }
        return getDeprecationsAnnotationInfoByUseSiteFromAccessors(firSession, firFunction, list, firFunction2, list2);
    }

    public static /* synthetic */ DeprecationsProvider getDeprecationsProviderFromAnnotations$default(List list, FirSession firSession, boolean z, List list2, int i, Object obj) {
        if ((i & 4) != 0) {
            list2 = null;
        }
        return getDeprecationsProviderFromAnnotations(list, firSession, z, list2);
    }

    public static final DeprecationsProvider getDeprecationsProviderFromAnnotations(List<? extends FirAnnotation> list, FirSession session, boolean z, List<VersionRequirement> list2) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return extractDeprecationAnnotationInfoPerUseSite(list, z, list2).toDeprecationsProvider(FirCachesFactoryKt.getFirCachesFactory(session));
    }

    private static final DeprecationInfo getOwnDeprecationForCallSite(FirBasedSymbol<?> firBasedSymbol, LanguageVersionSettings languageVersionSettings, AnnotationUseSiteTarget... annotationUseSiteTargetArr) {
        DeprecationsPerUseSite ownDeprecation;
        if (firBasedSymbol instanceof FirCallableSymbol) {
            ownDeprecation = ((FirCallableSymbol) firBasedSymbol).getDeprecation(languageVersionSettings);
        } else {
            ownDeprecation = firBasedSymbol instanceof FirClassLikeSymbol ? ((FirClassLikeSymbol) firBasedSymbol).getOwnDeprecation(languageVersionSettings) : null;
        }
        if (ownDeprecation == null) {
            ownDeprecation = DeprecationsKt.getEmptyDeprecationsPerUseSite();
        }
        return ownDeprecation.forUseSite((AnnotationUseSiteTarget[]) Arrays.copyOf(annotationUseSiteTargetArr, annotationUseSiteTargetArr.length));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [T, org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo] */
    public static final DeprecationInfo getDeprecationForCallSite(FirBasedSymbol<?> firBasedSymbol, final FirSession session, final AnnotationUseSiteTarget... sites) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(sites, "sites");
        if (!(firBasedSymbol instanceof FirTypeAliasSymbol)) {
            return getOwnDeprecationForCallSite(firBasedSymbol, FirLanguageSettingsComponentKt.getLanguageVersionSettings(session), (AnnotationUseSiteTarget[]) Arrays.copyOf(sites, sites.length));
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = getOwnDeprecationForCallSite(firBasedSymbol, FirLanguageSettingsComponentKt.getLanguageVersionSettings(session), (AnnotationUseSiteTarget[]) Arrays.copyOf(sites, sites.length));
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        ConeTypeUtilsKt.forEachType(((FirTypeAliasSymbol) firBasedSymbol).getResolvedExpandedTypeRef().getType(), new Function1<ConeKotlinType, Unit>() { // from class: org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt.getDeprecationForCallSite.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ConeKotlinType coneKotlinType) {
                invoke2(coneKotlinType);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Type inference failed for: r3v2, types: [T, org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo] */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ConeKotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Map<ConeKotlinType, DeprecationInfo> map = linkedHashMap;
                FirSession firSession = session;
                AnnotationUseSiteTarget[] annotationUseSiteTargetArr = sites;
                DeprecationInfo deprecationForCallSite = map.get(it);
                if (deprecationForCallSite == null) {
                    FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(it, firSession);
                    if (symbol == null) {
                        return;
                    }
                    deprecationForCallSite = DeprecationUtilsKt.getDeprecationForCallSite(symbol, firSession, (AnnotationUseSiteTarget[]) Arrays.copyOf(annotationUseSiteTargetArr, annotationUseSiteTargetArr.length));
                    map.put(it, deprecationForCallSite);
                }
                DeprecationInfo deprecationInfo = deprecationForCallSite;
                if (deprecationInfo == 0) {
                    return;
                }
                DeprecationInfo deprecationInfo2 = objectRef.element;
                if (deprecationInfo2 == null || deprecationInfo.compareTo(deprecationInfo2) > 0) {
                    objectRef.element = deprecationInfo;
                }
            }
        });
        return (DeprecationInfo) objectRef.element;
    }

    private static final ApiVersion getVersionFromArgument(FirAnnotation firAnnotation, Name name2) {
        String stringArgument = FirAnnotationUtilsKt.getStringArgument(firAnnotation, name2);
        if (stringArgument != null) {
            return ApiVersion.INSTANCE.parse(stringArgument);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final DeprecationLevelValue getDeprecationLevel(FirAnnotation firAnnotation) {
        Object next;
        FirExpression firExpression;
        FirExpression firExpressionUnwrapArgument;
        Name name2;
        String strAsString;
        if (FirAnnotationUtilsKt.getResolved(firAnnotation)) {
            firExpression = firAnnotation.getArgumentMapping().getMapping().get(StandardClassIds.Annotations.ParameterNames.INSTANCE.getDeprecatedLevel());
        } else {
            FirAnnotationCall firAnnotationCall = firAnnotation instanceof FirAnnotationCall ? (FirAnnotationCall) firAnnotation : null;
            if (firAnnotationCall == null) {
                return null;
            }
            Iterator<T> it = firAnnotationCall.getArgumentList().getArguments().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                FirExpression firExpression2 = (FirExpression) next;
                if ((firExpression2 instanceof FirNamedArgumentExpression) && Intrinsics.areEqual(((FirNamedArgumentExpression) firExpression2).getName(), StandardClassIds.Annotations.ParameterNames.INSTANCE.getDeprecatedLevel())) {
                    break;
                }
            }
            FirExpression firExpression3 = (FirExpression) next;
            firExpression = (firExpression3 == null || (firExpressionUnwrapArgument = FirExpressionUtilKt.unwrapArgument(firExpression3)) == null) ? (FirExpression) CollectionsKt.lastOrNull((List) ((FirCall) firAnnotation).getArgumentList().getArguments()) : firExpressionUnwrapArgument;
        }
        if (firExpression == null) {
            return null;
        }
        FirQualifiedAccessExpression firQualifiedAccessExpression = firExpression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpression : null;
        if (firQualifiedAccessExpression == null) {
            return null;
        }
        FirReference calleeReference = firQualifiedAccessExpression.getCalleeReference();
        FirNamedReference firNamedReference = calleeReference instanceof FirNamedReference ? (FirNamedReference) calleeReference : null;
        if (firNamedReference == null || (name2 = firNamedReference.getName()) == null || (strAsString = name2.asString()) == null) {
            return null;
        }
        for (DeprecationLevelValue deprecationLevelValue : DeprecationLevelValue.values()) {
            if (Intrinsics.areEqual(deprecationLevelValue.name(), strAsString)) {
                return deprecationLevelValue;
            }
        }
        return null;
    }

    public static final Set<String> getDeprecationAnnotationSimpleNames() {
        return deprecationAnnotationSimpleNames;
    }

    private static final DeprecationAnnotationInfoPerUseSiteStorage extractDeprecationAnnotationInfoPerUseSite(List<? extends FirAnnotation> list, boolean z, List<VersionRequirement> list2) {
        ApiVersion apiVersion;
        ConeClassLikeType coneClassLikeType;
        ConeClassLikeLookupTag lookupTag;
        Object next;
        DeprecationAnnotationInfo deprecatedSinceKotlinInfo;
        ConeClassLikeType coneClassLikeType2;
        ConeClassLikeLookupTag lookupTag2;
        ConeClassLikeLookupTag lookupTag3;
        ConeClassLikeLookupTag lookupTag4;
        ConeClassLikeLookupTag lookupTag5;
        ConeClassLikeLookupTag lookupTag6;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        ClassId deprecated = StandardClassIds.Annotations.INSTANCE.getDeprecated();
        Iterator<? extends FirAnnotation> it = list.iterator();
        while (true) {
            ClassId classId = null;
            if (!it.hasNext()) {
                break;
            }
            FirAnnotation next2 = it.next();
            FirTypeRef annotationTypeRef = next2.getAnnotationTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeClassLikeType)) {
                type = null;
            }
            ConeClassLikeType coneClassLikeType3 = (ConeClassLikeType) type;
            if (coneClassLikeType3 != null && (lookupTag6 = coneClassLikeType3.getLookupTag()) != null) {
                classId = lookupTag6.getClassId();
            }
            if (Intrinsics.areEqual(classId, deprecated)) {
                listCreateListBuilder.add(TuplesKt.to(next2, false));
            }
        }
        ClassId deprecated2 = StandardClassIds.Annotations.Java.INSTANCE.getDeprecated();
        for (FirAnnotation firAnnotation : list) {
            FirTypeRef annotationTypeRef2 = firAnnotation.getAnnotationTypeRef();
            FirResolvedTypeRef firResolvedTypeRef2 = annotationTypeRef2 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef2 : null;
            ConeKotlinType type2 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
            if (!(type2 instanceof ConeClassLikeType)) {
                type2 = null;
            }
            ConeClassLikeType coneClassLikeType4 = (ConeClassLikeType) type2;
            if (Intrinsics.areEqual((coneClassLikeType4 == null || (lookupTag5 = coneClassLikeType4.getLookupTag()) == null) ? null : lookupTag5.getClassId(), deprecated2)) {
                listCreateListBuilder.add(TuplesKt.to(firAnnotation, true));
            }
        }
        ClassId sinceKotlin = StandardClassIds.Annotations.INSTANCE.getSinceKotlin();
        for (FirAnnotation firAnnotation2 : list) {
            FirTypeRef annotationTypeRef3 = firAnnotation2.getAnnotationTypeRef();
            FirResolvedTypeRef firResolvedTypeRef3 = annotationTypeRef3 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef3 : null;
            ConeKotlinType type3 = firResolvedTypeRef3 != null ? firResolvedTypeRef3.getType() : null;
            if (!(type3 instanceof ConeClassLikeType)) {
                type3 = null;
            }
            ConeClassLikeType coneClassLikeType5 = (ConeClassLikeType) type3;
            if (Intrinsics.areEqual((coneClassLikeType5 == null || (lookupTag4 = coneClassLikeType5.getLookupTag()) == null) ? null : lookupTag4.getClassId(), sinceKotlin)) {
                listCreateListBuilder.add(TuplesKt.to(firAnnotation2, false));
            }
        }
        List<Pair> listBuild = CollectionsKt.build(listCreateListBuilder);
        DeprecationAnnotationInfoPerUseSiteStorageBuilder deprecationAnnotationInfoPerUseSiteStorageBuilder = new DeprecationAnnotationInfoPerUseSiteStorageBuilder();
        for (Pair pair : listBuild) {
            FirAnnotation firAnnotation3 = (FirAnnotation) pair.component1();
            boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
            FirTypeRef annotationTypeRef4 = firAnnotation3.getAnnotationTypeRef();
            FirResolvedTypeRef firResolvedTypeRef4 = annotationTypeRef4 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef4 : null;
            ConeKotlinType type4 = firResolvedTypeRef4 != null ? firResolvedTypeRef4.getType() : null;
            ConeClassLikeType coneClassLikeType6 = type4 instanceof ConeClassLikeType ? (ConeClassLikeType) type4 : null;
            if (Intrinsics.areEqual((coneClassLikeType6 == null || (lookupTag3 = coneClassLikeType6.getLookupTag()) == null) ? null : lookupTag3.getClassId(), StandardClassIds.Annotations.INSTANCE.getSinceKotlin())) {
                FirExpression firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(firAnnotation3, StandardClassIds.Annotations.ParameterNames.INSTANCE.getSinceKotlinVersion());
                FirConstExpression firConstExpression = firExpressionFindArgumentByName instanceof FirConstExpression ? (FirConstExpression) firExpressionFindArgumentByName : null;
                Object value = firConstExpression != null ? firConstExpression.getValue() : null;
                String str = value instanceof String ? (String) value : null;
                if (str != null && (apiVersion = ApiVersion.INSTANCE.parse(str)) != null) {
                    List<? extends FirAnnotation> list3 = list;
                    if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                        Iterator<T> it2 = list3.iterator();
                        do {
                            if (it2.hasNext()) {
                                FirTypeRef annotationTypeRef5 = ((FirAnnotation) it2.next()).getAnnotationTypeRef();
                                FirResolvedTypeRef firResolvedTypeRef5 = annotationTypeRef5 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef5 : null;
                                ConeKotlinType type5 = firResolvedTypeRef5 != null ? firResolvedTypeRef5.getType() : null;
                                coneClassLikeType = type5 instanceof ConeClassLikeType ? (ConeClassLikeType) type5 : null;
                            }
                        } while (!Intrinsics.areEqual((coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? null : lookupTag.getClassId(), StandardClassIds.Annotations.INSTANCE.getWasExperimental()));
                    }
                    deprecationAnnotationInfoPerUseSiteStorageBuilder.add(firAnnotation3.getUseSiteTarget(), new SinceKotlinInfo(apiVersion));
                    break;
                }
            } else {
                DeprecationLevelValue deprecationLevel = getDeprecationLevel(firAnnotation3);
                if (deprecationLevel == null) {
                    deprecationLevel = DeprecationLevelValue.WARNING;
                }
                boolean z2 = (zBooleanValue || z) ? false : true;
                Iterator<T> it3 = list.iterator();
                do {
                    if (!it3.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it3.next();
                    FirTypeRef annotationTypeRef6 = ((FirAnnotation) next).getAnnotationTypeRef();
                    FirResolvedTypeRef firResolvedTypeRef6 = annotationTypeRef6 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef6 : null;
                    ConeKotlinType type6 = firResolvedTypeRef6 != null ? firResolvedTypeRef6.getType() : null;
                    coneClassLikeType2 = type6 instanceof ConeClassLikeType ? (ConeClassLikeType) type6 : null;
                } while (!Intrinsics.areEqual((coneClassLikeType2 == null || (lookupTag2 = coneClassLikeType2.getLookupTag()) == null) ? null : lookupTag2.getClassId(), StandardClassIds.Annotations.INSTANCE.getDeprecatedSinceKotlin()));
                FirAnnotation firAnnotation4 = (FirAnnotation) next;
                String stringArgument = FirAnnotationUtilsKt.getStringArgument(firAnnotation3, StandardClassIds.Annotations.ParameterNames.INSTANCE.getDeprecatedMessage());
                if (firAnnotation4 == null) {
                    deprecatedSinceKotlinInfo = new DeprecatedInfo(deprecationLevel, z2, stringArgument);
                } else {
                    deprecatedSinceKotlinInfo = new DeprecatedSinceKotlinInfo(getVersionFromArgument(firAnnotation4, StandardClassIds.Annotations.ParameterNames.INSTANCE.getDeprecatedSinceKotlinWarningSince()), getVersionFromArgument(firAnnotation4, StandardClassIds.Annotations.ParameterNames.INSTANCE.getDeprecatedSinceKotlinErrorSince()), getVersionFromArgument(firAnnotation4, StandardClassIds.Annotations.ParameterNames.INSTANCE.getDeprecatedSinceKotlinHiddenSince()), stringArgument, z2);
                }
                deprecationAnnotationInfoPerUseSiteStorageBuilder.add(firAnnotation3.getUseSiteTarget(), deprecatedSinceKotlinInfo);
            }
        }
        if (list2 != null) {
            Iterator<T> it4 = list2.iterator();
            while (it4.hasNext()) {
                deprecationAnnotationInfoPerUseSiteStorageBuilder.add((AnnotationUseSiteTarget) null, new RequireKotlinInfo((VersionRequirement) it4.next()));
            }
        }
        return deprecationAnnotationInfoPerUseSiteStorageBuilder.build();
    }

    static {
        String strAsString = StandardClassIds.Annotations.INSTANCE.getDeprecated().getShortClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strAsString2 = StandardClassIds.Annotations.Java.INSTANCE.getDeprecated().getShortClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        String strAsString3 = StandardClassIds.Annotations.INSTANCE.getSinceKotlin().getShortClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
        deprecationAnnotationSimpleNames = SetsKt.setOf((Object[]) new String[]{strAsString, strAsString2, strAsString3});
        isHiddenEverywhereBesideSuperCalls$delegate = FirDeclarationDataRegistry.INSTANCE.data(IsHiddenEverywhereBesideSuperCalls.INSTANCE);
    }

    public static final Boolean isHiddenEverywhereBesideSuperCalls(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        return (Boolean) isHiddenEverywhereBesideSuperCalls$delegate.getValue(firCallableDeclaration, $$delegatedProperties[0]);
    }

    public static final void setHiddenEverywhereBesideSuperCalls(FirCallableDeclaration firCallableDeclaration, Boolean bool) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        isHiddenEverywhereBesideSuperCalls$delegate.setValue(firCallableDeclaration, $$delegatedProperties[0], bool);
    }

    public static final DeprecationAnnotationInfoPerUseSiteStorage getDeprecationsAnnotationInfoByUseSiteFromAccessors(FirSession session, FirFunction firFunction, List<? extends FirAnnotation> list, FirFunction firFunction2, List<? extends FirAnnotation> list2) {
        Map<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> storage;
        Map<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> storage2;
        Intrinsics.checkNotNullParameter(session, "session");
        DeprecationAnnotationInfoPerUseSiteStorageBuilder deprecationAnnotationInfoPerUseSiteStorageBuilder = new DeprecationAnnotationInfoPerUseSiteStorageBuilder();
        DeprecationAnnotationInfoPerUseSiteStorage deprecationAnnotationInfoPerUseSiteStorageExtractDeprecationInfoPerUseSite$default = firFunction2 != null ? extractDeprecationInfoPerUseSite$default(firFunction2, session, list2, null, null, 12, null) : null;
        if (deprecationAnnotationInfoPerUseSiteStorageExtractDeprecationInfoPerUseSite$default != null && (storage2 = deprecationAnnotationInfoPerUseSiteStorageExtractDeprecationInfoPerUseSite$default.getStorage()) != null) {
            for (Map.Entry<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> entry : storage2.entrySet()) {
                AnnotationUseSiteTarget key = entry.getKey();
                List<DeprecationAnnotationInfo> value = entry.getValue();
                if (key == null) {
                    deprecationAnnotationInfoPerUseSiteStorageBuilder.add(AnnotationUseSiteTarget.PROPERTY_SETTER, value);
                } else {
                    deprecationAnnotationInfoPerUseSiteStorageBuilder.add(key, value);
                }
            }
        }
        DeprecationAnnotationInfoPerUseSiteStorage deprecationAnnotationInfoPerUseSiteStorageExtractDeprecationInfoPerUseSite$default2 = firFunction != null ? extractDeprecationInfoPerUseSite$default(firFunction, session, list, null, null, 12, null) : null;
        if (deprecationAnnotationInfoPerUseSiteStorageExtractDeprecationInfoPerUseSite$default2 != null && (storage = deprecationAnnotationInfoPerUseSiteStorageExtractDeprecationInfoPerUseSite$default2.getStorage()) != null) {
            for (Map.Entry<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> entry2 : storage.entrySet()) {
                AnnotationUseSiteTarget key2 = entry2.getKey();
                List<DeprecationAnnotationInfo> value2 = entry2.getValue();
                if (key2 == null) {
                    deprecationAnnotationInfoPerUseSiteStorageBuilder.add(AnnotationUseSiteTarget.PROPERTY_GETTER, value2);
                } else {
                    deprecationAnnotationInfoPerUseSiteStorageBuilder.add(key2, value2);
                }
            }
        }
        return deprecationAnnotationInfoPerUseSiteStorageBuilder.build();
    }
}
