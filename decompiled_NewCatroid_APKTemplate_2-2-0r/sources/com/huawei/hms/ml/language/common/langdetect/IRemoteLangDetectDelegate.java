package com.huawei.hms.ml.language.common.langdetect;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteLangDetectDelegate extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectDelegate";

    public static class Default implements IRemoteLangDetectDelegate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectDelegate
        public int destroy() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectDelegate
        public List<LangDetectParcel> detect(Bundle bundle, LangDetectFrameParcel langDetectFrameParcel, LangDetectOptionsParcel langDetectOptionsParcel) throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectDelegate
        public int initial(IObjectWrapper iObjectWrapper, LangDetectOptionsParcel langDetectOptionsParcel) throws RemoteException {
            return 0;
        }
    }

    int destroy() throws RemoteException;

    List<LangDetectParcel> detect(Bundle bundle, LangDetectFrameParcel langDetectFrameParcel, LangDetectOptionsParcel langDetectOptionsParcel) throws RemoteException;

    int initial(IObjectWrapper iObjectWrapper, LangDetectOptionsParcel langDetectOptionsParcel) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteLangDetectDelegate {
        static final int TRANSACTION_destroy = 3;
        static final int TRANSACTION_detect = 2;
        static final int TRANSACTION_initial = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteLangDetectDelegate.DESCRIPTOR);
        }

        public static IRemoteLangDetectDelegate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteLangDetectDelegate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteLangDetectDelegate)) {
                return (IRemoteLangDetectDelegate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteLangDetectDelegate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteLangDetectDelegate.DESCRIPTOR);
                int iInitial = initial(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? LangDetectOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeInt(iInitial);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(IRemoteLangDetectDelegate.DESCRIPTOR);
                List<LangDetectParcel> listDetect = detect(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? LangDetectFrameParcel.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? LangDetectOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeTypedList(listDetect);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(IRemoteLangDetectDelegate.DESCRIPTOR);
                int iDestroy = destroy();
                parcel2.writeNoException();
                parcel2.writeInt(iDestroy);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteLangDetectDelegate {
            public static IRemoteLangDetectDelegate sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteLangDetectDelegate.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectDelegate
            public int initial(IObjectWrapper iObjectWrapper, LangDetectOptionsParcel langDetectOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteLangDetectDelegate.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (langDetectOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        langDetectOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initial(iObjectWrapper, langDetectOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectDelegate
            public List<LangDetectParcel> detect(Bundle bundle, LangDetectFrameParcel langDetectFrameParcel, LangDetectOptionsParcel langDetectOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteLangDetectDelegate.DESCRIPTOR);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (langDetectFrameParcel != null) {
                        parcelObtain.writeInt(1);
                        langDetectFrameParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (langDetectOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        langDetectOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detect(bundle, langDetectFrameParcel, langDetectOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(LangDetectParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.language.common.langdetect.IRemoteLangDetectDelegate
            public int destroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteLangDetectDelegate.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().destroy();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteLangDetectDelegate iRemoteLangDetectDelegate) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteLangDetectDelegate == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteLangDetectDelegate;
            return true;
        }

        public static IRemoteLangDetectDelegate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
