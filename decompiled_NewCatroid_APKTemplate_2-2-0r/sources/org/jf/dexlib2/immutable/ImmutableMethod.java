package org.jf.dexlib2.immutable;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.jf.dexlib2.HiddenApiRestriction;
import org.jf.dexlib2.base.reference.BaseMethodReference;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.MethodParameter;
import org.jf.util.ImmutableConverter;
import org.jf.util.ImmutableUtils;
import org.jf.util.collection.ArraySet;
import org.jf.util.collection.ListUtil;
import org.jf.util.collection.SetUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableMethod extends BaseMethodReference implements Method {
    private static final ImmutableConverter<ImmutableMethod, Method> CONVERTER = new ImmutableConverter<ImmutableMethod, Method>() { // from class: org.jf.dexlib2.immutable.ImmutableMethod.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public boolean isImmutable(Method method) {
            return method instanceof ImmutableMethod;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public ImmutableMethod makeImmutable(Method method) {
            return ImmutableMethod.of(method);
        }
    };
    protected final int accessFlags;
    protected final Set<? extends ImmutableAnnotation> annotations;
    protected final String definingClass;
    protected final Set<HiddenApiRestriction> hiddenApiRestrictions;
    protected final ImmutableMethodImplementation methodImplementation;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f1025name;
    protected final List<? extends ImmutableMethodParameter> parameters;
    protected final String returnType;

    public ImmutableMethod(String str, String str2, Iterable<? extends MethodParameter> iterable, String str3, int i, Set<? extends Annotation> set, Set<HiddenApiRestriction> set2, MethodImplementation methodImplementation) {
        this.definingClass = str;
        this.f1025name = str2;
        this.parameters = ImmutableMethodParameter.immutableListOf(iterable);
        this.returnType = str3;
        this.accessFlags = i;
        this.annotations = ImmutableAnnotation.immutableSetOf(set);
        this.hiddenApiRestrictions = set2 == null ? SetUtil.of() : ArraySet.copyOf((Collection) set2);
        this.methodImplementation = ImmutableMethodImplementation.of(methodImplementation);
    }

    public ImmutableMethod(String str, String str2, List<? extends ImmutableMethodParameter> list, String str3, int i, Set<? extends ImmutableAnnotation> set, Set<HiddenApiRestriction> set2, ImmutableMethodImplementation immutableMethodImplementation) {
        this.definingClass = str;
        this.f1025name = str2;
        this.parameters = ListUtil.nullToEmptyList(list);
        this.returnType = str3;
        this.accessFlags = i;
        this.annotations = ImmutableUtils.nullToEmptySet(set);
        this.hiddenApiRestrictions = ImmutableUtils.nullToEmptySet(set2);
        this.methodImplementation = immutableMethodImplementation;
    }

    public static ImmutableMethod of(Method method) {
        if (method instanceof ImmutableMethod) {
            return (ImmutableMethod) method;
        }
        return new ImmutableMethod(method.getDefiningClass(), method.getName(), method.getParameters(), method.getReturnType(), method.getAccessFlags(), method.getAnnotations(), method.getHiddenApiRestrictions(), method.getImplementation());
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
    public String getDefiningClass() {
        return this.definingClass;
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
    public String getName() {
        return this.f1025name;
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    public List<? extends CharSequence> getParameterTypes() {
        return this.parameters;
    }

    @Override // org.jf.dexlib2.iface.Method
    public List<? extends ImmutableMethodParameter> getParameters() {
        return this.parameters;
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    public String getReturnType() {
        return this.returnType;
    }

    @Override // org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
    public int getAccessFlags() {
        return this.accessFlags;
    }

    @Override // org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Annotatable
    public Set<? extends ImmutableAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
    public Set<HiddenApiRestriction> getHiddenApiRestrictions() {
        return this.hiddenApiRestrictions;
    }

    @Override // org.jf.dexlib2.iface.Method
    public ImmutableMethodImplementation getImplementation() {
        return this.methodImplementation;
    }

    public static Set<ImmutableMethod> immutableSetOf(Iterable<? extends Method> iterable) {
        return CONVERTER.toSortedSet(iterable);
    }
}
