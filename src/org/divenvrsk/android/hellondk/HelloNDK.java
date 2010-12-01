package org.divenvrsk.android.hellondk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class HelloNDK extends Activity {

    static final String TAG = "HelloNDK!";
    static final int ARRAY_SIZE = 10000;
    long timeUsedByNativeSort;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.runAgainButton).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startSorting();
            }
        });

        startSorting();
    }

    void startSorting() {
        int[] unsortedArray = new int[ARRAY_SIZE];
        Random random = new Random(System.currentTimeMillis());
        for (int counter = 0; counter < ARRAY_SIZE; counter++) {
            unsortedArray[counter] = random.nextInt();
        }

        sortBubble(unsortedArray, unsortedArray.length);
        javaNativeSort(unsortedArray, unsortedArray.length);

        ((TextView) findViewById(R.id.textView)).setText(
                "Sorted " + ARRAY_SIZE + " items:\n\n" +
                        "NDK:\t" + String.valueOf(getExecutionTime() / 1000) + " milliseconds (" + getExecutionTime() + " microseconds" + ")"
                        + "\nSDK:\t" + timeUsedByNativeSort + " milliseconds");
    }

    int[] javaNativeSort(int[] unsorted, int array_size) {
        long startTime, endTime;
        int[] bubbles_array = new int[array_size];
        System.arraycopy(unsorted, 0, bubbles_array, 0, array_size);

        startTime = System.currentTimeMillis();
        Arrays.sort(bubbles_array);
        endTime = System.currentTimeMillis();

        timeUsedByNativeSort = endTime - startTime;
        Log.d(TAG, "Native Sort (Arrays.sort(...)). Sorted in: " + timeUsedByNativeSort + " milliseconds.");
        return bubbles_array;
    }

    native int getExecutionTime();
    native int[] sortBubble(int array_to_sort[], int array_length);

    static {
        System.loadLibrary("bubble-sort");
    }

}