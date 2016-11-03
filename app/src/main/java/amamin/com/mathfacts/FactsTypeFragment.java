package amamin.com.mathfacts;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static amamin.com.mathfacts.MathFactsType.*;

public class FactsTypeFragment extends PreferenceFragment {
    private Context context;

    @BindView(R.id.fact_type_selector)
    RadioGroup fact_selector;

    public FactsTypeFragment() {
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
        setCurrentOperator();
    }

    private void setCurrentOperator()
    {
        MathFactsType selectedType = values()[MathFactsUtility.getPreference(getActivity(), MathFactsConstants.SP_Fact_Type, ADDITION.ordinal())];
        switch(selectedType) {
            case ADDITION:
                fact_selector.check(R.id.addition);
                break;
            case SUBTRACTION:
                fact_selector.check(R.id.subtraction);
                break;
            case MULTIPLICATION:
                fact_selector.check(R.id.multiplication);
                break;
            case DIVISION:
                fact_selector.check(R.id.division);
                break;
        }
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
        return inflater.inflate(R.layout.fragment_facts_type, container, false);
    }

    @OnClick(R.id.save_button)
    public void saveClicked()
    {
        switch(fact_selector.getCheckedRadioButtonId())
        {
            case R.id.addition:
                MathFactsUtility.setPreference(context, MathFactsConstants.SP_Fact_Type, ADDITION.ordinal());
                break;
            case R.id.multiplication:
                MathFactsUtility.setPreference(context, MathFactsConstants.SP_Fact_Type, MULTIPLICATION.ordinal());
                break;
            case R.id.division:
                MathFactsUtility.setPreference(context, MathFactsConstants.SP_Fact_Type, DIVISION.ordinal());
                break;
            case R.id.subtraction:
                MathFactsUtility.setPreference(context, MathFactsConstants.SP_Fact_Type, SUBTRACTION.ordinal());
                break;
        }
        getActivity().onBackPressed();
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
