package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.ExplicitApiMode;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.SourceHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirDeclarationSyntaxChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: RedundantVisibilityModifierSyntaxChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J2\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J*\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J \u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J(\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J,\u0010\u001d\u001a\u00020\u00062\"\u0010\u001e\u001a\u001e\u0012\u0014\u0012\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030 \u0012\u0004\u0012\u00020!0\u001f\u0012\u0004\u0012\u00020\u00100\u001fH\u0002J\u0018\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020$2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010%\u001a\u00020\u00062\u0006\u0010&\u001a\u00020'2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010(\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J \u0010)\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0016\u0010*\u001a\u00020\u000b*\u00020\f2\b\u0010+\u001a\u0004\u0018\u00010\u0006H\u0002J\u0014\u0010\u0012\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010,\u001a\u00020\u000b*\u0004\u0018\u00010\u00062\b\u0010-\u001a\u0004\u0018\u00010\u0014H\u0002R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\u00020\u000b*\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/RedundantVisibilityModifierSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirDeclarationSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "()V", "explicitVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "Lorg/jetbrains/kotlin/KtSourceElement;", "getExplicitVisibility", "(Lorg/jetbrains/kotlin/KtSourceElement;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "hasSetterWithImplicitVisibility", "", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "getHasSetterWithImplicitVisibility", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Z", "checkElementAndReport", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "implicitVisibility", "containingMemberDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkPropertyAndReport", "property", "checkPsiOrLightTree", FirebaseAnalytics.Param.SOURCE, "findBiggestVisibility", "processSymbols", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "findFunctionVisibility", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "findPropertyAccessorVisibility", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "findPropertyVisibility", "reportElement", "canMakeSetterMoreAccessible", "setterImplicitVisibility", "isEffectivelyHiddenBy", "declaration", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantVisibilityModifierSyntaxChecker extends FirDeclarationSyntaxChecker<FirDeclaration, KtDeclaration> {
    public static final RedundantVisibilityModifierSyntaxChecker INSTANCE = new RedundantVisibilityModifierSyntaxChecker();

    private RedundantVisibilityModifierSyntaxChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkPsiOrLightTree(FirDeclaration element, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (element instanceof FirPropertyAccessor) {
            return;
        }
        if (element instanceof FirConstructor) {
            KtSourceElement source2 = element.getSource();
            if ((source2 != null ? source2.getKind() : null) instanceof KtFakeSourceElementKind) {
                return;
            }
        }
        if (element instanceof FirProperty) {
            checkPropertyAndReport((FirProperty) element, context, reporter);
        } else {
            checkElementAndReport(element, context, reporter);
        }
    }

    private final void checkPropertyAndReport(FirProperty property, CheckerContext context, DiagnosticReporter reporter) {
        Visibility visibilityImplicitVisibility;
        FirPropertyAccessor setter = property.getSetter();
        if (setter != null) {
            RedundantVisibilityModifierSyntaxChecker redundantVisibilityModifierSyntaxChecker = INSTANCE;
            FirPropertyAccessor firPropertyAccessor = setter;
            visibilityImplicitVisibility = redundantVisibilityModifierSyntaxChecker.implicitVisibility(firPropertyAccessor, context);
            redundantVisibilityModifierSyntaxChecker.checkElementAndReport(firPropertyAccessor, visibilityImplicitVisibility, property, context, reporter);
        } else {
            visibilityImplicitVisibility = null;
        }
        FirPropertyAccessor getter = property.getGetter();
        if (getter != null) {
            INSTANCE.checkElementAndReport(getter, property, context, reporter);
        }
        FirBackingField backingField = property.getBackingField();
        if (backingField != null) {
            INSTANCE.checkElementAndReport(backingField, property, context, reporter);
        }
        if (canMakeSetterMoreAccessible(property, visibilityImplicitVisibility)) {
            return;
        }
        checkElementAndReport(property, context, reporter);
    }

    private final void checkElementAndReport(FirDeclaration element, FirMemberDeclaration containingMemberDeclaration, CheckerContext context, DiagnosticReporter reporter) {
        checkElementAndReport(element, implicitVisibility(element, context), containingMemberDeclaration, context, reporter);
    }

    private final void checkElementAndReport(FirDeclaration element, Visibility implicitVisibility, FirMemberDeclaration containingMemberDeclaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source = element.getSource();
        if (!((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) && (element instanceof FirMemberDeclaration)) {
            KtSourceElement source2 = element.getSource();
            Visibility explicitVisibility = source2 != null ? getExplicitVisibility(source2) : null;
            if (isEffectivelyHiddenBy(explicitVisibility, containingMemberDeclaration)) {
                reportElement(element, context, reporter);
            } else if ((((ExplicitApiMode) context.get$languageVersionSettings().getFlag(AnalysisFlags.getExplicitApiMode())) == ExplicitApiMode.DISABLED || !Intrinsics.areEqual(explicitVisibility, Visibilities.Public.INSTANCE)) && Intrinsics.areEqual(explicitVisibility, implicitVisibility)) {
                reportElement(element, context, reporter);
            }
        }
    }

    private final void reportElement(FirDeclaration element, CheckerContext context, DiagnosticReporter reporter) {
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, element.getSource(), FirErrors.INSTANCE.getREDUNDANT_VISIBILITY_MODIFIER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    private final boolean canMakeSetterMoreAccessible(FirProperty firProperty, Visibility visibility) {
        FirProperty firProperty2 = firProperty;
        if (firProperty2.getStatus().isOverride() && getHasSetterWithImplicitVisibility(firProperty) && visibility != null) {
            return !Intrinsics.areEqual(visibility, firProperty2.getStatus().getVisibility());
        }
        return false;
    }

    private final boolean getHasSetterWithImplicitVisibility(FirProperty firProperty) {
        KtSourceElement source;
        FirPropertyAccessor setter = firProperty.getSetter();
        if (setter == null) {
            return false;
        }
        KtSourceElement source2 = firProperty.getSource();
        LighterASTNode lighterASTNode = source2 != null ? source2.getLighterASTNode() : null;
        KtSourceElement source3 = setter.getSource();
        return Intrinsics.areEqual(lighterASTNode, source3 != null ? source3.getLighterASTNode() : null) || (source = setter.getSource()) == null || getExplicitVisibility(source) == null;
    }

    private final Visibility getExplicitVisibility(KtSourceElement ktSourceElement) {
        LighterASTNode lighterASTNodeVisibilityModifier = LightTreePositioningStrategiesKt.visibilityModifier(ktSourceElement.getTreeStructure(), ktSourceElement.getLighterASTNode());
        IElementType tokenType = lighterASTNodeVisibilityModifier != null ? lighterASTNodeVisibilityModifier.getTokenType() : null;
        KtModifierKeywordToken ktModifierKeywordToken = tokenType instanceof KtModifierKeywordToken ? (KtModifierKeywordToken) tokenType : null;
        if (ktModifierKeywordToken != null) {
            return SourceHelpersKt.toVisibilityOrNull(ktModifierKeywordToken);
        }
        return null;
    }

    private final Visibility implicitVisibility(FirDeclaration firDeclaration, CheckerContext checkerContext) {
        boolean z = firDeclaration instanceof FirPropertyAccessor;
        if (z) {
            FirPropertyAccessor firPropertyAccessor = (FirPropertyAccessor) firDeclaration;
            if (firPropertyAccessor.isSetter() && (CollectionsKt.last((List) checkerContext.getContainingDeclarations()) instanceof FirClass) && firPropertyAccessor.getPropertySymbol().getRawStatus().isOverride()) {
                return findPropertyAccessorVisibility(firPropertyAccessor, checkerContext);
            }
        }
        if (!z) {
            if (firDeclaration instanceof FirConstructor) {
                FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firDeclaration, checkerContext.getSession());
                if ((containingClassSymbol instanceof FirRegularClassSymbol) && (((FirClassSymbol) containingClassSymbol).getClassKind() == ClassKind.ENUM_CLASS || containingClassSymbol.getResolvedStatus().getModality() == Modality.SEALED)) {
                    return Visibilities.Private.INSTANCE;
                }
                return Visibilities.INSTANCE.getDEFAULT_VISIBILITY();
            }
            if ((firDeclaration instanceof FirSimpleFunction) && (CollectionsKt.last((List) checkerContext.getContainingDeclarations()) instanceof FirClass) && ((FirMemberDeclaration) firDeclaration).getStatus().isOverride()) {
                return findFunctionVisibility((FirSimpleFunction) firDeclaration, checkerContext);
            }
            if ((firDeclaration instanceof FirProperty) && (CollectionsKt.last((List) checkerContext.getContainingDeclarations()) instanceof FirClass) && ((FirMemberDeclaration) firDeclaration).getStatus().isOverride()) {
                return findPropertyVisibility((FirProperty) firDeclaration, checkerContext);
            }
            return Visibilities.INSTANCE.getDEFAULT_VISIBILITY();
        }
        return ((FirPropertyAccessor) firDeclaration).getPropertySymbol().getResolvedStatus().getVisibility();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [T, org.jetbrains.kotlin.descriptors.Visibilities$Private] */
    private final Visibility findBiggestVisibility(Function1<? super Function1<? super FirCallableSymbol<?>, ? extends ProcessorAction>, Unit> processSymbols) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = Visibilities.Private.INSTANCE;
        processSymbols.invoke(new Function1<FirCallableSymbol<?>, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantVisibilityModifierSyntaxChecker.findBiggestVisibility.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Type inference failed for: r4v3, types: [T, org.jetbrains.kotlin.descriptors.Visibility] */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirCallableSymbol<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Integer numCompare = Visibilities.INSTANCE.compare(objectRef.element, it.getResolvedStatus().getVisibility());
                if (numCompare != null && numCompare.intValue() < 0) {
                    objectRef.element = it.getResolvedStatus().getVisibility();
                }
                return ProcessorAction.NEXT;
            }
        });
        return (Visibility) objectRef.element;
    }

    private final Visibility findPropertyAccessorVisibility(FirPropertyAccessor accessor, CheckerContext context) {
        FirClassSymbol<? extends FirClass> symbol;
        FirClass firClassFindClosestClassOrObject = FirHelpersKt.findClosestClassOrObject(context);
        if (firClassFindClosestClassOrObject == null || (symbol = firClassFindClosestClassOrObject.getSymbol()) == null) {
            return Visibilities.Public.INSTANCE;
        }
        final FirPropertySymbol propertySymbol = accessor.getPropertySymbol();
        final FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) symbol, context.getSessionHolder().getSession(), context.getSessionHolder().getScopeSession(), false, FirResolvePhase.STATUS);
        return findBiggestVisibility(new Function1<Function1<? super FirCallableSymbol<?>, ? extends ProcessorAction>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantVisibilityModifierSyntaxChecker.findPropertyAccessorVisibility.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Function1<? super FirCallableSymbol<?>, ? extends ProcessorAction> function1) {
                invoke2(function1);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(final Function1<? super FirCallableSymbol<?>, ? extends ProcessorAction> checkVisibility) {
                Intrinsics.checkNotNullParameter(checkVisibility, "checkVisibility");
                firTypeScopeUnsubstitutedScope.processPropertiesByName(propertySymbol.getName(), new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantVisibilityModifierSyntaxChecker.findPropertyAccessorVisibility.1.1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirVariableSymbol<?> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                        invoke2(firVariableSymbol);
                        return Unit.INSTANCE;
                    }
                });
                FirTypeScopeKt.processOverriddenProperties(firTypeScopeUnsubstitutedScope, propertySymbol, new Function1<FirPropertySymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantVisibilityModifierSyntaxChecker.findPropertyAccessorVisibility.1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final ProcessorAction invoke(FirPropertySymbol property) {
                        Intrinsics.checkNotNullParameter(property, "property");
                        FirPropertyAccessorSymbol setterSymbol = property.getSetterSymbol();
                        return setterSymbol == null ? ProcessorAction.NEXT : checkVisibility.invoke(setterSymbol);
                    }
                });
            }
        });
    }

    private final Visibility findPropertyVisibility(final FirProperty property, CheckerContext context) {
        FirClassSymbol<? extends FirClass> symbol;
        FirClass firClassFindClosestClassOrObject = FirHelpersKt.findClosestClassOrObject(context);
        if (firClassFindClosestClassOrObject == null || (symbol = firClassFindClosestClassOrObject.getSymbol()) == null) {
            return Visibilities.Public.INSTANCE;
        }
        final FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) symbol, context.getSessionHolder().getSession(), context.getSessionHolder().getScopeSession(), false, FirResolvePhase.STATUS);
        return findBiggestVisibility(new Function1<Function1<? super FirCallableSymbol<?>, ? extends ProcessorAction>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantVisibilityModifierSyntaxChecker.findPropertyVisibility.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Function1<? super FirCallableSymbol<?>, ? extends ProcessorAction> function1) {
                invoke2(function1);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Function1<? super FirCallableSymbol<?>, ? extends ProcessorAction> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                firTypeScopeUnsubstitutedScope.processPropertiesByName(property.getSymbol().getName(), new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantVisibilityModifierSyntaxChecker.findPropertyVisibility.1.1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirVariableSymbol<?> it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                        invoke2(firVariableSymbol);
                        return Unit.INSTANCE;
                    }
                });
                FirTypeScopeKt.processOverriddenProperties(firTypeScopeUnsubstitutedScope, property.getSymbol(), (Function1<? super FirPropertySymbol, ? extends ProcessorAction>) it);
            }
        });
    }

    private final Visibility findFunctionVisibility(final FirSimpleFunction function, CheckerContext context) {
        FirClassSymbol<? extends FirClass> symbol;
        FirClass firClassFindClosestClassOrObject = FirHelpersKt.findClosestClassOrObject(context);
        if (firClassFindClosestClassOrObject == null || (symbol = firClassFindClosestClassOrObject.getSymbol()) == null) {
            return Visibilities.Unknown.INSTANCE;
        }
        final FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) symbol, context.getSessionHolder().getSession(), context.getSessionHolder().getScopeSession(), false, FirResolvePhase.STATUS);
        return findBiggestVisibility(new Function1<Function1<? super FirCallableSymbol<?>, ? extends ProcessorAction>, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantVisibilityModifierSyntaxChecker.findFunctionVisibility.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Function1<? super FirCallableSymbol<?>, ? extends ProcessorAction> function1) {
                invoke2(function1);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Function1<? super FirCallableSymbol<?>, ? extends ProcessorAction> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                firTypeScopeUnsubstitutedScope.processFunctionsByName(function.getSymbol().getName(), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantVisibilityModifierSyntaxChecker.findFunctionVisibility.1.1
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirNamedFunctionSymbol it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                        invoke2(firNamedFunctionSymbol);
                        return Unit.INSTANCE;
                    }
                });
                FirTypeScopeKt.processOverriddenFunctions(firTypeScopeUnsubstitutedScope, function.getSymbol(), (Function1<? super FirNamedFunctionSymbol, ? extends ProcessorAction>) it);
            }
        });
    }

    private final void checkElementAndReport(FirDeclaration element, CheckerContext context, DiagnosticReporter reporter) {
        FirMemberDeclaration firMemberDeclaration;
        Iterator it = CollectionsKt.asReversed(context.getContainingDeclarations()).iterator();
        while (true) {
            firMemberDeclaration = null;
            if (!it.hasNext()) {
                break;
            }
            FirDeclaration firDeclaration = (FirDeclaration) it.next();
            firMemberDeclaration = (FirMemberDeclaration) (firDeclaration instanceof FirMemberDeclaration ? firDeclaration : null);
            if (firMemberDeclaration != null && firMemberDeclaration != null) {
                break;
            }
        }
        checkElementAndReport(element, (FirMemberDeclaration) firMemberDeclaration, context, reporter);
    }

    private final boolean isEffectivelyHiddenBy(Visibility visibility, FirMemberDeclaration firMemberDeclaration) {
        EffectiveVisibility.Local effectiveVisibility;
        Visibility visibility2;
        Integer numCompareTo;
        if (firMemberDeclaration == null) {
            return false;
        }
        FirDeclarationStatus status = firMemberDeclaration.getStatus();
        FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
        if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
            effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
        }
        if (effectiveVisibility == null || (visibility2 = effectiveVisibility.toVisibility()) == null) {
            return false;
        }
        if (Intrinsics.areEqual(visibility2, Visibilities.Local.INSTANCE) && Intrinsics.areEqual(visibility, Visibilities.Internal.INSTANCE)) {
            return true;
        }
        return (visibility == null || (numCompareTo = visibility.compareTo(visibility2)) == null || numCompareTo.intValue() <= 0) ? false : true;
    }
}
