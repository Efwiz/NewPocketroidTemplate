package org.jetbrains.kotlin.ir.expressions;

import com.beust.jcommander.Parameters;
import com.huawei.hms.framework.common.ContainerUtils;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okhttp3.HttpUrl;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.slf4j.Marker;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'BINARY_PLUS' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:160)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: IrDynamicOperator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b#\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "", Constants.TMP_IMAGE_FILE_NAME, "", "isAssignmentOperator", "", "(Ljava/lang/String;ILjava/lang/String;Z)V", "getImage", "()Ljava/lang/String;", "()Z", "UNARY_PLUS", "UNARY_MINUS", "EXCL", "PREFIX_INCREMENT", "POSTFIX_INCREMENT", "PREFIX_DECREMENT", "POSTFIX_DECREMENT", "BINARY_PLUS", "BINARY_MINUS", "MUL", "DIV", "MOD", "GT", "LT", "GE", "LE", BuiltInOperatorNames.EQEQ, "EXCLEQ", BuiltInOperatorNames.EQEQEQ, "EXCLEQEQ", BuiltInOperatorNames.ANDAND, BuiltInOperatorNames.OROR, "EQ", "PLUSEQ", "MINUSEQ", "MULEQ", "DIVEQ", "MODEQ", "ARRAY_ACCESS", "INVOKE", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDynamicOperator {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ IrDynamicOperator[] $VALUES;
    public static final IrDynamicOperator ANDAND;
    public static final IrDynamicOperator ARRAY_ACCESS;
    public static final IrDynamicOperator BINARY_MINUS;
    public static final IrDynamicOperator BINARY_PLUS;
    public static final IrDynamicOperator DIV;
    public static final IrDynamicOperator EQEQ;
    public static final IrDynamicOperator EQEQEQ;
    public static final IrDynamicOperator EXCLEQ;
    public static final IrDynamicOperator EXCLEQEQ;
    public static final IrDynamicOperator GE;
    public static final IrDynamicOperator GT;
    public static final IrDynamicOperator INVOKE;
    public static final IrDynamicOperator LE;
    public static final IrDynamicOperator LT;
    public static final IrDynamicOperator MOD;
    public static final IrDynamicOperator MUL;
    public static final IrDynamicOperator OROR;
    private final String image;
    private final boolean isAssignmentOperator;
    public static final IrDynamicOperator UNARY_PLUS = new IrDynamicOperator("UNARY_PLUS", 0, Marker.ANY_NON_NULL_MARKER, false, 2, null);
    public static final IrDynamicOperator UNARY_MINUS = new IrDynamicOperator("UNARY_MINUS", 1, Parameters.DEFAULT_OPTION_PREFIXES, false, 2, null);
    public static final IrDynamicOperator EXCL = new IrDynamicOperator("EXCL", 2, "!", false, 2, null);
    public static final IrDynamicOperator PREFIX_INCREMENT = new IrDynamicOperator("PREFIX_INCREMENT", 3, "++", true);
    public static final IrDynamicOperator POSTFIX_INCREMENT = new IrDynamicOperator("POSTFIX_INCREMENT", 4, "++", true);
    public static final IrDynamicOperator PREFIX_DECREMENT = new IrDynamicOperator("PREFIX_DECREMENT", 5, "--", true);
    public static final IrDynamicOperator POSTFIX_DECREMENT = new IrDynamicOperator("POSTFIX_DECREMENT", 6, "--", true);
    public static final IrDynamicOperator EQ = new IrDynamicOperator("EQ", 22, ContainerUtils.KEY_VALUE_DELIMITER, true);
    public static final IrDynamicOperator PLUSEQ = new IrDynamicOperator("PLUSEQ", 23, "+=", true);
    public static final IrDynamicOperator MINUSEQ = new IrDynamicOperator("MINUSEQ", 24, "-=", true);
    public static final IrDynamicOperator MULEQ = new IrDynamicOperator("MULEQ", 25, "*=", true);
    public static final IrDynamicOperator DIVEQ = new IrDynamicOperator("DIVEQ", 26, "/=", true);
    public static final IrDynamicOperator MODEQ = new IrDynamicOperator("MODEQ", 27, "%=", true);

    private static final /* synthetic */ IrDynamicOperator[] $values() {
        return new IrDynamicOperator[]{UNARY_PLUS, UNARY_MINUS, EXCL, PREFIX_INCREMENT, POSTFIX_INCREMENT, PREFIX_DECREMENT, POSTFIX_DECREMENT, BINARY_PLUS, BINARY_MINUS, MUL, DIV, MOD, GT, LT, GE, LE, EQEQ, EXCLEQ, EQEQEQ, EXCLEQEQ, ANDAND, OROR, EQ, PLUSEQ, MINUSEQ, MULEQ, DIVEQ, MODEQ, ARRAY_ACCESS, INVOKE};
    }

    public static EnumEntries<IrDynamicOperator> getEntries() {
        return $ENTRIES;
    }

    public static IrDynamicOperator valueOf(String str) {
        return (IrDynamicOperator) Enum.valueOf(IrDynamicOperator.class, str);
    }

    public static IrDynamicOperator[] values() {
        return (IrDynamicOperator[]) $VALUES.clone();
    }

    private IrDynamicOperator(String str, int i, String str2, boolean z) {
        super(str, i);
        this.image = str2;
        this.isAssignmentOperator = z;
    }

    /* synthetic */ IrDynamicOperator(String str, int i, String str2, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, str2, (i2 & 2) != 0 ? false : z);
    }

    public final String getImage() {
        return this.image;
    }

    /* JADX INFO: renamed from: isAssignmentOperator, reason: from getter */
    public final boolean getIsAssignmentOperator() {
        return this.isAssignmentOperator;
    }

    static {
        int i = 2;
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z = false;
        BINARY_PLUS = new IrDynamicOperator("BINARY_PLUS", 7, Marker.ANY_NON_NULL_MARKER, z, i, defaultConstructorMarker);
        int i2 = 2;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        boolean z2 = false;
        BINARY_MINUS = new IrDynamicOperator("BINARY_MINUS", 8, Parameters.DEFAULT_OPTION_PREFIXES, z2, i2, defaultConstructorMarker2);
        MUL = new IrDynamicOperator("MUL", 9, "*", z, i, defaultConstructorMarker);
        DIV = new IrDynamicOperator("DIV", 10, "/", z2, i2, defaultConstructorMarker2);
        MOD = new IrDynamicOperator("MOD", 11, "%", z, i, defaultConstructorMarker);
        GT = new IrDynamicOperator("GT", 12, ">", z2, i2, defaultConstructorMarker2);
        LT = new IrDynamicOperator("LT", 13, "<", z, i, defaultConstructorMarker);
        GE = new IrDynamicOperator("GE", 14, ">=", z2, i2, defaultConstructorMarker2);
        LE = new IrDynamicOperator("LE", 15, "<=", z, i, defaultConstructorMarker);
        EQEQ = new IrDynamicOperator(BuiltInOperatorNames.EQEQ, 16, "==", z2, i2, defaultConstructorMarker2);
        EXCLEQ = new IrDynamicOperator("EXCLEQ", 17, "!=", z, i, defaultConstructorMarker);
        EQEQEQ = new IrDynamicOperator(BuiltInOperatorNames.EQEQEQ, 18, "===", z2, i2, defaultConstructorMarker2);
        EXCLEQEQ = new IrDynamicOperator("EXCLEQEQ", 19, "!==", z, i, defaultConstructorMarker);
        ANDAND = new IrDynamicOperator(BuiltInOperatorNames.ANDAND, 20, "&&", z2, i2, defaultConstructorMarker2);
        OROR = new IrDynamicOperator(BuiltInOperatorNames.OROR, 21, "||", z, i, defaultConstructorMarker);
        ARRAY_ACCESS = new IrDynamicOperator("ARRAY_ACCESS", 28, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, z, i, defaultConstructorMarker);
        INVOKE = new IrDynamicOperator("INVOKE", 29, "()", z2, i2, defaultConstructorMarker2);
        IrDynamicOperator[] irDynamicOperatorArr$values = $values();
        $VALUES = irDynamicOperatorArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(irDynamicOperatorArr$values);
    }
}
