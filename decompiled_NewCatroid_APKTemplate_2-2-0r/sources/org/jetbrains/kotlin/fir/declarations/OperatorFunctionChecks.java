package org.jetbrains.kotlin.fir.declarations;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.ScopesKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: OperatorFunctionChecks.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012JE\u0010\u0013\u001a\u00020\u0014*\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\t0\u00152\u0006\u0010\u0016\u001a\u00020\n2\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0018\"\u00020\u0007H\u0002¢\u0006\u0002\u0010\u0019JE\u0010\u0013\u001a\u00020\u0014*\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00050\u001c2\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0018\"\u00020\u0007H\u0002¢\u0006\u0002\u0010\u001dJ?\u0010\u0013\u001a\u00020\u0014*\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u001a2\u0006\u0010\u001e\u001a\u00020\u00052\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0018\"\u00020\u0007H\u0002¢\u0006\u0002\u0010\u001fR \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/OperatorFunctionChecks;", "", "()V", "checksByName", "", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/fir/declarations/Check;", "regexChecks", "Lkotlin/Pair;", "Lkotlin/text/Regex;", "isOperator", "Lorg/jetbrains/kotlin/fir/declarations/CheckResult;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "checkFor", "", "", "regex", "checks", "", "(Ljava/util/List;Lkotlin/text/Regex;[Lorg/jetbrains/kotlin/fir/declarations/Check;)V", "", "names", "", "(Ljava/util/Map;Ljava/util/Set;[Lorg/jetbrains/kotlin/fir/declarations/Check;)V", "name", "(Ljava/util/Map;Lorg/jetbrains/kotlin/name/Name;[Lorg/jetbrains/kotlin/fir/declarations/Check;)V", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OperatorFunctionChecks {
    public static final OperatorFunctionChecks INSTANCE;
    private static final Map<Name, List<Check>> checksByName;
    private static final List<Pair<Regex, List<Check>>> regexChecks;

    private OperatorFunctionChecks() {
    }

    public final CheckResult isOperator(FirSimpleFunction function, FirSession session, ScopeSession scopeSession) {
        Object next;
        Regex regex;
        String strAsString;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(session, "session");
        List<Check> list = checksByName.get(function.getName());
        if (list == null) {
            Iterator<T> it = regexChecks.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                regex = (Regex) ((Pair) next).getFirst();
                strAsString = function.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            } while (!regex.matches(strAsString));
            Pair pair = (Pair) next;
            list = pair != null ? (List) pair.getSecond() : null;
        }
        List<Check> list2 = list;
        if (list2 == null) {
            return CheckResult.IllegalFunctionName.INSTANCE;
        }
        Iterator<Check> it2 = list2.iterator();
        while (it2.hasNext()) {
            String strCheck = it2.next().check(function, session, scopeSession);
            if (strCheck != null) {
                return new CheckResult.IllegalSignature(strCheck);
            }
        }
        return CheckResult.SuccessCheck.INSTANCE;
    }

    static {
        OperatorFunctionChecks operatorFunctionChecks = new OperatorFunctionChecks();
        INSTANCE = operatorFunctionChecks;
        Map<Name, List<Check>> mapCreateMapBuilder = MapsKt.createMapBuilder();
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.GET, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.atLeast(1));
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.SET, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.atLeast(2), Checks.INSTANCE.simple("last parameter should not have a default value or be a vararg", new Function2<FirSimpleFunction, FirSession, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.OperatorFunctionChecks$checksByName$1$1
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSimpleFunction it, FirSession firSession) {
                Intrinsics.checkNotNullParameter(it, "it");
                Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 1>");
                FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.lastOrNull((List) it.getValueParameters());
                boolean z = false;
                if (firValueParameter != null && firValueParameter.getDefaultValue() == null && !firValueParameter.getIsVararg()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }));
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.GET_VALUE, Checks.INSTANCE.getMemberOrExtension(), Checks.INSTANCE.getNoDefaultAndVarargs(), Checks.ValueParametersCount.INSTANCE.atLeast(2), Checks.INSTANCE.isKProperty(), Checks.INSTANCE.getNonSuspend());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.SET_VALUE, Checks.INSTANCE.getMemberOrExtension(), Checks.INSTANCE.getNoDefaultAndVarargs(), Checks.ValueParametersCount.INSTANCE.atLeast(3), Checks.INSTANCE.isKProperty(), Checks.INSTANCE.getNonSuspend());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.PROVIDE_DELEGATE, Checks.INSTANCE.getMemberOrExtension(), Checks.INSTANCE.getNoDefaultAndVarargs(), Checks.ValueParametersCount.INSTANCE.exactly(2), Checks.INSTANCE.isKProperty(), Checks.INSTANCE.getNonSuspend());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.INVOKE, Checks.INSTANCE.getMemberOrExtension());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.CONTAINS, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.getSingle(), Checks.INSTANCE.getNoDefaultAndVarargs(), Checks.Returns.INSTANCE.getBoolean());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.ITERATOR, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.getNone());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.NEXT, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.getNone());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.HAS_NEXT, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.getNone(), Checks.Returns.INSTANCE.getBoolean());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.RANGE_TO, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.getSingle(), Checks.INSTANCE.getNoDefaultAndVarargs());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.RANGE_UNTIL, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.getSingle(), Checks.INSTANCE.getNoDefaultAndVarargs());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.EQUALS, Checks.INSTANCE.getMember(), new Check() { // from class: org.jetbrains.kotlin.fir.declarations.OperatorFunctionChecks$checksByName$1$2
            @Override // org.jetbrains.kotlin.fir.declarations.Check
            public String check(FirSimpleFunction function, FirSession session, ScopeSession scopeSession) {
                ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag;
                FirRegularClassSymbol firRegularClassSymbol;
                StringBuilder sb;
                Intrinsics.checkNotNullParameter(function, "function");
                Intrinsics.checkNotNullParameter(session, "session");
                if (scopeSession == null || (coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(function)) == null || (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, session)) == null) {
                    return null;
                }
                boolean zSupportsFeature = FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.CustomEqualsInValueClasses);
                FirRegularClassSymbol firRegularClassSymbol2 = firRegularClassSymbol;
                List<FirFunctionSymbol<?>> listOverriddenFunctions = ScopesKt.overriddenFunctions(function.getSymbol(), firRegularClassSymbol2, session, scopeSession);
                if ((listOverriddenFunctions instanceof Collection) && listOverriddenFunctions.isEmpty()) {
                    if (zSupportsFeature) {
                    }
                    sb = new StringBuilder("must override ''equals()'' in Any");
                    if (zSupportsFeature) {
                        sb.append(" or define ''equals(other: " + ConeTypeUtilsKt.renderReadable(ConeTypeUtilsKt.replaceArgumentsWithStarProjections(ScopeUtilsKt.defaultType(firRegularClassSymbol2))) + "): Boolean''");
                    }
                    String string = sb.toString();
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    return string;
                }
                Iterator<T> it = listOverriddenFunctions.iterator();
                while (it.hasNext()) {
                    ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag2 = ClassMembersKt.containingClassLookupTag((FirFunctionSymbol) it.next());
                    if (Intrinsics.areEqual(coneClassLikeLookupTagContainingClassLookupTag2 != null ? coneClassLikeLookupTagContainingClassLookupTag2.getClassId() : null, StandardClassIds.INSTANCE.getAny())) {
                    }
                }
                if (zSupportsFeature || !ValueClassesUtilsKt.isTypedEqualsInValueClass(function, session)) {
                    sb = new StringBuilder("must override ''equals()'' in Any");
                    if (zSupportsFeature && firRegularClassSymbol.getRawStatus().isInline()) {
                        sb.append(" or define ''equals(other: " + ConeTypeUtilsKt.renderReadable(ConeTypeUtilsKt.replaceArgumentsWithStarProjections(ScopeUtilsKt.defaultType(firRegularClassSymbol2))) + "): Boolean''");
                    }
                    String string2 = sb.toString();
                    Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                    return string2;
                }
                return null;
            }
        });
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.COMPARE_TO, Checks.INSTANCE.getMemberOrExtension(), Checks.Returns.INSTANCE.getInt(), Checks.ValueParametersCount.INSTANCE.getSingle(), Checks.INSTANCE.getNoDefaultAndVarargs());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.BINARY_OPERATION_NAMES, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.getSingle(), Checks.INSTANCE.getNoDefaultAndVarargs());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.SIMPLE_UNARY_OPERATION_NAMES, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.getNone());
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, SetsKt.setOf((Object[]) new Name[]{OperatorNameConventions.INC, OperatorNameConventions.DEC}), Checks.INSTANCE.getMemberOrExtension(), Checks.INSTANCE.full("receiver must be a supertype of the return type", new Function2<FirSession, FirSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.fir.declarations.OperatorFunctionChecks$checksByName$1$3
            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirSession session, FirSimpleFunction function) {
                ConeSimpleKotlinType coneType;
                FirTypeRef typeRef;
                Intrinsics.checkNotNullParameter(session, "session");
                Intrinsics.checkNotNullParameter(function, "function");
                ConeSimpleKotlinType dispatchReceiverType = function.getDispatchReceiverType();
                if (dispatchReceiverType != null) {
                    coneType = dispatchReceiverType;
                } else {
                    FirReceiverParameter receiverParameter = function.getReceiverParameter();
                    coneType = (receiverParameter == null || (typeRef = receiverParameter.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef);
                    if (coneType == null) {
                        return false;
                    }
                }
                return Boolean.valueOf(TypeUtilsKt.isSubtypeOf(FirTypeUtilsKt.getConeType(function.getReturnTypeRef()), TypeComponentsKt.getTypeContext(session), coneType));
            }
        }));
        operatorFunctionChecks.checkFor(mapCreateMapBuilder, OperatorNameConventions.ASSIGNMENT_OPERATIONS, Checks.INSTANCE.getMemberOrExtension(), Checks.Returns.INSTANCE.getUnit(), Checks.ValueParametersCount.INSTANCE.getSingle(), Checks.INSTANCE.getNoDefaultAndVarargs());
        checksByName = MapsKt.build(mapCreateMapBuilder);
        List<Pair<Regex, List<Check>>> listCreateListBuilder = CollectionsKt.createListBuilder();
        operatorFunctionChecks.checkFor(listCreateListBuilder, OperatorNameConventions.COMPONENT_REGEX, Checks.INSTANCE.getMemberOrExtension(), Checks.ValueParametersCount.INSTANCE.getNone());
        regexChecks = CollectionsKt.build(listCreateListBuilder);
    }

    private final void checkFor(Map<Name, List<Check>> map, Name name2, Check... checkArr) {
        map.put(name2, ArraysKt.asList(checkArr));
    }

    private final void checkFor(Map<Name, List<Check>> map, Set<Name> set, Check... checkArr) {
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            map.put((Name) it.next(), ArraysKt.asList(checkArr));
        }
    }

    private final void checkFor(List<Pair<Regex, List<Check>>> list, Regex regex, Check... checkArr) {
        list.add(TuplesKt.to(regex, ArraysKt.asList(checkArr)));
    }
}
