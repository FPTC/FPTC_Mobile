package org.pfccap.education.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import org.pfccap.education.dao.Ese;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ESE".
*/
public class EseDao extends AbstractDao<Ese, Long> {

    public static final String TABLENAME = "ESE";

    /**
     * Properties of entity Ese.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdEse = new Property(1, Long.class, "idEse", false, "ID_ESE");
        public final static Property IdPais = new Property(2, String.class, "idPais", false, "ID_PAIS");
        public final static Property IdCiudad = new Property(3, String.class, "idCiudad", false, "ID_CIUDAD");
        public final static Property Name = new Property(4, String.class, "name", false, "NAME");
        public final static Property State = new Property(5, Boolean.class, "state", false, "STATE");
    };


    public EseDao(DaoConfig config) {
        super(config);
    }
    
    public EseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ESE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ID_ESE\" INTEGER," + // 1: idEse
                "\"ID_PAIS\" TEXT," + // 2: idPais
                "\"ID_CIUDAD\" TEXT," + // 3: idCiudad
                "\"NAME\" TEXT," + // 4: name
                "\"STATE\" INTEGER);"); // 5: state
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ESE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Ese entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long idEse = entity.getIdEse();
        if (idEse != null) {
            stmt.bindLong(2, idEse);
        }
 
        String idPais = entity.getIdPais();
        if (idPais != null) {
            stmt.bindString(3, idPais);
        }
 
        String idCiudad = entity.getIdCiudad();
        if (idCiudad != null) {
            stmt.bindString(4, idCiudad);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        Boolean state = entity.getState();
        if (state != null) {
            stmt.bindLong(6, state ? 1L: 0L);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Ese readEntity(Cursor cursor, int offset) {
        Ese entity = new Ese( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // idEse
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // idPais
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // idCiudad
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // name
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0 // state
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Ese entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdEse(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setIdPais(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIdCiudad(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setState(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Ese entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Ese entity) {
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
    
}
