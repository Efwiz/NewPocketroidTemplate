package org.jetbrains.kotlin.javac.wrappers.trees;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.javac.JavaClassWithClassId;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TreeBasedClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B1\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rJ\u0012\u0010X\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0012\u0010Y\u001a\u0004\u0018\u00010\u00002\u0006\u0010<\u001a\u00020\"H\u0016J\b\u0010Z\u001a\u00020*H\u0016J\u0010\u0010[\u001a\u00020*2\u0006\u0010\\\u001a\u00020]H\u0016R!\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0012R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0012R\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0012R'\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00000%8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\u0014\u001a\u0004\b&\u0010'R\u0014\u0010)\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010+R\u0014\u0010,\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010+R\u0014\u0010-\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b-\u0010+R\u0014\u0010.\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010+R\u0014\u0010/\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u0010+R\u0014\u00100\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u0010+R\u0014\u00101\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u0010+R\u0014\u00102\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u0010+R\u0014\u00103\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u0010+R\u0014\u00104\u001a\u00020*8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u0010+R\u0016\u00105\u001a\u0004\u0018\u0001068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u001a\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b;\u0010\u0012R\u0014\u0010<\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u001a\u0010A\u001a\b\u0012\u0004\u0012\u00020B0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bC\u0010\u0012R\u001a\u0010D\u001a\b\u0012\u0004\u0012\u00020E0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010\u0012R!\u0010G\u001a\b\u0012\u0004\u0012\u00020B0\u000f8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bI\u0010\u0014\u001a\u0004\bH\u0010\u0012R\u001a\u0010J\u001a\b\u0012\u0004\u0012\u00020L0K8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010NR\u001d\u0010O\u001a\u0004\u0018\u00010P8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bS\u0010\u0014\u001a\u0004\bQ\u0010RR\u0014\u0010T\u001a\u00020U8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bV\u0010W¨\u0006^"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedClass;", "Lorg/jetbrains/kotlin/javac/wrappers/trees/TreeBasedElement;", "Lcom/sun/tools/javac/tree/JCTree$JCClassDecl;", "Lorg/jetbrains/kotlin/javac/JavaClassWithClassId;", Constants.TYPE_TREE, "compilationUnit", "Lcom/sun/source/tree/CompilationUnitTree;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "outerClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "(Lcom/sun/tools/javac/tree/JCTree$JCClassDecl;Lcom/sun/source/tree/CompilationUnitTree;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotations$delegate", "Lkotlin/Lazy;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "constructors", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", "getConstructors", "fields", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "getFields", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "innerClassNames", "Lorg/jetbrains/kotlin/name/Name;", "getInnerClassNames", "innerClasses", "", "getInnerClasses", "()Ljava/util/Map;", "innerClasses$delegate", "isAbstract", "", "()Z", "isAnnotationType", "isDeprecatedInJavaDoc", "isEnum", "isFinal", "isFromSource", "isInterface", "isRecord", "isSealed", "isStatic", "lightClassOriginKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getLightClassOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "methods", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "getMethods", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOuterClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "permittedTypes", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getPermittedTypes", "recordComponents", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "getRecordComponents", "supertypes", "getSupertypes", "supertypes$delegate", "typeParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getVirtualFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "virtualFile$delegate", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "findAnnotation", "findInnerClass", "hasDefaultConstructor", "isFromSourceCodeInScope", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TreeBasedClass extends TreeBasedElement<JCTree.JCClassDecl> implements JavaClassWithClassId {

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final Lazy annotations;
    private final ClassId classId;

    /* JADX INFO: renamed from: innerClasses$delegate, reason: from kotlin metadata */
    private final Lazy innerClasses;
    private final JavaClass outerClass;

    /* JADX INFO: renamed from: supertypes$delegate, reason: from kotlin metadata */
    private final Lazy supertypes;

    /* JADX INFO: renamed from: virtualFile$delegate, reason: from kotlin metadata */
    private final Lazy virtualFile;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public LightClassOriginKind getLightClassOriginKind() {
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public boolean isFromSourceCodeInScope(SearchScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isRecord */
    public boolean mo7266isRecord() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public boolean isSealed() {
        return false;
    }

    @Override // org.jetbrains.kotlin.javac.JavaClassWithClassId
    public ClassId getClassId() {
        return this.classId;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public JavaClass getOuterClass() {
        return this.outerClass;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TreeBasedClass(final JCTree.JCClassDecl tree, final CompilationUnitTree compilationUnit, final JavacWrapper javac, ClassId classId, JavaClass javaClass) {
        super((JCTree) tree, compilationUnit, javac);
        Intrinsics.checkNotNullParameter(tree, "tree");
        Intrinsics.checkNotNullParameter(compilationUnit, "compilationUnit");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.classId = classId;
        this.outerClass = javaClass;
        this.annotations = LazyKt.lazy(new Function0<List<? extends TreeBasedAnnotation>>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedClass$annotations$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends TreeBasedAnnotation> invoke() {
                Collection<JCTree.JCAnnotation> collectionAnnotations = UtilsKt.annotations(tree);
                CompilationUnitTree compilationUnitTree = compilationUnit;
                JavacWrapper javacWrapper = javac;
                TreeBasedClass treeBasedClass = this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionAnnotations, 10));
                Iterator<T> it = collectionAnnotations.iterator();
                while (it.hasNext()) {
                    arrayList.add(new TreeBasedAnnotation((JCTree.JCAnnotation) it.next(), compilationUnitTree, javacWrapper, treeBasedClass));
                }
                return arrayList;
            }
        });
        this.supertypes = LazyKt.lazy(new Function0<ArrayList<JavaClassifierType>>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedClass$supertypes$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ArrayList<JavaClassifierType> invoke() {
                SymbolBasedClassifierType<TypeMirror> java_lang_annotation_annotation;
                SymbolBasedClassifierType<TypeMirror> java_lang_object;
                ArrayList<JavaClassifierType> arrayList = new ArrayList<>();
                TreeBasedClass treeBasedClass = this.this$0;
                JavacWrapper javacWrapper = javac;
                JCTree.JCClassDecl jCClassDecl = tree;
                CompilationUnitTree compilationUnitTree = compilationUnit;
                if (treeBasedClass.mo7263isEnum()) {
                    arrayList.add(new EnumSupertype(treeBasedClass, javacWrapper));
                } else if (treeBasedClass.mo7261isAnnotationType() && (java_lang_annotation_annotation = javacWrapper.getJAVA_LANG_ANNOTATION_ANNOTATION()) != null) {
                    arrayList.add(java_lang_annotation_annotation);
                }
                JCTree jCTree = jCClassDecl.extending;
                if (jCTree != null) {
                    Intrinsics.checkNotNull(jCTree);
                    JavaType javaTypeCreate = TreeBasedType.INSTANCE.create(jCTree, compilationUnitTree, javacWrapper, CollectionsKt.emptyList(), treeBasedClass);
                    JavaClassifierType javaClassifierType = javaTypeCreate instanceof JavaClassifierType ? (JavaClassifierType) javaTypeCreate : null;
                    if (javaClassifierType != null) {
                        arrayList.add(javaClassifierType);
                    }
                }
                if (arrayList.isEmpty() && !treeBasedClass.mo7265isInterface() && (java_lang_object = javacWrapper.getJAVA_LANG_OBJECT()) != null) {
                    arrayList.add(java_lang_object);
                }
                Iterable<JCTree> iterable = jCClassDecl.implementing;
                if (iterable != null) {
                    Intrinsics.checkNotNull(iterable);
                    for (JCTree jCTree2 : iterable) {
                        TreeBasedType.Companion companion = TreeBasedType.INSTANCE;
                        Intrinsics.checkNotNull(jCTree2);
                        JavaType javaTypeCreate2 = companion.create(jCTree2, compilationUnitTree, javacWrapper, CollectionsKt.emptyList(), treeBasedClass);
                        JavaClassifierType javaClassifierType2 = javaTypeCreate2 instanceof JavaClassifierType ? (JavaClassifierType) javaTypeCreate2 : null;
                        if (javaClassifierType2 != null) {
                            arrayList.add(javaClassifierType2);
                        }
                    }
                }
                return arrayList;
            }
        });
        this.innerClasses = LazyKt.lazy(new Function0<Map<Name, ? extends TreeBasedClass>>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedClass$innerClasses$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Name, ? extends TreeBasedClass> invoke() {
                Iterable members = tree.getMembers();
                Intrinsics.checkNotNullExpressionValue(members, "getMembers(...)");
                List<JCTree.JCClassDecl> listFilterIsInstance = CollectionsKt.filterIsInstance(members, JCTree.JCClassDecl.class);
                CompilationUnitTree compilationUnitTree = compilationUnit;
                JavacWrapper javacWrapper = javac;
                TreeBasedClass treeBasedClass = this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFilterIsInstance, 10));
                for (JCTree.JCClassDecl jCClassDecl : listFilterIsInstance) {
                    ClassId classId2 = treeBasedClass.getClassId();
                    arrayList.add(new TreeBasedClass(jCClassDecl, compilationUnitTree, javacWrapper, classId2 != null ? classId2.createNestedClassId(Name.identifier(jCClassDecl.getSimpleName().toString())) : null, treeBasedClass));
                }
                ArrayList arrayList2 = arrayList;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
                for (Object obj : arrayList2) {
                    linkedHashMap.put(((JavaClass) obj).getName(), obj);
                }
                return linkedHashMap;
            }
        });
        this.virtualFile = LazyKt.lazy(new Function0<VirtualFile>() { // from class: org.jetbrains.kotlin.javac.wrappers.trees.TreeBasedClass$virtualFile$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final VirtualFile invoke() {
                JavacWrapper javacWrapper = javac;
                JavaFileObject sourceFile = compilationUnit.getSourceFile();
                Intrinsics.checkNotNullExpressionValue(sourceFile, "getSourceFile(...)");
                return javacWrapper.toVirtualFile(sourceFile);
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameIdentifier = Name.identifier(getTree().getSimpleName().toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return (Collection) this.annotations.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public JavaAnnotation mo7251findAnnotation(FqName fqName) {
        Object obj;
        Object next;
        ClassId classId;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Iterator<T> it = mo7252getAnnotations().iterator();
        do {
            obj = null;
            if (it.hasNext()) {
                next = it.next();
                classId = ((JavaAnnotation) next).getClassId();
            }
            return (JavaAnnotation) obj;
        } while (!Intrinsics.areEqual(classId != null ? classId.asSingleFqName() : null, fqName));
        obj = next;
        return (JavaAnnotation) obj;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return getJavac().isDeprecatedInJavaDoc(getTree(), getCompilationUnit());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    public boolean mo7260isAbstract() {
        JCTree.JCModifiers modifiers = getTree().getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        if (!UtilsKt.isAbstract(modifiers)) {
            if (mo7261isAnnotationType() || mo7263isEnum()) {
                Collection<JavaMethod> collectionMo7255getMethods = mo7255getMethods();
                if (!(collectionMo7255getMethods instanceof Collection) || !collectionMo7255getMethods.isEmpty()) {
                    Iterator<T> it = collectionMo7255getMethods.iterator();
                    while (it.hasNext()) {
                        if (((JavaMethod) it.next()).mo7260isAbstract()) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    public boolean mo7267isStatic() {
        if (!mo7263isEnum() && !mo7265isInterface()) {
            JavaClass outerClass = getOuterClass();
            if (!(outerClass != null ? outerClass.mo7265isInterface() : false)) {
                JCTree.JCModifiers modifiers = getTree().getModifiers();
                Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
                if (!UtilsKt.isStatic(modifiers)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    public boolean mo7264isFinal() {
        if (!mo7263isEnum()) {
            JCTree.JCModifiers modifiers = getTree().getModifiers();
            Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
            if (!UtilsKt.isFinal(modifiers)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        JavaClass outerClass = getOuterClass();
        if (outerClass != null && outerClass.mo7265isInterface()) {
            return Visibilities.Public.INSTANCE;
        }
        JCTree.JCModifiers modifiers = getTree().getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        return UtilsKt.getVisibility(modifiers);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<JavaTypeParameter> mo7258getTypeParameters() {
        Iterable typeParameters = getTree().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        Iterable<JCTree.JCTypeParameter> iterable = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (JCTree.JCTypeParameter jCTypeParameter : iterable) {
            Intrinsics.checkNotNull(jCTypeParameter);
            arrayList.add(new TreeBasedTypeParameter(jCTypeParameter, getCompilationUnit(), getJavac(), this));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public FqName getFqName() {
        ClassId classId = getClassId();
        FqName fqNameAsSingleFqName = classId != null ? classId.asSingleFqName() : null;
        if (fqNameAsSingleFqName != null) {
            return fqNameAsSingleFqName;
        }
        throw new UnsupportedOperationException("classId of " + getName() + " is null");
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getSupertypes() {
        return (Collection) this.supertypes.getValue();
    }

    public final Map<Name, TreeBasedClass> getInnerClasses() {
        return (Map) this.innerClasses.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isInterface */
    public boolean mo7265isInterface() {
        return (getTree().getModifiers().flags & 512) != 0;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isAnnotationType */
    public boolean mo7261isAnnotationType() {
        return (getTree().getModifiers().flags & 8192) != 0;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isEnum */
    public boolean mo7263isEnum() {
        return (getTree().getModifiers().flags & 16384) != 0;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getPermittedTypes() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getMethods */
    public Collection<JavaMethod> mo7255getMethods() {
        Iterable members = getTree().getMembers();
        Intrinsics.checkNotNullExpressionValue(members, "getMembers(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : members) {
            JCTree jCTree = (JCTree) obj;
            if (jCTree.getKind() == Tree.Kind.METHOD && !TreeInfo.isConstructor(jCTree)) {
                arrayList.add(obj);
            }
        }
        ArrayList<JCTree.JCMethodDecl> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (JCTree.JCMethodDecl jCMethodDecl : arrayList2) {
            Intrinsics.checkNotNull(jCMethodDecl, "null cannot be cast to non-null type com.sun.tools.javac.tree.JCTree.JCMethodDecl");
            arrayList3.add(new TreeBasedMethod(jCMethodDecl, getCompilationUnit(), this, getJavac()));
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getFields */
    public Collection<JavaField> mo7254getFields() {
        Iterable members = getTree().getMembers();
        Intrinsics.checkNotNullExpressionValue(members, "getMembers(...)");
        List listFilterIsInstance = CollectionsKt.filterIsInstance(members, JCTree.JCVariableDecl.class);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFilterIsInstance, 10));
        Iterator it = listFilterIsInstance.iterator();
        while (it.hasNext()) {
            arrayList.add(new TreeBasedField((JCTree.JCVariableDecl) it.next(), getCompilationUnit(), this, getJavac()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getConstructors */
    public Collection<JavaConstructor> mo7253getConstructors() {
        Iterable members = getTree().getMembers();
        Intrinsics.checkNotNullExpressionValue(members, "getMembers(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : members) {
            if (TreeInfo.isConstructor((JCTree) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList<JCTree.JCMethodDecl> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (JCTree.JCMethodDecl jCMethodDecl : arrayList2) {
            Intrinsics.checkNotNull(jCMethodDecl, "null cannot be cast to non-null type com.sun.tools.javac.tree.JCTree.JCMethodDecl");
            arrayList3.add(new TreeBasedConstructor(jCMethodDecl, getCompilationUnit(), this, getJavac()));
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getRecordComponents */
    public Collection<JavaRecordComponent> mo7257getRecordComponents() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: hasDefaultConstructor */
    public boolean mo7259hasDefaultConstructor() {
        return !mo7265isInterface() && mo7253getConstructors().isEmpty();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<Name> getInnerClassNames() {
        return getInnerClasses().keySet();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public VirtualFile getVirtualFile() {
        return (VirtualFile) this.virtualFile.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public TreeBasedClass findInnerClass(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getInnerClasses().get(name2);
    }
}
