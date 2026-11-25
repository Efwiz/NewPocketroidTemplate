package org.jetbrains.kotlin.javac.wrappers.symbols;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.descriptors.Visibilities;
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
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FakeSymbolBasedClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B)\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u0012\u0010S\u001a\u0004\u0018\u00010!2\u0006\u00105\u001a\u00020\u001dH\u0016J\b\u0010T\u001a\u00020%H\u0016J\u0010\u0010U\u001a\u00020%2\u0006\u0010V\u001a\u00020WH\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0012R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0012R\u001d\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020!0 8F¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010&R\u0014\u0010'\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010&R\u0014\u0010(\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010&R\u0014\u0010)\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010&R\u0014\u0010*\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010&R\u0014\u0010+\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010&R\u0014\u0010,\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010&R\u0014\u0010-\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b-\u0010&R\u0016\u0010.\u001a\u0004\u0018\u00010/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u001a\u00102\u001a\b\u0012\u0004\u0012\u0002030\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u0010\u0012R\u0014\u00105\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u001d\u00108\u001a\u0004\u0018\u00010!8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b;\u0010<\u001a\u0004\b9\u0010:R\u001a\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b?\u0010\u0012R\u001a\u0010@\u001a\b\u0012\u0004\u0012\u00020A0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010\u0012R\u001a\u0010C\u001a\b\u0012\u0004\u0012\u00020>0\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010\u0012R\u001a\u0010E\u001a\b\u0012\u0004\u0012\u00020G0F8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u0010IR\u001d\u0010J\u001a\u0004\u0018\u00010K8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\bN\u0010<\u001a\u0004\bL\u0010MR\u0014\u0010O\u001a\u00020P8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010R¨\u0006X"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/FakeSymbolBasedClass;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClassifier;", "Ljavax/lang/model/element/TypeElement;", "Lorg/jetbrains/kotlin/javac/JavaClassWithClassId;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "file", "Ljavax/tools/JavaFileObject;", "(Ljavax/lang/model/element/TypeElement;Lorg/jetbrains/kotlin/javac/JavacWrapper;Lorg/jetbrains/kotlin/name/ClassId;Ljavax/tools/JavaFileObject;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "constructors", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", "getConstructors", "()Ljava/util/Collection;", "fields", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "getFields", "getFile", "()Ljavax/tools/JavaFileObject;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "innerClassNames", "Lorg/jetbrains/kotlin/name/Name;", "getInnerClassNames", "innerClasses", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getInnerClasses", "()Ljava/util/Map;", "isAbstract", "", "()Z", "isAnnotationType", "isEnum", "isFinal", "isInterface", "isRecord", "isSealed", "isStatic", "lightClassOriginKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getLightClassOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "methods", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "getMethods", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "outerClass", "getOuterClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "outerClass$delegate", "Lkotlin/Lazy;", "permittedTypes", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getPermittedTypes", "recordComponents", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "getRecordComponents", "supertypes", "getSupertypes", "typeParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getVirtualFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "virtualFile$delegate", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "findInnerClass", "hasDefaultConstructor", "isFromSourceCodeInScope", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeSymbolBasedClass extends SymbolBasedClassifier<TypeElement> implements JavaClassWithClassId {
    private final ClassId classId;
    private final JavaFileObject file;

    /* JADX INFO: renamed from: outerClass$delegate, reason: from kotlin metadata */
    private final Lazy outerClass;

    /* JADX INFO: renamed from: virtualFile$delegate, reason: from kotlin metadata */
    private final Lazy virtualFile;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public JavaClass findInnerClass(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public LightClassOriginKind getLightClassOriginKind() {
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: hasDefaultConstructor */
    public boolean mo7259hasDefaultConstructor() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    public boolean mo7260isAbstract() {
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isAnnotationType */
    public boolean mo7261isAnnotationType() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isEnum */
    public boolean mo7263isEnum() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    public boolean mo7264isFinal() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public boolean isFromSourceCodeInScope(SearchScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isInterface */
    public boolean mo7265isInterface() {
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

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    public boolean mo7267isStatic() {
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
    public FakeSymbolBasedClass(final TypeElement element, final JavacWrapper javac, ClassId classId, JavaFileObject javaFileObject) {
        super((Element) element, javac);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.classId = classId;
        this.file = javaFileObject;
        this.outerClass = LazyKt.lazy(new Function0<FakeSymbolBasedClass>() { // from class: org.jetbrains.kotlin.javac.wrappers.symbols.FakeSymbolBasedClass$outerClass$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FakeSymbolBasedClass invoke() {
                TypeElement enclosingElement = element.getEnclosingElement();
                if (enclosingElement == null) {
                    return null;
                }
                JavacWrapper javacWrapper = javac;
                FakeSymbolBasedClass fakeSymbolBasedClass = this;
                if (enclosingElement.asType().getKind() != TypeKind.DECLARED) {
                    return null;
                }
                TypeElement typeElement = enclosingElement;
                ClassId classId2 = fakeSymbolBasedClass.getClassId();
                return new FakeSymbolBasedClass(typeElement, javacWrapper, classId2 != null ? classId2.getOuterClassId() : null, fakeSymbolBasedClass.getFile());
            }
        });
        this.virtualFile = LazyKt.lazy(new Function0<VirtualFile>() { // from class: org.jetbrains.kotlin.javac.wrappers.symbols.FakeSymbolBasedClass$virtualFile$2
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
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameIdentifier = Name.identifier(((TypeElement) getElement()).getSimpleName().toString());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        return Visibilities.Public.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<JavaTypeParameter> mo7258getTypeParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public FqName getFqName() {
        return new FqName(((TypeElement) getElement()).getQualifiedName().toString());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getSupertypes() {
        return CollectionsKt.emptyList();
    }

    public final Map<Name, JavaClass> getInnerClasses() {
        return MapsKt.emptyMap();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public JavaClass getOuterClass() {
        return (JavaClass) this.outerClass.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getRecordComponents */
    public Collection<JavaRecordComponent> mo7257getRecordComponents() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getPermittedTypes() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getMethods */
    public Collection<JavaMethod> mo7255getMethods() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getFields */
    public Collection<JavaField> mo7254getFields() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getConstructors */
    public Collection<JavaConstructor> mo7253getConstructors() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<Name> getInnerClassNames() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public VirtualFile getVirtualFile() {
        return (VirtualFile) this.virtualFile.getValue();
    }
}
