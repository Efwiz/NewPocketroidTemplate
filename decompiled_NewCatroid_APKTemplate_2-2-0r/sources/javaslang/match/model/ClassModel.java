package javaslang.match.model;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import net.bytebuddy.description.method.MethodDescription;

/* JADX INFO: loaded from: classes11.dex */
public class ClassModel {
    private final DeclaredType declaredType;
    private final Elements elementUtils;

    public static ClassModel of(Elements elements, TypeElement typeElement) {
        return new ClassModel(elements, typeElement.asType());
    }

    public ClassModel(Elements elements, DeclaredType declaredType) {
        this.elementUtils = elements;
        this.declaredType = declaredType;
    }

    public TypeElement typeElement() {
        return this.declaredType.asElement();
    }

    public String getClassName() {
        String fullQualifiedName = getFullQualifiedName();
        return hasDefaultPackage() ? fullQualifiedName : fullQualifiedName.substring(getPackageName().length() + 1);
    }

    public String getFullQualifiedName() {
        return typeElement().getQualifiedName().toString();
    }

    public List<MethodModel> getMethods() {
        return (List) typeElement().getEnclosedElements().stream().filter(new Predicate() { // from class: javaslang.match.model.ClassModel$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ClassModel.lambda$getMethods$0((Element) obj);
            }
        }).map(new Function() { // from class: javaslang.match.model.ClassModel$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2891lambda$getMethods$1$javaslangmatchmodelClassModel((Element) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ boolean lambda$getMethods$0(Element element) {
        String string = element.getSimpleName().toString();
        return (!(element instanceof ExecutableElement) || string.isEmpty() || "<init>".equals(string) || MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME.equals(string)) ? false : true;
    }

    /* JADX INFO: renamed from: lambda$getMethods$1$javaslang-match-model-ClassModel, reason: not valid java name */
    /* synthetic */ MethodModel m2891lambda$getMethods$1$javaslangmatchmodelClassModel(Element element) {
        return new MethodModel(this.elementUtils, (ExecutableElement) element);
    }

    public String getPackageName() {
        return this.elementUtils.getPackageOf(typeElement()).getQualifiedName().toString();
    }

    public List<TypeParameterModel> getTypeParameters() {
        return (List) this.declaredType.getTypeArguments().stream().map(new Function() { // from class: javaslang.match.model.ClassModel$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2892lambda$getTypeParameters$2$javaslangmatchmodelClassModel((TypeMirror) obj);
            }
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: lambda$getTypeParameters$2$javaslang-match-model-ClassModel, reason: not valid java name */
    /* synthetic */ TypeParameterModel m2892lambda$getTypeParameters$2$javaslangmatchmodelClassModel(TypeMirror typeMirror) {
        return new TypeParameterModel(this.elementUtils, typeMirror);
    }

    public boolean hasDefaultPackage() {
        return this.elementUtils.getPackageOf(typeElement()).isUnnamed();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof ClassModel) && toString().equals(obj.toString()));
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return this.declaredType.toString();
    }
}
