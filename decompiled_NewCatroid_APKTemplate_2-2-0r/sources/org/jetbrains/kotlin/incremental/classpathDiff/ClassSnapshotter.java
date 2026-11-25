package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.DoNothingBuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.buildtools.api.jvm.ClassSnapshotGranularity;
import org.jetbrains.kotlin.incremental.ClassNodeSnapshotter;
import org.jetbrains.kotlin.incremental.DifferenceCalculatorForPackageFacade;
import org.jetbrains.kotlin.incremental.KotlinClassInfo;
import org.jetbrains.kotlin.incremental.KotlinClassInfoKt;
import org.jetbrains.kotlin.incremental.PackagePartProtoData;
import org.jetbrains.kotlin.incremental.ProtoData;
import org.jetbrains.kotlin.incremental.SelectiveClassVisitor;
import org.jetbrains.kotlin.incremental.storage.ExternalizersKt;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.tree.ClassNode;
import org.jetbrains.org.objectweb.asm.tree.FieldNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: ClasspathSnapshotter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J8\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\b\u001a\u00020\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bJ\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u0002J\f\u0010\u0014\u001a\u00020\u0015*\u00020\u0016H\u0002¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassSnapshotter;", "", "()V", "snapshot", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassSnapshot;", "classes", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFileWithContentsProvider;", "granularity", "Lorg/jetbrains/kotlin/buildtools/api/jvm/ClassSnapshotGranularity;", "metrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "snapshotJavaClass", "Lorg/jetbrains/kotlin/incremental/classpathDiff/JavaClassSnapshot;", "classFile", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ClassFileWithContents;", "snapshotKotlinClass", "Lorg/jetbrains/kotlin/incremental/classpathDiff/KotlinClassSnapshot;", "isInaccessible", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/BasicClassInfo;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassSnapshotter {
    public static final ClassSnapshotter INSTANCE = new ClassSnapshotter();

    /* JADX INFO: compiled from: ClasspathSnapshotter.kt */
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
                iArr[KotlinClassHeader.Kind.SYNTHETIC_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS_PART.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KotlinClassHeader.Kind.UNKNOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private ClassSnapshotter() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ List snapshot$default(ClassSnapshotter classSnapshotter, List list, ClassSnapshotGranularity classSnapshotGranularity, BuildMetricsReporter buildMetricsReporter, int i, Object obj) {
        if ((i & 4) != 0) {
            buildMetricsReporter = DoNothingBuildMetricsReporter.INSTANCE;
        }
        return classSnapshotter.snapshot(list, classSnapshotGranularity, buildMetricsReporter);
    }

    private static final JvmClassName snapshot$getClassName(ClassFile classFile) {
        if (!StringsKt.endsWith(classFile.getUnixStyleRelativePath(), ".class", true)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(StringsKt.dropLast(classFile.getUnixStyleRelativePath(), 6));
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
        return jvmClassNameByInternalName;
    }

    public final List<ClassSnapshot> snapshot(List<ClassFileWithContentsProvider> classes, ClassSnapshotGranularity granularity, BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> metrics) {
        Intrinsics.checkNotNullParameter(classes, "classes");
        Intrinsics.checkNotNullParameter(granularity, "granularity");
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        List<ClassFileWithContentsProvider> list = classes;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            linkedHashMap.put(snapshot$getClassName(((ClassFileWithContentsProvider) obj).getClassFile()), obj);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(snapshot$snapshotClass(linkedHashMap2, metrics, linkedHashMap, granularity, (ClassFileWithContentsProvider) it.next()));
        }
        return arrayList;
    }

    private final boolean isInaccessible(BasicClassInfo basicClassInfo) {
        if (basicClassInfo.getIsKotlinClass()) {
            KotlinClassHeader kotlinClassHeader = basicClassInfo.getKotlinClassHeader();
            Intrinsics.checkNotNull(kotlinClassHeader);
            int i = WhenMappings.$EnumSwitchMapping$0[kotlinClassHeader.getKind().ordinal()];
            if (i != 1) {
                if (i != 2) {
                    return false;
                }
            } else if (!basicClassInfo.getIsPrivate() && !basicClassInfo.getIsLocal() && !basicClassInfo.getIsAnonymous() && !basicClassInfo.getIsSynthetic()) {
                return false;
            }
        } else if (!basicClassInfo.getIsPrivate() && !basicClassInfo.getIsLocal() && !basicClassInfo.getIsAnonymous() && !basicClassInfo.getIsSynthetic()) {
            return false;
        }
        return true;
    }

    private final KotlinClassSnapshot snapshotKotlinClass(ClassFileWithContents classFile, ClassSnapshotGranularity granularity) {
        Name shortClassName;
        KotlinClassInfo.Companion companion = KotlinClassInfo.INSTANCE;
        ClassId classId = classFile.getClassInfo().getClassId();
        KotlinClassHeader kotlinClassHeader = classFile.getClassInfo().getKotlinClassHeader();
        Intrinsics.checkNotNull(kotlinClassHeader);
        KotlinClassInfo kotlinClassInfoCreateFrom = companion.createFrom(classId, kotlinClassHeader, classFile.getContents());
        ClassId classId2 = kotlinClassInfoCreateFrom.getClassId();
        long jHashToLong = KotlinClassInfoKt.hashToLong(ExternalizersKt.toByteArray(KotlinClassInfoExternalizer.INSTANCE, kotlinClassInfoCreateFrom));
        KotlinClassInfo kotlinClassInfo = granularity == ClassSnapshotGranularity.CLASS_MEMBER_LEVEL ? kotlinClassInfoCreateFrom : null;
        switch (WhenMappings.$EnumSwitchMapping$0[kotlinClassInfoCreateFrom.getClassKind().ordinal()]) {
            case 1:
                List<JvmClassName> supertypes = classFile.getClassInfo().getSupertypes();
                ClassId companionObject = kotlinClassInfoCreateFrom.getCompanionObject();
                return new RegularKotlinClassSnapshot(classId2, jHashToLong, kotlinClassInfo, supertypes, (companionObject == null || (shortClassName = companionObject.getShortClassName()) == null) ? null : shortClassName.getIdentifier(), kotlinClassInfoCreateFrom.getConstantsInCompanionObject());
            case 2:
                throw new IllegalStateException(("Unexpected class " + classId2 + " with class kind SYNTHETIC_CLASS (synthetic classes should have been removed earlier)").toString());
            case 3:
            case 4:
                DifferenceCalculatorForPackageFacade.Companion companion2 = DifferenceCalculatorForPackageFacade.INSTANCE;
                ProtoData protoData = kotlinClassInfoCreateFrom.getProtoData();
                Intrinsics.checkNotNull(protoData, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.PackagePartProtoData");
                return new PackageFacadeKotlinClassSnapshot(classId2, jHashToLong, kotlinClassInfo, CollectionsKt.toSet(companion2.getNonPrivateMembers((PackagePartProtoData) protoData)));
            case 5:
                return new MultifileClassKotlinClassSnapshot(classId2, jHashToLong, kotlinClassInfo, kotlinClassInfoCreateFrom.getExtraInfo().getConstantSnapshots().keySet());
            case 6:
                throw new IllegalStateException(("Can't handle class " + classId2 + " with class kind UNKNOWN").toString());
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final JavaClassSnapshot snapshotJavaClass(ClassFileWithContents classFile, ClassSnapshotGranularity granularity) {
        long jSnapshotClass;
        ClassNode classNode = new ClassNode();
        new ClassReader(classFile.getContents()).accept(new SelectiveClassVisitor(classNode, new Function3<JvmMemberSignature.Field, Boolean, Boolean, Boolean>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClassSnapshotter$snapshotJavaClass$selectiveClassVisitor$1
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Boolean invoke(JvmMemberSignature.Field field, Boolean bool, Boolean bool2) {
                return invoke(field, bool.booleanValue(), bool2.booleanValue());
            }

            public final Boolean invoke(JvmMemberSignature.Field field, boolean z, boolean z2) {
                Intrinsics.checkNotNullParameter(field, "<anonymous parameter 0>");
                return Boolean.valueOf(!z);
            }
        }, new Function2<JvmMemberSignature.Method, Boolean, Boolean>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClassSnapshotter$snapshotJavaClass$selectiveClassVisitor$2
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Boolean invoke(JvmMemberSignature.Method method, Boolean bool) {
                return invoke(method, bool.booleanValue());
            }

            public final Boolean invoke(JvmMemberSignature.Method method, boolean z) {
                Intrinsics.checkNotNullParameter(method, "<anonymous parameter 0>");
                return Boolean.valueOf(!z);
            }
        }), 1);
        ClassNodeSnapshotter.INSTANCE.sortClassMembers(classNode);
        JavaClassMemberLevelSnapshot javaClassMemberLevelSnapshot = null;
        if (granularity == ClassSnapshotGranularity.CLASS_MEMBER_LEVEL) {
            String name2 = classNode.f1009name;
            Intrinsics.checkNotNullExpressionValue(name2, "name");
            JavaElementSnapshot javaElementSnapshot = new JavaElementSnapshot(name2, ClassNodeSnapshotter.snapshotClassExcludingMembers$default(ClassNodeSnapshotter.INSTANCE, classNode, false, 2, null));
            List<FieldNode> fields = classNode.fields;
            Intrinsics.checkNotNullExpressionValue(fields, "fields");
            List<FieldNode> list = fields;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (FieldNode fieldNode : list) {
                String name3 = fieldNode.f1011name;
                Intrinsics.checkNotNullExpressionValue(name3, "name");
                ClassNodeSnapshotter classNodeSnapshotter = ClassNodeSnapshotter.INSTANCE;
                Intrinsics.checkNotNull(fieldNode);
                arrayList.add(new JavaElementSnapshot(name3, classNodeSnapshotter.snapshotField(fieldNode)));
            }
            ArrayList arrayList2 = arrayList;
            List<MethodNode> methods = classNode.methods;
            Intrinsics.checkNotNullExpressionValue(methods, "methods");
            List<MethodNode> list2 = methods;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (MethodNode methodNode : list2) {
                String name4 = methodNode.f1016name;
                Intrinsics.checkNotNullExpressionValue(name4, "name");
                ClassNodeSnapshotter classNodeSnapshotter2 = ClassNodeSnapshotter.INSTANCE;
                Intrinsics.checkNotNull(methodNode);
                arrayList3.add(new JavaElementSnapshot(name4, classNodeSnapshotter2.snapshotMethod(methodNode, classNode.version)));
            }
            javaClassMemberLevelSnapshot = new JavaClassMemberLevelSnapshot(javaElementSnapshot, arrayList2, arrayList3);
        }
        JavaClassMemberLevelSnapshot javaClassMemberLevelSnapshot2 = javaClassMemberLevelSnapshot;
        if (granularity == ClassSnapshotGranularity.CLASS_MEMBER_LEVEL) {
            JavaClassMemberLevelSnapshotExternalizer javaClassMemberLevelSnapshotExternalizer = JavaClassMemberLevelSnapshotExternalizer.INSTANCE;
            Intrinsics.checkNotNull(javaClassMemberLevelSnapshot2);
            jSnapshotClass = KotlinClassInfoKt.hashToLong(ExternalizersKt.toByteArray(javaClassMemberLevelSnapshotExternalizer, javaClassMemberLevelSnapshot2));
        } else {
            jSnapshotClass = ClassNodeSnapshotter.INSTANCE.snapshotClass(classNode);
        }
        return new JavaClassSnapshot(classFile.getClassInfo().getClassId(), jSnapshotClass, javaClassMemberLevelSnapshot2, classFile.getClassInfo().getSupertypes());
    }

    private static final ClassSnapshot snapshot$snapshotClass(Map<ClassFileWithContentsProvider, ClassSnapshot> map, BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildMetricsReporter, Map<JvmClassName, ClassFileWithContentsProvider> map2, ClassSnapshotGranularity classSnapshotGranularity, ClassFileWithContentsProvider classFileWithContentsProvider) {
        JavaClassSnapshot javaClassSnapshot;
        ClassFileWithContentsProvider classFileWithContentsProvider2;
        ClassSnapshot classSnapshot = map.get(classFileWithContentsProvider);
        if (classSnapshot == null) {
            GradleBuildTime gradleBuildTime = GradleBuildTime.LOAD_CONTENTS_OF_CLASSES;
            buildMetricsReporter.startMeasure(gradleBuildTime);
            try {
                ClassFileWithContents classFileWithContentsLoadContents = classFileWithContentsProvider.loadContents();
                buildMetricsReporter.endMeasure(gradleBuildTime);
                ClassId outerClassId = classFileWithContentsLoadContents.getClassInfo().getClassId().getOuterClassId();
                ClassSnapshot classSnapshotSnapshot$snapshotClass = null;
                if (outerClassId != null && (classFileWithContentsProvider2 = map2.get(JvmClassName.byClassId(outerClassId))) != null) {
                    classSnapshotSnapshot$snapshotClass = snapshot$snapshotClass(map, buildMetricsReporter, map2, classSnapshotGranularity, classFileWithContentsProvider2);
                }
                ClassSnapshotter classSnapshotter = INSTANCE;
                if (classSnapshotter.isInaccessible(classFileWithContentsLoadContents.getClassInfo()) || (classSnapshotSnapshot$snapshotClass instanceof InaccessibleClassSnapshot)) {
                    classSnapshot = InaccessibleClassSnapshot.INSTANCE;
                } else {
                    if (!classFileWithContentsLoadContents.getClassInfo().getIsKotlinClass()) {
                        GradleBuildTime gradleBuildTime2 = GradleBuildTime.SNAPSHOT_JAVA_CLASSES;
                        buildMetricsReporter.startMeasure(gradleBuildTime2);
                        try {
                            JavaClassSnapshot javaClassSnapshotSnapshotJavaClass = classSnapshotter.snapshotJavaClass(classFileWithContentsLoadContents, classSnapshotGranularity);
                            buildMetricsReporter.endMeasure(gradleBuildTime2);
                            javaClassSnapshot = javaClassSnapshotSnapshotJavaClass;
                        } catch (Throwable th) {
                            buildMetricsReporter.endMeasure(gradleBuildTime2);
                            throw th;
                        }
                    } else {
                        GradleBuildTime gradleBuildTime3 = GradleBuildTime.SNAPSHOT_KOTLIN_CLASSES;
                        buildMetricsReporter.startMeasure(gradleBuildTime3);
                        try {
                            KotlinClassSnapshot kotlinClassSnapshotSnapshotKotlinClass = classSnapshotter.snapshotKotlinClass(classFileWithContentsLoadContents, classSnapshotGranularity);
                            buildMetricsReporter.endMeasure(gradleBuildTime3);
                            javaClassSnapshot = kotlinClassSnapshotSnapshotKotlinClass;
                        } catch (Throwable th2) {
                            buildMetricsReporter.endMeasure(gradleBuildTime3);
                            throw th2;
                        }
                    }
                    classSnapshot = javaClassSnapshot;
                }
                map.put(classFileWithContentsProvider, classSnapshot);
            } catch (Throwable th3) {
                buildMetricsReporter.endMeasure(gradleBuildTime);
                throw th3;
            }
        }
        return classSnapshot;
    }
}
