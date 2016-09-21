package amamin.com.mathfacts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaxNumberFragment extends PreferenceFragment {
    @BindView(R.id.max_number_picker)
    SeekBar maxSelectorSeekBar;

    @BindView(R.id.max_value)
    TextView maxValueDisplay;
    private Context context;

    public MaxNumberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setListener();
        getMax();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_max_number, container, false);
    }
    @OnClick(R.id.set_max_button)
    public void saveMax()
    {
        SharedPreferences preferences = context.getSharedPreferences(MathFactsConstants.SharedPreferences, Context.MODE_PRIVATE);
        preferences.edit().putInt(MathFactsConstants.SP_Max_Addend, maxSelectorSeekBar.getRight()).apply();
        getActivity().onBackPressed();
    }

    private void getMax()
    {
        if(maxSelectorSeekBar!=null) {
            SharedPreferences preferences = context.getSharedPreferences(MathFactsConstants.SharedPreferences, Context.MODE_PRIVATE);
            maxSelectorSeekBar.setProgress(preferences.getInt(MathFactsConstants.SP_Max_Addend, 1));
        }
    }
    private void setListener() {
        if(maxSelectorSeekBar!=null)
        {
            maxSelectorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    maxValueDisplay.setText(String.valueOf(i));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
