package com.huawei.hms.ml.common.ocr;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteTextRecognizerDelegate extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.common.ocr.IRemoteTextRecognizerDelegate";

    public static class Default implements IRemoteTextRecognizerDelegate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.common.ocr.IRemoteTextRecognizerDelegate
        public TextParcel detect(Bundle bundle, TextDetectorFrameParcel textDetectorFrameParcel, TextDetectorOptionsParcel textDetectorOptionsParcel) throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.ml.common.ocr.IRemoteTextRecognizerDelegate
        public int initial(IObjectWrapper iObjectWrapper, TextDetectorOptionsParcel textDetectorOptionsParcel) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.ml.common.ocr.IRemoteTextRecognizerDelegate
        public int unloadModel() throws RemoteException {
            return 0;
        }
    }

    TextParcel detect(Bundle bundle, TextDetectorFrameParcel textDetectorFrameParcel, TextDetectorOptionsParcel textDetectorOptionsParcel) throws RemoteException;

    int initial(IObjectWrapper iObjectWrapper, TextDetectorOptionsParcel textDetectorOptionsParcel) throws RemoteException;

    int unloadModel() throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteTextRecognizerDelegate {
        static final int TRANSACTION_detect = 2;
        static final int TRANSACTION_initial = 1;
        static final int TRANSACTION_unloadModel = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteTextRecognizerDelegate.DESCRIPTOR);
        }

        public static IRemoteTextRecognizerDelegate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteTextRecognizerDelegate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteTextRecognizerDelegate)) {
                return (IRemoteTextRecognizerDelegate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteTextRecognizerDelegate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteTextRecognizerDelegate.DESCRIPTOR);
                int iInitial = initial(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? TextDetectorOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeInt(iInitial);
                return true;
            }
            if (i != 2) {
                if (i == 3) {
                    parcel.enforceInterface(IRemoteTextRecognizerDelegate.DESCRIPTOR);
                    int iUnloadModel = unloadModel();
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnloadModel);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(IRemoteTextRecognizerDelegate.DESCRIPTOR);
            TextParcel textParcelDetect = detect(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? TextDetectorFrameParcel.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? TextDetectorOptionsParcel.CREATOR.createFromParcel(parcel) : null);
            parcel2.writeNoException();
            if (textParcelDetect != null) {
                parcel2.writeInt(1);
                textParcelDetect.writeToParcel(parcel2, 1);
            } else {
                parcel2.writeInt(0);
            }
            return true;
        }

        private static class Proxy implements IRemoteTextRecognizerDelegate {
            public static IRemoteTextRecognizerDelegate sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTextRecognizerDelegate.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.common.ocr.IRemoteTextRecognizerDelegate
            public int initial(IObjectWrapper iObjectWrapper, TextDetectorOptionsParcel textDetectorOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTextRecognizerDelegate.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (textDetectorOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        textDetectorOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initial(iObjectWrapper, textDetectorOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.common.ocr.IRemoteTextRecognizerDelegate
            public TextParcel detect(Bundle bundle, TextDetectorFrameParcel textDetectorFrameParcel, TextDetectorOptionsParcel textDetectorOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTextRecognizerDelegate.DESCRIPTOR);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (textDetectorFrameParcel != null) {
                        parcelObtain.writeInt(1);
                        textDetectorFrameParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (textDetectorOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        textDetectorOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detect(bundle, textDetectorFrameParcel, textDetectorOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? TextParcel.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.common.ocr.IRemoteTextRecognizerDelegate
            public int unloadModel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTextRecognizerDelegate.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unloadModel();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteTextRecognizerDelegate iRemoteTextRecognizerDelegate) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteTextRecognizerDelegate == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteTextRecognizerDelegate;
            return true;
        }

        public static IRemoteTextRecognizerDelegate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
