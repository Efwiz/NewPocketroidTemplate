package org.apache.xerces.xs.datatypes;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

/* JADX INFO: loaded from: classes13.dex */
public interface XSDateTime {
    int getDays();

    Duration getDuration();

    int getHours();

    String getLexicalValue();

    int getMinutes();

    int getMonths();

    double getSeconds();

    int getTimeZoneHours();

    int getTimeZoneMinutes();

    XMLGregorianCalendar getXMLGregorianCalendar();

    int getYears();

    boolean hasTimeZone();

    boolean isNormalized();

    XSDateTime normalize();
}
