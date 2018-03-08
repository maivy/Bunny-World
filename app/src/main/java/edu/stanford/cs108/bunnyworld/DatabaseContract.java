package edu.stanford.cs108.bunnyworld;

import android.provider.BaseColumns;

/**
 * Schema of database
 */

public class DatabaseContract {
    private DatabaseContract() {}

    public static class Games implements BaseColumns {
        public static final String TABLE_NAME = "games";
        public static final String GAME_ID = "_id";
        public static final String NAME = "name";
        public static final String CURR_PAGE_NUMBER = "currPageNumber";
        public static final String CURR_SHAPE_NUMBER = "currShapeNumber";

    }

    public static class Pages implements BaseColumns {
        public static final String TABLE_NAME = "pages";
        public static final String PAGE_ID = "_id";
        public static final String NAME = "name";
        public static final String GAME_ID = "gameId";
    }

    public static class Shapes implements BaseColumns {
        public static final String TABLE_NAME = "shapes";
        public static final String SHAPE_ID = "_id";
        public static final String NAME = "name";
        public static final String PAGE_ID = "pageId";
        public static final String GAME_ID = "gameId";
        public static final String SCRIPT = "script";
        public static final String IMAGE = "image";
        public static final String TEXT = "text";
        public static final String FONT_SIZE = "fontSize";
        public static final String IS_HIDDEN = "isHidden";
        public static final String IS_MOVABLE = "isMovable";
        public static final String IS_RECEIVING = "isReceiving";
        public static final String X = "x";
        public static final String Y = "y";
        public static final String WIDTH = "width";
        public static final String HEIGHT = "height";
    }
}