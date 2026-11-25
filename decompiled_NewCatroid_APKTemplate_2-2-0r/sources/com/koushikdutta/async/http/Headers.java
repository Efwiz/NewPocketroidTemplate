package com.koushikdutta.async.http;

import android.text.TextUtils;
import com.koushikdutta.async.util.TaggedList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class Headers {
    final Multimap map;

    public Headers() {
        this.map = new Multimap() { // from class: com.koushikdutta.async.http.Headers.1
            @Override // com.koushikdutta.async.http.Multimap
            protected List<String> newList() {
                return new TaggedList();
            }
        };
    }

    public Headers(Map<String, List<String>> map) {
        Multimap multimap = new Multimap() { // from class: com.koushikdutta.async.http.Headers.1
            @Override // com.koushikdutta.async.http.Multimap
            protected List<String> newList() {
                return new TaggedList();
            }
        };
        this.map = multimap;
        multimap.putAll(map);
    }

    public Multimap getMultiMap() {
        return this.map;
    }

    public List<String> getAll(String str) {
        return this.map.get(str.toLowerCase(Locale.US));
    }

    public String get(String str) {
        return this.map.getString(str.toLowerCase(Locale.US));
    }

    public Headers set(String str, String str2) {
        if (str2 != null && (str2.contains("\n") || str2.contains("\r"))) {
            throw new IllegalArgumentException("value must not contain a new line or line feed");
        }
        String lowerCase = str.toLowerCase(Locale.US);
        this.map.put(lowerCase, str2);
        ((TaggedList) this.map.get(lowerCase)).tagNull(str);
        return this;
    }

    public Headers add(String str, String str2) {
        String lowerCase = str.toLowerCase(Locale.US);
        this.map.add(lowerCase, str2);
        ((TaggedList) this.map.get(lowerCase)).tagNull(str);
        return this;
    }

    public Headers addLine(String str) {
        if (str != null) {
            String[] strArrSplit = str.trim().split(":", 2);
            if (strArrSplit.length == 2) {
                add(strArrSplit[0].trim(), strArrSplit[1].trim());
            } else {
                add(strArrSplit[0].trim(), "");
            }
        }
        return this;
    }

    public Headers addAll(String str, List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            add(str, it.next());
        }
        return this;
    }

    public Headers addAll(Map<String, List<String>> map) {
        for (String str : map.keySet()) {
            Iterator<String> it = map.get(str).iterator();
            while (it.hasNext()) {
                add(str, it.next());
            }
        }
        return this;
    }

    public Headers addAll(Headers headers) {
        this.map.putAll(headers.map);
        return this;
    }

    public List<String> removeAll(String str) {
        return (List) this.map.remove(str.toLowerCase(Locale.US));
    }

    public String remove(String str) {
        List<String> listRemoveAll = removeAll(str.toLowerCase(Locale.US));
        if (listRemoveAll == null || listRemoveAll.size() == 0) {
            return null;
        }
        return listRemoveAll.get(0);
    }

    public Headers removeAll(Collection<String> collection) {
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
        return this;
    }

    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder(256);
        Iterator<String> it = this.map.keySet().iterator();
        while (it.hasNext()) {
            TaggedList taggedList = (TaggedList) this.map.get(it.next());
            Iterator<T> it2 = taggedList.iterator();
            while (it2.hasNext()) {
                sb.append((String) taggedList.tag()).append(": ").append((String) it2.next()).append("\r\n");
            }
        }
        sb.append("\r\n");
        return sb;
    }

    public String toString() {
        return toStringBuilder().toString();
    }

    public String toPrefixString(String str) {
        return toStringBuilder().insert(0, str + "\r\n").toString();
    }

    public static Headers parse(String str) {
        String[] strArrSplit = str.split("\n");
        Headers headers = new Headers();
        for (String str2 : strArrSplit) {
            String strTrim = str2.trim();
            if (!TextUtils.isEmpty(strTrim)) {
                headers.addLine(strTrim);
            }
        }
        return headers;
    }
}
