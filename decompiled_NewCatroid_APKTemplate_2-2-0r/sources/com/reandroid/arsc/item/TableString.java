package com.reandroid.arsc.item;

import com.reandroid.arsc.value.Entry;
import com.reandroid.xml.StyleDocument;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class TableString extends StringItem {
    public TableString(boolean z) {
        super(z);
    }

    @Override // com.reandroid.arsc.item.StringItem
    public void serializeText(XmlSerializer xmlSerializer, boolean z) throws IOException {
        StyleDocument styleDocument = getStyleDocument();
        if (styleDocument == null) {
            super.serializeText(xmlSerializer, z);
        } else {
            styleDocument.serialize(xmlSerializer);
        }
    }

    public Iterator<Entry> getEntries(final boolean z) {
        return super.getUsers(Entry.class, new Predicate() { // from class: com.reandroid.arsc.item.TableString$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return TableString.lambda$getEntries$0(z, (Entry) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$getEntries$0(boolean z, Entry entry) {
        if (z) {
            return entry.isComplex();
        }
        return entry.isScalar();
    }

    public Iterator<Entry> getEntries(Predicate<Entry> predicate) {
        return super.getUsers(Entry.class, predicate);
    }
}
