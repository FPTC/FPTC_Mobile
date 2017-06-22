package org.pfccap.education.utilities;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.pfccap.education.R;

import java.util.ArrayList;

/**
 * Created by USUARIO on 30/05/2017.
 */

public class Table {

    private TableLayout table;
    private ArrayList<TableRow> rows;
    private Activity activity;
    private Resources rs;
    private int ROWS, COLUMNS;

    /**
     * Contructor de la tabla
     * @param activity Activadad donde va estar la tabla
     * @param table TableLayout donde se pinta la tabla
     */

    public Table(Activity activity, TableLayout table){
        this.activity = activity;
        this.table = table;
        rs = this.activity.getResources();
        ROWS = COLUMNS = 0;
        rows = new ArrayList<>();
    }

    /**
     * Añade la cabecera a la tabla
     * @param resourcehead Recurso (array) donde se encuentra la cabacera de la tabla
     */
    public void addHead(int resourcehead){

        TableRow.LayoutParams layoutCell;
        TableRow row = new TableRow(activity);
        TableRow.LayoutParams layoutRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(layoutRow);

        String[] arrayhead = rs.getStringArray(resourcehead);
        COLUMNS = arrayhead.length;

        for(int i= 0; i<arrayhead.length; i++){
            TextView text = new TextView(activity);
            layoutCell = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            text.setText(arrayhead[i]);
            text.setGravity(Gravity.CENTER_HORIZONTAL);
            if (Build.VERSION.SDK_INT < 23) {
                text.setTextAppearance(activity, R.style.style_cell_head);
            }else{
                text.setTextAppearance(R.style.style_cell_head);
            }
            text.setBackgroundResource(R.drawable.table_cell_head);
            text.setLayoutParams(layoutCell);

            row.addView(text);
        }

        table.addView(row);
        rows.add(row);

        ROWS++;
    }

    /**
     * Agrega una fila a la tabla
     * @param element Elementos de la fila
     */
    public void addRowTable(ArrayList<String> element) {
        TableRow.LayoutParams layoutCell;
        TableRow.LayoutParams layoutRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        TableRow row = new TableRow(activity);
        row.setLayoutParams(layoutRow);

        for (int i = 0; i < element.size(); i++) {
            TextView text = new TextView(activity);
            text.setText(String.valueOf(element.get(i)));
            text.setGravity(Gravity.CENTER_HORIZONTAL);
            if (Build.VERSION.SDK_INT < 23) {
                text.setTextAppearance(activity, R.style.style_cell);
            } else {
                text.setTextAppearance(R.style.style_cell);
            }
            text.setBackgroundResource(R.drawable.table_cell);
            layoutCell = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            text.setLayoutParams(layoutCell);

            row.addView(text);
        }

        table.addView(row);
        rows.add(row);

        ROWS++;
    }

    /**
     * Obtiene el ancho en píxeles de un texto en un String
     * @param texto Texto
     * @return Ancho en píxeles del texto
     */
    private int getWidthPixelText(String texto)
    {
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(50);

        p.getTextBounds(texto, 0, texto.length(), bounds);
        return bounds.width();
    }
}
