package javaslang.match.model;

import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/* JADX INFO: loaded from: classes11.dex */
public class ParameterModel {
    private final Elements elementUtils;
    private final VariableElement variableElement;

    public ParameterModel(Elements elements, VariableElement variableElement) {
        this.elementUtils = elements;
        this.variableElement = variableElement;
    }

    public ClassModel getType() {
        return new ClassModel(this.elementUtils, this.variableElement.asType());
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof ParameterModel) && toString().equals(obj.toString()));
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return this.variableElement.toString();
    }
}
