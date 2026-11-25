package org.jetbrains.kotlin.inline;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlagsUtilsKt;

/* JADX INFO: compiled from: inlineUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u001a6\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00040\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0002\u001a\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00012\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\n\u001a.\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00012\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0014\u001a\u00020\nH\u0002\u001a\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002¨\u0006\u0018"}, d2 = {"inlineFunctions", "", "Lorg/jetbrains/kotlin/inline/InlineFunction;", "functions", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "protoTypeTable", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;", "excludePrivateFunctions", "", "inlineFunctionsAndAccessors", "Lorg/jetbrains/kotlin/inline/InlineFunctionOrAccessor;", "header", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "excludePrivateMembers", "inlinePropertyAccessors", "Lorg/jetbrains/kotlin/inline/InlinePropertyAccessor;", "properties", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "excludePrivateAccessors", "isPrivate", OverlayablePolicy.NAME_flags, "", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineUtilKt {

    /* JADX INFO: compiled from: inlineUtil.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinClassHeader.Kind.values().length];
            try {
                iArr[KotlinClassHeader.Kind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS_PART.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ List inlineFunctionsAndAccessors$default(KotlinClassHeader kotlinClassHeader, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return inlineFunctionsAndAccessors(kotlinClassHeader, z);
    }

    public static final List<InlineFunctionOrAccessor> inlineFunctionsAndAccessors(KotlinClassHeader header, boolean z) {
        String[] strings;
        Intrinsics.checkNotNullParameter(header, "header");
        String[] data = header.getData();
        if (data != null && (strings = header.getStrings()) != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[header.getKind().ordinal()];
            if (i == 1) {
                Pair<JvmNameResolver, ProtoBuf.Class> classDataFrom = JvmProtoBufUtil.readClassDataFrom(data, strings);
                JvmNameResolver jvmNameResolverComponent1 = classDataFrom.component1();
                ProtoBuf.Class classComponent2 = classDataFrom.component2();
                List<ProtoBuf.Function> functionList = classComponent2.getFunctionList();
                Intrinsics.checkNotNullExpressionValue(functionList, "getFunctionList(...)");
                JvmNameResolver jvmNameResolver = jvmNameResolverComponent1;
                ProtoBuf.TypeTable typeTable = classComponent2.getTypeTable();
                Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
                List<InlineFunction> listInlineFunctions = inlineFunctions(functionList, jvmNameResolver, typeTable, z);
                List<ProtoBuf.Property> propertyList = classComponent2.getPropertyList();
                Intrinsics.checkNotNullExpressionValue(propertyList, "getPropertyList(...)");
                return CollectionsKt.plus((Collection) listInlineFunctions, (Iterable) inlinePropertyAccessors(propertyList, jvmNameResolver, z));
            }
            if (i == 2 || i == 3) {
                Pair<JvmNameResolver, ProtoBuf.Package> packageDataFrom = JvmProtoBufUtil.readPackageDataFrom(data, strings);
                JvmNameResolver jvmNameResolverComponent2 = packageDataFrom.component1();
                ProtoBuf.Package packageComponent2 = packageDataFrom.component2();
                List<ProtoBuf.Function> functionList2 = packageComponent2.getFunctionList();
                Intrinsics.checkNotNullExpressionValue(functionList2, "getFunctionList(...)");
                JvmNameResolver jvmNameResolver2 = jvmNameResolverComponent2;
                ProtoBuf.TypeTable typeTable2 = packageComponent2.getTypeTable();
                Intrinsics.checkNotNullExpressionValue(typeTable2, "getTypeTable(...)");
                List<InlineFunction> listInlineFunctions2 = inlineFunctions(functionList2, jvmNameResolver2, typeTable2, z);
                List<ProtoBuf.Property> propertyList2 = packageComponent2.getPropertyList();
                Intrinsics.checkNotNullExpressionValue(propertyList2, "getPropertyList(...)");
                return CollectionsKt.plus((Collection) listInlineFunctions2, (Iterable) inlinePropertyAccessors(propertyList2, jvmNameResolver2, z));
            }
            return CollectionsKt.emptyList();
        }
        return CollectionsKt.emptyList();
    }

    private static final List<InlineFunction> inlineFunctions(List<ProtoBuf.Function> list, NameResolver nameResolver, ProtoBuf.TypeTable typeTable, boolean z) {
        TypeTable typeTable2 = new TypeTable(typeTable);
        ArrayList<ProtoBuf.Function> arrayList = new ArrayList();
        for (Object obj : list) {
            ProtoBuf.Function function = (ProtoBuf.Function) obj;
            Boolean bool = Flags.IS_INLINE.get(function.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            if (bool.booleanValue() && (!z || !isPrivate(function.getFlags()))) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (ProtoBuf.Function function2 : arrayList) {
            JvmMemberSignature.Method jvmMethodSignature = JvmProtoBufUtil.INSTANCE.getJvmMethodSignature(function2, nameResolver, typeTable2);
            InlineFunction inlineFunction = jvmMethodSignature != null ? new InlineFunction(jvmMethodSignature, nameResolver.getString(function2.getName())) : null;
            if (inlineFunction != null) {
                arrayList2.add(inlineFunction);
            }
        }
        return arrayList2;
    }

    private static final List<InlinePropertyAccessor> inlinePropertyAccessors(List<ProtoBuf.Property> list, NameResolver nameResolver, boolean z) {
        ArrayList arrayList = new ArrayList();
        for (ProtoBuf.Property property : list) {
            GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, JvmProtoBuf.JvmPropertySignature> propertySignature = JvmProtoBuf.propertySignature;
            Intrinsics.checkNotNullExpressionValue(propertySignature, "propertySignature");
            JvmProtoBuf.JvmPropertySignature jvmPropertySignature = (JvmProtoBuf.JvmPropertySignature) ProtoBufUtilKt.getExtensionOrNull(property, propertySignature);
            if (jvmPropertySignature != null) {
                if (property.hasGetterFlags()) {
                    Boolean bool = Flags.IS_INLINE_ACCESSOR.get(property.getGetterFlags());
                    Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
                    if (bool.booleanValue() && (!z || !isPrivate(property.getGetterFlags()))) {
                        JvmProtoBuf.JvmMethodSignature getter = jvmPropertySignature.getGetter();
                        arrayList.add(new InlinePropertyAccessor(new JvmMemberSignature.Method(nameResolver.getString(getter.getName()), nameResolver.getString(getter.getDesc())), nameResolver.getString(property.getName())));
                    }
                }
                if (property.hasSetterFlags()) {
                    Boolean bool2 = Flags.IS_INLINE_ACCESSOR.get(property.getSetterFlags());
                    Intrinsics.checkNotNullExpressionValue(bool2, "get(...)");
                    if (bool2.booleanValue() && (!z || !isPrivate(property.getSetterFlags()))) {
                        JvmProtoBuf.JvmMethodSignature setter = jvmPropertySignature.getSetter();
                        arrayList.add(new InlinePropertyAccessor(new JvmMemberSignature.Method(nameResolver.getString(setter.getName()), nameResolver.getString(setter.getDesc())), nameResolver.getString(property.getName())));
                    }
                }
            }
        }
        return arrayList;
    }

    private static final boolean isPrivate(int i) {
        return DescriptorVisibilities.isPrivate(ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, Flags.VISIBILITY.get(i)));
    }
}
