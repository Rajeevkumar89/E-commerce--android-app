package sellit.android.cs4251.com.sellit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static sellit.android.cs4251.com.sellit.SellActivity.REQUEST_IMAGE_CAPTURE;


public class SignUpActivity extends android.support.v7.app.AppCompatActivity {

    private EditText fname,lname,dname,emailid,newpass,cpassword;
    private Button signUpbtn,cancel,mSave;
    private  FirebaseAuth mAuth;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView result;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myRef;
    private String userID;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupUI();
        mSave = findViewById(R.id.select);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateAllFeilds();
                if(ValidateAllFeilds()){
                    //Uplaod the data to the database
                    final String f_name = fname.getText().toString();
                    final String l_name = lname.getText().toString();
                    final String d_name = dname.getText().toString();
                    final String Email_ID =emailid.getText().toString().trim();
                    final String n_pass = newpass.getText().toString().trim();
                    String c_pass = cpassword.getText().toString();



                    mAuth.createUserWithEmailAndPassword(Email_ID,n_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()) {

                               mFirebaseDatabase = FirebaseDatabase.getInstance();
                               myRef= mFirebaseDatabase.getReference();
                               FirebaseUser user = mAuth.getCurrentUser();
                               userID = user.getUid();
                               UserInformation userInformation = new UserInformation(f_name,l_name,d_name,Email_ID,n_pass);
                               myRef.child("users").child(userID).setValue(userInformation);

                               Toast.makeText(SignUpActivity.this, "Registration Successful!!!", Toast.LENGTH_SHORT).show();

                               startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                           }
                           else
                               Toast.makeText(SignUpActivity.this,"Registration failed!!!",Toast.LENGTH_SHORT).show();
                       }
                   });

                }

            }
        });


    }

    private void setupUI()
    {
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        dname = findViewById(R.id.dname);
        emailid = findViewById(R.id.emaiid);
        newpass = findViewById(R.id.newpass);
        cpassword = findViewById(R.id.newconfpass);
        signUpbtn = findViewById(R.id.sign_up_confirm_button);
        result=findViewById(R.id.img);

    }

    private boolean ValidateAllFeilds(){
        boolean result = false;
        String name = fname.getText().toString();
        String lastname = lname.getText().toString();
        String disname = dname.getText().toString();
        String EmailID =emailid.getText().toString();
        String npass = newpass.getText().toString();
        String Cpass = cpassword.getText().toString();

        if(name.isEmpty()|| lastname.isEmpty()|| disname.isEmpty() || EmailID.isEmpty()|| npass.isEmpty()||Cpass.isEmpty() )
        {
            Toast.makeText(this,"Please enter alll the details",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(!npass.equals(Cpass))
            {
                Toast.makeText(this,"Passwords doesn't match",Toast.LENGTH_SHORT).show();
            }
            result =true;
        }

        return result;
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            result.setImageBitmap(imageBitmap);
        }
    }
}
