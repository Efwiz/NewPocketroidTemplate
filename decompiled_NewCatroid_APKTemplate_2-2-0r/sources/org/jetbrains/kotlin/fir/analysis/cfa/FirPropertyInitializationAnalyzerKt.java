package org.jetbrains.kotlin.fir.analysis.cfa;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRangeKt;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfo;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfoData;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.CFGNodeWithSubgraphs;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.QualifiedAccessNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.VariableAssignmentNode;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.VariableDeclarationNode;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;

/* JADX INFO: compiled from: FirPropertyInitializationAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0002\u001a\"\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010\u001ab\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00012\u0014\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0019H\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u0001*\u00020\u00152\u0006\u0010\f\u001a\u00020\u0001\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u001b"}, d2 = {"doNotReportUninitializedVariableForClassInitialization", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph$Kind;", "getDoNotReportUninitializedVariableForClassInitialization", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph$Kind;)Z", "evaluatedInPlace", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getEvaluatedInPlace", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Z", "checkPropertyAccesses", "", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfoData;", "isForClassInitialization", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "graph", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraph;", "properties", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "scope", "doNotReportUninitializedVariable", "scopes", "", "requiresInitialization", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyInitializationAnalyzerKt {

    /* JADX INFO: compiled from: FirPropertyInitializationAnalyzer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ControlFlowGraph.Kind.values().length];
            try {
                iArr[ControlFlowGraph.Kind.AnonymousFunction.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ControlFlowGraph.Kind.LocalFunction.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0035, code lost:
    
        if ((r3 instanceof org.jetbrains.kotlin.fir.declarations.FirFunction ? true : r3 instanceof org.jetbrains.kotlin.fir.declarations.FirClass) != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:?, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0022, code lost:
    
        if (((org.jetbrains.kotlin.fir.declarations.FirAnonymousObject) r3).getClassKind() != org.jetbrains.kotlin.descriptors.ClassKind.ENUM_ENTRY) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean getEvaluatedInPlace(org.jetbrains.kotlin.fir.declarations.FirDeclaration r3) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            boolean r0 = r3 instanceof org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction
            if (r0 == 0) goto L14
            org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction r3 = (org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction) r3
            org.jetbrains.kotlin.contracts.description.EventOccurrencesRange r3 = r3.getInvocationKind()
            boolean r3 = org.jetbrains.kotlin.contracts.description.EventOccurrencesRangeKt.isInPlace(r3)
            goto L38
        L14:
            boolean r0 = r3 instanceof org.jetbrains.kotlin.fir.declarations.FirAnonymousObject
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L27
            org.jetbrains.kotlin.fir.declarations.FirAnonymousObject r3 = (org.jetbrains.kotlin.fir.declarations.FirAnonymousObject) r3
            org.jetbrains.kotlin.descriptors.ClassKind r3 = r3.getClassKind()
            org.jetbrains.kotlin.descriptors.ClassKind r0 = org.jetbrains.kotlin.descriptors.ClassKind.ENUM_ENTRY
            if (r3 == r0) goto L25
            goto L2b
        L25:
            r3 = r1
            goto L38
        L27:
            boolean r0 = r3 instanceof org.jetbrains.kotlin.fir.declarations.FirConstructor
            if (r0 == 0) goto L2d
        L2b:
            r3 = r2
            goto L38
        L2d:
            boolean r0 = r3 instanceof org.jetbrains.kotlin.fir.declarations.FirFunction
            if (r0 == 0) goto L33
            r3 = r2
            goto L35
        L33:
            boolean r3 = r3 instanceof org.jetbrains.kotlin.fir.declarations.FirClass
        L35:
            if (r3 == 0) goto L2b
            goto L25
        L38:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.analysis.cfa.FirPropertyInitializationAnalyzerKt.getEvaluatedInPlace(org.jetbrains.kotlin.fir.declarations.FirDeclaration):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean requiresInitialization(FirPropertySymbol firPropertySymbol, boolean z) {
        Intrinsics.checkNotNullParameter(firPropertySymbol, "<this>");
        boolean z2 = !DeclarationAttributesKt.getHasExplicitBackingField(firPropertySymbol) && DeclarationAttributesKt.getHasBackingField(firPropertySymbol);
        if (!(firPropertySymbol instanceof FirSyntheticPropertySymbol)) {
            if (z) {
                if (firPropertySymbol.getHasDelegate() || z2) {
                    return true;
                }
            } else if (!firPropertySymbol.getHasInitializer() && z2 && !Intrinsics.areEqual((Object) ClassMembersKt.isCatchParameter((FirProperty) firPropertySymbol.getFir()), (Object) true)) {
                return true;
            }
        }
        return false;
    }

    public static final void checkPropertyAccesses(PropertyInitializationInfoData propertyInitializationInfoData, boolean z, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(propertyInitializationInfoData, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Set<FirPropertySymbol> properties = propertyInitializationInfoData.getProperties();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Object obj : properties) {
            if (requiresInitialization((FirPropertySymbol) obj, z)) {
                linkedHashSet.add(obj);
            }
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        if (linkedHashSet2.isEmpty()) {
            return;
        }
        checkPropertyAccesses(propertyInitializationInfoData, propertyInitializationInfoData.getGraph(), linkedHashSet2, context, reporter, null, z, false, new LinkedHashMap());
    }

    private static final boolean checkPropertyAccesses$hasCorrectReceiver(FirQualifiedAccessExpression firQualifiedAccessExpression, PropertyInitializationInfoData propertyInitializationInfoData) {
        FirThisReference calleeReference;
        FirExpression firExpressionUnwrapSmartcastExpression = FirExpressionUtilKt.unwrapSmartcastExpression(firQualifiedAccessExpression.getDispatchReceiver());
        FirBasedSymbol<?> boundSymbol = null;
        FirThisReceiverExpression firThisReceiverExpression = firExpressionUnwrapSmartcastExpression instanceof FirThisReceiverExpression ? (FirThisReceiverExpression) firExpressionUnwrapSmartcastExpression : null;
        if (firThisReceiverExpression != null && (calleeReference = firThisReceiverExpression.getCalleeReference()) != null) {
            boundSymbol = calleeReference.getBoundSymbol();
        }
        return Intrinsics.areEqual(boundSymbol, propertyInitializationInfoData.getReceiver());
    }

    /* JADX WARN: Code duplicated, block: B:106:0x0208  */
    /* JADX WARN: Multi-variable type inference failed */
    private static final void checkPropertyAccesses(PropertyInitializationInfoData propertyInitializationInfoData, ControlFlowGraph controlFlowGraph, Set<? extends FirPropertySymbol> set, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, FirDeclaration firDeclaration, boolean z, boolean z2, Map<FirPropertySymbol, FirDeclaration> map) {
        FirPropertySymbol resolvedPropertySymbol$default;
        FirQualifiedAccessExpression firQualifiedAccessExpressionUnwrapLValue;
        KtDiagnosticFactory1<FirPropertySymbol> captured_val_initialization;
        FirDeclaration firDeclaration2;
        for (CFGNode<?> cFGNode : controlFlowGraph.getNodes()) {
            if (cFGNode instanceof VariableDeclarationNode) {
                VariableDeclarationNode variableDeclarationNode = (VariableDeclarationNode) cFGNode;
                FirPropertySymbol symbol = variableDeclarationNode.getFir().getSymbol();
                if (firDeclaration != null && propertyInitializationInfoData.getReceiver() == null && variableDeclarationNode.getFir().getIsVal() && set.contains(symbol)) {
                    map.put(symbol, firDeclaration);
                }
            } else {
                boolean z3 = true;
                if (cFGNode instanceof VariableAssignmentNode) {
                    VariableAssignmentNode variableAssignmentNode = (VariableAssignmentNode) cFGNode;
                    FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(variableAssignmentNode.getFir());
                    if (calleeReference != null && (resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(calleeReference, false, 1, null)) != null && ((FirProperty) resolvedPropertySymbol$default.getFir()).getIsVal() && (firQualifiedAccessExpressionUnwrapLValue = FirExpressionUtilKt.unwrapLValue(variableAssignmentNode.getFir())) != null && checkPropertyAccesses$hasCorrectReceiver(firQualifiedAccessExpressionUnwrapLValue, propertyInitializationInfoData) && set.contains(resolvedPropertySymbol$default)) {
                        Collection collectionValues = propertyInitializationInfoData.getValue(cFGNode).values();
                        if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
                            Iterator it = collectionValues.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    EventOccurrencesRange eventOccurrencesRange = (EventOccurrencesRange) ((PropertyInitializationInfo) it.next()).get((Object) resolvedPropertySymbol$default);
                                    if (eventOccurrencesRange != null && EventOccurrencesRangeKt.canBeRevisited(eventOccurrencesRange)) {
                                        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, variableAssignmentNode.getFir().getLValue().getSource(), FirErrors.INSTANCE.getVAL_REASSIGNMENT(), resolvedPropertySymbol$default, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                                    }
                                }
                            }
                        }
                        if (!Intrinsics.areEqual(firDeclaration, map.get(resolvedPropertySymbol$default))) {
                            if (propertyInitializationInfoData.getReceiver() != null) {
                                captured_val_initialization = FirErrors.INSTANCE.getCAPTURED_MEMBER_VAL_INITIALIZATION();
                            } else {
                                captured_val_initialization = FirErrors.INSTANCE.getCAPTURED_VAL_INITIALIZATION();
                            }
                            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, variableAssignmentNode.getFir().getLValue().getSource(), captured_val_initialization, resolvedPropertySymbol$default, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                        }
                    }
                } else if (cFGNode instanceof QualifiedAccessNode) {
                    if (!z2) {
                        QualifiedAccessNode qualifiedAccessNode = (QualifiedAccessNode) cFGNode;
                        FirPropertySymbol resolvedPropertySymbol$default2 = FirReferenceUtilsKt.toResolvedPropertySymbol$default(qualifiedAccessNode.getFir().getCalleeReference(), false, 1, null);
                        if (resolvedPropertySymbol$default2 != null && !resolvedPropertySymbol$default2.getRawStatus().isLateInit() && checkPropertyAccesses$hasCorrectReceiver(qualifiedAccessNode.getFir(), propertyInitializationInfoData) && set.contains(resolvedPropertySymbol$default2)) {
                            Collection collectionValues2 = propertyInitializationInfoData.getValue(cFGNode).values();
                            if (!(collectionValues2 instanceof Collection) || !collectionValues2.isEmpty()) {
                                Iterator it2 = collectionValues2.iterator();
                                while (it2.hasNext()) {
                                    EventOccurrencesRange eventOccurrencesRange2 = (EventOccurrencesRange) ((PropertyInitializationInfo) it2.next()).get((Object) resolvedPropertySymbol$default2);
                                    if (!(eventOccurrencesRange2 != null && EventOccurrencesRangeKt.isDefinitelyVisited(eventOccurrencesRange2))) {
                                        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, qualifiedAccessNode.getFir().getSource(), FirErrors.INSTANCE.getUNINITIALIZED_VARIABLE(), resolvedPropertySymbol$default2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else if ((cFGNode instanceof CFGNodeWithSubgraphs) && (propertyInitializationInfoData.getReceiver() == null || cFGNode != controlFlowGraph.getExitNode())) {
                    for (ControlFlowGraph controlFlowGraph2 : ((CFGNodeWithSubgraphs) cFGNode).getSubGraphs()) {
                        boolean z4 = (z2 || (z && getDoNotReportUninitializedVariableForClassInitialization(controlFlowGraph2.getKind()))) ? z3 : false;
                        FirDeclaration declaration = controlFlowGraph2.getDeclaration();
                        if (declaration == null) {
                            firDeclaration2 = firDeclaration;
                        } else {
                            if (getEvaluatedInPlace(declaration)) {
                                declaration = null;
                            }
                            if (declaration == null) {
                                firDeclaration2 = firDeclaration;
                            } else {
                                firDeclaration2 = declaration;
                            }
                        }
                        checkPropertyAccesses(propertyInitializationInfoData, controlFlowGraph2, set, checkerContext, diagnosticReporter, firDeclaration2, z, z4, map);
                        z3 = z3;
                    }
                }
            }
        }
    }

    private static final boolean getDoNotReportUninitializedVariableForClassInitialization(ControlFlowGraph.Kind kind) {
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        return i == 1 || i == 2;
    }
}
