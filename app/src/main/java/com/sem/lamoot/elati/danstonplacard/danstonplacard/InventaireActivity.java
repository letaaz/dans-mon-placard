package com.sem.lamoot.elati.danstonplacard.danstonplacard;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.sem.lamoot.elati.danstonplacard.danstonplacard.database.model.Piece;

public class InventaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventaire);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setTitle(R.string.cuisine);
        Piece piece;
        Bundle extras = getIntent().getExtras();
        piece = (Piece) extras.get("piece");
        setTitle(piece.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
