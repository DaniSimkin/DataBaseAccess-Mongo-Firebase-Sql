package com.example.databaseaccess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.databaseaccess.Retrofit.IMyService;
import com.example.databaseaccess.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MongoActivity extends AppCompatActivity {

    TextView txt_create_account;
    MaterialEditText edit_login_email, edit_login_password;
    Button btn_login;
    boolean regSuccess = false; //

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mongo);

        //Init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        //Init view
        edit_login_email =  findViewById(R.id.edit_email_entrance);
        edit_login_password =  findViewById(R.id.edit_password_entrance);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(edit_login_email.getText().toString(),
                        edit_login_password.getText().toString());
            }
        });

        txt_create_account = (TextView) findViewById(R.id.txt_create_account);
        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup parent;
                ViewGroup root;
                final View register_layout = LayoutInflater.from(MongoActivity.this)
                        .inflate(R.layout.register_mongo_layout,null);



                new MaterialStyledDialog.Builder(MongoActivity.this)
                        .setIcon(R.drawable.ic_user)
                        .setTitle("REGISTRATION")
                        .setDescription("Please Fill Out All The Fields")
                        .setCustomView(register_layout)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                MaterialEditText edit_register_email = (MaterialEditText)register_layout.findViewById(R.id.edit_email); // register_layout here was in his video
                                MaterialEditText edit_register_name = (MaterialEditText)register_layout.findViewById(R.id.edit_name); // register_layout here was in his video
                                MaterialEditText edit_register_password = (MaterialEditText)register_layout.findViewById(R.id.edit_password); // register_layout here was in his video

                                if(TextUtils.isEmpty(edit_register_email.getText().toString()))
                                {
                                    Toast.makeText(MongoActivity.this,"Email Cannot Be Empty",Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if(TextUtils.isEmpty(edit_register_name.getText().toString()))
                                {
                                    Toast.makeText(MongoActivity.this,"Name Cannot Be Empty",Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if(TextUtils.isEmpty(edit_register_password.getText().toString()))
                                {
                                    Toast.makeText(MongoActivity.this,"Password Cannot Be Empty",Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                registerUser(edit_register_email.getText().toString(),
                                        edit_register_name.getText().toString(),
                                        edit_register_password.getText().toString());




                            }
                        }).show();

            }
        });

    }

    private void registerUser(String email, String name, String password) {

        compositeDisposable.add(iMyService.registerUser(email,name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(MongoActivity.this, "Registration Successful!"+response,Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    private void loginUser(String email, String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Email Cannot Be Empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Password Cannot Be Empty",Toast.LENGTH_SHORT).show();
            return;
        }

        compositeDisposable.add(iMyService.loginUser(email,password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String response) throws Exception {
                Toast.makeText(MongoActivity.this, ""+response,Toast.LENGTH_SHORT).show();
            }
        }));


    }



}
