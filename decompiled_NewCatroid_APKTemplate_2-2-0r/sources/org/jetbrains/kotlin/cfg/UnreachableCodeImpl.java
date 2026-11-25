package org.jetbrains.kotlin.cfg;

import com.reandroid.archive.PathTree;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtPsiUtil;

/* JADX INFO: compiled from: UnreachableCode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/cfg/UnreachableCodeImpl;", "Lorg/jetbrains/kotlin/cfg/UnreachableCode;", "reachableElements", "", "Lorg/jetbrains/kotlin/psi/KtElement;", "unreachableElements", "(Ljava/util/Set;Ljava/util/Set;)V", PathTree.NAME_elements, "getElements", "()Ljava/util/Set;", "getReachableElements", "getUnreachableElements", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnreachableCodeImpl implements UnreachableCode {
    private final Set<KtElement> elements;
    private final Set<KtElement> reachableElements;
    private final Set<KtElement> unreachableElements;

    /* JADX WARN: Multi-variable type inference failed */
    public UnreachableCodeImpl(Set<? extends KtElement> reachableElements, Set<? extends KtElement> unreachableElements) {
        Intrinsics.checkNotNullParameter(reachableElements, "reachableElements");
        Intrinsics.checkNotNullParameter(unreachableElements, "unreachableElements");
        this.reachableElements = reachableElements;
        this.unreachableElements = unreachableElements;
        Set<KtElement> setFindRootExpressions = KtPsiUtil.findRootExpressions(getUnreachableElements());
        Intrinsics.checkNotNullExpressionValue(setFindRootExpressions, "findRootExpressions(...)");
        this.elements = setFindRootExpressions;
    }

    @Override // org.jetbrains.kotlin.cfg.UnreachableCode
    public Set<KtElement> getReachableElements() {
        return this.reachableElements;
    }

    @Override // org.jetbrains.kotlin.cfg.UnreachableCode
    public Set<KtElement> getUnreachableElements() {
        return this.unreachableElements;
    }

    @Override // org.jetbrains.kotlin.cfg.UnreachableCode
    public Set<KtElement> getElements() {
        return this.elements;
    }
}
