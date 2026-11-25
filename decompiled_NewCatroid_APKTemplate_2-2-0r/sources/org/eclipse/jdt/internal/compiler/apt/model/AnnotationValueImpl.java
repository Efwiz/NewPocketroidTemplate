package org.eclipse.jdt.internal.compiler.apt.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.DoubleConstant;
import org.eclipse.jdt.internal.compiler.impl.FloatConstant;
import org.eclipse.jdt.internal.compiler.impl.LongConstant;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeIds;
import org.eclipse.jdt.internal.compiler.problem.ShouldNotImplement;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotationValueImpl implements AnnotationValue, TypeIds {
    private static final int T_AnnotationMirror = -1;
    private static final int T_ArrayType = -4;
    private static final int T_ClassObject = -3;
    private static final int T_EnumConstant = -2;
    private final BaseProcessingEnvImpl _env;
    private final int _kind;
    private final Object _value;

    public AnnotationValueImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, Object obj, TypeBinding typeBinding) {
        ArrayList arrayList;
        this._env = baseProcessingEnvImpl;
        int[] iArr = new int[1];
        if (typeBinding == null) {
            this._value = convertToMirrorType(obj, typeBinding, iArr);
            this._kind = iArr[0];
            return;
        }
        if (typeBinding.isArrayType()) {
            TypeBinding typeBindingElementsType = ((ArrayBinding) typeBinding).elementsType();
            if (obj instanceof Object[]) {
                Object[] objArr = (Object[]) obj;
                arrayList = new ArrayList(objArr.length);
                for (Object obj2 : objArr) {
                    arrayList.add(new AnnotationValueImpl(this._env, obj2, typeBindingElementsType));
                }
            } else {
                arrayList = new ArrayList(1);
                arrayList.add(new AnnotationValueImpl(baseProcessingEnvImpl, obj, typeBindingElementsType));
            }
            this._value = Collections.unmodifiableList(arrayList);
            this._kind = -4;
            return;
        }
        this._value = convertToMirrorType(obj, typeBinding, iArr);
        this._kind = iArr[0];
    }

    private Object convertToMirrorType(Object obj, TypeBinding typeBinding, int[] iArr) {
        if (typeBinding == null) {
            iArr[0] = 11;
            return "<error>";
        }
        boolean z = typeBinding instanceof BaseTypeBinding;
        if (z || typeBinding.f626id == 11) {
            if (obj == null) {
                if (z || typeBinding.f626id == 11) {
                    iArr[0] = 11;
                    return "<error>";
                }
                if (typeBinding.isAnnotationType()) {
                    iArr[0] = -1;
                    return this._env.getFactory().newAnnotationMirror(null);
                }
            } else if (obj instanceof Constant) {
                if (z) {
                    iArr[0] = ((BaseTypeBinding) typeBinding).f626id;
                } else if (typeBinding.f626id == 11) {
                    iArr[0] = ((Constant) obj).typeID();
                } else {
                    iArr[0] = 11;
                    return "<error>";
                }
                switch (iArr[0]) {
                    case 2:
                        return Character.valueOf(((Constant) obj).charValue());
                    case 3:
                        return Byte.valueOf(((Constant) obj).byteValue());
                    case 4:
                        return Short.valueOf(((Constant) obj).shortValue());
                    case 5:
                        return Boolean.valueOf(((Constant) obj).booleanValue());
                    case 7:
                        return Long.valueOf(((Constant) obj).longValue());
                    case 8:
                        return Double.valueOf(((Constant) obj).doubleValue());
                    case 9:
                        return Float.valueOf(((Constant) obj).floatValue());
                    case 10:
                        try {
                            if (!(obj instanceof LongConstant) && !(obj instanceof DoubleConstant) && !(obj instanceof FloatConstant)) {
                                return Integer.valueOf(((Constant) obj).intValue());
                            }
                            iArr[0] = 11;
                            return "<error>";
                        } catch (ShouldNotImplement unused) {
                            iArr[0] = 11;
                            return "<error>";
                        }
                    case 11:
                        return ((Constant) obj).stringValue();
                }
            }
        } else {
            if (typeBinding.isEnum()) {
                if (obj instanceof FieldBinding) {
                    iArr[0] = -2;
                    return this._env.getFactory().newElement((FieldBinding) obj);
                }
                iArr[0] = 11;
                return "<error>";
            }
            if (typeBinding.isAnnotationType()) {
                if (obj instanceof AnnotationBinding) {
                    iArr[0] = -1;
                    return this._env.getFactory().newAnnotationMirror((AnnotationBinding) obj);
                }
            } else if (obj instanceof TypeBinding) {
                iArr[0] = -3;
                return this._env.getFactory().newTypeMirror((TypeBinding) obj);
            }
        }
        iArr[0] = 11;
        return "<error>";
    }

    public <R, P> R accept(AnnotationValueVisitor<R, P> annotationValueVisitor, P p) {
        switch (this._kind) {
            case -4:
                return (R) annotationValueVisitor.visitArray((List) this._value, p);
            case -3:
                return (R) annotationValueVisitor.visitType((TypeMirror) this._value, p);
            case -2:
                return (R) annotationValueVisitor.visitEnumConstant((VariableElement) this._value, p);
            case -1:
                return (R) annotationValueVisitor.visitAnnotation((AnnotationMirror) this._value, p);
            case 0:
            case 1:
            case 6:
            default:
                return null;
            case 2:
                return (R) annotationValueVisitor.visitChar(((Character) this._value).charValue(), p);
            case 3:
                return (R) annotationValueVisitor.visitByte(((Byte) this._value).byteValue(), p);
            case 4:
                return (R) annotationValueVisitor.visitShort(((Short) this._value).shortValue(), p);
            case 5:
                return (R) annotationValueVisitor.visitBoolean(((Boolean) this._value).booleanValue(), p);
            case 7:
                return (R) annotationValueVisitor.visitLong(((Long) this._value).longValue(), p);
            case 8:
                return (R) annotationValueVisitor.visitDouble(((Double) this._value).doubleValue(), p);
            case 9:
                return (R) annotationValueVisitor.visitFloat(((Float) this._value).floatValue(), p);
            case 10:
                return (R) annotationValueVisitor.visitInt(((Integer) this._value).intValue(), p);
            case 11:
                return (R) annotationValueVisitor.visitString((String) this._value, p);
        }
    }

    public Object getValue() {
        return this._value;
    }

    public boolean equals(Object obj) {
        if (obj instanceof AnnotationValueImpl) {
            return this._value.equals(((AnnotationValueImpl) obj)._value);
        }
        return false;
    }

    public int hashCode() {
        return this._value.hashCode() + this._kind;
    }

    public String toString() {
        Object obj = this._value;
        if (obj == null) {
            return "null";
        }
        boolean z = true;
        if (obj instanceof String) {
            String str = (String) obj;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append('\"');
            for (int i = 0; i < str.length(); i++) {
                Util.appendEscapedChar(stringBuffer, str.charAt(i), true);
            }
            stringBuffer.append('\"');
            return stringBuffer.toString();
        }
        if (obj instanceof Character) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append('\'');
            Util.appendEscapedChar(stringBuffer2, ((Character) this._value).charValue(), false);
            stringBuffer2.append('\'');
            return stringBuffer2.toString();
        }
        if (obj instanceof VariableElement) {
            VariableElement variableElement = (VariableElement) obj;
            return String.valueOf(variableElement.asType().toString()) + "." + variableElement.getSimpleName();
        }
        if (obj instanceof Collection) {
            StringBuilder sb = new StringBuilder("{");
            for (AnnotationValue annotationValue : (Collection) obj) {
                if (!z) {
                    sb.append(", ");
                }
                sb.append(annotationValue.toString());
                z = false;
            }
            sb.append('}');
            return sb.toString();
        }
        if (obj instanceof TypeMirror) {
            return String.valueOf(this._value.toString()) + ".class";
        }
        return obj.toString();
    }
}
