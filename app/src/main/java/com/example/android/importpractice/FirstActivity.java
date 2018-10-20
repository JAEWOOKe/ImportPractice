package com.example.android.importpractice;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Base64;
        import android.widget.ImageView;

        import com.zomato.photofilters.geometry.Point;
        import com.zomato.photofilters.imageprocessors.Filter;
        import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
        import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
        import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
        import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubfilter;

public class FirstActivity extends AppCompatActivity{
    ImageView inputImageView;
    ImageView output1_ImageView;
    ImageView output2_ImageView;
    ImageView output3_ImageView;
    ImageView output4_ImageView;

    Bitmap inputImage;
    Bitmap outputImage1, outputImage2, outputImage3, outputImage4;

    //    Filter fooFilter;
    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        SharedPreferences result = getSharedPreferences("saveData", MODE_PRIVATE);
        String input = result.getString("imagePreference", "NOT FOUND");
        inputImage = decodeBase64(input);
        inputImageView = findViewById(R.id.input_imageView);
        output1_ImageView = findViewById(R.id.output1_imageView);
        output2_ImageView = findViewById(R.id.output2_imageView);
        output3_ImageView = findViewById(R.id.output3_imageView);
        output4_ImageView = findViewById(R.id.output4_imageView);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inMutable = true;
        // inputImage = BitmapFactory.decodeResource(getResources(), R.drawable.selfie, opts);
        inputImageView.setImageBitmap(inputImage);

        outputImage1 = inputImage.copy(inputImage.getConfig(), true);
        outputImage2 = inputImage.copy(inputImage.getConfig(), true);
        outputImage3 = inputImage.copy(inputImage.getConfig(), true);
        outputImage4 = inputImage.copy(inputImage.getConfig(), true);

//        Filter myFilter = new Filter();
//        myFilter.addSubFilter(new BrightnessSubfilter(20));
//        myFilter.addSubFilter(new ContrastSubfilter(0.5f));
//
//        outputImage1 = myFilter.processFilter(outputImage1);
//        output1_ImageView.setImageBitmap(outputImage1);

        output1_ImageView.setImageBitmap(Myfilter(outputImage1));
        output2_ImageView.setImageBitmap(ToneCurveSubfilter(outputImage2));
        output3_ImageView.setImageBitmap(SaturationSubfilter(outputImage3));
        output4_ImageView.setImageBitmap(ContrastSubfilter(outputImage4));

//        output2_ImageView.setOnClickListener(new myListener());
    }

    //    class myListener implements ImageView.OnClickListener {
//
//        @Override
//        public void onClick(ImageView v){
//            output1_ImageView.setImageBitmap(outputImage1);
//        }
//
//    }
    public Bitmap Myfilter(Bitmap input) {
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubfilter(20));
        myFilter.addSubFilter(new ContrastSubfilter(0.5f));
        Bitmap output = myFilter.processFilter(input);
        return output;
    }
    public Bitmap ToneCurveSubfilter(Bitmap input) {
        Filter Filter1 = new Filter();
        Point[] rgbKnots;
        rgbKnots = new Point[3];
        rgbKnots[0] = new Point(0, 0);
        rgbKnots[1] = new Point(175, 139);
        rgbKnots[2] = new Point(255, 255);

        Filter1.addSubFilter(new ToneCurveSubfilter(rgbKnots, null, null, null));
        Bitmap output = Filter1.processFilter(input);

        return output;
    }

    public Bitmap SaturationSubfilter(Bitmap input) {

        Filter Filter2 = new Filter();
        Filter2.addSubFilter(new SaturationSubfilter(1.3f));
        Bitmap output = Filter2.processFilter(input);

        return output;
    }

    public Bitmap ContrastSubfilter(Bitmap input) {

        Filter Filter3 = new Filter();
        Filter3.addSubFilter(new ContrastSubfilter(1.2f));
        Bitmap output = Filter3.processFilter(input);

        return output;
    }

    // method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}

/* https://stackoverflow.com/questions/18072448/how-to-save-image-in-shared-preference-in-android-shared-preference-issue-in-a */
