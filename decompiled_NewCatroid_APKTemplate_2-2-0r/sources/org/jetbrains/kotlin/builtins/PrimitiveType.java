package org.jetbrains.kotlin.builtins;

import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r4v0 org.jetbrains.kotlin.builtins.PrimitiveType, still in use, count: 1, list:
  (r4v0 org.jetbrains.kotlin.builtins.PrimitiveType) from 0x0076: FILLED_NEW_ARRAY 
  (r4v0 org.jetbrains.kotlin.builtins.PrimitiveType)
  (r5v0 org.jetbrains.kotlin.builtins.PrimitiveType)
  (r6v0 org.jetbrains.kotlin.builtins.PrimitiveType)
  (r7v0 org.jetbrains.kotlin.builtins.PrimitiveType)
  (r8v0 org.jetbrains.kotlin.builtins.PrimitiveType)
  (r9v0 org.jetbrains.kotlin.builtins.PrimitiveType)
  (r10v0 org.jetbrains.kotlin.builtins.PrimitiveType)
 A[WRAPPED] (LINE:32) elemType: org.jetbrains.kotlin.builtins.PrimitiveType
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:257)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:187)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: PrimitiveType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0086\u0081\u0002\u0018\u0000 \u001b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001b\u0010\u0005\u001a\u00020\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u000f\u001a\u00020\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\n\u001a\u0004\b\u0010\u0010\bR\u0011\u0010\u0002\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000ej\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001a¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "", "typeName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "arrayTypeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getArrayTypeFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "arrayTypeFqName$delegate", "Lkotlin/Lazy;", "arrayTypeName", "Lorg/jetbrains/kotlin/name/Name;", "getArrayTypeName", "()Lorg/jetbrains/kotlin/name/Name;", "typeFqName", "getTypeFqName", "typeFqName$delegate", "getTypeName", "BOOLEAN", "CHAR", "BYTE", "SHORT", "INT", "FLOAT", "LONG", "DOUBLE", "Companion", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveType {
    BOOLEAN("Boolean"),
    CHAR("Char"),
    BYTE("Byte"),
    SHORT("Short"),
    INT("Int"),
    FLOAT("Float"),
    LONG("Long"),
    DOUBLE("Double");


    /* JADX INFO: renamed from: arrayTypeFqName$delegate, reason: from kotlin metadata */
    private final Lazy arrayTypeFqName;
    private final Name arrayTypeName;

    /* JADX INFO: renamed from: typeFqName$delegate, reason: from kotlin metadata */
    private final Lazy typeFqName;
    private final Name typeName;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final Set<PrimitiveType> NUMBER_TYPES = SetsKt.setOf((Object[]) new PrimitiveType[]{new PrimitiveType("Char"), new PrimitiveType("Byte"), new PrimitiveType("Short"), new PrimitiveType("Int"), new PrimitiveType("Float"), new PrimitiveType("Long"), new PrimitiveType("Double")});

    @JvmStatic
    public static final PrimitiveType getByShortArrayName(String str) {
        return INSTANCE.getByShortArrayName(str);
    }

    @JvmStatic
    public static final PrimitiveType getByShortName(String str) {
        return INSTANCE.getByShortName(str);
    }

    public static EnumEntries<PrimitiveType> getEntries() {
        return $ENTRIES;
    }

    public static PrimitiveType valueOf(String str) {
        return (PrimitiveType) Enum.valueOf(PrimitiveType.class, str);
    }

    public static PrimitiveType[] values() {
        return (PrimitiveType[]) $VALUES.clone();
    }

    private PrimitiveType(String str) {
        super(str, i);
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this.typeName = nameIdentifier;
        Name nameIdentifier2 = Name.identifier(str + "Array");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        this.arrayTypeName = nameIdentifier2;
        this.typeFqName = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<FqName>() { // from class: org.jetbrains.kotlin.builtins.PrimitiveType$typeFqName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FqName invoke() {
                FqName fqNameChild = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(this.this$0.getTypeName());
                Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
                return fqNameChild;
            }
        });
        this.arrayTypeFqName = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<FqName>() { // from class: org.jetbrains.kotlin.builtins.PrimitiveType$arrayTypeFqName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FqName invoke() {
                FqName fqNameChild = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(this.this$0.getArrayTypeName());
                Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
                return fqNameChild;
            }
        });
    }

    static {
    }

    public final Name getTypeName() {
        return this.typeName;
    }

    public final Name getArrayTypeName() {
        return this.arrayTypeName;
    }

    public final FqName getTypeFqName() {
        return (FqName) this.typeFqName.getValue();
    }

    public final FqName getArrayTypeFqName() {
        return (FqName) this.arrayTypeFqName.getValue();
    }

    /* JADX INFO: compiled from: PrimitiveType.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/builtins/PrimitiveType$Companion;", "", "()V", "NUMBER_TYPES", "", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getByShortArrayName", "name", "", "getByShortName", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @JvmStatic
        public final PrimitiveType getByShortName(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            switch (name2.hashCode()) {
                case 73679:
                    if (name2.equals("Int")) {
                        return PrimitiveType.INT;
                    }
                    return null;
                case 2086184:
                    if (name2.equals("Byte")) {
                        return PrimitiveType.BYTE;
                    }
                    return null;
                case 2099062:
                    if (name2.equals("Char")) {
                        return PrimitiveType.CHAR;
                    }
                    return null;
                case 2374300:
                    if (name2.equals("Long")) {
                        return PrimitiveType.LONG;
                    }
                    return null;
                case 67973692:
                    if (name2.equals("Float")) {
                        return PrimitiveType.FLOAT;
                    }
                    return null;
                case 79860828:
                    if (name2.equals("Short")) {
                        return PrimitiveType.SHORT;
                    }
                    return null;
                case 1729365000:
                    if (name2.equals("Boolean")) {
                        return PrimitiveType.BOOLEAN;
                    }
                    return null;
                case 2052876273:
                    if (name2.equals("Double")) {
                        return PrimitiveType.DOUBLE;
                    }
                    return null;
                default:
                    return null;
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @JvmStatic
        public final PrimitiveType getByShortArrayName(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            switch (name2.hashCode()) {
                case -901856463:
                    if (name2.equals("BooleanArray")) {
                        return PrimitiveType.BOOLEAN;
                    }
                    return null;
                case -763279523:
                    if (name2.equals("ShortArray")) {
                        return PrimitiveType.SHORT;
                    }
                    return null;
                case -755911549:
                    if (name2.equals("CharArray")) {
                        return PrimitiveType.CHAR;
                    }
                    return null;
                case -74930671:
                    if (name2.equals("ByteArray")) {
                        return PrimitiveType.BYTE;
                    }
                    return null;
                case 22374632:
                    if (name2.equals("DoubleArray")) {
                        return PrimitiveType.DOUBLE;
                    }
                    return null;
                case 601811914:
                    if (name2.equals("IntArray")) {
                        return PrimitiveType.INT;
                    }
                    return null;
                case 948852093:
                    if (name2.equals("FloatArray")) {
                        return PrimitiveType.FLOAT;
                    }
                    return null;
                case 2104330525:
                    if (name2.equals("LongArray")) {
                        return PrimitiveType.LONG;
                    }
                    return null;
                default:
                    return null;
            }
        }
    }
}
