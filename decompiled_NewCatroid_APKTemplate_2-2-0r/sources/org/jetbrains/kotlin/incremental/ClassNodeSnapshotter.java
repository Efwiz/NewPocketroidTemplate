package org.jetbrains.kotlin.incremental;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.ClassWriter;
import org.jetbrains.org.objectweb.asm.tree.AnnotationNode;
import org.jetbrains.org.objectweb.asm.tree.ClassNode;
import org.jetbrains.org.objectweb.asm.tree.FieldNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: KotlinClassInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004J\u0018\u0010\b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0007\u001a\u00020\u0004¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClassNodeSnapshotter;", "", "()V", "emptyClass", "Lorg/jetbrains/org/objectweb/asm/tree/ClassNode;", "snapshotClass", "", "classNode", "snapshotClassExcludingMembers", "alsoExcludeKotlinMetaData", "", "snapshotField", "fieldNode", "Lorg/jetbrains/org/objectweb/asm/tree/FieldNode;", "snapshotMethod", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "classVersion", "", "sortClassMembers", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassNodeSnapshotter {
    public static final ClassNodeSnapshotter INSTANCE = new ClassNodeSnapshotter();

    private ClassNodeSnapshotter() {
    }

    public final long snapshotClass(ClassNode classNode) {
        Intrinsics.checkNotNullParameter(classNode, "classNode");
        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        byte[] byteArray = classWriter.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        return KotlinClassInfoKt.hashToLong(byteArray);
    }

    public static /* synthetic */ long snapshotClassExcludingMembers$default(ClassNodeSnapshotter classNodeSnapshotter, ClassNode classNode, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return classNodeSnapshotter.snapshotClassExcludingMembers(classNode, z);
    }

    public final long snapshotClassExcludingMembers(ClassNode classNode, boolean alsoExcludeKotlinMetaData) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(classNode, "classNode");
        List<FieldNode> list = classNode.fields;
        List<MethodNode> list2 = classNode.methods;
        List<AnnotationNode> list3 = classNode.visibleAnnotations;
        classNode.fields = CollectionsKt.emptyList();
        classNode.methods = CollectionsKt.emptyList();
        if (alsoExcludeKotlinMetaData) {
            if (list3 != null) {
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : list3) {
                    if (!Intrinsics.areEqual(((AnnotationNode) obj).desc, "Lkotlin/Metadata;")) {
                        arrayList2.add(obj);
                    }
                }
                arrayList = arrayList2;
            } else {
                arrayList = null;
            }
            classNode.visibleAnnotations = arrayList;
        }
        long jSnapshotClass = snapshotClass(classNode);
        classNode.fields = list;
        classNode.methods = list2;
        classNode.visibleAnnotations = list3;
        return jSnapshotClass;
    }

    public final long snapshotField(FieldNode fieldNode) {
        Intrinsics.checkNotNullParameter(fieldNode, "fieldNode");
        ClassNode classNodeEmptyClass = emptyClass();
        classNodeEmptyClass.fields.add(fieldNode);
        return snapshotClass(classNodeEmptyClass);
    }

    public final long snapshotMethod(MethodNode methodNode, int classVersion) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        ClassNode classNodeEmptyClass = emptyClass();
        classNodeEmptyClass.version = classVersion;
        classNodeEmptyClass.methods.add(methodNode);
        return snapshotClass(classNodeEmptyClass);
    }

    public final void sortClassMembers(ClassNode classNode) {
        Intrinsics.checkNotNullParameter(classNode, "classNode");
        List<FieldNode> fields = classNode.fields;
        Intrinsics.checkNotNullExpressionValue(fields, "fields");
        CollectionsKt.sortWith(fields, ComparisonsKt.compareBy(new Function1<FieldNode, Comparable<?>>() { // from class: org.jetbrains.kotlin.incremental.ClassNodeSnapshotter.sortClassMembers.1
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(FieldNode fieldNode) {
                return fieldNode.f1011name;
            }
        }, new Function1<FieldNode, Comparable<?>>() { // from class: org.jetbrains.kotlin.incremental.ClassNodeSnapshotter.sortClassMembers.2
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(FieldNode fieldNode) {
                return fieldNode.desc;
            }
        }));
        List<MethodNode> methods = classNode.methods;
        Intrinsics.checkNotNullExpressionValue(methods, "methods");
        CollectionsKt.sortWith(methods, ComparisonsKt.compareBy(new Function1<MethodNode, Comparable<?>>() { // from class: org.jetbrains.kotlin.incremental.ClassNodeSnapshotter.sortClassMembers.3
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(MethodNode methodNode) {
                return methodNode.f1016name;
            }
        }, new Function1<MethodNode, Comparable<?>>() { // from class: org.jetbrains.kotlin.incremental.ClassNodeSnapshotter.sortClassMembers.4
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(MethodNode methodNode) {
                return methodNode.desc;
            }
        }));
    }

    private final ClassNode emptyClass() {
        ClassNode classNode = new ClassNode();
        classNode.f1009name = "SomeClass";
        return classNode;
    }
}
