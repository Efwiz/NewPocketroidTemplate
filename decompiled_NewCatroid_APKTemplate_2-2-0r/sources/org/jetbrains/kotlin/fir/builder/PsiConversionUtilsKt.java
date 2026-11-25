package org.jetbrains.kotlin.fir.builder;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealPsiSourceElement;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirExpressionRef;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirBlockBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirComponentCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirEqualityOperatorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirTypeOperatorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirWhenSubjectExpressionBuilder;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;

/* JADX INFO: compiled from: PsiConversionUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000~\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aj\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u001d\u0010\u000b\u001a\u0019\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\f¢\u0006\u0002\b\u00102\u0019\u0010\u0011\u001a\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0010H\u0000\u001aI\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u001d\u0010\u000b\u001a\u0019\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\f¢\u0006\u0002\b\u0010H\u0000\u001aU\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u001b2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00142\u001d\u0010\u000b\u001a\u0019\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\f¢\u0006\u0002\b\u0010H\u0000\u001aa\u0010\u001f\u001a\u00020\u001b*\b\u0012\u0004\u0012\u00020!0 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\u001f\u0010%\u001a\u001b\u0012\u0006\u0012\u0004\u0018\u00010&\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001b0\f¢\u0006\u0002\b\u00102\u0019\u0010'\u001a\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0010H\u0000¢\u0006\u0002\u0010(\u001aV\u0010\u001f\u001a\u00020\u001b*\u00020!2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020$0#2\u001f\u0010%\u001a\u001b\u0012\u0006\u0012\u0004\u0018\u00010&\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001b0\f¢\u0006\u0002\b\u00102\u0019\u0010'\u001a\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0010H\u0000¨\u0006*"}, d2 = {"generateDestructuringBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "multiDeclaration", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "container", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "tmpVariable", "", "localEntries", "extractAnnotationsTo", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "", "Lkotlin/ExtensionFunctionType;", "toFirOrImplicitTypeRef", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "generateTemporaryVariable", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "specialName", "", "initializer", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "name", "Lorg/jetbrains/kotlin/name/Name;", "typeRef", "toFirWhenCondition", "", "Lorg/jetbrains/kotlin/psi/KtWhenCondition;", "subject", "Lorg/jetbrains/kotlin/fir/FirExpressionRef;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "convert", "Lorg/jetbrains/kotlin/psi/KtExpression;", "toFirOrErrorTypeRef", "([Lorg/jetbrains/kotlin/psi/KtWhenCondition;Lorg/jetbrains/kotlin/fir/FirExpressionRef;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "whenRefWithSubject", "psi2fir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PsiConversionUtilsKt {
    public static final FirExpression toFirWhenCondition(KtWhenCondition ktWhenCondition, FirExpressionRef<FirWhenExpression> whenRefWithSubject, Function2<? super KtExpression, ? super String, ? extends FirExpression> convert, Function1<? super KtTypeReference, ? extends FirTypeRef> toFirOrErrorTypeRef) {
        KtFakeSourceElement ktFakeSourceElement;
        KtFakeSourceElement ktFakeSourceElement2;
        KtFakeSourceElement ktFakeSourceElement3;
        KtFakeSourceElement ktFakeSourceElement4;
        Intrinsics.checkNotNullParameter(ktWhenCondition, "<this>");
        Intrinsics.checkNotNullParameter(whenRefWithSubject, "whenRefWithSubject");
        Intrinsics.checkNotNullParameter(convert, "convert");
        Intrinsics.checkNotNullParameter(toFirOrErrorTypeRef, "toFirOrErrorTypeRef");
        KtWhenCondition ktWhenCondition2 = ktWhenCondition;
        KtFakeSourceElementKind.WhenGeneratedSubject whenGeneratedSubject = KtFakeSourceElementKind.WhenGeneratedSubject.INSTANCE;
        if (whenGeneratedSubject instanceof KtRealSourceElementKind) {
            ktFakeSourceElement = new KtRealPsiSourceElement(ktWhenCondition2);
        } else {
            if (!(whenGeneratedSubject instanceof KtFakeSourceElementKind)) {
                throw new NoWhenBranchMatchedException();
            }
            ktFakeSourceElement = new KtFakeSourceElement(ktWhenCondition2, whenGeneratedSubject);
        }
        FirWhenSubjectExpressionBuilder firWhenSubjectExpressionBuilder = new FirWhenSubjectExpressionBuilder();
        KtSourceElement ktSourceElement = ktFakeSourceElement;
        firWhenSubjectExpressionBuilder.setSource(ktSourceElement);
        firWhenSubjectExpressionBuilder.setWhenRef(whenRefWithSubject);
        FirWhenSubjectExpression firWhenSubjectExpressionMo6809build = firWhenSubjectExpressionBuilder.mo6809build();
        KtSourceElement ktFakeSourceElement5 = null;
        if (!(ktWhenCondition instanceof KtWhenConditionWithExpression)) {
            if (ktWhenCondition instanceof KtWhenConditionInRange) {
                KtWhenConditionInRange ktWhenConditionInRange = (KtWhenConditionInRange) ktWhenCondition;
                FirExpression firExpressionInvoke = convert.invoke(ktWhenConditionInRange.getRangeExpression(), "No range in condition with range");
                FirWhenSubjectExpression firWhenSubjectExpression = firWhenSubjectExpressionMo6809build;
                boolean zIsNegated = ktWhenConditionInRange.isNegated();
                KtFakeSourceElementKind.WhenCondition whenCondition = KtFakeSourceElementKind.WhenCondition.INSTANCE;
                if (whenCondition instanceof KtRealSourceElementKind) {
                    ktFakeSourceElement3 = new KtRealPsiSourceElement(ktWhenCondition2);
                } else {
                    if (!(whenCondition instanceof KtFakeSourceElementKind)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ktFakeSourceElement3 = new KtFakeSourceElement(ktWhenCondition2, whenCondition);
                }
                KtSourceElement ktSourceElement2 = ktFakeSourceElement3;
                KtOperationReferenceExpression operationReference = ktWhenConditionInRange.getOperationReference();
                Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
                KtOperationReferenceExpression ktOperationReferenceExpression = operationReference;
                Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
                if (obj instanceof KtRealSourceElementKind) {
                    ktFakeSourceElement4 = new KtRealPsiSourceElement(ktOperationReferenceExpression);
                } else {
                    if (!(obj instanceof KtFakeSourceElementKind)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ktFakeSourceElement4 = new KtFakeSourceElement(ktOperationReferenceExpression, (KtFakeSourceElementKind) obj);
                }
                return ConversionUtilsKt.generateContainsOperation(firExpressionInvoke, firWhenSubjectExpression, zIsNegated, ktSourceElement2, ktFakeSourceElement4);
            }
            if (!(ktWhenCondition instanceof KtWhenConditionIsPattern)) {
                return FirExpressionUtilKt.buildErrorExpression$default(ktSourceElement, new ConeSyntaxDiagnostic("Unsupported when condition: " + ktWhenCondition.getClass()), null, 4, null);
            }
            FirTypeOperatorCallBuilder firTypeOperatorCallBuilder = new FirTypeOperatorCallBuilder();
            Object obj2 = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
            if (obj2 instanceof KtRealSourceElementKind) {
                ktFakeSourceElement2 = new KtRealPsiSourceElement(ktWhenCondition2);
            } else {
                if (!(obj2 instanceof KtFakeSourceElementKind)) {
                    throw new NoWhenBranchMatchedException();
                }
                ktFakeSourceElement2 = new KtFakeSourceElement(ktWhenCondition2, (KtFakeSourceElementKind) obj2);
            }
            firTypeOperatorCallBuilder.setSource(ktFakeSourceElement2);
            KtWhenConditionIsPattern ktWhenConditionIsPattern = (KtWhenConditionIsPattern) ktWhenCondition;
            firTypeOperatorCallBuilder.setOperation(ktWhenConditionIsPattern.isNegated() ? FirOperation.NOT_IS : FirOperation.IS);
            firTypeOperatorCallBuilder.setConversionTypeRef(toFirOrErrorTypeRef.invoke(ktWhenConditionIsPattern.getTypeReference()));
            firTypeOperatorCallBuilder.setArgumentList(FirArgumentUtilKt.buildUnaryArgumentList(firWhenSubjectExpressionMo6809build));
            return firTypeOperatorCallBuilder.mo6809build();
        }
        FirEqualityOperatorCallBuilder firEqualityOperatorCallBuilder = new FirEqualityOperatorCallBuilder();
        KtWhenConditionWithExpression ktWhenConditionWithExpression = (KtWhenConditionWithExpression) ktWhenCondition;
        KtExpression expression = ktWhenConditionWithExpression.getExpression();
        KtExpression firstChild = expression != null ? expression : ktWhenConditionWithExpression.getFirstChild();
        if (firstChild != null) {
            Intrinsics.checkNotNull(firstChild);
            KtFakeSourceElementKind.WhenCondition whenCondition2 = KtFakeSourceElementKind.WhenCondition.INSTANCE;
            if (whenCondition2 instanceof KtRealSourceElementKind) {
                ktFakeSourceElement5 = new KtRealPsiSourceElement(firstChild);
            } else {
                if (!(whenCondition2 instanceof KtFakeSourceElementKind)) {
                    throw new NoWhenBranchMatchedException();
                }
                ktFakeSourceElement5 = new KtFakeSourceElement(firstChild, whenCondition2);
            }
        }
        firEqualityOperatorCallBuilder.setSource(ktFakeSourceElement5);
        firEqualityOperatorCallBuilder.setOperation(FirOperation.EQ);
        firEqualityOperatorCallBuilder.setArgumentList(FirArgumentUtilKt.buildBinaryArgumentList(firWhenSubjectExpressionMo6809build, convert.invoke(ktWhenConditionWithExpression.getExpression(), "No expression in condition with expression")));
        return firEqualityOperatorCallBuilder.mo6809build();
    }

    public static /* synthetic */ FirVariable generateTemporaryVariable$default(FirModuleData firModuleData, KtSourceElement ktSourceElement, Name name2, FirExpression firExpression, FirTypeRef firTypeRef, Function2 function2, int i, Object obj) {
        if ((i & 16) != 0) {
            firTypeRef = null;
        }
        return generateTemporaryVariable(firModuleData, ktSourceElement, name2, firExpression, firTypeRef, function2);
    }

    public static final FirVariable generateTemporaryVariable(FirModuleData moduleData, KtSourceElement ktSourceElement, String specialName, FirExpression initializer, Function2<? super KtAnnotated, ? super FirAnnotationContainerBuilder, Unit> extractAnnotationsTo) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(specialName, "specialName");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        Intrinsics.checkNotNullParameter(extractAnnotationsTo, "extractAnnotationsTo");
        Name nameSpecial = Name.special("<" + specialName + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        return generateTemporaryVariable(moduleData, ktSourceElement, nameSpecial, initializer, null, extractAnnotationsTo);
    }

    public static final FirExpression toFirWhenCondition(KtWhenCondition[] ktWhenConditionArr, FirExpressionRef<FirWhenExpression> subject, Function2<? super KtExpression, ? super String, ? extends FirExpression> convert, Function1<? super KtTypeReference, ? extends FirTypeRef> toFirOrErrorTypeRef) {
        Intrinsics.checkNotNullParameter(ktWhenConditionArr, "<this>");
        Intrinsics.checkNotNullParameter(subject, "subject");
        Intrinsics.checkNotNullParameter(convert, "convert");
        Intrinsics.checkNotNullParameter(toFirOrErrorTypeRef, "toFirOrErrorTypeRef");
        ArrayList arrayList = new ArrayList(ktWhenConditionArr.length);
        for (KtWhenCondition ktWhenCondition : ktWhenConditionArr) {
            arrayList.add(toFirWhenCondition(ktWhenCondition, subject, convert, toFirOrErrorTypeRef));
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return ConversionUtilsKt.buildBalancedOrExpressionTree$default(arrayList2, 0, 0, 6, null);
    }

    public static final FirVariable generateTemporaryVariable(FirModuleData moduleData, KtSourceElement ktSourceElement, Name name2, FirExpression initializer, FirTypeRef firTypeRef, Function2<? super KtAnnotated, ? super FirAnnotationContainerBuilder, Unit> extractAnnotationsTo) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        Intrinsics.checkNotNullParameter(extractAnnotationsTo, "extractAnnotationsTo");
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        firPropertyBuilder.setSource(ktSourceElement);
        firPropertyBuilder.setModuleData(moduleData);
        firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
        if (firTypeRef == null) {
            firTypeRef = FirImplicitTypeRefImplWithoutSource.INSTANCE;
        }
        firPropertyBuilder.setReturnTypeRef(firTypeRef);
        firPropertyBuilder.setName(name2);
        firPropertyBuilder.setInitializer(initializer);
        firPropertyBuilder.setSymbol(new FirPropertySymbol(name2));
        firPropertyBuilder.setVar(false);
        firPropertyBuilder.setLocal(true);
        firPropertyBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
        PsiElement psi = KtSourceElementKt.getPsi(ktSourceElement);
        KtAnnotated ktAnnotated = psi instanceof KtAnnotated ? (KtAnnotated) psi : null;
        if (ktAnnotated != null) {
            extractAnnotationsTo.invoke(ktAnnotated, firPropertyBuilder);
        }
        return firPropertyBuilder.mo6809build();
    }

    public static final FirBlock generateDestructuringBlock(FirModuleData moduleData, KtDestructuringDeclaration multiDeclaration, FirVariable container, boolean z, boolean z2, Function2<? super KtAnnotated, ? super FirAnnotationContainerBuilder, Unit> extractAnnotationsTo, Function1<? super KtTypeReference, ? extends FirTypeRef> toFirOrImplicitTypeRef) {
        KtFakeSourceElement ktFakeSourceElement;
        KtFakeSourceElement ktFakeSourceElement2;
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(multiDeclaration, "multiDeclaration");
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(extractAnnotationsTo, "extractAnnotationsTo");
        Intrinsics.checkNotNullParameter(toFirOrImplicitTypeRef, "toFirOrImplicitTypeRef");
        FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
        KtDestructuringDeclaration ktDestructuringDeclaration = multiDeclaration;
        Object obj = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
        if (obj instanceof KtRealSourceElementKind) {
            ktFakeSourceElement = new KtRealPsiSourceElement(ktDestructuringDeclaration);
        } else {
            if (!(obj instanceof KtFakeSourceElementKind)) {
                throw new NoWhenBranchMatchedException();
            }
            ktFakeSourceElement = new KtFakeSourceElement(ktDestructuringDeclaration, (KtFakeSourceElementKind) obj);
        }
        firBlockBuilder.setSource(ktFakeSourceElement);
        if (z) {
            firBlockBuilder.getStatements().add(container);
        }
        boolean zIsVar = multiDeclaration.isVar();
        int i = 0;
        for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : multiDeclaration.getEntries()) {
            i++;
            PsiElement psiElementMo7392getNameIdentifier = ktDestructuringDeclarationEntry.mo7392getNameIdentifier();
            if (!Intrinsics.areEqual(psiElementMo7392getNameIdentifier != null ? psiElementMo7392getNameIdentifier.getText() : null, "_")) {
                Intrinsics.checkNotNull(ktDestructuringDeclarationEntry);
                KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry2 = ktDestructuringDeclarationEntry;
                Object obj2 = (KtSourceElementKind) KtRealSourceElementKind.INSTANCE;
                if (obj2 instanceof KtRealSourceElementKind) {
                    ktFakeSourceElement2 = new KtRealPsiSourceElement(ktDestructuringDeclarationEntry2);
                } else {
                    if (!(obj2 instanceof KtFakeSourceElementKind)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ktFakeSourceElement2 = new KtFakeSourceElement(ktDestructuringDeclarationEntry2, (KtFakeSourceElementKind) obj2);
                }
                Name nameAsSafeName = ktDestructuringDeclarationEntry.getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                List<FirStatement> statements = firBlockBuilder.getStatements();
                FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
                KtSourceElement ktSourceElement = ktFakeSourceElement2;
                firPropertyBuilder.setSource(ktSourceElement);
                firPropertyBuilder.setModuleData(moduleData);
                firPropertyBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firPropertyBuilder.setReturnTypeRef(toFirOrImplicitTypeRef.invoke(ktDestructuringDeclarationEntry.mo7396getTypeReference()));
                firPropertyBuilder.setName(nameAsSafeName);
                FirComponentCallBuilder firComponentCallBuilder = new FirComponentCallBuilder();
                KtSourceElement ktSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(ktSourceElement, KtFakeSourceElementKind.DesugaredComponentFunctionCall.INSTANCE, 0, 0, 6, null);
                firComponentCallBuilder.setSource(ktSourceElementFakeElement$default);
                firComponentCallBuilder.setExplicitReceiver(ConversionUtilsKt.generateResolvedAccessExpression(ktSourceElementFakeElement$default, container));
                firComponentCallBuilder.setComponentIndex(i);
                firPropertyBuilder.setInitializer(firComponentCallBuilder.mo6809build());
                firPropertyBuilder.setVar(zIsVar);
                firPropertyBuilder.setLocal(z2);
                firPropertyBuilder.setStatus(new FirDeclarationStatusImpl(z2 ? Visibilities.Local.INSTANCE : Visibilities.Public.INSTANCE, Modality.FINAL));
                firPropertyBuilder.setSymbol(new FirPropertySymbol(nameAsSafeName));
                extractAnnotationsTo.invoke(ktDestructuringDeclarationEntry, firPropertyBuilder);
                statements.add(firPropertyBuilder.mo6809build());
            }
        }
        return firBlockBuilder.mo6809build();
    }
}
