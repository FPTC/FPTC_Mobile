package org.pfccap.education.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import org.pfccap.education.dao.Ciudades;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CIUDADES".
*/
public class CiudadesDao extends AbstractDao<Ciudades, Long> {

    public static final String TABLENAME = "CIUDADES";

    /**
     * Properties of entity Ciudades.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdCity = new Property(1, Long.class, "idCity", false, "ID_CITY");
        public final static Property IdPais = new Property(2, String.class, "idPais", false, "ID_PAIS");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property State = new Property(4, Boolean.class, "state", false, "STATE");
    };


    public CiudadesDao(DaoConfig config) {
        super(config);
    }
    
    public CiudadesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CIUDADES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ID_CITY\" INTEGER," + // 1: idCity
                "\"ID_PAIS\" TEXT," + // 2: idPais
                "\"NAME\" TEXT," + // 3: name
                "\"STATE\" INTEGER);"); // 4: state
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CIUDADES\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Ciudades entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long idCity = entity.getIdCity();
        if (idCity != null) {
            stmt.bindLong(2, idCity);
        }
 
        String idPais = entity.getIdPais();
        if (idPais != null) {
            stmt.bindString(3, idPais);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        Boolean state = entity.getState();
        if (state != null) {
            stmt.bindLong(5, state ? 1L: 0L);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Ciudades readEntity(Cursor cursor, int offset) {
        Ciudades entity = new Ciudades( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // idCity
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // idPais
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0 // state
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Ciudades entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdCity(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setIdPais(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setState(cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Ciudades entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Ciudades entity) {
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
