package sg.edu.rp.c346.id20015553.l12oursongsfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Song> songList;
    ArrayAdapter adapter, spinnerAdapter;
    String moduleCode;
    Spinner spinner;
    int requestCode = 9;
    Button btn5Stars, btnAdd;
    AlertDialog.Builder alert, alert1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        spinner = findViewById(R.id.spinner);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);
        btnAdd = findViewById(R.id.btnAdd);

        final DBHelper dbh = new DBHelper(this);
        final ArrayList<String> years = dbh.getYears();
        songList = dbh.getAllSongs();
        dbh.close();

        adapter = new CustomAdapter(this, R.layout.row, songList);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater factory = LayoutInflater.from(SecondActivity.this);
                View textEntryView = factory.inflate(R.layout.update, null);

                final EditText editName = textEntryView.findViewById(R.id.editName);
                final EditText editSinger = textEntryView.findViewById(R.id.editSinger);
                final EditText editYear = textEntryView.findViewById(R.id.editYear);
                final RatingBar stars = textEntryView.findViewById(R.id.ratingBar);
                alert = new AlertDialog.Builder(SecondActivity.this);
                final Song currentSong = songList.get(i);
                alert.setView(textEntryView).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        if (editName.getText().toString().isEmpty() || editSinger.getText().toString().isEmpty() || editYear.getText().toString().isEmpty()) {
                            Toast.makeText(SecondActivity.this, "Empty inputs", Toast.LENGTH_SHORT).show();
                        } else {

                            new AlertDialog.Builder(SecondActivity.this)
                                    .setTitle("Are you sure ?")
                                    .setMessage("Do you want to update this item")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            DBHelper dbh = new DBHelper(SecondActivity.this);
                                            currentSong.setTitle(editName.getText().toString().trim());
                                            currentSong.setSingers(editSinger.getText().toString().trim());
                                            int year = 0;
                                            try {
                                                year = Integer.valueOf(editYear.getText().toString().trim());
                                            } catch (Exception e) {
                                                Toast.makeText(SecondActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            currentSong.setYearReleased(year);
                                            currentSong.setStars((int) stars.getRating());
                                            int result = dbh.updateSong(currentSong);
                                            if (result > 0) {
                                                Toast.makeText(SecondActivity.this, "Song updated", Toast.LENGTH_SHORT).show();
                                                setResult(RESULT_OK);
                                                finish();
                                            } else {
                                                Toast.makeText(SecondActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                                            }

                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("No",null)
                                    .show();

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                alert.setView(textEntryView).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AlertDialog.Builder(SecondActivity.this)
                                .setTitle("Are you sure ?")
                                .setMessage("Do you want to update this item")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DBHelper dbh = new DBHelper(SecondActivity.this);
                                        int result = dbh.deleteSong(currentSong.getId());
                                        if (result>0){
                                            Toast.makeText(SecondActivity.this, "Song deleted", Toast.LENGTH_SHORT).show();
                                            setResult(RESULT_OK);
                                            finish();
                                        } else {
                                            Toast.makeText(SecondActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setNegativeButton("No",null)
                                .show();

                        adapter.notifyDataSetChanged();
                    }
                });
                alert.show();
                return true;
            }
        });
        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                songList.clear();
                songList.addAll(dbh.getAllSongsByStars(5));
                adapter.notifyDataSetChanged();
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                songList.clear();
                songList.addAll(dbh.getAllSongsByYear(Integer.valueOf(years.get(position))));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater factory = LayoutInflater.from(SecondActivity.this);
                View textEntryView = factory.inflate(R.layout.update, null);

                final EditText editName = textEntryView.findViewById(R.id.editName);
                final EditText editSinger = textEntryView.findViewById(R.id.editSinger);
                final EditText editYear = textEntryView.findViewById(R.id.editYear);
                final RatingBar stars = textEntryView.findViewById(R.id.ratingBar);
                alert1 = new AlertDialog.Builder(SecondActivity.this);
                alert1.setView(textEntryView).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        if (editName.getText().toString().isEmpty() || editSinger.getText().toString().isEmpty() || editYear.getText().toString().isEmpty()) {
                            Toast.makeText(SecondActivity.this, "Empty inputs", Toast.LENGTH_SHORT).show();
                        } else {
                            String title = editName.getText().toString().trim();
                            String singers = editSinger.getText().toString().trim();
                            if (title.length() == 0 || singers.length() == 0){
                                Toast.makeText(SecondActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String year_str = editYear.getText().toString().trim();
                            int year = Integer.valueOf(year_str);
                            float starss = stars.getRating();

                            DBHelper dbh = new DBHelper(SecondActivity.this);
                            long result = dbh.insertSong(title, singers, year, starss);

                            if (result != -1) {
                                Toast.makeText(SecondActivity.this, "Song inserted", Toast.LENGTH_LONG).show();
                                editName.setText("");
                                editSinger.setText("");
                                editYear.setText("");
                            } else {
                                Toast.makeText(SecondActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                alert1.show();
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            DBHelper dbh = new DBHelper(this);
            songList.clear();
            songList.addAll(dbh.getAllSongs());
            dbh.close();
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }






}
