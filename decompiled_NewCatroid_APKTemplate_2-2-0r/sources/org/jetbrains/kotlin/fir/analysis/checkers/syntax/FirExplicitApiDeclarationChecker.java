package org.jetbrains.kotlin.fir.analysis.checkers.syntax;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.ExplicitApiMode;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.FirSourceUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.impl.FirPrimaryConstructor;
import org.jetbrains.kotlin.fir.resolve.transformers.PublishedApiEffectiveVisibilityKt;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: FirExplicitApiDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J0\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J(\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J0\u0010\u0017\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u001b\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002R\u001c\u0010\u0005\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00070\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirExplicitApiDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirDeclarationSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "()V", "codeFragmentTypes", "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IFileElementType;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "checkExplicitReturnType", "", "state", "Lorg/jetbrains/kotlin/config/ExplicitApiMode;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkPsiOrLightTree", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "checkVisibilityModifier", "explicitVisibilityIsNotRequired", "", "returnTypeCheckIsApplicable", "returnTypeRequired", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExplicitApiDeclarationChecker extends FirDeclarationSyntaxChecker<FirDeclaration, KtDeclaration> {
    public static final FirExplicitApiDeclarationChecker INSTANCE = new FirExplicitApiDeclarationChecker();
    private static final Set<IFileElementType> codeFragmentTypes = SetsKt.setOf((Object[]) new IFileElementType[]{KtNodeTypes.BLOCK_CODE_FRAGMENT, KtNodeTypes.EXPRESSION_CODE_FRAGMENT, KtNodeTypes.TYPE_CODE_FRAGMENT});

    private FirExplicitApiDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkPsiOrLightTree(FirDeclaration element, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        ExplicitApiMode explicitApiMode;
        EffectiveVisibility.Local effectiveVisibility;
        EffectiveVisibility.Local effectiveVisibility2;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (((source.getKind() instanceof KtRealSourceElementKind) || Intrinsics.areEqual(source.getKind(), KtFakeSourceElementKind.PropertyFromParameter.INSTANCE)) && (element instanceof FirMemberDeclaration) && (explicitApiMode = (ExplicitApiMode) context.get$languageVersionSettings().getFlag(AnalysisFlags.getExplicitApiMode())) != ExplicitApiMode.DISABLED && !(element instanceof FirEnumEntry)) {
            FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) element;
            FirDeclarationStatus status = firMemberDeclaration.getStatus();
            FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
            if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
                effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
            }
            if (effectiveVisibility.getPublicApi() || PublishedApiEffectiveVisibilityKt.getPublishedApiEffectiveVisibility(element) != null) {
                FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) context.getContainingDeclarations());
                FirMemberDeclaration firMemberDeclaration2 = firDeclaration instanceof FirMemberDeclaration ? (FirMemberDeclaration) firDeclaration : null;
                if (firMemberDeclaration2 != null) {
                    FirDeclarationStatus status2 = firMemberDeclaration2.getStatus();
                    FirResolvedDeclarationStatus firResolvedDeclarationStatus2 = status2 instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status2 : null;
                    if (firResolvedDeclarationStatus2 == null || (effectiveVisibility2 = firResolvedDeclarationStatus2.getEffectiveVisibility()) == null) {
                        effectiveVisibility2 = EffectiveVisibility.Local.INSTANCE;
                    }
                    if (effectiveVisibility2 != null && !effectiveVisibility2.getPublicApi()) {
                        return;
                    }
                }
                checkVisibilityModifier(explicitApiMode, firMemberDeclaration, source, context, reporter);
                checkExplicitReturnType(explicitApiMode, firMemberDeclaration, source, context, reporter);
            }
        }
    }

    private final void checkVisibilityModifier(ExplicitApiMode state, FirMemberDeclaration declaration, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement child$default;
        KtDiagnosticFactory0 no_explicit_visibility_in_api_mode_warning;
        IElementType MODIFIER_LIST = KtNodeTypes.MODIFIER_LIST;
        Intrinsics.checkNotNullExpressionValue(MODIFIER_LIST, "MODIFIER_LIST");
        KtSourceElement child$default2 = FirSourceUtilsKt.getChild$default(source, MODIFIER_LIST, 0, 1, false, 10, (Object) null);
        if (child$default2 != null) {
            TokenSet VISIBILITY_MODIFIERS = KtTokens.VISIBILITY_MODIFIERS;
            Intrinsics.checkNotNullExpressionValue(VISIBILITY_MODIFIERS, "VISIBILITY_MODIFIERS");
            child$default = FirSourceUtilsKt.getChild$default(child$default2, VISIBILITY_MODIFIERS, 0, 0, false, 14, (Object) null);
        } else {
            child$default = null;
        }
        if (child$default == null && !explicitVisibilityIsNotRequired(declaration, context)) {
            if (state == ExplicitApiMode.STRICT) {
                no_explicit_visibility_in_api_mode_warning = FirErrors.INSTANCE.getNO_EXPLICIT_VISIBILITY_IN_API_MODE();
            } else {
                no_explicit_visibility_in_api_mode_warning = FirErrors.INSTANCE.getNO_EXPLICIT_VISIBILITY_IN_API_MODE_WARNING();
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, no_explicit_visibility_in_api_mode_warning, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final boolean explicitVisibilityIsNotRequired(FirMemberDeclaration declaration, CheckerContext context) {
        if (declaration instanceof FirPrimaryConstructor ? true : declaration instanceof FirPropertyAccessor ? true : declaration instanceof FirValueParameter ? true : declaration instanceof FirAnonymousFunction) {
            return true;
        }
        if (declaration instanceof FirCallableDeclaration) {
            Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
            FirRegularClass firRegularClass = objLastOrNull instanceof FirRegularClass ? (FirRegularClass) objLastOrNull : null;
            if (((declaration instanceof FirProperty) && firRegularClass != null && (firRegularClass.getStatus().isData() || firRegularClass.getClassKind() == ClassKind.ANNOTATION_CLASS)) || declaration.getStatus().isOverride() || DeclarationUtilsKt.isLocalMember(declaration)) {
                return true;
            }
        }
        return false;
    }

    private final void checkExplicitReturnType(ExplicitApiMode state, FirMemberDeclaration declaration, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        KtDiagnosticFactory0 no_explicit_return_type_in_api_mode_warning;
        if ((declaration instanceof FirCallableDeclaration) && returnTypeCheckIsApplicable(source, context) && returnTypeRequired((FirCallableDeclaration) declaration, context)) {
            if (state == ExplicitApiMode.STRICT) {
                no_explicit_return_type_in_api_mode_warning = FirErrors.INSTANCE.getNO_EXPLICIT_RETURN_TYPE_IN_API_MODE();
            } else {
                no_explicit_return_type_in_api_mode_warning = FirErrors.INSTANCE.getNO_EXPLICIT_RETURN_TYPE_IN_API_MODE_WARNING();
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, no_explicit_return_type_in_api_mode_warning, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final boolean returnTypeCheckIsApplicable(KtSourceElement source, CheckerContext context) {
        IElementType TYPE_REFERENCE = KtNodeTypes.TYPE_REFERENCE;
        Intrinsics.checkNotNullExpressionValue(TYPE_REFERENCE, "TYPE_REFERENCE");
        if (FirSourceUtilsKt.getChild$default(source, TYPE_REFERENCE, 0, 1, false, 10, (Object) null) != null) {
            return false;
        }
        FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.first((List) context.getContainingDeclarations());
        Set<IFileElementType> set = codeFragmentTypes;
        KtSourceElement source2 = firDeclaration.getSource();
        if (CollectionsKt.contains(set, source2 != null ? source2.getElementType() : null)) {
            return false;
        }
        IElementType elementType = source.getElementType();
        if (Intrinsics.areEqual(elementType, KtNodeTypes.FUN)) {
            KtSingleValueToken EQ = KtTokens.EQ;
            Intrinsics.checkNotNullExpressionValue(EQ, "EQ");
            if (FirSourceUtilsKt.getChild$default(source, (IElementType) EQ, 0, 1, false, 10, (Object) null) == null) {
                return false;
            }
        } else if (!Intrinsics.areEqual(elementType, KtNodeTypes.PROPERTY)) {
            return false;
        }
        return true;
    }

    private final boolean returnTypeRequired(FirCallableDeclaration declaration, CheckerContext context) {
        FirDeclaration firDeclaration;
        return !DeclarationUtilsKt.isLocalMember(declaration) && ((firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) context.getContainingDeclarations())) == null || !DeclarationUtilsKt.isLocalMember(firDeclaration));
    }
}
