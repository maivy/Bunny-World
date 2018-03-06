package edu.stanford.cs108.bunnyworld;

import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract() {}

    public static class Games implements BaseColumns {
        public static final String TABLE_NAME = "games";
        public static final String GAME_ID = "_id";
        public static final String NAME = "name";
    }

    public static class Pages implements BaseColumns {
        public static final String TABLE_NAME = "pages";
        public static final String PAGE_ID = "_id";
        public static final String NAME = "name";
        public static final String GAME_ID = "game_id";
    }

    public static class Shapes implements BaseColumns {
        public static final String TABLE_NAME = "shapes";
        public static final String SHAPE_ID = "_id";
        public static final String NAME = "name";
        public static final String PAGE_ID = "page_id";
        public static final String SCRIPT = "script";
        public static final String IMAGE = "image";
        public static final String TEXT = "text";
        public static final String FONT_SIZE = "font_size";
        public static final String TEXT_COLOR = "text_color";
        public static final String IS_HIDDEN = "is_hidden";
        public static final String IS_MOVABLE = "is_movable";
        public static final String IS_RECEIVING = "is_receiving";
        public static final String X = "x";
        public static final String Y = "y";
        public static final String WIDTH = "width";
        public static final String HEIGHT = "height";
    }

//    public static class Images implements BaseColumns {
//        public static final String TABLE_NAME = "images";
//        public static final String IMAGE_ID = "_id";
//        public static final String FILE_NAME = "file_name";
//    }
}