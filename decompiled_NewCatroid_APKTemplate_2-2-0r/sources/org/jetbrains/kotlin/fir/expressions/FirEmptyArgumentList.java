package org.jetbrains.kotlin.fir.expressions;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;

/* JADX INFO: compiled from: FirArgumentUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/expressions/FirEmptyArgumentList;", "Lorg/jetbrains/kotlin/fir/expressions/FirAbstractArgumentList;", "()V", "arguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getArguments", "()Ljava/util/List;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEmptyArgumentList extends FirAbstractArgumentList {
    public static final FirEmptyArgumentList INSTANCE = new FirEmptyArgumentList();

    @Override // org.jetbrains.kotlin.fir.expressions.FirArgumentList, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return null;
    }

    private FirEmptyArgumentList() {
    }

    @Override // org.jetbrains.kotlin.fir.expressions.FirArgumentList
    public List<FirExpression> getArguments() {
        return CollectionsKt.emptyList();
    }
}
