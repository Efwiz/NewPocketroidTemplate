package org.jf.dexlib2.immutable;

import java.util.Collection;
import java.util.Set;
import org.jf.dexlib2.HiddenApiRestriction;
import org.jf.dexlib2.base.reference.BaseFieldReference;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory;
import org.jf.util.ImmutableConverter;
import org.jf.util.ImmutableUtils;
import org.jf.util.collection.ArraySet;
import org.jf.util.collection.SetUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableField extends BaseFieldReference implements Field {
    private static final ImmutableConverter<ImmutableField, Field> CONVERTER = new ImmutableConverter<ImmutableField, Field>() { // from class: org.jf.dexlib2.immutable.ImmutableField.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public boolean isImmutable(Field field) {
            return field instanceof ImmutableField;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public ImmutableField makeImmutable(Field field) {
            return ImmutableField.of(field);
        }
    };
    protected final int accessFlags;
    protected final Set<? extends ImmutableAnnotation> annotations;
    protected final String definingClass;
    protected final Set<HiddenApiRestriction> hiddenApiRestrictions;
    protected final ImmutableEncodedValue initialValue;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f1024name;
    protected final String type;

    public ImmutableField(String str, String str2, String str3, int i, EncodedValue encodedValue, Collection<? extends Annotation> collection, Set<HiddenApiRestriction> set) {
        this.definingClass = str;
        this.f1024name = str2;
        this.type = str3;
        this.accessFlags = i;
        this.initialValue = ImmutableEncodedValueFactory.ofNullable(encodedValue);
        this.annotations = ImmutableAnnotation.immutableSetOf(collection);
        this.hiddenApiRestrictions = set == null ? SetUtil.of() : ArraySet.copyOf((Collection) set);
    }

    public ImmutableField(String str, String str2, String str3, int i, ImmutableEncodedValue immutableEncodedValue, Set<? extends ImmutableAnnotation> set, Set<HiddenApiRestriction> set2) {
        this.definingClass = str;
        this.f1024name = str2;
        this.type = str3;
        this.accessFlags = i;
        this.initialValue = immutableEncodedValue;
        this.annotations = ImmutableUtils.nullToEmptySet(set);
        this.hiddenApiRestrictions = ImmutableUtils.nullToEmptySet(set2);
    }

    public static ImmutableField of(Field field) {
        if (field instanceof ImmutableField) {
            return (ImmutableField) field;
        }
        return new ImmutableField(field.getDefiningClass(), field.getName(), field.getType(), field.getAccessFlags(), field.getInitialValue(), field.getAnnotations(), field.getHiddenApiRestrictions());
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public String getDefiningClass() {
        return this.definingClass;
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public String getName() {
        return this.f1024name;
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    public String getType() {
        return this.type;
    }

    @Override // org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public int getAccessFlags() {
        return this.accessFlags;
    }

    @Override // org.jf.dexlib2.iface.Field
    public EncodedValue getInitialValue() {
        return this.initialValue;
    }

    @Override // org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Annotatable
    public Set<? extends ImmutableAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public Set<HiddenApiRestriction> getHiddenApiRestrictions() {
        return this.hiddenApiRestrictions;
    }

    public static Set<ImmutableField> immutableSetOf(Iterable<? extends Field> iterable) {
        return CONVERTER.toSortedSet(iterable);
    }
}
