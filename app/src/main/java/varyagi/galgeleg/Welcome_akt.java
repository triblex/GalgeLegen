package varyagi.galgeleg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class Welcome_akt extends AppCompatActivity implements Runnable {

    static Welcome_akt aktivitetDerVisesNu = null;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Welcome_akt", "Activity is started!");

        ImageView img = new ImageView(this);

        img.setImageResource(R.drawable.hangman);
        setContentView(img);

        // Hvis savedInstanceState ikke er null er det en aktivitet der er ved at blive genstartet
        //if (savedInstanceState == null) {
        handler.postDelayed(this, 3000); // <1> Kør run() om 3 sekunder
        //}
        aktivitetDerVisesNu = this;
    }

    public void run() {
        startActivity(new Intent(this, MainMenu_akt.class));
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        aktivitetDerVisesNu.finish();  // <2> Luk velkomsaktiviteten
        aktivitetDerVisesNu = null;
    }

    /**
     * Kaldes hvis brugeren trykker på tilbage-knappen.
     * I så tilfælde skal vi ikke hoppe videre til næste aktivitet
     */
    @Override
    public void finish() {
        super.finish();
        handler.removeCallbacks(this);
    }
}