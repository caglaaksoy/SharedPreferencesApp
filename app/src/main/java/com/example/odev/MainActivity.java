package com.example.odev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.odev.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;
    private Button bilgisilBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        bilgisilBtn=(Button) findViewById(R.id.bilgisilBtn);
        bilgisilBtn.setBackgroundColor(Color.TRANSPARENT);

        sharedPreferences = this.getSharedPreferences("com.example.odev", Context.MODE_PRIVATE);
        //sharedpreferrences kullanabilmek için yazdık

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        binding.YilEdt.setFilters(new InputFilter[] { filter });
    }


    public void saveFunction(View view) {
        String adEdt = binding.AdEdt.getText().toString();
        String konumEdt = binding.KonumEdt.getText().toString();
        String yilEdtText = binding.YilEdt.getText().toString();

        if (adEdt.isEmpty() || konumEdt.isEmpty() || yilEdtText.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
        } else {
            int yilEdt = 0;

            try {
                yilEdt = Integer.parseInt(yilEdtText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Geçersiz yıl değeri", Toast.LENGTH_SHORT).show();
                return;
            }

            sharedPreferences.edit().putString("kullanici_adi", adEdt).apply();
            sharedPreferences.edit().putString("konum_adi", konumEdt).apply();
            sharedPreferences.edit().putInt("yil", yilEdt).apply();

            Toast.makeText(this, "Bilgiler kaydedildi", Toast.LENGTH_SHORT).show();
        }
    }

    public  void showFunction(View view){
        String adTxt = sharedPreferences.getString("kullanici_adi","Kullanıcı Bulunamadı"); //ilki getirmek itediğimiz verinni idsi 2.si onu bulamazsa yerine nekoysun
        String konumTxt = sharedPreferences.getString("konum_adi","Konum Bulunamadı");
        Integer yilTxt = sharedPreferences.getInt("yil",0);

        binding.adTxt.setText("Öğrenci Ad Soyadı: "+adTxt);
        binding.konumTxt.setText("Konum: "+konumTxt);
        binding.yilTxt.setText("Mezuniyet Yılı: "+yilTxt);

        Toast.makeText(this, "Bilgiler gösterildi", Toast.LENGTH_SHORT).show();
    }

    public void deleteFunction(View view) {
        sharedPreferences.edit().clear().apply();
        Toast.makeText(this, "Bilgiler silindi", Toast.LENGTH_SHORT).show();
        binding.AdEdt.setText("Ad Soyad");
        showFunction(view);
        clearEditTextFields();
    }

    private void clearEditTextFields() {
        binding.AdEdt.getText().clear();
        binding.KonumEdt.getText().clear();
        binding.YilEdt.getText().clear();
    }
}

