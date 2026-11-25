package org.jetbrains.kotlin.javac.wrappers.symbols;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
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
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.javac.JavaClassWithClassId;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SymbolBasedClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B)\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u0012\u0010\\\u001a\u0004\u0018\u00010*2\u0006\u0010?\u001a\u00020&H\u0016J\b\u0010]\u001a\u00020/H\u0016J\u0010\u0010^\u001a\u00020/2\u0006\u0010_\u001a\u00020`H\u0002J\u0010\u0010a\u001a\u00020/2\u0006\u0010b\u001a\u00020cH\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012RA\u0010\u0013\u001a(\u0012\u000e\b\u0001\u0012\n \u0016*\u0004\u0018\u00010\u00150\u0015 \u0016*\u0012\u0012\f\u0012\n \u0016*\u0004\u0018\u00010\u00150\u0015\u0018\u00010\u00170\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0012R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\u0012R'\u0010(\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020*0)8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b-\u0010\u001b\u001a\u0004\b+\u0010,R\u0014\u0010.\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u00100R\u0014\u00101\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u00100R\u0014\u00102\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u00100R\u0014\u00103\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u00100R\u0014\u00104\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u00100R\u0014\u00105\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b5\u00100R\u0014\u00106\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u00100R\u0014\u00107\u001a\u00020/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u00100R\u0016\u00108\u001a\u0004\u0018\u0001098VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u001a\u0010<\u001a\b\u0012\u0004\u0012\u00020=0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b>\u0010\u0012R\u0014\u0010?\u001a\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b@\u0010AR\u001d\u0010B\u001a\u0004\u0018\u00010*8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bE\u0010\u001b\u001a\u0004\bC\u0010DR\u001a\u0010F\u001a\b\u0012\u0004\u0012\u00020G0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u0010\u0012R\u001a\u0010I\u001a\b\u0012\u0004\u0012\u00020J0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010\u0012R!\u0010L\u001a\b\u0012\u0004\u0012\u00020G0\u000f8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bN\u0010\u001b\u001a\u0004\bM\u0010\u0012R!\u0010O\u001a\b\u0012\u0004\u0012\u00020P0\u00178VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bR\u0010\u001b\u001a\u0004\bQ\u0010\u0019R\u001d\u0010S\u001a\u0004\u0018\u00010T8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bW\u0010\u001b\u001a\u0004\bU\u0010VR\u0014\u0010X\u001a\u00020Y8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bZ\u0010[¨\u0006d"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClass;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClassifier;", "Ljavax/lang/model/element/TypeElement;", "Lorg/jetbrains/kotlin/javac/JavaClassWithClassId;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "file", "Ljavax/tools/JavaFileObject;", "(Ljavax/lang/model/element/TypeElement;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lorg/jetbrains/kotlin/name/ClassId;Ljavax/tools/JavaFileObject;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "constructors", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", "getConstructors", "()Ljava/util/Collection;", "enclosedElements", "", "Ljavax/lang/model/element/Element;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "getEnclosedElements", "()Ljava/util/List;", "enclosedElements$delegate", "Lkotlin/Lazy;", "fields", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "getFields", "getFile", "()Ljavax/tools/JavaFileObject;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "innerClassNames", "Lorg/jetbrains/kotlin/name/Name;", "getInnerClassNames", "innerClasses", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getInnerClasses", "()Ljava/util/Map;", "innerClasses$delegate", "isAbstract", "", "()Z", "isAnnotationType", "isEnum", "isFinal", "isInterface", "isRecord", "isSealed", "isStatic", "lightClassOriginKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getLightClassOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "methods", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "getMethods", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "outerClass", "getOuterClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "outerClass$delegate", "permittedTypes", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getPermittedTypes", "recordComponents", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "getRecordComponents", "supertypes", "getSupertypes", "supertypes$delegate", "typeParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "getTypeParameters", "typeParameters$delegate", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getVirtualFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "virtualFile$delegate", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "findInnerClass", "hasDefaultConstructor", "isEnumValuesOrValueOf", "method", "Ljavax/lang/model/element/ExecutableElement;", "isFromSourceCodeInScope", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedClass extends SymbolBasedClassifier<TypeElement> implements JavaClassWithClassId {
    private final ClassId classId;

    /* JADX INFO: renamed from: enclosedElements$delegate, reason: from kotlin metadata */
    private final Lazy enclosedElements;
    private final JavaFileObject file;

    /* JADX INFO: renamed from: innerClasses$delegate, reason: from kotlin metadata */
    private final Lazy innerClasses;

    /* JADX INFO: renamed from: outerClass$delegate, reason: from kotlin metadata */
    private final Lazy outerClass;

    /* JADX INFO: renamed from: supertypes$delegate, reason: from kotlin metadata */
    private final Lazy supertypes;

    /* JADX INFO: renamed from: typeParameters$delegate, reason: from kotlin metadata */
    private final Lazy typeParameters;

    /* JADX INFO: renamed from: virtualFile$delegate, reason: from kotlin metadata */
    private final Lazy virtualFile;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public LightClassOriginKind getLightClassOriginKind() {
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: hasDefaultConstructor */
    public boolean mo7259hasDefaultConstructor() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public boolean isFromSourceCodeInScope(SearchScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        return false;
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

    public final JavaFileObject getFile() {
        return this.file;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedClass(final TypeElement element, final JavacWrapper javac, ClassId classId, JavaFileObject javaFileObject) {
        super((Element) element, javac);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.classId = classId;
        this.file = javaFileObject;
        this.typeParameters = LazyKt.lazy(new Function0<List<? extends SymbolBasedTypeParameter>>() { // from class: org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedClass$typeParameters$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends SymbolBasedTypeParameter> invoke() {
                List typeParameters = element.getTypeParameters();
                Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                List<TypeParameterElement> list = typeParameters;
                JavacWrapper javacWrapper = javac;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (TypeParameterElement typeParameterElement : list) {
                    Intrinsics.checkNotNull(typeParameterElement);
                    arrayList.add(new SymbolBasedTypeParameter(typeParameterElement, javacWrapper));
                }
                return arrayList;
            }
        });
        this.supertypes = LazyKt.lazy(new Function0<ArrayList<SymbolBasedClassifierType<? extends TypeMirror>>>() { // from class: org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedClass$supertypes$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ArrayList<SymbolBasedClassifierType<? extends TypeMirror>> invoke() {
                SymbolBasedClassifierType<TypeMirror> java_lang_object;
                ArrayList arrayList = new ArrayList();
                TypeElement typeElement = element;
                TypeMirror superclass = typeElement.getSuperclass();
                if (superclass instanceof NoType) {
                    superclass = null;
                }
                if (superclass != null) {
                    arrayList.add(superclass);
                }
                arrayList.addAll(typeElement.getInterfaces());
                ArrayList<SymbolBasedClassifierType<? extends TypeMirror>> arrayList2 = new ArrayList<>();
                JavacWrapper javacWrapper = javac;
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    arrayList2.add(new SymbolBasedClassifierType<>((TypeMirror) it.next(), javacWrapper));
                }
                TypeElement typeElement2 = element;
                JavacWrapper javacWrapper2 = javac;
                ArrayList<SymbolBasedClassifierType<? extends TypeMirror>> arrayList3 = arrayList2;
                if (arrayList3.isEmpty() && !Intrinsics.areEqual(typeElement2.getQualifiedName().toString(), CommonClassNames.JAVA_LANG_OBJECT) && (java_lang_object = javacWrapper2.getJAVA_LANG_OBJECT()) != null) {
                    arrayList3.add(java_lang_object);
                }
                return arrayList3;
            }
        });
        this.innerClasses = LazyKt.lazy(new Function0<Map<Name, ? extends SymbolBasedClass>>() { // from class: org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedClass$innerClasses$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Name, ? extends SymbolBasedClass> invoke() {
                List enclosedElements = this.this$0.getEnclosedElements();
                Intrinsics.checkNotNullExpressionValue(enclosedElements, "access$getEnclosedElements(...)");
                List<TypeElement> listFilterIsInstance = CollectionsKt.filterIsInstance(enclosedElements, TypeElement.class);
                JavacWrapper javacWrapper = javac;
                SymbolBasedClass symbolBasedClass = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFilterIsInstance, 10));
                for (TypeElement typeElement : listFilterIsInstance) {
                    ClassId classId2 = symbolBasedClass.getClassId();
                    arrayList.add(new SymbolBasedClass(typeElement, javacWrapper, classId2 != null ? classId2.createNestedClassId(Name.identifier(typeElement.getSimpleName().toString())) : null, symbolBasedClass.getFile()));
                }
                ArrayList arrayList2 = arrayList;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
                for (Object obj : arrayList2) {
                    linkedHashMap.put(((JavaClass) obj).getName(), obj);
                }
                return linkedHashMap;
            }
        });
        this.outerClass = LazyKt.lazy(new Function0<SymbolBasedClass>() { // from class: org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedClass$outerClass$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SymbolBasedClass invoke() {
                TypeElement enclosingElement = element.getEnclosingElement();
                if (enclosingElement == null) {
                    return null;
                }
                JavacWrapper javacWrapper = javac;
                SymbolBasedClass symbolBasedClass = this;
                if (enclosingElement.asType().getKind() != TypeKind.DECLARED) {
                    return null;
                }
                TypeElement typeElement = enclosingElement;
                ClassId classId2 = symbolBasedClass.getClassId();
                return new SymbolBasedClass(typeElement, javacWrapper, classId2 != null ? classId2.getOuterClassId() : null, symbolBasedClass.getFile());
            }
        });
        this.virtualFile = LazyKt.lazy(new Function0<VirtualFile>() { // from class: org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedClass$virtualFile$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final VirtualFile invoke() {
                JavaFileObject file = this.this$0.getFile();
                if (file != null) {
                    return javac.toVirtualFile(file);
                }
                return null;
            }
        });
        this.enclosedElements = LazyKt.lazy(new Function0<List<? extends Element>>() { // from class: org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedClass$enclosedElements$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends Element> invoke() {
                return element.getEnclosedElements();
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameIdentifier = Name.identifier(((TypeElement) getElement()).getSimpleName().toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [javax.lang.model.element.Element] */
    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    public boolean mo7260isAbstract() {
        return UtilsKt.isAbstract(getElement());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [javax.lang.model.element.Element] */
    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    public boolean mo7267isStatic() {
        return UtilsKt.isStatic(getElement());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [javax.lang.model.element.Element] */
    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    public boolean mo7264isFinal() {
        return UtilsKt.isFinal(getElement());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [javax.lang.model.element.Element] */
    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        return UtilsKt.getVisibility((Element) getElement());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<JavaTypeParameter> mo7258getTypeParameters() {
        return (List) this.typeParameters.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public FqName getFqName() {
        return new FqName(((TypeElement) getElement()).getQualifiedName().toString());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getSupertypes() {
        return (Collection) this.supertypes.getValue();
    }

    public final Map<Name, JavaClass> getInnerClasses() {
        return (Map) this.innerClasses.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public JavaClass getOuterClass() {
        return (JavaClass) this.outerClass.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isInterface */
    public boolean mo7265isInterface() {
        return ((TypeElement) getElement()).getKind() == ElementKind.INTERFACE;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isAnnotationType */
    public boolean mo7261isAnnotationType() {
        return ((TypeElement) getElement()).getKind() == ElementKind.ANNOTATION_TYPE;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isEnum */
    public boolean mo7263isEnum() {
        return ((TypeElement) getElement()).getKind() == ElementKind.ENUM;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getPermittedTypes() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getMethods */
    public Collection<JavaMethod> mo7255getMethods() {
        List<? extends Element> enclosedElements = getEnclosedElements();
        Intrinsics.checkNotNullExpressionValue(enclosedElements, "<get-enclosedElements>(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : enclosedElements) {
            Element element = (Element) obj;
            if (element.getKind() == ElementKind.METHOD) {
                Intrinsics.checkNotNull(element, "null cannot be cast to non-null type javax.lang.model.element.ExecutableElement");
                if (!isEnumValuesOrValueOf((ExecutableElement) element)) {
                    arrayList.add(obj);
                }
            }
        }
        ArrayList<ExecutableElement> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (ExecutableElement executableElement : arrayList2) {
            Intrinsics.checkNotNull(executableElement, "null cannot be cast to non-null type javax.lang.model.element.ExecutableElement");
            arrayList3.add(new SymbolBasedMethod(executableElement, this, getJavac()));
        }
        return arrayList3;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x004e  */
    private final boolean isEnumValuesOrValueOf(ExecutableElement method) {
        boolean zIsEmpty;
        if (!mo7263isEnum()) {
            return false;
        }
        String string = method.getSimpleName().toString();
        if (Intrinsics.areEqual(string, CompoundEntry.NAME_values)) {
            zIsEmpty = method.getParameters().isEmpty();
        } else if (Intrinsics.areEqual(string, "valueOf")) {
            List parameters = method.getParameters();
            if (parameters.size() == 1) {
                Intrinsics.checkNotNull(parameters);
                if (Intrinsics.areEqual(((VariableElement) CollectionsKt.first(parameters)).asType().toString(), CommonClassNames.JAVA_LANG_STRING)) {
                    zIsEmpty = true;
                } else {
                    zIsEmpty = false;
                }
            } else {
                zIsEmpty = false;
            }
        } else {
            zIsEmpty = false;
        }
        return zIsEmpty;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getFields */
    public Collection<JavaField> mo7254getFields() {
        List<? extends Element> enclosedElements = getEnclosedElements();
        Intrinsics.checkNotNullExpressionValue(enclosedElements, "<get-enclosedElements>(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : enclosedElements) {
            Element element = (Element) obj;
            if (element.getKind().isField() && Name.isValidIdentifier(element.getSimpleName().toString())) {
                arrayList.add(obj);
            }
        }
        ArrayList<VariableElement> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (VariableElement variableElement : arrayList2) {
            Intrinsics.checkNotNull(variableElement, "null cannot be cast to non-null type javax.lang.model.element.VariableElement");
            arrayList3.add(new SymbolBasedField(variableElement, this, getJavac()));
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getConstructors */
    public Collection<JavaConstructor> mo7253getConstructors() {
        List<? extends Element> enclosedElements = getEnclosedElements();
        Intrinsics.checkNotNullExpressionValue(enclosedElements, "<get-enclosedElements>(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : enclosedElements) {
            if (((Element) obj).getKind() == ElementKind.CONSTRUCTOR) {
                arrayList.add(obj);
            }
        }
        ArrayList<ExecutableElement> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (ExecutableElement executableElement : arrayList2) {
            Intrinsics.checkNotNull(executableElement, "null cannot be cast to non-null type javax.lang.model.element.ExecutableElement");
            arrayList3.add(new SymbolBasedConstructor(executableElement, this, getJavac()));
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getRecordComponents */
    public Collection<JavaRecordComponent> mo7257getRecordComponents() {
        return CollectionsKt.emptyList();
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
    public JavaClass findInnerClass(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getInnerClasses().get(name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<? extends Element> getEnclosedElements() {
        return (List) this.enclosedElements.getValue();
    }
}
