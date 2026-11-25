package org.jetbrains.kotlin.idea.references;

import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.references.ReferenceAccess;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: ReadWriteAccessChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \f2\u00020\u0001:\u0001\fJ$\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001e\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00032\u0006\u0010\n\u001a\u00020\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/ReadWriteAccessChecker;", "", "readWriteAccessWithFullExpression", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/references/ReferenceAccess;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "targetExpression", "useResolveForReadWrite", "", "readWriteAccessWithFullExpressionByResolve", "assignment", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "Companion", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReadWriteAccessChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    Pair<ReferenceAccess, KtExpression> readWriteAccessWithFullExpressionByResolve(KtBinaryExpression assignment);

    default Pair<ReferenceAccess, KtExpression> readWriteAccessWithFullExpression(KtExpression targetExpression, boolean useResolveForReadWrite) {
        Intrinsics.checkNotNullParameter(targetExpression, "targetExpression");
        KtExpression qualifiedExpressionForSelectorOrThis = KtPsiUtilKt.getQualifiedExpressionForSelectorOrThis(targetExpression);
        while (true) {
            PsiElement parent = qualifiedExpressionForSelectorOrThis.getLightParent();
            if (!(parent instanceof KtParenthesizedExpression ? true : parent instanceof KtAnnotatedExpression ? true : parent instanceof KtLabeledExpression)) {
                break;
            }
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtExpression");
            qualifiedExpressionForSelectorOrThis = (KtExpression) parent;
        }
        KtBinaryExpression assignmentByLHS = KtPsiUtilKt.getAssignmentByLHS(qualifiedExpressionForSelectorOrThis);
        if (assignmentByLHS != null) {
            if (Intrinsics.areEqual(assignmentByLHS.getOperationToken(), KtTokens.EQ)) {
                return TuplesKt.to(ReferenceAccess.WRITE, assignmentByLHS);
            }
            Pair<ReferenceAccess, KtExpression> writeAccessWithFullExpressionByResolve = useResolveForReadWrite ? readWriteAccessWithFullExpressionByResolve(assignmentByLHS) : null;
            return writeAccessWithFullExpressionByResolve == null ? TuplesKt.to(ReferenceAccess.READ_WRITE, assignmentByLHS) : writeAccessWithFullExpressionByResolve;
        }
        PsiElement parent2 = qualifiedExpressionForSelectorOrThis.getLightParent();
        KtUnaryExpression ktUnaryExpression = parent2 instanceof KtUnaryExpression ? (KtUnaryExpression) parent2 : null;
        if (ktUnaryExpression != null && CollectionsKt.contains((Iterable) AddToStdlibKt.constant(new Function0<Set<? extends KtSingleValueToken>>() { // from class: org.jetbrains.kotlin.idea.references.ReadWriteAccessChecker.readWriteAccessWithFullExpression.1
            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends KtSingleValueToken> invoke() {
                return SetsKt.setOf((Object[]) new KtSingleValueToken[]{KtTokens.PLUSPLUS, KtTokens.MINUSMINUS});
            }
        }), ktUnaryExpression.getOperationToken())) {
            return TuplesKt.to(ReferenceAccess.READ_WRITE, ktUnaryExpression);
        }
        return TuplesKt.to(ReferenceAccess.READ, qualifiedExpressionForSelectorOrThis);
    }

    /* JADX INFO: compiled from: ReadWriteAccessChecker.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/ReadWriteAccessChecker$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/idea/references/ReadWriteAccessChecker;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final ReadWriteAccessChecker getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(ReadWriteAccessChecker.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (ReadWriteAccessChecker) service;
        }
    }
}
