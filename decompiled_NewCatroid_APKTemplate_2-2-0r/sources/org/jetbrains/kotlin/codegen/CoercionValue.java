package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: StackValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\fH\u0016J\"\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00042\b\u0010\u0014\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\"\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0014R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CoercionValue;", "Lorg/jetbrains/kotlin/codegen/StackValue;", "value", "castType", "Lorg/jetbrains/org/objectweb/asm/Type;", "castKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "underlyingKotlinType", "(Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getValue", "()Lorg/jetbrains/kotlin/codegen/StackValue;", "isNonStaticAccess", "", "isRead", "putReceiver", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "putSelector", ModuleXmlParser.TYPE, "kotlinType", "storeSelector", "topOfStackType", "topOfStackKotlinType", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoercionValue extends StackValue {
    private final KotlinType castKotlinType;
    private final Type castType;
    private final KotlinType underlyingKotlinType;
    private final StackValue value;

    public final StackValue getValue() {
        return this.value;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CoercionValue(StackValue value, Type castType, KotlinType kotlinType, KotlinType kotlinType2) {
        super(castType, kotlinType, value.canHaveSideEffects());
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(castType, "castType");
        this.value = value;
        this.castType = castType;
        this.castKotlinType = kotlinType;
        this.underlyingKotlinType = kotlinType2;
    }

    @Override // org.jetbrains.kotlin.codegen.StackValue
    public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(v, "v");
        StackValue stackValue = this.value;
        stackValue.putSelector(stackValue.type, this.value.kotlinType, v);
        Type type2 = this.value.type;
        KotlinType kotlinType2 = this.value.kotlinType;
        Type type3 = this.castType;
        KotlinType kotlinType3 = this.underlyingKotlinType;
        if (kotlinType3 == null) {
            kotlinType3 = this.castKotlinType;
        }
        StackValue.coerce(type2, kotlinType2, type3, kotlinType3, v);
        StackValue.coerce(this.castType, this.castKotlinType, type, kotlinType, v);
    }

    @Override // org.jetbrains.kotlin.codegen.StackValue
    protected void storeSelector(Type topOfStackType, KotlinType topOfStackKotlinType, InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(topOfStackType, "topOfStackType");
        Intrinsics.checkNotNullParameter(v, "v");
        this.value.storeSelector(topOfStackType, topOfStackKotlinType, v);
    }

    @Override // org.jetbrains.kotlin.codegen.StackValue
    public void putReceiver(InstructionAdapter v, boolean isRead) {
        Intrinsics.checkNotNullParameter(v, "v");
        this.value.putReceiver(v, isRead);
    }

    @Override // org.jetbrains.kotlin.codegen.StackValue
    public boolean isNonStaticAccess(boolean isRead) {
        return this.value.isNonStaticAccess(isRead);
    }
}
