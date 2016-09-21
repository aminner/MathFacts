package amamin.com.mathfacts;

import android.content.Context;
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

public class MaxTimeFragment extends PreferenceFragment {
    @BindView(R.id.max_min_picker)
    SeekBar maxTimeSelectorSeekBar;

    @BindView(R.id.max_min_value)
    TextView maxValueDisplay;

    private Context context;

    public MaxTimeFragment() {
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
        return inflater.inflate(R.layout.fragment_max_time, container, false);
    }

    @OnClick(R.id.set_max_min_button)
    public void saveMax()
    {
        MathFactsUtility.setPreference(context, MathFactsConstants.SP_Max_Min, maxTimeSelectorSeekBar.getProgress());
        getActivity().onBackPressed();
    }

    private void getMax()
    {
        if(maxTimeSelectorSeekBar!=null) {
            maxTimeSelectorSeekBar.setProgress(MathFactsUtility.getPreference(context, MathFactsConstants.SP_Max_Min)-1);
        }
    }
    private void setListener() {
        if(maxTimeSelectorSeekBar!=null)
        {
            maxTimeSelectorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    maxValueDisplay.setText(String.valueOf(i+1));
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
