package chitfund.wayzontech.chitfund.chitfund.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

import chitfund.wayzontech.chitfund.chitfund.R;

public class AgentReportActivity extends AppCompatActivity {

    private EditText editTextInstallmentNo, editTextMemberCommission, editTextEntryNo,
            editTextRemailingCollection, editTextTotalRemailingCollection,
            editTextAmount, editTextFinalAmount, editTextSubmitAmount,
            editTextAdvanceAmount;

    private String strInstallmentNo, strMemeberCommission, strEntryNo,
            strRemailingCollection, strTotalRemailingCollection,
            strAmount, strFinalAmount, strSubmitAmount, strAdvanceAmount;

    private LinearLayout linearLayoutInstallmentNo, linearLayoutMemeberCommission,
            linearLayoutEntryNo, linearLayoutRemailingCollection,
            linearLayoutTotalRemailingCollection, linearLayoutAmount,
            linearLayoutFinalAmount, linearLayoutSubmitAmount,
            linearLayoutAdvanceAmount, linearLayoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_report);


        initialization();


    }

    private void initialization() {

        editTextInstallmentNo = findViewById(R.id.editTextInstallmentNo);
        editTextMemberCommission = findViewById(R.id.editTextMemberCommission);
        editTextEntryNo = findViewById(R.id.editTextEntryNo);
        editTextRemailingCollection = findViewById(R.id.editTextRemainingCollection);
        editTextTotalRemailingCollection = findViewById(R.id.editTextTotalRemainingCollection);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextFinalAmount = findViewById(R.id.editTextFinalAmount);
        editTextSubmitAmount = findViewById(R.id.editTextSubmitAmount);
        editTextAdvanceAmount = findViewById(R.id.editTextAdvanceAmount);

        linearLayoutInstallmentNo = findViewById(R.id.linearLayoutInstallmentNoAgent);
        linearLayoutMemeberCommission = findViewById(R.id.linearLayoutMemberCommissionAgent);
        linearLayoutEntryNo = findViewById(R.id.linearLayoutEntryNoAgent);
        linearLayoutRemailingCollection = findViewById(R.id.linearLayoutRemainingCollectionAgent);
        linearLayoutTotalRemailingCollection = findViewById(R.id.linearLayoutTotalRemainingCollectionAgent);
        linearLayoutAmount = findViewById(R.id.linearLayoutAmountAgent);
        linearLayoutFinalAmount = findViewById(R.id.linearLayoutFinalAmountAgent);
        linearLayoutSubmitAmount = findViewById(R.id.linearLayoutSubmitAmountAgent);
        linearLayoutAdvanceAmount = findViewById(R.id.linearLayoutAdvanceAmountAgent);
        linearLayoutMain = findViewById(R.id.linearLayoutMain);
    }

    private void textGetter() {
        strInstallmentNo = editTextInstallmentNo.getText().toString();
        strMemeberCommission = editTextMemberCommission.getText().toString();
        strEntryNo = editTextEntryNo.getText().toString();
        strRemailingCollection = editTextRemailingCollection.getText().toString();
        strTotalRemailingCollection = editTextTotalRemailingCollection.getText().toString();
        strAmount = editTextAmount.getText().toString();
        strFinalAmount = editTextFinalAmount.getText().toString();
        strSubmitAmount = editTextSubmitAmount.getText().toString();
        strAdvanceAmount = editTextAdvanceAmount.getText().toString();
    }
}
