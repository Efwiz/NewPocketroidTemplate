package org.jf.dexlib2.immutable;

import java.util.List;
import java.util.Set;
import org.jf.dexlib2.base.BaseMethodParameter;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.MethodParameter;
import org.jf.util.ImmutableConverter;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableMethodParameter extends BaseMethodParameter {
    private static final ImmutableConverter<ImmutableMethodParameter, MethodParameter> CONVERTER = new ImmutableConverter<ImmutableMethodParameter, MethodParameter>() { // from class: org.jf.dexlib2.immutable.ImmutableMethodParameter.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public boolean isImmutable(MethodParameter methodParameter) {
            return methodParameter instanceof ImmutableMethodParameter;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public ImmutableMethodParameter makeImmutable(MethodParameter methodParameter) {
            return ImmutableMethodParameter.of(methodParameter);
        }
    };
    protected final Set<? extends ImmutableAnnotation> annotations;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f1026name;
    protected final String type;

    @Override // org.jf.dexlib2.base.BaseMethodParameter, org.jf.dexlib2.iface.MethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
    public String getSignature() {
        return null;
    }

    public ImmutableMethodParameter(String str, Set<? extends Annotation> set, String str2) {
        this.type = str;
        this.annotations = ImmutableAnnotation.immutableSetOf(set);
        this.f1026name = str2;
    }

    public static ImmutableMethodParameter of(MethodParameter methodParameter) {
        if (methodParameter instanceof ImmutableMethodParameter) {
            return (ImmutableMethodParameter) methodParameter;
        }
        return new ImmutableMethodParameter(methodParameter.getType(), methodParameter.getAnnotations(), methodParameter.getName());
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
    public String getType() {
        return this.type;
    }

    @Override // org.jf.dexlib2.iface.MethodParameter
    public Set<? extends Annotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jf.dexlib2.iface.MethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
    public String getName() {
        return this.f1026name;
    }

    public static List<ImmutableMethodParameter> immutableListOf(Iterable<? extends MethodParameter> iterable) {
        return CONVERTER.toList(iterable);
    }
}
