package org.jetbrains.kotlin.fir.analysis.checkers;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirOuterClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImplKt;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.util.ListMultimap;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirConflictsHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a$\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014\u001a\"\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u00162\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000fH\u0002\u001a\u0018\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001aH\u0002\u001a\f\u0010\u001e\u001a\u00020\u0005*\u00020\u001aH\u0002\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0018\u0010\u0004\u001a\u00020\u0005*\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0018\u0010\t\u001a\u00020\u0005*\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000b¨\u0006\u001f"}, d2 = {"DEFAULT_STATUS_FOR_NORMAL_MAIN_FUNCTION", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedDeclarationStatusImpl;", "getDEFAULT_STATUS_FOR_NORMAL_MAIN_FUNCTION", "()Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedDeclarationStatusImpl;", "hasMainFunctionStatus", "", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "getHasMainFunctionStatus", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;)Z", "isTopLevel", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)Z", "checkForLocalRedeclarations", "", PathTree.NAME_elements, "", "Lorg/jetbrains/kotlin/fir/FirElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "groupTopLevelByName", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/DeclarationBuckets;", "declarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isExpectAndActual", "declaration1", "declaration2", "isCollectable", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirConflictsHelpersKt {
    private static final FirResolvedDeclarationStatusImpl DEFAULT_STATUS_FOR_NORMAL_MAIN_FUNCTION = FirResolvedDeclarationStatusImpl.INSTANCE.getDEFAULT_STATUS_FOR_STATUSLESS_DECLARATIONS();

    public static final FirResolvedDeclarationStatusImpl getDEFAULT_STATUS_FOR_NORMAL_MAIN_FUNCTION() {
        return DEFAULT_STATUS_FOR_NORMAL_MAIN_FUNCTION;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getHasMainFunctionStatus(FirSimpleFunction firSimpleFunction) {
        Object modifiersRepresentation = FirDeclarationStatusImplKt.getModifiersRepresentation(firSimpleFunction.getStatus());
        if (Intrinsics.areEqual(modifiersRepresentation, FirDeclarationStatusImplKt.getModifiersRepresentation(DEFAULT_STATUS_FOR_NORMAL_MAIN_FUNCTION))) {
            return true;
        }
        return Intrinsics.areEqual(modifiersRepresentation, FirDeclarationStatusImplKt.getModifiersRepresentation(FirResolvedDeclarationStatusImpl.INSTANCE.getDEFAULT_STATUS_FOR_SUSPEND_MAIN_FUNCTION()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isTopLevel(CallableId callableId) {
        return callableId.getClassName() == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isCollectable(FirDeclaration firDeclaration) {
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        if (firDeclaration instanceof FirCallableDeclaration) {
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firDeclaration;
            List<FirContextReceiver> contextReceivers = firCallableDeclaration.getContextReceivers();
            if (!(contextReceivers instanceof Collection) || !contextReceivers.isEmpty()) {
                Iterator<T> it = contextReceivers.iterator();
                while (it.hasNext()) {
                    if (ConeTypeUtilsKt.hasError(FirTypeUtilsKt.getConeType(((FirContextReceiver) it.next()).getTypeRef()))) {
                        return false;
                    }
                }
            }
            List<FirTypeParameterRef> typeParameters = firCallableDeclaration.getTypeParameters();
            if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
                Iterator<T> it2 = typeParameters.iterator();
                while (it2.hasNext()) {
                    if (ConeTypeUtilsKt.hasError(FirNestedClassifierScopeKt.toConeType((FirTypeParameterRef) it2.next()))) {
                        return false;
                    }
                }
            }
            FirReceiverParameter receiverParameter = firCallableDeclaration.getReceiverParameter();
            if (receiverParameter != null && (typeRef = receiverParameter.getTypeRef()) != null && (coneType = FirTypeUtilsKt.getConeType(typeRef)) != null && ConeTypeUtilsKt.hasError(coneType)) {
                return false;
            }
            if (firDeclaration instanceof FirFunction) {
                List<FirValueParameter> valueParameters = ((FirFunction) firDeclaration).getValueParameters();
                if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
                    Iterator<T> it3 = valueParameters.iterator();
                    while (it3.hasNext()) {
                        if (ConeTypeUtilsKt.hasError(FirTypeUtilsKt.getConeType(((FirValueParameter) it3.next()).getReturnTypeRef()))) {
                            return false;
                        }
                    }
                }
            }
        }
        if (firDeclaration instanceof FirSimpleFunction) {
            KtSourceElement source = firDeclaration.getSource();
            if (!((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) && !Intrinsics.areEqual(((FirSimpleFunction) firDeclaration).getName(), SpecialNames.NO_NAME_PROVIDED)) {
                return true;
            }
        } else if (firDeclaration instanceof FirRegularClass) {
            if (!Intrinsics.areEqual(((FirRegularClass) firDeclaration).getName(), SpecialNames.NO_NAME_PROVIDED)) {
                return true;
            }
        } else if (firDeclaration instanceof FirProperty) {
            KtSourceElement source2 = firDeclaration.getSource();
            if (!((source2 != null ? source2.getKind() : null) instanceof KtFakeSourceElementKind.EnumGeneratedDeclaration)) {
                return true;
            }
        } else {
            if (!(firDeclaration instanceof FirField)) {
                return true;
            }
            KtSourceElement source3 = firDeclaration.getSource();
            if (!Intrinsics.areEqual(source3 != null ? source3.getKind() : null, KtFakeSourceElementKind.ClassDelegationField.INSTANCE)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isExpectAndActual(FirDeclaration firDeclaration, FirDeclaration firDeclaration2) {
        if (!(firDeclaration instanceof FirMemberDeclaration) || !(firDeclaration2 instanceof FirMemberDeclaration)) {
            return false;
        }
        FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) firDeclaration;
        return (firMemberDeclaration.getStatus().isExpect() && ((FirMemberDeclaration) firDeclaration2).getStatus().isActual()) || (firMemberDeclaration.getStatus().isActual() && ((FirMemberDeclaration) firDeclaration2).getStatus().isExpect());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map<Name, DeclarationBuckets> groupTopLevelByName(List<? extends FirDeclaration> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (FirDeclaration firDeclaration : list) {
            if (isCollectable(firDeclaration)) {
                if (firDeclaration instanceof FirSimpleFunction) {
                    FirSimpleFunction firSimpleFunction = (FirSimpleFunction) firDeclaration;
                    Name name2 = firSimpleFunction.getName();
                    Object declarationBuckets = linkedHashMap.get(name2);
                    if (declarationBuckets == null) {
                        declarationBuckets = new DeclarationBuckets();
                        linkedHashMap.put(name2, declarationBuckets);
                    }
                    ((DeclarationBuckets) declarationBuckets).getSimpleFunctions().add(TuplesKt.to(firDeclaration, FirRedeclarationPresenter.INSTANCE.represent(firSimpleFunction)));
                } else if (firDeclaration instanceof FirProperty) {
                    FirProperty firProperty = (FirProperty) firDeclaration;
                    Name name3 = firProperty.getName();
                    Object declarationBuckets2 = linkedHashMap.get(name3);
                    if (declarationBuckets2 == null) {
                        declarationBuckets2 = new DeclarationBuckets();
                        linkedHashMap.put(name3, declarationBuckets2);
                    }
                    DeclarationBuckets declarationBuckets3 = (DeclarationBuckets) declarationBuckets2;
                    String strRepresent = FirRedeclarationPresenter.INSTANCE.represent((FirVariable) firDeclaration);
                    if (firProperty.getReceiverParameter() != null) {
                        declarationBuckets3.getExtensionProperties().add(TuplesKt.to(firDeclaration, strRepresent));
                    } else {
                        declarationBuckets3.getProperties().add(TuplesKt.to(firDeclaration, strRepresent));
                    }
                } else if (firDeclaration instanceof FirRegularClass) {
                    FirRegularClass firRegularClass = (FirRegularClass) firDeclaration;
                    Name name4 = firRegularClass.getName();
                    Object declarationBuckets4 = linkedHashMap.get(name4);
                    if (declarationBuckets4 == null) {
                        declarationBuckets4 = new DeclarationBuckets();
                        linkedHashMap.put(name4, declarationBuckets4);
                    }
                    DeclarationBuckets declarationBuckets5 = (DeclarationBuckets) declarationBuckets4;
                    declarationBuckets5.getClassLikes().add(TuplesKt.to(firDeclaration, FirRedeclarationPresenter.INSTANCE.represent(firRegularClass)));
                    if (firRegularClass.getClassKind() != ClassKind.OBJECT) {
                        List<FirDeclaration> declarations = firRegularClass.getDeclarations();
                        ArrayList<FirConstructor> arrayList = new ArrayList();
                        for (Object obj : declarations) {
                            if (obj instanceof FirConstructor) {
                                arrayList.add(obj);
                            }
                        }
                        List<Pair<FirConstructor, String>> constructors = declarationBuckets5.getConstructors();
                        for (FirConstructor firConstructor : arrayList) {
                            constructors.add(TuplesKt.to(firConstructor, FirRedeclarationPresenter.INSTANCE.represent(firConstructor, firRegularClass)));
                        }
                    }
                } else if (firDeclaration instanceof FirTypeAlias) {
                    FirTypeAlias firTypeAlias = (FirTypeAlias) firDeclaration;
                    Name name5 = firTypeAlias.getName();
                    Object declarationBuckets6 = linkedHashMap.get(name5);
                    if (declarationBuckets6 == null) {
                        declarationBuckets6 = new DeclarationBuckets();
                        linkedHashMap.put(name5, declarationBuckets6);
                    }
                    ((DeclarationBuckets) declarationBuckets6).getClassLikes().add(TuplesKt.to(firDeclaration, FirRedeclarationPresenter.INSTANCE.represent(firTypeAlias)));
                }
            }
        }
        return linkedHashMap;
    }

    public static final void checkForLocalRedeclarations(List<? extends FirElement> elements, CheckerContext context, DiagnosticReporter reporter) {
        FirVariableSymbol<? extends FirVariable> symbol;
        Name name2;
        Intrinsics.checkNotNullParameter(elements, "elements");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (elements.size() <= 1) {
            return;
        }
        ListMultimap listMultimap = new ListMultimap();
        for (FirElement firElement : elements) {
            if (firElement instanceof FirVariable) {
                FirVariable firVariable = (FirVariable) firElement;
                symbol = firVariable.getSymbol();
                name2 = firVariable.getName();
            } else if (!(firElement instanceof FirOuterClassTypeParameterRef)) {
                if (firElement instanceof FirTypeParameterRef) {
                    symbol = ((FirTypeParameterRef) firElement).getSymbol();
                    name2 = symbol.getName();
                } else {
                    symbol = null;
                    name2 = null;
                }
            }
            if (name2 != null && !name2.isSpecial()) {
                Intrinsics.checkNotNull(symbol);
                listMultimap.put(name2, symbol);
            }
        }
        Iterator it = listMultimap.getKeys().iterator();
        while (it.hasNext()) {
            List list = listMultimap.get((Name) it.next());
            if (list.size() > 1) {
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, ((FirBasedSymbol) it2.next()).getSource(), FirErrors.INSTANCE.getREDECLARATION(), list, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        }
    }
}
