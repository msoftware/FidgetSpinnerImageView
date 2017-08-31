# FidgetSpinnerImageView
This is a simple FidgetSpinner view. You can implement a Android FidgetSpinner with it.

The Image in the demo is taken from my android game
https://play.google.com/store/apps/details?id=com.jentsch.smartsquares
I added the FidgetSpinnerImageView in this project just for fun. So it is possible to spinn the logo :-). 

# Usage
1 Add the com.jentsch.fidgetspinnerview.FidgetSpinner to your layout

<com.jentsch.fidgetspinnerview.FidgetSpinner
        android:id="@+id/fidgetspinner"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
2. Add the image programmatically to the view.
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FidgetSpinner f = (FidgetSpinner)findViewById(R.id.fidgetspinner);
        f.setImageDrawable(R.drawable.spinner);
    }
    

[![FidgetSpinnerView demo](https://img.youtube.com/vi/6zTNGCX5VRk/0.jpg)](https://www.youtube.com/embed/6zTNGCX5VRk)
