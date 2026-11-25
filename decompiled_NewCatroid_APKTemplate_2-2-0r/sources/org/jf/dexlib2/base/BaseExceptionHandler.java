package org.jf.dexlib2.base;

import java.util.Comparator;
import java.util.Objects;
import org.jf.dexlib2.base.reference.BaseTypeReference;
import org.jf.dexlib2.iface.ExceptionHandler;
import org.jf.dexlib2.iface.reference.TypeReference;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BaseExceptionHandler implements ExceptionHandler {
    public static final Comparator<ExceptionHandler> BY_EXCEPTION = new Comparator<ExceptionHandler>() { // from class: org.jf.dexlib2.base.BaseExceptionHandler.2
        @Override // java.util.Comparator
        public int compare(ExceptionHandler exceptionHandler, ExceptionHandler exceptionHandler2) {
            String exceptionType = exceptionHandler.getExceptionType();
            if (exceptionType == null) {
                return exceptionHandler2.getExceptionType() != null ? 1 : 0;
            }
            if (exceptionHandler2.getExceptionType() == null) {
                return -1;
            }
            return exceptionType.compareTo(exceptionHandler2.getExceptionType());
        }
    };

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    public TypeReference getExceptionTypeReference() {
        final String exceptionType = getExceptionType();
        if (exceptionType == null) {
            return null;
        }
        return new BaseTypeReference() { // from class: org.jf.dexlib2.base.BaseExceptionHandler.1
            @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
            public String getType() {
                return exceptionType;
            }
        };
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    public int hashCode() {
        String exceptionType = getExceptionType();
        return ((exceptionType == null ? 0 : exceptionType.hashCode()) * 31) + getHandlerCodeAddress();
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    public boolean equals(Object obj) {
        if (!(obj instanceof ExceptionHandler)) {
            return false;
        }
        ExceptionHandler exceptionHandler = (ExceptionHandler) obj;
        return Objects.equals(getExceptionType(), exceptionHandler.getExceptionType()) && getHandlerCodeAddress() == exceptionHandler.getHandlerCodeAddress();
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler, java.lang.Comparable
    public int compareTo(ExceptionHandler exceptionHandler) {
        String exceptionType = getExceptionType();
        if (exceptionType == null) {
            if (exceptionHandler.getExceptionType() != null) {
                return 1;
            }
        } else {
            if (exceptionHandler.getExceptionType() == null) {
                return -1;
            }
            int iCompareTo = exceptionType.compareTo(exceptionHandler.getExceptionType());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        return Integer.compare(getHandlerCodeAddress(), exceptionHandler.getHandlerCodeAddress());
    }
}
