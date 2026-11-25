package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: BranchedValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \n2\u00020\u0001:\u0001\nB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ObjectCompare;", "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "operandType", "Lorg/jetbrains/org/objectweb/asm/Type;", "left", "Lorg/jetbrains/kotlin/codegen/StackValue;", "right", "(Lcom/intellij/psi/tree/IElementType;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;)V", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ObjectCompare extends BranchedValue {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ObjectCompare(IElementType opToken, Type operandType, StackValue left, StackValue right) {
        super(left, right, operandType, INSTANCE.getObjectCompareOpcode(opToken));
        Intrinsics.checkNotNullParameter(opToken, "opToken");
        Intrinsics.checkNotNullParameter(operandType, "operandType");
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
    }

    /* JADX INFO: compiled from: BranchedValue.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ObjectCompare$Companion;", "", "()V", "getObjectCompareOpcode", "", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getObjectCompareOpcode(IElementType opToken) {
            Intrinsics.checkNotNullParameter(opToken, "opToken");
            if (Intrinsics.areEqual(opToken, KtTokens.EQEQEQ) ? true : Intrinsics.areEqual(opToken, KtTokens.EQEQ)) {
                return 166;
            }
            if (Intrinsics.areEqual(opToken, KtTokens.EXCLEQEQEQ) ? true : Intrinsics.areEqual(opToken, KtTokens.EXCLEQ)) {
                return 165;
            }
            throw new UnsupportedOperationException("don't know how to generate this condjump");
        }
    }
}
