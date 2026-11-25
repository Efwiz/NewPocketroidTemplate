package androidx.annotation.experimental.lint;

import com.android.tools.lint.detector.api.JavaContext;
import com.intellij.psi.PsiClassType;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.uast.UAnnotation;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UClassLiteralExpression;
import org.jetbrains.uast.UExpression;

/* JADX INFO: compiled from: ExperimentalDetector.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/annotation-experimental-1.1.0.aar:lint.jar:androidx/annotation/experimental/lint/ExperimentalDetectorKt.class */
@Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 2, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0004\u0018\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a$\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0001H\u0002¨\u0006\n"}, d2 = {"getFullyQualifiedName", "", "Lorg/jetbrains/uast/UExpression;", "context", "Lcom/android/tools/lint/detector/api/JavaContext;", "hasMatchingAttributeValueClass", "", "Lorg/jetbrains/uast/UAnnotation;", "attributeName", "className", "annotation-experimental-lint"})
public final class ExperimentalDetectorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean hasMatchingAttributeValueClass(UAnnotation $this$hasMatchingAttributeValueClass, JavaContext context, String attributeName, String className) {
        UCallExpression uCallExpressionFindAttributeValue = $this$hasMatchingAttributeValueClass.findAttributeValue(attributeName);
        if (Intrinsics.areEqual(getFullyQualifiedName(uCallExpressionFindAttributeValue, context), className)) {
            return true;
        }
        if (uCallExpressionFindAttributeValue instanceof UCallExpression) {
            Iterable $this$any$iv = uCallExpressionFindAttributeValue.getValueArguments();
            if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
                return false;
            }
            for (Object element$iv : $this$any$iv) {
                UExpression attrValue = (UExpression) element$iv;
                if (Intrinsics.areEqual(getFullyQualifiedName(attrValue, context), className)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private static final String getFullyQualifiedName(UExpression $this$getFullyQualifiedName, JavaContext context) {
        Object objEvaluate;
        if ($this$getFullyQualifiedName instanceof UClassLiteralExpression) {
            objEvaluate = ((UClassLiteralExpression) $this$getFullyQualifiedName).getType();
        } else {
            objEvaluate = $this$getFullyQualifiedName != null ? $this$getFullyQualifiedName.evaluate() : null;
        }
        Object type = objEvaluate;
        Object obj = type;
        if (!(obj instanceof PsiClassType)) {
            obj = null;
        }
        PsiClassType it = (PsiClassType) obj;
        if (it != null) {
            return context.getEvaluator().getQualifiedName(it);
        }
        return null;
    }
}
