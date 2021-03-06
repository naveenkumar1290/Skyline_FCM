package planet.info.skyline;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import planet.info.skyline.controller.AppController;
import planet.info.skyline.util.Utils;

import static planet.info.skyline.util.Utility.URL_EP1;
import static planet.info.skyline.util.Utility.URL_EP2;


public class Updatecreatelocation extends BaseActivity {
    String updateloc = "";
    EditText et1;
    EditText et2;
    EditText et3, et4, et5;
    //ImageView i1,i2,i3,i4,i5,missing;
    ImageView missing, i1;
    TextView gomissi, govleraselect, gobacktosca, clientname;
    private TextView timerValue;
    String locationn, saveonnewloc = "", updatelocation = "";
    private ProgressDialog pDialog;
    String newlocr = "", crateid = "", unique_crateid = "";
    SharedPreferences sp;
    Editor ed;
    String webhit = "";
    DisplayImageOptions options;
    String mylocation = "real";
    ImageView homeacti;
    String oldid = "real";
    String casee = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_updatecreatelocation);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText11);
        et3 = (EditText) findViewById(R.id.editText12);
        et4 = (EditText) findViewById(R.id.editText13);
        et5 = (EditText) findViewById(R.id.editText14);
        i1 = (ImageView) findViewById(R.id.merchantname);
        missing = (ImageView) findViewById(R.id.missing);
        missing.setVisibility(View.VISIBLE);
//		i2=(ImageView)findViewById(R.id.selectvis);
//		i3=(ImageView)findViewById(R.id.selectvis1);
//		i4=(ImageView)findViewById(R.id.selectvis2);
//		i5=(ImageView)findViewById(R.id.selectvis3);
        gomissi = (TextView) findViewById(R.id.gomissi);
        i1.setImageResource(R.drawable.exhibitlogoa);
//		i2.setImageResource(R.drawable.selectgreena);
//		i3.setImageResource(R.drawable.selectgreena);
//		i4.setImageResource(R.drawable.selectgreena);
//		i5.setImageResource(R.drawable.selectgreena);
        timerValue = (TextView) findViewById(R.id.timer);
        gobacktosca = (TextView) findViewById(R.id.gobacktosca);
        govleraselect = (TextView) findViewById(R.id.govleraselect);

//		et1.setText("fjjf");
//	
//		
//		et2.setText("fwfhiwgfiwghfiwfh");
//		
//		et3.setText("fsdfshfishfhfvhugyuweghfuiwerhguirhegui");


        clientname = (TextView) findViewById(R.id.textView1);
        //   clientname.setText("");
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, new IntentFilter("subtask"));
//		i2.setVisibility(View.INVISIBLE);
//		i3.setVisibility(View.INVISIBLE);
//		i4.setVisibility(View.INVISIBLE);
//		i5.setVisibility(View.INVISIBLE);
        i1.setVisibility(View.VISIBLE);
        pDialog = new ProgressDialog(Updatecreatelocation.this);
        pDialog.setMessage("Kindly wait");
        pDialog.setCancelable(false);
        sp = getApplicationContext().getSharedPreferences("skyline", getApplicationContext().MODE_PRIVATE);
        ed = sp.edit();
        String nam = sp.getString("name", "");
        clientname.setText(nam);
        Intent ii = getIntent();
        Bundle nn = ii.getExtras();
        String ss = ii.getStringExtra("ss");
        //if(ii.)
        casee = ii.getStringExtra("case");
        try {
            if (nn.containsKey("oldlocation")) {
                //String lll="hello";//ii.getStringExtra("oldlocation");
                String lll = ii.getStringExtra("oldlocation");

                mylocation = lll;

            } else {

            }
            if (nn.containsKey("idi")) {
                //String lll="hello";//ii.getStringExtra("oldlocation");
                String llm = ii.getStringExtra("idi");

                oldid = llm;

            } else {

            }
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(),"wqdwqqd",2000).show();
//			String sdnv=	ii.getStringExtra("oldlocation");
//			 sdnv=sp.getString("oldlocation","");
            mylocation = "";
        }
        try {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 1);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            startActivity(marketIntent);

        }


        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.skylinelogopng)

                .showImageForEmptyUri(R.drawable.skylinelogopng)
                .showImageOnFail(R.drawable.skylinelogopng)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        String imageloc = sp.getString("imglo", "");
        if (imageloc.equals("") || imageloc.equalsIgnoreCase("")) {
            ed.putString("imglo", "").commit();
            missing.setVisibility(View.GONE);
        } else {
            imageLoadery.displayImage(imageloc, missing, options);
        }

        homeacti = (ImageView) findViewById(R.id.homeacti);


        homeacti.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ii = new Intent(Updatecreatelocation.this, SubmitClockTime.class);

                startActivity(ii);
                //	finish();

            }
        });

        gomissi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(Updatecreatelocation.this, "yes", 2000).show();
                //	startActivity(new Intent(Updatecreatelocation.this,SubmitClockTime.class));
            }
        });
        gobacktosca.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				startActivity(new Intent(Updatecreatelocation.this,Updatecreatelocation.class));
//			//onCreate(null);
//				finish();
                //recreate();
                startActivity(getIntent());
                finish();

            }
        });
        govleraselect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //showprogressdialog();
                if (crateid.equals("") || crateid.equalsIgnoreCase("")) {
                    if (et1.getText().toString().length() == 0) {
                        Toast.makeText(Updatecreatelocation.this, "Update LocationId can't be empty                  ", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                } else {
                    showdialogforupdateconfirm();
                }

                //	updatelocation(saveonnewloc);

            }
        });
    }

    public void eventgenerate() {
        JsonObjectRequest bb = new JsonObjectRequest(Method.GET, webhit, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject obj) {
                // TODO Auto-generated method stub
                //	Toast.makeText(Updatecreatelocation.this,"Successfully update ", Toast.LENGTH_LONG).show();
                hideprogressdialog();
                //hideprogressdialog();
                String loc = "";
                if (casee.equalsIgnoreCase("1") || casee.equals("1") || casee == "1") {
                    loc = mylocation;
                } else {
                    loc = locationn;
                }
/*			Intent ii=new Intent(Updatecreatelocation.this,Allmissingcreates.class);
            ii.putExtra("active", "update");
			ii.putExtra("alllocation", loc);
//			ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(ii);
			finish();*/
                Intent ii = new Intent(Updatecreatelocation.this, Allmissingcreates.class);
                ii.putExtra("active", "update");
                ii.putExtra("alllocation", loc);
//			ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.alert_with_intent(Updatecreatelocation.this, "Successfully update ", "", ii);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                //	hideprogressdialog();
                hideprogressdialog();
                String loc = "";
                if (casee.equalsIgnoreCase("1") || casee.equals("1") || casee == "1") {
                    loc = mylocation;
                } else {
                    loc = locationn;
                }
                Intent ii = new Intent(Updatecreatelocation.this, Allmissingcreates.class);
                ii.putExtra("active", "update");
                ii.putExtra("alllocation", loc);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //	ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ii);
                finish();

                Log.e("api_erro", arg0.toString());

            }
        });
    /*JsonObjectRequest jsonobj =new JsonObjectRequest(urlskyline, null, new Response.Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject arg0) {
			// TODO Auto-generated method stub
			
		}
	}, new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError arg0) {
			// TODO Auto-generated method stub
			
		}
	});	*/
        AppController.getInstance().addToRequestQueue(bb);
    }

    public void showprogressdialog() {
        pDialog.show();
    }

    public void hideprogressdialog() {
        pDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (casee.equalsIgnoreCase("1") || casee.equals("1") || casee == "1") {
                    try {
                        String result = "";
                        String[] gg = null;
                        String contents = data.getStringExtra("SCAN_RESULT");
                        //205.204.76.234/update_location_web.php?id=524&LocationId=arvind
                        if (contents.contains("loc=")) {
                            gg = contents.split("loc=");
                            String ll = contents;
                            String sss = ll.substring(ll.indexOf("_id="));
                            Log.d("value", sss);
                            String hom = contents;//"exhibitpower.com/admin/inventory/qrcrates1.php?act=edit_id=671_client=224_loc=S14-3-0";
                            result = hom.substring(hom.indexOf("_id=") + 4, hom.indexOf("_client"));


                            String ssdcf = result;
                            //exhibitpower.com/admin/inventory/qrcrates1.php?act=edit_id=671_client=224_loc=S14-3-0
                        }
                        if (casee.equalsIgnoreCase("1") || casee.equals("1") || casee == "1") {
                            try {
                                String createid = result;
                                try {
                                    locationn = gg[1];
                                    //updatelocation=gg[1];
                                } catch (Exception e) {
                                    locationn = "";
                                    //updatelocation="";
                                }

                                //
                                //locationn=mylocation;
                                //
                                updateloc =URL_EP1+"/show_location.php?id=" + createid;
                                //saveonnewloc="http://exhibitpower
                                // .com/update_location_web.php?id="+createid+"&LocationId="+locationn;
                                String tnam = sp.getString("tname", "arvind");
                                saveonnewloc =URL_EP1+"/update_location_web.php?id=" + createid + "&LocationId=" + mylocation + "&techname=" + tnam;
                                saveonnewloc = saveonnewloc.replace(" ", "%20");
                                //saveonnewloc="http://exhibitpower.com/update_location_web.php?id="+createid+"&LocationId="+locationn+"&techname=abhisir";
                                //updateloc="http://exhibitpower.com/update_location_web.php?id=524&location=arvind";
                            } catch (Exception ee) {
                                Toast.makeText(Updatecreatelocation.this, "Please scan a valid code                ", Toast.LENGTH_LONG).show();
                            }
                            showprogressdialog();
                            getoldloc(updateloc);

                        } else {

                            try {
                                String createid = result;
//					oldid=createid;
                                try {
                                    locationn = gg[1];
                                    //updatelocation=gg[1];
                                } catch (Exception e) {
                                    locationn = "";
                                    //updatelocation="";
                                }

                                //
                                //locationn=mylocation;
                                //
                                updateloc =URL_EP1+"/show_location.php?id=" + oldid;
                                //saveonnewloc="http://exhibitpower.com/update_location_web.php?id="+createid+"&LocationId="+locationn;
                                String tnam = sp.getString("tname", "arvind");
                                saveonnewloc =URL_EP1+"/update_location_web.php?id=" + oldid + "&LocationId=" + locationn + "&techname=" + tnam;
                                saveonnewloc = saveonnewloc.replace(" ", "%20");
                                //saveonnewloc="http://exhibitpower.com/update_location_web.php?id="+createid+"&LocationId="+locationn+"&techname=abhisir";
                                //updateloc="http://exhibitpower.com/update_location_web.php?id=524&location=arvind";
                            } catch (Exception ee) {
                                Toast.makeText(Updatecreatelocation.this, "Please scan a valid code          ", Toast.LENGTH_LONG).show();
                            }
                            showprogressdialog();
                            getoldloc(updateloc);

                        }
				
			/*	String createid=result;
				try{
					locationn=gg[1];
					//updatelocation=gg[1];
				}
				catch(Exception e){
					locationn="";
					//updatelocation="";
				}
				
			//
			//locationn=mylocation;
			//
				updateloc="http://exhibitpower.com/show_location.php?id="+createid;
				//saveonnewloc="http://exhibitpower.com/update_location_web.php?id="+createid+"&LocationId="+locationn;
				String tnam=sp.getString("tname", "arvind");
				  saveonnewloc="http://exhibitpower.com/update_location_web.php?id="+createid+"&LocationId="+locationn+"&techname="+tnam;
				  saveonnewloc=saveonnewloc.replace(" ", "%20");
				//saveonnewloc="http://exhibitpower.com/update_location_web.php?id="+createid+"&LocationId="+locationn+"&techname=abhisir";
				//updateloc="http://exhibitpower.com/update_location_web.php?id=524&location=arvind";
				  
				 */
                    } catch (Exception ee) {
                        Toast.makeText(Updatecreatelocation.this, "Please scan a valid code              ", Toast.LENGTH_LONG).show();
                    }
//				getoldloc(updateloc);
//				showprogressdialog();
                } else {
                    String contents = data.getStringExtra("SCAN_RESULT");
                    if (contents.contains("type=bin")) {
                        try {
                            String result = "";
                            String[] gg = null;
                            //String contents = data.getStringExtra("SCAN_RESULT");
                            String hom = contents;
                            result = hom.substring(hom.indexOf("_wn=") + 4, hom.length());
                            String ssdcf = result;
                            try {
                                try {
                                    locationn = gg[1];
                                } catch (Exception e) {
                                    locationn = "";
                                }
                                locationn = result;
                                updateloc =URL_EP1+"/show_location.php?id=" + oldid;

                                String tnam = sp.getString("tname", "arvind");
                                saveonnewloc =URL_EP1+"/update_location_web.php?id=" + oldid + "&LocationId=" + locationn + "&techname=" + tnam;
                                saveonnewloc = saveonnewloc.replace(" ", "%20");

                            } catch (Exception ee) {
                                Toast.makeText(Updatecreatelocation.this, "Please scan a valid code       ", Toast.LENGTH_LONG).show();
                            }
                            showprogressdialog();
                            getoldloc(updateloc);
                        } catch (Exception ee) {
                            Toast.makeText(Updatecreatelocation.this, "Please scan a valid code       ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // new code for old qr

                        String Crate_id = contents.substring(contents.indexOf("?id=") + 4,
                                contents.indexOf("_aid"));
                        Log.d("Crate_id", Crate_id);

                        saveonnewloc =URL_EP1+"/find_location_win.php?id="
                                + Crate_id;

                        getlocation(saveonnewloc);

                        //Toast.makeText(Updatecreatelocation.this,"Please scan a valid code      ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    public void getlocation(String links) {
        JsonObjectRequest updatess = new JsonObjectRequest(Method.GET, links,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsono) {
                try {
                    String new_location = jsono.getString("cds");
                    //---------------------///

                    try {
                        String result = "";
                        String[] gg = null;
                        //String contents = data.getStringExtra("SCAN_RESULT");
                        //String hom=contents;
                        result = new_location;
                        String ssdcf = result;
                        try {
                            try {
                                locationn = gg[1];
                            } catch (Exception e) {
                                locationn = "";
                            }
                            locationn = result;
                            updateloc =URL_EP1+"/show_location.php?id=" + oldid;

                            String tnam = sp.getString("tname", "arvind");
                            saveonnewloc =URL_EP1+"/update_location_web.php?id=" + oldid + "&LocationId=" + locationn + "&techname=" + tnam;
                            saveonnewloc = saveonnewloc.replace(" ", "%20");

                        } catch (Exception ee) {
                            Toast.makeText(Updatecreatelocation.this, "Please scan a valid code       ", Toast.LENGTH_LONG).show();
                        }
                        showprogressdialog();
                        getoldloc(updateloc);
                    } catch (Exception ee) {
                        Toast.makeText(Updatecreatelocation.this, "Please scan a valid code       ", Toast.LENGTH_LONG).show();
                    }


                    //new_location=scan
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                //
                //hideprogressdialog();
                //showdialogforcomplete();
//						Toast.makeText(ClientLeavingWithCrate.this,
//								"Successfully update errors", Toast.LENGTH_LONG)
//								.show();
            }
        });
        AppController.getInstance().addToRequestQueue(updatess);

    }

    public void getoldloc(String links) {
        JsonObjectRequest updatess = new JsonObjectRequest(Method.GET, updateloc, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsono) {
                // TODO Auto-generated method stub
                updatelayout(jsono);
                hideprogressdialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                hideprogressdialog();
            }
        });
        AppController.getInstance().addToRequestQueue(updatess);
    }

    public void updatelocation(String links) {
        JsonObjectRequest updatess = new JsonObjectRequest(Method.GET, links, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsono) {
                // TODO Auto-generated method stub
                //updatelayout(jsono);
                //	Toast.makeText(Updatecreatelocation.this,"Successfully update LocationId", Toast.LENGTH_LONG).show();
                //hideprogressdialog();
                //

//				String loc="";
//				if(casee.equalsIgnoreCase("1")||casee.equals("1")||casee=="1"){
//					loc=mylocation;
//				}
//				else{
//					loc=locationn;
//				}
//				Intent ii=new Intent(Updatecreatelocation.this,Allmissingcreates.class);
//				ii.putExtra("active", "update");
//				ii.putExtra("alllocation", loc);
////				ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//				ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(ii);
//				finish();
//				
                //
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                //hideprogressdialog();
                Toast.makeText(Updatecreatelocation.this, "Successfully update errors         ", Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(updatess);
    }

    public void updatelayout(JSONObject jjo) {
        JSONArray jj = null;
        try {
            jj = jjo.getJSONArray("cds");
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            String descsbout = "";
            String catetype = "";
            for (int i = 0; i <= jj.length(); i++) {
                try {
                    JSONObject jobj = jj.getJSONObject(i);
                    String cati = jobj.getString("id");
                    //String oldloc=jobj.getString("old_location");
                    crateid = cati;
                    String newloc = jobj.getString("cur_loacation");
                    descsbout = jobj.getString("description");
                    catetype = jobj.getString("type_name");
//			newlocr=newloc;
		/*	et1.setText(locationn);
			newlocr=locationn;//
			et2.setText(cati);
			ed.putString("catidnew", cati).commit();
			et3.setText(newloc);
			*/
//			et1.setText(locationn);
//			newlocr=locationn;//
//			String unique_craye_numb=jobj.getString("uni_crates");
//			et2.setText(unique_craye_numb);
//			ed.putString("catidnew", cati).commit();
//			et3.setText(mylocation);

                    if (casee.equalsIgnoreCase("1") || casee.equals("1") || casee == "1") {
                        et1.setText(mylocation);
                        newlocr = mylocation;//
                        String unique_craye_numb = jobj.getString("uni_crates");
                        unique_crateid = unique_craye_numb;
                        et2.setText(unique_craye_numb);
                        ed.putString("catidnew", cati).commit();
                        et3.setText(newloc);
                        et4.setText(catetype);
                        et5.setText(descsbout);
                    } else {
                        et1.setText(locationn);
                        newlocr = locationn;//
                        String unique_craye_numb = jobj.getString("uni_crates");
                        unique_crateid = unique_craye_numb;
                        et2.setText(unique_craye_numb);
                        ed.putString("catidnew", cati).commit();
                        et3.setText(mylocation);
                        et4.setText(catetype);
                        et5.setText(descsbout);
                    }

                } catch (Exception e) {

                }

            }
        } catch (Exception e) {

        }


    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            //Toast.makeText(Subtaskpage.this, "sub task added", 5000).show();
            //adapter1.notifyDataSetChanged();
            String taskida = intent.getStringExtra("value");
            String type = intent.getStringExtra("type");///type
            timerValue.setText(taskida);
//				if(type.equalsIgnoreCase("subtask")){
//				if(taskida.equals(Taskid))
//			      getlistsubtaskdata ();//
//				}
//				else if(type.equalsIgnoreCase("note")){
//					if(taskida.equals(Taskid))
//						getnotelistdata();
//				}
//				else{
//					
//				}

        }
    };

    public void showdialogforupdateconfirm() {
        final Dialog showd = new Dialog(Updatecreatelocation.this);
        showd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showd.setContentView(R.layout.alerdialforupdatelocat_new);
        showd.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        showd.setTitle("");
        showd.setCancelable(false);
        TextView nofo = (TextView) showd.findViewById(R.id.Btn_No);
        TextView yesfo = (TextView) showd.findViewById(R.id.Btn_Yes);
        TextView texrtdesc = (TextView) showd.findViewById(R.id.texrtdesc);
        texrtdesc.setText("Has crate " + unique_crateid + " been put in Location " + newlocr + " ?");//
        ImageView close = (ImageView) showd.findViewById(R.id.close);


        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//								startActivity(getIntent());
//								finish();

                showd.dismiss();

            }
        });
        nofo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(getIntent());
                finish();
                showd.dismiss();

            }
        });

        yesfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showprogressdialog();
                updatelocation(saveonnewloc);
                String jobid = sp.getString("jobid", "");
                //webhit="http://exhibitpower2.com/Register/auto_generate_event2.aspx?swo_id="+jobid+"&status=start";//+"&"+jobid;
                webhit = URL_EP2+"/Register/auto_generate_event2.aspx?swo_id=" + jobid + "&status=update";//+"&"+jobid;
                //  showprogressdialog();
                eventgenerate();
                showd.dismiss();
            }
        });

        showd.show();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//		et1.setText("");
//		et2.setText("");
//		et3.setText("");
    }

}
