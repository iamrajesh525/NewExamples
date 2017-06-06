package examples.com.examples;

/**
 * Created by Rajesh on 14-May-17.
 */

public class CallBack {
    //custom callback listener

    public interface MyListener {
        // you can define any parameter as per your requirement
        public void callback(View view, String result);
    }

    public class MyActivity extends Activity   {
        @override
        public void onCreate(){
            MyButton view = new MyButton(this);
            view.setMyListener(new MyListener() {
                @Override
                public void callback(View view, String result) {

                }
            });
        }

        // method invoke when mybutton will click
        @override
        public void callback(View view, String result) {
            // do your stuff here
        }
    }

    public class MyButton {
        MyListener myListener;

        public void setMyListener(MyListener myListener){
            this.myListener= myListener;
        }

        //call this method when callback is needed
        public void MyLogicToIntimateOthere() {
            ml.callback(this, "success");
        }
    }
}
