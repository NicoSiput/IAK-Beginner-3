package nicosiput.com.iakbeginnerjustjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class OrderActivity extends AppCompatActivity {

    EditText mTextNama;
    TextView mTextHarga,mTextQty;
    Button mButtonOrder,mButtonPlus,mButtonMin;
    // spinner
    Spinner mSpinnerMenu;
    List<String> mListMenu = new ArrayList<>();
    int harga = 0;
    int qty = 0;
    String pesan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextNama = (EditText) findViewById(R.id.mTextNama);
        mTextHarga = (TextView) findViewById(R.id.mTextHarga);
        mButtonOrder = (Button) findViewById(R.id.mButtonOrder);
        mTextQty = (TextView) findViewById(R.id.mTextQty);
        mButtonPlus = (Button) findViewById(R.id.mButtonPlus);
        mButtonMin = (Button) findViewById(R.id.mButtonMin);

        mSpinnerMenu = (Spinner) findViewById(R.id.mSpinnerMenu);
        mListMenu.add("-------");
        mListMenu.add("Martabak");
        mListMenu.add("Piscok Bakar");
        mListMenu.add("Ice Cream Sandwich");
        mListMenu.add("Lumpia Basah");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mListMenu);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMenu.setAdapter(dataAdapter);
    }

    public void onClickOrder(View view){

        Boolean pakaiSusu, pakaiCoklat;
        RadioButton rb_susu = (RadioButton)findViewById(R.id.rbSusu);
        RadioButton rb_coklat = (RadioButton)findViewById(R.id.rbCoklat);

        pakaiSusu = rb_susu.isChecked();
        pakaiCoklat = rb_coklat.isChecked();

        pesan = ambilPesan(pakaiSusu,pakaiCoklat);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"nicosiput@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order untuk : " + mTextNama.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, pesan);
        try {
            startActivity(Intent.createChooser(emailIntent, "send mail.."));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(this, "There is no email client installed,", Toast.LENGTH_LONG).show();
        }
    }

    private String ambilPesan(boolean susu, boolean coklat)
    {
        pesan = "Saya Pesan : " + mSpinnerMenu.getSelectedItem();
        pesan = pesan + "\nSebanyak : " + qty;
        pesan = pesan + "\nTopping :";
        pesan = pesan + "\nSusu : " + susu;
        pesan = pesan + "\nCoklat " + coklat;
        pesan = pesan + "\nSeharga : " + harga;
        return pesan;
    }
    public void OnClickPlus(View view){
        harga = harga + 5;
        qty = qty + 1;
        mTextQty.setText(qty+"");
        mTextHarga.setText("$"+harga);
    }

    public void onClickMin(View view){
        if(harga != 0) {
            harga = harga - 5;
            qty = qty - 1;
            mTextQty.setText(qty+"");
            mTextHarga.setText("$"+harga);
        }
        else {
            harga = 0;
            qty = 0;
            mTextQty.setText(qty+"");
            mTextHarga.setText("$"+harga);
        }
    }

    public void onClickReset(View v){
        harga = 0;
        qty = 0;
        mTextNama.setText("");
        mTextHarga.setText("$"+harga);
        mTextQty.setText(qty+"");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
