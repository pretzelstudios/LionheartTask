package uk.co.pretzelstudios.lionhearttask;

public class ExampleItem {

    // defining my methods
    private String mImageUrl;
    private String mCreator;
    private int mLikes;

    public ExampleItem(String imageUrl, String creator, int likes) {
        mImageUrl = imageUrl;
        mCreator = creator;
        mLikes = likes;
    }

    //return statements for my array adapter

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public int getLikeCount() {
        return mLikes;
    }
}