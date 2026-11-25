package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.contracts.description.KtBinaryLogicExpression;
import org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor;
import org.jetbrains.kotlin.contracts.description.KtErroneousCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtErroneousConstantReference;
import org.jetbrains.kotlin.contracts.description.KtErroneousContractElement;
import org.jetbrains.kotlin.contracts.description.KtErroneousIsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.KtErroneousValueParameterReference;
import org.jetbrains.kotlin.contracts.description.KtIsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.KtIsNullPredicate;
import org.jetbrains.kotlin.contracts.description.KtLogicalNot;
import org.jetbrains.kotlin.contracts.description.KtReturnsEffectDeclaration;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirContractElementDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: FirContractChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003:\u0001\u0012B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J(\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J \u0010\u0011\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirContractChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "()V", "EMPTY_CONTRACT_MESSAGE", "", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkContractNotAllowed", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;", "checkUnresolvedEffects", "DiagnosticExtractor", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirContractChecker extends FirDeclarationChecker<FirFunction> {
    public static final FirContractChecker INSTANCE = new FirContractChecker();
    private static final String EMPTY_CONTRACT_MESSAGE = "Empty contract block is not allowed";

    private FirContractChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirContractDescriptionOwner) {
            FirContractDescription contractDescription = ((FirContractDescriptionOwner) declaration).getContractDescription();
            FirResolvedContractDescription firResolvedContractDescription = contractDescription instanceof FirResolvedContractDescription ? (FirResolvedContractDescription) contractDescription : null;
            if (firResolvedContractDescription == null) {
                return;
            }
            checkUnresolvedEffects(firResolvedContractDescription, context, reporter);
            checkContractNotAllowed(declaration, firResolvedContractDescription, context, reporter);
            if (firResolvedContractDescription.getEffects().isEmpty() && firResolvedContractDescription.getUnresolvedEffects().isEmpty()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firResolvedContractDescription.getSource(), FirErrors.INSTANCE.getERROR_IN_CONTRACT_DESCRIPTION(), EMPTY_CONTRACT_MESSAGE, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }

    private final void checkUnresolvedEffects(FirResolvedContractDescription contractDescription, CheckerContext context, DiagnosticReporter reporter) {
        for (FirContractElementDeclaration firContractElementDeclaration : contractDescription.getUnresolvedEffects()) {
            ConeDiagnostic coneDiagnostic = (ConeDiagnostic) firContractElementDeclaration.getEffect().accept(DiagnosticExtractor.INSTANCE, null);
            if (coneDiagnostic != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firContractElementDeclaration.getSource(), FirErrors.INSTANCE.getERROR_IN_CONTRACT_DESCRIPTION(), coneDiagnostic.getReason(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }

    private final void checkContractNotAllowed(FirFunction declaration, FirResolvedContractDescription contractDescription, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source = contractDescription.getSource();
        if ((source != null ? source.getKind() : null) instanceof KtRealSourceElementKind) {
            if ((declaration instanceof FirPropertyAccessor) || (declaration instanceof FirAnonymousFunction)) {
                checkContractNotAllowed$contractNotAllowed(reporter, source, context, "Contracts are only allowed for functions");
                return;
            }
            FirFunction firFunction = declaration;
            if (firFunction.getStatus().getModality() == Modality.ABSTRACT || firFunction.getStatus().getModality() == Modality.OPEN || firFunction.getStatus().isOverride()) {
                checkContractNotAllowed$contractNotAllowed(reporter, source, context, "Contracts are not allowed for open or override functions");
                return;
            }
            if (firFunction.getStatus().isOperator()) {
                checkContractNotAllowed$contractNotAllowed(reporter, source, context, "Contracts are not allowed for operator functions");
            } else if (declaration.getSymbol().getCallableId().isLocal() || Intrinsics.areEqual(firFunction.getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
                checkContractNotAllowed$contractNotAllowed(reporter, source, context, "Contracts are not allowed for local functions");
            }
        }
    }

    private static final void checkContractNotAllowed$contractNotAllowed(DiagnosticReporter diagnosticReporter, KtSourceElement ktSourceElement, CheckerContext checkerContext, String str) {
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, FirErrors.INSTANCE.getCONTRACT_NOT_ALLOWED(), str, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    /* JADX INFO: compiled from: FirContractChecker.kt */
    @Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u001e\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0005J,\u0010\u0006\u001a\u0004\u0018\u00010\u00022\u0016\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\bj\u0002`\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J,\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0016\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\rj\u0002`\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J,\u0010\u000f\u001a\u0004\u0018\u00010\u00022\u0016\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0011j\u0002`\u00122\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J&\u0010\u0013\u001a\u00020\u00022\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u00142\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J&\u0010\u0015\u001a\u00020\u00022\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u00172\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J&\u0010\u0018\u001a\u00020\u00022\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u001a2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J&\u0010\u001b\u001a\u00020\u00022\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u001d2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J&\u0010\u001e\u001a\u00020\u00022\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020 2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J,\u0010!\u001a\u0004\u0018\u00010\u00022\u0016\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\"j\u0002`#2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J,\u0010$\u001a\u0004\u0018\u00010\u00022\u0016\u0010%\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020&j\u0002`'2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J,\u0010(\u001a\u0004\u0018\u00010\u00022\u0016\u0010)\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020*j\u0002`+2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J,\u0010,\u001a\u0004\u0018\u00010\u00022\u0016\u0010-\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020.j\u0002`/2\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016J,\u00100\u001a\u0004\u0018\u00010\u00022\u0016\u00101\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000202j\u0002`32\b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u0016¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirContractChecker$DiagnosticExtractor;", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "()V", "visitCallsEffectDeclaration", "callsEffect", "Lorg/jetbrains/kotlin/contracts/description/KtCallsEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeCallsEffectDeclaration;", "data", "visitConditionalEffectDeclaration", "conditionalEffect", "Lorg/jetbrains/kotlin/contracts/description/KtConditionalEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeConditionalEffectDeclaration;", "visitContractDescriptionElement", "contractDescriptionElement", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractDescriptionElement;", "visitErroneousCallsEffectDeclaration", "Lorg/jetbrains/kotlin/contracts/description/KtErroneousCallsEffectDeclaration;", "visitErroneousConstantReference", "erroneousConstantReference", "Lorg/jetbrains/kotlin/contracts/description/KtErroneousConstantReference;", "visitErroneousElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/contracts/description/KtErroneousContractElement;", "visitErroneousIsInstancePredicate", "isInstancePredicate", "Lorg/jetbrains/kotlin/contracts/description/KtErroneousIsInstancePredicate;", "visitErroneousValueParameterReference", "valueParameterReference", "Lorg/jetbrains/kotlin/contracts/description/KtErroneousValueParameterReference;", "visitIsInstancePredicate", "Lorg/jetbrains/kotlin/contracts/description/KtIsInstancePredicate;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeIsInstancePredicate;", "visitIsNullPredicate", "isNullPredicate", "Lorg/jetbrains/kotlin/contracts/description/KtIsNullPredicate;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeIsNullPredicate;", "visitLogicalBinaryOperationContractExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/contracts/description/KtBinaryLogicExpression;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeBinaryLogicExpression;", "visitLogicalNot", "logicalNot", "Lorg/jetbrains/kotlin/contracts/description/KtLogicalNot;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeLogicalNot;", "visitReturnsEffectDeclaration", "returnsEffect", "Lorg/jetbrains/kotlin/contracts/description/KtReturnsEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeReturnsEffectDeclaration;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class DiagnosticExtractor extends KtContractDescriptionVisitor {
        public static final DiagnosticExtractor INSTANCE = new DiagnosticExtractor();

        public ConeDiagnostic visitContractDescriptionElement(KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> contractDescriptionElement, Void data) {
            Intrinsics.checkNotNullParameter(contractDescriptionElement, "contractDescriptionElement");
            return null;
        }

        private DiagnosticExtractor() {
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitCallsEffectDeclaration(KtCallsEffectDeclaration ktCallsEffectDeclaration, Object obj) {
            return visitCallsEffectDeclaration((KtCallsEffectDeclaration<ConeKotlinType, ConeDiagnostic>) ktCallsEffectDeclaration, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitConditionalEffectDeclaration(KtConditionalEffectDeclaration ktConditionalEffectDeclaration, Object obj) {
            return visitConditionalEffectDeclaration((KtConditionalEffectDeclaration<ConeKotlinType, ConeDiagnostic>) ktConditionalEffectDeclaration, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitContractDescriptionElement(KtContractDescriptionElement ktContractDescriptionElement, Object obj) {
            return visitContractDescriptionElement((KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic>) ktContractDescriptionElement, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitErroneousCallsEffectDeclaration(KtErroneousCallsEffectDeclaration ktErroneousCallsEffectDeclaration, Object obj) {
            return visitErroneousCallsEffectDeclaration((KtErroneousCallsEffectDeclaration<ConeKotlinType, ConeDiagnostic>) ktErroneousCallsEffectDeclaration, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitErroneousConstantReference(KtErroneousConstantReference ktErroneousConstantReference, Object obj) {
            return visitErroneousConstantReference((KtErroneousConstantReference<ConeKotlinType, ConeDiagnostic>) ktErroneousConstantReference, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitErroneousElement(KtErroneousContractElement ktErroneousContractElement, Object obj) {
            return visitErroneousElement((KtErroneousContractElement<ConeKotlinType, ConeDiagnostic>) ktErroneousContractElement, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitErroneousIsInstancePredicate(KtErroneousIsInstancePredicate ktErroneousIsInstancePredicate, Object obj) {
            return visitErroneousIsInstancePredicate((KtErroneousIsInstancePredicate<ConeKotlinType, ConeDiagnostic>) ktErroneousIsInstancePredicate, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitErroneousValueParameterReference(KtErroneousValueParameterReference ktErroneousValueParameterReference, Object obj) {
            return visitErroneousValueParameterReference((KtErroneousValueParameterReference<ConeKotlinType, ConeDiagnostic>) ktErroneousValueParameterReference, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitIsInstancePredicate(KtIsInstancePredicate ktIsInstancePredicate, Object obj) {
            return visitIsInstancePredicate((KtIsInstancePredicate<ConeKotlinType, ConeDiagnostic>) ktIsInstancePredicate, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitIsNullPredicate(KtIsNullPredicate ktIsNullPredicate, Object obj) {
            return visitIsNullPredicate((KtIsNullPredicate<ConeKotlinType, ConeDiagnostic>) ktIsNullPredicate, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitLogicalBinaryOperationContractExpression(KtBinaryLogicExpression ktBinaryLogicExpression, Object obj) {
            return visitLogicalBinaryOperationContractExpression((KtBinaryLogicExpression<ConeKotlinType, ConeDiagnostic>) ktBinaryLogicExpression, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitLogicalNot(KtLogicalNot ktLogicalNot, Object obj) {
            return visitLogicalNot((KtLogicalNot<ConeKotlinType, ConeDiagnostic>) ktLogicalNot, (Void) obj);
        }

        @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
        public /* bridge */ /* synthetic */ Object visitReturnsEffectDeclaration(KtReturnsEffectDeclaration ktReturnsEffectDeclaration, Object obj) {
            return visitReturnsEffectDeclaration((KtReturnsEffectDeclaration<ConeKotlinType, ConeDiagnostic>) ktReturnsEffectDeclaration, (Void) obj);
        }

        public ConeDiagnostic visitConditionalEffectDeclaration(KtConditionalEffectDeclaration<ConeKotlinType, ConeDiagnostic> conditionalEffect, Void data) {
            Intrinsics.checkNotNullParameter(conditionalEffect, "conditionalEffect");
            DiagnosticExtractor diagnosticExtractor = this;
            ConeDiagnostic coneDiagnostic = (ConeDiagnostic) conditionalEffect.getEffect().accept(diagnosticExtractor, null);
            return coneDiagnostic == null ? (ConeDiagnostic) conditionalEffect.getCondition().accept(diagnosticExtractor, null) : coneDiagnostic;
        }

        public ConeDiagnostic visitReturnsEffectDeclaration(KtReturnsEffectDeclaration<ConeKotlinType, ConeDiagnostic> returnsEffect, Void data) {
            Intrinsics.checkNotNullParameter(returnsEffect, "returnsEffect");
            return (ConeDiagnostic) returnsEffect.getValue().accept(this, null);
        }

        public ConeDiagnostic visitCallsEffectDeclaration(KtCallsEffectDeclaration<ConeKotlinType, ConeDiagnostic> callsEffect, Void data) {
            Intrinsics.checkNotNullParameter(callsEffect, "callsEffect");
            return (ConeDiagnostic) callsEffect.getValueParameterReference().accept(this, data);
        }

        public ConeDiagnostic visitErroneousCallsEffectDeclaration(KtErroneousCallsEffectDeclaration<ConeKotlinType, ConeDiagnostic> callsEffect, Void data) {
            Intrinsics.checkNotNullParameter(callsEffect, "callsEffect");
            return callsEffect.getDiagnostic();
        }

        public ConeDiagnostic visitLogicalBinaryOperationContractExpression(KtBinaryLogicExpression<ConeKotlinType, ConeDiagnostic> binaryLogicExpression, Void data) {
            Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
            DiagnosticExtractor diagnosticExtractor = this;
            ConeDiagnostic coneDiagnostic = (ConeDiagnostic) binaryLogicExpression.getLeft().accept(diagnosticExtractor, null);
            return coneDiagnostic == null ? (ConeDiagnostic) binaryLogicExpression.getRight().accept(diagnosticExtractor, null) : coneDiagnostic;
        }

        public ConeDiagnostic visitLogicalNot(KtLogicalNot<ConeKotlinType, ConeDiagnostic> logicalNot, Void data) {
            Intrinsics.checkNotNullParameter(logicalNot, "logicalNot");
            return (ConeDiagnostic) logicalNot.getArg().accept(this, null);
        }

        public ConeDiagnostic visitIsInstancePredicate(KtIsInstancePredicate<ConeKotlinType, ConeDiagnostic> isInstancePredicate, Void data) {
            Intrinsics.checkNotNullParameter(isInstancePredicate, "isInstancePredicate");
            return (ConeDiagnostic) isInstancePredicate.getArg().accept(this, data);
        }

        public ConeDiagnostic visitErroneousIsInstancePredicate(KtErroneousIsInstancePredicate<ConeKotlinType, ConeDiagnostic> isInstancePredicate, Void data) {
            Intrinsics.checkNotNullParameter(isInstancePredicate, "isInstancePredicate");
            return isInstancePredicate.getDiagnostic();
        }

        public ConeDiagnostic visitIsNullPredicate(KtIsNullPredicate<ConeKotlinType, ConeDiagnostic> isNullPredicate, Void data) {
            Intrinsics.checkNotNullParameter(isNullPredicate, "isNullPredicate");
            return (ConeDiagnostic) isNullPredicate.getArg().accept(this, data);
        }

        public ConeDiagnostic visitErroneousConstantReference(KtErroneousConstantReference<ConeKotlinType, ConeDiagnostic> erroneousConstantReference, Void data) {
            Intrinsics.checkNotNullParameter(erroneousConstantReference, "erroneousConstantReference");
            return erroneousConstantReference.getDiagnostic();
        }

        public ConeDiagnostic visitErroneousValueParameterReference(KtErroneousValueParameterReference<ConeKotlinType, ConeDiagnostic> valueParameterReference, Void data) {
            Intrinsics.checkNotNullParameter(valueParameterReference, "valueParameterReference");
            return valueParameterReference.getDiagnostic();
        }

        public ConeDiagnostic visitErroneousElement(KtErroneousContractElement<ConeKotlinType, ConeDiagnostic> element, Void data) {
            Intrinsics.checkNotNullParameter(element, "element");
            return element.getDiagnostic();
        }
    }
}
