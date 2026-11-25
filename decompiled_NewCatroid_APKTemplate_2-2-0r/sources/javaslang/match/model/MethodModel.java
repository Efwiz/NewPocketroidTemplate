package javaslang.match.model;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/* JADX INFO: loaded from: classes11.dex */
public class MethodModel {
    private final Elements elementUtils;
    private final ExecutableElement executableElement;

    public MethodModel(Elements elements, ExecutableElement executableElement) {
        this.elementUtils = elements;
        this.executableElement = executableElement;
    }

    public ExecutableElement getExecutableElement() {
        return this.executableElement;
    }

    public String getName() {
        return this.executableElement.getSimpleName().toString();
    }

    public ParameterModel getParameter(int i) {
        List parameters = this.executableElement.getParameters();
        if (i < 0 || i > parameters.size()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return new ParameterModel(this.elementUtils, (VariableElement) parameters.get(i));
    }

    public ClassModel getReturnType() {
        return new ClassModel(this.elementUtils, this.executableElement.getReturnType());
    }

    public List<TypeParameterModel> getTypeParameters() {
        return (List) this.executableElement.getTypeParameters().stream().map(new Function() { // from class: javaslang.match.model.MethodModel$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2893lambda$getTypeParameters$0$javaslangmatchmodelMethodModel((TypeParameterElement) obj);
            }
        }).collect(Collectors.toList());
    }

    /* JADX INFO: renamed from: lambda$getTypeParameters$0$javaslang-match-model-MethodModel, reason: not valid java name */
    /* synthetic */ TypeParameterModel m2893lambda$getTypeParameters$0$javaslangmatchmodelMethodModel(TypeParameterElement typeParameterElement) {
        return new TypeParameterModel(this.elementUtils, typeParameterElement.asType());
    }

    public <A extends Annotation> boolean isAnnotatedWith(Class<A> cls) {
        return this.executableElement.getAnnotationsByType(cls).length > 0;
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof MethodModel) && toString().equals(obj.toString()));
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return this.executableElement.toString();
    }
}
