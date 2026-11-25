package org.jetbrains.kotlin.codegen.coroutines;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.common.VariableLivenessFrame;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;

/* JADX INFO: compiled from: CoroutineTransformerMethodVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "<name for destructuring parameter 0>", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/codegen/optimization/common/VariableLivenessFrame;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class CoroutineTransformerMethodVisitorKt$nodeTextWithLiveness$1 extends Lambda implements Function1<Pair<? extends VariableLivenessFrame, ? extends AbstractInsnNode>, CharSequence> {
    public static final CoroutineTransformerMethodVisitorKt$nodeTextWithLiveness$1 INSTANCE = new CoroutineTransformerMethodVisitorKt$nodeTextWithLiveness$1();

    CoroutineTransformerMethodVisitorKt$nodeTextWithLiveness$1() {
        super(1);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final CharSequence invoke2(Pair<VariableLivenessFrame, ? extends AbstractInsnNode> pair) {
        Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
        return pair.component1() + '|' + InlineCodegenUtilsKt.getInsnText(pair.component2());
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ CharSequence invoke(Pair<? extends VariableLivenessFrame, ? extends AbstractInsnNode> pair) {
        return invoke2((Pair<VariableLivenessFrame, ? extends AbstractInsnNode>) pair);
    }
}
