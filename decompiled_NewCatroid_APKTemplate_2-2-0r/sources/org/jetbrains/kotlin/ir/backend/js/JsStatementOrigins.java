package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;

/* JADX INFO: compiled from: StatementOrigins.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\f\bf\u0018\u00002\u00020\u0001:\u000b\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins;", "", "BIND_CALL", "CALLABLE_REFERENCE_CREATE", "CALLABLE_REFERENCE_INVOKE", "CLASS_REFERENCE", "COROUTINE_ROOT_LOOP", "COROUTINE_SWITCH", "EXPLICIT_INVOKE", "FACTORY_ORIGIN", "IMPLEMENTATION_DELEGATION_CALL", "STATEMENT_ORIGIN_COROUTINE_IMPL", "SYNTHESIZED_STATEMENT", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JsStatementOrigins {

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$BIND_CALL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BIND_CALL extends IrStatementOriginImpl {
        public static final BIND_CALL INSTANCE = new BIND_CALL();

        private BIND_CALL() {
            super("BIND_CALL");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$STATEMENT_ORIGIN_COROUTINE_IMPL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class STATEMENT_ORIGIN_COROUTINE_IMPL extends IrStatementOriginImpl {
        public static final STATEMENT_ORIGIN_COROUTINE_IMPL INSTANCE = new STATEMENT_ORIGIN_COROUTINE_IMPL();

        private STATEMENT_ORIGIN_COROUTINE_IMPL() {
            super("COROUTINE_IMPL");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$SYNTHESIZED_STATEMENT;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHESIZED_STATEMENT extends IrStatementOriginImpl {
        public static final SYNTHESIZED_STATEMENT INSTANCE = new SYNTHESIZED_STATEMENT();

        private SYNTHESIZED_STATEMENT() {
            super("SYNTHESIZED_STATEMENT");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$CALLABLE_REFERENCE_CREATE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CALLABLE_REFERENCE_CREATE extends IrStatementOriginImpl {
        public static final CALLABLE_REFERENCE_CREATE INSTANCE = new CALLABLE_REFERENCE_CREATE();

        private CALLABLE_REFERENCE_CREATE() {
            super("CALLABLE_REFERENCE_CREATE");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$CALLABLE_REFERENCE_INVOKE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CALLABLE_REFERENCE_INVOKE extends IrStatementOriginImpl {
        public static final CALLABLE_REFERENCE_INVOKE INSTANCE = new CALLABLE_REFERENCE_INVOKE();

        private CALLABLE_REFERENCE_INVOKE() {
            super("CALLABLE_REFERENCE_INVOKE");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$EXPLICIT_INVOKE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EXPLICIT_INVOKE extends IrStatementOriginImpl {
        public static final EXPLICIT_INVOKE INSTANCE = new EXPLICIT_INVOKE();

        private EXPLICIT_INVOKE() {
            super("EXPLICIT_INVOKE");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$FACTORY_ORIGIN;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FACTORY_ORIGIN extends IrDeclarationOriginImpl {
        public static final FACTORY_ORIGIN INSTANCE = new FACTORY_ORIGIN();

        private FACTORY_ORIGIN() {
            super("FACTORY_ORIGIN", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$COROUTINE_ROOT_LOOP;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class COROUTINE_ROOT_LOOP extends IrStatementOriginImpl {
        public static final COROUTINE_ROOT_LOOP INSTANCE = new COROUTINE_ROOT_LOOP();

        private COROUTINE_ROOT_LOOP() {
            super("COROUTINE_ROOT_LOOP");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$COROUTINE_SWITCH;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class COROUTINE_SWITCH extends IrStatementOriginImpl {
        public static final COROUTINE_SWITCH INSTANCE = new COROUTINE_SWITCH();

        private COROUTINE_SWITCH() {
            super("COROUTINE_SWITCH");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$CLASS_REFERENCE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CLASS_REFERENCE extends IrStatementOriginImpl {
        public static final CLASS_REFERENCE INSTANCE = new CLASS_REFERENCE();

        private CLASS_REFERENCE() {
            super("CLASS_REFERENCE");
        }
    }

    /* JADX INFO: compiled from: StatementOrigins.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsStatementOrigins$IMPLEMENTATION_DELEGATION_CALL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IMPLEMENTATION_DELEGATION_CALL extends IrStatementOriginImpl {
        public static final IMPLEMENTATION_DELEGATION_CALL INSTANCE = new IMPLEMENTATION_DELEGATION_CALL();

        private IMPLEMENTATION_DELEGATION_CALL() {
            super("IMPLEMENTATION_DELEGATION_CALL");
        }
    }
}
