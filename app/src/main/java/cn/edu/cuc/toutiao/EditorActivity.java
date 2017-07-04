package cn.edu.cuc.toutiao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

public class EditorActivity extends AppCompatActivity implements View.OnClickListener {
    private RichEditor editor;
    private ImageView undo;
    private ImageView redo;
    private CheckBox bold;
    private CheckBox italic;
    private CheckBox underline;
    private CheckBox strikethrough;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        editor = (RichEditor) findViewById(R.id.editor);
        undo = (ImageView) findViewById(R.id.undo);
        redo = (ImageView) findViewById(R.id.redo);
        bold = (CheckBox) findViewById(R.id.bold);
        italic = (CheckBox) findViewById(R.id.italic);
        underline = (CheckBox) findViewById(R.id.underline);
        strikethrough = (CheckBox) findViewById(R.id.strikethrough);

        undo.setOnClickListener(this);
        redo.setOnClickListener(this);
        bold.setOnClickListener(this);
        italic.setOnClickListener(this);
        underline.setOnClickListener(this);
        strikethrough.setOnClickListener(this);

        editor.setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener() {
            @Override
            public void onStateChangeListener(String text, List<RichEditor.Type> types) {
                bold.setChecked(types.contains(RichEditor.Type.BOLD));
                italic.setChecked(types.contains(RichEditor.Type.ITALIC));
                underline.setChecked(types.contains(RichEditor.Type.UNDERLINE));
                strikethrough.setChecked(types.contains(RichEditor.Type.STRIKETHROUGH));
                Log.d("Decoration","----------"+text);
            }
        });

        editor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                Log.d("Text","-----------"+text);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.undo:
                editor.undo();
                break;
            case R.id.redo:
                editor.redo();
                break;
            case R.id.bold:
                editor.setBold();
                break;
            case R.id.italic:
                editor.setItalic();
                break;
            case R.id.underline:
                editor.setUnderline();
                break;
            case R.id.strikethrough:
                editor.setStrikeThrough();
                break;
        }
    }
}
