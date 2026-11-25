package org.eclipse.jdt.internal.compiler.apt.model;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.VariableElement;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.AptBinaryLocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.AptSourceLocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class VariableElementImpl extends ElementImpl implements VariableElement {
    VariableElementImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, VariableBinding variableBinding) {
        super(baseProcessingEnvImpl, variableBinding);
    }

    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return (R) elementVisitor.visitVariable(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    protected AnnotationBinding[] getAnnotationBindings() {
        return ((VariableBinding) this._binding).getAnnotations();
    }

    public Object getConstantValue() {
        VariableBinding variableBinding = (VariableBinding) this._binding;
        Constant constant = variableBinding.constant();
        if (constant == null || constant == Constant.NotAConstant) {
            return null;
        }
        switch (variableBinding.type.f626id) {
            case 2:
                return Character.valueOf(constant.charValue());
            case 3:
                return Byte.valueOf(constant.byteValue());
            case 4:
                return Short.valueOf(constant.shortValue());
            case 5:
                return Boolean.valueOf(constant.booleanValue());
            case 6:
            default:
                return null;
            case 7:
                return Long.valueOf(constant.longValue());
            case 8:
                return Double.valueOf(constant.doubleValue());
            case 9:
                return Float.valueOf(constant.floatValue());
            case 10:
                return Integer.valueOf(constant.intValue());
            case 11:
                return constant.stringValue();
        }
    }

    public List<? extends Element> getEnclosedElements() {
        return Collections.emptyList();
    }

    public Element getEnclosingElement() {
        if (this._binding instanceof FieldBinding) {
            return this._env.getFactory().newElement(((FieldBinding) this._binding).declaringClass);
        }
        if (this._binding instanceof AptSourceLocalVariableBinding) {
            return this._env.getFactory().newElement(((AptSourceLocalVariableBinding) this._binding).methodBinding);
        }
        if (this._binding instanceof AptBinaryLocalVariableBinding) {
            return this._env.getFactory().newElement(((AptBinaryLocalVariableBinding) this._binding).methodBinding);
        }
        return null;
    }

    public ElementKind getKind() {
        if (this._binding instanceof FieldBinding) {
            if ((((FieldBinding) this._binding).modifiers & 16384) != 0) {
                return ElementKind.ENUM_CONSTANT;
            }
            return ElementKind.FIELD;
        }
        return ElementKind.PARAMETER;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public Set<Modifier> getModifiers() {
        if (this._binding instanceof VariableBinding) {
            return Factory.getModifiers(((VariableBinding) this._binding).modifiers, getKind());
        }
        return Collections.emptySet();
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    PackageElement getPackage() {
        if (this._binding instanceof FieldBinding) {
            return this._env.getFactory().newPackageElement(((FieldBinding) this._binding).declaringClass.fPackage);
        }
        throw new UnsupportedOperationException("NYI: VariableElmentImpl.getPackage() for method parameter");
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public Name getSimpleName() {
        return new NameImpl(((VariableBinding) this._binding).f628name);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public boolean hides(Element element) {
        FieldBinding fieldBinding;
        if (this._binding instanceof FieldBinding) {
            ElementImpl elementImpl = (ElementImpl) element;
            if (!(elementImpl._binding instanceof FieldBinding)) {
                return false;
            }
            FieldBinding fieldBinding2 = (FieldBinding) elementImpl._binding;
            if (!fieldBinding2.isPrivate() && fieldBinding2 != (fieldBinding = (FieldBinding) this._binding) && CharOperation.equals(fieldBinding.f628name, fieldBinding2.f628name) && fieldBinding.declaringClass.findSuperTypeOriginatingFrom(fieldBinding2.declaringClass) != null) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public String toString() {
        return new String(((VariableBinding) this._binding).f628name);
    }
}
