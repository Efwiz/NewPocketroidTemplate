package org.jf.baksmali.Adaptors;

import java.io.IOException;
import java.util.Collection;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.AnnotationVisibility;
import org.jf.dexlib2.iface.Annotation;

/* JADX INFO: loaded from: classes6.dex */
public class AnnotationFormatter {
    public static void writeTo(BaksmaliWriter baksmaliWriter, Collection<? extends Annotation> collection) throws IOException {
        boolean z = true;
        for (Annotation annotation : collection) {
            if (!z) {
                baksmaliWriter.write(10);
            }
            writeTo(baksmaliWriter, annotation);
            z = false;
        }
    }

    public static void writeTo(BaksmaliWriter baksmaliWriter, Annotation annotation) throws IOException {
        baksmaliWriter.write(".annotation ");
        baksmaliWriter.write(AnnotationVisibility.getVisibility(annotation.getVisibility()));
        baksmaliWriter.write(32);
        baksmaliWriter.write(annotation.getType());
        baksmaliWriter.write(10);
        baksmaliWriter.writeAnnotationElements(annotation.getElements());
        baksmaliWriter.write(".end annotation\n");
    }
}
