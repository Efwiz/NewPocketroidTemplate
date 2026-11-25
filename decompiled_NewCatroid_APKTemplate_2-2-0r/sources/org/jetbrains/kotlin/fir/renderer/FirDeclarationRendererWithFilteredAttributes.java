package org.jetbrains.kotlin.fir.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirDeclarationRendererWithFilteredAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004H\u0014¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRendererWithFilteredAttributes;", "Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRendererWithAttributes;", "()V", "attributeTypesToIds", "", "Lkotlin/Pair;", "", "", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDeclarationRendererWithFilteredAttributes extends FirDeclarationRendererWithAttributes {
    private static final Companion Companion = new Companion(null);
    private static final Set<String> IGNORED_ATTRIBUTES = SetsKt.setOf((Object[]) new String[]{"FirVersionRequirementsTableKey", "SourceElementKey"});

    @Override // org.jetbrains.kotlin.fir.renderer.FirDeclarationRendererWithAttributes
    protected List<Pair<String, Integer>> attributeTypesToIds() {
        List<Pair<String, Integer>> listAttributeTypesToIds = super.attributeTypesToIds();
        ArrayList arrayList = new ArrayList();
        for (Object obj : listAttributeTypesToIds) {
            if (!IGNORED_ATTRIBUTES.contains(((Pair) obj).getFirst())) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: FirDeclarationRendererWithFilteredAttributes.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRendererWithFilteredAttributes$Companion;", "", "()V", "IGNORED_ATTRIBUTES", "", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
