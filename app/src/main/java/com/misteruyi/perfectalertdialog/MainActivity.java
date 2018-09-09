package com.misteruyi.perfectalertdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cwg.perfectalertdialog.PerfectDialog;

public class MainActivity extends AppCompatActivity {

    private PerfectDialog perfectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenDialog(View view) {
        perfectDialog = new PerfectDialog(this)
                .addView(R.layout.custom_dialog_content)
                .addViewSetupInterface(inflatedView -> setUpViewContent(inflatedView, "Exit"))
                .setCancelable(true)
                .openDialog();
    }

    public void OpenDialogX(View view) {
        perfectDialog = new PerfectDialog(this, R.style.NoDialogAnimationOut)
                .addView(R.layout.custom_dialog_content)
                .addViewSetupInterface(inflatedView -> setUpViewContent(inflatedView, "Update"))
                .setCancelable(true)
                .openDialog();

    }

    private void setUpViewContent(View inflatedView, String buttonText) {
        EditText firstNameEt = inflatedView.findViewById(R.id.first_name_et);
        EditText lastNameEt = inflatedView.findViewById(R.id.last_name_et);
        Button enterButton = inflatedView.findViewById(R.id.enter_btn);

        firstNameEt.setText("Sui");
        lastNameEt.setText("Long");
        enterButton.setText(buttonText);

        if (buttonText.equalsIgnoreCase("Update"))
            enterButton.setOnClickListener(view -> perfectDialog = perfectDialog.updateView(R.layout.custom_dialog_content_2)
                    .addViewSetupInterface(inflatedView2 -> inflatedView2.findViewById(R.id.enter_btn_2)
                            .setOnClickListener(v -> perfectDialog.closeDialog()))
                    .setCancelable(true)
                    .openDialog()
            );
        else
            enterButton.setOnClickListener(view->perfectDialog.closeDialog());
    }
}
