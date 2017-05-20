package org.pfccap.education.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import org.pfccap.education.dao.AnswersQuestion;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ANSWERS_QUESTION".
*/
public class AnswersQuestionDao extends AbstractDao<AnswersQuestion, Long> {

    public static final String TABLENAME = "ANSWERS_QUESTION";

    /**
     * Properties of entity AnswersQuestion.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Description = new Property(1, String.class, "description", false, "DESCRIPTION");
        public final static Property Value = new Property(2, String.class, "value", false, "VALUE");
        public final static Property Points = new Property(3, String.class, "points", false, "POINTS");
        public final static Property IdQuestion = new Property(4, long.class, "idQuestion", false, "ID_QUESTION");
    };

    private DaoSession daoSession;


    public AnswersQuestionDao(DaoConfig config) {
        super(config);
    }
    
    public AnswersQuestionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ANSWERS_QUESTION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"DESCRIPTION\" TEXT," + // 1: description
                "\"VALUE\" TEXT," + // 2: value
                "\"POINTS\" TEXT," + // 3: points
                "\"ID_QUESTION\" INTEGER NOT NULL );"); // 4: idQuestion
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ANSWERS_QUESTION\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, AnswersQuestion entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(2, description);
        }
 
        String value = entity.getValue();
        if (value != null) {
            stmt.bindString(3, value);
        }
 
        String points = entity.getPoints();
        if (points != null) {
            stmt.bindString(4, points);
        }
        stmt.bindLong(5, entity.getIdQuestion());
    }

    @Override
    protected void attachEntity(AnswersQuestion entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public AnswersQuestion readEntity(Cursor cursor, int offset) {
        AnswersQuestion entity = new AnswersQuestion( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // description
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // value
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // points
            cursor.getLong(offset + 4) // idQuestion
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, AnswersQuestion entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDescription(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setValue(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPoints(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIdQuestion(cursor.getLong(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(AnswersQuestion entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(AnswersQuestion entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getQuestionsDao().getAllColumns());
            builder.append(" FROM ANSWERS_QUESTION T");
            builder.append(" LEFT JOIN QUESTIONS T0 ON T.\"ID_QUESTION\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected AnswersQuestion loadCurrentDeep(Cursor cursor, boolean lock) {
        AnswersQuestion entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Questions questions = loadCurrentOther(daoSession.getQuestionsDao(), cursor, offset);
         if(questions != null) {
            entity.setQuestions(questions);
        }

        return entity;    
    }

    public AnswersQuestion loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<AnswersQuestion> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<AnswersQuestion> list = new ArrayList<AnswersQuestion>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<AnswersQuestion> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<AnswersQuestion> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
