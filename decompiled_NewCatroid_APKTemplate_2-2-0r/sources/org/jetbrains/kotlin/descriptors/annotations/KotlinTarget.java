package org.jetbrains.kotlin.descriptors.annotations;

import com.google.android.gms.cast.MediaTrack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'PROPERTY' uses external variables
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
/* JADX INFO: compiled from: KotlinTarget.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b1\b\u0086\u0081\u0002\u0018\u0000 52\b\u0012\u0004\u0012\u00020\u00000\u0001:\u00015B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "", MediaTrack.ROLE_DESCRIPTION, "", "isDefault", "", "(Ljava/lang/String;ILjava/lang/String;Z)V", "getDescription", "()Ljava/lang/String;", "()Z", "CLASS", "ANNOTATION_CLASS", "TYPE_PARAMETER", "PROPERTY", "FIELD", "LOCAL_VARIABLE", "VALUE_PARAMETER", "CONSTRUCTOR", "FUNCTION", "PROPERTY_GETTER", "PROPERTY_SETTER", "TYPE", "EXPRESSION", "FILE", "TYPEALIAS", "TYPE_PROJECTION", "STAR_PROJECTION", "PROPERTY_PARAMETER", "CLASS_ONLY", Namer.CLASS_KIND_OBJECT, "STANDALONE_OBJECT", "COMPANION_OBJECT", Namer.CLASS_KIND_INTERFACE, "ENUM_CLASS", "ENUM_ENTRY", "LOCAL_CLASS", "LOCAL_FUNCTION", "MEMBER_FUNCTION", "TOP_LEVEL_FUNCTION", "MEMBER_PROPERTY", "MEMBER_PROPERTY_WITH_BACKING_FIELD", "MEMBER_PROPERTY_WITH_DELEGATE", "MEMBER_PROPERTY_WITHOUT_FIELD_OR_DELEGATE", "TOP_LEVEL_PROPERTY", "TOP_LEVEL_PROPERTY_WITH_BACKING_FIELD", "TOP_LEVEL_PROPERTY_WITH_DELEGATE", "TOP_LEVEL_PROPERTY_WITHOUT_FIELD_OR_DELEGATE", "BACKING_FIELD", "INITIALIZER", "DESTRUCTURING_DECLARATION", "LAMBDA_EXPRESSION", "ANONYMOUS_FUNCTION", "OBJECT_LITERAL", "Companion", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinTarget {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ KotlinTarget[] $VALUES;
    private static final Set<KotlinTarget> ALL_TARGET_SET;
    private static final List<KotlinTarget> ANNOTATION_CLASS_LIST;
    private static final List<KotlinTarget> CLASS_LIST;
    private static final List<KotlinTarget> COMPANION_OBJECT_LIST;
    public static final KotlinTarget CONSTRUCTOR;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final Set<KotlinTarget> DEFAULT_TARGET_SET;
    private static final List<KotlinTarget> ENUM_ENTRY_LIST;
    private static final List<KotlinTarget> ENUM_LIST;
    public static final KotlinTarget FIELD;
    private static final List<KotlinTarget> FILE_LIST;
    public static final KotlinTarget FUNCTION;
    private static final List<KotlinTarget> FUNCTION_LIST;
    private static final List<KotlinTarget> INTERFACE_LIST;
    private static final List<KotlinTarget> LOCAL_CLASS_LIST;
    public static final KotlinTarget LOCAL_VARIABLE;
    private static final List<KotlinTarget> OBJECT_LIST;
    public static final KotlinTarget PROPERTY;
    public static final KotlinTarget PROPERTY_GETTER;
    private static final List<KotlinTarget> PROPERTY_GETTER_LIST;
    public static final KotlinTarget PROPERTY_SETTER;
    private static final List<KotlinTarget> PROPERTY_SETTER_LIST;
    private static final Map<AnnotationUseSiteTarget, KotlinTarget> USE_SITE_MAPPING;
    public static final KotlinTarget VALUE_PARAMETER;
    private static final HashMap<String, KotlinTarget> map;
    private final String description;
    private final boolean isDefault;
    public static final KotlinTarget CLASS = new KotlinTarget("CLASS", 0, "class", false, 2, null);
    public static final KotlinTarget ANNOTATION_CLASS = new KotlinTarget("ANNOTATION_CLASS", 1, "annotation class", false, 2, null);
    public static final KotlinTarget TYPE_PARAMETER = new KotlinTarget("TYPE_PARAMETER", 2, "type parameter", false);
    public static final KotlinTarget TYPE = new KotlinTarget("TYPE", 11, "type usage", false);
    public static final KotlinTarget EXPRESSION = new KotlinTarget("EXPRESSION", 12, "expression", false);
    public static final KotlinTarget FILE = new KotlinTarget("FILE", 13, "file", false);
    public static final KotlinTarget TYPEALIAS = new KotlinTarget("TYPEALIAS", 14, "typealias", false);
    public static final KotlinTarget TYPE_PROJECTION = new KotlinTarget("TYPE_PROJECTION", 15, "type projection", false);
    public static final KotlinTarget STAR_PROJECTION = new KotlinTarget("STAR_PROJECTION", 16, "star projection", false);
    public static final KotlinTarget PROPERTY_PARAMETER = new KotlinTarget("PROPERTY_PARAMETER", 17, "property constructor parameter", false);
    public static final KotlinTarget CLASS_ONLY = new KotlinTarget("CLASS_ONLY", 18, "class", false);
    public static final KotlinTarget OBJECT = new KotlinTarget(Namer.CLASS_KIND_OBJECT, 19, "object", false);
    public static final KotlinTarget STANDALONE_OBJECT = new KotlinTarget("STANDALONE_OBJECT", 20, "standalone object", false);
    public static final KotlinTarget COMPANION_OBJECT = new KotlinTarget("COMPANION_OBJECT", 21, "companion object", false);
    public static final KotlinTarget INTERFACE = new KotlinTarget(Namer.CLASS_KIND_INTERFACE, 22, PsiKeyword.INTERFACE, false);
    public static final KotlinTarget ENUM_CLASS = new KotlinTarget("ENUM_CLASS", 23, "enum class", false);
    public static final KotlinTarget ENUM_ENTRY = new KotlinTarget("ENUM_ENTRY", 24, "enum entry", false);
    public static final KotlinTarget LOCAL_CLASS = new KotlinTarget("LOCAL_CLASS", 25, "local class", false);
    public static final KotlinTarget LOCAL_FUNCTION = new KotlinTarget("LOCAL_FUNCTION", 26, "local function", false);
    public static final KotlinTarget MEMBER_FUNCTION = new KotlinTarget("MEMBER_FUNCTION", 27, "member function", false);
    public static final KotlinTarget TOP_LEVEL_FUNCTION = new KotlinTarget("TOP_LEVEL_FUNCTION", 28, "top level function", false);
    public static final KotlinTarget MEMBER_PROPERTY = new KotlinTarget("MEMBER_PROPERTY", 29, "member property", false);
    public static final KotlinTarget MEMBER_PROPERTY_WITH_BACKING_FIELD = new KotlinTarget("MEMBER_PROPERTY_WITH_BACKING_FIELD", 30, "member property with backing field", false);
    public static final KotlinTarget MEMBER_PROPERTY_WITH_DELEGATE = new KotlinTarget("MEMBER_PROPERTY_WITH_DELEGATE", 31, "member property with delegate", false);
    public static final KotlinTarget MEMBER_PROPERTY_WITHOUT_FIELD_OR_DELEGATE = new KotlinTarget("MEMBER_PROPERTY_WITHOUT_FIELD_OR_DELEGATE", 32, "member property without backing field or delegate", false);
    public static final KotlinTarget TOP_LEVEL_PROPERTY = new KotlinTarget("TOP_LEVEL_PROPERTY", 33, "top level property", false);
    public static final KotlinTarget TOP_LEVEL_PROPERTY_WITH_BACKING_FIELD = new KotlinTarget("TOP_LEVEL_PROPERTY_WITH_BACKING_FIELD", 34, "top level property with backing field", false);
    public static final KotlinTarget TOP_LEVEL_PROPERTY_WITH_DELEGATE = new KotlinTarget("TOP_LEVEL_PROPERTY_WITH_DELEGATE", 35, "top level property with delegate", false);
    public static final KotlinTarget TOP_LEVEL_PROPERTY_WITHOUT_FIELD_OR_DELEGATE = new KotlinTarget("TOP_LEVEL_PROPERTY_WITHOUT_FIELD_OR_DELEGATE", 36, "top level property without backing field or delegate", false);
    public static final KotlinTarget BACKING_FIELD = new KotlinTarget("BACKING_FIELD", 37, "backing field", false, 2, null);
    public static final KotlinTarget INITIALIZER = new KotlinTarget("INITIALIZER", 38, "initializer", false);
    public static final KotlinTarget DESTRUCTURING_DECLARATION = new KotlinTarget("DESTRUCTURING_DECLARATION", 39, "destructuring declaration", false);
    public static final KotlinTarget LAMBDA_EXPRESSION = new KotlinTarget("LAMBDA_EXPRESSION", 40, "lambda expression", false);
    public static final KotlinTarget ANONYMOUS_FUNCTION = new KotlinTarget("ANONYMOUS_FUNCTION", 41, "anonymous function", false);
    public static final KotlinTarget OBJECT_LITERAL = new KotlinTarget("OBJECT_LITERAL", 42, "object literal", false);

    private static final /* synthetic */ KotlinTarget[] $values() {
        return new KotlinTarget[]{CLASS, ANNOTATION_CLASS, TYPE_PARAMETER, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER, CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, TYPE, EXPRESSION, FILE, TYPEALIAS, TYPE_PROJECTION, STAR_PROJECTION, PROPERTY_PARAMETER, CLASS_ONLY, OBJECT, STANDALONE_OBJECT, COMPANION_OBJECT, INTERFACE, ENUM_CLASS, ENUM_ENTRY, LOCAL_CLASS, LOCAL_FUNCTION, MEMBER_FUNCTION, TOP_LEVEL_FUNCTION, MEMBER_PROPERTY, MEMBER_PROPERTY_WITH_BACKING_FIELD, MEMBER_PROPERTY_WITH_DELEGATE, MEMBER_PROPERTY_WITHOUT_FIELD_OR_DELEGATE, TOP_LEVEL_PROPERTY, TOP_LEVEL_PROPERTY_WITH_BACKING_FIELD, TOP_LEVEL_PROPERTY_WITH_DELEGATE, TOP_LEVEL_PROPERTY_WITHOUT_FIELD_OR_DELEGATE, BACKING_FIELD, INITIALIZER, DESTRUCTURING_DECLARATION, LAMBDA_EXPRESSION, ANONYMOUS_FUNCTION, OBJECT_LITERAL};
    }

    public static EnumEntries<KotlinTarget> getEntries() {
        return $ENTRIES;
    }

    public static KotlinTarget valueOf(String str) {
        return (KotlinTarget) Enum.valueOf(KotlinTarget.class, str);
    }

    public static KotlinTarget[] values() {
        return (KotlinTarget[]) $VALUES.clone();
    }

    private KotlinTarget(String str, int i, String str2, boolean z) {
        super(str, i);
        this.description = str2;
        this.isDefault = z;
    }

    /* synthetic */ KotlinTarget(String str, int i, String str2, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, str2, (i2 & 2) != 0 ? true : z);
    }

    public final String getDescription() {
        return this.description;
    }

    /* JADX INFO: renamed from: isDefault, reason: from getter */
    public final boolean getIsDefault() {
        return this.isDefault;
    }

    static {
        int i = 2;
        DefaultConstructorMarker defaultConstructorMarker = null;
        boolean z = false;
        PROPERTY = new KotlinTarget("PROPERTY", 3, "property", z, i, defaultConstructorMarker);
        int i2 = 2;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        boolean z2 = false;
        FIELD = new KotlinTarget("FIELD", 4, "field", z2, i2, defaultConstructorMarker2);
        LOCAL_VARIABLE = new KotlinTarget("LOCAL_VARIABLE", 5, "local variable", z, i, defaultConstructorMarker);
        VALUE_PARAMETER = new KotlinTarget("VALUE_PARAMETER", 6, "value parameter", z2, i2, defaultConstructorMarker2);
        CONSTRUCTOR = new KotlinTarget("CONSTRUCTOR", 7, JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, z, i, defaultConstructorMarker);
        FUNCTION = new KotlinTarget("FUNCTION", 8, "function", z2, i2, defaultConstructorMarker2);
        PROPERTY_GETTER = new KotlinTarget("PROPERTY_GETTER", 9, "getter", z, i, defaultConstructorMarker);
        PROPERTY_SETTER = new KotlinTarget("PROPERTY_SETTER", 10, "setter", z2, i2, defaultConstructorMarker2);
        KotlinTarget[] kotlinTargetArr$values = $values();
        $VALUES = kotlinTargetArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(kotlinTargetArr$values);
        INSTANCE = new Companion(null);
        map = new HashMap<>();
        for (KotlinTarget kotlinTarget : values()) {
            map.put(kotlinTarget.name(), kotlinTarget);
        }
        KotlinTarget[] kotlinTargetArrValues = values();
        ArrayList arrayList = new ArrayList();
        for (KotlinTarget kotlinTarget2 : kotlinTargetArrValues) {
            if (kotlinTarget2.isDefault) {
                arrayList.add(kotlinTarget2);
            }
        }
        DEFAULT_TARGET_SET = CollectionsKt.toSet(arrayList);
        ALL_TARGET_SET = ArraysKt.toSet(values());
        KotlinTarget kotlinTarget3 = ANNOTATION_CLASS;
        KotlinTarget kotlinTarget4 = CLASS;
        ANNOTATION_CLASS_LIST = CollectionsKt.listOf((Object[]) new KotlinTarget[]{kotlinTarget3, kotlinTarget4});
        LOCAL_CLASS_LIST = CollectionsKt.listOf((Object[]) new KotlinTarget[]{LOCAL_CLASS, kotlinTarget4});
        CLASS_LIST = CollectionsKt.listOf((Object[]) new KotlinTarget[]{CLASS_ONLY, kotlinTarget4});
        KotlinTarget kotlinTarget5 = COMPANION_OBJECT;
        KotlinTarget kotlinTarget6 = OBJECT;
        COMPANION_OBJECT_LIST = CollectionsKt.listOf((Object[]) new KotlinTarget[]{kotlinTarget5, kotlinTarget6, kotlinTarget4});
        OBJECT_LIST = CollectionsKt.listOf((Object[]) new KotlinTarget[]{STANDALONE_OBJECT, kotlinTarget6, kotlinTarget4});
        INTERFACE_LIST = CollectionsKt.listOf((Object[]) new KotlinTarget[]{INTERFACE, kotlinTarget4});
        ENUM_LIST = CollectionsKt.listOf((Object[]) new KotlinTarget[]{ENUM_CLASS, kotlinTarget4});
        KotlinTarget kotlinTarget7 = ENUM_ENTRY;
        KotlinTarget kotlinTarget8 = PROPERTY;
        KotlinTarget kotlinTarget9 = FIELD;
        ENUM_ENTRY_LIST = CollectionsKt.listOf((Object[]) new KotlinTarget[]{kotlinTarget7, kotlinTarget8, kotlinTarget9});
        KotlinTarget kotlinTarget10 = PROPERTY_SETTER;
        PROPERTY_SETTER_LIST = CollectionsKt.listOf(kotlinTarget10);
        KotlinTarget kotlinTarget11 = PROPERTY_GETTER;
        PROPERTY_GETTER_LIST = CollectionsKt.listOf(kotlinTarget11);
        FUNCTION_LIST = CollectionsKt.listOf(FUNCTION);
        KotlinTarget kotlinTarget12 = FILE;
        FILE_LIST = CollectionsKt.listOf(kotlinTarget12);
        AnnotationUseSiteTarget annotationUseSiteTarget = AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER;
        KotlinTarget kotlinTarget13 = VALUE_PARAMETER;
        USE_SITE_MAPPING = MapsKt.mapOf(TuplesKt.to(annotationUseSiteTarget, kotlinTarget13), TuplesKt.to(AnnotationUseSiteTarget.FIELD, kotlinTarget9), TuplesKt.to(AnnotationUseSiteTarget.PROPERTY, kotlinTarget8), TuplesKt.to(AnnotationUseSiteTarget.FILE, kotlinTarget12), TuplesKt.to(AnnotationUseSiteTarget.PROPERTY_GETTER, kotlinTarget11), TuplesKt.to(AnnotationUseSiteTarget.PROPERTY_SETTER, kotlinTarget10), TuplesKt.to(AnnotationUseSiteTarget.RECEIVER, kotlinTarget13), TuplesKt.to(AnnotationUseSiteTarget.SETTER_PARAMETER, kotlinTarget13), TuplesKt.to(AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD, kotlinTarget9));
    }

    /* JADX INFO: compiled from: KotlinTarget.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u001b\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002002\u0006\u00102\u001a\u000200J\u0010\u00103\u001a\u0004\u0018\u00010\u00052\u0006\u00104\u001a\u00020+R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000bR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000bR\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000bR\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000bR\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000bR\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u000bR\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u000bR\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u000bR\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050\t¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u000bR\u001d\u0010$\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00050%¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u001a\u0010)\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020\u00050*X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget$Companion;", "", "()V", "ALL_TARGET_SET", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "getALL_TARGET_SET", "()Ljava/util/Set;", "ANNOTATION_CLASS_LIST", "", "getANNOTATION_CLASS_LIST", "()Ljava/util/List;", "CLASS_LIST", "getCLASS_LIST", "COMPANION_OBJECT_LIST", "getCOMPANION_OBJECT_LIST", "DEFAULT_TARGET_SET", "getDEFAULT_TARGET_SET", "ENUM_ENTRY_LIST", "getENUM_ENTRY_LIST", "ENUM_LIST", "getENUM_LIST", "FILE_LIST", "getFILE_LIST", "FUNCTION_LIST", "getFUNCTION_LIST", "INTERFACE_LIST", "getINTERFACE_LIST", "LOCAL_CLASS_LIST", "getLOCAL_CLASS_LIST", "OBJECT_LIST", "getOBJECT_LIST", "PROPERTY_GETTER_LIST", "getPROPERTY_GETTER_LIST", "PROPERTY_SETTER_LIST", "getPROPERTY_SETTER_LIST", "USE_SITE_MAPPING", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "getUSE_SITE_MAPPING", "()Ljava/util/Map;", "map", "Ljava/util/HashMap;", "", "classActualTargets", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "isInnerClass", "", "isCompanionObject", "isLocalClass", "valueOrNull", "name", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: KotlinTarget.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ClassKind.values().length];
                try {
                    iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ClassKind.CLASS.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ClassKind.OBJECT.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[ClassKind.INTERFACE.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[ClassKind.ENUM_CLASS.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[ClassKind.ENUM_ENTRY.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinTarget valueOrNull(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return (KotlinTarget) KotlinTarget.map.get(name2);
        }

        public final Set<KotlinTarget> getDEFAULT_TARGET_SET() {
            return KotlinTarget.DEFAULT_TARGET_SET;
        }

        public final Set<KotlinTarget> getALL_TARGET_SET() {
            return KotlinTarget.ALL_TARGET_SET;
        }

        public final List<KotlinTarget> getANNOTATION_CLASS_LIST() {
            return KotlinTarget.ANNOTATION_CLASS_LIST;
        }

        public final List<KotlinTarget> getLOCAL_CLASS_LIST() {
            return KotlinTarget.LOCAL_CLASS_LIST;
        }

        public final List<KotlinTarget> getCLASS_LIST() {
            return KotlinTarget.CLASS_LIST;
        }

        public final List<KotlinTarget> getCOMPANION_OBJECT_LIST() {
            return KotlinTarget.COMPANION_OBJECT_LIST;
        }

        public final List<KotlinTarget> getOBJECT_LIST() {
            return KotlinTarget.OBJECT_LIST;
        }

        public final List<KotlinTarget> getINTERFACE_LIST() {
            return KotlinTarget.INTERFACE_LIST;
        }

        public final List<KotlinTarget> getENUM_LIST() {
            return KotlinTarget.ENUM_LIST;
        }

        public final List<KotlinTarget> getENUM_ENTRY_LIST() {
            return KotlinTarget.ENUM_ENTRY_LIST;
        }

        public final List<KotlinTarget> getPROPERTY_SETTER_LIST() {
            return KotlinTarget.PROPERTY_SETTER_LIST;
        }

        public final List<KotlinTarget> getPROPERTY_GETTER_LIST() {
            return KotlinTarget.PROPERTY_GETTER_LIST;
        }

        public final List<KotlinTarget> getFUNCTION_LIST() {
            return KotlinTarget.FUNCTION_LIST;
        }

        public final List<KotlinTarget> getFILE_LIST() {
            return KotlinTarget.FILE_LIST;
        }

        public final List<KotlinTarget> classActualTargets(ClassKind kind, boolean isInnerClass, boolean isCompanionObject, boolean isLocalClass) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            switch (WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                case 1:
                    return getANNOTATION_CLASS_LIST();
                case 2:
                    if (!isInnerClass && isLocalClass) {
                        return getLOCAL_CLASS_LIST();
                    }
                    return getCLASS_LIST();
                case 3:
                    if (isCompanionObject) {
                        return getCOMPANION_OBJECT_LIST();
                    }
                    return getOBJECT_LIST();
                case 4:
                    return getINTERFACE_LIST();
                case 5:
                    if (isLocalClass) {
                        return getLOCAL_CLASS_LIST();
                    }
                    return getENUM_LIST();
                case 6:
                    return getENUM_ENTRY_LIST();
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        public final Map<AnnotationUseSiteTarget, KotlinTarget> getUSE_SITE_MAPPING() {
            return KotlinTarget.USE_SITE_MAPPING;
        }
    }
}
