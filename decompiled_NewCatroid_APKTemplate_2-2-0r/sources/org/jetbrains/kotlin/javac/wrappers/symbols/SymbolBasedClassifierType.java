package org.jetbrains.kotlin.javac.wrappers.symbols;

import androidx.exifinterface.media.ExifInterface;
import com.sun.tools.javac.code.Symbol;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: symbolBasedTypes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u001d\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0012R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedClassifierType;", ExifInterface.GPS_DIRECTION_TRUE, "Ljavax/lang/model/type/TypeMirror;", "Lorg/jetbrains/kotlin/javac/wrappers/symbols/SymbolBasedType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "typeMirror", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Ljavax/lang/model/type/TypeMirror;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "classifier", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "getClassifier", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "classifier$delegate", "Lkotlin/Lazy;", "classifierQualifiedName", "", "getClassifierQualifiedName", "()Ljava/lang/String;", "isDeprecatedInJavaDoc", "", "()Z", "isFake", "isRaw", "presentableText", "getPresentableText", "typeArguments", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getTypeArguments", "()Ljava/util/List;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SymbolBasedClassifierType<T extends TypeMirror> extends SymbolBasedType<T> implements JavaClassifierType {

    /* JADX INFO: renamed from: classifier$delegate, reason: from kotlin metadata */
    private final Lazy classifier;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SymbolBasedClassifierType(final T typeMirror, final JavacWrapper javac) {
        super(typeMirror, javac, null);
        Intrinsics.checkNotNullParameter(typeMirror, "typeMirror");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.classifier = LazyKt.lazy(new Function0<JavaClassifier>() { // from class: org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedClassifierType$classifier$2

            /* JADX INFO: compiled from: symbolBasedTypes.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[TypeKind.values().length];
                    try {
                        iArr[TypeKind.DECLARED.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[TypeKind.TYPEVAR.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Incorrect types in method signature: (TT;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JavaClassifier invoke() {
                FakeSymbolBasedClass fakeSymbolBasedClass;
                TypeKind kind = typeMirror.getKind();
                int i = kind == null ? -1 : WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
                if (i != 1) {
                    if (i != 2) {
                        return null;
                    }
                    TypeVariable typeVariable = typeMirror;
                    Intrinsics.checkNotNull(typeVariable, "null cannot be cast to non-null type javax.lang.model.type.TypeVariable");
                    TypeParameterElement typeParameterElementAsElement = typeVariable.asElement();
                    Intrinsics.checkNotNull(typeParameterElementAsElement, "null cannot be cast to non-null type javax.lang.model.element.TypeParameterElement");
                    return new SymbolBasedTypeParameter(typeParameterElementAsElement, javac);
                }
                DeclaredType declaredType = typeMirror;
                Intrinsics.checkNotNull(declaredType, "null cannot be cast to non-null type javax.lang.model.type.DeclaredType");
                TypeElement typeElementAsElement = declaredType.asElement();
                Intrinsics.checkNotNull(typeElementAsElement, "null cannot be cast to non-null type com.sun.tools.javac.code.Symbol.ClassSymbol");
                TypeElement typeElement = (Symbol.ClassSymbol) typeElementAsElement;
                JavacWrapper javacWrapper = javac;
                TypeElement typeElement2 = typeElement;
                ClassId classIdComputeClassId = UtilsKt.computeClassId(typeElement2);
                if (classIdComputeClassId == null || (fakeSymbolBasedClass = JavacWrapper.findClass$default(javacWrapper, classIdComputeClassId, null, 2, null)) == null) {
                    fakeSymbolBasedClass = new FakeSymbolBasedClass(typeElement2, javacWrapper, classIdComputeClassId, ((Symbol.ClassSymbol) typeElement).classfile);
                }
                return fakeSymbolBasedClass;
            }
        });
    }

    private final boolean isFake() {
        return getClassifier() instanceof FakeSymbolBasedClass;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public JavaClassifier getClassifier() {
        return (JavaClassifier) this.classifier.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: getTypeArguments */
    public List<JavaType> mo7270getTypeArguments() {
        if (getTypeMirror().getKind() != TypeKind.DECLARED || isFake()) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        DeclaredType typeMirror = getTypeMirror();
        Intrinsics.checkNotNull(typeMirror, "null cannot be cast to non-null type javax.lang.model.type.DeclaredType");
        DeclaredType declaredType = typeMirror;
        boolean z = false;
        while (!z) {
            Element elementAsElement = declaredType.asElement();
            Intrinsics.checkNotNullExpressionValue(elementAsElement, "asElement(...)");
            if (UtilsKt.isStatic(elementAsElement)) {
                z = true;
            }
            List typeArguments = declaredType.getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
            List list = typeArguments;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(SymbolBasedType.INSTANCE.create((TypeMirror) it.next(), getJavac()));
            }
            arrayList.addAll(arrayList2);
            TypeMirror enclosingType = declaredType.getEnclosingType();
            declaredType = enclosingType instanceof DeclaredType ? (DeclaredType) enclosingType : null;
            if (declaredType == null) {
                return arrayList;
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    /* JADX INFO: renamed from: isRaw */
    public boolean mo7271isRaw() {
        List<JavaTypeParameter> typeParameters;
        List<JavaTypeParameter> typeParameters2;
        if (!(getTypeMirror() instanceof DeclaredType) || isFake()) {
            return false;
        }
        JavaClassifier classifier = getClassifier();
        JavaClass javaClass = classifier instanceof JavaClass ? (JavaClass) classifier : null;
        if (javaClass != null && (typeParameters2 = javaClass.mo7258getTypeParameters()) != null && typeParameters2.isEmpty()) {
            return false;
        }
        if (!getTypeMirror().getTypeArguments().isEmpty()) {
            JavaClassifier classifier2 = getClassifier();
            JavaClass javaClass2 = classifier2 instanceof JavaClass ? (JavaClass) classifier2 : null;
            if (javaClass2 != null && (typeParameters = javaClass2.mo7258getTypeParameters()) != null && typeParameters.size() == getTypeMirror().getTypeArguments().size()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getClassifierQualifiedName() {
        return getTypeMirror().toString();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClassifierType
    public String getPresentableText() {
        return getTypeMirror().toString();
    }

    @Override // org.jetbrains.kotlin.javac.wrappers.symbols.SymbolBasedType, org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return !isFake() && super.getIsDeprecatedInJavaDoc();
    }
}
