package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chitfund.wayzontech.chitfund.chitfund.R;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //initRecyclerView(view);
        //prepareMovieData();
        return view;
    }

//    public void initRecyclerView(View view)
//    {
//       recyclerView =  view.findViewById(R.id.recyclerview_home);
//       memberName = new ArrayList<>();
//       layoutManager = new LinearLayoutManager(getActivity());
//       recyclerView.setLayoutManager(layoutManager);
//       recyclerView.setHasFixedSize(true);
//       groupListAdapter = new GroupListAdapter(memberName,getContext());
//       //recyclerView.setAdapter(groupListAdapter);
//       //recyclerView.addOnItemTouchListener(this);
//
//    }
////    private void prepareMovieData()
////    {
////
////        for (int i=1;i<=60;i++) {
////            MemberName memberName = new MemberName("Group # " + i);
////            this.memberName.add(memberName);
////
////        }
////
////    }
//
//    void recyclerView()
//    {
//       recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//           @Override
//           public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//               return false;
//           }
//
//           @Override
//           public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//           }
//
//           @Override
//           public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//           }
//       });
//
//    }

}
