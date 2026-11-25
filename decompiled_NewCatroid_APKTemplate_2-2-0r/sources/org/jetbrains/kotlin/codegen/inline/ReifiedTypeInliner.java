package org.jetbrains.kotlin.codegen.inline;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.jvm.lower.VarargLoweringKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;

/* JADX INFO: compiled from: ReifiedTypeInliner.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 8*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u000389:B;\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J5\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\rH\u0002¢\u0006\u0002\u0010\u001dJ-\u0010\u001e\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u001bH\u0002¢\u0006\u0002\u0010\u001fJ\u0018\u0010 \u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00162\u0006\u0010!\u001a\u00020\u001bH\u0002J\u0018\u0010\"\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00162\u0006\u0010!\u001a\u00020\u001bH\u0002J \u0010#\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00162\u0006\u0010!\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u0011H\u0002J%\u0010%\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010&J\u001a\u0010'\u001a\u0004\u0018\u00010(2\u0006\u0010\u0013\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J-\u0010)\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00028\u00002\u0006\u0010!\u001a\u00020\u001bH\u0002¢\u0006\u0002\u0010\u001fJ%\u0010*\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010&J\u000e\u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020-J-\u0010.\u001a\u00020\r2\u0006\u0010/\u001a\u00020\u00162\u0006\u0010$\u001a\u00020\u00112\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\r01H\u0082\bJ\u0014\u00102\u001a\u00020\u0002*\u00020\u00022\u0006\u00103\u001a\u00020\u0011H\u0002J\u0019\u00104\u001a\u00028\u0000*\u00028\u00002\u0006\u00105\u001a\u000206H\u0002¢\u0006\u0002\u00107J\u0014\u00104\u001a\u00020\u001b*\u00020\u001b2\u0006\u00105\u001a\u000206H\u0002R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner;", "KT", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "", "parametersMapping", "Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;", "intrinsicsSupport", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$IntrinsicsSupport;", "typeSystem", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "unifiedNullChecks", "", "(Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$IntrinsicsSupport;Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Z)V", "hasReifiedParameters", "maxStackSize", "", "isPluginNext", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "processAs", "Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;", "instructions", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", ModuleXmlParser.TYPE, "asmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "safe", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/InsnList;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/org/objectweb/asm/Type;Z)Z", "processIs", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/InsnList;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/org/objectweb/asm/Type;)Z", "processJavaClass", "parameter", "processNewArray", "processNextTypeInsn", "expectedNextOpcode", "processPlugin", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/InsnList;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Z", "processReifyMarker", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "processSpecialEnumFunction", "processTypeOf", "reifyInstructions", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "rewriteNextTypeInsn", "marker", "rewrite", "Lkotlin/Function1;", VarargLoweringKt.ARRAY_OF_NAME, "arrayDepth", "reify", "argument", "Lorg/jetbrains/kotlin/codegen/inline/ReificationArgument;", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/codegen/inline/ReificationArgument;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Companion", "IntrinsicsSupport", "OperationKind", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReifiedTypeInliner<KT extends KotlinTypeMarker> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String NEED_CLASS_REIFICATION_MARKER_METHOD_NAME = "needClassReification";
    public static final String REIFIED_OPERATION_MARKER_METHOD_NAME = "reifiedOperationMarker";
    public static final String pluginIntrinsicsMarkerMethod = "voidMagicApiCall";
    public static final String pluginIntrinsicsMarkerOwner = "kotlin/jvm/internal/MagicApiIntrinsics";
    public static final String pluginIntrinsicsMarkerSignature = "(Ljava/lang/Object;)V";
    private final boolean hasReifiedParameters;
    private final IntrinsicsSupport<KT> intrinsicsSupport;
    private final LanguageVersionSettings languageVersionSettings;
    private int maxStackSize;
    private final TypeParameterMappings<KT> parametersMapping;
    private final TypeSystemCommonBackendContext typeSystem;
    private final boolean unifiedNullChecks;

    /* JADX INFO: compiled from: ReifiedTypeInliner.kt */
    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\b\b\u0001\u0010\u0001*\u00020\u00022\u00020\u0003J\u0017\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H&J\u0015\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u0014J\u001d\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0019H&J\b\u0010\u001a\u001a\u00020\rH&J-\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\n\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010 J\u0015\u0010!\u001a\u00020\"2\u0006\u0010\n\u001a\u00028\u0001H&¢\u0006\u0002\u0010#R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006$À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$IntrinsicsSupport;", "KT", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "generateExternalEntriesForEnumTypeIfNeeded", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", ModuleXmlParser.TYPE, "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "generateTypeParameterContainer", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "isMutableCollectionType", "", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Z", "putClassInstance", "(Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)V", "reportNonReifiedTypeParameterWithRecursiveBoundUnsupported", "typeParameterName", "Lorg/jetbrains/kotlin/name/Name;", "reportSuspendTypeUnsupported", "rewritePluginDefinedOperationMarker", "reifiedInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "instructions", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "(Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/InsnList;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Z", "toKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/kotlin/types/KotlinType;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface IntrinsicsSupport<KT extends KotlinTypeMarker> {
        FieldInsnNode generateExternalEntriesForEnumTypeIfNeeded(KT type);

        void generateTypeParameterContainer(InstructionAdapter v, TypeParameterMarker typeParameter);

        GenerationState getState();

        boolean isMutableCollectionType(KT type);

        void putClassInstance(InstructionAdapter v, KT type);

        void reportNonReifiedTypeParameterWithRecursiveBoundUnsupported(Name typeParameterName);

        void reportSuspendTypeUnsupported();

        default boolean rewritePluginDefinedOperationMarker(InstructionAdapter v, AbstractInsnNode reifiedInsn, InsnList instructions, KT type) {
            Intrinsics.checkNotNullParameter(v, "v");
            Intrinsics.checkNotNullParameter(reifiedInsn, "reifiedInsn");
            Intrinsics.checkNotNullParameter(instructions, "instructions");
            Intrinsics.checkNotNullParameter(type, "type");
            return false;
        }

        KotlinType toKotlinType(KT type);
    }

    /* JADX INFO: compiled from: ReifiedTypeInliner.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[OperationKind.values().length];
            try {
                iArr[OperationKind.NEW_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[OperationKind.AS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[OperationKind.SAFE_AS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[OperationKind.IS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[OperationKind.JAVA_CLASS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[OperationKind.ENUM_REIFIED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[OperationKind.TYPE_OF.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @JvmStatic
    public static final boolean isNeedClassReificationMarker(AbstractInsnNode abstractInsnNode) {
        return INSTANCE.isNeedClassReificationMarker(abstractInsnNode);
    }

    @JvmStatic
    public static final void putNeedClassReificationMarker(MethodVisitor methodVisitor) {
        INSTANCE.putNeedClassReificationMarker(methodVisitor);
    }

    @JvmStatic
    public static final void putReifiedOperationMarker(OperationKind operationKind, ReificationArgument reificationArgument, InstructionAdapter instructionAdapter) {
        INSTANCE.putReifiedOperationMarker(operationKind, reificationArgument, instructionAdapter);
    }

    public ReifiedTypeInliner(TypeParameterMappings<KT> typeParameterMappings, IntrinsicsSupport<KT> intrinsicsSupport, TypeSystemCommonBackendContext typeSystem, LanguageVersionSettings languageVersionSettings, boolean z) {
        Intrinsics.checkNotNullParameter(intrinsicsSupport, "intrinsicsSupport");
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.parametersMapping = typeParameterMappings;
        this.intrinsicsSupport = intrinsicsSupport;
        this.typeSystem = typeSystem;
        this.languageVersionSettings = languageVersionSettings;
        this.unifiedNullChecks = z;
        this.hasReifiedParameters = typeParameterMappings != null ? typeParameterMappings.hasReifiedParameters() : false;
    }

    /* JADX INFO: compiled from: ReifiedTypeInliner.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$OperationKind;", "", "(Ljava/lang/String;I)V", "id", "", "getId", "()I", "NEW_ARRAY", "AS", "SAFE_AS", "IS", "JAVA_CLASS", "ENUM_REIFIED", "TYPE_OF", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum OperationKind {
        NEW_ARRAY,
        AS,
        SAFE_AS,
        IS,
        JAVA_CLASS,
        ENUM_REIFIED,
        TYPE_OF;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<OperationKind> getEntries() {
            return $ENTRIES;
        }

        public final int getId() {
            return ordinal();
        }
    }

    /* JADX INFO: compiled from: ReifiedTypeInliner.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ$\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J \u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u001aH\u0007J.\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$Companion;", "", "()V", "NEED_CLASS_REIFICATION_MARKER_METHOD_NAME", "", "REIFIED_OPERATION_MARKER_METHOD_NAME", "pluginIntrinsicsMarkerMethod", "pluginIntrinsicsMarkerOwner", "pluginIntrinsicsMarkerSignature", "isNeedClassReificationMarker", "", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isOperationReifiedMarker", "isReifiedMarker", "namePredicate", "Lkotlin/Function1;", "putNeedClassReificationMarker", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "putReifiedOperationMarker", "operationKind", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$OperationKind;", "argument", "Lorg/jetbrains/kotlin/codegen/inline/ReificationArgument;", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "putReifiedOperationMarkerIfNeeded", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "isNullable", "typeSystem", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isOperationReifiedMarker(AbstractInsnNode insn) {
            Intrinsics.checkNotNullParameter(insn, "insn");
            return isReifiedMarker(insn, new Function1<String, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner$Companion$isOperationReifiedMarker$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(Intrinsics.areEqual(it, ReifiedTypeInliner.REIFIED_OPERATION_MARKER_METHOD_NAME));
                }
            });
        }

        private final boolean isReifiedMarker(AbstractInsnNode insn, Function1<? super String, Boolean> namePredicate) {
            if (insn.getOpcode() != 184 || !(insn instanceof MethodInsnNode)) {
                return false;
            }
            MethodInsnNode methodInsnNode = (MethodInsnNode) insn;
            if (!Intrinsics.areEqual(methodInsnNode.owner, "kotlin/jvm/internal/Intrinsics")) {
                return false;
            }
            String name2 = methodInsnNode.f1015name;
            Intrinsics.checkNotNullExpressionValue(name2, "name");
            return namePredicate.invoke(name2).booleanValue();
        }

        @JvmStatic
        public final boolean isNeedClassReificationMarker(AbstractInsnNode insn) {
            Intrinsics.checkNotNullParameter(insn, "insn");
            return isReifiedMarker(insn, new Function1<String, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner$Companion$isNeedClassReificationMarker$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(String s) {
                    Intrinsics.checkNotNullParameter(s, "s");
                    return Boolean.valueOf(Intrinsics.areEqual(s, ReifiedTypeInliner.NEED_CLASS_REIFICATION_MARKER_METHOD_NAME));
                }
            });
        }

        @JvmStatic
        public final void putNeedClassReificationMarker(MethodVisitor v) {
            Intrinsics.checkNotNullParameter(v, "v");
            v.visitMethodInsn(184, "kotlin/jvm/internal/Intrinsics", ReifiedTypeInliner.NEED_CLASS_REIFICATION_MARKER_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), false);
        }

        @JvmStatic
        public final void putReifiedOperationMarker(OperationKind operationKind, ReificationArgument argument, InstructionAdapter v) {
            Intrinsics.checkNotNullParameter(operationKind, "operationKind");
            Intrinsics.checkNotNullParameter(argument, "argument");
            Intrinsics.checkNotNullParameter(v, "v");
            v.iconst(operationKind.getId());
            v.visitLdcInsn(argument.asString());
            v.invokestatic("kotlin/jvm/internal/Intrinsics", ReifiedTypeInliner.REIFIED_OPERATION_MARKER_METHOD_NAME, Type.getMethodDescriptor(Type.VOID_TYPE, Type.INT_TYPE, AsmTypes.JAVA_STRING_TYPE), false);
        }

        public final void putReifiedOperationMarkerIfNeeded(TypeParameterMarker typeParameter, boolean isNullable, OperationKind operationKind, InstructionAdapter v, TypeSystemCommonBackendContext typeSystem) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            Intrinsics.checkNotNullParameter(operationKind, "operationKind");
            Intrinsics.checkNotNullParameter(v, "v");
            Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
            if (typeSystem.isReified(typeParameter)) {
                String strAsString = typeSystem.getName(typeParameter).asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                ReifiedTypeInliner.INSTANCE.putReifiedOperationMarker(operationKind, new ReificationArgument(strAsString, isNullable, 0), v);
            }
        }
    }

    public final ReifiedTypeParametersUsages reifyInstructions(MethodNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        if (!this.hasReifiedParameters) {
            return new ReifiedTypeParametersUsages();
        }
        InsnList insnList = node.instructions;
        this.maxStackSize = 0;
        ReifiedTypeParametersUsages reifiedTypeParametersUsages = new ReifiedTypeParametersUsages();
        AbstractInsnNode[] array = insnList.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        for (AbstractInsnNode abstractInsnNode : array) {
            Companion companion = INSTANCE;
            Intrinsics.checkNotNull(abstractInsnNode);
            if (companion.isOperationReifiedMarker(abstractInsnNode)) {
                Intrinsics.checkNotNull(insnList);
                ReifiedTypeParametersUsages reifiedTypeParametersUsagesProcessReifyMarker = processReifyMarker((MethodInsnNode) abstractInsnNode, insnList);
                if (reifiedTypeParametersUsagesProcessReifyMarker != null) {
                    reifiedTypeParametersUsages.mergeAll(reifiedTypeParametersUsagesProcessReifyMarker);
                }
            }
        }
        node.maxStack += this.maxStackSize;
        return reifiedTypeParametersUsages;
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00a5  */
    private final ReifiedTypeParametersUsages processReifyMarker(MethodInsnNode insn, InsnList instructions) {
        ReificationArgument reificationArgument;
        TypeParameterMappings<KT> typeParameterMappings;
        TypeParameterMapping<KT> typeParameterMapping;
        boolean zProcessNewArray;
        boolean z;
        OperationKind operationKind = ReifiedTypeInlinerKt.getOperationKind(insn);
        if (operationKind == null || (reificationArgument = ReifiedTypeInlinerKt.getReificationArgument(insn)) == null || (typeParameterMappings = this.parametersMapping) == null || (typeParameterMapping = typeParameterMappings.get(reificationArgument.getParameterName())) == null) {
            return null;
        }
        Type typeReify = reify(typeParameterMapping.getAsmType(), reificationArgument);
        KotlinTypeMarker kotlinTypeMarkerReify = reify(typeParameterMapping.getType(), reificationArgument);
        if (typeParameterMapping.getReificationArgument() == null || operationKind == OperationKind.TYPE_OF) {
            MethodInsnNode methodInsnNode = insn;
            if (isPluginNext(methodInsnNode) && processPlugin(insn, instructions, kotlinTypeMarkerReify)) {
                z = true;
            } else {
                switch (WhenMappings.$EnumSwitchMapping$0[operationKind.ordinal()]) {
                    case 1:
                        zProcessNewArray = processNewArray(insn, typeReify);
                        break;
                    case 2:
                        zProcessNewArray = processAs(insn, instructions, kotlinTypeMarkerReify, typeReify, false);
                        break;
                    case 3:
                        zProcessNewArray = processAs(insn, instructions, kotlinTypeMarkerReify, typeReify, true);
                        break;
                    case 4:
                        zProcessNewArray = processIs(insn, instructions, kotlinTypeMarkerReify, typeReify);
                        break;
                    case 5:
                        zProcessNewArray = processJavaClass(insn, typeReify);
                        break;
                    case 6:
                        zProcessNewArray = processSpecialEnumFunction(insn, instructions, kotlinTypeMarkerReify, typeReify);
                        break;
                    case 7:
                        zProcessNewArray = processTypeOf(insn, instructions, kotlinTypeMarkerReify);
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                if (zProcessNewArray) {
                    z = true;
                } else {
                    z = false;
                }
            }
            if (z) {
                AbstractInsnNode previous = insn.getPrevious().getPrevious();
                Intrinsics.checkNotNull(previous);
                instructions.remove(previous);
                AbstractInsnNode previous2 = insn.getPrevious();
                Intrinsics.checkNotNull(previous2);
                instructions.remove(previous2);
                instructions.remove(methodInsnNode);
            }
        } else {
            ReificationArgument reificationArgumentCombine = reificationArgument.combine(typeParameterMapping.getReificationArgument());
            AbstractInsnNode previous3 = insn.getPrevious();
            Intrinsics.checkNotNull(previous3);
            instructions.set(previous3, new LdcInsnNode(reificationArgumentCombine.asString()));
        }
        return typeParameterMapping.getReifiedTypeParametersUsages();
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private final KT reify(KT kt, ReificationArgument reificationArgument) {
        TypeSystemCommonBackendContext typeSystemCommonBackendContext = this.typeSystem;
        KT kt2 = (KT) arrayOf(kt, reificationArgument.getArrayDepth());
        if (reificationArgument.getNullable()) {
            kt2 = (KT) typeSystemCommonBackendContext.makeNullable(kt2);
        }
        Intrinsics.checkNotNull(kt2, "null cannot be cast to non-null type KT of org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner");
        return kt2;
    }

    private final Type reify(Type type, ReificationArgument reificationArgument) {
        Type type2 = Type.getType(StringsKt.repeat("[", reificationArgument.getArrayDepth()) + type);
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        return type2;
    }

    private final KotlinTypeMarker arrayOf(KotlinTypeMarker kotlinTypeMarker, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            kotlinTypeMarker = this.typeSystem.arrayType(kotlinTypeMarker);
        }
        return kotlinTypeMarker;
    }

    private final boolean processNewArray(MethodInsnNode insn, Type parameter) {
        return processNextTypeInsn(insn, parameter, 189);
    }

    private final boolean processPlugin(MethodInsnNode insn, InsnList instructions, KT type) {
        AbstractInsnNode next = insn.getNext();
        if (next == null) {
            return false;
        }
        MethodNode methodNode = new MethodNode(589824, "fake", "()V", null, null);
        MaxStackFrameSizeAndLocalsCalculator maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc = InlineCodegenUtilsKt.wrapWithMaxLocalCalc(methodNode);
        if (!this.intrinsicsSupport.rewritePluginDefinedOperationMarker(new InstructionAdapter(maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc), next, instructions, type)) {
            return false;
        }
        maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitInsn(177);
        maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitMaxs(-1, -1);
        InsnList insnList = methodNode.instructions;
        insnList.remove(insnList.getLast());
        instructions.insert(insn, methodNode.instructions);
        this.maxStackSize = Math.max(this.maxStackSize, methodNode.maxStack);
        return true;
    }

    private final boolean isPluginNext(AbstractInsnNode insn) {
        AbstractInsnNode next;
        AbstractInsnNode next2 = insn.getNext();
        AbstractInsnNode next3 = (next2 == null || (next = next2.getNext()) == null) ? null : next.getNext();
        if (next3 == null || !(next3 instanceof MethodInsnNode)) {
            return false;
        }
        MethodInsnNode methodInsnNode = (MethodInsnNode) next3;
        return methodInsnNode.getOpcode() == 184 && Intrinsics.areEqual(methodInsnNode.owner, pluginIntrinsicsMarkerOwner) && Intrinsics.areEqual(methodInsnNode.f1015name, pluginIntrinsicsMarkerMethod) && Intrinsics.areEqual(methodInsnNode.desc, pluginIntrinsicsMarkerSignature) && (methodInsnNode.getPrevious() instanceof LdcInsnNode);
    }

    private final boolean processNextTypeInsn(MethodInsnNode insn, Type parameter, int expectedNextOpcode) {
        AbstractInsnNode next = insn.getNext();
        if (next == null || next.getOpcode() != expectedNextOpcode) {
            return false;
        }
        AbstractInsnNode next2 = insn.getNext();
        Intrinsics.checkNotNull(next2, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TypeInsnNode");
        ((TypeInsnNode) next2).desc = parameter.getInternalName();
        return true;
    }

    private final boolean processJavaClass(MethodInsnNode insn, Type parameter) {
        AbstractInsnNode next = insn.getNext();
        if (!(next instanceof LdcInsnNode)) {
            return false;
        }
        ((LdcInsnNode) next).cst = parameter;
        return true;
    }

    private final boolean processSpecialEnumFunction(MethodInsnNode insn, InsnList instructions, KT type, Type parameter) {
        AbstractInsnNode next;
        AbstractInsnNode next2 = insn.getNext();
        if (next2 == null || (next = next2.getNext()) == null) {
            return false;
        }
        if (next2.getOpcode() == 1 && next.getOpcode() == 25) {
            AbstractInsnNode next3 = next.getNext();
            if (next3 == null) {
                return false;
            }
            if (next3 instanceof MethodInsnNode) {
                MethodInsnNode methodInsnNode = (MethodInsnNode) next3;
                if (Intrinsics.areEqual(methodInsnNode.f1015name, "valueOf")) {
                    instructions.remove(next2);
                    methodInsnNode.owner = parameter.getInternalName();
                    methodInsnNode.desc = InlineIntrinsicsKt.getSpecialEnumFunDescriptor(parameter, true);
                    return true;
                }
            }
        } else {
            if (next2.getOpcode() == 3 && next.getOpcode() == 189) {
                instructions.remove(next2);
                instructions.remove(next);
                instructions.insert(insn, new MethodInsnNode(184, parameter.getInternalName(), CompoundEntry.NAME_values, InlineIntrinsicsKt.getSpecialEnumFunDescriptor(parameter, false), false));
                return true;
            }
            if (next2.getOpcode() == 1 && next.getOpcode() == 192) {
                instructions.remove(next2);
                instructions.remove(next);
                FieldInsnNode fieldInsnNodeGenerateExternalEntriesForEnumTypeIfNeeded = this.intrinsicsSupport.generateExternalEntriesForEnumTypeIfNeeded(type);
                if (fieldInsnNodeGenerateExternalEntriesForEnumTypeIfNeeded != null) {
                    instructions.insert(insn, fieldInsnNodeGenerateExternalEntriesForEnumTypeIfNeeded);
                } else {
                    instructions.insert(insn, new MethodInsnNode(184, parameter.getInternalName(), "getEntries", Type.getMethodDescriptor(AsmTypes.ENUM_ENTRIES, new Type[0]), false));
                }
                return true;
            }
        }
        return false;
    }

    private final boolean processAs(MethodInsnNode insn, InsnList instructions, KT type, Type asmType, boolean safe) {
        AbstractInsnNode next = insn.getNext();
        if (next != null) {
            Intrinsics.checkNotNull(next);
            if (next.getOpcode() != 192 || !(next instanceof TypeInsnNode)) {
                return false;
            }
            MethodNode methodNode = new MethodNode(589824);
            CodegenUtilKt.generateAsCast(new InstructionAdapter(methodNode), this.intrinsicsSupport.toKotlinType(type), asmType, safe, this.unifiedNullChecks);
            instructions.insert(insn, methodNode.instructions);
            if (Intrinsics.areEqual(((TypeInsnNode) next).desc, AsmTypes.OBJECT_TYPE.getInternalName())) {
                instructions.remove(next);
            }
            this.maxStackSize = Math.max(this.maxStackSize, 4);
            return true;
        }
        return false;
    }

    private final boolean processIs(MethodInsnNode insn, InsnList instructions, KT type, Type asmType) {
        AbstractInsnNode next = insn.getNext();
        if (next != null) {
            Intrinsics.checkNotNull(next);
            if (next.getOpcode() != 193 || !(next instanceof TypeInsnNode)) {
                return false;
            }
            MethodNode methodNode = new MethodNode(589824);
            CodegenUtilKt.generateIsCheck(new InstructionAdapter(methodNode), this.intrinsicsSupport.toKotlinType(type), asmType);
            instructions.insert(insn, methodNode.instructions);
            instructions.remove(next);
            this.maxStackSize = Math.max(this.maxStackSize, 2);
            return true;
        }
        return false;
    }

    private final boolean processTypeOf(MethodInsnNode insn, InsnList instructions, KT type) {
        AbstractInsnNode next = insn.getNext();
        if (next == null) {
            return false;
        }
        Intrinsics.checkNotNull(next);
        if (next.getOpcode() != 1) {
            return false;
        }
        MethodNode methodNode = new MethodNode(589824, "fake", "()V", null, null);
        MaxStackFrameSizeAndLocalsCalculator maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc = InlineCodegenUtilsKt.wrapWithMaxLocalCalc(methodNode);
        TypeOfKt.generateTypeOf(this.typeSystem, new InstructionAdapter(maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc), type, this.intrinsicsSupport);
        maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitInsn(177);
        maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitMaxs(-1, -1);
        InsnList insnList = methodNode.instructions;
        insnList.remove(insnList.getLast());
        instructions.insert(insn, methodNode.instructions);
        instructions.remove(next);
        this.maxStackSize = Math.max(this.maxStackSize, methodNode.maxStack);
        return true;
    }
}
