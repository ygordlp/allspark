package com.transformers.allspark.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.transformers.allspark.R;
import com.transformers.allspark.control.AllSparkApp;
import com.transformers.allspark.control.TransformersAPI;
import com.transformers.allspark.model.Transformer;
import com.transformers.allspark.util.AllSpark;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TRANSFORMER_ID = "TRANSFORMER_ID";
    public static final String NEW_TRANSFORMER = "NEW_TRANSFORMER";
    private static final String TAG = "DetailActivity";

    private TransformersAPI api;
    private AllSpark allSpark;
    private ProgressDialog dialog;
    private Button btnEdit;
    private Button btnOk;
    private Button btnDelete;
    private Transformer transformer;
    private TextView txtName;
    private TextView txtStrength;
    private TextView txtIntelligence;
    private TextView txtSpeed;
    private TextView txtEndurance;
    private TextView txtRank;
    private TextView txtCourage;
    private TextView txtFirepower;
    private TextView txtSkill;
    private ImageView imgTransformer;
    private ImageView imgTeamIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        AllSparkApp app = (AllSparkApp) getApplication();
        api = app.getTransformersAPI();
        allSpark = app.getAllSpark();

        String id = bundle.getString(TRANSFORMER_ID);
        transformer = api.getTransformer(id);

        boolean isNew = bundle.getBoolean(NEW_TRANSFORMER);

        btnEdit = findViewById(R.id.btnEdit);
        btnOk = findViewById(R.id.btnOk);
        btnDelete = findViewById(R.id.btnDelete);

        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        if (isNew) {
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        imgTransformer = findViewById(R.id.imgTransformer);
        imgTeamIcon = findViewById(R.id.imgTeamIcon);
        txtName = findViewById(R.id.txtName);
        txtStrength = findViewById(R.id.txtStrength);
        txtIntelligence = findViewById(R.id.txtIntelligence);
        txtSpeed = findViewById(R.id.txtSpeed);
        txtEndurance = findViewById(R.id.txtEndurance);
        txtRank = findViewById(R.id.txtRank);
        txtCourage = findViewById(R.id.txtCourage);
        txtFirepower = findViewById(R.id.txtFirepower);
        txtSkill = findViewById(R.id.txtSkill);

        updateUI();
    }

    /**
     * Updates UI with current Transformer values.
     */
    private void updateUI() {
        if (transformer == null) {
            Log.e(TAG, "Null Transformer for details");
            finish();
            return;
        }

        txtName.setText(transformer.getName());
        txtStrength.setText(Integer.toString(transformer.getStrength()));
        txtIntelligence.setText(Integer.toString(transformer.getIntelligence()));
        txtSpeed.setText(Integer.toString(transformer.getSpeed()));
        txtEndurance.setText(Integer.toString(transformer.getEndurance()));
        txtRank.setText(Integer.toString(transformer.getRank()));
        txtCourage.setText(Integer.toString(transformer.getCourage()));
        txtFirepower.setText(Integer.toString(transformer.getFirepower()));
        txtSkill.setText(Integer.toString(transformer.getSkill()));
        Picasso.get()
                .load(transformer.getTeam_icon())
                .error(R.drawable.card_placeholder)
                .into(imgTeamIcon);

        String imagePath = api.getTransformerImage(transformer);
        Picasso.get()
                .load(imagePath)
                .error(R.drawable.card_placeholder)
                .into(imgTransformer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                finish();
                break;
            case R.id.btnEdit:
                showEditDialog();
                break;
            case R.id.btnDelete:
                showDeleteDialog();
                break;
        }
    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.lbl_confirm_edit)
                .setPositiveButton(R.string.lbl_yes, (dialog, id) -> editConfirmed())
                .setNegativeButton(R.string.lbl_no, (dialog, id) -> dialogCanceled());
        builder.create().show();
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.lbl_confirm_delete)
                .setPositiveButton(R.string.lbl_yes, (dialog, id) -> deleteConfirmed())
                .setNegativeButton(R.string.lbl_no, (dialog, id) -> dialogCanceled());
        builder.create().show();
    }

    private void deleteConfirmed() {
        dialog = ProgressDialog.show(this, "", getString(R.string.lbl_deleting), true);
        new DeleteTask().execute();
    }

    private void onDeleteResult(boolean success) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (success) {
            Toast toast = Toast.makeText(this, R.string.lbl_delete_success, Toast.LENGTH_SHORT);
            toast.getView().setBackgroundResource(R.color.positive);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, R.string.lbl_delete_fail, Toast.LENGTH_LONG);
            toast.getView().setBackgroundResource(R.color.negative);
            toast.show();
        }
        finish();
    }

    private void editConfirmed() {
        dialog = ProgressDialog.show(this, "", getString(R.string.lbl_editing), true);
        allSpark.setRandomSpec(transformer);
        new EditTask().execute();
    }

    private void onEditResult(boolean success) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (success) {
            Toast toast = Toast.makeText(this, R.string.lbl_edit_success, Toast.LENGTH_SHORT);
            toast.getView().setBackgroundResource(R.color.positive);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, R.string.lbl_edit_fail, Toast.LENGTH_LONG);
            toast.getView().setBackgroundResource(R.color.negative);
            toast.show();
        }

        updateUI();
    }

    private void dialogCanceled() {
        Log.d(TAG, "Dialog canceled");
    }


    /**
     * Async task to delete transformer.
     */
    public class DeleteTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return api.deleteTransformer(transformer);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            onDeleteResult(result);
        }
    }

    /**
     * Async task to edit transformer.
     */
    public class EditTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return api.editTransformer(transformer);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            onEditResult(result);
        }
    }

}
