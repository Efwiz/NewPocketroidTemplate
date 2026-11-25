package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: MonitorInstruction.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/MonitorInstruction;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "opcode", "", "(I)V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MonitorInstruction extends IntrinsicMethod {
    public static final MonitorInstruction MONITOR_ENTER = new MonitorInstruction(194);
    public static final MonitorInstruction MONITOR_EXIT = new MonitorInstruction(195);
    private final int opcode;

    private MonitorInstruction(int i) {
        this.opcode = i;
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return new IntrinsicCallable(Type.VOID_TYPE, CollectionsKt.listOf(AsmTypes.OBJECT_TYPE)) { // from class: org.jetbrains.kotlin.codegen.intrinsics.MonitorInstruction.toCallable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(type, list, null, null, null, 16, null);
                Intrinsics.checkNotNull(type);
            }

            @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable
            public void invokeIntrinsic(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                v.visitInsn(MonitorInstruction.this.opcode);
            }
        };
    }
}
