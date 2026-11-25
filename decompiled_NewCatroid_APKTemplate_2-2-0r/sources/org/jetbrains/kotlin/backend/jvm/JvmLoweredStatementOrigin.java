package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;

/* JADX INFO: compiled from: StatementOrigins.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001:\u0006\u0002\u0003\u0004\u0005\u0006\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredStatementOrigin;", "", "DEFAULT_STUB_CALL_TO_IMPLEMENTATION", "DO_WHILE_COUNTER_LOOP", "FAKE_CONTINUATION", "FOLDED_ELVIS", "FOLDED_SAFE_CALL", "INLINE_LAMBDA", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JvmLoweredStatementOrigin {

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredStatementOrigin$DEFAULT_STUB_CALL_TO_IMPLEMENTATION;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DEFAULT_STUB_CALL_TO_IMPLEMENTATION extends IrStatementOriginImpl {
        public static final DEFAULT_STUB_CALL_TO_IMPLEMENTATION INSTANCE = new DEFAULT_STUB_CALL_TO_IMPLEMENTATION();

        private DEFAULT_STUB_CALL_TO_IMPLEMENTATION() {
            super("DEFAULT_STUB_CALL_TO_IMPLEMENTATION");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredStatementOrigin$DO_WHILE_COUNTER_LOOP;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DO_WHILE_COUNTER_LOOP extends IrStatementOriginImpl {
        public static final DO_WHILE_COUNTER_LOOP INSTANCE = new DO_WHILE_COUNTER_LOOP();

        private DO_WHILE_COUNTER_LOOP() {
            super("DO_WHILE_COUNTER_LOOP");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredStatementOrigin$INLINE_LAMBDA;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class INLINE_LAMBDA extends IrStatementOriginImpl {
        public static final INLINE_LAMBDA INSTANCE = new INLINE_LAMBDA();

        private INLINE_LAMBDA() {
            super("INLINE_LAMBDA");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredStatementOrigin$FAKE_CONTINUATION;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FAKE_CONTINUATION extends IrStatementOriginImpl {
        public static final FAKE_CONTINUATION INSTANCE = new FAKE_CONTINUATION();

        private FAKE_CONTINUATION() {
            super("FAKE_CONTINUATION");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredStatementOrigin$FOLDED_SAFE_CALL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOLDED_SAFE_CALL extends IrStatementOriginImpl {
        public static final FOLDED_SAFE_CALL INSTANCE = new FOLDED_SAFE_CALL();

        private FOLDED_SAFE_CALL() {
            super("FOLDED_SAFE_CALL");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredStatementOrigin$FOLDED_ELVIS;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOLDED_ELVIS extends IrStatementOriginImpl {
        public static final FOLDED_ELVIS INSTANCE = new FOLDED_ELVIS();

        private FOLDED_ELVIS() {
            super("FOLDED_ELVIS");
        }
    }
}
