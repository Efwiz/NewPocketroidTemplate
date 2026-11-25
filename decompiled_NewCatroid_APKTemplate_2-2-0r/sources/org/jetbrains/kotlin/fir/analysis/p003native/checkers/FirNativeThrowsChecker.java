package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.annotations.ThrowUtilKt;

/* JADX INFO: compiled from: FirNativeThrowsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003:\u0001$B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J*\u0010\u0012\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u00022\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u001a\u0010\u0016\u001a\u00020\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J.\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00170\u001b2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 *\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u000e\u0010\"\u001a\u00020\u0013*\u0004\u0018\u00010#H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeThrowsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "cancellationExceptionAndSupersClassIds", "", "Lorg/jetbrains/kotlin/name/ClassId;", "cancellationExceptionFqName", "Lorg/jetbrains/kotlin/name/FqName;", "throwsClassId", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkInheritance", "", "throwsAnnotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "decodeThrowsFilter", "Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeThrowsChecker$ThrowsFilter;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getInheritedThrows", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "getClassTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "hasUnresolvedArgument", "Lorg/jetbrains/kotlin/fir/FirElement;", "ThrowsFilter", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeThrowsChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirNativeThrowsChecker INSTANCE = new FirNativeThrowsChecker();
    private static final Set<ClassId> cancellationExceptionAndSupersClassIds;
    private static final FqName cancellationExceptionFqName;
    private static final ClassId throwsClassId;

    private FirNativeThrowsChecker() {
    }

    static {
        ClassId classId = ClassId.topLevel(ThrowUtilKt.getKOTLIN_THROWS_ANNOTATION_FQ_NAME());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        throwsClassId = classId;
        FqName fqName = new FqName("kotlin.coroutines.cancellation.CancellationException");
        cancellationExceptionFqName = fqName;
        cancellationExceptionAndSupersClassIds = SetsKt.setOf((Object[]) new ClassId[]{ClassId.topLevel(StandardNames.FqNames.throwable), ClassId.topLevel(new FqName("kotlin.Exception")), ClassId.topLevel(new FqName("kotlin.RuntimeException")), ClassId.topLevel(new FqName("kotlin.IllegalStateException")), ClassId.topLevel(fqName)});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        List<ConeKotlinType> classTypes;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(declaration, throwsClassId, context.getSession());
        FirAnnotationCall firAnnotationCall = annotationByClassId instanceof FirAnnotationCall ? (FirAnnotationCall) annotationByClassId : null;
        if (!checkInheritance(declaration, firAnnotationCall, context, reporter) || hasUnresolvedArgument(firAnnotationCall) || firAnnotationCall == null || (classTypes = getClassTypes(firAnnotationCall, context.getSession())) == null) {
            return;
        }
        if (classTypes.isEmpty()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotationCall.getSource(), FirNativeErrors.INSTANCE.getTHROWS_LIST_EMPTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        KtModifierKeywordToken SUSPEND_KEYWORD = KtTokens.SUSPEND_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(SUSPEND_KEYWORD, "SUSPEND_KEYWORD");
        if (FirKeywordUtilsKt.hasModifier(declaration, SUSPEND_KEYWORD)) {
            List<ConeKotlinType> list = classTypes;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (CollectionsKt.contains(cancellationExceptionAndSupersClassIds, ConeTypeUtilsKt.getClassId((ConeKotlinType) it.next()))) {
                        return;
                    }
                }
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotationCall.getSource(), FirNativeErrors.INSTANCE.getMISSING_EXCEPTION_IN_THROWS_ON_SUSPEND(), cancellationExceptionFqName, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private final boolean checkInheritance(FirDeclaration declaration, FirAnnotationCall throwsAnnotation, CheckerContext context, DiagnosticReporter reporter) {
        if (!(declaration instanceof FirSimpleFunction)) {
            return true;
        }
        Set<Map.Entry<FirNamedFunctionSymbol, ThrowsFilter>> setEntrySet = getInheritedThrows((FirSimpleFunction) declaration, throwsAnnotation, context).entrySet();
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        for (Object obj : setEntrySet) {
            if (hashSet.add((ThrowsFilter) ((Map.Entry) obj).getValue())) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.size() >= 2) {
            KtSourceElement source = declaration.getSource();
            KtDiagnosticFactory1<Collection<FirRegularClassSymbol>> incompatible_throws_inherited = FirNativeErrors.INSTANCE.getINCOMPATIBLE_THROWS_INHERITED();
            ArrayList arrayList3 = new ArrayList();
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) ((Map.Entry) it.next()).getKey());
                FirRegularClassSymbol firRegularClassSymbol = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, context.getSession()) : null;
                if (firRegularClassSymbol != null) {
                    arrayList3.add(firRegularClassSymbol);
                }
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, incompatible_throws_inherited, arrayList3, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return false;
        }
        Map.Entry entry = (Map.Entry) CollectionsKt.firstOrNull((List) arrayList2);
        if (entry == null) {
            return true;
        }
        FirNamedFunctionSymbol firNamedFunctionSymbol = (FirNamedFunctionSymbol) entry.getKey();
        ThrowsFilter throwsFilter = (ThrowsFilter) entry.getValue();
        if ((throwsAnnotation != null ? throwsAnnotation.getSource() : null) == null || Intrinsics.areEqual(decodeThrowsFilter(throwsAnnotation, context.getSession()), throwsFilter)) {
            return true;
        }
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag2 = ClassMembersKt.containingClassLookupTag(firNamedFunctionSymbol);
        FirRegularClassSymbol firRegularClassSymbol2 = coneClassLikeLookupTagContainingClassLookupTag2 != null ? LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag2, context.getSession()) : null;
        if (firRegularClassSymbol2 != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, throwsAnnotation.getSource(), FirNativeErrors.INSTANCE.getINCOMPATIBLE_THROWS_OVERRIDE(), firRegularClassSymbol2, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
        return false;
    }

    private final Map<FirNamedFunctionSymbol, ThrowsFilter> getInheritedThrows(FirSimpleFunction function, FirAnnotationCall throwsAnnotation, CheckerContext context) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        getInheritedThrows$getInheritedThrows(linkedHashSet, context, function, linkedHashMap, throwsAnnotation, function.getSymbol());
        return linkedHashMap;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0072  */
    private static final void getInheritedThrows$getInheritedThrows(Set<FirNamedFunctionSymbol> set, CheckerContext checkerContext, FirSimpleFunction firSimpleFunction, Map<FirNamedFunctionSymbol, ThrowsFilter> map, FirAnnotationCall firAnnotationCall, FirNamedFunctionSymbol firNamedFunctionSymbol) {
        FirAnnotationCall firAnnotationCall2;
        if (set.add(firNamedFunctionSymbol)) {
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firNamedFunctionSymbol);
            FirRegularClassSymbol firRegularClassSymbol = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, checkerContext.getSession()) : null;
            if (firRegularClassSymbol != null) {
                FirTypeScope firTypeScopeUnsubstitutedScope = FirHelpersKt.unsubstitutedScope(firRegularClassSymbol, checkerContext);
                firTypeScopeUnsubstitutedScope.processFunctionsByName(firNamedFunctionSymbol.getName(), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.native.checkers.FirNativeThrowsChecker$getInheritedThrows$getInheritedThrows$1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirNamedFunctionSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol2) {
                        invoke2(firNamedFunctionSymbol2);
                        return Unit.INSTANCE;
                    }
                });
                List<FirNamedFunctionSymbol> directOverriddenFunctions$default = FirTypeScopeKt.getDirectOverriddenFunctions$default(firTypeScopeUnsubstitutedScope, firNamedFunctionSymbol, false, 2, null);
                if (Intrinsics.areEqual(firNamedFunctionSymbol, firSimpleFunction.getSymbol()) || (firAnnotationCall == null && !directOverriddenFunctions$default.isEmpty())) {
                    for (FirNamedFunctionSymbol firNamedFunctionSymbol2 : directOverriddenFunctions$default) {
                        if (ClassMembersKt.isSubstitutionOrIntersectionOverride(firNamedFunctionSymbol2)) {
                            firAnnotationCall2 = null;
                        } else {
                            FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firNamedFunctionSymbol2, throwsClassId, checkerContext.getSession());
                            if (annotationByClassId instanceof FirAnnotationCall) {
                                firAnnotationCall2 = (FirAnnotationCall) annotationByClassId;
                            } else {
                                firAnnotationCall2 = null;
                            }
                        }
                        getInheritedThrows$getInheritedThrows(set, checkerContext, firSimpleFunction, map, firAnnotationCall2, firNamedFunctionSymbol2);
                    }
                    return;
                }
                map.put(firNamedFunctionSymbol, INSTANCE.decodeThrowsFilter(firAnnotationCall, checkerContext.getSession()));
            }
        }
    }

    private final boolean hasUnresolvedArgument(FirElement firElement) {
        if (firElement instanceof FirWrappedArgumentExpression) {
            return hasUnresolvedArgument(((FirWrappedArgumentExpression) firElement).getExpression());
        }
        if ((firElement instanceof FirResolvable) && FirReferenceUtilsKt.isError(((FirResolvable) firElement).getCalleeReference())) {
            return true;
        }
        if (firElement instanceof FirVarargArgumentsExpression) {
            Iterator<FirExpression> it = ((FirVarargArgumentsExpression) firElement).getArguments().iterator();
            while (it.hasNext()) {
                if (hasUnresolvedArgument(it.next())) {
                    return true;
                }
            }
        }
        if (!(firElement instanceof FirCall)) {
            return false;
        }
        Iterator<FirExpression> it2 = ((FirCall) firElement).getArgumentList().getArguments().iterator();
        while (it2.hasNext()) {
            if (hasUnresolvedArgument(it2.next())) {
                return true;
            }
        }
        return false;
    }

    private final ThrowsFilter decodeThrowsFilter(FirAnnotationCall throwsAnnotation, FirSession session) {
        List<ConeKotlinType> classTypes;
        return new ThrowsFilter((throwsAnnotation == null || (classTypes = getClassTypes(throwsAnnotation, session)) == null) ? null : CollectionsKt.toSet(classTypes));
    }

    private final List<ConeKotlinType> getClassTypes(FirAnnotationCall firAnnotationCall, FirSession firSession) {
        List<FirExpression> arguments;
        Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) firAnnotationCall.getArgumentList().getArguments());
        FirVarargArgumentsExpression firVarargArgumentsExpression = objFirstOrNull instanceof FirVarargArgumentsExpression ? (FirVarargArgumentsExpression) objFirstOrNull : null;
        if (firVarargArgumentsExpression == null || (arguments = firVarargArgumentsExpression.getArguments()) == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : arguments) {
            if (obj instanceof FirGetClassCall) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((FirExpression) CollectionsKt.first((List) ((FirGetClassCall) it.next()).getArgumentList().getArguments())).getTypeRef());
        }
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : arrayList3) {
            if (obj2 instanceof FirResolvedTypeRef) {
                arrayList4.add(obj2);
            }
        }
        ArrayList arrayList5 = arrayList4;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it2 = arrayList5.iterator();
        while (it2.hasNext()) {
            arrayList6.add(TypeExpansionUtilsKt.fullyExpandedType(((FirResolvedTypeRef) it2.next()).getType(), firSession));
        }
        return arrayList6;
    }

    /* JADX INFO: compiled from: FirNativeThrowsChecker.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\u001b\u0010\t\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0019\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeThrowsChecker$ThrowsFilter;", "", "classes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Ljava/util/Set;)V", "getClasses", "()Ljava/util/Set;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class ThrowsFilter {
        private final Set<ConeKotlinType> classes;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ThrowsFilter copy$default(ThrowsFilter throwsFilter, Set set, int i, Object obj) {
            if ((i & 1) != 0) {
                set = throwsFilter.classes;
            }
            return throwsFilter.copy(set);
        }

        public final Set<ConeKotlinType> component1() {
            return this.classes;
        }

        public final ThrowsFilter copy(Set<? extends ConeKotlinType> classes) {
            return new ThrowsFilter(classes);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof ThrowsFilter) && Intrinsics.areEqual(this.classes, ((ThrowsFilter) other).classes);
        }

        public int hashCode() {
            Set<ConeKotlinType> set = this.classes;
            if (set == null) {
                return 0;
            }
            return set.hashCode();
        }

        public String toString() {
            return "ThrowsFilter(classes=" + this.classes + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ThrowsFilter(Set<? extends ConeKotlinType> set) {
            this.classes = set;
        }

        public final Set<ConeKotlinType> getClasses() {
            return this.classes;
        }
    }
}
