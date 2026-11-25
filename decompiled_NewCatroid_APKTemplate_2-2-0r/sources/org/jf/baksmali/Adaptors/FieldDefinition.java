package org.jf.baksmali.Adaptors;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.HiddenApiRestriction;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.iface.value.IntEncodedValue;
import org.jf.dexlib2.iface.value.LongEncodedValue;
import org.jf.dexlib2.util.EncodedValueUtils;

/* JADX INFO: loaded from: classes6.dex */
public class FieldDefinition {
    public static void writeTo(BaksmaliWriter baksmaliWriter, Field field, boolean z) throws IOException {
        boolean zIsSet;
        EncodedValue initialValue = field.getInitialValue();
        int accessFlags = field.getAccessFlags();
        if (initialValue != null && AccessFlags.STATIC.isSet(accessFlags) && AccessFlags.FINAL.isSet(accessFlags)) {
            if (z) {
                if (EncodedValueUtils.isDefaultValue(initialValue)) {
                    initialValue = null;
                } else {
                    baksmaliWriter.write("# The value of this static final field might be set in the static constructor\n");
                }
            }
            zIsSet = AccessFlags.PUBLIC.isSet(accessFlags);
        } else {
            zIsSet = false;
        }
        baksmaliWriter.write(".field ");
        writeAccessFlagsAndRestrictions(baksmaliWriter, field.getAccessFlags(), field.getHiddenApiRestrictions());
        baksmaliWriter.writeSimpleName(field.getName());
        baksmaliWriter.write(58);
        baksmaliWriter.writeType(field.getType());
        if (initialValue != null) {
            baksmaliWriter.write(" = ");
            baksmaliWriter.writeEncodedValue(initialValue);
            if (zIsSet) {
                writeResourceIdCommentIfRequired(baksmaliWriter, initialValue);
            }
        }
        baksmaliWriter.write(10);
        Set<? extends Annotation> annotations = field.getAnnotations();
        if (annotations.size() > 0) {
            baksmaliWriter.indent(4);
            AnnotationFormatter.writeTo(baksmaliWriter, annotations);
            baksmaliWriter.deindent(4);
            baksmaliWriter.write(".end field\n");
        }
    }

    private static void writeResourceIdCommentIfRequired(BaksmaliWriter baksmaliWriter, EncodedValue encodedValue) throws IOException {
        int value;
        int valueType = encodedValue.getValueType();
        if (valueType == 4) {
            value = ((IntEncodedValue) encodedValue).getValue();
        } else if (valueType != 6) {
            return;
        } else {
            value = (int) ((LongEncodedValue) encodedValue).getValue();
        }
        String comment = baksmaliWriter.getOptions().getCommentProvider().getComment(value);
        if (comment == null) {
            return;
        }
        baksmaliWriter.write(" # ");
        baksmaliWriter.write(comment);
    }

    private static void writeAccessFlagsAndRestrictions(BaksmaliWriter baksmaliWriter, int i, Set<HiddenApiRestriction> set) throws IOException {
        for (AccessFlags accessFlags : AccessFlags.getAccessFlagsForField(i)) {
            baksmaliWriter.write(accessFlags.toString());
            baksmaliWriter.write(32);
        }
        Iterator<HiddenApiRestriction> it = set.iterator();
        while (it.hasNext()) {
            baksmaliWriter.write(it.next().toString());
            baksmaliWriter.write(32);
        }
    }
}
