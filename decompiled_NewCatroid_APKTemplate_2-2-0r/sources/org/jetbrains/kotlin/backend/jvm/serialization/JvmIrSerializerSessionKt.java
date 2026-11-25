package org.jetbrains.kotlin.backend.jvm.serialization;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.config.JvmSerializeIrMode;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: JvmIrSerializerSession.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0007H\u0002¨\u0006\b"}, d2 = {"forEveryDeclarationToSerialize", "", "topDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "mode", "Lorg/jetbrains/kotlin/config/JvmSerializeIrMode;", "action", "Lkotlin/Function1;", "ir.serialization.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmIrSerializerSessionKt {

    /* JADX INFO: compiled from: JvmIrSerializerSession.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JvmSerializeIrMode.values().length];
            try {
                iArr[JvmSerializeIrMode.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JvmSerializeIrMode.ALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JvmSerializeIrMode.INLINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void forEveryDeclarationToSerialize(IrDeclaration irDeclaration, JvmSerializeIrMode jvmSerializeIrMode, Function1<? super IrDeclaration, Unit> function1) {
        int i = WhenMappings.$EnumSwitchMapping$0[jvmSerializeIrMode.ordinal()];
        if (i == 1) {
            throw new IllegalStateException("should not even be called with serialization mode NONE".toString());
        }
        if (i == 2) {
            function1.invoke(irDeclaration);
        } else {
            if (i != 3) {
                return;
            }
            irDeclaration.accept(ForVisibleInlineFunctionsVisitor.INSTANCE, function1);
        }
    }
}
