# FidgetSpinnerImageView
This is a simple FidgetSpinner view. You can implement a Android FidgetSpinner with it.<br />

The Image in the demo is taken from my android game<br />
https://play.google.com/store/apps/details?id=com.jentsch.smartsquares<br />
I added the FidgetSpinnerImageView in this project just for fun. So it is possible to spinn the logo :-). <br />

# Usage
1 Add the com.jentsch.fidgetspinnerview.FidgetSpinner to your layout<br />

<com.jentsch.fidgetspinnerview.FidgetSpinner<br />
        android:id="@+id/fidgetspinner"<br />
        android:layout_width="match_parent"<br />
        android:layout_height="match_parent"><br />
        
2. Add the image programmatically to the view.<br />
@Override<br />
    protected void onCreate(Bundle savedInstanceState) {<br />
        super.onCreate(savedInstanceState);<br />
        setContentView(R.layout.activity_main);<br />
        FidgetSpinner f = (FidgetSpinner)findViewById(R.id.fidgetspinner);<br />
        f.setImageDrawable(R.drawable.spinner);<br />
    }<br />
    <br />

[![FidgetSpinnerView demo](https://img.youtube.com/vi/6zTNGCX5VRk/0.jpg)](https://www.youtube.com/embed/6zTNGCX5VRk)<br />
