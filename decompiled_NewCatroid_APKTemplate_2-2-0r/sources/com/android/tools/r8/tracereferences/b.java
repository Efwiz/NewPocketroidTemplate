package com.android.tools.r8.tracereferences;

import com.android.tools.r8.utils.Y2;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class b extends a {
    final boolean c;

    b(boolean z) {
        this.c = z;
    }

    @Override // com.android.tools.r8.tracereferences.a
    protected final void a(List list) {
        if (list.isEmpty()) {
            return;
        }
        a("-keeppackagenames " + Y2.a(",", list) + System.lineSeparator());
    }

    @Override // com.android.tools.r8.tracereferences.a
    protected final void b(TraceReferencesConsumer.TracedClass tracedClass) {
        if (tracedClass.isMissingDefinition()) {
            b("# Missing class: " + tracedClass.getReference().getTypeName());
            return;
        }
        a(this.c ? "-keep,allowobfuscation" : "-keep");
        if (tracedClass.getAccessFlags().isInterface()) {
            b(" interface " + tracedClass.getReference().getTypeName() + " {");
        } else if (tracedClass.getAccessFlags().isEnum()) {
            b(" enum " + tracedClass.getReference().getTypeName() + " {");
        } else {
            b(" class " + tracedClass.getReference().getTypeName() + " {");
        }
    }

    @Override // com.android.tools.r8.tracereferences.a
    protected final void b(TraceReferencesConsumer.TracedField tracedField) {
        a("  " + tracedField.getReference().getFieldType().getTypeName() + " " + tracedField.getReference().getFieldName() + ";" + System.lineSeparator());
    }
}
