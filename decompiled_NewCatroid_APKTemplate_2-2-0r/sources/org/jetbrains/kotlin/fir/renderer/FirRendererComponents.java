package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.contracts.description.ConeContractRenderer;

/* JADX INFO: compiled from: FirRendererComponents.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b`\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001fX¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0012\u0010\"\u001a\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u0004\u0018\u00010'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u0004\u0018\u00010+X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0012\u0010.\u001a\u00020/X¦\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0014\u00102\u001a\u0004\u0018\u000103X¦\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0014\u0010:\u001a\u0004\u0018\u00010;X¦\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0012\u0010>\u001a\u00020?X¦\u0004¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0014\u0010B\u001a\u0004\u0018\u00010CX¦\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0016\u0010F\u001a\u00060GR\u00020HX¦\u0004¢\u0006\u0006\u001a\u0004\bI\u0010Jø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006KÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "", "annotationRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirAnnotationRenderer;", "getAnnotationRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirAnnotationRenderer;", "bodyRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirBodyRenderer;", "getBodyRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirBodyRenderer;", "callArgumentsRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirCallArgumentsRenderer;", "getCallArgumentsRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirCallArgumentsRenderer;", "classMemberRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirClassMemberRenderer;", "getClassMemberRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirClassMemberRenderer;", "contractRenderer", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractRenderer;", "getContractRenderer", "()Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractRenderer;", "declarationRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRenderer;", "getDeclarationRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRenderer;", "errorExpressionRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirErrorExpressionRenderer;", "getErrorExpressionRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirErrorExpressionRenderer;", "fileAnnotationsContainerRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirFileAnnotationsContainerRenderer;", "getFileAnnotationsContainerRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirFileAnnotationsContainerRenderer;", "idRenderer", "Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;", "getIdRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/ConeIdRenderer;", "modifierRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirModifierRenderer;", "getModifierRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirModifierRenderer;", "packageDirectiveRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirPackageDirectiveRenderer;", "getPackageDirectiveRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirPackageDirectiveRenderer;", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "propertyAccessorRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirPropertyAccessorRenderer;", "getPropertyAccessorRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirPropertyAccessorRenderer;", "referencedSymbolRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirSymbolRenderer;", "getReferencedSymbolRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirSymbolRenderer;", "resolvePhaseRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirResolvePhaseRenderer;", "getResolvePhaseRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirResolvePhaseRenderer;", "typeRenderer", "Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRenderer;", "getTypeRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRenderer;", "valueParameterRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirValueParameterRenderer;", "getValueParameterRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirValueParameterRenderer;", "visitor", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "getVisitor", "()Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirRendererComponents {
    FirAnnotationRenderer getAnnotationRenderer();

    FirBodyRenderer getBodyRenderer();

    FirCallArgumentsRenderer getCallArgumentsRenderer();

    FirClassMemberRenderer getClassMemberRenderer();

    ConeContractRenderer getContractRenderer();

    FirDeclarationRenderer getDeclarationRenderer();

    FirErrorExpressionRenderer getErrorExpressionRenderer();

    FirFileAnnotationsContainerRenderer getFileAnnotationsContainerRenderer();

    ConeIdRenderer getIdRenderer();

    FirModifierRenderer getModifierRenderer();

    FirPackageDirectiveRenderer getPackageDirectiveRenderer();

    FirPrinter getPrinter();

    FirPropertyAccessorRenderer getPropertyAccessorRenderer();

    FirSymbolRenderer getReferencedSymbolRenderer();

    FirResolvePhaseRenderer getResolvePhaseRenderer();

    ConeTypeRenderer getTypeRenderer();

    FirValueParameterRenderer getValueParameterRenderer();

    FirRenderer.Visitor getVisitor();
}
