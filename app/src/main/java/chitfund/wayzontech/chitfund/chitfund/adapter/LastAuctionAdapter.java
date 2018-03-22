package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.LastAuction;

/**
 * Created by sandy on 22/3/18.
 */

public class LastAuctionAdapter extends RecyclerView.Adapter<LastAuctionAdapter.LastAuctionViewHolder> {

    private Context context;
    private ArrayList<LastAuction> lastAuctionArrayList;

    public LastAuctionAdapter(Context context,
                              ArrayList<LastAuction> lastAuctionArrayList) {
        this.context = context;
        this.lastAuctionArrayList = lastAuctionArrayList;
    }

    public class LastAuctionViewHolder extends RecyclerView.ViewHolder {
        private TextView date,amount,lockAmount,groupName,receivedBy;
        public LastAuctionViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.textLastAuctDate);
            amount = itemView.findViewById(R.id.textLastAuctAmount);
            lockAmount = itemView.findViewById(R.id.textLastLockAmount);
            groupName = itemView.findViewById(R.id.textLastGroupName);
            receivedBy = itemView.findViewById(R.id.textLastReceivedBy);
        }
    }
    @Override
    public LastAuctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.adapter_last_auction,parent,false);
        return new LastAuctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LastAuctionViewHolder holder, int position)
    {
        LastAuction lastAuction = lastAuctionArrayList.get(position);

        holder.date.setText(lastAuction.getDate());
        holder.amount.setText(lastAuction.getAmount());
        holder.lockAmount.setText(lastAuction.getLock_amount());
        holder.groupName.setText(lastAuction.getGroup_name());
        holder.receivedBy.setText(lastAuction.getReceived_by());

    }

    @Override
    public int getItemCount() {
        return lastAuctionArrayList.size();
    }

}
