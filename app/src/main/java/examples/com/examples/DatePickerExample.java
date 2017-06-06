package examples.com.examples;

/**
 * Created by Rajesh on 14-May-17.
 */

public class DatePickerExample {
    // Get Current Date
    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    //Raising a date picker
    DatePickerDialog dpd = new DatePickerDialog(Profile_Activity.this, new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            //i = year ; i1 = month of year ; i2 = day of month
            int m = (monthOfYear + 1);
            if (m < 10) {
                profile_DOB.setText("0" + dayOfMonth + "-" + m + "-" + year);
            } else {
                profile_DOB.setText(dayOfMonth + "-" + m + "-" + year);
            }
        }
    }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();

}
}
