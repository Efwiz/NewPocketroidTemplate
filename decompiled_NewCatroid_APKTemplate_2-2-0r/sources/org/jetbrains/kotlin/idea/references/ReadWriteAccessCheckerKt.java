package org.jetbrains.kotlin.idea.references;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.references.ReferenceAccess;

/* JADX INFO: compiled from: ReadWriteAccessChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00020\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0007"}, d2 = {"readWriteAccess", "Lorg/jetbrains/kotlin/resolve/references/ReferenceAccess;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "useResolveForReadWrite", "", "readWriteAccessWithFullExpression", "Lkotlin/Pair;", "kt-references"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReadWriteAccessCheckerKt {
    public static final Pair<ReferenceAccess, KtExpression> readWriteAccessWithFullExpression(KtExpression ktExpression, boolean z) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        ReadWriteAccessChecker.Companion companion = ReadWriteAccessChecker.INSTANCE;
        Project project = ktExpression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).readWriteAccessWithFullExpression(ktExpression, z);
    }

    public static final ReferenceAccess readWriteAccess(KtExpression ktExpression, boolean z) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        ReadWriteAccessChecker.Companion companion = ReadWriteAccessChecker.INSTANCE;
        Project project = ktExpression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return companion.getInstance(project).readWriteAccessWithFullExpression(ktExpression, z).getFirst();
    }
}
