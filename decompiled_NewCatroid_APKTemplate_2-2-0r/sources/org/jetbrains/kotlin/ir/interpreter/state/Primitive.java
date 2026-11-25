package org.jetbrains.kotlin.ir.interpreter.state;

import androidx.exifinterface.media.ExifInterface;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Primitive.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0000\u0018\u0000 \u001e*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001\u001eB\u0015\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0017\u001a\u00020\tH\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016R$\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00020\bj\u0002`\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/Primitive;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "value", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "fields", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Fields;", "getFields", "()Ljava/util/Map;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "getValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getField", NativeSymbol.TYPE_NAME, "getIrFunctionByIrCall", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "toString", "", "Companion", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Primitive<T> implements State {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Map<IrSymbol, State> fields;
    private final IrClass irClass;
    private final IrType type;
    private final T value;

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public State getField(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return null;
    }

    public Primitive(T t, IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.value = t;
        this.type = type;
        this.fields = new LinkedHashMap();
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(type);
        Intrinsics.checkNotNull(classOrNull);
        this.irClass = classOrNull.getOwner();
    }

    public final IrType getType() {
        return this.type;
    }

    public final T getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public Map<IrSymbol, State> getFields() {
        return this.fields;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrClass getIrClass() {
        return this.irClass;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrFunction getIrFunctionByIrCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        boolean isFakeOverride = owner.getIsFakeOverride();
        IrSimpleFunction irSimpleFunction = owner;
        return isFakeOverride ? UtilsKt.getFirstNonInterfaceOverridden(irSimpleFunction) : irSimpleFunction;
    }

    public String toString() {
        return "Primitive(value=" + this.value + ", type=" + IrUtilsKt.getDefaultType(getIrClass()) + ')';
    }

    /* JADX INFO: compiled from: Primitive.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/Primitive$Companion;", "", "()V", "nullStateOfType", "Lorg/jetbrains/kotlin/ir/interpreter/state/Primitive;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Primitive<?> nullStateOfType(IrType irType) {
            Intrinsics.checkNotNullParameter(irType, "irType");
            return new Primitive<>(null, irType);
        }
    }
}
