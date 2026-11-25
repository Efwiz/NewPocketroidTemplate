package org.jetbrains.kotlin.idea.references;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: KtForLoopInReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\fB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtForLoopInReference;", "Lorg/jetbrains/kotlin/idea/references/KtMultiReference;", "Lorg/jetbrains/kotlin/psi/KtForExpression;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/psi/KtForExpression;)V", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "Companion", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtForLoopInReference extends KtMultiReference<KtForExpression> {
    private static final List<Name> NAMES = CollectionsKt.listOf((Object[]) new Name[]{OperatorNameConventions.ITERATOR, OperatorNameConventions.NEXT, OperatorNameConventions.HAS_NEXT});

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtForLoopInReference(KtForExpression element) {
        super(element);
        Intrinsics.checkNotNullParameter(element, "element");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        PsiElement inKeyword = ((KtForExpression) getExpression()).getInKeyword();
        if (inKeyword != null) {
            int startOffsetInParent = inKeyword.getStartOffsetInParent();
            return new TextRange(startOffsetInParent, inKeyword.getTextLength() + startOffsetInParent);
        }
        TextRange EMPTY_RANGE = TextRange.EMPTY_RANGE;
        Intrinsics.checkNotNullExpressionValue(EMPTY_RANGE, "EMPTY_RANGE");
        return EMPTY_RANGE;
    }

    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public Collection<Name> getResolvesByNames() {
        return NAMES;
    }
}
