package org.jetbrains.kotlin.fir.analysis.checkers;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.elements.KtNameReferenceExpressionElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtTypeProjectionElementType;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;

/* JADX INFO: compiled from: SourceNavigator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0014\u0010\b\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\n0\tH\u0002J\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0004*\u0004\u0018\u00010\u0006H\u0002J\u000e\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u0006H\u0016J\u000e\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u0010H\u0016J\f\u0010\u0011\u001a\u00020\u0012*\u00020\u0013H\u0016J\f\u0010\u0014\u001a\u00020\u0012*\u00020\u0015H\u0016J\f\u0010\u0016\u001a\u00020\u0012*\u00020\u0015H\u0016J\f\u0010\u0017\u001a\u00020\u0012*\u00020\u0015H\u0016J-\u0010\u0018\u001a\u0004\u0018\u0001H\u0019\"\u0004\b\u0000\u0010\u0019*\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\u00190\u001cH\u0002¢\u0006\u0002\u0010\u001d¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/LightTreeSourceNavigator;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/SourceNavigator;", "()V", "getNullableChild", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "node", "ref", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Ref;", "", "getParentOfParent", "getRawIdentifier", "", "getRawName", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isCatchElementParameter", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "isInConstructorCallee", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "isInTypeConstraint", "isRedundantNullable", "withSource", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/FirElement;", "f", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LightTreeSourceNavigator implements SourceNavigator {
    private final <T> T withSource(FirElement firElement, Function1<? super KtSourceElement, ? extends T> function1) {
        KtSourceElement source = firElement.getSource();
        if (source != null) {
            return function1.invoke(source);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public boolean isInConstructorCallee(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Boolean bool = (Boolean) withSource(firTypeRef, new Function1<KtSourceElement, Boolean>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.LightTreeSourceNavigator.isInConstructorCallee.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtSourceElement source) {
                Intrinsics.checkNotNullParameter(source, "source");
                LighterASTNode parent = source.getTreeStructure().getParent(source.getLighterASTNode());
                return Boolean.valueOf(Intrinsics.areEqual(parent != null ? parent.getTokenType() : null, KtNodeTypes.CONSTRUCTOR_CALLEE));
            }
        });
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public boolean isInTypeConstraint(FirTypeRef firTypeRef) {
        LighterASTNode next;
        LighterASTNode lighterASTNode;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        KtSourceElement source = firTypeRef.getSource();
        if (source == null) {
            return false;
        }
        Iterator<LighterASTNode> it = LightTreePositioningStrategiesKt.getAncestors(source.getTreeStructure(), source.getLighterASTNode()).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            lighterASTNode = next;
            if (Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.TYPE_CONSTRAINT)) {
                break;
            }
        } while (!Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.TYPE_PARAMETER));
        LighterASTNode lighterASTNode2 = next;
        return Intrinsics.areEqual(lighterASTNode2 != null ? lighterASTNode2.getTokenType() : null, KtNodeTypes.TYPE_CONSTRAINT);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public CharSequence getRawIdentifier(KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        IElementType elementType = ktSourceElement.getElementType();
        if (elementType instanceof KtNameReferenceExpressionElementType ? true : Intrinsics.areEqual(elementType, KtTokens.IDENTIFIER)) {
            return ktSourceElement.getLighterASTNode().toString();
        }
        if (elementType instanceof KtTypeProjectionElementType) {
            return CollectionsKt.last((List) LightTreeUtilsKt.getChildren(ktSourceElement.getLighterASTNode(), ktSourceElement.getTreeStructure())).toString();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public String getRawName(FirDeclaration firDeclaration) {
        LighterASTNode lighterASTNodeNameIdentifier;
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        KtSourceElement source = firDeclaration.getSource();
        if (source == null || (lighterASTNodeNameIdentifier = LightTreePositioningStrategiesKt.nameIdentifier(source.getTreeStructure(), source.getLighterASTNode())) == null) {
            return null;
        }
        return lighterASTNodeNameIdentifier.toString();
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public boolean isCatchElementParameter(FirValueParameterSymbol firValueParameterSymbol) {
        LighterASTNode parentOfParent;
        Intrinsics.checkNotNullParameter(firValueParameterSymbol, "<this>");
        KtSourceElement source = firValueParameterSymbol.getSource();
        return Intrinsics.areEqual((source == null || (parentOfParent = getParentOfParent(source)) == null) ? null : parentOfParent.getTokenType(), KtNodeTypes.CATCH);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public boolean isRedundantNullable(FirTypeRef firTypeRef) {
        Ref<LighterASTNode[]> ref;
        LighterASTNode nullableChild;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        KtSourceElement source = firTypeRef.getSource();
        return (source == null || (nullableChild = getNullableChild(source, source.getLighterASTNode(), (ref = new Ref<>()))) == null || getNullableChild(source, nullableChild, ref) == null) ? false : true;
    }

    private final LighterASTNode getNullableChild(KtSourceElement source, LighterASTNode node, Ref<LighterASTNode[]> ref) {
        source.getTreeStructure().getChildren(node, ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        Intrinsics.checkNotNullExpressionValue(lighterASTNodeArr, "get(...)");
        LighterASTNode lighterASTNode = (LighterASTNode) ArraysKt.firstOrNull(lighterASTNodeArr);
        if (lighterASTNode != null && Intrinsics.areEqual(lighterASTNode.getTokenType(), KtNodeTypes.NULLABLE_TYPE)) {
            return lighterASTNode;
        }
        return null;
    }

    private final LighterASTNode getParentOfParent(KtSourceElement ktSourceElement) {
        if (ktSourceElement == null) {
            return null;
        }
        LighterASTNode parent = ktSourceElement.getTreeStructure().getParent(ktSourceElement.getLighterASTNode());
        LighterASTNode lighterASTNode = parent;
        if (lighterASTNode != null) {
            parent = ktSourceElement.getTreeStructure().getParent(lighterASTNode);
        }
        return parent;
    }
}
