package varyagi.galgeleg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu_akt extends AppCompatActivity implements View.OnClickListener {

    Button bPlay, bSettings, bHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        bPlay = (Button) findViewById(R.id.bPlay);
        bSettings = (Button) findViewById(R.id.bSettings);
        bHelp = (Button) findViewById(R.id.bHelp);


        bPlay.setOnClickListener(this);
        bSettings.setOnClickListener(this);
        bHelp.setOnClickListener(this);
    }
    public void onClick(View v) {

        if(v == bPlay){
            Intent play = new Intent(this, Game_akt.class);
            this.startActivity(play);
        }

        if(v == bSettings){
            Intent settings = new Intent(this, Settings_akt.class);
            this.startActivity(settings);
        }

        if(v == bHelp){
            Intent help = new Intent(this, Help_akt.class);
            this.startActivity(help);
        }
    }
}
