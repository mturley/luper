package com.teamluper.luper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.googlecode.androidannotations.annotations.EFragment;


//almost renders Friends from the facebook login
@EFragment
public class TabFriendsFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater infl, ViewGroup vg, Bundle state) {
    if(vg == null) return null;
    return (RelativeLayout)infl.inflate(R.layout.tab_friends_layout, vg, false);
  }

//    private Session userSession;
//    FragmentManager fManager;
//    FragmentTransaction fTrans;
//    private FriendPickerFragment friendPickerFragment;
//    public static final Uri FRIEND_PICKER = Uri.parse("picker://friend");



//    private void onSessionStateChange(final Session session, SessionState sessionState, Exception ex){
//      if(session != null && session.isOpened()){
//        getUserData(session);
//      }
//    }
//
//    private void getUserData(final Session session){
//      Request request = Request.newMeRequest(session,
//          new Request.GraphUserCallback() {
//            @Override
//            public void onCompleted(GraphUser user, Response response) {
//              if(user != null && session == Session.getActiveSession()){
//                //pictureView.setProfileId(user.getId());
//                //userName.setText(user.getName());
//                getFriends();
//
//              }
//              if(response.getError() !=null){
//
//              }
//            }
//          });
//      request.executeAsync();
//    }
//
//    private void getFriends(){
//      Session activeSession = Session.getActiveSession();
//      if(activeSession.getState().isOpened()){
//        Request friendRequest = Request.newMyFriendsRequest(activeSession,
//            new Request.GraphUserListCallback(){
//              @Override
//              public void onCompleted(List<GraphUser> users,
//                                      Response response) {
//                Log.i("INFO", response.toString());
//
//              }
//            });
//        Bundle params = new Bundle();
//        params.putString("fields", "id, name, picture");
//        friendRequest.setParameters(params);
//        friendRequest.executeAsync();
//      }
//    }
//
//// @Override
//    public void onCreate(Bundle savedInstanceState){
//
//   }


}
