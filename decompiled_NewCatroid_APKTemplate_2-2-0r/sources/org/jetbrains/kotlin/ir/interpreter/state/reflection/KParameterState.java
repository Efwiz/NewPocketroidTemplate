package org.jetbrains.kotlin.ir.interpreter.state.reflection;

import io.netty.util.internal.StringUtil;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KTypeProxy;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KParameterState.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\u000e\u0010\u0019\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u0007H\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KParameterState;", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/ReflectionState;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "index", "", Namer.METADATA_CLASS_KIND, "Lkotlin/reflect/KParameter$Kind;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;ILkotlin/reflect/KParameter$Kind;)V", "_type", "Lkotlin/reflect/KType;", "getIndex", "()I", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getKind", "()Lkotlin/reflect/KParameter$Kind;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getType", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "hashCode", "toString", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KParameterState extends ReflectionState {
    private KType _type;
    private final int index;
    private final IrClass irClass;
    private final IrValueParameter irParameter;
    private final KParameter.Kind kind;

    /* JADX INFO: compiled from: KParameterState.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KParameter.Kind.values().length];
            try {
                iArr[KParameter.Kind.EXTENSION_RECEIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KParameter.Kind.INSTANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KParameter.Kind.VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ KParameterState(IrClass irClass, IrValueParameter irValueParameter, int i, KParameter.Kind kind, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(irClass, irValueParameter, i, (i2 & 8) != 0 ? KParameter.Kind.VALUE : kind);
    }

    public final int getIndex() {
        return this.index;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrClass getIrClass() {
        return this.irClass;
    }

    public final IrValueParameter getIrParameter() {
        return this.irParameter;
    }

    public final KParameter.Kind getKind() {
        return this.kind;
    }

    public KParameterState(IrClass irClass, IrValueParameter irParameter, int i, KParameter.Kind kind) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(irParameter, "irParameter");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.irClass = irClass;
        this.irParameter = irParameter;
        this.index = i;
        this.kind = kind;
    }

    public final KType getType(CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        KType kType = this._type;
        if (kType != null) {
            Intrinsics.checkNotNull(kType);
            return kType;
        }
        KTypeProxy kTypeProxy = new KTypeProxy(new KTypeState(this.irParameter.getType(), callInterceptor.getEnvironment().getKTypeClass$ir_interpreter().getOwner()), callInterceptor);
        this._type = kTypeProxy;
        Intrinsics.checkNotNull(kTypeProxy);
        return kTypeProxy;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KParameterState");
        return Intrinsics.areEqual(this.irParameter, ((KParameterState) other).irParameter);
    }

    public int hashCode() {
        return this.irParameter.hashCode();
    }

    public String toString() {
        IrProperty owner;
        StringBuilder sb = new StringBuilder();
        int i = WhenMappings.$EnumSwitchMapping$0[this.kind.ordinal()];
        if (i == 1) {
            sb.append("extension receiver parameter");
        } else if (i == 2) {
            sb.append("instance parameter");
        } else if (i == 3) {
            sb.append("parameter #" + this.index + StringUtil.SPACE + this.irParameter.getName());
        }
        sb.append(" of ");
        IrElement parent = this.irParameter.getParent();
        if (parent instanceof IrSimpleFunction) {
            IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) parent).getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || sb.append(renderProperty(owner)) == null) {
                sb.append(renderFunction((IrFunction) parent));
            }
        } else if (parent instanceof IrFunction) {
            sb.append(renderFunction((IrFunction) parent));
        } else {
            if (!(parent instanceof IrProperty)) {
                throw new NotImplementedError(null, 1, null);
            }
            sb.append(renderProperty((IrProperty) parent));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
