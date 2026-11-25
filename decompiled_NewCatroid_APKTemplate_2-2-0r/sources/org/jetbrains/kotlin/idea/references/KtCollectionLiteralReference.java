package org.jetbrains.kotlin.idea.references;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.MultiRangeReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.resolve.ArrayFqNames;

/* JADX INFO: compiled from: KtCollectionLiteralReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \u00112\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001\u0011B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\b\u0010\u000b\u001a\u00020\fH\u0016J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000eH\u0016J\f\u0010\u000f\u001a\u00020\f*\u00020\u0010H\u0002R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtCollectionLiteralReference;", "Lorg/jetbrains/kotlin/idea/references/KtSimpleReference;", "Lorg/jetbrains/kotlin/psi/KtCollectionLiteralExpression;", "Lorg/jetbrains/kotlin/com/intellij/psi/MultiRangeReference;", "expression", "(Lorg/jetbrains/kotlin/psi/KtCollectionLiteralExpression;)V", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getRanges", "", "normalizeRange", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Companion", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtCollectionLiteralReference extends KtSimpleReference<KtCollectionLiteralExpression> implements MultiRangeReference {
    private static final List<Name> COLLECTION_LITERAL_CALL_NAMES = CollectionsKt.plus((Collection<? extends Name>) ArrayFqNames.INSTANCE.getPRIMITIVE_TYPE_TO_ARRAY().values(), ArrayFqNames.INSTANCE.getARRAY_OF_FUNCTION());

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtCollectionLiteralReference(KtCollectionLiteralExpression expression) {
        super(expression);
        Intrinsics.checkNotNullParameter(expression, "expression");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        PsiElement element = getPsiElement();
        Intrinsics.checkNotNullExpressionValue(element, "getElement(...)");
        return normalizeRange(element);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.MultiRangeReference
    public List<TextRange> getRanges() {
        TextRange[] textRangeArr = new TextRange[2];
        PsiElement leftBracket = ((KtCollectionLiteralExpression) getPsiElement()).getLeftBracket();
        textRangeArr[0] = leftBracket != null ? normalizeRange(leftBracket) : null;
        PsiElement rightBracket = ((KtCollectionLiteralExpression) getPsiElement()).getRightBracket();
        textRangeArr[1] = rightBracket != null ? normalizeRange(rightBracket) : null;
        return CollectionsKt.listOfNotNull((Object[]) textRangeArr);
    }

    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public Collection<Name> getResolvesByNames() {
        return COLLECTION_LITERAL_CALL_NAMES;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final TextRange normalizeRange(PsiElement psiElement) {
        TextRange textRangeShiftRight = psiElement.getTextRange().shiftRight(-((KtCollectionLiteralExpression) getExpression()).getTextOffset());
        Intrinsics.checkNotNullExpressionValue(textRangeShiftRight, "shiftRight(...)");
        return textRangeShiftRight;
    }
}
