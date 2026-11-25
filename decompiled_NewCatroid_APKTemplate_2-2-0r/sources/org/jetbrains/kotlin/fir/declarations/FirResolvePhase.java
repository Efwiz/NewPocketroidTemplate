package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.Constants;
import org.mozilla.javascript.ES6Iterator;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'COMPANION_GENERATION' uses external variables
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
/* JADX INFO: compiled from: FirResolvePhase.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0018\b\u0086\u0081\u0002\u0018\u0000 \u001a2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001aB\u0011\b\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007j\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "", "noProcessor", "", "(Ljava/lang/String;IZ)V", ES6Iterator.NEXT_METHOD, "getNext", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getNoProcessor", "()Z", "previous", "getPrevious", "RAW_FIR", "IMPORTS", "COMPILER_REQUIRED_ANNOTATIONS", "COMPANION_GENERATION", "SUPER_TYPES", "SEALED_CLASS_INHERITORS", "TYPES", "STATUS", "EXPECT_ACTUAL_MATCHING", "ARGUMENTS_OF_ANNOTATIONS", "CONTRACTS", "IMPLICIT_TYPES_BODY_RESOLVE", "ANNOTATIONS_ARGUMENTS_MAPPING", "BODY_RESOLVE", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvePhase {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ FirResolvePhase[] $VALUES;
    private static final FirResolvePhase ANALYZED_DEPENDENCIES;
    public static final FirResolvePhase ANNOTATIONS_ARGUMENTS_MAPPING;
    public static final FirResolvePhase ARGUMENTS_OF_ANNOTATIONS;
    public static final FirResolvePhase BODY_RESOLVE;
    public static final FirResolvePhase COMPANION_GENERATION;
    public static final FirResolvePhase CONTRACTS;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final FirResolvePhase DECLARATIONS;
    public static final FirResolvePhase EXPECT_ACTUAL_MATCHING;
    public static final FirResolvePhase IMPLICIT_TYPES_BODY_RESOLVE;
    public static final FirResolvePhase SEALED_CLASS_INHERITORS;
    public static final FirResolvePhase STATUS;
    public static final FirResolvePhase SUPER_TYPES;
    public static final FirResolvePhase TYPES;
    private final boolean noProcessor;
    public static final FirResolvePhase RAW_FIR = new FirResolvePhase("RAW_FIR", 0, true);
    public static final FirResolvePhase IMPORTS = new FirResolvePhase("IMPORTS", 1, false, 1, null);
    public static final FirResolvePhase COMPILER_REQUIRED_ANNOTATIONS = new FirResolvePhase("COMPILER_REQUIRED_ANNOTATIONS", 2, false, 1, null);

    private static final /* synthetic */ FirResolvePhase[] $values() {
        return new FirResolvePhase[]{RAW_FIR, IMPORTS, COMPILER_REQUIRED_ANNOTATIONS, COMPANION_GENERATION, SUPER_TYPES, SEALED_CLASS_INHERITORS, TYPES, STATUS, EXPECT_ACTUAL_MATCHING, ARGUMENTS_OF_ANNOTATIONS, CONTRACTS, IMPLICIT_TYPES_BODY_RESOLVE, ANNOTATIONS_ARGUMENTS_MAPPING, BODY_RESOLVE};
    }

    public static EnumEntries<FirResolvePhase> getEntries() {
        return $ENTRIES;
    }

    public static FirResolvePhase valueOf(String str) {
        return (FirResolvePhase) Enum.valueOf(FirResolvePhase.class, str);
    }

    public static FirResolvePhase[] values() {
        return (FirResolvePhase[]) $VALUES.clone();
    }

    private FirResolvePhase(String str, int i, boolean z) {
        super(str, i);
        this.noProcessor = z;
    }

    /* synthetic */ FirResolvePhase(String str, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, (i2 & 1) != 0 ? false : z);
    }

    public final boolean getNoProcessor() {
        return this.noProcessor;
    }

    static {
        int i = 1;
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z = false;
        COMPANION_GENERATION = new FirResolvePhase("COMPANION_GENERATION", 3, z, i, defaultConstructorMarker);
        int i2 = 1;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        boolean z2 = false;
        SUPER_TYPES = new FirResolvePhase("SUPER_TYPES", 4, z2, i2, defaultConstructorMarker2);
        SEALED_CLASS_INHERITORS = new FirResolvePhase("SEALED_CLASS_INHERITORS", 5, z, i, defaultConstructorMarker);
        TYPES = new FirResolvePhase("TYPES", 6, z2, i2, defaultConstructorMarker2);
        FirResolvePhase firResolvePhase = new FirResolvePhase("STATUS", 7, z, i, defaultConstructorMarker);
        STATUS = firResolvePhase;
        EXPECT_ACTUAL_MATCHING = new FirResolvePhase("EXPECT_ACTUAL_MATCHING", 8, z2, i2, defaultConstructorMarker2);
        int i3 = 1;
        DefaultConstructorMarker defaultConstructorMarker3 = null;
        boolean z3 = false;
        ARGUMENTS_OF_ANNOTATIONS = new FirResolvePhase("ARGUMENTS_OF_ANNOTATIONS", 9, z3, i3, defaultConstructorMarker3);
        int i4 = 1;
        DefaultConstructorMarker defaultConstructorMarker4 = null;
        boolean z4 = false;
        CONTRACTS = new FirResolvePhase("CONTRACTS", 10, z4, i4, defaultConstructorMarker4);
        IMPLICIT_TYPES_BODY_RESOLVE = new FirResolvePhase("IMPLICIT_TYPES_BODY_RESOLVE", 11, z3, i3, defaultConstructorMarker3);
        ANNOTATIONS_ARGUMENTS_MAPPING = new FirResolvePhase("ANNOTATIONS_ARGUMENTS_MAPPING", 12, z4, i4, defaultConstructorMarker4);
        FirResolvePhase firResolvePhase2 = new FirResolvePhase("BODY_RESOLVE", 13, z3, i3, defaultConstructorMarker3);
        BODY_RESOLVE = firResolvePhase2;
        FirResolvePhase[] firResolvePhaseArr$values = $values();
        $VALUES = firResolvePhaseArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(firResolvePhaseArr$values);
        INSTANCE = new Companion(null);
        DECLARATIONS = firResolvePhase;
        ANALYZED_DEPENDENCIES = firResolvePhase2;
    }

    public final FirResolvePhase getNext() {
        return values()[ordinal() + 1];
    }

    public final FirResolvePhase getPrevious() {
        return values()[ordinal() - 1];
    }

    /* JADX INFO: compiled from: FirResolvePhase.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase$Companion;", "", "()V", "ANALYZED_DEPENDENCIES", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getANALYZED_DEPENDENCIES", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "DECLARATIONS", "getDECLARATIONS", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirResolvePhase getDECLARATIONS() {
            return FirResolvePhase.DECLARATIONS;
        }

        public final FirResolvePhase getANALYZED_DEPENDENCIES() {
            return FirResolvePhase.ANALYZED_DEPENDENCIES;
        }
    }
}
