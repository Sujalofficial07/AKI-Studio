package com.akistudio.terminal;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View; // <-- THIS IMPORT IS ADDED
import android.widget.ScrollView;

import androidx.appcompat.widget.AppCompatTextView;

import com.akistudio.R;

/**
 * A simple view to display terminal-like output.
 */
public class TerminalView extends ScrollView {

    private AppCompatTextView textView;

    public TerminalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        // Set ScrollView properties
        setBackgroundColor(getResources().getColor(R.color.terminal_background, null));
        setFillViewport(true);

        // Create and configure the TextView
        textView = new AppCompatTextView(context);
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        textView.setTextColor(getResources().getColor(R.color.terminal_text, null));
        textView.setTypeface(Typeface.MONOSPACE);
        textView.setPadding(24, 24, 24, 24);
        textView.setTextIsSelectable(true);
        
        addView(textView);
    }

    /**
     * Appends text to the terminal view and scrolls to the bottom.
     * @param text The text to append.
     */
    public void appendText(final String text) {
        post(() -> {
            textView.append(text);
            fullScroll(View.FOCUS_DOWN); // This line now compiles correctly
        });
    }

    /**
     * Clears all text from the terminal view.
     */
    public void clear() {
        post(() -> textView.setText(""));
    }
}
