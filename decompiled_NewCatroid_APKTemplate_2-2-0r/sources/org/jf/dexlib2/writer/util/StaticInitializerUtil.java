package org.jf.dexlib2.writer.util;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jf.dexlib2.base.value.BaseArrayEncodedValue;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.value.ArrayEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory;
import org.jf.dexlib2.util.EncodedValueUtils;
import org.jf.util.CollectionUtils;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class StaticInitializerUtil {
    private static final Predicate<Field> HAS_INITIALIZER = new Predicate<Field>() { // from class: org.jf.dexlib2.writer.util.StaticInitializerUtil.2
        @Override // java.util.function.Predicate
        public boolean test(Field field) {
            EncodedValue initialValue = field.getInitialValue();
            return (initialValue == null || EncodedValueUtils.isDefaultValue(initialValue)) ? false : true;
        }
    };
    private static final Function<Field, EncodedValue> GET_INITIAL_VALUE = new Function<Field, EncodedValue>() { // from class: org.jf.dexlib2.writer.util.StaticInitializerUtil.3
        @Override // java.util.function.Function
        public EncodedValue apply(Field field) {
            EncodedValue initialValue = field.getInitialValue();
            return initialValue == null ? ImmutableEncodedValueFactory.defaultValueForType(field.getType()) : initialValue;
        }
    };

    public static ArrayEncodedValue getStaticInitializers(final Set<? extends Field> set) {
        final int iLastIndexOf = CollectionUtils.lastIndexOf(set, HAS_INITIALIZER);
        if (iLastIndexOf > -1) {
            return new BaseArrayEncodedValue() { // from class: org.jf.dexlib2.writer.util.StaticInitializerUtil.1
                @Override // org.jf.dexlib2.iface.value.ArrayEncodedValue
                public List<? extends EncodedValue> getValue() {
                    return ListUtil.transform(set, StaticInitializerUtil.GET_INITIAL_VALUE, iLastIndexOf + 1);
                }
            };
        }
        return null;
    }
}
