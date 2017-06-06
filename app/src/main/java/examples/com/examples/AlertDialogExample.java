package examples.com.examples;

/**
 * Created by Rajesh on 14-May-17.
 */

public class AlertDialogExample {


    public void showAlertDialog(Context context, String title, final String message , final Boolean status ) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Register.this);
        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        if(status){
                            if(message.equals(UtilsConstants.NO_INTERNET)){
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                            else {
                                finish();
                            }

                        }

                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Notifications1.this);

        // set title
        //alertDialogBuilder.setTitle("Alert");
        // alertDialogBuilder.setMessage("Clear all notifications?");

        // set dialog message
        alertDialogBuilder
                .setMessage("Click YES to delete all notifications.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        NotificationsSeenAPI();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //dialog box
    public void dialogSelect(final ArrayList<String> al_country, final TextView btn_data) {
        CLICKED_ID = -1;

        // add listener to button
        // Create custom dialog object
        final Dialog dialog = new Dialog(CaptureTwo.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog);
        // Set dialog title
        dialog.setTitle("Custom Dialog");
        RadioGroup rgp = (RadioGroup) dialog.findViewById(R.id.radiogroup);
        RadioGroup.LayoutParams rprms;

        Button title = (Button) dialog.findViewById(R.id.title);
        title.setTypeface(FONT_BOLD);
        if (ll != null)
            rgp.removeView(ll);
        ll = new LinearLayout(getApplicationContext());
        for (int i = 0; i < al_country.size(); i++) {
            final RadioButton rdbtn = new RadioButton(getApplicationContext());
            rdbtn.setTextColor(getResources().getColor(R.color.dark_ash));
            rdbtn.setButtonDrawable(getApplicationContext().getResources().getDrawable(R.drawable.custom_radio_button));
            rdbtn.setTextSize(16);
            rdbtn.setTypeface(FONT_REGULAR);
            rdbtn.setPadding(15, 0, 0, 0);
            rprms = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            rprms.setMargins(50, 10, 0, 10);
            rdbtn.setId(i);
            rdbtn.setText(al_country.get(i));
            rdbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "" + rdbtn.getId(), Toast.LENGTH_SHORT).show();
                    // dialog.dismiss();

                    CLICKED_ID = rdbtn.getId();

                }
            });
            rgp.addView(rdbtn, rprms);
        }
        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setTypeface(FONT_BOLD);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CLICKED_ID == -1) {
                    dialog.dismiss();
                } else {
                    btn_data.setText(al_country.get(CLICKED_ID));
                    dialog.dismiss();

                }

            }
        });

        Button close = (Button) dialog.findViewById(R.id.close);
        close.setTypeface(FONT_BOLD);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        // rgp.addView(ll);
        dialog.show();
        //myDialog.show();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        Window window = dialog.getWindow();
        window.setLayout(width - (width * 10 / 100), height - (height * 40 / 100));
    }
}
