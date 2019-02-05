package com.transformers.allspark.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.transformers.allspark.R;
import com.transformers.allspark.control.AllSparkApp;
import com.transformers.allspark.control.TransformersAPI;
import com.transformers.allspark.model.Transformer;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TRANSFORMER_ID = "TRANSFORMER_ID";
    public static final String EDIT_MODE = "EDIT_MODE";
    private static final String TAG = "DetailActivity";

    private TransformersAPI api;
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

        String id = bundle.getString(TRANSFORMER_ID);
        transformer = api.getTransformer(id);

        boolean isEditMode = bundle.getBoolean(EDIT_MODE);

        btnEdit = findViewById(R.id.btnEdit);
        btnOk = findViewById(R.id.btnOk);
        btnDelete = findViewById(R.id.btnDelete);

        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        if (!isEditMode) {
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

    private void updateUI() {

        txtName.setText(transformer.getName());
        txtStrength.setText(Integer.toString(transformer.getStrength()));
        txtIntelligence.setText(Integer.toString(transformer.getIntelligence()));
        txtSpeed.setText(Integer.toString(transformer.getSpeed()));
        txtEndurance.setText(Integer.toString(transformer.getEndurance()));
        txtRank.setText(Integer.toString(transformer.getRank()));
        txtCourage.setText(Integer.toString(transformer.getCourage()));
        txtFirepower.setText(Integer.toString(transformer.getFirepower()));
        txtSkill.setText(Integer.toString(transformer.getSkill()));
        if (transformer.getTeam().equals(Transformer.TEAM_AUTOBOTS)) {
            imgTeamIcon.setImageResource(R.drawable.ic_autobot);
        } else {
            imgTeamIcon.setImageResource(R.drawable.ic_decepticon);
        }

        String imagePath = api.getTransformerImage(transformer);
        Log.d(TAG, "Loading image: " + imagePath);
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

    public void showEditDialog() {

    }

    public void showDeleteDialog() {

    }
}
