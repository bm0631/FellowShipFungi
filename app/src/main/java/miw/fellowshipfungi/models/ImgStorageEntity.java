package miw.fellowshipfungi.models;

public abstract class ImgStorageEntity {
    private final String URLBase = "https://firebasestorage.googleapis.com/v0/b/fellowship-fungi.appspot.com/o/";
    protected String img;
    public String getImgUrl() {
        return URLBase + this.getFolderAndImg().replace(" ", "%20").replace(".jpg", "") + ".jpg?alt=media";
    }
    protected String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getFolderAndImg(){
        return getFolder()+this.img;
    }

    public abstract String getFolder();
}
