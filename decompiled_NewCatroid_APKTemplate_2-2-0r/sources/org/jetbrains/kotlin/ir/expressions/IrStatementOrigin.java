package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.objenesis.strategy.PlatformDescription;

/* JADX INFO: compiled from: IrStatementOrigin.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\bC\bf\u0018\u00002\u00020\u0001:B\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?@ABC¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "", "ADAPTED_FUNCTION_REFERENCE", BuiltInOperatorNames.ANDAND, "ANONYMOUS_FUNCTION", "ARGUMENTS_REORDERING_FOR_CALL", "BRIDGE_DELEGATION", "COMPONENT_N", "DEFAULT_VALUE", "DESTRUCTURING_DECLARATION", "DIV", "DIVEQ", "DO_WHILE_LOOP", "ELVIS", "EQ", BuiltInOperatorNames.EQEQ, BuiltInOperatorNames.EQEQEQ, "EXCL", "EXCLEQ", "EXCLEQEQ", "EXCLEXCL", "FOR_LOOP", "FOR_LOOP_HAS_NEXT", "FOR_LOOP_INNER_WHILE", "FOR_LOOP_ITERATOR", "FOR_LOOP_NEXT", "FUN_INTERFACE_CONSTRUCTOR_REFERENCE", "GET_ARRAY_ELEMENT", "GET_LOCAL_PROPERTY", "GET_PROPERTY", "GT", "GTEQ", "IF", "IN", "INITIALIZE_FIELD", "INITIALIZE_PROPERTY_FROM_PARAMETER", "INVOKE", "LAMBDA", "LT", "LTEQ", "MINUS", "MINUSEQ", "MUL", "MULTEQ", "NOT_IN", "OBJECT_LITERAL", BuiltInOperatorNames.OROR, "PARTIAL_LINKAGE_RUNTIME_ERROR", PlatformDescription.PERC, "PERCEQ", "PLUS", "PLUSEQ", "POSTFIX_DECR", "POSTFIX_INCR", "PREFIX_DECR", "PREFIX_INCR", "PROPERTY_REFERENCE_FOR_DELEGATE", "RANGE", "RANGE_UNTIL", "SAFE_CALL", "SUSPEND_CONVERSION", "SYNTHETIC_NOT_AUTOBOXED_CHECK", "UMINUS", "UPLUS", "VARIABLE_AS_FUNCTION", "WHEN", "WHEN_COMMA", "WHILE_LOOP", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrStatementOrigin {

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$SAFE_CALL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SAFE_CALL extends IrStatementOriginImpl {
        public static final SAFE_CALL INSTANCE = new SAFE_CALL();

        private SAFE_CALL() {
            super("SAFE_CALL");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$UMINUS;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UMINUS extends IrStatementOriginImpl {
        public static final UMINUS INSTANCE = new UMINUS();

        private UMINUS() {
            super("UMINUS");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$UPLUS;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UPLUS extends IrStatementOriginImpl {
        public static final UPLUS INSTANCE = new UPLUS();

        private UPLUS() {
            super("UPLUS");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$EXCL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EXCL extends IrStatementOriginImpl {
        public static final EXCL INSTANCE = new EXCL();

        private EXCL() {
            super("EXCL");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$EXCLEXCL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EXCLEXCL extends IrStatementOriginImpl {
        public static final EXCLEXCL INSTANCE = new EXCLEXCL();

        private EXCLEXCL() {
            super("EXCLEXCL");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$ELVIS;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ELVIS extends IrStatementOriginImpl {
        public static final ELVIS INSTANCE = new ELVIS();

        private ELVIS() {
            super("ELVIS");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$LT;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LT extends IrStatementOriginImpl {
        public static final LT INSTANCE = new LT();

        private LT() {
            super("LT");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$GT;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GT extends IrStatementOriginImpl {
        public static final GT INSTANCE = new GT();

        private GT() {
            super("GT");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$LTEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LTEQ extends IrStatementOriginImpl {
        public static final LTEQ INSTANCE = new LTEQ();

        private LTEQ() {
            super("LTEQ");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$GTEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GTEQ extends IrStatementOriginImpl {
        public static final GTEQ INSTANCE = new GTEQ();

        private GTEQ() {
            super("GTEQ");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$EQEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EQEQ extends IrStatementOriginImpl {
        public static final EQEQ INSTANCE = new EQEQ();

        private EQEQ() {
            super(BuiltInOperatorNames.EQEQ);
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$EQEQEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EQEQEQ extends IrStatementOriginImpl {
        public static final EQEQEQ INSTANCE = new EQEQEQ();

        private EQEQEQ() {
            super(BuiltInOperatorNames.EQEQEQ);
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$EXCLEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EXCLEQ extends IrStatementOriginImpl {
        public static final EXCLEQ INSTANCE = new EXCLEQ();

        private EXCLEQ() {
            super("EXCLEQ");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$EXCLEQEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EXCLEQEQ extends IrStatementOriginImpl {
        public static final EXCLEQEQ INSTANCE = new EXCLEQEQ();

        private EXCLEQEQ() {
            super("EXCLEQEQ");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$IN;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IN extends IrStatementOriginImpl {
        public static final IN INSTANCE = new IN();

        private IN() {
            super("IN");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$NOT_IN;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NOT_IN extends IrStatementOriginImpl {
        public static final NOT_IN INSTANCE = new NOT_IN();

        private NOT_IN() {
            super("NOT_IN");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$ANDAND;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ANDAND extends IrStatementOriginImpl {
        public static final ANDAND INSTANCE = new ANDAND();

        private ANDAND() {
            super(BuiltInOperatorNames.ANDAND);
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$OROR;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class OROR extends IrStatementOriginImpl {
        public static final OROR INSTANCE = new OROR();

        private OROR() {
            super(BuiltInOperatorNames.OROR);
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$PLUS;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PLUS extends IrStatementOriginImpl {
        public static final PLUS INSTANCE = new PLUS();

        private PLUS() {
            super("PLUS");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$MINUS;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MINUS extends IrStatementOriginImpl {
        public static final MINUS INSTANCE = new MINUS();

        private MINUS() {
            super("MINUS");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$MUL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MUL extends IrStatementOriginImpl {
        public static final MUL INSTANCE = new MUL();

        private MUL() {
            super("MUL");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$DIV;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DIV extends IrStatementOriginImpl {
        public static final DIV INSTANCE = new DIV();

        private DIV() {
            super("DIV");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$PERC;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PERC extends IrStatementOriginImpl {
        public static final PERC INSTANCE = new PERC();

        private PERC() {
            super(PlatformDescription.PERC);
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$RANGE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RANGE extends IrStatementOriginImpl {
        public static final RANGE INSTANCE = new RANGE();

        private RANGE() {
            super("RANGE");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$RANGE_UNTIL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RANGE_UNTIL extends IrStatementOriginImpl {
        public static final RANGE_UNTIL INSTANCE = new RANGE_UNTIL();

        private RANGE_UNTIL() {
            super("RANGE_UNTIL");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$INVOKE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class INVOKE extends IrStatementOriginImpl {
        public static final INVOKE INSTANCE = new INVOKE();

        private INVOKE() {
            super("INVOKE");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$VARIABLE_AS_FUNCTION;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class VARIABLE_AS_FUNCTION extends IrStatementOriginImpl {
        public static final VARIABLE_AS_FUNCTION INSTANCE = new VARIABLE_AS_FUNCTION();

        private VARIABLE_AS_FUNCTION() {
            super("VARIABLE_AS_FUNCTION");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$GET_ARRAY_ELEMENT;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GET_ARRAY_ELEMENT extends IrStatementOriginImpl {
        public static final GET_ARRAY_ELEMENT INSTANCE = new GET_ARRAY_ELEMENT();

        private GET_ARRAY_ELEMENT() {
            super("GET_ARRAY_ELEMENT");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$PREFIX_INCR;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PREFIX_INCR extends IrStatementOriginImpl {
        public static final PREFIX_INCR INSTANCE = new PREFIX_INCR();

        private PREFIX_INCR() {
            super("PREFIX_INCR");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$PREFIX_DECR;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PREFIX_DECR extends IrStatementOriginImpl {
        public static final PREFIX_DECR INSTANCE = new PREFIX_DECR();

        private PREFIX_DECR() {
            super("PREFIX_DECR");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$POSTFIX_INCR;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class POSTFIX_INCR extends IrStatementOriginImpl {
        public static final POSTFIX_INCR INSTANCE = new POSTFIX_INCR();

        private POSTFIX_INCR() {
            super("POSTFIX_INCR");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$POSTFIX_DECR;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class POSTFIX_DECR extends IrStatementOriginImpl {
        public static final POSTFIX_DECR INSTANCE = new POSTFIX_DECR();

        private POSTFIX_DECR() {
            super("POSTFIX_DECR");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$EQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EQ extends IrStatementOriginImpl {
        public static final EQ INSTANCE = new EQ();

        private EQ() {
            super("EQ");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$PLUSEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PLUSEQ extends IrStatementOriginImpl {
        public static final PLUSEQ INSTANCE = new PLUSEQ();

        private PLUSEQ() {
            super("PLUSEQ");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$MINUSEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MINUSEQ extends IrStatementOriginImpl {
        public static final MINUSEQ INSTANCE = new MINUSEQ();

        private MINUSEQ() {
            super("MINUSEQ");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$MULTEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MULTEQ extends IrStatementOriginImpl {
        public static final MULTEQ INSTANCE = new MULTEQ();

        private MULTEQ() {
            super("MULTEQ");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$DIVEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DIVEQ extends IrStatementOriginImpl {
        public static final DIVEQ INSTANCE = new DIVEQ();

        private DIVEQ() {
            super("DIVEQ");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$PERCEQ;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PERCEQ extends IrStatementOriginImpl {
        public static final PERCEQ INSTANCE = new PERCEQ();

        private PERCEQ() {
            super("PERCEQ");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$ARGUMENTS_REORDERING_FOR_CALL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ARGUMENTS_REORDERING_FOR_CALL extends IrStatementOriginImpl {
        public static final ARGUMENTS_REORDERING_FOR_CALL INSTANCE = new ARGUMENTS_REORDERING_FOR_CALL();

        private ARGUMENTS_REORDERING_FOR_CALL() {
            super("ARGUMENTS_REORDERING_FOR_CALL");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$DESTRUCTURING_DECLARATION;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DESTRUCTURING_DECLARATION extends IrStatementOriginImpl {
        public static final DESTRUCTURING_DECLARATION INSTANCE = new DESTRUCTURING_DECLARATION();

        private DESTRUCTURING_DECLARATION() {
            super("DESTRUCTURING_DECLARATION");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$GET_PROPERTY;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GET_PROPERTY extends IrStatementOriginImpl {
        public static final GET_PROPERTY INSTANCE = new GET_PROPERTY();

        private GET_PROPERTY() {
            super("GET_PROPERTY");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$GET_LOCAL_PROPERTY;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GET_LOCAL_PROPERTY extends IrStatementOriginImpl {
        public static final GET_LOCAL_PROPERTY INSTANCE = new GET_LOCAL_PROPERTY();

        private GET_LOCAL_PROPERTY() {
            super("GET_LOCAL_PROPERTY");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$IF;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IF extends IrStatementOriginImpl {
        public static final IF INSTANCE = new IF();

        private IF() {
            super("IF");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$WHEN;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WHEN extends IrStatementOriginImpl {
        public static final WHEN INSTANCE = new WHEN();

        private WHEN() {
            super("WHEN");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$WHEN_COMMA;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WHEN_COMMA extends IrStatementOriginImpl {
        public static final WHEN_COMMA INSTANCE = new WHEN_COMMA();

        private WHEN_COMMA() {
            super("WHEN_COMMA");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$WHILE_LOOP;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WHILE_LOOP extends IrStatementOriginImpl {
        public static final WHILE_LOOP INSTANCE = new WHILE_LOOP();

        private WHILE_LOOP() {
            super("WHILE_LOOP");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$DO_WHILE_LOOP;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DO_WHILE_LOOP extends IrStatementOriginImpl {
        public static final DO_WHILE_LOOP INSTANCE = new DO_WHILE_LOOP();

        private DO_WHILE_LOOP() {
            super("DO_WHILE_LOOP");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$FOR_LOOP;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOR_LOOP extends IrStatementOriginImpl {
        public static final FOR_LOOP INSTANCE = new FOR_LOOP();

        private FOR_LOOP() {
            super("FOR_LOOP");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$FOR_LOOP_ITERATOR;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOR_LOOP_ITERATOR extends IrStatementOriginImpl {
        public static final FOR_LOOP_ITERATOR INSTANCE = new FOR_LOOP_ITERATOR();

        private FOR_LOOP_ITERATOR() {
            super("FOR_LOOP_ITERATOR");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$FOR_LOOP_INNER_WHILE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOR_LOOP_INNER_WHILE extends IrStatementOriginImpl {
        public static final FOR_LOOP_INNER_WHILE INSTANCE = new FOR_LOOP_INNER_WHILE();

        private FOR_LOOP_INNER_WHILE() {
            super("FOR_LOOP_INNER_WHILE");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$FOR_LOOP_HAS_NEXT;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOR_LOOP_HAS_NEXT extends IrStatementOriginImpl {
        public static final FOR_LOOP_HAS_NEXT INSTANCE = new FOR_LOOP_HAS_NEXT();

        private FOR_LOOP_HAS_NEXT() {
            super("FOR_LOOP_HAS_NEXT");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$FOR_LOOP_NEXT;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOR_LOOP_NEXT extends IrStatementOriginImpl {
        public static final FOR_LOOP_NEXT INSTANCE = new FOR_LOOP_NEXT();

        private FOR_LOOP_NEXT() {
            super("FOR_LOOP_NEXT");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$LAMBDA;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LAMBDA extends IrStatementOriginImpl {
        public static final LAMBDA INSTANCE = new LAMBDA();

        private LAMBDA() {
            super("LAMBDA");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$DEFAULT_VALUE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DEFAULT_VALUE extends IrStatementOriginImpl {
        public static final DEFAULT_VALUE INSTANCE = new DEFAULT_VALUE();

        private DEFAULT_VALUE() {
            super("DEFAULT_VALUE");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$ANONYMOUS_FUNCTION;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ANONYMOUS_FUNCTION extends IrStatementOriginImpl {
        public static final ANONYMOUS_FUNCTION INSTANCE = new ANONYMOUS_FUNCTION();

        private ANONYMOUS_FUNCTION() {
            super("ANONYMOUS_FUNCTION");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$OBJECT_LITERAL;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class OBJECT_LITERAL extends IrStatementOriginImpl {
        public static final OBJECT_LITERAL INSTANCE = new OBJECT_LITERAL();

        private OBJECT_LITERAL() {
            super("OBJECT_LITERAL");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$ADAPTED_FUNCTION_REFERENCE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ADAPTED_FUNCTION_REFERENCE extends IrStatementOriginImpl {
        public static final ADAPTED_FUNCTION_REFERENCE INSTANCE = new ADAPTED_FUNCTION_REFERENCE();

        private ADAPTED_FUNCTION_REFERENCE() {
            super("ADAPTED_FUNCTION_REFERENCE");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$SUSPEND_CONVERSION;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SUSPEND_CONVERSION extends IrStatementOriginImpl {
        public static final SUSPEND_CONVERSION INSTANCE = new SUSPEND_CONVERSION();

        private SUSPEND_CONVERSION() {
            super("SUSPEND_CONVERSION");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$FUN_INTERFACE_CONSTRUCTOR_REFERENCE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FUN_INTERFACE_CONSTRUCTOR_REFERENCE extends IrStatementOriginImpl {
        public static final FUN_INTERFACE_CONSTRUCTOR_REFERENCE INSTANCE = new FUN_INTERFACE_CONSTRUCTOR_REFERENCE();

        private FUN_INTERFACE_CONSTRUCTOR_REFERENCE() {
            super("FUN_INTERFACE_CONSTRUCTOR_REFERENCE");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$INITIALIZE_PROPERTY_FROM_PARAMETER;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class INITIALIZE_PROPERTY_FROM_PARAMETER extends IrStatementOriginImpl {
        public static final INITIALIZE_PROPERTY_FROM_PARAMETER INSTANCE = new INITIALIZE_PROPERTY_FROM_PARAMETER();

        private INITIALIZE_PROPERTY_FROM_PARAMETER() {
            super("INITIALIZE_PROPERTY_FROM_PARAMETER");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$INITIALIZE_FIELD;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class INITIALIZE_FIELD extends IrStatementOriginImpl {
        public static final INITIALIZE_FIELD INSTANCE = new INITIALIZE_FIELD();

        private INITIALIZE_FIELD() {
            super("INITIALIZE_FIELD");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$PROPERTY_REFERENCE_FOR_DELEGATE;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PROPERTY_REFERENCE_FOR_DELEGATE extends IrStatementOriginImpl {
        public static final PROPERTY_REFERENCE_FOR_DELEGATE INSTANCE = new PROPERTY_REFERENCE_FOR_DELEGATE();

        private PROPERTY_REFERENCE_FOR_DELEGATE() {
            super("PROPERTY_REFERENCE_FOR_DELEGATE");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$BRIDGE_DELEGATION;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BRIDGE_DELEGATION extends IrStatementOriginImpl {
        public static final BRIDGE_DELEGATION INSTANCE = new BRIDGE_DELEGATION();

        private BRIDGE_DELEGATION() {
            super("BRIDGE_DELEGATION");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$SYNTHETIC_NOT_AUTOBOXED_CHECK;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_NOT_AUTOBOXED_CHECK extends IrStatementOriginImpl {
        public static final SYNTHETIC_NOT_AUTOBOXED_CHECK INSTANCE = new SYNTHETIC_NOT_AUTOBOXED_CHECK();

        private SYNTHETIC_NOT_AUTOBOXED_CHECK() {
            super("SYNTHETIC_NOT_AUTOBOXED_CHECK");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$PARTIAL_LINKAGE_RUNTIME_ERROR;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PARTIAL_LINKAGE_RUNTIME_ERROR extends IrStatementOriginImpl {
        public static final PARTIAL_LINKAGE_RUNTIME_ERROR INSTANCE = new PARTIAL_LINKAGE_RUNTIME_ERROR();

        private PARTIAL_LINKAGE_RUNTIME_ERROR() {
            super("PARTIAL_LINKAGE_RUNTIME_ERROR");
        }
    }

    /* JADX INFO: compiled from: IrStatementOrigin.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u0003HÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$COMPONENT_N;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOriginImpl;", "index", "", "(I)V", "getIndex", "()I", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class COMPONENT_N extends IrStatementOriginImpl {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final COMPONENT_N[] precreatedComponents;
        private final int index;

        public /* synthetic */ COMPONENT_N(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        public static /* synthetic */ COMPONENT_N copy$default(COMPONENT_N component_n, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = component_n.index;
            }
            return component_n.copy(i);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getIndex() {
            return this.index;
        }

        public final COMPONENT_N copy(int index) {
            return new COMPONENT_N(index);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof COMPONENT_N) && this.index == ((COMPONENT_N) other).index;
        }

        public int hashCode() {
            return Integer.hashCode(this.index);
        }

        @Override // org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl
        public String toString() {
            return "COMPONENT_N(index=" + this.index + ')';
        }

        /* JADX INFO: compiled from: IrStatementOrigin.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$COMPONENT_N$Companion;", "", "()V", "precreatedComponents", "", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$COMPONENT_N;", "[Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin$COMPONENT_N;", "withIndex", "index", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final COMPONENT_N withIndex(int index) {
                if (index < COMPONENT_N.precreatedComponents.length) {
                    return COMPONENT_N.precreatedComponents[index - 1];
                }
                return new COMPONENT_N(index, null);
            }
        }

        private COMPONENT_N(int i) {
            super("COMPONENT_" + i);
            this.index = i;
        }

        public final int getIndex() {
            return this.index;
        }

        static {
            COMPONENT_N[] component_nArr = new COMPONENT_N[32];
            int i = 0;
            while (i < 32) {
                int i2 = i + 1;
                component_nArr[i] = new COMPONENT_N(i2);
                i = i2;
            }
            precreatedComponents = component_nArr;
        }
    }
}
