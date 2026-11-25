package org.jetbrains.kotlin.load.java;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.kotlin.SignatureBuildingComponents;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: compiled from: SpecialGenericSignatures.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\b\u0016\u0018\u0000 \u00032\u00020\u0001:\u0003\u0003\u0004\u0005B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures;", "", "()V", "Companion", "SpecialSignatureInfo", "TypeSafeBarrierDescription", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SpecialGenericSignatures {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<String> ERASED_COLLECTION_PARAMETER_NAMES;
    private static final List<Companion.NameAndSignature> ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
    private static final List<String> ERASED_COLLECTION_PARAMETER_SIGNATURES;
    private static final Set<Name> ERASED_VALUE_PARAMETERS_SHORT_NAMES;
    private static final Set<String> ERASED_VALUE_PARAMETERS_SIGNATURES;
    private static final Map<Companion.NameAndSignature, TypeSafeBarrierDescription> GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP;
    private static final Map<Name, Name> JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP;
    private static final Set<String> JVM_SIGNATURES_FOR_RENAMED_BUILT_INS;
    private static final Map<Companion.NameAndSignature, Name> NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP;
    private static final List<Name> ORIGINAL_SHORT_NAMES;
    private static final Companion.NameAndSignature REMOVE_AT_NAME_AND_SIGNATURE;
    private static final Map<String, TypeSafeBarrierDescription> SIGNATURE_TO_DEFAULT_VALUES_MAP;
    private static final Map<String, Name> SIGNATURE_TO_JVM_REPRESENTATION_NAME;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: SpecialGenericSignatures.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$TypeSafeBarrierDescription;", "", "defaultValue", "", "(Ljava/lang/String;ILjava/lang/Object;)V", "getDefaultValue", "()Ljava/lang/Object;", "checkParameter", "", "index", "", "NULL", "INDEX", "FALSE", "MAP_GET_OR_DEFAULT", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TypeSafeBarrierDescription {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ TypeSafeBarrierDescription[] $VALUES;
        private final Object defaultValue;
        public static final TypeSafeBarrierDescription NULL = new TypeSafeBarrierDescription("NULL", 0, null);
        public static final TypeSafeBarrierDescription INDEX = new TypeSafeBarrierDescription("INDEX", 1, -1);
        public static final TypeSafeBarrierDescription FALSE = new TypeSafeBarrierDescription("FALSE", 2, false);
        public static final TypeSafeBarrierDescription MAP_GET_OR_DEFAULT = new MAP_GET_OR_DEFAULT("MAP_GET_OR_DEFAULT", 3);

        private static final /* synthetic */ TypeSafeBarrierDescription[] $values() {
            return new TypeSafeBarrierDescription[]{NULL, INDEX, FALSE, MAP_GET_OR_DEFAULT};
        }

        public /* synthetic */ TypeSafeBarrierDescription(String str, int i, Object obj, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i, obj);
        }

        public static EnumEntries<TypeSafeBarrierDescription> getEntries() {
            return $ENTRIES;
        }

        public static TypeSafeBarrierDescription valueOf(String str) {
            return (TypeSafeBarrierDescription) Enum.valueOf(TypeSafeBarrierDescription.class, str);
        }

        public static TypeSafeBarrierDescription[] values() {
            return (TypeSafeBarrierDescription[]) $VALUES.clone();
        }

        public boolean checkParameter(int index) {
            return true;
        }

        private TypeSafeBarrierDescription(String str, int i, Object obj) {
            super(str, i);
            this.defaultValue = obj;
        }

        public final Object getDefaultValue() {
            return this.defaultValue;
        }

        static {
            TypeSafeBarrierDescription[] typeSafeBarrierDescriptionArr$values = $values();
            $VALUES = typeSafeBarrierDescriptionArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(typeSafeBarrierDescriptionArr$values);
        }

        /* JADX INFO: compiled from: SpecialGenericSignatures.kt */
        @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\bÆ\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$TypeSafeBarrierDescription$MAP_GET_OR_DEFAULT;", "Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$TypeSafeBarrierDescription;", "checkParameter", "", "index", "", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        static final class MAP_GET_OR_DEFAULT extends TypeSafeBarrierDescription {
            @Override // org.jetbrains.kotlin.load.java.SpecialGenericSignatures.TypeSafeBarrierDescription
            public boolean checkParameter(int index) {
                return index != 1;
            }

            /* JADX WARN: Illegal instructions before constructor call */
            MAP_GET_OR_DEFAULT(String str, int i) {
                DefaultConstructorMarker defaultConstructorMarker = null;
                super(str, i, defaultConstructorMarker, defaultConstructorMarker);
            }
        }
    }

    /* JADX INFO: compiled from: SpecialGenericSignatures.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$SpecialSignatureInfo;", "", "valueParametersSignature", "", "isObjectReplacedWithTypeParameter", "", "(Ljava/lang/String;ILjava/lang/String;Z)V", "()Z", "getValueParametersSignature", "()Ljava/lang/String;", "ONE_COLLECTION_PARAMETER", "OBJECT_PARAMETER_NON_GENERIC", "OBJECT_PARAMETER_GENERIC", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum SpecialSignatureInfo {
        ONE_COLLECTION_PARAMETER("Ljava/util/Collection<+Ljava/lang/Object;>;", false),
        OBJECT_PARAMETER_NON_GENERIC(null, true),
        OBJECT_PARAMETER_GENERIC(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, true);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final boolean isObjectReplacedWithTypeParameter;
        private final String valueParametersSignature;

        public static EnumEntries<SpecialSignatureInfo> getEntries() {
            return $ENTRIES;
        }

        SpecialSignatureInfo(String str, boolean z) {
            this.valueParametersSignature = str;
            this.isObjectReplacedWithTypeParameter = z;
        }

        public final String getValueParametersSignature() {
            return this.valueParametersSignature;
        }

        /* JADX INFO: renamed from: isObjectReplacedWithTypeParameter, reason: from getter */
        public final boolean getIsObjectReplacedWithTypeParameter() {
            return this.isObjectReplacedWithTypeParameter;
        }
    }

    /* JADX INFO: compiled from: SpecialGenericSignatures.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001:\u00013B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010+\u001a\u0004\u0018\u00010\u000e2\u0006\u0010,\u001a\u00020\u000eJ\u000e\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u0005J$\u00100\u001a\u00020\t*\u00020\u00052\u0006\u0010,\u001a\u00020\u00052\u0006\u00101\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u0005H\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\r¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\r¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010R\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0007R\u0011\u0010\u001e\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001d\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00150\u0014¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018R\u001d\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\u0014¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0018R\u0015\u0010%\u001a\u00020&*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0015\u0010)\u001a\u00020&*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b*\u0010(¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$Companion;", "", "()V", "ERASED_COLLECTION_PARAMETER_NAMES", "", "", "getERASED_COLLECTION_PARAMETER_NAMES", "()Ljava/util/List;", "ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES", "Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$Companion$NameAndSignature;", "ERASED_COLLECTION_PARAMETER_SIGNATURES", "getERASED_COLLECTION_PARAMETER_SIGNATURES", "ERASED_VALUE_PARAMETERS_SHORT_NAMES", "", "Lorg/jetbrains/kotlin/name/Name;", "getERASED_VALUE_PARAMETERS_SHORT_NAMES", "()Ljava/util/Set;", "ERASED_VALUE_PARAMETERS_SIGNATURES", "getERASED_VALUE_PARAMETERS_SIGNATURES", "GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP", "", "Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$TypeSafeBarrierDescription;", "JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP", "getJVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP", "()Ljava/util/Map;", "JVM_SIGNATURES_FOR_RENAMED_BUILT_INS", "getJVM_SIGNATURES_FOR_RENAMED_BUILT_INS", "NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP", "ORIGINAL_SHORT_NAMES", "getORIGINAL_SHORT_NAMES", "REMOVE_AT_NAME_AND_SIGNATURE", "getREMOVE_AT_NAME_AND_SIGNATURE", "()Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$Companion$NameAndSignature;", "SIGNATURE_TO_DEFAULT_VALUES_MAP", "getSIGNATURE_TO_DEFAULT_VALUES_MAP", "SIGNATURE_TO_JVM_REPRESENTATION_NAME", "getSIGNATURE_TO_JVM_REPRESENTATION_NAME", "sameAsBuiltinMethodWithErasedValueParameters", "", "getSameAsBuiltinMethodWithErasedValueParameters", "(Lorg/jetbrains/kotlin/name/Name;)Z", "sameAsRenamedInJvmBuiltin", "getSameAsRenamedInJvmBuiltin", "getBuiltinFunctionNamesByJvmName", "name", "getSpecialSignatureInfo", "Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$SpecialSignatureInfo;", "builtinSignature", "method", "parameters", "returnType", "NameAndSignature", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SpecialSignatureInfo getSpecialSignatureInfo(String builtinSignature) {
            Intrinsics.checkNotNullParameter(builtinSignature, "builtinSignature");
            if (getERASED_COLLECTION_PARAMETER_SIGNATURES().contains(builtinSignature)) {
                return SpecialSignatureInfo.ONE_COLLECTION_PARAMETER;
            }
            if (((TypeSafeBarrierDescription) MapsKt.getValue(getSIGNATURE_TO_DEFAULT_VALUES_MAP(), builtinSignature)) == TypeSafeBarrierDescription.NULL) {
                return SpecialSignatureInfo.OBJECT_PARAMETER_GENERIC;
            }
            return SpecialSignatureInfo.OBJECT_PARAMETER_NON_GENERIC;
        }

        /* JADX INFO: compiled from: SpecialGenericSignatures.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\n¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$Companion$NameAndSignature;", "", "classInternalName", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "parameters", "returnType", "(Ljava/lang/String;Lorg/jetbrains/kotlin/name/Name;Ljava/lang/String;Ljava/lang/String;)V", "getClassInternalName", "()Ljava/lang/String;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getParameters", "getReturnType", "signature", "getSignature", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class NameAndSignature {
            private final String classInternalName;
            private final Name name;
            private final String parameters;
            private final String returnType;
            private final String signature;

            public static /* synthetic */ NameAndSignature copy$default(NameAndSignature nameAndSignature, String str, Name name2, String str2, String str3, int i, Object obj) {
                if ((i & 1) != 0) {
                    str = nameAndSignature.classInternalName;
                }
                if ((i & 2) != 0) {
                    name2 = nameAndSignature.name;
                }
                if ((i & 4) != 0) {
                    str2 = nameAndSignature.parameters;
                }
                if ((i & 8) != 0) {
                    str3 = nameAndSignature.returnType;
                }
                return nameAndSignature.copy(str, name2, str2, str3);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final String getClassInternalName() {
                return this.classInternalName;
            }

            /* JADX INFO: renamed from: component2, reason: from getter */
            public final Name getName() {
                return this.name;
            }

            /* JADX INFO: renamed from: component3, reason: from getter */
            public final String getParameters() {
                return this.parameters;
            }

            /* JADX INFO: renamed from: component4, reason: from getter */
            public final String getReturnType() {
                return this.returnType;
            }

            public final NameAndSignature copy(String classInternalName, Name name2, String parameters, String returnType) {
                Intrinsics.checkNotNullParameter(classInternalName, "classInternalName");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(parameters, "parameters");
                Intrinsics.checkNotNullParameter(returnType, "returnType");
                return new NameAndSignature(classInternalName, name2, parameters, returnType);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof NameAndSignature)) {
                    return false;
                }
                NameAndSignature nameAndSignature = (NameAndSignature) other;
                return Intrinsics.areEqual(this.classInternalName, nameAndSignature.classInternalName) && Intrinsics.areEqual(this.name, nameAndSignature.name) && Intrinsics.areEqual(this.parameters, nameAndSignature.parameters) && Intrinsics.areEqual(this.returnType, nameAndSignature.returnType);
            }

            public int hashCode() {
                return (((((this.classInternalName.hashCode() * 31) + this.name.hashCode()) * 31) + this.parameters.hashCode()) * 31) + this.returnType.hashCode();
            }

            public String toString() {
                return "NameAndSignature(classInternalName=" + this.classInternalName + ", name=" + this.name + ", parameters=" + this.parameters + ", returnType=" + this.returnType + ')';
            }

            public NameAndSignature(String classInternalName, Name name2, String parameters, String returnType) {
                Intrinsics.checkNotNullParameter(classInternalName, "classInternalName");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(parameters, "parameters");
                Intrinsics.checkNotNullParameter(returnType, "returnType");
                this.classInternalName = classInternalName;
                this.name = name2;
                this.parameters = parameters;
                this.returnType = returnType;
                this.signature = SignatureBuildingComponents.INSTANCE.signature(classInternalName, name2 + '(' + parameters + ')' + returnType);
            }

            public final String getClassInternalName() {
                return this.classInternalName;
            }

            public final Name getName() {
                return this.name;
            }

            public final String getParameters() {
                return this.parameters;
            }

            public final String getReturnType() {
                return this.returnType;
            }

            public final String getSignature() {
                return this.signature;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final NameAndSignature method(String str, String str2, String str3, String str4) {
            Name nameIdentifier = Name.identifier(str2);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            return new NameAndSignature(str, nameIdentifier, str3, str4);
        }

        public final List<String> getERASED_COLLECTION_PARAMETER_SIGNATURES() {
            return SpecialGenericSignatures.ERASED_COLLECTION_PARAMETER_SIGNATURES;
        }

        public final List<String> getERASED_COLLECTION_PARAMETER_NAMES() {
            return SpecialGenericSignatures.ERASED_COLLECTION_PARAMETER_NAMES;
        }

        public final Map<String, TypeSafeBarrierDescription> getSIGNATURE_TO_DEFAULT_VALUES_MAP() {
            return SpecialGenericSignatures.SIGNATURE_TO_DEFAULT_VALUES_MAP;
        }

        public final Set<Name> getERASED_VALUE_PARAMETERS_SHORT_NAMES() {
            return SpecialGenericSignatures.ERASED_VALUE_PARAMETERS_SHORT_NAMES;
        }

        public final Set<String> getERASED_VALUE_PARAMETERS_SIGNATURES() {
            return SpecialGenericSignatures.ERASED_VALUE_PARAMETERS_SIGNATURES;
        }

        public final NameAndSignature getREMOVE_AT_NAME_AND_SIGNATURE() {
            return SpecialGenericSignatures.REMOVE_AT_NAME_AND_SIGNATURE;
        }

        public final Map<String, Name> getSIGNATURE_TO_JVM_REPRESENTATION_NAME() {
            return SpecialGenericSignatures.SIGNATURE_TO_JVM_REPRESENTATION_NAME;
        }

        public final Set<String> getJVM_SIGNATURES_FOR_RENAMED_BUILT_INS() {
            return SpecialGenericSignatures.JVM_SIGNATURES_FOR_RENAMED_BUILT_INS;
        }

        public final List<Name> getORIGINAL_SHORT_NAMES() {
            return SpecialGenericSignatures.ORIGINAL_SHORT_NAMES;
        }

        public final Map<Name, Name> getJVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP() {
            return SpecialGenericSignatures.JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP;
        }

        public final Name getBuiltinFunctionNamesByJvmName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return getJVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP().get(name2);
        }

        public final boolean getSameAsBuiltinMethodWithErasedValueParameters(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "<this>");
            return getERASED_VALUE_PARAMETERS_SHORT_NAMES().contains(name2);
        }

        public final boolean getSameAsRenamedInJvmBuiltin(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "<this>");
            return getORIGINAL_SHORT_NAMES().contains(name2);
        }
    }

    static {
        Set<String> of = SetsKt.setOf((Object[]) new String[]{"containsAll", "removeAll", "retainAll"});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(of, 10));
        for (String str : of) {
            Companion companion = INSTANCE;
            String desc = JvmPrimitiveType.BOOLEAN.getDesc();
            Intrinsics.checkNotNullExpressionValue(desc, "getDesc(...)");
            arrayList.add(companion.method("java/util/Collection", str, "Ljava/util/Collection;", desc));
        }
        ArrayList arrayList2 = arrayList;
        ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES = arrayList2;
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            arrayList4.add(((Companion.NameAndSignature) it.next()).getSignature());
        }
        ERASED_COLLECTION_PARAMETER_SIGNATURES = arrayList4;
        List<Companion.NameAndSignature> list = ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList5.add(((Companion.NameAndSignature) it2.next()).getName().asString());
        }
        ERASED_COLLECTION_PARAMETER_NAMES = arrayList5;
        SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        Companion companion2 = INSTANCE;
        String strJavaUtil = signatureBuildingComponents.javaUtil("Collection");
        String desc2 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc2, "getDesc(...)");
        String strJavaUtil2 = signatureBuildingComponents.javaUtil("Collection");
        String desc3 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc3, "getDesc(...)");
        String strJavaUtil3 = signatureBuildingComponents.javaUtil("Map");
        String desc4 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc4, "getDesc(...)");
        String strJavaUtil4 = signatureBuildingComponents.javaUtil("Map");
        String desc5 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc5, "getDesc(...)");
        String strJavaUtil5 = signatureBuildingComponents.javaUtil("Map");
        String desc6 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc6, "getDesc(...)");
        String strJavaUtil6 = signatureBuildingComponents.javaUtil("List");
        String desc7 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc7, "getDesc(...)");
        String strJavaUtil7 = signatureBuildingComponents.javaUtil("List");
        String desc8 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc8, "getDesc(...)");
        Map<Companion.NameAndSignature, TypeSafeBarrierDescription> mapMapOf = MapsKt.mapOf(TuplesKt.to(companion2.method(strJavaUtil, "contains", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, desc2), TypeSafeBarrierDescription.FALSE), TuplesKt.to(companion2.method(strJavaUtil2, ProductAction.ACTION_REMOVE, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, desc3), TypeSafeBarrierDescription.FALSE), TuplesKt.to(companion2.method(strJavaUtil3, "containsKey", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, desc4), TypeSafeBarrierDescription.FALSE), TuplesKt.to(companion2.method(strJavaUtil4, "containsValue", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, desc5), TypeSafeBarrierDescription.FALSE), TuplesKt.to(companion2.method(strJavaUtil5, ProductAction.ACTION_REMOVE, "Ljava/lang/Object;Ljava/lang/Object;", desc6), TypeSafeBarrierDescription.FALSE), TuplesKt.to(companion2.method(signatureBuildingComponents.javaUtil("Map"), "getOrDefault", "Ljava/lang/Object;Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR), TypeSafeBarrierDescription.MAP_GET_OR_DEFAULT), TuplesKt.to(companion2.method(signatureBuildingComponents.javaUtil("Map"), "get", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR), TypeSafeBarrierDescription.NULL), TuplesKt.to(companion2.method(signatureBuildingComponents.javaUtil("Map"), ProductAction.ACTION_REMOVE, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR), TypeSafeBarrierDescription.NULL), TuplesKt.to(companion2.method(strJavaUtil6, "indexOf", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, desc7), TypeSafeBarrierDescription.INDEX), TuplesKt.to(companion2.method(strJavaUtil7, "lastIndexOf", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, desc8), TypeSafeBarrierDescription.INDEX));
        GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP = mapMapOf;
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapMapOf.size()));
        Iterator<T> it3 = mapMapOf.entrySet().iterator();
        while (it3.hasNext()) {
            Map.Entry entry = (Map.Entry) it3.next();
            linkedHashMap.put(((Companion.NameAndSignature) entry.getKey()).getSignature(), entry.getValue());
        }
        SIGNATURE_TO_DEFAULT_VALUES_MAP = linkedHashMap;
        Set setPlus = SetsKt.plus((Set) GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP.keySet(), (Iterable) ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES);
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setPlus, 10));
        Iterator it4 = setPlus.iterator();
        while (it4.hasNext()) {
            arrayList6.add(((Companion.NameAndSignature) it4.next()).getName());
        }
        ERASED_VALUE_PARAMETERS_SHORT_NAMES = CollectionsKt.toSet(arrayList6);
        ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setPlus, 10));
        Iterator it5 = setPlus.iterator();
        while (it5.hasNext()) {
            arrayList7.add(((Companion.NameAndSignature) it5.next()).getSignature());
        }
        ERASED_VALUE_PARAMETERS_SIGNATURES = CollectionsKt.toSet(arrayList7);
        Companion companion3 = INSTANCE;
        String desc9 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc9, "getDesc(...)");
        Companion.NameAndSignature nameAndSignatureMethod = companion3.method("java/util/List", "removeAt", desc9, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR);
        REMOVE_AT_NAME_AND_SIGNATURE = nameAndSignatureMethod;
        SignatureBuildingComponents signatureBuildingComponents2 = SignatureBuildingComponents.INSTANCE;
        String strJavaLang = signatureBuildingComponents2.javaLang("Number");
        String desc10 = JvmPrimitiveType.BYTE.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc10, "getDesc(...)");
        String strJavaLang2 = signatureBuildingComponents2.javaLang("Number");
        String desc11 = JvmPrimitiveType.SHORT.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc11, "getDesc(...)");
        String strJavaLang3 = signatureBuildingComponents2.javaLang("Number");
        String desc12 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc12, "getDesc(...)");
        String strJavaLang4 = signatureBuildingComponents2.javaLang("Number");
        String desc13 = JvmPrimitiveType.LONG.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc13, "getDesc(...)");
        String strJavaLang5 = signatureBuildingComponents2.javaLang("Number");
        String desc14 = JvmPrimitiveType.FLOAT.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc14, "getDesc(...)");
        String strJavaLang6 = signatureBuildingComponents2.javaLang("Number");
        String desc15 = JvmPrimitiveType.DOUBLE.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc15, "getDesc(...)");
        String strJavaLang7 = signatureBuildingComponents2.javaLang("CharSequence");
        String desc16 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc16, "getDesc(...)");
        String desc17 = JvmPrimitiveType.CHAR.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc17, "getDesc(...)");
        Map<Companion.NameAndSignature, Name> mapMapOf2 = MapsKt.mapOf(TuplesKt.to(companion3.method(strJavaLang, "toByte", "", desc10), Name.identifier("byteValue")), TuplesKt.to(companion3.method(strJavaLang2, "toShort", "", desc11), Name.identifier("shortValue")), TuplesKt.to(companion3.method(strJavaLang3, "toInt", "", desc12), Name.identifier("intValue")), TuplesKt.to(companion3.method(strJavaLang4, "toLong", "", desc13), Name.identifier("longValue")), TuplesKt.to(companion3.method(strJavaLang5, "toFloat", "", desc14), Name.identifier("floatValue")), TuplesKt.to(companion3.method(strJavaLang6, "toDouble", "", desc15), Name.identifier("doubleValue")), TuplesKt.to(nameAndSignatureMethod, Name.identifier(ProductAction.ACTION_REMOVE)), TuplesKt.to(companion3.method(strJavaLang7, "get", desc16, desc17), Name.identifier("charAt")));
        NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP = mapMapOf2;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(mapMapOf2.size()));
        Iterator<T> it6 = mapMapOf2.entrySet().iterator();
        while (it6.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it6.next();
            linkedHashMap2.put(((Companion.NameAndSignature) entry2.getKey()).getSignature(), entry2.getValue());
        }
        SIGNATURE_TO_JVM_REPRESENTATION_NAME = linkedHashMap2;
        Map<Companion.NameAndSignature, Name> map = NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Map.Entry<Companion.NameAndSignature, Name> entry3 : map.entrySet()) {
            linkedHashSet.add(Companion.NameAndSignature.copy$default(entry3.getKey(), null, entry3.getValue(), null, null, 13, null).getSignature());
        }
        JVM_SIGNATURES_FOR_RENAMED_BUILT_INS = linkedHashSet;
        Set<Companion.NameAndSignature> setKeySet = NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP.keySet();
        ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setKeySet, 10));
        Iterator<T> it7 = setKeySet.iterator();
        while (it7.hasNext()) {
            arrayList8.add(((Companion.NameAndSignature) it7.next()).getName());
        }
        ORIGINAL_SHORT_NAMES = arrayList8;
        Set<Map.Entry<Companion.NameAndSignature, Name>> setEntrySet = NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP.entrySet();
        ArrayList arrayList9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it8 = setEntrySet.iterator();
        while (it8.hasNext()) {
            Map.Entry entry4 = (Map.Entry) it8.next();
            arrayList9.add(new Pair(((Companion.NameAndSignature) entry4.getKey()).getName(), entry4.getValue()));
        }
        ArrayList<Pair> arrayList10 = arrayList9;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList10, 10)), 16));
        for (Pair pair : arrayList10) {
            linkedHashMap3.put((Name) pair.getSecond(), (Name) pair.getFirst());
        }
        JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP = linkedHashMap3;
    }
}
