package org.jetbrains.kotlin.metadata.deserialization;

import java.util.LinkedList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: NameResolverImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0016J.\u0010\u0013\u001a \u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u0015\u0012\u0004\u0012\u00020\u00120\u00142\u0006\u0010\u0016\u001a\u00020\u000eH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/metadata/deserialization/NameResolverImpl;", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "strings", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$StringTable;", "qualifiedNames", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$QualifiedNameTable;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$StringTable;Lorg/jetbrains/kotlin/metadata/ProtoBuf$QualifiedNameTable;)V", "getQualifiedNames", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$QualifiedNameTable;", "getStrings", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$StringTable;", "getPackageFqName", "", "index", "", "getQualifiedClassName", "getString", "isLocalClassName", "", "traverseIds", "Lkotlin/Triple;", "", "startingIndex", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NameResolverImpl implements NameResolver {
    private final ProtoBuf.QualifiedNameTable qualifiedNames;
    private final ProtoBuf.StringTable strings;

    /* JADX INFO: compiled from: NameResolverImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoBuf.QualifiedNameTable.QualifiedName.Kind.values().length];
            try {
                iArr[ProtoBuf.QualifiedNameTable.QualifiedName.Kind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoBuf.QualifiedNameTable.QualifiedName.Kind.PACKAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoBuf.QualifiedNameTable.QualifiedName.Kind.LOCAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public NameResolverImpl(ProtoBuf.StringTable strings, ProtoBuf.QualifiedNameTable qualifiedNames) {
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(qualifiedNames, "qualifiedNames");
        this.strings = strings;
        this.qualifiedNames = qualifiedNames;
    }

    public final ProtoBuf.StringTable getStrings() {
        return this.strings;
    }

    public final ProtoBuf.QualifiedNameTable getQualifiedNames() {
        return this.qualifiedNames;
    }

    @Override // org.jetbrains.kotlin.metadata.deserialization.NameResolver
    public String getString(int index) {
        String string = this.strings.getString(index);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    @Override // org.jetbrains.kotlin.metadata.deserialization.NameResolver
    public String getQualifiedClassName(int index) {
        Triple<List<String>, List<String>, Boolean> tripleTraverseIds = traverseIds(index);
        List<String> listComponent1 = tripleTraverseIds.component1();
        String strJoinToString$default = CollectionsKt.joinToString$default(tripleTraverseIds.component2(), ".", null, null, 0, null, null, 62, null);
        return listComponent1.isEmpty() ? strJoinToString$default : CollectionsKt.joinToString$default(listComponent1, "/", null, null, 0, null, null, 62, null) + '/' + strJoinToString$default;
    }

    @Override // org.jetbrains.kotlin.metadata.deserialization.NameResolver
    public boolean isLocalClassName(int index) {
        return traverseIds(index).getThird().booleanValue();
    }

    public final String getPackageFqName(int index) {
        return CollectionsKt.joinToString$default(traverseIds(index).getFirst(), ".", null, null, 0, null, null, 62, null);
    }

    private final Triple<List<String>, List<String>, Boolean> traverseIds(int startingIndex) {
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        boolean z = false;
        while (startingIndex != -1) {
            ProtoBuf.QualifiedNameTable.QualifiedName qualifiedName = this.qualifiedNames.getQualifiedName(startingIndex);
            String string = this.strings.getString(qualifiedName.getShortName());
            ProtoBuf.QualifiedNameTable.QualifiedName.Kind kind = qualifiedName.getKind();
            Intrinsics.checkNotNull(kind);
            int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
            if (i == 1) {
                linkedList2.addFirst(string);
            } else if (i == 2) {
                linkedList.addFirst(string);
            } else if (i == 3) {
                linkedList2.addFirst(string);
                z = true;
            }
            startingIndex = qualifiedName.getParentQualifiedName();
        }
        return new Triple<>(linkedList, linkedList2, Boolean.valueOf(z));
    }
}
