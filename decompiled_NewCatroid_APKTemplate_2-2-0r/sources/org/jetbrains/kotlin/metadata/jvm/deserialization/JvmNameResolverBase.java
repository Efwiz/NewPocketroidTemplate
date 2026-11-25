package org.jetbrains.kotlin.metadata.jvm.deserialization;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.asm.Advice;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;

/* JADX INFO: compiled from: JvmNameResolverBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B/\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0007H\u0016J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0007H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0007H\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolverBase;", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "strings", "", "", "localNameIndices", "", "", "records", "", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$StringTableTypes$Record;", "([Ljava/lang/String;Ljava/util/Set;Ljava/util/List;)V", "getStrings", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getQualifiedClassName", "index", "getString", "isLocalClassName", "", "Companion", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JvmNameResolverBase implements NameResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<String> PREDEFINED_STRINGS;
    private static final Map<String, Integer> PREDEFINED_STRINGS_MAP;

    /* JADX INFO: renamed from: kotlin, reason: collision with root package name */
    private static final String f923kotlin;
    private final Set<Integer> localNameIndices;
    private final List<JvmProtoBuf.StringTableTypes.Record> records;
    private final String[] strings;

    /* JADX INFO: compiled from: JvmNameResolverBase.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JvmProtoBuf.StringTableTypes.Record.Operation.values().length];
            try {
                iArr[JvmProtoBuf.StringTableTypes.Record.Operation.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JvmProtoBuf.StringTableTypes.Record.Operation.INTERNAL_TO_CLASS_ID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JvmProtoBuf.StringTableTypes.Record.Operation.DESC_TO_CLASS_ID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public JvmNameResolverBase(String[] strings, Set<Integer> localNameIndices, List<JvmProtoBuf.StringTableTypes.Record> records) {
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(localNameIndices, "localNameIndices");
        Intrinsics.checkNotNullParameter(records, "records");
        this.strings = strings;
        this.localNameIndices = localNameIndices;
        this.records = records;
    }

    public final String[] getStrings() {
        return this.strings;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0032  */
    @Override // org.jetbrains.kotlin.metadata.deserialization.NameResolver
    public String getString(int index) {
        String strSubstring;
        JvmProtoBuf.StringTableTypes.Record record = this.records.get(index);
        if (record.hasString()) {
            strSubstring = record.getString();
        } else if (record.hasPredefinedIndex()) {
            List<String> list = PREDEFINED_STRINGS;
            int size = list.size();
            int predefinedIndex = record.getPredefinedIndex();
            if (predefinedIndex >= 0 && predefinedIndex < size) {
                strSubstring = list.get(record.getPredefinedIndex());
            } else {
                strSubstring = this.strings[index];
            }
        } else {
            strSubstring = this.strings[index];
        }
        if (record.getSubstringIndexCount() >= 2) {
            List<Integer> substringIndexList = record.getSubstringIndexList();
            Intrinsics.checkNotNull(substringIndexList);
            Integer num = substringIndexList.get(0);
            Integer num2 = substringIndexList.get(1);
            Intrinsics.checkNotNull(num);
            if (num.intValue() >= 0) {
                int iIntValue = num.intValue();
                Intrinsics.checkNotNull(num2);
                if (iIntValue <= num2.intValue() && num2.intValue() <= strSubstring.length()) {
                    Intrinsics.checkNotNull(strSubstring);
                    strSubstring = strSubstring.substring(num.intValue(), num2.intValue());
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                }
            }
        }
        String strReplace$default = strSubstring;
        if (record.getReplaceCharCount() >= 2) {
            List<Integer> replaceCharList = record.getReplaceCharList();
            Intrinsics.checkNotNull(replaceCharList);
            Integer num3 = replaceCharList.get(0);
            Integer num4 = replaceCharList.get(1);
            Intrinsics.checkNotNull(strReplace$default);
            strReplace$default = StringsKt.replace$default(strReplace$default, (char) num3.intValue(), (char) num4.intValue(), false, 4, (Object) null);
        }
        String strReplace$default2 = strReplace$default;
        JvmProtoBuf.StringTableTypes.Record.Operation operation = record.getOperation();
        if (operation == null) {
            operation = JvmProtoBuf.StringTableTypes.Record.Operation.NONE;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[operation.ordinal()];
        if (i == 2) {
            Intrinsics.checkNotNull(strReplace$default2);
            strReplace$default2 = StringsKt.replace$default(strReplace$default2, '$', '.', false, 4, (Object) null);
        } else if (i == 3) {
            if (strReplace$default2.length() >= 2) {
                Intrinsics.checkNotNull(strReplace$default2);
                strReplace$default2 = strReplace$default2.substring(1, strReplace$default2.length() - 1);
                Intrinsics.checkNotNullExpressionValue(strReplace$default2, "substring(...)");
            }
            String str = strReplace$default2;
            Intrinsics.checkNotNull(str);
            strReplace$default2 = StringsKt.replace$default(str, '$', '.', false, 4, (Object) null);
        }
        Intrinsics.checkNotNull(strReplace$default2);
        return strReplace$default2;
    }

    @Override // org.jetbrains.kotlin.metadata.deserialization.NameResolver
    public String getQualifiedClassName(int index) {
        return getString(index);
    }

    @Override // org.jetbrains.kotlin.metadata.deserialization.NameResolver
    public boolean isLocalClassName(int index) {
        return this.localNameIndices.contains(Integer.valueOf(index));
    }

    /* JADX INFO: compiled from: JvmNameResolverBase.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\u0005¢\u0006\u0002\u0010\u000eR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolverBase$Companion;", "", "()V", "PREDEFINED_STRINGS", "", "", "getPREDEFINED_STRINGS", "()Ljava/util/List;", "PREDEFINED_STRINGS_MAP", "", "", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "getPredefinedStringIndex", "string", "(Ljava/lang/String;)Ljava/lang/Integer;", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<String> getPREDEFINED_STRINGS() {
            return JvmNameResolverBase.PREDEFINED_STRINGS;
        }

        public final Integer getPredefinedStringIndex(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            return (Integer) JvmNameResolverBase.PREDEFINED_STRINGS_MAP.get(string);
        }
    }

    static {
        String strJoinToString$default = CollectionsKt.joinToString$default(CollectionsKt.listOf((Object[]) new Character[]{'k', 'o', Character.valueOf(Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL), 'l', 'i', 'n'}), "", null, null, 0, null, null, 62, null);
        f923kotlin = strJoinToString$default;
        List<String> listListOf = CollectionsKt.listOf((Object[]) new String[]{strJoinToString$default + "/Any", strJoinToString$default + "/Nothing", strJoinToString$default + "/Unit", strJoinToString$default + "/Throwable", strJoinToString$default + "/Number", strJoinToString$default + "/Byte", strJoinToString$default + "/Double", strJoinToString$default + "/Float", strJoinToString$default + "/Int", strJoinToString$default + "/Long", strJoinToString$default + "/Short", strJoinToString$default + "/Boolean", strJoinToString$default + "/Char", strJoinToString$default + "/CharSequence", strJoinToString$default + "/String", strJoinToString$default + "/Comparable", strJoinToString$default + "/Enum", strJoinToString$default + "/Array", strJoinToString$default + "/ByteArray", strJoinToString$default + "/DoubleArray", strJoinToString$default + "/FloatArray", strJoinToString$default + "/IntArray", strJoinToString$default + "/LongArray", strJoinToString$default + "/ShortArray", strJoinToString$default + "/BooleanArray", strJoinToString$default + "/CharArray", strJoinToString$default + "/Cloneable", strJoinToString$default + "/Annotation", strJoinToString$default + "/collections/Iterable", strJoinToString$default + "/collections/MutableIterable", strJoinToString$default + "/collections/Collection", strJoinToString$default + "/collections/MutableCollection", strJoinToString$default + "/collections/List", strJoinToString$default + "/collections/MutableList", strJoinToString$default + "/collections/Set", strJoinToString$default + "/collections/MutableSet", strJoinToString$default + "/collections/Map", strJoinToString$default + "/collections/MutableMap", strJoinToString$default + "/collections/Map.Entry", strJoinToString$default + "/collections/MutableMap.MutableEntry", strJoinToString$default + "/collections/Iterator", strJoinToString$default + "/collections/MutableIterator", strJoinToString$default + "/collections/ListIterator", strJoinToString$default + "/collections/MutableListIterator"});
        PREDEFINED_STRINGS = listListOf;
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(listListOf);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
        for (IndexedValue indexedValue : iterableWithIndex) {
            linkedHashMap.put((String) indexedValue.getValue(), Integer.valueOf(indexedValue.getIndex()));
        }
        PREDEFINED_STRINGS_MAP = linkedHashMap;
    }
}
