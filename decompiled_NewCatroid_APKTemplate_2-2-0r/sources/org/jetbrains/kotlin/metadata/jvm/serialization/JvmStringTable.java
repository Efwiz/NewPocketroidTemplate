package org.jetbrains.kotlin.metadata.jvm.serialization;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolverBase;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolverKt;
import org.jetbrains.kotlin.metadata.serialization.StringTable;

/* JADX INFO: compiled from: JvmStringTable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u000bH\u0016J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u001fJ\f\u0010 \u001a\u00020\u0017*\u00020\u000fH\u0002R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00070\nj\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0007`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\u000ej\b\u0012\u0004\u0012\u00020\u000b`\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "Lorg/jetbrains/kotlin/metadata/serialization/StringTable;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolver;", "(Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolver;)V", "localNames", "Ljava/util/LinkedHashSet;", "", "Lkotlin/collections/LinkedHashSet;", "map", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "records", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$StringTableTypes$Record$Builder;", "Lkotlin/collections/ArrayList;", "strings", "getStrings", "()Ljava/util/ArrayList;", "getQualifiedClassNameIndex", "className", "isLocal", "", "getStringIndex", "string", "serializeTo", "", "output", "Ljava/io/OutputStream;", "toNameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "isTrivial", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JvmStringTable implements StringTable {
    private final LinkedHashSet<Integer> localNames;
    private final HashMap<String, Integer> map;
    private final ArrayList<JvmProtoBuf.StringTableTypes.Record.Builder> records;
    private final ArrayList<String> strings;

    /* JADX WARN: Multi-variable type inference failed */
    public JvmStringTable() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public JvmStringTable(JvmNameResolver jvmNameResolver) {
        ArrayList<String> arrayList = new ArrayList<>();
        this.strings = arrayList;
        ArrayList<JvmProtoBuf.StringTableTypes.Record.Builder> arrayList2 = new ArrayList<>();
        this.records = arrayList2;
        this.map = new HashMap<>();
        this.localNames = new LinkedHashSet<>();
        if (jvmNameResolver != null) {
            CollectionsKt.addAll(arrayList, jvmNameResolver.getStrings());
            List<JvmProtoBuf.StringTableTypes.Record> recordList = jvmNameResolver.getTypes().getRecordList();
            Intrinsics.checkNotNullExpressionValue(recordList, "getRecordList(...)");
            ArrayList<JvmProtoBuf.StringTableTypes.Record.Builder> arrayList3 = arrayList2;
            Iterator<T> it = recordList.iterator();
            while (it.hasNext()) {
                arrayList3.add(((JvmProtoBuf.StringTableTypes.Record) it.next()).toBuilder());
            }
            int size = this.strings.size();
            for (int i = 0; i < size; i++) {
                this.map.put(jvmNameResolver.getString(i), Integer.valueOf(i));
            }
            this.localNames.addAll(jvmNameResolver.getTypes().getLocalNameList());
        }
    }

    public /* synthetic */ JvmStringTable(JvmNameResolver jvmNameResolver, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : jvmNameResolver);
    }

    public final ArrayList<String> getStrings() {
        return this.strings;
    }

    @Override // org.jetbrains.kotlin.metadata.serialization.StringTable
    public int getStringIndex(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        HashMap<String, Integer> map = this.map;
        Integer numValueOf = map.get(string);
        if (numValueOf == null) {
            int size = this.strings.size();
            this.strings.add(string);
            JvmProtoBuf.StringTableTypes.Record.Builder builder = (JvmProtoBuf.StringTableTypes.Record.Builder) CollectionsKt.lastOrNull((List) this.records);
            if (builder != null && isTrivial(builder)) {
                builder.setRange(builder.getRange() + 1);
            } else {
                this.records.add(JvmProtoBuf.StringTableTypes.Record.newBuilder());
            }
            numValueOf = Integer.valueOf(size);
            map.put(string, numValueOf);
        }
        return numValueOf.intValue();
    }

    private final boolean isTrivial(JvmProtoBuf.StringTableTypes.Record.Builder builder) {
        return !builder.hasPredefinedIndex() && !builder.hasOperation() && builder.getSubstringIndexCount() == 0 && builder.getReplaceCharCount() == 0;
    }

    @Override // org.jetbrains.kotlin.metadata.serialization.StringTable
    public int getQualifiedClassNameIndex(String className, boolean isLocal) {
        Intrinsics.checkNotNullParameter(className, "className");
        Integer num = this.map.get(className);
        if (num != null && isLocal == this.localNames.contains(num)) {
            return num.intValue();
        }
        int size = this.strings.size();
        if (isLocal) {
            this.localNames.add(Integer.valueOf(size));
        }
        JvmProtoBuf.StringTableTypes.Record.Builder builderNewBuilder = JvmProtoBuf.StringTableTypes.Record.newBuilder();
        if (isLocal || StringsKt.contains$default((CharSequence) className, '$', false, 2, (Object) null)) {
            this.strings.add(className);
        } else {
            Integer predefinedStringIndex = JvmNameResolverBase.INSTANCE.getPredefinedStringIndex(className);
            if (predefinedStringIndex != null) {
                builderNewBuilder.setPredefinedIndex(predefinedStringIndex.intValue());
                this.strings.add("");
            } else {
                builderNewBuilder.setOperation(JvmProtoBuf.StringTableTypes.Record.Operation.DESC_TO_CLASS_ID);
                this.strings.add("L" + StringsKt.replace$default(className, '.', '$', false, 4, (Object) null) + ';');
            }
        }
        this.records.add(builderNewBuilder);
        this.map.put(className, Integer.valueOf(size));
        return size;
    }

    public final void serializeTo(OutputStream output) {
        Intrinsics.checkNotNullParameter(output, "output");
        JvmProtoBuf.StringTableTypes.Builder builderNewBuilder = JvmProtoBuf.StringTableTypes.newBuilder();
        ArrayList<JvmProtoBuf.StringTableTypes.Record.Builder> arrayList = this.records;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
        Iterator<T> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((JvmProtoBuf.StringTableTypes.Record.Builder) it.next()).build());
        }
        builderNewBuilder.addAllRecord(arrayList2);
        builderNewBuilder.addAllLocalName(this.localNames);
        builderNewBuilder.build().writeDelimitedTo(output);
    }

    public final NameResolver toNameResolver() {
        String[] strArr = (String[]) this.strings.toArray(new String[0]);
        LinkedHashSet<Integer> linkedHashSet = this.localNames;
        ArrayList<JvmProtoBuf.StringTableTypes.Record.Builder> arrayList = this.records;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
        Iterator<T> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((JvmProtoBuf.StringTableTypes.Record.Builder) it.next()).build());
        }
        return new JvmNameResolverBase(strArr, linkedHashSet, JvmNameResolverKt.toExpandedRecordsList(arrayList2));
    }
}
