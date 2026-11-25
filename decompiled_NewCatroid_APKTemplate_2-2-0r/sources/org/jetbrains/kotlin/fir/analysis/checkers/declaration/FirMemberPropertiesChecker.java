package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRangeKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.analysis.cfa.FirPropertyInitializationAnalyzerKt;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfo;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfoData;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifierList;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImplKt;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.NormalPath;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: FirMemberPropertiesChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J8\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0011H\u0002J\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirMemberPropertiesChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkProperty", "containingDeclaration", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "isDefinitelyAssignedInConstructor", "", "reachable", "collectInitializationInfo", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfo;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirMemberPropertiesChecker extends FirDeclarationChecker<FirClass> {
    public static final FirMemberPropertiesChecker INSTANCE = new FirMemberPropertiesChecker();

    private FirMemberPropertiesChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirControlFlowGraphReference controlFlowGraphReference;
        ControlFlowGraph controlFlowGraph;
        CFGNode<?> exitNode;
        EventOccurrencesRange eventOccurrencesRange;
        FirControlFlowGraphReference controlFlowGraphReference2;
        ControlFlowGraph controlFlowGraph2;
        CFGNode<?> enterNode;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        PropertyInitializationInfo propertyInitializationInfoCollectInitializationInfo = collectInitializationInfo(declaration, context, reporter);
        FirClass firClass = declaration instanceof FirControlFlowGraphOwner ? declaration : null;
        boolean z = (firClass == null || (controlFlowGraphReference2 = firClass.getControlFlowGraphReference()) == null || (controlFlowGraph2 = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference2)) == null || (enterNode = controlFlowGraph2.getEnterNode()) == null || !enterNode.getIsDead()) ? false : true;
        boolean z2 = z;
        for (FirAnnotationContainer firAnnotationContainer : declaration.getDeclarations()) {
            if (firAnnotationContainer instanceof FirProperty) {
                FirProperty firProperty = (FirProperty) firAnnotationContainer;
                checkProperty(declaration, firProperty, (propertyInitializationInfoCollectInitializationInfo == null || (eventOccurrencesRange = (EventOccurrencesRange) propertyInitializationInfoCollectInitializationInfo.get((Object) firProperty.getSymbol())) == null || !EventOccurrencesRangeKt.isDefinitelyVisited(eventOccurrencesRange)) ? false : true, context, reporter, !z2);
            }
            if (!z2) {
                FirControlFlowGraphOwner firControlFlowGraphOwner = firAnnotationContainer instanceof FirControlFlowGraphOwner ? (FirControlFlowGraphOwner) firAnnotationContainer : null;
                if (firControlFlowGraphOwner == null || (controlFlowGraphReference = firControlFlowGraphOwner.getControlFlowGraphReference()) == null || (controlFlowGraph = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference)) == null || (exitNode = controlFlowGraph.getExitNode()) == null || !exitNode.getIsDead()) {
                    z2 = false;
                }
            }
            z2 = true;
        }
    }

    private final PropertyInitializationInfo collectInitializationInfo(FirClass firClass, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        FirControlFlowGraphReference controlFlowGraphReference;
        ControlFlowGraph controlFlowGraph;
        FirClass firClass2 = firClass instanceof FirControlFlowGraphOwner ? firClass : null;
        if (firClass2 == null || (controlFlowGraphReference = firClass2.getControlFlowGraphReference()) == null || (controlFlowGraph = FirControlFlowGraphReferenceImplKt.getControlFlowGraph(controlFlowGraphReference)) == null) {
            return null;
        }
        List<FirDeclaration> declarations = firClass.getDeclarations();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            FirBasedSymbol<? extends FirDeclaration> symbol = ((FirDeclaration) it.next()).getSymbol();
            FirPropertySymbol firPropertySymbol = symbol instanceof FirPropertySymbol ? (FirPropertySymbol) symbol : null;
            if (firPropertySymbol == null || !FirPropertyInitializationAnalyzerKt.requiresInitialization(firPropertySymbol, true)) {
                firPropertySymbol = null;
            }
            if (firPropertySymbol != null) {
                linkedHashSet.add(firPropertySymbol);
            }
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        if (linkedHashSet2.isEmpty()) {
            return null;
        }
        PropertyInitializationInfoData propertyInitializationInfoData = new PropertyInitializationInfoData(linkedHashSet2, firClass.getSymbol(), controlFlowGraph);
        FirPropertyInitializationAnalyzerKt.checkPropertyAccesses(propertyInitializationInfoData, true, checkerContext, diagnosticReporter);
        return (PropertyInitializationInfo) propertyInitializationInfoData.getValue(controlFlowGraph.getExitNode()).get(NormalPath.INSTANCE);
    }

    private final void checkProperty(FirClass containingDeclaration, FirProperty property, boolean isDefinitelyAssignedInConstructor, CheckerContext context, DiagnosticReporter reporter, boolean reachable) {
        KtSourceElement source;
        KtSourceElement source2;
        KtSourceElement source3;
        KtSourceElement source4;
        KtSourceElement source5;
        KtSourceElement source6 = property.getSource();
        if (source6 == null || (source6.getKind() instanceof KtFakeSourceElementKind)) {
            return;
        }
        FirModifierList modifierList = FirKeywordUtilsKt.getModifierList(property.getSource());
        FirTopLevelPropertiesCheckerKt.checkPropertyInitializer(containingDeclaration, property, modifierList, isDefinitelyAssignedInConstructor, reporter, context, reachable);
        KtModifierKeywordToken ABSTRACT_KEYWORD = KtTokens.ABSTRACT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(ABSTRACT_KEYWORD, "ABSTRACT_KEYWORD");
        boolean zContains = FirKeywordUtilsKt.contains(modifierList, ABSTRACT_KEYWORD);
        FirProperty firProperty = property;
        boolean z = firProperty.getStatus().getModality() == Modality.ABSTRACT || zContains;
        if (containingDeclaration.getClassKind() == ClassKind.INTERFACE && Visibilities.INSTANCE.isPrivate(firProperty.getStatus().getVisibility()) && !z && ((property.getGetter() == null || (property.getGetter() instanceof FirDefaultPropertyAccessor)) && (source5 = property.getSource()) != null)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source5, FirErrors.INSTANCE.getPRIVATE_PROPERTY_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (z) {
            if (containingDeclaration instanceof FirRegularClass) {
                FirRegularClass firRegularClass = (FirRegularClass) containingDeclaration;
                if (firRegularClass.getStatus().getModality() != Modality.ABSTRACT && firRegularClass.getStatus().getModality() != Modality.SEALED && firRegularClass.getClassKind() != ClassKind.ENUM_CLASS && (source4 = property.getSource()) != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source4, FirErrors.INSTANCE.getABSTRACT_PROPERTY_IN_NON_ABSTRACT_CLASS(), property.getSymbol(), firRegularClass.getSymbol(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                    return;
                }
            }
            FirExpression initializer = property.getInitializer();
            if (initializer != null && (source3 = initializer.getSource()) != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source3, FirErrors.INSTANCE.getABSTRACT_PROPERTY_WITH_INITIALIZER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            FirExpression delegate = property.getDelegate();
            if (delegate != null && (source2 = delegate.getSource()) != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirErrors.INSTANCE.getABSTRACT_DELEGATED_PROPERTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        KtModifierKeywordToken OPEN_KEYWORD = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD, "OPEN_KEYWORD");
        if (!FirKeywordUtilsKt.contains(modifierList, OPEN_KEYWORD) || containingDeclaration.getClassKind() != ClassKind.INTERFACE || zContains || firProperty.getStatus().getModality() != Modality.ABSTRACT || DeclarationUtilsKt.isInsideExpectClass(containingDeclaration, context) || (source = property.getSource()) == null) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getREDUNDANT_OPEN_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
