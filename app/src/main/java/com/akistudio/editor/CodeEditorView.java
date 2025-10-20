package com.akistudio.editor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import com.akistudio.R;

/**
 * A custom EditText optimized for code editing.
 * Features: Monospaced font, dark theme, and basic styling.
 * Note: A production-grade IDE would require a more complex view with syntax highlighting.
 */
public class CodeEditorView extends AppCompatEditText {

    public CodeEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        // Set basic code editor properties
        setTypeface(Typeface.MONOSPACE);
        setBackgroundColor(getResources().getColor(R.color.editor_background, null));
        setTextColor(getResources().getColor(R.color.editor_text, null));
        setHintTextColor(getResources().getColor(R.color.editor_hint, null));
        setTextSize(14f); // Set text size in SP
        setPadding(32, 32, 32, 32); // Add some padding
        setHorizontallyScrolling(true);
        setLineSpacing(0f, 1.2f); // Improve line spacing
        setElevation(4f); // Add a subtle shadow
    }
}
