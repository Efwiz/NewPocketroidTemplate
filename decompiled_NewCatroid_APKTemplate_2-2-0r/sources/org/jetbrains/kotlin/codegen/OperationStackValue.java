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
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B:\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012!\u0010\u0006\u001a\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0\u0007¢\u0006\u0002\u0010\rJ\"\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00032\b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\bH\u0016R,\u0010\u0006\u001a\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/OperationStackValue;", "Lorg/jetbrains/kotlin/codegen/StackValue;", "resultType", "Lorg/jetbrains/org/objectweb/asm/Type;", "resultKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "lambda", "Lkotlin/Function1;", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "Lkotlin/ParameterName;", "name", RegisterSpec.PREFIX, "", "(Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/types/KotlinType;Lkotlin/jvm/functions/Function1;)V", "getLambda", "()Lkotlin/jvm/functions/Function1;", "putSelector", ModuleXmlParser.TYPE, "kotlinType", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class OperationStackValue extends StackValue {
    private final Function1<InstructionAdapter, Unit> lambda;

    public final Function1<InstructionAdapter, Unit> getLambda() {
        return this.lambda;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public OperationStackValue(Type resultType, KotlinType kotlinType, Function1<? super InstructionAdapter, Unit> lambda) {
        super(resultType, kotlinType);
        Intrinsics.checkNotNullParameter(resultType, "resultType");
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        this.lambda = lambda;
    }

    @Override // org.jetbrains.kotlin.codegen.StackValue
    public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(v, "v");
        this.lambda.invoke(v);
        coerceTo(type, kotlinType, v);
    }
}
