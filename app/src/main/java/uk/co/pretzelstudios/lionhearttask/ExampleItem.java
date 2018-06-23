package uk.co.pretzelstudios.lionhearttask;

public class ExampleItem {

    // defining my methods
    private String mImageUrl;
    private String mCreator;
    private int mLikes;

    // defining my strings and ints from my array.

    public ExampleItem(String imageUrl, String creator, int likes) {
        mImageUrl = imageUrl;
        mCreator = creator;
        mLikes = likes;
    }

    //return statements for my array adapter to pull data into my activity

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