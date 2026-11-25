package org.jf.dexlib2.immutable;

import java.util.Set;
import org.jf.dexlib2.base.BaseAnnotationElement;
import org.jf.dexlib2.iface.AnnotationElement;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory;
import org.jf.util.ImmutableConverter;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableAnnotationElement extends BaseAnnotationElement {
    private static final ImmutableConverter<ImmutableAnnotationElement, AnnotationElement> CONVERTER = new ImmutableConverter<ImmutableAnnotationElement, AnnotationElement>() { // from class: org.jf.dexlib2.immutable.ImmutableAnnotationElement.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public boolean isImmutable(AnnotationElement annotationElement) {
            return annotationElement instanceof ImmutableAnnotationElement;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public ImmutableAnnotationElement makeImmutable(AnnotationElement annotationElement) {
            return ImmutableAnnotationElement.of(annotationElement);
        }
    };

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f1023name;
    protected final ImmutableEncodedValue value;

    public ImmutableAnnotationElement(String str, EncodedValue encodedValue) {
        this.f1023name = str;
        this.value = ImmutableEncodedValueFactory.of(encodedValue);
    }

    public ImmutableAnnotationElement(String str, ImmutableEncodedValue immutableEncodedValue) {
        this.f1023name = str;
        this.value = immutableEncodedValue;
    }

    public static ImmutableAnnotationElement of(AnnotationElement annotationElement) {
        if (annotationElement instanceof ImmutableAnnotationElement) {
            return (ImmutableAnnotationElement) annotationElement;
        }
        return new ImmutableAnnotationElement(annotationElement.getName(), annotationElement.getValue());
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    public String getName() {
        return this.f1023name;
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    public EncodedValue getValue() {
        return this.value;
    }

    public static Set<ImmutableAnnotationElement> immutableSetOf(Iterable<? extends AnnotationElement> iterable) {
        return CONVERTER.toSet(iterable);
    }
}
