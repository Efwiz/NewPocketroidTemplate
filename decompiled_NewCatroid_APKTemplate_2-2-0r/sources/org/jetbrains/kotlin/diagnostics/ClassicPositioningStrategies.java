package org.jetbrains.kotlin.diagnostics;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.UnreachableCode;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: ClassicPositioningStrategies.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R \u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n*\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/ClassicPositioningStrategies;", "", "()V", "ACTUAL_DECLARATION_NAME", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "INCOMPATIBLE_DECLARATION", "UNREACHABLE_CODE", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "firstIncompatibility", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticMarker;", "getFirstIncompatibility", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticMarker;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassicPositioningStrategies {
    public static final ClassicPositioningStrategies INSTANCE = new ClassicPositioningStrategies();
    public static final PositioningStrategy<KtNamedDeclaration> ACTUAL_DECLARATION_NAME = new PositioningStrategies.DeclarationHeader<KtNamedDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.ClassicPositioningStrategies$ACTUAL_DECLARATION_NAME$1
        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> mark(KtNamedDeclaration element) {
            Intrinsics.checkNotNullParameter(element, "element");
            PsiElement nameIdentifier = element.mo7392getNameIdentifier();
            if (nameIdentifier != null) {
                return PositioningStrategyKt.markElement(nameIdentifier);
            }
            return element instanceof KtNamedFunction ? PositioningStrategies.DECLARATION_SIGNATURE.mark(element) : PositioningStrategies.DEFAULT.mark(element);
        }
    };
    public static final PositioningStrategy<KtNamedDeclaration> INCOMPATIBLE_DECLARATION = new PositioningStrategies.DeclarationHeader<KtNamedDeclaration>() { // from class: org.jetbrains.kotlin.diagnostics.ClassicPositioningStrategies$INCOMPATIBLE_DECLARATION$1
        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> markDiagnostic(DiagnosticMarker diagnostic) {
            List<TextRange> listMarkElement;
            KtPropertyAccessor setter;
            PsiElement funKeyword;
            PsiElement modifier;
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            PsiElement psiElement = diagnostic.getPsiElement();
            Intrinsics.checkNotNull(psiElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtNamedDeclaration");
            KtNamedDeclaration ktNamedDeclaration = (KtNamedDeclaration) psiElement;
            PsiElement modifierList = null;
            KtCallableDeclaration ktCallableDeclaration = ktNamedDeclaration instanceof KtCallableDeclaration ? (KtCallableDeclaration) ktNamedDeclaration : null;
            ExpectActualCompatibility.Incompatible firstIncompatibility = ClassicPositioningStrategies.INSTANCE.getFirstIncompatibility(diagnostic);
            if (!(firstIncompatibility == null ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.Unknown.INSTANCE) ? true : firstIncompatibility instanceof ExpectActualCompatibility.Incompatible.ClassScopes ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.EnumEntries.INSTANCE))) {
                if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ClassKind.INSTANCE)) {
                    KtModifierList modifierList2 = ktNamedDeclaration.getModifierList();
                    if (modifierList2 == null || (modifier = modifierList2.getModifier(KtTokens.ENUM_KEYWORD)) == null) {
                        KtModifierList modifierList3 = ktNamedDeclaration.getModifierList();
                        if (modifierList3 != null) {
                            modifierList = modifierList3.getModifier(KtTokens.ANNOTATION_KEYWORD);
                        }
                    } else {
                        modifierList = modifier;
                    }
                    ASTNode aSTNodeFindChildByType = ktNamedDeclaration.getNode().findChildByType(PositioningStrategies.INSTANCE.getClassKindTokens());
                    if (aSTNodeFindChildByType == null || (funKeyword = aSTNodeFindChildByType.getPsi()) == null) {
                        funKeyword = ktNamedDeclaration.mo7392getNameIdentifier();
                    }
                    if (modifierList != null && funKeyword != null) {
                        return PositioningStrategyKt.markRange(modifierList, funKeyword);
                    }
                } else {
                    if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.TypeParameterNames.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.FunctionTypeParameterCount.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ClassTypeParameterCount.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.FunctionTypeParameterUpperBounds.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ClassTypeParameterUpperBounds.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.TypeParameterVariance.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.TypeParameterReified.INSTANCE)) {
                        KtTypeParameterListOwner ktTypeParameterListOwner = ktNamedDeclaration instanceof KtTypeParameterListOwner ? (KtTypeParameterListOwner) ktNamedDeclaration : null;
                        modifierList = ktTypeParameterListOwner != null ? ktTypeParameterListOwner.mo7395getTypeParameterList() : null;
                    } else if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.CallableKind.INSTANCE)) {
                        KtNamedFunction ktNamedFunction = ktCallableDeclaration instanceof KtNamedFunction ? (KtNamedFunction) ktCallableDeclaration : null;
                        if (ktNamedFunction == null || (funKeyword = ktNamedFunction.getFunKeyword()) == null) {
                            KtProperty ktProperty = ktCallableDeclaration instanceof KtProperty ? (KtProperty) ktCallableDeclaration : null;
                            if (ktProperty != null) {
                                modifierList = ktProperty.getValOrVarKeyword();
                            }
                        }
                    } else if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ParameterShape.INSTANCE)) {
                        if (ktCallableDeclaration != null) {
                            KtElementImplStub ktElementImplStubMo7393getReceiverTypeReference = ktCallableDeclaration.mo7393getReceiverTypeReference();
                            if (ktElementImplStubMo7393getReceiverTypeReference == null) {
                                ktElementImplStubMo7393getReceiverTypeReference = ktCallableDeclaration.getValueParameterList();
                            }
                            modifierList = ktElementImplStubMo7393getReceiverTypeReference;
                        }
                        modifierList = modifierList;
                    } else {
                        if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ParameterCount.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ParameterTypes.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ParameterNames.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ValueParameterVararg.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ActualFunctionWithDefaultParameters.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ValueParameterNoinline.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ValueParameterCrossinline.INSTANCE)) {
                            modifierList = ktCallableDeclaration != null ? ktCallableDeclaration.getValueParameterList() : null;
                        } else if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ReturnType.INSTANCE)) {
                            modifierList = ktCallableDeclaration != null ? ktCallableDeclaration.mo7396getTypeReference() : null;
                        } else {
                            if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.FunctionModifiersDifferent.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.FunctionModifiersNotSubset.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.PropertyLateinitModifier.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.PropertyConstModifier.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.ClassModifiers.INSTANCE) ? true : Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.FunInterfaceModifier.INSTANCE)) {
                                modifierList = ktNamedDeclaration.getModifierList();
                            } else if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.PropertyKind.INSTANCE)) {
                                ASTNode aSTNodeFindChildByType2 = ktNamedDeclaration.getNode().findChildByType(PositioningStrategies.INSTANCE.getPropertyKindTokens());
                                if (aSTNodeFindChildByType2 != null) {
                                    modifierList = aSTNodeFindChildByType2.getPsi();
                                }
                            } else if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.Supertypes.INSTANCE)) {
                                KtClassOrObject ktClassOrObject = ktNamedDeclaration instanceof KtClassOrObject ? (KtClassOrObject) ktNamedDeclaration : null;
                                modifierList = ktClassOrObject != null ? ktClassOrObject.getSuperTypeList() : null;
                            } else if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.Modality.INSTANCE)) {
                                modifierList = KtPsiUtilKt.modalityModifier(ktNamedDeclaration);
                            } else if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.Visibility.INSTANCE)) {
                                modifierList = KtPsiUtilKt.visibilityModifier(ktNamedDeclaration);
                            } else if (Intrinsics.areEqual(firstIncompatibility, ExpectActualCompatibility.Incompatible.PropertySetterVisibility.INSTANCE)) {
                                KtProperty ktProperty2 = ktNamedDeclaration instanceof KtProperty ? (KtProperty) ktNamedDeclaration : null;
                                if (ktProperty2 != null && (setter = ktProperty2.getSetter()) != null) {
                                    modifierList = setter.getModifierList();
                                }
                                modifierList = modifierList;
                            } else {
                                throw new NoWhenBranchMatchedException();
                            }
                        }
                    }
                }
                modifierList = funKeyword;
            }
            return (modifierList == null || (listMarkElement = PositioningStrategyKt.markElement(modifierList)) == null) ? ClassicPositioningStrategies.ACTUAL_DECLARATION_NAME.mark(ktNamedDeclaration) : listMarkElement;
        }
    };
    public static final PositioningStrategy<PsiElement> UNREACHABLE_CODE = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.ClassicPositioningStrategies$UNREACHABLE_CODE$1
        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> markDiagnostic(DiagnosticMarker diagnostic) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            DiagnosticWithParameters2Marker diagnosticWithParameters2Marker = (DiagnosticWithParameters2Marker) diagnostic;
            UnreachableCode.Companion companion = UnreachableCode.INSTANCE;
            PsiElement psiElement = diagnosticWithParameters2Marker.getPsiElement();
            Intrinsics.checkNotNull(psiElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtElement");
            return companion.getUnreachableTextRanges((KtElement) psiElement, (Set) diagnosticWithParameters2Marker.getA(), (Set) diagnosticWithParameters2Marker.getB());
        }
    };

    private ClassicPositioningStrategies() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExpectActualCompatibility.Incompatible<MemberDescriptor> getFirstIncompatibility(DiagnosticMarker diagnosticMarker) {
        Object b;
        String factoryName = diagnosticMarker.getFactoryName();
        if (Intrinsics.areEqual(factoryName, Errors.NO_ACTUAL_FOR_EXPECT.getName())) {
            Intrinsics.checkNotNull(diagnosticMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters3Marker<*, *, *>");
            b = ((DiagnosticWithParameters3Marker) diagnosticMarker).getC();
        } else if (Intrinsics.areEqual(factoryName, Errors.ACTUAL_WITHOUT_EXPECT.getName())) {
            Intrinsics.checkNotNull(diagnosticMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters2Marker<*, *>");
            b = ((DiagnosticWithParameters2Marker) diagnosticMarker).getB();
        } else {
            b = null;
        }
        Map map = b instanceof Map ? (Map) b : null;
        if (map == null) {
            return null;
        }
        return (ExpectActualCompatibility.Incompatible) CollectionsKt.firstOrNull(map.keySet());
    }
}
