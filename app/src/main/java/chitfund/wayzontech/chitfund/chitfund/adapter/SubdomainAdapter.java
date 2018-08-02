package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.activity.AgentActivity;
import chitfund.wayzontech.chitfund.chitfund.activity.MainActivity;
import chitfund.wayzontech.chitfund.chitfund.model.Subdomain;
import chitfund.wayzontech.chitfund.chitfund.model.UserProfile;
import chitfund.wayzontech.chitfund.chitfund.session.SubdomainSession;

public class SubdomainAdapter extends RecyclerView.Adapter<SubdomainAdapter.RecyclerViewHolder> {
    private List<UserProfile> userProfileArrayList;
    private Context context;
    private String strUserame, strSubdomain, strUserid, strRoleid, strName;
    private SubdomainSession subdomainSession;

    public SubdomainAdapter(List<UserProfile> userProfileArrayList, Context context, SubdomainSession subdomainSession) {
        this.userProfileArrayList = userProfileArrayList;
        this.context = context;
        this.subdomainSession = subdomainSession;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_subdomain, parent, false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        final UserProfile userProfile = userProfileArrayList.get(position);
        holder.subdomain.setText(userProfile.getSundomainName());
        holder.name.setText(userProfile.getUsername());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSubdomain = userProfile.getSundomainName();
                strUserame = userProfile.getName();
                strRoleid = userProfile.getRole_id();
                strUserid = userProfile.getUser_id();
                strName = userProfile.getUsername();

                if (strRoleid.equals("3")) {
                    Subdomain subdomain = new Subdomain();
                    subdomain.setStrSubdomain(strSubdomain);
                    subdomain.setStrUsername(strName);
                    subdomain.setStrRoleId(strRoleid);
                    subdomain.setStrUserId(strUserid);
                    subdomain.setStrName(strUserame);
                    subdomainSession.userLogin(subdomain);
                    context.startActivity(new Intent(context, MainActivity.class));
                    ((Activity) context).finish();
                } else if (strRoleid.equals("4")) {
                    Subdomain subdomain = new Subdomain();
                    subdomain.setStrSubdomain(strSubdomain);
                    subdomain.setStrUsername(strName);
                    subdomain.setStrRoleId(strRoleid);
                    subdomain.setStrUserId(strUserid);
                    subdomain.setStrName(strUserame);
                    subdomainSession.userLogin(subdomain);
                    context.startActivity(new Intent(context, AgentActivity.class));
                    ((Activity) context).finish();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return userProfileArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView subdomain, name;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            subdomain = itemView.findViewById(R.id.textViewSubdomainName);
            name = itemView.findViewById(R.id.textViewSubdomainUserName);
        }
    }
}
