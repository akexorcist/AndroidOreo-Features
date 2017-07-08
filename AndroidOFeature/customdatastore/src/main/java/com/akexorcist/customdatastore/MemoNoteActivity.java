package com.akexorcist.customdatastore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Akexorcist on 7/9/2017 AD.
 */

public class MemoNoteActivity extends AppCompatActivity {
    private static final String KEY_MESSAGE = "key_message";

    private EditText etSaySomething;
    private Button btnSave;
    private Button btnRestore;
    private Button btnClear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_note);

        bindView();
        setupView();
    }

    private void bindView() {
        etSaySomething = findViewById(R.id.etSaySomething);
        btnSave = findViewById(R.id.btnSave);
        btnRestore = findViewById(R.id.btnRestore);
        btnClear = findViewById(R.id.btnClear);
    }

    private void setupView() {
        btnSave.setOnClickListener(onSaveClick());
        btnRestore.setOnClickListener(onRestoreClick());
        btnClear.setOnClickListener(onClearClick());
    }

    private View.OnClickListener onSaveClick() {
        return view -> saveMessageToDataStore();
    }

    private View.OnClickListener onRestoreClick() {
        return view -> restoreMessageFromDataStore();
    }

    private View.OnClickListener onClearClick() {
        return view -> clearMessageFromDataStore();
    }

    private void saveMessageToDataStore() {
        String message = etSaySomething.getText().toString();
        if (TextUtils.isEmpty(message)) {
            showToast(R.string.error_empty_message);
            return;
        }
        MemoDataStore.getInstance(this).putString(KEY_MESSAGE, message);
        etSaySomething.setText("");
        showToast(R.string.saved);
    }

    private void restoreMessageFromDataStore() {
        String message = MemoDataStore.getInstance(this).getString(KEY_MESSAGE, null);
        showToast(message);
    }

    private void clearMessageFromDataStore() {
        MemoDataStore.getInstance(this).removeString(KEY_MESSAGE);
        showToast(R.string.cleared);
    }

    private void showToast(int resId) {
        showToast(getString(resId));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
