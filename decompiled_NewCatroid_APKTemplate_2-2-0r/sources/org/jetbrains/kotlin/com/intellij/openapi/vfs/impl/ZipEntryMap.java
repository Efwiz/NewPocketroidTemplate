package org.jetbrains.kotlin.com.intellij.openapi.vfs.impl;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Conditions;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
final class ZipEntryMap extends AbstractMap<String, ArchiveHandler.EntryInfo> {
    private ArchiveHandler.EntryInfo[] entries;
    private EntrySet entrySet;
    private int size = 0;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 9 || i == 13 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 9 || i == 13 || i == 14) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 7:
                objArr[0] = "relativePath";
                break;
            case 2:
            case 5:
                objArr[0] = TypeBlock.NAME_entries;
                break;
            case 4:
                objArr[0] = "value";
                break;
            case 6:
            case 8:
                objArr[0] = "entry";
                break;
            case 9:
            case 13:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/ZipEntryMap";
                break;
            case 10:
                objArr[0] = "builder";
                break;
            case 11:
                objArr[0] = "sequence";
                break;
            case 12:
            default:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
        }
        if (i == 9) {
            objArr[1] = "getRelativePath";
        } else if (i == 13) {
            objArr[1] = "entrySet";
        } else if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/ZipEntryMap";
        } else {
            objArr[1] = CompoundEntry.NAME_values;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "index";
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "put";
                break;
            case 6:
            case 7:
                objArr[2] = "isTheOne";
                break;
            case 8:
                objArr[2] = "getRelativePath";
                break;
            case 9:
            case 13:
            case 14:
                break;
            case 10:
            case 11:
                objArr[2] = "appendReversed";
                break;
            case 12:
                objArr[2] = ProductAction.ACTION_REMOVE;
                break;
            default:
                objArr[2] = "get";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 9 && i != 13 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    ZipEntryMap(int i) {
        this.entries = new ArchiveHandler.EntryInfo[Math.max(10, i * 2)];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public ArchiveHandler.EntryInfo get(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(0);
        }
        String str = (String) obj;
        int iIndex = index(str, this.entries);
        int i = iIndex;
        do {
            ArchiveHandler.EntryInfo entryInfo = this.entries[i];
            if (entryInfo == null || isTheOne(entryInfo, str)) {
                return entryInfo;
            }
            i++;
            if (i == this.entries.length) {
                i = 0;
            }
        } while (i != iIndex);
        return null;
    }

    private static int index(String str, ArchiveHandler.EntryInfo[] entryInfoArr) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (entryInfoArr == null) {
            $$$reportNull$$$0(2);
        }
        return (str.hashCode() & Integer.MAX_VALUE) % entryInfoArr.length;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public ArchiveHandler.EntryInfo put(String str, ArchiveHandler.EntryInfo entryInfo) {
        if (this.size >= (this.entries.length * 5) / 8) {
            rehash();
        }
        ArchiveHandler.EntryInfo entryInfoPut = put(str, entryInfo, this.entries);
        if (entryInfoPut == null) {
            this.size++;
        }
        return entryInfoPut;
    }

    private static ArchiveHandler.EntryInfo put(String str, ArchiveHandler.EntryInfo entryInfo, ArchiveHandler.EntryInfo[] entryInfoArr) {
        ArchiveHandler.EntryInfo entryInfo2;
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (entryInfo == null) {
            $$$reportNull$$$0(4);
        }
        if (entryInfoArr == null) {
            $$$reportNull$$$0(5);
        }
        int iIndex = index(str, entryInfoArr);
        while (true) {
            entryInfo2 = entryInfoArr[iIndex];
            if (entryInfo2 == null || isTheOne(entryInfo2, str)) {
                break;
            }
            iIndex++;
            if (iIndex == entryInfoArr.length) {
                iIndex = 0;
            }
        }
        entryInfoArr[iIndex] = entryInfo;
        return entryInfo2;
    }

    private static boolean isTheOne(ArchiveHandler.EntryInfo entryInfo, CharSequence charSequence) {
        if (entryInfo == null) {
            $$$reportNull$$$0(6);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(7);
        }
        int length = charSequence.length();
        while (entryInfo != null) {
            CharSequence charSequence2 = entryInfo.shortName;
            if (!CharArrayUtil.regionMatches(charSequence, length - charSequence2.length(), charSequence.length(), charSequence2)) {
                return false;
            }
            length -= charSequence2.length();
            if (entryInfo.parent != null && entryInfo.parent.shortName.length() != 0 && length != 0) {
                if (charSequence.charAt(length - 1) != '/') {
                    return false;
                }
                length--;
            }
            entryInfo = entryInfo.parent;
        }
        return length == 0;
    }

    private void rehash() {
        ArchiveHandler.EntryInfo[] entryInfoArr = this.entries;
        ArchiveHandler.EntryInfo[] entryInfoArr2 = new ArchiveHandler.EntryInfo[entryInfoArr.length < 1000 ? entryInfoArr.length * 2 : (entryInfoArr.length * 3) / 2];
        for (ArchiveHandler.EntryInfo entryInfo : entryInfoArr) {
            if (entryInfo != null) {
                put(getRelativePath(entryInfo), entryInfo, entryInfoArr2);
            }
        }
        this.entries = entryInfoArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getRelativePath(ArchiveHandler.EntryInfo entryInfo) {
        if (entryInfo == null) {
            $$$reportNull$$$0(8);
        }
        StringBuilder sb = new StringBuilder(entryInfo.shortName.length() + 10);
        while (entryInfo != null) {
            if (sb.length() != 0 && entryInfo.shortName.length() != 0) {
                sb.append('/');
            }
            appendReversed(sb, entryInfo.shortName);
            entryInfo = entryInfo.parent;
        }
        String string = sb.reverse().toString();
        if (string == null) {
            $$$reportNull$$$0(9);
        }
        return string;
    }

    private static void appendReversed(StringBuilder sb, CharSequence charSequence) {
        if (sb == null) {
            $$$reportNull$$$0(10);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(11);
        }
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            sb.append(charSequence.charAt(length));
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public ArchiveHandler.EntryInfo remove(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(12);
        }
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.size = 0;
        this.entries = new ArchiveHandler.EntryInfo[10];
    }

    @Override // java.util.AbstractMap, java.util.Map
    public EntrySet entrySet() {
        EntrySet entrySet = this.entrySet;
        if (entrySet == null) {
            entrySet = new EntrySet();
            this.entrySet = entrySet;
        }
        if (entrySet == null) {
            $$$reportNull$$$0(13);
        }
        return entrySet;
    }

    private final class EntrySet extends AbstractSet<Map.Entry<String, ArchiveHandler.EntryInfo>> {
        private EntrySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return ZipEntryMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            ZipEntryMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<String, ArchiveHandler.EntryInfo>> iterator() {
            final Iterator it = ContainerUtil.iterate(Arrays.asList(ZipEntryMap.this.entries), Conditions.notNull()).iterator();
            return new Iterator<Map.Entry<String, ArchiveHandler.EntryInfo>>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ZipEntryMap.EntrySet.1
                @Override // java.util.Iterator
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override // java.util.Iterator
                public Map.Entry<String, ArchiveHandler.EntryInfo> next() {
                    ArchiveHandler.EntryInfo entryInfo = (ArchiveHandler.EntryInfo) it.next();
                    return new AbstractMap.SimpleEntry(ZipEntryMap.getRelativePath(entryInfo), entryInfo);
                }

                @Override // java.util.Iterator
                public void remove() {
                    it.remove();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return ((ArchiveHandler.EntryInfo) entry.getValue()).equals(ZipEntryMap.this.get(entry.getKey()));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<ArchiveHandler.EntryInfo> values() {
        List listFilter = ContainerUtil.filter(this.entries, Conditions.notNull());
        if (listFilter == null) {
            $$$reportNull$$$0(14);
        }
        return listFilter;
    }
}
