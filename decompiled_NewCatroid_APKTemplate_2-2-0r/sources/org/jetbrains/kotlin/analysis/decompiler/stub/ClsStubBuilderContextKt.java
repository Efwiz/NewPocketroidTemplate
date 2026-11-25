package org.jetbrains.kotlin.analysis.decompiler.stub;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: ClsStubBuilderContext.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0000¨\u0006\r"}, d2 = {"child", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderContext;", "typeParameterList", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "name", "Lorg/jetbrains/kotlin/name/Name;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "protoContainer", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "decompiler-to-stubs"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClsStubBuilderContextKt {
    public static /* synthetic */ ClsStubBuilderContext child$default(ClsStubBuilderContext clsStubBuilderContext, List list, Name name2, NameResolver nameResolver, TypeTable typeTable, ProtoContainer.Class r11, int i, Object obj) {
        if ((i & 2) != 0) {
            name2 = null;
        }
        Name name3 = name2;
        if ((i & 4) != 0) {
            nameResolver = clsStubBuilderContext.getNameResolver();
        }
        NameResolver nameResolver2 = nameResolver;
        if ((i & 8) != 0) {
            typeTable = clsStubBuilderContext.getTypeTable();
        }
        TypeTable typeTable2 = typeTable;
        if ((i & 16) != 0) {
            r11 = clsStubBuilderContext.getProtoContainer();
        }
        return child(clsStubBuilderContext, list, name3, nameResolver2, typeTable2, r11);
    }

    public static final ClsStubBuilderContext child(ClsStubBuilderContext clsStubBuilderContext, List<ProtoBuf.TypeParameter> typeParameterList, Name name2, NameResolver nameResolver, TypeTable typeTable, ProtoContainer.Class r13) {
        Intrinsics.checkNotNullParameter(clsStubBuilderContext, "<this>");
        Intrinsics.checkNotNullParameter(typeParameterList, "typeParameterList");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        ClsStubBuilderComponents components = clsStubBuilderContext.getComponents();
        FqName fqNameChild = name2 != null ? clsStubBuilderContext.getContainerFqName().child(name2) : clsStubBuilderContext.getContainerFqName();
        Intrinsics.checkNotNull(fqNameChild);
        return new ClsStubBuilderContext(components, nameResolver, fqNameChild, clsStubBuilderContext.getTypeParameters().child(nameResolver, typeParameterList), typeTable, r13);
    }
}
