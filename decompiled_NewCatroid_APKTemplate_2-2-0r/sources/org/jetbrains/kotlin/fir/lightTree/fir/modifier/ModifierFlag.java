package org.jetbrains.kotlin.fir.lightTree.fir.modifier;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v1 org.jetbrains.kotlin.fir.lightTree.fir.modifier.ModifierFlag, still in use, count: 1, list:
  (r0v1 org.jetbrains.kotlin.fir.lightTree.fir.modifier.ModifierFlag) from 0x0250: INVOKE 
  (wrap org.jetbrains.kotlin.lexer.KtModifierKeywordToken:0x024e: SGET  A[WRAPPED] org.jetbrains.kotlin.lexer.KtTokens.ENUM_KEYWORD org.jetbrains.kotlin.lexer.KtModifierKeywordToken)
  (r0v1 org.jetbrains.kotlin.fir.lightTree.fir.modifier.ModifierFlag)
 STATIC call: kotlin.TuplesKt.to(java.lang.Object, java.lang.Object):kotlin.Pair A[MD:<A, B>:(A, B):kotlin.Pair<A, B> (m), WRAPPED]
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
/* JADX INFO: compiled from: ModifierFlag.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\t\n\u0002\b+\b\u0086\u0081\u0002\u0018\u0000 -2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001-B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/ModifierFlag;", "", "value", "", "(Ljava/lang/String;IJ)V", "getValue", "()J", "NONE", "CLASS_ENUM", "CLASS_ANNOTATION", "CLASS_DATA", "CLASS_INLINE", "CLASS_INNER", "CLASS_COMPANION", "CLASS_FUN", "MEMBER_OVERRIDE", "MEMBER_LATEINIT", "VISIBILITY_PUBLIC", "VISIBILITY_PRIVATE", "VISIBILITY_INTERNAL", "VISIBILITY_PROTECTED", "VISIBILITY_UNKNOWN", "FUNCTION_TAILREC", "FUNCTION_OPERATOR", "FUNCTION_INFIX", "FUNCTION_INLINE", "FUNCTION_EXTERNAL", "FUNCTION_SUSPEND", "PROPERTY_CONST", "INHERITANCE_ABSTRACT", "INHERITANCE_FINAL", "INHERITANCE_OPEN", "INHERITANCE_SEALED", "PARAMETER_VARARG", "PARAMETER_NOINLINE", "PARAMETER_CROSSINLINE", "PARAMETER_CONST", "PLATFORM_EXPECT", "PLATFORM_ACTUAL", "PLATFORM_HEADER", "PLATFORM_IMPL", "VARIANCE_IN", "VARIANCE_OUT", "VARIANCE_INVARIANT", "REIFICATION_REIFIED", "Companion", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModifierFlag {
    NONE(0),
    CLASS_ENUM(1),
    CLASS_ANNOTATION(2),
    CLASS_DATA(4),
    CLASS_INLINE(8),
    CLASS_INNER(16),
    CLASS_COMPANION(32),
    CLASS_FUN(64),
    MEMBER_OVERRIDE(128),
    MEMBER_LATEINIT(256),
    VISIBILITY_PUBLIC(512),
    VISIBILITY_PRIVATE(1024),
    VISIBILITY_INTERNAL(2048),
    VISIBILITY_PROTECTED(4096),
    VISIBILITY_UNKNOWN(8192),
    FUNCTION_TAILREC(16384),
    FUNCTION_OPERATOR(32768),
    FUNCTION_INFIX(65536),
    FUNCTION_INLINE(131072),
    FUNCTION_EXTERNAL(262144),
    FUNCTION_SUSPEND(524288),
    PROPERTY_CONST(1048576),
    INHERITANCE_ABSTRACT(2097152),
    INHERITANCE_FINAL(4194304),
    INHERITANCE_OPEN(8388608),
    INHERITANCE_SEALED(TagBits.HasUnresolvedTypeVariables),
    PARAMETER_VARARG(TagBits.HasUnresolvedSuperclass),
    PARAMETER_NOINLINE(TagBits.HasUnresolvedSuperinterfaces),
    PARAMETER_CROSSINLINE(TagBits.HasUnresolvedEnclosingType),
    PARAMETER_CONST(TagBits.HasUnresolvedMemberTypes),
    PLATFORM_EXPECT(TagBits.HasTypeVariable),
    PLATFORM_ACTUAL(1073741824),
    PLATFORM_HEADER(2147483648L),
    PLATFORM_IMPL(4294967296L),
    VARIANCE_IN(8589934592L),
    VARIANCE_OUT(17179869184L),
    VARIANCE_INVARIANT(34359738368L),
    REIFICATION_REIFIED(68719476736L);

    private final long value;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ModifierFlag[] Values = values();
    private static final Map<IElementType, ModifierFlag> ElementTypeToModifierFlagMap = MapsKt.mutableMapOf(TuplesKt.to(KtTokens.ENUM_KEYWORD, new ModifierFlag(1)), TuplesKt.to(KtTokens.ANNOTATION_KEYWORD, new ModifierFlag(2)), TuplesKt.to(KtTokens.DATA_KEYWORD, new ModifierFlag(4)), TuplesKt.to(KtTokens.INNER_KEYWORD, new ModifierFlag(16)), TuplesKt.to(KtTokens.COMPANION_KEYWORD, new ModifierFlag(32)), TuplesKt.to(KtTokens.FUN_KEYWORD, new ModifierFlag(64)), TuplesKt.to(KtTokens.OVERRIDE_KEYWORD, new ModifierFlag(128)), TuplesKt.to(KtTokens.LATEINIT_KEYWORD, new ModifierFlag(256)), TuplesKt.to(KtTokens.PUBLIC_KEYWORD, new ModifierFlag(512)), TuplesKt.to(KtTokens.PRIVATE_KEYWORD, new ModifierFlag(1024)), TuplesKt.to(KtTokens.INTERNAL_KEYWORD, new ModifierFlag(2048)), TuplesKt.to(KtTokens.PROTECTED_KEYWORD, new ModifierFlag(4096)), TuplesKt.to(KtTokens.TAILREC_KEYWORD, new ModifierFlag(16384)), TuplesKt.to(KtTokens.OPERATOR_KEYWORD, new ModifierFlag(32768)), TuplesKt.to(KtTokens.INFIX_KEYWORD, new ModifierFlag(65536)), TuplesKt.to(KtTokens.EXTERNAL_KEYWORD, new ModifierFlag(262144)), TuplesKt.to(KtTokens.SUSPEND_KEYWORD, new ModifierFlag(524288)), TuplesKt.to(KtTokens.ABSTRACT_KEYWORD, new ModifierFlag(2097152)), TuplesKt.to(KtTokens.FINAL_KEYWORD, new ModifierFlag(4194304)), TuplesKt.to(KtTokens.OPEN_KEYWORD, new ModifierFlag(8388608)), TuplesKt.to(KtTokens.SEALED_KEYWORD, new ModifierFlag(TagBits.HasUnresolvedTypeVariables)), TuplesKt.to(KtTokens.VARARG_KEYWORD, new ModifierFlag(TagBits.HasUnresolvedSuperclass)), TuplesKt.to(KtTokens.NOINLINE_KEYWORD, new ModifierFlag(TagBits.HasUnresolvedSuperinterfaces)), TuplesKt.to(KtTokens.CROSSINLINE_KEYWORD, new ModifierFlag(TagBits.HasUnresolvedEnclosingType)), TuplesKt.to(KtTokens.EXPECT_KEYWORD, new ModifierFlag(TagBits.HasTypeVariable)), TuplesKt.to(KtTokens.ACTUAL_KEYWORD, new ModifierFlag(1073741824)), TuplesKt.to(KtTokens.HEADER_KEYWORD, new ModifierFlag(2147483648L)), TuplesKt.to(KtTokens.IMPL_KEYWORD, new ModifierFlag(4294967296L)), TuplesKt.to(KtTokens.IN_KEYWORD, new ModifierFlag(8589934592L)), TuplesKt.to(KtTokens.OUT_KEYWORD, new ModifierFlag(17179869184L)), TuplesKt.to(KtTokens.REIFIED_KEYWORD, new ModifierFlag(68719476736L)));

    public static EnumEntries<ModifierFlag> getEntries() {
        return $ENTRIES;
    }

    public static ModifierFlag valueOf(String str) {
        return (ModifierFlag) Enum.valueOf(ModifierFlag.class, str);
    }

    public static ModifierFlag[] values() {
        return (ModifierFlag[]) $VALUES.clone();
    }

    private ModifierFlag(long j) {
        super(str, i);
        this.value = j;
    }

    public final long getValue() {
        return this.value;
    }

    static {
    }

    /* JADX INFO: compiled from: ModifierFlag.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\n¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/ModifierFlag$Companion;", "", "()V", "ElementTypeToModifierFlagMap", "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/ModifierFlag;", "getElementTypeToModifierFlagMap", "()Ljava/util/Map;", "Values", "", "getValues", "()[Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/ModifierFlag;", "[Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/ModifierFlag;", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ModifierFlag[] getValues() {
            return ModifierFlag.Values;
        }

        public final Map<IElementType, ModifierFlag> getElementTypeToModifierFlagMap() {
            return ModifierFlag.ElementTypeToModifierFlagMap;
        }
    }
}
