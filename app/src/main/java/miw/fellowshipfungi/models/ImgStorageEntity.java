package miw.fellowshipfungi.models;

public abstract class ImgStorageEntity {
    private final String URLBase = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/";

    public String getImgUrl() {
        return URLBase + this.getFolderAndImg().replace(" ", "%20").replace(".jpg","") + ".jpg?alt=media";
    }
    public abstract String getFolderAndImg();
}
