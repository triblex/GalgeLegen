package varyagi.galgeleg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class Game_akt extends AppCompatActivity implements View.OnClickListener {

    Galgelogik game = new Galgelogik();

    private int usedLetters = 0;

    ImageView img;
    TextView tTypeField;
    TextView tWrongLetters;
    TextView tStatus;
    TextView tVisibleWord;
    private int current_image;
    int[] images;

    Button bEnter, bReset, bMainMenu;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_akt);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String ord = prefs.getString("ord", "(ukendt)");
        System.out.println("########################################################################  " + ord);

        if(ord.equals("(ukendt)")) {
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        game.hentOrdFraDr();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();
        }
        else {

        }


        images = new int[]{R.drawable.galge, R.drawable.forkert1, R.drawable.forkert2, R.drawable.forkert3, R.drawable.forkert4, R.drawable.forkert5, R.drawable.forkert6};

        img = (ImageView) findViewById(R.id.pGalge);
        tTypeField = (TextView) findViewById(R.id.TypeField);
        tWrongLetters = (TextView) findViewById(R.id.tWrongLetters);
        tStatus = (TextView) findViewById(R.id.tStatus);
        tVisibleWord = (TextView) findViewById(R.id.tVisibleWord);

        tVisibleWord.setText(game.getSynligtOrd());

        bEnter = (Button) findViewById(R.id.bEnter);
        bReset = (Button) findViewById(R.id.bReset);
        bMainMenu = (Button) findViewById(R.id.bMainMenu);


        bEnter.setOnClickListener(this);
        bReset.setOnClickListener(this);
        bMainMenu.setOnClickListener(this);

    }


    public void onClick(View v) {
        final WebView webView = new WebView(this);

        if (!game.erSpilletSlut()) {

            if(v == bMainMenu) {
                Intent menu = new Intent(this, MainMenu_akt.class);
                this.startActivity(menu);
            }

            if (v == bReset) {
                usedLetters = 0;
                current_image = 0;
                img.setImageResource(R.drawable.galge);
                game.nulstil();
                tWrongLetters.setText("");
                tVisibleWord.setText(game.getSynligtOrd());
                tStatus.setText("Welcome to Galgeleg");
            } else if (v == bEnter) {

                if (tTypeField.getText().toString().length() == 1) {
                    if (tWrongLetters.getText().toString().contains(tTypeField.getText().toString())) {
                        tTypeField.setText("");
                        tTypeField.setHint("Already Used.");

                    } else {
                        game.gætBogstav(tTypeField.getText().toString());
                        tTypeField.setText("");
                        if (game.erSidsteBogstavKorrekt()) {
                            tVisibleWord.setText(game.getSynligtOrd());
                            String ord = game.getSynligtOrd();
                            prefs.edit().putString("ord", ord).commit();
                        } else {
                            tWrongLetters.append(" " + game.getBrugteBogstaver().get(usedLetters));
                            current_image++;
                            current_image = current_image % images.length;
                            img.setImageResource(images[current_image]);

                        }
                        usedLetters++;
                    }

                    if (game.erSpilletVundet()) {
                        tStatus.setText("YOU WON THE GAME!");

                    }
                    if (game.erSpilletTabt()) {
                        tStatus.setText("YOU LOST THE GAME!");
                        tVisibleWord.setText("Ordet var: \"" + game.getOrdet() + "\", Du brugte " + game.getAntalForkerteBogstaver() + " forsøg.");

                    }

                } else {
                    tTypeField.setText("");
                    tTypeField.setHint("Type one, and ONLY one letter!.");
                }
            }

        }
        else {
            tStatus.setText("Press 'Reset' to try again.");
            if (v == bReset) {
                tTypeField.setText("");
                usedLetters = 0;
                current_image = 0;
                img.setImageResource(R.drawable.galge);
                game.nulstil();
                tWrongLetters.setText("");
                tVisibleWord.setText(game.getSynligtOrd());
                tStatus.setText("Welcome to Galgeleg");
            }
            if(v == bMainMenu) {
                Intent menu = new Intent(this, MainMenu_akt.class);
                this.startActivity(menu);
            }
        }
    }
}
