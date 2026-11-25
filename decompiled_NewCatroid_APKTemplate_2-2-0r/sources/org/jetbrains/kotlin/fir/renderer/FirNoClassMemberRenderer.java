package org.jetbrains.kotlin.fir.renderer;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;

/* JADX INFO: compiled from: FirNoClassMemberRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirNoClassMemberRenderer;", "Lorg/jetbrains/kotlin/fir/renderer/FirClassMemberRenderer;", "()V", "render", "", "declarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNoClassMemberRenderer extends FirClassMemberRenderer {
    @Override // org.jetbrains.kotlin.fir.renderer.FirClassMemberRenderer
    public void render(List<? extends FirDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(declarations, "declarations");
    }

    @Override // org.jetbrains.kotlin.fir.renderer.FirClassMemberRenderer
    public void render(FirRegularClass regularClass) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
    }
}
