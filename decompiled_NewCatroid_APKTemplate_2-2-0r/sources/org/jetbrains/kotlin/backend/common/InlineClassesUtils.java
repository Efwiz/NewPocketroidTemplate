package org.jetbrains.kotlin.backend.common;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: CommonBackendContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/InlineClassesUtils;", "", "getInlineClassUnderlyingType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "isClassInlineLike", "", "klass", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface InlineClassesUtils {
    default boolean isClassInlineLike(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return IrDeclarationsKt.isSingleFieldValueClass(klass);
    }

    default IrType getInlineClassUnderlyingType(IrClass irClass) {
        Object next;
        List<IrValueParameter> valueParameters;
        IrValueParameter irValueParameter;
        IrType type;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Iterator<T> it = irClass.getDeclarations().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(next instanceof IrConstructor));
        IrConstructor irConstructor = (IrConstructor) next;
        if (irConstructor != null) {
            IrConstructor irConstructor2 = irConstructor.isPrimary() ? irConstructor : null;
            if (irConstructor2 != null && (valueParameters = irConstructor2.getValueParameters()) != null && (irValueParameter = valueParameters.get(0)) != null && (type = irValueParameter.getType()) != null) {
                return type;
            }
        }
        throw new IllegalStateException(("Class has no primary constructor: " + IrUtilsKt.getFqNameWhenAvailable(irClass)).toString());
    }
}
