package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.javaslang.JavaslangAdaptersKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: ClassifierResolutionContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0002)*B!\b\u0016\u0012\u0018\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003j\u0002`\u0006¢\u0006\u0002\u0010\u0007Bi\b\u0002\u0012\u0018\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003j\u0002`\u0006\u0012\"\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b`\f\u0012\"\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000e0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000e`\f¢\u0006\u0002\u0010\u000fJ%\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\nH\u0000¢\u0006\u0002\b\u0015J\u001b\u0010\u0016\u001a\u00020\u00112\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0018H\u0000¢\u0006\u0002\b\u0019J\u0017\u0010\u001a\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001b\u001a\u00020\nH\u0000¢\u0006\u0002\b\u001cJ\r\u0010\u001d\u001a\u00020\u0000H\u0000¢\u0006\u0002\b\u001eJ\u0015\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\nH\u0000¢\u0006\u0002\b J\u0015\u0010!\u001a\u00020\"2\u0006\u0010\u001b\u001a\u00020\nH\u0000¢\u0006\u0002\b#J\u0010\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020\u0004H\u0002J\u0015\u0010&\u001a\u00020\"2\u0006\u0010'\u001a\u00020\nH\u0000¢\u0006\u0002\b(R \u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003j\u0002`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000e0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000e`\fX\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "", "classesByQName", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassIdToJavaClass;", "(Lkotlin/jvm/functions/Function1;)V", "typeParameters", "Ljavaslang/collection/Map;", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", "innerClasses", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext$InnerClassInfo;", "(Lkotlin/jvm/functions/Function1;Ljavaslang/collection/Map;Ljavaslang/collection/Map;)V", "addInnerClass", "", "innerInternalName", "outerInternalName", Namer.METADATA_SIMPLE_NAME, "addInnerClass$resolution_common_jvm", "addTypeParameters", "newTypeParameters", "", "addTypeParameters$resolution_common_jvm", "convertNestedClassInternalNameWithSimpleHeuristic", "internalName", "convertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm", "copyForMember", "copyForMember$resolution_common_jvm", "mapInternalNameToClassId", "mapInternalNameToClassId$resolution_common_jvm", "resolveByInternalName", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext$Result;", "resolveByInternalName$resolution_common_jvm", "resolveClass", "classId", "resolveTypeParameter", "name", "resolveTypeParameter$resolution_common_jvm", "InnerClassInfo", "Result", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassifierResolutionContext {
    private final Function1<ClassId, JavaClass> classesByQName;
    private Map<String, InnerClassInfo> innerClasses;
    private Map<String, JavaTypeParameter> typeParameters;

    /* JADX WARN: Multi-variable type inference failed */
    private ClassifierResolutionContext(Function1<? super ClassId, ? extends JavaClass> function1, Map<String, JavaTypeParameter> map, Map<String, InnerClassInfo> map2) {
        this.classesByQName = function1;
        this.typeParameters = map;
        this.innerClasses = map2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ClassifierResolutionContext(Function1<? super ClassId, ? extends JavaClass> classesByQName) {
        Intrinsics.checkNotNullParameter(classesByQName, "classesByQName");
        HashMap hashMapEmpty = HashMap.empty();
        Intrinsics.checkNotNullExpressionValue(hashMapEmpty, "empty(...)");
        HashMap hashMapEmpty2 = HashMap.empty();
        Intrinsics.checkNotNullExpressionValue(hashMapEmpty2, "empty(...)");
        this(classesByQName, hashMapEmpty, hashMapEmpty2);
    }

    /* JADX INFO: compiled from: ClassifierResolutionContext.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0005HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext$Result;", "", "classifier", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "qualifiedName", "", "(Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;Ljava/lang/String;)V", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "getQualifiedName", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Result {
        private final JavaClassifier classifier;
        private final String qualifiedName;

        public static /* synthetic */ Result copy$default(Result result, JavaClassifier javaClassifier, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                javaClassifier = result.classifier;
            }
            if ((i & 2) != 0) {
                str = result.qualifiedName;
            }
            return result.copy(javaClassifier, str);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final JavaClassifier getClassifier() {
            return this.classifier;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getQualifiedName() {
            return this.qualifiedName;
        }

        public final Result copy(JavaClassifier classifier, String qualifiedName) {
            Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
            return new Result(classifier, qualifiedName);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Result)) {
                return false;
            }
            Result result = (Result) other;
            return Intrinsics.areEqual(this.classifier, result.classifier) && Intrinsics.areEqual(this.qualifiedName, result.qualifiedName);
        }

        public int hashCode() {
            JavaClassifier javaClassifier = this.classifier;
            return ((javaClassifier == null ? 0 : javaClassifier.hashCode()) * 31) + this.qualifiedName.hashCode();
        }

        public String toString() {
            return "Result(classifier=" + this.classifier + ", qualifiedName=" + this.qualifiedName + ')';
        }

        public Result(JavaClassifier javaClassifier, String qualifiedName) {
            Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
            this.classifier = javaClassifier;
            this.qualifiedName = qualifiedName;
        }

        public final JavaClassifier getClassifier() {
            return this.classifier;
        }

        public final String getQualifiedName() {
            return this.qualifiedName;
        }
    }

    /* JADX INFO: compiled from: ClassifierResolutionContext.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext$InnerClassInfo;", "", "outerInternalName", "", Namer.METADATA_SIMPLE_NAME, "(Ljava/lang/String;Ljava/lang/String;)V", "getOuterInternalName", "()Ljava/lang/String;", "getSimpleName", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class InnerClassInfo {
        private final String outerInternalName;
        private final String simpleName;

        public InnerClassInfo(String outerInternalName, String simpleName) {
            Intrinsics.checkNotNullParameter(outerInternalName, "outerInternalName");
            Intrinsics.checkNotNullParameter(simpleName, "simpleName");
            this.outerInternalName = outerInternalName;
            this.simpleName = simpleName;
        }

        public final String getOuterInternalName() {
            return this.outerInternalName;
        }

        public final String getSimpleName() {
            return this.simpleName;
        }
    }

    public final void addInnerClass$resolution_common_jvm(String innerInternalName, String outerInternalName, String simpleName) {
        Intrinsics.checkNotNullParameter(innerInternalName, "innerInternalName");
        Intrinsics.checkNotNullParameter(outerInternalName, "outerInternalName");
        Intrinsics.checkNotNullParameter(simpleName, "simpleName");
        Map<String, InnerClassInfo> mapPut = this.innerClasses.put(innerInternalName, new InnerClassInfo(outerInternalName, simpleName));
        Intrinsics.checkNotNullExpressionValue(mapPut, "put(...)");
        this.innerClasses = mapPut;
    }

    public final void addTypeParameters$resolution_common_jvm(Collection<? extends JavaTypeParameter> newTypeParameters) {
        Intrinsics.checkNotNullParameter(newTypeParameters, "newTypeParameters");
        if (newTypeParameters.isEmpty()) {
            return;
        }
        Map<String, JavaTypeParameter> mapPut = this.typeParameters;
        for (JavaTypeParameter javaTypeParameter : newTypeParameters) {
            mapPut = mapPut.put(javaTypeParameter.getName().getIdentifier(), javaTypeParameter);
            Intrinsics.checkNotNullExpressionValue(mapPut, "put(...)");
        }
        this.typeParameters = mapPut;
    }

    private final Result resolveClass(ClassId classId) {
        JavaClass javaClassInvoke = this.classesByQName.invoke(classId);
        String strAsString = classId.asSingleFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return new Result(javaClassInvoke, strAsString);
    }

    public final Result resolveTypeParameter$resolution_common_jvm(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return new Result((JavaClassifier) JavaslangAdaptersKt.getOrNull(this.typeParameters, name2), name2);
    }

    public final ClassifierResolutionContext copyForMember$resolution_common_jvm() {
        return new ClassifierResolutionContext(this.classesByQName, this.typeParameters, this.innerClasses);
    }

    public final ClassId mapInternalNameToClassId$resolution_common_jvm(String internalName) {
        InnerClassInfo innerClassInfo;
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        if (StringsKt.contains$default((CharSequence) internalName, '$', false, 2, (Object) null) && (innerClassInfo = (InnerClassInfo) JavaslangAdaptersKt.getOrNull(this.innerClasses, internalName)) != null && Name.isValidIdentifier(innerClassInfo.getSimpleName())) {
            ClassId classIdCreateNestedClassId = mapInternalNameToClassId$resolution_common_jvm(innerClassInfo.getOuterInternalName()).createNestedClassId(Name.identifier(innerClassInfo.getSimpleName()));
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
            return classIdCreateNestedClassId;
        }
        ClassId classId = ClassId.topLevel(new FqName(StringsKt.replace$default(internalName, '/', '.', false, 4, (Object) null)));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        return classId;
    }

    public final ClassId convertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm(String internalName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        SmartList smartList = new SmartList();
        int length = internalName.length();
        for (int i = 0; i < length; i++) {
            if (internalName.charAt(i) == '$' && i > 0 && internalName.charAt(i - 1) != '/' && i < internalName.length() - 1 && internalName.charAt(i + 1) != '$') {
                smartList.add(Integer.valueOf(i));
            }
        }
        if (smartList.isEmpty()) {
            return null;
        }
        List<Pair> listZip = CollectionsKt.zip(CollectionsKt.plus((Collection) CollectionsKt.listOf(-1), (Iterable) smartList), CollectionsKt.plus((Collection<? extends Integer>) smartList, Integer.valueOf(internalName.length())));
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
        for (Pair pair : listZip) {
            Integer num = (Integer) pair.component1();
            Integer num2 = (Integer) pair.component2();
            int iIntValue = num.intValue() + 1;
            Intrinsics.checkNotNull(num2);
            String strSubstring = internalName.substring(iIntValue, num2.intValue());
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            arrayList.add(strSubstring);
        }
        ArrayList arrayList2 = arrayList;
        FqName fqName = new FqName(StringsKt.replace$default((String) arrayList2.get(0), '/', '.', false, 4, (Object) null));
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        return new ClassId(fqNameParent, new FqName(fqName.shortName().asString() + '.' + CollectionsKt.joinToString$default(arrayList2.subList(1, arrayList2.size()), ".", null, null, 0, null, null, 62, null)), false);
    }

    public final Result resolveByInternalName$resolution_common_jvm(String internalName) {
        ClassId classIdConvertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm;
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Result resultResolveClass = resolveClass(mapInternalNameToClassId$resolution_common_jvm(internalName));
        if (resultResolveClass.getClassifier() == null && StringsKt.contains$default((CharSequence) internalName, '$', false, 2, (Object) null) && (classIdConvertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm = convertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm(internalName)) != null) {
            Result resultResolveClass2 = resolveClass(classIdConvertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm);
            if ((resultResolveClass2.getClassifier() instanceof BinaryJavaClass) && Intrinsics.areEqual(classIdConvertNestedClassInternalNameWithSimpleHeuristic$resolution_common_jvm, ((BinaryJavaClass) resultResolveClass2.getClassifier()).getContext().mapInternalNameToClassId$resolution_common_jvm(internalName))) {
                return resultResolveClass2;
            }
        }
        return resultResolveClass;
    }
}
