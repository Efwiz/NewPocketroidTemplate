package com.koushikdutta.async.http;

import android.net.Uri;
import com.huawei.hms.framework.common.ContainerUtils;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class Multimap extends LinkedHashMap<String, List<String>> implements Iterable<NameValuePair> {
    private static final StringDecoder QUERY_DECODER = new StringDecoder() { // from class: com.koushikdutta.async.http.Multimap.1
        @Override // com.koushikdutta.async.http.Multimap.StringDecoder
        public String decode(String str) {
            return Uri.decode(str);
        }
    };
    private static final StringDecoder URL_DECODER = new StringDecoder() { // from class: com.koushikdutta.async.http.Multimap.2
        @Override // com.koushikdutta.async.http.Multimap.StringDecoder
        public String decode(String str) {
            return URLDecoder.decode(str);
        }
    };

    public interface StringDecoder {
        String decode(String str);
    }

    public Multimap() {
    }

    protected List<String> newList() {
        return new ArrayList();
    }

    public String getString(String str) {
        List<String> list = get(str);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public void add(String str, String str2) {
        List<String> listNewList = get(str);
        if (listNewList == null) {
            listNewList = newList();
            put(str, listNewList);
        }
        listNewList.add(str2);
    }

    public void put(String str, String str2) {
        List<String> listNewList = newList();
        listNewList.add(str2);
        put(str, listNewList);
    }

    public Multimap(List<NameValuePair> list) {
        for (NameValuePair nameValuePair : list) {
            add(nameValuePair.getName(), nameValuePair.getValue());
        }
    }

    public Multimap(Multimap multimap) {
        putAll(multimap);
    }

    public static Multimap parse(String str, String str2, boolean z, StringDecoder stringDecoder) {
        Multimap multimap = new Multimap();
        if (str == null) {
            return multimap;
        }
        for (String str3 : str.split(str2)) {
            String[] strArrSplit = str3.split(ContainerUtils.KEY_VALUE_DELIMITER, 2);
            String strTrim = strArrSplit[0].trim();
            String strDecode = strArrSplit.length > 1 ? strArrSplit[1] : null;
            if (z && strDecode != null && strDecode.endsWith("\"") && strDecode.startsWith("\"")) {
                strDecode = strDecode.substring(1, strDecode.length() - 1);
            }
            if (stringDecoder != null) {
                strTrim = stringDecoder.decode(strTrim);
                strDecode = stringDecoder.decode(strDecode);
            }
            multimap.add(strTrim, strDecode);
        }
        return multimap;
    }

    public static Multimap parseSemicolonDelimited(String str) {
        return parse(str, ";", true, null);
    }

    public static Multimap parseCommaDelimited(String str) {
        return parse(str, ",", true, null);
    }

    public static Multimap parseQuery(String str) {
        return parse(str, ContainerUtils.FIELD_DELIMITER, false, QUERY_DECODER);
    }

    public static Multimap parseUrlEncoded(String str) {
        return parse(str, ContainerUtils.FIELD_DELIMITER, false, URL_DECODER);
    }

    @Override // java.lang.Iterable
    public Iterator<NameValuePair> iterator() {
        ArrayList arrayList = new ArrayList();
        for (String str : keySet()) {
            Iterator it = ((List) get(str)).iterator();
            while (it.hasNext()) {
                arrayList.add(new BasicNameValuePair(str, (String) it.next()));
            }
        }
        return arrayList.iterator();
    }
}
