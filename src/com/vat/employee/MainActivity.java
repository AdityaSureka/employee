package com.vat.employee;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.vat.employee.JSONParser;
import com.vat.employee.SessionManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class MainActivity extends Activity {
	 private int year, month, day;
	 static final int ID_DATEPICKER = 0;
	 Button button;
	 //DatePicker datePicker1;
	private ProgressDialog pDialog;
	EditText fn,ln,ag,dob,em,mob; 
	Button b;
	RadioGroup rg;
	RadioButton rb1,rb2;
	String a,male;
//	public List<NameValuePair> params;
	SharedPreferences pref;
    
    private static String url_user_login = "http://54.225.191.181/bank.php";
    SessionManager session;
    
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    // Editor for Shared preferences
    Editor editor;    
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "fname";
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    private TextView text_date;
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	setCurrentDate();
addButtonListener();
	fn=(EditText)findViewById(R.id.editText1);
	ln=(EditText)findViewById(R.id.editText2);
	ag=	(EditText)findViewById(R.id.editText3);	
//dob=(EditText)findViewById(R.id.datePicker1);
 em=(EditText)findViewById(R.id.editText5);
 mob=(EditText)findViewById(R.id.editText6);
 rg = (RadioGroup) findViewById(R.id.radioGroup1);
 rb1=(RadioButton)findViewById(R.id.radio0);
 rb2=(RadioButton)findViewById(R.id.radio1);
		b=(Button)findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() 
		 {
		 public void onClick(View v)
		 {
   			   new StoreDetailsemp().execute();
         }
		});
	}
	public void setCurrentDate() {
			        text_date = (TextView) findViewById(R.id.text_date);
			       // datePicker1= (DatePicker)findViewById(R.id.datePicker1);
			        final Calendar calendar = Calendar.getInstance();
			        year = calendar.get(Calendar.YEAR);
			        month = calendar.get(Calendar.MONTH);
			        day = calendar.get(Calendar.DAY_OF_MONTH);
		     // set current date into textview
	       //text_date.setText(new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year).append(" "));
			        // set current date into Date Picker
			   }		
			    public void addButtonListener() {
			        button = (Button) findViewById(R.id.button2);
			   	    button.setOnClickListener(new OnClickListener() {
			    	public void onClick(View v) {
			                showDialog(ID_DATEPICKER);
			    	}
			    	        });
			    	
			    	    }
			 protected Dialog onCreateDialog(int id) {
			 switch (id) {
						        case ID_DATEPICKER:
			          return new DatePickerDialog(this, datePickerListener, year, month,day);
			        }
			        return null;
			    }	
			    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
		             year = selectedYear;
			            month = selectedMonth;
			            day = selectedDay;
			          // text_date.setText(new StringBuilder().append(month + 1)
			           //.append("-").append(day).append("-").append(year).append(" "));
			            // set selected date into Date Picker
			       //     datePicker1.init(year, month, day, null);
			        }
			    };

 public class StoreDetailsemp extends AsyncTask<String, String, String> {
	
/**
 * Before starting background thread Show Progress Dialog
 * */

protected void onPreExecute() {
    super.onPreExecute();
  
  
    pDialog = new ProgressDialog(MainActivity.this);
    pDialog.setMessage("Saving Data..");
    pDialog.setIndeterminate(false);
    pDialog.setCancelable(true);
    pDialog.show();
  
}

//@Override
//protected String doInBackground(String... params) {
	// TODO Auto-generated method stub
	//return null;
//}
 

public String doInBackground(String... args)
{
	int selected = rg.getCheckedRadioButtonId();
		  RadioButton rb1 =(RadioButton)findViewById(selected);
		   String gender = rb1.getText().toString(); 
           String dob =  String.valueOf(year)+("-")+String.valueOf(month+1)+("-")+String.valueOf(day);
		 
	// declare parameters that are passed to PHP script i.e. the name "birthyear" and its value submitted by user   
   // ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    List<NameValuePair> params = new ArrayList<NameValuePair>();

	 
	 // define the parameter
    
		params.add(new BasicNameValuePair("fname",fn.getText().toString()));	   
		params.add(new BasicNameValuePair("lname",ln.getText().toString()));		   			       
		//params.add(new BasicNameValuePair("age",ag.getText().toString()));		
		params.add(new BasicNameValuePair("gender",gender));	 
		params.add(new BasicNameValuePair("dob",dob));		   
		params.add(new BasicNameValuePair("emailid",em.getText().toString()));		
		params.add(new BasicNameValuePair("contactno",mob.getText().toString()));		      
		String response = null;
        JSONObject json = jsonParser.makeHttpRequest(url_user_login,"POST", params);
        // check log cat fro response
        //Log.d("Create Response", json.toString());   
	       // call executeHttpPost method passing necessary parameters 
	       try {
	    	   int success = json.getInt(TAG_SUCCESS);
               if(success==1)
           {
               //session.createLoginSession(success, success);
           	Intent i = new Intent(getApplicationContext(), DisplayActivity.class);
               startActivity(i);
           }                
           
           }
	       catch (JSONException e) {
           e.printStackTrace();
       } 
	    	   
	   return null; 
/**
* After completing background task Dismiss the progress dialog
* **/
}
protected void onPostExecute(String file_url) {
    // dismiss the dialog once done
    pDialog.dismiss();
    }
 }
}
