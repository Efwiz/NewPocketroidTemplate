package org.jf.dexlib2.immutable;

import java.util.Collection;
import java.util.Set;
import org.jf.dexlib2.base.BaseAnnotation;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.AnnotationElement;
import org.jf.util.ImmutableConverter;
import org.jf.util.ImmutableUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableAnnotation extends BaseAnnotation {
    private static final ImmutableConverter<ImmutableAnnotation, Annotation> CONVERTER = new ImmutableConverter<ImmutableAnnotation, Annotation>() { // from class: org.jf.dexlib2.immutable.ImmutableAnnotation.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public boolean isImmutable(Annotation annotation) {
            return annotation instanceof ImmutableAnnotation;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public ImmutableAnnotation makeImmutable(Annotation annotation) {
            return ImmutableAnnotation.of(annotation);
        }
    };
    protected final Set<? extends ImmutableAnnotationElement> elements;
    protected final String type;
    protected final int visibility;

    public ImmutableAnnotation(int i, String str, Collection<? extends AnnotationElement> collection) {
        this.visibility = i;
        this.type = str;
        this.elements = ImmutableAnnotationElement.immutableSetOf(collection);
    }

    public ImmutableAnnotation(int i, String str, Set<? extends ImmutableAnnotationElement> set) {
        this.visibility = i;
        this.type = str;
        this.elements = ImmutableUtils.nullToEmptySet(set);
    }

    public static ImmutableAnnotation of(Annotation annotation) {
        if (annotation instanceof ImmutableAnnotation) {
            return (ImmutableAnnotation) annotation;
        }
        return new ImmutableAnnotation(annotation.getVisibility(), annotation.getType(), annotation.getElements());
    }

    @Override // org.jf.dexlib2.iface.Annotation
    public int getVisibility() {
        return this.visibility;
    }

    @Override // org.jf.dexlib2.iface.Annotation, org.jf.dexlib2.iface.BasicAnnotation
    public String getType() {
        return this.type;
    }

    @Override // org.jf.dexlib2.iface.Annotation, org.jf.dexlib2.iface.BasicAnnotation
    public Set<? extends ImmutableAnnotationElement> getElements() {
        return this.elements;
    }

    public static Set<ImmutableAnnotation> immutableSetOf(Iterable<? extends Annotation> iterable) {
        return CONVERTER.toSet(iterable);
    }
}
