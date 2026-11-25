package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: StackValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\"\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\f\u001a\u00020\rH\u0016R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/StackValueWithLeaveTask;", "Lorg/jetbrains/kotlin/codegen/StackValue;", "stackValue", "leaveTasks", "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/codegen/StackValue;Lkotlin/jvm/functions/Function1;)V", "getLeaveTasks", "()Lkotlin/jvm/functions/Function1;", "getStackValue", "()Lorg/jetbrains/kotlin/codegen/StackValue;", "putReceiver", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "isRead", "", "putSelector", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StackValueWithLeaveTask extends StackValue {
    private final Function1<StackValue, Unit> leaveTasks;
    private final StackValue stackValue;

    public final StackValue getStackValue() {
        return this.stackValue;
    }

    public final Function1<StackValue, Unit> getLeaveTasks() {
        return this.leaveTasks;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public StackValueWithLeaveTask(StackValue stackValue, Function1<? super StackValue, Unit> leaveTasks) {
        super(stackValue.type, stackValue.kotlinType);
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(leaveTasks, "leaveTasks");
        this.stackValue = stackValue;
        this.leaveTasks = leaveTasks;
    }

    @Override // org.jetbrains.kotlin.codegen.StackValue
    public void putReceiver(InstructionAdapter v, boolean isRead) {
        Intrinsics.checkNotNullParameter(v, "v");
        this.stackValue.putReceiver(v, isRead);
    }

    @Override // org.jetbrains.kotlin.codegen.StackValue
    public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(v, "v");
        this.stackValue.putSelector(type, kotlinType, v);
        this.leaveTasks.invoke(this.stackValue);
    }
}
