package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;

/* JADX INFO: compiled from: Origins.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0005À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LoweredStatementOrigins;", "", "DEFAULT_DISPATCH_CALL", "STATEMENT_ORIGIN_INITIALIZER_OF_FIELD_FOR_CAPTURED_VALUE", "SYNTHESIZED_INIT_BLOCK", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LoweredStatementOrigins {

    /* JADX INFO: compiled from: Origins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LoweredStatementOrigins$STATEMENT_ORIGIN_INITIALIZER_OF_FIELD_FOR_CAPTURED_VALUE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class STATEMENT_ORIGIN_INITIALIZER_OF_FIELD_FOR_CAPTURED_VALUE extends IrStatementOriginImpl {
        public static final STATEMENT_ORIGIN_INITIALIZER_OF_FIELD_FOR_CAPTURED_VALUE INSTANCE = new STATEMENT_ORIGIN_INITIALIZER_OF_FIELD_FOR_CAPTURED_VALUE();

        private STATEMENT_ORIGIN_INITIALIZER_OF_FIELD_FOR_CAPTURED_VALUE() {
            super("INITIALIZER_OF_FIELD_FOR_CAPTURED_VALUE");
        }
    }

    /* JADX INFO: compiled from: Origins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LoweredStatementOrigins$SYNTHESIZED_INIT_BLOCK;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHESIZED_INIT_BLOCK extends IrStatementOriginImpl {
        public static final SYNTHESIZED_INIT_BLOCK INSTANCE = new SYNTHESIZED_INIT_BLOCK();

        private SYNTHESIZED_INIT_BLOCK() {
            super("SYNTHESIZED_INIT_BLOCK");
        }
    }

    /* JADX INFO: compiled from: Origins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/LoweredStatementOrigins$DEFAULT_DISPATCH_CALL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DEFAULT_DISPATCH_CALL extends IrStatementOriginImpl {
        public static final DEFAULT_DISPATCH_CALL INSTANCE = new DEFAULT_DISPATCH_CALL();

        private DEFAULT_DISPATCH_CALL() {
            super("DEFAULT_DISPATCH_CALL");
        }
    }
}
