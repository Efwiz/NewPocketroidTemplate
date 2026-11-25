package org.jetbrains.kotlin.incremental;

import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.incremental.storage.ExternalizersKt;
import org.jetbrains.kotlin.inline.InlineFunctionOrAccessor;
import org.jetbrains.kotlin.inline.InlineUtilKt;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.tree.ClassNode;
import org.jetbrains.org.objectweb.asm.tree.FieldNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: KotlinClassInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\t\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\n\u0010\u0006\u001a\u00020\u0007*\u00020\u0005¨\u0006\b"}, d2 = {"getExtraInfo", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo$ExtraInfo;", "classHeader", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "classContents", "", "hashToLong", "", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassInfoKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final KotlinClassInfo.ExtraInfo getExtraInfo(KotlinClassHeader kotlinClassHeader, byte[] bArr) {
        List<InlineFunctionOrAccessor> listInlineFunctionsAndAccessors = InlineUtilKt.inlineFunctionsAndAccessors(kotlinClassHeader, true);
        final LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listInlineFunctionsAndAccessors, 10)), 16));
        for (Object obj : listInlineFunctionsAndAccessors) {
            linkedHashMap.put(((InlineFunctionOrAccessor) obj).getJvmMethodSignature(), obj);
        }
        ClassNode classNode = new ClassNode();
        new ClassReader(bArr).accept(new SelectiveClassVisitor(classNode, new Function3<JvmMemberSignature.Field, Boolean, Boolean, Boolean>() { // from class: org.jetbrains.kotlin.incremental.KotlinClassInfoKt$getExtraInfo$selectiveClassVisitor$1
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Boolean invoke(JvmMemberSignature.Field field, Boolean bool, Boolean bool2) {
                return invoke(field, bool.booleanValue(), bool2.booleanValue());
            }

            public final Boolean invoke(JvmMemberSignature.Field field, boolean z, boolean z2) {
                Intrinsics.checkNotNullParameter(field, "<anonymous parameter 0>");
                return Boolean.valueOf(!z && z2);
            }
        }, new Function2<JvmMemberSignature.Method, Boolean, Boolean>() { // from class: org.jetbrains.kotlin.incremental.KotlinClassInfoKt$getExtraInfo$selectiveClassVisitor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Boolean invoke(JvmMemberSignature.Method method, Boolean bool) {
                return invoke(method, bool.booleanValue());
            }

            public final Boolean invoke(JvmMemberSignature.Method method, boolean z) {
                Intrinsics.checkNotNullParameter(method, "method");
                return Boolean.valueOf(linkedHashMap.keySet().contains(method));
            }
        }), !linkedHashMap.isEmpty() ? 0 : 3);
        ClassNodeSnapshotter.INSTANCE.sortClassMembers(classNode);
        Long lValueOf = kotlinClassHeader.getKind() == KotlinClassHeader.Kind.CLASS ? Long.valueOf(ClassNodeSnapshotter.INSTANCE.snapshotClassExcludingMembers(classNode, true)) : null;
        List<FieldNode> fields = classNode.fields;
        Intrinsics.checkNotNullExpressionValue(fields, "fields");
        List<FieldNode> list = fields;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (FieldNode fieldNode : list) {
            String str = fieldNode.f1011name;
            ConstantValueExternalizer constantValueExternalizer = ConstantValueExternalizer.INSTANCE;
            Object obj2 = fieldNode.value;
            Intrinsics.checkNotNull(obj2);
            Pair pair = TuplesKt.to(str, Long.valueOf(hashToLong(ExternalizersKt.toByteArray(constantValueExternalizer, obj2))));
            linkedHashMap2.put(pair.getFirst(), pair.getSecond());
        }
        List<MethodNode> methods = classNode.methods;
        Intrinsics.checkNotNullExpressionValue(methods, "methods");
        List<MethodNode> list2 = methods;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (MethodNode methodNode : list2) {
            String name2 = methodNode.f1016name;
            Intrinsics.checkNotNullExpressionValue(name2, "name");
            String desc = methodNode.desc;
            Intrinsics.checkNotNullExpressionValue(desc, "desc");
            Object obj3 = linkedHashMap.get(new JvmMemberSignature.Method(name2, desc));
            Intrinsics.checkNotNull(obj3);
            ClassNodeSnapshotter classNodeSnapshotter = ClassNodeSnapshotter.INSTANCE;
            Intrinsics.checkNotNull(methodNode);
            Pair pair2 = TuplesKt.to(obj3, Long.valueOf(classNodeSnapshotter.snapshotMethod(methodNode, classNode.version)));
            linkedHashMap3.put(pair2.getFirst(), pair2.getSecond());
        }
        return new KotlinClassInfo.ExtraInfo(lValueOf, linkedHashMap2, linkedHashMap3);
    }

    public static final long hashToLong(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return IncrementalJvmCacheKt.md5(bArr);
    }
}
