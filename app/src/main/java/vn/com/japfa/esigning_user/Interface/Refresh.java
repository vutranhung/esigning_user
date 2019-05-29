package vn.com.japfa.esigning_user.Interface;

import vn.com.japfa.esigning_user.models.DocumentDatum;

import java.util.List;

public interface Refresh {
    void refreshSuccess( List<DocumentDatum> listDocuments);
}
