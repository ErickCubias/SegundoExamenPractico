package com.example.ep2;

import static com.example.ep2.R.id.btnPlayPause;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Reproductor extends AppCompatActivity implements adaptadorMusic.OnItemClickListener {

    private List<cancion> songList = new ArrayList<>();
    private RecyclerView recyclerView;
    SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private int currentSongIndex = -1;
    private ImageButton btnPlayPause;
    private ImageButton btnNext;
    private  ImageButton btnPrevious;
    private LinearLayout liner;
    private TextView textView;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);


        recyclerView = findViewById(R.id.recyclerView);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnNext = findViewById(R.id.btnSiguiente);
        btnPrevious = findViewById(R.id.btnAnterior);
        liner = findViewById(R.id.linearLayout);
        textView = findViewById(R.id.Reproduciendo);
        seekBar = findViewById(R.id.seekBar);
        enviarDatos();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnPlayPause.setBackgroundResource(R.drawable.pausar);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteCancion();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPreviousSong();
            }
        });

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPause();
            }
        });


    }
    public void enviarDatos() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        songList.add(new cancion(R.drawable.up, "uptown funk you up", R.raw.uptownfunk));
        songList.add(new cancion(R.drawable.happy, "Happy", R.raw.happy));
        songList.add(new cancion(R.drawable.justtheway, "Just the way you are", R.raw.justthewayyouare));
        songList.add(new cancion(R.drawable.sugar, "Sugar", R.raw.sugar));
        songList.add(new cancion(R.drawable.rain, "have you ever seen the rain", R.raw.haveyoueverseentherain));

        adaptadorMusic adapter = new adaptadorMusic(songList, this, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    private void playPause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlayPause.setBackgroundResource(R.drawable.reproducir);
            } else {
                mediaPlayer.start();
                btnPlayPause.setBackgroundResource(R.drawable.pausar);
            }
        }
    }

    private void stopSong() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void siguienteCancion() {
        if (currentSongIndex < songList.size() - 1) {
            currentSongIndex++;
            cancion nextSong = songList.get(currentSongIndex);
            playSong(nextSong.getResourceId());
            liner.setBackground(ContextCompat.getDrawable(this, nextSong.getImage()));
            textView.setText(nextSong.getTittle());
        }
    }

    private void playPreviousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
            cancion previousSong = songList.get(currentSongIndex);
            playSong(previousSong.getResourceId());
            liner.setBackground(ContextCompat.getDrawable(this, previousSong.getImage()));
            textView.setText(previousSong.getTittle());
        }
    }

    private void playSong(int songResourceId) {
        stopSong();
        mediaPlayer = MediaPlayer.create(this, songResourceId);
        if (mediaPlayer != null) {
            seekBar.setMax(mediaPlayer.getDuration());

            seekBar.setProgress(0);

            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopSong();
                    siguienteCancion();
                }
            });

            final int[] currentPosition = {0};

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        try {
                            Thread.sleep(1000);
                            currentPosition[0] = mediaPlayer.getCurrentPosition();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    seekBar.setProgress(currentPosition[0]);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else {
            Toast.makeText(this, "No se puede reproducir", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onItemClick(int position) {
        if (position == currentSongIndex) {
            playPause();
        } else {
            cancion song = songList.get(position);
            playSong(song.getResourceId());
            currentSongIndex = position;

            liner.setBackground(ContextCompat.getDrawable(this, song.getImage()));
            textView.setText(song.getTittle());
        }
    }

}
