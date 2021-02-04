package com.example.rty.quickmeal.modules;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rty.quickmeal.MainMenu;
import com.example.rty.quickmeal.R;



public class DoPayment extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton debit,credit,freecharge,mobikwick,paytm;
    EditText dn,dc,dd,cn,cc,cd,mn,fn,pn;
    Button proceed, cancel;
    int status=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_payment);
        dn=(EditText) findViewById(R.id.dn);
        dc=(EditText) findViewById(R.id.dc);
        dd=(EditText) findViewById(R.id.dd);
        cn=(EditText) findViewById(R.id.cn);
        cc=(EditText) findViewById(R.id.cc);
        cd=(EditText) findViewById(R.id.cd);
        mn=(EditText) findViewById(R.id.mn);
        fn=(EditText) findViewById(R.id.fn);
        pn=(EditText) findViewById(R.id.pn);

        proceed=(Button) findViewById(R.id.proceed);
        cancel=(Button) findViewById(R.id.cancel);

        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        debit=(RadioButton) findViewById(R.id.debit);
        credit=(RadioButton) findViewById(R.id.credit);
        freecharge=(RadioButton) findViewById(R.id.freecharge);
        mobikwick=(RadioButton) findViewById(R.id.mobikwick);

        //-------- Set activity title and back button -----------
        getSupportActionBar().setTitle("Select Payment Mode");


        paytm=(RadioButton) findViewById(R.id.paytm);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(debit.isChecked()){
                    dn.setVisibility(View.VISIBLE);
                    dc.setVisibility(View.VISIBLE);
                    dd.setVisibility(View.VISIBLE);
                    status=1;
                }
                if(!(debit.isChecked())){
                    dn.setVisibility(View.INVISIBLE);
                    dc.setVisibility(View.INVISIBLE);
                    dd.setVisibility(View.INVISIBLE);
                    status=1;
                }
                if(credit.isChecked()){
                    cn.setVisibility(View.VISIBLE);
                    cc.setVisibility(View.VISIBLE);
                    cd.setVisibility(View.VISIBLE);
                    status=1;
                }
                if(!(credit.isChecked())){
                    cn.setVisibility(View.INVISIBLE);
                    cc.setVisibility(View.INVISIBLE);
                    cd.setVisibility(View.INVISIBLE);
                    status=1;
                }
                if(freecharge.isChecked()){
                    fn.setVisibility(View.VISIBLE);
                    status=1;
                }
                if(!(freecharge.isChecked())){
                    fn.setVisibility(View.INVISIBLE);
                    status=1;
                }
                if(mobikwick.isChecked()){
                    mn.setVisibility(View.VISIBLE);
                    status=1;
                }
                if(!(mobikwick.isChecked())){
                    mn.setVisibility(View.INVISIBLE);
                    status=1;
                }
                if(paytm.isChecked()){
                    pn.setVisibility(View.VISIBLE);
                    status=1;
                }
                if(!(paytm.isChecked())){
                    pn.setVisibility(View.INVISIBLE);
                    status=1;
                }
            }
        });


        // ----------- Cancel Order process----------
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoPayment.this);

                //Setting message manually and performing action on button click
                //builder.setTitle("Delete " + user_fName.get(arg2) + " " + user_lName.get(arg2));
                builder.setTitle("Cancel Order! " );
                builder.setMessage("Are you sure you want to cancel your order?");

                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainMenu.class));
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regexStr = "^[0-9]*$";
                if(status==0){
                    Toast.makeText(getApplicationContext(), "Select payment method first",
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                if(debit.isChecked()){


                    if(dn.getText().toString().trim().matches(regexStr))
                    {

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Enter only number in debit card number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!(dn.getText().toString().length()==16)){
                        Toast.makeText(getApplicationContext(), "Enter 16 digit debit card number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }




                    if(dc.getText().toString().trim().matches(regexStr))
                    {

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Enter only number in CVV",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!(dc.getText().toString().length()==3)){
                        Toast.makeText(getApplicationContext(), "Enter 3 digit CVV number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }



                    if(dd.getText().toString().length()==0){
                        Toast.makeText(getApplicationContext(), "Enter valid date",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                if(credit.isChecked()){

                    if(cn.getText().toString().trim().matches(regexStr))
                    {

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Enter only number in credit card number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!(cn.getText().toString().length()==16)){
                        Toast.makeText(getApplicationContext(), "Enter 16 digit credit card number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }




                    if(cc.getText().toString().trim().matches(regexStr))
                    {

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Enter only number in CVV",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!(cc.getText().toString().length()==3)){
                        Toast.makeText(getApplicationContext(), "Enter 3 digit CVV number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }



                    if(cd.getText().toString().length()==0){
                        Toast.makeText(getApplicationContext(), "Enter valid date",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
                if(freecharge.isChecked()){

                    if(fn.getText().toString().trim().matches(regexStr))
                    {

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Enter only number in Contact",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!(fn.getText().toString().length()==10)){
                        Toast.makeText(getApplicationContext(), "Enter Mobile number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if(mobikwick.isChecked()){
                    if(mn.getText().toString().trim().matches(regexStr))
                    {

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Enter only number in Contact",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!(mn.getText().toString().length()==10)){
                        Toast.makeText(getApplicationContext(), "Enter valid Mobile number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if(paytm.isChecked()){

                    if(pn.getText().toString().trim().matches(regexStr))
                    {

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Enter only number in Contact",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!(pn.getText().toString().length()==10)){
                        Toast.makeText(getApplicationContext(), "Enter Mobile number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }



                Intent i = new Intent(getApplicationContext(),opt.class);
                startActivity(i);
            }
        });
    }


}
