package org.jf.dexlib2.immutable.value;

import java.util.Collection;
import java.util.Set;
import org.jf.dexlib2.base.value.BaseAnnotationEncodedValue;
import org.jf.dexlib2.iface.AnnotationElement;
import org.jf.dexlib2.iface.value.AnnotationEncodedValue;
import org.jf.dexlib2.immutable.ImmutableAnnotationElement;
import org.jf.util.ImmutableUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableAnnotationEncodedValue extends BaseAnnotationEncodedValue implements ImmutableEncodedValue {
    protected final Set<? extends ImmutableAnnotationElement> elements;
    protected final String type;

    public ImmutableAnnotationEncodedValue(String str, Collection<? extends AnnotationElement> collection) {
        this.type = str;
        this.elements = ImmutableAnnotationElement.immutableSetOf(collection);
    }

    public ImmutableAnnotationEncodedValue(String str, Set<? extends ImmutableAnnotationElement> set) {
        this.type = str;
        this.elements = ImmutableUtils.nullToEmptySet(set);
    }

    public static ImmutableAnnotationEncodedValue of(AnnotationEncodedValue annotationEncodedValue) {
        if (annotationEncodedValue instanceof ImmutableAnnotationEncodedValue) {
            return (ImmutableAnnotationEncodedValue) annotationEncodedValue;
        }
        return new ImmutableAnnotationEncodedValue(annotationEncodedValue.getType(), annotationEncodedValue.getElements());
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue, org.jf.dexlib2.iface.BasicAnnotation
    public String getType() {
        return this.type;
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue, org.jf.dexlib2.iface.BasicAnnotation
    public Set<? extends ImmutableAnnotationElement> getElements() {
        return this.elements;
    }
}
