package org.jetbrains.kotlin.codegen.inline;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: ObjectTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "it", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class WhenMappingTransformer$doTransform$2$1 extends Lambda implements Function1<MethodNode, CharSequence> {
    public static final WhenMappingTransformer$doTransform$2$1 INSTANCE = new WhenMappingTransformer$doTransform$2$1();

    WhenMappingTransformer$doTransform$2$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final CharSequence invoke(MethodNode it) {
        Intrinsics.checkNotNullParameter(it, "it");
        String name2 = it.f1016name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        return name2;
    }
}
