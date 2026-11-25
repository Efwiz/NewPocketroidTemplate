package org.jetbrains.kotlin.backend.common.lower.loops;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b`\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\b\u0001\u0010\u00032\u00020\u0004J'\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00028\u00002\u0006\u0010\b\u001a\u00028\u00012\u0006\u0010\t\u001a\u00020\nH&¢\u0006\u0002\u0010\u000bJ1\u0010\f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00028\u00002\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\b\u001a\u00028\u00012\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u000fJ\u0015\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0012J\u0010\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u000eH\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "D", "", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "expression", "data", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "handle", "iteratorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrCall;Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "matchIterable", "", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Z", "matchIteratorCall", "call", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface HeaderInfoHandler<E extends IrExpression, D> {
    HeaderInfo build(E expression, D data, IrSymbol scopeOwner);

    boolean matchIterable(E expression);

    default boolean matchIteratorCall(IrCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        return true;
    }

    default HeaderInfo handle(E expression, IrCall iteratorCall, D data, IrSymbol scopeOwner) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        if ((iteratorCall == null || matchIteratorCall(iteratorCall)) && matchIterable(expression)) {
            return build(expression, data, scopeOwner);
        }
        return null;
    }
}
