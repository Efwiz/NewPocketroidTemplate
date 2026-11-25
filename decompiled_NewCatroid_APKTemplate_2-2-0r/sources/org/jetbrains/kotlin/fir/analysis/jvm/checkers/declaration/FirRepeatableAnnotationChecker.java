package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryForDeprecation4;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirClassReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirRepeatableAnnotationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J2\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J2\u0010\u0015\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J2\u0010\u0016\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J(\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J(\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J2\u0010\u001c\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u000e\u0010\u001d\u001a\u0004\u0018\u00010\u001e*\u00020\u0019H\u0002J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u001e*\u0006\u0012\u0002\b\u00030\u001f2\u0006\u0010 \u001a\u00020!H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirRepeatableAnnotationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "REPEATABLE_ANNOTATION_CONTAINER_NAME", "Lorg/jetbrains/kotlin/name/Name;", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkContainerParameters", "containerClass", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "annotationClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "annotationSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "checkContainerRetention", "checkContainerTarget", "checkJavaRepeatableAnnotationDeclaration", "javaRepeatable", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "checkKotlinRepeatableAnnotationDeclaration", "kotlinRepeatable", "checkRepeatableAnnotationContainer", "resolveContainerAnnotation", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirRepeatableAnnotationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirRepeatableAnnotationChecker INSTANCE = new FirRepeatableAnnotationChecker();
    private static final Name REPEATABLE_ANNOTATION_CONTAINER_NAME;

    private FirRepeatableAnnotationChecker() {
    }

    static {
        Name nameIdentifier = Name.identifier(JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        REPEATABLE_ANNOTATION_CONTAINER_NAME = nameIdentifier;
    }

    /* JADX WARN: Code duplicated, block: B:78:0x017d  */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        ClassId annotationClassId;
        FirClassLikeSymbol<?> classLikeSymbolByClassId;
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        List<FirAnnotation> annotations = declaration.getAnnotations();
        if (annotations.isEmpty()) {
            return;
        }
        HashMap map = new HashMap();
        FirSession session = context.getSession();
        for (FirAnnotation firAnnotation : annotations) {
            FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
            ClassId classId = null;
            FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
            if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
                classId = lookupTag.getClassId();
            }
            if (classId != null && (annotationClassId = FirAnnotationUtilsKt.toAnnotationClassId(firAnnotation, session)) != null && !annotationClassId.isLocal() && (classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(session).getClassLikeSymbolByClassId(annotationClassId)) != null) {
                AnnotationUseSiteTarget useSiteTarget = firAnnotation.getUseSiteTarget();
                ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef()), context.getSession());
                HashMap map2 = map;
                Object obj = map2.get(coneKotlinTypeFullyExpandedType);
                if (obj == null) {
                    obj = (List) new ArrayList();
                    map2.put(coneKotlinTypeFullyExpandedType, obj);
                }
                List list = (List) obj;
                if (!list.contains(useSiteTarget)) {
                    List list2 = list;
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        Iterator it = list2.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                boolean z = true;
                                boolean z2 = ((AnnotationUseSiteTarget) it.next()) == null;
                                if (useSiteTarget != null) {
                                    z = false;
                                }
                                if (z2 != z) {
                                    if (FirAnnotationHelpersKt.containsRepeatableAnnotation(classLikeSymbolByClassId, session) || FirAnnotationHelpersKt.getAnnotationRetention(classLikeSymbolByClassId, session) == AnnotationRetention.SOURCE) {
                                        map = map;
                                        useSiteTarget = useSiteTarget;
                                    } else if (FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.RepeatableAnnotations)) {
                                        ClassId classIdResolveContainerAnnotation = resolveContainerAnnotation(classLikeSymbolByClassId, session);
                                        if (classIdResolveContainerAnnotation != null) {
                                            List<FirAnnotation> list3 = annotations;
                                            if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                                                Iterator<T> it2 = list3.iterator();
                                                while (true) {
                                                    if (it2.hasNext()) {
                                                        if (Intrinsics.areEqual(FirAnnotationUtilsKt.toAnnotationClassId((FirAnnotation) it2.next(), session), classIdResolveContainerAnnotation)) {
                                                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirJvmErrors.INSTANCE.getREPEATED_ANNOTATION_WITH_CONTAINER(), classId, classIdResolveContainerAnnotation, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                                                            break;
                                                        }
                                                    } else {
                                                        break;
                                                    }
                                                }
                                            } else {
                                                break;
                                                break;
                                            }
                                        } else {
                                            map = map;
                                            useSiteTarget = useSiteTarget;
                                        }
                                    } else {
                                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirJvmErrors.INSTANCE.getNON_SOURCE_REPEATED_ANNOTATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                    }
                                }
                            }
                        }
                    }
                    map = map;
                    useSiteTarget = useSiteTarget;
                } else if (FirAnnotationHelpersKt.containsRepeatableAnnotation(classLikeSymbolByClassId, session)) {
                    map = map;
                    useSiteTarget = useSiteTarget;
                } else {
                    map = map;
                    useSiteTarget = useSiteTarget;
                }
                list.add(useSiteTarget);
                map = map;
            }
        }
        if (declaration instanceof FirRegularClass) {
            FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(annotations, StandardClassIds.Annotations.Java.INSTANCE.getRepeatable(), session);
            if (annotationByClassId != null) {
                checkJavaRepeatableAnnotationDeclaration(annotationByClassId, (FirRegularClass) declaration, context, reporter);
                return;
            }
            FirAnnotation annotationByClassId2 = FirAnnotationUtilsKt.getAnnotationByClassId(annotations, StandardClassIds.Annotations.INSTANCE.getRepeatable(), session);
            if (annotationByClassId2 != null) {
                checkKotlinRepeatableAnnotationDeclaration(annotationByClassId2, (FirRegularClass) declaration, context, reporter);
            }
        }
    }

    private final ClassId resolveContainerAnnotation(FirClassLikeSymbol<?> firClassLikeSymbol, FirSession firSession) {
        FirClassLikeSymbol<?> firClassLikeSymbol2 = firClassLikeSymbol;
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firClassLikeSymbol2, StandardClassIds.Annotations.INSTANCE.getRepeatable(), firSession);
        if (annotationByClassId == null && (annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firClassLikeSymbol2, StandardClassIds.Annotations.Java.INSTANCE.getRepeatable(), firSession)) == null) {
            return null;
        }
        return resolveContainerAnnotation(annotationByClassId);
    }

    private final ClassId resolveContainerAnnotation(FirAnnotation firAnnotation) {
        FirExpression argument;
        FirExpression firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(firAnnotation, StandardClassIds.Annotations.ParameterNames.INSTANCE.getValue());
        if (firExpressionFindArgumentByName == null) {
            return null;
        }
        FirGetClassCall firGetClassCall = firExpressionFindArgumentByName instanceof FirGetClassCall ? (FirGetClassCall) firExpressionFindArgumentByName : null;
        if (firGetClassCall != null && (argument = firGetClassCall.getArgument()) != null) {
            if (argument instanceof FirResolvedQualifier) {
                return ((FirResolvedQualifier) argument).getClassId();
            }
            if (argument instanceof FirClassReferenceExpression) {
                ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(FirTypeUtilsKt.getConeType(((FirClassReferenceExpression) argument).getClassTypeRef()));
                ConeClassLikeType coneClassLikeType = coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeClassLikeType ? (ConeClassLikeType) coneSimpleKotlinTypeLowerBoundIfFlexible : null;
                if (coneClassLikeType == null) {
                    return null;
                }
                return coneClassLikeType.getLookupTag().getClassId();
            }
        }
        return null;
    }

    private final void checkJavaRepeatableAnnotationDeclaration(FirAnnotation javaRepeatable, FirRegularClass annotationClass, CheckerContext context, DiagnosticReporter reporter) {
        ClassId classIdResolveContainerAnnotation = resolveContainerAnnotation(javaRepeatable);
        if (classIdResolveContainerAnnotation == null) {
            return;
        }
        FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(context.getSession()).getClassLikeSymbolByClassId(classIdResolveContainerAnnotation);
        FirRegularClassSymbol firRegularClassSymbol = classLikeSymbolByClassId instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbolByClassId : null;
        if (firRegularClassSymbol == null) {
            return;
        }
        checkRepeatableAnnotationContainer(annotationClass, firRegularClassSymbol, javaRepeatable.getSource(), context, reporter);
    }

    private final void checkKotlinRepeatableAnnotationDeclaration(FirAnnotation kotlinRepeatable, FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        if (FirScopeKt.getSingleClassifier(FirHelpersKt.unsubstitutedScope(declaration, context), REPEATABLE_ANNOTATION_CONTAINER_NAME) != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, kotlinRepeatable.getSource(), FirJvmErrors.INSTANCE.getREPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkRepeatableAnnotationContainer(FirRegularClass annotationClass, FirRegularClassSymbol containerClass, KtSourceElement annotationSource, CheckerContext context, DiagnosticReporter reporter) {
        checkContainerParameters(containerClass, annotationClass, annotationSource, context, reporter);
        checkContainerRetention(containerClass, annotationClass, annotationSource, context, reporter);
        checkContainerTarget(containerClass, annotationClass, annotationSource, context, reporter);
    }

    private final void checkContainerParameters(FirRegularClassSymbol containerClass, FirRegularClass annotationClass, KtSourceElement annotationSource, CheckerContext context, DiagnosticReporter reporter) {
        Object obj;
        Object next;
        Object next2;
        Iterator<T> it = containerClass.getDeclarationSymbols().iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            FirBasedSymbol firBasedSymbol = (FirBasedSymbol) next;
            if ((firBasedSymbol instanceof FirConstructorSymbol) && ((FirConstructorSymbol) firBasedSymbol).isPrimary()) {
                break;
            }
        }
        FirConstructorSymbol firConstructorSymbol = next instanceof FirConstructorSymbol ? (FirConstructorSymbol) next : null;
        if (firConstructorSymbol == null) {
            return;
        }
        List<FirValueParameterSymbol> valueParameterSymbols = firConstructorSymbol.getValueParameterSymbols();
        Name value = StandardClassIds.Annotations.ParameterNames.INSTANCE.getValue();
        List<FirValueParameterSymbol> list = valueParameterSymbols;
        Iterator<T> it2 = list.iterator();
        do {
            if (!it2.hasNext()) {
                next2 = null;
                break;
            }
            next2 = it2.next();
        } while (!Intrinsics.areEqual(((FirValueParameterSymbol) next2).getName(), value));
        FirValueParameterSymbol firValueParameterSymbol = (FirValueParameterSymbol) next2;
        if (firValueParameterSymbol == null || !ConeBuiltinTypeUtilsKt.isArrayType(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firValueParameterSymbol.getResolvedReturnTypeRef()), context.getSession())) || !Intrinsics.areEqual(ConeTypeProjectionKt.getType((ConeTypeProjection) ArraysKt.single(firValueParameterSymbol.getResolvedReturnTypeRef().getType().getTypeArguments())), ScopeUtilsKt.defaultType(annotationClass))) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationSource, FirJvmErrors.INSTANCE.getREPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY(), containerClass.getClassId(), FirDeclarationUtilKt.getClassId(annotationClass), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return;
        }
        for (Object obj2 : list) {
            FirValueParameterSymbol firValueParameterSymbol2 = (FirValueParameterSymbol) obj2;
            if (!Intrinsics.areEqual(firValueParameterSymbol2.getName(), value) && !firValueParameterSymbol2.getHasDefaultValue()) {
                obj = obj2;
                break;
            }
        }
        FirValueParameterSymbol firValueParameterSymbol3 = (FirValueParameterSymbol) obj;
        if (firValueParameterSymbol3 != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationSource, FirJvmErrors.INSTANCE.getREPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER(), containerClass.getClassId(), firValueParameterSymbol3.getName(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
    }

    private final void checkContainerRetention(FirRegularClassSymbol containerClass, FirRegularClass annotationClass, KtSourceElement annotationSource, CheckerContext context, DiagnosticReporter reporter) {
        AnnotationRetention annotationRetention = FirAnnotationHelpersKt.getAnnotationRetention(annotationClass.getSymbol(), context.getSession());
        AnnotationRetention annotationRetention2 = FirAnnotationHelpersKt.getAnnotationRetention(containerClass, context.getSession());
        if (annotationRetention2.compareTo(annotationRetention) < 0) {
            KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) annotationSource, (KtDiagnosticFactoryForDeprecation4<ClassId, String, ClassId, String>) ((KtDiagnosticFactoryForDeprecation4<Object, Object, Object, Object>) FirJvmErrors.INSTANCE.getREPEATABLE_CONTAINER_HAS_SHORTER_RETENTION()), containerClass.getClassId(), annotationRetention2.name(), FirDeclarationUtilKt.getClassId(annotationClass), annotationRetention.name(), (DiagnosticContext) context, (128 & 128) != 0 ? null : null);
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0058  */
    private final void checkContainerTarget(FirRegularClassSymbol containerClass, FirRegularClass annotationClass, KtSourceElement annotationSource, CheckerContext context, DiagnosticReporter reporter) {
        Set<KotlinTarget> allowedAnnotationTargets = FirAnnotationHelpersKt.getAllowedAnnotationTargets(annotationClass, context.getSession());
        for (KotlinTarget kotlinTarget : FirAnnotationHelpersKt.getAllowedAnnotationTargets(containerClass, context.getSession())) {
            boolean zContains = true;
            if (!allowedAnnotationTargets.contains(kotlinTarget)) {
                if (kotlinTarget == KotlinTarget.ANNOTATION_CLASS) {
                    if (!allowedAnnotationTargets.contains(KotlinTarget.CLASS) && !allowedAnnotationTargets.contains(KotlinTarget.TYPE)) {
                        zContains = false;
                    }
                } else if (kotlinTarget == KotlinTarget.CLASS) {
                    zContains = allowedAnnotationTargets.contains(KotlinTarget.TYPE);
                } else if (kotlinTarget == KotlinTarget.TYPE_PARAMETER) {
                    zContains = allowedAnnotationTargets.contains(KotlinTarget.TYPE);
                } else {
                    zContains = false;
                }
            }
            if (!zContains) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationSource, FirJvmErrors.INSTANCE.getREPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET(), containerClass.getClassId(), FirDeclarationUtilKt.getClassId(annotationClass), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                return;
            }
        }
    }
}
