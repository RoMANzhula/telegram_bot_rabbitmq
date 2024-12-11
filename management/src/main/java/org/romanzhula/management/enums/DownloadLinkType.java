package org.romanzhula.management.enums;

public enum DownloadLinkType {

    GET_DOCUMENT("file/get-document"),
    GET_PHOTO("file/get-photo")
    ;

    private final String downloadLink;

    DownloadLinkType(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    @Override
    public String toString() {
        return downloadLink;
    }

}
