package mostain.victory.com.onlinevehicle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    FirebaseUser user;
    String uservehicleType;
    private EditText glass, plateNumber, tyres;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout,submitButton;
    DatabaseReference databaseReference;

    String[] bankNames={"Cars","Trucks","Taxies","Motor Cycles","Van"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        glass = findViewById(R.id.glass);
        plateNumber = findViewById(R.id.platenumber);
        tyres = findViewById(R.id.tyres);
        submitButton =findViewById(R.id.submit);
        progressDialog = new ProgressDialog(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Report");

        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), bankNames[position], Toast.LENGTH_LONG).show();
        uservehicleType = bankNames[position];
        submitButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void addData(){
        String demail = user.getEmail();
        String dvehicleType = uservehicleType;
        String dglass = glass.getText().toString().trim();
        String dTyres = tyres.getText().toString().trim();
        String dPlateNumber = plateNumber.getText().toString().trim();
        progressDialog.setMessage("Submitting. Please Wait...");
        progressDialog.show();

        ModelOfData modelOfData = new ModelOfData(demail, dvehicleType, dPlateNumber, dglass, dTyres);

        databaseReference.push().setValue(modelOfData);
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Successfully submitted", Toast.LENGTH_LONG).show();
    }
}
