package org.jetbrains.kotlin.fir.contracts.description;

import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.contracts.description.KtBinaryLogicExpression;
import org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtConstantReference;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor;
import org.jetbrains.kotlin.contracts.description.KtIsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.KtIsNullPredicate;
import org.jetbrains.kotlin.contracts.description.KtLogicalNot;
import org.jetbrains.kotlin.contracts.description.KtReturnsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtValueParameterReference;
import org.jetbrains.kotlin.fir.contracts.FirContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirLegacyRawContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirRawContractDescription;
import org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription;
import org.jetbrains.kotlin.fir.contracts.impl.FirEmptyContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.renderer.FirPrinter;
import org.jetbrains.kotlin.fir.renderer.FirRenderer;
import org.jetbrains.kotlin.fir.renderer.FirRendererComponents;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;

/* JADX INFO: compiled from: ConeContractRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¾\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u001c\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0001B\u0005¢\u0006\u0002\u0010\u0006J>\u0010\u0016\u001a\u00020\u00022\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00020\u001bH\u0002J0\u0010\u001c\u001a\u00020\u001d2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0018H\u0002J\u000e\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010\u001e\u001a\u00020\u00022\u0006\u0010!\u001a\u00020\"J\u0015\u0010\u001e\u001a\u00020\u00022\u0006\u0010#\u001a\u00020$H\u0000¢\u0006\u0002\b%J\u0015\u0010\u001e\u001a\u00020\u00022\u0006\u0010&\u001a\u00020'H\u0000¢\u0006\u0002\b%J\u0015\u0010\u001e\u001a\u00020\u00022\u0006\u0010(\u001a\u00020)H\u0000¢\u0006\u0002\b%J\u000e\u0010\u001e\u001a\u00020\u00022\u0006\u0010*\u001a\u00020+J&\u0010,\u001a\u00020\u00022\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050.2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J&\u00100\u001a\u00020\u00022\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005022\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J&\u00103\u001a\u00020\u00022\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005052\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J&\u00106\u001a\u00020\u00022\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005082\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J&\u00109\u001a\u00020\u00022\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050;2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J&\u0010<\u001a\u00020\u00022\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050>2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J&\u0010?\u001a\u00020\u00022\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050A2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J&\u0010B\u001a\u00020\u00022\u0012\u0010C\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050D2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J&\u0010E\u001a\u00020\u00022\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050G2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u0018\u0010H\u001a\u00020\u001d*\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0018H\u0002R\u001a\u0010\u0007\u001a\u00020\bX\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u00060\u0012R\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006I"}, d2 = {"Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractRenderer;", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "()V", "components", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "getComponents$tree", "()Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "setComponents$tree", "(Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;)V", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "visitor", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "getVisitor", "()Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "inBracketsIfNecessary", "parent", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "child", "block", "Lkotlin/Function0;", "needsBrackets", "", "render", "contractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirContractDescription;", "effectDeclaration", "Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", "legacyRawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirLegacyRawContractDescription;", "render$tree", "rawContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirRawContractDescription;", "resolvedContractDescription", "Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "visitCallsEffectDeclaration", "callsEffect", "Lorg/jetbrains/kotlin/contracts/description/KtCallsEffectDeclaration;", "data", "visitConditionalEffectDeclaration", "conditionalEffect", "Lorg/jetbrains/kotlin/contracts/description/KtConditionalEffectDeclaration;", "visitConstantDescriptor", "constantReference", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "visitIsInstancePredicate", "isInstancePredicate", "Lorg/jetbrains/kotlin/contracts/description/KtIsInstancePredicate;", "visitIsNullPredicate", "isNullPredicate", "Lorg/jetbrains/kotlin/contracts/description/KtIsNullPredicate;", "visitLogicalBinaryOperationContractExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/contracts/description/KtBinaryLogicExpression;", "visitLogicalNot", "logicalNot", "Lorg/jetbrains/kotlin/contracts/description/KtLogicalNot;", "visitReturnsEffectDeclaration", "returnsEffect", "Lorg/jetbrains/kotlin/contracts/description/KtReturnsEffectDeclaration;", "visitValueParameterReference", "valueParameterReference", "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "isAtom", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeContractRenderer extends KtContractDescriptionVisitor {
    public FirRendererComponents components;

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitCallsEffectDeclaration(KtCallsEffectDeclaration ktCallsEffectDeclaration, Object obj) {
        visitCallsEffectDeclaration((KtCallsEffectDeclaration<ConeKotlinType, ConeDiagnostic>) ktCallsEffectDeclaration, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitConditionalEffectDeclaration(KtConditionalEffectDeclaration ktConditionalEffectDeclaration, Object obj) {
        visitConditionalEffectDeclaration((KtConditionalEffectDeclaration<ConeKotlinType, ConeDiagnostic>) ktConditionalEffectDeclaration, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitConstantDescriptor(KtConstantReference ktConstantReference, Object obj) {
        visitConstantDescriptor((KtConstantReference<ConeKotlinType, ConeDiagnostic>) ktConstantReference, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitIsInstancePredicate(KtIsInstancePredicate ktIsInstancePredicate, Object obj) {
        visitIsInstancePredicate((KtIsInstancePredicate<ConeKotlinType, ConeDiagnostic>) ktIsInstancePredicate, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitIsNullPredicate(KtIsNullPredicate ktIsNullPredicate, Object obj) {
        visitIsNullPredicate((KtIsNullPredicate<ConeKotlinType, ConeDiagnostic>) ktIsNullPredicate, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitLogicalBinaryOperationContractExpression(KtBinaryLogicExpression ktBinaryLogicExpression, Object obj) {
        visitLogicalBinaryOperationContractExpression((KtBinaryLogicExpression<ConeKotlinType, ConeDiagnostic>) ktBinaryLogicExpression, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitLogicalNot(KtLogicalNot ktLogicalNot, Object obj) {
        visitLogicalNot((KtLogicalNot<ConeKotlinType, ConeDiagnostic>) ktLogicalNot, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitReturnsEffectDeclaration(KtReturnsEffectDeclaration ktReturnsEffectDeclaration, Object obj) {
        visitReturnsEffectDeclaration((KtReturnsEffectDeclaration<ConeKotlinType, ConeDiagnostic>) ktReturnsEffectDeclaration, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Object visitValueParameterReference(KtValueParameterReference ktValueParameterReference, Object obj) {
        visitValueParameterReference((KtValueParameterReference<ConeKotlinType, ConeDiagnostic>) ktValueParameterReference, (Void) obj);
        return Unit.INSTANCE;
    }

    public final FirRendererComponents getComponents$tree() {
        FirRendererComponents firRendererComponents = this.components;
        if (firRendererComponents != null) {
            return firRendererComponents;
        }
        Intrinsics.throwUninitializedPropertyAccessException("components");
        return null;
    }

    public final void setComponents$tree(FirRendererComponents firRendererComponents) {
        Intrinsics.checkNotNullParameter(firRendererComponents, "<set-?>");
        this.components = firRendererComponents;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirPrinter getPrinter() {
        return getComponents$tree().getPrinter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirRenderer.Visitor getVisitor() {
        return getComponents$tree().getVisitor();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void render(FirDeclaration declaration) {
        FirContractDescription contractDescription;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FirContractDescriptionOwner firContractDescriptionOwner = declaration instanceof FirContractDescriptionOwner ? (FirContractDescriptionOwner) declaration : null;
        if (firContractDescriptionOwner == null || (contractDescription = firContractDescriptionOwner.getContractDescription()) == null) {
            return;
        }
        render(contractDescription);
    }

    public final void render(FirContractDescription contractDescription) {
        Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
        getPrinter().pushIndent$tree();
        if (!(contractDescription instanceof FirEmptyContractDescription)) {
            getPrinter().newLine();
            getPrinter().print("[" + (contractDescription instanceof FirResolvedContractDescription ? "R|" : "") + "Contract description]");
        }
        if (contractDescription instanceof FirLegacyRawContractDescription) {
            render$tree((FirLegacyRawContractDescription) contractDescription);
        } else if (contractDescription instanceof FirRawContractDescription) {
            render$tree((FirRawContractDescription) contractDescription);
        } else if (contractDescription instanceof FirResolvedContractDescription) {
            getPrinter().println(new Object[0]);
            render$tree((FirResolvedContractDescription) contractDescription);
        }
        getPrinter().popIndent$tree();
    }

    public final void render(FirEffectDeclaration effectDeclaration) {
        Intrinsics.checkNotNullParameter(effectDeclaration, "effectDeclaration");
        getPrinter().newLine();
        getPrinter().println("[Effect declaration] <");
        effectDeclaration.getEffect().accept(this, null);
        getPrinter().println(new Object[0]);
        getPrinter().println(">");
    }

    public final void render$tree(final FirLegacyRawContractDescription legacyRawContractDescription) {
        Intrinsics.checkNotNullParameter(legacyRawContractDescription, "legacyRawContractDescription");
        getPrinter().renderInBraces("<", ">", new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.contracts.description.ConeContractRenderer.render.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                legacyRawContractDescription.getContractCall().accept(this.getVisitor());
                this.getPrinter().newLine();
            }
        });
    }

    public final void render$tree(final FirRawContractDescription rawContractDescription) {
        Intrinsics.checkNotNullParameter(rawContractDescription, "rawContractDescription");
        getPrinter().renderInBraces("<", ">", new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.contracts.description.ConeContractRenderer.render.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                ConeContractRenderer.this.getPrinter().renderSeparatedWithNewlines$tree(rawContractDescription.getRawEffects(), ConeContractRenderer.this.getVisitor());
                ConeContractRenderer.this.getPrinter().newLine();
            }
        });
    }

    public final void render$tree(FirResolvedContractDescription resolvedContractDescription) {
        Intrinsics.checkNotNullParameter(resolvedContractDescription, "resolvedContractDescription");
        getPrinter().println(" <");
        getPrinter().pushIndent$tree();
        Iterator<T> it = resolvedContractDescription.getEffects().iterator();
        while (it.hasNext()) {
            ((FirEffectDeclaration) it.next()).getEffect().accept(this, null);
            getPrinter().println(new Object[0]);
        }
        getPrinter().popIndent$tree();
        getPrinter().println(">");
    }

    public void visitConditionalEffectDeclaration(KtConditionalEffectDeclaration<ConeKotlinType, ConeDiagnostic> conditionalEffect, Void data) {
        Intrinsics.checkNotNullParameter(conditionalEffect, "conditionalEffect");
        ConeContractRenderer coneContractRenderer = this;
        conditionalEffect.getEffect().accept(coneContractRenderer, data);
        getPrinter().print(" -> ");
        conditionalEffect.getCondition().accept(coneContractRenderer, data);
    }

    public void visitReturnsEffectDeclaration(KtReturnsEffectDeclaration<ConeKotlinType, ConeDiagnostic> returnsEffect, Void data) {
        Intrinsics.checkNotNullParameter(returnsEffect, "returnsEffect");
        getPrinter().print("Returns(");
        returnsEffect.getValue().accept(this, data);
        getPrinter().print(Constant.AFTER_QUTO);
    }

    public void visitCallsEffectDeclaration(KtCallsEffectDeclaration<ConeKotlinType, ConeDiagnostic> callsEffect, Void data) {
        Intrinsics.checkNotNullParameter(callsEffect, "callsEffect");
        getPrinter().print("CallsInPlace(");
        callsEffect.getValueParameterReference().accept(this, data);
        getPrinter().print(", " + callsEffect.getKind() + ')');
    }

    public void visitLogicalBinaryOperationContractExpression(final KtBinaryLogicExpression<ConeKotlinType, ConeDiagnostic> binaryLogicExpression, final Void data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        KtBinaryLogicExpression<ConeKotlinType, ConeDiagnostic> ktBinaryLogicExpression = binaryLogicExpression;
        inBracketsIfNecessary(ktBinaryLogicExpression, binaryLogicExpression.getLeft(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.contracts.description.ConeContractRenderer.visitLogicalBinaryOperationContractExpression.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                binaryLogicExpression.getLeft().accept(this, data);
            }
        });
        getPrinter().print(" " + binaryLogicExpression.getKind().getToken() + StringUtil.SPACE);
        inBracketsIfNecessary(ktBinaryLogicExpression, binaryLogicExpression.getRight(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.contracts.description.ConeContractRenderer.visitLogicalBinaryOperationContractExpression.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                binaryLogicExpression.getRight().accept(this, data);
            }
        });
    }

    public void visitLogicalNot(KtLogicalNot<ConeKotlinType, ConeDiagnostic> logicalNot, Void data) {
        Intrinsics.checkNotNullParameter(logicalNot, "logicalNot");
        inBracketsIfNecessary(logicalNot, logicalNot.getArg(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.contracts.description.ConeContractRenderer.visitLogicalNot.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                ConeContractRenderer.this.getPrinter().print("!");
            }
        });
        logicalNot.getArg().accept(this, data);
    }

    public void visitIsInstancePredicate(KtIsInstancePredicate<ConeKotlinType, ConeDiagnostic> isInstancePredicate, Void data) {
        Intrinsics.checkNotNullParameter(isInstancePredicate, "isInstancePredicate");
        isInstancePredicate.getArg().accept(this, data);
        getPrinter().print(" " + (isInstancePredicate.getIsNegated() ? "!" : "") + "is " + ConeTypeUtilsKt.renderForDebugging(isInstancePredicate.getType()));
    }

    public void visitIsNullPredicate(KtIsNullPredicate<ConeKotlinType, ConeDiagnostic> isNullPredicate, Void data) {
        Intrinsics.checkNotNullParameter(isNullPredicate, "isNullPredicate");
        isNullPredicate.getArg().accept(this, data);
        getPrinter().print(" " + (isNullPredicate.getIsNegated() ? "!=" : "==") + " null");
    }

    public void visitConstantDescriptor(KtConstantReference<ConeKotlinType, ConeDiagnostic> constantReference, Void data) {
        Intrinsics.checkNotNullParameter(constantReference, "constantReference");
        getPrinter().print(constantReference.getName());
    }

    public void visitValueParameterReference(KtValueParameterReference<ConeKotlinType, ConeDiagnostic> valueParameterReference, Void data) {
        Intrinsics.checkNotNullParameter(valueParameterReference, "valueParameterReference");
        getPrinter().print(valueParameterReference.getName());
    }

    private final void inBracketsIfNecessary(KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> parent, KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> child, Function0<Unit> block) {
        if (needsBrackets(parent, child)) {
            getPrinter().print("(");
            block.invoke();
            getPrinter().print(Constant.AFTER_QUTO);
            return;
        }
        block.invoke();
    }

    private final boolean isAtom(KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> ktContractDescriptionElement) {
        return (ktContractDescriptionElement instanceof KtValueParameterReference) || (ktContractDescriptionElement instanceof KtConstantReference) || (ktContractDescriptionElement instanceof KtIsNullPredicate) || (ktContractDescriptionElement instanceof KtIsInstancePredicate);
    }

    private final boolean needsBrackets(KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> parent, KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> child) {
        if (isAtom(child)) {
            return false;
        }
        return (parent instanceof KtLogicalNot) || parent.getClass() != child.getClass();
    }
}
